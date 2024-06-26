import React from 'react';
import Button from "@mui/material/Button";

const DeactivateDeviceButton = ({ deviceId, deviceStatus, fetchDevices }) => {
    const handleDeactivate = () => {
        console.log(`Deactivate device ${deviceId}`);
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/devices/${deviceId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({}),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to deactivate device');
                }
                console.log('Device deactivated successfully');
                fetchDevices(); // Refresh the device list
            })
            .catch(error => {
                console.error('Error deactivating device:', error.message);
            });
    };

    if (deviceStatus !== "true") {
        return null; // Do not render the button if the status is not "On"
    }

    return (
        <Button variant="contained" sx={{ bgcolor: 'grey','&:hover': { bgcolor: 'darkred' } }} onClick={handleDeactivate}>
            Deactivate
        </Button>
    );
};

export default DeactivateDeviceButton;
