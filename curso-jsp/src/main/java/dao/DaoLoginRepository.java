package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoLoginRepository {
	
	private Connection connection;
	
	public DaoLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	/*valida o perfil do usuário*/
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER(?) AND UPPER(senha) = UPPER(?) ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return true;/*autenticado*/
		}
		return false; /*não autenticado*/
	}

}
