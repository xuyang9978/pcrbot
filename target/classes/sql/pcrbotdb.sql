/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.27 : Database - pcrbotdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pcrbotdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pcrbotdb`;

/*Table structure for table `battle_log` */

DROP TABLE IF EXISTS `battle_log`;

CREATE TABLE `battle_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '出刀记录id',
  `member_id` bigint(11) NOT NULL COMMENT '出刀成员QQ号',
  `group_id` bigint(11) NOT NULL COMMENT '出刀成员所在公会群号',
  `damage` int(10) NOT NULL COMMENT '这一刀的伤害量，单位w',
  `battle_date` datetime NOT NULL COMMENT '出刀时间',
  `which_one` int(11) NOT NULL COMMENT '这刀打的老几',
  `rounds` int(11) NOT NULL COMMENT '这刀属于第几周目boss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `battle_log` */

/*Table structure for table `battling_list` */

DROP TABLE IF EXISTS `battling_list`;

CREATE TABLE `battling_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(11) NOT NULL COMMENT '正在出刀的成员QQ',
  `group_id` bigint(11) NOT NULL COMMENT '正在出刀的成员所属公会的QQ群号',
  `which_one` int(11) NOT NULL COMMENT '正在出刀第几个boss',
  `rounds` int(11) NOT NULL COMMENT '正在出刀第几周目的boss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `battling_list` */

/*Table structure for table `boss_list` */

DROP TABLE IF EXISTS `boss_list`;

CREATE TABLE `boss_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id，一条记录就是周目boss',
  `rounds` int(10) NOT NULL DEFAULT '1' COMMENT '第几周目boss',
  `boss1_hp` int(10) NOT NULL DEFAULT '600' COMMENT 'boss1血量',
  `boss2_hp` int(10) NOT NULL DEFAULT '800' COMMENT 'boss2血量',
  `boss3_hp` int(10) NOT NULL DEFAULT '1000' COMMENT 'boss3血量',
  `boss4_hp` int(10) NOT NULL DEFAULT '1200' COMMENT 'boss4血量',
  `boss5_hp` int(10) NOT NULL DEFAULT '2000' COMMENT 'boss5血量',
  `boss1_remain_hp` int(10) NOT NULL DEFAULT '600' COMMENT 'boss1剩余血量',
  `boss2_remain_hp` int(10) NOT NULL DEFAULT '800' COMMENT 'boss2剩余血量',
  `boss3_remain_hp` int(10) NOT NULL DEFAULT '1000' COMMENT 'boss3剩余血量',
  `boss4_remain_hp` int(10) NOT NULL DEFAULT '1200' COMMENT 'boss4剩余血量',
  `boss5_remain_hp` int(10) NOT NULL DEFAULT '2000' COMMENT 'boss5剩余血量',
  `group_id` bigint(11) NOT NULL COMMENT '属于哪一个公会QQ群的boss',
  `which_one` int(11) NOT NULL DEFAULT '1' COMMENT '当前打到老几了，默认老一',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `boss_list` */

/*Table structure for table `bot_status` */

DROP TABLE IF EXISTS `bot_status`;

CREATE TABLE `bot_status` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(11) NOT NULL COMMENT '群号',
  `group_bot_status` int(1) NOT NULL COMMENT '1表示true，0表示false',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bot_status` */

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '成员id',
  `member_id` bigint(11) NOT NULL COMMENT '成员QQ号',
  `group_id` bigint(11) NOT NULL COMMENT '成员所在QQ群',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `member` */

/*Table structure for table `order_list` */

DROP TABLE IF EXISTS `order_list`;

CREATE TABLE `order_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(11) NOT NULL COMMENT '预约出刀的成员QQ号',
  `group_id` bigint(11) NOT NULL COMMENT '预约出刀成员所在公会QQ群号',
  `which_one` int(11) NOT NULL COMMENT '预约老几',
  `damage` int(11) NOT NULL COMMENT '预估伤害量',
  `rounds` int(11) NOT NULL COMMENT '通过boss_list中的round来判断预约的是第几周目的boss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_list` */

/*Table structure for table `tree_list` */

DROP TABLE IF EXISTS `tree_list`;

CREATE TABLE `tree_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(11) NOT NULL COMMENT '挂树成员QQ',
  `group_id` bigint(11) NOT NULL COMMENT '挂树成员所在公会',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '默认挂树中，1表示正在挂树中，0表示挂树被救了',
  `which_one` int(11) NOT NULL COMMENT '第几个boss挂树',
  `rounds` int(11) NOT NULL COMMENT '第几周目的boss挂树',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tree_list` */

/*Table structure for table `unions` */

DROP TABLE IF EXISTS `unions`;

CREATE TABLE `unions` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '公会id',
  `union_name` varchar(100) NOT NULL COMMENT '公会名',
  `group_id` bigint(11) NOT NULL COMMENT '公会所在群号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `unions` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
