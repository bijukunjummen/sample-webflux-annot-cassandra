package cass.web;

import cass.domain.Hotel;
import cass.domain.HotelByLetter;
import cass.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping(path = "/{id}")
    public Mono<Hotel> get(@PathVariable("id") UUID uuid) {
        return this.hotelService.findOne(uuid);
    }

    @PostMapping
    public Mono<ResponseEntity<Hotel>> save(@RequestBody Hotel hotel) {
        return this.hotelService.save(hotel)
                .map(savedHotel -> new ResponseEntity<>(savedHotel, HttpStatus.CREATED));
    }

    @PutMapping
    public Mono<ResponseEntity<Hotel>> update(@RequestBody Hotel hotel) {
        return this.hotelService.update(hotel)
                .map(savedHotel -> new ResponseEntity<>(savedHotel, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public Mono<ResponseEntity<String>> delete(
            @PathVariable("id") UUID uuid) {
        return this.hotelService.delete(uuid).map((Boolean status) ->
                new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED));
    }

    @GetMapping(path = "/startingwith/{letter}")
    public Flux<HotelByLetter> findHotelsWithLetter(
            @PathVariable("letter") String letter) {
        return this.hotelService.findHotelsStartingWith(letter);
    }

    @GetMapping(path = "/fromstate/{state}")
    public Flux<Hotel> findHotelsInState(
            @PathVariable("state") String state) {
        return this.hotelService.findHotelsInState(state);
    }
}
