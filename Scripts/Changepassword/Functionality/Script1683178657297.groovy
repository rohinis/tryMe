import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import com.relevantcodes.extentreports.LogStatus as LogStatus
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

/*def Browser = GlobalVariable.G_Browser

String ReportFile = GlobalVariable.G_ReportName + '.html'

def extent = CustomKeywords.'reports.Generatereport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)*/

try {

	WebUI.delay(1)

	extentTest.log(LogStatus.PASS, 'Navigated to url - ' + GlobalVariable.G_BaseUrl)

	//click on the user menu
	WebUI.click(findTestObject('Object Repository/change_password/unityuser'))

	//click on the change passwd option
	WebUI.click(findTestObject('Object Repository/change_password/changepass_menu'))

	extentTest.log(LogStatus.PASS, 'Clicked on the change password icon  ')

	//verify the title
	println(WebUI.verifyElementPresent(findTestObject('Object Repository/change_password/title'), 20))

	String title = WebUI.getText(findTestObject('Object Repository/change_password/title'))
extentTest.log(LogStatus.PASS, ('Verify the Title ' + title) + 'is present')

	//set the old password
	WebUI.setText(findTestObject('change_password/oldpasswd'), GlobalVariable.G_Password)

	extentTest.log(LogStatus.PASS, 'Entered oldpassword::' + GlobalVariable.G_Password)
	WebUI.delay(1)

	WebUI.setText(findTestObject('Object Repository/change_password/newpasswd'),newpassword )

	extentTest.log(LogStatus.PASS, 'Entered new password::' + newpassword)
	WebUI.delay(1)

	WebUI.setText(findTestObject('Object Repository/change_password/confrmpasswd'), confirmpassword)

	extentTest.log(LogStatus.PASS, 'Entered Confirm password::' + confirmpassword)
	WebUI.delay(5)

	WebUI.click(findTestObject('Object Repository/change_password/button'))
	WebUI.delay(1)

	extentTest.log(LogStatus.PASS, 'Clicked on the change button')
	WebUI.delay(10)

	//click on the notification
	WebUI.click(findTestObject('Object Repository/Notificactions/Notification'))

	extentTest.log(LogStatus.PASS, 'Clicked on the Notification tab')

	//verify the error message
	TestObject s_msg = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notificationlistitem'),
		'data-tip', 'contains', ErrorMessage1, true)

	println(WebUI.verifyElementPresent(s_msg, 4))

	String msg = WebUI.getText(s_msg)


	extentTest.log(LogStatus.PASS, msg)

}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'

	extentTest.log(LogStatus.FAIL, ex)

	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))

	KeywordUtil.markFailed('ERROR: ' + ex)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'

	extentTest.log(LogStatus.FAIL, ex)

	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()
}





