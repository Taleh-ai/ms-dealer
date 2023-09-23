-- Table for ProductEntity
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR,
                          brand VARCHAR,
                          description TEXT,
                          quantity INT,
                          price DOUBLE PRECISION,
                          category_id INT REFERENCES products_category(id),
                          dealer_id INT REFERENCES stakeholders(id),
                          creation_date TIMESTAMP,
                          update_date TIMESTAMP,
                          created_by VARCHAR,
                          last_updated_by VARCHAR
);