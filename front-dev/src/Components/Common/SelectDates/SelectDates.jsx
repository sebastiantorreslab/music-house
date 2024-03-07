import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { Calendar, DateObject } from "react-multi-date-picker";
import "./SelectDates.css";
import { AuthContext } from "../../../Context/AuthContext";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

const SelectDates = ({ instTagNumber }) => {
  const [value, setValue] = useState([]);
  const [unavailableDates, setUnavailableDates] = useState([]);
  const [selectedRange, setSelectedRange] = useState([]);

  const { state } = useContext(AuthContext);
  const navigate = useNavigate();

  const months = [
    "Enero",
    "Febrero",
    "Marzo",
    "Abril",
    "Mayo",
    "Junio",
    "Julio",
    "Agosto",
    "Septiembre",
    "Octubre",
    "Noviembre",
    "Diciembre",
  ];
  const weekDays = ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"];

  const fetchDataDates = async () => {
    try {
      const response = await axios.post(
        "http://18.191.243.189:8081/rental/unavailable-dates",
        {
          instTagNumber: instTagNumber,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const dates = response.data.map((date) => ({
        start: new Date(date.value0),
        end: new Date(date.value1),
      }));
      setUnavailableDates(dates);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchDataDates();
  });

  const isUnavailable = (date) => {
    const currentDate = new Date(date.format("YYYY-MM-DD"));
    return unavailableDates.some(({ start, end }) => {
      const startDate = new Date(start);
      const endDate = new Date(end);
      return currentDate >= startDate && currentDate <= endDate;
    });
  };

  const mapDays = ({ date }) => {
    const isUnavailableDate = isUnavailable(date);
    const className = isUnavailableDate ? "unavailable-day" : "";
    const disabled = isUnavailableDate;
    return { className, disabled };
  };

  const rentInstrument = () => {
    const startDate = selectedRange[0];
    const endDate = selectedRange[1];
    const url = `/confirm-data?instTagNumber=${instTagNumber}&startDate=${startDate}&endDate=${endDate}`
    if (state.jwtToken !== "") {
      if(startDate!==undefined && endDate!==undefined) {
        navigate(url);
    }
    else {
      Swal.fire("Debes completar las 2 fechas", "", "error");
    }
    } else {
      Swal.fire({
        title: 'Tienes que iniciar sesión',
        text: 'Si no te has registrado, debes hacerlo.',
        icon: 'error',
        showCancelButton: true,
        confirmButtonText: 'Ir a Login',
        cancelButtonText: 'Cancelar',
        customClass: {
          confirmButton: 'btn btn-primary',
          cancelButton: 'btn btn-secondary'
        }
      }).then((result) => {
        if (result.isConfirmed) {
          navigate(`/login?prev=${"detail"}&tag=${instTagNumber}&startDate=${startDate}&endDate=${endDate}`); 
        }
      });
    }
  };

  const handleChange = (newValue) => {
    setValue(newValue);
    setSelectedRange(newValue.map((date) => date.format("YYYY-MM-DD")));
  };

  return (
    <div>
      <Calendar
        value={value}
        onChange={handleChange}
        range
        numberOfMonths={2}
        weekDays={weekDays}
        months={months}
        mapDays={mapDays}
      />

      <button className="rent_button" onClick={rentInstrument}>
        ALQUILAR
      </button>
    </div>
  );
};

export default SelectDates;