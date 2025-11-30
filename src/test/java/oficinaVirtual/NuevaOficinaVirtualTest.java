package oficinaVirtual;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Arrays;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import exceptions.confirmaProceso;
import pom.CC_Base;
import pom.ExtentReportManager;

import static pom.CC_Variables_Globales.*;

public class NuevaOficinaVirtualTest {

    private WebDriver driver;
    private CC_Base base;
    private ExtentReports extent;
    private ExtentTest test;

    private String user, pasword, rutAsegurado, PATH_DESCARGA, PATH_CAPTURA, ventanaPrincipalOriginal;

    @BeforeClass
    public void setup() {
        PATH_DESCARGA = pom.CC_Variables_Globales.PATH_DESCARGA;
        PATH_CAPTURA = pom.CC_Variables_Globales.PATH_CAPTURA;
        extent = ExtentReportManager.getInstance();

        // Setup Chrome con preferencias
        driver = CC_Base.setupChrome(PATH_DESCARGA, PATH_CAPTURA);
        base = new CC_Base(driver);

        // Cargar datos del JSON
        user = base.obtenerJason("Principal", "datos1", "user");
        pasword = base.obtenerJason("Principal", "datos1", "pasword");
        rutAsegurado = base.obtenerJason("Principal", "datos1", "rutAsegurado");

        // Ir a la URL
        base.llamarUrl(OFICINA_VIRTUAL);
        driver.manage().window().maximize();
        ventanaPrincipalOriginal = driver.getWindowHandle();
    }

