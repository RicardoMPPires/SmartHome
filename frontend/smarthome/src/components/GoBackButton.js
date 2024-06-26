import React from 'react';
import Box from '@mui/material/Box';
import Fab from '@mui/material/Fab';
import UndoIcon from '@mui/icons-material/Undo';
import Tooltip from '@mui/material/Tooltip';
import { useNavigate } from 'react-router-dom';

const GoBackButton = () => {
    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1);
    }

    return (
        <Box sx={{'& > :not(style)': {m: 1}}}>
            <Tooltip title="Go Back" arrow>
                <Fab
                    size="medium"
                    sx={{
                        width: '48px',
                        height: '48px',
                        borderRadius: '8px',
                        backgroundColor: 'blue',
                        color: 'white',
                        transition: '0.3s',
                        cursor: 'pointer',
                        '&:hover': {
                            backgroundColor: 'darkblue',
                        },
                    }}
                    aria-label="go-back"
                    onClick={goBack}
                >
                    <UndoIcon />
                </Fab>
            </Tooltip>
        </Box>
    );
}

export default GoBackButton;
