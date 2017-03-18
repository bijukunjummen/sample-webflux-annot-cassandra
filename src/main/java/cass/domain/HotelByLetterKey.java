package cass.domain;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

@PrimaryKeyClass
public class HotelByLetterKey implements Serializable {

    @PrimaryKeyColumn(name="first_letter", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String firstLetter;

    @PrimaryKeyColumn(name="hotel_name", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String name;

    @PrimaryKeyColumn(name="hotel_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID hotelId;

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLetter() {

        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
