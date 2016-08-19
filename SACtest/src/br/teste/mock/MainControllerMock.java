package br.teste.mock;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import br.com.controle.Conexao;
import br.com.controle.MainController;
import br.com.modelo.Atendimento;
import br.com.modelo.Estado;

/**
 * Classe que simula a controle da aplicação
 */
public class MainControllerMock extends MainController
{

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor padrão
	 */
	public MainControllerMock(Conexao conexao)
	{
		setConexao(conexao); 
	}
	
	/**
	 * apaga todos os estados presentes na base
	 */
	public void apagaEstados()
	{
		getSessionBD().createSQLQuery("delete from public.\"ESTADO\"").executeUpdate();
	}
	
	/**
	 * apaga todos os atendimentos presentes na base
	 */
	public void apagaAtendimentos()
	{
		getSessionBD().createSQLQuery("delete from public.\"ATENDIMENTO\"").executeUpdate();
	}
	
	/**
	 * sobrescreve o método que persiste um atendimento para que não redirecione
	 * para uma página
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String persistAtendimento()
	{
		this.estado = (Estado) sessionBD.get(Estado.class, this.estado.getSigla());
		if (this.estado == null)
		{
			setMsgSistema("Estado não encontrado.");
			return "fail";
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
			return "fail";
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
			return "fail";
		}
		
		if (this.atendimento.getData() == null)
		{
			this.atendimento.setData(new Date());
		}
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Atendimento.class).setProjection( Projections.max("id") );
		List<Atendimento> atendimentos = sessionBD.createCriteria(Atendimento.class).add( Property.forName("id").eq(subCriteria) ).list();
		int id;
		if (atendimentos != null && !atendimentos.isEmpty())
		{
			id = atendimentos.get(0).getId();
		}
		else
		{
			id = 1;
		}
		
		this.atendimento.setId(++id);
		
		sessionBD.save(this.atendimento);
		setMsgSistema("Cadastro realizado com sucesso!");
		return "ok";
	}
}
