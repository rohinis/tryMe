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
//============================================================================//==
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//===============================================================================================

try {

	def jobsTab=CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'), 20,extentTest,'Jobs Tab')
	if(jobsTab)
	{
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

	WebUI.delay(2)

	WebUI.scrollToElement(findTestObject('JobSubmissionForm/Link_Server'), 3)

	WebUI.delay(3)

//	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))




	def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
	def location='/stage/'+GlobalVariable.G_userName+'/FilesModule'
/*	println('##################################################################')
	println (location)
	println('##################################################################')



	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to '+location)*/
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)

	//WebUI.click(findTestObject('2020.1/Stage_folder'))
	/*boolean unityRightIcon=WebUI.verifyElementNotPresent(findTestObject('Object Repository/FilesPage/unity_right_icon'), 2)
	if(unityRightIcon) {
		
		extentTest.log(LogStatus.PASS, 'Verified that unityRightIcon is not present')
	}*/
	
	
	//WebUI.verifyElementPresent(, 0)
	
	//extentTest.log(LogStatus.PASS, 'Navigated to previous folder')
	
	 location='/stage/'+GlobalVariable.G_userName
	
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
	
	boolean unityRightIcon=WebUI.verifyElementNotPresent(findTestObject('Object Repository/FilesPage/unity_right_icon'), 2)
	if(unityRightIcon) {
		
		extentTest.log(LogStatus.PASS, 'Verified that unityRightIcon is not present')
	}

	extentTest.log(LogStatus.PASS, 'Navigated to previous folder')
	
	/*println(fileName)

	WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)
	
	//extentTest.log(LogStatus.PASS, 'Looking for file to perfrom operation - ' +Operation)

	WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

	extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + fileName)*/


	

	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}


}


catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	extentTest.log(LogStatus.FAIL, ex)

	//KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	extentTest.log(LogStatus.FAIL, e)

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()


}


