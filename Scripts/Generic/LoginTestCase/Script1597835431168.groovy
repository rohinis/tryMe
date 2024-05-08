import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

/*WebUI.openBrowser('')
 WebUI.navigateToUrl(GlobalVariable.G_BaseUrl)
 WebUI.delay(3)
 WebUI.maximizeWindow()*/
def Browser = GlobalVariable.G_Browser

String ReportFile = GlobalVariable.G_ReportName + '.html'

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)
try {

	if (Browser == 'Edge Chromium') {
		WebUI.click(findTestObject('Object Repository/GenericObjects/btn_EdgeChromium_Details_link'))

		WebUI.delay(3)

		WebUI.click(findTestObject('Object Repository/GenericObjects/link_EdgeChromium_proceed_link'))

		WebUI.delay(3)
		WebUI.deleteAllCookies()
	}
	else if (Browser == 'IE') {

		WebUI.click(findTestObject('Object Repository/GenericObjects/IE_Details_Link'))

		WebUI.delay(3)
		WebUI.waitForElementVisible(findTestObject('Object Repository/GenericObjects/EdgeProceedeLink'), 2)
		WebUI.click(findTestObject('Object Repository/GenericObjects/EdgeProceedeLink'))

		WebUI.delay(3)
	}
	extentTest.log(LogStatus.PASS, 'Navigated to url - ' +GlobalVariable.G_BaseUrl)
	
	WebUI.setText(findTestObject('LoginPage/username_txtbx'), username)
	extentTest.log(LogStatus.PASS, 'Entered Username  - ' +username)
	
	WebUI.setText(findTestObject('LoginPage/password_txtbx'), password)
	extentTest.log(LogStatus.PASS, 'Entered Password  - ' +password)
	
	WebUI.click(findTestObject('LoginPage/login_btn'))
	extentTest.log(LogStatus.PASS, 'Clicked Login Button')
	
		
	WebUI.delay(2)
	switch(userChoice)
	{
		case "Valid":

			def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'),
			10)

			if (jobsTab) {
				WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
			}
			break

		case "Invalid":
			def errUserName = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/LoginPage/ErrorMsg_Incorrect_Username_or_Password'),
			2)

			if (errUserName) {
				extentTest.log(LogStatus.PASS, 'Error msg is displyed for Incorrect Username or Password')
			}
			break;


			case "Blank":
			def errBlank = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/LoginPage/ErrorMsg_Incorrect_Username_or_Password'),
			2)

			if (errBlank) {
				extentTest.log(LogStatus.PASS, 'Error msg is displyed for Incorrect Username or Password')
			}
			break;

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