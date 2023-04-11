package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://localhost:5432/cadastro-jsp-servlets?autoReconnect=true";
	private static String user= "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {
		conectar();
	}
	
	private static void conectar() {
		
		try {
			if(connection == null) {
				 /*carrega o driver de conexão do banco*/
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, senha);
				 /*desabilita alterações no banco sem consentimento*/
				connection.setAutoCommit(false);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
