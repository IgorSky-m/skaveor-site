\c accounts;

INSERT INTO users (id, dt_create, dt_update, dt_delete, meta, email, name, password) VALUES (
'ae0dc57e-04d3-4a20-82c3-7a97854b6de2',
'2023-08-19 16:13:57.039',
'2023-08-19 16:13:57.039',
NULL,
'test admin role',
'test_a@t.com',
'Admin',
'bedf91f3e704171adc6212711a26ce4b9de1337b218a36d39c2b5981f07c4938'
);


INSERT INTO users (id, dt_create, dt_update, dt_delete, meta, email, name, password) VALUES (
'f3872f50-773a-400d-8ed5-1d175417df14',
'2023-08-10 16:41:48.891',
'2023-08-19 17:00:24.87',
NULL,
'test user role',
'test_u@t.com',
'user',
'36f028580bb02cc8272a9a020f4200e346e276ae664e45ee80745574e2f5ab80'
);

INSERT INTO roles (id, name, description) VALUES ( 0, 'USER', 'website user');
INSERT INTO roles (id, name, description) VALUES ( 1, 'ADMIN', 'website admin');

INSERT INTO custom_user_roles (custom_user_id, roles) VALUES ('ae0dc57e-04d3-4a20-82c3-7a97854b6de2', 1);
INSERT INTO custom_user_roles (custom_user_id, roles) VALUES ('f3872f50-773a-400d-8ed5-1d175417df14', 0);

