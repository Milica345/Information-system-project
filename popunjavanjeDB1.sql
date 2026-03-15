INSERT INTO podsistem1DB.grad (naziv) VALUES ('Beograd');
INSERT INTO podsistem1DB.grad (naziv) VALUES ('London');
INSERT INTO podsistem1DB.grad (naziv) VALUES ('Sarajevo');

INSERT INTO podsistem1DB.uloga (naziv) VALUES ('administrator');
INSERT INTO podsistem1DB.uloga (naziv) VALUES ('prodavac');
INSERT INTO podsistem1DB.uloga (naziv) VALUES ('kupac');

INSERT INTO podsistem1DB.korisnik (korisnickoIme, sifra, ime, prezime, adresa, idGrada, stanjeNovca) VALUES ('milan123', 'milanGlumac123', 'Milan', 'Maric', 'Ulica 1', 1, 345000.00);
INSERT INTO podsistem1DB.korisnik (korisnickoIme, sifra, ime, prezime, adresa, idGrada, stanjeNovca) VALUES ('emily123', 'najboljaGlumica123', 'Emily', 'Blunt', 'Ulica 2', 2, 1000000.00);
INSERT INTO podsistem1DB.korisnik (korisnickoIme, sifra, ime, prezime, adresa, idGrada, stanjeNovca) VALUES ('milica123', 'najboljiProjekat123', 'Milica', 'Milosevic', 'Ulica 3', 3, 9999999.00);
INSERT INTO podsistem1DB.korisnik (korisnickoIme, sifra, ime, prezime, adresa, idGrada, stanjeNovca) VALUES ('masa123', 'masaMirise123', 'Masa', 'Nik', 'Ulica 4', 1, 20000.00);
INSERT INTO podsistem1DB.korisnik (korisnickoIme, sifra, ime, prezime, adresa, idGrada, stanjeNovca) VALUES ('nenad123', 'nenadGOAT', 'Nenad', 'Skok', 'Ulica 5', 3, 44000.00);

INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (1, 1);
INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (1, 2);
INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (2, 3);
INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (3, 1);
INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (3, 2);
INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (3, 3);
INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (4, 3);
INSERT INTO podsistem1DB.imaUlogu (idKorisnika, idUloge) VALUES (5, 2);

