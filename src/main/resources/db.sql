CREATE TABLE `t_platform_information_type` (
  `oid` varchar(32) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `sort` bigint(10) DEFAULT NULL,
  `status` bigint(10) DEFAULT NULL COMMENT '0：关闭  1：启用',
  PRIMARY KEY (`oid`),
  KEY `Index_1` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_platform_channel` (
  `oid` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;