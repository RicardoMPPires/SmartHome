import React from 'react';
import Slider from '@mui/material/Slider';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';

const DecimalInput = ({ label, value, onChange }) => {
    const handleChange = (_, newValue) => {
        onChange(newValue);
    };

    return (
        <div>
            <Typography id="discrete-slider" gutterBottom>
                {label}
            </Typography>
            <Grid container spacing={2} alignItems="center">
                <Grid item xs>
                    <Slider
                        value={value === '' ? 0 : parseFloat(value)}
                        onChange={handleChange}
                        min={0}
                        max={label === 'Precision' ? 1 : 100} // Adjust max value accordingly
                        step={0.1} // Define the step as needed
                        valueLabelDisplay="auto"
                    />
                </Grid>
                <Grid item>
                    <Typography>{value}</Typography>
                </Grid>
            </Grid>
        </div>
    );
};

export default DecimalInput;
