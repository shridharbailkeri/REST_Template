package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void testDeleteBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("12345")
                .build();

        BeerDTO beerDTO = beerClient.createBeer(newDto);

        beerClient.deleteBeer(beerDTO.getId());

        //assertThrows(HttpClientErrorException.class, () -> {
            //should error
            beerClient.getBeerById(beerDTO.getId());
        //});
    }


    @Test
    void testUpdateBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("12345")
                .build();

        BeerDTO beerDTO = beerClient.createBeer(newDto);
        final String newName = "Mango Bobs 3";
        beerDTO.setBeerName(newName);
        BeerDTO updatedBeer = beerClient.updateBeer(beerDTO);

        assertEquals(newName, updatedBeer.getBeerName());
    }

    @Test
    void testCreateBeer() {

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("12345")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDto);
        assertNotNull(savedDto);
    }

    @Test
    void getBeerById() {
        Page<BeerDTO> beerDTOS = beerClient.listBeers(null);
        BeerDTO dto = beerDTOS.getContent().get(0);
        BeerDTO byId = beerClient.getBeerById(dto.getId());
        assertNotNull(byId);

    }

    @Test
    void listBeersNoBeerName() {

        beerClient.listBeers(null);

    }

    @Test
    void listBeers() {

        beerClient.listBeers("ALE");

    }
}