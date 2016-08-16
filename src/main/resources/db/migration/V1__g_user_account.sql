
create table IF NOT EXISTS g_user_account(
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(11) DEFAULT NULL COMMENT '用户名',
  password varchar(32) DEFAULT NULL COMMENT '密码',
   PRIMARY KEY (id),
  UNIQUE KEY unique_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录账户表';