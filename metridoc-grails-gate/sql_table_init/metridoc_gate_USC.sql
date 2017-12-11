-- MySQL dump 10.13  Distrib 5.7.19, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: metridoc
-- ------------------------------------------------------
-- Server version	5.7.19-0ubuntu0.16.04.1

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
-- Table structure for table `gate_USC`
--

DROP TABLE IF EXISTS `gate_USC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gate_USC` (
  `USC_id` int(11) NOT NULL,
  `USC_name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`USC_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gate_USC`
--

LOCK TABLES `gate_USC` WRITE;
/*!40000 ALTER TABLE `gate_USC` DISABLE KEYS */;
INSERT INTO `gate_USC` VALUES (0,'Staff'),(1,'Temporary'),(2,'Contractor'),(3,'Student'),(4,'Penn Alumni'),(5,'Visiting Scholar'),(6,'Visiting Student'),(7,'English Program'),(8,'Spouse / Partner'),(9,'Faculty'),(10,'UPHS (paid employees)'),(11,'Visiting Faculty'),(12,'GUEST'),(13,'Courtesy Appointment'),(14,'Student on Leave'),(15,'Howard Hughes'),(16,'Emeritus Faculty'),(17,'Public Safety contractor'),(18,'Retired Staff'),(19,'N/A'),(20,'Wistar Staff'),(21,'Chaplain\'s Office'),(22,'Consultant'),(23,'Adjunct Faculty'),(24,'Child'),(25,'Overseer'),(26,'Conference'),(27,'Retired Faculty'),(28,'UPHS (paid employee)'),(29,'University Trustee'),(30,'UPHS (non-paid employees)');
/*!40000 ALTER TABLE `gate_USC` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-10 16:31:16
