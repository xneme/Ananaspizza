BEGIN TRANSACTION;

CREATE TABLE Tayte (
    id SERIAL PRIMARY KEY,
    nimi TEXT,
    vegaaninen  BOOLEAN
);

CREATE TABLE Pohja (
    id SERIAL PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE Kastike (
    id SERIAL PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE Koko (
    id SERIAL PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE Pizza (
    id SERIAL PRIMARY KEY,
    nimi TEXT,
    pohja_id INTEGER,
    kastike_id INTEGER,
    koko_id INTEGER,
    hinta NUMERIC,
    FOREIGN KEY (pohja_id) REFERENCES Pohja(id),
    FOREIGN KEY (kastike_id) REFERENCES Kastike(id),
    FOREIGN KEY (koko_id) REFERENCES Koko(id)
);

CREATE TABLE PizzaTayte (
    id SERIAL PRIMARY KEY,
    pizza_id INTEGER,
    tayte_id INTEGER,
    FOREIGN KEY (pizza_id) REFERENCES Pizza(id),
    FOREIGN KEY (tayte_id) REFERENCES Tayte(id)
);

COMMIT;
