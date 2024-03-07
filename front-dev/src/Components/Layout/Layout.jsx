import React from "react";
import Header from "./Header/Header";
import Footer from "./Footer/Footer";
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div>
      <Header />
      <div className="main">
      <Outlet />
      </div>

      <Footer />
    </div>
  );
};

export default Layout;
