package com.example.lab3;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionLotRepository extends JpaRepository<AuctionLot, Long> {

    List<AuctionLot> findByEndDateBefore(LocalDateTime date);

    List<AuctionLot> findByEndDateBetween(LocalDateTime start, LocalDateTime end);
}
