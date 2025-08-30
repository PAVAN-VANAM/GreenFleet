import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { CreateUserForm } from '../../types';
import { ROUTES } from '../../utils/constants';
import Button from '../ui/Button';
import Input from '../ui/Input';

const RegisterForm: React.FC = () => {
  const [formData, setFormData] = useState<CreateUserForm>({
    name: '',
    email: '',
    password: '',
    bikeNumber: '',
    bikeLicense: '',
    co2Rate: 0,
    pucCertificate: '',
  });
  const [errors, setErrors] = useState<Partial<CreateUserForm>>({});
  const [loading, setLoading] = useState(false);
  
  const { register } = useAuth();
  const navigate = useNavigate();

  const validateForm = (): boolean => {
    const newErrors: Partial<CreateUserForm> = {};

    if (!formData.name) {
      newErrors.name = 'Name is required';
    } else if (formData.name.length < 2) {
      newErrors.name = 'Name must be at least 2 characters';
    }

    if (!formData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email';
    }

    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) return;

    setLoading(true);
    try {
      await register(formData);
      navigate(ROUTES.DASHBOARD);
    } catch (error) {
      console.error('Registration error:', error);
      setErrors({ email: 'Registration failed. Please try again.' });
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'number' ? parseFloat(value) || 0 : value
    }));
    
    // Clear error when user starts typing
    if (errors[name as keyof CreateUserForm]) {
      setErrors(prev => ({ ...prev, [name]: undefined }));
    }
  };

  return (
    <div className="w-full max-w-md mx-auto">
      <div className="card">
        <div className="text-center mb-6">
          <h2 className="text-2xl font-bold text-gray-900">Create Account</h2>
          <p className="text-gray-600 mt-2">Join the GreenFleet community</p>
        </div>
        
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            label="Full Name"
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            error={errors.name}
            placeholder="Enter your full name"
            required
          />
          
          <Input
            label="Email"
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            error={errors.email}
            placeholder="Enter your email"
            required
          />
          
          <Input
            label="Password"
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            error={errors.password}
            placeholder="Create a password (min 6 characters)"
            required
          />
          
          <Input
            label="Bike Number (Optional)"
            type="text"
            name="bikeNumber"
            value={formData.bikeNumber}
            onChange={handleChange}
            placeholder="Enter your bike number"
          />
          
          <Input
            label="Bike License (Optional)"
            type="text"
            name="bikeLicense"
            value={formData.bikeLicense}
            onChange={handleChange}
            placeholder="Enter your bike license"
          />
          
          <Input
            label="CO2 Rate (Optional)"
            type="number"
            name="co2Rate"
            value={formData.co2Rate?.toString() || ''}
            onChange={handleChange}
            placeholder="Enter CO2 emission rate"
            step="0.1"
          />
          
          <Input
            label="PUC Certificate (Optional)"
            type="text"
            name="pucCertificate"
            value={formData.pucCertificate}
            onChange={handleChange}
            placeholder="Enter PUC certificate number"
          />
          
          <Button
            type="submit"
            className="w-full"
            loading={loading}
          >
            Create Account
          </Button>
        </form>
        
        <div className="mt-6 text-center">
          <p className="text-gray-600">
            Already have an account?{' '}
            <button
              type="button"
              onClick={() => navigate(ROUTES.LOGIN)}
              className="text-primary-600 hover:text-primary-700 font-medium"
            >
              Sign In
            </button>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegisterForm;
