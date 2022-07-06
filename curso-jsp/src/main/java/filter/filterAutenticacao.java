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
	}

}
