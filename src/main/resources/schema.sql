create table book
(
   id integer not null,
   title varchar(255) not null,
   primary key(id)
);

create table player
(
    id integer not null,
    name varchar(255) not null,
    age integer not null,
    primary key(id)
);