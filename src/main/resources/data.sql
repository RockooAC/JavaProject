INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');

INSERT INTO user (id, email, password, name) VALUES (1, 'gabb@nrk.no', '{bcrypt}$2a$12$KPnCFB8CXddymGhMoyyfdeg9rrBEfNEIcbCmfBBxoI13EDws8Z7Ja', 'gAdmin');

INSERT INTO user_roles (user_id, roles_id) VALUES (1, 1);