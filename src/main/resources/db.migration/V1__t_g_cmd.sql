create table g_cmd(
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '命令id',
  cmd varchar(1000) not null COMMENT '命令内容',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='git查询命令表';
insert into g_cmd(cmd) values
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Rental653/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/BriAir/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Triclops/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/SMS/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Training/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/DataCenter/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/MessageProcessor/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/IncarSelf/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/TcpServer/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Android-BlueTooth/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/IOS-BlueTooth/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/JPA-Generator/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/Android-BlueTooth/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/IOS-BlueTooth/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/demo/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/Alikula/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/IncarSelf/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/ali-mns/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/cnpmjs.org/issues?state=all'),
('curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/Track/issues?state=all');