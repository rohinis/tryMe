import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable


//====================================================================================
ReportFile = 'PreReq-Report.html'
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location=navLocation
def jobID
try {
	WebUI.delay(3)
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'),
			10,extentTest,'JobsTab')
	println(jobsTab)
	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}
	extentTest.log(LogStatus.PASS, 'navigated to jobs tab')
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))
	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals',
			AppName, true)

	WebUI.click(newAppObj)
	extentTest.log(LogStatus.PASS, 'navigated to job submission page ')

	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/FilesPage/FilesSearch_filter'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), "JS")
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, "Listed all the JS files")
	
	
	
	TestObject newFileObj1 = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals','RunJobJS.sh', true)
	WebUI.delay(2)
	WebUI.click(newFileObj1)
	WebUI.rightClick(newFileObj1)
	
	idForCntxtMnJF = 'Add as Job Script'
	TestObject newRFBContextMnOption2 = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
			'id', 'equals', idForCntxtMnJF, true)
	WebUI.click(newRFBContextMnOption2)
	

	def errorPanel =(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'), 2,extentTest,'ErrorPanel')
	if (errorPanel) {
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
	}
	WebUI.click(findTestObject('WIP/RadioBtn_All Fields'))
	
	
	
	for(int i=0;i<7;i++) {
	WebUI.click(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'))
	WebUI.delay(2)
	WebUI.sendKeys(findTestObject('JobSubmissionForm/TxtBx_JobName'), Keys.chord(Keys.CONTROL, 'a'))
	WebUI.sendKeys(findTestObject('JobSubmissionForm/TxtBx_JobName'), Keys.chord(Keys.BACK_SPACE))
	WebUI.delay(2)
	WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'),Jobnamelist[i])
	
	
		
		TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals',Foldername[i], true)

		WebUI.delay(2)
	

		WebUI.delay(2)

		WebUI.click(newFileObj)
		WebUI.rightClick(newFileObj)
		String idForCntxtMnJF = 'Add as Dir'
		TestObject newRFBContextMnOption1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
				'id', 'equals', idForCntxtMnJF, true)
		WebUI.click(newRFBContextMnOption1)
		
	

		
		 def submitBtn=(new customWait.WaitForElement()).WaitForelementPresent (findTestObject('JobSubmissionForm/button_Submit_Job'), 10,extentTest,'SubmitButton')
		 
		 for( int j=1; j<=2;j++) {
		 if(submitBtn) {
			 WebUI.delay(2)
			 WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
		 }
		 }
	}
	
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	//extentTest.log(LogStatus.FAIL, ex)

	KeywordUtil.markFailed('ERROR: ' + ex)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	//extentTest.log(LogStatus.FAIL, e)

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()
}




