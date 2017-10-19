package br.com.bemobi.marcioalex.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

public class UrlUtils {
	
	/**
	 * Gerar conteudo da URL encriptada para
	 * @param url
	 * @return
	 */
	public static String gerarAlias(String url, int tamanho) {
		String alias;
		if (url==null) {
			return url;
		}
		
		BCryptPasswordEncoder bcript = new BCryptPasswordEncoder();
		alias = formatarAlias(bcript.encode(url), tamanho);
		
		
		return alias;
	}
	
	/**
	 * aproveita apenas os ultimos caracteres da string gerada pelo bcript de acordo com o tamaho definido no arquivo de configuração
	 * 
	 * @param aliasBruto
	 * @param tamanho
	 * @return
	 */
	private static String formatarAlias(String aliasBruto, int tamanho) {
		String aliasFormatado = retirarCaracteresEsp(aliasBruto); 
		aliasFormatado = aliasFormatado.substring((aliasFormatado.length() - (tamanho+1)), aliasFormatado.length()-1);
		return aliasFormatado;
	}

	/**
	 * retira caracteres especiais, pontos e espaços em branco antes, depois e no meio da string
	 * @param aliasBruto
	 * @return
	 */
	private static String retirarCaracteresEsp(String aliasBruto) {
		//TODO - Otimizar expressão regular
		String aliasLapidado = aliasBruto
				.replaceAll("/(?!\\w|\\s)./g","") //remove o que não e palavra ou espaco em branco
				.replaceAll("/\\s+/g", " ") // acha um ou mais espaços e transforma em um só espaço
				.replaceAll("/^(\\s*)([\\W\\w]*)(\\b\\s*$)/g", "") //remove espaços antes e depois tambem
				.replaceAll("[/^$.|?*+()]", ""); //remove os caracteres especiais restantes que podem ser gerados pelo bcript no momento do encode
		return aliasLapidado;
	}

	/**
	 * concatena a base da url ("radical" da url) com o alias gerado ou enviado pelo usuário como custom alias.
	 * @param urlBase
	 * @param alias
	 * @return
	 */
	public static String retornaUrlEncurtada(String urlBase, String alias) {
		return urlBase + alias;
	}
	

	
}

