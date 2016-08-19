package br.com.controle;

import java.io.Serializable;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import br.com.modelo.*;

/**
 * Classe de controle da aplicação
 */
@ManagedBean(name = "mainController")
@SessionScoped
public class MainController implements Serializable
{
	protected static final long serialVersionUID = 1L;
	protected String msgSistema;
	protected Conexao conexao;
	protected SessionFactory sessionFactory;
	protected Session sessionBD;
	protected Atendimento atendimento;
	protected Estado estado;
	
	/**
	 * Construtor padrão
	 */
	public MainController()
	{
		conexao = new Conexao();
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
	
	/**
	 * abre uma sessão do hibernate e uma transação de DB
	 */
	public void openHibernateSession()
	{
		sessionFactory = conexao.getSessionFactory();
		sessionBD = sessionFactory.openSession();
		sessionBD.beginTransaction();
	}
	
	/**
	 * fecha uma sessão do hibernate e comita a transação de DB
	 */
	public void closeHibernateSession()
	{
		sessionBD.getTransaction().commit();
		sessionBD.close();
		sessionFactory.close();
	}
	
	/**
	 * redirect para página de erro
	 * @return String direcionando para página de erro
	 */
	public String geraErro()
	{
		return "erro";
	}

	/**
	 * retorna mensagem do sistema a ser exibida na tela
	 * @return String com a mensagem a ser exibida
	 */
	public String getMsgSistema() 
	{
		return msgSistema;
	}

	/**
	 * configura mensagem do sistema a ser exibida na tela
	 * @param String com a mensagem a ser exibida
	 */
	public void setMsgSistema(String msg) 
	{
		this.msgSistema = msg;
	}
	
	/**
	 * retorna o atendimento que está sendo manipulado
	 * @return Atendimento a ser manipulado
	 */
	public Atendimento getAtendimento() 
	{
		return atendimento;
	}

	/**
	 * configura o atendimento que está sendo manipulando
	 * @param atendimento a ser manipulado
	 */
	public void setAtendimento(Atendimento atendimento)
	{
		this.atendimento = atendimento;
	}
	
	/**
	 * retorna o estado que está sendo manipulado
	 * @return Estado a ser manipulado
	 */
	public Estado getEstado()
	{
		return estado;
	}

	/**
	 * configura o estado que está sendo manipulando
	 * @param estado a ser manipulado
	 */
	public void setEstado(Estado estado)
	{
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
	@SuppressWarnings("unchecked")
	public List<Atendimento> listaAtendimentos() throws SQLException
	{
		List<Atendimento> lista = new LinkedList<Atendimento>();
		
		openHibernateSession();
		
		StringBuilder ejbql = new StringBuilder();
		ejbql.append(" SELECT a.id, a.tipo, ");
		ejbql.append(" a.motivo, a.detalhes, a.data,"); 
		//ejbql.append(" to_char(a.data, 'dd/mm/yyyy') data_formatada, ");
		ejbql.append(" e.sigla, e.nome");
		ejbql.append(" FROM \"ATENDIMENTO\" a");
		ejbql.append(" JOIN \"ESTADO\" e on a.estado = e.sigla");
		ejbql.append(" order by a.data desc, e.nome, a.tipo, a.motivo");
		
		
		SQLQuery query = sessionBD.createSQLQuery(ejbql.toString());
		List<Object[]> rows = query.list();
		
		final int IND_ID = 0;
		final int IND_TIPO = 1;
		final int IND_MOTIVO = 2;
		final int IND_DETALHES = 3;
		final int IND_DATA = 4;
		final int IND_SIGLA = 5;
		final int IND_NOME_ESTADO = 6;

		for(Object[] row : rows)
		{
			Atendimento atendimento = new Atendimento();
			
			atendimento.setId(Integer.parseInt(row[IND_ID].toString()));
			atendimento.setTipo(row[IND_TIPO].toString().charAt(0));
			for (EnumTipoAtendimento enumTipo : EnumTipoAtendimento.values())
			{
				if (atendimento.getTipo() == enumTipo.name().charAt(0))
				{
					atendimento.setTipoExtenso(enumTipo.getValor());
					break;
				}
			}
			atendimento.setMotivo(row[IND_MOTIVO].toString().charAt(0));
			for (EnumMotivoAtendimento enumMotivo : EnumMotivoAtendimento.values())
			{
				if (atendimento.getMotivo() == enumMotivo.name().charAt(0))
				{
					atendimento.setMotivoExtenso(enumMotivo.getValor());
					break;
				}
			}
			atendimento.setDetalhes(row[IND_DETALHES].toString());
			atendimento.setData((Date) row[IND_DATA]);
			Estado estado = new Estado(row[IND_SIGLA].toString(), row[IND_NOME_ESTADO].toString());
			atendimento.setEstado(estado);
			
			lista.add(atendimento);
		}
		
		closeHibernateSession();
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
	
	/**
	 * Inicia as entidades a serem manipuladas pelo Controller
	 */
	public void iniciaEntidades()
	{
		this.atendimento = new Atendimento();
		this.estado = new Estado();
	}
	
	/**
	 * Inicia as entidades a serem manipuladas pelo Controller
	 */
	public Session getSessionBD() 
	{
		return sessionBD;
	}

	/**
	 * Configura a sessão de BD a ser utilizada
	 * @param uma sessão de BD
	 */
	public void setSessionBD(Session sessionBD) 
	{
		this.sessionBD = sessionBD;
	}
	
	/**
	 * Recupera uma conexão de BD
	 * @return uma conexão de BD
	 */
	public Conexao getConexao() 
	{
		return conexao;
	}

	/**
	 * Configura uma conexão de BD
	 * @param uma conexão de BD
	 */
	public void setConexao(Conexao conexao) 
	{
		this.conexao = conexao;
	}
	
	/**
	 * Abre uma sessão do hibernate e persiste na base um atendimento
	 * @return a página a ser redirecionada o sistema
	 */
	public String salvaAtendimento()
	{
		String redirect = "";
		openHibernateSession();
		redirect = persistAtendimento();
		closeHibernateSession();
		
		return redirect;
	}
	
	/**
	 * persiste na base um atendimento
	 * @return a página a ser redirecionada o sistema
	 */
	@SuppressWarnings("unchecked")
	public String persistAtendimento()
	{
		this.estado = (Estado) sessionBD.get(Estado.class, this.estado.getSigla());
		if (this.estado == null)
		{
			setMsgSistema("Estado não encontrado.");
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, getMsgSistema(),
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
			setMsgSistema("Tipo de atendimento não encontrado.");
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, getMsgSistema(),
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
			setMsgSistema("Motivo de atendimento não encontrado.");
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, getMsgSistema(),
                                "Erro no cadastro!"));
			return "cadastroAtendimento.xhtml";
		}
		
		if (this.atendimento.getData() == null)
		{
			this.atendimento.setData(new Date());
		}
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Atendimento.class).setProjection( Projections.max("id") );
		List<Atendimento> atendimentos = sessionBD.createCriteria(Atendimento.class).add( Property.forName("id").eq(subCriteria) ).list();
		int id = atendimentos.get(0).getId();
		
		this.atendimento.setId(++id);
		
		sessionBD.save(this.atendimento);
		setMsgSistema("Cadastro realizado com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getMsgSistema()));
		return "cadastroAtendimento.xhtml";
	}


}
