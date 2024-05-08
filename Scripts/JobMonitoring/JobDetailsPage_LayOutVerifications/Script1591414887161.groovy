import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

def result=false
WebUI.delay(2)
try
{
	WebUI.delay(2)
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
		20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}	
	
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	println jobState
	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	jobState, true)
	if(userChoice.equals('DoubleClick'))
	{
		WebUI.doubleClick(newJobRow)
		extentTest.log(LogStatus.PASS, 'Double Clicked on Job row to navigate to Details Page')

	}
	else
	{
		WebUI.rightClick(newJobRow)
		WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
		extentTest.log(LogStatus.PASS, 'Navigated to Details Page through context menu')
	}

	WebUI.delay(2)
	WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

	extentTest.log(LogStatus.PASS, 'Clicked on Jobs Link ')

	if(WebUI.verifyElementPresent(findTestObject('Object Repository/JobMonitoringPage/ShellScript_App'), 3))
	{
		extentTest.log(LogStatus.PASS, 'Navigated to Job Monitoring Page ')
	}

}
catch (Exception ex) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
    WebUI.takeScreenshot(screenShotPath)
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	} 
catch (StepErrorException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
    WebUI.takeScreenshot(screenShotPath)
    extentTest.log(LogStatus.FAIL, e)
} 
finally { 
    extentTest.log(LogStatus.PASS, 'Closing the browser after executinge test case - '+ TestCaseName)
    extent.endTest(extentTest)
    extent.flush()
}
//=====================================================================================
