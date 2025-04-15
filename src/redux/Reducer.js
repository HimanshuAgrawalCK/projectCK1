const initialState = {
    isLoggedIn: false,
    userDetails: {},
    token: null,
    role: null,
  };
  
  const userReducer = (state = initialState, action) => {
    console.log("Action received in reducer: ", action);
    switch (action.type) {
      case "LOGIN_SUCCESS":
        return {
          ...state,
          isLoggedIn: true,
          userDetails: action.payload.userDetails,
          token: action.payload.token,
          role: action.payload.role,
        };
      case "LOGOUT":
        return {
          ...state,
          isLoggedIn: false,
          userDetails: null,
          token: null,
          role: null,
        };
  
      default:
        console.log(state);
        return state;
    }
  };
  
  export default userReducer;
  