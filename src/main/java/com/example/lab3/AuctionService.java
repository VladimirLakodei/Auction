package com.example.lab3;

import java.util.List;
import java.time.LocalTime;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuctionService {

    @Autowired
    private AuctionLotRepository lotRepository;

    public List<AuctionLot> getAllLots() {
        return lotRepository.findAll();
    }

    public List<AuctionLot> getEndedLots() {
        return lotRepository.findByEndDateBefore(LocalDateTime.now());
    }

    public List<AuctionLot> getTodayLots() {
        LocalDateTime start = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
        return lotRepository.findByEndDateBetween(start, end);
    }
}

