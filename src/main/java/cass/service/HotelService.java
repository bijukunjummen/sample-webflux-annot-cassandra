package cass.service;

import cass.domain.Hotel;
import cass.domain.HotelByLetter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface HotelService {
    Mono<Hotel> save(Hotel hotel);
    Mono<Hotel> update(Hotel hotel);
    Mono<Hotel> findOne(UUID uuid);
    Mono<Boolean> delete(UUID uuid);

    Flux<HotelByLetter> findHotelsStartingWith(String letter);
    Flux<Hotel> findHotelsInState(String state);
}
