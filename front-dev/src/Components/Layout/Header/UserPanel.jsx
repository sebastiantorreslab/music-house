import { useContext, useEffect, useState } from "react";
import "../Header/Header.css";
import { AuthContext } from "../../../Context/AuthContext";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2'

const UserPanel = (props) => {
    const navigate = useNavigate();
    
    const {state, dispatch} =   useContext(AuthContext)
    const [user,setUser] = useState(true)

    const logout = () => {
        dispatch({type:"LOGOUT"})
        Swal.fire(
          'Se ha cerrado la sesión con éxito',
          '',
          'success'
        )
        setUser(false)
      }

      props.onStateChange(user);


  return (
    <>
        <div className="userPanel">
            <button className="Booking-link-container-button" onClick={() => navigate("/bookings")}>Mis reservas</button>
            {
                state.custRole === "ROLE_ADMIN" ? (<button className="Booking-link-container-button" onClick={()=> navigate("/admin")}>
                        Panel de administración
                    </button>) : null
            }
            <button className="Booking-link-container-button" onClick={logout}>Cerrar Sesión</button>
        </div>
    </>
  );
};

export default UserPanel;

