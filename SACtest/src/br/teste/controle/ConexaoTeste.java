package br.teste.controle;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.controle.Conexao;

/**
 * Cria uma conex�o no hibernate para testes automatizados
 */
public class ConexaoTeste extends Conexao
{
	String HIBERNATE_CFG = "hibernate-test.cfg.xml";
	
	/**
	 * Traz uma SessionFactory a partir do xml de configura��o do hibernate
	 * @return SessionFactory retorna um factory de sess�es do hibernate
	 */
	@Override
	public SessionFactory getSessionFactory()
	{
		
		Configuration cfg = new Configuration().addResource(this.HIBERNATE_CFG).configure();
		
		SessionFactory sessionFactory = cfg
				.buildSessionFactory();
		
		return sessionFactory;
				
	}
}
