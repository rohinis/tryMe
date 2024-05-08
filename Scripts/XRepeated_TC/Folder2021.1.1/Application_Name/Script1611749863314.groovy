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

	switch(userChoice)
	{
		case 'Preferences':
			
		
		            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
		            extentTest.log(LogStatus.PASS, 'Click on profile tab')

		            WebUI.click(findTestObject('PageNavigation/Preferences/Preference'))
		            extentTest.log(LogStatus.PASS, 'Click on preference')
					
					def title = WebUI.getWindowTitle()
                     print(title)					
			
			break;

		case 'ServerMan':
		    
		            WebUI.mouseOver(findTestObject('Cluster_Registration/Manageservice'))
		            WebUI.click(findTestObject('Cluster_Registration/Manageservice'))
		            extentTest.log(LogStatus.PASS, 'Click on Manage Service')
					
					def title1 = WebUI.getWindowTitle()
					print(title1)
			
			break;
			
		case 'AccessMan':
		    
		            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
		            extentTest.log(LogStatus.PASS, 'Click on profile tab')
		            WebUI.delay(2)
		
		            WebUI.click(findTestObject('Access_Management/Access_management'))
		            extentTest.log(LogStatus.PASS, 'Click on access management')
					def title2 = WebUI.getWindowTitle()
					print(title2)
					
		    break;
		
		case 'AppComposer':
		
		            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
		            extentTest.log(LogStatus.PASS, 'Click on profile tab')
		            WebUI.delay(2)
	
		            WebUI.click(findTestObject('AppComposer/Appcomposer'))
		            extentTest.log(LogStatus.PASS, 'Click on App composer')
					
					def title3 = WebUI.getWindowTitle()
					print(title3)
		
		    break;
			
		case 'Sessions':
		            
		            WebUI.click(findTestObject('PageNavigation/Preferences/Sessions'))
					def title4 = WebUI.getWindowTitle()
					print(title4)
		
		    break;
			
		case 'Jobs':
		            WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
		            def title4 = WebUI.getWindowTitle()
		            print(title4)
		
		    break;
			
		case 'Files':
		
		             WebUI.click(findTestObject('Cluster_Registration/File'))
		             def title4 = WebUI.getWindowTitle()
		             print(title4)
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

