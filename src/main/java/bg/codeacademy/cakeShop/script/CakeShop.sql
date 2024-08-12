-- cakeShop.address definition

CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfe80plkgb7b5rvf00jfnik51q` (`city`,`street`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- cakeShop.bank_account definition

CREATE TABLE `bank_account` (
  `amount` float NOT NULL,
  `beneficiary` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `is_rental` bit(1) NOT NULL,
  `iban` varchar(255) NOT NULL,
  `currency` enum('BG','EU') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK699j998jxie2f134gfnu86q96` (`iban`),
  KEY `FK35x54dhm6wif5dtdx3sff4fu1` (`beneficiary`),
  CONSTRAINT `FK35x54dhm6wif5dtdx3sff4fu1` FOREIGN KEY (`beneficiary`) REFERENCES `personal_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- cakeShop.legal_entity definition

CREATE TABLE `legal_entity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `personal_data` int DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `uin` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK85tsx8lyd4g9b4d3ougobxfuq` (`uin`),
  UNIQUE KEY `UKqwkvi6b252e0w9l7nh59ohpp0` (`personal_data`),
  CONSTRAINT `FKpgoklx1d3ebbfohyv39o4stss` FOREIGN KEY (`personal_data`) REFERENCES `personal_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- cakeShop.personal_data definition

CREATE TABLE `personal_data` (
  `address` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `personal_name` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_role` enum('DELIVER','MANAGER','RENTIER','SHOP','WORKER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKggbcjir8fcit6rsftj01iuhuk` (`user_name`),
  KEY `FKsp92cl87hmncx83akulskj4dg` (`address`),
  CONSTRAINT `FKsp92cl87hmncx83akulskj4dg` FOREIGN KEY (`address`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;