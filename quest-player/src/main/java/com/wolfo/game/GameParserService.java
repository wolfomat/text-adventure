package com.wolfo.game;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.wolfo.AnsiColor;
import com.wolfo.beans.QAnswer;
import com.wolfo.beans.Quest;
import com.wolfo.params.ProgramParameters;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GameParserService {

	public void parseGame(final ProgramParameters params) {
		// todo read params and react.
	}

	private void printQuest(final Quest quest) {

		if (quest == null) {
			log.error("No quest given! Exiting.");
			return;
		}

		printText(quest.getQuestText().getText());
		log.info("");

		if (quest.getQQuestion() != null) {
			printText(StringUtils.defaultString(quest.getQQuestion().getText()));
			log.info("");
		}

		for (final QAnswer qAnswer : quest.getQAnswers()) {
			printText(StringUtils.defaultString(qAnswer.getAnswer()));
			log.info("");
		}

		if (!quest.getQAnswers().isEmpty()) {
			int answerSize = quest.getQAnswers().size();
			boolean isLooping = true;
			while(isLooping) {
				Integer questInput = readQuestInput();
				if (numberInRange(questInput, answerSize)) {
					log.info("FALSCHE ANTWORT ! - NOCHMAL !");
				} else {
					printQuest(quest.getQAnswers().get(questInput - 1).getNextQuest());
					isLooping = false;
				}
			}
		} else {
			log.info("----- ENDE ERREICHT ----");
		}
	}

	private boolean numberInRange(Integer num, int listSize) {
		return num <= 0 || num > listSize;
	}

	private Integer readQuestInput() {

		printEnterText();
		while(true) {
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			log.info("DU HAST GEWÄHLT: {" + input + "}");

			Integer result = inputResult(input);
			return result == null ? -1 : result;
		}
	}

	private void printEnterText() {
		printText(AnsiColor.PURPLE_BOLD.getValue() + ">>>  ", false);
	}

	private Integer inputResult(String input) {
		try {
			if (input != null) {
				return Integer.valueOf(input);
			}
			return null;
		} catch (NumberFormatException e) {
			return null;

		}
	}

	private void printText(String text) {
		printText(text, false);
	}

	private void printText(String text, boolean withNewLine) {
		log.info(text + AnsiColor.RESET.getValue());
		if (withNewLine) {
			log.info("");
		}
	}


}
