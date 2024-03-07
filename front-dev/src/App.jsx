import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./Components/Layout/Layout";
import Home from "./Pages/Home";
import Admin from "./Pages/Admin/Admin";
import Products from "./Pages/Products/Products";
import ProductDetail from "./Pages/ProductDetail/ProductDetail";
import Categories from "./Pages/Categories/Categories";
import SignIn from "./Pages/Login/SignIn/SignIn";
import AuthContextProvider from "./Context/AuthContext";
import Register from "./Pages/Login/Sign up/Register";
import ProtectedRoutes from "./ProtectedRoutes";
import VerifyEmail from "./Pages/Login/Sign up/VerifyEmail";
import ManageUsers from "./Pages/Admin/ManageUsers/ManageUsers";
import DeleteCategory from "./Pages/Admin/CRUD_Category/DeleteCategory/DeleteCategory";
import RegisterFormInstrument from "./Pages/Admin/CRUD_Product/RegisterProduct/RegisterFormInstrument";
import DeleteProduct from "./Pages/Admin/CRUD_Product/DeleteProduct/DeletedProduct";
import RegisterCategory from "./Pages/Admin/CRUD_Category/RegisterCategory/RegisterCategory";
import 'sweetalert2/dist/sweetalert2.css';
import Bookings from "./Pages/Bookings/Bookings";
import Confirm from "./Pages/Reservation/Confirm/Confirm";

function App() {
  return (
    <BrowserRouter>
      <AuthContextProvider>
        <Routes>
          <Route element={<Layout />}>
            <Route path="/" element={<Home />} />

            <Route path="/products" element={<Products />} />
            <Route path="/login" element={<SignIn />} />
            <Route path="/register" element={<Register />} />
            <Route path="/verify-email" element={<VerifyEmail />} />
            <Route path="/product-detail" element={<ProductDetail />} />
            <Route path="/categories" element={<Categories />} />
            <Route path="/bookings" element={<Bookings />} />
            <Route path="/confirm-data" element={<Confirm />} />

            <Route element={<ProtectedRoutes />}>
              <Route path="/admin" element={<Admin />} />
              <Route
                path="/register-product"
                element={<RegisterFormInstrument />}
              />
              <Route path="/register-category" element={<RegisterCategory />} />
              <Route path="/remove-product" element={<DeleteProduct />} />
              <Route path="/remove-category" element={<DeleteCategory />} />
              <Route path="/manage-users" element={<ManageUsers />} />
            </Route>
          </Route>

          <Route path="*" element={<h1>404 no encontrado</h1>} />
        </Routes>
      </AuthContextProvider>
    </BrowserRouter>
  );
}

export default App;
