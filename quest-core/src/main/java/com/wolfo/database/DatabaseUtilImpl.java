package com.wolfo.database;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wolfo.beans.QAnswer;
import com.wolfo.beans.QQuestion;
import com.wolfo.beans.QText;
import com.wolfo.beans.Quest;

@Service
public class DatabaseUtilImpl implements DatabaseUtil {

	private QAnswerReposity qAnswerReposity;
	private QQuestionReposity qQuestionReposity;
	private QTextRepository qTextRepository;
	private QuestReposity questReposity;

	@Autowired
	public void setqAnswerReposity(final QAnswerReposity qAnswerReposity) {
		this.qAnswerReposity = qAnswerReposity;
	}

	@Autowired
	public void setqQuestionReposity(final QQuestionReposity qQuestionReposity) {
		this.qQuestionReposity = qQuestionReposity;
	}

	@Autowired
	public void setqTextRepository(final QTextRepository qTextRepository) {
		this.qTextRepository = qTextRepository;
	}

	@Autowired
	public void setQuestReposity(final QuestReposity questReposity) {
		this.questReposity = questReposity;
	}


	@Override
	@Transactional
	public void saveQuest(final Quest q, final boolean isSafeAll) {
		if (q != null) {
			final Quest quest = q.getId() != null ? questReposity.findById(q.getId()).orElse(q) : q;
			if (isSafeAll) {
				questReposity.saveAndFlush(quest);
				saveQQuestion(quest.getQQuestion());
				saveQText(quest.getQuestText());
				saveQAnswers(quest.getQAnswers());
			} else {
				saveAllQuests(quest.getQAnswers());
				questReposity.saveAndFlush(quest);
			}
		}
	}

	private void saveAllQuests(final List<QAnswer> qList) {
		if (qList != null) {
			for (final QAnswer qAnswer : qList) {
				saveQuest(qAnswer.getNextQuest(), false);
			}
		}
	}

	@Override
	@Transactional
	public void saveQAnswers(final List<QAnswer> q) {
		if (q != null) {
			q.forEach(qa -> qAnswerReposity.saveAndFlush(qa));
		}
	}

	@Override
	@Transactional
	public void saveQQuestion(final QQuestion q) {
		if (q != null) {
			qQuestionReposity.saveAndFlush(q);
		}
	}

	@Override
	@Transactional
	public void saveQText(final QText q) {
		if (q != null) {
			qTextRepository.saveAndFlush(q);
		}
	}

	@Override
	public List<Quest> loadAllInitQuests() {
		final List<Quest> allInitQuest = questReposity.findAllByIsStartQuest(true);
		return allInitQuest;
	}

	@Override
	public Quest loadQuestByName(final String questName) {
		final Quest quest = questReposity.findByQuestSeriesAndIsStartQuest(questName, true);
		return quest;
	}

}
