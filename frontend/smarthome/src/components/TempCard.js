import React, { useEffect, useState, useCallback } from 'react';
import { Container, Card, CardContent, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import DeviceThermostatIcon from '@mui/icons-material/DeviceThermostat';
import axios from 'axios';
import config from './config';
import { red } from '@mui/material/colors';

const StyledContainer = styled(Container)(({ theme }) => ({
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: theme.spacing(4),
}));

const StyledCard = styled(Card)(({ theme }) => ({
    width: '150px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundImage:
        theme.palette.mode === 'light'
            ? 'linear-gradient(315deg, #f6f6f6 0%, #e9e9e9 74%)'
            : 'linear-gradient(315deg, #2a2a2a 0%, #1a1a1a 74%)',
}));

const StyledContent = styled(CardContent)(({ theme }) => ({
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    textAlign: 'center',
}));

const TempCard = ({ latitude, longitude }) => {
    const [temperature, setTemperature] = useState('');
    const [loading, setLoading] = useState(true);

    const primaryApiUrl = `${config.apiBaseUrl}/InstantaneousTemperature`;
    const groupNumber = 4;
    const fallbackApiUrl = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&units=metric&appid=3fd6f0bafafc6b4a19601d364ff909d3`;

    const fetchPrimaryTemperature = useCallback(async (hour, remainingTime) => {
        const source = axios.CancelToken.source();
        const timeout = setTimeout(() => {
            source.cancel(`Request timeout after ${remainingTime} ms`);
        }, remainingTime);

        try {
            const response = await axios.get(`${primaryApiUrl}?groupNumber=${groupNumber}&latitude=${latitude}&longitude=${longitude}&hour=${hour}`, {
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

    const fetchFallbackTemperature = useCallback(async () => {
        try {
            const response = await axios.get(fallbackApiUrl);
            return response.data.main.temp;
        } catch (error) {
            console.log('Fallback API request failed:', error.response ? error.response.data : error.message);
            return null;
        }
    }, [fallbackApiUrl]);

    const fetchTemperature = useCallback(async () => {
        try {
            setLoading(true);
            console.log('Starting fetchTemperature');

            const totalTimeout = 2000;
            const start = Date.now();
            const currentHour = new Date().getHours();

            let temp = await fetchPrimaryTemperature(currentHour, totalTimeout);

            const elapsed = Date.now() - start;
            const remainingTime = totalTimeout - elapsed;

            if (temp !== null) {
                console.log('Fetched temperature from primary API:', temp);
                setTemperature(temp.toFixed(2));
                return;
            }

            if (remainingTime > 0) {
                await new Promise(resolve => setTimeout(resolve, remainingTime));
            }

            console.log('Using fallback API');
            temp = await fetchFallbackTemperature();

            if (temp !== null) {
                console.log('Fetched temperature from fallback API:', temp);
                setTemperature(temp.toFixed(2));
            } else {
                console.log('Temperature data is undefined from both APIs');
                setTemperature('N/A');
            }
        } catch (error) {
            console.error("Error fetching the temperature data: ", error);
            setTemperature('N/A');
        } finally {
            setLoading(false);
        }
    }, [fetchFallbackTemperature, fetchPrimaryTemperature]);

    useEffect(() => {
        fetchTemperature().catch(error => console.error("Error in fetchTemperature", error));
        const interval = setInterval(() => {
            fetchTemperature().catch(error => console.error("Error in fetchTemperature", error));
        }, 15 * 60 * 1000);
        return () => clearInterval(interval);
    }, [fetchTemperature]);

    return (
        <StyledContainer>
            <StyledCard>
                <StyledContent>
                    <DeviceThermostatIcon sx={{ fontSize: 50, color: red[500] }} />
                    <Typography variant="h7">Temperature</Typography>
                    <Typography variant="body1">
                        {loading ? 'Loading...' : `${temperature} °C`}
                    </Typography>
                </StyledContent>
            </StyledCard>
        </StyledContainer>
    );
}

export default TempCard;