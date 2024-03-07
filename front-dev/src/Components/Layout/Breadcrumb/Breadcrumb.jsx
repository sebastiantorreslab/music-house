import styles from "../Breadcrumb/Breadcrumb.module.css";
const Breadcrumb = () => {
    return (
        <>
            <div className={styles.Breadcrumb__container}>
                <a href=""><span>Admin / </span></a>
                
                <a href=""><span className={styles.current}>Panel de administrador</span></a>
            </div>
        </>
    )
}

  export default Breadcrumb;