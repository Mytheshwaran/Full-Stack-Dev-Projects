import { createBrowserRouter } from 'react-router-dom'
import HomeApp from "./Components/HomeApp";

const routers = createBrowserRouter([
  {
    path: "/",
    element: <HomeApp />
  },
])

export default routers;