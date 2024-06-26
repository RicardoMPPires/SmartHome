import React, { useEffect, useState } from 'react';
import { alpha, Box } from "@mui/material";
import { useNavigate } from 'react-router-dom';
import RoomCardContainer from '../components/rooms/RoomCardContainer';
import GoBackButton from '../components/GoBackButton';
import RoomHeader from "../components/rooms/RoomHeader";


export default function Room() {
    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/rooms`)
            .then(response => response.json())
            .then(data => {
                if (data._embedded && data._embedded.roomDTOList) {
                    setRooms(data._embedded.roomDTOList);
                } else {
                    setRooms([]);
                }
            })
            .catch(err => console.log(err))
    }, []);

    const handleViewDevices = (roomId) => {
        navigate(`/rooms/${roomId}/devices`);
    };

    return (
        <Box sx={(theme) => ({
            width: '100%',
            minHeight: '100vh',
            backgroundImage:
                theme.palette.mode === 'light'
                    ? 'linear-gradient(180deg, #CEE5FD, #FFF)'
                    : `linear-gradient(180deg, #02294F, ${alpha('#090E10', 0.0)})`,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            margin: 0,
            padding: '20px',
            paddingTop: '64px'
        })}>
            <Box sx={{ width: '100%', display: 'flex', justifyContent: 'flex-end', marginBottom: '20px' }}>
                <GoBackButton />
            </Box>
            <Box sx={{ width: '100%', display: 'flex', justifyContent: 'flex-start', alignItems: 'center', paddingLeft: '10%' }}>
                <RoomHeader />
            </Box>
            <RoomCardContainer rooms={rooms} onButtonClick={handleViewDevices} />
        </Box>
    );
}
