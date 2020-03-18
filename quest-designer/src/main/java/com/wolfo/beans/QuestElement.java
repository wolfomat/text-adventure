package com.wolfo.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuestElement implements Serializable {

	public QuestElement(String questName, Quest quest) {
		this.questName = questName;
		this.quest = quest;
	}

	public String getElementId() {
		return questName;
	}

	@Getter
	@Setter
	private String questName;

	@Getter
	@Setter
	private String styleForPosition;

	@Getter
	@Setter
	private Quest quest;

	@Override
	public String toString() {
		return questName.replace("uest", "")
				.replace("lement", "");
	}

}
