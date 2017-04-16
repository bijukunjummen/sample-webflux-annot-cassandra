package cass.service;

import cass.domain.Hotel;
import cass.domain.HotelByLetter;
import cass.repository.HotelByLetterRepository;
import cass.repository.HotelRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final HotelByLetterRepository hotelByLetterRepository;

    public HotelServiceImpl(HotelRepository hotelRepository,
                            HotelByLetterRepository hotelByLetterRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelByLetterRepository = hotelByLetterRepository;
    }

    @Override
    public Mono<Hotel> save(Hotel hotel) {
        if (hotel.getId() == null) {
            hotel.setId(UUID.randomUUID());
        }
        Mono<Hotel> saved = this.hotelRepository.save(hotel);
        saved.then(this.hotelByLetterRepository.save(new HotelByLetter(hotel)));
        return saved;
    }

    @Override
    public Mono<Hotel> update(Hotel hotel) {
        return this.hotelRepository.findOne(hotel.getId())
                .flatMap(existingHotel ->
                        this.hotelByLetterRepository.delete(new HotelByLetter(existingHotel).getHotelByLetterKey())
                                .then(this.hotelByLetterRepository.save(new HotelByLetter(hotel)))
                                .then(this.hotelRepository.update(hotel)));
    }

    @Override
    public Mono<Hotel> findOne(UUID uuid) {
        return this.hotelRepository.findOne(uuid);
    }

    @Override
    public Mono<Boolean> delete(UUID uuid) {
        Mono<Hotel> hotelMono = this.hotelRepository.findOne(uuid);
        return hotelMono
                .flatMap((Hotel hotel) -> this.hotelRepository.delete(hotel.getId())
                .then(this.hotelByLetterRepository
                        .delete(new HotelByLetter(hotel).getHotelByLetterKey())));
    }

    @Override
    public Flux<HotelByLetter> findHotelsStartingWith(String letter) {
        return this.hotelByLetterRepository.findByFirstLetter(letter);
    }

    @Override
    public Flux<Hotel> findHotelsInState(String state) {
        return this.hotelRepository.findByState(state);
    }
}
