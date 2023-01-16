package com.project.team.plice.service.classes;

import com.project.team.plice.domain.data.TradeData;
import com.project.team.plice.dto.data.AddressDataDto;
import com.project.team.plice.dto.data.ApartDataDto;
import com.project.team.plice.repository.data.AddressDataRepository;
import com.project.team.plice.repository.data.ApartDataRepository;
import com.project.team.plice.repository.data.TradeDataRepository;
import com.project.team.plice.service.interfaces.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final AddressDataRepository addressDataRepository;
    private final ApartDataRepository apartDataRepository;
    private final TradeDataRepository tradeDataRepository;

    @Override
    public List<TradeData> findTradeData() {
        return tradeDataRepository.findAll();
    }

    @Override
    public List<TradeData> findTradeData(Double startLng, Double endLng, Double startLat, Double endLat) {
        return tradeDataRepository.findByLngBetweenAndLatBetween(startLng,endLng,startLat,endLat);
    }

    @Override
    public List<TradeData> findAllTradeDataOrderByPriceDesc() {
        return tradeDataRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<TradeData> findAllTradeDataOrderByPriceAsc() {
        return tradeDataRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public TradeData findTradeDataByAddress(String address) {
        return tradeDataRepository.findByAddressContainsIgnoreCase(address);
    }

    @Override
    public List<AddressDataDto> findAddressDataByAddress(String val) {
        return addressDataRepository.findByAddressContainingIgnoreCase(val).stream().map(e -> e.toDto())
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartDataDto> findApartByAddressOrName(String address, String name) {
        return apartDataRepository.findByAddressContainingIgnoreCaseAndNameContainingIgnoreCase(address, name).stream().map(e -> e.toDto())
                .collect(Collectors.toList());
    }
}
