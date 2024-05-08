import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================


def OpsCom=RunConfiguration.getProjectDir() + '/Upload/OpsCompleted.zip'
def Ops=RunConfiguration.getProjectDir() + '/Upload/Ops.zip'

def filePathOpsCom = CustomKeywords.'generateFilePath.filePath.getFilePath'(OpsCom)
def filePathOps = CustomKeywords.'generateFilePath.filePath.getFilePath'(Ops)


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


	def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
	def location=navLocation
	println('##################################################################')
	println (location)
	println('##################################################################')

	extentTest.log(LogStatus.PASS, 'navigated to jobs page')

	TestObject newFolderObj
	TestObject newFolderObjLevel2 = WebUI.modifyObjectProperty(findTestObject('FilesPage/FolderRowItem_ListView'), 'title','equals', 'ListView', true)


		TestObject LeftNavAppIdentifier = (new buildTestObj.CreateTestObjJobs()).myLeftNavAppIdentifier('ShellScriptPrePost')
		WebUI.click(LeftNavAppIdentifier)
		WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'))
		WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
		extentTest.log(LogStatus.PASS, 'resent jobs form')
		def errorPanel =(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'), 2,extentTest,'ErrorPanel')
		if (errorPanel) {
			WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
		}


		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		WebUI.waitForElementPresent(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), 5)
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'))
		WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), 'RunJob.sh')
		WebUI.sendKeys(findTestObject('JobSubmissionForm/textBx_file_filter'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Searched for input file - RunJob.sh')
		WebUI.delay(3)
		TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'text', 'equals','RunJob.sh', true)
		WebUI.click(newFileObj)
		WebUI.rightClick(newFileObj)
		String idForCntxtMn = 'Add as Job Script'
		TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
				'id', 'equals', idForCntxtMn, true)
		WebUI.click(newRFBContextMnOption)
		println('context menu ')
		WebUI.click(findTestObject('FilesPage/Icon_Close'))
		WebUI.delay(1)
		for(int i =0;i<100;i++) {
			def submitBtn=(new customWait.WaitForElement()).WaitForelementPresent (findTestObject('JobSubmissionForm/button_Submit_Job'), 10,extentTest,'SubmitButton')
			if(submitBtn) {
				WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
			}
			extentTest.log(LogStatus.PASS, 'submitted job - '+i)
			WebUI.delay(1)
		}
		
	

}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)
	extent.flush()
}
