import React, { useState } from 'react';
import { Form, Button, Alert, Container, Spinner } from 'react-bootstrap';
import axios from 'axios';
import CameraCapture from './CameraCapture';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserPlus, faEnvelope, faPhone } from '@fortawesome/free-solid-svg-icons';

const API_BASE_URL = 'http://127.0.0.1:8000/api'; 

const RegistrationForm = ({ setView }) => {
    const [username, setUsername] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [contact, setContact] = useState('');
    const [email, setEmail] = useState('');
    const [capturedImage, setCapturedImage] = useState(null);
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState('');
    const [loading, setLoading] = useState(false);

    const handleImageChange = (imageFileBlob) => {
        setCapturedImage(imageFileBlob);
        setMessage('Image captured successfully! Click Register to proceed.');
        setMessageType('success');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!capturedImage) {
            setMessage('Please capture your image first.');
            setMessageType('danger');
            return;
        }
        if (!username || !email ) {
            setMessage('Please fill in all required user details.');
            setMessageType('danger');
            return;
        }

        setLoading(true);
        setMessage('Registering...');
        setMessageType('info');

        const formData = new FormData();
        formData.append('username',username)
        formData.append('first_name',firstname)
        formData.append('last_name',lastname)
        formData.append('contact', contact)
        formData.append('email', email)
        formData.append('registered_face_image', capturedImage)
        try {
            const response = await axios.post(`${API_BASE_URL}/register/`, formData);
            setMessage(response.data.message || 'Registration successful!');
            setMessageType('success');
            setUsername('');
            setFirstname('');
            setLastname('');
            setContact('');
            setEmail('');
            setCapturedImage(null);
            setTimeout(() => setView('login'), 2000); // Redirect to login after success
        } catch (error) {
            console.error('Error during registration:', error);
            setMessage(error.response?.data?.error || 'Registration failed. Please try again.');
            setMessageType('danger');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container>
            <h2 className="text-center mb-4">Register New User</h2>
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

                <Form.Group className="mb-3">
                    <Form.Label><FontAwesomeIcon icon={faUserPlus} className="me-2" /> Firstname (Optional)</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Enter firstname"
                        value={firstname}
                        onChange={(e) => setFirstname(e.target.value)}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label><FontAwesomeIcon icon={faUserPlus} className="me-2" /> Lastname (Optional)</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Enter lastname"
                        value={lastname}
                        onChange={(e) => setLastname(e.target.value)}
                    />
                </Form.Group>


                <Form.Group className="mb-3">
                    <Form.Label><FontAwesomeIcon icon={faEnvelope} className="me-2" /> Email address</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label><FontAwesomeIcon icon={faPhone} className="me-2" /> Contact (Optional)</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Enter contact number"
                        value={contact}
                        minLength={10}
                        maxLength={10}
                        onChange={(e) => setContact(e.target.value)}
                    />
                </Form.Group>

                <CameraCapture onCapture={handleImageChange} buttonText="Capture Picture for Registration" loading={loading} />

                {message && <Alert variant={messageType} className="mt-3">{message}</Alert>}

                <Button
                    variant="primary"
                    type="submit"
                    className="w-100 py-2 mt-3"
                    disabled={loading || !capturedImage || !username || !email}
                >
                    {loading ? (
                        <>
                            <Spinner animation="border" size="sm" className="me-2" />
                            Registering...
                        </>
                    ) : (
                        'Register'
                    )}
                </Button>
            </Form>
        </Container>
    );
};

export default RegistrationForm;