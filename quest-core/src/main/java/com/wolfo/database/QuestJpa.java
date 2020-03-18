package com.wolfo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wolfo.beans.Quest;

public interface QuestJpa extends JpaRepository<Quest, Long> {
}
