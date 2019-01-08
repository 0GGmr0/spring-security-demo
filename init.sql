create table tbl_User(
  id int primary key auto_increment comment '用户的id',
  username varchar(32) unicode not null ,
  password varchar(64) not null,
  authority int default 1
);