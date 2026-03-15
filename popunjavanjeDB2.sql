INSERT INTO podsistem2DB.korisnik (idKorisnika, korisnickoIme) VALUES (1, 'milan123');
INSERT INTO podsistem2DB.korisnik (idKorisnika, korisnickoIme) VALUES (2, 'emily123');
INSERT INTO podsistem2DB.korisnik (idKorisnika, korisnickoIme) VALUES (3, 'milica123');
INSERT INTO podsistem2DB.korisnik (idKorisnika, korisnickoIme) VALUES (4, 'masa123');
INSERT INTO podsistem2DB.korisnik (idKorisnika, korisnickoIme) VALUES (5, 'nenad123');

INSERT INTO podsistem2DB.kategorija (naziv, idNadkategorije) VALUES ('elektronika', NULL);
INSERT INTO podsistem2DB.kategorija (naziv, idNadkategorije) VALUES ('televizori', 1);
INSERT INTO podsistem2DB.kategorija (naziv, idNadkategorije) VALUES ('telefoni', 1);
INSERT INTO podsistem2DB.kategorija (naziv, idNadkategorije) VALUES ('sklopivi telefoni', 3);

INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Samsung Galaxy S24', 'Najnoviji Samsung telefon', 899.99, 5.00, 3, 1);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Samsung Galaxy Z Fold 6', 'Sklopivi Samsung telefon', 1799.99, 10.00, 4, 1);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('iPhone 15', 'Apple telefon najnovije generacije', 999.99, 0.00, 3, 3);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Samsung Galaxy Z Flip 5', 'Kompaktan sklopivi telefon', 1099.99, 15.00, 4, 3);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Samsung QLED TV 55', 'Samsung QLED televizor 55 inca', 1299.99, 0.00, 2, 5);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('LG OLED TV 65', 'LG OLED televizor 65 inca', 2499.99, 20.00, 2, 5);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Sony Bravia 50', 'Sony televizor 50 inca', 799.99, 5.00, 2, 1);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Xiaomi 14', 'Xiaomi telefon sa dobrim fotoaparatom', 699.99, 10.00, 3, 3);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Motorola Razr 40', 'Motorola sklopivi telefon', 899.99, 0.00, 4, 5);
INSERT INTO podsistem2DB.artikal (naziv, opis, cena, popustUProcentima, idKategorije, idProizvodjaca) VALUES ('Philips TV 43', 'Philips televizor 43 inca', 499.99, 25.00, 2, 1);

INSERT INTO podsistem2DB.listaZelja (idKorisnika, datumKreiranja) VALUES (2, '2025-01-10');
INSERT INTO podsistem2DB.listaZelja (idKorisnika, datumKreiranja) VALUES (4, '2025-10-03');

INSERT INTO podsistem2DB.naListi (idListe, idArtikla, vremeDodavanja) VALUES (1, 3, '2025-01-10 14:30:00');
INSERT INTO podsistem2DB.naListi (idListe, idArtikla, vremeDodavanja) VALUES (2, 5, '2025-10-03 09:15:00');
INSERT INTO podsistem2DB.naListi (idListe, idArtikla, vremeDodavanja) VALUES (2, 8, '2025-10-05 10:45:00');

INSERT INTO podsistem2DB.korpa (idKorisnika, ukupnaCena) VALUES (2, 5199.96);
INSERT INTO podsistem2DB.korpa (idKorisnika, ukupnaCena) VALUES (4, 2599.97);
INSERT INTO podsistem2DB.korpa (idKorisnika, ukupnaCena) VALUES (1, 0.00);
INSERT INTO podsistem2DB.korpa (idKorisnika, ukupnaCena) VALUES (3, 0.00);
INSERT INTO podsistem2DB.korpa (idKorisnika, ukupnaCena) VALUES (5, 0.00);

INSERT INTO podsistem2DB.korpaSadrzi (idKorpe, idArtikla, kolicina) VALUES (1, 1, 2);
INSERT INTO podsistem2DB.korpaSadrzi (idKorpe, idArtikla, kolicina) VALUES (1, 6, 1);
INSERT INTO podsistem2DB.korpaSadrzi (idKorpe, idArtikla, kolicina) VALUES (1, 9, 1);
INSERT INTO podsistem2DB.korpaSadrzi (idKorpe, idArtikla, kolicina) VALUES (2, 3, 1);
INSERT INTO podsistem2DB.korpaSadrzi (idKorpe, idArtikla, kolicina) VALUES (2, 7, 2);