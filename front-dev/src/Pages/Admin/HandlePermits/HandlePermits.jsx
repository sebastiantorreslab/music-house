import React, { useEffect, useState } from "react";
import styled from 'styled-components'
import { useContext } from "react";
import axios from "axios";
import Swal from "sweetalert2";




const HandlePermits = () => {




return (

    <>
        <Overlay>
            <ModalContainer>
                <ModalHeader>
                  <div className="contentHeader">
                    <h2>Gestión de permisos</h2>                   
                  </div>
                    
                </ModalHeader>
                <div className="infoContainer">
                    <p>TIPO DE USUARIO: </p>
                    <p>ACTUAL: </p>
                    <p>NUEVO:
                        <select id="selector">
                            <option value="admin">Admin</option>
                            <option value="user">User</option>
                        </select>
                    </p>
                    <div className="containerButton">
                      <button >Guardar</button>
                    </div>
                    
                </div>
                
            </ModalContainer>
        </Overlay>
    </>

);

};

export default HandlePermits;


const Overlay = styled.div `
    position:fixed;
    top:0;
    left:0;
    height:100vh;
    width:100vw;
    color: black;
    background-color: rgba(0,0,0,0.5);
    display: flex;
    align-items:center;
    justify-content: center;
    border-color: black;
    border-width:5px;
`;

const ModalContainer = styled.div `
    background: white;
    width: 30vw;
    height: 500px;
    padding:20px;
    border-radius:10px;
    border: 1px black solid;
    box-shadow: 10px 30px 30px #aaa;

    button {
        background-color: #F39200;
        color: black;
        font-size: 20px;
        font-weight: 600;
        border-radius: 10px;
        border-width: 1px;
        border-style:ridge;
        border-color: none;
        text-align: center;
        width: 20%;
        height: 5vh;
        box-shadow: 0 10px 10px #aaa;
        
    }
    

    .containerButton {
      display:flex;
      flex-direction:row;
      justify-content: center;   
    }

    .infoContainer {
        display:flex;
        flex-direction: column;
        font-weight: 600;
        justify-content: space-around;
        height: 400px;
        width:100%
        text-align:start;   
    }
    select {
        margin-left:10px;
        width:300px;
        
        text-align:center;
        font-weight:bold;
    }
    
`;

const ModalHeader = styled.div `    
    border-bottom: 1px solid #E8E8E8;
    display:flex;
    flex-direction: column;
    padding-bottom: 10px;

    .contentHeader{
      display:flex;
      justify-content:space-between;
    }

    
`;