package com.example.lab3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BidTests {

    private Bid bid;
    private AuctionLot lot;
    private User user;

    @BeforeEach
    public void setUp() {
        lot = new AuctionLot();
        user = new User();
        bid = new Bid(1L, BigDecimal.TEN, lot, user);
    }

    @Test
    public void testBidInitialization() {
        assertNotNull(bid.getId());
        assertEquals(BigDecimal.TEN, bid.getAmount());
        assertEquals(lot, bid.getLot());
        assertEquals(user, bid.getUser());
        assertEquals(user.getName(), bid.getBidderName());
    }

    @Test
    public void testSetters() {
        BigDecimal newAmount = new BigDecimal("20.50");
        AuctionLot newLot = new AuctionLot();
        User newUser = new User();

        bid.setAmount(newAmount);
        bid.setLot(newLot);
        bid.setUser(newUser);

        assertEquals(newAmount, bid.getAmount());
        assertEquals(newLot, bid.getLot());
        assertEquals(newUser, bid.getUser());
    }
}
