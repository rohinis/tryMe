import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus


import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)

ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

String TCName=TestCaseName+' through ContextMenu options'

def extentTest = extent.startTest(TCName)

def viewIconTilePresent

def viewIconListPresent

TestObject newFileObj

WebUI.delay(2)

def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location=navLocation+'/FolderMultipleFiles/'
println('##################################################################')
println (location)
println('##################################################################')


try {
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/JobsTab_disabled'),
		20,extentTest,'Jobs Tab')

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

	def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'),
		3,extentTest,'Error Panel Close Icon')

	if (errorPanel) {
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
	}
	
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))

	WebUI.delay(2)

	CustomKeywords.'operations_JobsModule.JobSubmissions.JSAllFileds'(ToChange, ChangeValue, extentTest)

	if (ExecMode.equals('Array')) {
		extentTest.log(LogStatus.PASS, 'No file required for Array Job')
	} else {
		WebUI.delay(2)

		WebUI.scrollToElement(findTestObject('JobSubmissionForm/Link_Server'), 3)

		WebUI.delay(3)

		newFileObj = CustomKeywords.'operations_JobsModule.JobSubmissions.selectFile'(ExecMode, InputFile, extentTest)

		WebUI.click(newFileObj)

		WebUI.rightClick(newFileObj)

		extentTest.log(LogStatus.PASS, 'Right Clicked on Input file ' + InputFile)

		WebUI.delay(2)

		String idForCntxtMn = 'Add as ' + FileArg

		TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
			'id', 'equals', idForCntxtMn, true)

		WebUI.click(newRFBContextMnOption)

		extentTest.log(LogStatus.PASS, 'Clicked on context menu - ' + idForCntxtMn)
	}
	
	def submitBtn = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/button_Submit_Job'),
		20,extentTest,'Submit Button')

	if (submitBtn) {
		WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))

		extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')
	}
	
	WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)

	def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))

	extentTest.log(LogStatus.PASS, 'Notification Generated')

	GlobalVariable.G_JobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)

	extentTest.log(LogStatus.PASS, 'Job ID - ' + GlobalVariable.G_JobID)

	extentTest.log(LogStatus.PASS, 'Job Submission Done for - ' + TestCaseName)
	
	/*def submitBtn1 = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/button_Submit_Job'),
		20,extentTest,'Submit Button')

	if (submitBtn1) {
		extentTest.log(LogStatus.PASS, 'Verified Notification')
		
	}
	
	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
	WebUI.delay(2)

	/*
	TestObject viewIconTile = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title',
			'equals', 'Tile View', true)

	TestObject viewIconList = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title',
			'equals', 'List View', true)

	viewIconTilePresent = WebUI.waitForElementPresent(viewIconTile, 3, FailureHandling.CONTINUE_ON_FAILURE)

	viewIconListPresent = WebUI.waitForElementPresent(viewIconList, 3, FailureHandling.CONTINUE_ON_FAILURE)

	println('viewIconTilePresent - ' + viewIconTilePresent)

	println('viewIconListPresent - ' + viewIconListPresent)

	if (viewIconListPresent) {
		WebUI.click(viewIconList)
		extentTest.log(LogStatus.PASS, 'Changed View to ListView')
		WebUI.delay(2)
	}

	WebUI.delay(2)

	println(TestCaseName)

	if (TestCaseName.contains('Upload'))
	{
		println Operation

	}

	else
	{
		if (TestCaseName.contains('tile view')) {
			println("in tile wala if")
			WebUI.click(viewIconTile)
			extentTest.log(LogStatus.PASS, 'Changed View to Tile View - test case - ' +TestCaseName)

			WebUI.delay(2)

			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals',
					fileName, true //	WebUI.click(newFileObj)
					)
		} else {
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title',
					'equals', fileName, true)
		}

		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')
		
		WebUI.click(findTestObject('FilesPage/SelectallFiles'))
		
		WebUI.rightClick(findTestObject('FilesPage/MyFolderOne'))
		
		

		
	
	}
	*/
	
	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')
		
		WebUI.click(findTestObject('FilesPage/SelectallFiles'))
		
		WebUI.rightClick(findTestObject('FilesPage/MyFolderOne'))
	WebUI.delay(2)
	println "after is else "+Operation
	def result=CustomKeywords.'operations_FileModule.MultifileOperations.executeFileOperations'(Operation, TestCaseName , extentTest)

	if(result)
	{
		extentTest.log(LogStatus.PASS, 'File Operation - ' + TestCaseName +' Performed Sucessfully')
	}
	else
	{
		extentTest.log(LogStatus.FAIL,'File Operation - ' + TestCaseName +' failed')
	}
	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
	
	
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()


}

