import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus


import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)

ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

String TCName=TestCaseName+' through ContextMenu options'

def extentTest = extent.startTest(TCName)

def viewIconTilePresent

def viewIconListPresent

TestObject newFileObj

WebUI.delay(2)

def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location=navLocation+'/FolderMultipleFiles/'
println('##################################################################')
println (location)
println('##################################################################')


try {
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


switch(userChoice)
{
	case 'Input':
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
			WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFiles'))
			extentTest.log(LogStatus.PASS, 'Click on MultiFiles Folder')
			
			WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFiles'))
		break;

	case 'Output':
	WebUI.click(findTestObject('JobMonitoringPage/OutputFolder'))
	extentTest.log(LogStatus.PASS, 'Click on Output Folder')
		WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder'), 5)
	//WebUI.rightClick(findTestObject('JobMonitoringPage/OutputFolder_File'))
		WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder'), 5)
		String text2 = WebUI.getAttribute(findTestObject('Object Repository/JobMonitoringPage/breadcrumb'), 'title')
		extentTest.log(LogStatus.PASS, 'Bread crumb value ' + text2)
		WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFiles'))
		extentTest.log(LogStatus.PASS, 'Click on MultiFiles Folder')
		
		WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFiles'))
		
		
		
		break;

	case 'Running':
		WebUI.click(findTestObject('JobMonitoringPage/RunningFolder'))
		extentTest.log(LogStatus.PASS, 'Click on Running Folder')
	//WebUI.rightClick(findTestObject('JobMonitoringPage/RunningFolder_File'))
		WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFiles'))
		extentTest.log(LogStatus.PASS, 'Click on MultiFiles Folder')
		
		WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFiles'))
		

		break;
}

		
		
	WebUI.delay(2)
	println "after is else "+Operation
	def result=CustomKeywords.'operations_FileModule.operations_FileModule.MultifileOperations_JobSub.executeFileOperations'(Operation, TestCaseName , extentTest)

	if(result)
	{
		extentTest.log(LogStatus.PASS, 'File Operation - ' + TestCaseName +' Performed Sucessfully')
	}
	else
	{
		extentTest.log(LogStatus.FAIL,'File Operation - ' + TestCaseName +' failed')
	}
	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
	
	
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()


}

