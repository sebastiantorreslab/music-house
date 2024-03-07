import React, { useContext } from "react";
import ProductCard from "../../../../Components/Common/ProductCard/ProductCard";
import { Pagination, Stack } from "@mui/material";
import Banner from "../../../../Components/Layout/Banner/Banner";
import { useState, useEffect } from "react";
import { AuthContext } from "../../../../Context/AuthContext";
import axios from "axios";
import Swal from "sweetalert2";

const DeleteProduct = () => {
  const [products, setProducts] = useState([]);
  const [deletedProductId, setDeletedProductId] = useState(null);

  const { state } = useContext(AuthContext);

  useEffect(() => {
    const fetchDataProducts = axios.get(
      "http://18.191.243.189:8081/instruments/list?size=100"
    );
    fetchDataProducts
      .then((res) => {
        setProducts(res.data.content);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [deletedProductId]);

  const deleteProduct = (id) => {
    const deleteProductFetch = axios.delete(
      `http://18.191.243.189:8081/admin/instrument/${id}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${state.jwtToken}`,
        },
      }
    );
    deleteProductFetch
      .then((res) => {
        Swal.fire(res.data, "", "success");
        console.log(res);
        setDeletedProductId(id);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <Banner
        isFront={true}
        title="Eliminar productos"
        subtitle="Elimina los productos que ya no estan disponibles"
      />
      <div className="productsSection">
        <div className="products-container">
          {products.map((product, index) => {
            if (product.id === deletedProductId) {
              return null;
            }
            return (
              <ProductCard
                key={index}
                product={product}
                deleteable={deleteProduct}
              />
            );
          })}
        </div>
        <div className="pagination-container">
          <Stack spacing={5} className="pagination">
            <Pagination count={5} color="primary" />
          </Stack>
        </div>
      </div>
    </>
  );
};

export default DeleteProduct;
