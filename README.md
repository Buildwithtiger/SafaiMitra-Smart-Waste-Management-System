# SafaiMitra-Smart-Waste-Management-System
## 🚀 Complete Project Setup Guide - README.md

**Location:** `safaimitra/README.md`

---

## 📝 Complete README.md File

```markdown
# 🗑️ SafaiMitra - Smart Waste Management System

<div align="center">
  <img src="https://img.icons8.com/fluency/200/recycle.png" alt="SafaiMitra Logo">
  <h3>A Complete Waste Management & Cleaning Service Platform</h3>
  <p>Built with Spring Boot | Java | MySQL | Thymeleaf</p>
</div>

---

## 📋 Table of Contents

- [About The Project](#about-the-project)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Screenshots](#screenshots)
- [Contributors](#contributors)

---

## 📖 About The Project

**SafaiMitra** is a comprehensive waste management and cleaning service platform designed to connect citizens, cleaning workers, and municipal authorities. The system provides real-time tracking of garbage disposal trucks, professional cleaning services booking, and an admin dashboard for complete management.

### 🎯 Core Objectives

- 🚛 Real-time tracking of garbage collection vehicles
- 📅 Online booking for cleaning services
- 👑 Admin dashboard for complete management
- 📱 User-friendly interface for citizens
- ♻️ Promote sustainable waste management practices

---

## ✨ Features

### 👤 User Features

| Feature | Description |
| :--- | :--- |
| **User Registration/Login** | Secure authentication with Spring Security |
| **Schedule Pickup** | Book waste pickup with date/time selection |
| **Request Cleaner** | Book professional cleaning services |
| **Emergency Services** | 🚑 Free ambulance & 🔥 free fire brigade |
| **Special Disposal** | E-waste, medical, construction waste disposal |
| **My Schedule** | View and manage all bookings |
| **Profile Management** | Update profile and upload photo |
| **Cancel/Edit Bookings** | Modify or cancel pending bookings |
| **Real-Time GPS Tracking** | Track garbage vehicles on live map |
| **Multi-Language Support** | English, Hindi, Marathi |
| **AI Chatbot** | 24/7 virtual assistant for help |

### 👑 Admin Features

| Feature | Description |
| :--- | :--- |
| **Dashboard** | View statistics (Users, Bookings, Pending, Trucks) |
| **Manage Users** | View, delete users with profiles |
| **Manage Bookings** | View all bookings category wise |
| **Approve/Reject Bookings** | Admin approval system |
| **Category-wise Filtering** | Filter by service type |
| **Search & Filter** | Search any booking |

---

## 🛠️ Tech Stack

### Backend
| Technology | Version | Purpose |
| :--- | :--- | :--- |
| Java | 17 | Core programming language |
| Spring Boot | 3.1.5 | Framework |
| Spring Security | 6.1.5 | Authentication & Authorization |
| Spring Data JPA | 3.1.5 | Database operations |
| Hibernate | 6.2.13 | ORM |

### Frontend
| Technology | Version | Purpose |
| :--- | :--- | :--- |
| Thymeleaf | 3.1.2 | Template engine |
| Bootstrap | 5.3.0 | UI framework |
| Font Awesome | 6.5.0 | Icons |
| AOS.js | 2.3.1 | Scroll animations |
| Google Maps API | - | Live map integration |

### Database
| Technology | Version | Purpose |
| :--- | :--- | :--- |
| MySQL | 8.x | Relational database |

### Tools
| Tool | Purpose |
| :--- | :--- |
| IntelliJ IDEA | IDE |
| Maven | Build tool |
| XAMPP | MySQL server |
| Git | Version control |

---

## 📋 Prerequisites

### Software Requirements

1. **Java JDK 17**
   ```
   Download: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
   ```

2. **IntelliJ IDEA Community Edition**
   ```
   Download: https://www.jetbrains.com/idea/download/
   ```

3. **XAMPP** (or MySQL 8.x)
   ```
   Download: https://www.apachefriends.org/
   ```

4. **Maven** (Built-in IntelliJ)
   ```
   Download: https://maven.apache.org/download.cgi
   ```

5. **Git** (Optional)
   ```
   Download: https://git-scm.com/downloads
   ```

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/your-username/safaimitra.git
cd safaimitra
```

Or download the ZIP and extract.

---

### Step 2: Open Project in IntelliJ IDEA

1. **Open IntelliJ IDEA**
2. Click **File → Open**
3. Select the `safaimitra` folder
4. Click **OK**

---

### Step 3: Configure Java JDK

1. **File → Project Structure** (⌘ + ; or Ctrl + Shift + Alt + S)
2. **Project Settings → Project**
3. **SDK:** Select `17 (jdk-17.jdk)`
4. **Language Level:** `17 - Sealed types`
5. Click **Apply → OK**

