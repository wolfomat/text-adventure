package com.wolfo.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolfo.beans.Quest;

@Service
public class DatabaseReaderService {

	private QuestReposity questReposity;

	@Autowired
	public void setQuestReposity(final QuestReposity questReposity) {
		this.questReposity = questReposity;
	}

	public Quest loadInitialQuest(final String questName) {
		final Quest introQuest = questReposity.findByQuestSeriesAndIsStartQuest(questName, true);
		return introQuest;
	}

	public List<Quest> loadAllAvailableQuests() {
		final List<Quest> allQuests = questReposity.findAllByIsStartQuest(true);
		return allQuests;
	}

}
