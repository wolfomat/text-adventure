package com.wolfo.controller;


import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.springframework.beans.factory.annotation.Autowired;

import com.wolfo.beans.QAnswer;
import com.wolfo.beans.Quest;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.wolfo.utils.QuestUtils;

@Slf4j
@Named
public class QuestDialogEdit {

	private QuestNodeLib questNodeLib;

	@Autowired
	public void setQuestNodeLib(QuestNodeLib questNodeLib) {
		this.questNodeLib = questNodeLib;
	}

	@Setter
	private Element currentElement;

	@Getter
	@Setter
	private Quest currentQuest;

	public void onAddNewAnswer() {
		currentQuest.getQAnswers().add(QuestUtils.createEmptyAnswer(currentQuest));
		currentElement.addEndPoint(questNodeLib.createRectangleEndPoint(EndPointAnchor.CONTINUOUS_BOTTOM));

		PrimeFaces.current().ajax().update("quest-editor-form:questEditPanel");
	}

	public int obtainAnswerIndex(QAnswer qAnswer) {
		return currentQuest.getQAnswers().indexOf(qAnswer)+1;
	}

	public void deleteAnswer(QAnswer qAnswer) {
		// disconnect connection...?
		currentQuest.getQAnswers().remove(qAnswer);
	}


}
