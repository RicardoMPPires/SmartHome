import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AppBar from "../components/AppBar";
import LandingPage from '../pages/LandingPage';
import RoomPage from "../pages/RoomPage";
import DevicesPage from "../pages/DevicesPage";
import FunctionalityPage from "../pages/FunctionalityPage";

export default function MainRoute({darkMode, setDarkMode, userName, setUserName}) {
    return (
        <Router>
            <AppBar check={darkMode} change={() => setDarkMode(!darkMode)} setUserName={setUserName}/>
            <Routes>
                <Route path="/" element={<LandingPage userName={userName}/>}/>
                <Route path="/rooms" element={<RoomPage />} />
                <Route path="/rooms/:roomId/devices" element={<DevicesPage />} />
                <Route path="/rooms/:roomId/devices/:deviceId" element={<FunctionalityPage />} />
            </Routes>
        </Router>
    );
}
