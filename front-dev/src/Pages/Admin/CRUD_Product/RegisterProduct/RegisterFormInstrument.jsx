import React, { useContext, useEffect, useState } from "react";
import Banner from "../../../../Components/Layout/Banner/Banner";
import "./RegisterFormInstrument.css";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import * as Yup from "yup";
import { AuthContext } from "../../../../Context/AuthContext";
import axios from "axios";
import { MenuItem, Select, TextField } from "@mui/material";
import Swal from "sweetalert2";

const RegisterFormInstrument = () => {
  const navigate = useNavigate();

  const { state } = useContext(AuthContext);
  const [categoryOptions, setCategoryOptions] = useState([]);

  useEffect(() => {
    const categoryFetch = axios.get(
      "http://18.191.243.189:8081/instruments/categories/list",
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${state.jwtToken}`,
        },
      }
    );
    categoryFetch
      .then((res) => {
        setCategoryOptions(res.data);
        console.log("Categorias set");
      })
      .catch((error) => {
        console.log(error);
        Swal.fire(error.response.data, "", "error");
      });
  }, []);

  const { handleSubmit, handleChange, errors, values } = useFormik({
    initialValues: {
      instName: "",
      instBrand: "",
      instTagNumber: "",
      instAcqDate: "",
      instIsActive: true,
      category:
        categoryOptions.length > 0 ? categoryOptions[0].categoryTitle : "",
      instDescription: "",
      instPhoto: "",
      instPrice: "",
    },
    onSubmit: (data) => {
      console.log(data);
      console.log("Form submitted");
      const instrumentFetch = axios.post(
        "http://18.191.243.189:8081/admin/instrument/registry",
        data,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${state.jwtToken}`,
          },
        }
      );
      instrumentFetch
        .then((res) => {
          if (res.status === 200) {
            Swal.fire("Se ha registrado el instrumento", "", "success");
            navigate("/admin");
          } else {
            throw new Error("Instrumento ya registrado");
          }
        })
        .catch((error) => {
          console.log(error);
          Swal.fire(error.response.data, "", "error");
        });
    },
    validationSchema: Yup.object().shape({
      instName: Yup.string()
        .required("Este campo es obligatorio")
        .matches(/^[A-Za-z]+$/, "Debe contener solo letras"),
      instBrand: Yup.string().required("Este campo es obligatorio"),
      instTagNumber: Yup.string()
        .required("Este campo es obligatorio")
        .min(4, "Debe tener mínimo 4 dígitos")
        .max(6, "Debe tener maximo 6 dígitos"),
      instAcqDate: Yup.string().required("Este campo es obligatorio"),
      instIsActive: Yup.string().required("Este campo es obligatorio"),
      category: Yup.string().required("Este campo es obligatorio"),
      instDescription: Yup.string().required("Este campo es obligatorio"),
      instPhoto: Yup.string().required("Este campo es obligatorio"),
      instPrice: Yup.number()
        .required("Este campo es obligatorio")
        .min(0, "Debe ser positivo"),
    }),
    validateOnChange: false,
  });

  return (
    <>
      <Banner
        title="Agregar instrumentos al catálogo"
        subtitle="Permite la adición de ítems de catálogo general"
      />
      <h1 className="register-title">
        {" "}
        Formulario de adición de Instrumentos{" "}
      </h1>
      <div className="register-form-container">
        <form className="register-form" onSubmit={handleSubmit}>
          <TextField
            style={{ width: "60%" }}
            margin="normal"
            autoFocus
            label="Inserte el nombre"
            id="instName"
            name="instName"
            onChange={handleChange}
            error={errors.instName ? true : false}
            helperText={errors.instName}
            value={values.instName}
          />

          <TextField
            style={{ width: "60%" }}
            margin="normal"
            autoFocus
            label="Inserte la marca"
            id="instBrand"
            name="instBrand"
            onChange={handleChange}
            error={errors.instBrand ? true : false}
            helperText={errors.instBrand}
            value={values.instBrand}
          />

          <TextField
            style={{ width: "60%" }}
            margin="normal"
            autoFocus
            label="Inserte el número de etiqueta"
            onChange={handleChange}
            id="instTagNumber"
            name="instTagNumber"
            error={errors.instTagNumber ? true : false}
            helperText={errors.instTagNumber}
            value={values.instTagNumber}
          />

          <label>Fecha de Adquisición</label>
          <TextField
            style={{ width: "60%" }}
            margin="normal"
            autoFocus
            type="date"
            onChange={handleChange}
            id="instAcqDate"
            name="instAcqDate"
            error={errors.instAcqDate ? true : false}
            helperText={errors.instAcqDate}
            value={values.instAcqDate}
          />

          <label> ¿Se encuentra activo el instrumento? </label>
          <Select
            style={{ width: "60%" }}
            onChange={handleChange}
            id="instIsActive"
            name="instIsActive"
            error={errors.instIsActive ? true : false}
            helperText={errors.instIsActive}
            value={values.instIsActive}
          >
            <MenuItem value={true}>Activo</MenuItem>
            <MenuItem value={false}>Inactivo</MenuItem>
          </Select>

          <div className="category-container">
            <div>
              <label>Categoría del instrumento</label>
              <Select
                style={{ width: "60%" }}
                onChange={handleChange}
                id="category"
                name="category"
                error={errors.category ? true : false}
                helperText={errors.category}
                value={values.category}
              >
                {categoryOptions.map((category) => (
                  <MenuItem key={category.id} value={category.categoryTitle}>
                    {category.categoryTitle}
                  </MenuItem>
                ))}
              </Select>
            </div>
            <button onClick={() => navigate("/register-category")}>
              Agregar categoría
            </button>
          </div>

          <TextField
            style={{ width: "60%" }}
            margin="normal"
            autoFocus
            type="textarea"
            label="Inserte la descripción"
            onChange={handleChange}
            id="instDescription"
            name="instDescription"
            error={errors.instDescription ? true : false}
            helperText={errors.instDescription}
            value={values.instDescription}
          />

          <TextField
            style={{ width: "60%" }}
            margin="normal"
            autoFocus
            label="Url de la imagen"
            onChange={handleChange}
            id="instPhoto"
            name="instPhoto"
            error={errors.instPhoto ? true : false}
            helperText={errors.instPhoto}
            value={values.instPhoto}
          />

          <TextField
            style={{ width: "60%" }}
            margin="normal"
            autoFocus
            type="number"
            label="Precio del alquiler por dia"
            onChange={handleChange}
            id="instPrice"
            name="instPrice"
            error={errors.instPrice ? true : false}
            helperText={errors.instPrice}
            value={values.instPrice}
          />

          <button type="submit">REGISTRAR</button>
        </form>
      </div>
    </>
  );
};

export default RegisterFormInstrument;
