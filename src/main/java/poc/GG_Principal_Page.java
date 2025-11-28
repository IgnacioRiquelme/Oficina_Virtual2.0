package poc;

import java.io.File;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
//import java.util.concurrent.TimeoutException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import exceptions.confirmaProceso;
import pom.CC_Base;

public class GG_Principal_Page extends CC_Base {

    public GG_Principal_Page(WebDriver driver) {super(driver);}


    /**************** ****************/
    /********* Localizadores *********/
    /**************** ****************/
    
    /*datos login*/
    By txtUser = By.id("Rut");// RUT del usuario
    By txtPsw = By.id("Password");// Contraseña del usuario
    By btnIngresa = By.id("login-boton");// Botón ingresar en login

    /*menu principal*/
    By mnuInicio = By.id("menu-desplegable");//menu sin hacer click
    By mnuBusquedaRapida = By.id("menu-búsquedarápida");//id del menu cambia al estar desplegado


    /*menu busqueda rápida*/
    By txtRutBusRapida = By.xpath("//div[@id='app-buscador']/section/div[1]/div/header/section/div[1]/p/input");//caja de texto para búsqueda por rut en menú busqueda rapida
    By cboTipoVigencia = By.xpath("//div[@id='app-buscador']/section/div[1]/div/header/section/div[1]/p[2]/select");//combo box para tipo de vigencia en menú busqueda rapida
    By btnBusBusqRapida = By.id("btBusqueda");
    By btnLupa1BusRapida = By.xpath("//tbody/tr[1]/td[6]/a/i");
    By btnDescargarPoliza = By.xpath("//div[@class='modal w-1300 fade dinamico tabs show']/div/div/article[1]/div/div/a[2]");
    By vsrPoliza = By.xpath("//body/embed");

    By btnSeguroVidaSalud = By.xpath("//div[@id='app-buscador']/header/ul/li[2]");
    By txtRutVidaSalud = By.xpath("//div[@id='app-buscador']/section/div[2]/div/header/section/div[1]/p/input");
    By btnBusVidaSalud = By.xpath("//div[@id='app-buscador']/section/div[2]/div/header/section/button");
    By dgdDatosVidaSalud = By.xpath("//div[@id='app-buscador']/section/div[2]/div/section/div[3]/div/div[1]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[1]");
    //By btnDescargarXls = By.xpath("//div[@id='app-buscador']/section/div[2]/div/section/div[3]/div/div/div[1]/header/form/button/span");
    //By btnDescargarXls = By.xpath("//div[@id=\"app-buscador\"]/section/div[2]/div/section/div[3]/div/div[1]/div[1]/header/form/button");
    By btnDescargarXls = By.xpath("//form[@action='/Busqueda/ExportarBusquedaVida']/button");




    /*menu cobertura */
    By btnOpcionCobertura = By.xpath("//div[@class='modal w-1300 fade dinamico tabs show']/div/div/div[4]/div[1]/div/div[2]/div/div/ul/li[5]/a");//opción cobertura
    By lblTituloCobertura = By.xpath("//div[(text()='Cobertura')]");//título cobertura
    By dgdCobertura = By.xpath("//tr[@data-index='9']");//grid cobertura
    By mdlActivo = By.cssSelector("div.modal-body.activo");
    By btnDescargaCobertura = By.xpath("//section[@class='content']/div[1]/div/section/div[2]/div/div[2]/div/div/article[1]/div/div/a[1]");
    //div[@class='modal w-1300 fade dinamico tabs show']/div/div/div[4]/div/div/div[2]/div[6]/div/div/div/div/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr[10]/td[1]
    By ldrCobertura = By.xpath("//div[@id='despliegueinformativo']/div[2]/span");//loader cobertura

