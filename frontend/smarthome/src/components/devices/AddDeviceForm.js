import React, { useState } from 'react';
import Box from '@mui/material/Box';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import FormHelperText from '@mui/material/FormHelperText';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';

const AddDeviceForm = ({ roomID, onDeviceAdded, onClose }) => {
    const [deviceName, setDeviceName] = useState('');
    const [deviceModel, setDeviceModel] = useState('');
    const [errors, setErrors] = useState({ deviceName: false, deviceModel: false });

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Validate inputs
        let hasErrors = false;
        const newErrors = { deviceName: false, deviceModel: false };

        if (!deviceName) {
            newErrors.deviceName = true;
            hasErrors = true;
        }

        if (!deviceModel) {
            newErrors.deviceModel = true;
            hasErrors = true;
        }

        setErrors(newErrors);

        if (hasErrors) {
            return;
        }

        const newDevice = {
            deviceName,
            deviceModel,
            roomID,
        };

        try {
            const response = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/devices`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newDevice),
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            onDeviceAdded(data);
            setDeviceName('');
            setDeviceModel('');
            onClose(); // Close the modal after adding the device
        } catch (error) {
            console.error('Error adding device:', error);
        }
    };

    const handleDeviceNameChange = (e) => {
        setDeviceName(e.target.value);
        if (errors.deviceName && e.target.value) {
            setErrors((prevErrors) => ({ ...prevErrors, deviceName: false }));
        }
    };

    const handleDeviceModelChange = (e) => {
        setDeviceModel(e.target.value);
        if (errors.deviceModel && e.target.value) {
            setErrors((prevErrors) => ({ ...prevErrors, deviceModel: false }));
        }
    };

    return (
        <Paper elevation={3} sx={{ p: 2, mt: 3, mb: 3 }}>
            <Typography variant="h6" gutterBottom>
                Add New Device
            </Typography>
            <Box
                component="form"
                onSubmit={handleSubmit}
                sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}
            >
                <FormControl variant="outlined" error={errors.deviceName} fullWidth>
                    <InputLabel htmlFor="device-name">Device Name</InputLabel>
                    <OutlinedInput
                        id="device-name"
                        value={deviceName}
                        onChange={handleDeviceNameChange}
                        label="Device Name"
                    />
                    {errors.deviceName && (
                        <FormHelperText>Mandatory field</FormHelperText>
                    )}
                </FormControl>

                <FormControl variant="outlined" error={errors.deviceModel} fullWidth>
                    <InputLabel htmlFor="device-model">Device Model</InputLabel>
                    <OutlinedInput
                        id="device-model"
                        value={deviceModel}
                        onChange={handleDeviceModelChange}
                        label="Device Model"
                    />
                    {errors.deviceModel && (
                        <FormHelperText>Mandatory field</FormHelperText>
                    )}
                </FormControl>

                <Box sx={{ display: 'flex', justifyContent: 'flex-end', gap: 1, mt: 2 }}>
                    <Button
                        variant="contained"
                        sx={{
                            backgroundColor: 'red',
                            color: 'white',
                            '&:hover': {
                                backgroundColor: 'darkred',
                            },
                        }}
                        onClick={onClose}
                    >
                        Cancel
                    </Button>
                    <Button
                        type="submit"
                        variant="contained"
                        sx={{
                            backgroundColor: 'green',
                            color: 'white',
                            '&:hover': {
                                backgroundColor: 'darkgreen',
                            },
                        }}
                    >
                        Save
                    </Button>
                </Box>
            </Box>
        </Paper>
    );
};

export default AddDeviceForm;
