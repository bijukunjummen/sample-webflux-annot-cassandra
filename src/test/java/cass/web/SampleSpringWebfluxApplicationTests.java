package cass.web;

import cass.domain.Hotel;
import cass.domain.HotelByLetter;
import cass.service.HotelService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;


public class SampleSpringWebfluxApplicationTests {

    private WebTestClient client;

    private HotelService hotelService;

    private UUID sampleUUID = UUID.fromString("fd28ec06-6de5-4f68-9353-59793a5bdec2");

    @Before
    public void setUp() {
        this.hotelService = mock(HotelService.class);
        when(hotelService.findOne(sampleUUID)).thenReturn(Mono.just(new Hotel(sampleUUID, "test")));
        when(hotelService.save(any(Hotel.class))).thenAnswer(invocation -> {
            Hotel hotel = invocation.getArgument(0);
            return Mono.just(hotel);
        });
        when(hotelService.update(any(Hotel.class))).thenAnswer(invocation -> {
            Hotel hotel = invocation.getArgument(0);
            return Mono.just(hotel);
        });
        when(hotelService
                .findHotelsStartingWith(anyString()))
                .thenReturn(Flux.just(new HotelByLetter(new Hotel(sampleUUID, "test1"))));

        when(hotelService.findHotelsInState(anyString()))
                .thenReturn(Flux.just(new Hotel(sampleUUID, "test1"), new Hotel(sampleUUID, "test2")));

        this.client = WebTestClient.bindToController(new HotelController(hotelService)).build();
    }

    @Test
    public void testHotelGet() throws Exception {
        this.client.get().uri("/hotels/" + sampleUUID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Hotel.class)
                .isEqualTo(new Hotel(sampleUUID, "test"));
    }

    @Test
    public void testHotelSave() throws Exception {
        Hotel toBeSaved = new Hotel(sampleUUID, "test");
        this.client.post().uri("/hotels")
                .body(fromObject(toBeSaved))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Hotel.class)
                .isEqualTo(toBeSaved);

        verify(this.hotelService).save(toBeSaved);
    }

    @Test
    public void testHotelUpdate() throws Exception {
        Hotel toBeUpdated = new Hotel(sampleUUID, "test");
        this.client.put().uri("/hotels")
                .body(fromObject(toBeUpdated))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Hotel.class)
                .isEqualTo(toBeUpdated);
    }

    @Test
    public void testGetByFirstLetter() {
        this.client.get().uri("/hotels/startingwith/A")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(HotelByLetter.class)
                .hasSize(1);
    }

    @Test
    public void testGetByState() {
        this.client.get().uri("/hotels/fromstate/OR")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Hotel.class)
                .hasSize(2);
    }

}
