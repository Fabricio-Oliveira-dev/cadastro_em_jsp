package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.jasper.tagplugins.jstl.core.Catch;

import connection.SingleConnectionBanco;
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

@WebFilter(urlPatterns = { "/principal/*" }) /* intercepta todas as requisições que vierem do projeto */
public class filterAutenticacao extends HttpFilter {

	private static Connection connection;

	private static final long serialVersionUID = 1L;

	public filterAutenticacao() {
		super();
	}

	/* encerra os processos quando o servidor é parado */
	// mataria os processos de conexão com o banco
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* intercepta todas as requsicições do projeto e dá as respostas */
	/* tudo o que fizer no sistema vai fazer por aqui */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath(); // url que está sendo acessada

			/* validar se está logado, senão redireciona para a tela de login */

			if (usuarioLogado == null || usuarioLogado.equals("null")
					&& !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin") /* não está logado */
			) {

				RequestDispatcher redireciona = (RequestDispatcher) request
						.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor, realize o login correto");
				redireciona.forward(request, response);
				return; // para a execução e redireciona para o login

			} else {

				chain.doFilter(request, response); // acima valida, chain deixa o processo continuar
			}
			connection.commit(); /*deu tudo certo? comita no banco as alterações*/
			
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
	// iniciar a conexão com o banco
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}

}
