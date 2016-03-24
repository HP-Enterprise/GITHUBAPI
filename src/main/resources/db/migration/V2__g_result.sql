
create table g_result(
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'issue结果id',
  issue_id int not null COMMENT 'issueId',
  title varchar(500)  COMMENT '标题',
  assignee varchar(100)  COMMENT '执行人',
  labels varchar(100)  COMMENT '任务标识' ,
  milestone int COMMENT '任务阶段',
  state VARCHAR(20) COMMENT '状态',
  created_at TIMESTAMP NULL COMMENT '创建时间',
  updated_at TIMESTAMP NULL COMMENT '更新时间',
  closed_at  TIMESTAMP NULL COMMENT '完成时间',
  project VARCHAR(100) COMMENT '所属项目',
  user VARCHAR(50) COMMENT 'github用户',
  due_on TIMESTAMP NULL COMMENT '截止时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='issue结果表';