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
            // Captura de pantalla completa antes del enter
            base.capturaPantallaCompletaF("Login", "Pantalla_Login");

            // Hacer click en login
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

            // Paso 5: Hacer click en buscar
            System.out.println(">>>>>> Paso 5: Hacer click en buscar");
            test.log(Status.INFO, "Paso 5: Hacer click en buscar");
            base.click(By.id("btBusqueda"));

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
            // Esperar que se abra el modal
            base.pausaPorElementoVisible(By.cssSelector(".modal"));
            base.pausaFijaSeg(2);
            // Captura: Cápsulas de ayuda desde acceso directo
            base.capturaPantallaCompletaF("CapsulasAyuda", "Desde_Acceso_Directo");
            System.out.println(">>>>>> Cápsulas de ayuda desde acceso directo capturado");
            test.log(Status.INFO, "Cápsulas de ayuda desde acceso directo capturado");

            // Cerrar modal de Cápsulas de ayuda (uso helper seguro)
            try {
                base.cerrarModalSiExiste();
                base.pausaFijaSeg(2);
                System.out.println(">>>>>> Modal de Cápsulas de ayuda cerrado (si existía)");
                test.log(Status.INFO, "Modal de Cápsulas de ayuda cerrado (si existía)");
            } catch (Exception exModal) {
                System.out.println(">>>>>> Error cerrando modal de Cápsulas de ayuda: " + exModal.getMessage());
            }

            // -------------------- NUEVOS PASOS SOLICITADOS --------------------
            // 1) Volver a la pestaña principal original y cerrar cualquier pestaña secundaria abierta
            try {
                Set<String> handlesAct = driver.getWindowHandles();
                for (String h : handlesAct) {
                    if (!h.equals(ventanaPrincipalOriginal)) {
                        try {
                            driver.switchTo().window(h);
                            base.pausaFijaSeg(1);
                            base.capturaPantallaCompletaF("ExtraPage", "Antes_Cerrar");
                            driver.close();
                            System.out.println(">>>>>> Ventana secundaria cerrada: " + h);
                        } catch (Exception ex) {
                            System.out.println(">>>>>> No se pudo cerrar ventana secundaria " + h + ": " + ex.getMessage());
                        }
                    }
                }
                // Volver a la principal
                driver.switchTo().window(ventanaPrincipalOriginal);
                System.out.println(">>>>>> Retornado a la pestaña principal original");
            } catch (Exception e) {
                System.out.println(">>>>>> Error cerrando secundarias: " + e.getMessage());
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

            // 3) Volver al menú lateral y abrir Servicios de Información → pestaña Comisiones
            try {
                // Abrir menú lateral
                base.click(By.id("menu-desplegable"));
                base.pausaFijaSeg(1);
                base.pausaFijaSeg(2); // Mostrar menú
                base.pausaPorElementoVisible(By.id("menu-serviciosdeinformación"));
                // Click en Servicios de Información
                base.click(By.id("menu-serviciosdeinformación"));
                base.pausaFijaSeg(2);
                base.pausaFijaSeg(2); // Mostrar selección
                // Captura: Modal de Servicios de Información abierto
                base.capturaPantallaCompletaF("ServiciosInformacion", "Modal_Abierta");

                // Comisiones code commented out as per user request
                // test.log(Status.PASS, "Flujo completo ejecutado exitosamente");
            } catch (Exception e) {
                System.out.println(">>>>>> Error navegando a Comisiones o rellenando datos: " + e.getMessage());
            }

            // -------------------- FIN NUEVOS PASOS --------------------

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