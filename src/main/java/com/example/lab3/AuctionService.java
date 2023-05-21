package com.example.lab3;

import java.util.List;
import java.time.LocalTime;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    public List<AuctionLot> getAllLots() {
        // Implementation to retrieve all lots from the database
        // You can use a repository or a database access layer to fetch the data
        // For simplicity, let's assume a static list of lots
        return AuctionLotData.getAllLots();
    }

    public List<AuctionLot> getEndedLots() {
        LocalDateTime now = LocalDateTime.now();
        // Implementation to retrieve lots that have ended before the current time from the database
        // You can use a repository or a database access layer to fetch the data
        // For simplicity, let's assume a static list of lots and filter based on end date
        return AuctionLotData.getAllLots().stream()
                .filter(lot -> lot.getEndDate().isBefore(now))
                .collect(Collectors.toList());
    }

    public List<AuctionLot> getTodayLots() {
        LocalDateTime start = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
        // Implementation to retrieve lots that have an end date between today's start and end time from the database
        // You can use a repository or a database access layer to fetch the data
        // For simplicity, let's assume a static list of lots and filter based on end date
        return AuctionLotData.getAllLots().stream()
                .filter(lot -> lot.getEndDate().isAfter(start) && lot.getEndDate().isBefore(end))
                .collect(Collectors.toList());
    }
}
