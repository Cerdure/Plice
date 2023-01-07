package com.project.team.plice.repository;

import com.project.team.plice.domain.data.TradeData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeDataRepository extends JpaRepository<TradeData, Long> {
    public List<TradeData> findByLngBetweenAndLatBetween(Double startLng, Double endLng, Double startLat, Double endLat);
}
