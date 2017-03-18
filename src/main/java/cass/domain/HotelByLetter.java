package cass.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("hotels_by_letter")
public class HotelByLetter {

    public HotelByLetter() {}

    public HotelByLetter(Hotel hotel) {
        HotelByLetterKey hotelByLetterKey = new HotelByLetterKey();
        hotelByLetterKey.setFirstLetter(hotel.getName().substring(0, 1));
        hotelByLetterKey.setName(hotel.getName());
        hotelByLetterKey.setHotelId(hotel.getId());
        this.setHotelByLetterKey(hotelByLetterKey);
        this.setState(hotel.getState());
        this.setAddress(hotel.getAddress());
        this.setZip(hotel.getZip());
    }

    @PrimaryKey
    private HotelByLetterKey hotelByLetterKey;

    private String address;

    private String state;

    private String zip;

    public HotelByLetterKey getHotelByLetterKey() {
        return hotelByLetterKey;
    }

    public void setHotelByLetterKey(HotelByLetterKey hotelByLetterKey) {
        this.hotelByLetterKey = hotelByLetterKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
