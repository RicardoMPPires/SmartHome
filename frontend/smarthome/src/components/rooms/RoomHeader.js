import * as React from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';

export default function RoomHeader() {
    return (
        <Box sx={{ height: '1em', display: 'flex', alignItems: 'flex-start', justifyContent: 'flex-start' }}>
            <Typography variant="h4" className="functionality-box-title" sx={{ margin: '0px', padding: '0px' }}>
                Rooms
            </Typography>
        </Box>
    );
}