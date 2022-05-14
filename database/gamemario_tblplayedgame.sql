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
-- Table structure for table `tblplayedgame`
--

DROP TABLE IF EXISTS `tblplayedgame`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblplayedgame` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gainedpoint` int NOT NULL,
  `result` int NOT NULL,
  `characterid` int NOT NULL,
  `userid` int NOT NULL,
  `gameid` int NOT NULL,
  `playedtournamentid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `RB4_idx` (`characterid`),
  KEY `RB5_idx` (`userid`),
  KEY `RB6_idx` (`gameid`),
  KEY `RB7_idx` (`playedtournamentid`),
  CONSTRAINT `RB4` FOREIGN KEY (`characterid`) REFERENCES `tblcharacter` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `RB5` FOREIGN KEY (`userid`) REFERENCES `tbluser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `RB6` FOREIGN KEY (`gameid`) REFERENCES `tblgame` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `RB7` FOREIGN KEY (`playedtournamentid`) REFERENCES `tblplayedtournament` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100026 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblplayedgame`
--

LOCK TABLES `tblplayedgame` WRITE;
/*!40000 ALTER TABLE `tblplayedgame` DISABLE KEYS */;
INSERT INTO `tblplayedgame` VALUES (100000,250,1,100,10000,100000,NULL),(100001,200,0,101,10001,100000,NULL),(100002,300,1,102,10002,100001,NULL),(100003,280,0,105,10004,100001,NULL),(100004,400,1,103,10000,100002,NULL),(100005,395,0,102,10006,100002,NULL),(100006,380,0,100,10003,100003,NULL),(100007,420,1,101,10000,100003,NULL),(100008,400,0,102,10001,100004,NULL),(100009,380,1,103,10005,100004,NULL),(100010,399,1,100,10001,100005,NULL),(100011,396,0,101,10000,100005,NULL),(100012,395,1,102,10003,100006,NULL),(100013,390,0,103,10004,100006,NULL),(100014,670,1,102,10001,100007,NULL),(100015,600,0,103,10004,100007,NULL),(100016,850,1,102,10005,100008,NULL),(100017,790,0,100,10007,100008,NULL),(100018,875,1,102,10000,100009,NULL),(100019,780,0,101,10002,100009,NULL),(100020,0,0,104,10006,100010,NULL),(100021,0,0,107,10000,100010,NULL),(100022,0,0,104,10002,100011,NULL),(100023,0,0,102,10000,100011,NULL),(100024,0,0,106,10002,100012,NULL),(100025,0,0,107,10000,100012,NULL);
/*!40000 ALTER TABLE `tblplayedgame` ENABLE KEYS */;
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
