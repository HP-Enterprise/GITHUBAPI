create table g_work_detail(
  id int(11) NOT NULL AUTO_INCREMENT ,
  user_name VARCHAR (50)  COMMENT 'github 账户名' ,
  real_name VARCHAR (50) COMMENT '真实姓名',
  expected_time int(6) COMMENT '预期工作时间',
  actual_time int(6) COMMENT '实际工作时间',
  title  VARCHAR (500) COMMENT 'issue描述',
  project VARCHAR (50) COMMENT '所属项目',
  state VARCHAR (50) COMMENT 'issue状态',
  efficiency int(2) COMMENT '工作效率',
  week int(8) COMMENT '本周序号',
  month int(8) COMMENT '本月的序号',
  quarter int(2) COMMENT '本季度的序号',
  year int(8) COMMENT '本年的序号',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='详细工作统计表';