import React from 'react';
import Welcome from "../components/Welcome";
import DateTimeCard from "../components/DateTimeCard";

export default function LandingPage({userName}) {
    return (
        <div>
            <Welcome userName={userName}/>
        <DateTimeCard/>
        </div>
    )
}