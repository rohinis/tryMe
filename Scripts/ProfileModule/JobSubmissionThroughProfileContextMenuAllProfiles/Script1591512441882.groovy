import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject



import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
TestCaseName=TestCaseName+proName+' - from option - '+submissionType
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/ForProfiles/InputDeck/'
//=====================================================================================

String proElement
TestObject newProObj
TestObject newFileObj


//WebUI.delay(2)
WebUI.enableSmartWait()

try {
	//def filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			//20,extentTest,'App def')

	//if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	//}

	//WebUI.delay(2)
	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(userChoice, extentTest)
	if (TestCaseName.contains('tile view')) {
		//WebUI.delay(2)
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'data-automation-id', 'equals', fileName, true)
	}
	else
	{
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', fileName, true)
	}

	if(AppName.contains('InComplete'))
	{
		proElement = AppName + '-' + proName+' GUI'
		newProObj = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/CntxtMenu-SubMenu-ProfileItem'),'id', 'contains', proElement, true)
	}
	else
	{
		proElement = AppName + '-' + proName
		newProObj = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/CntxtMenu-SubMenu-ProfileItem'),'id', 'contains', proElement, true)
	findTestObject('Object Repository/ProfileOptions/textBoxGoToFolder')}
	
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
	/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))*/
	extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)

	WebUI.disableSmartWait()
	
	def istextBoxPresent = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/textBx_file_filter'),5,extentTest,'TextBox')

	if(istextBoxPresent)
	{
		WebUI.setText(findTestObject('JobSubmissionForm/textBx_file_filter'), fileName)
		WebUI.sendKeys(findTestObject('JobSubmissionForm/textBx_file_filter'), Keys.chord(Keys.ENTER))
	}
	//WebUI.delay(2)
	//WebUI.click(newFileObj)
	//WebUI.rightClick(newFileObj)
	WebUI.delay(2)
	boolean isNewFileExist=WebUI.verifyElementPresent(newFileObj, 10)
   if(isNewFileExist) {
	
	WebUI.rightClick(newFileObj)
	//WebUI.delay(2)
	}
	
	WebUI.enableSmartWait()
	
	def isMenuPresent = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('FilesPage/ContextMn_Process With'),5,extentTest,'ProcessWithMenu')

	if(isMenuPresent)
	{
		extentTest.log(LogStatus.PASS, 'Right Clicked on file  - ' + fileName)
		extentTest.log(LogStatus.PASS, 'Sub menu present')
		String screenShotforSubMenu = 'ExtentReports/ProfileScreenShots/' + TestCaseName +GlobalVariable.G_Browser+'SubMenu'+ '.png'
	//	String ScreenShotNameSM=TestCaseName +GlobalVariable.G_Browser+'SubMenu'+ '.png'
		WebUI.takeScreenshot(screenShotforSubMenu)
		screenShotforSubMenu= 'ProfileScreenShots/' + TestCaseName +GlobalVariable.G_Browser+'SubMenu'+ '.png'
		//extentTest.log(LogStatus.PASS,extentTest.addScreenCapture(screenShotforSubMenu))
		//WebUI.delay(2)
		WebUI.mouseOver(findTestObject('FilesPage/ContextMn_Process With'))
		//WebUI.delay(2)
		WebUI.click(findTestObject('FilesPage/ContextMn_Process With'))
		//WebUI.delay(2)
		extentTest.log(LogStatus.PASS, 'Clicked on Process With ')
	}


	switch(submissionType)
	{
		case 'Direct':
			WebUI.click(newProObj)
			String screenShotforProfileMenu = 'ProfileScreenShots/' + TestCaseName +GlobalVariable.G_Browser+'ProfileMenu'+ '.png'
			String ScreenShotNamePM=TestCaseName +GlobalVariable.G_Browser+'SubMenu'+ '.png'
			WebUI.takeScreenshot(ScreenShotNamePM)
			//extentTest.log(LogStatus.PASS,extentTest.addScreenCapture(screenShotforProfileMenu))
			extentTest.log(LogStatus.PASS, 'Clicked on Profile Menu item - ' +proElement)
		break
		case 'AllProfiles':
			def isElemenetPresent = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/AllProfiles/span_All Profiles'),
			3,extentTest,'AllProfilesMenu')
			println("sub menu present - "+ isElemenetPresent)
			if (isElemenetPresent) {
				//WebUI.delay(1)
				WebUI.mouseOver(findTestObject('Object Repository/AllProfiles/span_All Profiles'))
				//WebUI.delay(1)
				WebUI.click(findTestObject('Object Repository/AllProfiles/span_All Profiles'))
				String screenShotforSubMenuAll = 'ProfileScreenShots/' + TestCaseName +GlobalVariable.G_Browser+'All'+ '.png'
				String ScreenShotNameAll=TestCaseName +GlobalVariable.G_Browser+'All'+ '.png'
				WebUI.takeScreenshot(screenShotforSubMenuAll)
				//extentTest.log(LogStatus.PASS,extentTest.addScreenCapture(screenShotforSubMenuAll))
				WebUI.click(findTestObject('Object Repository/AllProfiles/SelectProfile_pop-up-SearchBox'))
				WebUI.sendKeys(findTestObject('Object Repository/AllProfiles/SelectProfile_pop-up-SearchBox'), proName)
				TestObject newProLabel = CustomKeywords.'buildTestObj.CreateTestObjFiles.ProLabelIdentifier'(proName,AppName)
				WebUI.click(newProLabel)
				//WebUI.delay(2)
				String screenShotforPopup = 'ProfileScreenShots/' + TestCaseName +GlobalVariable.G_Browser+'popUp'+ '.png'
				String ScreenShotPopup=TestCaseName +GlobalVariable.G_Browser+'popUp'+ '.png'
				WebUI.takeScreenshot(screenShotforPopup)
				//extentTest.log(LogStatus.PASS,extentTest.addScreenCapture(screenShotforPopup))
				WebUI.click(findTestObject('Object Repository/AllProfiles/button_Select'))
				//WebUI.delay(2)
				extentTest.log(LogStatus.PASS, 'Clicked on - ' + proElement)
			}
			else {
				extentTest.log(LogStatus.PASS, 'Profile Conetext-menu item not found - ' + proElement)
			}

			break


	}
	if(AppName.contains('InComplete'))
	{
		
		WebUI.click(findTestObject('Object Repository/JobSubmissionForm/TxtBox_ReqFiled_ToFill'))
		WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/TxtBox_ReqFiled_ToFill'), 'testString')
		extentTest.log(LogStatus.PASS, 'Entered text for required field')
		String screenShotforJS = 'ProfileScreenShots/' + TestCaseName +GlobalVariable.G_Browser+'JS'+ '.png'
		String ScreenShotJS=TestCaseName +GlobalVariable.G_Browser+'JS'+ '.png'
		WebUI.takeScreenshot(ScreenShotJS)
		//extentTest.log(LogStatus.PASS,extentTest.addScreenCapture(screenShotforJS))
		def submitBtn = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/button_Submit_Job'),20,extentTest,'SubmitButton')
		if (submitBtn) {
			//WebUI.delay(2)
			WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
			extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')
		}
	}

	WebUI.disableSmartWait()
	def result= WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
	if (result)
	{
		def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
		GlobalVariable.G_JobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)
		extentTest.log(LogStatus.PASS, 'Job Submitted through profile initiated from files page, JobID - ' + GlobalVariable.G_JobID)
		println('********************************************')
		extentTest.log(LogStatus.PASS, ('Verified ::  ' + TestCaseName) + ' :: Sucessfully')
	}
	else
	{
		extentTest.log(LogStatus.FAIL,'Job Submission notification not generated')
		extentTest.log(LogStatus.FAIL,  TestCaseName + ' :: failed')
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

