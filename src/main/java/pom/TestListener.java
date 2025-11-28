package pom;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;





public class TestListener implements ITestListener{

    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // Se ejecuta una vez por cada clase de prueba
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Crear una nueva entrada para cada test
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass(MarkupHelper.createLabel("Test aprobado", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(MarkupHelper.createLabel("Test fallido", ExtentColor.RED));
        test.get().fail(result.getThrowable());
        
        // Capturar screenshot si la prueba falla
        Object testInstance = result.getInstance();
        Class<?> testClass = result.getTestClass().getRealClass();
        try {
            WebDriver driver = getDriverInstance(testInstance, testClass);
            if (driver != null) {
                String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            test.get().info("No se pudo capturar la pantalla: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(MarkupHelper.createLabel("Test omitido", ExtentColor.YELLOW));
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Guardar el reporte al finalizar
        extent.flush();
    }

    // Utilidad para capturar screenshots
    private String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String screenshotFileName = testName + "_" + timestamp + ".png";
        String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + screenshotFileName;
        
        File directory = new File(System.getProperty("user.dir") + "/test-output/screenshots/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
            return "../screenshots/" + screenshotFileName; // Ruta relativa para el reporte
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para obtener la instancia del WebDriver
    private WebDriver getDriverInstance(Object testInstance, Class<?> testClass) {
        try {
            // Asumiendo que la clase de prueba tiene un campo 'driver'
            java.lang.reflect.Field driverField = testClass.getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(testInstance);
        } catch (Exception e) {
            return null;
        }
    }



}
