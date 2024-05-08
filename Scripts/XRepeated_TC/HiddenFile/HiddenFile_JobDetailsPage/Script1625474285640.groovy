import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.junit.After
import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus


import internal.GlobalVariable as GlobalVariable


TestObject newFileObj

WebUI.delay(2)

try {
	

			WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
			extentTest.log(LogStatus.PASS, 'Click on profile tab')

			WebUI.click(findTestObject('PageNavigation/Preferences/Preference'))
			extentTest.log(LogStatus.PASS, 'Click on preference')
			
            WebUI.delay(3)
			
			TestObject	 prefer = WebUI.modifyObjectProperty(findTestObject('PageNavigation/Preferences/Choice'), 'text', 'equals',
					preference, true)
			WebUI.click(prefer)
			extentTest.log(LogStatus.PASS, 'Click on preference')
			
			WebUI.click(findTestObject('Object Repository/JobMonitoringPage/Tickmark'))



			WebUI.click(findTestObject('PageNavigation/Preferences/Back'))
			extentTest.log(LogStatus.PASS, 'Click on back')


			
			
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
					
					WebUI.verifyElementNotPresent(findTestObject('Object Repository/JobMonitoringPage/hiddenfile'), 3)
					WebUI.delay(2)
				break;
	
			case 'Output':
			WebUI.click(findTestObject('JobMonitoringPage/OutputFolder'))
			extentTest.log(LogStatus.PASS, 'Click on Output Folder')
				WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder'), 5)
			//WebUI.rightClick(findTestObject('JobMonitoringPage/OutputFolder_File'))
				WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder'), 5)
				String text2 = WebUI.getAttribute(findTestObject('Object Repository/JobMonitoringPage/breadcrumb'), 'title')
				extentTest.log(LogStatus.PASS, 'Bread crumb value ' + text2)
				
				
				break;
	
			case 'Running':
				WebUI.click(findTestObject('JobMonitoringPage/RunningFolder'))
				extentTest.log(LogStatus.PASS, 'Click on Running Folder')
			//WebUI.rightClick(findTestObject('JobMonitoringPage/RunningFolder_File'))
	
				break;
		}
		
		
		

		


	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
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


