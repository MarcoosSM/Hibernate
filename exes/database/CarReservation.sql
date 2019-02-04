CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mydb`;
-- MySQL dump 10.16  Distrib 10.1.35-MariaDB, for Win32 (AMD64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	10.1.35-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `license_plate` varchar(10) NOT NULL,
  `model` varchar(45) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`license_plate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES ('0001AAA','ModelA','Black','Dacia'),('0002BBB','ModelB','Red','Audi'),('0003CCC','ModelC','Blue','Citroen'),('0004DDD','ModelD','Yellow','Corvette'),('0005EEE','ModelE','White','Dacia');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `dni` varchar(9) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('00000001A','Maria','C/ Francia','913293123'),('00000002B','José','C/ Italia','931312345'),('00000003C','Pedro','C/ Alemania','916755312'),('00000004D','Julio','C/ Verne','915243531'),('00000005E','Paula','C/ Mozart','912351232');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `start_date` varchar(10) DEFAULT NULL,
  `end_date` varchar(10) DEFAULT NULL,
  `client_dni` varchar(9) DEFAULT NULL,
  `id_reservation` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_reservation`),
  KEY `client_dni_idx` (`client_dni`),
  CONSTRAINT `client_dni` FOREIGN KEY (`client_dni`) REFERENCES `client` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES ('01-01-2019','10-01-2019','00000001A',1),('11-01-2019','20-01-2019','00000002B',2),('21-01-2019','30-01-2019','00000003C',3),('01-02-2019','10-02-2019','00000004D',4),('11-02-2019','20-02-2019','00000005E',5),('20-02-2019','28-02-2019','00000001A',6),('01-03-2019','10-03-2019','00000002B',7),('11-03-2019','20-03-2019','00000003C',8),('21-03-2019','30-03-2019','00000004D',9),('01-04-2019','10-04-2019','00000005E',10);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserved_cars`
--

DROP TABLE IF EXISTS `reserved_cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reserved_cars` (
  `id_reservation` int(11) NOT NULL,
  `license_plate` varchar(10) NOT NULL,
  PRIMARY KEY (`id_reservation`,`license_plate`),
  KEY `license_plate_idx` (`license_plate`),
  CONSTRAINT `id_reservation` FOREIGN KEY (`id_reservation`) REFERENCES `reservation` (`id_reservation`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `license_plate` FOREIGN KEY (`license_plate`) REFERENCES `car` (`license_plate`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserved_cars`
--

LOCK TABLES `reserved_cars` WRITE;
/*!40000 ALTER TABLE `reserved_cars` DISABLE KEYS */;
INSERT INTO `reserved_cars` VALUES (1,'0004DDD'),(1,'0005EEE'),(2,'0001AAA'),(3,'0002BBB'),(4,'0003CCC'),(5,'0004DDD'),(6,'0001AAA'),(6,'0004DDD'),(7,'0001AAA'),(8,'0002BBB'),(9,'0003CCC'),(10,'0004DDD');
/*!40000 ALTER TABLE `reserved_cars` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-03 23:24:49
