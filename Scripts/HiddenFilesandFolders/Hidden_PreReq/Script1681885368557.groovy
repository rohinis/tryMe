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
 def location= navLocation +'/ForHidden'
def jobID
def jobFile="RunJob.sh"
def jobName="hidden"
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
	   TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals',AppName, true)
	   WebUI.click(newAppObj)

	  

	   TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals','ForHidden', true)

	   WebUI.delay(2)
	   WebUI.delay(2)
	   
	   WebUI.click(findTestObject('WIP/RadioBtn_All Fields'))
	   for( int j=0; j<2;j++) {

			 if(j==1)
			 {
					jobFile="RunningJob.sh"
					jobName="hiddenRunning"
					WebUI.delay(2)
					/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
					WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), navLocation)
					WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))*/
					CustomKeywords.'generateFilePath.filePath.navlocation'(navLocation, extentTest)
					
			 }
			 WebUI.delay(2)
			 
			// WebUI.scrollToElement(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'), 3,  FailureHandling.STOP_ON_FAILURE)
			// WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'))
			// WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
			 WebUI.click(newFileObj)
			 WebUI.rightClick(newFileObj)
			 String idForCntxtMnJF = 'Add as Dir'
			 TestObject newRFBContextMnOption1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
						  'id', 'equals', idForCntxtMnJF, true)
			 WebUI.click(newRFBContextMnOption1)

			/* WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			 WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			 extentTest.log(LogStatus.PASS, 'navigated to - '+location+' in JS-RFB')*/
			 CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)

			  WebUI.delay(2)
			 WebUI.click(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'))
			 WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'),jobName)

			 TestObject newFileObj1 = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals',jobFile, true)
			 WebUI.delay(2)
			 WebUI.click(newFileObj1)
			 WebUI.rightClick(newFileObj1)

			 idForCntxtMnJF = 'Add as Job Script'
			 TestObject newRFBContextMnOption2 = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
						  'id', 'equals', idForCntxtMnJF, true)
			 WebUI.click(newRFBContextMnOption2)

			 def submitBtn=(new customWait.WaitForElement()).WaitForelementPresent (findTestObject('JobSubmissionForm/button_Submit_Job'), 10,extentTest,'SubmitButton')

			 for( int i=0; i<=2;i++) {
					if(submitBtn) {
						  WebUI.delay(3)
					WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
					}
			 }
	   }


WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
	   def jobText =  WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
	   extentTest.log(LogStatus.PASS, 'Notification Generated')
jobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'( jobText)
	   extentTest.log(LogStatus.PASS, 'Job Number - '+i+' Job ID - ' +  jobID)

}
catch (Exception ex) {
	   String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	   WebUI.takeScreenshot(screenShotPath)

	   //extentTest.log(LogStatus.FAIL, ex)

	   KeywordUtil.markFailed('ERROR: ' + e)
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


