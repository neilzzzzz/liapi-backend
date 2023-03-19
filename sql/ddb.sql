-- 创建库
create database if not exists liapi;

-- 切换库
use liapi;

-- 用户调用接口关系表
create table if not exists user_interface_info
(
    id            bigint auto_increment comment 'id' primary key,
    userId        bigint  comment '调用用户 id',
    interfaceInfoId        bigint  comment '接口 id',
    totalNum       int     default 0 comment '总调用次数',  //统计用户调用某个接口的次数
    leftNum       int     default 0 comment '剩余调用次数', //用户还可以调用这个接口多少次
    status       int     default 0 comment '0-正常， 1-禁用',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '用户调用接口关系表';