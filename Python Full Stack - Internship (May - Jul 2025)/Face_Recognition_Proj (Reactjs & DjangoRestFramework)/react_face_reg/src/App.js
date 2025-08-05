import { RouterProvider } from "react-router-dom";
import store from "./Redux/Store";
import routers from "./Router";
import { Provider } from "react-redux";

function App() {
  return (
    <div>
      <Provider store={store}>
        <RouterProvider router={routers} />
      </Provider>
    </div>
  );
}

export default App;
