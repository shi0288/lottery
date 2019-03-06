create table user(
	uid int unsigned not null auto_increment comment '用户ID',
	username varchar(20) not null comment '用户名',
	password varchar(32) not null comment '密码',
	status tinyint unsigned not null default 1 comment '状态： 1-启用；0-禁用',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	PRIMARY KEY (`uid`),
	unique key unique_username_password (`username`,`password`)
)engine=innodb default charset=utf8 comment '用户表';

alter table user  add  realname varchar(20) not null default '' comment '姓名';



create table account(
	uid int unsigned not null comment '用户ID',
	balance int unsigned not null default 0 comment '余额',
	update_at int unsigned not null default 0 comment '修改时间',
	PRIMARY KEY (`uid`)
)engine=innodb default charset=utf8 comment '账户表';

create table term(
	id int unsigned not null auto_increment comment '期次ID',
	game varchar(50) not null comment '游戏代号',
	term_code varchar(40) not null comment '期次号',
	next_code varchar(40) not null comment '下一期期次号',
	open_at int unsigned not null default 0 comment '开售时间',
	close_at int unsigned not null default 0 comment '结束时间',
	bonus_at int unsigned not null default 0 comment '开奖时间',
	win_number varchar(80)  not null default '' comment '开奖号码',
	status tinyint unsigned not null default 0 comment '状态 未开售 正在销售 销售结束 已开奖 已算奖 已封存',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '期次信息表';
CREATE INDEX index_status ON term(status);


create table ticket(
	id int unsigned not null auto_increment comment '彩票ID',
	uid int unsigned not null  comment '用户ID',
  status tinyint unsigned not null default 0 comment '状态',
  number varchar(240) not null comment '投注号码',
  play_type varchar(10) not null comment '玩法',
	bet_type varchar(10) not null comment '投注方式',
  amount int unsigned not null default 0 comment '余额',
  multiple int unsigned not null default 0 comment '倍数',
  bonus int unsigned not null default 0 comment '奖金',
  game_code varchar(10) not null comment '游戏代号',
	term_code varchar(40) not null comment '期次号',
	platform_id int unsigned not null  comment '平台id',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	print_at int unsigned not null default 0 comment '出票时间',
	cash_at int unsigned not null default 0 comment '返奖时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '彩票表';

create table trade_log(
	id int unsigned not null auto_increment comment '记录ID',
	uid int unsigned not null  comment '用户ID',
  type tinyint unsigned not null comment '类型  0 支出  1 收入',
  amount int unsigned not null comment '发生额',
  state_after int unsigned not null comment '发生前',
  state_before int unsigned not null comment '发生后',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '记录表';


create table term_report(
	id int unsigned not null auto_increment comment '报表ID',
	uid int unsigned not null  comment '用户ID',
	game_code varchar(10) not null comment '游戏代号',
	term_code varchar(40) not null comment '期次号',
  sale_amount int unsigned not null comment '售出金额',
  hit_amount int unsigned not null comment '中奖金额',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '期次报表';


create table plat(
	id int unsigned not null auto_increment comment '平台ID',
	name varchar(20) not null comment '标示名称',
	username varchar(20) not null comment '用户名',
	password varchar(32) not null comment '密码',
	login_url varchar(500) not null comment '登陆地址',
	balance_url varchar(500) not null comment '登陆地址',
	touzhu_url varchar(500) not null comment '登陆地址',
	cookies text,
	assist text,
	category_id int unsigned not null comment '类型ID',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	delete_at int unsigned not null default 0 comment '删除时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '平台表';


create table plat_category(
	id int unsigned not null auto_increment comment '平台类型',
	name varchar(20) not null comment '平台名称',
	excutor varchar(32) not null comment '执行类',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	delete_at int unsigned not null default 0 comment '删除时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '平台类型表';



create table qiaoqiaoying(
	id int unsigned not null auto_increment comment 'id',
	username varchar(20) not null comment '用户名',
	password varchar(32) not null comment '密码',
	login_url varchar(500) not null DEFAULT '' comment '登陆url',
	data_url varchar(500) not null DEFAULT '' comment '数据url',
	prize_url varchar(500) not null DEFAULT '' comment '中奖查询url',
 	token text comment 'token',
 	refresh_token text comment 'refresh_token',
	create_at int unsigned not null default 0 comment '创建时间',
	expires_in int unsigned not null default 0 comment '过期时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '悄悄赢';

alter table qiaoqiaoying  add  data_url_ffc varchar(200) not null DEFAULT '' comment '分分彩数据url';
alter table qiaoqiaoying  add  prize_url_ffc varchar(200) not null DEFAULT '' comment '分分彩中奖查询url';






create table prediction(
	id int unsigned not null auto_increment comment 'id',
	term varchar(50) not null comment '期次',
	game varchar(50) not null comment '游戏',
 	data text comment '数据',
	create_at int unsigned not null default 0 comment '创建时间',
    update_at int unsigned not null default 0 comment '更新时间',
	PRIMARY KEY (`id`)
)engine=innodb default charset=utf8 comment '预测号';





create table manage(
	id int unsigned not null auto_increment comment '用户ID',
	username varchar(20) not null comment '用户名',
	password varchar(32) not null comment '密码',
	status tinyint unsigned not null default 1 comment '状态： 1-启用；0-禁用',
	create_at int unsigned not null default 0 comment '创建时间',
	update_at int unsigned not null default 0 comment '修改时间',
	PRIMARY KEY (`id`),
	unique key unique_username_password (`username`,`password`)
)engine=innodb default charset=utf8 comment '用户表';


insert ignore into manage(username, password) values ('lottery', 'abc.123');
insert ignore into manage(username, password) values ('manage', '7ujmko0');



alter table user_rule  add  is_dividing  tinyint unsigned not null default 0 comment '止盈止损： 1-启用；0-禁用';
alter table user_rule  add  limit_lose DOUBLE not null  DEFAULT 0 comment '止损值';
alter table user_rule  add  limit_win DOUBLE not null  DEFAULT 0 comment '止盈值';
alter table user_rule  add  dividing_time int unsigned not null default 0  comment '开启时间(每次启用时都会更新)';



alter table user add setting tinyint unsigned not null default 0 comment '用户设置： 1-启用；0-禁用';



alter table user_order_log  add  send  tinyint unsigned not null default 0 comment '是否发送  0 未发送  1成功 2失败';
alter table user_order_log  add  response varchar(200) not null default '' comment '响应报文';

















