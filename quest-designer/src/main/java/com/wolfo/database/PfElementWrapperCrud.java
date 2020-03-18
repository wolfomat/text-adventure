package com.wolfo.database;

import org.springframework.data.repository.CrudRepository;

import com.wolfo.beans.PfElementWrapper;

public interface PfElementWrapperCrud extends CrudRepository<PfElementWrapper, Long> {
}
