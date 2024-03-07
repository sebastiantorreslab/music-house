import React from "react";
import "../Favorites/Favorites.css";
import ProductCard from "../ProductCard/ProductCard";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Grid } from "@mui/material";

const Favorites = () => {
  const navigate = useNavigate();
  const handleNavigateToProduct = (target) => {
    const tagDestination = target.instTagNumber;
    navigate("/product-detail?id=" + tagDestination);
  };

  const [products, setProducts] = useState([]);

  const fetchDataProducts = async (token) => {
    try {
      const response = await fetch(
        "http://18.191.243.189:8081/instruments/list?size=100",
        {
          method: "GET",
        }
      );

      if (response.ok) {
        const data = await response.json();
        let arr = data.content;
        function shuffleArray(array) {
          for (var i = array.length - 1; i > 0; i--) {
            var j = Math.floor(Math.random() * (i + 1));
            var temp = array[i];
            array[i] = array[j];
            array[j] = temp;
          }
        }
        shuffleArray(arr);
        setProducts(arr.length >= 8 ? arr.splice(0, 8) : arr);
      } else {
        console.error("Error en la solicitud:", response.status);
      }
    } catch (error) {
      console.error("Error al realizar la solicitud:", error);
    }
  };

  useEffect(() => {
    fetchDataProducts();
  }, []);

  return (
    <div className="favoritesSection">
      <h2 className="sectionFavorites-subtitle">Recomendados para tí</h2>
      <Grid
        container
        columns={{ xs: 2, sm: 4, md: 12 }}
        align="center"
        className="favorites-container"
      >
        {products.map((favorite, index) => (
          <Grid item xs={2} sm={2} md={3} key={index}>
            <ProductCard
              key={index}
              product={favorite}
              linkeable={() => handleNavigateToProduct(favorite)}
            />
          </Grid>
        ))}
      </Grid>
    </div>
  );
};
export default Favorites;
