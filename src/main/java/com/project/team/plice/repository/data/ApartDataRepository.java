package com.project.team.plice.repository;

import com.project.team.plice.domain.data.ApartData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartRepository extends JpaRepository<ApartData, Long> {
}
