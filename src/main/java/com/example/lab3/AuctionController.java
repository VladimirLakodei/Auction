package com.example.lab3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Comparator;

@RestController
@RequestMapping("/api/auction")
@Validated
public class AuctionController {

    @Autowired
    private AuctionLotRepository auctionLotRepository;

    @PostMapping("/lots")
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

        return bid;
    }
}

/*package com.example.lab3;

import java.util.Comparator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionLotRepository lotRepository;

    @PostMapping("/lots")
    public AuctionLot createLot(@RequestBody AuctionLot lot) {
        return lotRepository.save(lot);
    }

    @PostMapping("/lots/{lotId}/bids")
    public Bid placeBid(@PathVariable Long lotId, @RequestBody Bid bid) {
        AuctionLot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("Lot not found with id " + lotId));

        if (bid.getAmount().compareTo(lot.getStartingPrice()) < 0) {
            throw new InvalidBidException("Bid amount must be greater than or equal to the starting price.");
        }

        if (lot.getBuyNowPrice() != null && bid.getAmount().compareTo(lot.getBuyNowPrice()) >= 0) {
            throw new InvalidBidException("Bid amount exceeds buy now price.");
        }

        Bid highestBid = lot.getBids().stream().max(Comparator.comparing(Bid::getAmount)).orElse(null);

        if (highestBid != null && bid.getAmount().compareTo(highestBid.getAmount()) <= 0) {
            throw new InvalidBidException("Bid amount must be greater than the current highest bid.");
        }

        bid.setLot(lot);
        lot.getBids().add(bid);
        lotRepository.save(lot);

        return bid;
    }
}*/
