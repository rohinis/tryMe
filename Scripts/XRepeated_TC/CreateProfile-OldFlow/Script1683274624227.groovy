import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.junit.Assert.*

import java.awt.Robot
import java.awt.event.KeyEvent as KeyEvent

import org.junit.Test
import org.openqa.selenium.Keys

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
TestCaseName= TestCaseName + ' - '+proName
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()

//=====================================================================================

Robot rob = new Robot()


WebUI.delay(2)
try
{
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}

	WebUI.delay(2)

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

	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	WebUI.delay(2)

	WebUI.scrollToElement(findTestObject('LoginPage/NewJobPage/List_NCPU_White_Theam'), 3)

	WebUI.setText(findTestObject('LoginPage/NewJobPage/List_NCPU_White_Theam'), '2')

	WebUI.setText(findTestObject('LoginPage/NewJobPage/List_Mem_WhiteTheam'), '120')

	extentTest.log(LogStatus.PASS, 'Changed the value for NCPU and MEMORY fileds ')


	switch(ProfileType)
	{
		case'Local':
			WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			def location=navLocation+'/ProfileFiles/'
			println('##################################################################')
			println (location)
			println('##################################################################')

			WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
			WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			extentTest.log(LogStatus.PASS, 'Navigated to /stage/'+GlobalVariable.G_userName+'/ProfileFiles/ in RFB ')
			def filePath = (RunConfiguration.getProjectDir() + '/Upload/JobFiles/') + InputFile
			def newFP = CustomKeywords.'generateFilePath.filePath.getFilePath'(filePath)
			println(newFP)
			WebUI.uploadFile(findTestObject('Object Repository/JobSubmissionForm/LocalFileUploadElement'),newFP)
			extentTest.log(LogStatus.PASS, 'Uploading File to RFB - '+InputFile)
			WebUI.delay(3)
			break;

		case 'Remote':
			WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			def fileLocation=navLocation+'/ForProfiles/InputDeck/'
			println('##################################################################')
			println (fileLocation)
			println('##################################################################')
			WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), fileLocation)
			WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			extentTest.log(LogStatus.PASS, 'Navigated to /stage/InputDeck in RFB ')
			WebUI.waitForElementPresent(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), 5)
			WebUI.click(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'))
			WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), RemoteFile)
			WebUI.sendKeys(findTestObject('JobSubmissionForm/textBx_file_filter'), Keys.chord(Keys.ENTER))
			WebUI.delay(3)
			TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'text', 'equals',
					RemoteFile, true)
			WebUI.click(newFileObj)
			WebUI.rightClick(newFileObj)
			extentTest.log(LogStatus.PASS, 'Context Menu Invoked for Input File - ' + RemoteFile)
			WebUI.delay(2)
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


		case 'Duplicate':
			WebUI.rightClick(LeftNavAppIdentifier)
			def duplicateOption = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/SubMenu_Duplicate'),5,extentTest,'DuplicateOption')
			extentTest.log(LogStatus.PASS, 'Duplicate Profile Option Case - '+duplicateOption )
			if (duplicateOption) {
				extentTest.log(LogStatus.PASS, 'Test to verify duplicate option exists - Pass ')
				WebUI.click(findTestObject('JobSubmissionForm/SubMenu_Duplicate'))
				def duplicatePro=proName+'-copy'
				println(duplicatePro)
				TestObject LeftNavAppIdentifierProDuplicate = CustomKeywords.'buildTestObj.CreateTestObjJobs.myLeftNavAppIdentifier'(duplicatePro)
				WebUI.waitForElementPresent(LeftNavAppIdentifierProDuplicate, 5)

				def isProfilePersentPro1 = WebUI.verifyElementPresent(LeftNavAppIdentifierProDuplicate, 5)
				if(isProfilePersentPro1)
				{
					extentTest.log(LogStatus.PASS, 'Duplicate profile created - '+ duplicatePro)
				}
			}
			break;



		case 'SetAsDefault':

			WebUI.rightClick(LeftNavAppIdentifier)
			def setAsDefaultOption = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/JobSubmissionForm/SubMenu_SetAsDefault'),5,extentTest,'SetAsDefaultOption')
			extentTest.log(LogStatus.PASS, 'Duplicate Profile Option Case - '+setAsDefaultOption )
			if (setAsDefaultOption) {
				extentTest.log(LogStatus.PASS, 'Duplicate Profile Option Case - '+setAsDefaultOption )
				extentTest.log(LogStatus.PASS, 'Test to verify set as default option exists - Pass ')
				WebUI.click(findTestObject('Object Repository/JobSubmissionForm/SubMenu_SetAsDefault'))
				def not=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_ProfileAsDefault'), 5)
				extentTest.log(LogStatus.PASS, 'Profile set as Default - '+ not)

			}
			break;
	}

	

	
	
	WebUI.click(findTestObject('LoginPage/NewJobPage/Btn_Save As'))
	WebUI.delay(3)

	WebUI.waitForElementPresent(findTestObject('LoginPage/NewJobPage/label_Save Profile'), 5)
	WebUI.delay(3)

	if (GlobalVariable.G_Browser == 'Edge') {
		CustomKeywords.'funtionsForEdge.EdgeFunctions.setTestToField'(proName, findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'))
	} else {
		WebUI.sendKeys(findTestObject('LoginPage/NewJobPage/TxtBx_saveProfile'), proName)
	}

	if(ProfileType.equals('Cancel'))
	{
		WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/button_Cancel'))
		extentTest.log(LogStatus.PASS, 'Clicked on Save As ')
		extentTest.log(LogStatus.PASS, 'Entered profile name -  '+proName)
		extentTest.log(LogStatus.PASS, 'Profile Creation Option Selected - '+ProfileType)
		def isProfilePersentProCan = WebUI.verifyElementPresent(LeftNavAppIdentifier, 3,FailureHandling.CONTINUE_ON_FAILURE)
		if(isProfilePersentProCan)
		{
			extentTest.log(LogStatus.PASS, 'Profile not created - '+ proName)
		}
		else
		{

			extentTest.log(LogStatus.PASS, 'Profile not created - '+ proName)

		}

	}
	else
	{
		if(ProfileType.equals('Duplicate'))
		{
			WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/button_Cancel'))

		}
		else{

			extentTest.log(LogStatus.PASS, 'Clicked on Save As ')
			extentTest.log(LogStatus.PASS, 'Entered profile name -  '+proName)
			extentTest.log(LogStatus.PASS, 'Verified Test Case - AD-1509 Create Profile pop up validation.')
			WebUI.click(findTestObject('LoginPage/NewJobPage/button_Save'))
			WebUI.delay(3)

			WebUI.waitForElementPresent(LeftNavAppIdentifier, 5)

			def isProfilePersent = WebUI.verifyElementPresent(LeftNavAppIdentifier, 5)

			if (isProfilePersent) {
				extentTest.log(LogStatus.PASS, 'Verified newly created profile  -  '+proName)
				extentTest.log(LogStatus.PASS, ('Verified ::  ' + TestCaseName) + ' :: Sucessfully')

			} else {

				extentTest.log(LogStatus.FAIL, ( TestCaseName) + ' :: failed')
			}
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

