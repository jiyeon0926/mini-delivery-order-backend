CREATE TABLE IF NOT EXISTS `users` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`email`	VARCHAR(50)	NOT NULL UNIQUE,
	`password`	VARCHAR(200)	NOT NULL,
	`nickname`	VARCHAR(20)	NOT NULL,
	`role`	VARCHAR(10)	NOT NULL,
	`is_deleted`	BOOLEAN	NOT NULL	DEFAULT false,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE IF NOT EXISTS `stores` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`user_id`	BIGINT	NOT NULL,
	`name`	VARCHAR(40)	NOT NULL,
	`address`	VARCHAR(50)	NOT NULL,
	`min_order_amount`	INT	NOT NULL,
	`open_time`	TIME	NOT NULL,
	`close_time`	TIME	NOT NULL,
	`store_status`	VARCHAR(10)	NOT NULL,
	`is_deleted`	BOOLEAN	NOT NULL	DEFAULT false,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,

	FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE IF NOT EXISTS `menus` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`store_id`	BIGINT	NOT NULL,
	`name`	VARCHAR(20)	NOT NULL,
	`price`	INT	NOT NULL,
	`is_deleted`	BOOLEAN	NOT NULL	DEFAULT false,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,

	FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

CREATE TABLE IF NOT EXISTS `carts` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`user_id`	BIGINT	NOT NULL UNIQUE,
	`store_id`	BIGINT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,

	FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
	FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

CREATE TABLE IF NOT EXISTS `cart_items` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`cart_id`	BIGINT	NOT NULL,
	`menu_id`	BIGINT	NOT NULL,
	`quantity`	INT	NOT NULL	DEFAULT 1,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,

	UNIQUE (`cart_id`, `menu_id`),
	FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`)
);

CREATE TABLE IF NOT EXISTS `orders` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`user_id`	BIGINT	NOT NULL,
	`store_id`	BIGINT	NOT NULL,
	`order_number`	VARCHAR(50)	NOT NULL UNIQUE,
	`address`	VARCHAR(50)	NOT NULL,
	`order_status`	VARCHAR(20)	NOT NULL,
	`rejection_reason`	VARCHAR(200)	NULL,
	`total_amount`	INT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,

	FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

CREATE TABLE IF NOT EXISTS `order_items` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`order_id`	BIGINT	NOT NULL,
	`menu_id`	BIGINT	NOT NULL,
	`quantity`	INT	NOT NULL	DEFAULT 1,
	`price`	INT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,

	UNIQUE (`order_id`, `menu_id`),
	FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`)
);

CREATE TABLE IF NOT EXISTS `reviews` (
	`id`	BIGINT	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	`user_id`	BIGINT	NOT NULL,
	`order_id`	BIGINT	NOT NULL UNIQUE,
	`rating`	INT	NOT NULL	DEFAULT 1,
	`content`	TEXT	NOT NULL,
	`is_deleted`	BOOLEAN	NOT NULL	DEFAULT false,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,

    CHECK (rating BETWEEN 1 AND 5),
	FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
	FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
);