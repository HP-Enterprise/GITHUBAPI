create table g_work(
  id int(11) NOT NULL AUTO_INCREMENT ,
  username VARCHAR (50) NOT NULL COMMENT 'github 账户名' ,
  realname VARCHAR (50) COMMENT '真实姓名',
  finished_work int(6) COMMENT '已完成工作量',
  unfinished_work int(6) COMMENT '未完成工作量',
  work_hours int(5) COMMENT '工作时长',
  week_in_year int(2) COMMENT '本周的序号',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作量统计表';