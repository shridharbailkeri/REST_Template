package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    //private static final String BASE_URL = "http://localhost:8080";

    private static final String GET_BEER_PATH = "/api/v1/beer";

    private static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";
    @Override
    public Page<BeerDTO> listBeers(String beerName) {

        RestTemplate restTemplate = restTemplateBuilder.build();

        // it does http get  .getForEntity()
        //ResponseEntity<String> stringResponse = restTemplate
        //                                            .getForEntity(BASE_URL + GET_BEER_PATH, String.class);

        //ResponseEntity<Map> mapResponse = restTemplate
        //        .getForEntity(BASE_URL + GET_BEER_PATH, Map.class);

        //ResponseEntity<JsonNode> jsonResponse = restTemplate
        //        .getForEntity(BASE_URL + GET_BEER_PATH, JsonNode.class);

        //jsonResponse.getBody().findPath("content")
        //        .elements().forEachRemaining(node -> {
        //            System.out.println(node.get("beerName").asText());
        //        });
        //System.out.println(stringResponse.getBody());
        // .findPath("content") here am saying get the content property and then for each element in content
        // we want to apply the function mentioned sout and we r going to take that node get the beer name property
        // and display it as text , jackson gives lot of flexibility when working
        // json objects they get mapped to java objects , json node objects which is very very flexible
        //node.get("beerName") get operation returns back json node object
        // so for that specific node we want text property from that

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH); // uses default root path

        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        ResponseEntity<BeerDTOPageImpl> response = restTemplate
                .getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);

        return response.getBody();
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }

    @Override
    public BeerDTO createBeer(BeerDTO newDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        URI uri = restTemplate.postForLocation(GET_BEER_PATH, newDto);
        return restTemplate.getForObject(uri.getPath(), BeerDTO.class);
    }

    @Override
    public BeerDTO updateBeer(BeerDTO beerDTO) {
        // we r passing beerDto as argument , we r going to send that to our api client via below line using a put method
        // that would get converted to json sent through the api and apis gonna return back with a no content
        // then we r using the beerdto again to get id value form it
        // finally we r using existing method getBeerById to fetch the updated entity
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(GET_BEER_BY_ID_PATH, beerDTO, beerDTO.getId());
        return getBeerById(beerDTO.getId());
    }

    @Override
    public void deleteBeer(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        restTemplate.delete(GET_BEER_BY_ID_PATH, beerId);
    }
}
