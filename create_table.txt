create table movie (
    id           char(3), 
    title        varchar (100), 
    company      varchar (50),
    releasedate  date,
    country      varchar (10),  
    totalscreen  int,
    profit       numeric (15,2),
    totalnum     int,
    grade        varchar (50),
    primary key (id));
