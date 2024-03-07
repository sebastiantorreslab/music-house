import * as React from "react";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import { Link, useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import * as Yup from "yup";
import "../Login.css";
import axios from "axios";
import { useContext, useEffect } from "react";

import Swal from 'sweetalert2'
import { AuthContext } from "../../../Context/AuthContext";

const command = {
  emiter:"",
  tag:"",
  startDate:"",
  endDate:"",
}

const SignIn = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    command.emiter = urlParams.get("prev");
    command.tag = urlParams.get("tag");
    command.startDate = urlParams.get("startDate");
    command.endDate = urlParams.get("endDate");
    console.log({command});
  }, [window.location.search]);

  const { dispatch } = useContext(AuthContext);

  const { handleSubmit, handleChange, errors, values } = useFormik({
    initialValues: {
      custEmail: "",
      custPassword: "",
    },
    onSubmit: (data) => {
      const loginFetch = axios.post("http://18.191.243.189:8081/login", data);
      loginFetch.then((res) => {
          dispatch({ type: "LOGIN", payload: { custDni: res.data.custDni,custName: res.data.custName,
            custLastName: res.data.custLastName,
            custRole:res.data.custRole,               
            jwtToken: res.data.jwtToken}});
            if(command.emiter === "detail"){
              navigate(`/product-detail?id=${command.tag}`)
            }else{
              navigate("/")
            }
      })
      .catch((error) => {
        console.log(error);
        Swal.fire('Datos incorrectos', '', 'error');
      });
      
    },
    validationSchema: Yup.object().shape({
      custEmail: Yup.string()
        .required("Este campo es obligatorio")
        .email("Debe ser formato email"),
      custPassword: Yup.string().required("Este campo es obligatorio"),
    }),
    validateOnChange: false,
  });

  return (
    <div className="login-container">
      <h2>Iniciar Sesión</h2>
      <Box
        component="form"
        onSubmit={handleSubmit}
        noValidate
        className="login-form"
      >
        <TextField
          margin="normal"
          fullWidth
          id="custEmail"
          label="Correo Electrónico"
          name="custEmail"
          autoFocus
          onChange={handleChange}
          error={errors.custEmail ? true : false}
          helperText={errors.custEmail}
          value={values.custEmail}
        />
        <TextField
          margin="normal"
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
        <button className="button-submit" type="submit">
          Iniciar Sesión
        </button>
        <div className="form-links">
          <Link href="#" variant="body2" className="login-links">
            Te olvidaste la contraseña?
          </Link>
          <Link to="/register" variant="body2" className="login-links">
            No tienes cuenta? Regístrate
          </Link>
        </div>
      </Box>
    </div>
  );
};

export default SignIn;
