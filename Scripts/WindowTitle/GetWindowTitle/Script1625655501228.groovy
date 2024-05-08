import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys
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



WebDriver driver = DriverFactory.getWebDriver()


ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//String screenShot='ExtentReports/'+TestCaseName+userChoice+GlobalVariable.G_Browser+'.png'
TestObject newFileObj

try {
	
		WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
		extentTest.log(LogStatus.PASS, 'Click on profile tab')
		WebUI.delay(2)
		
		WebUI.click(findTestObject('Landing_Page/ListItem_Logout'))
		extentTest.log(LogStatus.PASS, 'Click on logout')
		
		WebUI.verifyElementVisible(findTestObject('LogOut-PopUp/Title_Logout'))
		
		WebUI.delay(2)
		
		WebUI.click(findTestObject('GenericObjects/btn_Yes'))
		extentTest.log(LogStatus.PASS, 'Click on yes button')
		WebUI.delay(2)
		
		def title = WebUI.getWindowTitle()
		println(title)
		if(title== "Logout-Altair Access") {
		
		extentTest.log(LogStatus.PASS, 'Browser page  -  ' + title)}
		else
			extentTest.log(LogStatus.FAIL, 'Failed to verify the title')
		
		
	
	
	if (TestCaseName.contains('Login'))
	{
		WebUI.click(findTestObject('AppComposer/Login'))
		extentTest.log(LogStatus.PASS, 'Click on Login again')
		
		def title1 = WebUI.getWindowTitle()
		println(title1)
		extentTest.log(LogStatus.PASS, 'Browser page  -  ' + title1)
	}
   
    if (GlobalVariable.G_Browser == 'Edge') {
        WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
    }
}
catch (Exception ex) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

  	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


    KeywordUtil.markFailed('ERROR: ' + e)
} 
catch (StepErrorException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

 	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


    KeywordUtil.markFailed('ERROR: ' + e)
} 
finally { 
    extent.endTest(extentTest)

    extent.flush()
}

