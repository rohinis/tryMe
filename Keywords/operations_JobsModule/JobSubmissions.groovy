package operations_JobsModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.model.FailureHandling as FailureHandling

public class JobSubmissions {

	@Keyword
	def JSAllFileds(String toChange,String ChangeValue,extentTest) {

		def stageOut=ChangeValue
		def navLocation=(new generateFilePath.filePath()).execLocation()
		def fileLocation =navLocation+ '/JobsModule'
		switch (toChange) {

			case 'NCPU':
				WebUI.click(findTestObject('JobSubmissionForm/List_NCPUS'))
				WebUI.setText(findTestObject('JobSubmissionForm/List_NCPUS'),'2')
				extentTest.log(LogStatus.PASS, 'Changed the NCPU value to - 2')
				break

			case 'QUEUE':
				WebUI.click(findTestObject('WIP/RadioBtn_All Fields'))
				WebUI.scrollToElement(findTestObject('WIP/label_Queue'), 0)
				WebUI.click(findTestObject('WIP/div_workq'))
				TestObject newQueueObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/dropDown_version'), 'text', 'equals',	ChangeValue, true)
				WebUI.mouseOver(newQueueObj)
				WebUI.click(newQueueObj)
				extentTest.log(LogStatus.PASS, 'Changed the QUEUE value to - '+ChangeValue)
				break


			case 'MEM':
				WebUI.click(findTestObject('Object Repository/JobSubmissionForm/List_Memory'))
				WebUI.setText(findTestObject('JobSubmissionForm/List_Memory'),'120')
				extentTest.log(LogStatus.PASS, 'Changed the MEMORY value to - 120')
				break

			case 'OUTFOLDER':
				WebUI.click(findTestObject('JobSubmissionForm/RadioBtn_All Fields'))
				WebUI.scrollToElement(findTestObject('Object Repository/JobSubmissionForm/TextBx_OutPut_Folder'),2)
				WebUI.click(findTestObject('Object Repository/JobSubmissionForm/TextBx_OutPut_Folder'))
				WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/TextBx_OutPut_Folder'), stageOut)
				extentTest.log(LogStatus.PASS, 'Set the OUTPUT FOLDER value to - '+stageOut)
				break

			case 'VERSION':
				println ("version --- "+ ChangeValue)

				if (ChangeValue.equals('ShellScript')) {
					println('no version for this app')
					extentTest.log(LogStatus.PASS, 'No vversion for ShellScript app def')
				} else {
					WebUI.scrollToElement(findTestObject('JobSubmissionForm/versionDropDown'), 3)
					WebUI.click(findTestObject('JobSubmissionForm/versionDropDown'))
					TestObject newVerObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/dropDown_version'), 'text','equals',ChangeValue, true)
					WebUI.click(newVerObj)
					extentTest.log(LogStatus.PASS, 'Changed the VERSION value to - '+ChangeValue)
				}
				break
			case 'SetOutPutDir':
			//WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			//WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), fileLocation)
			//	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))

				(new generateFilePath.filePath()).navlocation(fileLocation, extentTest)




				WebUI.waitForElementVisible(findTestObject('FilesPage/btn_NewFileFolder'), 10)
				WebUI.click(findTestObject('FilesPage/btn_NewFileFolder'))
				WebUI.click(findTestObject('FilesPage/ListItem_Folder'))
				WebUI.waitForElementVisible(findTestObject('FilesPage/TextBxFolder_input'), 5)
				WebUI.setText(findTestObject('FilesPage/TextBxFolder_input'), stageOut)
				WebUI.click(findTestObject('FilesPage/btn_Save'))

				WebUI.click(findTestObject('Object Repository/FilesPage/Icon_Refresh'))
				WebUI.delay(2)


				TestObject newFolderObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_Folder'), 'data-automation-id', 'equals',stageOut, true)
				WebUI.click(newFolderObj)
				WebUI.rightClick(newFolderObj)
				extentTest.log(LogStatus.PASS, 'Right Clicked on Folder to set the OUTPUT FOLDER ' + stageOut)
				WebUI.delay(2)
				String idForCntxtMn = 'Add as Output'
				TestObject newRFBContextMnOption = WebUI.modifyObjectProperty(findTestObject('Object Repository/LoginPage/NewJobPage/ContextMenu_RFB_FilePicker'),
						'id', 'contains', idForCntxtMn, true)
				WebUI.delay(2)
				WebUI.click(newRFBContextMnOption)
				extentTest.log(LogStatus.PASS, 'Clicked on context menu item - ' + idForCntxtMn)
				extentTest.log(LogStatus.PASS, 'Set the OUTPUT FOLDER to - '+stageOut)
				break;
		}
	}


