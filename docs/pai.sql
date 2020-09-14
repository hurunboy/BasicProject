-- root
-- 81hkZLbz

-- admin
-- 5j78mEPYi2

--- 物品一级分类
CREATE TABLE `tb_category_level1` (
  `category_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '一级ID',
  
  `category_name` varchar(50) NOT NULL COMMENT '文字',
  `category_url` varchar(100) NOT NULL COMMENT '图标',
  `category_desc` varchar(100) NOT NULL COMMENT '物品描述',

  `sort` smallint(1) DEFAULT '0' COMMENT '排序级别',
  `status` smallint(1) DEFAULT '1' COMMENT '状态 0 下架 1 上架 9 删除',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`) 
) ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8 
COMMENT='物品一级分类';


--- 物品二级分类
CREATE TABLE `tb_category_level2` (
  `category_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '二级ID',
  `category_parent_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '一级物品ID',
  
  `category_name` varchar(50) NOT NULL COMMENT '文字',
  `category_url` varchar(100) NOT NULL COMMENT '图标',
  `category_desc` varchar(100) NOT NULL COMMENT '物品描述',

  `sort` smallint(1) DEFAULT '0' COMMENT '排序级别',
  `status` smallint(1) DEFAULT '1' COMMENT '状态 0 下架 1 上架 9 删除',
  
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`) 
) ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8 
COMMENT='物品二级分类';


-- 物品
CREATE TABLE `tb_merchandise` (
  `merchandise_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `category_id_leve1` bigint(11) NOT NULL COMMENT '一级分类',
  `category_id_leve2` bigint(11) NOT NULL COMMENT '二级分类',

  --- 商品属性
  `merchandise_title` varchar(50) NOT NULL COMMENT '商品名称',
  `merchandise_url` varchar(100) NOT NULL COMMENT '商品列表中展示的URL',

  --- 拍卖属性
  `start_price` decimal(8,2) NOT NULL COMMENT '起拍价格',
  `market_price` decimal(8,2) NOT NULL COMMENT '市场价格',
  `markup_price` decimal(8,2) NOT NULL COMMENT '加拍价格',
  `limit_price` decimal(8,2) NOT NULL COMMENT '最高限价',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`merchandise_id`)
) ENGINE=InnoDB 
AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8 
COMMENT='物品';

-- 物品详情
CREATE TABLE `tb_merchandise_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `merchandise_id` bigint(11) NOT NULL COMMENT '物品ID',

  `merchandise_detail_url` varchar(100) NOT NULL COMMENT '详细图片路径',
  `type` smallint(1) DEFAULT '0' COMMENT '1 轮播图 2 商品详细图',
  `sort` smallint(1) DEFAULT '0' COMMENT '排序级别',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB
AUTO_INCREMENT=1
DEFAULT CHARSET=utf8
COMMENT='物品详情';


-- 用户信息
CREATE TABLE `tb_user` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `nick_name` varchar(15) DEFAULT NULL COMMENT '用户昵称',
  `gender` char(1) DEFAULT NULL COMMENT '用户性别 1.男 2.女 NULL.未知',
  `icon_img_url` varchar(500) DEFAULT NULL COMMENT '用户头像',
  `cert_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `cert_card` varchar(64) DEFAULT NULL COMMENT '身份证',
  `device_id` varchar(64) DEFAULT NULL COMMENT '设备ID',
  `referrer_user_id` bigint(11) DEFAULT NULL COMMENT '介绍人',
  `created_source` varchar(64) DEFAULT NULL COMMENT '注册来源',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9000006 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户表';

-- 自己的会员等级 member_level
-- 自己的拍卖次数 auction_num
-- 直接推荐人数量
-- 间接推荐人数量


