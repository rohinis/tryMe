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
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

//=====================================================================================
def result

try {
	WebUI.delay(2)

	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20, extentTest, 'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

		extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')
	}

	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))

	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), jobname)

	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',jobState, true)

	WebUI.click(newJobFilter)

	extentTest.log(LogStatus.PASS, 'Searched for job with Job Name - '+jobname)

	WebUI.delay(2)

	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	println(jobState)

	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',jobState, true)

	WebUI.rightClick(newJobRow)

	WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))

	extentTest.log(LogStatus.PASS, 'Click on view details job')
	
	extentTest.log(LogStatus.PASS, 'Navigate back to jobs page by clicking on the Jobs link(top link)')

    WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	

	
	attribute = WebUI.getAttribute(findTestObject('Object Repository/JobMonitoringPage/JM_SearchBox'), 'value')
	extentTest.log(LogStatus.PASS, 'Verify that the search still persists with the job criteria searched previoulsy::    '+ attribute)
	
	WebUI.verifyMatch(attribute, jobname, true)
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
	extentTest.log(LogStatus.PASS, 'Closing the browser after executinge test case - '+ TestCaseName)
	extent.endTest(extentTest)
	extent.flush()
}
//=====================================================================================



