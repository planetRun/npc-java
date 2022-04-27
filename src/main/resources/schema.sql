CREATE TABLE `t_client` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `target_url` varchar(255) DEFAULT NULL COMMENT '公网域名+端口',
                            `client_vkey` varchar(255) DEFAULT NULL,
                            `client_basic_name` varchar(255) DEFAULT '',
                            `client_basic_password` varchar(255) DEFAULT '',
                            `server_addr` varchar(255) DEFAULT NULL,
                            `local_url` varchar(255) DEFAULT NULL,
                            `conn_type` varchar(255) DEFAULT 'tcp',
                            `trans_type` int(2) DEFAULT '1',
                            `status` int(2) DEFAULT NULL COMMENT '2',
                            `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `npc_uid` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;