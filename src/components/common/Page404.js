import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
const Page404 = () => {

    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            <h1>404 - Page Not Found</h1>
            <p>The page you're looking for doesn't exist or has been moved.</p>
            <Link to="/login">Go back to Home</Link>
        </div>
    );
};

export default Page404;
