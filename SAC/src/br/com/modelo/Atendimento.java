package br.com.modelo;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Entity;
import org.hibernate.validator.Length;

/**
 * Classe que registra um atendimento
 */
@Entity
@ManagedBean(name = "atendimento")
public class Atendimento implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private char tipo;
	private String tipoExtenso;
	private String estado;
	private char motivo;
	private String motivoExtenso;
	private String detalhes;
	private Date data;
	
	/**
	 * Retorna o id do atendimento
	 *@return id do atendimento.
	 */
	@Id
	public int getId() 
	{
		return id;
	}
	
	/**
	 * Configura o id do atendimento
	 *@param id do atendimento.
	 */
	public void setId(int id) 
	{
		this.id = id;
	}
	
	/**
	 * Retorna o tipo do atendimento no banco de dados
	 *@return tipo do atendimento no banco de dados.
	 */
	public char getTipo()
	{
		return tipo;
	}
	
	/**
	 * Configura o tipo do atendimento no banco de dados
	 *@param tipo do atendimento no banco de dados.
	 */
	public void setTipo(char tipo) 
	{
		this.tipo = tipo;
	}
	
	/**
	 * Retorna o tipo do atendimento a ser exibido na tela
	 *@return o tipo do atendimento a ser exibido na tela
	 */
	@Transient
	public String getTipoExtenso()
	{
		return tipoExtenso;
	}
	
	/**
	 * Configura o tipo do atendimento a ser exibido na tela
	 *@param tipo do atendimento a ser exibido na tela.
	 */
	public void setTipoExtenso(String tipoExtenso)
	{
		this.tipoExtenso = tipoExtenso;
	}
	
	/**
	 * Retorna o estado da federa��o onde foi aberto o atendimento
	 *@return o estado onde foi aberto o atendimento
	 */
	@ManyToOne
	public String getEstado() 
	{
		return estado;
	}
	
	/**
	 * Configura o estado da federa��o onde foi aberto o atendimento
	 *@param o estado onde foi aberto o atendimento
	 */
	public void setEstado(String estado) 
	{
		this.estado = estado;
	}
	
	/**
	 * Retorna o motivo pelo qual foi aberto o atendimento. A ser guardado no banco de dados.
	 *@retrun o motivo pelo qual foi aberto o atendimento
	 */
	public char getMotivo() 
	{
		return motivo;
	}
	
	/**
	 * Configura o motivo pelo qual foi aberto o atendimento. A ser guardado no banco de dados.
	 *@param o motivo pelo qual foi aberto o atendimento
	 */
	public void setMotivo(char motivo) 
	{
		this.motivo = motivo;
	}
	
	/**
	 * Retorna o motivo pelo qual foi aberto o atendimento. A ser exibido na tela.
	 *@retrun o motivo pelo qual foi aberto o atendimento
	 */
	@Transient
	public String getMotivoExtenso() 
	{
		return motivoExtenso;
	}
	
	/**
	 * Configura o motivo pelo qual foi aberto o atendimento. A ser exibido na tela.
	 *@param o motivo pelo qual foi aberto o atendimento
	 */
	public void setMotivoExtenso(String motivoExtenso) 
	{
		this.motivoExtenso = motivoExtenso;
	}
	
	/**
	 * Retorna os detalhes do atendimento
	 *@retrun os detalhes do atendimento
	 */
	@Length(max = 500, message = "Campo suporta no m�ximo 500 caracteres")
	public String getDetalhes() 
	{
		return detalhes;
	}
	
	/**
	 * Configura os detalhes do atendimento
	 *@param os detalhes do atendimento
	 */
	public void setDetalhes(String detalhes) 
	{
		this.detalhes = detalhes;
	}
	
	/**
	 * Retorna a data do atendimento
	 *@retrun a data do atendimento
	 */
	public Date getData() 
	{
		return data;
	}
	
	/**
	 * Configura a data do atendimento
	 *@param a data do atendimento
	 */
	public void setData(Date data)
	{
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((detalhes == null) ? 0 : detalhes.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + id;
		result = prime * result + motivo;
		result = prime * result + tipo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atendimento other = (Atendimento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (detalhes == null) {
			if (other.detalhes != null)
				return false;
		} else if (!detalhes.equals(other.detalhes))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id != other.id)
			return false;
		if (motivo != other.motivo)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
}
