import axios from "axios";
import store from "../redux/store";
const URL = `http://localhost:8080`;
const API_URL = `${URL}/api/auth`;

export const authAxios = axios.create();

authAxios.interceptors.request.use(
  (config) => {
    const token = store.getState().token;
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

authAxios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export const loginUser = async (email, password) => {
  try {
    const response = await axios.post(`${URL}/cloudbalance/login`, {
      email,
      password,
    });
    console.log("I was here");
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const validateToken = async (token) => {
  try {
    const response = await authAxios.get(`${API_URL}/validate`);
    return response.data;
  } catch (error) {
    throw new Error("Invalid token");
  }
};

//GEtting all user details
export const getUserDetails = async (userId) => {
  try {
    const token = store.getState().token;
    const response = await authAxios.get(`${API_URL}/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching user details: ", error);
  }
  // console.log("Response from get user details: ", response.data);
};

export const fetchUsers = async (page, size) => {
  try {
    const token = localStorage.getItem("token");
    console.log("TOken is : ", token);
    const response = await authAxios.get(
      `${API_URL}/users?page=${page}&size=${size}`,
      {
        headers: {
          Authorization: `Bearer ${token}}`,
        },
      }
    );
    console.log("Response from fetch users: ", response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching users: ", error);
  }
};

export const fetchRoles = async () => {
  try {
    const token = store.getState().token;
    const response = await authAxios.get(`${URL}/roles`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log("Response from fetch roles: ", response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching roles: ", error);
  }
};

export const createUser = async (userData) => {
  try {
    const token = localStorage.getItem("token");
    console.log("Token is : ", token);
    const response = await authAxios.post(`${API_URL}/register`, userData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error creating user: ", error);
    throw error;
  }
};

export const getAllAccounts = async (id=0) => {
  try {
    const token = localStorage.getItem("token");
    const response = await authAxios.get(`${URL}/aws?id=${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log("Response from get all accounts: ", response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching accounts: ", error);
    throw error;
  }
};

export const logOut = async () => {
  try {
    const token = localStorage.getItem("token");
    const response = await authAxios.post(`${URL}/cloudbalance/logout`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};


export const updateUser = async (userId, userData) => {
  try{
    const response = await authAxios.patch(`${API_URL}/${userId}`, userData,{
      headers: {
        Authorization: `Bearer ${store.getState().token}`
    }});
    return response.data;
  } catch(error){
    console.error("Error updating user: ", error);
    throw error;
  }
};