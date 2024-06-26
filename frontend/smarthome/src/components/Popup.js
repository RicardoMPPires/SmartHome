import React from 'react';
import {Snackbar} from '@mui/material';



const Popup = ({ open, onClose }) => {
    return (
        <Snackbar
            anchorOrigin={{vertical: 'top', horizontal: 'center'}}
            open={open}
            autoHideDuration={3000}
            onClose={onClose}
            message="Inactive Device"
        />
    );
};

export default Popup;
