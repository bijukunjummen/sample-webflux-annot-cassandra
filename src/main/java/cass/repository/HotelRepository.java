package cass.repository;

import cass.domain.Hotel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface HotelRepository  {
    Mono<Hotel> save(Hotel hotel);
    Mono<Hotel> update(Hotel hotel);
    Mono<Hotel> findOne(UUID hotelId);
    Mono<Boolean> delete(UUID hotelId);
    Flux<Hotel> findByState(String state);
}
