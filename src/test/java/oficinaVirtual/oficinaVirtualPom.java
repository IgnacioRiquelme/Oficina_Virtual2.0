package oficinaVirtual;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import exceptions.confirmaProceso;
import poc.GG_Login_Page;
import poc.GG_Principal_Page;
import pom.CC_Base;
import pom.CC_Variables_Globales;
import pom.ExtentReportManager;

public class oficinaVirtualPom {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<CC_Base> base = new ThreadLocal<>();
    private static ThreadLocal<GG_Login_Page> login = new ThreadLocal<>();
    private static ThreadLocal<GG_Principal_Page> principal = new ThreadLocal<>();

    ExtentReports extent;
    ExtentTest test;

    private void ejecutarTest(String testName, Runnable action) {
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest(testName);
        try {
            action.run();
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Error: " + e.getMessage());
            Assert.fail("Error: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
        }
    }

    //clases involucradas


    String user, rutAsegurado, pasword, //datos login
           PATH_DESCARGA, PATH_CAPTURA, //ruta carpetas
           periodo, quincena, rutContratante, rutCorredor, corredor; //datos comisiones

            


    @BeforeClass

    public void beforeClass() {
        PATH_DESCARGA = pom.CC_Variables_Globales.PATH_DESCARGA;
        PATH_CAPTURA = pom.CC_Variables_Globales.PATH_CAPTURA;
        extent = ExtentReportManager.getInstance();
        driver.set(CC_Base.setupChrome(PATH_DESCARGA, PATH_CAPTURA));
        base.set(new CC_Base(driver.get()));
        login.set(new GG_Login_Page(driver.get()));
        principal.set(new GG_Principal_Page(driver.get()));

        base.get().llamarUrl(CC_Variables_Globales.OFICINA_VIRTUAL);
        driver.get().manage().window().maximize();

        ///////////////////////////// Llamar json/////////////////////////////////////////////

        /*datos usuarios*/
        user = base.get().obtenerJason("Principal", "datos1", "user"); //datos login
        pasword = base.get().obtenerJason("Principal", "datos1", "pasword");//datos login
        rutAsegurado = base.get().obtenerJason("Principal", "datos1", "rutAsegurado");//datos login
        periodo = base.get().obtenerJason("Principal", "datos2", "periodo");//datos comisiones
        quincena = base.get().obtenerJason("Principal", "datos2", "quincena");//datos comisiones
        corredor = base.get().obtenerJason("Principal", "datos2", "corredor");//datos comisiones
        rutCorredor = base.get().obtenerJason("Principal", "datos2", "rutCorredor");//datos comisiones
        rutContratante = base.get().obtenerJason("Principal", "datos2", "rutContratante");//datos comisiones

        // Hacer login una vez
        login.get().loginCorrecto(user, pasword, "LoginInicial", PATH_CAPTURA);
    }
    @AfterClass
    public void afterClass(){
        if (driver.get() != null) {
            base.get().driverQuit();
            driver.remove();
            base.remove();
            login.remove();
        }
        extent.flush(); // Escribe el reporte al final de la clase
    }

    /* t0001
     * paso 1: Login correcto
     * paso 2: capturas de pantalla
     */

    /* 
    @Test
    public void t0001(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0001");
        try {
            login.get().loginCorrecto(user, pasword, "t0001", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }*/

    /* t0002
     * paso 1: Login correcto
     * paso 2: Busqueda rapida
     * paso 3: descarga de poliza
     * paso 4: capturas de pantalla
     */

    @Test
    public void t0002(){
        ejecutarTest("t0002", () -> {
            principal.get().descargaPoliza(user, pasword, "t0002", PATH_CAPTURA, rutAsegurado);
            Assert.assertTrue(true, "No se encontró el mensaje de éxito");
        });
    }

    /* t0003
     * paso 1: Login correcto
     * paso 2: Busqueda rapida
     * paso 3: Consultar Coberturas
     * paso 4: capturas de pantalla
     */

     @Test
    public void t0003(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0003");
        try {
            principal.get().revisionCoberturas(user, pasword, "t0003", PATH_CAPTURA, rutAsegurado);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    /* t0004
     * paso 1: Login correcto
     * paso 2: Busqueda rapida
     * paso 3: Validar Menu Servicios de Informacion
     * paso 4: capturas de pantalla
     */

     @Test
    public void t0004(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0004");
        try {
            principal.get().validarMenuServiciosInformacion(user, pasword, "t0004", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    /* t0005
     * paso 1: Login correcto
     * paso 2: Validar Menu Servicios de Informacion desde icono acceso rapido
     * paso 3: capturas de pantalla
     */

     @Test
    public void t0005(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0005");
        try {
            principal.get().validarMenuServiciosInformacionIcono(user, pasword, "t0005", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    /* t0006
     * paso 1: Login correcto
     * paso 2: Validar Menu Noticias
     * paso 3: capturas de pantalla
     */

     @Test
    public void t0006(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0006");
        try {
            principal.get().validarMenuNoticias(user, pasword, "t0006", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    /* t0007
     * paso 1: Login correcto
     * paso 2: Validar Menu Noticias Icono acceso rapido
     * paso 3: capturas de pantalla
     */

     

     @Test
    public void t0007(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0007");
        try {
            principal.get().validarMenuNoticiasIcono(user, pasword, "t0007", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

     /* t0008
     * paso 1: Login correcto
     * paso 2: Validar Menu Capsulas Ayuda 
     * paso 3: capturas de pantalla
     */

     @Test
    public void t0008(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0008");
        try {
            principal.get().validarMenuCapsulasAyuda(user, pasword, "t0008", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    /* t0009
     * paso 1: Login correcto
     * paso 2: Validar Menu Ayuda en Linea Icono acceso rapido
     * paso 3: capturas de pantalla
     */

     @Test
    public void t0009(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0009");
        try {
            principal.get().validarMenuCapsulasAyudaIcono(user, pasword, "t0009", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    
    

    /* t0010
     * paso 1: Login correcto
     * paso 2: Validar Menu Preguntas Frecuentes Icono acceso rapido
     * paso 3: capturas de pantalla
     */

     @Test
    public void t0010(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0010");
        try {
            principal.get().validarMenuPreguntasFrecuentesIcono(user, pasword, "t0010", PATH_CAPTURA);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    /* t0011
     * paso 1: Login correcto
     * paso 2: Validar Menu Comisiones
     * paso 3: capturas de pantalla
     */

     @Test
    public void t0011(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0011");
        try {
            principal.get().validarComisiones(user, pasword, "t0011", PATH_CAPTURA, periodo, quincena, corredor, rutCorredor);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }

    /* t0011
     * paso 1: Login correcto
     * paso 2: Validar Menu Busqueda Rapida, Pestana Vida y Salud
     * paso 3: capturas de pantalla
     */

     @Test
    public void t0012(){
        com.aventstack.extentreports.ExtentTest test = ExtentReportManager.getInstance().createTest("t0012");
        try {
            principal.get().validarPestanaVidaSalud(user, pasword, "t0012", PATH_CAPTURA, rutContratante);
            boolean condicionExito = true;
            Assert.assertTrue(condicionExito, "No se encontró el mensaje de éxito");
            test.log(Status.PASS, "Prueba ejecutada exitosamente");
        } catch (confirmaProceso e) {
            test.log(Status.FAIL, "Login no se genero: " + e.getMessage());
            Assert.fail("Login no se genero: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Error durante la ejecución: " + e.getMessage());
            test.log(Status.FAIL, e);
            Assert.fail("Test fallido: " + e.getMessage());
            System.out.println("Error en el test: " + e.getMessage());
            //base.get().driverQuit();
        }
    }
    

}
