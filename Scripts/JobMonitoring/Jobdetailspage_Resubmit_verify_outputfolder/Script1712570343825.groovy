import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.RemoteWebDriver
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.util.KeywordUtil

'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)


String ReportFile=GlobalVariable.G_ReportName+".html"

def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus;

def extentTest = extent.startTest(TestCaseName)
def result
WebUI.delay(2)
try
{
	WebUI.delay(2)
def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'), 20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}
	
	
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			'Completed', true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)
	//extentTest.log(LogStatus.INFO, 'Clicked on job with state  - ' + jobState)

	//println jobState
	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	'Completed', true)
		WebUI.rightClick(newJobRow)
		
		
	
	//WebUI.click(findTestObject('Object Repository/JobMonitoringPage/Resubmit_Icon'))
		WebUI.delay(2)
	//WebUI.click(findTestObject('2020.1/Details'))
		WebUI.delay(5)
		WebUI.click(findTestObject('Object Repository/JobDetailsPage/Output_tab'))
	
	
	//String outputfolderpath =WebUI.getText(	findTestObject('Object Repository/JobMonitoringPage/resubmit_msg'))
	String outputfolderpath =WebUI.getText(	findTestObject('Object Repository/JobDetailsPage/BreadCrumb_path'))
	println("outputfolderpath"+ outputfolderpath)
	
	def  outputfolder  = [] 
	outputfolder= outputfolderpath.split('/')
	
	//println("outputfolerpath:::: "+ outputfolder[5])
	
	println("outputfolder1:::: "+ outputfolder [5])
	
/*	//jo to jobs page and resubmit 
	String attvalue = WebUI.getAttribute(findTestObject('Object Repository/JobSubmissionForm/TextBx_OutPut_Folder'), 'value')
	if(attvalue.contains(outputfolder1)) {
		
	}*/
	outputfolder1=outputfolder [5]
	WebUI.delay(10)
	//WebUI.click(findTestObject('Object Repository/FilesPage/button_Yes'))
		//WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
	String attvalue = WebUI.getAttribute(findTestObject('Object Repository/JobSubmissionForm/TextBx_OutPut_Folder'), 'value')
	println("attvalue"+ attvalue)
	if(attvalue.contains(outputfolder1)) {
		
		println("inside if " + outputfolder1)
	}
	
	
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}

}

catch (Exception  ex)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL,ex)
	KeywordUtil.markFailed('ERROR: '+ e)

}
catch (StepErrorException  e)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL,e)
	KeywordUtil.markFailed('ERROR: '+ e)
	

}
finally
{

	extent.endTest(extentTest);
	extent.flush();

}

