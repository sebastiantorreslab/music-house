import React, { useContext } from "react";
import ProductCard from "../../../../Components/Common/ProductCard/ProductCard";
import { Grid, ListItem, Pagination, Stack } from "@mui/material";
import Banner from "../../../../Components/Layout/Banner/Banner";
import { useState, useEffect } from "react";
import { AuthContext } from "../../../../Context/AuthContext";
import axios from "axios";
import Swal from "sweetalert2";
import "./DeleteCategory.css";

const DeleteCategory = () => {
  const [categories, setCategories] = useState([]);
  const [deletedCategoryId, setDeletedCategoryId] = useState(null);

  const { state } = useContext(AuthContext);

  useEffect(() => {
    const fetchDataCategories = axios.get(
      "http://18.191.243.189:8081/instruments/categories/list"
    );
    fetchDataCategories
      .then((res) => {
        console.log(res.data);
        setCategories(res.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [deletedCategoryId]);

  const DeleteCategory = (id, name) => {
    const deleteProductFetch = axios.delete(
      `http://18.191.243.189:8081/admin/category/${name}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${state.jwtToken}`,
        },
      }
    );
    deleteProductFetch
      .then((res) => {
        Swal.fire(res.data, "", "success");
        console.log(res);
        setDeletedCategoryId(id);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <Banner
        isFront={true}
        title="Eliminar categorías"
        subtitle="Elimina las categorías que ya no estan disponibles"
      />
      <Grid
        container
        columns={{ xs: 2, sm: 4, md: 12 }}
        align="center"
        className="deleteCategoryContainer"
      >
        {categories.map((category, index) => {
          if (category.id === deletedCategoryId) {
            return null;
          }
          return (
            <Grid
              item
              xs={2}
              sm={2}
              md={3}
              key={index}
              className="categories-container-card"
            >
              <div className="card-image-container">
                <img
                  src={category.cegoryImageURL}
                  alt={category.categoryTitle}
                />
              </div>
              <div className="card-content-container">
                <p className="card-content__category">
                  {category.categoryTitle}
                </p>
              </div>
              <button
                className="Avatar-container-button"
                onClick={() =>
                  DeleteCategory(category.id, category.categoryTitle)
                }
              >
                Eliminar categoría
              </button>
            </Grid>
          );
        })}
      </Grid>
    </>
  );
};

export default DeleteCategory;
