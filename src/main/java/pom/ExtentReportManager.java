package pom;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ExtentReportManager {

    private static ExtentReports extent;
    private static final String REPORT_PATH = System.getProperty("user.dir") + "/test-output/ExtentReports/";

    public static ExtentReports getInstance() {
        if (extent == null) {
            String fileName = "TestReport_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".html";
            createReportDirectory();
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORT_PATH + fileName);
            
            htmlReporter.config().setDocumentTitle("Informe de Pruebas Automatizadas");
            htmlReporter.config().setReportName("Resultados de Testing con Selenium");
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
            
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Sistema Operativo", System.getProperty("os.name"));
            extent.setSystemInfo("Ambiente", "QA");
            extent.setSystemInfo("Ingeniero de Pruebas", "Carlos Torres");
        }
        return extent;
    }

    private static void createReportDirectory() {
        File directory = new File(REPORT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }


}
