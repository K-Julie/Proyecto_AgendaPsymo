-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: agenda_psymo
-- ------------------------------------------------------
-- Server version	8.0.3-rc-log

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
-- Table structure for table `citas`
--

DROP TABLE IF EXISTS `citas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citas` (
  `id_cita` int(11) NOT NULL AUTO_INCREMENT,
  `id_consultante` int(11) NOT NULL,
  `id_profesional` int(11) NOT NULL,
  `especialidad` enum('psicologia','psiquiatria') NOT NULL,
  `tipo_servicio` enum('primera_vez','control') NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `estado` enum('agendada','cancelada') NOT NULL DEFAULT 'agendada',
  PRIMARY KEY (`id_cita`),
  UNIQUE KEY `id_profesional` (`id_profesional`,`fecha_hora`),
  KEY `id_consultante` (`id_consultante`),
  CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`id_consultante`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`id_profesional`) REFERENCES `profesionales` (`id_profesional`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citas`
--

LOCK TABLES `citas` WRITE;
/*!40000 ALTER TABLE `citas` DISABLE KEYS */;
INSERT INTO `citas` VALUES (1,2,3,'psicologia','primera_vez','2025-09-13 14:00:00','cancelada'),(3,2,3,'psicologia','primera_vez','2025-10-02 10:00:00','agendada'),(4,2,3,'psicologia','control','2025-09-23 16:00:00','cancelada'),(5,2,3,'psicologia','control','2025-10-20 15:00:00','cancelada'),(6,2,8,'psiquiatria','primera_vez','2025-12-18 09:00:00','agendada'),(8,5,4,'psicologia','primera_vez','2025-12-17 14:00:00','agendada'),(10,5,4,'psicologia','control','2025-12-26 16:00:00','cancelada'),(11,5,10,'psiquiatria','primera_vez','2025-11-29 09:00:00','cancelada'),(12,6,6,'psicologia','primera_vez','2025-12-04 15:00:00','agendada'),(13,8,4,'psicologia','primera_vez','2025-12-17 08:00:00','agendada'),(14,8,10,'psiquiatria','primera_vez','2025-12-20 08:00:00','cancelada'),(15,15,8,'psiquiatria','primera_vez','2025-12-23 16:00:00','agendada'),(16,19,8,'psiquiatria','primera_vez','2025-12-06 11:00:00','agendada'),(17,8,4,'psicologia','control','2025-12-26 11:00:00','agendada'),(18,16,1,'psicologia','primera_vez','2025-11-28 08:00:00','agendada'),(19,2,1,'psicologia','primera_vez','2025-11-15 08:00:00','agendada');
/*!40000 ALTER TABLE `citas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesionales`
--

DROP TABLE IF EXISTS `profesionales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesionales` (
  `id_profesional` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_documento` varchar(20) NOT NULL,
  `numero_documento` varchar(30) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `especialidad` varchar(50) NOT NULL,
  `consultorio` varchar(10) NOT NULL,
  PRIMARY KEY (`id_profesional`),
  UNIQUE KEY `numero_documento` (`numero_documento`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesionales`
--

LOCK TABLES `profesionales` WRITE;
/*!40000 ALTER TABLE `profesionales` DISABLE KEYS */;
INSERT INTO `profesionales` VALUES (1,'CC','1043127117','Carol Juliana','Ospina Puentes','Psicologia','101'),(3,'CC','1223137877','Adriana Lucia','Hinestroza Soler','Psicologia','102'),(4,'CC','1082337102','Guillermo','Zamora Leon','Psicologia','103'),(6,'CC','82337102','Miguel','Zambrano Reyes','Psicologia','104'),(8,'CC','1034587665','Anderson','Jara Yate','Psiquiatria','201'),(10,'CC','52134909','Vanessa Andrea','Hernandez Mora','Psiquiatria','202'),(11,'CC','1287745097','Samara','Oliveros Lopez','Psiquiatria','203'),(12,'CC','53918655','Santiago','Linares Muñoz','Psiquiatria','204');
/*!40000 ALTER TABLE `profesionales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_documento` varchar(20) NOT NULL,
  `numero_documento` varchar(30) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol` enum('consultante','administrador') NOT NULL,
  `correo` varchar(100) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `numero_documento` (`numero_documento`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'CC','6324210','Katherine','Neira Suarez','123456','administrador','katha33@gmail.com','3128653729','1990-01-23'),(2,'CC','2930029','Luz Mariana','Gomez Ardila','345678','consultante','luzmariana31@gmail.com','3129653729','1983-03-12'),(5,'CC','5438290','Ernesto','Contreras Bonilla','contre23','consultante','ernesto11b@hotmail.com','3168123349','1968-03-09'),(6,'CC','1010174788','Adriana Lucia','Castellanos Molano','adrilu22','consultante','lucy22@gmail.com','3129650302','2002-12-22'),(8,'CC','1021935039','Dilan Manuel','Diaz Cruz','dima11','consultante','dmdc10@gmail.com','3171002567','1983-03-27'),(9,'CC','1032145459','Daniela','Acosta Robayo','dani0211','consultante','danniela_ar@gmail.com','3128556464','1992-11-02'),(10,'CC','58434111','Wilmar','Linares Ramirez','wilmar22','consultante','wilmarlinares22@gmail.com','3107632048','1978-03-22'),(12,'CC','5639381','Martina ','Sanchez Lopez','mar1506','consultante','martinasanchezz@gmail.com','3194096189','1971-06-15'),(15,'CC','1022708311','Emely Mariana','Quintero Melo','eme123','consultante','ememq@gmail.com','3202506782','2002-07-02'),(16,'CC','1021378601','Ana Paula ','Lujan Ordoñez','anapau1','consultante','anapaulujan@gmail.com','3115902313','2004-04-15'),(17,'CC','1010145002','Jefferson ','Quintero Rodriguez','jeffer','consultante','jefferqr@outlook.com','3162279700','1999-12-28'),(19,'CE','1012367532','Diego','Blanco Castro','diegobl','consultante','castrod4@gmail.com','3190562134','1989-05-13'),(21,'CC','1020098296','Gissele ','Duarte Alonso','asfafdaf','consultante','gisse_le95@hotmail.com','3208873454','1995-05-17'),(23,'CC','1022368322','Muzan Jackson','Lopez Becerra','kimetsu','consultante','muzan@gmail.com','3203123665','2000-06-01');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-01 12:26:52
