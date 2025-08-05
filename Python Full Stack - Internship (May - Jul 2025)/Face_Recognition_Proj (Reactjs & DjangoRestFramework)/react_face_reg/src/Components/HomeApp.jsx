import { useEffect, useState } from 'react';
import { Container, Row, Col, Button, Alert } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import {
    selectCurrentUser,
    selectAuthStatus,
    selectAuthError,
    selectCurrentToken,
    clearAuth,
} from '../Redux/userSlice';

import RegistrationForm from './RegistrationForm';
import LoginForm from './LoginForm';
import UserProfile from './UserProfile';

function App() {
    const dispatch = useDispatch();
    const user = useSelector(selectCurrentUser);
    const authStatus = useSelector(selectAuthStatus);
    const authError = useSelector(selectAuthError);
    const token = useSelector(selectCurrentToken);

    const [view, setView] = useState('register'); 
    
    useEffect(() => {
        if (user) { 
            setView('profile');
        } else if (!token) { 
            setView('login');
        }
    }, [token, user, authStatus, dispatch]);


    const handleLogout = () => {
        dispatch(clearAuth());
        setView('login');
    };

    const handleLoginSuccess = () => {
        setView('profile'); 
    };

    return (
        <Container className="my-5">
            <Row className="justify-content-md-center">
                <Col md={8} lg={6}>
                    <div className="border p-4 rounded shadow-lg bg-white">
                        <h1 className="text-center mb-4 text-primary">Face Recognition App</h1>

                        {user ? (
                            <div className="text-center mb-4">
                                <Alert variant="success">Welcome, <strong>{user.username}</strong>!</Alert>
                                <Button variant="danger" onClick={handleLogout}>
                                    Logout
                                </Button>
                            </div>
                        ) : (
                            <div className="d-flex justify-content-center mb-4">
                                <Button
                                    variant={view === 'register' ? 'primary' : 'outline-primary'}
                                    onClick={() => setView('register')}
                                    className="me-2"
                                >
                                    Register
                                </Button>
                                <Button
                                    variant={view === 'login' ? 'primary' : 'outline-primary'}
                                    onClick={() => setView('login')}
                                >
                                    Login
                                </Button>
                            </div>
                        )}

                        {authStatus === 'loading' && !user && (
                            <Alert variant="info" className="text-center">Loading user data...</Alert>
                        )}
                        {authStatus === 'failed' && authError && !user && (
                            <Alert variant="danger" className="text-center">Error: {authError?.detail || authError?.error || 'Failed to authenticate user.'}</Alert>
                        )}

                        {!user && view === 'register' && <RegistrationForm setView={setView} />}
                        {!user && view === 'login' && <LoginForm onLoginSuccess={handleLoginSuccess} />}
                        {user && view === 'profile' && <UserProfile />}
                    </div>
                </Col>
            </Row>
        </Container>
    );
}

export default App;