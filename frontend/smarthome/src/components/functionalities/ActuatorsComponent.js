import React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button'; // Ensure Button is imported correctly from MUI
import StatusSlider from './StatusSlider';
import '../../css/SensorActuatorStyling.css';
import AddFunctionalityButton from './AddFunctionalityButton';
import { useTheme } from '@mui/material';
import clsx from 'clsx';

const formatActuatorType = (type) => {
    return type.replace(/([A-Z])/g, ' $1').trim();
};

const ActuatorsComponent = ({ deviceID, actuators = [], onAddActuator, onUpdate, handleSliderUpdate }) => {
    const theme = useTheme();
    const [deviceStatus, setDeviceStatus] = React.useState('');

    React.useEffect(() => {
        const fetchDeviceStatus = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/devices/${deviceID}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch device status');
                }
                const data = await response.json();
                setDeviceStatus(data.deviceStatus); // Assuming the API returns the status field
            } catch (error) {
                console.error('Error fetching device status:', error);
                setDeviceStatus('false'); // Default to inactive if status fetch fails
            }
        };

        fetchDeviceStatus();
    }, [deviceID]);

    const handleSliderUpdateInternal = (actuatorId, newStatus) => {
        if (handleSliderUpdate) {
            handleSliderUpdate(actuatorId, newStatus);
        }
    };

    return (
        <Box className="container">
            <Box className={clsx("header-row", { "dark-theme": theme.palette.mode === 'dark' })}>
                <Typography variant="h5" className="title"><b>Actuators</b></Typography>
                <Box className="button-container">
                    <AddFunctionalityButton deviceID={deviceID} type="actuator" onFunctionalityAdded={onAddActuator} deviceStatus={deviceStatus} />
                </Box>
            </Box>
            {actuators.length === 0 ? (
                <Typography variant="subtitle1" style={{ textAlign: 'center', marginTop: '20px' }}>
                    No installed Actuators, to install please&nbsp;
                    <span style={{ fontWeight: 'bold', textDecoration: 'underline' }}>press</span>&nbsp;the <b>Add</b> button
                </Typography>
            ) : (
                <Box className="list">
                    <Box className={clsx("header", { "dark-theme": theme.palette.mode === 'dark' })}>
                        <Box className="column name">Name</Box>
                        <Box className="column type">Type</Box>
                        <Box className="column status">Status</Box>
                        <Box className="column actions">Actions</Box>
                    </Box>
                    {actuators.map((actuator, index) => (
                        <Box key={index} className={clsx("row", { "dark-theme": theme.palette.mode === 'dark' })}>
                            <Box className="column name">{actuator.actuatorName}</Box>
                            <Box className="column type">{formatActuatorType(actuator.actuatorTypeID)}</Box>
                            <Box className="column status">{actuator.status}</Box>
                            <Box className="column actions">
                                {(actuator.actuatorTypeID === "RollerBlindActuator" && deviceStatus !== "false")? (
                                    <StatusSlider
                                        initialStatus={actuator.status}
                                        actuatorId={actuator.actuatorId}
                                        onUpdate={(newStatus) => handleSliderUpdateInternal(actuator.actuatorId, newStatus)}
                                    />
                                ) : (
                                    <Button
                                        variant="contained"
                                        style={{ backgroundColor: 'transparent', color: 'black' }} // Set background to transparent and text to black
                                        disabled
                                    >
                                        {deviceStatus === "false" ? 'Inactive Device' : 'Future Release'}
                                    </Button>
                                )}
                            </Box>
                        </Box>
                    ))}
                </Box>
            )}
        </Box>
    );
};

export default ActuatorsComponent;
