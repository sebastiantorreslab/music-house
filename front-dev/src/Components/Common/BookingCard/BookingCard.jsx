import "./BookingCard.css";

const BookingCard = ({
  product,
  index,
  deleteable,
  linkeable,
  previous,
  bookingId,
}) => {
  //console.log("producto: " , product)

  const calculateDays = (startDate, endDate) => {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const oneDay = 24 * 60 * 60 * 1000; // Milliseconds in a day
    const diffInDays = Math.round(Math.abs((end - start) / oneDay));
    return diffInDays;
  };

  return (
    <div
      key={index}
      className={
        previous
          ? "main-container-booking-card past"
          : "main-container-booking-card current"
      }
      onClick={linkeable && (() => linkeable(product.instTagNumber))}
    >
      <div className="booking-card-image-container">
        <img src={product.instPhoto} alt="" />
      </div>

      <div className="booking-card-content-container">
        <div className="booking-card-content__info-section">
          {product.instName && (
            <p className="booking-card-content__name"> {product.instName} </p>
          )}
          <p className="booking-card-content__brand_model">
            ID Reserva : {bookingId}
          </p>
          <p className="booking-card-content__brand_model">
            {product.instBrand && <span>{product.instBrand}</span>}
          </p>

          <p className="booking-card-content__dates">
            <span>{`${product.startDate} | ${product.endDate}`}</span>
          </p>
        </div>

        <div className="booking-card-content__money-section">
          <p className="booking-card-content__price">
            {product.instPrice && (
              <span id="price"> {product.rentalCharge}</span>
            )}
            {product.instPrice && <span id="currency_rate"> USD </span>}
          </p>

          <p className="booking-card-content__price">
            <span id="price_calculation">
              {" "}
              {calculateDays(product.startDate, product.endDate)} días x{" "}
              {product.instPrice} USD/día{" "}
            </span>
          </p>
        </div>
      </div>

      {/*deleteable && <button onClick={ () => deleteable(product.instTagNumber) }> eliminar </button>*/}
    </div>
  );
};

export default BookingCard;
