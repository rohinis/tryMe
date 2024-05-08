import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject



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
import  org.openqa.selenium.Keys

import internal.GlobalVariable as GlobalVariable



'Login into PAW '
/*
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_AdminUser, ('password') : GlobalVariable.G_AdminPasswd],
FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

String ReportFile=GlobalVariable.G_ReportName+".html"

def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus;

def extentTest = extent.startTest(TestCaseName)
String screenShot='ExtentReports/'+TestCaseName+userChoice+GlobalVariable.G_Browser+'.png'
def result
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

try
{
	WebUI.delay(2)
	//WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	
	WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
	extentTest.log(LogStatus.PASS, 'Click on profile tab')
	WebUI.delay(2)
	
	WebUI.click(findTestObject('Access_Management/Access_management'))
	extentTest.log(LogStatus.PASS, 'Click on access management')
	
	switch(userChoice)
	{
		
		case'Security':
		
		WebUI.click(findTestObject('Access_Management/Add_rolebutton'))
		extentTest.log(LogStatus.PASS, 'Click on add role')
	
		WebUI.click(findTestObject('Access_Management/Edit_role'))
		extentTest.log(LogStatus.PASS, 'Click on edit role')
	
		WebUI.delay(2)
		
			//WebUI.click(findTestObject('Access_Management/Edit_roleid'))
			WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))
			    WebUI.sendKeys(findTestObject('Object Repository/AppComposer/Text'), Keys.chord(Keys.CONTROL,'a'))
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), roleid)
				extentTest.log(LogStatus.PASS, 'Add roleid name -' + roleid)
				
				WebUI.click(findTestObject('Access_Management/Confirm_button'))
				extentTest.log(LogStatus.PASS, 'Click on save')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/System_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck System tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Audit_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Audit tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Application_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck application tickmark')
				
				WebUI.click(findTestObject('Access_Management/Save_role'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.delay(3)
				
				WebUI.click(findTestObject('Access_Management/Add_user'))
				extentTest.log(LogStatus.PASS, 'Click on users')
		
				WebUI.click(findTestObject('Access_Management/Add_userbutton'))
				extentTest.log(LogStatus.PASS, 'Click on add user')
		
				WebUI.click(findTestObject('Access_Management/Firstname'))
				WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
				extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)
		
				WebUI.click(findTestObject('Access_Management/Lastname'))
				WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
				extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)
		
				WebUI.click(findTestObject('Access_Management/Username'))
				WebUI.doubleClick(findTestObject('Access_Management/Username'))
				WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
				WebUI.delay(2)
				  WebUI.sendKeys(findTestObject('Object Repository/Access_Management/Username_text'), Keys.chord(Keys.BACK_SPACE))
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), username)
				extentTest.log(LogStatus.PASS, 'Add username name - ' + username )
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Access_Management/Add_role'))
				extentTest.log(LogStatus.PASS, 'Click on add role')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/Access_Management/sec_role'))
				extentTest.log(LogStatus.PASS, 'Click on secrole role to be added')
				WebUI.delay(2)
				
				//WebUI.click(findTestObject('Object Repository/Access_Management/Manager_role'))
		
				WebUI.click(findTestObject('Object Repository/AppComposer/Ok_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Ok button')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Landing_Page/ListItem_Logout'))
				extentTest.log(LogStatus.PASS, 'Click on logout')
				
				WebUI.verifyElementVisible(findTestObject('LogOut-PopUp/Title_Logout'))
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				extentTest.log(LogStatus.PASS, 'Click on yes button')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('AppComposer/Login'))
				extentTest.log(LogStatus.PASS, 'Click on Login again')
				
				WebUI.setText(findTestObject('LoginPage/username_txtbx'),'secuser')
				extentTest.log(LogStatus.PASS, 'Enter username secuser ')
				
				WebUI.setText(findTestObject('LoginPage/password_txtbx'), 'secuser')
				extentTest.log(LogStatus.PASS, 'Enter  password  secuser')
				
				WebUI.click(findTestObject('LoginPage/login_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Login')
				WebUI.delay(2)
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Access_Management/Access_management'))
				extentTest.log(LogStatus.PASS, 'Click on access management')
				
				WebUI.click(findTestObject('Access_Management/Add_user'))
				extentTest.log(LogStatus.PASS, 'Click on users')
				
				WebUI.click(findTestObject('Access_Management/samita'))
				extentTest.log(LogStatus.PASS, 'Click on samita user to assign resources')
				
				
				
				WebUI.click(findTestObject('Access_Management/Add_role'))
				extentTest.log(LogStatus.PASS, 'Click on assign role button')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/Access_Management/sec_role'))
				extentTest.log(LogStatus.PASS, 'Select secrole to assign role')
				
				
				WebUI.click(findTestObject('Object Repository/AppComposer/Ok_btn'))
				extentTest.log(LogStatus.PASS, 'Click on save')
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.doubleClick(findTestObject('Access_Management/samita'))
				extentTest.log(LogStatus.PASS, 'Click on samita user to verify')
				
				WebUI.delay(3)
				
				WebUI.verifyElementPresent(findTestObject('Object Repository/Access_Management/Verifysecrole'), 3)
				extentTest.log(LogStatus.PASS, 'Verify that secuser is able to assign roles')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.doubleClick(findTestObject('Access_Management/Vizuser'))
				extentTest.log(LogStatus.PASS, 'Click on vizuser user to add role using + icon')
				
				WebUI.click(findTestObject('Access_Management/Add_role'))
				
				WebUI.click(findTestObject('Object Repository/Access_Management/sec_role'))
				extentTest.log(LogStatus.PASS, 'Click on secrole role to be added')
				WebUI.delay(2)
				
				//WebUI.click(findTestObject('Object Repository/Access_Management/Manager_role'))
		
				WebUI.click(findTestObject('Object Repository/AppComposer/Ok_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Ok button')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.verifyElementPresent(findTestObject('Object Repository/Access_Management/Verifysecrole'), 3)
				extentTest.log(LogStatus.PASS, 'Verify secrole assign to vizuser using add role +  icon button')
				
				
					
		
		break	
		
		case'System':
		
		WebUI.click(findTestObject('Access_Management/Add_rolebutton'))
		extentTest.log(LogStatus.PASS, 'Click on add role')
	
		WebUI.click(findTestObject('Access_Management/Edit_role'))
		extentTest.log(LogStatus.PASS, 'Click on edit role')
	
		WebUI.delay(2)
		
			//WebUI.click(findTestObject('Access_Management/Edit_roleid'))
			WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))
				    WebUI.sendKeys(findTestObject('Object Repository/AppComposer/Text'), Keys.chord(Keys.CONTROL,'a'))
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), roleid)
				extentTest.log(LogStatus.PASS, 'Add roleid name -' + roleid)
				
				WebUI.click(findTestObject('Access_Management/Confirm_button'))
				extentTest.log(LogStatus.PASS, 'Click on save')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Security_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Security tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Audit_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Audit tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Application_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Application tickmark')
				
				WebUI.click(findTestObject('Access_Management/Save_role'))
				extentTest.log(LogStatus.PASS, 'Click on Save')
				
				WebUI.delay(3)
				
				WebUI.click(findTestObject('Access_Management/Add_user'))
				extentTest.log(LogStatus.PASS, 'Click on users')
		
				WebUI.click(findTestObject('Access_Management/Add_userbutton'))
				extentTest.log(LogStatus.PASS, 'Click on add user')
		
				WebUI.click(findTestObject('Access_Management/Firstname'))
				WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
				extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)
		
				WebUI.click(findTestObject('Access_Management/Lastname'))
				WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
				extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)
		
				WebUI.click(findTestObject('Access_Management/Username'))
				WebUI.doubleClick(findTestObject('Access_Management/Username'))
				WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
				WebUI.delay(2)
				    WebUI.sendKeys(findTestObject('Object Repository/Access_Management/Username_text'), Keys.chord(Keys.BACK_SPACE))
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), username)
				extentTest.log(LogStatus.PASS, 'Add username name - ' + username )
				
				WebUI.click(findTestObject('Access_Management/Add_role'))
				extentTest.log(LogStatus.PASS, 'Click on add role')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/securole'))
				extentTest.log(LogStatus.PASS, 'Select securole')
				
				WebUI.delay(2)
				
				//WebUI.click(findTestObject('Object Repository/Access_Management/Manager_role'))
		
				WebUI.click(findTestObject('Object Repository/AppComposer/Ok_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Ok button')
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Landing_Page/ListItem_Logout'))
				extentTest.log(LogStatus.PASS, 'Click on logout')
				
				WebUI.verifyElementVisible(findTestObject('LogOut-PopUp/Title_Logout'))
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				extentTest.log(LogStatus.PASS, 'Click on yes button')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('AppComposer/Login'))
				extentTest.log(LogStatus.PASS, 'Click on Login again')
				
				WebUI.setText(findTestObject('LoginPage/username_txtbx'),'securole')
				extentTest.log(LogStatus.PASS, 'Enter username securole ')
				
				WebUI.setText(findTestObject('LoginPage/password_txtbx'), 'securole')
				extentTest.log(LogStatus.PASS, 'Enter  password  securole')
				
				WebUI.click(findTestObject('LoginPage/login_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Login')
			
				WebUI.delay(2)
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Access_Management/Access_management'))
				extentTest.log(LogStatus.PASS, 'Click on access management')
				result= WebUI.verifyElementNotPresent(findTestObject('Object Repository/Access_Management/roles_link'),8)
				if(result)
					
					extentTest.log(LogStatus.PASS," Verify roles link is Not present  ::   user is not able to assign roles & privileges")
				extentTest.log(LogStatus.PASS, 'Verify User is able to create the user')
				
				
				WebUI.click(findTestObject('Access_Management/Add_userbutton'))
				extentTest.log(LogStatus.PASS, 'Click on add user')
		
				WebUI.click(findTestObject('Access_Management/Firstname'))
				WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
				extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)
		
				WebUI.click(findTestObject('Access_Management/Lastname'))
				WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
				extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)
		
				WebUI.click(findTestObject('Access_Management/Username'))
				WebUI.doubleClick(findTestObject('Access_Management/Username'))
				WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
				WebUI.delay(2)
				WebUI.sendKeys(findTestObject('Object Repository/Access_Management/Username_text'), Keys.chord(Keys.BACK_SPACE))
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), 'secapp')
				extentTest.log(LogStatus.PASS, 'Add username name - secapp' )
				
				//WebUI.click(findTestObject('Access_Management/Add_role'))
				//extentTest.log(LogStatus.PASS, 'Click on add role')
				
				//WebUI.click(findTestObject('Object Repository/Access_Management/securole'))
				//extentTest.log(LogStatus.PASS, 'Select securole')
				
				WebUI.delay(2)
				
				//WebUI.click(findTestObject('Object Repository/Access_Management/Manager_role'))
		
				//WebUI.click(findTestObject('Object Repository/AppComposer/Ok_btn'))
				//extentTest.log(LogStatus.PASS, 'Click on Ok button')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
		
		
		break
		
		case'Application':
		
		WebUI.click(findTestObject('Access_Management/Add_rolebutton'))
		extentTest.log(LogStatus.PASS, 'Click on add role')
	
		WebUI.click(findTestObject('Access_Management/Edit_role'))
		extentTest.log(LogStatus.PASS, 'Click on edit role')
	
		WebUI.delay(2)
		
			//WebUI.click(findTestObject('Access_Management/Edit_roleid'))
			WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))
				WebUI.sendKeys(findTestObject('Object Repository/AppComposer/Text'), Keys.chord(Keys.CONTROL,'a'))
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), roleid)
				extentTest.log(LogStatus.PASS, 'Add roleid name -' + roleid)
				
				WebUI.click(findTestObject('Access_Management/Confirm_button'))
				extentTest.log(LogStatus.PASS, 'Click on save')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Security_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck security tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/System_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck System tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Audit_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Audit tickmark')
				
				
				
				WebUI.click(findTestObject('Access_Management/Save_role'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.delay(3)
				
				WebUI.click(findTestObject('Access_Management/Add_user'))
				extentTest.log(LogStatus.PASS, 'Click on users')
		
				WebUI.click(findTestObject('Access_Management/Add_userbutton'))
				extentTest.log(LogStatus.PASS, 'Click on add user')
		
				WebUI.click(findTestObject('Access_Management/Firstname'))
				WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
				extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)
		
				WebUI.click(findTestObject('Access_Management/Lastname'))
				WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
				extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)
		
				WebUI.click(findTestObject('Access_Management/Username'))
				WebUI.doubleClick(findTestObject('Access_Management/Username'))
				WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
				WebUI.delay(2)
				WebUI.sendKeys(findTestObject('Object Repository/Access_Management/Username_text'), Keys.chord(Keys.BACK_SPACE))

				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), username)
				extentTest.log(LogStatus.PASS, 'Add username name - ' + username )
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Access_Management/Add_role'))
				extentTest.log(LogStatus.PASS, 'Click on add role')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Approle'))
				extentTest.log(LogStatus.PASS, 'Click on approle role to add')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/AppComposer/Ok_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Ok button')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Landing_Page/ListItem_Logout'))
				extentTest.log(LogStatus.PASS, 'Click on logout')
				
				WebUI.verifyElementVisible(findTestObject('LogOut-PopUp/Title_Logout'))
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				extentTest.log(LogStatus.PASS, 'Click on yes button')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('AppComposer/Login'))
				extentTest.log(LogStatus.PASS, 'Click on Login again')
				
				WebUI.setText(findTestObject('LoginPage/username_txtbx'),'Appuser')
				extentTest.log(LogStatus.PASS, 'Enter username Appuser ')
				
				WebUI.setText(findTestObject('LoginPage/password_txtbx'), 'Appuser')
				extentTest.log(LogStatus.PASS, 'Enter  password  Appuser')
				
				WebUI.click(findTestObject('LoginPage/login_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Login')
				WebUI.delay(2)
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(2)
				
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/Access_Management/Access_management'), 3)
				extentTest.log(LogStatus.PASS, 'Verify Access management is not present for application user')
				
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/AuditLogs/AuditLogs'), 3)
				extentTest.log(LogStatus.PASS, 'Verify Audit Logs is not present for application user')
		
		
		break
		
		case'Audit':
		
		WebUI.click(findTestObject('Access_Management/Add_rolebutton'))
		extentTest.log(LogStatus.PASS, 'Click on add role')
	
		WebUI.click(findTestObject('Access_Management/Edit_role'))
		extentTest.log(LogStatus.PASS, 'Click on edit role')
	
		WebUI.delay(2)
		
			//WebUI.click(findTestObject('Access_Management/Edit_roleid'))
			WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))
				WebUI.sendKeys(findTestObject('Object Repository/AppComposer/Text'), Keys.chord(Keys.CONTROL,'a'))
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')
				WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), roleid)
				extentTest.log(LogStatus.PASS, 'Add roleid name -' + roleid)
				
				WebUI.click(findTestObject('Access_Management/Confirm_button'))
				extentTest.log(LogStatus.PASS, 'Click on save')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Security_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Security tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/System_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Audit tickmark')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Application_tickmark'))
				extentTest.log(LogStatus.PASS, 'Uncheck Application tickmark')
				
				WebUI.click(findTestObject('Access_Management/Save_role'))
				extentTest.log(LogStatus.PASS, 'Click on Save')
				
				WebUI.delay(3)
				
				WebUI.click(findTestObject('Access_Management/Add_user'))
				extentTest.log(LogStatus.PASS, 'Click on users')
		
				WebUI.click(findTestObject('Access_Management/Add_userbutton'))
				extentTest.log(LogStatus.PASS, 'Click on add user')
		
				WebUI.click(findTestObject('Access_Management/Firstname'))
				WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
				extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)
		
				WebUI.click(findTestObject('Access_Management/Lastname'))
				WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
				extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)
		
				WebUI.click(findTestObject('Access_Management/Username'))
				WebUI.doubleClick(findTestObject('Access_Management/Username'))
				WebUI.delay(3)
				WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
				WebUI.delay(2)
					WebUI.sendKeys(findTestObject('Object Repository/Access_Management/Username_text'), Keys.chord(Keys.BACK_SPACE))
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
				WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), username)
				extentTest.log(LogStatus.PASS, 'Add username name - ' + username )
				
				WebUI.click(findTestObject('Access_Management/Add_role'))
				extentTest.log(LogStatus.PASS, 'Click on add role')
		
				WebUI.click(findTestObject('Object Repository/Access_Management/auditrole'))
				extentTest.log(LogStatus.PASS, 'Click on auditrole role ')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/AppComposer/Ok_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Ok button')
				
				WebUI.click(findTestObject('Object Repository/Access_Management/Save_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Save button')
				
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Landing_Page/ListItem_Logout'))
				extentTest.log(LogStatus.PASS, 'Click on logout')
				
				WebUI.verifyElementVisible(findTestObject('LogOut-PopUp/Title_Logout'))
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				extentTest.log(LogStatus.PASS, 'Click on yes button')
				WebUI.delay(2)
				
				WebUI.click(findTestObject('AppComposer/Login'))
				extentTest.log(LogStatus.PASS, 'Click on Login again')
				
				WebUI.setText(findTestObject('LoginPage/username_txtbx'),'audituser')
				extentTest.log(LogStatus.PASS, 'Enter username audituser ')
				
				WebUI.setText(findTestObject('LoginPage/password_txtbx'), 'audituser')
				extentTest.log(LogStatus.PASS, 'Enter  password  audituser')
				
				WebUI.click(findTestObject('LoginPage/login_btn'))
				extentTest.log(LogStatus.PASS, 'Click on Login')
				WebUI.delay(2)
				WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
				extentTest.log(LogStatus.PASS, 'Click on profile tab')
				WebUI.delay(5)
				
				WebUI.click(findTestObject('AuditLogs/AuditLogs'))
				extentTest.log(LogStatus.PASS, 'Click on Audit Logs')
				
				WebUI.delay(5)
				
				extentTest.log(LogStatus.PASS, ' Log details ')
				CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver ,extentTest)
		
		
		break
		
		
		
			
	}	
	
	

	
		if (GlobalVariable.G_Browser == 'IE') {
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
catch (StepFailedException e)
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

