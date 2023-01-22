package com.project.team.plice.repository.data;

import com.project.team.plice.domain.data.ApartData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApartDataRepository extends JpaRepository<ApartData, Long> {
    public List<ApartData> findByNameContainingIgnoreCase(String val);
    public List<ApartData> findByAddressContainingIgnoreCaseAndNameContainingIgnoreCase(String address, String name);
    public Optional<ApartData> findById(Long id);
    public ApartData findByNameContains(String apartName);
}
