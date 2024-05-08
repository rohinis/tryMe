import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject



import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


import internal.GlobalVariable as GlobalVariable


WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
//RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
TestCaseName='Test Case to delete profile - '+proName
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'
//=====================================================================================
def isElementPresnt

//WebUI.delay(2)
WebUI.enableSmartWait()
try
{

	//def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			//20,extentTest,'App def')

	//if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	//}

	//WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals', AppName, true)

	WebUI.click(newAppObj)
	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission form for - '+AppName )

	//WebUI.delay(2)

	TestObject LeftNavAppIdentifier = CustomKeywords.'buildTestObj.CreateTestObjJobs.myLeftNavAppIdentifier'(proName)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	def isProfilePersent = WebUI.verifyElementPresent(LeftNavAppIdentifier, 5)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	/*if (isProfilePersent) {
		WebUI.click(LeftNavAppIdentifier)
		isElementPresent=WebUI.verifyElementPresent(findTestObject('Object Repository/JobSubmissionForm/Title_Reset'),3,FailureHandling.CONTINUE_ON_FAILURE)
		if(isElementPresent)
		{
			if(WebUI.verifyElementPresent(findTestObject('Object Repository/JobSubmissionForm/Text_Reset'),3,FailureHandling.CONTINUE_ON_FAILURE))
			WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
		}

	}*/
	//WebUI.delay(2)
	WebUI.disableSmartWait()
	
	//navigating to manageProfile
	WebUI.click(findTestObject('Object Repository/ProfileOptions/manage_Profile_Icon'))
	WebUI.delay(2)
	
	WebElement profName= driver.findElement(By.xpath("//div[contains(text(),'"+proName+"')]"))
	WebElement ele1 = driver.findElement(By.xpath("//div[text()='"+proName+"']/parent::div//div[@class='filelist-gridicon']"))
	Actions action= new Actions(driver)
	action.moveToElement(profName).click(ele1).build().perform()
	//WebDriverWait wait = new WebDriverWait(driver, 10)
	//WebElement button=	wait.until(ExpectedConditions.elementToBeClickable(ele1))
	//button.click()
	//JavascriptExecutor jse1 = (JavascriptExecutor)driver;
	//jse1.executeScript("arguments[0].click()", ele1);
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/ProfileOptions/delete_Btn'))
	extentTest.log(LogStatus.PASS, 'clicked on delete option for the - '+ proName)
	
	boolean delProf=WebUI.verifyElementPresent(findTestObject('Object Repository/ProfileOptions/delete_Profile'), 4)
	if(delProf) {
		WebUI.click(findTestObject('Object Repository/ProfileOptions/yes_Btn'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/ProfileOptions/close_Btn'))
		
	}
	
	/*def deleteOption = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('ProfileOptions/Icon_Delete_Profile'),5,extentTest,'DeleteOption')
	extentTest.log(LogStatus.PASS, 'Loaded Profile -  '+proName )

	if (deleteOption) {
		extentTest.log(LogStatus.PASS, 'Test to verify delete option exists - Pass ')

		WebUI.mouseOver(findTestObject('ProfileOptions/Icon_Delete_Profile'))
		WebUI.delay(2)
		WebUI.click(findTestObject('ProfileOptions/Icon_Delete_Profile'))
		extentTest.log(LogStatus.PASS, 'Clicked on Delete ')
		WebUI.delay(3)
		WebUI.click(findTestObject('GenericObjects/btn_Yes'))
		WebUI.delay(2)
	}*/
	
	if(ProfileType.equals('Cancel'))
		{
			WebUI.click(findTestObject('Object Repository/ProfileOptions/Save_this_Profile'))
			//boolean cancelBtn=WebUI.verifyElementPresent(findTestObject('Object Repository/LoginPage/NewJobPage/button_Cancel'), 2)
			//if(cancelBtn) {
			WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/button_Cancel'))
			//}
			extentTest.log(LogStatus.PASS, 'Clicked on Save As ')
			extentTest.log(LogStatus.PASS, 'Entered profile name -  '+proName)
			extentTest.log(LogStatus.PASS, 'Profile Creation Option Selected - '+ProfileType)
			/*def isProfilePersentProCan = WebUI.verifyElementPresent(LeftNavAppIdentifier, 3,FailureHandling.CONTINUE_ON_FAILURE)
			if(isProfilePersentProCan)
			{
				extentTest.log(LogStatus.PASS, 'Profile not created - '+ proName)
			}
			else
			{
				extentTest.log(LogStatus.PASS, 'Profile not created - '+ proName)
			}*/
		

	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	def result = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(LeftNavAppIdentifier,2,extentTest,'deletedProfile')
	
	WebUI.click(findTestObject('Object Repository/ProfileOptions/manage_Profile_Icon'))
	def result1 = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(LeftNavAppIdentifier,2,extentTest,'deletedProfile')
	//println(result)
	if (result || result1)
	{
		extentTest.log(LogStatus.FAIL,'Profile not deleted')
		//extentTest.log(LogStatus.FAIL, ( TestCaseName) + ' :: failed')

	}
	else {
		extentTest.log(LogStatus.PASS, 'Deleted Profile - '+proName )
		extentTest.log(LogStatus.PASS, ('Verified ::  ' + TestCaseName) + ' :: Sucessfully')

	}
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

