package br.com.bemobi.marcioalex.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;







@Entity
@Table(name="url")
public class Url implements Serializable{

	private static final long serialVersionUID = 5608579500584941660L;
	private Integer id;
	private String urlOriginal;
	private String urlEncurtada;
	private String alias;
	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	public Url() {}
	

	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrlOriginal() {
		return urlOriginal;
	}
	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}
	public String getUrlEncurtada() {
		return urlEncurtada;
	}
	public void setUrlEncurtada(String urlEncurtada) {
		this.urlEncurtada = urlEncurtada;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((urlEncurtada == null) ? 0 : urlEncurtada.hashCode());
		result = prime * result + ((urlOriginal == null) ? 0 : urlOriginal.hashCode());
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
		Url other = (Url) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (urlEncurtada == null) {
			if (other.urlEncurtada != null)
				return false;
		} else if (!urlEncurtada.equals(other.urlEncurtada))
			return false;
		if (urlOriginal == null) {
			if (other.urlOriginal != null)
				return false;
		} else if (!urlOriginal.equals(other.urlOriginal))
			return false;
		return true;
	}
	
	
}
