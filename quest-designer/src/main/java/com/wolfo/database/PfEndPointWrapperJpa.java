package com.wolfo.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wolfo.beans.PfEndPointWrapper;

public interface PfEndPointWrapperJpa extends JpaRepository<PfEndPointWrapper, Long> {
}
