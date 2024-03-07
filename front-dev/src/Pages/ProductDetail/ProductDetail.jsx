import React, { useEffect, useState, useContext } from "react";
import "./ProductDetail.css";
import Banner from "../../Components/Layout/Banner/Banner";
import { Rating, TextField } from "@mui/material";
import DescriptionIcon from "@mui/icons-material/Description";
import BrandingWatermarkIcon from "@mui/icons-material/BrandingWatermark";
import CategoryIcon from "@mui/icons-material/Category";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import HighlightOffIcon from "@mui/icons-material/HighlightOff";
import MonetizationOnIcon from "@mui/icons-material/MonetizationOn";
import Swal from "sweetalert2";
import StarIcon from "@mui/icons-material/Star";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../Context/AuthContext";
import axios from "axios";
import SelectDates from "../../Components/Common/SelectDates/SelectDates";

const ProductDetail = () => {
  const navigate = useNavigate();

  const [props, setProps] = useState({});
  const [ranking, setRanking] = useState({
    id: 0,
    custDni: "",
    rating: 0,
    review: "",
  });

  const [newRanking, setNewRanking] = useState(false);

  const { state } = useContext(AuthContext);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const fieldValue = name === "id" ? parseInt(value) : value;
  
    setRanking((prevState) => ({
      ...prevState,
      [name]: fieldValue,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(ranking);
    const response = axios.put(
      `http://18.191.243.189:8081/rental/review`,
      {
        id: ranking.id,
        custDni: ranking.custDni,
        rating: Number(ranking.rating),
        review: ranking.review,
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${state.jwtToken}`,
        },
      }
    );
    response
      .then((res) => {
        setNewRanking(true);
              Swal.fire("Se ha registrado la reseña con éxito", "", "success");
      })
      .catch((error) => {
        console.log(error);
        Swal.fire(error.response.data, "", "error");
      });
  };

  const fetchDataProductByID = async (idTag) => {
    setNewRanking(false);
    try {
      const response = await fetch(
        "http://18.191.243.189:8081/instruments/" + idTag,
        {
          method: "GET",
        }
      );
      if (response.ok) {
        const data = await response.json();
        setProps(data);
      } else {
        console.error("Error en la solicitud:", response.status);
      }
    } catch (error) {
      Swal.fire("No encontramos el producto, intentá con otro", "", "error");
      navigate("/products");
    }
  };

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const idProduct = urlParams.get("id");
    fetchDataProductByID(idProduct);
    if (!idProduct) {
      Swal.fire("No encontramos el producto, intentá con otro", "", "error");
      navigate("/products");
    }
  }, [newRanking]);

  
  return (
    <>
      <Banner
        isFront={true}
        title="Detalles del instrumento"
        subtitle="Verificá las características del instrumento"
      />
      <div className="block-image-container">
        <div className="block-image-container__main">
          <img src={props.instPhoto} alt="imagen principal" />
        </div>
        <div className="block-image-container__grid">
          <div className="block-image-container-A">
            <img src={props.instPhoto} alt="imagen A" />
            <img src={props.instPhoto} alt="imagen B" />
          </div>
          <div className="block-image-container-B">
            <img src={props.instPhoto} alt="imagen C" />
            <img src={props.instPhoto} alt="imagen D" />
          </div>
        </div>
      </div>
      <div className="main-product-container">
        <div className="product-container">
          {/* <div className="detail-item-image-container">
                <img src={props.instPhoto} alt="foto principal" />
            </div> */}
          <div className="detail-item-description-container">
            <span className="detail-item-title">{props.instName}</span>

            <div className="detail-item-text-container">
              <h3>
                <MonetizationOnIcon /> Precio
              </h3>
              <p>{props.instPrice} USD/día</p>
            </div>

            {state.jwtToken !== "" ? (
              <div className="detail-item-text-container ranking">
                <div className="avgRanking-container">
                  <h3>RESEÑAS</h3>
                  <button className="avgRanking-btn">
                    {props.ranking_prom}
                    <StarIcon />
                  </button>
                  <p className="avgRanking-txt">
                    {props.ranking_count} reseñas
                  </p>
                </div>

                <TextField
                  style={{ width: "50%" }}
                  margin="normal"
                  name="id"
                  label="Id de la Reserva"
                  type="number"
                  id="id"
                  onChange={handleChange}
                />
                <TextField
                style={{ width: "50%" }}
                  margin="normal"
                  name="custDni"
                  label="Dni"
                  type="text"
                  id="custDni"
                  defaultValue={state.custDni}
                  onChange={handleChange}
                />
                <Rating
                  name="ranking"
                  value={parseInt(ranking.rating)}
                  onChange={(e, value) => {
                    setRanking({ ...ranking, rating: value });
                  }}
                  emptyIcon={<StarIcon style={{ color: "gray" }} />}
                  icon={<StarIcon style={{ color: "rgba(243, 146, 0, 1)" }} />}
                />
                <TextField
                style={{ width: "50%" }}
                  margin="normal"
                  name="review"
                  label="Descripcion de la reseña"
                  type="text"
                  id="review"
                  onChange={handleChange}
                />
                <button
                  className="ranking-btn"
                  onClick={(e) => handleSubmit(e)}
                >
                  Enviar calificación
                </button>
              </div>
            ) : null}

            <div className="detail-item-text-container">
              <h3>
                <BrandingWatermarkIcon /> MARCA
              </h3>
              <p>{props.instBrand}</p>
            </div>
            <div className="detail-item-text-container">
              <h3>
                <CategoryIcon /> CATEGORÍA
              </h3>
              <p>{props.category}</p>
            </div>
           
            <div className="detail-item-text-container">
              <h3>
                {" "}
                <DescriptionIcon /> DESCRIPCIÓN
              </h3>
              <p>{props.instDescription}</p>
            </div>
            <div className="detail-item-icon-container">
              <div>
                {props.instIsActive == true ? (
                  <>
                    <h3>DISPONIBLE <CheckCircleOutlineIcon className="check-icon active" /></h3>{" "}
                    
                  </>
                ) : (
                  <>
                    <h3>NO DISPONIBLE <HighlightOffIcon className="check-icon inactive" /></h3>{" "}
                  </>
                )}
              </div>
            </div>
            <div className="detail-item-text-container">
              <h3>FECHAS DISPONIBLES</h3>
              <SelectDates instTagNumber={props.instTagNumber} />
            </div>
           
          </div>
        </div>
      </div>
    </>
  );
};

export default ProductDetail;
