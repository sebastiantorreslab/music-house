import React from "react";
import "../Login.css";
import { useFormik } from "formik";
import * as Yup from "yup";
import { Box, Grid, MenuItem, Select, TextField } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from "sweetalert2";

const Register = () => {
  const navigate = useNavigate();

  const { handleSubmit, handleChange, errors, values } = useFormik({
    initialValues: {
      custDni: "",
      custFirsName: "",
      custLastName: "",
      custEmail: "",
      custPassword: "",
      custPhone: "",
      address: {
        custAddress: "",
        city: { cityCode: " ", country: { countryCode: " " } },
      },
    },
    onSubmit: (data) => {
      console.log(data);
      const loginFetch = axios.post(
        "http://18.191.243.189:8081/customers/registry",
        data
      );
      loginFetch
        .then((res) => {
          if (res.status === 200) {
            Swal.fire("Te has registrado con éxito", "", "success");
            navigate("/verify-email");
          } else {
            throw new Error("Datos ya registrados");
          }
        })
        .catch((error) => {
          console.log(error);
          Swal.fire(error.response.data, "", "error");
        });
    },
    validationSchema: Yup.object().shape({
      custDni: Yup.string().required("Este campo es obligatorio")
      .min(5, "Debe tener mínimo 5 dígitos")
      .max(10, "Debe tener máximo 10 dígitos"),
      custFirsName: Yup.string()
        .required("Este campo es obligatorio")
        .min(3, "Debe escribir un nombre"),
      custLastName: Yup.string()
        .required("Este campo es obligatorio")
        .min(3, "Debe escribir un apellido"),
      custEmail: Yup.string()
        .required("Este campo es obligatorio")
        .email("Debe ser formato email"),
      custPassword: Yup.string()
        .required("Este campo es obligatorio")
        .min(6, "La contraseña debe tener mínimo 6 caracteres"),
      custPhone: Yup.number(
        "Debe escribir un número"
      ).required("Este campo es obligatorio")
      .min(6, "Debe tener mínimo 6 dígitos"),
      address: Yup.object().shape({
        custAddress: Yup.string().required("Este campo es obligatorio"),
        city: Yup.object().shape({
          cityCode: Yup.string().required("Este campo es obligatorio"),
          country: Yup.object().shape({
            countryCode: Yup.string().required("Este campo es obligatorio"),
          }),
        }),
      }),
    }),
    validateOnChange: false,
  });

  return (
    <div className="register-container">
      <h2>Regístrate</h2>
      <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 5 }}>
        <Grid container spacing={2}>
          <Grid item xs={6} sm={6}>
            <TextField
              name="custFirsName"
              fullWidth
              id="custFirsName"
              label="Nombre"
              onChange={handleChange}
              error={errors.custFirsName ? true : false}
              helperText={errors.custFirsName}
              value={values.custFirsName}
              autoFocus
            />
          </Grid>
          <Grid item xs={6} sm={6}>
            <TextField
              fullWidth
              id="custLastName"
              label="Apellido"
              name="custLastName"
              onChange={handleChange}
              error={errors.custLastName ? true : false}
              helperText={errors.custLastName}
              value={values.custLastName}
            />
          </Grid>
          <Grid item xs={6} sm={6}>
            <TextField
              fullWidth
              id="custDni"
              label="Dni"
              name="custDni"
              onChange={handleChange}
              error={errors.custDni ? true : false}
              helperText={errors.custDni}
              value={values.custDni}
            />
          </Grid>
          <Grid item xs={6} sm={6}>
            <TextField
              fullWidth
              id="custPhone"
              label="Numero de teléfono"
              name="custPhone"
              onChange={handleChange}
              error={errors.custPhone ? true : false}
              helperText={errors.custPhone}
              value={values.custPhone}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              id="custEmail"
              label="Correo Electrónico"
              name="custEmail"
              onChange={handleChange}
              error={errors.custEmail ? true : false}
              helperText={errors.custEmail}
              value={values.custEmail}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              name="custPassword"
              label="Contraseña"
              type="password"
              id="custPassword"
              onChange={handleChange}
              error={errors.custPassword ? true : false}
              helperText={errors.custPassword}
              value={values.custPassword}
            />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField
              fullWidth
              id="address.custAddress"
              label="Calle"
              name="address.custAddress"
              onChange={handleChange}
              error={
                errors.address && errors.address.custAddress ? true : false
              }
              helperText={errors.address && errors.address.custAddress}
              value={values.address && values.address.custAddress}
            />
          </Grid>
          <Grid item xs={6} sm={4}>
            <Select
              fullWidth
              id="address.city.cityCode"
              label="Elige una ciudad"
              name="address.city.cityCode"
              onChange={handleChange}
              error={
                errors.address && values.address.city.cityCode ? true : false
              }
              value={values.address && values.address.city.cityCode}
            >
              <MenuItem value=" ">Elige tu ciudad</MenuItem>
              <MenuItem value="BOGOTA">BOGOTA</MenuItem>
              <MenuItem value="BUENOS_AIRES">BUENOS_AIRES</MenuItem>
              <MenuItem value="CALI">CALI</MenuItem>
              <MenuItem value="CORDOBA">CORDOBA</MenuItem>
              <MenuItem value="MEDELLIN">MEDELLIN</MenuItem>
              <MenuItem value="MENDOZA">MENDOZA</MenuItem>
            </Select>
          </Grid>

          <Grid item xs={6} sm={4}>
            <Select
              fullWidth
              id="address.city.country.countryCode"
              label="Elige tu pais"
              name="address.city.country.countryCode"
              onChange={handleChange}
              error={
                errors.address && values.address.city.country.countryCode
                  ? true
                  : false
              }
              value={values.address && values.address.city.country.countryCode}
            >
              <MenuItem value=" ">Elige tu país</MenuItem>
              <MenuItem value="COL">COL</MenuItem>
              <MenuItem value="ARG">ARG</MenuItem>
            </Select>
          </Grid>
        </Grid>
        <div className="register-button">
          <button className="button-register" type="submit">
            Regístrate
          </button>
        </div>

        <Grid container justifyContent="flex-end">
          <Grid item>
            <Link to="/login" variant="body2" className="login-links">
              Ya tienes una cuenta? Inicia Sesión
            </Link>
          </Grid>
        </Grid>
      </Box>
    </div>
  );
};

export default Register;
