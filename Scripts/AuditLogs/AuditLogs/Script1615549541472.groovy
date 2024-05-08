import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.event.KeyEvent as KeyEvent
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.exception.StepFailedException as StepFailedException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

/*
'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_AdminUser, ('password') : GlobalVariable.G_AdminPasswd],
FailureHandling.STOP_ON_FAILURE)
*/
WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = (RemoteWebDriver) wrappedWebDriver


ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

String screenShot = ((('ExtentReports/' + TestCaseName) + userChoice) + GlobalVariable.G_Browser) + '.png'

def result=false

def text=null

def location = '/stage/pbsworks/'

WebUI.delay(2)

def TCName = TestCaseName + ' - through top menu icons'


TestObject newFileObj=null

try {
    WebUI.delay(2)

    //WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
    WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))

    extentTest.log(LogStatus.PASS, 'Click on profile tab')

    WebUI.delay(2)

    WebUI.click(findTestObject('AuditLogs/AuditLogs'))

    extentTest.log(LogStatus.PASS, 'Click on Audit Logs')

    switch (userChoice) {
        case 'ip add':
            String value = '10.75.22.142'

            WebUI.delay(2)

            result = WebUI.verifyElementPresent(findTestObject('Object Repository/AuditLogs/Ipadd'), 5)

            println('Ipaddress value - ' + result)

            if (result) {
                extentTest.log(LogStatus.PASS, ' Ipaddres value matched')

                extentTest.log(LogStatus.PASS, ('Ipaddress value  - "' + value) + '" is listed') //extentTest.log(LogStatus.PASS, fileToCheck + ' - Not pasted')
				extentTest.log(LogStatus.PASS, 'Verified that in Audit log page IP address field should show the correct local IP address')
            } else {
                extentTest.log(LogStatus.FAIL, 'Failed to verify the ipaddress')
            }
            
            //WebUI.verifyElementPresent(findTestObject('AuditLogs/Ipadd'), 4)
          

            break
        case 'Title':
            WebUI.verifyElementPresent(findTestObject('AuditLogs/AuditActivity'), 3)

            extentTest.log(LogStatus.PASS, ' Verify title element present ')

             result=WebUI.waitForElementPresent(findTestObject('Object Repository/AuditLogs/AuditActivity'), 5)
			if(result) {
				

             text = WebUI.getText(findTestObject('Object Repository/AuditLogs/AuditActivity'))

            extentTest.log(LogStatus.PASS, 'Title displayed -' + text)
				}
				else {
					extentTest.log(LogStatus.FAIL, "failed to verify the title")
				}

            break
        case 'Calendar':
            //Audit Log: Verify the arrows icons in 'From date' to 'To date' calendar
            WebUI.click(findTestObject('Object Repository/AuditLogs/Calendar_Icon_From_date'))

            extentTest.log(LogStatus.PASS, ' Click on  the Calendar icon::  From Date:: ')
			WebUI.delay(2)
			//WebUI.click(findTestObject('Object Repository/AuditLogs/From_date_fwd_arrow'))

            //From Date: Click calendar icon and verify that forward arrow is disabled & only backward arrow is enabled
            boolean res1 = WebUI.verifyElementClickable(findTestObject('Object Repository/AuditLogs/From_date_back_arrow'))

            boolean res2 = WebUI.verifyElementNotClickable(findTestObject('Object Repository/AuditLogs/From_date_fwd_arrow'))
		

            if (res1 && res2) {
                extentTest.log(LogStatus.PASS, ' verify that forward arrow is disabled & only backward arrow is enabled  ')
            } else {
                extentTest.log(LogStatus.FAIL, 'Failed to verify the arrow')
            }
            
            //To Date: Click calendar icon and verify that backward arrow is disabled & only forward arrow is enabled
            extentTest.log(LogStatus.PASS, ' Click on  the Calendar icon::  To Date:: ')

            WebUI.delay(4)
			

            WebUI.click(findTestObject('Object Repository/AuditLogs/Calender_Icon_To_date'))
			WebUI.delay(2)
			
		//	WebUI.click(findTestObject('Object Repository/AuditLogs/To_date_back_arrrow'))

            res1 = WebUI.verifyElementNotClickable(findTestObject('Object Repository/AuditLogs/To_date_back_arrrow'))

            res2 = WebUI.verifyElementClickable(findTestObject('Object Repository/AuditLogs/To_date_fwd_arrow'))
			

            if (res1 && res2) {
                extentTest.log(LogStatus.PASS, ' verify that backward arrow is disabled & only forward arrow is enabled  ')
            } else {
                extentTest.log(LogStatus.FAIL, 'Failed to verify the arrow')
            }
            
            break
        case 'Upload':
            WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))

            extentTest.log(LogStatus.PASS, ' Click on Files Tab ')

            WebUI.delay(2)

            WebUI.waitForElementVisible(findTestObject('2020.1/Upload_File'), 5)

            extentTest.log(LogStatus.PASS, 'Click on Upload file')

            'Click Upload File Button '
            def filePath = RunConfiguration.getProjectDir() + '/Upload/100MB_ActualTextFile.txt'

            def newFP = new generateFilePath.filePath().getFilePath(filePath)

            println(newFP)

            WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFP)

            extentTest.log(LogStatus.PASS, 'Upload  file')

            WebUI.delay(5)

            WebUI.click(findTestObject('Object Repository/AuditLogs/Cancel'))

            break
        case 'Disable':
            WebUI.click(findTestObject('AuditLogs/SearchBox'))

            extentTest.log(LogStatus.PASS, ' Click on Searchbox')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), '')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), username)

            extentTest.log(LogStatus.PASS, 'Add username name - ' + username)

            WebUI.sendKeys(findTestObject('Object Repository/AuditLogs/SearchBox'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, ' Hit on Enter ')

            WebUI.verifyElementPresent(findTestObject('AuditLogs/Nolog'), 3)

            extentTest.log(LogStatus.PASS, ' Verified that when user provides invalid search keyword, a message should be displayed No logs to show')

            WebUI.waitForElementPresent(findTestObject('AuditLogs/Nolog'), 5)

            text = WebUI.getText(findTestObject('AuditLogs/Nolog'))

            extentTest.log(LogStatus.PASS, 'No Log message genereted - ' + text)

			result=WebUI.verifyElementHasAttribute(findTestObject('Object Repository/AuditLogs/export_button'),'disabled', 20)
			

            def res1 = WebUI.verifyElementPresent(findTestObject('Object Repository/AuditLogs/Pagination_element1'),  5)
			def res2 = WebUI.verifyElementPresent(findTestObject('Object Repository/AuditLogs/Pagination_element2'),  5)
		//	def res3 = WebUI.verifyElementPresent(findTestObject('Object Repository/AuditLogs/Pagination_element3'),  5)
			
		//	def res4 = WebUI.verifyElementPresent(findTestObject('Object Repository/AuditLogs/Pagination_element4'),  5)
			

            if (res1 && res2 &&result) {
                extentTest.log(LogStatus.PASS, ' Verify < , << , > , >> arrows are disabled ')

                extentTest.log(LogStatus.PASS, ' Verify export data button is disabled ')
            } else {
                extentTest.log(LogStatus.FAIL, ' Failed to verify the buttons')
            }
            
            break
        case 'audit page':
            WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

            TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals', 
                AppName, true)

            WebUI.click(newAppObj)

            extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - ' + AppName)

            //	WebUI.doubleClick(newAppObj)
            WebUI.delay(2)

            def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'), 
                3, extentTest, 'Error Panel Close Icon')

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
                20, extentTest, 'Submit Button')

            if (submitBtn) {
                WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))

                extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')
            }
            
            WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)

            def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))

            extentTest.log(LogStatus.PASS, 'Notification Generated'+ jobText)

            CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)

            extentTest.log(LogStatus.PASS, 'Job ID - ' + GlobalVariable.G_JobID)

            extentTest.log(LogStatus.PASS, 'Job Submission Done for - ' + TestCaseName)

            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))

            extentTest.log(LogStatus.PASS, 'Click on profile tab')

            WebUI.delay(2)

            WebUI.click(findTestObject('AuditLogs/AuditLogs'))

            extentTest.log(LogStatus.PASS, 'Click on Audit Logs')

            WebUI.delay(2)

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            break
        case 'No logs':
            WebUI.click(findTestObject('AuditLogs/SearchBox'))

            extentTest.log(LogStatus.PASS, ' Click on Searchbox')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), '')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), username)

            extentTest.log(LogStatus.PASS, 'Add username name - ' + username)

            WebUI.sendKeys(findTestObject('Object Repository/AuditLogs/SearchBox'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, ' Hit on Enter ')

            WebUI.verifyElementPresent(findTestObject('AuditLogs/Nolog'), 3)

            extentTest.log(LogStatus.PASS, ' Verified that when user provides invalid search keyword, a message should be displayed No logs to show')

            result = WebUI.waitForElementPresent(findTestObject('AuditLogs/Nolog'), 5)

            text = WebUI.getText(findTestObject('AuditLogs/Nolog'))

            if (result) {
                extentTest.log(LogStatus.PASS, 'No Log message genereted - ' + text)
            } else {
                extentTest.log(LogStatus.FAIL, 'Not able to see NO logs message')
            }
            
            break
        case 'Download':
            WebUI.click(findTestObject('AuditLogs/ExportData'))

            WebUI.delay(5)

            /*
				 
								 File downloadFolder = new File(GlobalVariable.G_DownloadFolder)
				 
								 List namesOfFiles = Arrays.asList(downloadFolder.list())
				 
								 if (namesOfFiles.contains('auditlog')) {
									 println('success')
									 //extentTest.log(LogStatus.PASS, 'file to downloaded ')
				 
								 } else {
									 println('fail')
								 }
				 
								 return true
								 
								 */
            break
        case 'Logs':
            WebUI.click(findTestObject('AuditLogs/SearchBox'))

            extentTest.log(LogStatus.PASS, ' Click on Searchbox')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), '')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), 'Success')

            extentTest.log(LogStatus.PASS, 'Add username name pbsadmin')

            WebUI.sendKeys(findTestObject('Object Repository/AuditLogs/SearchBox'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, ' Hit on Enter ')

            WebUI.delay(2)

            extentTest.log(LogStatus.PASS, ' Log details ')

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            WebUI.delay(2)

            WebUI.click(findTestObject('AuditLogs/close_icon'))

            extentTest.log(LogStatus.PASS, ' Click on close icon ')

            WebUI.click(findTestObject('AuditLogs/SearchBox'))

            extentTest.log(LogStatus.PASS, ' Click on Searchbox')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), '')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), 'pbsworks')

            extentTest.log(LogStatus.PASS, 'Add username name pbsworks')

            WebUI.sendKeys(findTestObject('Object Repository/AuditLogs/SearchBox'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, ' Hit on Enter ')

            WebUI.delay(2)

            extentTest.log(LogStatus.PASS, ' Log details ')

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            WebUI.delay(2)

            WebUI.click(findTestObject('AuditLogs/close_icon'))

            extentTest.log(LogStatus.PASS, ' Click on close icon ')

            WebUI.click(findTestObject('AuditLogs/SearchBox'))

            extentTest.log(LogStatus.PASS, ' Click on Searchbox')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), '')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), 'delete')

            extentTest.log(LogStatus.PASS, 'Enter action  - delete')

            WebUI.sendKeys(findTestObject('Object Repository/AuditLogs/SearchBox'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, ' Hit on Enter ')

            WebUI.delay(2)

            extentTest.log(LogStatus.PASS, ' Log details ')

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            WebUI.click(findTestObject('AuditLogs/close_icon'))

            extentTest.log(LogStatus.PASS, ' Click on close icon ')

            WebUI.click(findTestObject('AuditLogs/SearchBox'))

            extentTest.log(LogStatus.PASS, ' Click on Searchbox')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), '')

            WebUI.setText(findTestObject('AuditLogs/SearchBox'), 'user')

            extentTest.log(LogStatus.PASS, 'Enter type - user')

            WebUI.sendKeys(findTestObject('Object Repository/AuditLogs/SearchBox'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, ' Hit on Enter ')

            WebUI.delay(2)

            extentTest.log(LogStatus.PASS, ' Log details ')

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            //WebUI.click(findTestObject('AuditLogs/Close'))
            //extentTest.log(LogStatus.PASS, ' Click on close icon ')
            break
        case 'Pagination':
            //WebUI.verifyElementPresent(findTestObject('AuditLogs/Pagination'), 3)
            //extentTest.log(LogStatus.PASS, ' Verified that >, <, >>, << buttons for paginations are working without any issue ')

          //  WebUI.verifyElementPresent(findTestObject('FilesPage/FilesPageNavigation'), 3, FailureHandling.STOP_ON_FAILURE)

//            WebUI.click(findTestObject('FilesPage/FilesPageNavigation'), FailureHandling.STOP_ON_FAILURE)

         //   extentTest.log(LogStatus.PASS, 'Click on page navigation')
			
			

          //  String data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

          //  println('value of page holder - ' + data)

          //  extentTest.log(LogStatus.PASS, 'Verified that when user enters a page number and hits enter paginations should work without any issue')
		
		WebUI.click(findTestObject('Object Repository/AuditLogs/pagination-dropdown'))
		
		boolean isPresent=	WebUI.verifyElementPresent(findTestObject('Object Repository/AuditLogs/Entries_per_page'), 2)
	
				extentTest.log(LogStatus.PASS, 'Select 25 log entries from the dropdown ')
				WebUI.click(findTestObject('Object Repository/AuditLogs/Entries_per_page'))
					if(isPresent) {
						extentTest.log(LogStatus.PASS, ' Verified that 25 logs present in Entries per page ')
						}
						
						boolean numberStatus=WebUI.verifyElementPresent(findTestObject('Object Repository/AuditLogs/pagination_number_status'), 2)
						if(numberStatus) {
						
							
							def pageentry=WebUI.getText(findTestObject('Object Repository/AuditLogs/pagination_number_status'))
							if(pageentry.contains('25')) {
								extentTest.log(LogStatus.PASS, ' Verified that Logs Showing Entry between 1 to 25 ')
							}
							else
								extentTest.log(LogStatus.FAIL, ' Failed to verify the entry ')
								WebUI.delay(2)
								
								WebUI.click(findTestObject('Object Repository/AuditLogs/Pagination_element3'))
								println("pageentry"+pageentry)
								
								extentTest.log(LogStatus.PASS, ' click on the >(next) arrow  ')
							 pageentry=WebUI.getText(findTestObject('Object Repository/AuditLogs/pagination_number_status'))
							 WebUI.delay(4)
							 println("pageentry"+pageentry)
								if(pageentry.contains('50')) {
									extentTest.log(LogStatus.PASS, ' Verified that Logs Showing Entry between 26 to 50 ')
								}
								else
									extentTest.log(LogStatus.FAIL, ' Failed to verify the entry ')
									
							
						
						}

            break
        case 'File':
            WebUI.delay(2)

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))

            extentTest.log(LogStatus.PASS, ' Click on Files Tab ')

            WebUI.delay(2)

            WebUI.waitForElementVisible(findTestObject('2020.1/Upload_File'), 5)

            extentTest.log(LogStatus.PASS, 'Click on Upload file')

            'Click Upload File Button '
            def filePath = RunConfiguration.getProjectDir() + '/Upload/InputDeck/RunJob.sh'

            def newFP = new generateFilePath.filePath().getFilePath(filePath)

            println(newFP)

            WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFP)

            extentTest.log(LogStatus.PASS, 'Upload  file')

            WebUI.delay(3)

            //WebUI.click(findTestObject('2020.1/Cancel_button'))
            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))

            extentTest.log(LogStatus.PASS, 'Click on profile tab')

            WebUI.delay(2)

            WebUI.click(findTestObject('AuditLogs/AuditLogs'))

            extentTest.log(LogStatus.PASS, 'Click on Audit Logs')

            WebUI.delay(2)

            extentTest.log(LogStatus.PASS, 'Verifying Log in action details')

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))

            extentTest.log(LogStatus.PASS, 'Click on file tab')

           /* WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

            extentTest.log(LogStatus.PASS, 'Click on edit path')

            WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

            WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')*/
			
			CustomKeywords.'generateFilePath.filePath.navlocation'(location, extentTest)
			

            WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

            extentTest.log(LogStatus.PASS, 'Click on files filter')

            WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)

            println(fileName)

            WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)

            extentTest.log(LogStatus.PASS, 'Looking for file to perfrom operation - ' + Operation)

            WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

            extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + fileName)

            newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', 
                fileName, true)

            def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, 
                fileName)

            println(fileItem)

            if (fileItem) {
                WebUI.waitForElementPresent(newFileObj, 3)

                WebUI.click(newFileObj)

                WebUI.rightClick(newFileObj)
            }
            
            WebUI.delay(2)

            println('after is else ' + Operation)

            def result1 = CustomKeywords.'operations_FileModule.fileOperations.executeFileOperations'(Operation, TestCaseName, 
                extentTest)

            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))

            extentTest.log(LogStatus.PASS, 'Click on profile tab')

            WebUI.delay(2)

            WebUI.click(findTestObject('AuditLogs/AuditLogs'))

            extentTest.log(LogStatus.PASS, 'Click on Audit Logs')

            WebUI.delay(2)

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            break
        case 'Jobs':
            WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

            TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals', 
                AppName, true)

            WebUI.click(newAppObj)

            extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - ' + AppName)

            //	WebUI.doubleClick(newAppObj)
            WebUI.delay(2)

            def errorPanel = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('JobSubmissionForm/JS_ErrorModalPanel'), 
                3, extentTest, 'Error Panel Close Icon')

            if (errorPanel) {
                WebUI.click(findTestObject('Object Repository/JobSubmissionForm/button_Close'))
            }
            
            WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))

            WebUI.delay(2)

            //CustomKeywords.'operations_JobsModule.JobSubmissions.JSAllFileds'(ToChange, ChangeValue,extentTest)
			
			CustomKeywords.'operations_JobsModule.JobSubmissions.JSAllFileds'(ToChange,ChangeValue, extentTest)
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
                20, extentTest, 'Submit Button')

            if (submitBtn) {
                WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))

                extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')
            }
            
            WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)

            def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))

            extentTest.log(LogStatus.PASS, 'Notification Generated')

            CustomKeywords.'operations_JobsModule.GetJobRowDetails.getJobID'(jobText)

            extentTest.log(LogStatus.PASS, 'Job ID - ' + GlobalVariable.G_JobID)

            extentTest.log(LogStatus.PASS, 'Job Submission Done for - ' + TestCaseName)

            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))

            extentTest.log(LogStatus.PASS, 'Click on profile tab')

            WebUI.delay(2)

            WebUI.click(findTestObject('AuditLogs/AuditLogs'))

            extentTest.log(LogStatus.PASS, 'Click on Audit Logs')

            WebUI.delay(2)

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))

            WebUI.delay(2)

           // WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

            TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 
                'equals', jobState, true)

            WebUI.click(newJobFilter)

            WebUI.delay(2)

            extentTest.log(LogStatus.INFO, 'Clicked on job with state  - ' + jobState)

            println(jobState)

            TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 
                'equals', jobState, true)

            WebUI.click(newJobRow)

            result = CustomKeywords.'operations_JobsModule.executeJobAction_Icon.perfromJobAction'(jobAction, TCName, extentTest)

            WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))

            extentTest.log(LogStatus.PASS, 'Click on profile tab')

            WebUI.delay(2)

            WebUI.click(findTestObject('AuditLogs/AuditLogs'))

            extentTest.log(LogStatus.PASS, 'Click on Audit Logs')

            WebUI.delay(2)

            extentTest.log(LogStatus.PASS, 'Verifying Log in action details')

            CustomKeywords.'demo.AuditLog.auditLogs'(katalonWebDriver, extentTest)

            break
			case 'last':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)

			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to last page arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('3')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}
			
		//CustomKeywords.'operations_FileModule.getRowDetails.getFilePage'(katalonWebDriver, extentTest,TestCaseName)
		extentTest.log(LogStatus.PASS, 'Test case for pagination passed')

			break
		case 'previous':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_PreviousPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)

			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'))

				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_PreviousPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to last page arrow ')

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to previous arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('2')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}
			
				CustomKeywords.'operations_FileModule.getRowDetails.getFilePage'(katalonWebDriver, extentTest,TestCaseName)
				extentTest.log(LogStatus.PASS, 'Test case for pagination passed')
			break
		case 'first':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)

			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'))

				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_FirstPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to last page arrow ')

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to first arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('1')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}
			
				CustomKeywords.'operations_FileModule.getRowDetails.getFilePage'(katalonWebDriver, extentTest,TestCaseName)
				extentTest.log(LogStatus.PASS, 'Test case for pagination passed')

			break
		case 'next':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.delay(2)
			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to next arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('2')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}
			
		//	CustomKeywords.'operations_FileModule.getRowDetails.getFilePage'(katalonWebDriver, extentTest,TestCaseName)
				extentTest.log(LogStatus.PASS, 'Test case for pagination passed')

			break
		case 'page':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.delay(2)
			if (isLinkPresent) {
				WebUI.click(findTestObject('FilesPage/PageHolder'))
				WebUI.delay(1)
				WebUI.setText(findTestObject('FilesPage/PageHolder'), '2')
				extentTest.log(LogStatus.PASS, 'Enter the value inside the pagenumber box and click on the Goto button ')

				WebUI.click(findTestObject('Object Repository/FilesPage/GoButton'))

				WebUI.delay(2)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')
				extentTest.log(LogStatus.PASS, 'Verify the current page number is  ' +data)

				if (data.equalsIgnoreCase('2')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box ---page case ----- ' + data)
				}
			}
			
				CustomKeywords.'operations_FileModule.getRowDetails.getFilePage'(katalonWebDriver, extentTest,TestCaseName)
				extentTest.log(LogStatus.PASS, 'Test case for pagination passed')

			break
	

    }
    
    if (GlobalVariable.G_Browser == 'IE') {
        WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
    }
}
catch (Exception ex) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

    extentTest.log(LogStatus.FAIL, ex)

    KeywordUtil.markFailed('ERROR: ' + e)
} 
catch (StepErrorException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

    extentTest.log(LogStatus.FAIL, e)

    KeywordUtil.markFailed('ERROR: ' + e)
} 
catch (StepFailedException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

    extentTest.log(LogStatus.FAIL, e)

    KeywordUtil.markFailed('ERROR: ' + e)
} 
finally { 
    extent.endTest(extentTest)

    extent.flush()
}

