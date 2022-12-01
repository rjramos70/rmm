// Operational System Families
INSERT INTO op_family(name, created_by, created_on, updated_by, updated_on) VALUES ('Windows OS', 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO op_family(name, created_by, created_on, updated_by, updated_on) VALUES ('Mac OS', 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO op_family(name, created_by, created_on, updated_by, updated_on) VALUES ('Unix OS', 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);


// Service types
INSERT INTO types(description, op_id, created_by,created_on,updated_by,updated_on) VALUES ('Windows Workstation', 1, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO types(description, op_id, created_by,created_on,updated_by,updated_on) VALUES ('Windows Server', 1, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO types(description, op_id, created_by,created_on,updated_by,updated_on) VALUES ('Mac', 2, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO types(description, op_id, created_by,created_on,updated_by,updated_on) VALUES ('Unix', 2, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);

// Device
INSERT INTO device(system_name, qtd, type_id,created_by,created_on,updated_by,updated_on) VALUES ('WINDOWS_001', 2, 1, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO device(system_name, qtd, type_id,created_by,created_on,updated_by,updated_on) VALUES ('MAC_001', 3, 3, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO device(system_name, qtd, type_id,created_by,created_on,updated_by,updated_on) VALUES ('WINDOWS_002', 1, 3, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);

// Service
INSERT INTO services(description, created_by, created_on, updated_by, updated_on) VALUES ('Antivirus', 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO services(description, created_by, created_on, updated_by, updated_on) VALUES ('Backup', 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO services(description, created_by, created_on, updated_by, updated_on) VALUES ('PSA',  'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO services(description, created_by, created_on, updated_by, updated_on) VALUES ('Screen Share', 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO services(description, created_by, created_on, updated_by, updated_on) VALUES ('Device','renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO services(description, created_by, created_on, updated_by, updated_on) VALUES ('Service XPTO','renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);

// Price per service
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (1, 1, 5, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (2, 1, 7, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (1, 2, 3, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (2, 2, 3, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (1, 3, 2, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (2, 3, 2, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (1, 4, 1, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (2, 4, 1, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (1, 5, 4, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO price_per_service(op_id, service_id, price, created_by,created_on,updated_by,updated_on) VALUES (2, 5, 4, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);

// Customer
INSERT INTO customers(name, total_cost, created_by,created_on,updated_by,updated_on) VALUES ('Marco Aurelio', 0, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO customers(name, total_cost, created_by,created_on,updated_by,updated_on) VALUES ('Luiz Mathues', 0, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);
INSERT INTO customers(name, total_cost, created_by,created_on,updated_by,updated_on) VALUES ('Osar Ramires', 0, 'renato', CURRENT_TIMESTAMP, 'renato', CURRENT_TIMESTAMP);

// CUSTOMERS_SERVICES
INSERT INTO customers_services(customer_id, services_id) VALUES (1, 1);

// CUSTOMERS_DEVICES
INSERT INTO customers_devices(customer_id, devices_id) VALUES (1, 1);


