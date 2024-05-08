import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.configuration.RunConfiguration
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
	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
	WebUI.delay(2)


	def filePath = (RunConfiguration.getProjectDir() + '/Upload/InputDeck/NewBar.fem')
	def newFP=(new generateFilePath.filePath()).getFilePath(filePath)
	println(newFP)
	WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFP )



	TestObject newFileObj
	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)
	if (TestCaseName.contains('tile view')) {
		WebUI.delay(2)
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'data-automation-id', 'equals', fileName, true)
	}
	else
	{
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', fileName, true)
	}

	WebUI.delay(2)

	println(TestCaseName)

	if (TestCaseName.contains('Upload'))
	{
		println Operation

	}

	else
	{
		if (TestCaseName.contains('tile view')) {
			println("in tile wala if")
			WebUI.click(viewIconTile)
			extentTest.log(LogStatus.PASS, 'Changed View to Tile View - test case - ' +TestCaseName)

			WebUI.delay(2)

			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'data-automation-id', 'equals',
					fileName, true //	WebUI.click(newFileObj)
					)
		} else {
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id',
					'equals', fileName, true)
		}
		//WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
		def location='/stage/'+GlobalVariable.G_userName
		WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
		WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('Object Repository/FilesPage/gotofoldertext'), 10)
		WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), location)
		WebUI.delay(2)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Navigated to ' + location)
		//WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

		//WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		//extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')

		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

		WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)


		println(fileName)

		WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)


		WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + fileName)


		//boolean fileItem=WebUI.verifyElementPresent(newFileObj, 3)
		/*def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 5,extentTest,fileName)

		println(fileItem)

		if (fileItem) {

			WebUI.waitForElementPresent(newFileObj, 3)

			WebUI.click(newFileObj)

			WebUI.rightClick(newFileObj)
			extentTest.log(LogStatus.PASS, "right click on newFileObj")
		}


	}
	WebUI.mouseOver(findTestObject('2019.3/ProcessWith'))
	WebUI.click(findTestObject('2019.3/ProcessWith'))
	extentTest.log(LogStatus.PASS, 'Click on ProcessWith')*/

		
		boolean isNewFileExist=WebUI.verifyElementPresent(newFileObj, 10)
		if(isNewFileExist) {
		
		WebUI.rightClick(newFileObj)
		WebUI.delay(2)
		}
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
			WebUI.delay(2)
			WebUI.mouseOver(findTestObject('FilesPage/ContextMn_Process With'))
			WebUI.delay(2)
			WebUI.click(findTestObject('FilesPage/ContextMn_Process With'))
			WebUI.delay(2)
			extentTest.log(LogStatus.PASS, 'Clicked on Process With ')
		}
		
	WebUI.mouseOver(findTestObject('2019.3/span_Optistruct'))
	WebUI.delay(2)
	WebUI.click(findTestObject('2019.3/span_Optistruct'))
	extentTest.log(LogStatus.PASS, 'Click on Optistruct')

	WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
	extentTest.log(LogStatus.PASS, "Opened Notification Panel" )
	WebUI.delay(2)
	result = WebUI.verifyElementPresent(findTestObject('Notificactions/Notification_JobSubmission'),5)

	println("notification status - "+result)
	extentTest.log(LogStatus.PASS, "Verified notification for job submission ")

	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}}
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