    @Test
    public void t001_Login_Busqueda_Rapida() {
        test = ExtentReportManager.getInstance().createTest("t001_Login_Busqueda_Rapida");
        try {
            // Paso 1: Insertar credenciales y hacer login
            System.out.println(">>>>>> Paso 1: Insertar credenciales y hacer login");
            test.log(Status.INFO, "Paso 1: Insertar credenciales y hacer login");
            base.insertarDatos(user, By.id("Rut"));
            base.insertarDatos(pasword, By.id("Password"));

            // Pausa para captura
            base.pausaFijaSeg(2);
            base.click(By.id("login-boton"));
            System.out.println(">>>>>> Login realizado exitosamente");
            test.log(Status.INFO, "Login realizado exitosamente");

            // Paso 2: Abrir menú desplegable
            System.out.println(">>>>>> Paso 2: Abrir menú desplegable");
            test.log(Status.INFO, "Paso 2: Abrir menú desplegable");
            base.pausaPorElementoVisible(By.id("menu-desplegable"));
            base.click(By.id("menu-desplegable"));

            // Pausa para captura
            base.pausaFijaSeg(3);
            // Captura de pantalla después de abrir menú
            base.capturaPantallaCompletaF("Menu", "Menu_Desplegado");
            System.out.println(">>>>>> Menú desplegado abierto");
            test.log(Status.INFO, "Menú desplegado abierto");

            // Paso 3: Navegar a Búsqueda Rápida
            System.out.println(">>>>>> Paso 3: Navegar a Búsqueda Rápida");
            test.log(Status.INFO, "Paso 3: Navegar a Búsqueda Rápida");
            base.pausaPorElementoVisible(By.id("menu-búsquedarápida"));
            base.click(By.id("menu-búsquedarápida"));

            // Pausa para captura
            base.pausaFijaSeg(2);
            // Captura de pantalla de la página de Búsqueda Rápida
            base.capturaPantallaCompletaF("BusquedaRapida", "Pantalla_Busqueda_Rapida");
            System.out.println(">>>>>> Página de Búsqueda Rápida cargada");
            test.log(Status.INFO, "Página de Búsqueda Rápida cargada");

            // Paso 4: Ingresar RUT a buscar y elegir Tipo de vigencia
            System.out.println(">>>>>> Paso 4: Ingresar RUT a buscar y elegir Tipo de vigencia");
            test.log(Status.INFO, "Paso 4: Ingresar RUT a buscar y elegir Tipo de vigencia");
            // Esperar que cargue la página de búsqueda
            base.pausaPorElementoVisible(By.xpath("//div[@id='app-buscador']/section/div[1]/div/header/section/div[1]/p/input"));

            // Insertar RUT
            base.insertarDatos(rutAsegurado, By.xpath("//div[@id='app-buscador']/section/div[1]/div/header/section/div[1]/p/input"));
            System.out.println(">>>>>> RUT ingresado: " + rutAsegurado);
            test.log(Status.INFO, "RUT ingresado: " + rutAsegurado);

            // Seleccionar tipo de vigencia "SI" (Vigente)
            base.selecSelect(By.xpath("//div[@id='app-buscador']/section/div[1]/div/header/section/div[1]/p[2]/select"), "SI");
            System.out.println(">>>>>> Tipo de vigencia seleccionado: Vigente");
            test.log(Status.INFO, "Tipo de vigencia seleccionado: Vigente");

            System.out.println(">>>>>> Datos de búsqueda completados");
            test.log(Status.INFO, "Datos de búsqueda completados");

            // Paso 5: Hacer click en buscar (robusto: reintentos + fallback JS)
            System.out.println(">>>>>> Paso 5: Hacer click en buscar");
            test.log(Status.INFO, "Paso 5: Hacer click en buscar");
            By buscarInicial = By.id("btBusqueda");
            boolean clickedInicial = base.clickComoIcono(buscarInicial);
            if (!clickedInicial) {
                try {
                    // última oportunidad: JS click directo
                    WebElement buscarEl = driver.findElement(buscarInicial);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buscarEl);
                    System.out.println(">>>>>> Fallback JS click en Buscar (inicial) ejecutado");
                } catch (Exception e) {
                    System.out.println(">>>>>> Error en Servicios de Información, Comisiones o Vida y Salud: " + e.getMessage());
                    try {
                        // Capturar evidencia visual del fallo
                        base.capturaPantallaCompletaF("Error_Flujo", "Servicios_Comisiones_Vida_Error");
                    } catch (Exception exCap) {
                        System.out.println("No se pudo capturar pantalla de error: " + exCap.getMessage());
                    }
                    // Registrar fallo en el reporte y relanzar para que la prueba falle correctamente
                    test.log(Status.FAIL, "Error en Servicios/Comisiones/Vida y Salud: " + e.getMessage());
                    throw e;
                }
            }

            // Pausa para captura de resultados
            base.pausaFijaSeg(2);
            // Captura: Resultados de búsqueda
            base.capturaPantallaCompletaF("BusquedaRapida", "Resultados_Busqueda_Rapida");
            System.out.println(">>>>>> Resultados de búsqueda mostrados");
            test.log(Status.INFO, "Resultados de búsqueda mostrados");

            // Paso 6: Explorar grilla - click en lupa
            System.out.println(">>>>>> Paso 6: Explorar grilla - click en lupa");
            test.log(Status.INFO, "Paso 6: Explorar grilla - click en lupa");
            base.pausaPorElementoVisible(By.xpath("//tbody/tr[1]/td[6]/a/i"));
            base.click(By.xpath("//tbody/tr[1]/td[6]/a/i"));

            // Pausa para carga de modal
            base.pausaFijaSeg(5);
            // Captura: Visor de póliza
            base.capturaPantallaCompletaF("Visor", "Visor_Poliza");
            System.out.println(">>>>>> Visor de póliza abierto");
            test.log(Status.INFO, "Visor de póliza abierto");

            // Paso 7: Descargar póliza
            System.out.println(">>>>>> Paso 7: Descargar póliza");
            test.log(Status.INFO, "Paso 7: Descargar póliza");
            String ventanaPrincipal = driver.getWindowHandle();
            base.pausaPorElementoVisible(By.xpath("//div[@class='modal w-1300 fade dinamico tabs show']/div/div/article[1]/div/div/a[2]"));
            base.click(By.xpath("//div[@class='modal w-1300 fade dinamico tabs show']/div/div/article[1]/div/div/a[2]"));

            // Cambiar a nueva pestaña
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equals(ventanaPrincipal)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Pausa para carga del visor
            base.pausaFijaSeg(3);
            // Captura: Visor de póliza descargada
            base.capturaPantallaCompletaF("Visor", "Visor_Poliza_Descargada");
            System.out.println(">>>>>> Visor de póliza descargada capturado");
            test.log(Status.INFO, "Visor de póliza descargada capturado");

            // Cerrar pestaña y volver
            driver.close();
            driver.switchTo().window(ventanaPrincipal);
            System.out.println(">>>>>> Pestaña cerrada, retornado a principal");
            test.log(Status.INFO, "Pestaña cerrada, retornado a principal");

            // Cerrar modal del visor si quedó abierto
            if (base.elementoVisible(By.cssSelector("a.pestanas-cerrar"))) {
                WebElement closeButton = driver.findElement(By.cssSelector("a.pestanas-cerrar"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
                base.pausaPorElemento(By.cssSelector(".modal"));
                base.pausaFijaSeg(2);
            }

            // Paso 8: Acceder a Servicios de información por menú lateral y acceso directo
            System.out.println(">>>>>> Paso 8: Acceder a Servicios de información por menú lateral y acceso directo");
            test.log(Status.INFO, "Paso 8: Acceder a Servicios de información por menú lateral y acceso directo");

            // Abrir menú desplegable
            base.click(By.id("menu-desplegable"));
            base.pausaFijaSeg(2); // Esperar animación
            base.pausaPorElementoVisible(By.id("menu-serviciosdeinformación"));

            // Hover sobre icono en menú lateral
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.id("menu-serviciosdeinformación"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover en menú lateral
            base.capturaPantallaCompletaF("ServiciosInformacion", "Hover_Menu_Lateral");
            System.out.println(">>>>>> Hover en menú lateral capturado");
            test.log(Status.INFO, "Hover en menú lateral capturado");

            // Click en icono de menú lateral
            base.click(By.id("menu-serviciosdeinformación"));
            base.pausaFijaSeg(2);
            // Captura: Servicios de información desde menú lateral
            base.capturaPantallaCompletaF("ServiciosInformacion", "Desde_Menu_Lateral");
            System.out.println(">>>>>> Servicios de información desde menú lateral capturado");
            test.log(Status.INFO, "Servicios de información desde menú lateral capturado");

            // Cerrar modal
            base.pausaPorElementoVisible(By.cssSelector("a.pestanas-cerrar"));
            WebElement closeButton = driver.findElement(By.cssSelector("a.pestanas-cerrar"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
            base.pausaPorElemento(By.cssSelector(".modal"));
            base.pausaFijaSeg(2);
            System.out.println(">>>>>> Modal cerrado, en página principal");
            test.log(Status.INFO, "Modal cerrado, en página principal");

            // Hover sobre icono de acceso directo
            base.pausaPorElementoVisible(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[2]"));
            actions.moveToElement(driver.findElement(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[2]"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover sobre acceso directo
            base.capturaPantallaCompletaF("ServiciosInformacion", "Hover_Acceso_Directo");
            System.out.println(">>>>>> Hover sobre acceso directo capturado");
            test.log(Status.INFO, "Hover sobre acceso directo capturado");

            // Click en icono de acceso directo
            base.click(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[2]"));
            base.pausaFijaSeg(2);
            // Captura: Servicios de información desde acceso directo
            base.capturaPantallaCompletaF("ServiciosInformacion", "Desde_Acceso_Directo");
            System.out.println(">>>>>> Servicios de información desde acceso directo capturado");
            test.log(Status.INFO, "Servicios de información desde acceso directo capturado");

            // Cerrar modal de Servicios de Información
            base.pausaPorElementoVisible(By.cssSelector("a.pestanas-cerrar"));
            WebElement closeButton2 = driver.findElement(By.cssSelector("a.pestanas-cerrar"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton2);
            base.pausaPorElemento(By.cssSelector(".modal"));
            base.pausaFijaSeg(2);
            System.out.println(">>>>>> Modal de Servicios de Información cerrado");
            test.log(Status.INFO, "Modal de Servicios de Información cerrado");

            // Paso 9: Acceder a Noticias por menú lateral y acceso directo
            System.out.println(">>>>>> Paso 9: Acceder a Noticias por menú lateral y acceso directo");
            test.log(Status.INFO, "Paso 9: Acceder a Noticias por menú lateral y acceso directo");

            // Abrir menú desplegable
            base.click(By.id("menu-desplegable"));
            base.pausaFijaSeg(2); // Esperar animación
            base.pausaPorElementoVisible(By.id("menu-noticias"));

            // Hover sobre icono de noticias en menú lateral
            actions.moveToElement(driver.findElement(By.id("menu-noticias"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover en menú lateral para noticias
            base.capturaPantallaCompletaF("Noticias", "Hover_Menu_Lateral");
            System.out.println(">>>>>> Hover en menú lateral para noticias capturado");
            test.log(Status.INFO, "Hover en menú lateral para noticias capturado");

            // Click en icono de noticias en menú lateral
            base.click(By.id("menu-noticias"));
            base.pausaFijaSeg(2);
                // Guardar identificador de ventana principal antes de abrir la secundaria
                String ventanaPrincipalAccesoDirecto = driver.getWindowHandle();
                Set<String> handlesAccesoDirecto = driver.getWindowHandles();
                for (String handle : handlesAccesoDirecto) {
                    if (!handle.equals(ventanaPrincipalAccesoDirecto)) {
                        driver.switchTo().window(handle);
                        break;
                    }
                }
            // Pausa para carga de página
            base.pausaFijaSeg(3);
            // Captura: Noticias desde menú lateral
            base.capturaPantallaCompletaF("Noticias", "Desde_Menu_Lateral");
            System.out.println(">>>>>> Noticias desde menú lateral capturado");
            test.log(Status.INFO, "Noticias desde menú lateral capturado");

            // Cerrar ventana de noticias y volver
            driver.close();
            // Verificar que la ventana principal sigue abierta antes de hacer el switch
            Set<String> handlesDespuesCerrarAccesoDirecto = driver.getWindowHandles();
            if (handlesDespuesCerrarAccesoDirecto.contains(ventanaPrincipalAccesoDirecto)) {
                driver.switchTo().window(ventanaPrincipalAccesoDirecto);
                System.out.println(">>>>>> Ventana de noticias cerrada, retornado a principal");
                test.log(Status.INFO, "Ventana de noticias cerrada, retornado a principal");
            } else {
                System.out.println(">>>>>> La ventana principal no está disponible tras cerrar la secundaria");
                test.log(Status.WARNING, "La ventana principal no está disponible tras cerrar la secundaria");
            }

            // Cerrar menú desplegable si está abierto
            if (base.elementoVisible(By.id("menu-desplegable"))) {
                base.click(By.id("menu-desplegable"));
                base.pausaFijaSeg(1);
            }

            // Hover sobre icono de acceso directo para noticias
            base.pausaPorElementoVisible(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Noticias']"));
            actions.moveToElement(driver.findElement(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Noticias']"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover sobre acceso directo para noticias
            base.capturaPantallaCompletaF("Noticias", "Hover_Acceso_Directo");
            System.out.println(">>>>>> Hover sobre acceso directo para noticias capturado");
            test.log(Status.INFO, "Hover sobre acceso directo para noticias capturado");

            // Click en icono de acceso directo para noticias
            base.click(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Noticias']"));
            // Cambiar a nueva ventana de noticias
            String ventanaPrincipal2 = driver.getWindowHandle();
            Set<String> handles2 = driver.getWindowHandles();
            boolean switched2 = false;
            for (String handle : handles2) {
                if (!handle.equals(ventanaPrincipal2)) {
                    driver.switchTo().window(handle);
                    switched2 = true;
                    break;
                }
            }
            // Pausa para carga de página
            base.pausaFijaSeg(5);
            // Captura: Noticias desde acceso directo
            base.capturaPantallaCompletaF("Noticias", "Desde_Acceso_Directo");
            System.out.println(">>>>>> Noticias desde acceso directo capturado");
            test.log(Status.INFO, "Noticias desde acceso directo capturado");

            // Retornar a pestaña principal
            Set<String> allHandles = driver.getWindowHandles();
            String mainHandle = allHandles.iterator().next();
            driver.switchTo().window(mainHandle);
            System.out.println(">>>>>> Retornado a pestaña principal");
            test.log(Status.INFO, "Retornado a pestaña principal");

            // Antes del Paso 10: detectar y cerrar ventanas secundarias (ej. Noticias)
            // Esto captura la última pantalla de cada ventana secundaria y la cierra,
            // asegurando que no queden pestañas abiertas antes de entrar a Cápsulas de ayuda.
            try {
                Set<String> handlesAntesCapsulas = driver.getWindowHandles();
                for (String h : handlesAntesCapsulas) {
                    if (!h.equals(ventanaPrincipalOriginal)) {
                        try {
                            driver.switchTo().window(h);
                            base.pausaFijaSeg(1);
                            // Captura la última pantalla de la ventana secundaria (Noticias)
                            base.capturaPantallaCompletaF("Noticias", "Ultima_antes_cerrar");
                            // Cerrar la ventana secundaria
                            driver.close();
                            System.out.println(">>>>>> Ventana secundaria cerrada: " + h);
                        } catch (Exception ex) {
                            System.out.println(">>>>>> No se pudo cerrar ventana secundaria " + h + ": " + ex.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(">>>>>> Error detectando ventanas antes de Capsulas: " + e.getMessage());
            }

            // Asegurar que volvemos a la ventana principal original
            try {
                driver.switchTo().window(ventanaPrincipalOriginal);
            } catch (Exception ex) {
                System.out.println(">>>>>> No se pudo retornar a la ventana principal original: " + ex.getMessage());
            }

            // Paso 10: Acceder a Cápsulas de ayuda por menú lateral
            System.out.println(">>>>>> Paso 10: Acceder a Cápsulas de ayuda por menú lateral");
            test.log(Status.INFO, "Paso 10: Acceder a Cápsulas de ayuda por menú lateral");

            // Abrir menú desplegable
            actions.moveToElement(driver.findElement(By.id("menu-desplegable"))).perform();
            base.click(By.id("menu-desplegable"));
            base.pausaFijaSeg(2);

            // Hover sobre icono de Ayuda en Línea en menú lateral
            actions.moveToElement(driver.findElement(By.id("menu-ayudaenlínea"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover en menú lateral para Ayuda en Línea
            base.capturaPantallaCompletaF("AyudaEnLinea", "Hover_Menu_Lateral");
            System.out.println(">>>>>> Hover en menú lateral para Ayuda en Línea capturado");
            test.log(Status.INFO, "Hover en menú lateral para Ayuda en Línea capturado");

            // Click en icono de Ayuda en Línea en menú lateral
            base.click(By.id("menu-ayudaenlínea"));
            base.pausaFijaSeg(5);

            // Esperar que cargue Cápsulas de ayuda
            base.pausaPorElementoVisible(By.xpath("//a[contains(@onclick, '339')]"));
            // Hover sobre Cápsulas de ayuda
            actions.moveToElement(driver.findElement(By.xpath("//a[contains(@onclick, '339')]"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover en Cápsulas de ayuda
            base.capturaPantallaCompletaF("CapsulasAyuda", "Hover_Capsulas_Ayuda");
            System.out.println(">>>>>> Hover en Cápsulas de ayuda capturado");
            test.log(Status.INFO, "Hover en Cápsulas de ayuda capturado");

            // Click en Cápsulas de ayuda
            base.click(By.xpath("//a[contains(@onclick, '339')]"));
            base.pausaFijaSeg(3);

            // Esperar a que se abra la nueva ventana
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Cambiar a nueva ventana de Cápsulas de ayuda
            Set<String> handles3 = driver.getWindowHandles();
            String ventanaPrincipal3 = driver.getWindowHandle();
            for (String handle : handles3) {
                if (!handle.equals(ventanaPrincipal3)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Pausa para carga
            base.pausaFijaSeg(2);
            // Captura: Cápsulas de ayuda
            base.capturaPantallaCompletaF("CapsulasAyuda", "Desde_Capsulas_Ayuda");
            System.out.println(">>>>>> Cápsulas de ayuda capturado");
            test.log(Status.INFO, "Cápsulas de ayuda capturado");

            // Cerrar ventana de Cápsulas de ayuda y volver
            driver.close();
            driver.switchTo().window(ventanaPrincipal3);
            System.out.println(">>>>>> Ventana de Cápsulas de ayuda cerrada, retornado a principal");
            test.log(Status.INFO, "Ventana de Cápsulas de ayuda cerrada, retornado a principal");

            // Cerrar ventana emergente con botón "Cerrar" (helper localizado)
            base.cerrarModalSiExiste();
            base.pausaFijaSeg(2);
            System.out.println(">>>>>> Ventana emergente (si existía) cerrada");
            test.log(Status.INFO, "Ventana emergente (si existía) cerrada");

            // Cerrar modal de error si existe
            try {
                if (base.elementoVisible(By.cssSelector("div[aria-labelledby='modal-error'] a.boton.bg-gris"))) {
                    WebElement closeModal = driver.findElement(By.cssSelector("div[aria-labelledby='modal-error'] a.boton.bg-gris"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeModal);
                    base.pausaFijaSeg(2);
                }
            } catch (Exception e) {
                // No modal de error
            }

            // Cerrar barra lateral (menú desplegable)
            base.click(By.id("menu-desplegable"));
            base.pausaFijaSeg(2);
            System.out.println(">>>>>> Barra lateral cerrada");
            test.log(Status.INFO, "Barra lateral cerrada");

            // Hover sobre acceso directo de Cápsulas de ayuda
            base.pausaPorElementoVisible(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Cápsulas de ayuda']"));
            actions.moveToElement(driver.findElement(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Cápsulas de ayuda']"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover sobre acceso directo de Cápsulas de ayuda
            base.capturaPantallaCompletaF("CapsulasAyuda", "Hover_Acceso_Directo");
            System.out.println(">>>>>> Hover sobre acceso directo de Cápsulas de ayuda capturado");
            test.log(Status.INFO, "Hover sobre acceso directo de Cápsulas de ayuda capturado");

            // Hover sobre acceso directo de Cápsulas de ayuda
            base.pausaPorElementoVisible(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Cápsulas de ayuda']"));
            actions.moveToElement(driver.findElement(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Cápsulas de ayuda']"))).perform();
            base.pausaFijaSeg(1);
            // Captura: Hover sobre acceso directo de Cápsulas de ayuda
            base.capturaPantallaCompletaF("CapsulasAyuda", "Hover_Acceso_Directo");
            System.out.println(">>>>>> Hover sobre acceso directo de Cápsulas de ayuda capturado");
            test.log(Status.INFO, "Hover sobre acceso directo de Cápsulas de ayuda capturado");

            // Click en acceso directo de Cápsulas de ayuda
            base.click(By.xpath("//article[@id='contenedorDashboardRapido']/section/a[@title='Cápsulas de ayuda']"));
            base.pausaFijaSeg(3);
            // Captura: Cápsulas de ayuda desde acceso directo
            base.capturaPantallaCompletaF("CapsulasAyuda", "Desde_Acceso_Directo");
            System.out.println(">>>>>> Cápsulas de ayuda desde acceso directo capturado");
            test.log(Status.INFO, "Cápsulas de ayuda desde acceso directo capturado");

            // Cerrar modal o ventana si existe
            base.cerrarModalSiExiste();
            base.pausaFijaSeg(2);
            System.out.println(">>>>>> Modal o ventana de Cápsulas de ayuda cerrado (si existía)");
            test.log(Status.INFO, "Modal o ventana de Cápsulas de ayuda cerrado (si existía)");

            // Volver a la página principal y cerrar cualquier segunda pestaña abierta
            try {
                Set<String> handlesFinal = driver.getWindowHandles();
                for (String h : handlesFinal) {
                    if (!h.equals(ventanaPrincipalOriginal)) {
                        try {
                            driver.switchTo().window(h);
                            base.pausaFijaSeg(1);
                            // Captura la última pantalla antes de cerrar
                            base.capturaPantallaCompletaF("ExtraPage", "Antes_Cerrar_Final");
                            // Cerrar la ventana secundaria
                            driver.close();
                            System.out.println(">>>>>> Ventana secundaria final cerrada: " + h);
                        } catch (Exception ex) {
                            System.out.println(">>>>>> No se pudo cerrar ventana secundaria final " + h + ": " + ex.getMessage());
                        }
                    }
                }
                // Volver a la ventana principal original
                driver.switchTo().window(ventanaPrincipalOriginal);
                System.out.println(">>>>>> Retornado a la página principal original");
            } catch (Exception e) {
                System.out.println(">>>>>> Error cerrando ventanas finales: " + e.getMessage());
            }

            // 2) Ir a Accesos Rápidos -> Ayuda en Línea (icono con <em class='bci-icon color'>) y capturar
            try {
                By accesosAyuda = By.xpath("//article[@id='contenedorDashboardRapido']/section/a[3]");
                base.pausaPorElementoVisible(accesosAyuda);
                // Hover y captura previa
                Actions act = new Actions(driver);
                act.moveToElement(driver.findElement(accesosAyuda)).perform();
                base.pausaFijaSeg(2);
                base.capturaPantallaCompletaF("AyudaEnLinea", "Hover_Accesos_Rapidos");

                // Click en Accesos Rápidos -> Ayuda en Línea
                base.click(accesosAyuda);
                base.pausaFijaSeg(5);
                // Captura de la Ayuda en Línea
                base.capturaPantallaCompletaF("AyudaEnLinea", "Desde_Accesos_Rapidos");
                System.out.println(">>>>>> Accedido a Ayuda en Línea desde Accesos Rápidos y capturado");

                // Si abrió una nueva pestaña/ventana, cerrarla y volver a la principal
                try {
                    Set<String> afterAccesos = driver.getWindowHandles();
                    for (String h : afterAccesos) {
                        if (!h.equals(ventanaPrincipalOriginal)) {
                            driver.switchTo().window(h);
                            base.pausaFijaSeg(1);
                            driver.close();
                            System.out.println(">>>>>> Cerrada pestaña abierta por Accesos Rápidos: " + h);
                        }
                    }
                    driver.switchTo().window(ventanaPrincipalOriginal);
                } catch (Exception ex2) {
                    System.out.println(">>>>>> Error cerrando pestaña de Accesos Rápidos: " + ex2.getMessage());
                }
            } catch (Exception e) {
                System.out.println(">>>>>> No se pudo acceder a Accesos Rápidos -> Ayuda en Línea: " + e.getMessage());
            }

            // Abrir Servicios de Información desde menú lateral nuevamente
            try {
                // Abrir menú desplegable
                base.click(By.id("menu-desplegable"));
                base.pausaFijaSeg(2);
                base.pausaPorElementoVisible(By.id("menu-serviciosdeinformación"));

                // Click en Servicios de Información
                base.click(By.id("menu-serviciosdeinformación"));
                base.pausaFijaSeg(2);
                // Captura: Servicios de Información abierto desde menú lateral
                base.capturaPantallaCompletaF("ServiciosInformacion", "Desde_Menu_Lateral_Final");
                System.out.println(">>>>>> Servicios de Información abierto desde menú lateral nuevamente");
                test.log(Status.INFO, "Servicios de Información abierto desde menú lateral nuevamente");

                // Seleccionar pestaña Comisiones
                By comisionesTab = By.xpath("//section[@id='ContenedorGenerales']/header/ul/li[2]");
                base.pausaPorElementoVisible(comisionesTab);
                base.click(comisionesTab);
                base.pausaFijaSeg(1);

                // Rellenar campos desde Principal.json (datos2)
                String periodo = base.obtenerJason("Principal", "datos2", "periodo");
                String quincena = base.obtenerJason("Principal", "datos2", "quincena");
                String rutContratante = base.obtenerJason("Principal", "datos2", "rutContratante");
                String rutCorredor = base.obtenerJason("Principal", "datos2", "rutCorredor");
                String corredor = base.obtenerJason("Principal", "datos2", "corredor");

                // Periodo: input tipo fecha (usar helper del page object)
                poc.GG_Principal_Page principalPage = new poc.GG_Principal_Page(driver);
                By periodoInput = By.xpath("//div/div[2]/header[1]/section/p/input");
                principalPage.pausaPorElementoVisible(periodoInput);
                principalPage.insertarDatos(periodo, periodoInput);
                principalPage.pausaFijaMs(1000);

                // Quincena: seleccionar la opción usando el helper reutilizable (indice desde JSON)
                By quincenaInput = By.xpath("//div[@id='vs3__combobox']/div[1]/input");
                principalPage.pausaPorElementoVisible(quincenaInput);
                principalPage.seleccionarVueSelectPorIndice(quincenaInput, quincena);
                principalPage.pausaFijaMs(1000);


                // RUT Corredor
                By rutCorredorInput = By.xpath("//div/div[2]/header[1]/section/p[3]/input");
                principalPage.pausaPorElementoVisible(rutCorredorInput);
                principalPage.insertarDatos(rutCorredor, rutCorredorInput);
                principalPage.pausaFijaMs(1000);

                // Corredor: usar el selector especializado del page object
                By cboCorredorLocator = By.id("vs4__combobox");
                principalPage.pausaPorElementoVisible(cboCorredorLocator);
                principalPage.selecComboCorredor(cboCorredorLocator, corredor);
                principalPage.pausaFijaMs(2000); // Pausa extra para que se complete la selección

                // Captura del formulario de Comisiones relleno
                base.capturaPantallaCompletaF("Comisiones", "Formulario_Relleno_Final");
                System.out.println(">>>>>> Formulario de Comisiones rellenado con todos los datos del JSON");
                test.log(Status.INFO, "Formulario de Comisiones rellenado con todos los datos del JSON");

                // Hacer click en buscar (usar reintentos y fallback JS si está bloqueado)
                By buscarButton = By.xpath("//div/div[2]/header[1]/section/a");
                // Intentar click robusto con reintentos
                boolean clicked = base.reintentarClick(buscarButton);
                if (!clicked) {
                    try {
                        WebElement buscarEl = driver.findElement(buscarButton);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buscarEl);
                        System.out.println(">>>>>> Fallback JS click en Buscar ejecutado");
                    } catch (Exception ex) {
                        System.out.println(">>>>>> No se pudo hacer click en Buscar: " + ex.getMessage());
                        throw ex;
                    }
                }
                base.pausaFijaSeg(3);
                // Captura de resultados de búsqueda
                base.capturaPantallaCompletaF("Comisiones", "Resultados_Busqueda");
                System.out.println(">>>>>> Búsqueda de comisiones realizada");
                test.log(Status.INFO, "Búsqueda de comisiones realizada");

                // Descargar Excel de Comisiones
                By descargarComisionesBtn = By.xpath("//button[@onclick=\"$('#tipoDocumentoComi').val('xlsx');\"]");
                base.pausaPorElementoVisible(descargarComisionesBtn);
                base.click(descargarComisionesBtn);
                base.pausaFijaSeg(5); // Esperar descarga
                System.out.println(">>>>>> Excel de Comisiones descargado");
                test.log(Status.INFO, "Excel de Comisiones descargado");

                // Capturar pantalla después de descarga
                base.capturaPantallaCompletaF("Excel_Comisiones", "Descargado");
                System.out.println(">>>>>> Pantalla de descarga de Comisiones capturada");
                test.log(Status.INFO, "Pantalla de descarga de Comisiones capturada");

                // Cerrar modal
                base.cerrarModalSiExiste();

                // Ir nuevamente a menú lateral y seleccionar Búsqueda Rápida
                base.click(By.id("menu-desplegable"));
                base.pausaFijaSeg(2);
                base.pausaPorElementoVisible(By.id("menu-búsquedarápida"));
                base.click(By.id("menu-búsquedarápida"));
                base.pausaFijaSeg(2);
                System.out.println(">>>>>> Navegado a Búsqueda Rápida nuevamente");
                test.log(Status.INFO, "Navegado a Búsqueda Rápida nuevamente");

                // Ir a la segunda pestaña: Seguros de Vida y Salud
                By tabVidaSalud = By.xpath("//li[contains(text(),'Seguros de Vida y Salud')]");
                base.pausaPorElementoVisible(tabVidaSalud);
                base.click(tabVidaSalud);
                base.pausaFijaSeg(2);
                System.out.println(">>>>>> Pestaña Seguros de Vida y Salud seleccionada");
                test.log(Status.INFO, "Pestaña Seguros de Vida y Salud seleccionada");

                // Ingresar RUT a consultar (RUT ya seleccionado por defecto)
                By inputRutVida = By.xpath("//div[@id='app-buscador']/section/div[2]/div/header/section/div[1]/p/input");
                base.pausaPorElementoClikeable(inputRutVida);
                base.insertarDatos(rutContratante, inputRutVida);
                System.out.println(">>>>>> RUT ingresado para Vida y Salud: " + rutContratante);
                test.log(Status.INFO, "RUT ingresado para Vida y Salud: " + rutContratante);

                // Hacer click en buscar (Vida y Salud) usando el locator que funciona en el proyecto
                By buscarVidaBtn = By.xpath("//div[@id='app-buscador']/section/div[2]/div/header/section/button");
                base.pausaPorElementoClikeable(buscarVidaBtn);
                base.click(buscarVidaBtn);
                System.out.println(">>>>>> Buscar Vida y Salud ejecutado");
                base.pausaFijaSeg(3);
                // Captura de resultados de búsqueda Vida y Salud
                base.capturaPantallaCompletaF("VidaSalud", "Resultados_Busqueda");
                System.out.println(">>>>>> Búsqueda de Vida y Salud realizada");
                test.log(Status.INFO, "Búsqueda de Vida y Salud realizada");

                // Descargar Excel de Vida y Salud (fallback: probar varios selectores y click robusto)
                List<By> descargarLocators = Arrays.asList(
                    By.xpath("//button[@onclick=\"$('#TipoDescargaProduccionVida').val('xlsx');\"]"),
                    By.xpath("//form[@action='/Busqueda/ExportarBusquedaVida']/button"),
                    By.xpath("//button[contains(normalize-space(.),'Descargar') and contains(normalize-space(.),'Vida') ]"),
                    By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'excel')]")
                );

                boolean downloaded = false;
                for (By loc : descargarLocators) {
                    try {
                        base.pausaPorElementoVisible(loc);
                        boolean clickedDownload = base.reintentarClick(loc);
                        if (!clickedDownload) {
                            try {
                                WebElement el = driver.findElement(loc);
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                            } catch (Exception jsEx) {
                                // continuar a siguiente locator
                                continue;
                            }
                        }
                        // esperar breve tiempo a que la descarga/acción se dispare
                        base.pausaFijaSeg(5);
                        downloaded = true;
                        System.out.println(">>>>>> Excel de Vida y Salud descargado via locator: " + loc.toString());
                        test.log(Status.INFO, "Excel de Vida y Salud descargado via locator: " + loc.toString());
                        break;
                    } catch (Exception e) {
                        // intentar siguiente locator
                    }
                }

                if (!downloaded) {
                    try { base.capturaPantallaCompletaF("Error_Flujo", "Vida_Descarga_NoEncontrada"); } catch (Exception ignore) {}
                    test.log(Status.FAIL, "No se pudo encontrar botón de descarga Vida y Salud con locators conocidos");
                    throw new RuntimeException("No se pudo encontrar botón descarga Vida y Salud con locators conocidos");
                }

                // Capturar pantalla después de descarga
                base.capturaPantallaCompletaF("Excel_VidaSalud", "Descargado");
                System.out.println(">>>>>> Pantalla de descarga de Vida y Salud capturada");
                test.log(Status.INFO, "Pantalla de descarga de Vida y Salud capturada");

                // Captura final y cerrar página
                base.capturaPantallaCompletaF("Final", "Test_Completado");
                System.out.println(">>>>>> Test completado exitosamente");
                test.log(Status.INFO, "Test completado exitosamente");

            } catch (Exception e) {
                System.out.println(">>>>>> Error en Servicios de Información, Comisiones o Vida y Salud: " + e.getMessage());
                try { base.capturaPantallaCompletaF("Error_Flujo", "Servicios_Comisiones_Vida_Error"); } catch (Exception ignore) {}
                // Relanzar para que la excepción llegue al catch externo y la prueba falle correctamente
                throw e;
            }

            // Si llegamos aquí sin excepciones, marcar PASS
            test.log(Status.PASS, "Flujo completo ejecutado exitosamente");
        } catch (Exception e) {
            test.log(Status.FAIL, "Error en el flujo: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            base.driverQuit();
        }
        extent.flush();
    }
}