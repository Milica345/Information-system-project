INSERT INTO podsistem3DB.korisnik (idKorisnika, korisnickoIme) VALUES (1, 'milan123');
INSERT INTO podsistem3DB.korisnik (idKorisnika, korisnickoIme) VALUES (2, 'emily123');
INSERT INTO podsistem3DB.korisnik (idKorisnika, korisnickoIme) VALUES (3, 'milica123');
INSERT INTO podsistem3DB.korisnik (idKorisnika, korisnickoIme) VALUES (4, 'masa123');
INSERT INTO podsistem3DB.korisnik (idKorisnika, korisnickoIme) VALUES (5, 'nenad123');

INSERT INTO podsistem3DB.narudzbina (ukupnaCena, vremeKreiranja, adresa, gradZaDostavu, idKorisnika) VALUES (4399.97, '2025-09-10 10:00:00', 'Ulica 2', 'London', 2);
INSERT INTO podsistem3DB.narudzbina (ukupnaCena, vremeKreiranja, adresa, gradZaDostavu, idKorisnika) VALUES (2999.97, '2025-09-15 14:00:00', 'Ulica 4', 'Beograd', 4);

INSERT INTO podsistem3DB.stavka (idNarudzbine, kolicinaArtikla, idArtikla, jedinicnaCena, idProdavca) VALUES (1, 1, 2, 1799.99, 1);
INSERT INTO podsistem3DB.stavka (idNarudzbine, kolicinaArtikla, idArtikla, jedinicnaCena, idProdavca) VALUES (1, 2, 5, 1299.99, 5);
INSERT INTO podsistem3DB.stavka (idNarudzbine, kolicinaArtikla, idArtikla, jedinicnaCena, idProdavca) VALUES (2, 1, 4, 1099.99, 3);
INSERT INTO podsistem3DB.stavka (idNarudzbine, kolicinaArtikla, idArtikla, jedinicnaCena, idProdavca) VALUES (2, 2, 8, 699.99, 3);
INSERT INTO podsistem3DB.stavka (idNarudzbine, kolicinaArtikla, idArtikla, jedinicnaCena, idProdavca) VALUES (2, 1, 10, 499.99, 1);

INSERT INTO podsistem3DB.transakcija (idNarudzbine, suma, vremePlacanja) VALUES (1, 4399.97, '2025-09-10 10:05:00');
INSERT INTO podsistem3DB.transakcija (idNarudzbine, suma, vremePlacanja) VALUES (2, 2999.97, '2025-09-15 14:05:00');