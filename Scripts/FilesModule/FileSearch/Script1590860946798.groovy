import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
String TCName = TestCaseName
def extentTest = extent.startTest(TCName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

TestObject newFileObj
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'
WebUI.enableSmartWait()
if (TestCaseName.contains('tile view'))
{
	//WebUI.delay(2)
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', searchStr,true)
} else {
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals', searchStr,true)
}

//WebUI.delay(2)

try {
	def filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20,extentTest,'App def')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
//	WebUI.delay(2)

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

	/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - '+location)*/
	
	   CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
	
	
	WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))
	WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)
	println(searchStr)
	WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), searchStr)
	extentTest.log(LogStatus.PASS, 'Entered Search String - ' + searchStr)
	WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Hit enter button')
	def result=CustomKeywords.'operations_FileModule.getRowDetails.getSearchResutls'(katalonWebDriver, extentTest,searchStr,TestCaseName)
	extentTest.log(LogStatus.PASS, 'result value from TC = '+ result)
	
	switch(userChoice)
	{
		case 'Positive':
			if (result)
			{
				println("positive - result - true")
				extentTest.log(LogStatus.PASS, 'All the files listed that contain search string in the filename')
				extentTest.log(LogStatus.PASS, 'Verified the files listed match search criteria')
			}
			break

		case 'Negative':
			if(result)
			{
				extentTest.log(LogStatus.FAIL, 'The empty folder msg is not displayed')

			}
			else
			{
				extentTest.log(LogStatus.PASS, 'The empty folder msg is displayed')
				extentTest.log(LogStatus.PASS, 'Verified the empty msg dispayed')

			}
			break
	}
WebUI.disableSmartWait()
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL, ex)
	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL, e)
	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)
	extent.flush()
}
