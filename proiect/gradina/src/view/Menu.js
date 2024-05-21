import * as React from 'react';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';

export default function MenuBar({role, onChangePage, lang}) {
    const [anchorEl1, setAnchorEl1] = React.useState(null); // State for first menu
    const [anchorEl2, setAnchorEl2] = React.useState(null); // State for second menu

    const open1 = Boolean(anchorEl1);
    const open2 = Boolean(anchorEl2);

    const handleClick1 = (event) => {
        setAnchorEl1(event.currentTarget);
    };

    const handleClick2 = (event) => {
        setAnchorEl2(event.currentTarget);
    };

    const handleClose1 = () => {
        setAnchorEl1(null);
    };

    const handleClose2 = () => {
        setAnchorEl2(null);
    };

    const handleLogin = () => {
        onChangePage(role, 2, 0, 0, lang);
        handleClose1();
    };

    const handleViewPlants = () => {
        onChangePage(role, 1, 0, 0, lang);
        handleClose1();
    };

    const handleViewUsers = () => {
        onChangePage(role, 3, 0, 0, lang);
        handleClose1();
    };

    const handleAddPlant = () => {
        onChangePage(role, 4, null, 0, lang);
        handleClose1();
    };

    const handleSwitchRom = () => {
        onChangePage(role, 1, 0, 0, 'romana');
        handleClose2();
    };

    const handleSwitchEng = () => {
        onChangePage(role, 1, 0, 0, 'english');
        handleClose2();
    };

    const handleSwitchFr = () => {
        onChangePage(role, 1, 0, 0, 'french');
        handleClose2();
    };

    const handleSwitchRus = () => {
        onChangePage(role, 1, 0, 0, 'russian');
        handleClose2();
    };

    const handleAddUser = () => {
        onChangePage(role, 5, 0, null, lang);
        handleClose1();
    };

    return (
        <div>
            <Button
                id="basic-button-1"
                aria-controls={open1 ? 'basic-menu-1' : undefined}
                aria-haspopup="true"
                aria-expanded={open1 ? 'true' : undefined}
                onClick={handleClick1}
            >
                {lang === 'romana' && "Meniu"}
                {lang === 'english' && "Menu"}
                {lang === 'french' && "Menu"}
                {lang === 'russian' && "Меню"}
            </Button>
            <Menu
                id="basic-menu-1"
                anchorEl={anchorEl1}
                open={open1}
                onClose={handleClose1}
                MenuListProps={{
                    'aria-labelledby': 'basic-button-1',
                }}
            >
                <MenuItem onClick={handleLogin}>
                    {lang === 'romana' && "Autentificare"}
                    {lang === 'english' && "Log in"}
                    {lang === 'french' && "Autentification"}
                    {lang === 'russian' && "Войти"}
                </MenuItem>
                <MenuItem onClick={handleViewPlants}>
                    {lang === 'romana' && "Vizualizare plante"}
                    {lang === 'english' && "View plants"}
                    {lang === 'french' && "Visualiser les plants"}
                    {lang === 'russian' && "Растения"}
                </MenuItem>
                {role === 'employee' && <MenuItem onClick={handleAddPlant}>
                    {lang === 'romana' && "Adăugare plantă"}
                    {lang === 'english' && "Add plant"}
                    {lang === 'french' && "Ajouter une plante"}
                    {lang === 'russian' && "Добавить растение"}
                </MenuItem>}
                {role === 'administrator' && <MenuItem onClick={handleViewUsers}>
                    {lang === 'romana' && "Vizualizare utilizatori"}
                    {lang === 'english' && "View users"}
                    {lang === 'french' && "Visualiser les utilisateur"}
                    {lang === 'russian' && "Пользователи"}
                </MenuItem>}
                {role === 'administrator' && <MenuItem onClick={handleAddUser}>
                    {lang === 'romana' && "Adăugare utilizator"}
                    {lang === 'english' && "Add user"}
                    {lang === 'french' && "Ajouter un utilisateur"}
                    {lang === 'russian' && "Добавить пользователя"}
                </MenuItem>}
            </Menu>

            <Button
                id="basic-button-2"
                aria-controls={open2 ? 'basic-menu-2' : undefined}
                aria-haspopup="true"
                aria-expanded={open2 ? 'true' : undefined}
                onClick={handleClick2}
            >
                {lang === 'romana' && "Schimbarea limbii"}
                {lang === 'english' && "Change language"}
                {lang === 'french' && "Choisir langue"}
                {lang === 'russian' && "Выбрать язык"}
            </Button>
            <Menu
                id="basic-menu-2"
                anchorEl={anchorEl2}
                open={open2}
                onClose={handleClose2}
                MenuListProps={{
                    'aria-labelledby': 'basic-button-2',
                }}
            >
                <MenuItem onClick={handleSwitchRom}>Română</MenuItem>
                <MenuItem onClick={handleSwitchEng}>English</MenuItem>
                <MenuItem onClick={handleSwitchFr}>French</MenuItem>
                <MenuItem onClick={handleSwitchRus}>Русский</MenuItem>
            </Menu>
        </div>
    );
}
