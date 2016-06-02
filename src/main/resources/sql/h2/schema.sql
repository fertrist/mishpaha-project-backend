CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(100) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE phones(
	personId int NOT NULL,
	phone varchar(60),
	PRIMARY KEY(personId, phone)
);
CREATE TABLE emails(
	personId int NOT NULL,
    email varchar(80),
    PRIMARY KEY(personId, email)
);
CREATE TABLE categories(
	id int AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE(name)
);
CREATE TABLE persons(
	id int AUTO_INCREMENT,
	firstName varchar(60) NOT NULL,
	midName varchar(60),
	lastName varchar(60),
	sex boolean,
	birthDay date,
	isJew boolean,
	givesTithe boolean DEFAULT false,
	categoryId int NOT NULL,
	address varchar(100),
	comment varchar(100),
	wasAdded DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT ucFullName UNIQUE(firstName, midName, lastName),
	FOREIGN KEY(categoryId) REFERENCES categories(id),
	PRIMARY KEY(id)
);
CREATE TABLE tribes(
	id int AUTO_INCREMENT,
	name varchar(60),
	PRIMARY KEY (id),
	UNIQUE (name)
);
CREATE TABLE regions(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	tribeId int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (leaderId) REFERENCES persons(id),
	FOREIGN KEY (tribeId) REFERENCES tribes(id),
	UNIQUE(leaderId, tribeId)
);
CREATE TABLE groups(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	regionId int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (leaderId) REFERENCES persons(id),
	FOREIGN KEY (regionId) REFERENCES regions(id),
	UNIQUE(leaderId, regionId)
);
CREATE TABLE groupMembers(
	personId int NOT NULL,
	groupId int NOT NULL,
	FOREIGN KEY (groupId) REFERENCES groups(id),
	FOREIGN KEY (personId) REFERENCES persons(id),
	UNIQUE(groupId, personId)
);
CREATE TABLE eventTypes(
    id int AUTO_INCREMENT,
    type varchar(60) not null,
    UNIQUE(type),
    PRIMARY KEY(id)
);
CREATE TABLE eventsTbl(
    id int AUTO_INCREMENT,
    personId int NOT NULL,
    groupId int NOT NULL,
    typeId int NOT NULL,
    happened DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comment VARCHAR(255),
    PRIMARY KEY(id),
    UNIQUE(personId, typeId, happened),
    FOREIGN KEY(typeId) REFERENCES eventTypes(id)
);

