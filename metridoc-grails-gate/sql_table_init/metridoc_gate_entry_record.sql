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
-- Table structure for table `gate_entry_record`
--

DROP TABLE IF EXISTS `gate_entry_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gate_entry_record` (
  `entry_id` int(11) NOT NULL,
  `entry_datetime` datetime DEFAULT NULL,
  `door` int(11) DEFAULT NULL,
  `affiliation` int(11) DEFAULT NULL,
  `center` int(11) DEFAULT NULL,
  `USC` int(11) DEFAULT NULL,
  `department` int(11) DEFAULT NULL,
  PRIMARY KEY (`entry_id`),
  KEY `affiliation` (`affiliation`),
  KEY `center` (`center`),
  KEY `USC` (`USC`),
  KEY `department` (`department`),
  KEY `gate_entry_record_ibfk_1_idx` (`door`),
  CONSTRAINT `gate_entry_record_ibfk_1` FOREIGN KEY (`door`) REFERENCES `gate_door` (`door_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gate_entry_record_ibfk_2` FOREIGN KEY (`affiliation`) REFERENCES `gate_affiliation` (`affiliation_id`),
  CONSTRAINT `gate_entry_record_ibfk_3` FOREIGN KEY (`center`) REFERENCES `gate_center` (`center_id`),
  CONSTRAINT `gate_entry_record_ibfk_4` FOREIGN KEY (`USC`) REFERENCES `gate_USC` (`USC_id`),
  CONSTRAINT `gate_entry_record_ibfk_5` FOREIGN KEY (`department`) REFERENCES `gate_department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gate_entry_record`
--

LOCK TABLES `gate_entry_record` WRITE;
/*!40000 ALTER TABLE `gate_entry_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `gate_entry_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-10 16:34:39
