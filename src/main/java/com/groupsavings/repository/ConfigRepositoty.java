package com.groupsavings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.groupsavings.model.entity.Config;

@Repository
public interface ConfigRepositoty extends JpaRepository<Config, Long> {

	@Query("SELECT c FROM Config c WHERE c.id = 1L")
	Config findConfigByIdOne();
}
