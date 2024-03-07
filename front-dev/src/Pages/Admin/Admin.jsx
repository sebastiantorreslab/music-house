import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Admin.module.css";

import Banner from "../../Components/Layout/Banner/Banner";

import showFolder from "../../assets/folder.png";
import addFolder from "../../assets/folder-add.png";
import removeFolder from "../../assets/folder-minus.png";

const Admin = () => {
  const navigate = useNavigate();

  return (
    <>
      <Banner title="Administrador" subtitle="Gestiona el catálogo completo" />

      <h2 className={styles.subtitle}>Panel del administrador</h2>

      <nav className={styles.nav__container}>
        <div className={styles.nav__container__item}>
          <div className={styles.navitem__title_container}>
            <img src={addFolder} alt="folder icon" />
            <div className={styles.navitem__title_info}>
              <h3>Agregar instrumentos al catálogo</h3>
              <p>Permite la adición de ítems de catálogo general</p>
            </div>
          </div>
          <button
            className={styles.admin__menu_button}
            onClick={() => navigate("/register-product")}
          >
            Entrar
          </button>
        </div>

        <div className={styles.nav__container__item}>
          <div className={styles.navitem__title_container}>
            <img src={addFolder} alt="folder icon" />
            <div className={styles.navitem__title_info}>
              <h3>Agregar categorías al catálogo</h3>
              <p>Permite la adición de categorías</p>
            </div>
          </div>
          <button
            className={styles.admin__menu_button}
            onClick={() => navigate("/register-category")}
          >
            Entrar
          </button>
        </div>

        <div className={styles.nav__container__item}>
          <div className={styles.navitem__title_container}>
            <img src={removeFolder} alt="folder icon" />
            <div className={styles.navitem__title_info}>
              <h3>Eliminar instrumentos al catálogo</h3>
              <p>Permite la eliminación de ítems del catálogo general</p>
            </div>
          </div>
          <button
            className={styles.admin__menu_button}
            onClick={() => navigate("/remove-product")}
          >
            Entrar
          </button>
        </div>

        <div className={styles.nav__container__item}>
          <div className={styles.navitem__title_container}>
            <img src={removeFolder} alt="folder icon" />
            <div className={styles.navitem__title_info}>
              <h3>Eliminar categorías al catálogo</h3>
              <p>
                Permite la eliminación de las categorías del catálogo general
              </p>
            </div>
          </div>
          <button
            className={styles.admin__menu_button}
            onClick={() => navigate("/remove-category")}
          >
            Entrar
          </button>
        </div>

        <div className={styles.nav__container__item}>
          <div className={styles.navitem__title_container}>
            <img src={showFolder} alt="folder icon" />
            <div className={styles.navitem__title_info}>
              <h3>Administrar usuarios</h3>
              <p>Permite eliminar y modificar los permisos de cada usuario</p>
            </div>
          </div>
          <button
            className={styles.admin__menu_button}
            onClick={() => navigate("/manage-users")}
          >
            Entrar
          </button>
        </div>
      </nav>
    </>
  );
};

export default Admin;
