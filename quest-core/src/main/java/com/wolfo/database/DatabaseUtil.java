package com.wolfo.database;

import java.util.List;

import com.wolfo.beans.QAnswer;
import com.wolfo.beans.QQuestion;
import com.wolfo.beans.QText;
import com.wolfo.beans.Quest;

public interface DatabaseUtil {

	void saveQuest(Quest q, boolean isSaveAll);
	void saveQAnswers(List<QAnswer> q);
	void saveQQuestion(QQuestion q);
	void saveQText(QText q);

	List<Quest> loadAllInitQuests();
	Quest loadQuestByName(String questName);

}
