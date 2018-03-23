package com.automation.accelerators;

import com.automation.support.IFrameworkConstant;
import com.automation.support.TestProperties;
import com.automation.utilities.AccessJsonFile;
import com.beust.jcommander.Parameter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.automation.report.CReporter;
import com.automation.report.ReporterConstants;
import com.automation.support.MyListener;


public class TestEngineWeb {
    protected AppiumDriver appiumDriver = null;
    public WebDriver WebDriver = null;
    public EventFiringWebDriver Driver = null;
    public RemoteWebDriver RDriver = null;
    protected CReporter reporter = null;

    /*cloud platform*/
    public String browser = null;
    public String version = null;
    public String platform = null;
    public String environment = null;
    public String localBaseUrl = null;
    public String cloudBaseUrl = null;
    public String userName = null;
    public String accessKey = null;
    public String cloudImplicitWait = null;
    public String cloudPageLoadTimeOut = null;
    public String updateJira = null;
    public String buildNumber = "";
    public String jobName = "";
    public String executedFrom = null;
    public String executionType = null;
    public String suiteExecution = null;
    public String suiteStartTime = null;
    public String ExtentReportDirectoryPath = System.getProperty("user.dir") + "\\extent_reports\\";
    public String reportFileName = new SimpleDateFormat("yyyy-M-dd-HH-mm'_index.html'").format(new Date());
    public String timeStamp = new SimpleDateFormat("MMddHHmm").format(new Date());
    public String ExtentReportPath = ExtentReportDirectoryPath + reportFileName;
    public static ExtentReports extent;
    public ExtentTest logger;
    public static AccessJsonFile userDataFile;



    /**/

