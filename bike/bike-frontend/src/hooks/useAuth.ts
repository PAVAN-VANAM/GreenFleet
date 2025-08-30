import { useState, useEffect, createContext, useContext } from 'react';
import { AuthUser, LoginRequest } from '../types';
import { userAPI } from '../services/api';

interface AuthContextType {
  user: AuthUser | null;
  loading: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
  register: (userData: any) => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const useAuthProvider = () => {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const initializeAuth = () => {
      const storedUser = localStorage.getItem('currentUser');
      if (storedUser) {
        try {
          setUser(JSON.parse(storedUser));
        } catch (error) {
          console.error('Error parsing stored user:', error);
          localStorage.removeItem('currentUser');
        }
      }
      setLoading(false);
    };

    initializeAuth();
  }, []);

  const login = async (credentials: LoginRequest) => {
    try {
      setLoading(true);
      const response = await userAPI.loginUser(credentials);
      
      // Extract user ID from response (assuming format "Login successful! Welcome, {userId}")
      const userIdMatch = response.match(/Welcome,\s*(\d+)/);
      if (userIdMatch) {
        const userId = parseInt(userIdMatch[1], 10);
        const userData = await userAPI.getUserById(userId);
        
        const authUser: AuthUser = {
          id: userData.id,
          name: userData.name,
          email: userData.email,
          rating: userData.rating,
        };
        
        setUser(authUser);
        localStorage.setItem('currentUser', JSON.stringify(authUser));
        localStorage.setItem('authToken', `user_${userId}`); // Simple token for demo
      } else {
        throw new Error('Failed to extract user information');
      }
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const register = async (userData: any) => {
    try {
      setLoading(true);
      const newUser = await userAPI.createUser(userData);
      
      const authUser: AuthUser = {
        id: newUser.id,
        name: newUser.name,
        email: newUser.email,
        rating: newUser.rating,
      };
      
      setUser(authUser);
      localStorage.setItem('currentUser', JSON.stringify(authUser));
      localStorage.setItem('authToken', `user_${newUser.id}`);
    } catch (error) {
      console.error('Registration error:', error);
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('currentUser');
    localStorage.removeItem('authToken');
  };

  return {
    user,
    loading,
    login,
    logout,
    register,
  };
};

export { AuthContext };
