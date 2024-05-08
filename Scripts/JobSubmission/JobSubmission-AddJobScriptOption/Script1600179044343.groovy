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
//====================================================================================
WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver
//RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

TestObject newFileObj=null

try {
		def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
		20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}




	extentTest.log(LogStatus.PASS, 'Navigated Jobs Tab')

	WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals',
			AppName, true)

	WebUI.click(newAppObj)

	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - ' + AppName)

	//	WebUI.doubleClick(newAppObj)
	WebUI.delay(2)

	def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'),
			2,extentTest,'ErrorPanel')

	if (errorPanel) {
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
	}

	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))

	WebUI.delay(2)

	CustomKeywords.'operations_JobsModule.JobSubmissions.JSAllFileds'(ToChange, ChangeValue, extentTest)

	WebUI.delay(2)

	WebUI.scrollToElement(findTestObject('JobSubmissionForm/Link_Server'), 3)

	WebUI.delay(3)

	if (ExecMode.equals('LocalForm')) {
		newFileObj = CustomKeywords.'operations_JobsModule.JobSubmissions.selectFile'(ExecMode, InputFile, extentTest)
	} else {
		if (ExecMode.equals('Local')) {
			newFileObj = CustomKeywords.'operations_JobsModule.JobSubmissions.selectFile'(ExecMode, InputFile, extentTest)
		} else {
			if (TestCaseName.contains('ShortCut')) {
				ExecMode = 'ShortCut'

				newFileObj = CustomKeywords.'operations_JobsModule.JobSubmissions.selectFile'(ExecMode, InputFile, extentTest)
			} else {
				newFileObj = CustomKeywords.'operations_JobsModule.JobSubmissions.selectFile'(ExecMode, InputFile, extentTest)
			}
		}

		WebUI.click(newFileObj)

		WebUI.rightClick(newFileObj)

		extentTest.log(LogStatus.PASS, 'Right Clicked on Input file ' + InputFile)

		WebUI.delay(2)


		String idForCntxtMn = 'Add as ' + FileArg

		TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
				'id', 'equals', idForCntxtMn, true)

		WebUI.click(newRFBContextMnOption)

		extentTest.log(LogStatus.PASS, 'Clicked on context menu - ' + idForCntxtMn)
	}

	WebUI.click(findTestObject('JobSubmissionForm/RemoveFile'))

	WebUI.click(findTestObject('JobSubmissionForm/CloseFilterIcon'))

	WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

	WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)


	println(fileName)

	WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)

	WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

	extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + fileName)



	WebUI.waitForElementPresent(findTestObject('JobSubmissionForm/FileName'),5)
	WebUI.click(findTestObject('JobSubmissionForm/FileName'))

	WebUI.rightClick(findTestObject('JobSubmissionForm/FileName'))

	WebUI.click(findTestObject('JobSubmissionForm/Add_JobScript'))

	WebUI.verifyElementPresent(findTestObject('JobSubmissionForm/Verify_Jobscript'), 5)

	def submitBtn = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/button_Submit_Job'),
			20,extentTest,'SubmitButton')

	if (submitBtn) {
		WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))

		extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')
	}
	
	WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)

	def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))

	extentTest.log(LogStatus.PASS, 'Notification Generated')

	GlobalVariable.G_JobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)

	extentTest.log(LogStatus.PASS, 'Job ID - ' + GlobalVariable.G_JobID)





	if (GlobalVariable.G_Browser == 'IE') {
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


