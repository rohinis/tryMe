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

if(TestCaseName.contains('tile view'))
{
	println('tile view not supported in this module')
}
else
{
	//====================================================================================
	ReportFile = (GlobalVariable.G_ReportName + '.html')
	def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
	def LogStatus = com.relevantcodes.extentreports.LogStatus
	TestCaseName='JobSubmission RFB - '+TestCaseName
	def extentTest = extent.startTest(TestCaseName)
	CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
	//=====================================================================================

	def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
	def location=null
	def result=null
	if (TestOperation.contains('icon'))
	{
		location = navLocation + '/FilesModule/FileOpsIcons'
	}
	else
	{
		location = navLocation + '/FilesModule/FileOps/'
	}

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

	/*	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))


		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Navigated to '+location)*/
		CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
		WebUI.delay(2)
		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))
		WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)
		WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)
		extentTest.log(LogStatus.PASS, 'Looking for file to perfrom operation - ' +TestOperation)
		WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + fileName)

		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File'), 'data-automation-id', 'equals',fileName, true)

		def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20,extentTest,fileName)
		if (TestOperation.contains('icon'))
		{
			WebUI.waitForElementPresent(newFileObj, 3)
			WebUI.click(newFileObj)
			extentTest.log(LogStatus.PASS, 'Clicked on file ' + fileName)
			result = CustomKeywords.'operations_FileModule.fileOperations_Icon.executeFileOperations'(TestOperation, TestCaseName,extentTest)
		}

		else
		{
			WebUI.waitForElementPresent(newFileObj, 3)
			WebUI.click(newFileObj)
			WebUI.rightClick(newFileObj)
			extentTest.log(LogStatus.PASS, 'Right Clicked File to invoke context menu on  - ' + fileName)
			result=CustomKeywords.'operations_FileModule.fileOperations.executeFileOperations'(Operation, TestCaseName , extentTest)
		}



		if (result)
		{
			extentTest.log(LogStatus.PASS, ('Verified - ' + TestCaseName) + '  Sucessfully')
		} else {
			extentTest.log(LogStatus.FAIL,  TestCaseName + ' - failed')
		}

	}
	catch (Exception ex) {
		String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
		WebUI.takeScreenshot(screenShotPath)
		String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
		extentTest.log(LogStatus.FAIL, ex)
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	}
	catch (StepErrorException e) {
		String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
		WebUI.takeScreenshot(screenShotPath)
		extentTest.log(LogStatus.FAIL, e)
	}
	finally {
		extent.endTest(extentTest)
		extent.flush()
	}
	//=====================================================================================


}