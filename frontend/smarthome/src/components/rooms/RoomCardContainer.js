import React from 'react';
import {Grid, useMediaQuery, useTheme} from '@mui/material';
import RoomCard from './RoomCard';
import Box from "@mui/material/Box";
import {CSSTransition, TransitionGroup} from 'react-transition-group';
import '../../css/SlideAnimations.css';

const drawerWidth = 125;

const RoomCardContainer = ({rooms, onButtonClick}) => {

    const theme = useTheme();
    const isSmallScreen = useMediaQuery(theme.breakpoints.down('sm'));

    return (
        <Box sx={{
            width: isSmallScreen ? '100%' : `calc(100% - ${drawerWidth}px)`,
            marginLeft: isSmallScreen ? '0px' : `${drawerWidth}px`,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            padding: '20px',
            paddingTop: '64px' // Ensure there's enough space for the AppBar
        }}>
            <Grid container spacing={3}>
                <TransitionGroup component={null}>
                    {rooms.map((room, index) => (
                        <CSSTransition key={index} timeout={800} classNames="slide">
                            <Grid item xs={12} sm={6} md={4} lg={3}>
                                <RoomCard
                                    roomName={room.roomName}
                                    roomHeight={room.roomHeight}
                                    roomLength={room.roomLength}
                                    roomWidth={room.roomWidth}
                                    onButtonClick={() => onButtonClick(room.id)}
                                />
                            </Grid>
                        </CSSTransition>
                    ))}
                </TransitionGroup>
            </Grid>
        </Box>
    );
};

export default RoomCardContainer;
