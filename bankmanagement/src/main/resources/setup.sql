-- data base setup script for the project 

CREATE DATABASE if not EXISTS bank_management;

use bank_management;

CREATE TABLE IF NOT EXISTS user_data (
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    mobile_number VARCHAR(15) not null,
    pin VARCHAR(6) not null,
    balance DECIMAL(8, 2) default 0.00
) AUTO_INCREMENT = 1000;

-- SELECT *
-- FROM User_Data;
CREATE table if not EXISTS account_logs(
    account_number INT,
    date_and_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount int NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (account_number) REFERENCES user_data (account_number) ON DELETE CASCADE
);

CREATE TRIGGER create_log
AFTER
UPDATE ON user_data FOR EACH ROW BEGIN
INSERT INTO account_logs (account_number, amount)
VALUES (NEW.account_number, NEW.balance - OLD.balance);

END;

CREATE INDEX search_By_acc ON user_data (account_number);

CREATE INDEX searchLog_By_acc ON account_logs (account_number);

-- some sample data 
INSERT INTO user_data (name, mobile_number, pin, balance)
VALUES (
        'Amit Sharma',
        '9876543210',
        'am1234',
        5000
    ),
    (
        'Priya Desai',
        '9123456789',
        'pr4567',
        12000
    ),
    ('Rahul Mehta', '9988776655', 'ra7890', 2500),
    ('Sneha Kulkarni', '9012345678', 'sn1111', 800),
    (
        'Arjun Reddy',
        '9765432109',
        'ar2222',
        35000
    ),
    (
        'Fatima Khan',
        '9098765432',
        'fk3333',
        15000
    ),
    ('Vikram Patel', '9345678901', 'vp4444', 650),
    ('Neha Gupta', '9456789012', 'ng5555', 4200),
    ('Ravi Joshi', '9567890123', 'rj6666', 9999),
    ('Ananya Nair', '9678901234', 'an7777', 2000);