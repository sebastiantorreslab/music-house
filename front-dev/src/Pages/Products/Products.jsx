import React, { useContext } from "react";
import ProductCard from "../../Components/Common/ProductCard/ProductCard";
import "./Products.css";
import { Pagination, Stack } from "@mui/material";
import Banner from "../../Components/Layout/Banner/Banner";

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Products = () => {
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);

  const handleNavigateToProduct = (target) => {
    const tagDestination = target.instTagNumber;
    navigate("/product-detail?id=" + tagDestination);
  };

  useEffect(() => {
    const fetchDataProducts = async () => {
      try {
        const response = await fetch(
          "http://18.191.243.189:8081/instruments/list?size=100",
          {
            method: "GET",
          }
        );

        if (response.ok) {
          const data = await response.json();
          setProducts(data.content);
        } else {
          console.error("Error en la solicitud:", response.status);
        }
      } catch (error) {
        console.error("Error al realizar la solicitud:", error);
      }
    };

    fetchDataProducts();
  }, []);

  return (
    <>
      <Banner
        isFront={true}
        title="Todos los productos"
        subtitle="Encuentra todo lo que necesitas en un solo lugar"
      />
      <div className="productsSection">
        <h2 className="productsSection-subtitle">PRODUCTOS</h2>
        <div className="products-container">
          {products.map((product, index) => (
            <ProductCard
              key={index}
              product={product}
              linkeable={() => handleNavigateToProduct(product)}
            />
          ))}
        </div>
        <div className="pagination-container">
          <Stack spacing={5} className="pagination">
            <Pagination count={5} color="primary" />
          </Stack>
        </div>
      </div>
    </>
  );
};

export default Products;
