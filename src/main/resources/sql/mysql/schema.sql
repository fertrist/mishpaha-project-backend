CREATE TABLE districts(
	id int NOT NULL AUTO_INCREMENT,
	district varchar(60),
	PRIMARY KEY(id),
	UNIQUE(district)
);

INSERT INTO districts values ("Оболонь");
INSERT INTO districts values ("Подол");
INSERT INTO districts values ("Дорогожичи");
INSERT INTO districts values ("Шулявка");


CREATE TABLE persons(
	id int NOT NULL AUTO_INCREMENT,
	firstName varchar(60) NOT NULL,
	sername varchar(60),
	lastName varchar(60),
	sex boolean NOT NULL,
	birthDay date,
	phone varchar(20),
	homePhone varchar(20),
	districtId int,
	address varchar(100),
	email varchar(80),
	isJew boolean,
	givesTithe boolean DEFAULT false,
	wasAdded date DEFAULT GETDATE(),
	CONSTRAINT ucFullName UNIQUE(firstName, sername, lastName),
	PRIMARY KEY(id),
	FOREIGN KEY (districtId) REFERENCES districts(id)
);

INSERT INTO persons (firstName, sex, districtId, isJew)
values('Paul', true, 2, true);

CREATE TABLE ministries();
CREATE TABLE volunteers();

CREATE TABLE tribes(
	id int NOT NULL AUTO_INCREMENT,
	leaderId int NOT NULL,
	name varchar(60),
	PRIMARY KEY (id),
	UNIQUE (name)
);

CREATE TABLE regions(
	id int NOT NULL AUTO_INCREMENT,
	tribeId int
);

CREATE TABLE groups(
	id int NOT NULL AUTO_INCREMENT,

	PRIMARY KEY (id)
);


DROP TABLE tribes;
DROP TABLE regions;
DROP TABLE groups;
DROP TABLE volunteers;
DROP TABLE ministries;
DROP TABLE districts;
DROP TABLE persons;










