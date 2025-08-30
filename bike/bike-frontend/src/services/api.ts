import axios from 'axios';
import {
  User,
  LoginRequest,
  Vehicle,
  Ride,
  RideDTO,
  RideBooking,
  CreateBookingRequest,
  ApiResponse,
} from '../types';

// Create axios instance with default config
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor to add auth token if available
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor to handle errors globally
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('authToken');
      localStorage.removeItem('currentUser');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// User API
export const userAPI = {
  // Create user (Register)
  createUser: async (user: Omit<User, 'id'>): Promise<User> => {
    const response = await api.post<User>('/users', user);
    return response.data;
  },

  // Login user
  loginUser: async (loginRequest: LoginRequest): Promise<string> => {
    const response = await api.post<string>('/users/login', loginRequest);
    return response.data;
  },

  // Get all users
  getAllUsers: async (): Promise<User[]> => {
    const response = await api.get<User[]>('/users');
    return response.data;
  },

  // Get user by ID
  getUserById: async (id: number): Promise<User> => {
    const response = await api.get<User>(`/users/${id}`);
    return response.data;
  },

  // Update user
  updateUser: async (id: number, user: Partial<User>): Promise<User> => {
    const response = await api.put<User>(`/users/${id}`, user);
    return response.data;
  },

  // Delete user
  deleteUser: async (id: number): Promise<void> => {
    await api.delete(`/users/${id}`);
  },
};

// Vehicle API
export const vehicleAPI = {
  // Create vehicle for a user
  createVehicle: async (userId: number, vehicle: Omit<Vehicle, 'id' | 'user'>): Promise<Vehicle> => {
    const response = await api.post<Vehicle>(`/vehicles/user/${userId}`, vehicle);
    return response.data;
  },

  // Get all vehicles
  getAllVehicles: async (): Promise<Vehicle[]> => {
    const response = await api.get<Vehicle[]>('/vehicles');
    return response.data;
  },

  // Get vehicle by ID
  getVehicleById: async (id: number): Promise<Vehicle> => {
    const response = await api.get<Vehicle>(`/vehicles/${id}`);
    return response.data;
  },

  // Get vehicles by user
  getVehiclesByUser: async (userId: number): Promise<Vehicle[]> => {
    const response = await api.get<Vehicle[]>(`/vehicles/user/${userId}`);
    return response.data;
  },

  // Update vehicle
  updateVehicle: async (id: number, vehicle: Partial<Vehicle>): Promise<Vehicle> => {
    const response = await api.put<Vehicle>(`/vehicles/${id}`, vehicle);
    return response.data;
  },

  // Delete vehicle
  deleteVehicle: async (id: number): Promise<void> => {
    await api.delete(`/vehicles/${id}`);
  },
};

// Ride API
export const rideAPI = {
  // Create ride
  createRide: async (ride: Omit<Ride, 'rideId'>): Promise<RideDTO> => {
    const response = await api.post<RideDTO>('/rides', ride);
    return response.data;
  },

  // Get all rides
  getAllRides: async (): Promise<Ride[]> => {
    const response = await api.get<Ride[]>('/rides');
    return response.data;
  },

  // Get ride by ID
  getRideById: async (id: number): Promise<Ride> => {
    const response = await api.get<Ride>(`/rides/${id}`);
    return response.data;
  },

  // Update ride
  updateRide: async (id: number, ride: Partial<Ride>): Promise<Ride> => {
    const response = await api.put<Ride>(`/rides/${id}`, ride);
    return response.data;
  },

  // Delete ride
  deleteRide: async (id: number): Promise<void> => {
    await api.delete(`/rides/${id}`);
  },
};

// Ride Booking API
export const bookingAPI = {
  // Create booking
  createBooking: async (booking: CreateBookingRequest): Promise<RideBooking> => {
    const response = await api.post<RideBooking>(
      `/bookings?rideId=${booking.rideId}&poolUserId=${booking.poolUserId}&seatsBooked=${booking.seatsBooked}`
    );
    return response.data;
  },

  // Get all bookings
  getAllBookings: async (): Promise<RideBooking[]> => {
    const response = await api.get<RideBooking[]>('/bookings');
    return response.data;
  },

  // Get booking by ID
  getBookingById: async (bookingId: number): Promise<RideBooking> => {
    const response = await api.get<RideBooking>(`/bookings/${bookingId}`);
    return response.data;
  },

  // Get bookings by ride
  getBookingsByRide: async (rideId: number): Promise<RideBooking[]> => {
    const response = await api.get<RideBooking[]>(`/bookings/ride/${rideId}`);
    return response.data;
  },

  // Get bookings by user
  getBookingsByUser: async (poolUserId: number): Promise<RideBooking[]> => {
    const response = await api.get<RideBooking[]>(`/bookings/user/${poolUserId}`);
    return response.data;
  },

  // Delete booking
  deleteBooking: async (bookingId: number): Promise<void> => {
    await api.delete(`/bookings/${bookingId}`);
  },
};

export default api;
