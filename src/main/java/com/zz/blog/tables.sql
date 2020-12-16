drop table if exists `t_article`;
create table `t_article` (
	`id` int not null auto_increment,
	`title` varchar(36) not null,
    `summary` varchar(100) not null,
	`content` text not null,
    `img_url` varchar(40),
	`create_date` datetime not null,
	PRIMARY key(`id`)
) ENGINE=INNODB DEFAULT charset = 'utf8mb4';


