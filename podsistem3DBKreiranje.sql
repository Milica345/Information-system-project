CREATE DATABASE  IF NOT EXISTS `podsistem3DB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `podsistem3DB`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: podsistem3DB
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
-- Table structure for table `narudzbina`
--

DROP TABLE IF EXISTS `narudzbina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `narudzbina` (
  `idNarudzbine` int(11) NOT NULL AUTO_INCREMENT,
  `ukupnaCena` decimal(15,2) NOT NULL DEFAULT 0.00,
  `vremeKreiranja` datetime NOT NULL DEFAULT current_timestamp(),
  `adresa` varchar(200) NOT NULL,
  `gradZaDostavu` varchar(100) NOT NULL,
  `idKorisnika` int(11) NOT NULL,
  PRIMARY KEY (`idNarudzbine`),
  KEY `idKorisnika_narudzbina_idx` (`idKorisnika`),
  CONSTRAINT `idKorisnika_narudzbina` FOREIGN KEY (`idKorisnika`) REFERENCES `korisnik` (`idKorisnika`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stavka`
--

DROP TABLE IF EXISTS `stavka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stavka` (
  `idStavke` int(11) NOT NULL AUTO_INCREMENT,
  `idNarudzbine` int(11) NOT NULL,
  `kolicinaArtikla` int(11) NOT NULL DEFAULT 1,
  `idArtikla` int(11) NOT NULL,
  `jedinicnaCena` decimal(10,2) NOT NULL DEFAULT 0.00,
  `idProdavca` int(11) DEFAULT NULL,
  PRIMARY KEY (`idStavke`),
  KEY `idNarudzbine_stavka_idx` (`idNarudzbine`),
  KEY `idKorisnika_stavka_idx` (`idProdavca`),
  CONSTRAINT `idKorisnika_stavka` FOREIGN KEY (`idProdavca`) REFERENCES `korisnik` (`idKorisnika`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `idNarudzbine_stavka` FOREIGN KEY (`idNarudzbine`) REFERENCES `narudzbina` (`idNarudzbine`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `idTransakcije` int(11) NOT NULL AUTO_INCREMENT,
  `idNarudzbine` int(11) NOT NULL,
  `suma` decimal(15,2) NOT NULL DEFAULT 0.00,
  `vremePlacanja` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`idTransakcije`),
  UNIQUE KEY `idNarudzbine_UNIQUE` (`idNarudzbine`),
  KEY `idNarudzbine_transakcija_idx` (`idNarudzbine`),
  CONSTRAINT `idNarudzbine_transakcija` FOREIGN KEY (`idNarudzbine`) REFERENCES `narudzbina` (`idNarudzbine`) ON UPDATE CASCADE
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

-- Dump completed on 2026-03-02 21:40:42
