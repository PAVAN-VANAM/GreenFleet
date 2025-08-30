import React, { useState, useEffect } from 'react';
import { useAuth } from '../hooks/useAuth';
import { vehicleAPI, rideAPI, bookingAPI } from '../services/api';
import { Vehicle, Ride, RideBooking } from '../types';
import Layout from '../components/layout/Layout';

const DashboardPage: React.FC = () => {
  const { user } = useAuth();
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [rides, setRides] = useState<Ride[]>([]);
  const [bookings, setBookings] = useState<RideBooking[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboardData = async () => {
      if (!user) return;
      
      try {
        const [vehiclesData, ridesData, bookingsData] = await Promise.all([
          vehicleAPI.getVehiclesByUser(user.id),
          rideAPI.getAllRides(),
          bookingAPI.getBookingsByUser(user.id),
        ]);
        
        setVehicles(vehiclesData);
        setRides(ridesData);
        setBookings(bookingsData);
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, [user]);

  if (loading) {
    return (
      <Layout>
        <div className="flex items-center justify-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
        </div>
      </Layout>
    );
  }

  return (
    <Layout>
      <div className="space-y-6">
        {/* Welcome Section */}
        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="px-4 py-5 sm:p-6">
            <h1 className="text-2xl font-bold text-gray-900">
              Welcome back, {user?.name}!
            </h1>
            <p className="mt-1 text-gray-500">
              Here's what's happening with your green transportation
            </p>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 gap-5 sm:grid-cols-3">
          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <div className="w-8 h-8 bg-blue-500 rounded-full flex items-center justify-center">
                    <span className="text-white text-sm font-medium">{vehicles.length}</span>
                  </div>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Your Vehicles
                    </dt>
                    <dd className="text-lg font-medium text-gray-900">
                      {vehicles.length} registered
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <div className="w-8 h-8 bg-green-500 rounded-full flex items-center justify-center">
                    <span className="text-white text-sm font-medium">{rides.length}</span>
                  </div>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Available Rides
                    </dt>
                    <dd className="text-lg font-medium text-gray-900">
                      {rides.length} total rides
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <div className="w-8 h-8 bg-purple-500 rounded-full flex items-center justify-center">
                    <span className="text-white text-sm font-medium">{bookings.length}</span>
                  </div>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Your Bookings
                    </dt>
                    <dd className="text-lg font-medium text-gray-900">
                      {bookings.length} booked rides
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Recent Activity */}
        <div className="bg-white shadow overflow-hidden sm:rounded-md">
          <div className="px-4 py-5 sm:px-6">
            <h3 className="text-lg leading-6 font-medium text-gray-900">
              Quick Actions
            </h3>
            <p className="mt-1 max-w-2xl text-sm text-gray-500">
              Get started with these common tasks
            </p>
          </div>
          <div className="border-t border-gray-200">
            <div className="grid grid-cols-1 gap-4 p-4 sm:grid-cols-2 lg:grid-cols-4">
              <button className="btn-primary p-4 text-center">
                Add New Vehicle
              </button>
              <button className="btn-secondary p-4 text-center">
                Create Ride
              </button>
              <button className="btn-secondary p-4 text-center">
                Find Rides
              </button>
              <button className="btn-secondary p-4 text-center">
                View Bookings
              </button>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default DashboardPage;
