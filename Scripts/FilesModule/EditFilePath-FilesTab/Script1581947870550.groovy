import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable


String ReportFile = GlobalVariable.G_ReportName + '.html'
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule'

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)


println('##################################################################')
println(location)
println('##################################################################')

//WebUI.delay(2)
WebUI.enableSmartWait()

try {
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20, extentTest, 'Files Tab')
	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}
	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab ')

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

	/*	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	extentTest.log(LogStatus.PASS, 'Clicked on edit file path icon')
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	extentTest.log(LogStatus.PASS, 'Entered the location to navigate - '+location)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))*/
		
		WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
	//	WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('Object Repository/FilesPage/gotofoldertext'), 10)
		WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), location)
		//WebUI.delay(2)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Navigated to - '+location)
	
	
		extentTest.log(LogStatus.PASS, 'Hit enter key ')
		
	//WebUI.delay(2)

	
	WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
	//WebUI.delay(3)
	extentTest.log(LogStatus.PASS, 'Clicked on edit file path icon again ')
	String navigatedLocation=WebUI.getAttribute(findTestObject('Object Repository/FilesPage/gotofoldertext'), 'value')
	extentTest.log(LogStatus.PASS, 'Extracted the value from path text box ')

	if(navigatedLocation.equals(location))
	{
		extentTest.log(LogStatus.PASS, 'Extracted value - '+navigatedLocation+' matched the entered value - '+location)
		
	}
	else
	{
		extentTest.log(LogStatus.FAIL, 'Extracted value - '+navigatedLocation+' does not match the entered value - '+location)
	}

WebUI.disableSmartWait()
}
catch (Exception ex)
{
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e)
{
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	KeywordUtil.markFailed('ERROR: ' + e)
}
finally
{
	extent.endTest(extentTest)
	extent.flush()
}

