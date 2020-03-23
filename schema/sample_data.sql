DROP DATABASE IF EXISTS secure_banking_system;

CREATE DATABASE secure_banking_system;

USE secure_banking_system;

CREATE TABLE `secure_banking_system`.`user` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  status INT NOT NULL,
  incorrect_attempts INT DEFAULT 0,
  created_date DATETIME DEFAULT NOW(),
  modified_date DATETIME DEFAULT NOW(),
  role VARCHAR(100)
);

CREATE TABLE `secure_banking_system`.`user_details` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  address1 VARCHAR(255) NOT NULL,
  address2 VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  province VARCHAR(255) NOT NULL,
  zip INT NOT NULL,
  date_of_birth DATETIME NOT NULL,
  ssn VARCHAR(15) NOT NULL UNIQUE,
  question_1 VARCHAR(255) NOT NULL,
  question_2 VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(id) ON DELETE CASCADE
);

CREATE TABLE `secure_banking_system`.`account` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  account_number VARCHAR(255) NOT NULL UNIQUE,
  account_type VARCHAR(100) NOT NULL,
  current_balance DECIMAL(30, 5) DEFAULT 0.0,
  created_date DATETIME NOT NULL DEFAULT NOW(),
  status BOOLEAN NOT NULL,
  interest DECIMAL(10, 5) DEFAULT 0.0,
  approval_date DATETIME,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(id)
);

CREATE TABLE `secure_banking_system`.`transaction` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  approval_status BOOLEAN NOT NULL,
  amount DECIMAL(10, 5),
  is_critical_transaction BOOLEAN NOT NULL,
  requested_date DATETIME NOT NULL DEFAULT NOW(),
  decision_date DATETIME DEFAULT NULL,
  from_account VARCHAR(255),
  to_account VARCHAR(255),
  transaction_type VARCHAR(100) NOT NULL,
  request_assigned_to INT DEFAULT NULL,
  approval_level_required VARCHAR(100) NOT NULL,
  level_1_approval BOOLEAN DEFAULT NULL,
  level_2_approval BOOLEAN DEFAULT NULL,
  approved BOOLEAN DEFAULT NULL, /* Can be approved by merchant or bank employee depending on type of request */
  FOREIGN KEY (request_assigned_to) REFERENCES `secure_banking_system`.`user`(id),
  FOREIGN KEY (from_account) REFERENCES `secure_banking_system`.`account`(account_number),
  FOREIGN KEY (to_account) REFERENCES `secure_banking_system`.`account`(account_number)
);

CREATE TABLE `secure_banking_system`.`appointment` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_user_id INT NOT NULL,
  assigned_to_user_id INT NOT NULL,
  created_date DATETIME NOT NULL DEFAULT NOW(),
  appointment_status VARCHAR(25) NOT NULL,
  FOREIGN KEY (appointment_user_id) REFERENCES `secure_banking_system`.`user`(id),
  FOREIGN KEY (assigned_to_user_id) REFERENCES `secure_banking_system`.`user`(id)
);

CREATE TABLE `secure_banking_system`.`login_history` (
  id INT PRIMARY KEY AUTO_INCREMENT, 
  user_id INT NOT NULL,
  logged_in DATETIME NOT NULL DEFAULT NOW(),
  logged_out DATETIME DEFAULT NULL,
  ip_address VARCHAR(25) NOT NULL,
  device_type VARCHAR(25) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(id)
);




-- Generation time: Thu, 19 Mar 2020 07:54:02 +0000
-- Host: mysql.hostinger.ro
-- DB name: u574849695_22
/*!40030 SET NAMES UTF8 */;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*
abernathy.donato (customer)
reanna.dibbert (merchant)
yrunolfsson (tier2)
ifay (admin)
kirlin.jaqueline (customer)
abbey.haley tier1)
lmorar (customer)

All have password: 1234
*/


