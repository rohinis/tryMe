import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

def isElementPresent

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
	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+AppName)

	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	WebUI.delay(2)

	WebUI.click(findTestObject('Object Repository/JobSubmissionForm/RadioBtn_All Fields'))
	extentTest.log(LogStatus.PASS, 'Clicked on show all button ')
	WebUI.verifyElementPresent(findTestObject('Object Repository/JobSubmissionForm/label_Queue'), 5)
	extentTest.log(LogStatus.PASS, 'Verified the optional filed - Queue is visible ')
	WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'))
	extentTest.log(LogStatus.PASS, 'Clicked on Reset link ')
		if(WebUI.verifyElementPresent(findTestObject('Object Repository/JobSubmissionForm/Title_Reset'),3))
	{
		extentTest.log(LogStatus.PASS, 'Reset dialog displayed')
		WebUI.verifyElementPresent(findTestObject('Object Repository/JobSubmissionForm/Text_Reset'),3)
		extentTest.log(LogStatus.PASS, 'Reset dialog text verified')

	}

	switch(userChoice)
	{
		case 'Yes':
			WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
			extentTest.log(LogStatus.PASS, 'Clicked on Yes on dialog box')
		//		WebUI.verifyElementVisible(newAppObj, FailureHandling.STOP_ON_FAILURE)
			isElementPresent=WebUI.verifyElementVisible(findTestObject('Object Repository/JobSubmissionForm/Lable_JobName'), FailureHandling.CONTINUE_ON_FAILURE)
			println(isElementPresent)
			if(isElementPresent)
			{
				extentTest.log(LogStatus.FAIL, 'Verified JS Form is not reset')
			}
			else
			{
				extentTest.log(LogStatus.PASS, 'Verified Queue filed is no more visible and Verified Job Name filed is visible')
				extentTest.log(LogStatus.PASS, 'Verified JS Form is reset')
			}
			break
		case 'No':
			WebUI.mouseOver(findTestObject('Object Repository/JobSubmissionForm/Btn_No_Reset'))
			WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Btn_No_Reset'))
			extentTest.log(LogStatus.PASS, 'Clicked on No on dialog box')
			isElementPresent=WebUI.verifyElementVisible(findTestObject('Object Repository/JobSubmissionForm/label_Queue'))
			if(isElementPresent)
			{
				extentTest.log(LogStatus.PASS, 'Verified Queue filed and Job Name fileds are visible')
				extentTest.log(LogStatus.PASS, 'Verified JS Form is not reset')
			}

			break
		case 'Close':
			WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Icon_Close_LeavePage'))
			extentTest.log(LogStatus.PASS, 'Clicked on Close Icon on dialog box')
			isElementPresent=WebUI.verifyElementVisible(findTestObject('Object Repository/JobSubmissionForm/label_Queue'))
			if(isElementPresent)
			{
				extentTest.log(LogStatus.PASS, 'Verified Queue filed and Job Name fileds are visible')
				extentTest.log(LogStatus.PASS, 'Verified JS Form is not reset')
			}
			break

	}


}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))
}
finally {
	extent.endTest(extentTest)
	extent.flush()
}

