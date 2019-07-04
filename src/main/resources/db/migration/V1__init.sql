CREATE TABLE `repos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `repository` varchar(100) NOT NULL DEFAULT '',
  `counting` int(11) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;