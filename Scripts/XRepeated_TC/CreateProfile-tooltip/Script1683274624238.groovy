import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

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
TestCaseName= TestCaseName + ' - '+proName
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()

//=====================================================================================

Robot rob = new Robot()


WebUI.delay(2)
try
{
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}

	WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals', AppName, true)
	TestObject LeftNavAppIdentifier = CustomKeywords.'buildTestObj.CreateTestObjJobs.myLeftNavAppIdentifier'(proName)
	WebUI.click(newAppObj)

	def GP = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'),2,extentTest,'JSPage')

	if (GP) {
		extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+AppName)		}

	else
	{
		extentTest.log(LogStatus.PASS, 'User not navigated to Job Submission For for - '+AppName)
	}


	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+AppName)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	WebUI.click(findTestObject('LoginPage/NewJobPage/Btn_Save As'))
	WebUI.waitForElementPresent(findTestObject('LoginPage/NewJobPage/label_Save Profile'), 5)
	WebUI.waitForElementPresent(findTestObject('LoginPage/NewJobPage/label_Save Profile'), 5)

	WebUI.clearText(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'))
	WebUI.sendKeys(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), proName)
	WebUI.click(findTestObject('LoginPage/NewJobPage/button_Save'))
	String  tooltip=WebUI.getAttribute(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), 'validationMessage')
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/button_Cancel'))
	WebUI.delay(1)
	String myXpath="//span[@title='"+proName+"']"
	List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
	println listElement.size()




	println(tooltip)

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