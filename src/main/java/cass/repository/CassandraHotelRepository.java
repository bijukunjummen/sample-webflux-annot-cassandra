package cass.repository;

import cass.domain.Hotel;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class CassandraHotelRepository implements HotelRepository {
    
    private final ReactiveCassandraOperations cassandraTemplate;
    
    public CassandraHotelRepository(ReactiveCassandraOperations cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }
    
    @Override
    public Mono<Hotel> save(Hotel hotel) {
        return this.cassandraTemplate.insert(hotel);
    }

    @Override
    public Mono<Hotel> update(Hotel hotel) {
        return this.cassandraTemplate.update(hotel);
    }

    @Override
    public Mono<Hotel> findOne(UUID hotelId) {
        return this.cassandraTemplate.selectOneById(hotelId, Hotel.class);
    }

    @Override
    public Mono<Boolean> delete(UUID hotelId) {
        return this.cassandraTemplate.deleteById(hotelId, Hotel.class);
    }

    @Override
    public Flux<Hotel> findByState(String state) {
        Select select = QueryBuilder.select().from("hotels_by_state");
        select.where(QueryBuilder.eq("state", state));
        return this.cassandraTemplate.select(select, Hotel.class);
    }
}
