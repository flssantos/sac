package br.com.controle;
import java.sql.*;

import org.hibernate.SessionFactory;
//import org.hibernate.service.ServiceRegistry;
import org.hibernate.cfg.Configuration;

public class Conexao 
{
	private String local;
	private String user;
	private String senha;
	private Connection c;
	private Statement statment;
	private String str_conexao;
	private String driverjdbc;
	private String dialect;

	public Conexao(String bd, String local, String porta, String banco, String user, String senha) 
	{
		if ("postgresql".equals(bd))
		{
			setConexaoPostgreSQL(local, porta, banco, user, senha);
		}
		//else... another DB
  	} 

	public void setConexaoPostgreSQL(String local, String porta, String banco, String user, String senha)
	{
		setStr_conexao("jdbc:postgresql://"+ local +":" + porta +"/"+ banco);
		setLocal(local);
		setSenha(senha);
		setUser(user);
		setDriverjdbc("org.postgresql.Driver");
		setDialect("org.hibernate.dialect.PostgreSQLDialect");
	}
	
	public SessionFactory getSessionFactory()
	{
		String HIBERNATE_CFG = "hibernate.cfg.xml";
		
		Configuration cfg = new Configuration().addResource(HIBERNATE_CFG).configure();
        //ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().
         //       applySettings(cfg.getProperties()).buildServiceRegistry();
        //SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		SessionFactory sessionFactory = cfg
				//.setProperty("hibernate.dialect", getDialect())
				//.setProperty("hibernate.connection.driver_class", getDriverjdbc())
				//.setProperty("hibernate.connection.url", getStr_conexao())
				//.setProperty("hibernate.connection.username", getUser())
				//.setProperty("hibernate.connection.password", getSenha())
				//.setProperty("hibernate.show_sql", "false")
				//.setProperty("hibernate.format_sql", "false")
				//.setProperty("hibernate.c3p0.acquire_increment", "1")
				//.setProperty("hibernate.c3p0.idle_test_period", "100")
				//.setProperty("hibernate.c3p0.max_size", "10")
				//.setProperty("hibernate.c3p0.max_statements", "0")
				//.setProperty("hibernate.c3p0.min_size", "5")
				//.setProperty("hibernate.c3p0.timeout", "100")
				//.addAnnotatedClass(Estado.class)
				//.addAnnotatedClass(Atendimento.class)
				.buildSessionFactory();
		
		return sessionFactory;
				
	}
	
	public void configUser(String user, String senha) {
		setUser(user);
		setSenha(senha);
	}

	public void configLocal(String banco) {
		setLocal(banco);
	}

	//Conexão com o Banco de Dados
	public void conect(){
		try {
			Class.forName(getDriverjdbc());
			setC(DriverManager.getConnection(getStr_conexao(), getUser(), getSenha()));
			setStatment(getC().createStatement());
		}catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} 
	}

	public void disconect(){
		try {
			getC().close();
		}catch (SQLException ex) {
			System.err.println(ex);
			ex.printStackTrace();
		}
	}

	public ResultSet executaQuery(String query){
		try {
			return getStatment().executeQuery(query);
		}catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	} 

	// GETs AND SETs
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Connection getC() {
		return c;
	}

	public void setC(Connection c) {
		this.c = c;
	}

	public Statement getStatment() {
		return statment;
	}

	public void setStatment(Statement statment) {
		this.statment = statment;
	}

	public String getStr_conexao() {
		return str_conexao;
	}

	public void setStr_conexao(String str_conexao) {
		this.str_conexao = str_conexao;
	}

	public String getDriverjdbc() {
		return driverjdbc;
	}

	public void setDriverjdbc(String driverjdbc) {
		this.driverjdbc = driverjdbc;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

}