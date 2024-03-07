import React, { useContext } from "react";
import "./RegisterCategory.css";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import * as Yup from "yup";
import { AuthContext } from "../../../../Context/AuthContext";
import axios from "axios";
import Swal from "sweetalert2";
import { TextField } from "@mui/material";

const RegisterCategory = () => {
  const navigate = useNavigate();

  const { state } = useContext(AuthContext);

  const { handleSubmit, handleChange, errors, values } = useFormik({
    initialValues: {
      categoryTitle: "",
      categoryDescription: "",
      categoryImage: "",
    },
    onSubmit: (data) => {
      const categoryFetch = axios.post(
        "http://18.191.243.189:8081/admin/category/registry",
        data,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${state.jwtToken}`,
          },
        }
      );
      categoryFetch
        .then((res) => {
          if (res.status === 201) {
            Swal.fire("Se ha registrado la categoría", "", "success");
            navigate("/register-product");
          } else {
            throw new Error("Categoría ya registrada");
          }
        })
        .catch((error) => {
          console.log(error);
          Swal.fire(error.response.data, "", "error");
        });
    },
    validationSchema: Yup.object().shape({
      categoryTitle: Yup.string()
        .required("Este campo es obligatorio")
        .min(5, "Debe tener mínimo 5 dígitos"),
      categoryDescription: Yup.string().required("Este campo es obligatorio"),
      categoryImage: Yup.string().required("Este campo es obligatorio"),
    }),
    validateOnChange: false,
  });

  return (
    <div>
      <form className="register-form-category" onSubmit={handleSubmit}>
        <h1 className="register-title">
          {" "}
          Formulario de adición de Categorías{" "}
        </h1>
        <TextField
          style={{ width: "50%" }}
          margin="normal"
          name="categoryTitle"
          id="categoryTitle"
          label="Nombre"
          autoFocus
          onChange={handleChange}
          error={errors.categoryTitle ? true : false}
          helperText={errors.categoryTitle}
          value={values.categoryTitle.toUpperCase()}
        />

        <TextField
          style={{ width: "50%" }}
          margin="normal"
          name="categoryDescription"
          id="categoryDescription"
          label="Inserte la descripción"
          autoFocus
          onChange={handleChange}
          error={errors.categoryDescription ? true : false}
          helperText={errors.categoryDescription}
          value={values.categoryDescription}
        />
        <TextField
          style={{ width: "50%" }}
          margin="normal"
          name="categoryImage"
          id="categoryImage"
          label="Url de la imagen"
          autoFocus
          onChange={handleChange}
          error={errors.categoryImage ? true : false}
          helperText={errors.categoryImage}
          value={values.categoryImage}
        />
        <button type="submit">REGISTRAR</button>
      </form>
    </div>
  );
};

export default RegisterCategory;
