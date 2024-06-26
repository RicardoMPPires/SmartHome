import React, { useEffect, useState, useCallback } from 'react';
import { Container, Grid, Card, CardContent, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import WbTwilightIcon from '@mui/icons-material/WbTwilight';
import axios from 'axios';
import config from './config';
import { yellow } from '@mui/material/colors';

const StyledContainer = styled(Container)(({ theme }) => ({
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: theme.spacing(4),
}));

const StyledCard = styled(Card)(({ theme }) => ({
    width: '210px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundImage:
        theme.palette.mode === 'light'
            ? 'linear-gradient(315deg, #f6f6f6 0%, #e9e9e9 74%)'
            : 'linear-gradient(315deg, #2a2a2a 0%, #1a1a1a 74%)',
}));

const StyledContent = styled(CardContent)(({ theme }) => ({
    textAlign: 'center',
}));

const formatTime = (time) => {
    const hours = Math.floor(time);
    const minutes = Math.round((time - hours) * 60);
    return `${hours}h${minutes < 10 ? '0' : ''}${minutes}m`;
};

const formatTimeString = (timeString) => {
    try {
        const timePart = timeString.split('T')[1];
        const [hours, minutes] = timePart.split(':');
        return `${hours}h${minutes}m`;
    } catch (error) {
        console.error('Error formatting time:', error);
        return 'Invalid time';
    }
};

const SunCard = ({ latitude, longitude }) => {
    const [sunrise, setSunrise] = useState('Loading...');
    const [sunset, setSunset] = useState('Loading...');
    const primaryApiUrl = `${config.apiBaseUrl}/SunriseOrSunsetTime`;
    const groupNumber = 4;

    const fetchPrimarySunTime = useCallback(async (option, remainingTime) => {
        const source = axios.CancelToken.source();
        const timeout = setTimeout(() => {
            source.cancel(`Request timeout after ${remainingTime} ms`);
        }, remainingTime);

        try {
            const response = await axios.get(`${primaryApiUrl}?groupNumber=${groupNumber}&latitude=${latitude}&longitude=${longitude}&option=${option}`, {
                cancelToken: source.token,
            });
            clearTimeout(timeout);
            return response.data.measurement;
        } catch (error) {
            if (axios.isCancel(error)) {
                console.log('Primary API request cancelled:', error.message);
            } else {
                console.log('Primary API request failed:', error.response ? error.response.data : error.message);
            }
            return null;
        }
    }, [primaryApiUrl, latitude, longitude]);

    const fetchSunDataFromBackend = useCallback(async (sensorTypeId) => {
        const date = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format
        try {
            const res = await axios.post(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/logs/get-sun-reading`, null, {
                params: {
                    date: date,
                    latitude: latitude,
                    longitude: longitude,
                    sensorTypeId: sensorTypeId
                }
            });
            console.log(`Response for ${sensorTypeId}: `, res.data);
            return res.data;
        } catch (error) {
            console.error(`Error fetching the ${sensorTypeId} data: `, error);
            return 'Error';
        }
    }, [latitude, longitude]);

    const fetchSunTimes = useCallback(async () => {
        try {
            console.log('Starting fetchSunTimes');

            const totalTimeout = 3000;
            const start = Date.now();

            let sunriseTime = await fetchPrimarySunTime('sunrise', totalTimeout);
            const elapsedForSunrise = Date.now() - start;
            const remainingTimeForSunset = totalTimeout - elapsedForSunrise;

            if (sunriseTime !== null && sunriseTime !== 'NaN') {
                setSunrise(formatTime(sunriseTime));
            } else {
                const sunriseData = await fetchSunDataFromBackend('SunriseSensor');
                setSunrise(formatTimeString(sunriseData));
            }

            let sunsetTime = await fetchPrimarySunTime('sunset', remainingTimeForSunset);
            if (sunsetTime !== null && sunsetTime !== 'NaN') {
                setSunset(formatTime(sunsetTime));
            } else {
                const sunsetData = await fetchSunDataFromBackend('SunsetSensor');
                setSunset(formatTimeString(sunsetData));
            }
        } catch (error) {
            console.error("Error fetching the sun time data: ", error);
            setSunrise('N/A');
            setSunset('N/A');
        }
    }, [fetchPrimarySunTime, fetchSunDataFromBackend]);

    useEffect(() => {
        fetchSunTimes().catch(error => console.error("Error in fetchSunTimes", error));
    }, [fetchSunTimes]);

    return (
        <StyledContainer>
            <StyledCard>
                <StyledContent>
                    <Grid container spacing={4} justifyContent='center'>
                        <Grid item xs={12} md={6} container direction='column' alignItems='center'>
                            <WbSunnyIcon sx={{ color: "#f0d43a", fontSize: 50 }} />
                            <Typography variant="h7">Sunrise</Typography>
                            <Typography variant="body1" noWrap>{sunrise}</Typography>
                        </Grid>
                        <Grid item xs={12} md={6} container direction='column' alignItems='center'>
                            <WbTwilightIcon sx={{ color: yellow[800], fontSize: 50 }} />
                            <Typography variant="h7">Sunset</Typography>
                            <Typography variant="body1" noWrap>{sunset}</Typography>
                        </Grid>
                    </Grid>
                </StyledContent>
            </StyledCard>
        </StyledContainer>
    );
};

export default SunCard;
