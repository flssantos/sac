package br.com.controle;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Cria uma conex�o no hibernate
 */
public class Conexao 
{
	protected String HIBERNATE_CFG = "hibernate.cfg.xml";
	
	/**
	 * Traz uma SessionFactory a partir do xml de configura��o do hibernate
	 * @return SessionFactory retorna um factory de sess�es do hibernate
	 */
	public SessionFactory getSessionFactory()
	{
		
		Configuration cfg = new Configuration().addResource(this.HIBERNATE_CFG).configure();
		
		SessionFactory sessionFactory = cfg
				.buildSessionFactory();
		
		return sessionFactory;
				
	}

}