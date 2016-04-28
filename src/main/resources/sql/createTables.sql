----------------------------------------------------TABLE
CREATE TABLE phones(
	personId int NOT NULL,
	phone varchar(60),
	PRIMARY KEY(personId, phone)
);
----------------------------------------------------TABLE
CREATE TABLE emails(
	personId int NOT NULL,
    email varchar(80),
    PRIMARY KEY(personId, email)
);
----------------------------------------------------TABLE
CREATE TABLE addresses(
	personId int NOT NULL,
	country varchar(30),
	region varchar(30),
	city varchar(30),
	district varchar(30),
	street varchar(30),
	building varchar(10),
	flat int,
	PRIMARY KEY (personId)
);
----------------------------------------------------TABLE
CREATE TABLE categories(
	id int AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE(name)
);
----------------------------------------------------TABLE
CREATE TABLE persons(
	id int AUTO_INCREMENT,
	firstName varchar(60) NOT NULL,
	midName varchar(60),
	lastName varchar(60),
	sex boolean,
	birthDay date,
	isJew boolean,
	givesTithe boolean DEFAULT false,
	categoryId int,
	comment varchar(100),
	wasAdded date DEFAULT GETDATE(),
	CONSTRAINT ucFullName UNIQUE(firstName, midName, lastName),
	FOREIGN KEY (categoryId) REFERENCES categories(id),
	PRIMARY KEY (id)
);
----------------------------------------------------TABLE
CREATE TABLE tribes(
	id int AUTO_INCREMENT,
	name varchar(60),
	PRIMARY KEY (id),
	UNIQUE (name)
);
----------------------------------------------------TABLE
CREATE TABLE regions(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	tribeId int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (leaderId) REFERENCES persons(id),
	FOREIGN KEY (tribeId) REFERENCES tribes(id),
	UNIQUE(leaderId, tribeId)
);
----------------------------------------------------TABLE
CREATE TABLE groups(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	regionId int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (leaderId) REFERENCES persons(id),
	FOREIGN KEY (regionId) REFERENCES regions(id),
	UNIQUE(leaderId, regionId)
);
----------------------------------------------------TABLE
CREATE TABLE groupMembers(
	personId int NOT NULL,
	groupId int NOT NULL,
	FOREIGN KEY (groupId) REFERENCES groups(id),
	FOREIGN KEY (personId) REFERENCES persons(id),
	UNIQUE(groupId, personId)
);
----------------------------------------------------TABLE
CREATE TABLE ministries(
	id int AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE(name)
);
----------------------------------------------------TABLE
CREATE TABLE volunteers(
	id int AUTO_INCREMENT,
	personId int NOT NULL,
	ministryId int NOT NULL,
	UNIQUE(personId, ministryId)
);
----------------------------------------------------TABLE
CREATE TABLE schools(
	id int AUTO_INCREMENT,
	schoolLevel varchar(30) NOT NULL,
	start DATE,
	graduation DATE,
	teacher varchar(60) NOT NULL,
	PRIMARY KEY (id)
);
----------------------------------------------------TABLE
CREATE TABLE graduations(
	schoolId int NOT NULL,
	personId int NOT NULL,
	PRIMARY KEY (schoolId, personId)
);
----------------------------------------------------TABLE
CREATE TABLE trainings(
	id int AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	tookPlace DATE NOT NULL,
	PRIMARY KEY (id)
);
----------------------------------------------------TABLE
CREATE TABLE doneTrainings(
	personId INT NOT NULL,
	trainingId INT NOT NULL,
	UNIQUE(personId, trainingId)
);
----------------------------------------------------TABLE
CREATE TABLE changeRecords(
	tookPlace DATE DEFAULT GETDATE(),
	personId int NOT NULL,
	groupId int NOT NULL,
	wasAdded boolean NOT NULL,
	comment varchar(60)
);