INSERT INTO `user` VALUES ('1','abernathy.donato','$2a$10$KERumbzDDN4phCp8ywE.IuSv2WesIQWAFWMPb3MZPytzYTyA/P35S','0','9','2013-07-23 10:39:46','1984-05-15 11:55:32','customer'),
('2','reanna.dibbert','$2a$10$KERumbzDDN4phCp8ywE.IuSv2WesIQWAFWMPb3MZPytzYTyA/P35S','2','1','1977-08-26 12:32:30','1972-02-23 16:40:42','merchant'),
('3','yrunolfsson','$2a$10$KERumbzDDN4phCp8ywE.IuSv2WesIQWAFWMPb3MZPytzYTyA/P35S','0','0','1993-11-29 07:47:43','1997-10-18 07:11:45','tier2'),
('4','ifay','$2a$10$KERumbzDDN4phCp8ywE.IuSv2WesIQWAFWMPb3MZPytzYTyA/P35S','0','8','2015-06-26 23:51:29','2007-02-03 15:02:27','admin'),
('5','kristoffer.leuschke','64d73b08f103c391ee1d2930ed3c037357f68a184546f8046b977ca48b9f','2','7','2005-04-07 13:23:26','2019-07-19 04:19:41','customer'),
('6','vicky04','8737bc7dad85fa2760bffd3758175613366e72173b96caab8b34e666b61a','1','2','2008-05-16 06:54:21','1976-09-17 03:56:00','admin'),
('7','arlene21','11a76b4e37d1692379b58d7d2a8fc25f5d94bdeef56bf220829f387ac011','1','5','1970-08-16 13:46:58','1994-07-08 02:33:31','admin'),
('8','kirlin.jaqueline','$2a$10$KERumbzDDN4phCp8ywE.IuSv2WesIQWAFWMPb3MZPytzYTyA/P35S','2','7','2014-12-07 04:44:21','2002-02-08 00:41:09','customer'),
('9','abbey.haley','$2a$10$KERumbzDDN4phCp8ywE.IuSv2WesIQWAFWMPb3MZPytzYTyA/P35S','1','3','2002-07-21 03:06:38','2018-02-14 03:17:22','tier1'),
('10','lewis.bailey','4b11546c65e164d5d92cd8bf8d29325220894c7fdc94a0cb165bd60cdca1','2','6','2003-06-07 08:36:27','2005-11-26 00:11:59','admin'),
('11','pmcglynn','e9f0c67709c438171a14de04ea509b20c0b7e5d0e76960a7ef8f99b27bd4','1','7','2000-08-04 15:43:26','1986-05-04 04:10:56','merchant'),
('12','littel.laila','4dd8de725d7b31b24a736bce03bcbaa681cdc45b1520134f38d0bacb960b','1','4','1998-03-02 10:44:15','1982-11-09 21:17:10','tier2'),
('13','bednar.ben','2dca53b2a3fd7ee0d559bcac5fc8422741855dd128840d96c9e6d7276e41','1','4','1988-07-15 07:09:03','1993-10-04 08:37:51','merchant'),
('14','quigley.reece','48eaedca05da43d961f2bbe72ccafc633dad8b1130b63eb2b70c770a6fdf','2','2','2004-07-03 17:14:10','1988-02-26 11:24:01','tier1'),
('15','june38','0693ba0d776f1af90441fb9f00326554840b0a119859653490d055199e11','2','3','1990-03-27 00:08:45','1974-09-18 23:53:44','merchant'),
('16','lmorar','$2a$10$KERumbzDDN4phCp8ywE.IuSv2WesIQWAFWMPb3MZPytzYTyA/P35S','1','1','1978-09-25 23:33:54','1990-08-13 02:51:03','customer'),
('17','gibson.geo','ad59b14fc4a0517823a29e950c2fcd254f2cf9e725d90d251ea0e874c290','1','0','1984-01-29 00:22:45','1985-06-23 19:21:26','tier1'),
('18','grant.rahul','f9c66ec7bc5e70df167b435e092a05b21f46a5fbf8ffb81b906af3f63946','2','0','1970-12-28 00:25:37','2020-02-27 11:21:30','customer'),
('19','camille91','19c1788c3262b0871b14be1ff603e77cb2ca1a547869befd04a4bbff9e07','1','9','1988-07-05 07:42:50','2014-12-28 02:50:00','merchant'),
('20','elvis33','2ecbc853944cde8cfc819f61b7922b05a2b10905591571aa04c5f137118c','1','8','2002-11-14 03:33:55','2019-08-11 03:53:10','tier1'),
('21','clark.mosciski','7eeea0252332c29f33415892ab2ea912f1e89f82b991bf138923bd732c40','2','3','2006-05-07 20:52:19','1991-09-14 00:48:18','merchant'),
('22','ebony32','37e0f6ab23178d714c80bd8fb5930892ab4621a2621f819762f8b5e5b468','0','5','2009-05-22 05:36:38','1983-12-18 05:01:56','admin'),
('23','vweimann','2fcc8b2ca343fae8ef91f5d9862f623faecf3303800a3c36a72ba3f05cba','1','2','1980-10-14 15:23:59','1985-11-13 09:49:42','merchant'),
('24','polly38','e9fb3ce7d445e86ac2cecbc7059cba1c0339175421375ae77b60e8d3cdcd','1','5','1976-09-26 06:02:22','1977-10-19 22:38:21','customer'),
('25','raina.ziemann','0fc32c20d84bb535e64cb401c61068d884c26419a26bc86b453d78df0a32','0','3','2007-04-02 19:00:24','1985-04-02 21:42:49','tier2'),
('26','weimann.guadalupe','31f2e046ef9f6d94b2dfd36d5fa87fef1949cae4f85b8d5b96f30cefad61','0','0','1981-07-29 03:29:29','1986-07-11 20:10:29','merchant'),
('27','bailey27','9af182b5b4eb596064de924ed09ff1009005782874ba6bb88eea033dc541','0','6','1982-11-22 04:24:43','1981-05-07 03:17:48','customer'),
('28','yost.saul','45bed7577ea09e351da46ad766ecb6740380f8b47d5fe8e6f9c0e67f10dc','1','9','2001-05-26 13:07:12','1996-08-21 13:24:17','tier1'),
('29','spinka.alena','4b2327336202c85731d61a97f1dc642cb261eb5bb24292b4cd4d23393f24','1','1','1980-02-17 01:44:32','1974-07-15 17:40:26','tier2'),
('30','keebler.cortney','e9c1726bffbefac336fa116e031de1852143f4c7f5db91839635e275611c','1','8','1989-03-03 23:48:35','2000-08-30 06:27:24','admin'),
('31','maye.prohaska','de3a8b4e1674335dec0881bca638d2a6ea80f378ca608b0081146b09a27f','2','8','1974-07-06 03:53:39','2000-04-11 08:42:36','customer'),
('32','immanuel60','722c06f4f4fd7722c30f741d1c944e6f4bee5f0ad50b519065e9314cf750','1','5','2001-04-04 14:50:43','2003-11-26 18:50:19','admin'),
('33','rohan.luisa','e01880d6feeb26c6fba8d13bdc3e08585a61ce075c08b2137b64221bff49','1','5','2008-10-12 14:46:55','1978-06-28 03:41:12','merchant'),
('34','pparker','ea7d09e4d833449e480a2b825d54bb67d4074ea4b1f7032088f4fed73bd0','1','5','2012-06-22 00:01:18','1992-12-09 17:28:43','tier2'),
('35','guido61','9ba059991ffe589a3427b537908c09e23c9a99c7deab244bc526aab95af1','0','0','1984-06-18 01:29:18','2000-05-09 18:14:05','admin'),
('36','willow.gerlach','b80e585bab6c17a35e018af6971d15ce4854b4a772d82a980817d018ba81','1','1','2005-10-23 22:40:17','1970-02-02 12:34:49','merchant'),
('37','jada93','b28875fdd32aa6f0388581600c4fd811d29f095ed917bf492e5d7cc8c699','1','8','1992-07-13 21:47:43','2007-03-31 06:49:13','tier2'),
('38','santos.osinski','907b1bf49eb22ee7fbe8bc2a40303b6e8393a917c84c115d73e7b5363a12','1','5','2011-10-31 14:40:12','2016-09-29 06:13:27','admin'),
('39','joesph20','a00afa7e98ecc4252e453f0dbb30e76b46589feba8a09bccbadda0e52e4d','2','7','1972-03-16 15:00:29','2002-02-15 20:11:49','customer'),
('40','rkihn','7f02765a261eac717db477dd6b0f1dc34441caf14bb64327355a0c9a81c5','1','9','2017-12-02 04:17:06','2001-10-14 09:58:04','tier2'),
('41','darrin22','8ba83d80f686d09d24a2a41be8ef8b126f5088a2055d5a1dd6cf869012b6','0','9','2002-01-29 21:41:01','1981-07-17 20:37:55','tier2'),
('42','rosemarie27','99ef348786bfc2497697bbc802f78ebed7126df6f8c132ca95f7a1c34ca2','2','3','1990-06-01 09:31:26','2006-04-25 11:14:59','merchant'),
('43','leannon.fred','b3bf1077c317771dc66778ca8a85cad29ae6c8fd1acad1a88c670ea3be97','1','4','2011-10-26 05:22:57','2012-04-27 11:57:52','customer'),
('44','cjenkins','7ea37bda36ee94468d08728873adb7c225a3ea3ff6291f9209fa72bc1b29','0','4','1994-06-09 07:21:27','2011-03-25 04:56:00','admin'),
('45','leuschke.jodie','150bd44adec1adbbf90b7ec88e4fe34dd764e96a24110a792c4c10a4dbaa','0','9','2007-11-15 05:46:27','1983-10-01 11:39:38','customer'),
('46','ryder30','91f6beb49bcaeb53f694bca33dbd7fb7451fd3916e08fdcc1a26c4b96aa9','1','2','2017-11-14 02:56:38','2010-05-07 17:19:50','tier1'),
('47','kailey.bahringer','a69e3d88174259f45c9508ad253e481239fc6214e5a2e601fbe755d1d2d0','2','3','1979-10-22 22:51:39','2016-10-06 02:07:19','admin'),
('48','dooley.darby','3f4e4a77d2f823cca059dcdb5bf63add77de4d084ee72ae00b57f05de2f8','0','5','2014-02-28 10:41:13','1985-10-13 04:43:09','customer'),
('49','walsh.vilma','4c006c2f1cab77b021f7b0c9eb41cb60b45060099ec232fe99ff4f51707c','2','1','1985-06-08 11:28:45','2005-07-04 18:15:11','tier1'),
('50','gina71','4c5fb965c16941dff3d9f490f199715a13cb514d1c994ceb911eb47a2fee','2','9','1992-03-06 18:06:40','1971-05-03 12:49:19','tier2'),
('51','chase79','1bad95f3fa22f2b39bde82663d9559e813f3b9eb317381f365a3ad4cbf5e','0','8','1999-08-02 17:53:58','2011-07-06 02:50:13','admin'),
('52','kutch.nya','356bc0348390fe9e242222121b6e10f8cd8fea4e320c17da0abbfd52c7a7','0','4','1982-04-19 04:03:00','1994-07-23 07:49:23','tier1'),
('53','torey.marvin','301d58609f23934183185342de404b5ad4b17fc361af13ddfe1074885caa','2','2','1985-12-07 12:32:25','1971-11-27 19:38:48','tier2'),
('54','jay.daniel','4076204738569628351879ad56adaca756e02e6ae39d11c9e48cdc7f3c3c','1','6','2019-04-12 04:43:27','2013-09-23 13:07:30','admin'),
('55','bryana.gaylord','f0239f3bf41c6f630ec6d793dc0f941f17686b3705b9a5eba8c5bfb65c69','2','5','1975-07-19 00:23:16','2007-10-08 18:36:23','tier2'),
('56','edyth.beer','43a10be3f6467e8fa35407e25336675dd22cd0bc74f2ebc4d2e559e3900a','0','0','1984-05-03 19:10:26','2007-04-19 04:35:04','merchant'),
('57','reinger.kareem','6911e3507694ecc198465b270cfa819441c159d3f65b2bd1efa5047a380c','2','0','1993-04-11 22:49:40','2017-11-23 16:50:10','customer'),
('58','schaefer.else','cc49d9895ff7076a1d0bba798cf9457513fb9477ecc3f47905bb8a463f24','2','6','1977-06-09 10:34:55','1995-04-16 18:48:50','tier2'),
('59','cmitchell','380aebcd2184ec2687d1b2fd3258d2f0520b60a92dbf73745ddcbdd00da4','0','4','1979-07-04 10:43:08','1980-11-28 09:40:38','admin'),
('60','mmcglynn','412c31a262664e728b783038e271bc9860cc2aaa1c2fbea1940539700d5d','2','1','1977-10-25 05:32:07','2002-02-07 00:13:12','tier1'),
('61','cathy.turcotte','d2154c26442335c16b2fe3dbb361fc52ea390429a79cadbc8cdfaf2b4944','1','4','2012-08-20 09:34:07','1973-01-10 10:53:51','tier1'),
('62','bahringer.norval','9b76aef8f794663af1e76c79d0b455aa56002da76ed8e2cfa2cdcf32caa6','2','1','1994-01-04 15:03:54','1972-11-29 19:18:31','customer'),
('63','yost.ara','e53e51756f30bcd4ea859f34b415efcc1f8cc9d4ed207ba00a9f17325a29','0','8','1985-03-26 07:09:39','2008-06-11 10:46:36','merchant'),
('64','bruen.max','41aeccfb08356af59925e78cdc21579c4cf27897fdda7f551854ad9afbdc','1','2','1993-07-24 02:20:51','2016-02-13 21:39:18','customer'),
('65','kuvalis.lori','d2c5d4023174ab558394ae62ebf3d895a95b2e3149541c4249438f816a34','0','6','1980-10-18 20:16:29','1992-12-10 07:26:47','admin'),
('66','katelin.terry','5fe39d251906a37b2483a0931050562e704e8d5c71602f42d7aa3c8ba247','0','1','1975-07-16 09:16:26','2012-12-05 13:15:44','merchant'),
('67','kuhn.erika','6812f6dc50ee9c2a84ccc172465f97f75579da57df1fa0f4fee76af4d997','0','1','2014-12-29 05:49:43','2012-10-16 21:32:58','customer'),
('68','hermiston.maegan','6d1e627f1e4062617d3a1e60ff9b6755f4b9a520a0e4b1ef056856a6c221','1','5','2011-10-13 18:07:40','1994-08-08 14:05:45','merchant'),
('69','jabari23','61f4f2696b916b1800ecd18ff2b42691a47c95e10864eaa00ec03edb98b5','2','2','2012-07-23 01:20:48','1974-09-09 23:50:45','admin'),
('70','wlittel','191ed90b52d271da5da8e76c2ccab4e930ed99f2799dc689c0df0b0a2dac','0','1','1995-12-25 03:42:25','2004-10-14 03:17:43','merchant'),
('71','otto05','51af9dc94394eaba17188e8336c0a6c7d14bfe5bcdcf2c78a50b92c9388b','0','7','2018-09-14 04:01:41','2007-03-12 08:54:16','tier2'),
('72','judd.hayes','fe04805cc75bbd54e3c76eef3dbff1644708bdca1c3882a58a0936561311','0','7','1980-04-15 04:08:16','1976-11-02 12:34:53','merchant'),
('73','garfield41','59a1788df2daf9b3e55592a3534533956ab281f53ed12555ba89f9088e46','0','3','2004-12-23 21:03:47','2015-12-04 20:20:10','tier1'),
('74','aubrey.hoppe','af2bcbb85781451def1eebc143da62389eb336c13148d17210e85cbca981','2','1','2007-12-15 02:17:08','2001-10-16 06:30:13','tier1'),
('75','towne.sammie','f791bee7665c8e2813c601008ea47e0d69bfe865165bce670aa66d21b9e0','0','8','2012-08-26 01:38:03','1992-11-11 14:43:11','customer'),
('76','urogahn','d6d417277ac11c184de1668295f482e253ce9f58bc799b18532377dc76b0','1','2','2004-03-26 12:10:23','2011-08-17 00:51:09','admin'),
('77','lemke.kyleigh','593f2d369f83873c8953ae14f1cf3491600b2123b8cd970b3718f0d3140a','0','6','1996-02-02 17:24:30','1978-03-18 06:18:19','customer'),
('78','greenholt.jedidiah','932b12465c6303fbee18ebb69fca76afd96cce573992c07d009697c2194b','2','1','2012-03-13 07:04:51','2006-08-16 21:55:01','tier2'),
('79','barton.mae','31568945424a3275044fc19d55727bc97b74c1ed774a2569e1f36bf9f5ae','0','0','1993-07-06 00:33:00','1977-09-01 17:37:09','customer'),
('80','herzog.marlon','fef52d253e5c4047827512217560c8534881c0a7d8fa75af25379ad58fd0','2','2','1996-12-30 10:31:27','2007-10-21 20:58:28','tier1'),
('81','trowe','e94a9023f45bf0f685269db8d6162a8255061135666c3f3a2208dab7f1bc','0','9','2003-10-27 14:46:16','1973-11-28 05:38:52','tier1'),
('82','fpfeffer','f1094219a3a3bc69098b9465c2cfc6061fa2157656f80055973e3ff767a5','2','0','2016-11-01 19:50:29','1994-07-15 02:07:26','tier1'),
('83','ymraz','0679c27eb24adeaf15c3c61bca0440470ac739be46c4dc0ef2d7b2b8ba00','2','0','1997-07-31 19:07:30','1987-04-23 16:19:53','tier2'),
('84','hilpert.concepcion','fef720d31f05227a7a32ff7cb03f4f18e534f1cf643e0f26de18c7ff06e1','0','8','2014-05-12 19:00:00','2009-09-15 15:45:03','tier1'),
('85','o\'keefe.gerhard','5bd9e765ab03063f8adceafb37c0299f7bdc36430f3cdef779bcbd0f0a6c','0','4','2010-04-28 13:11:31','1986-06-16 08:20:36','admin'),
('86','pagac.gladys','d1421daf646e0f5bf43fa6370678f3d8446f2b4f0d5c06b7396a87ef28e6','2','1','1987-09-15 20:15:53','2008-06-11 22:21:20','tier2'),
('87','corwin.bennie','e5553a91e5ccf183454c46a916ca97b4112a2cfa77f970767301ef137154','0','0','1981-01-19 14:36:19','2005-03-08 10:31:46','tier1'),
('88','davin08','b03099412e8d0f60d44b9294c69c01b44e19127b81e873ec09ce8ff2b4dc','1','6','2013-11-02 16:14:10','1992-03-04 12:39:20','admin'),
('89','cassin.jaylan','7f531151a7e910e70080e2a3fdee22a443c6cdd18386f304d89c769f7ef2','1','6','1999-07-01 12:24:19','1985-05-20 15:48:47','admin'),
('90','gkreiger','2e5cbc00b78f716192dbc540714e432b3d0bcca06aa762d4a45c593ba72f','1','9','1979-09-08 01:44:25','2017-12-11 17:57:06','customer'),
('91','brooklyn.fritsch','e756544272b639a6c8f3e84d4d6988b228f53bcb51798da19770cf98c8ea','2','1','1989-04-15 02:01:55','1984-07-24 18:31:26','merchant'),
('92','doug.koss','5efaccfc451ab6849f1c3b38a1ce384bda5b902c102feecce675f9965569','1','8','1985-04-18 07:04:20','1986-06-09 21:00:12','merchant'),
('93','hills.jaden','b99cf128021beef9b8d8310df6139c344a7d5e64232e093af8fc1fc57142','2','5','1985-11-11 22:08:12','2010-03-12 01:09:46','merchant'),
('94','domenick29','3fe3648e99e00a83f0ed6328f2143d5adde818e39e63e4dbed0b8c45e79f','2','0','2017-01-07 13:13:48','1987-05-12 11:39:41','customer'),
('95','logan35','a46f5bb35af38005c7ca712e2bd60f9dadd50b90ccbc8d0fc8cc0003ae3e','0','5','2014-01-16 16:37:41','1988-10-04 12:12:48','tier1'),
('96','emmanuel12','9a6cd1eec613611029c89cb4f338ce372935f3b7981bfb7c232862856919','2','6','2011-02-28 11:24:09','1972-11-12 19:31:32','tier1'),
('97','fcruickshank','5424aa9089df790d551d401f46a3f44004867308af2d69667f770495e255','2','7','2002-11-03 15:40:33','2015-09-24 13:13:25','merchant'),
('98','ikshlerin','0c790e6ac09e9416d90d0893da48ce7aef80944a3da78f7fa45b9ba0bdf4','1','7','2016-10-01 19:43:00','1976-02-16 05:12:57','tier1'),
('99','collier.shaina','47417443d85c7a8cf38d99e84fb4c481ac1a6de1d0c1c30058ba9c2a6f79','0','1','1990-11-12 01:55:10','2017-07-28 19:45:12','tier1'),
('100','ruecker.nichole','7568b79f89789a7e622203f5c45c9400377e8f99e7fb11d7014a9fe62dee','1','3','1987-05-12 22:20:02','2017-09-26 15:18:56','merchant'); 




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;








-- Generation time: Thu, 19 Mar 2020 07:55:13 +0000
-- Host: mysql.hostinger.ro
-- DB name: u574849695_22
/*!40030 SET NAMES UTF8 */;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

