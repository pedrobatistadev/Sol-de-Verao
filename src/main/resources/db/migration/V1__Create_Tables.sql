
CREATE TABLE Categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            description VARCHAR(45) NOT NULL
);

CREATE TABLE Inventories (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             description VARCHAR(100) NOT NULL
);

CREATE TABLE Customers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(45) NOT NULL,
                           cpf VARCHAR(45) NOT NULL,
                           phone VARCHAR(45),
                           credit BIT(1) NOT NULL DEFAULT b'0',
                           type VARCHAR(20) NOT NULL,
                           enabled BIT(1) NOT NULL DEFAULT b'1',
                           date_birth DATE,
                           creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT uk_customers_cpf UNIQUE (cpf)
);

CREATE TABLE Customers_Log (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              customer_id BIGINT NOT NULL,
                              action VARCHAR(255) NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              PRIMARY KEY (id),

                              CONSTRAINT fk_customers_log_customers
                                  FOREIGN KEY (customer_id)
                                      REFERENCES Customers(id)
);


CREATE TABLE Employees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(45) NOT NULL,
                           cpf VARCHAR(45) NOT NULL,
                           phone VARCHAR(45),
                           enabled BIT(1) NOT NULL DEFAULT b'1',
                           date_birth DATE,
                           creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT uk_employees_cpf UNIQUE (cpf)
);

CREATE TABLE Products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(45) NOT NULL,
                          category_id BIGINT NOT NULL,
                          unit_price DECIMAL(10,2) NOT NULL,
                          quantity INT NOT NULL,
                          inventory_id BIGINT NOT NULL,
                          enabled BIT(1) NOT NULL DEFAULT b'1',
                          creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_products_categories
                              FOREIGN KEY (category_id)
                                  REFERENCES Categories(id),

                          CONSTRAINT fk_products_inventories
                              FOREIGN KEY (inventory_id)
                                  REFERENCES Inventories(id)
);

CREATE TABLE Products_Log (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              product_id BIGINT NOT NULL,
                              action VARCHAR(255) NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              PRIMARY KEY (id),

                              CONSTRAINT fk_products_log_product
                                  FOREIGN KEY (product_id)
                                      REFERENCES products(id)
);

CREATE TABLE Sales (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       customer_id BIGINT NOT NULL,
                       employee_id BIGINT NOT NULL,
                       payment_method VARCHAR(20) NOT NULL,
                       number_of_installment INT NOT NULL,
                       creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_sales_customer
                           FOREIGN KEY (customer_id)
                               REFERENCES Customers(id),

                       CONSTRAINT fk_sales_employee
                           FOREIGN KEY (employee_id)
                               REFERENCES Employees(id)
);

CREATE TABLE Purchases (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           customer_id BIGINT NOT NULL,
                           employee_id BIGINT NOT NULL,
                           payment_method VARCHAR(20) NOT NULL,
                           number_of_installment INT NOT NULL,
                           creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_purchases_customer
                               FOREIGN KEY (customer_id)
                                   REFERENCES Customers(id),

                           CONSTRAINT fk_purchases_employee
                               FOREIGN KEY (employee_id)
                                   REFERENCES Employees(id)
);

CREATE TABLE Products_Sales (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                sale_id BIGINT NOT NULL,
                                product_id BIGINT NOT NULL,
                                quantity INT NOT NULL,
                                total_value DECIMAL(10,2) NOT NULL,

                                CONSTRAINT fk_products_sales_sale
                                    FOREIGN KEY (sale_id)
                                        REFERENCES Sales(id),

                                CONSTRAINT fk_products_sales_product
                                    FOREIGN KEY (product_id)
                                        REFERENCES Products(id)
);

CREATE TABLE Products_Purchases (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    purchase_id BIGINT NOT NULL,
                                    product_id BIGINT NOT NULL,
                                    quantity INT NOT NULL,
                                    total_value DECIMAL(10,2) NOT NULL,

                                    CONSTRAINT fk_products_purchases_purchase
                                        FOREIGN KEY (purchase_id)
                                            REFERENCES Purchases(id),

                                    CONSTRAINT fk_products_purchases_product
                                        FOREIGN KEY (product_id)
                                            REFERENCES Products(id)
);

CREATE TABLE Accounts_Receivable (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     sale_id BIGINT NOT NULL,
                                     installment_number INT NOT NULL,
                                     payment DATE,
                                     total_value DECIMAL(10,2) NOT NULL,
                                     due_date DATE NOT NULL,
                                     creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                     CONSTRAINT fk_accounts_receivable_sale
                                         FOREIGN KEY (sale_id)
                                             REFERENCES Sales(id)
);

CREATE TABLE Accounts_Payable (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  purchase_id BIGINT NOT NULL,
                                  installment_number INT NOT NULL,
                                  payment DATE,
                                  total_value DECIMAL(10,2) NOT NULL,
                                  due_date DATE NOT NULL,
                                  creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT fk_accounts_payable_purchase
                                      FOREIGN KEY (purchase_id)
                                          REFERENCES Purchases(id)
);