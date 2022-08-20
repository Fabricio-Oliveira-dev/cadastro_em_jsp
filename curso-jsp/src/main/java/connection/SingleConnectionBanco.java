package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {/*Se fecha sess�es e transa��es*/
	
	private static String banco = "jdbc:postgresql://ec2-44-205-64-253.compute-1.amazonaws.com:5432/dbo9mmuvkkiipc?sslmode=require&autoReconnect=true";
	private static String user = "tvvyzablxhnkhy";
	private static String senha = "37fad8e698e7a7a855d80b34a8c4cddd856effa9d9799fbfaa36d973ee97d9ec";
	private static Connection connection = null;
	
	
	public static Connection getConnection() {
		return connection;
	}
	
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() { /*quando tiver uma inst�ncia ir� conectar*/
		conectar();
	}
	
	
	private static void conectar () {
		
		try {
			
			if (connection == null) {
				Class.forName("org.postgresql.Driver"); /*carrega o driver de conex�o do banco*/
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); /*para n�o efetuar autera��es no banco sem nosso comando*/
			
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace(); /*mostrar qualquer erro no momento de conectar*/
		}
		
	}
	
}