    /*menu servicios de información*/
    By mnuServiciosInfo = By.id("menu-serviciosdeinformación");
    //By lblBusquedaDatos = By.xpath("//section[@id='ContenedorGenerales']/div/section/header/div/h2");
    By lblBusquedaDatos = By.xpath("//section[@id='ContenedorGenerales']/div[1]/section/header/div/h2");//título servicios de información
    By mnuServiciosInfoIcono = By.xpath("//article[@id='contenedorDashboardRapido']/section/a[2]");
    By mnuComisiones = By.xpath("//section[@id='ContenedorGenerales']/header/ul/li[2]");
    By lblComisiones = By.xpath("//section[@id='seccion_Comision']/section/header/div/h2");//título comisiones
    //section[@id='ContenedorGenerales']/div[1]/section/header/div/h2
    By cboPeriodoComisiones = By.xpath("//div/div[2]/header[1]/section/p/input");//combo periodo comisiones
    //By cboQuincenaComisiones = By.xpath("//div[@id='vs3__combobox']/div/span");//combo quincena comisiones
    By btnCboQuincenaComisiones = By.xpath("//div[@id='vs3__combobox']/div[2]");//combo quincena comisiones
    By cboQuincenaComisiones = By.xpath("//div[@id='vs3__combobox']/div[1]/input");//combo quincena comisiones
    By cboRamoBtn = By.xpath("//div[@id='vs5__combobox']/div[2]");//boton combo ramo comisiones
    By cboRamoSeleccion = By.xpath("//div[@id='vs5__listbox']/ul/li[2]");//selección combo ramo comisiones

    By txtRutContratante = By.xpath("//div/div[2]/header[1]/section/p[2]/input");//caja texto rut contratante
    By txtRutCorredor = By.xpath("//div/div[2]/header[1]/section/p[3]/input");//caja texto rut corredor
    By btnBuscarComisiones = By.xpath("//div/div[2]/header[1]/section/a");//botón buscar comisiones
    By cboCorredor = By.id("vs4__combobox");
    By btnDescargarComisiones = By.xpath("//div[@id='Contenedor']/section[1]/div[2]/section/section/div/div[2]/section/header/div/div[5]/form/button");

    /*menu noticias*/

    By mnuNoticias = By.id("menu-noticias");
    By lblTituloNoticias = By.xpath("//body/div[3]/div[2]/div/h1");//título noticias
    By mnuNoticiasIcono = By.xpath("//article[@id='contenedorDashboardRapido']/section/a[1]");
    By btnPrimeraNoticia = By.xpath("//body/div[4]/div/div[1]/div/a");
    By btnNovenaNoticia = By.xpath("//body/div[4]/div/div[9]/div/a");

    /*menu capsulas ayuda */
    //By mnuAyudaenLinea = By.xpath("//li[@class='sub activo']/a");
    By mnuAyudaenLinea = By.id("menu-ayudaenlínea");
    By mnuCapsulasAyuda = By.xpath("//li[@class='sub activo']/ul");
    By mnuAyudaIcono = By.xpath("//article[@id='contenedorDashboardRapido']/section/a[4]");
    By lblCapsulasAyuda = By.xpath("//body/div[3]/div[2]/div/h1");//título ayuda

    /*menu preguntas frecuentes */
    By mnuAyudaenLineaIcono = By.xpath("//article[@id='contenedorDashboardRapido']/section/a[3]");
    By lblPreguntasFrecuentes = By.xpath("//body/div[3]/div/div/h1");//título preguntas frecuentes


    /*********************************************************/
    //////*********************************************************//////
                   /////// Menú Mantenedores //////
                 ///// submenu Nombre Comercial /////
    //////*********************************************************//////


