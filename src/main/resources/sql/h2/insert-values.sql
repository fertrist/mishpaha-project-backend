INSERT INTO users values ('admin', 'password', true, 'ROLE_ADMIN');
INSERT INTO users values ('user', 'password', true, 'ROLE_USER');

INSERT INTO categories (name) values ('Белый');
INSERT INTO categories (name) values ('Синий');
INSERT INTO categories (name) values ('Зелёный');
INSERT INTO categories (name) values ('Еврейский');

INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-1', 'Фамилия-1', 'Отчество-1', 1, true, '1986-12-03', 'адрес-1', true);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-2', 'Фамилия-2', 'Отчество-2', 1, false, '1986-12-03', 'адрес-3', false);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-3', 'Фамилия-3', 'Отчество-3', 1, true, '1986-12-03', 'адрес-3', true);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-4', 'Фамилия-4', 'Отчество-4', 1, false, '1986-12-03', 'адрес-4', false);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-5', 'Фамилия-5', 'Отчество-5', 1, true, '1986-12-03', 'адрес-5', true);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-6', 'Фамилия-6', 'Отчество-6', 1, false, '1986-12-03', 'адрес-6', false);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-7', 'Фамилия-7', 'Отчество-7', 2, true, '1986-12-03', 'адрес-7', true);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-8', 'Фамилия-8', 'Отчество-8', 2, false, '1986-12-03', 'адрес-8', false);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-9', 'Фамилия-9', 'Отчество-9', 3, true, '1986-12-03', 'адрес-9', true);
INSERT INTO persons(firstName, midName, lastName, categoryId, sex, birthDay, address, isJew)
 values ('Человек-10', 'Фамилия-10', 'Отчество-10', 4, false, '1986-12-03', 'адрес-10', false);
