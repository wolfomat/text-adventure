package com.wolfo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wolfo.beans.PfElementWrapper;

public interface PfElementWrapperJpa extends JpaRepository<PfElementWrapper, Long> {
}