-- 用户推荐关系表 7层关系
CREATE TABLE `tb_user_relation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',

  `v1` varchar(100) DEFAULT NULL COMMENT '',
  `v2` varchar(100) DEFAULT NULL COMMENT '',
  `v3` varchar(100) DEFAULT NULL COMMENT '',
  `v4` varchar(100) DEFAULT NULL COMMENT '',
  `v5` varchar(100) DEFAULT NULL COMMENT '',
  `v6` varchar(100) DEFAULT NULL COMMENT '',
  `v7` varchar(100) DEFAULT NULL COMMENT '',
  `v8` varchar(100) DEFAULT NULL COMMENT '',
  `v9` varchar(100) DEFAULT NULL COMMENT '',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户推荐关系表';


-- 用户登录表
CREATE TABLE `tb_user_agent` (
  `user_agent_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户登录编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `wx_open_id` varchar(64) DEFAULT NULL COMMENT '微信openid',
  `wx_union_id` varchar(64) DEFAULT NULL COMMENT '微信unionId',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `login_pwd` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `deleted` smallint(1) DEFAULT '1' COMMENT '1 有效 0 无效',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`user_agent_id`),
  UNIQUE KEY `uniq_user_id` (`user_id`),
  UNIQUE KEY `uniq_telephone` (`telephone`),
  UNIQUE KEY `uniq_email` (`email`),
  UNIQUE KEY `uniq_login_name` (`wx_open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户登录表';

-- 用户资产表
CREATE TABLE `tb_user_asset` (
  `asset_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长',
  `user_id` bigint(11) NOT NULL COMMENT '用户 标识',
  `currency` varchar(20) NOT NULL COMMENT 'rmb 人民币；score 积分',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '用户总资产',
  `available` decimal(10,2) DEFAULT '0.00' COMMENT '用户可用资产',
  `status` smallint(1) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`asset_id`),
  UNIQUE KEY `uniq_user_id` (`user_id`,`currency`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户资产表';

-- 拍币流水记录
CREATE TABLE `tb_asset_record_pb` (
  `record_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `user_id` bigint(11) NOT NULL COMMENT '触发人 用户编号',
  `pre_amount` decimal(10,2) DEFAULT NULL COMMENT '上一笔总金额',
  `change_amount` decimal(10,2) DEFAULT NULL COMMENT '变动总金额',
  `remaining_amount` decimal(10,2) DEFAULT NULL COMMENT '剩余金额',

  `record_detail` varchar(50) DEFAULT NULL COMMENT '记录 明细',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`record_id`),
  KEY `idx_circle_user_id` (`circle_id`,`from_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='拍币流水记录';

-- 积分流水记录
CREATE TABLE `tb_asset_record_score` (
  `record_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `user_id` bigint(11) NOT NULL COMMENT '触发人 用户编号',
  `pre_amount` decimal(10,2) DEFAULT NULL COMMENT '上一笔总金额',
  `change_amount` decimal(10,2) DEFAULT NULL COMMENT '变动总金额',
  `remaining_amount` decimal(10,2) DEFAULT NULL COMMENT '剩余金额',

  `record_detail` varchar(50) DEFAULT NULL COMMENT '记录 明细',

  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`record_id`),
  KEY `idx_circle_user_id` (`circle_id`,`from_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='积分流水记录';


-- 商户


-- 拍卖表
CREATE TABLE `tb_auction` (
  `contract_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `b_advert_id` bigint(11) NOT NULL COMMENT '借贷 ID',
  `borrow_user_id` bigint(11) NOT NULL COMMENT '借款 用户编号',
  `loan_user_id` bigint(11) NOT NULL COMMENT '放款 用户编号',
  `coin_name_en` varchar(25) NOT NULL COMMENT '质押币种 大写',
  `coin_num` decimal(20,10) NOT NULL COMMENT '抵押币种 数量',
  `coin_name_borrow` varchar(25) DEFAULT NULL COMMENT '借贷币种 大写',
  `total_borrow_num` decimal(20,10) NOT NULL COMMENT '总借贷数量 ',
  `curr_coin_price` decimal(20,10) DEFAULT NULL COMMENT '下单时 抵押币种 价值',
  `annualized_rate` decimal(10,4) NOT NULL COMMENT '年化率',
  `pledge_rate` int(11) NOT NULL COMMENT '质押率 1：50%，2：55%，3：60%，4：65%，5：70%',
  `time_limit` int(11) NOT NULL COMMENT '借贷期限 7天 15天 30天，60天，90天，120天',
  `interest_type` smallint(1) NOT NULL COMMENT '付息方式 1 周期付(固定30天) 2 到期付 3 按天付',
  `total_interest` int(11) NOT NULL COMMENT '总期数',
  `start_interest_date` timestamp NOT NULL COMMENT '起息 时间 之后',
  `end_interest_date` timestamp NOT NULL COMMENT '到息 时间 之前',
  `add_coin_status` smallint(1) DEFAULT '0' COMMENT '加仓状态 0-否 1-是',
  `status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1 执行中 2 违约 3 完成 4 平仓完成',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`contract_id`),
  KEY `idx_b_advert_id` (`b_advert_id`),
  KEY `idx_borrow_user_id` (`borrow_user_id`),
  KEY `idx_loan_user_id` (`loan_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='借贷 合约表';
