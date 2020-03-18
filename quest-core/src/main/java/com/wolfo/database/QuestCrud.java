package com.wolfo.database;

import org.springframework.data.repository.CrudRepository;

import com.wolfo.beans.Quest;

public interface QuestCrud extends CrudRepository<Quest, Long>  {
}
