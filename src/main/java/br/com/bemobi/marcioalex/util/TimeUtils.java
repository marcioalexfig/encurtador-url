package br.com.bemobi.marcioalex.util;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class TimeUtils {

	public static String tempoTotal (Instant tempoInicial, Instant tempoFinal) {
		Duration duracao = Duration.between(tempoInicial, tempoFinal);
		String duracaoFormatada = DurationFormatUtils.formatDuration(duracao.toMillis(), "s.SSS"); 
		return duracaoFormatada + " s";
	}
	
}
