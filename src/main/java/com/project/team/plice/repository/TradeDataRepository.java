package com.project.team.plice.repository;

import com.project.team.plice.domain.Apart;
import com.project.team.plice.domain.TradeData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeDataRepository extends JpaRepository<TradeData, Long> {
    public List<TradeData> findByLngBetweenAndLatBetween(String startLng, String endLng, String startLat, String endLat);
}
