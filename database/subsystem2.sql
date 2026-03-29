-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.45

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
  `IDArt` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `opis` varchar(45) NOT NULL,
  `cena` double NOT NULL,
  `popust` int NOT NULL DEFAULT '0',
  `IDKat` int NOT NULL,
  `IDKor` int NOT NULL,
  PRIMARY KEY (`IDArt`),
  KEY `IDKat_idx` (`IDKat`),
  KEY `IDKor_idx` (`IDKor`),
  CONSTRAINT `IDKat` FOREIGN KEY (`IDKat`) REFERENCES `kategorija` (`IDKat`),
  CONSTRAINT `IDKor` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `IDKat` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `IDKatNat` int DEFAULT NULL,
  PRIMARY KEY (`IDKat`),
  KEY `FK_IDKat_idx` (`IDKatNat`),
  CONSTRAINT `IDKatNad` FOREIGN KEY (`IDKatNat`) REFERENCES `kategorija` (`IDKat`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IDKor` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IDKor`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `korpa`
--

DROP TABLE IF EXISTS `korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korpa` (
  `IDKor` int NOT NULL,
  `cena` double NOT NULL,
  PRIMARY KEY (`IDKor`),
  KEY `IDKor_idx` (`IDKor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lista_zelja`
--

DROP TABLE IF EXISTS `lista_zelja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista_zelja` (
  `IDKor` int NOT NULL,
  `datum` date NOT NULL,
  PRIMARY KEY (`IDKor`),
  CONSTRAINT `FK_Lista_Zelja_IDKor` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stavka_korpa`
--

DROP TABLE IF EXISTS `stavka_korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stavka_korpa` (
  `IDArt` int NOT NULL,
  `IDKor` int NOT NULL,
  `kolicina` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`IDArt`,`IDKor`),
  KEY `FK_Stavke_Korpa_idx` (`IDKor`),
  CONSTRAINT `FK_Stavke_Artikal` FOREIGN KEY (`IDArt`) REFERENCES `artikal` (`IDArt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stavka_liste_zelja`
--

DROP TABLE IF EXISTS `stavka_liste_zelja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stavka_liste_zelja` (
  `IDKor` int NOT NULL,
  `IDArt` int NOT NULL,
  `vreme_dodavanja` datetime NOT NULL,
  PRIMARY KEY (`IDKor`,`IDArt`),
  KEY `FK_stavka_liste_zelja_IDArt_idx` (`IDArt`),
  CONSTRAINT `FK_stavka_liste_zelja_IDArt` FOREIGN KEY (`IDArt`) REFERENCES `artikal` (`IDArt`),
  CONSTRAINT `FK_stavka_liste_zelja_IDKor` FOREIGN KEY (`IDKor`) REFERENCES `lista_zelja` (`IDKor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-12 16:39:30
