BEGIN TRANSACTION;

CREATE TABLE PizzaAnnos (
    id INTEGER PRIMARY KEY,
    nimi TEXT,
    koko TEXT,
    hinta DOUBLE
);

CREATE TABLE Tayte (
    id INTEGER PRIMARY KEY,
    nimi TEXT,
    vegaaninen  BOOLEAN
);

CREATE TABLE Pohjakastike (
    id INTEGER PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE Pohja (
    id INTEGER PRIMARY KEY,
    nimi TEXT
);

CREATE TABLE PizzaTayte (
    pizza_id INTEGER,
    tayte_id INTEGER,
    FOREIGN KEY (pizza_id) REFERENCES PizzaAnnos(id),
    FOREIGN KEY (tayte_id) REFERENCES Tayte(id)
);

CREATE TABLE PizzaPohjakastike (
    pizza_id INTEGER,
    pohjakastike_id INTEGER,
    FOREIGN KEY (pizza_id) REFERENCES PizzaAnnos(id),
    FOREIGN KEY (pohjakastike_id) REFERENCES Pohjakastike(id)
);

CREATE TABLE PizzaPohja (
    pizza_id INTEGER,
    pohja_id INTEGER,
    FOREIGN KEY (pizza_id) REFERENCES PizzaAnnos(id),
    FOREIGN KEY (pohja_id) REFERENCES Pohja(id)
);
