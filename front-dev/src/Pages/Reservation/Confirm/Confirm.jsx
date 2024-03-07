import React from "react";
import { Navigate, useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import Banner from "../../../Components/Layout/Banner/Banner";
import "./Confirm.css";
import { useContext } from "react";
import { AuthContext } from "../../../Context/AuthContext";
import axios from "axios";
import Swal from "sweetalert2";

const Confirm = () => {
  const location = useLocation();
  const navigate = useNavigate()
  const [product, setProduct] = useState({});

  const { state } = useContext(AuthContext);

  const searchParams = new URLSearchParams(location.search);
  const startDate = searchParams.get("startDate");
  const endDate = searchParams.get("endDate");
  let tag = searchParams.get("instTagNumber");
  let url = "http://18.191.243.189:8081/instruments/" + tag;

  const fetchDataProduct = async () => {
    try {
      const response = await fetch(url, {
        method: "GET",
      });

      if (response.ok) {
        const data = await response.json();
        setProduct(data);
      } else {
        console.error("Error en la solicitud:", response.status);
      }
    } catch (error) {
      console.error("Error al realizar la solicitud:", error);
    }
  };

  useEffect(() => {
    fetchDataProduct();
  }, [product]);

  const calculatePrice = () => {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const differenceInDays = Math.floor((end - start) / (1000 * 60 * 60 * 24));
    return (differenceInDays + 1) * product.instPrice;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const response = axios.post(
      `http://18.191.243.189:8081/rental/registry`,
      {
        instTagNumber: product.instTagNumber,
        custDni: state.custDni,
        startDate: startDate,
        endDate: endDate,
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${state.jwtToken}`,
        },
      }
    );
    response.then((res) => {
      Swal.fire("Se ha reservado el instrumento con éxito", `El id de la reserva es: ${res.data.id}`, "success");
      navigate("/bookings")
    })
    .catch((error) => {
      //console.log("EL ERR", error);
      let mensajeError = "";

      if(error.response.data[0]){
        let mensajeLocal = "Fecha de inicio: "+error.response.data[0].error;
        mensajeError +=  mensajeLocal + " || " 
      }
      if(error.response.data[1]){
        let mensajeLocal = "Fecha de finalización: "+error.response.data[1].error;
        mensajeError +=  mensajeLocal + "\n" 
      }

      //console.log("Log 1: ", error.response.data[0].field, error.response.data[0].error);
      //console.log("Log 2: ", error.response.data[1].field, error.response.data[1].error);

      Swal.fire("No pudimos reservar, verifica tus datos", mensajeError, "warning");
    })
  };

  return (
    <>
      <Banner
        title="Confirmar reserva"
        subtitle="Revisa que todos los datos sean correctos"
      />
      <div className="container">
        <div className="header-reserve">
          <h2>Reserva</h2>
          <button className="edit-button">
            <span className="material-symbols-outlined">edit</span>
          </button>
        </div>
        <div className="container-reserve">
          <div>
            <p>{product.instName}</p>
            <p>Precio: {product.instPrice} USD/día</p>
            <p>Fecha de inicio: {startDate}</p>
            <p>Fecha de finalización: {endDate}</p>
          </div>

          <div className="total">
            <h2>TOTAL</h2>
            <h2>${calculatePrice()}</h2>
          </div>
        </div>
      </div>
      <button className="finish-button" onClick={(e) => handleSubmit(e)}>
        Finalizar alquiler
      </button>
    </>
  );
};

export default Confirm;
