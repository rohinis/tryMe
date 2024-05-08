import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.relevantcodes.extentreports.LogStatus as LogStatus
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.remote.RemoteWebElement as RemoteWebElement

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

//========================================================================================================
WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

//=========================================================================================================
WebUI.delay(2)



try {
//	CustomKeywords.'todelete_preReq_Old.jobMonitorigColFilter.addColumn'(extentTest)
    WebUI.delay(2)

    WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

    extentTest.log(LogStatus.PASS, 'Click on Jobs tab')

    WebUI.delay(2)

    WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

    extentTest.log(LogStatus.PASS, 'Click on the  reset')

	

	TestObject newJobFilterCategoryRight = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjFilterCategoryIdentifierRight'(
		FilterCategory)

	TestObject newJobFilterTitle = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Title_FilterCategory'),
		'text', 'equals', FilterTitle, true)


isElementPresentRight = WebUI.waitForElementPresent(newJobFilterCategoryRight, 3, FailureHandling.CONTINUE_ON_FAILURE)



	println(isElementPresentRight)

	println('**************************')

	WebUI.delay(4)
	WebUI.scrollToElement(newJobFilterTitle, 3)

	
	if (isElementPresentRight) {
		println('right')

		WebUI.click(newJobFilterTitle)

		extentTest.log(LogStatus.PASS, 'Selected filter category ' + FilterTitle)
		extentTest.log(LogStatus.PASS,'Expand the Filterif its collapsed')
		
		
	}
	WebUI.delay(5)
    TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals', jobfilter, true)

    WebUI.click(newJobFilter)

    WebUI.delay(2)

    extentTest.log(LogStatus.PASS, 'SElect the JobFilter  - ' + jobfilter)

    println(jobfilter)
	extentTest.log(LogStatus.PASS, "  "+message)
	WebUI.delay(2)
	
	CustomKeywords.'operations_JobsModule.GetJobRowDetails.newGrid'(katalonWebDriver,colid, filtername, extentTest)
	
}
catch (Exception ex) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

    extentTest.log(LogStatus.FAIL, ex)

    KeywordUtil.markFailed('ERROR: ' + e)
} 
catch (StepErrorException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

    extentTest.log(LogStatus.FAIL, e)

    KeywordUtil.markFailed('ERROR: ' + e)
} 
finally { 
    extent.endTest(extentTest)

    extent.flush()
}

