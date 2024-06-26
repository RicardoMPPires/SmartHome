import React from 'react';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Fab from '@mui/material/Fab';
import CloseIcon from '@mui/icons-material/Close';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import TablePagination from '@mui/material/TablePagination';
import { styled } from '@mui/system';
import withWidth from "@mui/material/Hidden/withWidth";

const StyledTableContainer = styled(TableContainer)(({ theme }) => ({
    [theme.breakpoints.down('sm')]: {
        '& .MuiTableCell-root': {
            display: 'block',
            textAlign: 'left',
            borderBottom: 'none',
            width: '100%',
        },
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    [theme.breakpoints.down('sm')]: {
        display: 'block',
        marginBottom: theme.spacing(2),
        borderBottom: '1px solid #ddd',
    },
}));

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [theme.breakpoints.down('sm')]: {
        display: 'flex',
        flexDirection: 'column',
        textAlign: 'left',
        '&:before': {
            content: 'attr(data-label)',
            fontWeight: 'bold',
            marginRight: theme.spacing(1),
        },
    },
}));

class GetReadingsButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            logs: [],
            open: false,
            sensorTypes: [],
            page: 0,
            rowsPerPage: 2,
        };
    }

    fetchSensorTypes = async () => {
        const response = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/sensortypes`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const data = await response.json();
            const sensorTypes = data._embedded.sensorTypeDTOList;
            this.setState({ sensorTypes });
        } else {
            console.error('Failed to fetch sensor types');
        }
    };

    fetchLogs = async () => {
        const deviceId = this.props.deviceID;

        await this.fetchSensorTypes();

        const response = await fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/logs?deviceId=${deviceId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const data = await response.json();
            const logs = data._embedded ? data._embedded.logDTOList : [];

            for (let log of logs) {
                const sensorType = this.state.sensorTypes.find(st => st.sensorTypeID === log.sensorTypeID);
                if (sensorType) {
                    log.unit = sensorType.unit;
                } else {
                    console.error(`Sensor type not found for sensorTypeID: ${log.sensorTypeID}`);
                }
            }

            this.setState({ logs, open: true });
        } else {
            console.error('Failed to fetch logs');
        }
    };


    handleClose = () => {
        this.setState({ open: false });
    };

    formatDateTime = (dateTime) => {
        const date = new Date(dateTime);
        const formattedDate = `Date: ${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
        const formattedTime = `Time: ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}:${String(date.getSeconds()).padStart(2, '0')}`;
        return (
            <div>
                <div>{formattedDate}</div>
                <div>{formattedTime}</div>
            </div>
        );
    };

    handleChangePage = (event, newPage) => {
        this.setState({ page: newPage });
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({ rowsPerPage: parseInt(event.target.value, 10), page: 0 });
    };

    render() {
        const { logs, open, page, rowsPerPage } = this.state;
        const paginatedLogs = logs.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

        const isSmallScreen = this.props.width === 'xs' || this.props.width === 'sm';

        return (
            <div>
                <Button sx={{ width: '100%' }} variant="contained" color="primary" onClick={this.fetchLogs}>
                    Activity Log
                </Button>
                <Modal
                    open={open}
                    onClose={this.handleClose}
                    aria-labelledby="modal-title"
                    aria-describedby="modal-description"
                >
                    <Box sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: '100%',
                        maxWidth: '600px',
                        maxHeight: '90%',
                        bgcolor: 'background.paper',
                        border: '2px solid #000',
                        boxShadow: 24,
                        p: 2,
                        overflow: 'auto'
                    }}>
                        <Fab
                            sx={{ position: 'absolute', top: 8, right: 8, bgcolor: 'red', color: 'white' }}
                            onClick={this.handleClose}
                        >
                            <CloseIcon />
                        </Fab>
                        <Typography id="modal-title" variant="h6" component="h2" sx={{ fontWeight: 'bold', fontSize: '1.5rem', textAlign: 'center', mb: 2 }}>
                            Logs
                        </Typography>
                        <StyledTableContainer component={Paper} sx={{ flexGrow: 1 }}>
                            <Table stickyHeader>
                                {!isSmallScreen && (
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="center" sx={{ fontWeight: 'bold', color: 'black', fontSize: '1rem' }}>Time</TableCell>
                                        <TableCell align="center" sx={{ fontWeight: 'bold', color: 'black', fontSize: '1rem' }}>Reading</TableCell>
                                        <TableCell align="center" sx={{ fontWeight: 'bold', color: 'black', fontSize: '1rem' }}>Sensor Type</TableCell>
                                    </TableRow>
                                </TableHead>
                                )}
                                <TableBody>
                                    {paginatedLogs.length > 0 ? (
                                        paginatedLogs.map((log) => (
                                            <StyledTableRow key={log.logID}>
                                                <StyledTableCell align="center" data-label="Time" sx={{ color: 'black' }}>
                                                    {this.formatDateTime(log.time)}
                                                </StyledTableCell>
                                                <StyledTableCell align="center" data-label="Reading" sx={{ color: 'black' }}>
                                                    {`${log.reading} ${log.unit}`}
                                                </StyledTableCell>
                                                <StyledTableCell align="center" data-label="Sensor Type" sx={{ color: 'black' }}>
                                                    {log.sensorTypeID.replace(/([A-Z])/g, ' $1').trim()}
                                                </StyledTableCell>
                                            </StyledTableRow>
                                        ))
                                    ) : (
                                        <TableRow>
                                            <TableCell align="center" colSpan={3} sx={{ color: 'black' }}>No logs available for the selected period.</TableCell>
                                        </TableRow>
                                    )}
                                </TableBody>
                            </Table>
                            <TablePagination
                                rowsPerPageOptions={[2, 5, 10]}
                                component="div"
                                count={logs.length}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                onPageChange={this.handleChangePage}
                                onRowsPerPageChange={this.handleChangeRowsPerPage}
                            />
                        </StyledTableContainer>
                    </Box>
                </Modal>
            </div>
        );
    }
}

export default withWidth()(GetReadingsButton);
