package com.wolfo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wolfo.beans.QAnswer;

public interface QAnswerJpa extends JpaRepository<QAnswer, Long> {
}
