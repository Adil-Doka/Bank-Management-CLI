CREATE DATABASE if not EXISTS Bank_Management;

use Bank_Management;

CREATE TABLE IF NOT EXISTS User_Data (
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    mobileNumber VARCHAR(15) not null,
    pin VARCHAR(6) not null,
    balance int default 0
) AUTO_INCREMENT = 1000;

-- SELECT *
-- FROM User_Data;
CREATE table if not EXISTS Account_Logs(
    account_number INT,
    DateAndTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount int NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (account_number) REFERENCES User_Data (account_number) ON DELETE CASCADE
);

CREATE TRIGGER create_log
AFTER
UPDATE ON User_Data FOR EACH ROW BEGIN
INSERT INTO Account_Logs (account_number, amount)
VALUES (NEW.account_number, NEW.balance - OLD.balance);

END;

CREATE INDEX search_By_acc ON User_Data (account_number);

CREATE INDEX searchLog_By_acc ON Account_Logs (account_number);

INSERT INTO User_Data (name, mobileNumber, password, balance)
VALUES ('Amit Sharma', '9876543210', 'am1234', 5000),
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