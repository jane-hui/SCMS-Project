-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bcms
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookings` (
  `BK_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUST_NAME` varchar(30) NOT NULL,
  `CUST_CTNO` varchar(30) NOT NULL,
  `BK_DATE` varchar(10) NOT NULL,
  `TIME_DATA` varchar(50) NOT NULL,
  `TIME_DETAIL` varchar(500) NOT NULL,
  `BK_PERIOD` int(11) DEFAULT NULL,
  `US_ID` int(11) NOT NULL,
  `DATE_CREATED` date NOT NULL,
  `DATE_MODIFIED` date DEFAULT NULL,
  PRIMARY KEY (`BK_ID`),
  KEY `FK_USERID` (`US_ID`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`US_ID`) REFERENCES `users` (`US_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,'Jane','000-0000000','2019-06-25','[114, 115, 116]','Court 1 (14:00 - 17:00)\n',3,1,'2019-06-27','2019-07-02'),(2,'Mary','111-1111111','2019-06-26','[110, 111, 112, 210, 211, 212]','Court 1 (10:00 - 13:00)\nCourt 2 (10:00 - 13:00)\n',6,1,'2019-06-27','2019-07-02'),(3,'Harold Finch','222-2222222','2019-06-27','[110, 111, 112]','Court 1 (10:00 - 13:00)\n',3,1,'2019-06-27','2019-07-02'),(5,'Jane Hui','000-0000000','2019-06-29','[112]','Court 1 (12:00 - 13:00)\n',1,1,'2019-06-28','2019-07-05'),(8,'John Reese','444-4444444','2019-06-30','[119, 219]','Court 1 (19:00 - 20:00)\nCourt 2 (19:00 - 20:00)\n',2,1,'2019-06-29','2019-07-05'),(9,'Tom','555-5555555','2019-06-30','[212, 312]','Court 2 (12:00 - 13:00)\nCourt 3 (12:00 - 13:00)\n',2,1,'2019-06-29','2019-07-02'),(10,'Tom','555-5555555','2019-06-29','[311, 312, 313]','Court 3 (11:00 - 14:00)\n',3,1,'2019-06-29','2019-07-02'),(11,'Sherly','777-7777777','2019-06-29','[121, 221]','Court 1 (21:00 - 22:00)\nCourt 2 (21:00 - 22:00)\n',2,1,'2019-06-29','2019-07-02'),(12,'Jane','000-0000000','2019-07-01','[112, 212]','Court 1 (12:00 - 13:00)\nCourt 2 (12:00 - 13:00)\n',2,2,'2019-07-02','2019-07-03'),(13,'john','444-4444444','2019-07-02','[112, 212, 213]','Court 1 (12:00 - 13:00)\nCourt 2 (12:00 - 14:00)\n',3,1,'2019-07-02',NULL),(14,'Peter','888-8888888','2019-07-05','[111, 211]','Court 1 (11:00 - 12:00)\nCourt 2 (11:00 - 12:00)\n',2,2,'2019-07-05',NULL),(15,'Tony','999-9999999','2019-07-05','[314]','Court 3 (14:00 - 15:00)\n',1,1,'2019-07-05',NULL),(16,'Steve','666-666666','2019-07-05','[117, 118]','Court 1 (17:00 - 19:00)\n',2,1,'2019-07-05',NULL);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prices`
--

DROP TABLE IF EXISTS `prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prices` (
  `IT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ITEM_NAME` varchar(50) NOT NULL,
  `PRICE` double NOT NULL,
  `DATE_CREATED` date NOT NULL,
  `DATE_MODIFIED` date DEFAULT NULL,
  PRIMARY KEY (`IT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prices`
--

LOCK TABLES `prices` WRITE;
/*!40000 ALTER TABLE `prices` DISABLE KEYS */;
INSERT INTO `prices` VALUES (1,'Court Fee',12,'2019-07-01','2019-07-01'),(2,'Others',0,'2019-07-01',NULL),(3,'Shuttlecock (Barrel)',12,'2019-07-01','2019-07-01'),(4,'Shuttlecock (Pieces)',5,'2019-07-01',NULL),(5,'Racquet String ',10,'2019-07-01',NULL),(6,'Racquet Grip',10,'2019-07-01','2019-07-01'),(7,'Beverage (Tin)',1.5,'2019-07-01',NULL),(8,'Beverage (Bottle)',4,'2019-07-01',NULL),(9,'Mineral Water (500ml)',1.7,'2019-07-01','2019-07-01'),(10,'Mineral Water (1.5L)',5,'2019-07-01','2019-07-01');
/*!40000 ALTER TABLE `prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sales` (
  `SALE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BK_ID` int(11) DEFAULT NULL,
  `COURTFEE_TOTAL` double DEFAULT NULL,
  `ITEM_LIST` varchar(500) DEFAULT NULL,
  `ITEM_TOTAL` double DEFAULT NULL,
  `GRAND_TOTAL` double NOT NULL,
  `MONEY_RECEIVED` double NOT NULL,
  `BALANCE` double NOT NULL,
  `US_ID` int(11) NOT NULL,
  `DATE_CREATED` datetime NOT NULL,
  `DATE_MODIFIED` datetime DEFAULT NULL,
  PRIMARY KEY (`SALE_ID`),
  KEY `FK_BKID` (`BK_ID`),
  KEY `FK_USERID` (`US_ID`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`BK_ID`) REFERENCES `bookings` (`BK_ID`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`US_ID`) REFERENCES `users` (`US_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,1,36,'Shuttlecock (Barrel) x 1 = RM12.00\n',12,48,50,2,2,'2019-07-04 00:26:06',NULL),(2,2,72,NULL,NULL,72,80.5,8.5,2,'2019-07-04 00:26:21',NULL),(3,3,36,'Others x 1 = RM12.45\n',12.45,48.45,50,1.55,2,'2019-07-04 00:26:50',NULL),(4,NULL,NULL,'Racquet String  x 1 = RM10.00\n',10,10,10,0,2,'2019-07-04 00:27:02',NULL),(5,5,12,'Mineral Water (500ml) x 2 = RM3.40\n',3.4,15.4,17,1.6,1,'2019-07-05 16:29:48',NULL),(6,5,12,'Mineral Water (500ml) x 1 = RM1.70\n',1.7,13.7,15,1.3,1,'2019-07-05 16:39:39',NULL),(7,NULL,NULL,'Others x 1 = RM12.38\n',12.38,12.38,14,1.62,1,'2019-07-05 16:42:43',NULL);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `US_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(30) NOT NULL,
  `PASSWORD` blob NOT NULL,
  `SALT` blob NOT NULL,
  `FIRSTNAME` varchar(30) NOT NULL,
  `LASTNAME` varchar(30) NOT NULL,
  `EMAIL` varchar(30) NOT NULL,
  `POSITION` varchar(10) NOT NULL,
  `DATE_CREATED` date NOT NULL,
  PRIMARY KEY (`US_ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin',_binary 'ø#n•\àÀ<òg¢g|H\Ü ',_binary 'pYÑ†0§‚\ÈG&ðPX±„','Administrator','BCMS','admin@bcms.com','MANAGER','2019-06-25'),(2,'staff1',_binary '\Äñ¿û%?’ók5‘¿bºW',_binary 'ô\Ézˆ=6Š½\ß\èh²¯“','Staff1','BCMS','staff1@bcms.com','STAFF','2019-06-25'),(3,'staff2',_binary '–n\Û#\Ê]i²I -Md¨%',_binary '¡\\\0C\Ëj³•e±¥','Staff2','S2','staff2@bcms.com','STAFF','2019-07-05');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-12 21:56:35
