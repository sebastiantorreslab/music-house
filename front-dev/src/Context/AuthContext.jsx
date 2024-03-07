import React, { createContext, useReducer } from 'react';

export const AuthContext = createContext();

const initialState = {
    custDni: "",
    custName: "",
    custLastName: "",
    custRole:"",
    jwtToken:""
}

const authReducer = (state, action) => {
    switch (action.type) {
        case "LOGIN":
            const login = {...state,
                custDni: action.payload.custDni,
                custName: action.payload.custName,
                custLastName: action.payload.custLastName,
                custRole:action.payload.custRole,               
                jwtToken: action.payload.jwtToken
            }
            return login
        case "LOGOUT":
            return {...state, custRole:"", jwtToken: ""}
        default:
            return state;
    }
}

const AuthContextProvider = ({children}) => {

    const [state, dispatch] = useReducer(authReducer, initialState)


    return (
        <AuthContext.Provider value={{state, dispatch}}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthContextProvider;