    //private DesiredCapabilities capabilitiesForAppium = new DesiredCapabilities();


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws Throwable {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
        String formattedDate = sdf.format(date);
        suiteStartTime = formattedDate.replace(":", "_").replace(" ", "_");
        System.out.println("Suite time ==============>" + suiteStartTime);
        extent = new ExtentReports(ExtentReportPath, true);
        extent.addSystemInfo("Base URL", IFrameworkConstant.baseUrl);
        extent.loadConfig(new File(System.getProperty("user.dir") + "\\configurations\\extent_reports_config.xml"));
        if (IFrameworkConstant.defaultUserDataFile) {
            userDataFile = new AccessJsonFile();
        } else {
            //customizedUserDataFile should be handled here
            userDataFile = new AccessJsonFile(IFrameworkConstant.customizedUserDataFile);
        }

        //If required delete old reports or movie it to old folder
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"automationName", "browser", "browserVersion", "environment", "platformName"})
    public void beforeClass(String automationName, String browser, String browserVersion, String environment, String platformName) throws IOException, InterruptedException {

        /*get configuration */
        this.browser = browser;
        this.version = browserVersion;
        this.platform = platformName;
        this.environment = environment;
        this.localBaseUrl = IFrameworkConstant.baseUrl;

        this.userName = ReporterConstants.SAUCELAB_USERNAME;
        this.accessKey = ReporterConstants.SAUCELAB_ACCESSKEY;
        this.executedFrom = System.getenv("COMPUTERNAME");
        this.cloudImplicitWait = ReporterConstants.CLOUD_IMPLICIT_WAIT;
        this.cloudPageLoadTimeOut = ReporterConstants.CLOUD_PAGELOAD_TIMEOUT;
        this.updateJira = "";



        /**/
        System.out.println(environment);

        if (environment.equalsIgnoreCase("local")) {
            this.setWebDriverForLocal(browser);
        }
        if (environment.equalsIgnoreCase("cloudSauceLabs")) {

            this.setRemoteWebDriverForCloudSauceLabs();
        }
        if (environment.equalsIgnoreCase("grid")) {
            this.setWebdriverForGrid(browser);
        }
        if (environment.equalsIgnoreCase("cloudSauceLabsJenkins")) {
            this.updateConfigurationForCloudSauceLabsJenkins();
            /*set remoteWebDriver for cloudsaucelabs*/

            this.setRemoteWebDriverForCloudSauceLabs();
        }


        if (environment.equalsIgnoreCase("cloudBrowserStackJenkins")) {
            /*TBD: Not Implemented For Running Using Jenkins*/
            this.updateConfigurationForCloudBrowserStackJenkins();
        }
        reporter = CReporter.getCReporter(browser, platformName, environment, true);

        //String Node = "http://10.112.66.52:5555/wd/hub";

        this.Driver = new EventFiringWebDriver(this.WebDriver);
        MyListener myListener = new MyListener();
        this.Driver.register(myListener);
        extent.addSystemInfo("Browser", browser);
        Driver.get(IFrameworkConstant.baseUrl);
        Driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        //Driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Driver.manage().window().maximize();
        Driver.manage().deleteAllCookies();
        reporter.calculateSuiteStartTime();
    }


    @Parameters({"browser"})
    @AfterClass(alwaysRun = true)
    public void afterClass(String browser) throws Exception {
        if (browser.equalsIgnoreCase("firefox")) {
            Driver.quit();
        } else {
            Driver.quit();
        }
        //Driver.close();

        reporter.calculateSuiteExecutionTime();
        reporter.createHtmlSummaryReport(IFrameworkConstant.baseUrl, true);
        reporter.closeSummaryReport();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        reporter.initTestCase(this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf(".")), method.getName(), method.getAnnotation(Test.class).description(), true);
        logger = extent.startTest(method.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException, NoSuchMethodException {
        String tetCaseID = this.getClass().getDeclaredMethod(result.getName()).getAnnotation(TestProperties.class).testID();
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, "Test case failed is: " + result.getName());
            logger.log(LogStatus.FAIL, result.getThrowable());
            String screenShotPath = capture(Driver, "screenShot" + timeStamp);
            System.out.println("screenShotPath====>" + screenShotPath);
            String image = logger.addScreenCapture(screenShotPath);
            logger.log(LogStatus.FAIL, "Snapshot below: " + image);
            System.out.println("#############" + tetCaseID + ": FAILED    ##########");
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.FAIL, "Test case skipped is: " + result.getName());
            System.out.println("#############" + tetCaseID + ": SKIP    ##########");
        }
        System.out.println("#############" + tetCaseID + ": PASS    ##########");
        extent.endTest(logger);
        extent.flush();
        reporter.calculateTestCaseExecutionTime();
        reporter.closeDetailedReport();
        reporter.updateTestCaseStatus();
    }


    public void setWebdriverForGrid(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        if (browser.equalsIgnoreCase("IE")) {
            caps = DesiredCapabilities.internetExplorer();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            caps = DesiredCapabilities.firefox();
        } else if (browser.equalsIgnoreCase("chrome")) {
            caps = DesiredCapabilities.chrome();
        } else {
            caps = DesiredCapabilities.safari();
        }
        String Node = "http://172.16.28.74:4444/wd/hub";
        //URL commandExecutorUri = new URL("http://ondemand.saucelabs.com/wd/hub");
        this.WebDriver = new RemoteWebDriver(new URL(Node), caps);
    }

    private void setWebDriverForLocal(String browser) throws IOException, InterruptedException {
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
                this.WebDriver = new FirefoxDriver();
                break;
            case "ie":

                DesiredCapabilities capab = DesiredCapabilities.internetExplorer();
                capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capab.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
                //capab.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, INIT_PAGE);

                File file = new File("drivers\\IE32BIT\\IEDriverServer.exe");
                System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capab.setJavascriptEnabled(true);
                capab.setCapability("requireWindowFocus", true);
                capab.setCapability("enablePersistentHover", false);

                this.WebDriver = new InternetExplorerDriver(capab);

                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("test-type");
                options.addArguments("--start-maximized");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                this.WebDriver = new ChromeDriver(capabilities);

                break;
            case "Safari":
                WebDriver = new SafariDriver();

        }

    }

    private void setRemoteWebDriverForCloudSauceLabs() throws IOException, InterruptedException {
        if (this.browser.equalsIgnoreCase("Safari")) {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, this.browser);
            desiredCapabilities.setCapability(CapabilityType.VERSION, this.version);
            desiredCapabilities.setCapability(CapabilityType.PLATFORM, this.platform);
            desiredCapabilities.setCapability("username", this.userName);
            desiredCapabilities.setCapability("accessKey", this.accessKey);
            desiredCapabilities.setCapability("accessKey", this.accessKey);
            desiredCapabilities.setCapability("name", this.executedFrom + " - " /*+ this.jobName + " - " + this.buildNumber*/ + this.platform + " - " + this.browser);
            URL commandExecutorUri = new URL("http://ondemand.saucelabs.com/wd/hub");
            for (int i = 1; i <= 10; i++) {

                try {
                    this.WebDriver = new RemoteWebDriver(commandExecutorUri, desiredCapabilities);

                    break;
                } catch (Exception e1) {
                    Runtime.getRuntime().exec("taskkill /F /IM Safari.exe");
                    Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
                    Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");

                    continue;

                }
            }
        } else {

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, this.browser);
            desiredCapabilities.setCapability(CapabilityType.VERSION, this.version);
            desiredCapabilities.setCapability(CapabilityType.PLATFORM, this.platform);
            desiredCapabilities.setCapability("username", this.userName);
            desiredCapabilities.setCapability("accessKey", this.accessKey);
            desiredCapabilities.setCapability("accessKey", this.accessKey);
            desiredCapabilities.setCapability("name", this.executedFrom + " - " /*+ this.jobName + " - " + this.buildNumber*/ + this.platform + " - " + this.browser);
            URL commandExecutorUri = new URL("http://ondemand.saucelabs.com/wd/hub");
            this.WebDriver = new RemoteWebDriver(commandExecutorUri, desiredCapabilities);
        }
    }

    private void updateConfigurationForCloudSauceLabsJenkins() {
        this.browser = System.getenv("SELENIUM_BROWSER");
        this.version = System.getenv("SELENIUM_VERSION");
        this.platform = System.getenv("SELENIUM_PLATFORM");
        this.userName = System.getenv("SAUCE_USER_NAME");
        this.accessKey = System.getenv("SAUCE_API_KEY");
        this.buildNumber = System.getenv("BUILD_NUMBER");
        this.jobName = System.getenv("JOB_NAME");
    }

    /*TBD: Not Implemented For Running Using Jenkins*/
    private void updateConfigurationForCloudBrowserStackJenkins() {

    }


    public String capture(WebDriver driver, String screenShotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String epoch2 = String.valueOf(System.currentTimeMillis() / 1000);
        String reportPath = "/extent_reports/screenshots/" + screenShotName + "_" + epoch2 + "_" + ".png";
        String dest = System.getProperty("user.dir") + reportPath;
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);
        return ".." + reportPath;
    }

    public EventFiringWebDriver getDriver() {
        return this.Driver;
    }

    public CReporter getReporter() {
        return this.reporter;
    }

    public ExtentTest getLogger() {
        return this.logger;
    }
}
