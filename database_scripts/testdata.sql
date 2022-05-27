insert into delivery_db.roles (role_id, role_name)
values (0, 'CUSTOMER'), (1, 'ADMIN'), (2, 'DELIVERY_MAN');

insert into delivery_db.statuses (status_id, status_name, status_description)
values (0, 'NOT_RECEIVED', 'Package is not received yet. We are waiting for it.'),
       (1, 'RECEIVED', 'Package is already received. We are going to send it.'),
       (2, 'SENT', 'Package is sent. Wait for it.'),
       (3, 'DELIVERED', 'Package is delivered.');

insert into delivery_db.users (first_name, last_name, patronymic, email, password, role_id, username)
values ('Petro', 'Petrov', 'Petrovich', 'petr.petrov2000@gmail.com', 'password1', 1, 'petr'),
       ('Dmitry', 'Dmitrov', 'Dmitrovich', 'dmitr.dmitrovich1999@gmail.com', 'password1', 2, 'dmitr'),
       ('Tymur', 'Dumanskyi', 'Valerievich', 'cap.tim.2001@gmail.com', 'password1', 0, 'timr'),
       ('Oleh', 'Lukyanenko', 'Pavlovich', 'oleh.luk1969@gmail.com', 'password1', 0, 'oleh1969');
