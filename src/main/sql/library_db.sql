CREATE TABLE Person (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(50) NOT NULL,
    year_of_birth int NOT NULL
);

CREATE TABLE Book (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar(50) NOT NULL,
    author varchar(50) NOT NULL,
    year int NOT NULL,
    person_id int REFERENCES Person(id) ON DELETE SET NULL,
    date_of_borrowing date
);