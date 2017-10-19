package br.com.bemobi.marcioalex.controller;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bemobi.marcioalex.dto.UrlDTO;
import br.com.bemobi.marcioalex.modelo.Url;
import br.com.bemobi.marcioalex.response.Response;
import br.com.bemobi.marcioalex.service.UrlService;
import br.com.bemobi.marcioalex.util.TimeUtils;


@RestController
@RequestMapping("/api/encurtarurl")
public class EncurtadorURLController {
	
	@Autowired
	private UrlService urlService;
	
	
	/**
	 * Shorten URL
	 * @Valid habilita as validações declaradas no DTO
	 * @param empresaDto
	 * @param resultadoValidacao  é o retorno com as mensagens de erro das validação de dentro do DTO
	 * @return
	 */
	@PutMapping	
	public ResponseEntity<Response<UrlDTO>> cadastrarUrl(@Valid @RequestBody UrlDTO urlDto, BindingResult resultadoValidacao)	{
		
		Instant horaChamada = Instant.now(); 
		Response<UrlDTO> resposta = new Response<UrlDTO>();
		List<String> erros = new ArrayList<String>();
		
		try {
			//verifica erros de validação (bean validation) e adiciona o que estiver no response
			if (!resultadoValidacao.getAllErrors().isEmpty()) {
				
				resultadoValidacao.getAllErrors().forEach(erro -> resposta.getErros().add(erro.getDefaultMessage()));
				
				//para o processamento e retorna os erros
				return ResponseEntity.badRequest().body(resposta);
			} 
			
			//verifica se o custon alias enviado já existe na base de dados
			if (urlDto.getAlias()!=null) {
				if (urlService.validarAlias(urlDto.getAlias())) {
					resposta.setErr_code("001");
					resposta.setErr_description("CUSTOM ALIAS ALREADY EXISTS");
					return ResponseEntity.badRequest().body(resposta);
				}
			}
			//se não tem erro, chama o serviço e grava na base de dados
			Url urlCadastrada = urlService.cadastrarUrl(urlDto);
			urlDto.setAlias(urlCadastrada.getAlias());
				Instant horaRetorno = Instant.now(); 
			urlDto.setTempo(TimeUtils.tempoTotal(horaChamada, horaRetorno)); 
			urlDto.setUrlOriginal(urlCadastrada.getUrlOriginal());
			urlDto.setUrlEncurtada(urlCadastrada.getUrlEncurtada());
			resposta.setDados(urlDto);
			
		} catch (Exception e) {
			erros.add(e.getLocalizedMessage());
			erros.forEach(erro -> resposta.getErros().add(erro));
			//para tudo e retorna os erros
			return ResponseEntity.badRequest().body(resposta);
		}

		return ResponseEntity.ok(resposta);
		
	}
	
	/**
	 * Retrieve URL
	 * @param alias
	 * @return
	 */
	@GetMapping (value = "/{alias}")
	public ResponseEntity retornarUrl( @RequestBody @PathVariable("alias") String alias)	{
		
		Response<String> resposta = new Response<String>();
		List<String> erros = new ArrayList<String>();
		Url urlCadastrada = null;
		
		try {
			
			//verifica se o custon alias enviado existe na base de dados
			if (alias!=null) {
				urlCadastrada = urlService.consultarUrlPorAlias(alias);
				if (urlCadastrada==null) {
					resposta.setErr_code("002");
					resposta.setErr_description("SHORTENED URL NOT FOUND");
					return ResponseEntity.badRequest().body(resposta);
				}
			}

			//caso o alias exista, redirecionar para a url original cadastrada
			resposta.setDados(urlCadastrada.getUrlOriginal());
			URI redirecionar = new URI("http://" + urlCadastrada.getUrlOriginal());
		    HttpHeaders httpHeaders = new HttpHeaders();
		    httpHeaders.setLocation(redirecionar);
		    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
			
		} catch (Exception e) {
			erros.add(e.getLocalizedMessage());
			erros.forEach(erro -> resposta.getErros().add(erro));
			//para tudo e retorna os erros
			return ResponseEntity.badRequest().body(resposta);
		}
		
		//return ResponseEntity.ok().body(resposta);

	}
	
}
