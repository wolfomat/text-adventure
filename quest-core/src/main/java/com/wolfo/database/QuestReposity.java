package com.wolfo.database;

import com.wolfo.beans.Quest;

public interface QuestReposity extends QuestCrud, QuestJpa {

	Quest findByQuestSeries(String questName);

}
