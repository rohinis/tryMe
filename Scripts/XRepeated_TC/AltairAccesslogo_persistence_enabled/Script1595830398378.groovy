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

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

WebUI.delay(2)

try {
	
	switch(userChoice)
	{
	
	  
	     
	case 'Persistence Off':
	    
	    WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
	    extentTest.log(LogStatus.PASS, 'Click on profile tab')

	    WebUI.click(findTestObject('PageNavigation/Preferences/Preference'))
	    extentTest.log(LogStatus.PASS, 'Click on preference')

	    TestObject	 prefer = WebUI.modifyObjectProperty(findTestObject('PageNavigation/Preferences/Choice'), 'text', 'equals',
	    preference, true)
	    WebUI.click(prefer)
	    extentTest.log(LogStatus.PASS, 'Click on persistence')
		
		
		
		WebUI.click(findTestObject('PageNavigation/Preferences/Back'))
		extentTest.log(LogStatus.PASS, 'Click on back')
		
		WebUI.delay(2)
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))
	
		TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
				'Completed', true)
	
		WebUI.click(newJobFilter)
	
		WebUI.delay(2)
		extentTest.log(LogStatus.INFO, 'Clicked on job with state  - ')
	
		
		TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	'Completed', true)
			WebUI.rightClick(newJobRow)
			
			
		
		WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
		
		WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder_File'), 5)
		
		WebUI.click(findTestObject('PageNavigation/Preferences/Sessions'))
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
		WebUI.delay(2)
		WebUI.verifyElementPresent(findTestObject('PageNavigation/Preferences/Appdef'), 5)
		extentTest.log(LogStatus.PASS, 'Verify app def is present hence user is on Jobs Tab')
	    break
		
	case 'Persistence On':
	    
	WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
	extentTest.log(LogStatus.PASS, 'Click on profile tab')

	WebUI.click(findTestObject('PageNavigation/Preferences/Preference'))
	extentTest.log(LogStatus.PASS, 'Click on preference')

	TestObject	 prefer = WebUI.modifyObjectProperty(findTestObject('PageNavigation/Preferences/Choice'), 'text', 'equals',
	preference, true)
	WebUI.click(prefer)
	extentTest.log(LogStatus.PASS, 'Click on preference')
	
	WebUI.click(findTestObject('PageNavigation/Preferences/Tickmark'))
	extentTest.log(LogStatus.PASS, 'Click on Tickmark')
	
	WebUI.click(findTestObject('PageNavigation/Preferences/Back'))
	
	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
		jobState, true)

WebUI.click(newJobFilter)

WebUI.delay(2)
extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)


TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	jobState, true)
WebUI.rightClick(newJobRow)



WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
extentTest.log(LogStatus.PASS, 'Click on view details jobs ')

	
	WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	extentTest.log(LogStatus.PASS, 'Click on Altair Access Logo  hence persistence enabled remain in job details page')
	
	
	
	break
	
	   
		
	case 'RVS':
	    
	    WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
	    extentTest.log(LogStatus.PASS, 'Click on profile tab')

	    WebUI.click(findTestObject('PageNavigation/Preferences/Preference'))
	    extentTest.log(LogStatus.PASS, 'Click on preference')

	    TestObject	 prefer = WebUI.modifyObjectProperty(findTestObject('PageNavigation/Preferences/Choice'), 'text', 'equals',
	    preference, true)
	    WebUI.click(prefer)
	    extentTest.log(LogStatus.PASS, 'Click on preference')
		
		WebUI.verifyElementPresent(findTestObject('PageNavigation/Preferences/Autorefreshtime'), 5)
		extentTest.log(LogStatus.PASS, 'Verify autorefresh time')
	    
	    break
		    
	}
	 
	
	
	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
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
    extentTest.log(LogStatus.PASS, 'Closing the browser after executinge test case - '+ TestCaseName)
    extent.endTest(extentTest)
    extent.flush()
}
//=====================================================================================

