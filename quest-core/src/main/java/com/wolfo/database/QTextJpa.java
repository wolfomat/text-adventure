package com.wolfo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wolfo.beans.QText;

public interface QTextJpa extends JpaRepository<QText, Long> {
}
