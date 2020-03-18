package com.wolfo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wolfo.beans.QQuestion;

public interface QQuestionJpa extends JpaRepository<QQuestion, Long> {
}
