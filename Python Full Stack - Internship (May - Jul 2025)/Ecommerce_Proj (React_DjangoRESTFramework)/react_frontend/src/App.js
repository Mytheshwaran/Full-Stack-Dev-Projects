import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom'; 
import { Container } from 'react-bootstrap';
import Footer from './Components/Footer/Footer';
import Header from './Components/Header/Header';
import HomeScreen from './Components/SCREENS/HomeScreen';
import ProductsScreen from './Components/SCREENS/ProductScreen';

function App() {
  return (
    <div className="App m-0 p-0">
        <BrowserRouter>
          <Header />
          <main>
              <Container>
                <Routes>
                  <Route path='/' element={<HomeScreen />} />
                  <Route path='/product/:id' element={<ProductsScreen />} />
                </Routes>
              </Container>
          </main>
          <Footer />
        </BrowserRouter>
    </div>
  );
}

export default App;