---

### Step 4: Setup MySQL Database

#### 4.1 Start XAMPP/MySQL

```bash
# Open XAMPP Control Panel
# Click "Start" on MySQL
```

#### 4.2 Create Database

```sql
CREATE DATABASE IF NOT EXISTS safaimitra_db;
```

#### 4.3 Update Database Configuration

**File:** `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/safaimitra_db?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=          # Your MySQL password (leave blank if none)
```

---

### Step 5: Maven Build

#### Option A: IntelliJ Built-in Maven

1. **Right side** → **Maven** tab (🐘 icon)
2. **Lifecycle → clean** double-click
3. **Lifecycle → install** double-click

#### Option B: Terminal

```bash
mvn clean install -DskipTests
```

---

### Step 6: Add VM Options (Port Configuration)

#### IntelliJ Run Configuration

1. **Run → Edit Configurations**
2. **Spring Boot → SafaimitraApplication**
3. **VM options** add karo:
   ```
   -Dserver.port=8080
   ```
4. **Apply → OK**

> 💡 **Note:** If port 8080 is busy, change to 8081:
> ```
> -Dserver.port=8081
> ```

---

### Step 7: Run the Application

#### Option A: IntelliJ Run

1. Open `SafaiMitraApplication.java`
2. Click **Green Play Button** (▶️)
3. Wait for: `Started SafaimitraApplication in X seconds`

#### Option B: Terminal Run

```bash
mvn spring-boot:run
```

#### Option C: Run on Different Port

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

---

### Step 8: Verify Application

| Component | Status |
| :--- | :--- |
| ✅ Application Started | http://localhost:8080 |
| ✅ Database Connected | MySQL on port 3306 |
| ✅ JPA Initialized | Tables created automatically |

---

## 🌐 Access the Application

### 🔑 Default URLs

| Page | URL |
| :--- | :--- |
| **Homepage** | `http://localhost:8080/` |
| **Register** | `http://localhost:8080/register` |
| **Login** | `http://localhost:8080/login` |
| **Admin Register** | `http://localhost:8080/register-admin` |
| **Admin Dashboard** | `http://localhost:8080/admin/dashboard` |
| **Schedule Pickup** | `http://localhost:8080/schedule` |
| **Request Cleaner** | `http://localhost:8080/cleaner/request` |
| **Emergency** | `http://localhost:8080/emergency` |
| **Special Disposal** | `http://localhost:8080/disposal/special` |
| **Subscription** | `http://localhost:8080/subscription` |
| **My Schedule** | `http://localhost:8080/my-schedule` |
| **Profile** | `http://localhost:8080/profile` |

---

### 🔑 Login Credentials

#### Admin Account

| Field | Value |
| :--- | :--- |
| **Email** | `admin@safaimitra.com` |
| **Password** | `admin123` |

#### User Account

| Field | Value |
| :--- | :--- |
| **Email** | `user@safaimitra.com` |
| **Password** | `user123` |

#### Create Admin from Browser

```
http://localhost:8080/create-admin
```

Or register as admin:
```
http://localhost:8080/register-admin
```

---

## 📁 Project Structure

```
safaimitra/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── safaimitra/
│   │   │           ├── SafaiMitraApplication.java
│   │   │           ├── controller/
│   │   │           │   ├── AuthController.java
│   │   │           │   ├── UserController.java
│   │   │           │   ├── BookingController.java
│   │   │           │   └── AdminController.java
│   │   │           ├── service/
│   │   │           │   ├── UserService.java
│   │   │           │   └── BookingService.java
│   │   │           ├── repository/
│   │   │           │   ├── UserRepository.java
│   │   │           │   └── BookingRepository.java
│   │   │           ├── model/
│   │   │           │   ├── User.java
│   │   │           │   └── Booking.java
│   │   │           └── config/
│   │   │               └── SecurityConfig.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   ├── style.css
│   │       │   │   └── admin.css
│   │       │   ├── js/
│   │       │   └── images/
│   │       └── templates/
│   │           ├── fragments/
│   │           │   ├── navbar.html
│   │           │   └── footer.html
│   │           ├── auth/
│   │           │   ├── login.html
│   │           │   ├── register.html
│   │           │   └── register-admin.html
│   │           ├── user/
│   │           │   ├── index.html
│   │           │   ├── profile.html
│   │           │   ├── schedule-pickup.html
│   │           │   ├── request-cleaner.html
│   │           │   ├── emergency.html
│   │           │   ├── special-disposal.html
│   │           │   ├── subscription.html
│   │           │   ├── my-schedule.html
│   │           │   └── edit-booking.html
│   │           └── admin/
│   │               ├── dashboard.html
│   │               ├── users.html
│   │               └── bookings.html
│   └── test/
└── pom.xml
```

