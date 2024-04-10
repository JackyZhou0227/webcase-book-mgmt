SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `mobile_phone` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `status` tinyint(1) NULL DEFAULT 0,
                           `real_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `create_date` timestamp(0) NULL DEFAULT NULL,
                           `update_date` timestamp(0) NULL DEFAULT NULL,
                           `last_login_date` timestamp(0) NULL DEFAULT NULL,
                           `avatar_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
                           UNIQUE INDEX `mobile_phone`(`mobile_phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 插入一个用户：
INSERT INTO `t_user`(`user_name`, `password`, `mobile_phone`, `status`,
                     `real_name`, `create_date`, `update_date`, `last_login_date`, `avatar_url`)
VALUES ('admin', '123456', '18888888888', 0, '超级管理员',
        '2020-05-27 11:54:12', null, null, null);

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `price` float(32, 2) NOT NULL,
  `publisher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publish_date` date NOT NULL,
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
CREATE TABLE `t_log` (
                         `id` INT(11) NOT NULL AUTO_INCREMENT,
                         `user_name` VARCHAR(255) NOT NULL,
                         `url` VARCHAR(2048) NOT NULL,
                         `request_time` DATETIME NOT NULL,
                         `request_duration` BIGINT NOT NULL, -- 修改为 BIGINT 存储毫秒值
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;