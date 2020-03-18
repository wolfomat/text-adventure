package com.wolfo.database;

import org.springframework.data.repository.CrudRepository;

import com.wolfo.beans.PfElementWrapper;
import com.wolfo.beans.PfEndPointWrapper;

public interface PfEndPointWrapperCrud extends CrudRepository<PfEndPointWrapper, Long> {
}
