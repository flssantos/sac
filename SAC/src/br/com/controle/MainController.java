package br.com.controle;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.modelo.*;

@ManagedBean(name = "mainController")
@RequestScoped
public class MainController implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msgErro;
	private static EntityManagerFactory emf;
	private EntityManager em;
	
	public MainController()
	{
		
	}
	
	/**
	 * Enum que define os possíveis tipos de atendimento.
	 */
	public enum EnumTipoAtendimento
	{
		TELEFONE("telefone"),
		EMAIL("email"),
		CHAT("chat");
		
		private String enumTipoAtendimento;
		
		/**
		 * @param tipo de atendimento a ser atribuído.
		 */
		EnumTipoAtendimento(String tipoAtendimento)
		{
			enumTipoAtendimento = tipoAtendimento;
		}
		
		/**
		 * @return o tipo de atendimento.
		 */
		public String getEnumTipoAtendimento()
		{
			return enumTipoAtendimento;
		}
	}
	
	/**
	 * Enum que define os possíveis motivos de atendimento.
	 */
	public enum EnumMotivoAtendimento
	{
		DUVIDA("dúvida"),
		ELOGIO("elogio"),
		SUGESTAO("sugestão");
		
		private String enumMotivoAtendimento;
		
		/**
		 * @param motivo para o qual foi criado o atendimento
		 */
		EnumMotivoAtendimento(String motivoAtendimento)
		{
			enumMotivoAtendimento = motivoAtendimento;
		}
		
		/**
		 * @return o motivo do atendimento.
		 */
		public String getEnumMotivoAtendimento()
		{
			return enumMotivoAtendimento;
		}
	}
	
	//public void conectaBD(Conexao c)
	public void conectaBD()
	{
		//c = new Conexao("postgresql", "localhost","5433","SAC_BD","sac","sac");
		//c.conect();
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}
	
	//public void desconectaBD(Conexao c)
	public void desconectaBD()
	{
		//c.disconect();
		em.getTransaction().commit();
        emf.close();
	}
	
	public String geraErro()
	{
		return "erro";
	}

	public String getMsgErro() {
		return msgErro;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}
	
	/**
	 * Lista os atendimentos cadastrados no sistema
	 * @return uma lista com todos os atendimentos, agrupando por dia do atendimento, 
	 * estado e ordenados pela data de atendimento, do mais atual para o mais antigo
	 */
	@SuppressWarnings("unchecked")
	public List<Atendimento> listaAtendimentos()
	{
		List<Atendimento> lista = new LinkedList<Atendimento>();
		
		conectaBD();
		Query query = em.createQuery("select a from atendimento a");
		lista = query.getResultList();
        desconectaBD();
		return lista;
	}

}
