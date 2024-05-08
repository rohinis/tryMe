import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

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

TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals','Running.sh', true)

def toget
def navLocation =(new generateFilePath.filePath()).execLocation()

def location=navLocation+'/ForJM/C1/OpsCompleted'
println('##################################################################')
println (location)
println('##################################################################')
/*
 def OpsCom=RunConfiguration.getProjectDir() + '/Upload/OpsCompleted.zip'
 def filePathOpsCom = (new generateFilePath.filePath()).getFilePath(OpsCom)
 String Dir='C1'
 String Zip=filePathOpsCom
 String Nav='OpsCompleted'
 */
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
	/*def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'),
			3,extentTest,'ErrorPanel')
	if (errorPanel) {
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
	}*/
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	WebUI.delay(2)
	/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - '+location)*/
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
	TestObject LeftNavAppIdentifier = (new buildTestObj.CreateTestObjJobs()).myLeftNavAppIdentifier('ShellScript')
	WebUI.click(LeftNavAppIdentifier)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	//WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'))
//	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))

	extentTest.log(LogStatus.PASS, 'Reset the JS form ')

	errorPanel =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'), 2,extentTest,'ErrorPanel')
	if (errorPanel) {
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
	}
	WebUI.click(findTestObject('WIP/RadioBtn_All Fields'))
	extentTest.log(LogStatus.PASS, 'Select the All Fields option  ')

	WebUI.click(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'))
	WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'),'Ops')
	WebUI.click(newFileObj)
	WebUI.rightClick(newFileObj)
	extentTest.log(LogStatus.PASS, 'Added the Job Script file - Running.sh ')


	String idForCntxtMn = 'Add as Job Script'
	TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
			'id', 'equals', idForCntxtMn, true)
	WebUI.click(newRFBContextMnOption)
	println('context menu ')
	String [] JobFiles = ['ToDelete.txt', 'ToOpen.txt', 'ToOpenWith.txt','ToRename.txt']
	int x=0
	for (String name1:JobFiles) {
		String JF =JobFiles[x]
		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_Refresh'))

		println(JF)
		TestObject newJobFile = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals',JF, true)
		WebUI.click(newJobFile)
		WebUI.rightClick(newJobFile)
		String idForCntxtMnJF = 'Add in Job Files'
		TestObject newRFBContextMnOption1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
				'id', 'equals', idForCntxtMnJF, true)
		WebUI.click(newRFBContextMnOption1)
		extentTest.log(LogStatus.PASS, 'Added the Job File -  '+ JF)

		x=x+1
	}

	def submitBtn=(new customWait.WaitForElement()).WaitForelementPresent (findTestObject('JobSubmissionForm/button_Submit_Job'), 10,extentTest,'SubmitButton')
	if(submitBtn) {
		WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
	}

	extentTest.log(LogStatus.PASS, 'Submited the Job ')
	WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
	def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
	GlobalVariable.G_JobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)
	extentTest.log(LogStatus.PASS, 'Job id - '+ GlobalVariable.G_JobID)

	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
	WebUI.delay(2)
	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)
	WebUI.delay(2)
	/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, ('Navigated to ' + location) + ' in files tab')*/
	
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
	x=0
	for (String JF1:JobFiles) {
		//String JF1 =JobFiles[x]
		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_Refresh'))

		println(JF1)
		TestObject newJobFile = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals',JF1, true)

		def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newJobFile, 20, extentTest, JF1)
		println(fileItem)
		if (fileItem) {
			extentTest.log(LogStatus.PASS, 'Input file - '+JF1+' exists ')
		}
		//x=x+1
	}

	extentTest.log(LogStatus.PASS, 'Verified - ' + TestCaseName)

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

