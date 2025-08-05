import { useState } from 'react';
import { Form, Button, Alert, Container, Spinner } from 'react-bootstrap';
import axios from 'axios';
import CameraCapture from './CameraCapture';
import { useDispatch } from 'react-redux';
import { setAuthTokens, setUser } from '../Redux/userSlice';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserPlus, faSignInAlt } from '@fortawesome/free-solid-svg-icons';

const API_BASE_URL = 'http://127.0.0.1:8000/api'; 

const LoginForm = ({ onLoginSuccess }) => {
    const [username, setUsername] = useState('');
    const [capturedImage, setCapturedImage] = useState(null);
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState(''); 
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();

    const handleImageCapture = (imageData) => {
        setCapturedImage(imageData);
        setMessage('Image captured! Click Login to authenticate.');
        setMessageType('info');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!capturedImage) {
            setMessage('Please capture your image first.');
            setMessageType('danger');
            return;
        }

        setLoading(true);
        setMessage('Logging in...');
        setMessageType('info');

        try {
            const formData = new FormData();
            formData.append('username', username)
            formData.append('captured_image', capturedImage)
            const response = await axios.post(`${API_BASE_URL}/login/`, formData);

            setMessage(response.data.message || 'Login successful!');
            setMessageType('success');
            
            dispatch(setAuthTokens({ access: response.data.tokens.access, refresh: response.data.tokens.refresh }));
            dispatch(setUser({ ...response.data.user})); 

            onLoginSuccess();  // set profile view
        } catch (error) {
            console.error('Error during login:', error);
            setMessage(error.response?.data?.error || 'Login failed. Face not recognized.');
            setMessageType('danger');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <h2 className="text-center mb-4">Login with Face Recognition</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label><FontAwesomeIcon icon={faUserPlus} className="me-2" /> Username</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Enter username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </Form.Group>

                <CameraCapture onCapture={handleImageCapture} buttonText="Capture Picture for Login" loading={loading} />

                {message && <Alert variant={messageType} className="mt-3">{message}</Alert>}

                <Button
                    variant="primary"
                    type="submit"
                    className="w-100 py-2 mt-3"
                    disabled={loading || !capturedImage}
                >
                    {loading ? (
                        <>
                            <Spinner animation="border" size="sm" className="me-2" />
                            Logging In...
                        </>
                    ) : (
                        <>
                            <FontAwesomeIcon icon={faSignInAlt} className="me-2" /> Login
                        </>
                    )}
                </Button>
            </Form>
        </Container>
    );
};

export default LoginForm;