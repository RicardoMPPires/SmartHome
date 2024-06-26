import React, { useState } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import FunctionalityTypeDropdown from "./FunctionalityTypeDropdown";
import DecimalInput from "./DecimalInput";
import { validateInputs } from './FunctionalityInputValidation';

const AddFunctionalityForm = ({ deviceID, type, onFunctionalityAdded, onClose }) => {
    const [formData, setFormData] = useState({});
    const [selectedActuatorType, setSelectedActuatorType] = useState('');
    const [errors, setErrors] = useState({});

    const handleInputChange = (fieldName, value) => {
        setFormData(prevState => ({
            ...prevState,
            [fieldName]: value,
            deviceID, // Include deviceID here
        }));
        if (errors[fieldName]) {
            setErrors(prevErrors => ({
                ...prevErrors,
                [fieldName]: false,
                [`${fieldName}Error`]: '',
            }));
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const newErrors = validateInputs(formData, type, selectedActuatorType);
        setErrors(newErrors);
        if (Object.keys(newErrors).length > 0) {
            return;
        }

        try {
            console.log(formData);
            const response = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/${type}s`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Network response was not ok: ${errorMessage}`);
            }

            const data = await response.json();
            onFunctionalityAdded(data); // Trigger rerender in the parent component
            onClose();
        } catch (error) {
            console.error(`Error adding ${type}:`, error.message);
        }
    };

    const handleActuatorTypeChange = (value) => {
        setSelectedActuatorType(value);
        handleInputChange('actuatorTypeID', value);
    };

    return (
        <Paper elevation={3} sx={{ p: 2, mt: 3, mb: 3 }}>
            <Typography variant="h6" gutterBottom>
                Add New {type === 'sensor' ? 'Sensor' : 'Actuator'}
            </Typography>
            <Box
                component="form"
                onSubmit={handleSubmit}
                sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}
            >
                <FormControl error={errors[`${type}Name`]} variant="outlined" fullWidth>
                    <TextField
                        label={`${type === 'sensor' ? 'Sensor' : 'Actuator'} Name`}
                        value={formData[`${type}Name`] || ''}
                        onChange={(e) => handleInputChange(`${type}Name`, e.target.value)}
                        fullWidth
                        error={errors[`${type}Name`]}
                    />
                    {errors[`${type}Name`] && (
                        <FormHelperText sx={{ color: 'red' }}>{errors[`${type}NameError`]}</FormHelperText>
                    )}
                </FormControl>

                {type === 'actuator' && (
                    <>
                        <FormControl error={errors['actuatorTypeID']} variant="outlined" fullWidth>
                            <FunctionalityTypeDropdown
                                value={formData['actuatorTypeID'] || ''}
                                onChange={handleActuatorTypeChange}
                                type={type} // Pass the type prop to the ActuatorTypeDropdown
                            />
                            {errors['actuatorTypeID'] && (
                                <FormHelperText sx={{ color: 'red' }}>{errors['actuatorTypeIDError']}</FormHelperText>
                            )}
                        </FormControl>

                        {selectedActuatorType === 'DecimalValueActuator' && (
                            <>
                                <FormControl error={errors.precision} variant="outlined" fullWidth>
                                    <DecimalInput
                                        label="Precision"
                                        value={formData.precision || ''}
                                        onChange={(value) => handleInputChange('precision', value)}
                                    />
                                    {errors.precision && (
                                        <FormHelperText sx={{ color: 'red' }}>{errors.precisionError}</FormHelperText>
                                    )}
                                </FormControl>
                                <FormControl error={errors.lowerLimit} variant="outlined" fullWidth>
                                    <DecimalInput
                                        label="Lower Limit"
                                        value={formData.lowerLimit || ''}
                                        onChange={(value) => handleInputChange('lowerLimit', value)}
                                    />
                                    {errors.lowerLimit && (
                                        <FormHelperText sx={{ color: 'red' }}>{errors.lowerLimitError}</FormHelperText>
                                    )}
                                </FormControl>
                                <FormControl error={errors.upperLimit} variant="outlined" fullWidth>
                                    <DecimalInput
                                        label="Upper Limit"
                                        value={formData.upperLimit || ''}
                                        onChange={(value) => handleInputChange('upperLimit', value)}
                                    />
                                    {errors.upperLimit && (
                                        <FormHelperText sx={{ color: 'red' }}>{errors.upperLimitError}</FormHelperText>
                                    )}
                                </FormControl>
                            </>
                        )}

                        {selectedActuatorType === 'IntegerValueActuator' && (
                            <>
                                <FormControl error={errors.lowerLimit} variant="outlined" fullWidth>
                                    <TextField
                                        label="Lower Limit"
                                        value={formData.lowerLimit || ''}
                                        onChange={(e) => handleInputChange('lowerLimit', e.target.value)}
                                        fullWidth
                                        error={errors.lowerLimit}
                                    />
                                    {errors.lowerLimit && (
                                        <FormHelperText sx={{ color: 'red' }}>{errors.lowerLimitError}</FormHelperText>
                                    )}
                                </FormControl>
                                <FormControl error={errors.upperLimit} variant="outlined" fullWidth>
                                    <TextField
                                        label="Upper Limit"
                                        value={formData.upperLimit || ''}
                                        onChange={(e) => handleInputChange('upperLimit', e.target.value)}
                                        fullWidth
                                        error={errors.upperLimit}
                                    />
                                    {errors.upperLimit && (
                                        <FormHelperText sx={{ color: 'red' }}>{errors.upperLimitError}</FormHelperText>
                                    )}
                                </FormControl>
                            </>
                        )}
                    </>
                )}

                {type === 'sensor' && (
                    <FormControl error={errors['sensorTypeID']} variant="outlined" fullWidth>
                        <FunctionalityTypeDropdown
                            value={formData['sensorTypeID'] || ''}
                            onChange={(value) => handleInputChange('sensorTypeID', value)}
                            type={type} // Pass the type prop to the SensorTypeDropdown
                        />
                        {errors['sensorTypeID'] && (
                            <FormHelperText sx={{ color: 'red' }}>{errors['sensorTypeIDError']}</FormHelperText>
                        )}
                    </FormControl>
                )}

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

export default AddFunctionalityForm;
