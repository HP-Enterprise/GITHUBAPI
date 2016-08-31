create table g_project(
  id int(11) NOT NULL AUTO_INCREMENT ,
  name VARCHAR (50) NOT NULL COMMENT '项目名' ,
  description VARCHAR (500) COMMENT '描述',
  created_at TIMESTAMP NULL COMMENT '创建时间',
  open_issue int(6) COMMENT '开启的任务',
  is_private boolean  COMMENT '性质',
  organization VARCHAR (50) COMMENT '组织',

  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目列表';