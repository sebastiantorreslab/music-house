import React from 'react';
import "../Login.css";
import EmailIcon from '@mui/icons-material/Email';

const VerifyEmail = () => {
    return (
        <div className='verify-container'>
            <EmailIcon />
            <h2>Verifica tu email</h2>
            <p>Hemos enviado un email de verificación.</p>
            <p>El link expira en 48 hs.</p>
        </div>
    );
}

export default VerifyEmail;
