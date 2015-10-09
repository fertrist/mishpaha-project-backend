----------------------------------------------------TABLE
CREATE TABLE districts(
	id int AUTO_INCREMENT,
	name varchar(60),
	PRIMARY KEY(id),
	UNIQUE(name)
);
--INSERT INTO districts (name) values ('Оболонь');
--INSERT INTO districts (name) values ('Подол');
--INSERT INTO districts (name) values ('Дорогожичи');
--INSERT INTO districts (name) values ('Шулявка');

----------------------------------------------------TABLE
CREATE TABLE phones(
	personId int NOT NULL,
	phone varchar(60),
	PRIMARY KEY(personId, phone)
);
--INSERT INTO phones (personId, phone) values (1, '0634561237');
--INSERT INTO phones (personId, phone) values (1, '0446589631');
--INSERT INTO phones (personId, phone) values (2, '0677894561');
--INSERT INTO phones (personId, phone) values (3, '0984561239');
--INSERT INTO phones (personId, phone) values (3, '0443652356');
--INSERT INTO phones (personId, phone) values (4, '0731236548');

----------------------------------------------------TABLE
CREATE TABLE emails(
	personId int NOT NULL,
    email varchar(80),
    PRIMARY KEY(personId, email)
);
--INSERT INTO emails (personId, email) values (1, 'ivanov.ivan@gmail.com');
--INSERT INTO emails (personId, email) values (2, 'petrov.petr@gmail.com');
--INSERT INTO emails (personId, email) values (2, 'petrov.petr@rambler.ru');
--INSERT INTO emails (personId, email) values (3, 'fedorov.fedor@gmail.com');
--INSERT INTO emails (personId, email) values (3, 'fedorov.fedor@rambler.ru');
--INSERT INTO emails (personId, email) values (4, 'evgeniev.evgeniy@gmail.com');

----------------------------------------------------TABLE
CREATE TABLE persons(
	id int AUTO_INCREMENT,
	firstName varchar(60) NOT NULL,
	midName varchar(60),
	lastName varchar(60),
	sex boolean,
	birthDay date,
	districtId int,
	address varchar(100),
	isJew boolean,
	givesTithe boolean DEFAULT false,
	wasAdded date DEFAULT GETDATE(),
	CONSTRAINT ucFullName UNIQUE(firstName, midName, lastName),
	PRIMARY KEY(id),
	FOREIGN KEY (districtId) REFERENCES districts(id)
);
--INSERT INTO persons(firstName, midName, lastName, sex, birthDay, districtId, address, isJew)
-- values ('Коленный-1', 'Сидор', 'Сидорович', true, '1960-09-15', 1, 'дома', true);
--INSERT INTO persons(firstName, midName, lastName, sex, birthDay, districtId, address, isJew)
-- values ('Коленный-2', 'Сидор', 'Сидорович', true, '1955-10-15', 1, 'дома', true);
--INSERT INTO persons(firstName, midName, lastName, sex, birthDay, districtId, address, isJew)
-- values ('Коленный-3', 'Сидор', 'Сидорович', true, '1965-11-15', 1, 'дома', true);
--INSERT INTO persons(firstName, midName, lastName, sex, birthDay, districtId, address, isJew)
-- values ('Коленный-4', 'Сидор', 'Сидорович', true, '1970-03-15', 1, 'дома', true);
--
--INSERT INTO persons(firstName, midName, lastName, sex, birthDay, districtId, address, isJew)
-- values ('Имя-1', 'Фамилия-1', 'Отчество-1', true, '1986-12-03', 1, 'где-то в центре', true);
--INSERT INTO persons(firstName, midName, lastName, sex, birthDay, districtId, address, isJew)
-- values ('Имя-2', 'Фамилия-2', 'Отчество-2', true, '1988-08-05', 2, 'Ильинская 43, 12', false);
--INSERT INTO persons(firstName, midName, lastName, districtId, sex, birthDay, address)
-- values ('Имя-3',  'Фамилия-3', 'Отчество-3', 1, true, '1985-04-07', 'Лайоша Гавро 67а, 83');
--INSERT INTO persons(firstName, midName, lastName, districtId, isJew, birthDay, address, givesTithe, sex)
-- values ('Имя-4',  'Фамилия-4', 'Отчество-4', 4, true, '1990-03-01', 'Олени Телиги 20, кв.4', true, true);
--INSERT INTO persons(firstName, midName, lastName, districtId, isJew, birthDay, address, givesTithe, sex)
-- values ('Имя-5',  'Фамилия-5', 'Отчество-5', 4, true, '1990-03-01', 'Олени Телиги 20, кв.4', true, true);
--INSERT INTO persons(firstName, midName, lastName, districtId, isJew, birthDay, address, givesTithe, sex)
-- values ('Имя-6',  'Фамилия-6', 'Отчество-6', 4, true, '1990-03-01', 'Олени Телиги 20, кв.4', true, true);

