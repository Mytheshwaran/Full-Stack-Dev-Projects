import React from 'react';
import { Alert, Card, Col, Container, Image, ListGroup, Row } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '../Redux/userSlice';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faIdCard, faEnvelope, faPhone, faUser } from '@fortawesome/free-solid-svg-icons';

const UserProfile = () => {
    const user = useSelector(selectCurrentUser);

    if (!user) {
        return <Alert variant="warning" className="text-center">User data not available.</Alert>;
    }

    return (
        <Card className="shadow-sm">
            <Card.Header as="h5" className="text-center bg-primary text-white">
                <FontAwesomeIcon icon={faIdCard} className="me-2" /> Your Profile
            </Card.Header>
            <Card.Body>
                <Container>
                    <Row>
                        <Col lg={4} md={4}>
                            <Image src={user.userImage} thumbnail style={{width:'100%',height:'100%'}}/>
                        </Col>
                        <Col lg={8} md={6}>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong><FontAwesomeIcon icon={faUser} className="me-2" /> Username:</strong> {user.username}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong><FontAwesomeIcon icon={faUser} className="me-2" /> Firstname:</strong> {user.firstname}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong><FontAwesomeIcon icon={faUser} className="me-2" /> lastname:</strong> {user.lastname}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong><FontAwesomeIcon icon={faEnvelope} className="me-2" /> Email:</strong> {user.email}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong><FontAwesomeIcon icon={faPhone} className="me-2" /> Contact:</strong> {user.contact || 'N/A'}
                                </ListGroup.Item>
                            </ListGroup>
                        </Col>
                    </Row>
                </Container>
            </Card.Body>
            <Card.Footer className="text-muted text-center">
                You are successfully logged in via face recognition!
            </Card.Footer>
        </Card>
    );
};

export default UserProfile;