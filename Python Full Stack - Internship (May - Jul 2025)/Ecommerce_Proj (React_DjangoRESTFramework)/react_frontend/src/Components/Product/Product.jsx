import React from 'react'
import {Card} from 'react-bootstrap'
import './Product.css'
import Rating from '../Rating/Rating'
import { Link } from 'react-router-dom'

const DJANGO_BASE_URL="http://localhost:8000"
function Product({product}) {
  return (
    <div>
      <Card className="my-3 p-3 rounded h-100" id="product-card-id">
          <Link to={`/product/${product.id}`}>
            <Card.Img src={DJANGO_BASE_URL + product.image} id="card-img-id"/>
          </Link>
          <Card.Body>
            <Link to={`/product/${product.id}`} className="text-decoration-none">
              <strong>{product.name}</strong>
            </Link>
          </Card.Body>
          <Card.Text as="div">
            <div className="py-3">
              <Rating value={product.rating} text={`(${product.numReviews}) Reviews`} color="yellow" />
            </div>
          </Card.Text>
          <Card.Text as="h4">
            ₹{product.price}
          </Card.Text>
      </Card>
    </div>
  )
}

export default Product
