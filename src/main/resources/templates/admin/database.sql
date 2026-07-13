-- ================================================================
-- SAF A MITRA - Complete Database Schema
-- ================================================================
-- Author: Onkar Sawant
-- Description: Complete database for SafaiMitra Waste Management System
-- ================================================================

-- ================================================================
-- 1. CREATE DATABASE
-- ================================================================

DROP DATABASE IF EXISTS safaimitra_db;
CREATE DATABASE IF NOT EXISTS safaimitra_db;
USE safaimitra_db;

-- ================================================================
-- 2. USERS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    role VARCHAR(20) DEFAULT 'USER',
    address VARCHAR(255),
    profile_pic VARCHAR(255),
    gender VARCHAR(10),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- ================================================================
-- 3. BOOKINGS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS bookings (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        user_id BIGINT NOT NULL,
                                        service_type VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    preferred_date DATE NOT NULL,
    preferred_time TIME NOT NULL,
    priority VARCHAR(20) DEFAULT 'NORMAL',
    status VARCHAR(20) DEFAULT 'PENDING',
    instructions TEXT,
    gender VARCHAR(20),
    emergency_type VARCHAR(50),
    waste_type VARCHAR(50),
    description TEXT,
    quantity VARCHAR(50),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- ================================================================
-- 4. SERVICES TABLE (Subscription Plans)
-- ================================================================

CREATE TABLE IF NOT EXISTS services (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    duration VARCHAR(50),
    features TEXT,
    is_popular BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- ================================================================
-- 5. SUBSCRIPTIONS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS subscriptions (
                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             user_id BIGINT NOT NULL,
                                             service_id BIGINT NOT NULL,
                                             start_date DATE NOT NULL,
                                             end_date DATE NOT NULL,
                                             status VARCHAR(20) DEFAULT 'ACTIVE',
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
    );

-- ================================================================
-- 6. EMERGENCY REQUESTS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS emergency_requests (
                                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                                  user_id BIGINT NOT NULL,
                                                  emergency_type VARCHAR(50) NOT NULL,
    description TEXT,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    assigned_to VARCHAR(100),
    responded_at TIMESTAMP,
    resolved_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- ================================================================
-- 7. SPECIAL DISPOSAL REQUESTS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS special_disposal_requests (
                                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                                         user_id BIGINT NOT NULL,
                                                         waste_type VARCHAR(50) NOT NULL,
    description TEXT,
    quantity VARCHAR(50),
    address VARCHAR(255) NOT NULL,
    preferred_date DATE NOT NULL,
    preferred_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    disposal_date DATE,
    certificate_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- ================================================================
-- 8. TRUCKS TABLE (For Live Tracking)
-- ================================================================

CREATE TABLE IF NOT EXISTS trucks (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      vehicle_no VARCHAR(50) UNIQUE NOT NULL,
    driver_name VARCHAR(100) NOT NULL,
    driver_phone VARCHAR(20),
    current_lat DECIMAL(10,8),
    current_lng DECIMAL(11,8),
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    capacity VARCHAR(50),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- ================================================================
-- 9. CLEANERS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS cleaners (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    gender VARCHAR(10),
    rating DECIMAL(2,1) DEFAULT 0.0,
    experience_years INT DEFAULT 0,
    availability VARCHAR(20) DEFAULT 'AVAILABLE',
    service_type VARCHAR(50),
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- ================================================================
-- 10. BOOKING HISTORY TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS booking_history (
                                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                               booking_id BIGINT NOT NULL,
                                               user_id BIGINT NOT NULL,
                                               action VARCHAR(50) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- ================================================================
-- 11. FEEDBACK / REVIEWS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS feedbacks (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         user_id BIGINT NOT NULL,
                                         booking_id BIGINT,
                                         rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE SET NULL
    );

-- ================================================================
-- 12. NOTIFICATIONS TABLE
-- ================================================================

CREATE TABLE IF NOT EXISTS notifications (
                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             user_id BIGINT NOT NULL,
                                             title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- ================================================================
-- 13. INSERT SAMPLE DATA
-- ================================================================

-- 1. Insert Admin User (Password: admin123)
INSERT INTO users (full_name, email, password, phone, role, address, active) VALUES
    ('Admin', 'admin@safaimitra.com', '$2a$10$4VDUaUbzlvetjoZW1xLfEePf0M/MUGYSqmgFwpimSU.tgAXA21Woi', '9876543200', 'ADMIN', 'Rajgurunagar, Pune', TRUE);

-- 2. Insert Sample Users (Password: admin123)
INSERT INTO users (full_name, email, password, phone, role, address, gender, active) VALUES
                                                                                         ('Onkar Sawant', 'onkar@safaimitra.com', '$2a$10$4VDUaUbzlvetjoZW1xLfEePf0M/MUGYSqmgFwpimSU.tgAXA21Woi', '9876543201', 'USER', 'Rajgurunagar, Pune', 'Male', TRUE),
                                                                                         ('Sarang Shinde', 'sarang@safaimitra.com', '$2a$10$4VDUaUbzlvetjoZW1xLfEePf0M/MUGYSqmgFwpimSU.tgAXA21Woi', '9876543202', 'USER', 'Khed, Pune', 'Male', TRUE),
                                                                                         ('Priya Sharma', 'priya@safaimitra.com', '$2a$10$4VDUaUbzlvetjoZW1xLfEePf0M/MUGYSqmgFwpimSU.tgAXA21Woi', '9876543203', 'USER', 'Pune, Maharashtra', 'Female', TRUE);

-- 3. Insert Services (Subscription Plans)
INSERT INTO services (name, description, price, duration, features, is_popular) VALUES
                                                                                    ('Basic', 'Perfect for small households', 299.00, 'Monthly', '2 Pickups/month|Home Cleaning|Email Support', FALSE),
                                                                                    ('Premium', 'Best value for regular users', 599.00, 'Monthly', '8 Pickups/month|Home + Office Cleaning|Priority Support|GPS Tracking|Special Disposal', TRUE),
                                                                                    ('Enterprise', 'For businesses and large organizations', 999.00, 'Monthly', 'Unlimited Pickups|Full Service Package|24/7 Priority Support|Live GPS Tracking|Custom Reports', FALSE);

-- 4. Insert Trucks
INSERT INTO trucks (vehicle_no, driver_name, driver_phone, status, capacity, current_lat, current_lng) VALUES
                                                                                                           ('MH-14-1234', 'Rajesh Kumar', '9876543210', 'AVAILABLE', '5 Ton', 18.8660000, 73.8890000),
                                                                                                           ('MH-14-5678', 'Suresh Patil', '9876543211', 'ON_ROUTE', '3 Ton', 18.8670000, 73.8900000),
                                                                                                           ('MH-14-9012', 'Mahesh Shinde', '9876543212', 'AVAILABLE', '7 Ton', 18.8650000, 73.8880000);

-- 5. Insert Cleaners
INSERT INTO cleaners (name, phone, gender, rating, experience_years, availability, service_type, verified) VALUES
                                                                                                               ('Priya Sharma', '9876543213', 'Female', 4.5, 5, 'AVAILABLE', 'HOME_CLEANING', TRUE),
                                                                                                               ('Amit Kumar', '9876543214', 'Male', 4.2, 3, 'AVAILABLE', 'DRAIN_CLEANING', TRUE),
                                                                                                               ('Sneha Patil', '9876543215', 'Female', 4.8, 7, 'AVAILABLE', 'EVENT_CLEANING', TRUE);

-- 6. Insert Sample Bookings
INSERT INTO bookings (user_id, service_type, address, preferred_date, preferred_time, priority, status, instructions) VALUES
                                                                                                                          (2, 'HOME_CLEANING', 'Rajgurunagar, Pune', CURDATE(), '10:00:00', 'NORMAL', 'PENDING', 'Need deep cleaning of 2BHK flat'),
                                                                                                                          (2, 'DRAIN_CLEANING', 'Rajgurunagar, Pune', CURDATE() + INTERVAL 1 DAY, '14:00:00', 'URGENT', 'PENDING', 'Kitchen drain blocked'),
                                                                                                                          (3, 'EVENT_CLEANING', 'Khed, Pune', CURDATE() + INTERVAL 2 DAY, '09:00:00', 'NORMAL', 'COMPLETED', 'Wedding event cleanup'),
                                                                                                                          (4, 'HOME_CLEANING', 'Pune, Maharashtra', CURDATE() + INTERVAL 3 DAY, '11:00:00', 'NORMAL', 'PENDING', 'Regular home cleaning');

-- 7. Insert Cleaner Service Bookings
INSERT INTO bookings (user_id, service_type, address, preferred_date, preferred_time, priority, status, gender, phone) VALUES
                                                                                                                           (2, 'CLEANER_SERVICE', 'Rajgurunagar, Pune', CURDATE() + INTERVAL 1 DAY, '15:00:00', 'NORMAL', 'PENDING', 'Female', '9876543201'),
                                                                                                                           (3, 'CLEANER_SERVICE', 'Khed, Pune', CURDATE() + INTERVAL 2 DAY, '10:00:00', 'URGENT', 'PENDING', 'Male', '9876543202');

-- 8. Insert Emergency Bookings
INSERT INTO bookings (user_id, service_type, emergency_type, description, address, phone, status, priority) VALUES
                                                                                                                (2, 'EMERGENCY', 'BIOHAZARD', 'Chemical spill in laboratory', 'Rajgurunagar, Pune', '9876543201', 'PENDING', 'URGENT'),
                                                                                                                (3, 'EMERGENCY', 'BLOCKAGE', 'Severe drain blockage', 'Khed, Pune', '9876543202', 'COMPLETED', 'URGENT'),
                                                                                                                (4, 'EMERGENCY', 'AMBULANCE', 'Medical emergency', 'Pune, Maharashtra', '9876543203', 'PENDING', 'URGENT');

-- 9. Insert Special Disposal Bookings
INSERT INTO bookings (user_id, service_type, waste_type, description, quantity, address, preferred_date, preferred_time, status) VALUES
                                                                                                                                     (2, 'SPECIAL_DISPOSAL', 'E-WASTE', 'Old laptops and mobile phones', 'Medium', 'Rajgurunagar, Pune', CURDATE() + INTERVAL 3 DAY, '11:00:00', 'PENDING'),
                                                                                                                                     (3, 'SPECIAL_DISPOSAL', 'MEDICAL', 'Used syringes and medical waste', 'Small', 'Khed, Pune', CURDATE() + INTERVAL 1 DAY, '15:00:00', 'PENDING'),
                                                                                                                                     (4, 'SPECIAL_DISPOSAL', 'RECYCLE', 'Paper and plastic waste', 'Large', 'Pune, Maharashtra', CURDATE() + INTERVAL 5 DAY, '09:00:00', 'COMPLETED');

-- 10. Insert Sample Feedbacks
INSERT INTO feedbacks (user_id, booking_id, rating, comment) VALUES
                                                                 (2, 3, 5, 'Excellent service! Very professional and timely.'),
                                                                 (3, 3, 4, 'Good service, but could be faster.'),
                                                                 (4, 9, 5, 'Great disposal service! Very eco-friendly.');

-- 11. Insert Sample Notifications
INSERT INTO notifications (user_id, title, message, type) VALUES
                                                              (2, 'Booking Confirmed', 'Your booking for Home Cleaning has been confirmed for 10:00 AM', 'BOOKING'),
                                                              (2, 'Payment Reminder', 'Your subscription payment is due in 3 days', 'PAYMENT'),
                                                              (3, 'Service Completed', 'Your event cleaning service has been completed successfully', 'SERVICE'),
                                                              (4, 'Booking Confirmed', 'Your special disposal request has been confirmed', 'BOOKING');

-- ================================================================
-- 14. VERIFY DATA
-- ================================================================

SELECT '✅ USERS' AS 'Table', COUNT(*) AS 'Count' FROM users
UNION ALL
SELECT '✅ BOOKINGS', COUNT(*) FROM bookings
UNION ALL
SELECT '✅ SERVICES', COUNT(*) FROM services
UNION ALL
SELECT '✅ TRUCKS', COUNT(*) FROM trucks
UNION ALL
SELECT '✅ CLEANERS', COUNT(*) FROM cleaners
UNION ALL
SELECT '✅ EMERGENCY REQUESTS', COUNT(*) FROM emergency_requests
UNION ALL
SELECT '✅ SPECIAL DISPOSAL', COUNT(*) FROM special_disposal_requests
UNION ALL
SELECT '✅ FEEDBACKS', COUNT(*) FROM feedbacks
UNION ALL
SELECT '✅ NOTIFICATIONS', COUNT(*) FROM notifications;

-- ================================================================
-- 15. SHOW ALL USERS WITH DETAILS
-- ================================================================

SELECT id, full_name, email, phone, role, gender, active, created_at FROM users;

-- ================================================================
-- 16. SHOW ALL BOOKINGS WITH USER DETAILS
-- ================================================================

SELECT
    b.id,
    u.full_name AS user_name,
    b.service_type,
    b.address,
    b.preferred_date,
    b.preferred_time,
    b.priority,
    b.status,
    b.created_at
FROM bookings b
         JOIN users u ON b.user_id = u.id
ORDER BY b.created_at DESC;

-- ================================================================
-- 17. SHOW BOOKINGS BY CATEGORY
-- ================================================================

-- Schedule Pickups
SELECT * FROM bookings WHERE service_type IN ('HOME_CLEANING', 'DRAIN_CLEANING', 'EVENT_CLEANING', 'OFFICE_CLEANING');

-- Cleaner Services
SELECT * FROM bookings WHERE service_type = 'CLEANER_SERVICE';

-- Emergency
SELECT * FROM bookings WHERE service_type = 'EMERGENCY';

-- Special Disposal
SELECT * FROM bookings WHERE service_type = 'SPECIAL_DISPOSAL';

-- ================================================================
-- 18. STATISTICS QUERIES
-- ================================================================

-- Total Users
SELECT COUNT(*) AS total_users FROM users;

-- Total Bookings
SELECT COUNT(*) AS total_bookings FROM bookings;

-- Pending Bookings
SELECT COUNT(*) AS pending_bookings FROM bookings WHERE status = 'PENDING';

-- Completed Bookings
SELECT COUNT(*) AS completed_bookings FROM bookings WHERE status = 'COMPLETED';

-- Cancelled Bookings
SELECT COUNT(*) AS cancelled_bookings FROM bookings WHERE status = 'CANCELLED';

-- Bookings by Service Type
SELECT service_type, COUNT(*) AS count FROM bookings GROUP BY service_type;

-- Bookings by Status
SELECT status, COUNT(*) AS count FROM bookings GROUP BY status;

-- ================================================================
-- 19. DROP ALL TABLES (For Clean Reset)
-- ================================================================

-- DROP TABLE IF EXISTS notifications;
-- DROP TABLE IF EXISTS feedbacks;
-- DROP TABLE IF EXISTS booking_history;
-- DROP TABLE IF EXISTS cleaners;
-- DROP TABLE IF EXISTS trucks;
-- DROP TABLE IF EXISTS special_disposal_requests;
-- DROP TABLE IF EXISTS emergency_requests;
-- DROP TABLE IF EXISTS subscriptions;
-- DROP TABLE IF EXISTS services;
-- DROP TABLE IF EXISTS bookings;
-- DROP TABLE IF EXISTS users;

-- ================================================================
-- 20. END OF SCHEMA
-- ================================================================