import React, {useEffect, useState} from 'react';
import {Card, Container, Grid, Typography, useTheme, useMediaQuery } from '@mui/material';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import {styled} from "@mui/material/styles";

const DateTimeContainer = styled(Container)(({theme}) => ({
    display: 'flex',
    justifyContent: 'right',
    alignItems: 'top',
    marginTop: theme.spacing(0),
    position: 'absolute',
    top: 100,
    right: 0,
}));

const DateTimeCard = styled(Card)(({theme}) => ({
    width: '15%',
    display: 'flex',
    justifyContent: 'right',
    alignItems: 'up',
    backgroundImage:
        theme.palette.mode === 'light'
            ? 'linear-gradient(315deg, #f6f6f6 0%, #e9e9e9 74%)'
            : 'linear-gradient(315deg, #2a2a2a 0%, #1a1a1a 74%)',
}));

const DateAndTimeCard = () => {
    const [currentDate, setCurrentDate] = useState(new Date());
    const [currentTime, setCurrentTime] = useState(new Date());

    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000);

        const now = new Date();
        const tomorrow = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1);
        const timeUntilMidnight = tomorrow - now;

        const dateTimer = setTimeout(() => {
            setCurrentDate(new Date());
        }, timeUntilMidnight);

        return () => {
            clearInterval(timer);
            clearTimeout(dateTimer);
        };
    }, []);

    const formattedDate = `${currentDate.getDate().toString().padStart(2, '0')}/${(currentDate.getMonth() + 1).toString().padStart(2, '0')}/${currentDate.getFullYear()}`;
    const formattedTime = `${currentTime.getHours().toString().padStart(2, '0')}:${currentTime.getMinutes().toString().padStart(2, '0')}:${currentTime.getSeconds().toString().padStart(2, '0')}`;

    const theme = useTheme();
    const isSmallScreen = useMediaQuery(theme.breakpoints.down('sm'));

    if (isSmallScreen) {
        return null;
    }

    return (
        <Grid item xs={12} sm={6} md={4} lg={3}>
            <DateTimeContainer>
                <DateTimeCard>
                    <Grid container direction={isSmallScreen ? 'column' : 'row'} alignitems="center">
                        <Grid item xs={4} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                            <AccessTimeIcon style={{fontSize: '3rem'}}/>
                        </Grid>
                        <Grid item xs={8}>
                            <Grid container direction="column"
                                  style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                <Typography variant="body1" style={{fontSize: '1.3rem' }}>
                                    {formattedTime}
                                </Typography>
                                <Typography variant="body1" style={{ marginTop: isSmallScreen ? '0.5rem' : '-0.5rem'}}>
                                    {formattedDate}
                                </Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                </DateTimeCard>
            </DateTimeContainer>
        </Grid>
    );
};

export default DateAndTimeCard;