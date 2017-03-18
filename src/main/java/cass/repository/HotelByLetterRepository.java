package cass.repository;

import cass.domain.HotelByLetter;
import cass.domain.HotelByLetterKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelByLetterRepository {
    Flux<HotelByLetter> findByFirstLetter(String letter);
    Mono<HotelByLetter> save(HotelByLetter hotelByLetter);
    Mono<Boolean> delete(HotelByLetterKey hotelByLetterKey);
}
