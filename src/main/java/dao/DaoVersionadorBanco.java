package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;

public class DaoVersionadorBanco implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;

	public DaoVersionadorBanco() {
		connection = SingleConnectionBanco.getConnection();
	}

	public void gravaArquivoSqlRodado(String nome_file) throws Exception {

		String sql = "INSERT INTO versionadorbanco(arquivo_sql) VALUES (?);";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, nome_file);
		preparedStatement.execute();
	}

	public boolean arquivoSqlRodado(String nome_do_arquivo) throws Exception {

		String sql = "SELECT COUNT(1) > 0 AS rodado FROM versionadorbanco WHERE arquivo_sql = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, nome_do_arquivo);

		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();

		return resultSet.getBoolean("rodado");
	}
}
