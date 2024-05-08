import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

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
TestCaseName=TestCaseName + ' through TopMenu icons'
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FoldersModule/FolderOpsIcons'
//=====================================================================================

//WebUI.delay(2)
WebUI.enableSmartWait()

try {
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20, extentTest, 'App def')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

	//WebUI.delay(3)

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

	if (TestCaseName.contains('tile view')) {
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/FolderRowItem_TileView'), 'data-automation-id', 'equals',folderName, true )

	} else {
		newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/FolderRowItem_ListView'), 'data-automation-id','equals', folderName, true)

	}
	if (TestCaseName.contains('Upload')) {
		println(Operation) //	WebUI.click(newFileObj)
		//WebUI.rightClick(newFileObj)
	}
	 else {

	/*	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, ('Navigated to -' + location) + ' in RFB ')*/
		 CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)

		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))
		WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)
		WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), folderName)
		WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + folderName)

		def folderItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, folderName)
		println(folderItem)
		if (folderItem) {
			WebUI.waitForElementPresent(newFileObj, 3)
			WebUI.click(newFileObj)
		}
	}
	//WebUI.delay(2)
	println('after is else ' + Operation)

	def result = CustomKeywords.'operations_FileModule.folderOperations_Icon.executeFolderOperations'(Operation, TestCaseName, extentTest)
	//def result = CustomKeywords.'demo.hello.executeFileOperations'(Operation, TestCaseName,extentTest)
	if (result) {
		extentTest.log(LogStatus.PASS, ('File Operation - ' + TestCaseName) + ' Performed Sucessfully')
	} else {
		extentTest.log(LogStatus.FAIL, ('File Operation - ' + TestCaseName) + ' failed')
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
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
}
finally {
	extent.endTest(extentTest)
	extent.flush()
}


