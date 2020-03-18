package com.wolfo.database;

import org.springframework.data.repository.CrudRepository;

import com.wolfo.beans.QQuestion;

public interface QQuestionCrud extends CrudRepository<QQuestion, Long>  {
}
