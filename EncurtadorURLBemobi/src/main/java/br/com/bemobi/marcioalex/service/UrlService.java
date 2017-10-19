package br.com.bemobi.marcioalex.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.bemobi.marcioalex.dto.UrlDTO;
import br.com.bemobi.marcioalex.modelo.Url;
import br.com.bemobi.marcioalex.repository.UrlRepository;
import br.com.bemobi.marcioalex.util.UrlUtils;

@Service
public class UrlService {

	@Autowired
	private UrlRepository urlRepository;
	
	@Value("${urlencurtada.base}")
	private String baseUrl;
	
	@Value("${alias.tamanho}")
	private int tamanhoAlias;
	
	public void metodoTeste() {
		System.out.println("#####  Metodo de Teste  #####");
	}
	/**
	 * busca na base de dados (retrieve url)
	 * @param nome
	 * @return
	 */
	public Url retornaUrlPorAlias (String alias) {
		Url e = urlRepository.findByAlias(alias);
		return e;
	}
	
	/**
	 * Cadastrar url e retornar DTO para o Json/Rest
	 * @param url
	 * @return
	 */
	public Url cadastrarUrl(UrlDTO urlDTO) {
		//url a ser cadastrada na base
		Url url = new Url();
		
		//gerando alias com base na urlOriginal e substituindo-o no dto
		if (urlDTO.getAlias()==null) {
			//verifica se o alias gerado automaticamete já existe na base de dados e gera outro, caso exista
			boolean aliasJaExiste = true;
			String novoAlias = null;
			while (aliasJaExiste) {
				novoAlias = UrlUtils.gerarAlias(urlDTO.getUrlOriginal(), tamanhoAlias);
				if (!validarAlias(novoAlias)){
					aliasJaExiste = false;
				}
			}
			urlDTO.setAlias(novoAlias);
		} 
		
		//configurando URL Encurtada com base no alias
		url.setUrlEncurtada(UrlUtils.retornaUrlEncurtada(baseUrl, urlDTO.getAlias()));
		
		//preenchendo objeto com dados do parametros e dados gerados
		url.setAlias(urlDTO.getAlias());
		url.setUrlOriginal(urlDTO.getUrlOriginal());
		
		//retorno do cadastro
		Url urlCadastrada = urlRepository.save(url);
			
		return urlCadastrada;
		
	}
	/**
	 * Verifica se um alias já existe na base de dados
	 * @param alias
	 * @return
	 */
	public boolean validarAlias(String alias) {
		boolean existe = false;
		Url urlCadastrada = urlRepository.findByAlias(alias);
		if (urlCadastrada!=null) {
			existe = true;
		}
		return existe;
	}
	public Url consultarUrlPorAlias(String alias) {
		return urlRepository.findByAlias(alias);
	}
	
}
