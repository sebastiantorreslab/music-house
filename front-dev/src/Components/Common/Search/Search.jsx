import React from "react";
import "./Search.css"

const Search = () => {
  return (
    <div className="search">
      <input type="text" placeholder="¿Qué estás buscando?"/>
      <input id="SelectDate" type="date" placeholder="Fecha" />
      <button type='submit'>
        Buscar
      </button>
    </div>
  );
};

export default Search;
