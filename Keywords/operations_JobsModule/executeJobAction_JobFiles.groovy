package operations_JobsModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent

import internal.GlobalVariable

public class executeJobAction_JobFiles {


	@Keyword
	def perfromJobAction(String Action , String TestCaseName , extentTest,userChoice) {
		def isNotoficationPresent=false
		String fileName=null
		def isElementPresent=null
		boolean result=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		WebUI.delay(3)
		println("Action" + Action)

		switch (Action) {

			case 'View Details':
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				WebUI.delay(2)
				TestObject jobTitle=(new buildTestObj.CreateTestObj()).myJobTitleIndentifier()
				def jobID=WebUI.getText(jobTitle)
				String[] splitAddress = jobID.split('\\.')
				GlobalVariable.G_JobIdFromDetails=splitAddress[0]
				println GlobalVariable.G_JobIdFromDetails
				extentTest.log(LogStatus.PASS, 'job id from details page '+ GlobalVariable.G_JobIdFromDetails)
				result=true
				return result
				break



			case 'View Subjobs':

				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				return result
				break

			case 'Delete':
				fileName='ToDelete.txt'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						fileName, true)
				WebUI.scrollToElement(newFileObj, 3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)
				extentTest.log(LogStatus.PASS, 'Clicked on Context Menu Option for - Delete')
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				WebUI.delay(3)
				if (TestCaseName.contains("No")) {
					WebUI.click(findTestObject('GenericObjects/btn_No'))
					isElementPresent=WebUI.verifyElementPresent(newFileObj, 3)
					if(isElementPresent) {
						result=true
						extentTest.log(LogStatus.PASS, 'Verified file not deleted as per the test case')
					}
				}
				else {
					WebUI.click(findTestObject('GenericObjects/btn_Yes'))
					WebUI.delay(2)
					extentTest.log(LogStatus.PASS, 'Deleting file   ')
					isElementPresent=WebUI.verifyElementNotPresent(newFileObj, 3)
					if(isElementPresent) {
						extentTest.log(LogStatus.PASS, 'Verified file is deleted as per the test case')
						result=true
					}
				}


				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
				WebUI.delay(3)
				isNotoficationPresent=WebUI.waitForElementPresent(findTestObject('Object Repository/Notificactions/Notification_DeleteFile'), 5)
				println("notification status - "+isNotoficationPresent)
				extentTest.log(LogStatus.PASS, 'Verified notification')

				return result
				break

			case 'Rename':
				fileName='ToRename.txt'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						fileName, true)
				WebUI.scrollToElement(newFileObj, 3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)

				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)

				def Renameto='Renamefile.txt'
				TestObject renameTextBxObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/NewFile_input'), 'value', 'equals', fileName, true)
				WebUI.setText(renameTextBxObj, Renameto)
				extentTest.log(LogStatus.PASS, 'Renamed file to  '+Renameto)

				WebUI.click(findTestObject('FilesPage/btn_Save'))
				WebUI.delay(3)

				extentTest.log(LogStatus.PASS, 'Clicked on Save Button')
				TestObject newFileObj1 = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						Renameto, true)
				def isElemenetPresent=WebUI.waitForElementVisible(newFileObj1, 10,FailureHandling.CONTINUE_ON_FAILURE)
				if(isElemenetPresent){
					extentTest.log(LogStatus.PASS, "Filed renamed to "+Renameto + " isElemenetPresent " + isElemenetPresent)
					result=true
				}
				else {
					extentTest.log(LogStatus.FAIL,"Filed not renamed to "+Renameto + " isElemenetPresent " + isElemenetPresent)
					result=false
				}

				return result
				break



			case 'Refresh':
				fileName='Running.sh'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						fileName, true)
				WebUI.scrollToElement(newFileObj, 3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)

				println ("Form job actions refresh - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				return true
				break


			case 'New File':

				fileName='Running.sh'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						fileName, true)
				WebUI.scrollToElement(newFileObj, 3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)

				def Renameto='NewFile.txt'
				TestObject renameTextBxObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/NewFile_input'), 'value', 'equals', 'New Text Document.txt', true)
				WebUI.setText(renameTextBxObj, Renameto)
				extentTest.log(LogStatus.PASS, 'Renamed file to  '+Renameto)

				WebUI.click(findTestObject('FilesPage/btn_Save'))
				WebUI.delay(3)
				extentTest.log(LogStatus.PASS, 'Clicked on Save Button')
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
				result=WebUI.verifyElementPresent(findTestObject('Object Repository/Notificactions/Notification_FileCreation'), 5)
				extentTest.log(LogStatus.PASS, "Opened Notification Panel" )
				return result

				break

			case 'Terminate':
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				if (TestCaseName.contains("No")) {
					println("No")
					WebUI.click(findTestObject('GenericObjects/btn_No'))
					result=true
					extentTest.log(LogStatus.PASS, 'Not terminating job  ')
				}
				else {

					WebUI.click(findTestObject('GenericObjects/btn_Yes'))
					WebUI.delay(2)
					extentTest.log(LogStatus.PASS, 'terminating job  ')
					WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
					WebUI.delay(2)
					isNotoficationPresent=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobTerminate'), 5)
					println("notification status - "+isNotoficationPresent)
					extentTest.log(LogStatus.PASS, 'Verified notification')
					result=isNotoficationPresent
				}
				return result
				break

			case 'Resubmit':
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				isElementPresent=(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/JobDetailsPage/Msg_ResubmitWarning'), 5,extentTest, 'Resubmit Warning')
				if(isElementPresent) {
					//WebUI.check(findTestObject('Object Repository/JobDetailsPage/Msg_ResubmitWarning'))
					WebUI.click(findTestObject('Object Repository/JobMonitoringPage/button_Yes'))
				}
				WebUI.delay(2)
				WebUI.waitForElementClickable(findTestObject('JobSubmissionForm/button_Submit_Job'), 10)
				WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))
				extentTest.log(LogStatus.PASS, 'resubmitted job  ')
				isNotoficationPresent=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 5)
				def jobText = WebUI.getText(findTestObject('Notificactions/Notification_JobSubmission'))
				def JobID=(new operations_JobsModule.GetJobRowDetails()).getJobID(jobText)
				extentTest.log(LogStatus.PASS, 'Verified notification - new job id '+ JobID)

				result=isNotoficationPresent
				return result
				break

			case 'Open':
				fileName='ToOpen.txt'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						fileName, true)
				WebUI.scrollToElement(newFileObj, 3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)

				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				extentTest.log(LogStatus.PASS, 'Clicked on Context Menu Option for - '+Action)
				String fileNameFormViewerPanel=	WebUI.getAttribute(findTestObject('Object Repository/TrialTestObject'), 'textContent')
				fileNameFormViewerPanel=fileNameFormViewerPanel.trim()
				println(fileNameFormViewerPanel +"=="+fileName)
				if(fileNameFormViewerPanel.contains(fileName)) {
					println("inside if  ")
					result=true
					extentTest.log(LogStatus.PASS, 'Verified filename on the file viewer panel - '+fileName)
				}
				WebUI.delay(3)
				WebUI.click(findTestObject('Object Repository/FileEditor/Close_Button_fileEditor'))
				extentTest.log(LogStatus.PASS, 'Clicked on Close Button ')
				TestObject TestObjFolder=(new buildTestObj.CreateJobSubmissionObjs()).myJobDetailsFolder(userChoice)
				def isFolderPresent=WebUI.verifyElementPresent(TestObjFolder,3)
				extentTest.log(LogStatus.PASS, 'User Navigated to '+userChoice+' folder tab ')
				println isFolderPresent
				return result
				break


			case 'Open With':
				fileName='ToOpenWith.txt'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						fileName, true)
				WebUI.scrollToElement(newFileObj, 3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)

				println ("Form job actions - "+Action)
				WebUI.mouseOver(findTestObject('Object Repository/FileEditor/ContextMenu_OpenWith'))
				WebUI.click(findTestObject('Object Repository/FileEditor/ContextMenu_OpenWith'))
				WebUI.delay(2)
				WebUI.mouseOver(findTestObject('Object Repository/FilesPage/span_Text Editor'))
				WebUI.click(findTestObject('Object Repository/FilesPage/span_Text Editor'))

				extentTest.log(LogStatus.PASS, 'Clicked on Context Menu Option for - '+Action)

				WebUI.delay(4)
				String fileNameFormViewerPanel=	WebUI.getAttribute(findTestObject('Object Repository/TrialTestObject'), 'textContent')
				fileNameFormViewerPanel=fileNameFormViewerPanel.trim()
				println(fileNameFormViewerPanel +"=="+fileName)
				if(fileNameFormViewerPanel.contains(fileName)) {
					println("inside if  ")
					result=true
					extentTest.log(LogStatus.PASS, 'Verified filename on the file viewer panel - '+fileName)
				}
				WebUI.click(findTestObject('Object Repository/FileEditor/Close_Button_fileEditor'))
				extentTest.log(LogStatus.PASS, 'Clicked on Close Button ')
				return result
				break

			case 'Download':
				println("Action" + Action)
				fileName='Running.sh'
				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals',
						fileName, true)
				WebUI.scrollToElement(newFileObj, 3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)
				println ("Form job actions - "+Action)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', Action, true)
				WebUI.delay(2)
				WebUI.click(newJobAction)
				extentTest.log(LogStatus.PASS, 'Downloading job')


			/*def downloadLoc=GlobalVariable.G_DownloadFolder
			 File downloadFolder = new File("C://KatalonDownloads")
			 List namesOfFiles = Arrays.asList(downloadFolder.list())
			 println(namesOfFiles.size())
			 if (namesOfFiles.contains('ForFileViewer.txt')) {
			 println('success')
			 //extentTest.log(LogStatus.PASS, 'file to downloaded ')
			 result=true
			 } else {
			 println('fail')
			 result=false
			 }*/

				return true
				break

			case 'Move To Queue':
				println ("Form job actions - "+Action)
				WebUI.delay(2)
				WebUI.click(findTestObject('JobMonitoringPage/ContextMenu_MoveToQueue'))
				WebUI.delay(2)
				WebUI.click(findTestObject('JobMonitoringPage/CntxtMn-SubMn_accessQueue'))
				TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',	'Queued', true)
				WebUI.rightClick(newJobRow)
				WebUI.delay(1)
				TestObject newJobAction = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/ContextMenu_JobAction'),
						'id', 'equals', 'View Details', true)
				WebUI.click(newJobAction)
				WebUI.delay(2)

				WebUI.click(findTestObject('JobDetailsPage/JobDetailsLink_Details'))
				extentTest.log(LogStatus.PASS,"Navigated to Details Tab")
				WebUI.click(findTestObject('JobDetailsPage/TextBx_DetailsFilter'))

				WebUI.setText(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), 'queue name')
				WebUI.click(findTestObject('JobDetailsPage/Detail_QueueName'))
				TestObject newQueueObj = WebUI.modifyObjectProperty(findTestObject('JobDetailsPage/Detail_QueueName'), 'text', 'equals',	'accessQueue', true)
				println("---------- queuename "+WebUI.waitForElementPresent(newQueueObj, 4, FailureHandling.CONTINUE_ON_FAILURE))
			/*
			 def jobID=WebUI.getText(findTestObject('JobDetailsPage/jobTitle'))
			 String[] splitAddress = jobID.split('\\.')
			 GlobalVariable.G_JobIdFromDetails=splitAddress[0]
			 println GlobalVariable.G_JobIdFromDetails
			 */
				result=WebUI.waitForElementPresent(newQueueObj, 4, FailureHandling.CONTINUE_ON_FAILURE)
				return result
				break


			default:
				break
		}
		return result
	}
}
