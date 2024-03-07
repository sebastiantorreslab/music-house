import React from "react";
import ProductCard from "../../Components/Common/ProductCard/ProductCard";
import "./Categories.css";
import { Pagination, Stack } from "@mui/material";
import Banner from "../../Components/Layout/Banner/Banner";
import { useEffect } from "react";
import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../Context/AuthContext";

const Categories = () => {
  const navigate = useNavigate();

  let initialCategories = [
    "Cuerdas",
    "Vientos",
    "Percusion",
    "Piano",
    "Accesorios",
  ];

  const [categories, setCategories] = useState([]);
  const [products, setProducts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("CUERDAS");
  const [currentVisualPage, setCurrentVisualPage] = useState(1); // for pagination (starts at 1)
  const [currentPage, setCurrentPage] = useState(0); // for backend -1 from visual (starts at 0)
  const [totalPages, setTotalPages] = useState(0); // for backend -1 from visual (starts at 0)

  const handleChangeCategory = (e) => {
    const newCategory = e.target.innerText;
    navigate("/categories?cat=" + newCategory);
    setSelectedCategory(newCategory);

    setCurrentVisualPage(1);
    setCurrentPage(0);
  };

  const handlePageChange = (event, page) => {
    setCurrentVisualPage(page);
    setCurrentPage(page - 1);
  };

  const handleNavigateToProduct = (target) => {
    const tagDestination = target.instTagNumber;
    navigate("/product-detail?id=" + tagDestination);
  };

  const { state, dispatch } = useContext(AuthContext);

  const fetchDataLogin = async (cat) => {
    //if(state.jwtToken !== "") {
    fetchDataCategories();
    fetchDataProductsFiltered(state.jwtToken, cat);
    //}
  };

  const fetchDataCategories = async () => {
    try {
      const response = await fetch(
        "http://18.191.243.189:8081/instruments/categories/list",
        {
          method: "GET",
        }
      );
      if (response.ok) {
        const data = await response.json();
        setCategories(data);
      } else {
        console.error("Error en la solicitud:", response.status);
        setCategories([]);
      }
    } catch (error) {
      console.error("Error al realizar la solicitud:", error);
      setCategories([]);
    }
  };

  const fetchDataProductsFiltered = async (token, cat) => {
    try {
      const response = await fetch(
        "http://18.191.243.189:8081/instruments/category/" +
          cat +
          "?size=3&page=" +
          currentPage,
        {
          method: "GET",
        }
      );

      if (response.ok) {
        const data = await response.json();
        //console.log("productos", data.content);
        //console.log("==========>", data);
        setProducts(data.content);
        setTotalPages(data.totalPages);
      } else {
        console.error("Error en la solicitud:", response.status);
        setProducts([]);
      }
    } catch (error) {
      console.error("Error al realizar la solicitud:", error);
      setProducts([]);
    }
  };

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const urlCategory = urlParams.get("cat");
    fetchDataLogin(urlCategory.toUpperCase());
    setSelectedCategory(urlCategory.toUpperCase());
  }, [selectedCategory, state.jwtToken, currentPage]);

  return (
    <>
      <Banner
        isFront={true}
        title={selectedCategory}
        subtitle="Instrumentos disponibles en la categoría"
      />
      <div className="filtered-categories-container">
        <div className="selector-categories-list-container">
          <h3 className="selector-categories-title">Otras categorías</h3>
          {categories.map((category, index) =>
            category.categoryTitle.toUpperCase() ==
            selectedCategory.toUpperCase() ? (
              <button
                onClick={handleChangeCategory}
                className="selector-categories-item active"
                key={index}
              >
                {category.categoryTitle.toUpperCase()}
              </button>
            ) : (
              <button
                onClick={handleChangeCategory}
                className="selector-categories-item inactive"
                key={index}
              >
                {category.categoryTitle.toUpperCase()}
              </button>
            )
          )}
        </div>

        <div className="productsSection">
          <h2 className="productsSection-subtitle">
            PRODUCTOS EN LA CATEGORÍA
          </h2>

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
              <Pagination
                count={totalPages}
                color="primary"
                onChange={(event, page) => handlePageChange(event, page)}
                page={currentVisualPage}
              />
            </Stack>
          </div>
        </div>
      </div>
    </>
  );
};

export default Categories;
