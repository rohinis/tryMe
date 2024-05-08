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

def Browser = GlobalVariable.G_Browser
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
//=====================================================================================

try {
	if (Browser == 'Edge Chromium') {
		WebUI.click(findTestObject('Object Repository/GenericObjects/btn_EdgeChromium_Details_link'))
		WebUI.delay(3)
		WebUI.click(findTestObject('Object Repository/GenericObjects/link_EdgeChromium_proceed_link'))
		WebUI.delay(3)
		WebUI.deleteAllCookies()
	}
	if (Browser == 'IE') {
		WebUI.click(findTestObject('Object Repository/GenericObjects/IE_Details_Link'))
		WebUI.delay(3)
		WebUI.waitForElementVisible(findTestObject('Object Repository/GenericObjects/EdgeProceedeLink'), 2)
		WebUI.click(findTestObject('Object Repository/GenericObjects/EdgeProceedeLink'))
		WebUI.delay(3)
	}

	WebUI.setText(findTestObject('LoginPage/username_txtbx'), username)
	WebUI.setText(findTestObject('LoginPage/password_txtbx'), password)
	WebUI.click(findTestObject('LoginPage/login_btn'))
	extentTest.log(LogStatus.PASS, 'Entered Creds - username - '+username +' password - '+password)

	extentTest.log(LogStatus.PASS, 'Clicked on Login Button ')


	def jobsTab = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/Landing_Page/LandigPage_AltairAccess_Link'),
			10,extentTest, 'AltairAccess Logo ')

	if (jobsTab) {
		WebUI.click(findTestObject('Object Repository/Landing_Page/LandigPage_AltairAccess_Link'))
	}

	extentTest.log(LogStatus.PASS, 'Verified AltairAccess Logo post login ')


	//click on the user menu
	WebUI.click(findTestObject('Object Repository/change_password/unityuser'))

	//click on the change passwd option
	WebUI.click(findTestObject('Object Repository/change_password/changepass_menu'))

	extentTest.log(LogStatus.PASS, 'Clicked on the change password icon  ')

	//verify the title
	println(WebUI.verifyElementPresent(findTestObject('Object Repository/change_password/title'), 20))

	String title = WebUI.getText(findTestObject('Object Repository/change_password/title'))

	println('Error message******' + title)

	extentTest.log(LogStatus.PASS, ('Verify the Title ' + title) + 'is present')

	//set the old password
	WebUI.setText(findTestObject('change_password/oldpasswd'), oldpassword)

	extentTest.log(LogStatus.PASS, 'Entered oldpassword::' + oldpassword)
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
	TestObject s_msg = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notificationlistitem'), 'data-tip', 'contains', ErrorMessage1, true)

	println(WebUI.verifyElementPresent(s_msg, 4))

	String msg = WebUI.getText(s_msg)
	println("**************" +msg)

	extentTest.log(LogStatus.PASS, msg)

	//logout from the Application
	WebUI.delay(1)

	WebUI.click(findTestObject('Object Repository/change_password/unityuser'))

	WebUI.click(findTestObject('Object Repository/LogOut-PopUp/logout'))

	WebUI.waitForElementVisible(findTestObject('Object Repository/LogOut-PopUp/ok_btn'), 20)

	WebUI.click(findTestObject('Object Repository/LogOut-PopUp/ok_btn'))

	extentTest.log(LogStatus.PASS, 'Logged out from the Application')


	WebUI.click(findTestObject('Object Repository/LoginPage/Loginagain'))

	WebUI.delay(70)

	WebUI.setText(findTestObject('LoginPage/txtBx_userName'), username)
	extentTest.log(LogStatus.PASS, 'Entered username::' + username)

	WebUI.setText(findTestObject('LoginPage/txtBx_passwd'),password)
	extentTest.log(LogStatus.PASS, 'Entered password::' + password)

	extentTest.log(LogStatus.PASS, 'Login again with the same old password' + password)

	WebUI.delay(1)

	WebUI.click(findTestObject('LoginPage/btn_loginBtn'))
	WebUI.delay(15)

	WebUI.waitForElementVisible(findTestObject('Object Repository/LoginPage/login_error'), 20)

	extentTest.log(LogStatus.PASS, 'username or password is incorrect')
	WebUI.refresh()
	WebUI.delay(2)

	WebUI.setText(findTestObject('LoginPage/txtBx_userName'), username)
	extentTest.log(LogStatus.PASS, 'Entered username::' + username)

	WebUI.setText(findTestObject('LoginPage/txtBx_passwd'),newpassword)
	extentTest.log(LogStatus.PASS, 'Entered password::' + newpassword)
	WebUI.delay(1)

	WebUI.click(findTestObject('LoginPage/btn_loginBtn'))

	extentTest.log(LogStatus.PASS, 'Logged in  with the new password' + newpassword)

jobsTab = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/Landing_Page/LandigPage_AltairAccess_Link'),
			10,extentTest, 'AltairAccess Logo ')

	if (jobsTab) {
		WebUI.click(findTestObject('Object Repository/Landing_Page/LandigPage_AltairAccess_Link'))
		extentTest.log(LogStatus.PASS, 'Jobs page is loaded')
		extentTest.log(LogStatus.PASS, ('Verified ::  ' + TestCaseName) + ' :: Sucessfully')
	} else {
		extentTest.log(LogStatus.FAIL, ( TestCaseName) + ' :: failed')
	}

}catch (Exception ex) {
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