	@Keyword
	def selectFile(String mode , String InputFile,extentTest){

		TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals',
				InputFile, true)
		extentTest.log(LogStatus.PASS, 'Input File Selection Mode- '+ mode)

		def navLocation=(new generateFilePath.filePath()).execLocation()
		def shortCutFileLocation

		def location=navLocation+'/JSUploads/'
		def fileLocation =navLocation+ '/JobsModule/InputDeck'
		if(GlobalVariable.G_Platform.equals('Windows')) {
			shortCutFileLocation = 'C:/stage/ShortCutFiles'
		}
		else {

			if(GlobalVariable.G_Platform.equals('Gird')) {
				shortCutFileLocation = '/gridusers/'+GlobalVariable.G_userName+'/ShortCutFiles'
			}
			else {
				shortCutFileLocation = '/stage/'+GlobalVariable.G_userName+'/ShortCutFiles'
			}
		}
		def folderLocation=GlobalVariable.G_userName+'-ToSetOutPutDir'


		switch (mode){
			case 'Local':
				WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
				WebUI.delay(2)
				WebUI.waitForElementVisible(findTestObject('Object Repository/FilesPage/gotofoldertext'), 10)
				WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), location)
				WebUI.delay(2)
				WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))
				extentTest.log(LogStatus.PASS, 'Navigated to '+ location +'in RFB ')
				def FolderEmptytext = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/FilesPage/Label_FolderEmpty'), 3,extentTest,'Lable Empty')
				println(FolderEmptytext)
				if (FolderEmptytext) {
					println('FolderEmptytext')
				}
				else {
					WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
					//def ele = WebUI.verifyElementClickable(findTestObject('Object Repository/FilesPage/FilesDelete_img'))
					def ele = WebUI.verifyElementClickable(findTestObject('/JobSubmissionForm/Icon_DeleteIcon_Fileop'))
					println('===============================')
					println(ele)
					println('===============================')
					WebUI.delay(2)
					//	WebUI.click(findTestObject('/FilesPage/FilesDelete_img'))
					WebUI.click(findTestObject('/JobSubmissionForm/Icon_DeleteIcon_Fileop'))
					WebUI.delay(2)
					WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				}
				def filePath = (RunConfiguration.getProjectDir() + '/Upload/JobFiles/') + InputFile
				def newFP = (new generateFilePath.filePath()).getFilePath(filePath)
				println(newFP)
				WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFP)
				extentTest.log(LogStatus.PASS, 'Uploading File to RFB - '+InputFile)
				def FileUploadClose = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/JobSubmissionForm/Icon_Close_UploadNotification'), 20,extentTest,'Upload Panel Close Icon')
				println("upload Notfication - "+FileUploadClose)
				def UploadedFile = (new customWait.WaitForElement()).WaitForelementPresent(newFileObj, 20,extentTest,InputFile)
				println("uploaded file - "+UploadedFile)
				if (UploadedFile) {
					WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Icon_Close_UploadNotification'))
				}
				return newFileObj
				break;

			case 'Remote':
			/*WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
			 WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), fileLocation)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))
			 extentTest.log(LogStatus.PASS, 'Navigated to '+ fileLocation +'in RFB ')*/
				(new generateFilePath.filePath()).navlocation(fileLocation, extentTest)
				WebUI.delay(2)
				WebUI.waitForElementPresent(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), 5)
				WebUI.click(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'))
				WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), InputFile)
				WebUI.sendKeys(findTestObject('JobSubmissionForm/textBx_file_filter'), Keys.chord(Keys.ENTER))
				extentTest.log(LogStatus.PASS, 'Searched for input file - '+InputFile)
				WebUI.delay(2)
				return newFileObj
				break;

			case 'ShortCut':
				(new generateFilePath.filePath()).navlocation(shortCutFileLocation, extentTest)
				extentTest.log(LogStatus.PASS, 'Navigating to - ' + shortCutFileLocation)
			/*WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
			 WebUI.delay(2)
			 WebUI.waitForElementVisible(findTestObject('Object Repository/FilesPage/gotofoldertext'), 10)
			 WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), shortCutFileLocation)
			 WebUI.delay(2)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))*/

			////WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
				println(shortCutFileLocation)
			//	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), shortCutFileLocation)
			//	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
				WebUI.delay(2)
				WebUI.waitForElementPresent(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), 5)
				WebUI.click(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'))
				WebUI.setText(findTestObject('Object Repository/JobSubmissionForm/textBx_file_filter'), InputFile)
				WebUI.sendKeys(findTestObject('JobSubmissionForm/textBx_file_filter'), Keys.chord(Keys.ENTER))
				extentTest.log(LogStatus.PASS, 'Searched for input file - '+InputFile)
				WebUI.delay(2)
				//extentTest.log(LogStatus.PASS, 'Navigating to - ' + shortCutFileLocation)


				return newFileObj
				break;


			case 'LocalRad':

				(new generateFilePath.filePath()).navlocation(location, extentTest)
			/*WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			 WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))*/
				extentTest.log(LogStatus.PASS, 'Navigated to '+ location +'in RFB ')
			//	def FolderEmptytext = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/FilesPage/Label_FolderEmpty'), 20,extentTest,'EmptyLabel')
				def FolderEmptytext= WebUI.waitForElementPresent(findTestObject('Object Repository/FilesPage/Label_FolderEmpty'),10, FailureHandling.CONTINUE_ON_FAILURE)
				println(FolderEmptytext)
				if (FolderEmptytext) {
					println('FolderEmptytext')
				}
				else {
					WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
					def ele = WebUI.verifyElementClickable(findTestObject('/JobSubmissionForm/Icon_DeleteIcon_Fileop'))
					println(ele)
					WebUI.click(findTestObject('/JobSubmissionForm/Icon_DeleteIcon_Fileop'))
					WebUI.delay(2)
					WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				}
				def filePath = (RunConfiguration.getProjectDir() + '/Upload/JobFiles/') + InputFile
				def newFP = (new generateFilePath.filePath()).getFilePath(filePath)
				println(newFP)
				WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFP)
				def f2 = RunConfiguration.getProjectDir() + '/Upload/JobFiles/CUBE_0001.rad'
				def p2 = (new generateFilePath.filePath()).getFilePath(f2)
				WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), p2)

				extentTest.log(LogStatus.PASS, 'Uploading File to RFB - '+InputFile)
				def FileUploadClose = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/JobSubmissionForm/Icon_Close_UploadNotification'), 20,extentTest,'Upload panel close icon')
				println("upload Notfication - "+FileUploadClose)
				def UploadedFile = (new customWait.WaitForElement()).WaitForelementPresent(newFileObj, 20,extentTest,InputFile)
				println("uploaded file - "+UploadedFile)
				if (UploadedFile) {
					WebUI.click(findTestObject('Object Repository/JobSubmissionForm/Icon_Close_UploadNotification'))
				}
				return newFileObj
				break;



			case 'LocalForm':
			//WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			//WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
			//WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))


			/*	WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
			 WebUI.delay(2)
			 WebUI.waitForElementVisible(findTestObject('Object Repository/FilesPage/gotofoldertext'), 10)
			 WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), location)
			 WebUI.delay(2)
			 WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))*/
				(new generateFilePath.filePath()).navlocation(location, extentTest)

				extentTest.log(LogStatus.PASS, 'Navigated to '+ location +'in RFB ')
				def FolderEmptytext = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/FilesPage/Label_FolderEmpty'),  20,extentTest,'EmptyLabel')
				println(FolderEmptytext)
				if (FolderEmptytext) {
					println('FolderEmptytext')
				}
				else {
					WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
					def ele = WebUI.verifyElementClickable(findTestObject('/JobSubmissionForm/Icon_DeleteIcon_Fileop'))
					println(ele)
					WebUI.click(findTestObject('/JobSubmissionForm/Icon_DeleteIcon_Fileop'))
					WebUI.delay(2)
					WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				}
				def filePath = (RunConfiguration.getProjectDir() + '/Upload/') + InputFile
				def newFP = (new generateFilePath.filePath()).getFilePath(filePath)
				println(newFP)
				WebUI.uploadFile(findTestObject('JobSubmissionForm/LocalFileUploadElement'), newFP)
				extentTest.log(LogStatus.PASS, 'Uploading File to Primary File element - '+InputFile)
				def FileUploadClose = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/JobSubmissionForm/Icon_Close_UploadNotification'),  20,extentTest,'Upload Panel CloseIcon')
				println("upload Notfication - "+FileUploadClose)
				def UploadedFile = (new customWait.WaitForElement()).WaitForelementPresent(newFileObj,  20,extentTest,InputFile)
				println("uploaded file - "+UploadedFile)
				return newFileObj
				break;
		}
	}



	@Keyword
	def printJobState(WebDriver katalonWebDriver,extentTest){
		def LogStatus = com.relevantcodes.extentreports.LogStatus

		WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
		WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),GlobalVariable.G_JobID)
		WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.ENTER))

		if(GlobalVariable.G_Browser.equals('FireFox')) {
			WebUI.delay(5)
			extentTest.log(LogStatus.PASS, 'Waiting for jobs table to load on FireFox')
		}

		String myXpath="//div[@col-id='jobState']"
		List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		println listElement.size()

		for(int i =1;i<listElement.size();i++) {
			RemoteWebElement ele = listElement.get(i)
			String myText=ele.getText()
			println (ele.getText())
			extentTest.log(LogStatus.PASS, 'Current Job State for Job ID  - '+ GlobalVariable.G_JobID+ ' is - '+myText)
		}
	}
}
