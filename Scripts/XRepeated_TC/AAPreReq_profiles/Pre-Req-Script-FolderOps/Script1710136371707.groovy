import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys


import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable


//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location=navLocation
println('##################################################################')
println (location)
println('##################################################################')


try {


	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	WebUI.delay(2)
	
	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)
	
	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	//extentTest.log(LogStatus.PASS, 'Navigated to -  '+  location)

	String[] ZipFile=['MultipleFolder.zip','MultipleFolderIcons.zip','FoldersModule.zip']
	int i=0
	for (String name:ZipFile)
	{
		String zipName =ZipFile[i]
		println(zipName)
		filePathFT = (RunConfiguration.getProjectDir() + '/Upload/') + zipName
		newFPFT=CustomKeywords.'generateFilePath.filePath.getFilePath'(filePathFT)
		println(newFPFT)
		WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFPFT )
		WebUI.delay(2)
		isCancelPresent = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('/FilesPage/Cancel_button'),5,extentTest,'Cancel button')
		if (isCancelPresent) {
			WebUI.click(findTestObject('/FilesPage/Cancel_button'))
		}
		i=i+1
	}
i=0
	WebUI.delay(2)
	for (String name:ZipFile)
	{
		String zipName =ZipFile[i]
		TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals',zipName, true)
		WebUI.rightClick(newFileObj)
		WebUI.delay(3)
		WebUI.click(findTestObject('FilesPage/ContextMenu_FileGrid_UnCompress'))
		i=i+1
	}


}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	//extentTest.log(LogStatus.FAIL, ex)

	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	//extentTest.log(LogStatus.FAIL, e)

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	//extent.endTest(extentTest)

	//extent.flush()


}

