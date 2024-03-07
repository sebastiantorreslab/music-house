import React from "react";
import Banner from "../../../Components/Layout/Banner/Banner";
import "./ManageUsers.css";
import UserCard from "../../../Components/Common/UserCard/UserCard";
import { useState, useEffect } from "react";
import { useContext } from "react";
import { AuthContext } from "../../../Context/AuthContext";
import HandlePermits from "../HandlePermits/HandlePermits";

const ManageUsers = () => {
  const [users, setUsers] = useState([]);
  const { state } = useContext(AuthContext);

  const [mostrar, setMostrar] = useState(false);

  const click = () => {
    setMostrar(true);
  };

  useEffect(() => {
    const fetchDataLogin = async () => {
      if (state.jwtToken !== "") {
        fetchDataUsers(state.jwtToken);
      }
    };

    const fetchDataUsers = async (token) => {
      try {
        const response = await fetch(
          "http://18.191.243.189:8081/admin/customer/list?page=0&size=300",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.ok) {
          const data = await response.json();

          setUsers(data.content);
          // Haz lo que necesites con el bearer token
        } else {
          console.error("Error en la solicitud:", response.status);
        }
      } catch (error) {
        console.error("Error al realizar la solicitud:", error);
      }
    };

    fetchDataLogin();
  }, [state.jwtToken, users]);

  return (
    <>
      <Banner
        title="Administrar roles de usuarios"
        subtitle="Permite actualizar los permisos de cada usuario"
      />
      <h1 className="register-title"> Gestión de roles de usuario </h1>

      <div className="navbar">
        <div id="admin-search" className="search">
          <input type="text" placeholder="Buscar usuario" />
          <button>Buscar</button>
        </div>
        <button onClick={click} className="permissionsButton">
          Modificar Permisos
        </button>
      </div>

      <div className="container">
        <p>
          <span>Nombre</span>

          <span>Email</span>

          <span>Rol</span>
        </p>

        {users.map((user, index) => (
          <UserCard key={index} user={user} />
        ))}
      </div>
    </>
  );
};

export default ManageUsers;