INSERT INTO `user_details` VALUES ('1','1','Jefferey','et','Hickle','mokuneva@example.org','972-291-2142x21','Suite 969','500 Weissnat Dale','South Leonora','Amara Circles','71218','1989-04-12 20:07:40','538159370997768','Praesentium possimus perferendis quis voluptatem.','Omnis sint eum adipisci molestias.'),
('2','2','Colton','id','Lakin','mann.maryam@example.net','1-508-010-9619x','Apt. 163','4943 Tillman Shore','Boyerstad','Hailey Hill','89901','1979-06-09 13:20:46','544628863582422','Quisquam voluptatem magni qui rem ut.','Dolores porro harum mollitia aut.'),
('3','3','Brandon','ullam','Considine','pouros.loyal@example.org','749-450-6367','Suite 447','1866 Wisoky Lock Suite 761','Gorczanyshire','Lexus Coves','56307','1973-11-19 14:58:46','528625783138940','Quia molestias itaque velit asperiores qui.','Ex enim possimus dolorum magni minima.'),
('4','4','Jed','magnam','Davis','buckridge.coleman@example.com','1-571-189-8216x','Apt. 672','7391 Wilber Park Apt. 941','Schambergerside','Braun Light','87801','1976-03-15 23:34:19','540170927445553','Earum unde magnam neque aut autem.','Blanditiis vel et quo aut deleniti saepe molestiae.'),
('5','5','Elta','sint','Anderson','hammes.brooklyn@example.net','667-185-9762x09','Suite 303','5750 Batz Walk Apt. 934','New Chris','Schulist Mall','83141','1975-06-04 18:58:05','492971012714519','Sint at ex velit quidem omnis consectetur enim est.','Praesentium aut suscipit non modi quisquam.'),
('6','6','Douglas','explicabo','Gutmann','ihahn@example.com','+55(0)622810473','Suite 933','2729 Schiller Stream','Metzshire','Mitchel Gardens','27805','2005-10-30 02:48:32','4539827989399','Minima eum praesentium voluptatem sapiente.','Deleniti non aut pariatur alias sint.'),
('7','7','Elza','voluptates','Rowe','ellsworth72@example.com','1-265-628-5920x','Apt. 847','80899 Camila Courts','South Laurineshire','Kilback Circles','18282','2014-10-16 05:04:57','4485749280860','Rerum error voluptatem at porro.','Harum non nobis est sunt sint.'),
('8','8','Brionna','deleniti','Rempel','antonetta31@example.com','252-772-2176','Apt. 155','348 Pfannerstill Village Suite 240','New Marjolaine','Josue Keys','94051','1984-04-25 05:55:08','341629304006292','Rerum quasi nisi aspernatur veniam reprehenderit culpa tempora veritatis.','Omnis iusto labore nihil deserunt est ut voluptatem velit.'),
('9','9','Elna','nihil','Rowe','zieme.candice@example.net','901-152-9665x92','Suite 031','288 Kilback Cape','Genovevaborough','Ziemann Summit','63932','1997-06-11 01:04:50','525479867857703','Dicta animi numquam velit ut culpa reprehenderit.','Nam dolorem enim in quam.'),
('10','10','Berta','officiis','Ryan','rod.trantow@example.org','1-090-218-7196','Suite 341','9767 Lorena Wells','Port Prestonshire','Lowell Wells','14571','1979-01-30 12:46:13','601109620303150','Excepturi sed aspernatur est quo non tempora ut.','Quo laborum quia exercitationem ducimus aut enim similique.'),
('11','11','Brayan','ducimus','Kulas','kovacek.alene@example.com','029.993.0564x36','Suite 065','1219 Emmet Views','Hillsport','Lind Lane','51343','1988-06-07 09:38:16','550406598705725','Atque similique nulla tenetur cumque.','Mollitia blanditiis qui quis eveniet facilis.'),
('12','12','Brooke','aliquid','Lubowitz','turcotte.minnie@example.org','087.641.0355x15','Apt. 567','844 Deckow Burg Apt. 752','Bartside','Marvin Mission','8148','1995-05-17 18:51:50','453922133273680','Similique deleniti repellendus est repellat ut.','Aut qui placeat et ducimus animi amet.'),
('13','13','Evelyn','enim','Donnelly','dayana.orn@example.com','737.639.2928x23','Apt. 663','94478 Iliana Knolls Suite 457','Port Alexzanderbury','Leo Prairie','21427','1995-12-19 04:15:36','550936747465355','Voluptatem qui laborum deleniti dolores magnam.','Quisquam inventore molestias commodi quae vel doloremque provident.'),
('14','14','Amanda','est','Carter','joe.thompson@example.com','768.047.6307x59','Suite 776','1255 Dahlia Court Suite 108','East Dustin','Shannon Corners','82531','2000-01-26 23:27:52','522724811833060','Esse occaecati ipsa rerum dolor.','Natus ut dignissimos vel quia dicta sed esse.'),
('15','15','Lorna','ex','Jenkins','linwood81@example.org','484.382.3187x66','Suite 106','461 Jamil Summit','Nedraville','Rodriguez Divide','99616','2019-12-01 21:18:20','512428235027735','Nihil eveniet quo libero aliquam sit ea non.','Incidunt nobis commodi officia qui repellendus esse consectetur repellendus.'),
('16','16','Lacey','sequi','Wilkinson','william.west@example.org','+31(8)552000558','Suite 555','3608 Marquise Walk Apt. 646','Lake Jovanny','Eriberto Parkway','16225','1979-05-11 22:24:01','373265783882982','Officiis magnam nostrum culpa dicta cumque.','Pariatur similique autem veritatis quia quia ea et.'),
('17','17','Carmella','rem','Williamson','fvonrueden@example.com','147-402-9984x14','Apt. 276','58120 Schowalter Streets Apt. 561','Briceview','Nico Passage','40060','1970-04-14 19:55:50','455646527069372','Sed placeat ipsam error expedita.','Facere sit et et consequatur sapiente et.'),
('18','18','Pinkie','voluptatem','West','tommie.douglas@example.com','270.411.3178x47','Suite 050','13661 Kautzer Island','Lake Lilliantown','Stroman Field','8549','1990-02-20 15:55:46','539650894167201','Vel assumenda natus earum facilis.','Ut adipisci occaecati magni nisi quaerat sed.'),
('19','19','Eino','deleniti','Turcotte','florence.huel@example.net','07134306449','Apt. 639','338 Makenzie Ranch Apt. 806','Susannaborough','Bode Mountains','3284','2005-07-29 12:17:09','601136095045898','Iusto ut ea ut nesciunt.','Ab inventore eveniet consectetur est nulla eligendi est enim.'),
('20','20','Juanita','odit','Bechtelar','utowne@example.org','1-753-288-9095x','Suite 967','0020 Schowalter Tunnel','Lake Assuntatown','Wuckert Pass','80650','2011-09-03 08:08:18','522325031546250','Natus recusandae quis fugiat et et.','Corporis asperiores placeat nobis provident aut.'),
('21','21','Caden','qui','Emmerich','wzemlak@example.com','887.053.5246','Suite 914','2671 Cameron Parkway Suite 264','Reedbury','Arlene Heights','41290','2006-08-18 12:12:19','4366149979589','Modi praesentium explicabo accusantium reprehenderit reprehenderit fuga.','Ullam commodi dolore iste animi amet.'),
('22','22','Maeve','aut','Toy','qgreenfelder@example.com','036.322.3334x18','Apt. 656','0610 Herman Light','South Brianamouth','Solon Point','12978','1999-07-04 09:45:33','538451602981132','Necessitatibus dolores sapiente explicabo explicabo molestias.','Qui magnam est et eos vel dolor consequatur.'),
('23','23','Opal','maxime','Krajcik','tklocko@example.org','846.531.6015x62','Apt. 798','023 Kihn Mountains Apt. 773','South Antonia','Monahan Square','75412','1973-04-22 06:18:37','402400719424271','Fugit autem quaerat harum.','Voluptatum expedita quia eum quidem architecto sunt.'),
('24','24','Edmund','quas','Mayert','kpowlowski@example.com','388.483.1439x36','Apt. 953','36163 Syble Haven Suite 482','Rennershire','Borer Stream','15143','2004-12-30 08:12:38','448514351323595','Quia est quis et deserunt ut voluptas voluptatem aut.','Cupiditate culpa qui voluptatibus tempora vitae consectetur.'),
('25','25','Toni','nisi','West','isabel.windler@example.net','1-315-907-5748','Apt. 101','399 Torp Grove Suite 654','Lindville','Theodore Keys','86212','1996-05-17 01:49:12','341888399255919','Et deserunt a sunt est saepe.','Molestiae aut id at a.'),
('26','26','Jazmyn','ut','Mante','devan02@example.com','(913)293-5547x8','Apt. 683','56891 McClure Rapid','Nikolausburgh','Feil Road','95426','1996-04-19 07:26:07','4929093917061','Aut sed natus reprehenderit iusto consequatur est.','Non quam exercitationem aut tempore.'),
('27','27','Dorian','facere','Dare','kertzmann.jadyn@example.net','395.736.5885x34','Suite 200','5973 Pfannerstill Key','Gradyhaven','Chandler Greens','79847','1984-01-07 09:05:56','348494029492333','Quasi voluptas autem dolor reprehenderit architecto doloribus qui.','Unde est sed deleniti earum sequi.'),
('28','28','Zachery','laboriosam','Huel','schuyler.halvorson@example.com','478.388.1295','Apt. 291','593 Metz Mills Suite 373','Leopoldmouth','Samson Pass','35941','1979-08-02 12:36:37','471600634711796','Deserunt similique voluptatem sed dolores.','Fuga voluptatibus cum enim sit est dolor molestiae.'),
('29','29','Bennie','accusantium','Parisian','metz.augustus@example.com','348.607.7906x75','Apt. 802','9053 Howell Estates Apt. 425','South Meggie','Rohan Lock','9148','1971-07-29 19:44:26','544983914117609','Est vel dignissimos accusamus sunt ut.','Distinctio non alias ut molestias.'),
('30','30','Forest','temporibus','Kovacek','kunde.allan@example.org','(039)107-1634','Suite 335','458 Ritchie Drives','Metzside','Welch Via','96690','1974-11-07 07:46:57','453917311984007','Porro qui sed hic ab.','Earum ut sunt non dolores nisi laudantium est magni.'),
('31','31','Douglas','est','Littel','jreichert@example.org','(384)426-3412x7','Apt. 224','22357 Bins Spurs Apt. 362','North Desiree','Silas Circle','59827','2012-09-26 13:43:25','453944997611505','Optio praesentium dolore velit maxime ducimus omnis.','Et et aspernatur deserunt aliquam error quas voluptas.'),
('32','32','Eveline','explicabo','Botsford','kulas.esteban@example.com','267.645.0920','Apt. 711','196 Aracely Islands','New Clinthaven','Chelsie Village','70918','1995-02-14 18:07:33','544645928032901','Totam non qui qui ratione enim aperiam.','Deserunt amet numquam architecto consequatur ut.'),
('33','33','Amber','magni','Predovic','denesik.helene@example.org','924.927.8686x32','Apt. 239','40135 Hilll Drive Suite 918','New Veldabury','Felicia Road','58730','1995-05-26 06:14:10','522083569843133','Odit aut adipisci quod occaecati laborum.','Repudiandae nostrum non libero fugit ipsum.'),
('34','34','Filiberto','alias','Wisozk','ashtyn.bradtke@example.com','1-392-843-3866x','Suite 851','3910 Daniel Causeway Apt. 558','Port Emanuel','Hickle Bridge','60445','1989-05-19 09:58:08','601113354479966','Accusantium distinctio eligendi tempora maxime.','Sequi veritatis minima sed porro.'),
('35','35','Alia','qui','Gleichner','bednar.sarah@example.org','1-439-433-2044x','Apt. 491','032 Karli Pines','Bergstromborough','Arvid Forest','15257','1995-10-11 12:45:48','498136462479140','Vero id quo facere occaecati vitae ut.','Ut ut et numquam consequatur hic.'),
('36','36','Jaiden','quo','Lesch','fadel.rachel@example.com','1-031-967-0050x','Suite 847','2231 Hulda Extensions Suite 583','Rheaview','Hammes Road','28065','2005-06-23 06:50:18','543590035557495','Velit officia aut atque voluptatum dolores suscipit.','Sint accusantium qui porro alias modi rem voluptate at.'),
('37','37','Kevon','pariatur','Zboncak','destiney69@example.org','(183)675-6069','Apt. 137','711 Connelly Rue','West Chad','Crist Stravenue','63714','2008-09-07 20:16:23','521534809871871','Dolores eveniet maiores eos mollitia inventore pariatur molestias.','Magni asperiores rerum repellat quibusdam autem consequatur.'),
('38','38','Lonny','impedit','Morar','uschmitt@example.com','137.815.9649','Apt. 020','501 Sharon Union','New Thalia','Hellen Walk','23583','2018-03-27 01:25:01','453222490553868','Autem odit cum nobis accusamus.','Ea qui amet eveniet commodi quia cum.'),
('39','39','Shanna','beatae','Parker','burnice.mitchell@example.com','860-218-1237','Apt. 154','428 Nicolas Viaduct Suite 303','West Maggie','Hessel Avenue','86131','1979-01-15 14:21:34','542293709096758','Explicabo laborum quia eum sint delectus sequi.','Quis eum ea qui amet.'),
('40','40','Garry','laborum','Hilll','quigley.zetta@example.com','1-831-998-7337x','Suite 008','25436 Flavie Pines','West Flavieport','Streich Turnpike','20733','2003-05-21 16:21:48','4929301636288','Assumenda qui totam ducimus voluptatem magni facilis exercitationem.','Odio aut magnam quis itaque voluptas.'),
('41','41','Matilde','asperiores','Welch','o\'kon.tyrell@example.net','(741)813-1350x0','Suite 356','98162 Santiago Port','New Ford','Lilyan Crest','34060','1970-11-16 20:48:04','553997613037273','Voluptas cupiditate quod autem et.','Quas officia neque ducimus consectetur dolorem corrupti.'),
('42','42','Pasquale','suscipit','Mayer','alvah.hessel@example.com','(816)523-7923x0','Apt. 442','243 Klein Street','West Alisa','Hegmann Light','62216','1983-05-14 12:22:24','492984493790027','Rem rerum voluptates aperiam id placeat.','Beatae debitis praesentium fugiat est voluptatem rerum voluptatum.'),
('43','43','Kassandra','aut','Bergnaum','jbernier@example.net','01962300176','Apt. 121','910 Mante Islands Suite 196','Port Ona','Abernathy Square','59078','2003-05-11 02:45:39','539625966001358','Labore animi temporibus nobis repudiandae aperiam illum.','Odit illo est ipsam molestias sapiente sit.'),
('44','44','Laurel','vitae','Ledner','elouise44@example.com','(200)845-3784','Suite 767','56729 Hettinger Gardens','Port Joannieburgh','Yundt Ridges','13642','2015-11-08 02:05:03','526603176006241','Nostrum et quaerat quo voluptates non quia fuga.','Quia inventore nesciunt architecto accusamus.'),
('45','45','Myah','ut','Senger','magdalena87@example.net','1-832-933-9913','Suite 922','1946 Bert Expressway','Garnettborough','Flatley Crescent','99930','1990-06-11 13:07:17','510080653122452','Facilis repudiandae et consectetur doloremque aperiam.','Aspernatur odio quis placeat sed quia.'),
('46','46','Shana','ea','Ebert','eloisa55@example.net','090.493.1237','Suite 749','00051 Morissette Ranch Suite 358','West Eugeneport','Kessler Isle','19679','2014-11-13 16:21:55','528518685989703','Autem ut tempore ut perferendis vero sed.','Aut voluptas exercitationem ex numquam.'),
('47','47','Pattie','et','Lubowitz','ashley.welch@example.net','785.942.2036x62','Apt. 367','435 Thiel Rapid Apt. 625','West Lemuelton','Alisa Meadows','54042','2013-11-19 17:45:17','4178026879639','Necessitatibus modi ut culpa repudiandae voluptas.','Omnis dolor velit est voluptatum.'),
('48','48','Vernon','vitae','Schmeler','yjacobson@example.net','(615)250-6728','Apt. 484','6103 Nedra Locks','North Howard','Cooper Stream','76277','2005-02-26 14:38:21','548147727204892','Similique ad alias cumque unde dolores omnis.','At nihil qui excepturi nam autem et nam consequatur.'),
('49','49','Dillan','fugit','Hand','chaz.cormier@example.net','562.143.2028x66','Apt. 798','84162 Doyle Street Suite 635','New Franciscafort','Lesch Harbors','77233','2004-12-16 13:30:13','517905897318170','Pariatur omnis tempora sit unde facere qui ex.','Est earum id omnis pariatur provident aut.'),
('50','50','Regan','rerum','Crooks','angus.moore@example.net','525-953-9513x37','Apt. 278','1985 Elisa Path Suite 802','East Daphneeland','Stehr Trail','78556','2001-02-27 21:14:38','520567195696542','Necessitatibus qui alias beatae expedita.','Ipsum quidem consequatur omnis molestias ut omnis natus.'),
('51','51','Cheyanne','odit','Davis','auer.ayana@example.org','801-806-0506','Apt. 512','43599 Mckenzie Spring','South Thomas','O\'Connell Mountain','6263','2000-10-28 19:00:37','4556762320914','Ut saepe commodi qui et consequuntur commodi sint quae.','Quo iure et libero omnis facilis aperiam consequatur.'),
('52','52','Alysa','aut','Stanton','rowe.deja@example.org','+64(1)708634214','Suite 863','50920 Charlotte Highway Suite 382','Kiehnside','Douglas Burg','37395','1972-09-10 04:27:54','556912293688275','Sed delectus aut ut magnam voluptas ut iste.','Reiciendis et doloribus qui iure aperiam labore alias.'),
('53','53','Arden','incidunt','Tillman','dheathcote@example.net','894-698-6617x12','Suite 901','2000 Vincent Highway','New Rosalynside','Lehner Village','67405','1992-07-09 19:22:31','453251253552655','Iste est commodi ut excepturi ratione optio velit.','Quia expedita vero qui eum occaecati corrupti.'),
('54','54','Lyda','eum','Marquardt','ybeahan@example.org','506.506.3836x82','Suite 604','48805 Marquis Greens Suite 147','South Gretchenton','Schmeler Park','399','1996-05-23 19:42:43','511224616124282','Pariatur nostrum dicta eaque cumque temporibus in sit.','Consequatur est quis ducimus eius accusantium.'),
('55','55','Melyna','enim','Gerlach','davis.angela@example.com','020.048.1043','Suite 390','13473 Fay Mills Apt. 387','East Vern','Murray Road','59644','1974-02-21 04:21:47','601194215116522','Eaque temporibus voluptatem sint sequi cupiditate dolore similique.','Praesentium et illum dolorem vero.'),
('56','56','Keara','voluptas','Kuvalis','thiel.rebekah@example.net','(607)291-1928x6','Suite 100','465 Jacey Harbors Apt. 983','North Mikaylastad','Jordi Manors','50899','2003-05-23 09:36:47','4485733260162','Similique dolorem nesciunt beatae ut eaque.','Voluptatum enim sed molestiae cum quia.'),
('57','57','Lizeth','ut','Cummings','wolff.simeon@example.org','1-313-329-7998','Apt. 498','32171 Garrett Courts','South Berneiceborough','Green Crossroad','83924','2018-12-13 05:07:19','529596592430730','Rem dolores voluptas ipsa incidunt similique voluptatem est qui.','Earum nisi temporibus ut et nam porro.'),
('58','58','Clint','eos','Boyer','lawson40@example.com','1-130-690-3173','Suite 003','1481 Gene Forest Apt. 524','Rubiechester','Hudson Fields','78941','1979-02-23 02:43:42','349220686068899','Cumque qui omnis et.','Nihil sint non voluptate ad dolor ipsam sit.'),
('59','59','Christine','quaerat','Abshire','cassandre98@example.com','+26(1)892949365','Suite 883','1356 Alysson Neck','Keelingbury','Talia Center','48399','1985-09-14 22:12:58','601109227886092','Ab odio voluptas est optio natus.','Consequatur enim molestiae laborum impedit reiciendis.'),
('60','60','Ressie','eos','Gutkowski','cecile59@example.org','501-291-4210','Suite 988','156 Schumm Extension Apt. 691','Monaborough','Coralie Inlet','59742','2019-03-10 01:51:03','471608714004561','Distinctio voluptas eum vitae et.','At quia aut quod rerum.'),
('61','61','Jeffrey','qui','Huel','joaquin64@example.com','1-498-108-7821x','Apt. 690','143 Osinski Corners Apt. 581','New Raymundo','Mya Glens','85463','2009-05-30 03:44:21','601156271760203','Voluptatem voluptatem est magnam omnis temporibus dolores.','Quisquam impedit ipsum minima est eos exercitationem et dolor.'),
('62','62','Gregory','qui','Greenfelder','whansen@example.org','138-701-1296x70','Apt. 114','156 Koelpin Corners','Flatleybury','Skiles Ferry','90724','1976-08-26 14:22:52','531187161329207','Corrupti ex quia enim laboriosam.','Ipsa vel numquam est sequi iste aperiam.'),
('63','63','Jimmy','ut','Roob','gilda02@example.org','126-214-5610x65','Apt. 214','757 Rosa Walks Apt. 496','Port Clarabelleville','Hulda Shoals','30346','2017-07-04 08:02:58','528282238700889','Dolor rem rerum ad quis ea fugiat.','Aliquam molestiae atque sunt aut non consequuntur.'),
('64','64','Mariah','ut','Leuschke','garrison47@example.com','308-781-8234','Apt. 254','0997 Kimberly Mall Apt. 300','Port Neoma','Jerrod Mills','72039','1995-09-10 14:42:25','4024007181269','Aspernatur aperiam fuga harum ducimus.','Saepe quia iure quibusdam repellat eum voluptates dicta.'),
('65','65','Kim','quibusdam','Renner','erdman.euna@example.com','301-655-3409x84','Apt. 594','9454 Muller Greens Apt. 657','South Ruthiefurt','Orlo Manors','77270','1998-09-04 09:06:34','402400716893217','Quasi temporibus ullam id earum sit vel maxime.','Est in exercitationem earum odit voluptate.'),
('66','66','Myrna','alias','Mosciski','virgil08@example.net','709.351.0434x74','Suite 610','0737 Renner Terrace Apt. 735','South Cicerostad','Bonita Fork','76470','2011-12-10 04:11:47','550928839399990','Qui delectus nihil dolores tempore ut enim.','Omnis beatae qui nihil et quibusdam recusandae aut ipsum.'),
('67','67','Carlos','nulla','Luettgen','jaylin88@example.com','751-163-7218x73','Suite 252','4722 Durward Crest','Johathanside','Domenic Spring','45977','1976-12-04 22:06:33','4532330464965','Quia ea dignissimos sit nobis.','Autem nihil vel quisquam et rerum.'),
('68','68','Miller','occaecati','Orn','shakira.hagenes@example.com','06186628800','Apt. 680','75807 Wolff Points Suite 895','South Tara','Swaniawski Street','3483','2000-06-30 12:42:31','402400719684460','Sint quod eum rerum sapiente beatae quis veniam.','Tempora quaerat provident maiores modi voluptates.'),
('69','69','Charlie','dolorem','Roberts','darien70@example.com','(592)650-2590','Apt. 570','6152 Velma Way Suite 547','Wernerhaven','Hallie Cove','55173','2005-02-05 23:01:26','418485055155606','Vel est fugiat possimus.','Dignissimos modi labore commodi ab explicabo vitae.'),
('70','70','Herbert','aperiam','Runte','jgleichner@example.net','(344)321-8563x5','Suite 100','889 D\'Amore Dam','Schillerview','Freeda Trafficway','6238','1985-10-19 15:09:21','402400711718324','Est sapiente vel est omnis.','Tempora molestiae rerum hic odio.'),
('71','71','Arlie','magni','McClure','beahan.jorge@example.net','(876)141-3636x4','Suite 395','837 Julius Causeway','Farrellfort','Zander Shoal','79735','1979-07-24 23:58:39','4532160199030','Doloremque quas sapiente vel neque.','Qui quo placeat corrupti aperiam.'),
('72','72','Julianne','nemo','Mertz','kelsi.cartwright@example.com','+24(5)624521846','Apt. 443','7447 Beth Wall','Hodkiewicztown','Barton Curve','63328','2003-01-19 08:37:04','4539630867866','Et dolor id dolores est et facere.','Minus quibusdam ut quos.'),
('73','73','Sheldon','veniam','Collier','vmcglynn@example.net','1-749-251-0819x','Apt. 407','4870 Jermaine Vista','South Carletonfort','Foster Summit','33272','2009-11-19 15:14:02','377037371799840','Voluptatem distinctio suscipit qui voluptatem.','Ratione esse hic dolore eum.'),
('74','74','Leonora','minima','Hodkiewicz','pbernhard@example.com','1-243-442-8708x','Suite 597','58990 Carolina Landing','New Pasquale','Abernathy Well','52374','1981-09-20 21:14:18','453945813613492','Eos nesciunt itaque et quis quis ex illum.','Eveniet vitae fugiat sit doloribus aliquid doloribus esse.'),
('75','75','Nestor','quisquam','Cummerata','haag.lauretta@example.com','826-551-2641x90','Apt. 126','2009 Gutkowski Way Apt. 166','New Eloise','Cristobal Streets','94723','1971-12-23 15:24:28','548657082239186','Non natus eos a aut maiores reiciendis animi.','Assumenda debitis illo numquam aut eum enim et.'),
('76','76','Verona','eum','Kunze','lavina.stanton@example.org','129-933-2592','Apt. 542','1016 Adell Mount','New Enafort','Jessica Islands','10303','1971-01-15 07:45:41','448545388986630','Molestiae velit error voluptas nobis repudiandae culpa.','Qui nesciunt ut at debitis dignissimos.'),
('77','77','Etha','quisquam','Howe','burnice.schmitt@example.com','179.645.8990x17','Suite 061','567 Tyree Forges','South Emely','Princess Center','11833','2006-07-04 19:38:40','455623183612189','Illo rerum fugiat esse.','Sed eius temporibus aut voluptas molestiae corporis adipisci necessitatibus.'),
('78','78','Stephan','eius','Nolan','ortiz.brandyn@example.org','397.747.7840x19','Suite 489','28025 Flo Manor','New Savion','Corwin Ramp','76106','2000-06-16 16:08:03','453973468151250','Non vitae dolorem animi facilis nulla et amet dicta.','Quaerat delectus voluptatem ex dolores ea.'),
('79','79','Alexander','quae','Dietrich','nelle.o\'conner@example.org','500.638.6459x90','Suite 205','9345 Johnny Parkways Suite 816','Hodkiewiczbury','Cronin Vista','74879','1996-08-08 02:00:09','510470100171690','Aperiam accusantium at quas asperiores.','Tempora nemo temporibus quibusdam dignissimos sed aut.'),
('80','80','Kelley','recusandae','Okuneva','cathrine62@example.com','01648937745','Apt. 061','641 Lura Club','Dominicview','Casper Haven','5698','1985-05-31 00:33:33','601109423266625','Eius quis et a magnam molestias possimus ut.','Suscipit sequi aut expedita praesentium error.'),
('81','81','Eino','sunt','Nader','lakin.emory@example.org','1-863-162-0947x','Apt. 568','31422 Cormier Trafficway Apt. 088','Elmiramouth','Jerde Lake','34374','2014-10-14 17:06:42','601179144150268','Maxime impedit aut adipisci reprehenderit.','Autem est temporibus autem deleniti voluptatem.'),
('82','82','Ara','aspernatur','Kuhic','myrtie.hilll@example.com','(602)715-5976','Suite 250','78542 Krajcik Parkways Suite 200','Wunschberg','Huel Stream','92254','2010-05-03 00:07:00','455613126023233','Nihil doloribus exercitationem sit sunt ea nostrum non.','Aut et molestias et maiores corrupti.'),
('83','83','Tiara','est','Runolfsson','mclaughlin.nia@example.net','895-669-8155','Suite 369','9180 Lueilwitz Meadows Suite 637','North Elmo','Koepp Crescent','57625','1998-06-26 13:28:29','348794279361769','Est ab perspiciatis qui a.','Dolore quam omnis consectetur assumenda accusantium.'),
('84','84','Garth','ullam','Mohr','steve35@example.com','224-579-7562x21','Apt. 427','7075 Arch River Suite 400','Inesview','Kshlerin Fork','11542','1977-11-30 12:08:39','453996175580773','Nemo nihil repellat non quia magni dicta aspernatur.','Omnis placeat praesentium odio delectus consequatur quas.'),
('85','85','Shanie','vitae','Goldner','emmalee17@example.com','105-642-6204x72','Apt. 912','30608 Esmeralda Mission','South Vita','Konopelski Centers','8946','2014-03-15 20:50:34','499732111500347','Nemo nihil ut culpa culpa ut cupiditate facere ea.','Similique laudantium at asperiores ut.'),
('86','86','Reggie','reiciendis','Rodriguez','yauer@example.com','(241)641-0466x8','Suite 686','80073 Cummings Spring Suite 930','Labadietown','Jerrold Manors','55266','2019-06-26 16:25:36','455622371915906','Praesentium in dolorum qui eligendi.','Alias quaerat autem necessitatibus quia expedita ut est assumenda.'),
('87','87','Alexys','voluptatem','Smith','aletha96@example.com','658-950-0480x58','Suite 353','03246 Cassin Forks','Urbanstad','Lesley Lake','51775','2009-05-09 09:38:39','402400710267916','Ullam sint ab vero labore est vel quod.','Quia ratione cumque deleniti odit nemo ea.'),
('88','88','Franz','cum','Nikolaus','lenora.lebsack@example.org','(545)471-5743x9','Apt. 808','7175 Lance View Apt. 102','Sheilaburgh','Hauck Hills','78738','1981-04-05 07:53:54','455642284779744','Totam rerum et animi cumque vitae culpa dolor.','Eos minima molestiae voluptates dolor unde quia.'),
('89','89','Nico','architecto','Ondricka','yasmine.kub@example.org','01659257546','Suite 913','1159 Elmer Terrace Apt. 473','Eraport','Ernesto Loaf','83254','1999-06-22 04:46:42','4556410848439','Tempora enim optio quasi aut numquam voluptates ut.','Incidunt non ut veritatis a ad quod adipisci.'),
('90','90','Lonny','consequatur','Ward','iemmerich@example.org','322-849-6733','Suite 247','258 Dameon Wells Suite 022','South Asha','Labadie Dale','34327','1993-05-22 16:56:21','4862635837638','Sunt dolorem culpa dolores debitis dolores quia.','Dolor et quibusdam adipisci.'),
('91','91','Ashley','et','Bailey','johns.hettie@example.com','07460342017','Suite 667','58826 Carmine Cove Suite 745','Reinafort','Abagail Points','409','1995-12-31 09:08:30','453260227976840','Ab doloribus iure ea et.','Quia iusto numquam minus.'),
('92','92','Myah','temporibus','Shanahan','hilll.hank@example.net','370-483-3575x90','Apt. 359','0486 Kassulke Inlet','East Angelchester','Rosalia Mall','96000','1976-04-03 19:31:05','491653257557569','Nemo beatae corrupti commodi quaerat labore aliquam.','Perferendis consequuntur quia nihil autem molestiae.'),
('93','93','Aniyah','omnis','Zboncak','adams.arlene@example.net','875.043.7672x93','Apt. 711','10915 Schmidt Union','Nolanburgh','Hahn Extensions','84727','2012-04-05 23:11:42','545509931038296','Sed accusantium velit officia quod ea totam.','Quo aut autem qui et.'),
('94','94','Ruby','doloremque','Gaylord','shessel@example.org','475-271-7374','Apt. 671','804 Theodore Mill Apt. 784','Port Richard','Moore Mountain','11089','1993-07-05 09:28:39','541502169398450','Et provident rerum velit sed quo iusto.','Explicabo facilis consequatur eum debitis.'),
('95','95','Kristy','laboriosam','Wintheiser','gerard86@example.org','1-319-359-7325x','Apt. 778','8632 Langosh Trail','South Keonmouth','Abernathy Turnpike','21019','1972-07-23 03:53:52','521820020817253','Non velit quaerat dignissimos quasi aut natus.','Pariatur nesciunt ut repellendus deleniti velit.'),
('96','96','Madisyn','ut','Labadie','ubergstrom@example.com','158-812-2771','Suite 756','130 Reece Station Apt. 777','Goldaland','Laisha Divide','34395','1981-09-23 14:57:44','4024007129768','Vel odio asperiores est saepe repudiandae perferendis ex.','Soluta placeat delectus voluptatem molestias laboriosam.'),
('97','97','Ola','consequatur','Abbott','ilene37@example.net','(843)968-4256x3','Suite 272','9615 Franecki Cove Apt. 171','Celestineport','Cruickshank Course','76753','1988-08-17 16:20:05','517446105663052','Vel eos omnis velit porro itaque hic porro.','Sequi qui et repudiandae ratione.'),
('98','98','Raymond','aut','Eichmann','osvaldo.jenkins@example.net','(350)651-6091x4','Apt. 187','7502 Hane Knoll Suite 553','Kaciville','Bins Neck','45912','1997-01-21 23:58:29','601110121313986','Et laborum corrupti voluptas.','Sapiente enim reiciendis perferendis aspernatur iusto facere.'),
('99','99','Ernest','molestiae','Kuhic','rebeka.mosciski@example.net','1-124-071-6155x','Suite 278','4389 Parker Estates Apt. 692','Port Henri','Nikolaus Knolls','55515','1972-11-29 10:07:32','530799530990114','Est est sit asperiores labore quis fuga.','Omnis error impedit distinctio autem corporis.'),
('100','100','Dario','nihil','Padberg','feil.abigail@example.org','1-901-595-8988','Suite 039','079 Runolfsdottir Junctions','Columbusville','Chanelle Garden','10298','2012-12-29 05:43:29','524041757666818','Assumenda pariatur necessitatibus magnam error.','Aut atque atque sed qui.'); 




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;







