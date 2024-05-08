import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable
//====================================================================================
WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
//RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

println "*****************************************************"
println GlobalVariable.G_Platform
println "*****************************************************"

def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = (navLocation + '/JobsModule/')
println "*****************************************************"
println location
println "*****************************************************"


TestObject newFileObj=null

try {
     WebUI.enableSmartWait()
	//def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/JobsTab_disabled'),
			//20, extentTest, 'Jobs Tab')

	//if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	//}
	extentTest.log(LogStatus.PASS, 'Navigated Jobs Tab')
	//WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals',
			AppName, true)
	WebUI.click(newAppObj)
	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - ' + AppName)
	//WebUI.delay(2)

	def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'),
			3, extentTest, 'Error Panel Close Icon')
	if (errorPanel) {
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
	}
	WebUI.doubleClick(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	
	
	if(AppName.contains('InComplete'))
	{
		WebUI.doubleClick(findTestObject('Object Repository/JobSubmissionForm/TxtBox_ReqFiled_ToFill'))
		WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/TxtBox_ReqFiled_ToFill'), 'testString')
	}

	CustomKeywords.'operations_JobsModule.JobSubmissions.JSAllFileds'(ToChange, ChangeValue, extentTest)

	if (ExecMode.equals('Array'))
	{
		WebUI.delay(2)
		extentTest.log(LogStatus.PASS, 'No file required for Array Job')
	}
	else
	{
		//WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('JobSubmissionForm/Link_Server'), 3)
		//WebUI.delay(3)

		WebUI.disableSmartWait()
		
		newFileObj = CustomKeywords.'operations_JobsModule.JobSubmissions.selectFile'(ExecMode, InputFile, extentTest)
		//WebUI.click(newFileObj)
		
		WebUI.rightClick(newFileObj)
		extentTest.log(LogStatus.PASS, 'Right Clicked on Input file ' + InputFile)
		//WebUI.delay(2)
		String idForCntxtMn = 'Add as ' + FileArg
		TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
				'id', 'equals', idForCntxtMn, true)
		WebUI.click(newRFBContextMnOption)
		extentTest.log(LogStatus.PASS, 'Clicked on context menu - ' + idForCntxtMn)
	}

	def submitBtn = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/button_Submit_Job'),
			20, extentTest, 'Submit Button')
	if (submitBtn) {
		WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
		extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')
	}

	WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
	def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
	extentTest.log(LogStatus.PASS, 'Notification Generated')
	GlobalVariable.G_JobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)
	extentTest.log(LogStatus.PASS, 'Job ID - ' + GlobalVariable.G_JobID)

   WebUI.enableSmartWait()
	
	if (ToChange.equals('SetOutPutDir')) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
		extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
		//WebUI.delay(2)
		CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)
		//WebUI.delay(2)
		//WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
		//WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	//	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		
		/*WebUI.click(findTestObject('Object Repository/FilesPage/FilesSearch_filter'))
		WebUI.setText(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), Keys.chord(Keys.ENTER))*/
		
		CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
		
	//	extentTest.log(LogStatus.PASS, ('Navigated to ' + location) + ' in files tab')
		TestObject newFileObjJS = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title',
				'equals', InputFile, true)
		def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObjJS, 20, extentTest, InputFile)
		println(fileItem)
		if (fileItem) {
			extentTest.log(LogStatus.PASS, 'Output file - jobFile.out exists ')
		}
	}

	TestObject jobIdEle = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjJobRow'(GlobalVariable.G_JobID)
	WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	//WebUI.delay(2)

	if (ExecMode.equals('Array')) {
		//WebUI.delay(2)
		CustomKeywords.'operations_JobsModule.GetJobRowDetails.checkSubJobs'(katalonWebDriver, 'JS', extentTest)
	}

	extentTest.log(LogStatus.PASS, 'Verified - ' + TestCaseName)

	WebUI.disableSmartWait()
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
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
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
}
finally {
	extent.endTest(extentTest)
	extent.flush()
}



