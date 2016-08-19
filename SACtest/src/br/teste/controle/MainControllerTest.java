package br.teste.controle;

import static org.testng.AssertJUnit.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidStateException;
import org.junit.Before;
import org.junit.Test;

import br.com.modelo.Atendimento;
import br.com.modelo.Estado;
import br.teste.mock.MainControllerMock;

/**
 * Classe testa a classe de controle da aplicação
 */
public class MainControllerTest
{
	private MainControllerMock mainController;
	
	/**
	 * método a ser executado logo no inicio da execução da classe
	 * Configura a conexão e limpa o BD, apagando seus dados
	 */
	@Before
	public void init() throws Exception 
	{
		mainController = new MainControllerMock(new ConexaoTeste());
		mainController.openHibernateSession();
		mainController.apagaAtendimentos();
		mainController.apagaEstados();
		mainController.closeHibernateSession();
	}
	
	/**
	 * testa o comportamento do sistema quando o atendimento a ser salvo
	 * tem um tipo de atendimento não aceito pela base
	 * 
	 */
	@Test
	public void testaNovoAtendimentoTipoInexistente()
	{
		String msg = "Tipo de atendimento não encontrado.";
		Estado estado = new Estado("RJ", "Rio de Janeiro");
		
		mainController.openHibernateSession();
		mainController.getSessionBD().save(estado);
		mainController.setEstado(estado);
		
		Atendimento atendimento = new Atendimento();
		atendimento.setTipo('Z');
		atendimento.setEstado(estado);
		atendimento.setMotivo('S');
		atendimento.setDetalhes("N/D");
		atendimento.setData(new Date());
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		
		assertEquals("Tipo de atendimento 'Z' não deveria existir na base", msg,
				mainController.getMsgSistema());
		
		mainController.apagaAtendimentos();
		mainController.apagaEstados();
		mainController.closeHibernateSession();
	}
	
	/**
	 * testa o comportamento do sistema quando o atendimento a ser salvo
	 * tem um estado da federação não existente na base
	 * 
	 */
	@Test
	public void testaNovoAtendimentoEstadoInexistente()
	{
		String msg = "Estado não encontrado.";
		Estado estado = new Estado("NY", "Nova Iorque");
		
		mainController.openHibernateSession();
		mainController.setEstado(estado);
		
		Atendimento atendimento = new Atendimento();
		atendimento.setTipo('T');
		atendimento.setEstado(estado);
		atendimento.setMotivo('D');
		atendimento.setDetalhes("N/D");
		atendimento.setData(new Date());
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		
		assertEquals("Estado de Nova Iorque não deveria existir na base", msg,
				mainController.getMsgSistema());
		
		mainController.apagaAtendimentos();
		mainController.apagaEstados();
		mainController.closeHibernateSession();
	}
	
	/**
	 * testa o comportamento do sistema quando o atendimento a ser salvo
	 * tem um motivo de atendimento não aceito pela base
	 * 
	 */
	@Test
	public void testaNovoAtendimentoMotivoInexistente()
	{
		String msg = "Motivo de atendimento não encontrado.";
		Estado estado = new Estado("RJ", "Rio de Janeiro");
		
		mainController.openHibernateSession();
		mainController.getSessionBD().save(estado);
		mainController.setEstado(estado);
		
		Atendimento atendimento = new Atendimento();
		atendimento.setTipo('C');
		atendimento.setEstado(estado);
		atendimento.setMotivo('Z');
		atendimento.setDetalhes("N/D");
		atendimento.setData(new Date());
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		
		assertEquals("Motivo de atendimento 'Z' não deveria existir na base", msg,
				mainController.getMsgSistema());
		
		mainController.apagaAtendimentos();
		mainController.apagaEstados();
		mainController.closeHibernateSession();
	}
	
