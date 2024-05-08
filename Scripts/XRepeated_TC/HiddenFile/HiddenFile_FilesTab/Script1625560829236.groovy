import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.junit.Assert.*

import org.junit.Test
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/HiddenItems/'

//=====================================================================================
def result

try {

	extentTest.log(LogStatus.PASS, 'prefValue --- '+prefValue)

	CustomKeywords.'customWait.setPrefrenceHidden.setprefrence'(prefValue, extentTest)
	extentTest.log(LogStatus.PASS, 'Setting the prefrence to view hidden files')
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20, extentTest, 'Files Tab')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}
	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
	WebUI.delay(2)

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

	WebUI.delay(2)
	println(TestCaseName)
	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)

	result=CustomKeywords.'customWait.setPrefrenceHidden.checkHiddenItems'(prefValue, TestCaseName, extentTest)

	if (result) {
		extentTest.log(LogStatus.PASS, ('Hidden file - ' + fileName) + 'is listed after log out and login ')
	} else {
		extentTest.log(LogStatus.FAIL, ('Hidden file - ' + fileName) + 'is listed ')
	}

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
	WebUI.click(findTestObject('AppComposer/Login'))
	extentTest.log(LogStatus.PASS, 'Click on Login again')

	WebUI.setText(findTestObject('LoginPage/username_txtbx'), GlobalVariable.G_userName)
	extentTest.log(LogStatus.PASS, 'Enter username -  '+ GlobalVariable.G_userName)
	WebUI.setText(findTestObject('LoginPage/password_txtbx'), GlobalVariable.G_Password)
	extentTest.log(LogStatus.PASS, 'Enter  password  - '+ GlobalVariable.G_Password)
	WebUI.click(findTestObject('LoginPage/login_btn'))
	extentTest.log(LogStatus.PASS, 'Click on Login')
	WebUI.delay(2)

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')

	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)

	result=CustomKeywords.'customWait.setPrefrenceHidden.checkHiddenItems'(prefValue, TestCaseName, extentTest)
	if (result) {
		extentTest.log(LogStatus.PASS, ('Hidden file - ' + fileName) + 'is listed after log out and login ')
	} else {
		extentTest.log(LogStatus.FAIL, ('Hidden file - ' + fileName) + 'is listed ')
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
	extentTest.log(LogStatus.PASS, 'Closing the browser after executinge test case - ' + TestCaseName)

	extent.endTest(extentTest)

	extent.flush()
}


