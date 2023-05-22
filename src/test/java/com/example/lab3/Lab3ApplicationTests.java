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
        AuctionLot lot = new AuctionLot(1L, "Lot 1", "Description for Lot 1", BigDecimal.valueOf(100), null, LocalDateTime.now().plusDays(7));
        lot.setName("Test Auction Lot");
        lot.setDescription("Test description");
        lot.setStartingPrice(BigDecimal.valueOf(100));

        AuctionLot createdLot = auctionController.createLot(lot);

        assertNotNull(createdLot.getId());
        assertEquals("Test Auction Lot", createdLot.getName());
        assertEquals("Test description", createdLot.getDescription());
        assertEquals(BigDecimal.valueOf(100), createdLot.getStartingPrice());
    }
}
