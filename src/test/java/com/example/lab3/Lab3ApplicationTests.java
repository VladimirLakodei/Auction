import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.lab3.Lab3Application;
import com.example.lab3.AuctionLot;
import com.example.lab3.AuctionController;

@SpringBootTest(classes = Lab3Application.class)
class Lab3ApplicationTests {

    @Autowired
    private AuctionController auctionController;

    @Test
    void testCreateAuction() {
        // Test logic here
        AuctionLot lot = new AuctionLot(1L, "Lot 1", "Description for Lot 1", BigDecimal.valueOf(100), null, LocalDateTime.now().plusDays(7));
        lot.setName("Test Auction Lot");
        lot.setDescription("Test description");
        lot.setStartingPrice(BigDecimal.valueOf(100));

        AuctionLot createdLot = auctionController.createLot(lot);

        // Assert or verify the expected behavior
        // For example, you can check if the created lot matches the input values or if it has been added to the list of lots
        // You can use assertions provided by testing frameworks like JUnit or libraries like AssertJ or Hamcrest

        // Example using JUnit assertion:
        assertNotNull(createdLot.getId());
        assertEquals("Test Auction Lot", createdLot.getName());
        assertEquals("Test description", createdLot.getDescription());
        assertEquals(BigDecimal.valueOf(100), createdLot.getStartingPrice());
    }
}
