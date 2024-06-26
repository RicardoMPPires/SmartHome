import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Tooltip from '@mui/material/Tooltip';
import Modal from '@mui/material/Modal';
import AddFunctionalityForm from "./AddFunctionalityForm";
import Popup from "../Popup"; // Import the Popup component

export default function AddFunctionalityButton({ type, onFunctionalityAdded, deviceID, deviceStatus }) {
    const [open, setOpen] = useState(false);
    const [inactiveDeviceOpen, setInactiveDeviceOpen] = useState(false); // State to track inactive device popup

    const handleOpen = () => {
        if (deviceStatus === 'false') {
            setInactiveDeviceOpen(true); // Show the inactive device popup
        } else {
            setOpen(true); // Show the regular modal
        }
    };

    const handleClose = () => {
        setOpen(false);
        setInactiveDeviceOpen(false); // Close the inactive device popup
    };

    return (
        <Box sx={{ '& > :not(style)': { m: 1 } }}>
            <Tooltip title="Add" arrow>
                <Fab
                    size="medium"
                    sx={{
                        width: '48px',
                        height: '48px',
                        borderRadius: '8px',
                        backgroundColor: 'green',
                        color: 'white',
                        transition: '0.3s',
                        cursor: 'pointer',
                        '&:hover': {
                            backgroundColor: 'darkgreen',
                        },
                    }}
                    aria-label="add"
                    onClick={handleOpen}
                >
                    <AddIcon />
                </Fab>
            </Tooltip>

            {/* Conditionally render the Modal or Popup based on deviceStatus */}
            {deviceStatus === 'false' ? (
                <Popup open={inactiveDeviceOpen} onClose={handleClose} />
            ) : (
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="add-modal-title"
                    aria-describedby="add-modal-description"
                >
                    <Box
                        sx={{
                            position: 'absolute',
                            top: '50%',
                            left: '50%',
                            transform: 'translate(-50%, -50%)',
                            width: 400,
                            bgcolor: 'background.paper',
                            boxShadow: 24,
                            p: 4,
                            borderRadius: '8px',
                        }}
                    >
                        <h2 id="add-modal-title">Add {type === 'sensor' ? 'Sensor' : 'Actuator'}</h2>
                        <AddFunctionalityForm type={type} onFunctionalityAdded={onFunctionalityAdded} deviceID={deviceID} onClose={handleClose} />
                    </Box>
                </Modal>
            )}
        </Box>
    );
}
