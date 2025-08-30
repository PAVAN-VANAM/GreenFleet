import React, { useState, useEffect } from 'react';
import { useAuth } from '../hooks/useAuth';
import { rideAPI, vehicleAPI } from '../services/api';
import { Ride, CreateRideForm } from '../types';
import Layout from '../components/layout/Layout';
import RideForm from '../components/ride/RideForm';
import RideList from '../components/ride/RideList';
import Button from '../components/ui/Button';

const RidesPage: React.FC = () => {
  const { user } = useAuth();
  const [rides, setRides] = useState<Ride[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingRide, setEditingRide] = useState<Ride | null>(null);
  const [formLoading, setFormLoading] = useState(false);
  const [filter, setFilter] = useState<'all' | 'my'>('all');

  useEffect(() => {
    fetchRides();
  }, []);

  const fetchRides = async () => {
    try {
      setLoading(true);
      const data = await rideAPI.getAllRides();
      setRides(data);
    } catch (error) {
      console.error('Error fetching rides:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateRide = async (formData: CreateRideForm) => {
    if (!user) return;
    
    try {
      setFormLoading(true);
      
      // Get the selected vehicle to include in the ride
      const vehicle = await vehicleAPI.getVehicleById(formData.vehicleId);
      
      const rideData = {
        user: { id: user.id, name: user.name, email: user.email },
        vehicle,
        sourceLat: 0, // These would normally come from geocoding
        sourceLng: 0,
        sourceAddress: formData.sourceAddress,
        destinationLat: 0,
        destinationLng: 0,
        destinationAddress: formData.destinationAddress,
        availableSeats: formData.availableSeats,
        distanceKm: formData.distanceKm,
        farePerKm: formData.farePerKm,
        weatherFactorApplied: formData.weatherFactorApplied,
        trafficFactorApplied: formData.trafficFactorApplied,
        maxFactorAllowed: formData.maxFactorAllowed,
      };
      
      await rideAPI.createRide(rideData);
      await fetchRides();
      setShowForm(false);
    } catch (error) {
      console.error('Error creating ride:', error);
      throw error;
    } finally {
      setFormLoading(false);
    }
  };

  const handleEditRide = async (formData: CreateRideForm) => {
    if (!editingRide?.rideId) return;
    
    try {
      setFormLoading(true);
      await rideAPI.updateRide(editingRide.rideId, {
        sourceAddress: formData.sourceAddress,
        destinationAddress: formData.destinationAddress,
        availableSeats: formData.availableSeats,
        distanceKm: formData.distanceKm,
        farePerKm: formData.farePerKm,
        weatherFactorApplied: formData.weatherFactorApplied,
        trafficFactorApplied: formData.trafficFactorApplied,
        maxFactorAllowed: formData.maxFactorAllowed,
      });
      await fetchRides();
      setEditingRide(null);
      setShowForm(false);
    } catch (error) {
      console.error('Error updating ride:', error);
      throw error;
    } finally {
      setFormLoading(false);
    }
  };

  const handleDeleteRide = async (rideId: number) => {
    if (!confirm('Are you sure you want to delete this ride?')) return;
    
    try {
      await rideAPI.deleteRide(rideId);
      await fetchRides();
    } catch (error) {
      console.error('Error deleting ride:', error);
    }
  };

  const handleEdit = (ride: Ride) => {
    setEditingRide(ride);
    setShowForm(true);
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingRide(null);
  };

  const filteredRides = filter === 'my' 
    ? rides.filter(ride => ride.user?.id === user?.id)
    : rides;

  return (
    <Layout>
      <div className="space-y-6">
        {/* Header */}
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">Rides</h1>
            <p className="text-gray-600">Create and manage ride sharing</p>
          </div>
          {!showForm && (
            <Button onClick={() => setShowForm(true)}>
              Create New Ride
            </Button>
          )}
        </div>

        {/* Filter Tabs */}
        <div className="border-b border-gray-200">
          <nav className="-mb-px flex space-x-8">
            <button
              onClick={() => setFilter('all')}
              className={`py-2 px-1 border-b-2 font-medium text-sm ${
                filter === 'all'
                  ? 'border-primary-500 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
              }`}
            >
              All Rides
            </button>
            <button
              onClick={() => setFilter('my')}
              className={`py-2 px-1 border-b-2 font-medium text-sm ${
                filter === 'my'
                  ? 'border-primary-500 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
              }`}
            >
              My Rides
            </button>
          </nav>
        </div>

        {/* Form */}
        {showForm && (
          <RideForm
            onSubmit={editingRide ? handleEditRide : handleCreateRide}
            onCancel={handleCancel}
            initialData={editingRide ? {
              vehicleId: editingRide.vehicle?.id || 0,
              sourceAddress: editingRide.sourceAddress,
              destinationAddress: editingRide.destinationAddress,
              availableSeats: editingRide.availableSeats,
              distanceKm: editingRide.distanceKm,
              farePerKm: editingRide.farePerKm,
              maxFactorAllowed: editingRide.maxFactorAllowed,
              weatherFactorApplied: editingRide.weatherFactorApplied,
              trafficFactorApplied: editingRide.trafficFactorApplied,
            } : undefined}
            loading={formLoading}
          />
        )}

        {/* Ride List */}
        <RideList
          rides={filteredRides}
          onEdit={handleEdit}
          onDelete={handleDeleteRide}
          loading={loading}
          currentUserId={user?.id}
        />
      </div>
    </Layout>
  );
};

export default RidesPage;
