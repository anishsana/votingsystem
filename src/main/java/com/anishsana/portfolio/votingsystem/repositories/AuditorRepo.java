package com.anishsana.portfolio.votingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anishsana.portfolio.votingsystem.entity.Auditor;

@Repository
public interface AuditorRepo extends JpaRepository<Auditor, Integer> {
	public Auditor findByName(String name);
}
