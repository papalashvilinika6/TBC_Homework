# LiveF1 – Android Application

## Purpose of the Application

The **LiveF1** application was created to make users’ lives easier by providing a **simple, fast, and reliable way to access Formula 1–related information** in one place.

Instead of searching across multiple platforms, users can:
- View drivers and race-related data
- Navigate the app smoothly with a modern UI
- Stay authenticated without repeated logins
- Access structured, up-to-date information with minimal effort

The main goal of the application is **usability, clarity, and performance**, while following modern Android development best practices.

---

## How to Use the Application

### 1. Authentication
- Users can **register or log in** using email/password (Firebase Authentication).
- Once logged in, the session is **persisted**, so the user does not need to log in again when reopening the app.

### 2. Navigation
- The application uses **Fragments** and **Navigation Component**.
- Bottom navigation allows users to move between main sections easily.

### 3. Viewing Data
- Data is fetched from remote APIs using **Retrofit**.
- Lists are displayed using **RecyclerView**.
- UI updates reactively based on state changes.

### 4. State Management
- User actions are handled through **Events**
- UI state is exposed via **State**
- One-time actions (navigation, errors) are handled using **SideEffects**

This ensures predictable behavior and a clean UI flow.

---

##  Technologies & Tools Used

### Programming Language
- **Kotlin**

### Architecture
- **MVVM + MVI**
- **Clean Architecture** (Data → Presentation, no domain layer)
- **Single source of truth for UI state**

### Android Components
- Fragments (no Activities for UI logic)
- ViewModel
- View Binding
- Navigation Component
- RecyclerView
- ConstraintLayout (AppCompat widgets only)

### Dependency Injection
- **Hilt (Dagger)**

### Networking
- **Retrofit**
- **OkHttp**
- **Gson Converter**

### Asynchronous Programming
- **Kotlin Coroutines**
- **Flow / SharedFlow / StateFlow**

### Local & Remote Data
- Firebase Authentication
- Firebase Firestore (if applicable)
- REST APIs

### UI / Design
- XML layouts
- Material Design principles
- Dark-themed, modern UI

---


