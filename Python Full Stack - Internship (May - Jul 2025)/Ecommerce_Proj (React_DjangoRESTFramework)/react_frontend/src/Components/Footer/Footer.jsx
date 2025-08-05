import React from 'react'
import { Col, Container, Row } from 'react-bootstrap'
import './Footer.css'

function Footer() {
  return (
    <div>
        <Container fluid className="bg-dark text-white py-3">
            <Row>
                <Col className="text-center">&copy; Copyrights</Col>
            </Row>
        </Container>
    </div>
  )
}

export default Footer
