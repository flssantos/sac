package br.com.controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "mainController")
@RequestScoped
public class MainController implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensagem;
	public MainController()
	{
		mensagem = "Bem vindo!";
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