    public void descargaPoliza(String user, String pasword, String var, String PATH_CAPTURA,  String rutAsegurado) {
        pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        //pausaPorElementoClikeable(mnuBusquedaRapida);
        /* captura pantalla */
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
        capturaPantallaCompletaF(var, "2_Mnu Lateral_Busqueda Rápida");
        pausaPorElementoClikeable(mnuBusquedaRapida); click(mnuBusquedaRapida);
        pausaPorElementoLocaizado(txtRutBusRapida);
        insertarDatos(rutAsegurado, txtRutBusRapida);
        capturaPantallaCompletaF(var, "3_Pantalla Busqueda Rut");
        pausaPorElementoClikeable(btnBusBusqRapida); click(btnBusBusqRapida); 

        if (elementoVisible(btnLupa1BusRapida)) {
            pausaPorElementoVisible(btnLupa1BusRapida);
            /* captura pantalla */
            capturaPantallaCompletaF(var, "4_Resultados Busqueda Rapida");
            System.out.println("Se desplegó el resultado de la búsqueda rápida");
            click(btnLupa1BusRapida);
        } else {
            throw new confirmaProceso("No se encontró el resultado de la búsqueda rápida");
        }

        pausaPorElementoVisible(btnDescargarPoliza);
        String ventanaPrincipal = driver.getWindowHandle();
        if (elementoVisible(btnDescargarPoliza)) {
            pausaPorElementoClikeable(btnDescargarPoliza);
            /* captura pantalla */
            capturaPantallaCompletaF(var, "5_Información General");
            System.out.println("Se desplegó el resultado de la búsqueda rápida");
            click(btnDescargarPoliza);
            cambioVentana();
        } else {
            throw new confirmaProceso("No se encontró el resultado de la búsqueda rápida");
        }



            /* captura pantalla */
            implicitWait(5000);
            //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(CC_Variables_Globales.PAUSA_GENERAL_2));
            //wait.until(ExpectedConditions.urlContains("visordocumentos.bciseguros.cl/Visor.aspx"));
            if(elementoVisible(vsrPoliza)) {
                domCompleto();
                System.out.println("Se visualiza el visor de póliza");
                pausaFijaMs(6000);
                capturaPantallaCompletaF(var, "6_Visor de Póliza");
                retornoVentana(driver, ventanaPrincipal);
            } else {
                throw new confirmaProceso("No se visualiza el visor de póliza");
            }
    }

     public void revisionCoberturas(String user, String pasword, String var, String PATH_CAPTURA,  String rutAsegurado) {
         pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        //pausaPorElementoClikeable(mnuBusquedaRapida);
        /* captura pantalla */
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
        capturaPantallaCompletaF(var, "2_Mnu Lateral_Busqueda Rápida");
        pausaPorElementoClikeable(mnuBusquedaRapida); click(mnuBusquedaRapida);
        pausaPorElementoLocaizado(txtRutBusRapida);
        insertarDatos(rutAsegurado, txtRutBusRapida);
        capturaPantallaCompletaF(var, "3_Pantalla Busqueda Rut");
        pausaPorElementoClikeable(btnBusBusqRapida); click(btnBusBusqRapida); 

        if (elementoVisible(btnLupa1BusRapida)) {
            pausaPorElementoClikeable(btnLupa1BusRapida);
            /* captura pantalla */
            capturaPantallaCompletaF(var, "4_Resultados Busqueda Rapida");
            System.out.println("Se desplegó el resultado de la búsqueda rápida");
            click(btnLupa1BusRapida);
        } else {
            throw new confirmaProceso("No se encontró el resultado de la búsqueda rápida");
        }

        
        waitForinvisibilityOfElement(ldrCobertura, 10);
        waitForVisibilityOfElement(btnOpcionCobertura, 10);
        pausaPorElementoClikeable(btnOpcionCobertura);
        clickConRetry(btnOpcionCobertura);
        waitForVisibilityOfElement(lblTituloCobertura, 10);
            if (elementoVisible(lblTituloCobertura)) {
                System.out.println("Se visualiza la pestaña cobertura");
                capturaPantallaCompletaF(var, "5_Cobertura");
                waitForVisibilityOfElement(dgdCobertura, 15);
                pausaPorElementoClikeable(btnDescargaCobertura); click(btnDescargaCobertura);
                pausaFijaMs(2000);
                capturaPantallaCompletaF(var, "6_Descarga_Cobertura");

                //implicitWait(15000);

                //click(btnOpcionCobertura);
                //implicitWait(5000);
                
            } else {
                throw new confirmaProceso("No se encontró la pestaña cobertura");
            }
    }