-- Generation time: Thu, 19 Mar 2020 07:58:33 +0000
-- Host: mysql.hostinger.ro
-- DB name: u574849695_22
/*!40030 SET NAMES UTF8 */;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

INSERT INTO `account` VALUES ('1','24','4555266457458','savings','20993.91000','2003-12-22 19:53:22','0','0.18000','1977-09-25 07:55:21'),
('2','13','343318810755766','credit','5553.96000','1989-11-19 04:25:23','2','0.31000','1970-06-05 23:27:16'),
('3','13','5360168127434497','credit','17348.20000','2006-04-15 02:47:58','2','0.49000','1981-04-28 05:46:07'),
('4','42','5383038664566379','checking','11961.05000','2018-05-17 20:57:50','2','0.14000','2015-10-08 21:59:49'),
('5','70','4532901265806820','checking','35841.84000','2015-10-15 09:46:14','1','0.27000','1986-03-16 05:51:55'),
('6','5','4556483743521342','credit','45445.90000','2004-01-22 19:21:30','0','0.26000','2007-04-12 12:00:18'),
('7','67','5174903381802945','checking','8259.21000','2005-02-06 20:20:11','0','0.03000','1997-03-05 05:14:24'),
('8','1','4485994734610528','checking','32804.01000','1985-11-09 01:06:05','1','0.38000','2019-05-10 22:33:24'),
('9','15','342898423849069','checking','20511.45000','2003-01-02 23:08:53','0','0.06000','1999-10-23 19:35:00'),
('10','97','5175432487801572','credit','11486.11000','2014-09-09 13:19:03','1','0.32000','2019-02-05 11:32:16'),
('11','70','5398974253375118','credit','9216.69000','2016-03-16 02:20:15','1','0.37000','2006-11-07 20:29:52'),
('12','66','344632761159904','checking','39284.58000','1984-11-16 12:23:52','2','0.37000','2014-05-19 22:11:56'),
('13','1','5303325719352467','savings','32265.69000','1970-03-21 04:10:16','1','0.08000','2003-07-15 05:36:37'),
('14','56','4485090768058','credit','40461.34000','2020-01-27 12:48:54','0','0.27000','1977-11-17 08:47:30'),
('15','18','6011417025406643','savings','30759.97000','2009-05-20 04:50:47','2','0.39000','2016-06-09 05:16:13'),
('16','66','5284544356974792','savings','34495.89000','1999-12-09 20:52:20','2','0.34000','1993-07-04 01:11:50'),
('17','33','5264285309914491','credit','7769.29000','1986-06-20 23:58:10','0','0.28000','2016-12-26 22:54:26'),
('18','31','6011409579660559','checking','24427.12000','1999-02-26 04:33:05','1','0.08000','1982-09-08 02:56:44'),
('19','39','4916858197127','checking','41355.76000','2015-04-10 08:17:29','1','0.09000','1984-01-05 07:11:24'),
('20','33','4929474943074','checking','38452.96000','1991-12-10 08:27:44','0','0.47000','1986-01-17 01:08:35'),
('21','42','5262828495848663','credit','7434.91000','2011-06-24 19:09:12','2','0.07000','1994-09-10 14:04:24'),
('22','5','6011451123243952','checking','18538.64000','1986-05-13 12:55:52','1','0.03000','2009-11-22 00:23:33'),
('23','27','4539537686643','checking','6861.17000','1988-04-14 14:33:20','2','0.27000','2013-09-11 12:52:31'),
('24','31','4911534786759215','savings','12177.14000','1992-04-05 18:16:14','0','0.39000','1987-03-14 08:06:41'),
('25','27','6011365094409063','savings','12039.35000','1988-09-29 15:17:09','0','0.19000','1985-01-30 09:47:01'),
('26','79','5379148778000788','credit','18914.31000','1993-04-10 10:09:30','1','0.13000','2013-09-10 22:21:20'),
('27','43','5123163392888266','credit','12519.05000','1984-05-06 11:11:34','0','0.35000','2006-12-27 01:44:12'),
('28','90','349279691736924','credit','693.13000','1973-01-17 12:22:27','2','0.41000','2005-01-22 03:22:29'),
('29','27','4024007135233760','savings','28830.65000','2019-01-14 22:01:26','0','0.23000','1991-04-09 00:12:14'),
('30','79','4716647129924638','savings','3336.89000','1986-12-10 04:50:21','2','0.24000','1994-12-10 21:13:32'),
('31','97','4532798352379','savings','21614.05000','1998-10-17 10:46:02','2','0.24000','2003-12-27 16:07:22'),
('32','42','4916077582123','credit','31275.28000','1989-09-04 15:32:32','1','0.06000','1981-02-21 19:58:53'),
('33','13','5562851526432964','checking','12047.90000','1978-02-13 18:10:17','2','0.25000','1988-03-12 09:32:03'),
('34','11','373642606637853','savings','36055.47000','1992-10-17 20:09:31','2','0.26000','1980-10-24 22:00:35'),
('35','23','4716783596152','checking','14250.20000','2000-03-13 01:07:35','1','0.38000','1989-08-12 03:27:03'),
('36','70','5138628820333107','credit','9097.14000','1988-09-26 00:52:22','0','0.40000','1984-08-07 02:56:10'),
('37','8','5575907109849975','credit','27362.12000','2015-06-19 19:27:30','2','0.37000','1977-04-30 18:16:48'),
('38','77','5313159211106138','savings','30139.98000','1973-12-19 01:01:42','1','0.26000','1989-09-28 17:31:44'),
('39','93','6011983839667888','savings','1277.50000','1988-08-06 01:34:57','2','0.36000','1979-12-25 07:28:43'),
('40','21','6011204587532799','checking','14233.42000','1997-10-28 08:42:34','2','0.17000','2007-07-10 02:09:00'),
('41','91','4556738644084','savings','5292.37000','2015-07-20 06:52:54','2','0.04000','1972-02-28 09:59:48'),
('42','68','4024007131576','credit','9801.57000','1989-07-06 10:05:26','1','0.31000','1974-05-14 05:26:56'),
('43','11','5235923859208862','checking','20988.85000','1995-11-01 16:41:27','1','0.35000','1974-03-29 12:23:07'),
('44','75','5490790949642255','credit','17434.24000','1996-01-09 17:56:10','2','0.07000','1972-08-14 17:08:02'),
('45','68','5391941803556162','checking','42585.20000','1987-05-17 19:49:13','0','0.29000','1978-12-31 16:06:18'),
('46','56','4556239737279','savings','35673.86000','1981-08-27 07:41:31','2','0.37000','2011-10-30 09:57:31'),
('47','77','5569288714870477','credit','42446.40000','1977-12-22 04:31:35','1','0.18000','2019-04-16 13:23:38'),
('48','5','6011200923559032','savings','7114.21000','1973-03-05 16:39:27','0','0.46000','1984-01-16 17:47:43'),
('49','13','5204454321039053','credit','30503.85000','1982-01-26 07:32:05','2','0.29000','1977-12-10 04:50:27'),
('50','26','345196673476612','credit','8252.84000','1981-05-22 22:59:53','1','0.22000','1992-06-24 11:09:47'),
('51','1','5247837775605452','credit','32662.01000','1997-05-26 16:39:33','1','0.32000','1980-10-09 08:24:04'),
('52','23','4532754328953','credit','24094.34000','2007-06-08 02:48:17','0','0.13000','1976-09-23 12:48:33'),
('53','23','5345241672326302','checking','48767.36000','1971-06-21 11:48:09','1','0.45000','2020-03-12 01:25:08'),
('54','70','5260974697789230','savings','36550.11000','1984-12-28 14:07:30','1','0.27000','1994-06-19 07:01:13'),
('55','92','5171120752328458','credit','43767.15000','1984-02-21 11:26:21','1','0.18000','1976-12-25 12:57:12'),
('56','91','5201990015820678','credit','6403.78000','2017-08-04 20:23:20','1','0.48000','2003-12-15 09:49:55'),
('57','26','5235311203925692','credit','37778.69000','1981-05-23 12:03:14','0','0.41000','1982-09-08 00:54:11'),
('58','79','4532748928484','checking','30593.66000','1979-04-16 00:14:58','2','0.22000','1989-04-16 00:33:35'),
('59','19','5121203668918712','savings','36247.30000','1986-01-12 13:43:21','0','0.19000','2001-03-11 00:53:17'),
('60','16','5402053575367734','savings','28283.50000','1990-05-25 01:04:00','0','0.44000','2018-07-25 15:01:41'),
('61','79','5388819676740575','savings','16772.48000','1982-04-15 14:44:03','2','0.37000','1971-03-20 09:03:12'),
('62','75','379446424296470','savings','28899.70000','2013-05-22 15:43:19','1','0.16000','1979-09-01 08:34:28'),
('63','23','5321298475507144','credit','22580.39000','2020-01-25 02:25:14','0','0.24000','2004-03-23 13:42:02'),
('64','75','370605648482856','credit','3799.71000','1987-03-26 20:24:32','0','0.17000','1997-12-04 16:20:50'),
('65','36','5161434282137398','checking','4062.67000','1993-05-25 06:23:25','1','0.13000','1987-02-11 09:33:46'),
('66','18','5231787808617319','checking','2629.54000','1999-07-27 01:48:12','1','0.39000','1986-12-17 09:59:58'),
('67','23','4916752097854','credit','45912.57000','2012-06-11 00:02:24','2','0.13000','1991-10-25 02:42:11'),
('68','93','4485057802950','savings','15890.32000','1993-03-07 21:14:12','0','0.03000','2004-07-26 02:38:11'),
('69','11','4716994342389529','savings','29776.45000','1983-10-14 23:50:38','2','0.07000','1994-01-25 12:21:48'),
('70','11','5257230237620949','credit','32845.25000','2016-03-19 18:33:45','2','0.46000','2006-12-29 21:02:27'),
('71','33','5536525906266823','savings','33550.85000','2015-01-28 03:52:34','1','0.34000','1998-02-09 07:55:29'),
('72','57','348104087166591','savings','36767.84000','1984-06-12 09:54:12','2','0.43000','1989-07-13 04:26:39'),
('73','26','340189230918678','checking','43854.02000','1972-10-23 23:59:42','0','0.21000','1974-05-01 13:37:58'),
('74','72','4024007130285','savings','44183.08000','1978-01-13 12:46:23','0','0.18000','2006-07-07 08:11:35'),
('75','2','5118288461678203','credit','48440.80000','1993-10-25 22:53:59','1','0.36000','2001-05-04 09:57:46'),
('76','1','4485877341105','checking','42883.29000','1986-07-30 11:58:40','2','0.16000','2017-12-23 14:03:54'),
('77','26','5489322514946438','credit','42738.14000','1972-09-21 20:46:03','2','0.41000','1976-06-24 21:16:18'),
('78','94','4539309266608','checking','34428.61000','2018-08-21 04:01:30','0','0.09000','2008-03-17 06:11:25'),
('79','42','5374656821028651','savings','40747.09000','1978-03-05 16:38:29','2','0.07000','1982-10-13 19:17:15'),
('80','19','4539792668185013','credit','27025.47000','1983-01-04 00:25:51','2','0.27000','1989-04-20 23:16:36'),
('81','93','4556606009438957','checking','17445.87000','1991-11-09 16:54:06','2','0.31000','1979-07-13 05:47:36'),
('82','91','4539739101839','credit','26114.48000','1986-08-28 03:18:34','0','0.26000','2015-05-10 04:40:02'),
('83','31','4929461443839','credit','6031.62000','2007-01-15 04:52:35','2','0.47000','1997-05-10 07:33:06'),
('84','64','5513164323818555','savings','1303.45000','2004-06-15 09:59:38','0','0.40000','1973-08-23 15:12:25'),
('85','67','5465594839098515','credit','46925.92000','2003-01-09 03:03:44','1','0.12000','1995-05-05 04:35:09'),
('86','39','5150529854477246','savings','23984.38000','1971-03-23 20:53:18','1','0.43000','2014-12-01 01:48:36'),
('87','91','4551426013541423','credit','32816.07000','2017-05-12 16:23:46','2','0.37000','1995-11-07 23:17:54'),
('88','1','5381527919175434','credit','29094.44000','1985-02-17 18:50:35','0','0.30000','1995-09-17 11:36:42'),
('89','75','5126090228173787','savings','14155.70000','2002-09-04 04:38:15','0','0.22000','2018-11-21 07:14:41'),
('90','19','348250419548489','credit','7604.59000','2003-11-18 14:37:52','2','0.49000','2014-10-14 22:56:02'),
('91','94','6011524565957127','credit','39332.87000','2014-11-11 22:39:32','0','0.49000','2002-11-23 14:52:32'),
('92','57','4539260853473246','savings','47728.54000','1990-04-17 09:13:23','1','0.21000','1994-06-16 09:30:58'),
('93','43','4532167720665706','savings','44197.22000','1991-06-04 14:59:39','0','0.46000','1998-09-23 07:40:05'),
('94','11','5217529087736324','credit','18888.54000','2018-02-13 06:28:20','2','0.24000','2005-04-06 09:43:26'),
('95','79','4539668143480','checking','47003.06000','2017-07-20 02:11:30','0','0.32000','1970-10-05 11:02:37'),
('96','62','4477408530488','checking','33295.66000','2006-06-08 15:09:11','2','0.13000','1993-08-31 02:04:00'),
('97','68','5375323830301027','savings','44783.37000','1993-07-22 20:09:07','0','0.21000','1979-01-21 21:19:25'),
('98','19','5385346558840871','savings','7115.22000','2010-05-27 10:59:09','1','0.42000','1979-05-21 20:38:57'),
('99','91','4716602301753727','credit','5566.82000','2011-11-11 16:49:51','0','0.29000','2019-11-01 08:06:31'),
('100','23','5264637589496079','checking','48955.59000','2007-06-18 18:37:47','0','0.35000','2011-10-02 01:11:44'); 




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;








