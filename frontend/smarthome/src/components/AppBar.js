import * as React from 'react';
import { useState } from 'react';
import {
    useTheme, useMediaQuery,
    CssBaseline,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText, Card, Dialog, DialogTitle, DialogContentText, DialogContent, DialogActions
} from '@mui/material';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { AccountCircle } from "@mui/icons-material";
import DarkModeIcon from '@mui/icons-material/DarkMode';
import HomeIcon from '@mui/icons-material/Home';
import BedRoomParentIcon from '@mui/icons-material/BedroomParent';
import MenuIcon from '@mui/icons-material/Menu';
import { useNavigate, Link } from "react-router-dom";
import TextField from "@mui/material/TextField";

const drawerWidth = 125;

export default function Appbar({ change, setUserName }) {

    const [userLogin, setUserLogin] = useState(null);
    const [mobileOpen, setMobileOpen] = useState(false);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
    const [openDialog, setOpenDialog] = useState(false);
    const [signedIn, setSignedIn] = useState(false);

    const handleUserClick = (event) => {
        setUserLogin(event.currentTarget);
    }

    const handleUserClose = () => {
        setUserLogin(null);
        setOpenDialog(false)
    }

    const handleDrawerToggle = () => {
        setMobileOpen(!mobileOpen);
    }

    const handleSignInClick = () => {
        if (signedIn) {
            setUserName(''); // Clear user's name when signing out
            setSignedIn(false);
        } else {
            setUserLogin(null);
            setOpenDialog(true);
        }
    }

    const handleNameSubmit = (event) => {
        event.preventDefault();
        const name = event.target.elements.name.value;
        setUserName(name);
        setSignedIn(true);
        setOpenDialog(false);
    };

    const navigate = useNavigate();

    const handleListItemClick = (item) => {
        switch (item) {
            case 'Home':
                navigate('/');
                break;
            case 'Rooms':
                navigate('/rooms');
                break;
            default:
                break;
        }
    }

    const drawer = (
        <div>
            <Toolbar />
            <Box sx={{ overflow: 'auto' }}>
                <List>
                    <ListItem onClick={() => handleListItemClick('Home')}>
                        <Card sx={{
                            width: '100px',
                            height: '80px',
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center',
                            backgroundImage:
                                theme.palette.mode === 'light'
                                    ? 'linear-gradient(315deg, #f6f6f6 0%, #e9e9e9 74%)'
                                    : 'linear-gradient(315deg, #2a2a2a 0%, #1a1a1a 74%)',
                        }}>
                            <ListItemButton sx={{
                                display: 'flex',
                                flexDirection: 'column',
                                width: '100%',
                                height: '100%',
                                justifyContent: 'center',
                                alignItems: 'center'
                            }}>
                                <ListItemIcon sx={{ minWidth: 'auto' }}>
                                    <HomeIcon fontSize='large' />
                                </ListItemIcon>
                                <ListItemText primary='Home' />
                            </ListItemButton>
                        </Card>
                    </ListItem>
                    <ListItem onClick={() => handleListItemClick('Rooms')}>
                        <Card sx={{
                            width: '100px',
                            height: '80px',
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center',
                            backgroundImage:
                                theme.palette.mode === 'light'
                                    ? 'linear-gradient(315deg, #f6f6f6 0%, #e9e9e9 74%)'
                                    : 'linear-gradient(315deg, #2a2a2a 0%, #1a1a1a 74%)',
                        }}>
                            <ListItemButton sx={{
                                display: 'flex',
                                flexDirection: 'column',
                                width: '100%',
                                height: '100%',
                                justifyContent: 'center',
                                alignItems: 'center'
                            }}>
                                <ListItemIcon sx={{ minWidth: 'auto' }}>
                                    <BedRoomParentIcon fontSize='large' />
                                </ListItemIcon>
                                <ListItemText primary='Rooms' />
                            </ListItemButton>
                        </Card>
                    </ListItem>
                </List>
            </Box>
        </div>
    );

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1, backgroundColor: "#1976d2" }}>
                <Toolbar>
                    {isMobile && (
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            edge="start"
                            onClick={handleDrawerToggle}
                            sx={{ mr: 2, display: { sm: 'none' } }}
                        >
                            <MenuIcon />
                        </IconButton>
                    )}
                    <Box sx={{ flexGrow: 1 }}>
                        <Typography variant="h6"
                                    component={Link}
                                    to="/"
                                    sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit' }}>
                            SMARTHOME 4
                        </Typography>
                    </Box>
                    <IconButton color="inherit"
                                onClick={change}>
                        <DarkModeIcon />
                    </IconButton>
                    <Button color="inherit"
                            onClick={handleUserClick}
                    >
                        <AccountCircle />
                    </Button>
                    <Menu anchorEl={userLogin}
                          open={Boolean(userLogin)}
                          onClose={handleUserClose}
                    >
                        <MenuItem onClick={handleSignInClick}>
                            {signedIn ? 'Sign Out' : 'Sign In'}
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>
            <Box
                component="nav"
                sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
            >
                <Drawer
                    variant="temporary"
                    open={mobileOpen}
                    onClose={handleDrawerToggle}
                    ModalProps={{
                        keepMounted: true,
                    }}
                    sx={{
                        display: { xs: 'block', sm: 'none' },
                        '& .MuiDrawer-paper': { width: drawerWidth, boxSizing: 'border-box' },
                    }}
                >
                    {drawer}
                </Drawer>
                <Drawer
                    variant="permanent"
                    sx={{
                        display: { xs: 'none', sm: 'block' },
                        '& .MuiDrawer-paper': { width: drawerWidth, boxSizing: 'border-box' },
                    }}
                    open
                >
                    {drawer}
                </Drawer>
            </Box>
            {/*<Box*/}
            {/*    component="main"*/}
            {/*    sx={{*/}
            {/*        flexGrow: 1,*/}
            {/*        p: 3,*/}
            {/*        mt: 8, // Add margin-top to ensure content doesn't go under the AppBar*/}
            {/*        width: { sm: `calc(100% - ${drawerWidth}px)` }*/}
            {/*    }}*/}
            {/*>*/}
                <Dialog open={openDialog} onClose={handleUserClose}>
                    <DialogTitle>Sign In</DialogTitle>
                    <form onSubmit={handleNameSubmit}>
                        <DialogContent>
                            <DialogContentText>
                                Please enter your name
                            </DialogContentText>
                            <TextField
                                autoFocus
                                margin="dense"
                                id="name"
                                label="Name"
                                type="text"
                                fullWidth
                                variant="standard"
                            />
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={handleUserClose}>Cancel</Button>
                            <Button type="submit">Submit</Button>
                        </DialogActions>
                    </form>
                </Dialog>
                {/* Your main content goes here */}
            {/*</Box>*/}
        </Box>
    );
}
