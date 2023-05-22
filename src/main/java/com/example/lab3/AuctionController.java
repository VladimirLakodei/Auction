package com.example.lab3;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auction")
@Validated
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/lots")
    public List<AuctionLot> getAllLots(@RequestParam(required = false) String filter) {
        List<AuctionLot> allLots = AuctionLotData.getAllLots();

        if (filter != null) {
            LocalDateTime now = LocalDateTime.now();

            if (filter.equalsIgnoreCase("ended")) {
                // Filter lots that have already ended
                allLots = allLots.stream()
                        .filter(lot -> lot.getEndDate().isBefore(now))
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase("today")) {
                // Filter lots that are listed today
                allLots = allLots.stream()
                        .filter(lot -> lot.getEndDate().toLocalDate().equals(now.toLocalDate()) && !lot.getEndDate().isBefore(now))
                        .collect(Collectors.toList());
            }
        }

        return allLots;
    }

    @Autowired
    AuctionLotRepository auctionLotRepository;

    @PostMapping("/lots")
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionLot createLot(@Valid @RequestBody AuctionLot lot) {
        return auctionLotRepository.save(lot);
    }

/*
    @PostMapping("/lots")
    public AuctionLot createLot(@Valid @RequestBody AuctionLot lot) {
        // Implementation to create a new lot and save it to the database
        // You can use a repository or a database access layer to save the lot
        // For simplicity, let's assume a static list of lots and add the new lot to it
        AuctionLotData.addLot(lot);
        return lot;
    }
*/


    @PostMapping("/lots/{lotId}/bids")
    public Bid placeBid(@PathVariable Long lotId, @Valid @RequestBody Bid bid) {
        AuctionLot lot = AuctionLotData.getAllLots().stream()
                .filter(l -> l.getId().equals(lotId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found with id " + lotId));

        if (bid.getAmount().doubleValue() < lot.getStartingPrice().doubleValue()) {
            throw new InvalidBidException("Bid amount must be greater than or equal to the starting price.");
        }

        if (lot.getBuyNowPrice() != null) {
            bid.setAmount(lot.getBuyNowPrice());
        }

        Bid highestBid = lot.getBids().stream().max(Comparator.comparing(Bid::getAmount)).orElse(null);

        if (highestBid != null && bid.getAmount().compareTo(highestBid.getAmount()) <= 0) {
            throw new InvalidBidException("Bid amount must be greater than the current highest bid.");
        }

        // Retrieve user information from the request body
        String bidderName = bid.getBidderName();

        // TODO: Save user information to the database or perform any other necessary actions

        // Set user information in the bid object
        User user = new User(); // Create a new User object
        user.setName(bidderName); // Set the bidder name

        bid.setUser(user);

        bid.setLot(lot);
        lot.addBid(bid); // Add the bid to the lot's bid list

        return bid;
    }
}
