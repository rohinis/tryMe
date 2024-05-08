package operations_FileModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

public class checkUnZip {



	@Keyword
	def performUnzip(String TestCaseName , extentTest,userChoice) {
		def result=false
		String CompressedFileXpath=null
		String FolderXpath =null
		TestObject newZipFolderObj= null
		TestObject newUnZipFolderObj= null
		TestObject newFileObj= null
		def isOriFilePresent= false
		def isZipUploaded =false
		def isFoderUnzipped =false
		WebUI.delay(2)
		String FolderName= null
		def toClean=null

		def isUploadDialogPresent=(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/Title_label_UnzipOnUpload'),5, extentTest, 'Unzip Confirmation dialog')
		def isUnzipMsgPresent=(new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/2020.1/Verify_unzip_message'),5, extentTest, 'Unzip Confirmation msg')
		WebUI.delay(2)
		if(isUploadDialogPresent) {
			if(TestCaseName.contains('tile view')) {

				CompressedFileXpath="//label[@title = 'ToUploadTV.zip']"
				FolderXpath="//label[@data-automation-id = 'ToUploadTV']"
				FolderName='ToUpload'
				newZipFolderObj=new TestObject('objectName')
				newZipFolderObj.addProperty('xpath', ConditionType.EQUALS, CompressedFileXpath)
				newUnZipFolderObj=new TestObject('objectName')
				newUnZipFolderObj.addProperty('xpath', ConditionType.EQUALS, FolderXpath)
				newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', 'ToUpload.txt', true)
			}
			else {

				CompressedFileXpath="//div[@data-automation-id = 'ToUploadLV.zip']"
				FolderXpath="//div[@data-automation-id = 'ToUploadLV']"
				FolderName='ToUpload'
				newZipFolderObj=new TestObject('objectName')
				newZipFolderObj.addProperty('xpath', ConditionType.EQUALS, CompressedFileXpath)
				newUnZipFolderObj=new TestObject('objectName')
				newUnZipFolderObj.addProperty('xpath', ConditionType.EQUALS, FolderXpath)
				newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'data-automation-id', 'equals', 'ToUpload.txt', true)
			}
			switch(userChoice) {
				case 'Yes':
					WebUI.click(findTestObject('Object Repository/FilesPage/button_Yes'))
					extentTest.log(LogStatus.PASS, 'Clicked YES on Unzip on Upload confirmation pop-up')
					WebUI.delay(2)
					isZipUploaded=WebUI.verifyElementPresent(newZipFolderObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
					isFoderUnzipped=WebUI.verifyElementPresent(newUnZipFolderObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
					println("isZipUploaded --- "+isZipUploaded)
					println("isFoderUnzipped --- "+isFoderUnzipped )
					if(isZipUploaded&&isFoderUnzipped) {
						extentTest.log(LogStatus.PASS, ' Compressed zip file is listed - '+FolderName+'.zip')
						extentTest.log(LogStatus.PASS, ' Uncompressed folder is listed - '+FolderName)
						WebUI.click(newUnZipFolderObj)
						WebUI.doubleClick(newUnZipFolderObj)
						extentTest.log(LogStatus.PASS, ' Navigated into uncompressed folder - '+FolderName)

						isOriFilePresent=WebUI.verifyElementPresent(newFileObj, 3, FailureHandling.CONTINUE_ON_FAILURE)
						if(isOriFilePresent	) {
							extentTest.log(LogStatus.PASS, ' The original file exists in the uncompressed folder  - ToUpload.txt')
							result=true
						}
						else {
							result = false
						}
						WebUI.click(findTestObject('Object Repository/FilesPage/link_breadCumb_level2'))
						WebUI.delay(2)
						WebUI.click(newZipFolderObj)
						WebUI.rightClick(newZipFolderObj)
						TestObject newFileOp
						newFileOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/ContextMenu_FileOperation'), 'id', 'equals', 'Delete', true)
						WebUI.click(newFileOp)
						WebUI.delay(2)
						WebUI.click(findTestObject('GenericObjects/btn_Yes'))
						WebUI.delay(2)


						WebUI.click(newUnZipFolderObj)
						WebUI.rightClick(newUnZipFolderObj)
						newFileOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/ContextMenu_FileOperation'), 'id', 'equals', 'Delete', true)
						WebUI.click(newFileOp)
						WebUI.delay(2)
						WebUI.click(findTestObject('GenericObjects/btn_Yes'))
						WebUI.delay(2)
					}
					return result
					break


				case 'No':
					WebUI.click(findTestObject('Object Repository/Access_Management/Confirm_cancel'))
					extentTest.log(LogStatus.PASS, 'Clicked NO on Unzip on Upload confirmation pop-up')
					WebUI.delay(2)
					isZipUploaded=WebUI.verifyElementPresent(newZipFolderObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
					isFoderUnzipped=WebUI.verifyElementPresent(newUnZipFolderObj, 5, FailureHandling.CONTINUE_ON_FAILURE)

					if(isZipUploaded) {
						extentTest.log(LogStatus.PASS, ' Compressed zip file is listed - '+FolderName+'.zip')
						if(isFoderUnzipped) {
							extentTest.log(LogStatus.FAIL, ' Uncompressed folder is listed - '+FolderName+' on hitting No button')
							result=false
						}
						else {
							extentTest.log(LogStatus.PASS, ' Uncompressed folder is not listed - on hitting No button')
							result=true
						}
					}
					else {
						result=false
					}
					println("after if ")
					WebUI.click(newZipFolderObj)
					WebUI.rightClick(newZipFolderObj)
					TestObject newFileOp
					newFileOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/ContextMenu_FileOperation'), 'id', 'equals', 'Delete', true)
					WebUI.click(newFileOp)
					WebUI.delay(2)
					WebUI.click(findTestObject('GenericObjects/btn_Yes'))
					WebUI.delay(2)

					return result
					break


				case 'Close':
					WebUI.click(findTestObject('Object Repository/Generic_Actions/Close_button'))
					extentTest.log(LogStatus.PASS, 'Clicked Close Icon  on Unzip on Upload confirmation pop-up')
					WebUI.delay(2)
					isZipUploaded=WebUI.verifyElementPresent(newZipFolderObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
					isFoderUnzipped=WebUI.verifyElementPresent(newUnZipFolderObj, 5, FailureHandling.CONTINUE_ON_FAILURE)

					if(isZipUploaded&&isFoderUnzipped) {
						extentTest.log(LogStatus.FAIL, ' Compressed zip file - '+FolderName+'.zip is listed  on hitting close icon')
						result=false
					}
					else {
						extentTest.log(LogStatus.PASS, ' Compressed zip file  - '+FolderName+'.zip is not listed on hitting close icon')
						result=true
					}
					return result
					break

				case 'layout':
					if(isUploadDialogPresent) {
						extentTest.log(LogStatus.PASS, ' The unzip dialog presnt with title - Unzip File?')
						result=true
					}
					if(isUnzipMsgPresent) {
						extentTest.log(LogStatus.PASS, ' The unzip msg - "Do you want to unzip the file?" is present')
						result=true
					}

					break
			}
		}
		else {
			extentTest.log(LogStatus.FAIL, ' Unzip Confirmation dialog did not appear')
			result=false
			return result
		}
	}
}