-- Generation time: Thu, 19 Mar 2020 08:01:06 +0000
-- Host: mysql.hostinger.ro
-- DB name: u574849695_22
/*!40030 SET NAMES UTF8 */;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

INSERT INTO `transaction` VALUES ('1','0','0.73038','1','2011-10-28 19:00:36','2014-01-27 03:57:49','5235311203925692','348250419548489','cc','40','tier1','1','0','0'),
('2','0','99999.99999','0','1980-10-04 02:24:27','1970-05-17 11:10:07','4551426013541423','5201990015820678','transfer','20','tier2','1','1','1'),
('3','1','99999.99999','0','2001-09-15 22:06:40','1978-12-14 12:35:43','5247837775605452','5562851526432964','transfer','38','tier1','0','0','0'),
('4','1','39271.02533','0','1975-06-17 06:39:08','2018-07-17 02:53:50','4532754328953','5161434282137398','debit','34','tier2','0','0','0'),
('5','0','0.00000','1','2005-01-27 06:04:28','1993-02-28 13:40:36','4556606009438957','5201990015820678','debit','52','tier1','1','1','0'),
('6','1','99999.99999','1','1983-03-16 14:37:48','1979-11-24 17:25:31','5313159211106138','4556239737279','cc','69','tier1','1','1','0'),
('7','1','1097.62613','0','2011-06-06 06:35:31','1970-01-09 10:38:51','6011409579660559','4716994342389529','debit','46','tier1','0','1','1'),
('8','1','99999.99999','1','1989-11-13 02:25:24','1999-10-20 20:02:13','5569288714870477','5260974697789230','cc','54','tier1','0','0','1'),
('9','0','293.50000','1','2011-01-22 16:05:36','1991-03-17 17:11:15','4929474943074','344632761159904','cc','58','tier2','0','0','0'),
('10','1','99999.99999','1','2016-08-06 05:05:11','1970-07-14 14:48:57','5138628820333107','5379148778000788','cc','3','tier2','0','1','0'),
('11','0','99999.99999','0','1983-12-17 00:08:34','1974-04-29 06:28:03','4539537686643','348250419548489','transfer','35','tier1','1','0','0'),
('12','1','167.24371','0','1975-10-08 20:59:39','1990-01-19 23:30:36','4485877341105','5126090228173787','cc','71','tier1','1','1','1'),
('13','0','99999.99999','0','1992-08-23 10:39:04','2017-01-13 19:39:56','5204454321039053','4477408530488','transfer','59','tier1','0','1','1'),
('14','0','513.40000','0','1973-03-16 04:27:09','1993-12-22 06:15:37','5398974253375118','5231787808617319','transfer','17','tier1','1','0','0'),
('15','1','99999.99999','0','1983-05-04 03:15:17','2020-01-16 18:14:04','6011451123243952','4532167720665706','cc','28','tier1','0','0','1'),
('16','0','99999.99999','1','1977-10-27 11:24:42','2008-04-06 19:58:39','5231787808617319','5569288714870477','transfer','71','tier1','0','1','0'),
('17','1','99999.99999','0','1998-04-24 23:21:29','2014-06-03 14:44:52','4532754328953','5264637589496079','debit','47','tier1','0','1','0'),
('18','1','0.00000','1','2003-05-31 00:07:47','1998-05-07 21:34:19','348104087166591','4916858197127','debit','85','tier1','1','0','0'),
('19','0','0.00000','0','2002-09-19 03:47:41','1999-07-06 23:11:54','6011204587532799','5264637589496079','transfer','35','tier1','0','1','1'),
('20','1','0.00000','0','1970-11-14 14:43:16','1992-10-18 19:39:13','5374656821028651','5123163392888266','cc','9','tier2','1','1','0'),
('21','1','0.00000','1','2001-05-20 16:14:31','2004-04-14 10:47:59','4532754328953','4532754328953','debit','98','tier1','0','1','1'),
('22','1','99999.99999','1','1999-07-08 22:27:19','2006-05-07 09:04:28','4716994342389529','5489322514946438','transfer','89','tier1','0','0','1'),
('23','0','2324.82514','0','2000-11-05 19:26:24','2009-07-25 22:26:00','5569288714870477','348104087166591','debit','60','tier2','0','0','1'),
('24','0','30.10370','1','1981-06-07 22:21:05','1970-04-05 19:20:39','4539792668185013','5345241672326302','transfer','84','tier1','0','1','0'),
('25','1','35476.73983','0','2005-04-13 23:00:45','1981-12-02 06:26:44','5235923859208862','4539792668185013','debit','53','tier1','0','0','0'),
('26','0','421.00000','1','1993-03-29 11:52:03','1998-01-18 08:10:50','5513164323818555','6011204587532799','debit','95','tier2','1','1','1'),
('27','0','99999.99999','1','1975-11-02 05:05:24','1997-06-10 06:54:33','343318810755766','5388819676740575','transfer','87','tier1','0','0','1'),
('28','0','99999.99999','0','2017-08-28 04:09:58','1984-10-14 07:15:38','4716783596152','5171120752328458','debit','9','tier2','1','1','1'),
('29','0','99999.99999','0','1992-09-21 02:00:03','1974-02-09 12:24:31','5383038664566379','340189230918678','cc','49','tier2','0','0','0'),
('30','1','99999.99999','0','1995-08-01 20:39:12','1984-09-17 12:12:58','5123163392888266','5379148778000788','cc','38','tier2','1','0','1'),
('31','1','99999.99999','0','1982-11-10 22:15:46','1994-03-05 19:12:21','5321298475507144','4929474943074','cc','29','tier1','0','1','0'),
('32','1','8.45600','1','2008-06-05 03:28:47','2002-08-09 08:39:50','6011409579660559','5569288714870477','debit','50','tier1','0','0','1'),
('33','0','6.33662','1','2007-07-28 06:25:50','2015-07-02 04:19:35','4539260853473246','5264285309914491','debit','58','tier2','1','0','1'),
('34','1','99999.99999','1','1993-10-19 23:10:17','1976-05-09 08:14:25','4716602301753727','4024007131576','cc','53','tier1','1','0','1'),
('35','0','0.00000','0','1981-10-19 11:49:26','2012-01-09 05:37:04','4916858197127','343318810755766','cc','99','tier1','1','0','0'),
('36','1','26.90000','0','2000-12-02 15:32:32','2018-01-29 01:53:49','5204454321039053','5260974697789230','cc','40','tier1','0','1','1'),
('37','1','60.00000','1','2010-09-29 05:00:54','1987-07-09 07:53:45','5204454321039053','373642606637853','cc','59','tier1','0','1','0'),
('38','1','5640.00000','1','1971-10-30 07:08:32','1973-10-10 13:46:29','5303325719352467','5313159211106138','debit','89','tier2','0','1','0'),
('39','1','2.64837','1','2007-02-07 22:26:22','1987-12-01 07:07:42','5262828495848663','4485994734610528','cc','96','tier1','0','0','0'),
('40','0','1.40832','0','2011-04-29 06:04:52','1975-11-21 01:17:24','4532901265806820','5375323830301027','transfer','81','tier2','1','1','0'),
('41','1','1.13284','0','1971-10-02 00:34:56','1975-06-05 17:08:24','5247837775605452','5235311203925692','debit','7','tier1','1','0','1'),
('42','1','0.00000','0','1998-07-18 12:23:43','1988-04-06 03:27:36','5126090228173787','4024007135233760','cc','96','tier2','0','1','1'),
('43','0','0.00000','1','2013-12-14 16:36:03','2016-03-12 20:13:32','4551426013541423','6011524565957127','debit','78','tier2','1','0','0'),
('44','0','1.00000','1','2003-05-31 11:46:35','2011-11-21 07:15:38','5398974253375118','4477408530488','cc','55','tier1','0','0','1'),
('45','1','99999.99999','1','1986-12-19 13:26:12','2012-12-30 02:40:48','4024007135233760','4539309266608','transfer','89','tier2','0','0','1'),
('46','0','99999.99999','1','1971-08-15 18:37:04','2014-03-03 14:12:35','6011417025406643','4551426013541423','cc','96','tier1','0','0','0'),
('47','1','99999.99999','0','2001-01-30 11:41:13','2001-04-28 11:24:21','4539309266608','4556606009438957','cc','53','tier2','0','0','1'),
('48','1','99999.99999','1','2008-07-08 02:20:22','2016-04-18 03:55:26','6011204587532799','5402053575367734','transfer','98','tier2','1','0','1'),
('49','0','99999.99999','0','1998-06-08 03:55:10','1989-07-01 07:17:33','4485090768058','5536525906266823','debit','86','tier1','0','1','0'),
('50','0','99999.99999','0','1995-09-08 15:14:49','1977-03-31 10:57:20','4929474943074','5345241672326302','debit','54','tier1','0','1','1'),
('51','0','99999.99999','0','1979-05-05 05:55:42','1985-06-23 20:27:04','4716783596152','5303325719352467','transfer','95','tier1','1','0','1'),
('52','1','1.27234','1','1993-08-15 20:34:47','2012-09-03 17:23:15','4916752097854','5379148778000788','cc','10','tier1','0','1','0'),
('53','1','99999.99999','0','1981-06-04 13:56:48','1977-05-25 05:26:44','379446424296470','5264637589496079','transfer','35','tier1','1','1','1'),
('54','1','99999.99999','0','1974-01-21 04:44:13','1981-04-16 18:52:53','345196673476612','5313159211106138','cc','61','tier1','0','0','1'),
('55','1','99999.99999','0','1990-07-05 05:29:15','1974-07-12 07:31:02','5121203668918712','5569288714870477','debit','74','tier2','1','1','1'),
('56','0','0.76000','0','1993-08-27 17:16:42','2005-08-05 05:03:16','5383038664566379','4532798352379','debit','76','tier1','1','1','1'),
('57','0','0.00000','1','1996-07-30 08:00:44','1970-03-21 21:10:19','5235311203925692','5374656821028651','transfer','32','tier1','1','1','0'),
('58','1','1224.58042','1','1977-11-14 03:08:53','1997-06-04 09:59:37','5150529854477246','6011983839667888','cc','46','tier2','1','1','0'),
('59','1','808.91683','0','2003-04-05 17:34:55','2016-11-08 19:25:08','348104087166591','5260974697789230','debit','50','tier2','0','1','1'),
('60','1','40286.14389','0','2001-01-06 11:13:56','1979-04-12 22:18:45','349279691736924','4916858197127','cc','89','tier2','0','1','1'),
('61','0','99999.99999','1','2013-02-17 10:44:41','1996-08-22 05:53:46','5313159211106138','4485057802950','debit','83','tier2','0','1','1'),
('62','0','99999.99999','0','1984-04-14 18:08:42','2018-07-14 01:29:56','5379148778000788','4539309266608','transfer','38','tier1','1','0','0'),
('63','0','5393.40000','0','1970-10-01 08:06:41','1994-07-09 22:40:05','4716994342389529','4716647129924638','transfer','47','tier2','0','0','0'),
('64','0','99999.99999','0','1983-07-06 17:00:29','1990-01-01 13:47:17','4485057802950','343318810755766','cc','88','tier2','1','1','0'),
('65','1','16.24600','0','2000-10-28 05:15:43','1990-08-27 10:06:18','5513164323818555','5381527919175434','debit','87','tier1','0','1','1'),
('66','0','99999.99999','1','2012-05-15 23:46:49','2014-12-08 09:54:34','5562851526432964','5161434282137398','debit','32','tier1','1','1','0'),
('67','1','113.50400','1','2004-02-05 18:19:55','1972-05-06 22:03:00','4485877341105','5264637589496079','cc','50','tier1','0','0','1'),
('68','1','0.40000','0','2019-08-25 02:09:01','1971-03-06 18:22:53','4716994342389529','5575907109849975','transfer','6','tier2','1','1','0'),
('69','0','976.85922','0','2016-12-16 10:23:10','2013-11-25 03:25:54','4911534786759215','4485877341105','debit','58','tier2','1','0','0'),
('70','1','99999.99999','0','1989-04-01 23:19:52','1982-11-13 17:22:09','5374656821028651','5204454321039053','cc','7','tier2','0','0','1'),
('71','1','0.00000','1','1997-04-09 06:48:47','2011-06-11 21:47:05','5260974697789230','5126090228173787','transfer','25','tier2','1','0','0'),
('72','1','99999.99999','1','1980-08-01 23:25:34','1972-06-28 19:45:19','5231787808617319','4485090768058','transfer','12','tier2','1','0','0'),
('73','1','99999.99999','0','1998-08-25 03:51:51','1973-08-10 01:35:45','4532798352379','4485057802950','transfer','83','tier2','0','0','1'),
('74','1','148.00000','1','1996-11-21 22:10:30','2006-08-09 01:30:24','4485994734610528','4477408530488','cc','52','tier1','0','1','0'),
('75','0','99999.99999','0','1988-03-13 12:55:50','1988-06-15 17:26:04','5121203668918712','6011200923559032','transfer','88','tier2','0','0','1'),
('76','0','95565.62860','1','2009-02-11 17:55:45','1971-12-24 22:36:43','5257230237620949','6011409579660559','debit','44','tier2','1','1','0'),
('77','1','4380.48829','0','2003-05-02 01:11:51','2010-05-26 08:50:18','4551426013541423','4555266457458','transfer','80','tier2','0','0','1'),
('78','0','10.04841','0','2011-09-05 02:37:39','1996-04-02 12:57:46','4024007135233760','5284544356974792','transfer','86','tier1','0','0','1'),
('79','0','3721.56020','1','1984-01-28 23:58:47','1989-09-15 14:51:03','5465594839098515','5262828495848663','cc','85','tier2','0','0','1'),
('80','1','89.10316','0','1995-07-21 11:15:49','2005-02-22 15:10:46','5264285309914491','6011365094409063','transfer','52','tier2','0','1','0'),
('81','0','31.25730','0','1980-12-31 20:56:49','1970-10-17 01:32:19','5121203668918712','5321298475507144','cc','17','tier2','1','0','1'),
('82','0','19.74500','0','1992-12-29 02:42:15','1988-11-05 11:11:53','5284544356974792','4929461443839','transfer','59','tier1','1','0','0'),
('83','1','99999.99999','0','2017-11-12 22:06:53','1996-01-05 13:17:25','343318810755766','5490790949642255','transfer','41','tier2','0','0','1'),
('84','0','6933.61900','1','2002-01-16 10:01:36','1996-03-27 13:59:18','6011983839667888','6011451123243952','cc','4','tier1','1','1','0'),
('85','1','5.10000','1','1971-03-19 16:09:48','2012-03-23 15:58:35','4485090768058','4485090768058','debit','98','tier2','0','1','0'),
('86','1','1315.78687','0','1994-06-15 21:05:50','2004-06-30 01:10:27','5388819676740575','4024007135233760','transfer','3','tier2','1','1','1'),
('87','1','311.90258','1','2004-08-27 23:01:56','2010-11-17 09:31:13','5465594839098515','5345241672326302','cc','44','tier1','0','0','0'),
('88','0','8.00000','0','1989-12-05 08:03:07','1988-08-22 23:52:20','4539792668185013','4916858197127','transfer','10','tier1','0','1','0'),
('89','1','2.08234','0','2019-02-23 03:08:04','1995-08-13 22:59:06','4551426013541423','5264637589496079','debit','6','tier2','1','1','1'),
('90','1','51120.80000','1','2000-02-15 00:51:31','1979-04-05 23:03:02','5231787808617319','4556483743521342','debit','73','tier2','0','0','1'),
('91','1','9710.16572','1','2009-04-30 11:13:21','2017-07-13 13:08:56','4485994734610528','4477408530488','debit','71','tier1','0','0','1'),
('92','1','2216.59389','0','1991-09-15 21:42:34','1986-10-01 04:13:49','5536525906266823','349279691736924','cc','88','tier1','1','0','0'),
('93','0','13735.15000','0','2005-08-04 23:55:00','2012-01-06 07:16:31','4556483743521342','5161434282137398','transfer','54','tier1','0','1','1'),
('94','1','99999.99999','0','1974-11-11 04:25:20','1982-07-22 05:25:41','349279691736924','4916752097854','transfer','96','tier1','1','1','0'),
('95','1','99999.99999','1','2001-06-16 10:24:22','2003-04-30 03:51:58','349279691736924','6011200923559032','cc','6','tier2','0','1','0'),
('96','1','99999.99999','0','2017-12-22 06:01:47','1996-03-04 20:21:56','5161434282137398','5536525906266823','cc','89','tier1','0','0','1'),
('97','1','12737.23184','1','2014-06-04 14:15:21','2001-11-13 03:33:59','345196673476612','5204454321039053','debit','55','tier1','0','0','0'),
('98','1','99999.99999','1','2004-07-09 22:01:47','1990-09-02 22:22:20','4716994342389529','345196673476612','debit','51','tier2','0','0','1'),
('99','0','99999.99999','0','1996-03-07 10:10:07','2013-02-06 04:49:49','5264637589496079','6011417025406643','debit','46','tier2','0','0','0'),
('100','0','22.59393','0','2018-01-24 16:07:35','1970-09-03 09:32:28','4539260853473246','4485877341105','cc','82','tier2','1','0','0'); 




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- Generation time: Thu, 19 Mar 2020 08:02:19 +0000
-- Host: mysql.hostinger.ro
-- DB name: u574849695_22
/*!40030 SET NAMES UTF8 */;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

