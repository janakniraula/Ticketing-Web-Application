# 🎟️ TicketingApp

**Event Ticketing and Resale Platform**  
A comprehensive ticketing application for managing events, ticket sales, and secure peer-to-peer ticket reselling with price ceiling enforcement.

---

## 🚀 Features

### 🔐 Secure Authentication
- Email & password login  
- Google OAuth2 integration  
- JWT token-based security  

### 🎭 Event Management
- Create and manage events with detailed information  
- Support for various event types (concerts, sports, theater, etc.)  
- Venue and seating management  
- Pricing tier configuration  

### 🎟️ Ticketing System
- Purchase tickets for events  
- QR code generation for ticket validation  
- Mobile-friendly ticket display  
- Scan tickets at event entry  

### 💰 Resale Marketplace
- List tickets for resale  
- Price ceiling enforcement (cannot sell above original price)  
- Secure peer-to-peer transactions  
- Anti-fraud protection  

### 👤 User Management
- User profiles and account management  
- Purchase history tracking  
- Seller reputation system  

### 💳 Payment Processing
- Secure payment integration  
- Transaction history  
- Refund processing  

---

## 🛠️ Technology Stack

### Backend
- **Java 17** - Core programming language  
- **Spring Boot 3.4.x** - Application framework  
- **Spring Security** - Authentication and authorization  
- **Spring Data JPA** - Data persistence  
- **PostgreSQL** - Relational database  
- **Hibernate** - ORM for database interaction  
- **JWT** - Stateless authentication  
- **OAuth2** - Google authentication  
- **ZXing** - QR code generation  
- **Maven** - Build and dependency management  

### Frontend (Planned)
- **React** - UI library  
- **TypeScript** - Type-safe JavaScript  
- **Axios** - HTTP client  
- **React Router** - Routing  
- **Material UI** - UI components  
- **Redux Toolkit** - State management  

### DevOps & Tools
- **Git** - Version control  
- **Docker** - Containerization *(planned)*  
- **Postman** - API testing  
- **JUnit & Mockito** - Unit & integration testing  

---

## 📦 Getting Started

### Prerequisites
- Java JDK 17 or higher  
- PostgreSQL 12 or higher  
- Maven 3.6 or higher  
- Node.js 16 or higher *(for frontend)*  

### Backend Setup

```bash
# Clone the repository
git clone https://github.com/yourusername/ticketingapp.git
cd ticketingapp
