package com.example.lab3;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionLotData {

    private static List<AuctionLot> lots = new ArrayList<>();

    static {
        // Create some example lots
        AuctionLot lot1 = new AuctionLot(1L, "Lot 1", "Description for Lot 1", BigDecimal.valueOf(100), null, LocalDateTime.now().plusDays(7));
        AuctionLot lot2 = new AuctionLot(2L, "Lot 2", "Description for Lot 2", BigDecimal.valueOf(200), BigDecimal.valueOf(500), LocalDateTime.now().plusDays(14));

        // Add the lots to the list
        lots.add(lot1);
        lots.add(lot2);
    }

    public static List<AuctionLot> getAllLots() {
        return lots;
    }

    public static void addLot(AuctionLot lot) {
        lots.add(lot);
    }

    // Other utility methods for managing lots
    // ...
}
