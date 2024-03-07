import FacebookIcon from '@mui/icons-material/Facebook';
import TwitterIcon from '@mui/icons-material/Twitter';
import InstagramIcon from '@mui/icons-material/Instagram';
import './Footer.css'

const Footer = () => {
    return (
      <footer className="footer">
          <p>© 2023 
            <a className='Title-bold'> M </a><a className='Title-semibold'>u </a><a className='Title-Medium'>s </a><a className='Title-regular'>i </a><a className='Title-light'>c</a>
            <a className='Title-regular'>House</a>
          </p>
          <div className="social-icon-container">
            <a href=""><InstagramIcon className="social-icon" /></a>
            <a href=""><FacebookIcon className="social-icon" /></a>
            <a href=""><TwitterIcon className="social-icon"/></a>
          </div>
      </footer>
    )
  }

  export default Footer