-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem3
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
  `IDArt` int NOT NULL,
  PRIMARY KEY (`IDArt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grad`
--

DROP TABLE IF EXISTS `grad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grad` (
  `IDGra` int NOT NULL,
  PRIMARY KEY (`IDGra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IDKor` int NOT NULL,
  PRIMARY KEY (`IDKor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `narudzbina`
--

DROP TABLE IF EXISTS `narudzbina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `narudzbina` (
  `IDNar` int NOT NULL AUTO_INCREMENT,
  `IDKor` int NOT NULL,
  `IDGra` int NOT NULL,
  `ukupna_cena` double NOT NULL,
  `vreme` datetime NOT NULL,
  `adresa` varchar(100) NOT NULL,
  PRIMARY KEY (`IDNar`),
  KEY `FK_Nar_IDKor_idx` (`IDKor`),
  KEY `FK_Nar_IDGra_idx` (`IDGra`),
  CONSTRAINT `FK_Nar_IDGra` FOREIGN KEY (`IDGra`) REFERENCES `grad` (`IDGra`),
  CONSTRAINT `FK_Nar_IDKor` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stavka_narudzbina`
--

DROP TABLE IF EXISTS `stavka_narudzbina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stavka_narudzbina` (
  `IDNar` int NOT NULL,
  `IDArt` int NOT NULL,
  `kolicina` int NOT NULL,
  `jedinicnaCena` double NOT NULL,
  PRIMARY KEY (`IDNar`,`IDArt`),
  KEY `FK_stavka_nar_IDArt_idx` (`IDArt`),
  CONSTRAINT `FK_stavka_nar_IDArt` FOREIGN KEY (`IDArt`) REFERENCES `artikal` (`IDArt`),
  CONSTRAINT `FK_stavka_nar_IDNar` FOREIGN KEY (`IDNar`) REFERENCES `narudzbina` (`IDNar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `IDTra` int NOT NULL AUTO_INCREMENT,
  `IDNar` int NOT NULL,
  `suma` double NOT NULL,
  `vreme` datetime NOT NULL,
  PRIMARY KEY (`IDTra`),
  UNIQUE KEY `IDNar_UNIQUE` (`IDNar`),
  KEY `FK_transakcija_IDNar_idx` (`IDNar`),
  CONSTRAINT `FK_transakcija_IDNar` FOREIGN KEY (`IDNar`) REFERENCES `narudzbina` (`IDNar`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-12 16:39:37
