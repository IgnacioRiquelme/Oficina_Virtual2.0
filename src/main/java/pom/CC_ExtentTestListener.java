package pom;

/*import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class CC_ExtentTestListener implements ITestListener{
    
    private static ExtentReports extent = new ExtentReports();
    private static ExtentTest test;
    private static ExtentHtmlReporter htmlReporter;

    @Override
    public void onStart(ITestContext context) {
        // Configura el reporte HTML
        htmlReporter = new ExtentHtmlReporter("extent-report.html");
        htmlReporter.config().setDocumentTitle("Reporte de Tests Automatizados");
        htmlReporter.config().setReportName("Resumen de Ejecuciones");
        extent.attachReporter(htmlReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Inicia un nuevo test en el reporte
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test aprobado exitosamente");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test falló: " + result.getThrowable());
        // Opcional: Agrega una captura de pantalla aquí
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test omitido");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Finaliza y genera el reporte
        extent.flush();
    }


}*/
