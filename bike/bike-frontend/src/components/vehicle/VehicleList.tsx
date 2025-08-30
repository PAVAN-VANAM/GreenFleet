import React from 'react';
import { Vehicle } from '../../types';
import { formatVehicleType, formatFuelType } from '../../utils/helpers';
import Button from '../ui/Button';

interface VehicleListProps {
  vehicles: Vehicle[];
  onEdit: (vehicle: Vehicle) => void;
  onDelete: (vehicleId: number) => void;
  loading?: boolean;
}

const VehicleList: React.FC<VehicleListProps> = ({
  vehicles,
  onEdit,
  onDelete,
  loading = false,
}) => {
  if (loading) {
    return (
      <div className="card">
        <div className="flex items-center justify-center h-32">
          <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600"></div>
        </div>
      </div>
    );
  }

  if (vehicles.length === 0) {
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
              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
            />
          </svg>
          <h3 className="mt-2 text-sm font-medium text-gray-900">No vehicles</h3>
          <p className="mt-1 text-sm text-gray-500">
            Get started by adding your first vehicle.
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {vehicles.map((vehicle) => (
        <div key={vehicle.id} className="card">
          <div className="flex items-center justify-between">
            <div className="flex-1">
              <h4 className="text-lg font-medium text-gray-900">
                {vehicle.model}
              </h4>
              <div className="mt-2 grid grid-cols-2 gap-4 text-sm text-gray-600">
                <div>
                  <span className="font-medium">Type:</span> {formatVehicleType(vehicle.vehicleType)}
                </div>
                <div>
                  <span className="font-medium">Fuel:</span> {formatFuelType(vehicle.fuelType)}
                </div>
                <div>
                  <span className="font-medium">Mileage:</span> {vehicle.mileage} km/l
                </div>
                <div>
                  <span className="font-medium">Seats:</span> {vehicle.seats}
                </div>
                <div>
                  <span className="font-medium">CO2 per km:</span> {vehicle.co2PerKm} kg
                </div>
                <div>
                  <span className="font-medium">Added:</span>{' '}
                  {vehicle.createdAt ? new Date(vehicle.createdAt).toLocaleDateString() : 'Unknown'}
                </div>
              </div>
            </div>
            
            <div className="flex space-x-2 ml-4">
              <Button
                variant="outline"
                size="sm"
                onClick={() => onEdit(vehicle)}
              >
                Edit
              </Button>
              <Button
                variant="danger"
                size="sm"
                onClick={() => vehicle.id && onDelete(vehicle.id)}
              >
                Delete
              </Button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default VehicleList;
