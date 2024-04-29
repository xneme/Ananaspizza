
BEGIN TRANSACTION;

CREATE TABLE Tayte (
    id SERIAL PRIMARY KEY,
    nimi TEXT,
    vegaaninen  BOOLEAN
);

CREATE TABLE Pohja (
    id SERIAL PRIMARY KEY,
    nimi TEXT,
    vegaaninen  BOOLEAN
);

CREATE TABLE Kastike (
    id SERIAL PRIMARY KEY,
    nimi TEXT,
    vegaaninen  BOOLEAN
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
    ohje TEXT,
    FOREIGN KEY (pizza_id) REFERENCES Pizza(id),
    FOREIGN KEY (tayte_id) REFERENCES Tayte(id)
);

INSERT INTO Pohja (nimi, vegaaninen) VALUES ('Ei pohjaa', true);
INSERT INTO Pohja (nimi, vegaaninen) VALUES ('Ohut vehnäpohja', true);
INSERT INTO Pohja (nimi, vegaaninen) VALUES ('Pannupohja', true);
INSERT INTO Pohja (nimi, vegaaninen) VALUES ('Ananaspohja', true);

INSERT INTO Kastike (nimi, vegaaninen) VALUES ('Ei kastiketta', true);
INSERT INTO Kastike (nimi, vegaaninen) VALUES ('Tomaattikastike', true);
INSERT INTO Kastike (nimi, vegaaninen) VALUES ('Ananaskastike', true);

INSERT INTO Koko (nimi) VALUES ('Normaali');
INSERT INTO Koko (nimi) VALUES ('Perhe');
INSERT INTO Koko (nimi) VALUES ('Jättiläismäinen');

INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Ananas', true);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Mozzarella', false);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Pepperoni', false);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Fetajuusto', false);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Juustonkorvike', true);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Vöner', true);

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ('Ananas-ananaspizza', 2, 2, 1, 7.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (1, 1, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (1, 1, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (1, 2, '');

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ('Ananasräjähdys', 4, 3, 3, 23.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (2, 1, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (2, 1, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (2, 1, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (2, 1, '');

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ('Ananas-pepperonipizza', 3, 2, 2, 11.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (3, 1, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (3, 3, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (3, 2, '');

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ('Vegaaninen ananaspizza', 2, 2, 1, 7.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (4, 1, '');
INSERT INTO PizzaTayte (pizza_id, tayte_id, ohje) VALUES (4, 5, '');

COMMIT;
