import React, { useEffect, useState } from 'react'
import { Col, Container, Row } from 'react-bootstrap'
//import products from '../Product/ProductsData'
import Product from '../Product/Product'
import axios from 'axios'

const DJANGO_BASE_URL = "http://localhost:8000";
function HomeScreen() {
  const [products, setProducts] = useState([])
  useEffect(() => {
    async function fetchProducts(){
      try{
        const {data}=await axios.get(`${DJANGO_BASE_URL}/api/products/`)
        setProducts(data)
      }
      catch(error){
        console.error('Fetch Products Error: ',error)
      }
    }
    fetchProducts();
  },[])
  return (
    <div>
      <Container>
        <Row>
            {products.map((product)=>
                <Col key={product.id} sm={12} md={6} lg={4} xl={3}>
                    <Product product={product}/>
                </Col>
            )}
        </Row>
      </Container>
    </div>
  )
}

export default HomeScreen
