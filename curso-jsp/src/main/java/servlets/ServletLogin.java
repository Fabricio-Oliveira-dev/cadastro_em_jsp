package servlets;

import java.io.IOException;

import dao.DaoLoginRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

/*Controller s�o servlets*/
@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) /* Mapeamento de URL que vem da tela */
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoLoginRepository daoLoginRepository = new DaoLoginRepository();

	public ServletLogin() {
	}

	/* recebe os dados pela url em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/* recebe os dados enviados por um formul�rio */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
			/* valida��o se foi informado os dados */
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				/* pegando par�metro e passando para objeto */
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);

				/* redirecionando para a tela depois do login */
				if (daoLoginRepository.validarAutenticacao(modelLogin)) { /* simulando login */

					request.getSession().setAttribute("usuario", modelLogin.getLogin());

					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp"; /*
															 * se n�o tiver uma tela sendo acessada, coloca a tela
															 * incial do sistema
															 */
					}

					jakarta.servlet.RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {
					/* redirecionamento para tela de login se estiver errado */
					jakarta.servlet.RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha corretamente");
					redirecionar.forward(request, response);
				}

			} else {
				/* redirecionamento para tela de login se estiver incompleto */
				jakarta.servlet.RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", "Informe o login e senha corretamente");
				redirecionar.forward(request, response);
			}

			/* autentica��o */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
