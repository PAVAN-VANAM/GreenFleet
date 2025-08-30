import React, { useState } from 'react';
import { CreateVehicleForm, VehicleType, FuelType } from '../../types';
import { VEHICLE_TYPE_OPTIONS, FUEL_TYPE_OPTIONS } from '../../utils/constants';
import Button from '../ui/Button';
import Input from '../ui/Input';
import Select from '../ui/Select';

interface VehicleFormProps {
  onSubmit: (data: CreateVehicleForm) => Promise<void>;
  onCancel: () => void;
  initialData?: Partial<CreateVehicleForm>;
  loading?: boolean;
}

const VehicleForm: React.FC<VehicleFormProps> = ({
  onSubmit,
  onCancel,
  initialData,
  loading = false,
}) => {
  const [formData, setFormData] = useState<CreateVehicleForm>({
    vehicleType: initialData?.vehicleType || VehicleType.TWO_WHEELER,
    model: initialData?.model || '',
    mileage: initialData?.mileage || 0,
    fuelType: initialData?.fuelType || FuelType.PETROL,
    seats: initialData?.seats || 1,
    co2PerKm: initialData?.co2PerKm || 0,
  });
  const [errors, setErrors] = useState<Partial<CreateVehicleForm>>({});

  const validateForm = (): boolean => {
    const newErrors: Partial<CreateVehicleForm> = {};

    if (!formData.model) {
      newErrors.model = 'Model is required';
    }

    if (!formData.mileage || formData.mileage <= 0) {
      newErrors.mileage = 'Mileage must be greater than 0';
    }

    if (!formData.seats || formData.seats <= 0) {
      newErrors.seats = 'Seats must be greater than 0';
    }

    if (!formData.co2PerKm || formData.co2PerKm <= 0) {
      newErrors.co2PerKm = 'CO2 per km must be greater than 0';
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
      console.error('Vehicle form submission error:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'number' ? parseFloat(value) || 0 : value
    }));
    
    // Clear error when user starts typing
    if (errors[name as keyof CreateVehicleForm]) {
      setErrors(prev => ({ ...prev, [name]: undefined }));
    }
  };

  return (
    <div className="card">
      <h3 className="text-lg font-medium text-gray-900 mb-4">
        {initialData ? 'Edit Vehicle' : 'Add New Vehicle'}
      </h3>
      
      <form onSubmit={handleSubmit} className="space-y-4">
        <Select
          label="Vehicle Type"
          name="vehicleType"
          value={formData.vehicleType}
          onChange={handleChange}
          options={VEHICLE_TYPE_OPTIONS}
          required
        />
        
        <Input
          label="Model"
          type="text"
          name="model"
          value={formData.model}
          onChange={handleChange}
          error={errors.model}
          placeholder="Enter vehicle model"
          required
        />
        
        <Input
          label="Mileage (km/l)"
          type="number"
          name="mileage"
          value={formData.mileage.toString()}
          onChange={handleChange}
          error={errors.mileage}
          placeholder="Enter mileage"
          step="0.1"
          required
        />
        
        <Select
          label="Fuel Type"
          name="fuelType"
          value={formData.fuelType}
          onChange={handleChange}
          options={FUEL_TYPE_OPTIONS}
          required
        />
        
        <Input
          label="Number of Seats"
          type="number"
          name="seats"
          value={formData.seats.toString()}
          onChange={handleChange}
          error={errors.seats}
          placeholder="Enter number of seats"
          min="1"
          required
        />
        
        <Input
          label="CO2 per km (kg)"
          type="number"
          name="co2PerKm"
          value={formData.co2PerKm.toString()}
          onChange={handleChange}
          error={errors.co2PerKm}
          placeholder="Enter CO2 emission per km"
          step="0.01"
          required
        />
        
        <div className="flex space-x-3 pt-4">
          <Button
            type="submit"
            loading={loading}
            className="flex-1"
          >
            {initialData ? 'Update Vehicle' : 'Add Vehicle'}
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

export default VehicleForm;
