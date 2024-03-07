import React from "react";
import Search from "../Components/Common/Search/Search";
import Categories from "../Components/Common/Categories/Categories";
import Favorites from "../Components/Common/Favorites/Favorites";
import Banner from "../Components/Layout/Banner/Banner";

const Home = () => {
  return (
    <div className="main-app-container">
      <Banner
        isFront={true}
        title="Dale ritmo a tu  potencial"
        subtitle="Especialistas en instrumentos"
      />
      <Categories />
      <Favorites />
    </div>
  );
};

export default Home;
