import { useContext, useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { AuthContext } from "./Context/AuthContext";

const ProtectedRoutes = () => {

const navigate = useNavigate()

  const { state } = useContext(AuthContext);

  useEffect(() => {
    if (state.custRole !== "ROLE_ADMIN") {
      navigate('/');
    } else if (state.custRole === "" && state.jwtToken === "") {
      navigate('/');
    }
  }, [state.custRole, state.jwtToken, navigate]);

  if(state.custRole === "ROLE_ADMIN") {
    return <Outlet />
  }

  return navigate('/');
};

export default ProtectedRoutes;