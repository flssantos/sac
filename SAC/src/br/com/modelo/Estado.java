package br.com.modelo;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;

/**
 * Estado da federa��o
 */
@Entity
@ManagedBean(name = "estado")
@Table(name = "\"ESTADO\"")
public class Estado implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;
	private String sigla;
	private String nome;
	
	/**
	 * Construtor padr�o
	 */
	public Estado()
	{
		
	}
	
	/**
	 * Construtor com propriedades parametrizadas na inicializa��o
	 */
	public Estado(String sigla, String nome)
	{
		this.sigla = sigla;
		this.nome = nome;
	}
	/**
	 * Retorna a sigla do estado da federa��o
	 *@return a sigla do estado da federa��o.
	 */
	@Id
	public String getSigla() 
	{
		return sigla;
	}
	
	/**
	 * Configura a sigla do estado da federa��o
	 *@param a sigla do estado da federa��o.
	 */
	public void setSigla(String sigla) 
	{
		this.sigla = sigla;
	}
	
	/**
	 * Retorna o nome do estado da federa��o
	 *@return o nome do estado da federa��o.
	 */
	@Length(max = 50, message = "Campo suporta no m�ximo 50 caracteres")
	@Column(name="nome")
	public String getNome() 
	{
		return nome;
	}
	
	/**
	 * Configura o nome do estado da federa��o
	 *@param o nome do estado da federa��o.
	 */
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Estado other = (Estado) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
	
}
