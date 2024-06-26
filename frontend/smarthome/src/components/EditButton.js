import React, { useState } from 'react';
import { Button, Snackbar } from '@mui/material';

const EditButton = () => {
    const [open, setOpen] = useState(false);

    const handleClick = () => {
        setOpen(true);
        setTimeout(() => {
            setOpen(false);
        }, 3000); // Close the Snackbar after 3 seconds
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <Button variant="contained" color="secondary" onClick={handleClick}>
                Edit
            </Button>
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                open={open}
                autoHideDuration={3000}
                onClose={handleClose}
                message="Future implementation"
            />
        </>
    );
};

export default EditButton;
