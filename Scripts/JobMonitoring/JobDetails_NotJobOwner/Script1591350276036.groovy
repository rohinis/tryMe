import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

def result=false
def msgPass=false
def mssFail=false

WebUI.delay(2)

try {
	WebUI.delay(2)
	
		def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
		20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}	
	
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	println(AllJobsUser)

	
		WebUI.click(findTestObject('Object Repository/JobMonitoringPage/RadioBtn_AllJobs'))
		WebUI.delay(2)
		extentTest.log(LogStatus.PASS, 'All Jobs Filter Applied')
		WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
		//WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
		WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), AllJobsUser)
		//	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.ENTER))

		if(GlobalVariable.G_Browser.equals('FireFox')) {
			WebUI.delay(5)
			extentTest.log(LogStatus.PASS, 'Waiting for jobs table to load on FireFox')
		}
	

	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)
	WebUI.click(newJobFilter)
	WebUI.delay(2)
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)
	println(jobState)


	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	jobState, true)
	WebUI.rightClick(newJobRow)

	WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))


	switch (folder) {

		case 'Running':
			WebUI.click(findTestObject('Object Repository/JobMonitoringPage/RunningFolder'))
			extentTest.log(LogStatus.PASS, 'Navigated to Running Folder')
			result=WebUI.verifyElementClickable(findTestObject('JobMonitoringPage/ErrMsg_NotJobOwner'), FailureHandling.CONTINUE_ON_FAILURE)
			break;
		case 'Output':
			WebUI.click(findTestObject('Object Repository/JobMonitoringPage/OutputFolder'))
			extentTest.log(LogStatus.PASS, 'Navigated to Output Folder')
			result=WebUI.verifyElementClickable(findTestObject('JobMonitoringPage/ErrMsg_NotJobOwner'), FailureHandling.CONTINUE_ON_FAILURE)
			break;
			case 'Input':
			WebUI.click(findTestObject('Object Repository/JobMonitoringPage/InputFolder'))
			extentTest.log(LogStatus.PASS, 'Navigated to Inputjob_detail_download_btn Folder')
			result=WebUI.verifyElementClickable(findTestObject('JobMonitoringPage/ErrMsg_NotJobOwner'), FailureHandling.CONTINUE_ON_FAILURE)
			break;
	}


		msgPass='Verified - Non Job Owners see msg for other’s job'
		msgFail='Non Job Owners do not see msg for other’s job'
	

	if (result) {
		extentTest.log(LogStatus.PASS, msgPass)
	}
	else
	{	 extentTest.log(LogStatus.FAIL, msgFail)

	}

	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	extentTest.log(LogStatus.FAIL, ex)

	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	extentTest.log(LogStatus.FAIL, e)

	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepFailedException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	extentTest.log(LogStatus.FAIL, e)

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()
}