----------------------------------------------------TABLE
CREATE TABLE tribes(
	id int AUTO_INCREMENT,
	leaderId int NOT NULL,
	name varchar(60),
	PRIMARY KEY (id),
	UNIQUE (name)
);
--INSERT INTO tribes (leaderId, name) values (1, 'Колено-1');
--INSERT INTO tribes (leaderId, name) values (2, 'Колено-2');
--INSERT INTO tribes (leaderId, name) values (3, 'Колено-3');
--INSERT INTO tribes (leaderId, name) values (4, 'Колено-4');

----------------------------------------------------TABLE
CREATE TABLE categories(
	id int AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE(name)
);
--INSERT INTO categories (name) values ('Белый список');
--INSERT INTO categories (name) values ('Зелёный список');
--INSERT INTO categories (name) values ('Еврейский список');
--INSERT INTO categories (name) values ('Синий список');

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
--INSERT INTO regions (leaderId, tribeId) values (1, 1);
--INSERT INTO regions (leaderId, tribeId) values (2, 2);
--INSERT INTO regions (leaderId, tribeId) values (3, 3);
--INSERT INTO regions (leaderId, tribeId) values (4, 4);

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
--INSERT INTO groups (leaderId, regionId) values (5, 1);
--INSERT INTO groups (leaderId, regionId) values (7, 1);
--INSERT INTO groups (leaderId, regionId) values (10, 4);
----------------------------------------------------TABLE
CREATE TABLE groupMembers(
	personId int NOT NULL,
	groupId int NOT NULL,
	categoryId int NOT NULL,
	FOREIGN KEY (groupId) REFERENCES groups(id),
	FOREIGN KEY (personId) REFERENCES persons(id),
	FOREIGN KEY (categoryId) REFERENCES categories(id),
	UNIQUE(groupId, personId)
);
--INSERT INTO groupMembers (personId, groupId, categoryId) values (5, 1, 1);
--INSERT INTO groupMembers (personId, groupId, categoryId) values (6, 1, 2);
--INSERT INTO groupMembers (personId, groupId, categoryId) values (7, 2, 1);
--INSERT INTO groupMembers (personId, groupId, categoryId) values (8, 2, 3);
--INSERT INTO groupMembers (personId, groupId, categoryId) values (9, 3, 1);
--INSERT INTO groupMembers (personId, groupId, categoryId) values (10, 3, 1);

----------------------------------------------------TABLE
CREATE TABLE ministries(
	id int AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE(name)
);
--INSERT INTO ministries (name) values ('Административное');
--INSERT INTO ministries (name) values ('Группа порядка');
--INSERT INTO ministries (name) values ('Попечитель');

