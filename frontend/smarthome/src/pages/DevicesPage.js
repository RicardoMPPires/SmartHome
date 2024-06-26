import React, { useEffect, useState, useCallback } from 'react';
import { alpha, Box } from "@mui/material";
import { useNavigate, useParams } from 'react-router-dom';
import DeviceCardContainer from '../components/devices/DeviceCardContainer';
import GoBackButton from '../components/GoBackButton';
import DeviceHeader from "../components/devices/DeviceHeader";
import AddDeviceButton from '../components/devices/AddDeviceButton'; // Adjust the import path as necessary

export default function DevicesPage() {
    const [devices, setDevices] = useState([]);
    const [roomName, setRoomName] = useState('');
    const navigate = useNavigate();
    const { roomId } = useParams();

    const fetchDevices = useCallback(() => {
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/devices?roomID=${roomId}`)
            .then(response => response.json())
            .then(data => {
                if (data._embedded && data._embedded.deviceDTOList) {
                    setDevices(data._embedded.deviceDTOList);
                } else {
                    setDevices([]);
                }
            })
            .catch(err => console.log(err))
    }, [roomId]);

    useEffect(() => {
        // Fetch room details
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/rooms/${roomId}`)
            .then(response => response.json())
            .then(data => {
                setRoomName(data.roomName);
            })
            .catch(err => console.log(err));

        fetchDevices();
    }, [fetchDevices, roomId]);

    const handleViewDeviceDetails = (deviceId) => {
        navigate(`/rooms/${roomId}/devices/${deviceId}`);
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
                <AddDeviceButton roomID={roomId} onDeviceAdded={fetchDevices} />
            </Box>
            <Box sx={{ width: '100%', display: 'flex', justifyContent: 'flex-start', alignItems: 'center', paddingLeft: '10%' }}>
                <DeviceHeader roomName={roomName}/>
            </Box>
            <DeviceCardContainer devices={devices} onViewDetails={handleViewDeviceDetails} fetchDevices={fetchDevices} />
        </Box>
    );
}