---

## 🗄️ Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'USER',
    address VARCHAR(255),
    profile_pic VARCHAR(255),
    gender VARCHAR(10),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Bookings Table

```sql
CREATE TABLE bookings (
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
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## 📊 API Endpoints

### Auth Endpoints

| Method | URL | Description |
| :--- | :--- | :--- |
| GET | `/register` | Show register page |
| POST | `/register` | Register new user |
| GET | `/register-admin` | Show admin register page |
| POST | `/register-admin` | Register new admin |
| GET | `/login` | Show login page |

### User Endpoints

| Method | URL | Description |
| :--- | :--- | :--- |
| GET | `/` | Homepage |
| GET | `/profile` | User profile |
| POST | `/profile` | Update profile |

### Booking Endpoints

| Method | URL | Description |
| :--- | :--- | :--- |
| GET | `/schedule` | Schedule pickup form |
| POST | `/schedule` | Submit pickup |
| GET | `/cleaner/request` | Request cleaner form |
| POST | `/cleaner/request` | Submit cleaner request |
| GET | `/emergency` | Emergency form |
| POST | `/emergency` | Submit emergency |
| GET | `/disposal/special` | Special disposal form |
| POST | `/disposal/special` | Submit disposal |
| GET | `/subscription` | Subscription plans |
| GET | `/my-schedule` | User's bookings |
| GET | `/booking/cancel/{id}` | Cancel booking |
| GET | `/booking/edit/{id}` | Edit booking form |
| POST | `/booking/edit/{id}` | Update booking |

### Admin Endpoints

| Method | URL | Description |
| :--- | :--- | :--- |
| GET | `/admin/dashboard` | Admin dashboard |
| GET | `/admin/users` | Manage users |
| GET | `/admin/bookings` | Manage bookings |
| GET | `/admin/approve/{id}` | Approve booking |
| GET | `/admin/reject/{id}` | Reject booking |
| GET | `/admin/delete-user/{id}` | Delete user |

---

## 🚨 Troubleshooting

### Common Errors & Solutions

#### Error: "Port 8080 already in use"

**Solution:** Change port in VM options:
```
-Dserver.port=8081
```

#### Error: "Unknown database 'safaimitra_db'"

```sql
CREATE DATABASE IF NOT EXISTS safaimitra_db;
```

#### Error: "Access denied for user 'root'@'localhost'"

**Solution:** Update password in `application.properties`:
```properties
spring.datasource.password=your_password
```

#### Error: "Maven build failed"

```bash
mvn clean install -DskipTests
```

#### Error: "Thymeleaf template not found"

**Solution:** Check template location:
```
src/main/resources/templates/page.html
```

#### Error: "File upload failed"

**Solution:** Create upload directory:
```bash
mkdir -p src/main/resources/static/uploads/profile
```

---

## 📸 Screenshots

### Homepage
![Homepage](https://via.placeholder.com/800x400/2d3436/28a745?text=SafaiMitra+Homepage)

### Login Page
![Login Page](https://via.placeholder.com/800x400/2d3436/28a745?text=Login+Page)

### Admin Dashboard
![Admin Dashboard](https://via.placeholder.com/800x400/2d3436/28a745?text=Admin+Dashboard)

### Profile Page
![Profile Page](https://via.placeholder.com/800x400/2d3436/28a745?text=Profile+Page)

---

## 👥 Contributors

- **Onkar Sawant** - Developer
- **Sarang Shinde** - Developer

---

## 📄 License

This project is for educational purposes.

---

## 🙏 Acknowledgements

- Spring Boot Team
- Thymeleaf Team
- Bootstrap Team
- Google Maps Platform

---

## 📞 Contact

For any queries, contact:
- Email: info@safaimitra.com
- Phone: +91 98765 43210

---

<div align="center">
  Made with ❤️ for a cleaner planet
</div>
```

---

## 🚀 Quick Start Commands

```bash
# Clone repository
git clone https://github.com/your-username/safaimitra.git
cd safaimitra

# Build project
mvn clean install -DskipTests

# Run project
mvn spring-boot:run

# Run on different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```
## 🌐 Complete SafaiMitra Application URLs

---

## 🔐 Authentication Pages

| Page | URL | Description |
| :--- | :--- | :--- |
| Login | `http://localhost:8080/login` | User & Admin Login |
| Register | `http://localhost:8080/register` | New User Registration |
| Admin Register | `http://localhost:8080/register-admin` | New Admin Registration |
| Create Admin (Quick) | `http://localhost:8080/create-admin` | Quick Admin Creation |
| Logout | `http://localhost:8080/logout` | Logout User/Admin |

