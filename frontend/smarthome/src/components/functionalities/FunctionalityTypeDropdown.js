import React, { useEffect, useState } from 'react';
import { TextField, MenuItem } from '@mui/material';

const formatType = (type) => {
    // Add a space before each capital letter, except for the first character
    return type.replace(/([A-Z])/g, ' $1').trim();
};

const FunctionalityTypeDropdown = ({ value, onChange, type }) => {
    const [sensorTypes, setSensorTypes] = useState([]);
    const [actuatorTypes, setActuatorTypes] = useState([]);

    useEffect(() => {
        if (type === 'sensor') {
            fetchSensorTypes();
        } else {
            fetchActuatorTypes();
        }
    }, [type]);

    const fetchSensorTypes = () => {
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/sensortypes`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data._embedded && data._embedded['sensorTypeDTOList']) {
                    setSensorTypes(data._embedded['sensorTypeDTOList']);
                } else {
                    console.error('Sensor types not found in response:', data);
                }
            })
            .catch(error => {
                console.error('Error fetching sensor types:', error);
            });
    };

    const fetchActuatorTypes = () => {
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/actuatortypes`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data._embedded && data._embedded['actuatorTypeDTOList']) {
                    setActuatorTypes(data._embedded['actuatorTypeDTOList']);
                } else {
                    console.error('Actuator types not found in response:', data);
                }
            })
            .catch(error => {
                console.error('Error fetching actuator types:', error);
            });
    };

    const types = type === 'sensor' ? sensorTypes : actuatorTypes;
    const idField = type === 'sensor' ? 'sensorTypeID' : 'actuatorTypeID';

    return (
        <TextField
            select
            label={`${type === 'sensor' ? 'Sensor' : 'Actuator'} Type`}
            value={value}
            onChange={(e) => onChange(e.target.value)}
            fullWidth
        >
            {types.map((typeItem, index) => (
                <MenuItem key={typeItem[idField] + index} value={typeItem[idField]}>
                    {formatType(typeItem[idField])}
                </MenuItem>
            ))}
        </TextField>
    );
};

export default FunctionalityTypeDropdown;
