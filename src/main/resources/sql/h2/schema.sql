----------------------------------------------------SIMPLE TABLES
----------------------------------------------------TABLE#1
CREATE TABLE districts(
	id int AUTO_INCREMENT,
	district varchar(60),
	PRIMARY KEY(id),
	UNIQUE(district)
);
INSERT INTO districts (district) values ('Оболонь');
INSERT INTO districts (district) values ('Подол');
INSERT INTO districts (district) values ('Дорогожичи');
INSERT INTO districts (district) values ('Шулявка');

----------------------------------------------------TABLE#2
CREATE TABLE phones(
	personId int NOT NULL,
	phone varchar(60),
	PRIMARY KEY(personId, phone)
);
INSERT INTO phones (personId, phone) values (1, '06345612378');
INSERT INTO phones (personId, phone) values (2, '06778945612');
INSERT INTO phones (personId, phone) values (3, '09845612389');
INSERT INTO phones (personId, phone) values (4, '07312365489');

----------------------------------------------------TABLE#3
CREATE TABLE tribes(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	name varchar(60),
	PRIMARY KEY (id),
	UNIQUE (name)
);
INSERT INTO tribes (name) values ('Первое');
INSERT INTO tribes (name) values ('Второе');
INSERT INTO tribes (name) values ('Третье');
INSERT INTO tribes (name) values ('Четвертое');

----------------------------------------------------TABLE#4



----------------------------------------------------TABLE#X (needs persons table)
CREATE TABLE regions(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	tribeId int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (leaderId) REFERENCES persons(id),
	FOREIGN KEY (tribeId) REFERENCES tribes(tribeId)
);
INSERT INTO tribes (leaderId, tribeId) values (1, 1);
INSERT INTO tribes (leaderId, tribeId) values (2, 2);
INSERT INTO tribes (leaderId, tribeId) values (3, 3);
INSERT INTO tribes (leaderId, tribeId) values (4, 4);

----------------------------------------------------TABLE#X (needs persons regions)
CREATE TABLE groups(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	tribeId int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (leaderId) REFERENCES persons(id),
	FOREIGN KEY (tribeId) REFERENCES tribes(tribeId)
);
INSERT INTO tribes (leaderId, tribeId) values (1, 1);
INSERT INTO tribes (leaderId, tribeId) values (2, 2);
INSERT INTO tribes (leaderId, tribeId) values (3, 3);
INSERT INTO tribes (leaderId, tribeId) values (4, 4);



--CREATE TABLE persons(
--	id int NOT NULL AUTO_INCREMENT,
--	firstName varchar(60) NOT NULL,
--	sername varchar(60),
--	lastName varchar(60),
--	sex boolean NOT NULL,
--	birthDay date,
--	phone varchar(20),
--	homePhone varchar(20),
--	districtId int,
--	address varchar(100),
--	email varchar(80),
--	isJew boolean,
--	givesTithe boolean DEFAULT false,
--	wasAdded date DEFAULT GETDATE(),
--	CONSTRAINT ucFullName UNIQUE(firstName, sername, lastName),
--	PRIMARY KEY(id),
--	FOREIGN KEY (districtId) REFERENCES districts(id)
--);
--
--INSERT INTO persons (firstName, sex, districtId, isJew)
--values('Paul', true, 2, true);
--
--CREATE TABLE ministries();
--CREATE TABLE volunteers();
--
--CREATE TABLE regions(
--	id int NOT NULL AUTO_INCREMENT,
--	tribeId int
--);
--
--CREATE TABLE groups(
--	id int NOT NULL AUTO_INCREMENT,
--
--	PRIMARY KEY (id)
--);
--
--
--DROP TABLE tribes;
--DROP TABLE regions;
--DROP TABLE groups;
--DROP TABLE volunteers;
--DROP TABLE ministries;
--DROP TABLE districts;
--DROP TABLE persons;










