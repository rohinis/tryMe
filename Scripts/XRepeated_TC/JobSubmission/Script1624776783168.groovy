import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password], 
    FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

String ReportFile = GlobalVariable.G_ReportName + '.html'

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

TestObject newFileObj

try {
    	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/JobsTab_disabled'),
		20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}

    
    extentTest.log(LogStatus.PASS, 'Navigated Jobs Tab')

    WebUI.delay(2)

    TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals', 
        AppName, true)

    WebUI.click(newAppObj)

    extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - ' + AppName)

    //	WebUI.doubleClick(newAppObj)
    WebUI.delay(2)

    def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'), 
        3,extentTest,'Error Panel Close Icon')

    if (errorPanel) {
        WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
    }
    
    WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))

    WebUI.delay(2)

    CustomKeywords.'operations_JobsModule.JobSubmissions.JSAllFileds'(ToChange, ChangeValue, extentTest)

    if (ExecMode.equals('Array')) {
        extentTest.log(LogStatus.PASS, 'No file required for Array Job')
    } else {
        WebUI.delay(2)

        WebUI.scrollToElement(findTestObject('JobSubmissionForm/Link_Server'), 3)

        WebUI.delay(3)

        newFileObj = CustomKeywords.'operations_JobsModule.JobSubmissions.selectFile'(ExecMode, InputFile, extentTest)

        WebUI.click(newFileObj)

        WebUI.rightClick(newFileObj)

        extentTest.log(LogStatus.PASS, 'Right Clicked on Input file ' + InputFile)

        WebUI.delay(2)

        String idForCntxtMn = 'Add as ' + FileArg

        TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'), 
            'id', 'equals', idForCntxtMn, true)

        WebUI.click(newRFBContextMnOption)

        extentTest.log(LogStatus.PASS, 'Clicked on context menu - ' + idForCntxtMn)
    }
    
    def submitBtn = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/button_Submit_Job'), 
        20,extentTest,'Submit Button')

    if (submitBtn) {
        WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))

        extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')
    }
    
    WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)

    def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))

    extentTest.log(LogStatus.PASS, 'Notification Generated')

    GlobalVariable.G_JobID=CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)

    extentTest.log(LogStatus.PASS, 'Job ID - ' + GlobalVariable.G_JobID)

    extentTest.log(LogStatus.PASS, 'Job Submission Done for - ' + TestCaseName)
	
    /*def submitBtn1 = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/button_Submit_Job'), 
        20,extentTest,'Submit Button')

    if (submitBtn1) {
		extentTest.log(LogStatus.PASS, 'Verified Notification')
		
    }
*/
 //   WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

    if (ToChange.equals('SetOutPutDir')) {
        WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))

        extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')

        WebUI.delay(2)

        TestObject viewIconTile = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 
            'title', 'equals', 'Tile View', true)

        TestObject viewIconList = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 
            'title', 'equals', 'List View', true)

        viewIconTilePresent = WebUI.waitForElementPresent(viewIconTile, 3, FailureHandling.CONTINUE_ON_FAILURE)

        viewIconListPresent = WebUI.waitForElementPresent(viewIconList, 3, FailureHandling.CONTINUE_ON_FAILURE)

        println('viewIconTilePresent - ' + viewIconTilePresent)

        println('viewIconListPresent - ' + viewIconListPresent)

        if (viewIconListPresent) {
            WebUI.click(viewIconList)

            extentTest.log(LogStatus.PASS, 'Changed View to ListView')

            WebUI.delay(2)
        }
        
        WebUI.delay(2)

        WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

        def navLocation = new generateFilePath.filePath().execLocation()

        def location = (navLocation + '/JobsModule/') + ChangeValue

        WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

        WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))

        extentTest.log(LogStatus.PASS, ('Navigated to ' + location) + ' in files tab')

        TestObject newFileObjJS = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 
            'equals', InputFile, true)

        def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObjJS, 20,extentTest,InputFile)

        println(fileItem)

        if (fileItem) {
            extentTest.log(LogStatus.PASS, 'Output file - jobFile.out exists ')
        }
    }
    
    TestObject jobIdEle = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjJobRow'(GlobalVariable.G_JobID)

    WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

    WebUI.delay(2)

   // String jobState = CustomKeywords.'operations_JobsModule.JobSubmissions.printJobState'(katalonWebDriver, extentTest)

    if (ExecMode.equals('Array')) {
        //call keyword 
//	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/Icon_Close'))
	WebUI.delay(2)
		CustomKeywords.'operations_JobsModule.GetJobRowDetails.checkSubJobs'(katalonWebDriver,'JS',extentTest)
		
		    }
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))
	extentTest.log(LogStatus.PASS, 'Click on reset')

	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	println jobState
	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	jobState, true)
		WebUI.rightClick(newJobRow)
		
		
	
	WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

   //WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder_File'), 5)
	//WebUI.click(findTestObject('JobMonitoringPage/OutputFolder_File'))
	WebUI.click(findTestObject('JobMonitoringPage/RunningFolder'))
	extentTest.log(LogStatus.PASS, 'Click on Input Folder')
	
	
	
	
	def filePathFT = (RunConfiguration.getProjectDir() + '/Upload/') + ToUpload
	def newFPFT=CustomKeywords.'generateFilePath.filePath.getFilePath'(filePathFT)
	println(newFPFT)
	
	WebUI.uploadFile(findTestObject('2020.1/UploadFile'), newFPFT )
	extentTest.log(LogStatus.PASS, 'Uploading zip file - JobsModule.zip')
	WebUI.delay(5)
	WebUI.click(findTestObject('Object Repository/FilesPage/button_Yes'))
	extentTest.log(LogStatus.PASS, 'Clicked YES on Unzip on Upload confirmation pop-up')
	WebUI.delay(2)
		
		
    
    if (GlobalVariable.G_Browser == 'Edge') {
        WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
    }
}
catch (Exception ex) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

  	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


    KeywordUtil.markFailed('ERROR: ' + e)
} 
catch (StepErrorException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

 	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


    KeywordUtil.markFailed('ERROR: ' + e)
} 
finally { 
    extent.endTest(extentTest)

    extent.flush()
}

