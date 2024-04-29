BEGIN TRANSACTION;

INSERT INTO Pohja (nimi) VALUES ('Ei pohjaa');
INSERT INTO Pohja (nimi) VALUES ('Ohut vehnäpohja');
INSERT INTO Pohja (nimi) VALUES ('Pannupohja');
INSERT INTO Pohja (nimi) VALUES ('Ananaspohja');

INSERT INTO Kastike (nimi) VALUES ('Ei kastiketta');
INSERT INTO Kastike (nimi) VALUES ('Tomaattikastike');
INSERT INTO Kastike (nimi) VALUES ('Ananaskastike');

INSERT INTO Koko (nimi) VALUES ('Normaali');
INSERT INTO Koko (nimi) VALUES ('Perhe');
INSERT INTO Koko (nimi) VALUES ('Jättiläismäinen');

INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Ananas', 1);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Mozzarella', 0);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Pepperoni', 0);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Fetajuusto', 0);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Juustonkorvike', 1);
INSERT INTO Tayte (nimi, vegaaninen) VALUES ('Vöner', 1);

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ("Ananas-ananaspizza", 2, 2, 1, 7.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (1, 1);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (1, 1);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (1, 2);

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ("Ananasräjähdys", 4, 3, 3, 23.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (2, 1);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (2, 1);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (2, 1);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (2, 1);

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ("Ananas-pepperonipizza", 3, 2, 2, 11.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (3, 1);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (3, 3);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (3, 2);

INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta)
    VALUES ("Vegaaninen ananaspizza", 2, 2, 1, 7.90);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (4, 1);
INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (4, 5);

COMMIT;

