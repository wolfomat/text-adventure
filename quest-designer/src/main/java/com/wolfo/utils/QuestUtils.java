package com.wolfo.utils;

import java.util.ArrayList;

import com.wolfo.beans.QAnswer;
import com.wolfo.beans.QQuestion;
import com.wolfo.beans.QText;
import com.wolfo.beans.Quest;

public class QuestUtils {

	private QuestUtils() { }

	public static Quest createEmptyQuest() {
		Quest quest = new Quest();
		quest.setQuestText(new QText());
		quest.setQQuestion(new QQuestion());
		quest.setQAnswers(new ArrayList<>());
		return quest;
	}

	public static QAnswer createEmptyAnswer(Quest quest) {
		QAnswer qAnswer = new QAnswer();
		qAnswer.setQuest(quest);
		return qAnswer;
	}

}
