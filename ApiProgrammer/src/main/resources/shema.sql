CREATE TABLE user (
   id             INT(11) NOT NULL AUTO_INCREMENT,
   email          VARCHAR(100) NOT NULL,
   first_name     VARCHAR(50),
   last_name      VARCHAR(50),
   password       VARCHAR(100) NOT NULL,
   profile_img	  VARCHAR(100),
   CONSTRAINT PK_USER PRIMARY KEY (id)
);

CREATE TABLE banner (
   id             INT(11) NOT NULL AUTO_INCREMENT,
   banner_name    VARCHAR(100),
   banner_image   VARCHAR(100),
   description    VARCHAR(100),
   CONSTRAINT PK_BANNER PRIMARY KEY (id)
);

CREATE TABLE service (
   id             	INT(11) NOT NULL AUTO_INCREMENT,
   service_code   	VARCHAR(100),
   service_name   	VARCHAR(100),
   service_icon   	VARCHAR(100),
   service_tarif	int(100),
   CONSTRAINT PK_SERVICE PRIMARY KEY (id)
);

CREATE TABLE users_balance (
    id          INT(11) NOT NULL AUTO_INCREMENT,
	user_id		INT(11) NOT NULL unique,
    balance		INT(100) NOT NULL,
    CONSTRAINT PK_USERS_BALANCE PRIMARY KEY (id),
    CONSTRAINT FK_USERUSERSBALANCE FOREIGN KEY (user_id)
    REFERENCES user(id)
);

CREATE TABLE history_transaction (
    id          		INT(11) NOT NULL AUTO_INCREMENT,
    user_id				INT(11) NOT NULL,
    invoice_number		VARCHAR(50),
    transaction_type 	VARCHAR(20),
    description			VARCHAR(100),
    total_amount 		INT(100),
    created_on			VARCHAR(100),
    CONSTRAINT PK_HISTORY_TRANSACTION PRIMARY KEY (id),
    CONSTRAINT FK_USERHISTORYTRANSACTION FOREIGN KEY (user_id)
    REFERENCES user(id)
);

CREATE TABLE transaction (
    id          		INT(11) NOT NULL AUTO_INCREMENT,
	user_id				INT(11) NOT NULL,
    invoice_number		VARCHAR(100) GENERATED ALWAYS AS (CONCAT('INV', LPAD(id,3,'0'))),
    service_code		VARCHAR(100),
	service_name		VARCHAR(100),
    transaction_type	VARCHAR(20),
    total_amount		INT(100),
    created_on			VARCHAR(100),
    CONSTRAINT PK_TRANSACTION PRIMARY KEY (id),
    CONSTRAINT FK_USERTRANSACTION FOREIGN KEY (user_id)
    REFERENCES user(id)
);