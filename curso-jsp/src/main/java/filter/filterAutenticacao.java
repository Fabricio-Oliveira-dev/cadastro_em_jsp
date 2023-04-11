package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*" }) /* intercepta todas as requisi��es que vierem do projeto */
public class filterAutenticacao extends HttpFilter {

	private static Connection connection;

	private static final long serialVersionUID = 1L;

	public filterAutenticacao() {
		super();
	}

	/* encerra os processos quando o servidor � parado */
	// mataria os processos de conex�o com o banco
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* intercepta todas as requsici��es do projeto e d� as respostas */
	/* tudo o que fizer no sistema vai fazer por aqui */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath(); // url que est� sendo acessada

			/* validar se est� logado, sen�o redireciona para a tela de login */

			if (usuarioLogado == null || usuarioLogado.equals("null")
					&& !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin") /* n�o est� logado */
			) {

				RequestDispatcher redireciona = (RequestDispatcher) request
						.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor, realize o login correto");
				redireciona.forward(request, response);
				return; // para a execu��o e redireciona para o login

			} else {

				chain.doFilter(request, response); // acima valida, chain deixa o processo continuar
			}
			connection.commit(); /*deu tudo certo? comita no banco as altera��es*/
			
		} catch (Exception e) {
			e.printStackTrace();
			
			jakarta.servlet.RequestDispatcher redirecionar = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/* executado quando o servidor sobe o projeto */
	// iniciar a conex�o com o banco
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
		
		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();
		
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;
		
		File[] filesSql = new File(caminhoPastaSQL).listFiles();
		
		try {
			
			for (File file : filesSql) {
				
				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				
				if (!arquivoJaRodado) {
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
						while (lerArquivo.hasNext()) {
							
							sql.append(lerArquivo.nextLine());
							sql.append("\n");
						}
						
					connection.prepareStatement(sql.toString()).execute();
					daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
					
					connection.commit();
					lerArquivo.close();
				}
			}
			
		}catch (Exception e) {
			
				try {
					
					connection.rollback();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			
			e.printStackTrace();
		}
	}

}