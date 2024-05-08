import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================



String screenShot='ExtentReports/'+TestCaseName+option+GlobalVariable.G_Browser+'.png'

TestObject newFileObj

//WebUI.delay(2)
def result



def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location=navLocation+'/'+location
println('##################################################################')
println (location)
println('##################################################################')



WebUI.enableSmartWait()
try {
	def filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20,extentTest,'Files Tab')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}


	extentTest.log(LogStatus.PASS, 'Click on files tab')
	//WebUI.delay(3)
	WebUI.mouseOver(findTestObject('FilesPage/Bookmark'))
	WebUI.clickOffset(findTestObject('FilesPage/Bookmark'), 0, 0)
//	WebUI.delay(3)
	
	
extentTest.log(LogStatus.PASS, 'Click on bookmark')


	switch (option){
		case 'create':

			
			WebUI.delay(2)
			WebUI.mouseOver(findTestObject('FilesPage/Createbookmark'))
			WebUI.clickOffset(findTestObject('FilesPage/Createbookmark'), 0, 0)
			WebUI.delay(3)
			extentTest.log(LogStatus.PASS, 'Click on bookmark')
			WebUI.waitForElementVisible(findTestObject('Filespage/Enter_Name'), 10)
			WebUI.delay(2)
			WebUI.sendKeys(findTestObject('Filespage/Enter_Name'),bookMarkName)
			extentTest.log(LogStatus.PASS, 'Enter the Name of bookmark - ' + bookMarkName)

			WebUI.delay(2)
			WebUI.setText(findTestObject('Object Repository/FilesPage/TxtBx_BookMarkLocation'),location)
			extentTest.log(LogStatus.PASS, 'Enter the Path of bookmark - '+ location)

			WebUI.delay(4)
			WebUI.click(findTestObject('FilesPage/Confirm_button'))
			extentTest.log(LogStatus.PASS, 'Click on ok button')
			if (TestCaseName.contains('already')) {
				WebUI.delay(2)
				String validationMsg=WebUI.getAttribute(findTestObject('Object Repository/FilesPage/Enter_Name'), 'validationMessage')
				println("validationMsg"+ validationMsg)
				def retvalue =WebUI.verifyElementAttributeValue(findTestObject('Object Repository/FilesPage/Enter_Name'), 'validationMessage', validationMsg, 3)
				if(retvalue== true) {
					extentTest.log(LogStatus.PASS, 'verified the message::   '+validationMsg)
					
				}
				
				extentTest.log(LogStatus.PASS, 'Verify through Screenshot')
				WebUI.takeScreenshot(screenShot)
				
			}
			else {
			
			
			

			WebUI.delay(1)

			WebUI.click(findTestObject('FilesPage/Bookmark'))
			extentTest.log(LogStatus.PASS, 'Click on bookmark')

			WebUI.delay(1)

			WebUI.click(findTestObject('FilesPage/Managebookmark'))
			extentTest.log(LogStatus.PASS, 'Click on manage bookmark')

			TestObject bookmark = WebUI.modifyObjectProperty(findTestObject('FilesPage/Check_Bookmark'), 'text','equals',bookMarkName , true)
			result=WebUI.verifyElementPresent(bookmark, 10)
			if(result)
			extentTest.log(LogStatus.PASS, 'Checked the created bookmark ')
			else
				extentTest.log(LogStatus.FAIL, 'Failed to verify the bookmark ')
			}
			break;

		case'create empty name':

			WebUI.click(findTestObject('FilesPage/Createbookmark'))
			extentTest.log(LogStatus.PASS, 'Click on create new bookmark')
			WebUI.delay(2)

			WebUI.sendKeys(findTestObject('Filespage/Enter_Name'),bookMarkName)
			extentTest.log(LogStatus.PASS, 'Enter the Name of bookmark - ' + bookMarkName)


			WebUI.setText(findTestObject('Object Repository/FilesPage/TxtBx_BookMarkLocation'),location)
			extentTest.log(LogStatus.PASS, 'Enter the Path of bookmark - '+ location)


			//WebUI.click(findTestObject('FilesPage/Cancel_button'))
			//extentTest.log(LogStatus.PASS, 'Click on ok button')
			
			result=WebUI.verifyElementHasAttribute(findTestObject('FilesPage/Confirm_button'),'disabled',5)
			if(result) {
				extentTest.log(LogStatus.PASS, 'user is not able to create bookmark by empty name :: Verified the create button is disabled')
				}
				else
					extent.log(LogStatus.FAIL," failed to verify")

			break

		case 'invalid':

			WebUI.click(findTestObject('FilesPage/Createbookmark'))
			extentTest.log(LogStatus.PASS, 'Click on create new bookmark')
			WebUI.delay(2)

			WebUI.sendKeys(findTestObject('Filespage/Enter_Name'),bookMarkName)
			extentTest.log(LogStatus.PASS, 'Enter the Name of bookmark - ' + bookMarkName)


			WebUI.setText(findTestObject('Object Repository/FilesPage/TxtBx_BookMarkLocation'),location)
			extentTest.log(LogStatus.PASS, 'Enter the Path of bookmark - '+ location)


			WebUI.click(findTestObject('FilesPage/Confirm_button'))
			extentTest.log(LogStatus.PASS, 'Click on ok button')
			WebUI.delay(3)

			WebUI.waitForElementPresent(findTestObject('FilesPage/DeleteFolder_Bookmark'), 10)
			WebUI.rightClick(findTestObject('FilesPage/DeleteFolder_Bookmark'))
			WebUI.click(findTestObject('FilesPage/DeleteFolder'))

			WebUI.delay(2)
			WebUI.click(findTestObject('FilesPage/button_Yes'))
			extentTest.log(LogStatus.PASS, 'Click on yes button')
			
			WebUI.delay(3)
			WebUI.mouseOver(findTestObject('FilesPage/Bookmark'))
			WebUI.clickOffset(findTestObject('FilesPage/Bookmark'), 0, 0)

			WebUI.click(findTestObject('FilesPage/Managebookmark'))
			extentTest.log(LogStatus.PASS, 'Click on the manage bookmark')
		
			WebUI.delay(2)
			TestObject bookmark = WebUI.modifyObjectProperty(findTestObject('FilesPage/ManageBookmark_title_link'), 'title','equals',bookMarkName , true)
			WebUI.click(bookmark)
			extentTest.log(LogStatus.PASS, 'Clicked on the created bookmark')
			WebUI.delay(2)
			
			extentTest.log(LogStatus.PASS, 'Click on the Notification')
			WebUI.click(findTestObject('Object Repository/Notificactions/Notification'))

			result=WebUI.verifyElementPresent(findTestObject('FilesPage/InvalidPath_popup'), 2)
			if(result)
			extentTest.log(LogStatus.PASS, 'Verified the invalid path message ')
			else
				extentTest.log(LogStatus.FAIL, 'Failed to verify ')

			break
		case 'remove':

			WebUI.click(findTestObject('FilesPage/Managebookmark'))
			extentTest.log(LogStatus.PASS, 'Click on the manage bookmark')
			WebUI.delay(2)

			TestObject bookmark = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_BookMark'), 'text','equals',location , true)
			WebUI.click(bookmark)
			extentTest.log(LogStatus.PASS, 'Clicked on existing bookmark - '+bookMarkName)
			WebUI.delay(2)
			if (TestCaseName.contains("No"))
			{
				println("No")
				WebUI.click(findTestObject('Object Repository/Cancel_ModalPanel'))
				extentTest.log(LogStatus.PASS, 'Click on cancel button')


			}
			else {
				println("yes")

				WebUI.click(findTestObject('Object Repository/GenericObjects/btn_Yes'))
				extentTest.log(LogStatus.PASS, 'Click on remove button')
			}
			result= WebUI.verifyElementNotPresent(bookmark, 5)
			if(result) {
				extentTest.log(LogStatus.PASS, 'verified the bookmark deleted successfully!! ')
			}
			else
				extentTest.log(LogStatus.FAIL, 'Failed to delete the bookmark ')
			break
	}
	WebUI.disableSmartWait()
	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
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
	extentTest.log(LogStatus.PASS, 'Closing the browser after executinge test case - '+ TestCaseName)
	extent.endTest(extentTest)
	extent.flush()
}
//=====================================================================================

