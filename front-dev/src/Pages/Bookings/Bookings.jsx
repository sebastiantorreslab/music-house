import React from "react";
import "./Bookings.css";
import { useEffect } from "react";
import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../../src/Context/AuthContext";
import Banner from "../../Components/Layout/Banner/Banner"
import BookingCard from "../../Components/Common/BookingCard/BookingCard"

const Bookings = () => {

    const navigate = useNavigate();
    const [bookings, setBookings] = useState([]);
    const [pastBookings, setPastBookings] = useState([]);

    const splitItemsByDate = (items) =>{

    const compareDatesByDayMonthYear = (current, end) => {
        const day1 = current.getDate()-1;
        const month1 = current.getMonth();
        const year1 = current.getFullYear();
        
        const day2 = end.getDate();
        const month2 = end.getMonth();
        const year2 = end.getFullYear();
        
        if (year1 !== year2) {
            return year1 - year2;
        }
        
        if (month1 !== month2) {
            return month1 - month2;
        }
        
        return day1 - day2;
        }

    const currentDate = new Date();
    // Establecer la hora a las 0:0:0 (inicio del día)
    currentDate.setHours(0, 0, 0, 0); 
    
    const pastItems = [];
    const todayItems = [];
    const futureItems = [];

    items.forEach(item => {
        const endDate = new Date(item.endDate);
        // Establecer la hora a las 00:00:0 (inicio del día)
        endDate.setHours(0, 0, 0, 0); 
        //if (endDate.getTime() === currentDate.getTime()) {
        if (compareDatesByDayMonthYear (currentDate, endDate) == 0) {
            todayItems.push(item);
            //console.log("HOY: ", item);
        } else if (endDate < currentDate) {
            pastItems.push(item);
        } else {
            futureItems.push(item);
        }
    });
    return {
        pastItems,
        todayItems,
        futureItems
    };
    }

    const updateItems = (current, past) => {
        setBookings(current);
        setPastBookings(past);
    }

    const {state} = useContext(AuthContext);
    
    const fetchDataLogin = async () => {
        if(state.jwtToken !== "") {
            fetchDataBookings();
        }else{
            navigate("/login");
        }
    };

    const fetchDataBookings = async () => {
        try{
            const response = await fetch(
                //"http://18.191.243.189:8081/instruments/categories/list",
                //"http://localhost:8081/rental/list",
                "http://18.191.243.189:8081/rental/list",
                    {
                        method: "POST",
                        body: JSON.stringify(
                            {
                                custDni: state.custDni
                                
                            }
                        ),
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${state.jwtToken}`
                        }
                    }
                );            
            if (response.ok) {
                const data = await response.json();        
                //console.log("Info: " , data.content);
                fetchDataBookedInstruments(data.content);
            } else {
                console.error('Error en la solicitud:', response.status);
                setPastBookings([]);
                setBookings([]);
            }
        } catch (error) {
            console.error('Error al realizar la solicitud:', error);
            setPastBookings([]);
            setBookings([]);
        }
    }

    const fetchDataBookedInstruments = async (bookings) => {
        let newItems = [];
        // console.log("fetching booked instruments ...");
        try{
            bookings.forEach(async (item) => {
            const response = await fetch(
                //"http://localhost:8081/instruments/"+item.instTagNumber,
                "http://18.191.243.189:8081/instruments/"+item.instTagNumber,
                    {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${state.jwtToken}`
                        }
                    }
                );            
                if (response.ok) {
                    const data = await response.json();        
                    const newItem = {...item, ...data}
                    newItems.push(newItem);                    
                    const { pastItems, todayItems, futureItems } = splitItemsByDate(newItems);
                    //console.log("pasado: ", pastItems);
                    //console.log("hoy: ", todayItems);
                    //console.log("futuros: ", futureItems);
                    updateItems([...todayItems, ...futureItems], pastItems);
                } else {
                    console.error('Error en la solicitud:', response.status);
                }
            });

        } catch (error) {
            console.error('Error al realizar la solicitud:', error);
            setPastBookings([]);
            setBookings([]);
        }
    }



useEffect(() => {
    fetchDataLogin();
},[state.jwtToken]);


return (
    <>
    <Banner isFront={true} title={"Reservas"} subtitle="Reservas activas y pasadas"/>
        <div className="Bookings-container">
        <div className="Bookings-section">
            <div className="booking-section-title">Reservas activas</div>
                <div className="bookings-container">
                    { bookings.map( 
                        (booking) => (
                            <BookingCard key={booking.id} product={booking} bookingId={booking.id}/>
                        ))
                    }
                </div>
        </div>
        <div className="Bookings-section">
            <div className="booking-section-title">Historial de reservas</div>
                <div className="bookings-container">
                    { pastBookings.map( 
                        (booking) => (
                            <BookingCard key={booking.id} product={booking} previous={true} />
                        ))
                    }
                </div>
            </div>
        </div>
    </>
    );
};

export default Bookings;