    /*
    public void revisionCoberturas(String user, String pasword, String var, String PATH_CAPTURA,  String rutAsegurado) {
        pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        //pausaPorElementoClikeable(mnuBusquedaRapida);
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
        capturaPantallaCompleta(var);
        pausaPorElementoClikeable(mnuBusquedaRapida); click(mnuBusquedaRapida);
        pausaPorElementoLocaizado(txtRutBusRapida);
        insertarDatos(rutAsegurado, txtRutBusRapida);
        pausaPorElementoClikeable(btnBusBusqRapida); click(btnBusBusqRapida); 

        if (elementoVisible(btnLupa1BusRapida)) {
            pausaPorElementoVisible(btnLupa1BusRapida);

            capturaPantallaCompleta(var);
            System.out.println("Se desplegó el resultado de la búsqueda rápida");
            click(btnLupa1BusRapida);
        } else {
            throw new confirmaProceso("No se encontró el resultado de la búsqueda rápida");
        }

        //waitForinvisibilityOfElement(mdlActivo, 10);
        waitForVisibilityOfElement(btnOpcionCobertura, 10);
        //ignoraOverlay(btnOpcionCobertura, 0);
        pausaPorElementoClikeable(btnOpcionCobertura);
        clickConRetry(btnOpcionCobertura);
        waitForVisibilityOfElement(lblTituloCobertura, 10);
            if (elementoVisible(lblTituloCobertura)) {
                waitForVisibilityOfElement(dgdCobertura, 15);
                System.out.println("Se visualiza la pestaña cobertura");
                capturaPantallaCompleta(var);
                waitForVisibilityOfElement(dgdCobertura, 15);

                implicitWait(15000);

                //click(btnOpcionCobertura);
                //implicitWait(5000);
                
            } else {
                throw new confirmaProceso("No se encontró la pestaña cobertura");
            }
    }*/
    

    public void validarMenuServiciosInformacion(String user, String pasword, String var, String PATH_CAPTURA) {
        pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
        /* captura pantalla */
        pausaFijaMs(1000);
        capturaPantallaCompletaF(var, "3_Menu_Serv_Informacion");
        pausaPorElementoClikeable(mnuServiciosInfo); click(mnuServiciosInfo);
        waitForVisibilityOfElement(lblBusquedaDatos, 10);
            if (elementoVisible(lblBusquedaDatos)) {
                System.out.println("Se visualiza el menú Servicios de Información");
                pausaFijaMs(1000);
                capturaPantallaCompletaF(var, "4_Ventana_Serv_Informacion");
                
            } else {
                throw new confirmaProceso("No se encontró el menú Servicios de Información");
            }
    }


    public void validarMenuServiciosInformacionIcono(String user, String pasword, String var, String PATH_CAPTURA) {
        zoomMenos();
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
        pausaPorElementoClikeable(mnuServiciosInfoIcono); 
        /* captura pantalla */
        pausaFijaMs(1000);
        click(mnuServiciosInfoIcono);
        waitForVisibilityOfElement(lblBusquedaDatos, 10);
            if (elementoVisible(lblBusquedaDatos)) {
                System.out.println("Se visualiza el menú Servicios de Información desde icono de acceso rápido");
                waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
                capturaPantallaCompletaF(var, "3_Ventana_Serv_Informacion_Icono");
                
            } else {
                throw new confirmaProceso("No se encontró el menú Servicios de Información desde icono de acceso rápido");
            }
    }

