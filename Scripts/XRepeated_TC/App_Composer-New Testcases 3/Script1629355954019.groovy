import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot
import java.awt.event.KeyEvent as KeyEvent

import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

/*
'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)
*/

WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
String screenShot='ExtentReports/'+TestCaseName+userChoice+GlobalVariable.G_Browser+'.png'
def result
WebUI.delay(2)
Robot rob = new Robot()

try
{
	WebUI.delay(2)
	//WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

	WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
	extentTest.log(LogStatus.PASS, 'Click on profile tab')
	WebUI.delay(2)

	WebUI.click(findTestObject('AppComposer/Appcomposer'))
	extentTest.log(LogStatus.PASS, 'Click on App composer')
	
	

	switch(userChoice)
	{
	
			
			
			case'Options':
			
			WebUI.click(findTestObject('AppComposer/AppName'))
			extentTest.log(LogStatus.PASS, 'Click to Entered App name')
			WebUI.doubleClick(findTestObject('AppComposer/AppName'))
			rob.keyPress(KeyEvent.VK_BACK_SPACE)
			rob.keyRelease(KeyEvent.VK_BACK_SPACE)
			WebUI.setText(findTestObject('AppComposer/AppName'), '')
			WebUI.setText(findTestObject('AppComposer/AppName'), app)
			extentTest.log(LogStatus.PASS, 'Entered App name -' + app)
			WebUI.click(findTestObject('AppComposer/Executableinput'))
			extentTest.log(LogStatus.PASS, 'Click to add exec commands')
			WebUI.setText(findTestObject('AppComposer/Executableinput'), '')
			WebUI.setText(findTestObject('AppComposer/Executableinput'), exec)
			extentTest.log(LogStatus.PASS, 'Add Exec commands - ' + exec)

			WebUI.click(findTestObject('AppComposer/Save'))
			extentTest.log(LogStatus.PASS, 'Click on Save button')
			WebUI.waitForElementPresent(findTestObject('Object Repository/AppComposer/AppSaved'), 5)
			
				def Text10 = WebUI.getText(findTestObject('Object Repository/AppComposer/AppSaved'))
			
				extentTest.log(LogStatus.PASS, 'Notification Generated ' + Text10)
			
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app ' + app +' created and click on ellipses')
			WebUI.verifyElementPresent(findTestObject('AppComposer/Edit'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Edit option is present')
			
			WebUI.verifyElementPresent(findTestObject('AppComposer/Copy'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Copy option is present')
			
			WebUI.verifyElementPresent(findTestObject('AppComposer/Share'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Share option is present')
			
			WebUI.verifyElementPresent(findTestObject('AppComposer/Delete'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Edit option is present')
			
			break
			
			

	}
	




	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}

}

catch (Exception  ex)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL,ex)
	KeywordUtil.markFailed('ERROR: '+ e)

}
catch (StepErrorException  e)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL,e)
	KeywordUtil.markFailed('ERROR: '+ e)

}
catch (StepFailedException e)
{
	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL,e)
	KeywordUtil.markFailed('ERROR: '+ e)
}
finally
{

	extent.endTest(extentTest);
	extent.flush();

}

