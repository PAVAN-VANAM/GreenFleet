import React, { useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import { userAPI } from '../services/api';
import { User } from '../types';
import Layout from '../components/layout/Layout';
import Button from '../components/ui/Button';
import Input from '../components/ui/Input';

const ProfilePage: React.FC = () => {
  const { user, logout } = useAuth();
  const [editing, setEditing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    name: user?.name || '',
    email: user?.email || '',
    bikeNumber: '',
    bikeLicense: '',
    co2Rate: '',
    pucCertificate: '',
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) return;

    try {
      setLoading(true);
      await userAPI.updateUser(user.id, {
        name: formData.name,
        email: formData.email,
        bikeNumber: formData.bikeNumber || undefined,
        bikeLicense: formData.bikeLicense || undefined,
        co2Rate: formData.co2Rate ? parseFloat(formData.co2Rate) : undefined,
        pucCertificate: formData.pucCertificate || undefined,
      });
      setEditing(false);
    } catch (error) {
      console.error('Error updating profile:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  return (
    <Layout>
      <div className="max-w-2xl mx-auto space-y-6">
        {/* Header */}
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Profile</h1>
          <p className="text-gray-600">Manage your account information</p>
        </div>

        {/* Profile Card */}
        <div className="card">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-lg font-medium text-gray-900">
              Personal Information
            </h2>
            {!editing && (
              <Button variant="outline" onClick={() => setEditing(true)}>
                Edit Profile
              </Button>
            )}
          </div>

          {editing ? (
            <form onSubmit={handleSubmit} className="space-y-4">
              <Input
                label="Full Name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
              />
              
              <Input
                label="Email"
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
              />
              
              <Input
                label="Bike Number"
                name="bikeNumber"
                value={formData.bikeNumber}
                onChange={handleChange}
                placeholder="Optional"
              />
              
              <Input
                label="Bike License"
                name="bikeLicense"
                value={formData.bikeLicense}
                onChange={handleChange}
                placeholder="Optional"
              />
              
              <Input
                label="CO2 Rate"
                type="number"
                name="co2Rate"
                value={formData.co2Rate}
                onChange={handleChange}
                placeholder="Optional"
                step="0.1"
              />
              
              <Input
                label="PUC Certificate"
                name="pucCertificate"
                value={formData.pucCertificate}
                onChange={handleChange}
                placeholder="Optional"
              />
              
              <div className="flex space-x-3 pt-4">
                <Button type="submit" loading={loading} className="flex-1">
                  Save Changes
                </Button>
                <Button
                  type="button"
                  variant="secondary"
                  onClick={() => setEditing(false)}
                  className="flex-1"
                >
                  Cancel
                </Button>
              </div>
            </form>
          ) : (
            <div className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="label">Name</label>
                  <p className="text-gray-900">{user?.name}</p>
                </div>
                <div>
                  <label className="label">Email</label>
                  <p className="text-gray-900">{user?.email}</p>
                </div>
                <div>
                  <label className="label">Rating</label>
                  <p className="text-gray-900">
                    {user?.rating ? `${user.rating}/5` : 'Not rated yet'}
                  </p>
                </div>
                <div>
                  <label className="label">User ID</label>
                  <p className="text-gray-900">{user?.id}</p>
                </div>
              </div>
            </div>
          )}
        </div>

        {/* Account Actions */}
        <div className="card">
          <h2 className="text-lg font-medium text-gray-900 mb-4">
            Account Actions
          </h2>
          <div className="space-y-3">
            <Button variant="danger" onClick={logout} className="w-full">
              Sign Out
            </Button>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default ProfilePage;
