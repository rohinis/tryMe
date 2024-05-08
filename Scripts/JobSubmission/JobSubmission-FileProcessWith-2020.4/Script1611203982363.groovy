import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
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
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'
//=====================================================================================



try {

		def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
		20,extentTest,'Jobs Tab')

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

	//WebUI.delay(2)

	//WebUI.scrollToElement(findTestObject('JobSubmissionForm/Link_Server'), 3)

	//WebUI.delay(3)

	//WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))




	//navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
	 location=navLocation+'/JobsModule/InputDeck'
	/* WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
	 WebUI.delay(2)
	 WebUI.waitForElementVisible(findTestObject('Object Repository/FilesPage/gotofoldertext'), 10)
	 WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), location)
	 WebUI.delay(2)
	 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))*/
	 
	 CustomKeywords.'generateFilePath.filePath.navlocation'( location,extentTest)
	println('##################################################################')
	println (location)
	println('##################################################################')



	//WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

	//WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	

	WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

	WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)
	
	extentTest.log(LogStatus.PASS, 'Searched for the file - '+InputFile + 'using search Filter')


	println(InputFile)

	WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), InputFile)
	//extentTest.log(LogStatus.PASS, 'Looking for file to perfrom operation - ' +Operation)

	WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

	extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + InputFile)

	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File'), 'data-automation-id', 'equals',
			InputFile, true) //	WebUI.click(newFileObj)

	def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20,extentTest,InputFile)

	println(fileItem)

	if (fileItem) {

		WebUI.waitForElementPresent(newFileObj, 3)

		WebUI.click(newFileObj)

		WebUI.doubleClick(newFileObj)
	}

    switch(userChoice)
	{
		case 'Process With':
		
		  boolean processWithBtn=    WebUI.verifyElementPresent(findTestObject('2020.4/ProcessWith'), 2)
		  if(processWithBtn) {
			  WebUI.click(findTestObject('2020.4/ProcessWith'))
			  extentTest.log(LogStatus.PASS, 'Clicked on ProcessWith')
			  
			boolean shellScrptBtn=  WebUI.verifyElementPresent(findTestObject('Object Repository/2020.4/ShellScriptBtn'), 2)
			if(shellScrptBtn) {
				WebUI.click(findTestObject('Object Repository/2020.4/ShellScriptBtn'))
				extentTest.log(LogStatus.PASS, 'Clicked on ShellScript and  Submitted  the Job')
			}
		  }
		  WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
		  def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
		  extentTest.log(LogStatus.PASS, 'Notification Generated' + jobText)
		
		break
	}

	
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}


}


catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TestCaseNameExtent+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))

	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseNameExtent) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)
	String p =TestCaseNameExtent+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	extentTest.log(LogStatus.FAIL, e)

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()


}


