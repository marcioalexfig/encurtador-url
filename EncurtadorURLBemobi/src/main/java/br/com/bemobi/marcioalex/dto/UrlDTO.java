package br.com.bemobi.marcioalex.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class UrlDTO {
	private String urlOriginal;
	private String urlEncurtada;
	private Long tempo;
	private String alias;
	
	public UrlDTO() {}
	
	@NotEmpty (message = "URL Original necessita ser fornecida!")
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
	public Long getTempo() {
		return tempo;
	}
	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public UrlDTO(String urlOriginal, String urlEncurtada, Long tempo, String alias) {
		super();
		this.urlOriginal = urlOriginal;
		this.urlEncurtada = urlEncurtada;
		this.tempo = tempo;
		this.alias = alias;
	}
	
	
}
