package operations_JobsModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable

public class executeJobAction_JobDetails_Topmenu {

	@Keyword
	def perfromJobAction(String Action , String TestCaseName , extentTest) {
		def isNotoficationPresent=false
		boolean result=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		WebUI.delay(3)

		switch (Action) {
			case 'Download':
			case 'job_detail_download_btn':
				Action='job_detail_download_btn'

				println ("Form job actions - "+Action)
				WebUI.delay(2)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Jobdetails_topmenu'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				WebUI.delay(2)
			/*TestObject jobTitle=(new buildTestObj.CreateTestObj()).myJobTitleIndentifier()
			 def jobID=WebUI.getText(jobTitle)
			 String[] splitAddress = jobID.split('\\.')
			 GlobalVariable.G_JobIdFromDetails=splitAddress[0]
			 println GlobalVariable.G_JobIdFromDetails
			 extentTest.log(LogStatus.PASS, 'job id from details page '+ GlobalVariable.G_JobIdFromDetails)*/
				result=true
				break



			case 'job_detail_resubmit_btn':
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Jobdetails_topmenu'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				WebUI.delay(2)
				def isElementPresent=(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/JobDetailsPage/Msg_ResubmitWarning'), 5,extentTest, 'Resubmit Warning')
				if(isElementPresent) {
					//WebUI.check(findTestObject('Object Repository/JobDetailsPage/ChBx_RememberChoice'))
					WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
				}
				WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
				extentTest.log(LogStatus.PASS, 'resubmitted job  ')
				isNotoficationPresent=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
				def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
				def JobID=(new operations_JobsModule.GetJobRowDetails()).getJobID(jobText)
				extentTest.log(LogStatus.PASS, 'Verified notification - new job id '+ JobID)
				result=isNotoficationPresent

				return result
				break


			case 'MoveToQueue':
			case 'job_detail_movetoqueue_btn':
				Action='job_detail_movetoqueue_btn'

				println ("Form job actions - "+Action)
				WebUI.click(findTestObject('Object Repository/JobDetailsPage/Btn_MoveToQueue'))
				WebUI.delay(2)
				extentTest.log(LogStatus.PASS,"clicked on Move TO Queue Btn ")
				WebUI.click(findTestObject('Object Repository/JobDetailsPage/DropDown_accessQueue'))
				extentTest.log(LogStatus.PASS,"clicked on accessQueue menu item ")
				WebUI.delay(2)
				WebUI.click(findTestObject('JobDetailsPage/JobDetailsLink_Details'))
				extentTest.log(LogStatus.PASS,"Navigated to Details Tab")
				WebUI.click(findTestObject('JobDetailsPage/TextBx_DetailsFilter'))
				WebUI.setText(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), 'queue name')
				WebUI.click(findTestObject('JobDetailsPage/Detail_QueueName'))
				TestObject newQueueObj = WebUI.modifyObjectProperty(findTestObject('JobDetailsPage/Detail_QueueName'), 'text', 'equals',	'accessQueue', true)
				println("---------- queuename "+WebUI.waitForElementPresent(newQueueObj, 4, FailureHandling.CONTINUE_ON_FAILURE))

				def queueName=WebUI.getText(newQueueObj)
				if(queueName.equals("accessQueue")) {
					result=true
					extentTest.log(LogStatus.PASS,"Verified queue name in job properties - "+ queueName)
				}
				else
					result=false
				break

			case 'job_detail_terminate_btn':
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Jobdetails_topmenu'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)

				WebUI.delay(2)
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				WebUI.delay(2)
				extentTest.log(LogStatus.PASS, 'terminating job  ')
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
				WebUI.delay(2)
				isNotoficationPresent=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobTerminate'), 5)
				println("notification status - "+isNotoficationPresent)
				extentTest.log(LogStatus.PASS, 'Verified notification')
				result=isNotoficationPresent
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))

				result=true
				break

			case 'job_detail_upload_btn':
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Jobdetails_topmenu'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				result=true
				break

			case 'job_detail_delete_btn':
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Jobdetails_topmenu'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				WebUI.delay(2)
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				WebUI.delay(2)
				extentTest.log(LogStatus.PASS, 'deleting job  ')
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
				WebUI.delay(3)
				isNotoficationPresent=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobDelete'), 5)
				println("notification status - "+isNotoficationPresent)
				extentTest.log(LogStatus.PASS, 'Verified notification')
				result=isNotoficationPresent
				result=true
				break
			/*
			 def jobID=WebUI.getText(findTestObject('JobDetailsPage/jobTitle'))
			 String[] splitAddress = jobID.split('\\.')
			 GlobalVariable.G_JobIdFromDetails=splitAddress[0]
			 println GlobalVariable.G_JobIdFromDetails
			 */extentTest.log(LogStatus.PASS, 'job id from details page '+ GlobalVariable.G_JobIdFromDetails)
				result=true
				break


			default:
				break
		}
		return result
	}
}
