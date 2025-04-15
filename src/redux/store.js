import { createStore } from 'redux';
import userReducer from './Reducer';

// Create Redux store
const store = createStore(
  userReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

export default store;
