package com.wolfo.database;

import org.springframework.data.repository.CrudRepository;

import com.wolfo.beans.QAnswer;

public interface QAnswerCrud extends CrudRepository<QAnswer, Long>  {
}
