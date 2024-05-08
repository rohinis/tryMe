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
 def location= navLocation +'/ForJM/C1/OpsCompleted'
def jobID
def jobFile="Running.sh"
def jobName="arrayjob"
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

	   WebUI.delay(2)
	   WebUI.scrollToElement(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'), 3,  FailureHandling.STOP_ON_FAILURE)
	   WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'))
	   WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
	  
	   for( int j=0; j<=2;j++) {

			 if(j==1)
			 {
				 WebUI.delay(2)
				 WebUI.click(findTestObject('Object Repository/JobSubmissionForm/RunningTime_JobArray'))
				 WebUI.delay(5)
				 WebUI.clearText(findTestObject('Object Repository/JobSubmissionForm/RunningTime_JobArray'))
					jobFile="Running.sh"
					jobName="arrayjobrunning"
					WebUI.delay(5)
				
					WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/RunningTime_JobArray'), "123456789")	
			 }
			 if(j==2) {
				 WebUI.delay(2)
				 WebUI.clearText(findTestObject('Object Repository/JobSubmissionForm/RunningTime_JobArray'))
				 jobFile="Running.sh"
				 jobName="arrayjobQueued"
				 WebUI.delay(2)
				 WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/List_Memory'), "32768")
			 }
			 WebUI.delay(2)
			 
			
			

			 WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			 WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			 extentTest.log(LogStatus.PASS, 'navigated to - '+location+' in JS-RFB')

			  WebUI.click(findTestObject('WIP/RadioBtn_All Fields'))
			 WebUI.click(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'))
			 WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/TxtBx_JobName'),jobName)

			 TestObject newFileObj1 = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'text', 'equals',jobFile, true)
			 WebUI.delay(2)
			 WebUI.click(newFileObj1)
			 WebUI.rightClick(newFileObj1)

			 idForCntxtMnJF = 'Add as Job Script'
			 TestObject newRFBContextMnOption2 = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
						  'id', 'equals', idForCntxtMnJF, true)
			 WebUI.click(newRFBContextMnOption2)
			 
			 String [] JobFiles = ['ToDelete.txt', 'ToOpen.txt', 'ToOpenWith.txt', 'ToRename.txt', 'ToEdit.txt']
			 int x=0
			 for (String name1:JobFiles) {
				 String JF =JobFiles[x]
				 WebUI.click(findTestObject('Object Repository/FilesPage/Icon_Refresh'))
	 
				 println(JF)
				 TestObject newJobFile = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'text', 'equals',JF, true)
				 WebUI.click(newJobFile)
				 WebUI.rightClick(newJobFile)
				 String idForCntxtMnJF = 'Add in Job Files'
				 TestObject newRFBContextMnOption1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
						 'id', 'equals', idForCntxtMnJF, true)
				 WebUI.click(newRFBContextMnOption1)
				 x=x+1
			 }

			 def submitBtn=(new customWait.WaitForElement()).WaitForelementPresent (findTestObject('JobSubmissionForm/button_Submit_Job'), 10,extentTest,'SubmitButton')

			 for( int i=0; i<2;i++) {
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
	   
	   WebUI.delay(5)
	   jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'),
		   10,extentTest,'JobsTab')

   if (jobsTab) {
	   WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
   }

   String myXpath="//a[contains(@class,'show-text-ellipsis')][contains(text(),'"+jobID+"')]"
   println (myXpath)
   TestObject jobRow = new TestObject('objectName')
   jobRow.addProperty('xpath', ConditionType.EQUALS, myXpath)
   WebUI.delay(2)
   WebUI.mouseOver(jobRow)
   WebUI.click(jobRow)
   def result=CustomKeywords.'operations_JobsModule.executeJobAction_JobDetails_Topmenu.perfromJobAction'('job_detail_terminate_btn',TestCaseName,extentTest)

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



