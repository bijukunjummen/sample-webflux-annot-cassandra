package cass.web;

import cass.domain.Hotel;
import cass.service.HotelService;
import cass.web.HotelController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SampleSpringWebfluxApplicationTests {

    private WebTestClient client ;
    
    private UUID sampleUUID = UUID.fromString("fd28ec06-6de5-4f68-9353-59793a5bdec2");
    
    @Before
    public void setUp() {
        HotelService hotelService = mock(HotelService.class);
        this.client = WebTestClient.bindToController(new HotelController(hotelService)).build();
        when(hotelService.findOne(sampleUUID)).thenReturn(Mono.just(new Hotel(sampleUUID, "test")));
    }
    
    @Test
    public void testHotelGet() throws Exception {
        this.client.get().uri("/hotels/" + sampleUUID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Hotel.class).value().isEqualTo(new Hotel(sampleUUID, "test"));
    }

}
