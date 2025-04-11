create table orders
(
    id         uuid    not null
        constraint "pk_orders"
            primary key,
    courier_id uuid,
    location_x integer not null,
    location_y integer not null,
    status     text    not null
);

create table transports
(
    id    uuid    not null
        constraint "pk_transports"
            primary key,
    name  text    not null,
    speed integer not null
);

create table couriers
(
    id           uuid    not null
        constraint "pk_couriers"
            primary key,
    name         text    not null,
    transport_id uuid    not null
        constraint "fk_couriers_transports_transport_id"
            references transports
            on delete cascade,
    location_x   integer not null,
    location_y   integer not null,
    status       text    not null
);

create index "ix_couriers_transport_id"
    on couriers (transport_id);




