import React, { useState } from 'react';
import Slider from '@mui/material/Slider';
import Button from '@mui/material/Button';
import '../../css/StatusSlider.css';

const isValidNumber = (value) => {
    const number = parseInt(value, 10);
    return !isNaN(number) ? number : 100;
};

const StatusSlider = ({ initialStatus, onUpdate, actuatorId }) => {
    const [newStatus, setNewStatus] = useState(isValidNumber(initialStatus));
    const [loading, setLoading] = useState(false);
    const [feedback, setFeedback] = useState(null);
    const [unsavedChanges, setUnsavedChanges] = useState(false);

    const handleSliderChange = (event, newValue) => {
        setNewStatus(newValue);
        setUnsavedChanges(true);
    };

    const handleSetStatus = () => {
        setLoading(true);
        setFeedback(null);
        setUnsavedChanges(false);

        // Log the request details
        console.log(`Sending request to ${process.env.REACT_APP_BACKEND_API_URL}/smarthome/actuators/${actuatorId}/act with command: ${newStatus}`);

        // Make a POST request to update the status
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/actuators/${actuatorId}/act?command=${newStatus}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const newStatusFromApi = parseInt(data.status, 10);
                if (!isNaN(newStatusFromApi)) {
                    setNewStatus(newStatusFromApi);
                    onUpdate(newStatusFromApi); // Update parent component with the new status
                    setFeedback('New position set');
                    setTimeout(() => {
                        setFeedback(null);
                    }, 3000);
                } else {
                    console.error('Invalid status received from API:', data.status);
                    setFeedback('Set failed');
                }
            })
            .catch(error => {
                console.error('Error updating status:', error);
                setFeedback('Error updating status');
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <div className="status-slider-container">
            <Slider
                value={newStatus}
                onChange={handleSliderChange}
                aria-labelledby="discrete-slider"
                valueLabelDisplay="auto"
                step={1}
                marks
                min={0}
                max={100}
            />
            <Button
                variant="contained"
                onClick={handleSetStatus}
                disabled={loading}
                className={`status-slider-button ${unsavedChanges ? 'unsaved-changes' : 'grey'}`}
            >
                {loading ? 'Setting...' : 'Set'}
            </Button>
            {feedback && (
                <p className={feedback === 'New position set' ? 'success-feedback' : 'error-feedback'}>
                    {feedback}
                </p>
            )}
        </div>
    );
};

export default StatusSlider;
