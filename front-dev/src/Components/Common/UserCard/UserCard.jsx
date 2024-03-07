import React, { useEffect,useState } from "react";
import "./UserCard.css";
import UpdateUser from "./UpdateUser";
import { useContext } from "react";
import { AuthContext } from "../../../Context/AuthContext";
import axios from "axios";
import Swal from "sweetalert2";

const UserCard = ({user}) => {
  const {state} =   useContext(AuthContext);
  const[estado,setEstado] = useState(false);  

  useEffect(() => {

  }, [state.jwtToken]);
    
 const deleteFunction = () => {
  let dni = user.custDni;
  let url= "http://18.191.243.189:8081/admin/customer/delete"+"/" + dni;

  const deleteUser = axios.delete(url,
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${state.jwtToken}`,
      },
    }
  );
  deleteUser
  .then((res) => {
      if (res.status === 202) {
        Swal.fire("Se ha eliminado el usuario", "", "success");
        console.log("Usuario borrado");
        
      } else {
        throw new Error("No se realizaron cambios");
      }
    })
    .catch((error) => {
      console.log(error);
    });

 }

  return (
    <>
      <div className="userCard">
          <div className="name">{user.custFirsName} {user.custLastName}</div>
          <div className="email">{user.custEmail}</div>
          <div>{user.role=='ROLE_ADMIN' ?  'Admin' : 'User'}</div>
          <button onClick={() => {setEstado(!estado)}} className="changeRol"><span class="material-symbols-outlined">edit</span></button>
          <button onClick={deleteFunction} className="deleteUSer"><span class="material-symbols-outlined">delete</span></button>
      </div>
      <div>
      {estado ? <UpdateUser user={user} estado={estado} /> : null }
      </div>
      { estado ? <button onClick={() => {setEstado(!estado)}} className="closeModal"><span class="material-symbols-outlined">close</span></button> : null}
    </>
  );
};

export default UserCard;

