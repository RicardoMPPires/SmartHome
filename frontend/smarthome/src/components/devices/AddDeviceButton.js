import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Tooltip from '@mui/material/Tooltip';
import Modal from '@mui/material/Modal';
import AddDeviceForm from './AddDeviceForm'; // Adjust the import path as necessary

export default function AddDeviceButton({ roomID, onDeviceAdded }) {
    const [open, setOpen] = useState(false);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
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
                    <h2 id="add-modal-title">Add Device</h2>
                    <AddDeviceForm roomID={roomID} onDeviceAdded={onDeviceAdded} onClose={handleClose} />
                </Box>
            </Modal>
        </Box>
    );
}
