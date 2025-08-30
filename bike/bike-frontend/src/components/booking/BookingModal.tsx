import React, { useState } from 'react';
import { Ride, CreateBookingRequest } from '../../types';
import { formatCurrency } from '../../utils/helpers';
import Button from '../ui/Button';
import Input from '../ui/Input';

interface BookingModalProps {
  ride: Ride;
  userId: number;
  onSubmit: (booking: CreateBookingRequest) => Promise<void>;
  onClose: () => void;
  loading?: boolean;
}

const BookingModal: React.FC<BookingModalProps> = ({
  ride,
  userId,
  onSubmit,
  onClose,
  loading = false,
}) => {
  const [seatsBooked, setSeatsBooked] = useState(1);
  const [error, setError] = useState('');

  const totalFare = ride.distanceKm * ride.farePerKm * seatsBooked;

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (seatsBooked <= 0 || seatsBooked > ride.availableSeats) {
      setError(`Please select between 1 and ${ride.availableSeats} seats`);
      return;
    }

    try {
      await onSubmit({
        rideId: ride.rideId!,
        poolUserId: userId,
        seatsBooked,
      });
      onClose();
    } catch (error) {
      console.error('Booking error:', error);
      setError('Failed to book ride. Please try again.');
    }
  };

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-lg shadow-xl max-w-md w-full mx-4">
        <div className="mb-4">
          <h3 className="text-lg font-medium text-gray-900">Book This Ride</h3>
          <p className="text-sm text-gray-500 mt-1">
            Confirm your booking details
          </p>
        </div>

        <div className="space-y-4 mb-6">
          <div className="bg-gray-50 p-4 rounded-lg">
            <h4 className="font-medium text-gray-900 mb-2">Ride Details</h4>
            <div className="space-y-2 text-sm text-gray-600">
              <div><span className="font-medium">From:</span> {ride.sourceAddress}</div>
              <div><span className="font-medium">To:</span> {ride.destinationAddress}</div>
              <div><span className="font-medium">Driver:</span> {ride.user?.name}</div>
              <div><span className="font-medium">Vehicle:</span> {ride.vehicle?.model}</div>
              <div><span className="font-medium">Distance:</span> {ride.distanceKm} km</div>
              <div><span className="font-medium">Fare per km:</span> {formatCurrency(ride.farePerKm)}</div>
              <div><span className="font-medium">Available seats:</span> {ride.availableSeats}</div>
            </div>
          </div>

          <form onSubmit={handleSubmit}>
            <Input
              label="Number of Seats"
              type="number"
              value={seatsBooked.toString()}
              onChange={(e) => {
                setSeatsBooked(parseInt(e.target.value) || 1);
                setError('');
              }}
              min="1"
              max={ride.availableSeats}
              error={error}
              required
            />

            <div className="bg-blue-50 p-4 rounded-lg mt-4">
              <div className="flex justify-between items-center">
                <span className="font-medium text-gray-900">Total Fare:</span>
                <span className="text-lg font-bold text-blue-600">
                  {formatCurrency(totalFare)}
                </span>
              </div>
              <div className="text-xs text-gray-500 mt-1">
                {seatsBooked} seat(s) × {ride.distanceKm} km × {formatCurrency(ride.farePerKm)}/km
              </div>
            </div>

            <div className="flex space-x-3 mt-6">
              <Button
                type="submit"
                loading={loading}
                className="flex-1"
              >
                Confirm Booking
              </Button>
              <Button
                type="button"
                variant="secondary"
                onClick={onClose}
                className="flex-1"
              >
                Cancel
              </Button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default BookingModal;
