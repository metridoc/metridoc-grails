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
-- Table structure for table `gate_door`
--

DROP TABLE IF EXISTS `gate_door`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gate_door` (
  `door_id` int(11) NOT NULL,
  `door_name` varchar(60) DEFAULT NULL,
  `short_name` varchar(60) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`door_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gate_door`
--

LOCK TABLES `gate_door` WRITE;
/*!40000 ALTER TABLE `gate_door` DISABLE KEYS */;
INSERT INTO `gate_door` VALUES (0,'VAN PELT LIBRARY EMPLOYEE REAR DOOR_ *VPL','Van Pelt Library Staff Rear Door',0,0,''),(1,'VAN PELT LIBRARY TURN1_ *VPL','Van Pelt Library Turnstyle 1',0,0,''),(2,'VAN PELT LIBRARY TURN2_ *VPL','Van Pelt Library Turnstyle 2',0,0,''),(3,'VAN PELT LIBRARY ADA DOOR_ *VPL','Van Pelt Library ADA Door',0,0,''),(4,'NBC MYRIN BLDG LIBRARY ENT DR 9153-46889 *NBC','NBC Myrin Building Library Door',0,0,''),(5,'FURNESS TURN 170-6416 *FUR','Furness Library Turnstyle',0,0,'');
/*!40000 ALTER TABLE `gate_door` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-10 16:31:15
