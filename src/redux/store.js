// store.js
import { createStore } from 'redux';
import { combineReducers } from 'redux';  // Import combineReducers

import userReducer from './Reducer';  // Assuming your reducer is in Reducer.js

// Combine reducers if you have more than one
const rootReducer = combineReducers({
  user: userReducer,  // You can add more reducers as your app grows
});

// Create the Redux store with the combined reducers and DevTools integration
const store = createStore(
  rootReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()  // Redux DevTools
);

export default store;
