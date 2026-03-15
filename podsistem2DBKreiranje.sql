CREATE DATABASE  IF NOT EXISTS `podsistem2DB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `podsistem2DB`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: podsistem2DB
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.22-MariaDB-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `artikal`
--

DROP TABLE IF EXISTS `artikal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artikal` (
  `idArtikla` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) NOT NULL,
  `opis` varchar(200) NOT NULL,
  `cena` decimal(10,2) NOT NULL DEFAULT 0.00,
  `popustUProcentima` decimal(5,2) NOT NULL DEFAULT 0.00,
  `idKategorije` int(11) DEFAULT NULL,
  `idProizvodjaca` int(11) NOT NULL,
  PRIMARY KEY (`idArtikla`),
  KEY `idKategorije_artikal_idx` (`idKategorije`),
  KEY `idKorisnika_artikal_idx` (`idProizvodjaca`),
  CONSTRAINT `idKategorije_artikal` FOREIGN KEY (`idKategorije`) REFERENCES `kategorija` (`idKategorije`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `idKorisnika_artikal` FOREIGN KEY (`idProizvodjaca`) REFERENCES `korisnik` (`idKorisnika`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `idKategorije` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) NOT NULL,
  `idNadkategorije` int(11) DEFAULT NULL,
  PRIMARY KEY (`idKategorije`),
  KEY `idNadkategorije_kategorija_idx` (`idNadkategorije`),
  CONSTRAINT `idNadkategorije_kategorija` FOREIGN KEY (`idNadkategorije`) REFERENCES `kategorija` (`idKategorije`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idKorisnika` int(11) NOT NULL AUTO_INCREMENT,
  `korisnickoIme` varchar(50) NOT NULL,
  PRIMARY KEY (`idKorisnika`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `korpa`
--

DROP TABLE IF EXISTS `korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korpa` (
  `idKorpe` int(11) NOT NULL AUTO_INCREMENT,
  `ukupnaCena` decimal(15,2) NOT NULL DEFAULT 0.00,
  `idKorisnika` int(11) NOT NULL,
  PRIMARY KEY (`idKorpe`),
  UNIQUE KEY `idKorisnika_UNIQUE` (`idKorisnika`),
  KEY `idKorisnika_korpa_idx` (`idKorisnika`),
  CONSTRAINT `idKorisnika_korpa` FOREIGN KEY (`idKorisnika`) REFERENCES `korisnik` (`idKorisnika`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `korpaSadrzi`
--

DROP TABLE IF EXISTS `korpaSadrzi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korpaSadrzi` (
  `idKorpe` int(11) NOT NULL,
  `idArtikla` int(11) NOT NULL,
  `kolicina` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idKorpe`,`idArtikla`),
  KEY `idArtikla_korpaSadrzi_idx` (`idArtikla`),
  CONSTRAINT `idArtikla_korpaSadrzi` FOREIGN KEY (`idArtikla`) REFERENCES `artikal` (`idArtikla`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idKorpe_korpaSadrzi` FOREIGN KEY (`idKorpe`) REFERENCES `korpa` (`idKorpe`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `listaZelja`
--

DROP TABLE IF EXISTS `listaZelja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listaZelja` (
  `idListe` int(11) NOT NULL AUTO_INCREMENT,
  `idKorisnika` int(11) NOT NULL,
  `datumKreiranja` date NOT NULL,
  PRIMARY KEY (`idListe`),
  UNIQUE KEY `idKorisnika_UNIQUE` (`idKorisnika`),
  KEY `idKorisnika_listaZelja_idx` (`idKorisnika`),
  CONSTRAINT `idKorisnika_listaZelja` FOREIGN KEY (`idKorisnika`) REFERENCES `korisnik` (`idKorisnika`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `naListi`
--

DROP TABLE IF EXISTS `naListi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `naListi` (
  `idListe` int(11) NOT NULL,
  `idArtikla` int(11) NOT NULL,
  `vremeDodavanja` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`idListe`,`idArtikla`),
  KEY `idArtikla_naListi_idx` (`idArtikla`),
  CONSTRAINT `idArtikla_naListi` FOREIGN KEY (`idArtikla`) REFERENCES `artikal` (`idArtikla`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idListe_naListi` FOREIGN KEY (`idListe`) REFERENCES `listaZelja` (`idListe`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-02 21:39:16
