import { VehicleType, FuelType, MaxFactorAllowed } from '../types';

export const VEHICLE_TYPE_OPTIONS = [
  { value: VehicleType.TWO_WHEELER, label: 'Two Wheeler' },
  { value: VehicleType.FOUR_WHEELER, label: 'Four Wheeler' },
];

export const FUEL_TYPE_OPTIONS = [
  { value: FuelType.PETROL, label: 'Petrol' },
  { value: FuelType.DIESEL, label: 'Diesel' },
  { value: FuelType.ELECTRIC, label: 'Electric' },
];

export const MAX_FACTOR_OPTIONS = [
  { value: MaxFactorAllowed.WEATHER, label: 'Weather' },
  { value: MaxFactorAllowed.TRAFFIC, label: 'Traffic' },
  { value: MaxFactorAllowed.BOTH, label: 'Both' },
];

export const API_BASE_URL = 'http://localhost:8080/api';

export const ROUTES = {
  HOME: '/',
  LOGIN: '/login',
  REGISTER: '/register',
  DASHBOARD: '/dashboard',
  VEHICLES: '/vehicles',
  RIDES: '/rides',
  BOOKINGS: '/bookings',
  PROFILE: '/profile',
} as const;
