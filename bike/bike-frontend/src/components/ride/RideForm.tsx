import React, { useState, useEffect } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { vehicleAPI } from '../../services/api';
import { CreateRideForm, MaxFactorAllowed, Vehicle } from '../../types';
import { MAX_FACTOR_OPTIONS } from '../../utils/constants';
import Button from '../ui/Button';
import Input from '../ui/Input';
import Select from '../ui/Select';

interface RideFormProps {
  onSubmit: (data: CreateRideForm) => Promise<void>;
  onCancel: () => void;
  initialData?: Partial<CreateRideForm>;
  loading?: boolean;
}

const RideForm: React.FC<RideFormProps> = ({
  onSubmit,
  onCancel,
  initialData,
  loading = false,
}) => {
  const { user } = useAuth();
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [formData, setFormData] = useState<CreateRideForm>({
    vehicleId: initialData?.vehicleId || 0,
    sourceAddress: initialData?.sourceAddress || '',
    destinationAddress: initialData?.destinationAddress || '',
    availableSeats: initialData?.availableSeats || 1,
    distanceKm: initialData?.distanceKm || 0,
    farePerKm: initialData?.farePerKm || 0,
    maxFactorAllowed: initialData?.maxFactorAllowed || MaxFactorAllowed.BOTH,
    weatherFactorApplied: initialData?.weatherFactorApplied || false,
    trafficFactorApplied: initialData?.trafficFactorApplied || false,
  });
  const [errors, setErrors] = useState<Partial<CreateRideForm>>({});

  useEffect(() => {
    const fetchVehicles = async () => {
      if (!user) return;
      
      try {
        const data = await vehicleAPI.getVehiclesByUser(user.id);
        setVehicles(data);
        
        // Auto-select first vehicle if no initial data
        if (data.length > 0 && !initialData?.vehicleId) {
          setFormData(prev => ({ ...prev, vehicleId: data[0].id || 0 }));
        }
      } catch (error) {
        console.error('Error fetching vehicles:', error);
      }
    };

    fetchVehicles();
  }, [user, initialData]);

  const validateForm = (): boolean => {
    const newErrors: Partial<CreateRideForm> = {};

    if (!formData.vehicleId) {
      newErrors.vehicleId = 'Vehicle is required';
    }

    if (!formData.sourceAddress) {
      newErrors.sourceAddress = 'Source address is required';
    }

    if (!formData.destinationAddress) {
      newErrors.destinationAddress = 'Destination address is required';
    }

    if (!formData.availableSeats || formData.availableSeats <= 0) {
      newErrors.availableSeats = 'Available seats must be greater than 0';
    }

    if (!formData.distanceKm || formData.distanceKm <= 0) {
      newErrors.distanceKm = 'Distance must be greater than 0';
    }

    if (!formData.farePerKm || formData.farePerKm <= 0) {
      newErrors.farePerKm = 'Fare per km must be greater than 0';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) return;

    try {
      await onSubmit(formData);
    } catch (error) {
      console.error('Ride form submission error:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    
    if (type === 'checkbox') {
      const target = e.target as HTMLInputElement;
      setFormData(prev => ({ ...prev, [name]: target.checked }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: type === 'number' ? parseFloat(value) || 0 : value
      }));
    }
    
    // Clear error when user starts typing
    if (errors[name as keyof CreateRideForm]) {
      setErrors(prev => ({ ...prev, [name]: undefined }));
    }
  };

  const vehicleOptions = vehicles.map(vehicle => ({
    value: vehicle.id || 0,
    label: `${vehicle.model} (${vehicle.vehicleType})`
  }));

  return (
    <div className="card">
      <h3 className="text-lg font-medium text-gray-900 mb-4">
        {initialData ? 'Edit Ride' : 'Create New Ride'}
      </h3>
      
      <form onSubmit={handleSubmit} className="space-y-4">
        <Select
          label="Vehicle"
          name="vehicleId"
          value={formData.vehicleId.toString()}
          onChange={handleChange}
          options={vehicleOptions}
          error={errors.vehicleId?.toString()}
          placeholder="Select a vehicle"
          required
        />
        
        <Input
          label="Source Address"
          type="text"
          name="sourceAddress"
          value={formData.sourceAddress}
          onChange={handleChange}
          error={errors.sourceAddress}
          placeholder="Enter pickup location"
          required
        />
        
        <Input
          label="Destination Address"
          type="text"
          name="destinationAddress"
          value={formData.destinationAddress}
          onChange={handleChange}
          error={errors.destinationAddress}
          placeholder="Enter destination"
          required
        />
        
        <div className="grid grid-cols-2 gap-4">
          <Input
            label="Available Seats"
            type="number"
            name="availableSeats"
            value={formData.availableSeats.toString()}
            onChange={handleChange}
            error={errors.availableSeats?.toString()}
            placeholder="Number of seats"
            min="1"
            required
          />
          
          <Input
            label="Distance (km)"
            type="number"
            name="distanceKm"
            value={formData.distanceKm.toString()}
            onChange={handleChange}
            error={errors.distanceKm?.toString()}
            placeholder="Trip distance"
            step="0.1"
            required
          />
        </div>
        
        <Input
          label="Fare per km (₹)"
          type="number"
          name="farePerKm"
          value={formData.farePerKm.toString()}
          onChange={handleChange}
          error={errors.farePerKm?.toString()}
          placeholder="Price per kilometer"
          step="0.01"
          required
        />
        
        <Select
          label="Max Factor Allowed"
          name="maxFactorAllowed"
          value={formData.maxFactorAllowed}
          onChange={handleChange}
          options={MAX_FACTOR_OPTIONS}
          required
        />
        
        <div className="space-y-2">
          <label className="label">Additional Factors</label>
          <div className="space-y-2">
            <label className="flex items-center">
              <input
                type="checkbox"
                name="weatherFactorApplied"
                checked={formData.weatherFactorApplied}
                onChange={handleChange}
                className="mr-2"
              />
              Apply weather factor
            </label>
            <label className="flex items-center">
              <input
                type="checkbox"
                name="trafficFactorApplied"
                checked={formData.trafficFactorApplied}
                onChange={handleChange}
                className="mr-2"
              />
              Apply traffic factor
            </label>
          </div>
        </div>
        
        <div className="flex space-x-3 pt-4">
          <Button
            type="submit"
            loading={loading}
            className="flex-1"
          >
            {initialData ? 'Update Ride' : 'Create Ride'}
          </Button>
          <Button
            type="button"
            variant="secondary"
            onClick={onCancel}
            className="flex-1"
          >
            Cancel
          </Button>
        </div>
      </form>
    </div>
  );
};

export default RideForm;
