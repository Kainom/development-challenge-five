create schema if not exists med;


CREATE TABLE
    med.usuario (
        id BIGSERIAL PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        password_ VARCHAR(255) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE,
        role_ VARCHAR(20) NOT NULL
    );