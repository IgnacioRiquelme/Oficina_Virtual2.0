    package pom;



import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;


public class CC_Base {

    protected WebDriver driver;

    public CC_Base (WebDriver driver){this.driver=driver;}

    //setup chromdriver
    public static WebDriver setupChrome(String PATH_DESCARGA, String PATH_CAPTURA) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-notifications");

        // Configura las preferencias de descarga ANTES de crear el driver
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", PATH_DESCARGA); // Ruta absoluta
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);

        chromeOptions.setExperimentalOption("prefs", prefs);
        //chromeOptions.addArguments("--incognito"); // Modo incógnito

        WebDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    //llamar url
    public void llamarUrl(String url){ //-->url es una variable global
        try {
            driver.get(url);
            log(">>>>>> Info: Launching....."+url);
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }


    //generación de log
    public void log(String message){Reporter.log(message);
    }

    //insertar datos
    public void insertarDatos(String inputText, By locator){
        try {
            driver.findElement(locator).sendKeys(inputText);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    //generar click
    public void click(By locator){
        try{
            driver.findElement(locator).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CC_Variables_Globales.PAUSA_GENERAL));
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    //generar doble click
    public void dobleClick(By locator){
        try{
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void selecRadioButon(By locator){
        WebElement radioButton = driver.findElement(locator);
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }

    public void selecCheckBox(By locator){
        WebElement checkBox = driver.findElement(locator);
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
    }

    public void tabulador(By locator){
        WebElement cajaTexto = driver.findElement(locator);
        cajaTexto.sendKeys(Keys.TAB);
    }

     public void selecSelect(By locator, String inputText){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementoSelect = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(elementoSelect);
        select.selectByValue(inputText);

    }


    //seleccionar opción de combo (1ero click al combo luego click a la selección)
    public void seleccionar(By locator1, By locator2){
        try {
            WebElement combo =driver.findElement(locator1);//click combo
            combo.click();
            WebElement seleccion = driver.findElement(locator2);//click selección
            seleccion.click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void WebDriverWait(int wait){
        new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void zoomMenos(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='70%'");
    }

    public void zoomFinal(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='45%'");
    }

    //selección de combo especifico
    public void seleccionarCbo(String locator31, int i,  By locator1, By locator2, By locator3 , String inputText){
        try{
            WebElement cboBox = driver.findElement(locator1);       //localizamos el combo
            cboBox.click();     //click en el combo --> para visualizar las opciones de la lista
            List<WebElement> listaOpciones = driver.findElements(locator2);     //creamos lista de elementos con todas las opciones

            for (i=1; i<=listaOpciones.size(); i++){
                WebElement cboBoxSelec = driver.findElement(By.xpath(locator31.replace("y",String.valueOf(i))));
                /*luego de saber la cantidad de opciones de la lista, reemplazamos la variable y con la opción que queremos seleccionar,
                locator31 --> variable exclusiva para este flujo, se uso para poder utilizar el replace con un string
                la variable esta declarada en el page*/

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                    wait.until(ExpectedConditions.visibilityOf(cboBoxSelec));       //tiempo para el combo
                    String seleccion = cboBoxSelec.getText();       //obtenemos el text del locator desde el elementoweb (linea 128)

                    if (seleccion.contains(inputText)){//       comparamos el json con la selección
                        cboBoxSelec.click();        //hacemos click en la selección
                        break;
                    }
            }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void selCboDPSAsegurado(String locator32, int i, By locator1, By locator2, By locator3 , String inputText){
        try{
            WebElement cboBox = driver.findElement(locator1);       //localizamos el combo
            cboBox.click();     //click en el combo --> para visualizar las opciones de la lista
            List<WebElement> listaOpciones = driver.findElements(locator2);     //creamos lista de elementos con todas las opciones


            for (i=1; i<=listaOpciones.size(); i++){
                WebElement cboBoxSelec = driver.findElement(By.xpath(locator32.replace("y",String.valueOf(i))));
                /*luego de saber la cantidad de opciones de la lista, reemplazamos la variable y con la opción que queremos seleccionar,
                locator31 --> variable exclusiva para este flujo, se uso para poder utilizar el replace con un string
                la variable esta declarada en el page*/

                WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
                wait2.until(ExpectedConditions.visibilityOf(cboBoxSelec));       //tiempo para el combo
                String seleccion = cboBoxSelec.getText();       //obtenemos el text del locator desde el elementoweb (linea 128)

                if (seleccion.contains(inputText)){//       comparamos el json con la selección
                    cboBoxSelec.click();        //hacemos click en la selección
                    Actions actions = new Actions(driver);
                    actions.sendKeys(Keys.ESCAPE).perform();
                    break;
                }
            }
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void selCboDPSCarga(String locator34, int i, By locator1, By locator2, By locator3 , String inputText){
        try{
            WebElement cboBox = driver.findElement(locator1);       //localizamos el combo
            cboBox.click();     //click en el combo --> para visualizar las opciones de la lista
            List<WebElement> listaOpciones = driver.findElements(locator2);     //creamos lista de elementos con todas las opciones

            for (i=1; i<=listaOpciones.size(); i++){
                WebElement cboBoxSelec = driver.findElement(By.xpath(locator34.replace("y",String.valueOf(i))));
                /*luego de saber la cantidad de opciones de la lista, reemplazamos la variable y con la opción que queremos seleccionar,
                locator31 --> variable exclusiva para este flujo, se uso para poder utilizar el replace con un string
                la variable esta declarada en el page*/

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                wait.until(ExpectedConditions.visibilityOf(cboBoxSelec));       //tiempo para el combo
                String seleccion = cboBoxSelec.getText();       //obtenemos el text del locator desde el elementoweb (linea 128)

                if (seleccion.contains(inputText)){//       comparamos el json con la selección
                    cboBoxSelec.click();        //hacemos click en la selección
                    break;
                }
            }
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void selCboDPSAntecedente(String locator33, int i, By locator1, By locator2, By locator3 , String inputText){
        try{
            WebElement cboBox = driver.findElement(locator1);       //localizamos el combo
            cboBox.click();     //click en el combo --> para visualizar las opciones de la lista
            driver.findElement(locator3).sendKeys(inputText);// se agrega el texto en el filtro para poder seleccioar la opcion
            List<WebElement> listaOpciones = driver.findElements(locator2);     //creamos lista de elementos con todas las opciones

            for (i=1; i<=listaOpciones.size(); i++){
                WebElement cboBoxSelec = driver.findElement(By.xpath(locator33.replace("y",String.valueOf(i))));
                /*luego de saber la cantidad de opciones de la lista, reemplazamos la variable y con la opción que queremos seleccionar,
                locator31 --> variable exclusiva para este flujo, se uso para poder utilizar el replace con un string
                la variable esta declarada en el page*/

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOf(cboBoxSelec));       //tiempo para el combo
                String seleccion = cboBoxSelec.getText();       //obtenemos el text del locator desde el elementoweb (linea 128)


                if (seleccion.contains(inputText)){//       comparamos el json con la selección
                    cboBoxSelec.click();        //hacemos click en la selección
                    break;
                }
            }
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void selCboDPSAntecedente2da(String locator35, int i, By locator1, By locator2, By locator5 , String inputText){
        try{
            WebElement cboBox = driver.findElement(locator1);       //localizamos el combo
            cboBox.click();//click en el combo --> para visualizar las opciones de la lista
            driver.findElement(locator5).sendKeys(inputText);
            List<WebElement> listaOpciones = driver.findElements(locator2);     //creamos lista de elementos con todas las opciones


            for (i=1; i<=listaOpciones.size(); i++){
                WebElement cboBoxSelec = driver.findElement(By.xpath(locator35.replace("y",String.valueOf(i))));
                /*luego de saber la cantidad de opciones de la lista, reemplazamos la variable y con la opción que queremos seleccionar,
                locator31 --> variable exclusiva para este flujo, se uso para poder utilizar el replace con un string
                la variable esta declarada en el page*/

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOf(cboBoxSelec));       //tiempo para el combo
                String seleccion = cboBoxSelec.getText();       //obtenemos el text del locator desde el elementoweb (linea 128)


                if (seleccion.contains(inputText)){//       comparamos el json con la selección
                    cboBoxSelec.click();        //hacemos click en la selección
                    Actions actions = new Actions(driver);
                    actions.sendKeys(Keys.ESCAPE).perform();
                    break;
                }
            }
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void seleccionarDocumento(int i, String locatorD){
        try {

            for (i=1; i<=3; i++){
                WebElement documentoSelec = driver.findElement(By.xpath(locatorD.replace("y",String.valueOf(i))));

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                wait.until(ExpectedConditions.visibilityOf(documentoSelec));       //tiempo para el xpath
                String seleccion = documentoSelec.getText(); //llevamos el xpath detectado a una variable

                if (seleccion.contains("Seguro")){
                    documentoSelec.click(); // Por ejemplo, hacer clic en él
                    System.out.println(seleccion);
                    i=i++;
                    break;
                }
            }
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }
    


    //pausa implicita --> variable global
    public void implicitWait(){
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CC_Variables_Globales.PAUSA_GENERAL));
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }

    //pausa implicita --> variable sencilla no global
    public void implicitWait(int pausa){
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(pausa));
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }



    //pausa explicita --> asociada a un elemento
    public void pausaPorElementoVisible(By locator){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(CC_Variables_Globales.PAUSA_GENERAL));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }

    public void pausaPorElementoLocaizado(By locator){
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }

    public void pausaPorElementoClikeable(By locator){
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }

    public void pausaPorElemento(By locator){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(CC_Variables_Globales.PAUSA_GENERAL));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }catch (TimeoutException e){
            e.printStackTrace();
        }
    }

    // Pausa fija: espera bloqueante por milisegundos
    public void pausaFijaMs(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Pausa fija: segundos
    public void pausaFijaSeg(int seg) {
        pausaFijaMs(seg * 1000L);
    }


    //validar elemento visible
    public boolean elementoVisible(By locator){
        try{
            driver.findElement(locator).isDisplayed();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //validar elemento novisible
    public boolean elementNoVisible(By locator){
        try{
            driver.findElement(locator).isDisplayed();
            return false;
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return true;
        }
    }

    public boolean reintentarClick(By locator) {
        boolean result = false;
        int intento = 0;
        while(intento < 3) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                result = true;
                break;
            } catch(StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                intento++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return result;
    }


    public void reintentarClick2(By locator, int intentosMaximos, int tiempoEntreIntentosSegundos) {
        int intento = 0;
        boolean exito = false;
    
        while (intento < intentosMaximos) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(tiempoEntreIntentosSegundos));
                WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
                elemento.click();
                exito = true;
                System.out.println("Intento " + (intento + 1) + ": Click exitoso en " + locator.toString());
                break;
            } catch (Exception e) {
                System.out.println("Intento " + (intento + 1) + ": Falló el click sobre " + locator.toString() + ". Reintentando...");
                intento++;
                try {
                    Thread.sleep(tiempoEntreIntentosSegundos * 1000L);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrumpido durante espera entre reintentos.", ie);
                }
            }
        }
    
        if (!exito) {
            throw new RuntimeException("No se pudo hacer click en " + locator.toString() + " luego de " + intentosMaximos + " intentos.");
        }
    }

    //validar mensaje de error
    public void obtenerUrl(){
        String laUrl = driver.getCurrentUrl();
        Assert.assertEquals("La URL no coincide con la esperada", CC_Variables_Globales.OFICINA_VIRTUAL, laUrl+"/particular");
        System.out.println("la url es "+ laUrl);
    }


    //validar mensaje de error
    public String obtenerTextoTxt(By locator){
        try {
            WebElement inputField = driver.findElement(locator);
            String inputValue = inputField.getAttribute("value");
            System.out.println(inputValue);
            return inputValue;
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
        }
    }


    //cierra navegador
    public void driverClose(){
        try {
            driver.close();
        }catch (NoSuchSessionException e){
            e.printStackTrace();
        }
    }


    public void driverQuit() {
        // Cierra el navegador después de cada prueba
        if (driver != null) {
            driver.quit();
        }
    }

    //obtener datos del jsonFile
    public String obtenerJason(String fileName, String jsonFileObject, String jsonKey){
        try {
            InputStream inputStream = new FileInputStream(CC_Variables_Globales.PATH_JSON_DATA + fileName + ".json");
            JSONObject jsonObject = new JSONObject(new JSONTokener(inputStream));
            String jsonValue = (String) jsonObject.getJSONObject(jsonFileObject).get(jsonKey);
            return jsonValue;
        }catch (FileNotFoundException e){
            Assert.fail("No existe Json File");
            return null;
        }
    }


    public void capturaPantalla(String var){
        try {
            // Esperar a que la página esté completamente cargada
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));

            // Pequeña pausa adicional para asegurar que todo el contenido dinámico esté cargado
            Thread.sleep(2000);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destinationFile = new File(CC_Variables_Globales.PATH_CAPTURA + var+"_"+timestamp+".jpg");
            FileHandler.copy(screenshot, destinationFile);
        }catch (NoSuchSessionException | IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void capturaPantallaCompleta(String nombreArchivo) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String ruta = CC_Variables_Globales.PATH_CAPTURA + nombreArchivo + "_" + timestamp + ".png";
            ImageIO.write(screenFullImage, "png", new File(ruta));
            //System.out.println("Captura de pantalla completa guardada en: " + ruta);
        } catch (AWTException | IOException ex) {
            ex.printStackTrace();
        }
    }

     	public void capturaPantallaCompletaF(String nombreBase, String textoAdicional) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

            String textoFormateado = (textoAdicional != null && !textoAdicional.isEmpty())
                ? "_" + textoAdicional.replaceAll("\\s+", "_")
                : "";

            String ruta = CC_Variables_Globales.PATH_CAPTURA + nombreBase + textoFormateado + "_" + timestamp + ".png";
            ImageIO.write(screenFullImage, "png", new File(ruta));
        } catch (AWTException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setDriver(WebDriver webDriver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDriver'");
    }

    public void focusElemento(By locator) {
        try {
            WebElement elemento = driver.findElement(locator);// convierte el locator a un elemento web
            Actions actions = new Actions(driver);  //hace el llamado a la libreria de acciones
            actions.moveToElement(elemento).perform();// mueve el mouse al elemento y lo enfoca
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enfocar el elemento: " + e.getMessage());
        }
    }

    public String obtenerSrc(WebElement img) {
        return img.getAttribute("src");
    }

    public void waitLoading(By locator){
        // Espera a que el loader desaparezca antes de hacer click
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

}
