import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

// Reusable toast based on status code
export const showToast = (message, statusCode) => {
  if (statusCode >= 200 && statusCode < 300) {
    toast.success(message);
  } else if (statusCode >= 400 && statusCode < 500) {
    toast.warn(message+"\t"+statusCode);
  } else if (statusCode >= 500) {
    toast.error(message+"\t"+statusCode);
  } else {
    toast.info(message);
  }
};

// ToastContainer wrapper (used once globally, typically in App.js)
export const ToastWrapper = () => <ToastContainer position="top-right" autoClose={3000} />;
