import axios from "axios";
import store from "../redux/store";
import { showToast } from "../components/common/Toaster";

const URL = `http://localhost:8080`;
const API_URL = `${URL}/api/auth`;

export const authAxios = axios.create();
let isTokenExpired = false;

authAxios.interceptors.request.use(
  (config) => {
    const token = store.getState().user.token;
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    console.error("Error in request: ", error);
    showToast("Error in request");
    return Promise.reject(error);
  }
);

authAxios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (
      error.response &&
      error.response.status === 401 &&
      !isTokenExpired
    ) {
      isTokenExpired = true;
      showToast("Session expired. Please log in again", 401);
      setTimeout(() => {
        window.location.href = "/login";
      }, 1500);
    }
    if(error.response && error.response.status === 400){
      showToast(error.response.data, 400);
    }
    return Promise.reject(error);
  }
);

// Login
export const loginUser = async (email, password) => {
  const response = await axios.post(`${URL}/cloudbalance/login`, {
    email,
    password,
  });
  return response.data;
};

// Get user details
export const getUserDetails = async (userId) => {
  const response = await authAxios.get(`${API_URL}/${userId}`);
  return response.data;
};

// Fetch users
export const fetchUsers = async (page, size) => {
  const response = await authAxios.get(
    `${API_URL}/users?page=${page}&size=${size}`
  );
  return response.data;
};

// Fetch roles
export const fetchRoles = async () => {
  const response = await authAxios.get(`${URL}/roles`);
  return response.data;
};

// Create user
export const createUser = async (userData) => {
  const response = await authAxios.post(`${API_URL}/register`, userData);
  return response.data;
};

// Get accounts
export const getAllAccounts = async (id = 0) => {
  const response = await authAxios.get(`${URL}/aws?id=${id}`);
  return response.data;
};

// Logout
export const logOut = async () => {
  const response = await authAxios.post(`${URL}/cloudbalance/logout`);
  return response;
};

// Update user
export const updateUser = async (userId, userData) => {
  const response = await authAxios.patch(`${API_URL}/${userId}`, userData);
  return response.data;
};

// Add account
export const addAccount = async (accountData) => {
  const response = await authAxios.post(`${URL}/aws`, accountData);
  return response.data;
};

export const ec2Instance = async (accountId) => {
  const response = await authAxios.get(`${URL}/aws/ec2instances?accountId=${accountId}`);
  return response.data;
}

export const rdsInstance = async (accountId) => {
  const response = await authAxios.get(`${URL}/aws/rdsinstances?accountId=${accountId}`);
  return response.data;
}

export const asgInstance = async (accountId) => {
  const response = await authAxios.get(`${URL}/aws/asginstances?accountId=${accountId}`);
  return response.data;
}

export const getAllColumns = async() =>{
  const response = await authAxios.get(`${URL}/snowflake/getAllColumns`);
  return response.data;
}

export const getChartDataWithGroupAndFilters = async(requestData) =>{
  const response = await authAxios.post(`${URL}/snowflake/getSumByGroupAndFilter`,requestData);
  return response.data;
}