import React, { useState, useEffect } from 'react';
import { useAuth } from '../hooks/useAuth';
import { bookingAPI, rideAPI } from '../services/api';
import { RideBooking, Ride, CreateBookingRequest } from '../types';
import { formatCurrency, formatDate } from '../utils/helpers';
import Layout from '../components/layout/Layout';
import BookingModal from '../components/booking/BookingModal';
import RideList from '../components/ride/RideList';
import Button from '../components/ui/Button';

const BookingsPage: React.FC = () => {
  const { user } = useAuth();
  const [bookings, setBookings] = useState<RideBooking[]>([]);
  const [availableRides, setAvailableRides] = useState<Ride[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedRide, setSelectedRide] = useState<Ride | null>(null);
  const [bookingLoading, setBookingLoading] = useState(false);
  const [view, setView] = useState<'bookings' | 'available'>('bookings');

  useEffect(() => {
    fetchData();
  }, [user]);

  const fetchData = async () => {
    if (!user) return;
    
    try {
      setLoading(true);
      const [bookingsData, ridesData] = await Promise.all([
        bookingAPI.getBookingsByUser(user.id),
        rideAPI.getAllRides(),
      ]);
      
      setBookings(bookingsData);
      // Filter out rides created by current user and rides with no available seats
      setAvailableRides(
        ridesData.filter(ride => 
          ride.user?.id !== user.id && ride.availableSeats > 0
        )
      );
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleBookRide = async (bookingRequest: CreateBookingRequest) => {
    try {
      setBookingLoading(true);
      await bookingAPI.createBooking(bookingRequest);
      await fetchData(); // Refresh data
      setSelectedRide(null);
    } catch (error) {
      console.error('Error booking ride:', error);
      throw error;
    } finally {
      setBookingLoading(false);
    }
  };

  const handleCancelBooking = async (bookingId: number) => {
    if (!confirm('Are you sure you want to cancel this booking?')) return;
    
    try {
      await bookingAPI.deleteBooking(bookingId);
      await fetchData();
    } catch (error) {
      console.error('Error canceling booking:', error);
    }
  };

  const BookingsList = () => {
    if (loading) {
      return (
        <div className="card">
          <div className="flex items-center justify-center h-32">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600"></div>
          </div>
        </div>
      );
    }

    if (bookings.length === 0) {
      return (
        <div className="card text-center">
          <div className="py-8">
            <svg
              className="mx-auto h-12 w-12 text-gray-400"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012-2"
              />
            </svg>
            <h3 className="mt-2 text-sm font-medium text-gray-900">No bookings yet</h3>
            <p className="mt-1 text-sm text-gray-500">
              Book your first ride to get started.
            </p>
          </div>
        </div>
      );
    }

    return (
      <div className="space-y-4">
        {bookings.map((booking) => (
          <div key={booking.bookingId} className="card">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <h4 className="text-lg font-medium text-gray-900 mb-2">
                  {booking.ride.sourceAddress} → {booking.ride.destinationAddress}
                </h4>
                
                <div className="grid grid-cols-2 gap-4 text-sm text-gray-600">
                  <div>
                    <span className="font-medium">Driver:</span> {booking.ride.user?.name}
                  </div>
                  <div>
                    <span className="font-medium">Vehicle:</span> {booking.ride.vehicle?.model}
                  </div>
                  <div>
                    <span className="font-medium">Seats Booked:</span> {booking.seatsBooked}
                  </div>
                  <div>
                    <span className="font-medium">Total Fare:</span> {
                      booking.fare ? formatCurrency(booking.fare) : 
                      formatCurrency(booking.ride.distanceKm * booking.ride.farePerKm * booking.seatsBooked)
                    }
                  </div>
                  <div>
                    <span className="font-medium">Distance:</span> {booking.ride.distanceKm} km
                  </div>
                  {booking.co2Saved && (
                    <div>
                      <span className="font-medium">CO2 Saved:</span> {booking.co2Saved.toFixed(2)} kg
                    </div>
                  )}
                </div>
                
                {booking.bookingTime && (
                  <div className="mt-2 text-xs text-gray-500">
                    Booked: {formatDate(booking.bookingTime)}
                  </div>
                )}
              </div>
              
              <div className="ml-4">
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => booking.bookingId && handleCancelBooking(booking.bookingId)}
                >
                  Cancel
                </Button>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  };

  return (
    <Layout>
      <div className="space-y-6">
        {/* Header */}
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Ride Bookings</h1>
          <p className="text-gray-600">Manage your ride bookings and find new rides</p>
        </div>

        {/* View Toggle */}
        <div className="border-b border-gray-200">
          <nav className="-mb-px flex space-x-8">
            <button
              onClick={() => setView('bookings')}
              className={`py-2 px-1 border-b-2 font-medium text-sm ${
                view === 'bookings'
                  ? 'border-primary-500 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
              }`}
            >
              My Bookings ({bookings.length})
            </button>
            <button
              onClick={() => setView('available')}
              className={`py-2 px-1 border-b-2 font-medium text-sm ${
                view === 'available'
                  ? 'border-primary-500 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
              }`}
            >
              Available Rides ({availableRides.length})
            </button>
          </nav>
        </div>

        {/* Content */}
        {view === 'bookings' ? (
          <BookingsList />
        ) : (
          <RideList
            rides={availableRides}
            onBook={setSelectedRide}
            loading={loading}
            showActions={true}
            currentUserId={user?.id}
          />
        )}

        {/* Booking Modal */}
        {selectedRide && user && (
          <BookingModal
            ride={selectedRide}
            userId={user.id}
            onSubmit={handleBookRide}
            onClose={() => setSelectedRide(null)}
            loading={bookingLoading}
          />
        )}
      </div>
    </Layout>
  );
};

export default BookingsPage;
