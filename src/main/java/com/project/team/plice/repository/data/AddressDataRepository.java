package com.project.team.plice.repository;

import com.project.team.plice.domain.data.AddressData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalCodeRepository extends JpaRepository<AddressData, Long> {
}
