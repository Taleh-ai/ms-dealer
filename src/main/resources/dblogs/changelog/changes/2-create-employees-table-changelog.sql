-- Table for EmployeEntity
CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR,
                           surname VARCHAR,
                           role VARCHAR,
                           email VARCHAR,
                           password VARCHAR,
                           dealer_id INT REFERENCES stakeholders(id),
                           creation_date TIMESTAMP,
                           update_date TIMESTAMP,
                           created_by VARCHAR,
                           last_updated_by VARCHAR
);