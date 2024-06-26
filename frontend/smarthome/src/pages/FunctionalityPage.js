import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import ActuatorsComponent from '../components/functionalities/ActuatorsComponent';
import SensorsComponent from '../components/functionalities/SensorsComponent';
import GoBackButton from '../components/GoBackButton';
import { alpha, useMediaQuery, useTheme } from "@mui/material";

const drawerWidth = 125;

// Custom hook to fetch actuators and sensors data
const useDeviceData = (deviceId) => {
    const [actuators, setActuators] = useState([]);
    const [sensors, setSensors] = useState([]);

    useEffect(() => {
        const fetchDeviceData = async () => {
            try {
                const actuatorResponse = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/actuators?deviceId=${deviceId}`);
                const sensorResponse = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/sensors?deviceId=${deviceId}`);

                const actuatorData = await actuatorResponse.json();
                const sensorData = await sensorResponse.json();

                if (actuatorData._embedded && actuatorData._embedded.actuatorDTOList) {
                    setActuators(actuatorData._embedded.actuatorDTOList);
                } else {
                    setActuators([]);
                }

                if (sensorData._embedded && sensorData._embedded.sensorDTOList) {
                    setSensors(sensorData._embedded.sensorDTOList);
                } else {
                    setSensors([]);
                }
            } catch (error) {
                console.error('Error fetching device data:', error);
            }
        };

        fetchDeviceData();

    }, [deviceId]);

    return { actuators, sensors, setActuators, setSensors };
};

const FunctionalityPage = () => {
    const { deviceId } = useParams();
    const { actuators, setActuators, sensors, setSensors } = useDeviceData(deviceId);

    // Function to update actuators state
    const updateActuatorsState = (updatedActuators) => {
        setActuators(updatedActuators);
    };

    // Function to update actuators
    const onAddActuator = (newActuator) => {
        setActuators(prevActuators => [...prevActuators, newActuator]);
    };

    // Function to update Sensors
    const onAddSensor = (newSensor) => {
        setSensors(prevSensors => [...prevSensors, newSensor]);
    };

    const theme = useTheme();
    const isSmallScreen = useMediaQuery(theme.breakpoints.down('sm'));

    return (
        <Box sx={(theme) => ({
            width: isSmallScreen ? '100%' : `calc(100% - ${drawerWidth}px)`,
            marginLeft: isSmallScreen ? '0px' : `${drawerWidth}px`,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            padding: '20px',
            paddingTop: '64px',
            backgroundImage:
                theme.palette.mode === 'light'
                    ? 'linear-gradient(180deg, #CEE5FD, #FFF)'
                    : `linear-gradient(180deg, #02294F, ${alpha('#090E10', 0.0)})`,
        })}>
            <Box sx={{width: '100%', display: 'flex', justifyContent: 'flex-end', marginBottom: '20px'}}>
                <GoBackButton/>
            </Box>
            <Box className="functionality-page-container" sx={{width: '100%'}}>
                <Box className="functionality-content" sx={{width: '100%'}}>
                    <Box className="functionality-box" sx={{width: '100%'}}>
                        <Typography variant="h4" className="functionality-box-title">Device Functionalities</Typography>
                        <ActuatorsComponent
                            deviceID={deviceId}
                            actuators={actuators}
                            onAddActuator={onAddActuator}
                            onUpdate={updateActuatorsState}
                            handleSliderUpdate={(actuatorId, newStatus) => {
                                const updatedActuators = actuators.map(actuator => {
                                    if (actuator.actuatorId === actuatorId) {
                                        return { ...actuator, status: newStatus };
                                    }
                                    return actuator;
                                });
                                updateActuatorsState(updatedActuators);
                            }}
                        />
                        <SensorsComponent deviceID={deviceId} sensors={sensors} onAddSensor={onAddSensor}/>
                    </Box>
                </Box>
            </Box>
        </Box>
    );
}

export default FunctionalityPage;
