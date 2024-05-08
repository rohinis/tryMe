package operations_FileModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable

public class four {

	@Keyword
	def executeFileOperations(String Operation,String TestCaseName ,extentTest) {

		def date = new Date()
		def sdf = new SimpleDateFormat("ddMMyyyy_HHmmss")
		def e1 = sdf.format(date)
		def e2 =sdf.format(date)
		boolean result=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		println ("Control in Keyword --- icon ops ---- e2 ----- "+ e2)
		WebUI.delay(2)
		switch (Operation) {




			case 'Delete':

			//	WebUI.doubleClick(findTestObject('JobMonitoringPage/ListView'))
			//	extentTest.log(LogStatus.PASS, 'Double click on List view folder')
				WebUI.delay(3)
				WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
				extentTest.log(LogStatus.PASS, ' Click on select all ')
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Jobdetails_topmenu'),'id', 'equals', 'job_detail_delete_btn', true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				WebUI.delay(2)
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				WebUI.delay(2)
				extentTest.log(LogStatus.PASS, 'deleting job  ')
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
				WebUI.delay(3)
				def isNotoficationPresent=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobDelete'), 5)
				println("notification status - "+isNotoficationPresent)
				extentTest.log(LogStatus.PASS, 'Verified notification')
				result=isNotoficationPresent
				result=true
				break

			case 'Download':
			//	WebUI.doubleClick(findTestObject('JobMonitoringPage/ListView'))
			//	extentTest.log(LogStatus.PASS, 'Double click on List view folder')
				WebUI.delay(3)
				WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
				extentTest.log(LogStatus.PASS, ' Click on select all ')
				WebUI.rightClick(findTestObject('JobMonitoringPage/RowItem_JobdDeails'))
				extentTest.log(LogStatus.PASS, 'Right click on folder to perform Download operation using icon')

				WebUI.click(findTestObject('Object Repository/JobMonitoringPage/Download_Icon'))

				File downloadFolder = new File(GlobalVariable.G_DownloadFolder)

				List namesOfFiles = Arrays.asList(downloadFolder.list())

				if (namesOfFiles.contains('ToDownload.txt')) {
					println('success')
					//extentTest.log(LogStatus.PASS, 'file to downloaded ')

				} else {
					println('fail')
				}

				return true
				break

			case 'Resubmit':

			//	WebUI.doubleClick(findTestObject('JobMonitoringPage/ListView'))
			//	extentTest.log(LogStatus.PASS, 'Double click on List view folder')
				WebUI.delay(3)
				WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
				extentTest.log(LogStatus.PASS, ' Click on select all ')
				WebUI.rightClick(findTestObject('JobMonitoringPage/RowItem_JobdDeails'))
				extentTest.log(LogStatus.PASS, 'Right click on folder to perform Resubmit  operation')

				TestObject newFileOp=WebUI.modifyObjectProperty(findTestObject('Object Repository/JobMonitoringPage/Resubmit_Icon'), 'text', 'equals', Operation, true)
				WebUI.click(newFileOp)

				def isElementPresent=(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/JobDetailsPage/Msg_ResubmitWarning'), 5,extentTest, 'Resubmit Warning')
				if(isElementPresent) {
					WebUI.check(findTestObject('Object Repository/JobDetailsPage/Msg_ResubmitWarning'))
					WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
				}
				WebUI.delay(2)

				WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))

				extentTest.log(LogStatus.PASS, 'Clicked on Submit Button ')

				result=WebUI.verifyElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5, FailureHandling.CONTINUE_ON_FAILURE)


				def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))

				extentTest.log(LogStatus.PASS, 'Notification Generated')

				def JobID=(new operations_JobsModule.GetJobRowDetails()).getJobID(jobText)


				extentTest.log(LogStatus.PASS, 'Job ID - ' + JobID)

				extentTest.log(LogStatus.PASS, 'Job Submission Done for - ' + TestCaseName)
				return result

				break
		}
	}
}


