# Task Management System 🚀

Welcome to the **Task Management System**! 🎯 This powerful backend API helps you manage projects, tasks, users, and track activity logs seamlessly. Built with **Java Spring Boot**, it ensures **secure authentication**, **role-based access control**, and **detailed logging of all operations**. Let's dive in! 🌟

---

## ⚙️ Tech Stack
- **Java Spring Boot** (Backend Framework)
- **MySQL** (Database)
- **JWT Authentication** (Security)
- **Spring Security** (Role-based Access Control)
- **Maven** (Dependency Management)
- **Lombok** (Boilerplate Code Reduction)

---

## 🛠️ Setup Instructions

1. **Clone the repository**
   ```sh
   git clone https://github.com/your-repo/task-management-system.git
   cd task-management-system
   ```
2. **Configure Environment Variables**
   - Create an `.env` file or set environment variables manually.
   - Add database credentials, JWT secret key, and other necessary configurations.

3. **Run the Application**
   ```sh
   mvn spring-boot:run
   ```

4. **Access API via Postman or Frontend**
   - Base URL: `http://localhost:8080/api`

---

## 🔐 Authentication & Roles
- **Admin** 👑 - Full access to all operations.
- **ProjectsManager** 📂 - Manages projects and assigned users.
- **TasksManager** 📋 - Manages tasks within projects.
- **User** 🧑‍💻 - Can update task status and view their tasks.

---

## 📌 API Endpoints

### 🔑 Authentication
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT token (Access token && Refresh token) |

### 📂 Project Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/projects` | Get all projects |
| POST | `/api/projects` | Create a new project |
| GET | `/api/projects/{id}` | Get project details |
| PUT | `/api/projects/{id}` | Update project |
| DELETE | `/api/projects/{id}` | Delete project |
| POST | `/api/projects/addusertoproject/{projectid}/{username}` | Assign a user to a project |
| DELETE | `/api/projects/deleteUserfromProject/{projectid}/{username}` | Remove a user from a project |

### 📋 Task Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/tasks` | Get all tasks |
| POST | `/tasks` | Create a new task |
| GET | `/tasks/{id}` | Get task details |
| PUT | `/tasks/{id}` | Update task |
| DELETE | `/tasks/{id}` | Delete task |
| GET | `/tasks/taskofuser` | Get tasks assigned to the logged-in user |
| POST | `/tasks/userupdatetask/{taskid}` | User updates their task status |

### 📜 Activity Logs
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/activitylogs` | Get all activity logs |
| GET | `/api/activitylogs/{id}` | Get activity log by ID |

---

## 🚀 Future Enhancements
🔹 Add **email notifications** for task assignments 📩
🔹 Implement **file uploads** for task attachments 📁
🔹 Create **a frontend dashboard** for better UX 🎨

---

## 🏆 Contributing
We welcome contributions! Feel free to submit PRs, report issues, or suggest new features.

---

## 📝 License
This project is licensed under the **MIT License**.

---

💡 *Happy Coding!* 💡 🚀

