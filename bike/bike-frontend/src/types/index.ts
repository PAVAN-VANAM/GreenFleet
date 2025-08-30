// User Types
export interface User {
  id: number;
  name: string;
  email: string;
  password?: string;
  rating?: number;
  stateOfRidePool?: string;
  bikeNumber?: string;
  bikeLicense?: string;
  co2Rate?: number;
  pucCertificate?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthUser {
  id: number;
  name: string;
  email: string;
  rating?: number;
}

// Vehicle Types
export const VehicleType = {
  TWO_WHEELER: 'TWO_WHEELER',
  FOUR_WHEELER: 'FOUR_WHEELER',
} as const;

export type VehicleType = typeof VehicleType[keyof typeof VehicleType];

export const FuelType = {
  PETROL: 'PETROL',
  DIESEL: 'DIESEL',
  ELECTRIC: 'ELECTRIC',
} as const;

export type FuelType = typeof FuelType[keyof typeof FuelType];

export interface Vehicle {
  id?: number;
  user?: User;
  vehicleType: VehicleType;
  model: string;
  mileage: number;
  fuelType: FuelType;
  seats: number;
  co2PerKm: number;
  createdAt?: string;
  updatedAt?: string;
}

// Ride Types
export const MaxFactorAllowed = {
  WEATHER: 'WEATHER',
  TRAFFIC: 'TRAFFIC',
  BOTH: 'BOTH',
} as const;

export type MaxFactorAllowed = typeof MaxFactorAllowed[keyof typeof MaxFactorAllowed];

export interface Ride {
  rideId?: number;
  user?: User;
  vehicle?: Vehicle;
  sourceLat: number;
  sourceLng: number;
  sourceAddress: string;
  destinationLat: number;
  destinationLng: number;
  destinationAddress: string;
  availableSeats: number;
  distanceKm: number;
  farePerKm: number;
  weatherFactorApplied?: boolean;
  trafficFactorApplied?: boolean;
  maxFactorAllowed: MaxFactorAllowed;
  createdAt?: string;
  updatedAt?: string;
}

export interface RideDTO {
  rideId: number;
  userId: number;
  userName: string;
  userEmail: string;
  vehicleId: number;
  vehicleModel: string;
  vehicleType: string;
  seats: number;
  sourceLat: number;
  sourceLng: number;
  sourceAddress: string;
  destinationLat: number;
  destinationLng: number;
  destinationAddress: string;
  availableSeats: number;
  distanceKm: number;
  farePerKm: number;
  weatherFactorApplied: boolean;
  trafficFactorApplied: boolean;
  maxFactorAllowed: string;
  createdAt: string;
  updatedAt: string;
}

// Ride Booking Types
export interface RideBooking {
  bookingId?: number;
  ride: Ride;
  poolUser: User;
  seatsBooked: number;
  fare?: number;
  co2Saved?: number;
  bookingTime?: string;
}

export interface CreateBookingRequest {
  rideId: number;
  poolUserId: number;
  seatsBooked: number;
}

// API Response Types
export interface ApiResponse<T> {
  data?: T;
  message?: string;
  error?: string;
  status: number;
}

// Form Types
export interface CreateUserForm {
  name: string;
  email: string;
  password: string;
  bikeNumber?: string;
  bikeLicense?: string;
  co2Rate?: number;
  pucCertificate?: string;
}

export interface CreateVehicleForm {
  vehicleType: VehicleType;
  model: string;
  mileage: number;
  fuelType: FuelType;
  seats: number;
  co2PerKm: number;
}

export interface CreateRideForm {
  vehicleId: number;
  sourceAddress: string;
  destinationAddress: string;
  availableSeats: number;
  distanceKm: number;
  farePerKm: number;
  maxFactorAllowed: MaxFactorAllowed;
  weatherFactorApplied?: boolean;
  trafficFactorApplied?: boolean;
}

// Navigation Types
export interface NavItem {
  name: string;
  href: string;
  icon?: React.ComponentType<any>;
  current?: boolean;
}
