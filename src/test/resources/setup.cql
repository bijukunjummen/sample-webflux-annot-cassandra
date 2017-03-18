CREATE TABLE IF NOT EXISTS  sample.hotels (
    id UUID,
    name varchar,
    address varchar,
    state varchar,
    zip varchar,
    primary key(id)
);

CREATE TABLE IF NOT EXISTS  sample.hotels_by_letter (
    first_letter varchar,
    hotel_name varchar,
    hotel_id UUID,
    address varchar,
    state varchar,
    zip varchar,
    primary key((first_letter), hotel_name, hotel_id)
);


CREATE MATERIALIZED VIEW sample.hotels_by_state AS
SELECT id, name, address, state, zip FROM hotels
WHERE state IS NOT NULL AND id IS NOT NULL AND name IS NOT NULL
PRIMARY KEY ((state), id)
WITH CLUSTERING ORDER BY (name DESC)