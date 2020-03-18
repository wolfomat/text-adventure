package com.wolfo.database;

import org.springframework.data.repository.CrudRepository;

import com.wolfo.beans.QText;

public interface QTextCrud extends CrudRepository<QText, Long>  {
}
