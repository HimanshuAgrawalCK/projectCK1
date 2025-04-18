import React from "react";
import { showToast } from "../../common/Toaster";

export default function DonePage({handleRestart}) {

    const handleSubmit = (e) =>{
        e.preventDefault();
        showToast("Redirecting to Onboarding Page");
        setTimeout(() => {
            handleRestart();
        }, 2000);
    }
    return (
        <div className="done-page">
            <img src="/images/green_tick_check.svg" alt="Success checkmark" />
            <h1>Thank you for CUR Access!</h1>
            <span>If you have additional accounts to onboard, Please Click on 
                <button type="submit" onClick={handleSubmit}>Onboard</button> to proceed.
            </span>
        </div>
    );
}
