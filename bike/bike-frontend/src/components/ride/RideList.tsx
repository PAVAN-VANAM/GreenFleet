import React from 'react';
import { Ride } from '../../types';
import { formatCurrency, formatDistance, formatDate } from '../../utils/helpers';
import Button from '../ui/Button';

interface RideListProps {
  rides: Ride[];
  onEdit?: (ride: Ride) => void;
  onDelete?: (rideId: number) => void;
  onBook?: (ride: Ride) => void;
  loading?: boolean;
  showActions?: boolean;
  currentUserId?: number;
}

const RideList: React.FC<RideListProps> = ({
  rides,
  onEdit,
  onDelete,
  onBook,
  loading = false,
  showActions = true,
  currentUserId,
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

  if (rides.length === 0) {
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
              d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <h3 className="mt-2 text-sm font-medium text-gray-900">No rides available</h3>
          <p className="mt-1 text-sm text-gray-500">
            Create a new ride to get started.
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {rides.map((ride) => {
        const isOwnRide = currentUserId && ride.user?.id === currentUserId;
        const totalFare = (ride.distanceKm * ride.farePerKm);
        
        return (
          <div key={ride.rideId} className="card">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <div className="flex items-center space-x-2 mb-2">
                  <h4 className="text-lg font-medium text-gray-900">
                    {ride.sourceAddress} → {ride.destinationAddress}
                  </h4>
                  {isOwnRide && (
                    <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                      Your Ride
                    </span>
                  )}
                </div>
                
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm text-gray-600">
                  <div>
                    <span className="font-medium">Driver:</span> {ride.user?.name || 'Unknown'}
                  </div>
                  <div>
                    <span className="font-medium">Vehicle:</span> {ride.vehicle?.model || 'Unknown'}
                  </div>
                  <div>
                    <span className="font-medium">Available Seats:</span> {ride.availableSeats}
                  </div>
                  <div>
                    <span className="font-medium">Distance:</span> {formatDistance(ride.distanceKm)}
                  </div>
                  <div>
                    <span className="font-medium">Fare per km:</span> {formatCurrency(ride.farePerKm)}
                  </div>
                  <div>
                    <span className="font-medium">Total Fare:</span> {formatCurrency(totalFare)}
                  </div>
                  <div>
                    <span className="font-medium">Weather Factor:</span> {ride.weatherFactorApplied ? 'Yes' : 'No'}
                  </div>
                  <div>
                    <span className="font-medium">Traffic Factor:</span> {ride.trafficFactorApplied ? 'Yes' : 'No'}
                  </div>
                </div>
                
                {ride.createdAt && (
                  <div className="mt-2 text-xs text-gray-500">
                    Created: {formatDate(ride.createdAt)}
                  </div>
                )}
              </div>
              
              {showActions && (
                <div className="flex space-x-2 ml-4">
                  {isOwnRide ? (
                    <>
                      {onEdit && (
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => onEdit(ride)}
                        >
                          Edit
                        </Button>
                      )}
                      {onDelete && ride.rideId && (
                        <Button
                          variant="danger"
                          size="sm"
                          onClick={() => onDelete(ride.rideId!)}
                        >
                          Delete
                        </Button>
                      )}
                    </>
                  ) : (
                    onBook && ride.availableSeats > 0 && (
                      <Button
                        variant="primary"
                        size="sm"
                        onClick={() => onBook(ride)}
                      >
                        Book Ride
                      </Button>
                    )
                  )}
                </div>
              )}
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default RideList;
