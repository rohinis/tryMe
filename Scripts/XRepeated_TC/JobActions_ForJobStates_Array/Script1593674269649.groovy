import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
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

def result
String myText

WebUI.delay(2)

try {
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'),
			20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}


	WebUI.delay(2)

	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	TestObject newJobFilterCategoryDown = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjFilterCategoryIdentifierDown'('job_array_filters')

	TestObject newJobFilterCategoryRight = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjFilterCategoryIdentifierRight'('job_array_filters')

	TestObject newJobFilterTitle = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Title_FilterCategory'),	'text', 'equals', 'Job Type', true)

	TestObject newJobFilterValue = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Title_FilterCategory'),	'text', 'equals', 'Batch Jobs', true)

	isElementPresentDown = WebUI.waitForElementPresent(newJobFilterCategoryDown, 3, FailureHandling.CONTINUE_ON_FAILURE)

	isElementPresentRight = WebUI.waitForElementPresent(newJobFilterCategoryRight, 3, FailureHandling.CONTINUE_ON_FAILURE)

	println('**************************')

	println(isElementPresentDown)

	println(isElementPresentRight)

	println('**************************')

	WebUI.delay(4)
	WebUI.scrollToElement(newJobFilterTitle, 3)

	if (isElementPresentDown) {
		println('down')

		WebUI.click(newJobFilterValue)
	}

	if (isElementPresentRight) {
		println('right')

		WebUI.click(newJobFilterTitle)

		WebUI.delay(2)

		WebUI.click(newJobFilterValue)
	}




	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)

	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	println(jobState)

	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',jobState, true)


	switch (userChoice) {

		case 'DetailsPage':
			WebUI.rightClick(newJobRow)
			TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),'id', 'equals', 'View Details', true)
			WebUI.delay(2)
			WebUI.click(newJobAction)
			result=CustomKeywords.'operations_JobsModule.executeJobAction_JobDetails_Topmenu.perfromJobAction'(jobAction,TestCaseName,extentTest)
			break;

		case "Icon" :
			WebUI.click(newJobRow)
			result=CustomKeywords.'operations_JobsModule.executeJobAction_Icon.perfromJobAction'(jobAction,TestCaseName,extentTest)

			break;

		case "Contextmenu":
			WebUI.rightClick(newJobRow)
			result = CustomKeywords.'operations_JobsModule.executeJobAction.perfromJobAction'(jobAction, TestCaseName, userChoice,extentTest)
			break;

	}




	if (result) {
		extentTest.log(LogStatus.PASS, ((' Verified job action - ' + jobAction) + ' for job state ') + jobState)
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
finally {
	extent.endTest(extentTest)

	extent.flush()
}