	/**
	 * testa o comportamento do sistema quando o atendimento a ser salvo
	 * não tem uma data
	 * 
	 */
	@Test
	public void testaNovoAtendimentoSemData()
	{
		String msg = "Cadastro realizado com sucesso!";
		Estado estado = new Estado("RJ", "Rio de Janeiro");
		
		mainController.openHibernateSession();
		mainController.getSessionBD().save(estado);
		mainController.setEstado(estado);
		
		Atendimento atendimento = new Atendimento();
		atendimento.setTipo('C');
		atendimento.setEstado(estado);
		atendimento.setMotivo('E');
		atendimento.setDetalhes("N/D");
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		
		assertEquals("Cadastro deveria ter sido realizado com data atual como default", msg,
				mainController.getMsgSistema());
		
		mainController.apagaAtendimentos();
		mainController.apagaEstados();
		mainController.closeHibernateSession();
	}
	
	/**
	 * testa o comportamento do sistema quando se excede o tamanho
	 * do campo de detalhes do atendimento
	 * 
	 */
	@Test(expected=InvalidStateException.class)
	public void testaNovoAtendimentoComDetalhesExcedentes()
	{
		Estado estado = new Estado("RJ", "Rio de Janeiro");
		final int MAX_SIZE_DETALHES = 500;
		
		mainController.openHibernateSession();
		mainController.getSessionBD().save(estado);
		mainController.setEstado(estado);
		
		Atendimento atendimento = new Atendimento();
		atendimento.setTipo('C');
		atendimento.setEstado(estado);
		atendimento.setMotivo('E');
		String detalhes = "";
		for(int i = 0; i <= (MAX_SIZE_DETALHES + 1); i++)
		{
			detalhes += "a";
		}
		atendimento.setDetalhes(detalhes);
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		
		mainController.apagaAtendimentos();
		mainController.apagaEstados();
		mainController.closeHibernateSession();
		fail("Esperava exceção InvalidStateException para ser lançada. Não ocorreu.");
	}
	
	/**
	 * testa se o sistema está ordenando perfeitamente a listagem de atendimentos a ser trazida
	 * 
	 */
	@Test
	public void testaOrdemListagemAtendimentos() throws ParseException, SQLException
	{
		Estado estado = new Estado("RJ", "Rio de Janeiro");
		
		mainController.openHibernateSession();
		mainController.getSessionBD().save(estado);
		mainController.setEstado(estado);
		
		Atendimento atendimento = new Atendimento();
		atendimento.setTipo('C');
		atendimento.setEstado(estado);
		atendimento.setMotivo('E');
		atendimento.setDetalhes("N/D");
		Date data = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		data = df.parse("01-07-2016");
		atendimento.setData(data);
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		
		atendimento = new Atendimento();
		atendimento.setTipo('T');
		atendimento.setEstado(estado);
		atendimento.setMotivo('S');
		atendimento.setDetalhes("N/D");
		data = new Date();
		df = new SimpleDateFormat("dd-MM-yyyy");
		data = df.parse("30-07-2016");
		atendimento.setData(data);
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		
		estado = new Estado("MG", "Minas Gerais");
		mainController.getSessionBD().save(estado);
		mainController.setEstado(estado);
		
		atendimento = new Atendimento();
		atendimento.setTipo('E');
		atendimento.setEstado(estado);
		atendimento.setMotivo('D');
		atendimento.setDetalhes("N/D");
		data = new Date();
		df = new SimpleDateFormat("dd-MM-yyyy");
		data = df.parse("01-07-2016");
		atendimento.setData(data);
		
		mainController.setAtendimento(atendimento);
		mainController.persistAtendimento();
		mainController.closeHibernateSession();
		
		Map<Date, Map<Estado, List<Atendimento>>> mapAtendimentos;
		mapAtendimentos = mainController.listaAtendimentosPorEstadoPorData();
		
		data = new Date();
		df = new SimpleDateFormat("dd-MM-yyyy");
		data = df.parse("30-07-2016");
		assertEquals("Primeira data a ser trazida não é 30-07-2016", 
				(Date)mapAtendimentos.keySet().toArray()[0], data);
		
		data = new Date();
		df = new SimpleDateFormat("dd-MM-yyyy");
		data = df.parse("01-07-2016");
		estado = new Estado("MG", "Minas Gerais");
		
		assertEquals("Primeiro estado listado em 01-07-2016 não é Minas Gerais", 
				(Estado)mapAtendimentos.get(data).keySet().toArray()[0], estado);
		
		mainController.openHibernateSession();
		mainController.apagaAtendimentos();
		mainController.apagaEstados();
		mainController.closeHibernateSession();
	}

}
