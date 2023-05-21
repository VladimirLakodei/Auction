import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import com.example.lab3.AuctionLot;
import com.example.lab3.Bid;
import com.example.lab3.User;


@SpringJUnitConfig
@SpringBootTest
@DataJpaTest
public class BidTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCreateBid() {
        // Create a new bid
        AuctionLot lot = new AuctionLot(1L, "Lot 1", "Description for Lot 1", BigDecimal.valueOf(100), null, LocalDateTime.now().plusDays(7));
        lot.setName("Lot 1");
        entityManager.persist(lot);

        User user = new User();
        user.setName("John Doe");
        entityManager.persist(user);

        BigDecimal amount = new BigDecimal("100.00");
        Bid bid = new Bid(amount, lot, user);
        entityManager.persist(bid);

        // Flush and clear the entity manager to synchronize changes
        entityManager.flush();
        entityManager.clear();

        // Retrieve the bid from the database
        Bid retrievedBid = entityManager.find(Bid.class, bid.getId());

        // Perform assertions
        assertNotNull(retrievedBid);
        assertEquals(amount, retrievedBid.getAmount());
        assertEquals(lot, retrievedBid.getLot());
        assertEquals(lot, retrievedBid.getBidderName());
    }
}
