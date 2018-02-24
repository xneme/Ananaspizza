BEGIN TRANSACTION;

CREATE TABLE Pizza (
    id INTEGER PRIMARY KEY,
    nimi TEXT,
    pohja_id INTEGER,
    kastike_id INTEGER,
    koko_id INTEGER,
    hinta DOUBLE,
    FOREIGN KEY (pohja_id) REFERENCES Pohja(id),
    FOREIGN KEY (kastike_id) REFERENCES Kastike(id),
    FOREIGN KEY (koko_id) REFERENCES Koko(id)
);

CREATE TABLE Tayte (
    id INTEGER PRIMARY KEY,
    nimi TEXT,
    vegaaninen  BOOLEAN
);

CREATE TABLE Pohja (
    id INTEGER PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE Kastike (
    id INTEGER PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE Koko (
    id INTEGER PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE PizzaTayte (
    pizza_id INTEGER,
    tayte_id INTEGER,
    FOREIGN KEY (pizza_id) REFERENCES PizzaAnnos(id),
    FOREIGN KEY (tayte_id) REFERENCES Tayte(id)
);