    public void validarMenuNoticias(String user, String pasword, String var, String PATH_CAPTURA) {
        String ventanaPrincipal = driver.getWindowHandle();
        pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        pausaPorElementoClikeable(mnuNoticias);
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));

        //implicitWait(10000);
        /* captura pantalla */
        pausaFijaMs(1000);
        capturaPantallaCompletaF(var, "3_Menu_Noticias");
        click(mnuNoticias);
        pausaFijaMs(1000);
        //implicitWait(10000);
        cambioVentana();
        waitForVisibilityOfElement(lblTituloNoticias, 30);
            if (elementoVisible(lblTituloNoticias)) {
                //waitForVisibilityOfElement(lblTituloNoticias, 30);
                //System.out.println("Se visualiza el menú Noticias");
                waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
                pausaFijaMs(1000);
                capturaPantallaCompletaF(var, "4_Ventana_Noticias");
                retornoVentana(driver, ventanaPrincipal);
                //capturaPantallaCompleta(var);

                
            } else {
                throw new confirmaProceso("No se encontró el menú Noticias");
            }
    }

    public void validarMenuNoticiasIcono(String user, String pasword, String var, String PATH_CAPTURA) {
        String ventanaPrincipal = driver.getWindowHandle();
        /* captura pantalla */
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
        pausaFijaMs(1000);
        //capturaPantallaCompletaF(var, "3_Menu_Noticias_Icono"   );
        zoomMenos();
        pausaPorElementoClikeable(mnuNoticiasIcono); click(mnuNoticiasIcono);
        //implicitWait(10000);

        implicitWait(10000);
        cambioVentanaStandar();
        implicitWait(10000);
        zoomMenos();
        waitForVisibilityOfElement(btnPrimeraNoticia, 30);
            if (elementoVisible(btnPrimeraNoticia)) {
                waitForVisibilityOfElement(btnPrimeraNoticia, 30);
                //System.out.println("Se visualiza el menú Noticias");
                waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
                pausaFijaMs(4000);
                capturaPantallaCompletaF(var, "3_Ventana_Noticias");
                retornoVentana(driver, ventanaPrincipal);

                
            } else {
                throw new confirmaProceso("No se encontró el menú Noticias");
            }
    }

    public void validarMenuCapsulasAyuda(String user, String pasword, String var, String PATH_CAPTURA) {
        String ventanaPrincipal = driver.getWindowHandle();
        pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        pausaPorElementoClikeable(mnuAyudaenLinea);
        //waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
        pausaFijaMs(1000);
        /* captura pantalla */
        click(mnuAyudaenLinea);
        pausaFijaMs(1000);
        capturaPantallaCompletaF(var, "3_Menu_Ayuda en Linea");
        click(mnuCapsulasAyuda);
        pausaFijaMs(1000);
        //implicitWait(10000);
        cambioVentana();
            if (elementoVisible(lblCapsulasAyuda)) {
                //waitForVisibilityOfElement(lblTituloNoticias, 30);
                //System.out.println("Se visualiza el menú Noticias");
                waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(2500));
                pausaFijaMs(1000);
                capturaPantallaCompletaF(var, "4_Ventana_Capsulas_Ayuda");
                retornoVentana(driver, ventanaPrincipal);
                //capturaPantallaCompleta(var);

                
            } else {
                throw new confirmaProceso("No se encontró el menú Capsulas de Ayuda");
            }
    }


    public void validarMenuCapsulasAyudaIcono(String user, String pasword, String var, String PATH_CAPTURA) {
        String ventanaPrincipal = driver.getWindowHandle();
        /* captura pantalla */
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
        zoomMenos();
        pausaPorElementoClikeable(mnuAyudaIcono); click(mnuAyudaIcono);
        //implicitWait(10000);

        implicitWait(10000);
        cambioVentanaStandar();
        implicitWait(10000);
        zoomMenos();
        waitForVisibilityOfElement(lblCapsulasAyuda, 30);
            if (elementoVisible(lblCapsulasAyuda)) {
                waitForVisibilityOfElement(lblCapsulasAyuda, 30);
                //System.out.println("Se visualiza el menú Noticias");
                //waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
                pausaFijaMs(1000); capturaPantallaCompletaF(var, "3_Ventana_Capsulas_Ayuda");
                retornoVentana(driver, ventanaPrincipal);
            } else {
                throw new confirmaProceso("No se encontró el menú Ayuda");
            }
    }


    public void validarMenuPreguntasFrecuentesIcono(String user, String pasword, String var, String PATH_CAPTURA) {
        String ventanaPrincipal = driver.getWindowHandle();
        /* captura pantalla */
        zoomMenos();
        pausaPorElementoClikeable(mnuAyudaenLineaIcono); click(mnuAyudaenLineaIcono);
        //implicitWait(10000);

        implicitWait(10000);
        cambioVentanaStandar();
        implicitWait(10000);
        zoomMenos();
        waitForVisibilityOfElement(lblPreguntasFrecuentes, 30);
            if (elementoVisible(lblPreguntasFrecuentes)) {
                waitForVisibilityOfElement(lblPreguntasFrecuentes, 30);
                //System.out.println("Se visualiza el menú Noticias");
                //waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
                pausaFijaMs(5000);
                capturaPantallaCompletaF(var, "3_Ventana_Preguntas_Frecuentes");
                retornoVentana(driver, ventanaPrincipal);
            } else {
                throw new confirmaProceso("No se encontró el menú Ayuda");
            }
    }

    public void validarComisiones(String user, String pasword, String var, String PATH_CAPTURA, String periodo, String quincena, String corredor, String rutCorredor) {
        pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        //waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
        /* captura pantalla */
        pausaFijaMs(1000); capturaPantallaCompletaF(var, "3_Menu_Serv_Informacion");
        pausaPorElementoClikeable(mnuServiciosInfo); click(mnuServiciosInfo);
        pausaPorElementoClikeable(mnuComisiones); click(mnuComisiones);
        //waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
        /* captura pantalla */
        //capturaPantallaCompleta(var);
        waitForVisibilityOfElement(lblComisiones, 10);
            if (elementoVisible(lblComisiones)) {
                System.out.println("Se visualiza el menú Comisiones");
                pausaPorElementoLocaizado(cboPeriodoComisiones); insertarDatos(periodo, cboPeriodoComisiones); escape(driver);
                seleccionarVueSelectPorIndice(cboQuincenaComisiones, quincena);
                //insertarDatos(rutContratante, txtRutContratante);
                insertarDatos(rutCorredor, txtRutCorredor);
                selecComboCorredor(cboCorredor, corredor);
                pausaFijaMs(1000);
                pausaPorElementoClikeable(btnBuscarComisiones); click(btnBuscarComisiones);
                pausaFijaMs(4000);
                pausaPorElementoClikeable(btnDescargarComisiones); click(btnDescargarComisiones);

                //waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
                pausaFijaMs(11000); capturaPantallaCompletaF(var, "4_Ventana_Comisiones");
                System.out.println("Se genero descarga de comisiones");
            } else {
                throw new confirmaProceso("No se encontró el menú Comisiones");
            }
    }

    public void validarPestanaVidaSalud(String user, String pasword, String var, String PATH_CAPTURA, String rutContratante) {
        pausaPorElementoClikeable(mnuInicio); click(mnuInicio);
        waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
        pausaPorElementoClikeable(mnuBusquedaRapida); click(mnuBusquedaRapida);
        pausaFijaMs(1000);
        capturaPantallaCompletaF(var, "3_Menu_Busqueda_Rapida");
        pausaPorElementoClikeable(btnSeguroVidaSalud); click(btnSeguroVidaSalud);
        pausaPorElementoLocaizado(txtRutVidaSalud); insertarDatos(rutContratante, txtRutVidaSalud);
        pausaPorElementoClikeable(btnBusVidaSalud); click(btnBusVidaSalud);
        pausaFijaMs(1000);
        //pausaPorElementoLocaizado(dgdDatosVidaSalud);
            if (elementoVisible(dgdDatosVidaSalud)) {
                System.out.println("Se visualiza la busqueda de Vida y Salud");
                //waitDomStable(Duration.ofSeconds(10), Duration.ofMillis(1000));
                pausaFijaMs(1000);
                capturaPantallaCompletaF(var, "4_Datos_Seguro_Vida_Salud");
                pausaPorElementoClikeable(btnDescargarXls); click(btnDescargarXls); 
                System.out.println("Se generó la descarga de Vida y Salud");
                pausaFijaMs(5000);
                capturaPantallaCompletaF(var, "5_Descarga_XLS_Vida_Salud");
            } else {
                throw new confirmaProceso("No se encontró la busqueda de Vida y Salud");
            }

        
    }

    /////////////////////////////////////////////////
    /////////////////////////////////////////////////
        /////// Metodos reutilizables///////
    /////////////////////////////////////////////////
    /////////////////////////////////////////////////
    
    public void loginCorrecto(String user, String pasword, String var, String PATH_CAPTURA) {


        //crearCarpeta1(PATH_DESCARGA);
        crearCarpeta2(PATH_CAPTURA);
        insertarDatos(user, txtUser); insertarDatos(pasword, txtPsw); 

        /*captura pantalla */
        capturaPantallaCompletaF(var, "1_Login");

        pausaPorElementoClikeable(btnIngresa); click(btnIngresa); 
        implicitWait(3000); pausaPorElementoVisible(btnIngresa);
        flujoConLogin(mnuInicio);//Confirma Login para reporte
        pausaPorElementoLocaizado(mnuInicio);
        pausaFijaMs(3000);
        capturaPantallaCompletaF(var, "2_Home");
        
    }
    
    /*public void carpetaDescarga(String PATH_DESCARGA) {
        File carpeta = new File(PATH_DESCARGA);
        if (!carpeta.exists()) {
            // Si la carpeta NO existe, la creas.
            if (carpeta.mkdirs()) {
                System.out.println("Carpeta creada: " + PATH_DESCARGA);
            } else {
                System.out.println("No se pudo crear la carpeta: " + PATH_DESCARGA);
            }
        } else {
            // Si la carpeta YA existe, eliminas todos los archivos dentro.
            File[] files = carpeta.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (file.delete()) {
                            System.out.println("Archivo borrado: " + file.getName());
                        } else {
                            System.out.println("No se pudo borrar: " + file.getName());
                        }
                    }
                }
                System.out.println("Carpeta limpia y lista --> " + PATH_DESCARGA);
            }
        }
    }*/

    /*public static String flujoConfirmacionArchivoDescarga (String PATH_DESCARGA, String nombrePdf){

            File dir = new File(PATH_DESCARGA);
            File[] files = dir.listFiles();
            if (files == null) return null;

            for (File file : files){
                //if (file.getName().contains(nombrePdf)){
                if (file.getName().equals(nombrePdf)){
                    System.out.println("✅ Documento encontrado --> " + file.getName());
                    return file.getName();
                }
            }

            System.out.println("❌ Archivo con nombre --> " + nombrePdf + " no encontrado");
            throw new confirmaProceso("No se encontró el documento con nombre --> " + nombrePdf);            
            //return null;

            

    }*/

    public void crearCarpeta2(String PATH_CAPTURA) {
        File carpeta = new File(PATH_CAPTURA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    public  void cambioVentana(){
        String mainWindow = driver.getWindowHandle();
        implicitWait();
        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                String urlActual = driver.getCurrentUrl();
                System.out.println("🪟 Cambiado a: " + handle);
                System.out.println("🌐 URL actual: " + urlActual);

                if (urlActual.startsWith("visor")) {
                    System.out.println("✅ Es un visor URL, tomando captura...");
                    //capturaPantalla(var);
                } else {
                    System.out.println("⚠️ No es un visor URL, puedes aplicar otra lógica aquí.");
                }
                break;
            }else {
                System.out.println("🪟 Ventana principal: " + handle + " - URL: " + driver.getCurrentUrl());
            }
            implicitWait(3000);
        }
    }

    public  void cambioVentanaStandar(){
        // Cambia a la nueva ventana/pestaña abierta por una acción previa

        String main = driver.getWindowHandle();
        int ventanasAntes = driver.getWindowHandles().size();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1) Espera a que se abra una nueva ventana/pestaña
        wait.until(ExpectedConditions.numberOfWindowsToBe(ventanasAntes + 1));

        // 2) Switch a la que no es la principal (la nueva)
        Set<String> handles = driver.getWindowHandles();
        String nueva = handles.stream()
                .filter(h -> !h.equals(main))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró nueva ventana"));

        driver.switchTo().window(nueva);

        // 3) Espera a que cargue y valida por URL
        wait.until(d -> "complete".equals(
            ((JavascriptExecutor) d).executeScript("return document.readyState"))
        );

        String urlActual = driver.getCurrentUrl();
        System.out.println("🪟 Cambiado a: " + nueva);
        System.out.println("🌐 URL actual: " + urlActual);

        if (urlActual.contains("noticias")) {  // mejor que startsWith
            System.out.println("✅ Es 'noticias', tomando captura...");
            // capturaPantalla(var);
        } else {
            System.out.println("⚠️ No es 'noticias'. Aplica otra lógica.");
        }
    }

    public void waitDomStable(Duration timeout, Duration poll) {
        long end = System.currentTimeMillis() + timeout.toMillis();
        Long prev = -1L; int same = 0;
        while (System.currentTimeMillis() < end) {
            Long now = (Long)((JavascriptExecutor)driver).executeScript("return document.getElementsByTagName('*').length");
            if (now.equals(prev)) { same++; if (same >= 3) return; } else { same = 0; prev = now; }
            try { Thread.sleep(poll.toMillis()); } catch (InterruptedException ignored) {}
        }
    }
    

    public void retornoVentana(WebDriver driver, String mainHandle) {
        if (driver != null && mainHandle != null && !mainHandle.isEmpty()) {
            driver.switchTo().window(mainHandle);
            System.out.println("🔙 Retornado a la ventana principal: " + mainHandle);
        } else {
            System.out.println("⚠️ No se puede retornar: mainHandle es nulo o vacío.");
        }
    }

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

    /*public void flujoConNombreComercial(By locator, String nombrePdf, String PATH_DESCARGA) {
        try {
            // Paso 1: Verifica que el botón de descargar sea visible y realiza la acción
            if (elementoVisible(btnDescargar)) {
                System.out.println("Detecto el botón descargar");
                Thread.sleep(5000);

                // Paso 2: Validar la existencia del archivo en la carpeta de descargas
                File dir = new File(PATH_DESCARGA);
                File[] files = dir.listFiles();
                boolean archivoEncontrado = false;

                if (files != null) {
                    for (File file : files) {
                        // Usa 'contains' para coincidencia parcial, o 'equals' si quieres exacta
                        if (file.getName().contains(nombrePdf)) {
                            archivoEncontrado = true;
                            System.out.println("✅ Archivo encontrado: " + file.getName());
                            break;
                        }else{
                            System.out.println("❌ Archivo no encontrado: " + file.getName() +"en la carpeta"+ PATH_DESCARGA);
                        }
                    }
                }

                // Paso 3: Acciona según el resultado de la validación
                if (!archivoEncontrado) {
                    throw new confirmaProceso("No se encontró el archivo esperado con nombre: " + nombrePdf);
                } else {
                    System.out.println("Validación exitosa: El archivo con nombre '" + nombrePdf + "' está presente en la carpeta de descargas.");
                }
            } else {
                throw new confirmaProceso("No se encontró el elemento del botón de descargar");
            }
        } catch (Exception e) {
            throw new confirmaProceso("Error en flujo de Confirmación de Nombre Comercial: " + e.getMessage());
        }
    }*/

    public void domCompleto() {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(CC_Variables_Globales.PAUSA_GENERAL));
        //wait.until(ExpectedConditions.urlContains("visordocumentos.bciseguros.cl/Visor.aspx"));
        //wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        //wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? 'ok' : null"));
        ExpectedCondition<Boolean> domCargado = (WebDriver d) ->"complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState"));

        wait.until(domCargado);
    }

    public void waitForVisibilityOfElement(By locator, int segundos) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(segundos));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForinvisibilityOfElement(By locator, int segundos) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(segundos));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void ignoraOverlay(By locator, int segundos) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickConRetry(By locator) {
    int retries = 2;
    for (int i = 0; i <= retries; i++) {
        try {
            click(locator);
            return;
        } catch (StaleElementReferenceException | ElementClickInterceptedException ex) {
            if (i == retries) throw ex;
            // Re-ubica el elemento tras el re-render
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        }
    }
}

    public static void escape(WebDriver driver) {
        try {
            driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
            return;
        } catch (Exception ignored) {}

        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            return;
        } catch (Exception ignored) {}

        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    public void seleccionarVueSelectPorIndice(By locator, String inputText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));
        input.click();

        String listboxId = input.getAttribute("aria-controls");     // ej: "vs3__listbox"
        String optionId  = listboxId.replace("__listbox", "__option-" + inputText);

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(optionId)));
        option.click();
    }

    public void selecComboCorredor(By locator, String corredor) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Paso 1: Desplegar el combobox
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
            dropdown.click();

            // Paso 2: Esperar y localizar la opción cuyo texto contenga el string
            String optionXpath = String.format(
                "//li[contains(@class, 'vs__dropdown-option') and contains(normalize-space(text()), '%s')]", corredor);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
            option.click();

            System.out.println("Opción seleccionada que contiene: " + corredor);

        } catch (TimeoutException e) {
            System.err.println("Timeout: No se encontró opción que contenga → " + corredor);
        } catch (Exception e) {
            System.err.println("Error durante selección: " + e.getMessage());
        }
    }
        

    public void selecComboCorredor2(By dropdownLocator, String corredor) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Paso 1: Clic para abrir el dropdown
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            dropdown.click();

            // Paso 2: Esperar a que aparezca la opción que contenga el texto
            String xpathOption = String.format(
                "//ul[@id='vs4__listbox']//li[contains(@class,'vs__dropdown-option') and contains(text(),'%s')]",
                corredor
            );

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOption)));

            // Paso 3: Click en la opción
            option.click();

            System.out.println(" Opción seleccionada: " + option.getText());

        } catch (TimeoutException e) {
            System.err.println("[✖] Timeout: No se encontró la opción con texto: " + corredor);
        } catch (Exception e) {
            System.err.println("[✖] Error al seleccionar la opción: " + e.getMessage());
        }
    }


}
