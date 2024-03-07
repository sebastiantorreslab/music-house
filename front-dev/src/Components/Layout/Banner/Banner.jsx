import { useState, useEffect } from "react";
import Search from "../../Common/Search/Search";
import styles from "../Banner/Banner.module.css";
import { useContext } from "react";
import { AuthContext } from "../../../Context/AuthContext";

const Banner = (props) => {
  const [user, setUser] = useState(false);

  const {state } =   useContext(AuthContext)

  useEffect(() => {
    if(state.jwtToken !== "") {
      setUser(true)
    }
    
  }, [state.jwtToken]);


  return (
    <>
      <div 
        className={
          props.isFront
            ? styles.banner_container_front
            : styles.banner_container_back
        }
      >
        
        <div className={styles.banner_container_info}>
          <h1>{props.title}</h1>
          <h3>{props.subtitle}</h3>
        </div> 
        {
          state.custRole === "ROLE_ADMIN" ? (null) : (<Search />)
        }
        
      </div>
    </>
  );
};

export default Banner;
