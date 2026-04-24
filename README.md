# User Notes App

A full-stack application for managing user notes with a Spring Boot backend and Angular frontend.

## Prerequisites

### Backend
- Java 17 or higher
- Maven is **not required** - the project includes Maven Wrapper (mvnw)

### Frontend
- Node.js 24 or higher
- npm 10 or higher

## Project Structure

```
user_notes_app/
├── user_notes_backend/     # Spring Boot backend
└── user_notes_frontend/    # Angular frontend
```

## Running the Application

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd user_notes_backend
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd user_notes_frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

   - The frontend will be available at `http://localhost:4200`

    - The frontend is configured with a proxy to the backend (see `proxy.conf.json`)
  
## API Documentation

The backend API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI spec: `http://localhost:8080/v3/api-docs`

## Development Workflow

1. **Start the backend first** on port 8080
2. **Start the frontend** on port 4200
3. The frontend is configured with a proxy to the backend (see `proxy.conf.json`)
4. Make changes and the development servers will auto-reload

## Building for Production

### Backend
```bash
cd user_notes_backend
./mvnw clean package
java -jar target/user-notes-backend-*.jar
```

### Frontend
```bash
cd user_notes_frontend
ng build --configuration production
```

The production build will be in `user_notes_frontend/dist/`

## Troubleshooting
- **CORS issues**: Ensure the backend is running and the frontend proxy is correctly configured
