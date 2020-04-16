-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: capacitacion-prestamos
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

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
-- Table structure for table `lineas`
--

DROP TABLE IF EXISTS `lineas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lineas` (
  `linea_id` int(11) NOT NULL,
  `nombre` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `sistema_amortizacion` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `tasa_tipo` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `tasa_modulo` int(11) NOT NULL,
  `tasa_min` decimal(18,2) NOT NULL,
  `tasa_max` decimal(18,2) NOT NULL,
  `amortizacion_periodo` int(11) NOT NULL,
  `amortizacion_unidad` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `cuotas_min` int(11) NOT NULL,
  `cuotas_max` int(11) NOT NULL,
  `capital_min` decimal(18,2) NOT NULL,
  `capital_max` decimal(18,2) NOT NULL,
  `edad_maxima` int(11) NOT NULL,
  `fecha_alta` date NOT NULL,
  `usuario_tipo_documento_id` int(11) NOT NULL,
  `usuario_numero_documento` bigint(20) NOT NULL,
  PRIMARY KEY (`linea_id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_lineas_usuarios_idx` (`usuario_tipo_documento_id`,`usuario_numero_documento`),
  CONSTRAINT `fk_lineas_usuarios` FOREIGN KEY (`usuario_tipo_documento_id`, `usuario_numero_documento`) REFERENCES `usuarios` (`id_tipodocumento`, `numero_documento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `localidades`
--

DROP TABLE IF EXISTS `localidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localidades` (
  `id_localidad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(300) NOT NULL,
  `id_provincia` int(11) NOT NULL,
  `codigo_postal` varchar(10) NOT NULL,
  PRIMARY KEY (`id_localidad`),
  UNIQUE KEY `localidades_nombre_id_provincia_UNIQUE` (`nombre`,`id_provincia`),
  KEY `fk_provincias_localidades_idx` (`id_provincia`),
  CONSTRAINT `FK9hk6pt1dlxrkxixygbw0v0mrq` FOREIGN KEY (`id_provincia`) REFERENCES `provincias` (`id_provincia`),
  CONSTRAINT `fk_provincias_localidades` FOREIGN KEY (`id_provincia`) REFERENCES `provincias` (`id_provincia`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personas` (
  `id_tipodocumento` int(11) NOT NULL,
  `numero_documento` bigint(20) NOT NULL,
  `nombre_apellido` varchar(400) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `genero` char(1) NOT NULL,
  `es_argentino` tinyint(1) NOT NULL,
  `correo_electronico` varchar(300) DEFAULT NULL,
  `foto_cara` blob,
  `id_localidad` int(11) NOT NULL,
  `codigo_postal` varchar(10) NOT NULL,
  PRIMARY KEY (`id_tipodocumento`,`numero_documento`),
  UNIQUE KEY `nombre_apellido_UNIQUE` (`nombre_apellido`),
  UNIQUE KEY `UK_j9gd7x5woiskn2o5q95lgxl97` (`nombre_apellido`),
  KEY `fk_localidades_id_localidad_idx` (`id_localidad`),
  CONSTRAINT `fk_localidades_id_localidad` FOREIGN KEY (`id_localidad`) REFERENCES `localidades` (`id_localidad`),
  CONSTRAINT `fk_personas_localidades` FOREIGN KEY (`id_localidad`) REFERENCES `localidades` (`id_localidad`),
  CONSTRAINT `fk_personas_tipos_documentos` FOREIGN KEY (`id_tipodocumento`) REFERENCES `tipos_documentos` (`id_tipodocumento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personas_domicilios`
--

DROP TABLE IF EXISTS `personas_domicilios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personas_domicilios` (
  `id_tipodocumento` int(11) NOT NULL,
  `numero_documento` bigint(20) NOT NULL,
  `id_domicilio` int(11) NOT NULL,
  `nombre_domicilio` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `id_localidad` int(11) NOT NULL,
  `es_principal` tinyint(1) NOT NULL,
  `fecha_ingreso` date NOT NULL,
  PRIMARY KEY (`id_tipodocumento`,`numero_documento`,`id_domicilio`),
  KEY `fk_personas_domicilios_localidades_idx` (`id_localidad`),
  CONSTRAINT `fk_personas_domicilios_localidades` FOREIGN KEY (`id_localidad`) REFERENCES `localidades` (`id_localidad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_personas_domicilios_personas` FOREIGN KEY (`id_tipodocumento`, `numero_documento`) REFERENCES `personas` (`id_tipodocumento`, `numero_documento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prestamos` (
  `prestamo_id` bigint(20) NOT NULL,
  `id_tipodocumento` int(11) NOT NULL,
  `numero_documento` bigint(20) NOT NULL,
  `linea_id` int(11) NOT NULL,
  `sistema_amortizacion` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `fecha_alta` date NOT NULL,
  `fecha_primer_vto` date NOT NULL,
  `tasa_efectiva` decimal(19,2) NOT NULL,
  `tasa_modulo` int(11) NOT NULL,
  `amortizacion_periodo` int(11) NOT NULL,
  `amortizacion_unidad` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `capital_prestado` decimal(19,2) NOT NULL,
  `total_intereses` decimal(19,2) NOT NULL,
  `total_cuotas` int(11) NOT NULL,
  `usuario_tipo_documento_id` int(11) NOT NULL,
  `usuario_numero_documento` bigint(20) NOT NULL,
  PRIMARY KEY (`prestamo_id`),
  KEY `fk_prestamos_lineas_idx` (`linea_id`),
  KEY `fk_prestamos_usuarios_idx` (`usuario_tipo_documento_id`,`usuario_numero_documento`),
  KEY `fk_prestamos_personas_idx` (`id_tipodocumento`,`numero_documento`),
  CONSTRAINT `fk_prestamos_lineas` FOREIGN KEY (`linea_id`) REFERENCES `lineas` (`linea_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prestamos_personas` FOREIGN KEY (`id_tipodocumento`, `numero_documento`) REFERENCES `personas` (`id_tipodocumento`, `numero_documento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prestamos_usuarios` FOREIGN KEY (`usuario_tipo_documento_id`, `usuario_numero_documento`) REFERENCES `usuarios` (`id_tipodocumento`, `numero_documento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prestamos_cuotas`
--

DROP TABLE IF EXISTS `prestamos_cuotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prestamos_cuotas` (
  `prestamo_id` bigint(20) NOT NULL,
  `nro_cuota` int(11) NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `importe_capital` decimal(19,2) NOT NULL,
  `importe_intereses` decimal(19,2) NOT NULL,
  `importe_total` decimal(19,2) NOT NULL,
  `saldo_capital` decimal(19,2) NOT NULL,
  PRIMARY KEY (`prestamo_id`,`nro_cuota`),
  CONSTRAINT `fk_prestamos_prestamos_cuotas` FOREIGN KEY (`prestamo_id`) REFERENCES `prestamos` (`prestamo_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provincias`
--

DROP TABLE IF EXISTS `provincias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provincias` (
  `id_provincia` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(400) NOT NULL,
  `region` char(3) NOT NULL,
  PRIMARY KEY (`id_provincia`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `UK_otj7kmq4jxw8xre8265k3tvxj` (`nombre`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipos_documentos`
--

DROP TABLE IF EXISTS `tipos_documentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_documentos` (
  `id_tipodocumento` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) NOT NULL,
  `abreviatura` varchar(5) NOT NULL,
  `validar_como_cuit` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_tipodocumento`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `UK_6vovq0mrq287touvd8y8j36xb` (`abreviatura`),
  UNIQUE KEY `UK_pfokmuvqnms483ruwtmddiwbw` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id_tipodocumento` int(11) NOT NULL,
  `numero_documento` bigint(20) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `hashed_pwd` varchar(200) NOT NULL,
  PRIMARY KEY (`id_tipodocumento`,`numero_documento`),
  UNIQUE KEY `nombre_usuario_UNIQUE` (`nombre_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-16 14:02:55
