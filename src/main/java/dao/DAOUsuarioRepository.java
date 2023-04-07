package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	/*gera o gráfico de usuário por média salarial de acordo com o perfil com parâmetros de data*/
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		String sql = "SELECT AVG(rendamensal) AS media_salarial, perfil FROM model_login WHERE usuario_id  = ? AND "
						+ "datanascimento >= ? AND datanascimento <= ? GROUP BY perfil";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, userLogado);
		preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd")
													.format(new SimpleDateFormat("dd/mm/yyyy")
													.parse(dataInicial))));
		
		preparedStatement.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd")
													.format(new SimpleDateFormat("dd/mm/yyyy")
													.parse(dataFinal))));
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}
	
	/*gera o gráfico de usuário por média salarial padrão*/
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception {
		
		String sql = "SELECT AVG(rendamensal) AS media_salarial, perfil FROM model_login WHERE usuario_id  = ? GROUP BY  perfil";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, userLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}
	
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
		
		if (objeto.isNovo()) {/*Grava um novo*/
		
		String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento, rendamensal)  "
						+ "VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?,?,?,?,?, ?);";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setString(1, objeto.getLogin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		preparedSql.setLong(5, userLogado);
		preparedSql.setString(6, objeto.getPerfil());
		preparedSql.setString(7, objeto.getSexo());
		preparedSql.setString(8, objeto.getCep());
		preparedSql.setString(9, objeto.getLogradouro());
		preparedSql.setString(10, objeto.getBairro());
		preparedSql.setString(11, objeto.getLocalidade());
		preparedSql.setString(12, objeto.getUf());
		preparedSql.setString(13, objeto.getNumero());
		preparedSql.setDate(14, objeto.getDataNascimento());
		preparedSql.setDouble(15, objeto.getRendamensal());
		preparedSql.execute();
		
		connection.commit();
		
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser =?, extensaofotouser=? WHERE login =?";
				
				preparedSql = connection.prepareStatement(sql);
				
				preparedSql.setString(1, objeto.getFotouser());
				preparedSql.setString(2, objeto.getExtensaofotouser());
				preparedSql.setString(3, objeto.getLogin());
				preparedSql.execute();
				
				connection.commit();
			}
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro =?, localidade=?, uf=?, numero =?, datanascimento =?, rendamensal =? WHERE id =  "+objeto.getId()+";";
			
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setString(5, objeto.getPerfil());
			prepareSql.setString(6, objeto.getSexo());
			prepareSql.setString(7, objeto.getCep());
			prepareSql.setString(8, objeto.getLogradouro());
			prepareSql.setString(9, objeto.getBairro());
			prepareSql.setString(10, objeto.getLocalidade());
			prepareSql.setString(11, objeto.getUf());
			prepareSql.setString(12, objeto.getNumero());
			prepareSql.setDate(13, objeto.getDataNascimento());
			prepareSql.setDouble(14, objeto.getRendamensal());
			prepareSql.executeUpdate();
			
			connection.commit();
			
			
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser =?, extensaofotouser=? WHERE id =?";
				
				prepareSql = connection.prepareStatement(sql);
				
				prepareSql.setString(1, objeto.getFotouser());
				prepareSql.setString(2, objeto.getExtensaofotouser());
				prepareSql.setLong(3, objeto.getId());
				
				prepareSql.execute();
				
				connection.commit();
			}
		}
		return this.consultaUsuario(objeto.getLogin(), userLogado);
	}
	
	/*cria consulta paginada dos usuários*/
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin is false AND usuario_id = " + userLogado + " ORDER BY nome OFFSET "+offset+" LIMIT 5";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	/*total de páginas para paginação*/
	public int totalPagina(Long userLogado) throws Exception {
		
		String sql = "SELECT COUNT(1) AS total FROM model_login  WHERE usuario_id = " + userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		return pagina.intValue();
	}
	
	/*consulta usuários para o relatório*/
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin is false AND usuario_id = " + userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	/*consulta usuários para relatórios com parâmetros de data*/
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin is false AND usuario_id = " + userLogado 
						+ " AND datanascimento >= ? and datanascimento <= ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd")
												.format(new SimpleDateFormat("dd/mm/yyyy")
												.parse(dataInicial))));
		
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd")
												.format(new SimpleDateFormat("dd/mm/yyyy")
												.parse(dataFinal))));
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	/*consulta os usuários*/
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin is false AND usuario_id = " + userLogado + " LIMIT 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	/*consulta usuários da página atual*/
	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		String sql = "SELECT COUNT(1) AS total FROM model_login  WHERE UPPER(nome) LIKE UPPER(?) AND useradmin is false AND usuario_id = ? ";
	
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		return pagina.intValue();
	}
	
	/*consulta setando um número de retorno*/
	public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login  WHERE UPPER(nome) LIKE UPPER(?) AND useradmin is false AND usuario_id = ? OFFSET "+offset+" LIMIT 5";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login  WHERE UPPER(nome) LIKE UPPER(?) AND useradmin is false AND usuario_id = ? LIMIT 5";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	/*consulta os usuários logados*/
	public ModelLogin consultaUsuarioLogado(String login) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resutlado =  statement.executeQuery();
		
		while (resutlado.next()) /*Se tem resultado*/ {
			
			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setUseradmin(resutlado.getBoolean("useradmin"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setSexo(resutlado.getString("sexo"));
			modelLogin.setFotouser(resutlado.getString("fotouser"));
			modelLogin.setCep(resutlado.getString("cep"));
			modelLogin.setLogradouro(resutlado.getString("logradouro"));
			modelLogin.setBairro(resutlado.getString("bairro"));
			modelLogin.setLocalidade(resutlado.getString("localidade"));
			modelLogin.setUf(resutlado.getString("uf"));
			modelLogin.setNumero(resutlado.getString("numero"));
			modelLogin.setDataNascimento(resutlado.getDate("datanascimento"));
			modelLogin.setRendamensal(resutlado.getDouble("rendamensal"));
		}
		return modelLogin;
	}
	
	/*consulta usuário por login*/
	public ModelLogin consultaUsuario(String login) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"') AND useradmin is false ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resutlado =  statement.executeQuery();
		
		while (resutlado.next()) /*Se tem resultado*/ {
			
			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setUseradmin(resutlado.getBoolean("useradmin"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setSexo(resutlado.getString("sexo"));
			modelLogin.setFotouser(resutlado.getString("fotouser"));
			modelLogin.setCep(resutlado.getString("cep"));
			modelLogin.setLogradouro(resutlado.getString("logradouro"));
			modelLogin.setBairro(resutlado.getString("bairro"));
			modelLogin.setLocalidade(resutlado.getString("localidade"));
			modelLogin.setUf(resutlado.getString("uf"));
			modelLogin.setNumero(resutlado.getString("numero"));
			modelLogin.setDataNascimento(resutlado.getDate("datanascimento"));
			modelLogin.setRendamensal(resutlado.getDouble("rendamensal"));
		}
		return modelLogin;
	}
	
	/*consulta usuário por login e que esteja logado*/
	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"') AND useradmin is false AND usuario_id = " + userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resutlado =  statement.executeQuery();
		
		while (resutlado.next()) {
			
			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setSexo(resutlado.getString("sexo"));
			modelLogin.setFotouser(resutlado.getString("fotouser"));
			modelLogin.setCep(resutlado.getString("cep"));
			modelLogin.setLogradouro(resutlado.getString("logradouro"));
			modelLogin.setBairro(resutlado.getString("bairro"));
			modelLogin.setLocalidade(resutlado.getString("localidade"));
			modelLogin.setUf(resutlado.getString("uf"));
			modelLogin.setNumero(resutlado.getString("numero"));
			modelLogin.setDataNascimento(resutlado.getDate("datanascimento"));
			modelLogin.setRendamensal(resutlado.getDouble("rendamensal"));
		}
		return modelLogin;
	}
	
	/*consulta usuário por ID*/
	public ModelLogin consultaUsuarioID(Long id) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE id = ? AND useradmin is false";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		
		ResultSet resutlado =  statement.executeQuery();
		
		while (resutlado.next()) {
			
			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setSexo(resutlado.getString("sexo"));
			modelLogin.setFotouser(resutlado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resutlado.getString("extensaofotouser"));
			modelLogin.setCep(resutlado.getString("cep"));
			modelLogin.setLogradouro(resutlado.getString("logradouro"));
			modelLogin.setBairro(resutlado.getString("bairro"));
			modelLogin.setLocalidade(resutlado.getString("localidade"));
			modelLogin.setUf(resutlado.getString("uf"));
			modelLogin.setNumero(resutlado.getString("numero"));
			modelLogin.setDataNascimento(resutlado.getDate("datanascimento"));
			modelLogin.setRendamensal(resutlado.getDouble("rendamensal"));
		}	
		return modelLogin;
	}
	
	/*consulta usuário por ID e que esteja logado*/
	public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login where id = ? AND useradmin is false AND usuario_id = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		
		ResultSet resutlado =  statement.executeQuery();
		
		while (resutlado.next()) {
			
			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setSexo(resutlado.getString("sexo"));
			modelLogin.setFotouser(resutlado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resutlado.getString("extensaofotouser"));
			modelLogin.setCep(resutlado.getString("cep"));
			modelLogin.setLogradouro(resutlado.getString("logradouro"));
			modelLogin.setBairro(resutlado.getString("bairro"));
			modelLogin.setLocalidade(resutlado.getString("localidade"));
			modelLogin.setUf(resutlado.getString("uf"));
			modelLogin.setNumero(resutlado.getString("numero"));
			modelLogin.setDataNascimento(resutlado.getDate("datanascimento"));
			modelLogin.setRendamensal(resutlado.getDouble("rendamensal"));
		}
		return modelLogin;
	}
	
	/*valida login*/
	public boolean validarLogin(String login) throws Exception {
		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE UPPER(login) = UPPER('"+login+"');";
		
        PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado =  statement.executeQuery();
		resultado.next();
		
		return resultado.getBoolean("existe");
	}
	
	/*deleta o usuário*/
	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? AND useradmin is false;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();
		
		connection.commit();
	}
	
	/*lista usuários pai do sistema*/
	public List<ModelTelefone> listFone(Long idUserPai) throws Exception {
		
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		
		String sql = "SELECT * FROM telefone WHERE usuario_pai_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, idUserPai);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		while (rs.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
		}
		return retorno;
	}
}