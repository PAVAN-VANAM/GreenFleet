import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext, useAuthProvider } from './hooks/useAuth';
import { ROUTES } from './utils/constants';
import ProtectedRoute from './components/auth/ProtectedRoute';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import VehiclesPage from './pages/VehiclesPage';
import RidesPage from './pages/RidesPage';
import BookingsPage from './pages/BookingsPage';
import ProfilePage from './pages/ProfilePage';

const App: React.FC = () => {
  const auth = useAuthProvider();

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <div className="App">
          <Routes>
            {/* Public routes */}
            <Route path={ROUTES.LOGIN} element={<LoginPage />} />
            <Route path={ROUTES.REGISTER} element={<RegisterPage />} />
            
            {/* Protected routes */}
            <Route
              path={ROUTES.DASHBOARD}
              element={
                <ProtectedRoute>
                  <DashboardPage />
                </ProtectedRoute>
              }
            />
            <Route
              path={ROUTES.VEHICLES}
              element={
                <ProtectedRoute>
                  <VehiclesPage />
                </ProtectedRoute>
              }
            />
            <Route
              path={ROUTES.RIDES}
              element={
                <ProtectedRoute>
                  <RidesPage />
                </ProtectedRoute>
              }
            />
            <Route
              path={ROUTES.BOOKINGS}
              element={
                <ProtectedRoute>
                  <BookingsPage />
                </ProtectedRoute>
              }
            />
            <Route
              path={ROUTES.PROFILE}
              element={
                <ProtectedRoute>
                  <ProfilePage />
                </ProtectedRoute>
              }
            />
            
            {/* Default redirect */}
            <Route path={ROUTES.HOME} element={<Navigate to={ROUTES.DASHBOARD} replace />} />
            <Route path="*" element={<Navigate to={ROUTES.DASHBOARD} replace />} />
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
};

export default App;