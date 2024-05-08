import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

//WebUI.delay(2)
TestObject newFileObj

//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation+'/UnZipOps'
//=====================================================================================
WebUI.enableSmartWait()
try {
    def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 
        20, extentTest, 'App def')

    if (filesTab) {
        WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
    }
    extentTest.log(LogStatus.PASS, 'Naviagted to Files Tab')

    CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)
   // WebUI.delay(2)

/*	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
    WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
    WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
    extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)
    WebUI.delay(2)*/
	CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
	
    def filePath = (RunConfiguration.getProjectDir() + '/Upload/') + fileToUpload
    def newFP = CustomKeywords.'generateFilePath.filePath.getFilePath'(filePath)

    WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFP)
    extentTest.log(LogStatus.PASS, 'Uploading zip file - '+fileToUpload)

		def result=CustomKeywords.'operations_FileModule.checkUnZip.performUnzip'(TestCaseName, extentTest, userChoice)
		
	if(result){
    extentTest.log(LogStatus.PASS, 'Verified test case - '+TestCaseName)
	}
	else
	{
    extentTest.log(LogStatus.FAIL, 'Test case - '+TestCaseName+' failed')
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
    extent.endTest(extentTest)
    extent.flush()
}
