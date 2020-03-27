package com.wolfo.params;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
public class ProgramParameters {

	@Getter
	@Setter
	@Value("${gameName}")
	private String gameName;

	@Getter
	@Setter
	@Value("${listAllGames}")
	private boolean isListAllGames;

}
