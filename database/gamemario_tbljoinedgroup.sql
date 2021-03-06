-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: gamemario
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `tbljoinedgroup`
--

DROP TABLE IF EXISTS `tbljoinedgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbljoinedgroup` (
  `userid` int NOT NULL,
  `groupid` int NOT NULL,
  `position` int NOT NULL,
  PRIMARY KEY (`userid`,`groupid`),
  KEY `RB2.2_idx` (`groupid`),
  CONSTRAINT `RB2.1` FOREIGN KEY (`userid`) REFERENCES `tbluser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `RB2.2` FOREIGN KEY (`groupid`) REFERENCES `tblgroup` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbljoinedgroup`
--

LOCK TABLES `tbljoinedgroup` WRITE;
/*!40000 ALTER TABLE `tbljoinedgroup` DISABLE KEYS */;
INSERT INTO `tbljoinedgroup` VALUES (10000,1000,2),(10000,1001,2),(10000,1002,1),(10001,1001,1),(10002,1000,0),(10003,1000,0),(10003,1004,2),(10005,1003,2),(10006,1000,1),(10006,1005,2),(10007,1002,2),(10009,1000,1),(10009,1007,2),(10013,1006,2);
/*!40000 ALTER TABLE `tbljoinedgroup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-14 12:10:57
