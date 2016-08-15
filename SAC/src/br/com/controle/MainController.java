package br.com.controle;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import br.com.modelo.*;

@ManagedBean(name = "mainController")
@SessionScoped
public class MainController implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msgErro;
	//@PersistenceUnit(unitName="sac")
	//private static EntityManagerFactory emf;
	//@PersistenceContext
	//private EntityManager em;
	private Conexao c;
	private SessionFactory sessionFactory;
	private Session sessionBD;
	private Atendimento atendimento;
	private Estado estado;
	
	public MainController()
	{
		c = new Conexao("postgresql", "localhost","5433","SAC_BD","sac","sacsenha");
	}
	
	/**
	 * Enum que define os possíveis tipos de atendimento.
	 */
	public enum EnumTipoAtendimento
	{
		TELEFONE("Telefone"),
		EMAIL("Email"),
		CHAT("Chat");
		
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
		public String getValor()
		{
			return enumTipoAtendimento;
		}
	}
	
	/**
	 * Enum que define os possíveis motivos de atendimento.
	 */
	public enum EnumMotivoAtendimento
	{
		DUVIDA("Dúvida"),
		ELOGIO("Elogio"),
		SUGESTAO("Sugestão");
		
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
		public String getValor()
		{
			return enumMotivoAtendimento;
		}
	}
	
	public void conectaBD()
	{
		c.conect();
	}
	
	public void desconectaBD()
	{
		c.disconect();
	}
	
	public void openHibernateSession()
	{
		sessionFactory = c.getSessionFactory();
		sessionBD = sessionFactory.openSession();
		sessionBD.beginTransaction();
	}
	
	public void closeHibernateSession()
	{
		sessionBD.getTransaction().commit();
		sessionBD.close();
		sessionFactory.close();
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
	
	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	/**
	 * Retorna todos os tipos possíveis de atendimentos
	 * @return lista com os tipos existentes de atendimento
	 */
	public List<EnumTipoAtendimento> retornaTiposAtendimentosPossiveis() 
	{
		List<EnumTipoAtendimento> tipos = new LinkedList<EnumTipoAtendimento>();
		for(EnumTipoAtendimento enumTipo : EnumTipoAtendimento.values())
		{
			tipos.add(enumTipo);
		}
		return tipos;
	}
	
	/**
	 * Retorna todos os motivos possíveis de atendimentos
	 * @return lista com os motivos existentes de atendimento
	 */
	public List<EnumMotivoAtendimento> retornaMotivosAtendimentosPossiveis() 
	{
		List<EnumMotivoAtendimento> motivos = new LinkedList<EnumMotivoAtendimento>();
		for(EnumMotivoAtendimento enumMotivo : EnumMotivoAtendimento.values())
		{
			motivos.add(enumMotivo);
		}
		return motivos;
	}
	
	/**
	 * Retorna todos os estados possíveis
	 * @return lista com todos os estados da federação que estão cadastrados no sistema
	 */
	@SuppressWarnings("unchecked")
	public List<Estado> retornaEstadosPossiveis()
	{
		openHibernateSession();
		Criteria criteria = sessionBD.createCriteria(Estado.class);
		criteria.addOrder(Order.asc("nome"));
		List<Estado> estados = criteria.list();
		closeHibernateSession();
		
		return estados;
	}

	/**
	 * Este método converte uma data para uma String
	 * com o pattern utilizado no Brasil
	 * 
	 * @param Date a data que será convertida.
	 * 
	 * @return String Data convertida.
	 */
	public String formataDataBrasil(Date data)
	{
		return getDateAsString(data, "dd/MM/yyyy");
	}
	
	/**
	 * Este método converte uma data para uma String, utilizando um pattern passado
	 * por parâmetro.
	 * 
	 * @param unconvertedDate Date Data que será convertida.
	 * @param pattern String Padrão para conversão da data.
	 * 
	 * @return String Data convertida.
	 */
	public String getDateAsString(Date unconvertedDate, String pattern)
	{
		if (unconvertedDate == null)
		{
			System.out.println("Data passada é nula. O retorno do método é null (a ser tratado pela interface)");
			return null;
		}
		
		StringBuffer convertedDate = new StringBuffer();
		
		SimpleDateFormat convertedDateFormat = new SimpleDateFormat(pattern);
		
		convertedDate = convertedDateFormat.format(unconvertedDate, convertedDate, new FieldPosition(0));
		
		return convertedDate.toString();
	}
	
	/**
	 * Lista os atendimentos cadastrados no sistema
	 * @return uma lista com todos os atendimentos, ordenando por dia do atendimento
	 * (do mais atual para o mais antigo) e estado
	 * @throws uma exceção de BD
	 */
	public List<Atendimento> listaAtendimentos() throws SQLException
	{
		List<Atendimento> lista = new LinkedList<Atendimento>();
		
		conectaBD();
		StringBuilder ejbql = new StringBuilder();
		ejbql.append(" SELECT a.id, a.tipo, ");
		ejbql.append(" a.motivo, a.detalhes, a.data,"); 
		//ejbql.append(" to_char(a.data, 'dd/mm/yyyy') data_formatada, ");
		ejbql.append(" e.sigla, e.nome");
		ejbql.append(" FROM public.\"ATENDIMENTO\" a");
		ejbql.append(" JOIN public.\"ESTADO\" e on a.estado = e.sigla");
		ejbql.append(" order by a.data desc, e.nome, a.tipo, a.motivo");
		
		
		ResultSet rs = c.executaQuery(ejbql.toString());
		
		while (rs.next())
		{
			Atendimento atendimento = new Atendimento();
			
			atendimento.setId(rs.getInt("id"));
			atendimento.setTipo(rs.getString("tipo").charAt(0));
			for (EnumTipoAtendimento enumTipo : EnumTipoAtendimento.values())
			{
				if (atendimento.getTipo() == enumTipo.name().charAt(0))
				{
					atendimento.setTipoExtenso(enumTipo.getValor());
					break;
				}
			}
			atendimento.setMotivo(rs.getString("motivo").charAt(0));
			for (EnumMotivoAtendimento enumMotivo : EnumMotivoAtendimento.values())
			{
				if (atendimento.getMotivo() == enumMotivo.name().charAt(0))
				{
					atendimento.setMotivoExtenso(enumMotivo.getValor());
					break;
				}
			}
			atendimento.setDetalhes(rs.getString("detalhes"));
			atendimento.setData(rs.getDate("data"));
			Estado estado = new Estado(rs.getString("sigla"), rs.getString("nome"));
			atendimento.setEstado(estado);
			
			lista.add(atendimento);
		}
		
        desconectaBD();
		return lista;
	}

	/**
	 * Lista os atendimentos cadastrados no sistema, agrupados em estados
	 * @return uma lista com todos os atendimentos, agrupando por estado 
	 * e mantendo a ordem da lista trazida em listaAtendimentos()
	 * @throws uma exceção de BD
	 */
	public Map<Estado, List<Atendimento>> listaAtendimentosPorEstado() throws SQLException
	{
		List<Atendimento> atendimentos = listaAtendimentos();
		Map<Estado, List<Atendimento>> atendimentosPorEstado = new LinkedHashMap<Estado, List<Atendimento>>();
		
		for (Atendimento atendimento : atendimentos)
		{
			if (atendimentosPorEstado.containsKey(atendimento.getEstado()))
			{
				atendimentosPorEstado.get(atendimento.getEstado()).add(atendimento);
			}
			else
			{
				List<Atendimento> listaAux = new LinkedList<Atendimento>();
				listaAux.add(atendimento);
				atendimentosPorEstado.put(atendimento.getEstado(), listaAux);
			}
		}
		
		return atendimentosPorEstado;
	}
	
	
	/**
	 * Lista os atendimentos cadastrados no sistema, agrupados por data e por estados
	 * @return uma lista com todos os atendimentos, agrupando por data e estado 
	 * e mantendo a ordem da lista trazida em listaAtendimentosPorEstado()
	 * @throws uma exceção de BD
	 */
	public Map<Date, Map<Estado, List<Atendimento>>> listaAtendimentosPorEstadoPorData() throws SQLException
	{
		List<Atendimento> atendimentos = listaAtendimentos();
		Map<Date, Map<Estado, List<Atendimento>>> atendimentosPorEstadoPorData = 
				new LinkedHashMap<Date, Map<Estado, List<Atendimento>>>();
		
		for (Atendimento atendimento : atendimentos)
		{	
			Date data = atendimento.getData();
			Estado estado  = atendimento.getEstado();
			
			if (atendimentosPorEstadoPorData.containsKey(data))
			{
				if (atendimentosPorEstadoPorData.get(data).containsKey(estado))
				{
					atendimentosPorEstadoPorData.get(data).get(estado).add(atendimento);
				}
				else
				{
					List<Atendimento> listaAux = new LinkedList<Atendimento>();
					listaAux.add(atendimento);
					atendimentosPorEstadoPorData.get(data).put(estado, listaAux);
				}
			}
			else
			{
				List<Atendimento> listaAux = new LinkedList<Atendimento>();
				listaAux.add(atendimento);
				Map<Estado, List<Atendimento>> MapaAux = new LinkedHashMap<Estado, List<Atendimento>>();
				MapaAux.put(estado, listaAux);
				atendimentosPorEstadoPorData.put(data, MapaAux);
			}
			
		}
		
		return atendimentosPorEstadoPorData;
	}
	
	public void iniciaEntidades()
	{
		this.atendimento = new Atendimento();
		this.estado = new Estado();
	}
	
	
	@SuppressWarnings("unchecked")
	public String persistAtendimento()
	{
		openHibernateSession();
		this.estado = (Estado) sessionBD.get(Estado.class, this.estado.getSigla());
		if (this.estado == null)
		{
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Estado não encontrado.",
                                "Erro no cadastro!"));
			return "cadastroAtendimento.xhtml";
		}
		this.atendimento.setEstado(this.estado);
		
		for (EnumTipoAtendimento enumTipo : EnumTipoAtendimento.values())
		{
			if (this.atendimento.getTipo() == enumTipo.name().charAt(0))
			{
				atendimento.setTipoExtenso(enumTipo.getValor());
			}
		}
		
		if ((this.atendimento.getTipoExtenso() == null) || ("".equals(this.atendimento.getTipoExtenso())))
		{
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de atendimento não encontrado.",
                                "Erro no cadastro!"));
			return "cadastroAtendimento.xhtml";
		}
		
		for (EnumMotivoAtendimento enumMotivo : EnumMotivoAtendimento.values())
		{
			if (this.atendimento.getMotivo() == enumMotivo.name().charAt(0))
			{
				atendimento.setMotivoExtenso(enumMotivo.getValor());
			}
		}
		
		if ((this.atendimento.getMotivoExtenso() == null) || ("".equals(this.atendimento.getMotivoExtenso())))
		{
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Motivo de atendimento não encontrado.",
                                "Erro no cadastro!"));
			return "cadastroAtendimento.xhtml";
		}
		
		this.atendimento.setData(new Date());
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Atendimento.class).setProjection( Projections.max("id") );
		List<Atendimento> atendimentos = sessionBD.createCriteria(Atendimento.class).add( Property.forName("id").eq(subCriteria) ).list();
		int id = atendimentos.get(0).getId();
		
		this.atendimento.setId(++id);
		
		sessionBD.save(this.atendimento);
		closeHibernateSession();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));;
		return "cadastroAtendimento.xhtml";
	}

}
