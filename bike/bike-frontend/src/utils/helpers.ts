import { VehicleType, FuelType, MaxFactorAllowed } from '../types';

export const formatVehicleType = (type: VehicleType): string => {
  switch (type) {
    case VehicleType.TWO_WHEELER:
      return 'Two Wheeler';
    case VehicleType.FOUR_WHEELER:
      return 'Four Wheeler';
    default:
      return type;
  }
};

export const formatFuelType = (type: FuelType): string => {
  switch (type) {
    case FuelType.PETROL:
      return 'Petrol';
    case FuelType.DIESEL:
      return 'Diesel';
    case FuelType.ELECTRIC:
      return 'Electric';
    default:
      return type;
  }
};

export const formatMaxFactor = (factor: MaxFactorAllowed): string => {
  switch (factor) {
    case MaxFactorAllowed.WEATHER:
      return 'Weather';
    case MaxFactorAllowed.TRAFFIC:
      return 'Traffic';
    case MaxFactorAllowed.BOTH:
      return 'Both';
    default:
      return factor;
  }
};

export const formatCurrency = (amount: number): string => {
  return new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: 'INR',
  }).format(amount);
};

export const formatDistance = (distance: number): string => {
  return `${distance.toFixed(1)} km`;
};

export const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  return date.toLocaleDateString('en-IN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  });
};

export const calculateTotalFare = (distanceKm: number, farePerKm: number): number => {
  return distanceKm * farePerKm;
};

export const calculateCO2Saved = (distanceKm: number, co2PerKm: number, seatsShared: number): number => {
  return distanceKm * co2PerKm * seatsShared;
};

export const truncateText = (text: string, maxLength: number): string => {
  if (text.length <= maxLength) return text;
  return text.substring(0, maxLength) + '...';
};

export const validateEmail = (email: string): boolean => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

export const validatePhone = (phone: string): boolean => {
  const phoneRegex = /^[6-9]\d{9}$/;
  return phoneRegex.test(phone);
};
