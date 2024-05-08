package toupload



import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.relevantcodes.extentreports.LogStatus
import org.openqa.selenium.Keys
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

import internal.GlobalVariable

public class Forupload {

	def isNotificationPresent=false
	def result=false

	@Keyword
	def UploadFolder(extentTest,String InputFolder, String UserChoice, String TestCaseName) {


		def filePath = (RunConfiguration.getProjectDir() + '/Upload/') + InputFolder
		def newFP = (new generateFilePath.filePath()).getFilePath(filePath)
		println(newFP)
		String folderPath = newFP
		TestObject Foldername

		TestObject uploadInput = findTestObject('FilesPage/UploadFolderBtn')
		WebUI.delay(3)
		if (TestCaseName.contains('context')){
			uploadInput=findTestObject('Object Repository/JobDetailsPage/FolderUploadBtnContextmenu')
			WebUI.delay(3)
		}

		WebUI.uploadFile(uploadInput, folderPath)
		extentTest.log(LogStatus.PASS, 'Uploading the Folder ::  '+ InputFolder)

		extentTest.log(LogStatus.PASS, 'Click on the Notification tab')

		WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
		WebUI.delay(3)

		isNotificationPresent=WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_FolderUpload'), 5)
		println("notification status - "+isNotificationPresent)
		result=isNotificationPresent
		extentTest.log(LogStatus.PASS, 'Folder has been uploaded successfully')

		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/Notificactions/close_icon_upload_popup'))

		WebUI.waitForElementVisible(findTestObject('Object Repository/Notificactions/Ok_btn'), 5)
		WebUI.click(findTestObject('Object Repository/Notificactions/Ok_btn'))
		if(UserChoice !="RunningFolder") {

			WebUI.click(findTestObject('Object Repository/FilesPage/FilesSearch_filter'))
			WebUI.setText(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), InputFolder)
			WebUI.sendKeys(findTestObject('Object Repository/FilesPage/FilesSearch_filter'), Keys.chord(Keys.ENTER))
		}

		WebUI.delay(2)
		if(TestCaseName.contains("Tile View")){
			Foldername = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/RowItem_File_TileView'), 'title', 'equals',InputFolder, true)
			WebUI.delay(2)
		}
		else {
			Foldername = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/RowItem_Folder'), 'data-automation-id', 'equals',InputFolder, true)
			WebUI.delay(2)
		}


		println("input folder"+ InputFolder)
		//WebUI.click(Foldername)
		//	WebUI.delay(2)
		//WebUI.mouseOver(Foldername)
		WebUI.doubleClick(Foldername)

		extentTest.log(LogStatus.PASS, 'Navigate inside the folder by Double-click ')
		String [] JobFiles = [
			'ToDelete.txt',
			'ToOpen.txt',
			'ToOpenWith.txt',
			'ToRename.txt',
			'Running.sh',
			'Folder1',
			'Folder2'
		]
		int x=0
		for (String name1:JobFiles) {
			String JF =JobFiles[x]

			println(JF)
			TestObject newJobFile = WebUI.modifyObjectProperty(findTestObject('JobSubmissionForm/File_InputFile'), 'data-automation-id', 'equals',JF, true)
			WebUI.verifyElementPresent(newJobFile, 5)
			x++
		}
		println("x==="+ x)
		if(x==7)
			extentTest.log(LogStatus.PASS, 'All the Files and Folders are present inside the Folder ')
	}
}




