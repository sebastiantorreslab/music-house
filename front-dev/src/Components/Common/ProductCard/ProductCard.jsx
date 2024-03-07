/*import { Button, Card, CardActions, CardContent, CardMedia } from "@mui/material"*/
/*import "../../Common/Favorites.css"*/
import "./ProductCard.css"

const ProductCard = ({product, index, deleteable, linkeable}) => {
    return (
      <div key={index} className="main-container-card" onClick={ linkeable && (() => linkeable(product.instTagNumber)) }> 
        <div className="card-image-container">
            <img src={product.instPhoto} alt="" />
        </div>            
        <div className="card-content-container">
            <div className="card-content__info-section">
            {/*product.category && <p className="card-content__type"> {product.category} </p>*/}
            {product.instName && <p className="card-content__name"> {product.instName} </p>}
            <p className="card-content__brand_model"> 
              {product.instBrand  && <span>{product.instBrand }</span>}
              {product.year && <span> | </span>}
              {product.year && <span>{product.year}</span> } 
            </p>
            </div>
            <div className="card-content__money-section">
                    {
                    product.discount!=0 &&
                    <p className="card-content__old_price">
                        {product.oldPrice && <span>$ {product.oldPrice} USD/día</span>}
                    </p>                        
                    }
                <p className="card-content__price">
                    {product.instPrice  && <span id='price'>${product.instPrice }</span>}
                    {product.instPrice  && <span id='currency_rate'> USD/día </span>}
                    {
                        product.discount && product.discount!=0 &&
                        <span id='perc_discount'>
                            (-{product.discount}%)
                        </span>
                    }
                </p>
                {
                  product.instIsActive  === true  ? <h4 className="in-stock">Disponible</h4> : <h4 className="out-of-stock">Sin stock</h4>
                }
            </div>
        </div>
        {deleteable && <button className="Avatar-container-button" onClick={ () => deleteable(product.instTagNumber) }> Eliminar producto </button>}
      </div>        
    )
  }

  export default ProductCard;