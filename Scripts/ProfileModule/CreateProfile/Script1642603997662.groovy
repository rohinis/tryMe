import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject



import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.JavascriptExecutor;

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
//RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
TestCaseName= TestCaseName + ' - '+proName
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
//====================================================================================


def isProfilePersent

//WebUI.delay(2)
WebUI.enableSmartWait()
try
{
	//def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			//20,extentTest,'App def')

	//if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))


	//WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals', AppName, true)
	TestObject LeftNavAppIdentifier = CustomKeywords.'buildTestObj.CreateTestObjJobs.myLeftNavAppIdentifier'(proName)
	WebUI.click(newAppObj)

	def GP = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'),2,extentTest,'JSPage')



	

	if (GP) {
		extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+AppName)		}

	else
	{
		extentTest.log(LogStatus.PASS, 'User not navigated to Job Submission For for - '+AppName)
	}


	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+AppName)
	def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'),2,extentTest,'ErrorPanel')

	if (errorPanel) {
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
	}

	//WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	//WebUI.delay(2)

	WebUI.scrollToElement(findTestObject('LoginPage/NewJobPage/List_NCPU_White_Theam'), 3)

	WebUI.setText(findTestObject('LoginPage/NewJobPage/List_NCPU_White_Theam'), '2')

	WebUI.setText(findTestObject('LoginPage/NewJobPage/List_Mem_WhiteTheam'), '120')

	extentTest.log(LogStatus.PASS, 'Changed the value for NCPU and MEMORY fileds ')


	switch(ProfileType)
	{
		case'Local':
			//WebUI.delay(3)
		//	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			def location=navLocation+'/ProfileFiles/'
			println('##################################################################')
			println (location)
			CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
			println('##################################################################')

			//WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
		//	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			extentTest.log(LogStatus.PASS, 'Navigated to /stage/'+GlobalVariable.G_userName+'/ProfileFiles/ in RFB ')
			//WebUI.delay(5)
			def filePath = (RunConfiguration.getProjectDir() + '/Upload/JobFiles/') + InputFile
			def newFP = CustomKeywords.'generateFilePath.filePath.getFilePath'(filePath)
			println(newFP)
			WebUI.uploadFile(findTestObject('Object Repository/JobSubmissionForm/LocalFileUploadElement'),newFP)
			extentTest.log(LogStatus.PASS, 'Uploading File to RFB - '+InputFile)
			//WebUI.delay(3)
			break;

		case 'Remote':
			//WebUI.delay(3)
		//	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			def fileLocation=navLocation+'/ForProfiles/InputDeck/'
			println('##################################################################')
			println (fileLocation)
			
			CustomKeywords.'generateFilePath.filePath.navlocation'(fileLocation, extentTest)
			
			//WebUI.click(findTestObject('Object Repository/FilesPage/FilesSearch_filter'))
			//WebUI.setText(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), fileLocation)
		//WebUI.sendKeys(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), Keys.chord(Keys.ENTER))
			println('##################################################################')
		//	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), fileLocation)
		//	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			extentTest.log(LogStatus.PASS, 'Navigated to /stage/InputDeck in RFB ')
			WebUI.waitForElementClickable(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), 10)
			WebUI.waitForElementPresent(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), 5)
			WebUI.click(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'))
			WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), RemoteFile)
			WebUI.sendKeys(findTestObject('JobSubmissionForm/textBx_file_filter'), Keys.chord(Keys.ENTER))
			//WebUI.delay(3)
			TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals',
					RemoteFile, true)
			//WebUI.click(newFileObj)
			WebUI.rightClick(newFileObj)
			extentTest.log(LogStatus.PASS, 'Context Menu Invoked for Input File - ' + RemoteFile)
			//WebUI.delay(2)
			String idForCntxtMn = 'Add as ' + FileArg
			TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
					'id', 'equals', idForCntxtMn, true)
			WebUI.click(newRFBContextMnOption)
			extentTest.log(LogStatus.PASS, 'Selected Context Menu option - ' + idForCntxtMn)
			break;

		case 'Incomplete':
			WebUI.click(findTestObject('Object Repository/JobSubmissionForm/TxtBx_ForProfileReqFiled'))
			WebUI.clearText(findTestObject('Object Repository/JobSubmissionForm/TxtBx_ForProfileReqFiled'))
			break;

		case 'NoFile':
			println('No File selected')
			break;

	}
	
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/Save_this_Profile_Icon'))
	def saveBtn = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/Btn_Save As'),2,extentTest,'Save Profile Btn ')


	if(saveBtn)
	{
		//WebUI.click(findTestObject('LoginPage/NewJobPage/Btn_Save As'))
		WebElement ele1 = driver.findElement(By.xpath("//button[@id='save_profile_dlg_save_btn']"))
		JavascriptExecutor jse1 = (JavascriptExecutor)driver;
		jse1.executeScript("arguments[0].click()", ele1);
	//	WebUI.delay(3)
	}

	WebUI.waitForElementPresent(findTestObject('LoginPage/NewJobPage/label_Save Profile'), 5)
	//WebUI.delay(3)
	extentTest.log(LogStatus.PASS, 'Clicked on Save As ')
	extentTest.log(LogStatus.PASS, 'Entered profile name -  '+proName)

	//WebUI.clearText(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'))
	//WebUI.doubleClick(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'))
	WebUI.sendKeys(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), Keys.chord(Keys.CONTROL, 'a'))
	WebUI.sendKeys(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), Keys.chord(Keys.BACK_SPACE))
	//WebUI.delay(3)
	WebUI.setText(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), proName)
	//WebUI.sendKeys(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), proName)






	if(ProfileType.equals('Cancel'))
	
	{
		println("inside cancel")
		WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/button_Cancel'))
		extentTest.log(LogStatus.PASS, 'Clicked on Save As ')
		extentTest.log(LogStatus.PASS, 'Entered profile name -  '+proName)
		extentTest.log(LogStatus.PASS, 'Profile Creation Option Selected - '+ProfileType)
		def isProfilePersentProCan = WebUI.verifyElementPresent(LeftNavAppIdentifier, 3,FailureHandling.CONTINUE_ON_FAILURE)
		if(isProfilePersentProCan)
		{
			extentTest.log(LogStatus.PASS, 'Profile is not created - '+ proName)
		}
		else
		{
			extentTest.log(LogStatus.FAIL, 'Profile is created - '+ proName)
		}
	}

	else
	{
		extentTest.log(LogStatus.PASS, 'Verified Test Case - AD-1509 Create Profile pop up validation.')
		WebUI.click(findTestObject('LoginPage/NewJobPage/button_Save'))
		//WebUI.delay(3)
		if(ProfileType.equals('Duplicate'))
		{
			String  tooltip=WebUI.getAttribute(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), 'validationMessage')
			if (tooltip.contains('already exists'))
			{
				WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/button_Cancel'))
			}
			WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))

			String myXpath="//span[@title='"+proName+"']"
			List<WebElement> listElement = driver.findElements(By.xpath(myXpath))
			println listElement.size()
			if (listElement.size()<=1)
			{
				extentTest.log(LogStatus.PASS, 'Verified duplicate profile not created' )
			}
			else
			{
				extentTest.log(LogStatus.FAIL, 'Verified duplicate  profile created' )
				extentTest.log(LogStatus.INFO, 'Profile list' )
				String myText
				for(int i =1;i<listElement.size();i++) {
					RemoteWebElement ele = listElement.get(i)
					myText=ele.getText()
					extentTest.log(LogStatus.INFO, myText )

				}

			}

		}
		else
		{
			//WebUI.delay(2)
			//WebUI.waitForElementClickable(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'), 10)
				WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
				/*WebElement ele = driver.findElement(By.xpath("//span[@title='Generic Profile']"));
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("arguments[0].click()", ele);*/
			isProfilePersent = WebUI.verifyElementPresent(LeftNavAppIdentifier, 5)

			if (isProfilePersent) {

				//WebUI.waitForElementPresent(LeftNavAppIdentifier, 5)
				WebUI.click(LeftNavAppIdentifier)
				//if(WebUI.verifyElementPresent(findTestObject('Object Repository/JobSubmissionForm/Title_Reset'),10))
				//{
					//WebUI.verifyElementPresent(findTestObject('Object Repository/JobSubmissionForm/Text_Reset'),3)
					WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
				//}
				WebUI.click(LeftNavAppIdentifier)
				extentTest.log(LogStatus.PASS, 'Verified newly created profile  -  '+proName)
				extentTest.log(LogStatus.PASS, ('Verified ::  ' + TestCaseName) + ' :: Sucessfully')

			}
			else
			{

				extentTest.log(LogStatus.FAIL,  TestCaseName + ' :: failed')
			}
		}
	}
WebUI.disableSmartWait()

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