INSERT INTO `login_history` VALUES ('1','1','1984-07-26 05:56:35','2013-03-06 17:29:15','19.161.138.67','tablet'),
('2','2','1975-05-06 23:32:19','2009-05-06 09:21:03','254.184.54.84','unknown'),
('3','3','2006-01-17 01:45:35','2018-07-22 08:57:49','129.126.232.165','mobile'),
('4','4','2019-04-28 17:22:31','1988-08-12 04:25:21','198.50.254.105','tablet'),
('5','5','1975-08-21 15:54:09','2003-06-17 05:26:01','245.25.27.205','desktop'),
('6','6','1994-08-24 19:21:07','1981-12-17 04:35:23','205.54.162.97','tablet'),
('7','7','1995-05-30 17:08:14','2003-03-27 07:17:19','82.190.89.23','unknown'),
('8','8','1999-07-30 16:11:46','2010-01-31 08:04:09','135.166.251.175','tablet'),
('9','9','2012-08-10 04:59:48','2001-04-25 14:43:27','41.211.168.157','mobile'),
('10','10','2002-11-17 10:51:34','1996-07-26 06:03:46','65.124.77.207','unknown'),
('11','11','2002-09-08 18:04:14','2017-04-27 19:17:24','236.177.242.129','desktop'),
('12','12','2006-05-19 05:53:15','2001-11-25 08:57:02','127.211.191.252','mobile'),
('13','13','2002-02-08 08:11:35','1998-08-09 15:56:32','124.42.217.248','desktop'),
('14','14','1973-11-14 09:16:48','2019-03-09 10:33:16','174.93.164.157','desktop'),
('15','15','2004-07-14 00:48:02','2001-01-11 22:33:05','89.29.214.206','desktop'),
('16','16','1985-12-30 00:59:48','1997-08-15 03:41:26','67.188.16.222','tablet'),
('17','17','1996-02-18 20:17:25','1982-05-24 07:53:40','139.133.225.101','mobile'),
('18','18','1980-01-03 13:31:17','2004-10-30 16:56:15','188.254.141.103','desktop'),
('19','19','2016-01-24 03:10:50','2018-05-07 12:28:18','4.94.46.250','desktop'),
('20','20','2010-08-15 02:27:46','1992-11-02 22:50:52','12.205.42.146','unknown'),
('21','21','1983-04-09 00:29:16','2001-07-20 03:24:49','209.226.230.2','mobile'),
('22','22','2019-03-12 18:24:46','2016-06-25 23:26:46','75.48.24.171','tablet'),
('23','23','2002-11-27 02:44:04','2004-01-23 21:44:19','165.147.192.174','desktop'),
('24','24','1999-11-26 07:20:42','1988-01-09 21:59:31','254.49.248.198','tablet'),
('25','25','1978-02-21 19:49:35','1978-11-22 11:03:39','32.251.31.164','mobile'),
('26','26','1986-07-19 10:43:04','1998-12-17 15:04:19','234.162.124.7','mobile'),
('27','27','2008-06-29 03:39:26','2008-06-25 08:33:37','219.24.158.210','unknown'),
('28','28','1987-01-11 17:19:56','2015-04-09 01:42:08','21.161.180.139','tablet'),
('29','29','1987-05-08 20:51:07','2005-12-15 20:23:27','199.172.74.195','desktop'),
('30','30','2015-04-15 07:04:49','2002-09-24 06:31:23','88.249.175.184','tablet'),
('31','31','1986-04-14 07:42:21','1990-07-31 09:46:24','49.15.193.36','unknown'),
('32','32','1983-01-08 20:44:02','1998-04-17 20:20:58','69.158.219.65','unknown'),
('33','33','2015-09-22 14:49:49','2003-09-10 20:59:11','99.75.13.240','unknown'),
('34','34','1994-09-07 14:51:54','1977-08-24 12:39:05','163.77.184.115','unknown'),
('35','35','2007-05-13 02:36:20','1971-10-24 04:10:20','181.74.106.130','desktop'),
('36','36','2015-07-09 03:11:30','1995-01-27 23:51:37','129.79.121.197','mobile'),
('37','37','1982-11-23 19:01:20','2001-12-03 19:30:35','25.222.239.68','mobile'),
('38','38','2008-09-10 20:15:55','2013-02-19 04:52:44','97.244.6.166','mobile'),
('39','39','1974-05-29 16:09:02','2007-03-16 04:31:26','218.55.98.168','desktop'),
('40','40','1973-04-02 09:01:54','1979-10-01 05:32:28','150.24.237.125','mobile'),
('41','41','2018-08-07 07:46:47','2013-05-27 18:50:13','26.106.174.35','tablet'),
('42','42','1989-09-09 08:38:32','1972-06-06 02:59:26','194.89.252.251','tablet'),
('43','43','1991-07-14 23:32:16','1991-08-20 12:27:37','195.1.223.25','mobile'),
('44','44','2020-01-02 20:55:33','1971-05-23 10:36:38','34.3.81.225','desktop'),
('45','45','2009-04-29 20:48:57','1990-11-14 14:26:41','150.103.192.53','desktop'),
('46','46','1998-08-29 20:47:00','2004-06-14 16:17:33','129.212.61.36','tablet'),
('47','47','2012-03-05 03:26:20','1996-01-21 00:58:25','200.251.174.227','unknown'),
('48','48','2011-08-06 09:12:48','2001-09-26 17:51:48','8.214.38.126','unknown'),
('49','49','2016-12-27 01:33:37','2003-03-19 06:53:33','178.125.143.140','mobile'),
('50','50','2011-05-18 05:13:53','1989-07-09 18:16:10','147.214.1.251','unknown'),
('51','51','2005-12-09 06:14:51','1983-03-30 14:06:55','206.18.31.225','tablet'),
('52','52','1984-03-12 06:43:31','1992-01-15 18:58:03','126.164.157.27','desktop'),
('53','53','2007-03-27 22:36:48','1995-06-09 07:12:34','80.141.100.88','unknown'),
('54','54','1980-04-16 09:15:50','1991-07-11 15:29:25','8.178.128.90','tablet'),
('55','55','1981-06-18 04:52:21','2011-01-25 22:43:36','90.132.235.27','tablet'),
('56','56','1993-07-26 07:18:15','1985-08-19 03:30:46','204.189.86.76','desktop'),
('57','57','1994-04-08 16:18:43','2004-08-17 12:56:10','123.160.211.86','unknown'),
('58','58','2019-11-17 03:50:35','1978-06-27 04:45:57','159.218.234.132','unknown'),
('59','59','1984-11-26 14:46:43','1979-10-10 05:48:30','26.53.82.126','tablet'),
('60','60','1977-05-26 15:57:45','1986-11-19 05:38:59','29.143.251.221','tablet'),
('61','61','2011-07-12 09:41:07','1981-10-10 15:17:34','14.248.53.172','mobile'),
('62','62','1984-08-15 16:17:04','2013-09-13 13:40:23','198.100.215.42','tablet'),
('63','63','1991-01-11 02:11:14','1991-04-27 18:46:51','184.25.133.245','tablet'),
('64','64','1971-03-02 15:30:40','1998-10-02 14:06:14','95.111.140.32','unknown'),
('65','65','1996-08-03 10:37:02','2017-08-19 18:42:48','66.197.167.158','mobile'),
('66','66','1980-11-23 09:44:03','1995-09-18 22:31:15','92.162.71.229','unknown'),
('67','67','2012-08-29 10:06:34','1974-02-21 21:16:27','89.255.123.151','tablet'),
('68','68','1979-07-12 06:52:25','1977-08-28 18:31:24','126.42.43.221','unknown'),
('69','69','1991-09-26 07:20:43','2019-06-01 03:03:45','45.236.112.142','mobile'),
('70','70','1999-05-19 16:01:21','1998-05-10 05:26:09','171.123.208.21','mobile'),
('71','71','1970-04-08 16:45:08','2002-02-07 04:05:35','42.137.129.151','desktop'),
('72','72','1985-06-17 14:06:16','1974-10-22 19:28:19','192.53.117.176','tablet'),
('73','73','2004-12-26 17:31:46','1974-08-27 22:36:46','218.134.58.140','mobile'),
('74','74','1971-05-18 01:16:07','2003-04-03 03:26:16','0.135.214.23','tablet'),
('75','75','2006-06-23 11:39:10','2008-11-08 18:29:13','203.96.108.64','desktop'),
('76','76','1987-09-26 09:48:47','1991-05-04 22:57:09','11.191.227.155','mobile'),
('77','77','1981-09-02 21:35:58','2017-01-05 03:55:12','194.248.23.16','desktop'),
('78','78','2001-02-02 16:47:18','1977-01-18 12:07:03','187.140.212.224','tablet'),
('79','79','1998-04-27 01:26:24','2011-02-13 08:08:17','14.5.46.116','mobile'),
('80','80','1991-01-29 15:04:08','1989-01-15 15:07:25','10.8.222.154','unknown'),
('81','81','1993-07-11 13:23:15','1988-01-12 11:08:07','110.42.19.99','unknown'),
('82','82','1983-04-09 07:15:15','1990-06-04 17:31:08','13.62.129.31','tablet'),
('83','83','2015-12-29 09:13:55','2015-04-16 18:13:39','190.156.0.123','tablet'),
('84','84','1970-06-29 10:04:19','2008-11-26 04:45:37','193.193.233.102','unknown'),
('85','85','1970-03-23 09:55:27','1973-10-18 13:25:09','88.186.151.203','desktop'),
('86','86','1980-07-30 21:27:17','1975-07-01 22:17:38','134.236.64.174','unknown'),
('87','87','2002-12-22 04:54:00','2018-04-23 18:09:11','176.71.42.102','unknown'),
('88','88','1987-05-30 21:12:58','1970-05-11 16:40:01','239.203.69.122','desktop'),
('89','89','2013-03-18 05:05:38','2013-02-02 21:23:16','208.195.39.202','desktop'),
('90','90','2020-01-27 04:05:37','1971-06-15 15:46:27','68.250.31.88','unknown'),
('91','91','2002-01-24 15:39:50','2005-10-11 03:14:04','56.198.99.113','mobile'),
('92','92','1978-12-11 01:48:53','1978-10-20 19:21:57','150.66.194.86','unknown'),
('93','93','2004-01-03 23:57:18','2011-10-24 19:06:03','224.212.34.84','unknown'),
('94','94','1984-06-12 05:09:08','2005-11-30 08:32:47','175.22.143.32','unknown'),
('95','95','1993-11-10 11:51:37','1980-07-17 03:03:31','53.121.108.45','unknown'),
('96','96','2001-02-21 06:25:47','1985-10-18 16:04:08','215.25.131.49','mobile'),
('97','97','2003-04-03 13:11:20','1983-08-23 23:58:55','163.177.227.57','unknown'),
('98','98','2014-03-13 13:51:35','1979-10-28 18:29:15','129.23.175.127','desktop'),
('99','99','1998-10-04 09:38:36','2011-05-25 05:33:01','57.27.95.185','tablet'),
('100','100','1991-11-09 04:53:48','1979-05-10 12:12:26','253.23.176.59','tablet'); 




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;



