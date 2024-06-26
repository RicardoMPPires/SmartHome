import './App.css';
import {createTheme, ThemeProvider} from "@mui/material/styles";
import {useState} from "react";
import MainRoute from './routes/MainRoute';

function App() {

    const [darkMode, setDarkMode] = useState(false);
    const [userName, setUserName] = useState('');

    const theme = createTheme({
        palette: {
            mode: darkMode ? "dark" : "light",
        },
    })


    return (
        <ThemeProvider theme={theme}>
            <MainRoute darkMode={darkMode} setDarkMode={setDarkMode} userName={userName} setUserName={setUserName}/>
        </ThemeProvider>
    );
}

export default App;
