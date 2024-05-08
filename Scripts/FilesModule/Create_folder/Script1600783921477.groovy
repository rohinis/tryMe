import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

String ReportFile = GlobalVariable.G_ReportName + '.html'
def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()

WebUI.enableSmartWait()
def location = navLocation + '/FoldersModule'

if (TestCaseName.contains('tile view'))
	{
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals',FolderName, true )
		location = navLocation + '/FoldersModule/CreateFolder/TileView'
	}
	else
	{
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_Folder'), 'title','equals', FolderName, true)
		location = navLocation + '/FoldersModule/CreateFolder/ListView'
	}


CustomKeywords.'toLogin.ForLogin.Login'(extentTest)


//WebUI.delay(2)

try {

	def filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20,extentTest,'App def')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}


	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)
	
	/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)*/
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
//	WebUI.delay(2)


//	WebUI.delay(2)
	WebUI.waitForElementVisible(findTestObject('FilesPage/btn_NewFileFolder'), 10)
	WebUI.click(findTestObject('FilesPage/btn_NewFileFolder'))
	extentTest.log(LogStatus.PASS, 'Clicked on New File/Folder  Button')

	WebUI.click(findTestObject('FilesPage/ListItem_Folder'))
	extentTest.log(LogStatus.PASS, 'Clicked on Folder list item')

	WebUI.waitForElementVisible(findTestObject('FilesPage/TextBxFolder_input'), 5)
	WebUI.setText(findTestObject('FilesPage/TextBxFolder_input'), FolderName)
	extentTest.log(LogStatus.PASS, "Entered Folder Name to Create - "+FolderName )
	WebUI.click(findTestObject('FilesPage/btn_Save'))
	extentTest.log(LogStatus.PASS, "Clicked on Save Button" )

	switch (userChoice)
	{
		case 'Positive':

			String folder = FolderName.trim()
			WebUI.delay(2)
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to open Notification Panel')
			WebUI.delay(2)
			TestObject createFolderNotification = CustomKeywords.'buildTestObj.CreateTestObjFiles.myTestObjFolderCreateNotification'(folder)
			def notification = WebUI.verifyElementPresent(createFolderNotification, 5)

			String msg = ('The Directory ' + FolderName) + ' has been created successfully'
			println(notification)
			if (notification)
			{
				extentTest.log(LogStatus.PASS, FolderName + '- Created Folder and verified notification')
				extentTest.log(LogStatus.PASS, ('Notification with msg - "' + msg) + '" is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, FolderName + ' - Not Created')
				extentTest.log(LogStatus.FAIL)
			}
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to close Notification Panel')
			def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, FolderName)
			println(fileItem)
			if (fileItem)
			{
				WebUI.waitForElementPresent(newFileObj, 3)
				extentTest.log(LogStatus.PASS, ('Verified Folder - ' + FolderName) + ' is listed')
			}
			break;

		case 'Negative':
			WebUI.verifyElementPresent(findTestObject('FilesPage/SpecialChar_popup'), 3)
			WebUI.click(findTestObject('Object Repository/FilesPage/btn_Cancel'))
			WebUI.delay(2)
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to open Notification Panel')
			WebUI.delay(2)
			TestObject createFolderErrorNotification = CustomKeywords.'buildTestObj.CreateTestObjFiles.myTestObjFolderCreateErrorNotification'(FolderName)
			def notification = WebUI.verifyElementPresent(createFolderErrorNotification, 5)

			String msg = 'Please provide a valid name, A file name cannot contain any of the following characters "'+'\\ / ? : " '
			println(notification)
			if (notification)
			{
				extentTest.log(LogStatus.PASS, ('Notification with msg - "' + msg) + '" is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, FolderName + ' - Is Created')
				extentTest.log(LogStatus.FAIL)
			}
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to close Notification Panel')
			def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 3, extentTest, FolderName)
			println(fileItem)
			if (fileItem)
			{
				extentTest.log(LogStatus.FAIL, ('Folder - ' + FolderName) + ' is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, ('Verified Folder - ' + FolderName) + ' is not listed')

			}
			break;
	}
	WebUI.disableSmartWait()

	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
}catch (Exception  ex)
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
finally
{

	extent.endTest(extentTest);
	extent.flush();

}
