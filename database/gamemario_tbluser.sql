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
-- Table structure for table `tbluser`
--

DROP TABLE IF EXISTS `tbluser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbluser` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `gender` int NOT NULL,
  `dateofbirth` date NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10014 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbluser`
--

LOCK TABLES `tbluser` WRITE;
/*!40000 ALTER TABLE `tbluser` DISABLE KEYS */;
INSERT INTO `tbluser` VALUES (10000,'hoangtu@yahoo.com','hoangtu','Hoàng Văn Tú',0,'2000-10-24',''),(10001,'huonggiang@yahoo.com','huonggiang','Đinh Hương Giang',1,'2000-09-12',NULL),(10002,'thuynga@yahoo.com','thuynga','Lê Thị Thúy Nga',1,'1996-07-22',NULL),(10003,'hoanganh@yahoo.com','hoanganh','Nguyễn Hoàng Anh',0,'1995-03-19',NULL),(10004,'quangan@yahoo.com','quangan','Trịnh Quang An',0,'1996-11-05',NULL),(10005,'sontung@yahoo.com','sontung','Nguyễn Sơn Tùng',0,'1991-12-28',NULL),(10006,'nghipv@yahoo.com','nghipv','Phạm Văn Nghị',0,'2000-11-15',NULL),(10007,'chint@yahoo.com','chint','Nguyễn Thùy Chi',1,'1996-02-26',NULL),(10008,'lannt@yahoo.com','lannt','Nguyễn Thị Lan',1,'2021-10-06',NULL),(10009,'thutt@yahoo.com','thutt','Trần Thị Thu',1,'1998-06-15',NULL),(10010,'pdanh@yahoo.com','pdanh','Phạm Đức Anh',0,'2000-07-18',NULL),(10011,'leloi@gmail.com','leloi','Lê Lợi',0,'1998-08-19',NULL),(10012,'hangtt@gmail.com','hangtt','Trần Thị Thúy Hằng',1,'2002-07-09',NULL),(10013,'mailn@gmail.com','mailn','Lê Ngọc Mai',1,'2000-05-17',NULL);
/*!40000 ALTER TABLE `tbluser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-14 12:10:56
