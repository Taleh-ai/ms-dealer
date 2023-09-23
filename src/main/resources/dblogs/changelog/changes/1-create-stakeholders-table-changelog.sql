-- Table for DealerEntity
CREATE TABLE stakeholders (
                              id SERIAL PRIMARY KEY,
                              company_name VARCHAR,
                              category VARCHAR,
                              location VARCHAR,
                              contact_number VARCHAR,
                              email VARCHAR,
                              password VARCHAR,
                              role VARCHAR,
                              creation_date TIMESTAMP,
                              update_date TIMESTAMP
);