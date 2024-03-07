import React from "react";
import "./Categories.css";
import { useNavigate } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import { AuthContext } from "../../../Context/AuthContext";

const Categories = () => {
  const navigate = useNavigate();
  document.cookie = "SameSite=Lax";

  const [categories, setCategories] = useState([]);
  const {state, dispatch} =   useContext(AuthContext);

  const handleNavigateCategory = (target) => {
    const newCategory = target.categoryTitle;
    navigate("/categories?cat=" + newCategory);
  };

  const nextCategories = () => {
    const carrusel = document.querySelector(".categories-container");
    carrusel.scrollLeft += 500;
  };

  const backCategories = () => {
    const carrusel = document.querySelector(".categories-container");
    carrusel.scrollLeft -= 500;
  };

  useEffect(() => {

    const fetchDataLogin = async () => {
      //if(state.jwtToken !== "") {
        fetchCategories();
        //fetchDataProducts(state.jwtToken); // pendiente el conteo
      //}
    };

    const fetchCategories = async () => {
      
      try {
        const response = await fetch(
          "http://18.191.243.189:8081/instruments/categories/list",
          {
            method: "GET"
          }
        );

        if (response.ok) {
          const data = await response.json();
          setCategories(data);

        } else {
          console.error("Error en la solicitud:", response.status);
        }
      } catch (error) {
        console.error("Error al realizar la solicitud:", error);
      }

    }

    const fetchDataProducts = async (token) => {

      try {
        const response = await fetch(
          "http://18.191.243.189:8081/instruments/list?size=100",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (response.ok) {
          const data = await response.json();
          console.log("Information: ", data);

          /** Count Items Per Category **/
          /** Mientras arreglamos las categorías **/
          let categoriesName = [
            "CUERDAS",
            "VIENTOS",
            "PERCUSION",
            "PIANO",
            "ACCESORIOS",
            "VOZ",
          ];
          let counters = [0, 0, 0, 0, 0, 0];

          data.content.forEach((product) => {
            categoriesName.forEach((itemName, j) => {
              if (itemName === product.category) {
                counters[j] += 1;
              }
            });
          });

          console.log(counters);
        } else {
          console.error("Error en la solicitud:", response.status);
        }
      } catch (error) {
        console.error("Error al realizar la solicitud:", error);
      }
    };

    fetchDataLogin();

  }, []);

  return (
    <div className="sectionCategories">
      <h2 className="sectionCategories-subtitle">
        Nuestras categorías disponibles
      </h2>

      <div className="control-button">
        <button onClick={backCategories} className="navigateButton">
          <span className="material-symbols-outlined">navigate_before</span>
        </button>
        <button onClick={nextCategories} className="navigateButton">
          <span className="material-symbols-outlined">navigate_next</span>
        </button>
      </div>

      <div className="categories-container">
        {categories.map((category, index) => (
          <div key={index} className="categories-container-card">
            <a onClick={() => handleNavigateCategory(category)}>
              <div className="card-image-container">
                <img src={category.cegoryImageURL} alt={category.categoryTitle} />
              </div>
              <div className="card-content-container">
                <p className="card-content__category">{category.categoryTitle}</p>
                <p className="card-content__inventory">
                  <span> {category.quantity} </span>artículos
                </p>
              </div>
            </a>
          </div>
        ))}
      </div>

    </div>
  );
};

export default Categories;
