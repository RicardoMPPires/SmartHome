import React, { useEffect, useState } from 'react';
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { useTheme } from "@mui/material";
import clsx from "clsx";
import AddFunctionalityButton from "./AddFunctionalityButton";

const formatType = (type) => {
    // Add a space before each capital letter, except for the first character
    return type.replace(/([A-Z])/g, ' $1').trim();
};

const SensorsComponent = ({ deviceID, sensors, onAddSensor }) => {
    const theme = useTheme();
    const [logs, setLogs] = useState([]);
    const [sensorTypes, setSensorTypes] = useState({});
    const [deviceStatus, setDeviceStatus] = useState('');

    useEffect(() => {
        const fetchLogs = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/logs?deviceId=${deviceID}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch logs');
                }
                const data = await response.json();
                if (data._embedded && data._embedded.logDTOList) {
                    setLogs(data._embedded.logDTOList);
                } else {
                    setLogs([]);
                }
            } catch (error) {
                console.error('Error fetching logs:', error);
                setLogs([]);
            }
        };

        fetchLogs();
    }, [deviceID]);

    useEffect(() => {
        const fetchSensorTypes = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/sensortypes`);
                if (!response.ok) {
                    throw new Error('Failed to fetch sensor types');
                }
                const data = await response.json();
                if (data._embedded && data._embedded.sensorTypeDTOList) {
                    const types = {};
                    data._embedded.sensorTypeDTOList.forEach(type => {
                        types[type.sensorTypeID] = type.unit;
                    });
                    setSensorTypes(types);
                } else {
                    setSensorTypes({});
                }
            } catch (error) {
                console.error('Error fetching sensor types:', error);
                setSensorTypes({});
            }
        };

        fetchSensorTypes();
    }, []);

    useEffect(() => {
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

    const getReadingForSensor = (sensorID) => {
        const log = logs.find(log => log.sensorID === sensorID);
        return log ? log.reading : "No reading available";
    };

    const getUnitForSensor = (sensorTypeID) => {
        return sensorTypes[sensorTypeID] || "";
    };

    return (
        <Box className="container">
            <Box className={clsx("header-row", { "dark-theme": theme.palette.mode === 'dark' })}>
                <Typography variant="h5" className="title"><b>Sensors</b></Typography>
                <Box className="button-container">
                    <AddFunctionalityButton deviceID={deviceID} type="sensor" onFunctionalityAdded={onAddSensor} deviceStatus={deviceStatus} />
                </Box>
            </Box>

            {sensors.length === 0 ? (
                <Typography variant="subtitle1" style={{ textAlign: 'center', marginTop: '20px' }}>
                    No installed Sensors. Please add sensors using the button above.
                </Typography>
            ) : (
                <Box className="list">
                    <Box className={clsx("header", { "dark-theme": theme.palette.mode === 'dark' })}>
                        <Box className="column name">Name</Box>
                        <Box className="column type">Type</Box>
                        <Box className="column status">Reading</Box>
                        <Box className="column actions">Actions</Box>
                    </Box>
                    {sensors.map((sensor, index) => (
                        <Box key={index} className={clsx("row", { "dark-theme": theme.palette.mode === 'dark' })}>
                            <Box className="column name">{sensor.sensorName}</Box>
                            <Box className="column type">{formatType(sensor.sensorTypeID)}</Box>
                            <Box className="column status">
                                {getReadingForSensor(sensor.sensorID)}
                                {getReadingForSensor(sensor.sensorID) !== "No reading available" && ` ${getUnitForSensor(sensor.sensorTypeID)}`}
                            </Box>
                            <Box className="column actions">
                                <Button
                                    variant="contained"
                                    style={{ backgroundColor: 'transparent', color: 'black' }}
                                    disabled
                                >
                                    {deviceStatus === "false" ? 'Inactive Device' : 'Future Release'}
                                </Button>
                            </Box>
                        </Box>
                    ))}
                </Box>
            )}
        </Box>
    );
};

export default SensorsComponent;

