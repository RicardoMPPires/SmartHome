import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardMedia, Typography, Box, Button } from "@mui/material";
import roomImage1 from '../../images/roomImages/room1.png';
import roomImage2 from '../../images/roomImages/room2.png';
import roomImage3 from '../../images/roomImages/room3.png';
import roomImage4 from '../../images/roomImages/room4.png';
import roomImage5 from '../../images/roomImages/room5.png';
import roomImage6 from '../../images/roomImages/room6.png';
import roomImage7 from '../../images/roomImages/room7.png';
import roomImage8 from '../../images/roomImages/room8.png';
import roomImage9 from '../../images/roomImages/room9.png';
import roomImage10 from '../../images/roomImages/room10.png';
import EditButton from '../EditButton';

// Array of room images
const roomImages = [roomImage1, roomImage2, roomImage3, roomImage4, roomImage5, roomImage6, roomImage7, roomImage8, roomImage9, roomImage10];

// Function to get a random image
const getRandomImage = () => {
    return roomImages[Math.floor(Math.random() * roomImages.length)];
};

const RoomCard = ({ roomName, roomHeight, roomLength, roomWidth, onButtonClick }) => {
    // State to store the random image for the room
    const [placeholderImage, setPlaceholderImage] = useState('');

    // Set the image when the component mounts
    useEffect(() => {
        setPlaceholderImage(getRandomImage());
    }, []);

    const [isHovered, setIsHovered] = useState(false);

    return (
        <Card className="card" style={{
            border: '1px solid #ccc',
            borderRadius: '15px',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            transition: 'transform 0.3s ease',
            transform: isHovered ? 'scale(1.05)' : 'scale(1)'
        }} onMouseEnter={() => setIsHovered(true)} onMouseLeave={() => setIsHovered(false)}>
            <Box className="image-container">
                <CardMedia
                    component="img"
                    image={placeholderImage}
                    alt="Room Placeholder"
                    style={{
                        objectFit: 'cover',
                        borderRadius: '0 0 15px 15px', // Rounded corners only at the bottom
                        width: '100%',  // Ensure the image takes 100% width of the container
                        height: '200px',  // Set a fixed height to maintain aspect ratio
                        maxHeight: '200px',  // Limit maximum height to prevent excessive stretching
                    }}
                />
            </Box>
            <CardContent className="content-container">
                <Box className="text-container">
                    <Typography gutterBottom variant="h5" component="div" style={{ fontSize: '1.5rem', marginBottom: '8px' }}>
                        {roomName}
                    </Typography>
                    <Typography variant="body1" color="text.secondary" style={{ marginBottom: '8px' }}>
                        <b>Height:</b> {roomHeight} meters<br />
                        <b>Length:</b> {roomLength} meters<br />
                        <b>Width:</b> {roomWidth} meters<br />
                    </Typography>
                </Box>
                <Box className="button-container">
                    <Button variant="contained" color="primary" onClick={onButtonClick} sx={{ marginRight: '8px' }} roomName={roomName}>
                        View Devices
                    </Button>
                    <EditButton />
                </Box>
            </CardContent>
        </Card>
    );
};

export default RoomCard;
