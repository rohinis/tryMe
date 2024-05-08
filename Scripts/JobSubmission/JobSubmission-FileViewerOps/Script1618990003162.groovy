import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable


if(TestCaseName.contains('tile view'))
{
	println('tile view not supported in this module')
}
else
{

WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver
//RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
TestCaseName='JobSubmission RFB - '+TestCaseName
if(userChoice.equals('DoubleClick'))
{
	TestCaseName=TestCaseName+' - open file by Double Clicking on it'

}
else
{
	TestCaseName=TestCaseName+' - open file through context menu open option'

}
def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'
TestObject newFileObj

	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', fileName, true)

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


	if (TestCaseName.contains('Upload')) {
		println(TestOperation
				)
	}
	else
	{

	/*	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Navigated to '+location)*/
		
		CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
		
		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))
		WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)
		println(fileName)
		WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)
		extentTest.log(LogStatus.PASS, 'Looking for file - '+fileName+' to perfrom operation - ' + TestOperation)
		WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))


		def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj,20,extentTest,fileName)
		println(fileItem)

		if (fileItem) {
			WebUI.waitForElementPresent(newFileObj, 3)
			WebUI.click(newFileObj)
			extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + fileName)
			if(userChoice.equals('DoubleClick'))
			{

				WebUI.doubleClick(newFileObj)
				extentTest.log(LogStatus.PASS, 'Double clicked on file to open it ')

			}
			else
			{
				WebUI.rightClick(newFileObj)
				extentTest.log(LogStatus.PASS, 'RightClicked on File  - ' + fileName)
				WebUI.delay(2)
				WebUI.click(findTestObject('FilesPage/ContextMenu_FileOperation_Open'))
				extentTest.log(LogStatus.PASS, 'Clicked on Open menu item ')
				println('after is else ' + TestOperation)
				WebUI.delay(3)

			}

		}
	}

	def result = CustomKeywords.'operations_FileModule.fileViewerOperations.executeFileOperations'(katalonWebDriver,TestOperation, TestCaseName,
			extentTest)


	if (result)
	{
		extentTest.log(LogStatus.PASS, ('Verified - ' + TestCaseName) + '  Sucessfully')
	} else {
		extentTest.log(LogStatus.FAIL, TestCaseName + ' - failed')
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
