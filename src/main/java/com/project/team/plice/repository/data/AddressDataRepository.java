package com.project.team.plice.repository.data;

import com.project.team.plice.domain.data.AddressData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressDataRepository extends JpaRepository<AddressData, Long> {
    public List<AddressData> findByAddressContainingIgnoreCase(String val);
}
