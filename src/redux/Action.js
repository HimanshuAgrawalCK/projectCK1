export const LOGIN = 'LOGIN_SUCCESS';
export const LOGOUT = 'LOGOUT';

export const loginSuccess = (token, role, userDetails)=>({
    type: LOGIN,
    payload:{
        token,role,userDetails
    }
})

export const logout = () =>({
    type: LOGOUT
})