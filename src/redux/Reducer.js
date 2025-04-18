// userReducer.js

const getInitialState = () => {
  const userData = localStorage.getItem("userData");
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";  // Convert it to a boolean

  if (userData && token && role && isLoggedIn) {
    return {
      isLoggedIn,
      userDetails: JSON.parse(userData),
      token,
      role,
    };
  }

  return {
    isLoggedIn: false,
    userDetails: {},
    token: null,
    role: null,
  };
};

const userReducer = (state = getInitialState(), action) => {
  console.log("Action received in reducer: ", action);
  switch (action.type) {
    case "LOGIN_SUCCESS":
      localStorage.setItem("token", action.payload.token);
      localStorage.setItem("userData", JSON.stringify(action.payload.userDetails));
      localStorage.setItem("role", action.payload.role);
      localStorage.setItem("isLoggedIn", "true");  // Add this line to store `isLoggedIn`

      return {
        ...state,
        isLoggedIn: true,
        userDetails: action.payload.userDetails,
        token: action.payload.token,
        role: action.payload.role,
      };

    case "LOGOUT":
      localStorage.removeItem("token");
      localStorage.removeItem("userData");
      localStorage.removeItem("role");
      localStorage.removeItem("isLoggedIn");

      return {
        isLoggedIn: false,
        userDetails: {},
        token: null,
        role: null,
      };

    default:
      return state;
  }
};

export default userReducer;
