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

	def filePathFT
	def newFPFT
	def isCancelPresent

	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	WebUI.delay(2)

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

	WebUI.waitForElementVisible(findTestObject('FilesPage/btn_NewFileFolder'), 10)
	WebUI.click(findTestObject('FilesPage/btn_NewFileFolder'))
	WebUI.click(findTestObject('FilesPage/ListItem_Folder'))
	WebUI.waitForElementVisible(findTestObject('FilesPage/TextBxFolder_input'), 5)
	WebUI.setText(findTestObject('FilesPage/TextBxFolder_input'), 'UnZipOps')
	WebUI.click(findTestObject('FilesPage/btn_Save'))
	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_Refresh'))


	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))


String[] ZipFile=['MultipleFiles.zip','MultipleFilesIcons.zip','FilesModule.zip','MultipleFolder.zip','MultipleFolderIcons.zip','FoldersModule.zip']
		int i=0
	WebUI.delay(3)
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
	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	println("end")
}