---

## 🏠 User Pages

| Page | URL | Description |
| :--- | :--- | :--- |
| Homepage | `http://localhost:8080/` | Main Dashboard |
| Profile | `http://localhost:8080/profile` | User Profile Management |
| My Schedule | `http://localhost:8080/my-schedule` | View All Bookings |
| Edit Booking | `http://localhost:8080/booking/edit/{id}` | Edit Specific Booking |
| Cancel Booking | `http://localhost:8080/booking/cancel/{id}` | Cancel Specific Booking |

---

## 📅 Booking Service Pages

| Page | URL | Description |
| :--- | :--- | :--- |
| Schedule Pickup | `http://localhost:8080/schedule` | Book Waste Pickup |
| Request Cleaner | `http://localhost:8080/cleaner/request` | Book Cleaning Service |
| Emergency | `http://localhost:8080/emergency` | Emergency Services (Ambulance/Fire) |
| Special Disposal | `http://localhost:8080/disposal/special` | Special Waste Disposal |
| Subscription | `http://localhost:8080/subscription` | View Subscription Plans |

---

## 👑 Admin Pages

| Page | URL | Description |
| :--- | :--- | :--- |
| Admin Dashboard | `http://localhost:8080/admin/dashboard` | Admin Main Dashboard |
| Manage Users | `http://localhost:8080/admin/users` | View All Users |
| Manage Bookings | `http://localhost:8080/admin/bookings` | View All Bookings |
| Approve Booking | `http://localhost:8080/admin/approve/{id}` | Approve Specific Booking |
| Reject Booking | `http://localhost:8080/admin/reject/{id}` | Reject Specific Booking |
| Delete User | `http://localhost:8080/admin/delete-user/{id}` | Delete Specific User |
| User Details (AJAX) | `http://localhost:8080/admin/user/{id}` | View User Profile (JSON) |

---

## 📝 Complete URL List (Copy-Paste Ready)

```
============================================================
🔐 AUTHENTICATION PAGES
============================================================
Login:               http://localhost:8080/login
Register:            http://localhost:8080/register
Admin Register:      http://localhost:8080/register-admin
Create Admin:        http://localhost:8080/create-admin
Logout:              http://localhost:8080/logout

============================================================
🏠 USER PAGES
============================================================
Homepage:            http://localhost:8080/
Profile:             http://localhost:8080/profile
My Schedule:         http://localhost:8080/my-schedule

============================================================
📅 BOOKING PAGES
============================================================
Schedule Pickup:     http://localhost:8080/schedule
Request Cleaner:     http://localhost:8080/cleaner/request
Emergency:           http://localhost:8080/emergency
Special Disposal:    http://localhost:8080/disposal/special
Subscription:        http://localhost:8080/subscription

============================================================
👑 ADMIN PAGES
============================================================
Admin Dashboard:     http://localhost:8080/admin/dashboard
Manage Users:        http://localhost:8080/admin/users
Manage Bookings:     http://localhost:8080/admin/bookings

============================================================
🔧 DYNAMIC PAGES (Replace {id} with actual ID)
============================================================
Edit Booking:        http://localhost:8080/booking/edit/1
Cancel Booking:      http://localhost:8080/booking/cancel/1
Approve Booking:     http://localhost:8080/admin/approve/1
Reject Booking:      http://localhost:8080/admin/reject/1
Delete User:         http://localhost:8080/admin/delete-user/1
View User Details:   http://localhost:8080/admin/user/1
```

---

## 🔑 Login Credentials

| Role | Email | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@safaimitra.com` | `admin123` |
| **User** | `onkar@safaimitra.com` | `admin123` |
| **User** | `sarang@safaimitra.com` | `admin123` |
| **User** | `priya@safaimitra.com` | `admin123` |

---

## 🚀 How to Use Dynamic URLs (with ID)

### Example 1: Edit Booking
```
http://localhost:8080/booking/edit/1
```
Yahan `1` booking ID hai.

### Example 2: Cancel Booking
```
http://localhost:8080/booking/cancel/1
```
Yahan `1` booking ID hai.

### Example 3: Admin Approve/Reject
```
http://localhost:8080/admin/approve/1
http://localhost:8080/admin/reject/1
```
Yahan `1` booking ID hai.

---

## 💡 Quick Access

| Page | Quick URL |
| :--- | :--- |
| Admin Dashboard | `http://localhost:8080/admin/dashboard` |
| Login | `http://localhost:8080/login` |
| Register | `http://localhost:8080/register` |
| Homepage | `http://localhost:8080/` |
| My Schedule | `http://localhost:8080/my-schedule` |

---

**Ab browser mein in URLs ko directly paste karo! 😊**
