import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

String ReportFile = GlobalVariable.G_ReportName + '.html'
def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)

fileName="CntxMenu"+fileName

println (fileName)
//WebUI.delay(2)

def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location=navLocation+'/FilesModule/CreateFiles/ListView'


TestObject newFileObj
WebUI.enableSmartWait()
if (TestCaseName.contains('tile view'))
{
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', fileName, true )
	location=navLocation+'/FilesModule/CreateFiles/TileView'

}
else
{
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', fileName, true)
}

println('================================================================')
println (location)
println('================================================================')


CustomKeywords.'toLogin.ForLogin.Login'(extentTest)


try
{
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20,extentTest,'Files Tab')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

	extentTest.log(LogStatus.PASS, "Navigated to Files Tab " )

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName,extentTest)

	//WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	//WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	//WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	//extentTest.log(LogStatus.PASS, 'Navigated to - '+location)
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)

	
	//WebUI.delay(3)
	
	
	//WebUI.mouseOver(findTestObject('Object Repository/FilesPage/GoButton'), FailureHandling.CONTINUE_ON_FAILURE)
	//WebUI.mouseOverOffset(findTestObject('Object Repository/FilesPage/GoButton'), 10, -60, )
//	WebUI.rightClickOffset(findTestObject('Object Repository/FilesPage/GoButton'), 10, -60, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.mouseOver(findTestObject('Object Repository/FilesPage/Pagination_icons'), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.mouseOverOffset(findTestObject('Object Repository/FilesPage/Pagination_icons'), 10, -60, )
	WebUI.rightClickOffset(findTestObject('Object Repository/FilesPage/Pagination_icons'), 10, -60, FailureHandling.CONTINUE_ON_FAILURE)

	
//WebUI.mouseOver(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), FailureHandling.CONTINUE_ON_FAILURE)
//WebUI.mouseOverOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), 10, 200, )
//WebUI.rightClickOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), 10, 200, FailureHandling.CONTINUE_ON_FAILURE)

	
	/*	//WebUI.mouseOverOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), 10, 450, )
	WebUI.mouseOverOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), -50, -200, )
	WebUI.delay(2)

	//WebUI.rightClickOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), 10, 450, FailureHandling.STOP_ON_FAILURE)
	extentTest.log(LogStatus.PASS,"Invoked context menu in empty area ")
	WebUI.rightClickOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), -50, -200, FailureHandling.STOP_ON_FAILURE)

	WebUI.delay(2)*/


	WebUI.click(findTestObject('FilesPage/ContextMenu_CreateNewFile'))
	extentTest.log(LogStatus.PASS,"Clicked context menu item  - New file ")

	WebUI.click(findTestObject('FilesPage/TextBx_CreateFile'))

	WebUI.waitForElementVisible(findTestObject('FilesPage/TextBx_CreateFile'), 5)

	WebUI.click(findTestObject('FilesPage/TextBx_CreateFile'))


	println("============================== "+fileName)
	WebUI.setText(findTestObject('FilesPage/TextBx_CreateFile'), fileName)
	extentTest.log(LogStatus.PASS,"Enterted File Name to create - "+fileName)
	WebUI.click(findTestObject('FilesPage/btn_Save'))
	extentTest.log(LogStatus.PASS, "Clicked on Save Button" )

		
	switch (userChoice)
	{
		case 'Positive':

			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to open Notification Panel')
			//WebUI.delay(2)
			TestObject createFileNotification = CustomKeywords.'buildTestObj.CreateTestObjFiles.myTestObjFileCreateNotification'(fileName)
			def notification = WebUI.verifyElementPresent(createFileNotification, 5)

			String msg = ('The File ' + fileName) + ' has been created successfully'
			println(notification)
			if (notification)
			{
				extentTest.log(LogStatus.PASS, fileName + '- Created File and verified notification')
				extentTest.log(LogStatus.PASS, ('Notification with msg - "' + msg) + '" is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, fileName + ' - Not Created')
				extentTest.log(LogStatus.FAIL)
			}
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to close Notification Panel')
			def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, fileName)
			println(fileItem)
			if (fileItem)
			{
				WebUI.waitForElementPresent(newFileObj, 3)
				extentTest.log(LogStatus.PASS, ('Verified File - ' + fileName) + ' is listed')
			}
			break;

		case 'Negative':
			WebUI.verifyElementPresent(findTestObject('FilesPage/SpecialChar_popup'), 3)
			WebUI.click(findTestObject('Object Repository/FilesPage/btn_Cancel'))
			//WebUI.delay(2)
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to open Notification Panel')
			//WebUI.delay(2)
			TestObject createFolderErrorNotification = CustomKeywords.'buildTestObj.CreateTestObjFiles.myTestObjFolderCreateErrorNotification'(fileName)
			def notification = WebUI.verifyElementPresent(createFolderErrorNotification, 5)

			String msg = 'Please provide a valid name, A file name cannot contain any of the following characters "'+'\\ / ? : " '
			println(notification)
			if (notification)
			{
				extentTest.log(LogStatus.PASS, ('Notification with msg - "' + msg) + '" is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, fileName + ' - Is Created')
				extentTest.log(LogStatus.FAIL)
			}
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to close Notification Panel')
			def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 3, extentTest, fileName)
			println(fileItem)
			if (fileItem)
			{
				extentTest.log(LogStatus.FAIL, ('File - ' + fileName) + ' is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, ('Verified File - ' + fileName) + ' is not listed')

			}
			break;
	}
	WebUI.disableSmartWait()

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
	KeywordUtil.markFailed('ERROR: '+ e)

}
catch (StepErrorException  e)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))
	KeywordUtil.markFailed('ERROR: '+ e)


}
finally
{

	extent.endTest(extentTest);
	extent.flush();

}