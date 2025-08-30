import React, { useState, useEffect } from 'react';
import { useAuth } from '../hooks/useAuth';
import { vehicleAPI } from '../services/api';
import { Vehicle, CreateVehicleForm } from '../types';
import Layout from '../components/layout/Layout';
import VehicleForm from '../components/vehicle/VehicleForm';
import VehicleList from '../components/vehicle/VehicleList';
import Button from '../components/ui/Button';

const VehiclesPage: React.FC = () => {
  const { user } = useAuth();
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingVehicle, setEditingVehicle] = useState<Vehicle | null>(null);
  const [formLoading, setFormLoading] = useState(false);

  useEffect(() => {
    fetchVehicles();
  }, [user]);

  const fetchVehicles = async () => {
    if (!user) return;
    
    try {
      setLoading(true);
      const data = await vehicleAPI.getVehiclesByUser(user.id);
      setVehicles(data);
    } catch (error) {
      console.error('Error fetching vehicles:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddVehicle = async (formData: CreateVehicleForm) => {
    if (!user) return;
    
    try {
      setFormLoading(true);
      await vehicleAPI.createVehicle(user.id, formData);
      await fetchVehicles();
      setShowForm(false);
    } catch (error) {
      console.error('Error adding vehicle:', error);
      throw error;
    } finally {
      setFormLoading(false);
    }
  };

  const handleEditVehicle = async (formData: CreateVehicleForm) => {
    if (!editingVehicle?.id) return;
    
    try {
      setFormLoading(true);
      await vehicleAPI.updateVehicle(editingVehicle.id, formData);
      await fetchVehicles();
      setEditingVehicle(null);
      setShowForm(false);
    } catch (error) {
      console.error('Error updating vehicle:', error);
      throw error;
    } finally {
      setFormLoading(false);
    }
  };

  const handleDeleteVehicle = async (vehicleId: number) => {
    if (!confirm('Are you sure you want to delete this vehicle?')) return;
    
    try {
      await vehicleAPI.deleteVehicle(vehicleId);
      await fetchVehicles();
    } catch (error) {
      console.error('Error deleting vehicle:', error);
    }
  };

  const handleEdit = (vehicle: Vehicle) => {
    setEditingVehicle(vehicle);
    setShowForm(true);
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingVehicle(null);
  };

  return (
    <Layout>
      <div className="space-y-6">
        {/* Header */}
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">My Vehicles</h1>
            <p className="text-gray-600">Manage your registered vehicles</p>
          </div>
          {!showForm && (
            <Button onClick={() => setShowForm(true)}>
              Add New Vehicle
            </Button>
          )}
        </div>

        {/* Form */}
        {showForm && (
          <VehicleForm
            onSubmit={editingVehicle ? handleEditVehicle : handleAddVehicle}
            onCancel={handleCancel}
            initialData={editingVehicle || undefined}
            loading={formLoading}
          />
        )}

        {/* Vehicle List */}
        <VehicleList
          vehicles={vehicles}
          onEdit={handleEdit}
          onDelete={handleDeleteVehicle}
          loading={loading}
        />
      </div>
    </Layout>
  );
};

export default VehiclesPage;
