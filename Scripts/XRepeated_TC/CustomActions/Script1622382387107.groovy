import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================


def result

def isElementPresentRight

def isElementPresentDown

WebUI.delay(2)

try {

	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}

	WebUI.delay(2)

	def isFilterPresent= CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/JobMonitoringPage/icon_removeFilter'),5,extentTest,'RemoveFilterIcon')
	if(isFilterPresent)
	{
		WebUI.click(findTestObject('Object Repository/JobMonitoringPage/icon_removeFilter'))
		extentTest.log(LogStatus.PASS, 'Clicked on filter delete icon' )
		WebUI.refresh()
	}
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))


	TestObject newJobFilterCategoryDown = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjFilterCategoryIdentifierDown'(
			FilterCategory)

	TestObject newJobFilterCategoryRight = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjFilterCategoryIdentifierRight'(
			FilterCategory)

	TestObject newJobFilterTitle = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Title_FilterCategory'),
			'text', 'equals', FilterTitle, true)

	TestObject newJobFilterValue = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Title_FilterCategory'),
			'text', 'equals', FilterValue, true)

	isElementPresentDown = WebUI.waitForElementPresent(newJobFilterCategoryDown, 3, FailureHandling.CONTINUE_ON_FAILURE)

	isElementPresentRight = WebUI.waitForElementPresent(newJobFilterCategoryRight, 3, FailureHandling.CONTINUE_ON_FAILURE)

	println('**************************')

	println(isElementPresentDown)

	println(isElementPresentRight)

	println('**************************')

	WebUI.delay(4)
	WebUI.scrollToElement(newJobFilterTitle, 3)

	if (isElementPresentDown) {
		println('down')

		WebUI.click(newJobFilterValue)
		extentTest.log(LogStatus.PASS,'Verification for AD-1418 - Expand and Collapse the left navigation filters')

	}

	if (isElementPresentRight) {
		println('right')

		WebUI.click(newJobFilterTitle)

		extentTest.log(LogStatus.PASS, 'Selected filter category ' + FilterTitle)
		extentTest.log(LogStatus.PASS,'Verification for AD-1418 - Expand and Collapse the left navigation filters')

		WebUI.delay(2)

		WebUI.click(newJobFilterValue)

		extentTest.log(LogStatus.PASS, 'Selected filter value ' + FilterValue)

		extentTest.log(LogStatus.PASS,'Verification for AD-1419 - Check an Un-check the left navigation filters')
	}

	WebUI.rightClick(findTestObject('Object Repository/tryPAWFolder/tryPAW'))
	TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),'id', 'equals', 'View Details', true)
	WebUI.delay(2)
	WebUI.click(newJobAction)
	WebUI.delay(2)

	
	WebUI.click(findTestObject('Object Repository/JobDetailsPage/Btn_CustomActions'))
	WebUI.click(findTestObject('Object Repository/JobDetailsPage/MenuItem_SendSignal'))
	WebUI.click(findTestObject('Object Repository/JobDetailsPage/Btn_SendSignal_DropDown'))
	WebUI.click(	findTestObject('Object Repository/JobDetailsPage/ListItem_Print'))
	
	
	WebUI.click(findTestObject('Object Repository/2020.1/Okbuttoon'))
	
	
	WebUI.click(findTestObject('Object Repository/JobDetailsPage/DetailsTab_Console'))
	
	
	def out=WebUI.getText(findTestObject('Object Repository/JobDetailsPage/TabArea_stdout'))
	println(out)
	
	println(('==================' + result) + '==========================')

	if (result) {
		extentTest.log(LogStatus.FAIL, 'Job Not Filtered for ' + FilterValue)
	} else {
		extentTest.log(LogStatus.PASS, ('Job  Filtered for ' + FilterValue) + ' Jobs')
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

