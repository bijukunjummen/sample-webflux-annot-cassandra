package cass.repository;

import cass.domain.HotelByLetter;
import cass.domain.HotelByLetterKey;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CassandraHotelByLetterRepository implements HotelByLetterRepository {
    private final ReactiveCassandraOperations cassandraTemplate;

    public CassandraHotelByLetterRepository(ReactiveCassandraOperations cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public Flux<HotelByLetter> findByFirstLetter(String letter) {
        Select select = QueryBuilder.select().from("hotels_by_letter");
        select.where(QueryBuilder.eq("first_letter", letter));
        return this.cassandraTemplate.select(select, HotelByLetter.class);
    }

    @Override
    public Mono<HotelByLetter> save(HotelByLetter hotelByLetter) {
        return this.cassandraTemplate.insert(hotelByLetter);
    }

    @Override
    public Mono<Boolean> delete(HotelByLetterKey hotelByLetterKey) {
        return this.cassandraTemplate.deleteById(hotelByLetterKey, HotelByLetter.class);
    }

}
