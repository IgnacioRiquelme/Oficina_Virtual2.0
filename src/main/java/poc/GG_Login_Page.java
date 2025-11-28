package poc;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import exceptions.confirmaProceso;
import pom.CC_Base;

public class GG_Login_Page  extends CC_Base{

    public GG_Login_Page(WebDriver driver) {super(driver);}
    
    

    /********* Loguin *********/

    /*datos login*/
    By txtUser = By.id("Rut");// RUT del usuario
    By txtPsw = By.id("Password");// Contraseña del usuario
    By btnIngresa = By.id("login-boton");// Botón ingresar en login


    /*menu inicio*/
    //By mnuInicio= By.xpath("//div[@id='main']/app-dashboard/app-menu/section/ul");
    By mnuInicio = By.id("menu-desplegable");
    

//////*********************************************************//////
                  ////// login correcto //////
//////*********************************************************//////

    public void loginCorrecto(String user, String pasword, String var, String PATH_CAPTURA) {

        crearCarpeta2(PATH_CAPTURA);
        insertarDatos(user, txtUser); insertarDatos(pasword, txtPsw); 
        pausaPorElementoClikeable(btnIngresa);
        /*captura pantalla */
        capturaPantallaCompletaF(var, "1_Login");
        pausaPorElementoClikeable(btnIngresa); click(btnIngresa); 
        implicitWait(3000); pausaPorElementoVisible(mnuInicio);
        /*captura pantalla */
        pausaPorElementoVisible(mnuInicio);
        //focusElemento(mnuInicio);
        capturaPantallaCompletaF(var, "2_Login");
        flujoConLogin(btnIngresa);//Confirma Login para reporte

    }




    /////////////////////////////////////////////////
    /////////////////////////////////////////////////
        /////// Metodos reutilizables///////
    /////////////////////////////////////////////////
    /////////////////////////////////////////////////



    public void flujoConLogin(By locator) {
        try {
            if (elementoVisible(mnuInicio)) {
                System.out.println("Se generó el login correctamente");
            }else {
                throw new confirmaProceso("No se encontró el elemento del menú de inicio");
            }
        }catch (Exception e){
            throw new confirmaProceso("Error en flujo de  Confirmacion de Login: " + e.getMessage());
        }
    }
    
    public void crearCarpeta1(String PATH_DESCARGA) {
        File carpeta = new File(PATH_DESCARGA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    public void crearCarpeta2(String PATH_CAPTURA) {
        File carpeta = new File(PATH_CAPTURA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }




}