----------------------------------------------------TABLE
CREATE TABLE volunteers(
	id int AUTO_INCREMENT,
	personId int NOT NULL,
	ministryId int NOT NULL,
	UNIQUE(personId, ministryId)
);
--INSERT INTO volunteers (personId, ministryId) values (5, 3);
--INSERT INTO volunteers (personId, ministryId) values (5, 1);
--INSERT INTO volunteers (personId, ministryId) values (6, 1);
--INSERT INTO volunteers (personId, ministryId) values (7, 3);
--INSERT INTO volunteers (personId, ministryId) values (8, 2);
--INSERT INTO volunteers (personId, ministryId) values (9, 2);
--INSERT INTO volunteers (personId, ministryId) values (10, 3);
--INSERT INTO volunteers (personId, ministryId) values (10, 2);

----------------------------------------------------TABLE
CREATE TABLE schools(
	id int AUTO_INCREMENT,
	schoolLevel SMALLINT,
	start DATE,
	graduation DATE,
	teacher varchar(60) NOT NULL,
	PRIMARY KEY (id)
);
--INSERT INTO schools (schoolLevel, start, graduation, teacher)
-- values (2, '2015-02-01', '2015-07-01', 'Русняк');
--INSERT INTO schools (schoolLevel, start, graduation, teacher)
-- values (1, '2015-09-21', '2015-12-01', 'Фурса');

----------------------------------------------------TABLE
CREATE TABLE graduations(
	schoolId int NOT NULL,
	personId int NOT NULL,
	PRIMARY KEY (schoolId, personId)
);
--INSERT INTO graduations (schoolId, personId)
-- values (1, 5);
--INSERT INTO graduations (schoolId, personId)
-- values (1, 6);
--INSERT INTO graduations (schoolId, personId)
-- values (1, 7);
-- INSERT INTO graduations (schoolId, personId)
--  values (1, 8);
--INSERT INTO graduations (schoolId, personId)
-- values (1, 9);
--INSERT INTO graduations (schoolId, personId)
-- values (1, 10);
--INSERT INTO graduations (schoolId, personId)
-- values (2, 7);
--INSERT INTO graduations (schoolId, personId)
-- values (2, 5);
--INSERT INTO graduations (schoolId, personId)
-- values (2, 10);

----------------------------------------------------TABLE

CREATE TABLE trainings(
	id int AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	tookPlace DATE NOT NULL,
	PRIMARY KEY (id)
);
--INSERT INTO trainings (name, tookPlace)
--values ('Эффективный руководитель', '2015-05-01');
--INSERT INTO trainings (name, tookPlace)
--values ('Для промоутеров', '2015-08-19');


----------------------------------------------------TABLE

CREATE TABLE doneTrainings(
	personId INT NOT NULL,
	trainingId INT NOT NULL,
	UNIQUE(personId, trainingId)
);
--INSERT INTO doneTrainings (personId, trainingId)
--values (5, 1);
--INSERT INTO doneTrainings (personId, trainingId)
--values (5, 2);
--INSERT INTO doneTrainings (personId, trainingId)
--values (7, 1);
--INSERT INTO doneTrainings (personId, trainingId)
--values (7, 2);
--INSERT INTO doneTrainings (personId, trainingId)
--values (10, 1);
--INSERT INTO doneTrainings (personId, trainingId)
--values (10, 2);


----------------------------------------------------TABLE
CREATE TABLE changeRecords(
	tookPlace DATE DEFAULT GETDATE(),
	personId int NOT NULL,
	groupId int NOT NULL,
	wasAdded boolean NOT NULL,
	comment varchar(60)
);
--INSERT INTO changeRecords(personId, groupId, wasAdded)
--values(5, 1, true);
--INSERT INTO changeRecords(personId, groupId, wasAdded)
--values(6, 1, true);
--INSERT INTO changeRecords(personId, groupId, wasAdded)
--values(7, 2, true);
--INSERT INTO changeRecords(personId, groupId, wasAdded)
--values(8, 2, true);
--INSERT INTO changeRecords(personId, groupId, wasAdded)
--values(9, 3, true);
--INSERT INTO changeRecords(personId, groupId, wasAdded)
--values(10, 3, true);