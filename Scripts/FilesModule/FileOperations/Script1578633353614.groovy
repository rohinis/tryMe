import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

TestObject newFileObj = null

def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()

def location = navLocation + '/FilesModule/FileOps/'

if (TestCaseName.contains('tile view')) {
    newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', fileName, 
        true)
} else {
    newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', 
        fileName, true)
}

try {
    def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'), 
        20, extentTest, 'Files Tab')

    if (filesTab) {
        WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
    }
    WebUI.enableSmartWait()
    extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')

   // WebUI.delay(2)

    println('==============================================')

    println(TestCaseName)

    println('==============================================')

    CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

    if (TestCaseName.contains('Upload')) {
        println(TestOperation) /*	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Navigated to ' + location)*/
    } else {
        CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
		
		WebUI.delay(2)
   

        WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 5)

        println(fileName)
		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))
		

        WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)

        extentTest.log(LogStatus.PASS, (('Looking for file - ' + fileName) + ' to perfrom operation - ') + TestOperation)

        WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

        extentTest.log(LogStatus.PASS, 'Found File  - ' + fileName)
		extentTest.log(LogStatus.PASS,'Searched the file by using Search box in the Files Page ')

        def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, fileName)

        println(fileItem)

        if (fileItem) {
            WebUI.waitForElementPresent(newFileObj, 3)

            WebUI.click(newFileObj)

            extentTest.log(LogStatus.PASS, 'Clicked on file ' + fileName)

            WebUI.rightClick(newFileObj)

            extentTest.log(LogStatus.PASS, 'Right Clicked File to invoke context menu on  - ' + fileName)
        }
    }
    
  //  WebUI.delay(2)
	WebUI.disableSmartWait()

    def result = CustomKeywords.'operations_FileModule.fileOperations.executeFileOperations'(TestOperation, TestCaseName, 
        extentTest)

    if (result) {
        extentTest.log(LogStatus.PASS, ('Verified - ' + TestCaseName) + '  Sucessfully')
    } else {
        extentTest.log(LogStatus.FAIL, TestCaseName + ' - failed')
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
    extent.endTest(extentTest)

    extent.flush()
}

