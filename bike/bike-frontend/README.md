# GreenFleet Frontend

A modern React TypeScript frontend application for the GreenFleet bike sharing and ride pooling platform.

## Features

### 🔐 Authentication

- User registration and login
- Protected routes with authentication guards
- User profile management

### 🚗 Vehicle Management

- Add, edit, and delete vehicles
- Support for two-wheelers and four-wheelers
- Vehicle details including mileage, fuel type, and CO2 emissions

### 🚕 Ride Management

- Create and manage ride offerings
- View all available rides
- Edit and delete your own rides
- Advanced ride filtering

### 📋 Booking System

- Book available rides from other users
- View and manage your bookings
- Real-time seat availability
- Fare calculation with distance and seat count

### 🎨 Modern UI

- Responsive design with Tailwind CSS
- Clean and intuitive user interface
- Mobile-friendly layout
- Loading states and error handling

## Technology Stack

- **React 18** - Modern React with hooks
- **TypeScript** - Type-safe development
- **Vite** - Fast build tool and development server
- **Tailwind CSS** - Utility-first CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **React Hook Form** - Form handling and validation

## Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── auth/           # Authentication components
│   ├── booking/        # Booking-related components
│   ├── layout/         # Layout components
│   ├── ride/           # Ride management components
│   ├── ui/             # Basic UI components
│   └── vehicle/        # Vehicle management components
├── hooks/              # Custom React hooks
├── pages/              # Page components
├── services/           # API service layer
├── types/              # TypeScript type definitions
└── utils/              # Utility functions and constants
```

## Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Spring Boot backend running on http://localhost:8080

### Installation

1. Install dependencies:

   ```bash
   npm install
   ```

2. Start the development server:

   ```bash
   npm run dev
   ```

3. Open your browser and navigate to `http://localhost:5173`

### Building for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## API Integration

The frontend communicates with the Spring Boot backend through a RESTful API. The base URL is configured as `http://localhost:8080/api`.

### Available Endpoints

- **Users**: `/api/users`
  - User registration, login, profile management
- **Vehicles**: `/api/vehicles`
  - Vehicle CRUD operations
- **Rides**: `/api/rides`
  - Ride creation and management
- **Bookings**: `/api/bookings`
  - Ride booking system

## Key Features Implemented

### Authentication Flow

- Complete user registration with optional bike details
- Secure login with session management
- Protected routes that redirect to login when not authenticated

### Vehicle Management

- Add vehicles with detailed specifications
- Support for different vehicle types (two-wheeler, four-wheeler)
- Fuel type selection (Petrol, Diesel, Electric)
- CO2 emission tracking

### Ride Creation

- Create rides with source and destination
- Set available seats and pricing
- Weather and traffic factor considerations
- Distance and fare calculations

### Booking System

- Browse available rides from other users
- Real-time booking with seat selection
- Fare calculation and confirmation
- Booking history and management

### User Experience

- Responsive design for all screen sizes
- Loading states and error handling
- Form validation with helpful error messages
- Intuitive navigation and user flows

## Development

### Code Organization

- Components are organized by feature
- Shared UI components in `components/ui/`
- Type definitions centralized in `types/`
- API calls abstracted in service layer

### State Management

- Local state with React hooks
- Authentication context for user state
- API state management with loading and error states

### Styling

- Tailwind CSS for utility-first styling
- Custom component classes for consistency
- Responsive design patterns
- Modern color scheme with primary blue and green accents

## Contributing

1. Follow the existing code structure and naming conventions
2. Use TypeScript for all new code
3. Add proper error handling and loading states
4. Test your changes across different screen sizes
5. Ensure API integration works correctly

## License

This project is part of the GreenFleet application suite.
