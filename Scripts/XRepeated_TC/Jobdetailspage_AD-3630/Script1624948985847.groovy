import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

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
		def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
		20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}
	
	
	extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')


	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))


	//WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
	//WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
	//WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), 'Ops')

	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	println jobState
	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	jobState, true)
	WebUI.rightClick(newJobRow)



	WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
	extentTest.log(LogStatus.PASS, 'Click on view details job')


			WebUI.click(findTestObject('JobMonitoringPage/InputFolder'))
		//WebUI.rightClick(findTestObject('JobMonitoringPage/OutputFolder_File'))

			extentTest.log(LogStatus.PASS, 'Click on Input Folder')
			WebUI.waitForElementPresent(findTestObject('Object Repository/JobMonitoringPage/hpccluster'), 5)
			
				def Text1 = WebUI.getText(findTestObject('Object Repository/JobMonitoringPage/hpccluster'))
			
				extentTest.log(LogStatus.PASS, 'Notification Generated ' + Text1)
				/*
				fileName='Running.sh'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals',
						fileName, true)
						*/
				
				//WebUI.rightClick(findTestObject('Object Repository/JobMonitoringPage/Running.e'))
				WebUI.delay(2)
				WebUI.rightClick(findTestObject('Object Repository/JobMonitoringPage/RunningFolder_File'))
				
				WebUI.click(findTestObject('JobMonitoringPage/Open'))
				WebUI.click(findTestObject('Object Repository/FileEditor/Close_Button_fileEditor'))
				extentTest.log(LogStatus.PASS, 'Clicked on Close Button ')
				result=true
				WebUI.verifyElementPresent(findTestObject('JobMonitoringPage/InputFolder'), 3)
				extentTest.log(LogStatus.PASS, 'Verify Input Folder is present')
		
				
		WebUI.click(findTestObject('JobMonitoringPage/OutputFolder'))
		extentTest.log(LogStatus.PASS, 'Click on Output Folder')
			WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder'), 5)
		//WebUI.rightClick(findTestObject('JobMonitoringPage/OutputFolder_File'))
			WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder'), 5)
		    String text2 = WebUI.getAttribute(findTestObject('Object Repository/JobMonitoringPage/breadcrumb'), 'title')
			extentTest.log(LogStatus.PASS, 'Bread crumb value ' + text2)
			
			WebUI.click(findTestObject('JobMonitoringPage/Open'))
			WebUI.click(findTestObject('Object Repository/FileEditor/Close_Button_fileEditor'))
			extentTest.log(LogStatus.PASS, 'Clicked on Close Button ')
			result=true
			WebUI.verifyElementPresent(findTestObject('JobMonitoringPage/OutputFolder'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Output Folder is present')
			
			
			WebUI.click(findTestObject('JobMonitoringPage/RunningFolder'))
			extentTest.log(LogStatus.PASS, 'Click on Running Folder')
		//WebUI.rightClick(findTestObject('JobMonitoringPage/RunningFolder_File'))
			WebUI.rightClick(findTestObject('Object Repository/JobMonitoringPage/RunningFolder_File'))
			
			WebUI.click(findTestObject('JobMonitoringPage/Open'))
			WebUI.click(findTestObject('Object Repository/FileEditor/Close_Button_fileEditor'))
			extentTest.log(LogStatus.PASS, 'Clicked on Close Button ')
			result=true
			
			WebUI.verifyElementPresent(findTestObject('JobMonitoringPage/RunningFolder'), 3)
			extentTest.log(LogStatus.PASS, 'Verify Running Folder is present')

			
	
	


	if(result)
	{
		extentTest.log(LogStatus.PASS, ' Verified file operation - ' + jobAction + ' for job state '+ jobState)
	}
	else
	{
		extentTest.log(LogStatus.FAIL,'some exception')
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

