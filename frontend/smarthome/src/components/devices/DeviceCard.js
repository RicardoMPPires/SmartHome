import React, { useState, useEffect } from 'react';
import { Card, CardContent, Typography, Box, Button, CardMedia } from "@mui/material";
import EditButton from '../EditButton';
import GetReadingsButton from './GetReadingsButton';
import DeactivateDeviceButton from './DeactivateDeviceButton';
import deviceImage1 from '../../images/deviceImages/device1.png';
import deviceImage2 from '../../images/deviceImages/device2.png';
import deviceImage3 from '../../images/deviceImages/device3.png';
import deviceImage4 from '../../images/deviceImages/device4.png';
import deviceImage5 from '../../images/deviceImages/device5.png';
import deviceImage6 from '../../images/deviceImages/device6.png';
import deviceImage7 from '../../images/deviceImages/device7.png';
import deviceImage8 from '../../images/deviceImages/device8.png';
import deviceImage9 from '../../images/deviceImages/device9.png';
import deviceImage10 from '../../images/deviceImages/device10.png';

// Array of device images
const deviceImages = [deviceImage1, deviceImage2, deviceImage3, deviceImage4, deviceImage5, deviceImage6, deviceImage7, deviceImage8, deviceImage9, deviceImage10];

// Function to get a random image
const getRandomImage = () => {
    return deviceImages[Math.floor(Math.random() * deviceImages.length)];
};

const DeviceCard = ({ deviceID, deviceName, deviceModel, deviceStatus, onButtonClick, fetchDevices }) => {
    // State to store the random image for the device
    const [deviceImage, setDeviceImage] = useState('');

    // Set the image when the component mounts
    useEffect(() => {
        setDeviceImage(getRandomImage());
    }, []);

    const [isHovered, setIsHovered] = useState(false);

    return (
        <Card className="card" style={{
            border: '1px solid #ccc',
            borderRadius: '15px',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            width: '100%',
            height: '100%',
            transition: 'transform 0.3s ease',
            transform: isHovered ? 'scale(1.05)' : 'scale(1)'
        }} onMouseEnter={() => setIsHovered(true)} onMouseLeave={() => setIsHovered(false)}>
            <CardMedia
                component="img"
                image={deviceImage}
                alt="Device"
                style={{
                    objectFit: 'cover',
                    borderRadius: '15px 15px 0 0',
                    width: '100%',
                    height: '200px', // Fixed height for consistent size
                    maxHeight: '200px', // Limit maximum height to prevent excessive stretching
                }}
            />
            <CardContent className="content-container">
                <Box className="text-container">
                    <Typography gutterBottom variant="h5" component="div" style={{ fontSize: '1.5rem', marginBottom: '8px' }}>
                        {deviceName}
                    </Typography>
                    <Typography variant="body1" color="text.secondary" style={{ marginBottom: '8px' }}>
                        Model: {deviceModel}<br />
                        Status: {deviceStatus === "true" ? 'On' : 'Off'}
                    </Typography>
                </Box>
                <Box className="button-container" sx={{
                    display: 'grid',
                    gridTemplateColumns: '1fr 1fr', // Equal-width columns
                    gap: '16px',
                }}>
                    <Box sx={{ display: 'grid', gap: '8px' }}>
                        <Button variant="contained" color="primary" onClick={() => onButtonClick(deviceID)}
                                sx={{ width: '100%', whiteSpace: 'pre-line', fontSize: '12px' }}>
                            View Functionalities
                        </Button>
                        <GetReadingsButton deviceID={deviceID} sx={{ width: '100%', height: '90px', fontSize: '10px' }} />
                    </Box>
                    <Box sx={{ display: 'grid', gap: '8px' }}>
                        <EditButton sx={{ width: '100%' }} />
                        <DeactivateDeviceButton deviceId={deviceID} deviceStatus={deviceStatus} fetchDevices={fetchDevices} sx={{ width: '100%' }} />
                    </Box>
                </Box>
            </CardContent>
        </Card>
    );
};

export default DeviceCard;
