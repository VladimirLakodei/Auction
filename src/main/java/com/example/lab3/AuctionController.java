package com.example.lab3;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auction")
@Validated
public class AuctionController {

    private final AuctionLotRepository auctionLotRepository;

    @Autowired
    public AuctionController(AuctionLotRepository auctionLotRepository) {
        this.auctionLotRepository = auctionLotRepository;
    }

    @GetMapping("/lots")
    public List<AuctionLot> getAllLots(@RequestParam(required = false) String filter) {
        List<AuctionLot> allLots;

        if (filter != null) {
            LocalDateTime now = LocalDateTime.now();

            if (filter.equalsIgnoreCase("ended")) {
                allLots = auctionLotRepository.findAll()
                        .stream()
                        .filter(lot -> lot.getEndDate().isBefore(now))
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase("today")) {
                allLots = auctionLotRepository.findAll()
                        .stream()
                        .filter(lot -> lot.getEndDate().toLocalDate().equals(now.toLocalDate()) && !lot.getEndDate().isBefore(now))
                        .collect(Collectors.toList());
            } else {
                allLots = auctionLotRepository.findAll();
            }
        } else {
            allLots = auctionLotRepository.findAll();
        }

        return allLots;
    }

    @PostMapping("/lots")
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionLot createLot(@Valid @RequestBody AuctionLot lot) {
        return auctionLotRepository.save(lot);
    }

    @PostMapping("/lots/{lotId}/bids")
    public Bid placeBid(@PathVariable Long lotId, @Valid @RequestBody Bid bid) {
        AuctionLot lot = auctionLotRepository.findById(lotId)
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

        bid.setLot(lot);
        lot.addBid(bid);

        auctionLotRepository.save(lot);

        return bid;
    }
}
