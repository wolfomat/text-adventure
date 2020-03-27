package com.wolfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

import com.wolfo.game.GameParserService;
import com.wolfo.params.ProgramParameters;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EntityScan(basePackages = {"com.wolfo"})
@Slf4j
public class PlayerStart {


	public static void main(String[] args) {
		final ApplicationContext context = SpringApplication.run(PlayerStart.class, args);
		final ProgramParameters programParameters = context.getBean(ProgramParameters.class);
		final GameParserService parserService = context.getBean(GameParserService.class);
		parserService.parseGame(programParameters);
	}

}
