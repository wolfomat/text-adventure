package com.wolfo.database;

import java.util.List;

import com.wolfo.beans.Quest;

public interface QuestReposity extends QuestCrud, QuestJpa {

	Quest findByQuestSeriesAndIsStartQuest(String questSeries, boolean isStartQuest);

	List<Quest> findAllByIsStartQuest(boolean isStartQuest);
}
