import React, { useEffect, useState } from "react";
//import products from "../Product/ProductsData";
import { Link, useParams } from "react-router-dom";
import {
  Button,
  Card,
  Col,
  Container,
  Image,
  ListGroup,
  ListGroupItem,
  Row,
} from "react-bootstrap";
import Rating from "../Rating/Rating";
import axios from "axios";

const DJANGO_BASE_URL = "http://localhost:8000";
function ProductScreen() {
  const { id } = useParams();
  const [product, setProduct]=useState([]);
  useEffect(()=>{
    async function fetchProduct() {
      try{
        const {data} = await axios.get(`${DJANGO_BASE_URL}/api/product/${id}`);
        setProduct(data)
      }
      catch(error){
        console.error("Fetch Product Error: ",error)
      }
    }
    fetchProduct();
  }, [id])
  // const product = products.find((p) => p.id === id);
  return (
    <div>
      <Link to="/" className="my-2 btn btn-primary">
        Go Back
      </Link>
      <Container className="py-3 ">
        <Row>
          <Col md={6} lg={4}>
            <Image fluid src={DJANGO_BASE_URL + product.image} alt={product.name} style={{width:"100%"}} />
          </Col>
          <Col md={6} lg={8}>
            <ListGroup>
              <ListGroupItem>
                <h1>{product.name}</h1><h6>({product.brand})</h6>
              </ListGroupItem>
              <ListGroupItem>
                <Rating
                  value={product.rating}
                  text={`(${product.numReviews}) Reviews`}
                  color="yellow"
                />
              </ListGroupItem>
              <ListGroupItem>
                <h3>Price: ₹{product.price}</h3>
              </ListGroupItem>
              <ListGroupItem>
                <p>Description: {product.description}</p>
              </ListGroupItem>
            </ListGroup>
          </Col>
        </Row>
        <Row>
          <Col md={4}>
            <Card>
              <ListGroup>
                <ListGroupItem>
                  <Row>
                    <Col>Price:</Col>
                    <Col>
                      <strong>₹{product.price}</strong>
                    </Col>
                  </Row>
                  <Row>
                    <Col>Status:</Col>
                    <Col>
                      <strong>
                        {product.countInStock > 1 ? "In Stock " : "Out Of Stock"} {`(${product.countInStock})`}
                      </strong>
                    </Col>
                  </Row>
                  <Row>
                    <Button className="btn" type="button" disabled={product.countInStock === 0}>
                      Add Cart
                    </Button>
                  </Row>
                </ListGroupItem>
              </ListGroup>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default ProductScreen;

// #Create a file named src/setupProxy.js (it must be exactly this name and location).

// const { createProxyMiddleware } = require('http-proxy-middleware');

// module.exports = function(app) {
//   app.use(
//     '/api', // Proxy requests starting with /api
//     createProxyMiddleware({
//       target: 'http://localhost:8000', // Your Django backend URL
//       changeOrigin: true,
//     })
//   );
//   app.use(
//     '/images', // Proxy requests starting with /images (your MEDIA_URL)
//     createProxyMiddleware({
//       target: 'http://localhost:8000', // Your Django backend URL
//       changeOrigin: true,
//     })
//   );
// };