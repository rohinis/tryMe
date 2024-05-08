import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject



import org.openqa.selenium.Keys

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
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
String screenShot='ExtentReports/'+TestCaseName+userChoice+GlobalVariable.G_Browser+'.png'
def result
WebUI.delay(2)


try
{
	WebUI.delay(2)


	WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
	extentTest.log(LogStatus.PASS, 'Click on profile tab')
	WebUI.delay(2)

	WebUI.click(findTestObject('AppComposer/Appcomposer'))
	extentTest.log(LogStatus.PASS, 'Click on App composer')

	//println("userchoice ++++++"+userChoice+"++++++++++++")
	
	boolean hpcclusterTmp=	WebUI.waitForElementVisible(findTestObject('Object Repository/AppComposer/hpcluster_temp'), 10)
	if(hpcclusterTmp) {
		
		WebUI.click(findTestObject('Object Repository/AppComposer/hpcluster_temp'))
		WebUI.mouseOver(findTestObject('Object Repository/AppComposer/hpcluster_temp'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/AppComposer/NewApp_temp'))
		extentTest.log(LogStatus.PASS, 'Clicked on new Appcomposer')
	}

	switch(userChoice)
	{

		case 'Publish':
		CustomKeywords.'appcomposer.Create_NewApp.create_app'(app,exec,extentTest)

			
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/Publish'))
			extentTest.log(LogStatus.PASS, 'Click on the Publish  button')
			WebUI.delay(5)
			result=WebUI.verifyElementPresent(findTestObject('AppComposer/PublishAdmin'), 3)
			if (result)
			{

				extentTest.log(LogStatus.PASS, 'Verify the pop up Do you want to publish this application to admin user ')
			} else {
				extentTest.log(LogStatus.FAIL, 'Failed to verify the popup')
			}




			break

		case 'verifyNonAdmin ':
		WebUI.delay(2)

		
		TestObject appdefname = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppComposer/application_name'),'data-automation-id', 'equals', app, true)
		WebUI.delay(2)
		WebUI.click(appdefname)
		extentTest.log(LogStatus.PASS, 'Select the Existing app def '+ app)
		WebUI.delay(3)
		

			result=WebUI.verifyElementNotPresent(findTestObject('AppComposer/Publishall'), 3)
			if (result)
			{

				extentTest.log(LogStatus.PASS, 'Verify Publish to all button not present for non admin user')
			} else {
				extentTest.log(LogStatus.FAIL, 'Failed to verify the publish all button')
			}
			break
	}

	if (result)
	{
		extentTest.log(LogStatus.PASS, ('Verified ::  ' + TestCaseName) + ' :: Sucessfully')
	} else {
		extentTest.log(LogStatus.FAIL, ( TestCaseName) + ' :: failed')
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







