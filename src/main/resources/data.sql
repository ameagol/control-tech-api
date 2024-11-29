-- Inserting the roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Inserting the users
INSERT INTO users (name, username, email, password)
VALUES ('Usuario', 'user', 'user@yahoo.com', '$2a$12$xeTO09ACTJ/C5yXp1/w.iOuvxR0h2usohe/p3yxfcySEINQ2hm9o6');

INSERT INTO users (name, username, email, password)
VALUES ('Ricardo', 'ricaprado', 'ricardo@yahoo.com', '$2a$12$p36N/AbAO3HLskGYZr.PMu1U47uF7o.vxb/QEMopxE.c4wG6QhSj6');

-- Inserting data into the users_roles table
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);  -- User "Usuario" -> ROLE_USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);  -- User "Ricardo" -> ROLE_ADMIN


INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (1, CURRENT_TIMESTAMP, 'MacBook Pro', '12345A', 'Computador', 'FRU123', 'ok', 'John Doe', 'john.doe@example.com', 'Apple', 'INV-001', 'High-performance laptop from Apple.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (2, CURRENT_TIMESTAMP, 'Epson EcoTank L3250', '67890B', 'Impressora', 'FRU456', 'conserto', 'Jane Smith', 'jane.smith@example.com', 'Epson', 'INV-002', 'Multi-function color printer.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (3, CURRENT_TIMESTAMP, 'iPhone 14 Pro', '11223C', 'Celular', 'FRU789', 'analise', 'Alice Johnson', 'alice.johnson@example.com', 'Apple', 'INV-003', 'Latest flagship smartphone from Apple.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (4, CURRENT_TIMESTAMP, 'Dell UltraSharp 32"', '44556D', 'Monitor', 'FRU012', 'ok', 'Bob Brown', 'bob.brown@example.com', 'Dell', 'INV-004', 'High-resolution 32-inch monitor.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (5, CURRENT_TIMESTAMP, 'Gaming PC - Predator Orion 3000', '77889E', 'Computador', 'FRU345', 'quebrado', 'Charlie Davis', 'charlie.davis@example.com', 'Acer', 'INV-005', 'Custom-built gaming desktop.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (6, CURRENT_TIMESTAMP, 'Samsung Galaxy S22', '99110F', 'Celular', 'FRU678', 'ok', 'Diana Evans', 'diana.evans@example.com', 'Samsung', 'INV-006', 'High-end Android smartphone.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (7, CURRENT_TIMESTAMP, 'HP DeskJet 2722', '22334G', 'Impressora', 'FRU901', 'analise', 'Ethan Green', 'ethan.green@example.com', 'HP', 'INV-007', 'Compact home printer.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (8, CURRENT_TIMESTAMP, 'Dell Inspiron Desktop', '33445H', 'Computador', 'FRU234', 'quebrado', 'Fiona Harris', 'fiona.harris@example.com', 'Dell', 'INV-008', 'All-in-one desktop.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (9, CURRENT_TIMESTAMP, 'Dell P2422HE', '44556I', 'Monitor', 'FRU567', 'ok', 'George Ingram', 'george.ingram@example.com', 'Dell', 'INV-009', 'High-quality office monitor.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (10, CURRENT_TIMESTAMP, 'Mac Mini M2', '55667J', 'Computador', 'FRU890', 'analise', 'Hannah Jackson', 'hannah.jackson@example.com', 'Apple', 'INV-010', 'Compact desktop computer.');

-- Partes Entries
INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (11, CURRENT_TIMESTAMP, 'Logitech MX Master 3', '66789K', 'Partes', 'FRU1234', 'ok', 'Ian Thomas', 'ian.thomas@example.com', 'Logitech', 'INV-011', 'Wireless ergonomic mouse.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (12, CURRENT_TIMESTAMP, 'Razer BlackWidow V3', '77890L', 'Partes', 'FRU5678', 'analise', 'Olivia Parker', 'olivia.parker@example.com', 'Razer', 'INV-012', 'Mechanical gaming keyboard.');

INSERT INTO devices (id, created_at, name, serial, type, fru, status, owner, email, branch, invoice, description)
VALUES (13, CURRENT_TIMESTAMP, 'Polycom VVX 250', '88901M', 'Partes', 'FRU9012', 'quebrado', 'Mason Taylor', 'mason.taylor@example.com', 'Polycom', 'INV-013', 'Office desk phone.');
