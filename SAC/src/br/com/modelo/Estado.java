package br.com.modelo;

import javax.faces.bean.ManagedBean;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;
import org.hibernate.validator.Length;

/**
 * Estado da federação
 */
@Entity
@ManagedBean(name = "estado")
public class Estado 
{
	private String sigla;
	private String nome;
	
	/**
	 * Retorna a sigla do estado da federação
	 *@return a sigla do estado da federação.
	 */
	@Id
	public String getSigla() 
	{
		return sigla;
	}
	
	/**
	 * Configura a sigla do estado da federação
	 *@param a sigla do estado da federação.
	 */
	public void setSigla(String sigla) 
	{
		this.sigla = sigla;
	}
	
	/**
	 * Retorna o nome do estado da federação
	 *@return o nome do estado da federação.
	 */
	@Length(max = 50, message = "Campo suporta no máximo 50 caracteres")
	public String getNome() 
	{
		return nome;
	}
	
	/**
	 * Configura o nome do estado da federação
	 *@param o nome do estado da federação.
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