-- Generation time: Thu, 19 Mar 2020 08:03:03 +0000
-- Host: mysql.hostinger.ro
-- DB name: u574849695_22
/*!40030 SET NAMES UTF8 */;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

INSERT INTO `appointment` VALUES ('1','42','7','1994-03-04 10:35:31','6'),
('2','26','78','1983-12-06 10:36:08','1'),
('3','36','95','1980-08-17 17:29:07','6'),
('4','19','9','2018-07-17 01:58:36','7'),
('5','26','71','1972-01-11 23:28:52','7'),
('6','45','53','2015-07-02 13:46:51','7'),
('7','39','52','2002-07-11 05:14:54','7'),
('8','94','17','1993-11-15 20:11:37','8'),
('9','39','61','1992-11-06 02:41:19','6'),
('10','15','37','1974-02-02 22:51:09','5'),
('11','24','69','2000-03-31 18:36:22','9'),
('12','79','96','1971-12-19 02:35:08','3'),
('13','77','83','1980-10-06 00:35:35','8'),
('14','63','38','1972-09-26 06:47:31','2'),
('15','93','95','1972-11-01 09:33:06','6'),
('16','57','25','2011-06-21 22:42:45','2'),
('17','45','4','1996-07-11 07:54:52','1'),
('18','94','52','1982-06-21 01:59:33',''),
('19','68','47','2020-01-22 20:49:02',''),
('20','56','25','2009-07-14 09:09:36','7'),
('21','19','73','1971-09-14 14:40:12','9'),
('22','43','28','2015-03-05 23:18:28','7'),
('23','56','80','2010-10-14 13:51:14','8'),
('24','36','44','1988-06-10 12:33:35','8'),
('25','1','47','1999-08-25 20:04:19','5'),
('26','27','37','1984-08-01 15:42:45','1'),
('27','13','3','1985-08-30 01:31:11','8'),
('28','72','3','2019-01-20 02:14:44','5'),
('29','100','6','2008-10-23 11:27:43','8'),
('30','36','29','2004-07-04 02:41:07','8'),
('31','26','29','1976-10-02 12:35:59','8'),
('32','100','73','1977-03-06 14:01:57','6'),
('33','92','81','2004-01-26 11:44:09','9'),
('34','67','4','1984-04-18 10:14:54','1'),
('35','8','61','1981-02-23 07:56:01',''),
('36','31','99','1970-11-07 18:20:56','7'),
('37','19','99','1977-04-08 03:55:46','8'),
('38','11','81','2001-12-16 02:50:05','7'),
('39','100','73','2008-08-02 03:50:41','6'),
('40','63','73','2001-04-03 07:41:36','7'),
('41','75','20','1985-02-08 00:25:01','2'),
('42','68','51','2005-09-20 22:30:32','8'),
('43','11','28','1997-04-01 01:53:33',''),
('44','75','88','1999-05-17 00:14:11','9'),
('45','31','85','2003-11-02 01:25:06','8'),
('46','91','35','1998-06-07 21:56:30','7'),
('47','5','52','1992-07-01 14:06:54','3'),
('48','63','12','1993-01-17 04:38:01','3'),
('49','2','46','1992-05-05 02:05:31','7'),
('50','79','41','1978-03-28 14:33:24','5'),
('51','23','10','1970-04-19 16:28:02','5'),
('52','57','3','1988-08-06 16:32:34','9'),
('53','18','47','1974-05-30 20:18:37','5'),
('54','26','20','1972-02-06 03:10:41','9'),
('55','42','76','1985-03-06 14:55:52','6'),
('56','36','37','1996-11-27 21:18:29','1'),
('57','79','85','1994-05-07 10:54:38','7'),
('58','23','4','1976-08-04 12:15:22','6'),
('59','93','6','2002-09-09 08:40:44','1'),
('60','39','47','2006-03-17 04:17:21','8'),
('61','93','29','1998-02-16 13:47:24','5'),
('62','48','83','1999-09-16 08:18:29','8'),
('63','26','7','1978-01-04 00:30:36','5'),
('64','18','69','1986-02-20 15:33:54','6'),
('65','93','96','1988-04-22 12:44:40','3'),
('66','67','95','1993-04-24 00:29:07','2'),
('67','64','87','2008-06-26 03:57:29','2'),
('68','33','32','2009-07-30 20:44:02','6'),
('69','33','76','1971-05-14 11:21:30','9'),
('70','36','22','2002-09-06 05:16:57',''),
('71','94','59','1983-06-26 16:42:18','7'),
('72','33','9','2004-10-23 23:14:25','8'),
('73','92','38','1989-11-18 23:53:06','8'),
('74','1','96','1970-10-16 22:28:29','2'),
('75','19','81','1990-12-29 08:54:34',''),
('76','57','53','2009-07-21 15:14:35','2'),
('77','15','49','2011-06-17 12:47:18','6'),
('78','56','87','2012-10-14 14:46:05','6'),
('79','62','35','2011-07-05 19:21:42',''),
('80','42','25','2012-08-01 16:26:06','2'),
('81','45','9','2018-02-16 21:14:10','5'),
('82','31','3','1970-12-24 02:05:39',''),
('83','68','55','1978-08-07 18:32:20','4'),
('84','27','59','1979-12-30 01:15:07','3'),
('85','24','10','1983-05-21 15:44:10','8'),
('86','21','74','1991-09-16 09:33:22','7'),
('87','13','83','1974-07-28 08:23:59','9'),
('88','42','22','1980-02-05 02:50:14','6'),
('89','19','98','2002-09-05 14:18:44','8'),
('90','36','17','2003-09-28 02:28:20','6'),
('91','68','76','2020-01-12 03:36:42','1'),
('92','11','85','2004-03-25 19:10:47','4'),
('93','77','65','1970-07-15 13:37:27','4'),
('94','75','53','1975-06-20 02:20:22','8'),
('95','77','22','2018-02-28 00:23:04',''),
('96','66','65','2009-03-29 12:57:55','3'),
('97','66','10','1986-01-04 13:40:54',''),
('98','68','65','1986-09-19 13:25:46','8'),
('99','75','51','1991-02-12 22:54:43','3'),
('100','31','59','1970-09-21 23:20:44',''); 




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

