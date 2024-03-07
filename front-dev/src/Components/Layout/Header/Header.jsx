import "../Header/Header.css";
import logo from "../../../assets/MHLogo.png";
import { useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import { Avatar } from "@mui/material";
import { AuthContext } from "../../../Context/AuthContext";
import { useEffect } from "react";
import Swal from 'sweetalert2'
import UserPanel from "./UserPanel";

const Header = () => {
  const navigate = useNavigate();

  const [user, setUser] = useState(false);

  const {state, dispatch} =   useContext(AuthContext)

  const [viewPanel, setViewPanel] = useState(false);

  const handleStateChange = (newState) => {
    setUser(newState);
  };


  useEffect(() => {
    if(state.jwtToken !== "") {
      setUser(true)
    }
    
  }, [state.jwtToken]);


  return (
    <>
      <header className="Header-container">
        <div onClick={() => navigate("/")} className="Header-container-isologo">
          <img className="Header-container-logo" src={logo} alt="Logo" />
          <div className="Header-container-brand">
            <p>
              <a className="Title-bold">M </a>
              <a className="Title-semibold">u </a>
              <a className="Title-Medium">s </a>
              <a className="Title-regular">i </a>
              <a className="Title-light">c </a>
              <a className="Title-regular">House</a>
            </p>
            <h4 className="Header-container-brand-slogan">Marcá tu ritmo...</h4>
          </div>
        </div>

        {!user ? (
          <div className="Header-container-buttons">
            <button
              className="Header-container-button button-logIn"
              onClick={() => navigate("/login")}
            >
              Iniciar Sesión
            </button>
            <button
              className="Header-container-button button-signUp"
              onClick={() => navigate("/register")}
            >
              Crear Cuenta
            </button>
          </div>
        ) : (
          <div onMouseEnter={() => setViewPanel(true)} onMouseLeave={() => setViewPanel(false)} className="Avatar-container-buttons">
                        
            <div  className="Avatar-user">
              <span>Hola {state.custName}</span>
              <Avatar aria-label="recipe" id="avatar" >
              {state.custName[0]} {state.custLastName[0]}
              </Avatar>
              {
                viewPanel ? <UserPanel onStateChange={handleStateChange}/> : null
              }
            </div>
           
          </div>
        )}
      </header>
    </>
  );
};

export default Header;
