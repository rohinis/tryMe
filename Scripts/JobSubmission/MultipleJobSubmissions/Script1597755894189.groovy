import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus
import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable as GlobalVariable

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'
//=====================================================================================

def filePathShellRun = RunConfiguration.getProjectDir() + '/Upload/JobFiles/shJob.sh'
def newFPSHR = CustomKeywords.'generateFilePath.filePath.getFilePath'(filePathShellRun)
def filePathShellFail = RunConfiguration.getProjectDir() + '/Upload/JobFiles/pyJob.py'
def newFPSHFail = CustomKeywords.'generateFilePath.filePath.getFilePath'(filePathShellFail)

try {

		def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
		20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}
	extentTest.log(LogStatus.PASS, 'Navigated Job Tab')

	WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals',
			AppName, true)

	WebUI.click(newAppObj)
	extentTest.log(LogStatus.PASS, 'Navigated to job submission form ')
	
	def jobidSH
	def jobidPY
	jobidSH = CustomKeywords.'todelete_preReq_Old.jobSubmissionForPreReq.JSMulti'(newFPSHR, AppName,extentTest)
	extentTest.log(LogStatus.PASS,"Submitted job for .sh extension")
	extentTest.log(LogStatus.PASS,"job id for .sh extension - "+ jobidSH)

	jobidPY=CustomKeywords.'todelete_preReq_Old.jobSubmissionForPreReq.JSMulti'(newFPSHFail, AppName,extentTest)
	extentTest.log(LogStatus.PASS,"Submitted job for .py extension")
	extentTest.log(LogStatus.PASS,"job id for .py extension - "+ jobidPY)
	jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'),20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}
	extentTest.log(LogStatus.PASS, 'Navigated to jobs page ')
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
	//WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), 'shJob')
	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Searched for shjob')
	//String myXpath="//a[contains(@class,'show-text-ellipsis')][contains(text(),'"+jobidSH+"')]"
	String myXpath="//a[contains(@data-automation-id,'"+jobidSH+"')]"
	println (myXpath)
	TestObject jobRow = new TestObject('objectName')
	jobRow.addProperty('xpath', ConditionType.EQUALS, myXpath)
	WebUI.delay(2)
	WebUI.mouseOver(jobRow)
	WebUI.delay(4)
	

	extentTest.log(LogStatus.PASS, 'Selected the job row and initiated resubmit action for shjob ')
	if(GlobalVariable.G_Browser.equals('Chrome'))
	{
		WebUI.rightClick(jobRow)
		println("HI =============")
		def result = CustomKeywords.'operations_JobsModule.executeJobAction.perfromJobAction'('Resubmit', TestCaseName,userChoice,extentTest)
	}
	else
	{
		WebUI.click(jobRow)
		def result=CustomKeywords.'operations_JobsModule.executeJobAction_JobDetails_Topmenu.perfromJobAction'('job_detail_resubmit_btn',TestCaseName,extentTest)
	}
	
	jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'),20,extentTest,'Jobs Tab')
	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	
	}
	extentTest.log(LogStatus.PASS, 'Navigated to jobs page ')
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))
	WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), 'pyJob')
	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Searched for pyjob')
	String myXpath1="//a[contains(@class,'show-text-ellipsis')][contains(text(),'"+jobidPY+"')]"
	TestObject jobRow1 = new TestObject('objectName')
	jobRow1.addProperty('xpath', ConditionType.EQUALS, myXpath1)
	WebUI.delay(2)
	WebUI.mouseOver(jobRow1)
	

	extentTest.log(LogStatus.PASS, 'Selected the job row and initiated resubmit action for pyjob')
	if(GlobalVariable.G_Browser.equals('Chrome'))
	{
		WebUI.rightClick(jobRow1)
		def result = CustomKeywords.'operations_JobsModule.executeJobAction.perfromJobAction'('Resubmit', TestCaseName,userChoice,extentTest)
	}
	else
	{
		WebUI.click(jobRow1)
		def result=CustomKeywords.'operations_JobsModule.executeJobAction_JobDetails_Topmenu.perfromJobAction'('job_detail_resubmit_btn',TestCaseName,extentTest)
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
