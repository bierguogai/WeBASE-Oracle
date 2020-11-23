/*
-- open MySQL client
mysql -u root -p xxxx

-- Creates the new database
create database db_example;

-- Creates the user( replace springuser and ThePassword)
create user 'springuser'@'%' identified by 'ThePassword';

-- Gives all privileges to the new user on the newly created database
grant all on db_example.* to 'springuser'@'%';
*/

/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : weoracle

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 21/10/2020 17:45:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for req_history
-- ----------------------------
DROP TABLE IF EXISTS `req_history`;

CREATE TABLE `req_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `req_id` varchar(64) DEFAULT NULL COMMENT '请求编号',
  `oracle_version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT 'Oracle 合约版本号，默认 1',
  `source_type` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '数据来源，0. url。默认0 ',
  `req_query` varchar(512) NOT NULL COMMENT '请求地址格式',
  `req_status` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '请求状态, 0. 请求中；1. 请求失败；2. 请求成功。默认 0',
  `user_contract` varchar(128) NOT NULL DEFAULT '0' COMMENT '调用合约地址',
  `process_time` int(11) NOT NULL DEFAULT '0' COMMENT '请求处理时长，默认 0',
  `result` text DEFAULT NULL COMMENT '请求结果',
  `times_amount` text DEFAULT NULL COMMENT '防止出现小数',
  `error` varchar(512) DEFAULT NULL COMMENT '请求失败时的错误信息',
  `proof_type` int(10) unsigned DEFAULT '0' COMMENT '证明类型, 0. 无证明；1. 签名认证。默认0',
  `proof` text DEFAULT NULL COMMENT '证明内容',
  `service_id_list` varchar(256) DEFAULT NULL COMMENT '处理请求的 service id 列表',
  `create_time` datetime DEFAULT NULL COMMENT '请求创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '请求上次修改时间',
  PRIMARY KEY (`id`),
  KEY `uniq_reqid` (`req_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
