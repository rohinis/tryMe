import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
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

def result
WebUI.delay(2)
try
{
	WebUI.delay(2)
		def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
		20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}
	
	
	extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')


	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

    /*
	WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
	//WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), 'Ops')
      */
	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	println jobState
	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	jobState, true)
	WebUI.rightClick(newJobRow)



	WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
	extentTest.log(LogStatus.PASS, 'Click on View details')
	
	WebUI.delay(2)


	switch(userChoice)
	{
		case 'Fields':
			//WebUI.verifyElementPresent(findTestObject('2020.4/Status'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Status element present')
			
			WebUI.verifyElementPresent(findTestObject('2020.4/Queue'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Queue element present')
			
			WebUI.verifyElementPresent(findTestObject('2020.4/Owner'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Owner element present')
			
			WebUI.verifyElementPresent(findTestObject('2020.4/Project'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Project element present')
			
			WebUI.verifyElementPresent(findTestObject('2020.4/Cores'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Cores element present')
			
			WebUI.verifyElementPresent(findTestObject('2020.4/Starttime'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Starttime element present')
			
			break;

		case 'Files':
		    
		    WebUI.click(findTestObject('2020.4/Dropdown'))
			extentTest.log(LogStatus.PASS, 'Click on dropdown')
			
			break;
			
		
		
		case 'No data':
		    
		    WebUI.verifyElementPresent(findTestObject('2020.4/NoData'), 3)
			extentTest.log(LogStatus.PASS, 'Verify No data available')
		
		    break;

			
	}
	


	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}

}

catch (Exception  ex)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)


	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))

}
catch (StepErrorException  e)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)

	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))
}
finally
{

	extent.endTest(extentTest);
	extent.flush();

}

