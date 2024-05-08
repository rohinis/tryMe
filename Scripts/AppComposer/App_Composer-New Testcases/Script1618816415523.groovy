import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

/*
'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)
*/

WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
String screenShot='ExtentReports/'+TestCaseName+userChoice+GlobalVariable.G_Browser+'.png'
def result
WebUI.delay(2)



try
{
	WebUI.delay(2)
	//WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs')

	WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
	extentTest.log(LogStatus.PASS, 'Click on profile tab')
	WebUI.delay(2)

	WebUI.click(findTestObject('AppComposer/Appcomposer'))
	extentTest.log(LogStatus.PASS, 'Click on App composer')
	
	WebUI.delay(2)
	
	boolean hpcclusterTmp=	WebUI.waitForElementVisible(findTestObject('Object Repository/AppComposer/hpcluster_temp'), 10)
	if(hpcclusterTmp) {
		
		WebUI.click(findTestObject('Object Repository/AppComposer/hpcluster_temp'))
		WebUI.mouseOver(findTestObject('Object Repository/AppComposer/hpcluster_temp'))
		//WebUI.delay(2)
		
		boolean newAppTemp=WebUI.verifyElementPresent(findTestObject('Object Repository/AppComposer/NewApp_temp'), 5)
		if(newAppTemp) {
		WebUI.click(findTestObject('Object Repository/AppComposer/NewApp_temp'))
		extentTest.log(LogStatus.PASS, 'Clicked on new Appcomposer')}
	}
	switch(userChoice)
	{

		case'Space':
		CustomKeywords.'appcomposer.Create_NewApp.create_app'(app,exec, extentTest)
			
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the Test123File  created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Copy'))
			extentTest.log(LogStatus.PASS, 'Click on Clone option')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/AppComposer/Save_ btn'))
			extentTest.log(LogStatus.PASS, 'Click on Save button')
			
			WebUI.waitForElementPresent(findTestObject('Object Repository/AppComposer/AppSaved'), 5)
			
				def Text = WebUI.getText(findTestObject('Object Repository/AppComposer/AppSaved'))
			
				extentTest.log(LogStatus.PASS, 'Notification Generated ' + Text)
			WebUI.delay(3)
			cloneobj = WebUI.modifyObjectProperty(findTestObject('AppComposer/application_name'), 'data-automation-id', 'equals','Test123File_1',true)
			result=WebUI.verifyElementPresent(cloneobj, 3)
			WebUI.delay(2)
			WebUI.click(findTestObject('AppComposer/JobsTab'))

			extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')
			cloneobj2 = WebUI.modifyObjectProperty(findTestObject('AppComposer/App_name_jobspage'), 'data-automation-id', 'equals','Test123File_1',true)
			result1=WebUI.verifyElementPresent(cloneobj2, 3)
			
			
			if(result&&result1) {
				extentTest.log(LogStatus.PASS, 'Verify cloned  copy is created and present in both App composer and Jobs page')
			}
			else {
				extentTest.log(LogStatus.FAIL, 'Failed to verify the cloned copy')
			}
			
			break
			
			case 'Special char':
			
			CustomKeywords.'appcomposer.Create_NewApp.create_app'(app,exec, extentTest)
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app Test12File created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Copy'))
			extentTest.log(LogStatus.PASS, 'Click on Clone option')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/AppComposer/Save_ btn'))
			extentTest.log(LogStatus.PASS, 'Click on Save button')
			
			WebUI.waitForElementPresent(findTestObject('Object Repository/AppComposer/AppSaved'), 5)
			
				def Text3 = WebUI.getText(findTestObject('Object Repository/AppComposer/AppSaved'))
			
				extentTest.log(LogStatus.PASS, 'Notification Generated ' + Text3)
			result=WebUI.verifyElementPresent(findTestObject('AppComposer/Testapp_clone'), 3)
			
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/JobsTab'))
			
			extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')
			cloneobj2 = WebUI.modifyObjectProperty(findTestObject('AppComposer/App_name_jobspage'), 'data-automation-id', 'equals', "Test12File_1",true)
			result1=WebUI.verifyElementPresent(cloneobj2, 3)
	
		
			
			if(result&&result1) {
	           extentTest.log(LogStatus.PASS, 'Verify  copy of app created and present in both App composer and Jobs page')			}
			else {
				extentTest.log(LogStatus.FAIL, 'Failed to verify the cloned copy')
			}
			
			
			break
			
			/*case'Share':
			
	
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app ' + app +' created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Share'))
			extentTest.log(LogStatus.PASS, 'Click on Share option')
			
			WebUI.click(findTestObject('AppComposer/Input'))
			extentTest.log(LogStatus.PASS, 'Click on Input text box')
			WebUI.setText(findTestObject('AppComposer/Input'), '')
			WebUI.setText(findTestObject('AppComposer/Input'), 'bhuvana')
			extentTest.log(LogStatus.PASS, 'Add username bhuvana to share the apllication')
			WebUI.click(findTestObject('AppComposer/Share_btn'))
			extentTest.log(LogStatus.PASS, 'Click on Share button')
			
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Share'))
			extentTest.log(LogStatus.PASS, 'Click on Share option')
			
			WebUI.click(findTestObject('AppComposer/Input'))
			extentTest.log(LogStatus.PASS, 'Click on Input text box')
			WebUI.setText(findTestObject('AppComposer/Input'), '')
			WebUI.setText(findTestObject('AppComposer/Input'), 'bhuvana')
			extentTest.log(LogStatus.PASS, 'Add username bhuvana to share the apllication')
			WebUI.click(findTestObject('AppComposer/Share_btn'))
			extentTest.log(LogStatus.PASS, 'Click on Share button')
			
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Share'))
			extentTest.log(LogStatus.PASS, 'Click on Clone option')
			
			WebUI.click(findTestObject('AppComposer/Input'))
			extentTest.log(LogStatus.PASS, 'Click on Input text box')
			WebUI.setText(findTestObject('AppComposer/Input'), '')
			WebUI.setText(findTestObject('AppComposer/Input'), 'bhuvana')
			extentTest.log(LogStatus.PASS, 'Add username bhuvana to share the apllication')
			WebUI.click(findTestObject('AppComposer/Share_btn'))
			extentTest.log(LogStatus.PASS, 'Click on Share button')
			

			break
			*/
			case'Publish':
			TestObject appdefname = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppComposer/application_name'),'title', 'equals', app, true)
			WebUI.click(appdefname)
			extentTest.log(LogStatus.PASS, 'Select the Existing app def '+ app)
			WebUI.delay(3)
			
		
			WebUI.click(findTestObject('AppComposer/Publishall'))
			extentTest.log(LogStatus.PASS, 'Click on Publish to all button')
			WebUI.delay(5)
			WebUI.click(findTestObject('AppComposer/Ok_btn'))
			extentTest.log(LogStatus.PASS, 'Click on Ok button , PAS configuration change pop up')
			
			WebUI.click(findTestObject('AppComposer/Publishapp'))
			extentTest.log(LogStatus.PASS, 'Click on ' + app+ ' in left navigation')
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app ' + app + ' created and click on ellipses')
			WebUI.verifyElementPresent(findTestObject('AppComposer/Copy'),3)
			extentTest.log(LogStatus.PASS, 'Click on Copy  option')
			
			break
			
			case 'New':
			CustomKeywords.'appcomposer.Create_NewApp.create_app'(app,exec, extentTest)
			
			
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app '+ app +' created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Copy'))
			extentTest.log(LogStatus.PASS, 'Click on clone option')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/AppComposer/Save_ btn'))
			extentTest.log(LogStatus.PASS, 'Click on Save button')
			WebUI.verifyElementPresent(findTestObject('AppComposer/Newapp'), 3)
			//WebUI.click(findTestObject('Object Repository/AppComposer/Newapp'))
			
			
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app created and click on ellipses')
			
			WebUI.click(findTestObject('AppComposer/Share'))
			extentTest.log(LogStatus.PASS, 'Click on Share option')
			
			WebUI.click(findTestObject('AppComposer/Input'))
			extentTest.log(LogStatus.PASS, 'Click on Input text box')
			WebUI.setText(findTestObject('AppComposer/Input'), '')
			WebUI.setText(findTestObject('AppComposer/Input'), 'bhuvana')
			extentTest.log(LogStatus.PASS, 'Add username bhuvana to share the apllication')
			WebUI.click(findTestObject('AppComposer/Share_btn'))
			extentTest.log(LogStatus.PASS, 'Click on Share button')
			
			WebUI.delay(3)
		    WebUI.click(findTestObject('AppComposer/Newapp_clone'))
			extentTest.log(LogStatus.PASS, 'Click on apllication in left navigation')
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app ' + app + ' created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Copy'))
			extentTest.log(LogStatus.PASS, 'Click on Clone option')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/AppComposer/Save_ btn'))
			extentTest.log(LogStatus.PASS, 'Click on Save button')
			WebUI.waitForElementPresent(findTestObject('Object Repository/AppComposer/AppSaved'), 5)
			
				def Text7 = WebUI.getText(findTestObject('Object Repository/AppComposer/AppSaved'))
			
				extentTest.log(LogStatus.PASS, 'Notification Generated ' + Text7)
			cloneresult= WebUI.verifyElementPresent(findTestObject('AppComposer/Newapp_clone'), 3)	
		
			
			WebUI.delay(5)
			
			WebUI.click(findTestObject('AppComposer/Newapp_clone'))
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Deleteapp'))
			extentTest.log(LogStatus.PASS, 'Click on Delete option')
			WebUI.delay(3)
		deleteobj = WebUI.modifyObjectProperty(findTestObject('AppComposer/application_name'), 'data-automation-id', 'equals', 'Newapp_1_1',true)
			    deleteresult=WebUI.verifyElementNotPresent(deleteobj, 5)
		
		
			WebUI.delay(3)
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app '+ app +' created and click on ellipses')
			
			WebUI.click(findTestObject('AppComposer/Edit'))
			extentTest.log(LogStatus.PASS, 'Click on Edit option')
			
			//WebUI.mouseOver(findTestObject('AppComposer/Editapp'))
			
			//WebUI.click(findTestObject('AppComposer/Editapp'))
			extentTest.log(LogStatus.PASS, 'Click on ' + app + 'on left side')
			
			//WebUI.mouseOver(findTestObject('AppComposer/AppName'))
			
			WebUI.click(findTestObject('AppComposer/AppName'))
			extentTest.log(LogStatus.PASS, 'Click to Entered App name')
			WebUI.doubleClick(findTestObject('AppComposer/AppName'))
			//rob.keyPress(KeyEvent.VK_BACK_SPACE)
			//rob.keyRelease(KeyEvent.VK_BACK_SPACE)
			WebUI.sendKeys(findTestObject('AppComposer/AppName'), Keys.chord(Keys.CONTROL,'a'))
			WebUI.setText(findTestObject('AppComposer/AppName'), '')
			WebUI.setText(findTestObject('AppComposer/AppName'),'Demonew')
			extentTest.log(LogStatus.PASS, 'Rename the created app - Demonew' )
			
			WebUI.click(findTestObject('AppComposer/Save'))
			extentTest.log(LogStatus.PASS, 'Click on Save button')
			WebUI.delay(3)
			WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
			extentTest.log(LogStatus.PASS, 'Click on Jobs Tab')
			WebUI.delay(3)
			
		   editresult=WebUI.verifyElementPresent(findTestObject('AppComposer/Demonew'), 3)
		
			
			if(editresult &&cloneresult && deleteresult)
			{
				extentTest.log(LogStatus.PASS, 'Verify Newapp_1 clone has been created and present in jobs page')
				extentTest.log(LogStatus.PASS, 'Verify app has been renamed to Demonew and present on jobs page')
				extentTest.log(LogStatus.PASS, 'Verify app has been Deleted successfully !!')
				result=true
				
			}
			else
			{
				extentTest.log(LogStatus.FAIL, 'failing the testcase as one of the verification got failed')
				extentTest.log(LogStatus.INFO, 'cloned app'+ cloneresult)
				extentTest.log(LogStatus.INFO, 'Edited app'+ editresult)
				extentTest.log(LogStatus.INFO, 'Deleted app'+ deleteresult)
				result=false
			}
			
			
						
			break
			
			case'Clone':
			CustomKeywords.'appcomposer.Create_NewApp.create_app'(app,exec, extentTest)
			WebUI.click(findTestObject('AppComposer/Ellipses'))
			extentTest.log(LogStatus.PASS, 'Select the app '+ app +' created and click on ellipses')
			WebUI.click(findTestObject('AppComposer/Copy'))
			extentTest.log(LogStatus.PASS, 'Click on Clone option')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/AppComposer/Save_ btn'))
			extentTest.log(LogStatus.PASS, 'Click on Save button')
			
			WebUI.waitForElementPresent(findTestObject('Object Repository/AppComposer/AppSaved'), 5)
			
				def Text9 = WebUI.getText(findTestObject('Object Repository/AppComposer/AppSaved'))
			
				extentTest.log(LogStatus.PASS, 'Notification Generated ' + Text9)
			result=WebUI.verifyElementPresent(findTestObject('AppComposer/Demo_clone'), 3)
			WebUI.click(findTestObject('AppComposer/JobsTab'))
			
			WebUI.delay(3)
			
			extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')
			cloneobj2 = WebUI.modifyObjectProperty(findTestObject('AppComposer/App_name_jobspage'), 'data-automation-id', 'equals', "Demo_1",true)
			result1=WebUI.verifyElementPresent(cloneobj2, 3)
	
		
			
			if(result&&result1) {
			 	extentTest.log(LogStatus.PASS, ' Demo_1  has been created and present in both App composer and  jobs page')		}
			else {
				extentTest.log(LogStatus.FAIL, 'Failed to verify the cloned copy')
			}
			break
			
			case'Publishall':
			WebUI.delay(2)
			TestObject appdefname = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppComposer/application_name'),'data-automation-id', 'equals', app, true)
			WebUI.click(appdefname)
			extentTest.log(LogStatus.PASS, 'Select the Existing app def '+ app)
			WebUI.delay(3)
			
			println('hhjhj')
			WebUI.delay(3)
			
			WebUI.click(findTestObject('AppComposer/Publishall'))
			extentTest.log(LogStatus.PASS, 'Click on Publish to all button')
			WebUI.delay(3)
			result=WebUI.verifyElementPresent(findTestObject('Object Repository/AppComposer/Configuration'), 10)
			if(result)
			extentTest.log(LogStatus.PASS, 'Verify the popup:There is a change in PAS configuration. ')
			else
				extentTest.log(LogStatus.FAIL, 'Failed to verify the popup. ')
			
			//WebUI.click(findTestObject('AppComposer/Ok_btn'))
			//extentTest.log(LogStatus.PASS, 'Click on Ok button')
			
			
			break
			
			
			case'Access':
				CustomKeywords.'appcomposer.Create_NewApp.create_app'(app,exec, extentTest)

			WebUI.click(findTestObject('AppComposer/Publishall'))
			extentTest.log(LogStatus.PASS, 'Click on Publish to all button')
			
			WebUI.delay(5)
			WebUI.click(findTestObject('AppComposer/Ok_btn'))
			extentTest.log(LogStatus.PASS, 'Click on Ok button')
			WebUI.delay(2)
			
			WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
			extentTest.log(LogStatus.PASS, 'Click on profile tab')
			WebUI.delay(2)
			
			WebUI.click(findTestObject('Access_Management/Access_management'))
			extentTest.log(LogStatus.PASS, 'Click on access management')
			
			WebUI.click(findTestObject('Access_Management/Add_rolebutton'))
			extentTest.log(LogStatus.PASS, 'Click on edit role')
			
			WebUI.click(findTestObject('Access_Management/Edit_roleid'))
			WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))
			//rob.keyPress(KeyEvent.VK_BACK_SPACE)
			//rob.keyRelease(KeyEvent.VK_BACK_SPACE)
			//WebUI.sendKeys(findTestObject('AppComposer/AppName'), Keys.chord(Keys.CONTROL,'a'))
			WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')
			WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), 'demo')
			extentTest.log(LogStatus.PASS, 'Add roleid name - demo')
			
			WebUI.click(findTestObject('Access_Management/Confirm_button'))
			extentTest.log(LogStatus.PASS, 'Click on save')
			
			WebUI.delay(5)
				
			WebUI.click(findTestObject('Access_Management/Add_resources'))
			extentTest.log(LogStatus.PASS, 'Click on add resources icon')
			WebUI.delay(3)
			WebUI.mouseOver(findTestObject('AppComposer/one_resource'))
			
			WebUI.click(findTestObject('AppComposer/one_resource'))
			extentTest.log(LogStatus.PASS, 'Select the Access app as resource')
			WebUI.delay(2)
			WebUI.click(findTestObject('Access_Management/Confirm_button'))
			extentTest.log(LogStatus.PASS, 'Click on Confirm button')
			
			WebUI.delay(2)
			WebUI.click(findTestObject('Access_Management/Save_role'))
			extentTest.log(LogStatus.PASS, 'Click on add resources icon')
			WebUI.delay(3)
			
			TestObject role =WebUI.modifyObjectProperty(findTestObject('Access_Management/Roleinfo'),'text', 'equals','demo', true)
			WebUI.doubleClick(role)
			extentTest.log(LogStatus.PASS, 'Click on added role')
			
			WebUI.click(findTestObject('AppComposer/one_resource'))
			result=WebUI.verifyElementPresent(findTestObject('AppComposer/one_resource'), 10)
			if(result)
			extentTest.log(LogStatus.PASS, 'Verified globally publish app showed up in access management')
			else
				extentTest.log(LogStatus.FAIL, 'Failed to verify the app')
		
			
			break
			
			case'Unpublish':
		//	CustomKeywords.'appcomposer.Create_NewApp.create_app'(app,exec, extentTest)
			
	
			
			/*	WebUI.click(findTestObject('AppComposer/Publishall'))
				extentTest.log(LogStatus.PASS, 'Click on Publish to all button')
				WebUI.delay(2)
				WebUI.waitForElementVisible(findTestObject('AppComposer/Ok_btn'),10)
				WebUI.click(findTestObject('AppComposer/Ok_btn'))*/
				WebUI.delay(5)
				WebUI.click(findTestObject('AppComposer/Ellipses'))
				extentTest.log(LogStatus.PASS, 'Select the app :: ' + app +' created and click on ellipses')
				WebUI.delay(3)
				WebUI.click(findTestObject('AppComposer/Unpublish'))
				extentTest.log(LogStatus.PASS, 'Click on  the Unpublish option')
				//WebUI.waitForElementPresent(findTestObject('AppComposer/Unpubpop'), 5)
	
				
				
				result=WebUI.verifyElementPresent(findTestObject('AppComposer/Unpubpop'), 10)
				println("result +++++"+result)
				if(result)
								
					extentTest.log(LogStatus.PASS, 'Verify ' + app +' Application is unpublished successfully and not present in jobs page')
				else
					extentTest.log(LogStatus.FAIL, 'Failed to verify ')
	
	
				break
			
		

	}
	




	if (result)
	{
		extentTest.log(LogStatus.PASS, ('Verified ::  ' + TestCaseName) + ' :: Sucessfully')
	} else {
		extentTest.log(LogStatus.FAIL, TestCaseName+ ' :: failed')
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

