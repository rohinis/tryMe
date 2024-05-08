
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
ReportFile = (GlobalVariable.G_ReportName + '.html')
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
			  location=navLocation+'/JobsModule/InputDeck'
			  
										   println(location)
			  
							 
							 TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals','RunJob.sh', true)
							/* WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
							 WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
							 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
							 WebUI.delay(2)
							 extentTest.log(LogStatus.PASS, 'navigated to - '+location+' in JS-RFB')*/
							 
							 CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
							 TestObject LeftNavAppIdentifier = CustomKeywords.'buildTestObj.CreateTestObjJobs.leftNavAppIdentifier'('shellscript')
							 WebUI.click(LeftNavAppIdentifier)
							 extentTest.log(LogStatus.PASS, 'loaded job submission form for - shellscript')
							 WebUI.delay(2)
							/* WebUI.scrollToElement(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'), 3,  FailureHandling.STOP_ON_FAILURE)
							 WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Link_ResetLink'))
							 WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))*/


							 def errorPanel =(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'), 2,extentTest,'ErrorPanel')
							 if (errorPanel) {
										   WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
							 }
							 
							 WebUI.click(newFileObj)
							 WebUI.rightClick(newFileObj)
							 String idForCntxtMn = 'Add as Job Script'
							 TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
														  'id', 'equals', idForCntxtMn, true)
							 WebUI.click(newRFBContextMnOption)
							 println('context menu ')
							 
							 def submitBtn=(new customWait.WaitForElement()).WaitForelementPresent (findTestObject('JobSubmissionForm/button_Submit_Job'), 10,extentTest,'SubmitButton')
							 if(submitBtn) {
									WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
							 }
			  WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
							 def jobText =  WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
							 extentTest.log(LogStatus.PASS, 'Notification Generated')
			  jobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'( jobText)
							 extentTest.log(LogStatus.PASS, 'Job Number - '+' Job ID - ' +  jobID)
			  


			  jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'),
										   10,extentTest,'JobsTab')

			  if (jobsTab) {
							WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
							 extentTest.log(LogStatus.PASS, 'Navigated to jobs page ')
							 
			  }

			  String myXpath="//a[contains(@class,'show-text-ellipsis')][contains(text(),'"+jobID+"')]"
			  println (myXpath)
			  TestObject jobRow = new TestObject('objectName')
			  jobRow.addProperty('xpath', ConditionType.EQUALS, myXpath)
			  WebUI.delay(5)
			  WebUI.mouseOver(jobRow)
			  WebUI.click(jobRow)
			  extentTest.log(LogStatus.PASS, 'Navigated to job summary page ')
			  //def result=CustomKeywords.'operations_JobsModule.executeJobAction_JobDetails_Topmenu.perfromJobAction'('job_detail_terminate_btn',TestCaseName,extentTest)
			  
			  WebUI.delay(2)

			  WebUI.click(findTestObject('Object Repository/JobDetailsPage/Btn_CustomActions'))
			  extentTest.log(LogStatus.PASS, 'Clicked on CustomAction button ')
			  WebUI.click(findTestObject('Object Repository/JobDetailsPage/Btn_SendSignal') )
			  extentTest.log(LogStatus.PASS, 'Clicked on Send Signal Drop down ')
			  WebUI.delay(2)
			  
			  WebUI.click(findTestObject('Object Repository/JobMonitoringPage/Ok_btn'))
			  extentTest.log(LogStatus.PASS, 'Clicked on RUN Btn')
			  
			  String res=WebUI.getAttribute( findTestObject('Object Repository/JobDetailsPage/txt_CustomActionOutput'), 'textContent')

			  println(res)
			  
			  if(res.contains('STOP'))
			  {
							 extentTest.log(LogStatus.PASS, 'Executed STOP action ')
							 extentTest.log(LogStatus.PASS, 'Verifed msg present on page - '+res)
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

			  KeywordUtil.markFailed('ERROR: ' + ex)
}
finally
{
			  extent.endTest(extentTest)

			  extent.flush()
}





