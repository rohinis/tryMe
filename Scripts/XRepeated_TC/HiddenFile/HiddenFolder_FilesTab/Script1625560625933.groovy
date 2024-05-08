import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus


import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)

ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

String TCName=TestCaseName+' through ContextMenu options'

def extentTest = extent.startTest(TCName)

def viewIconTilePresent

def viewIconListPresent

TestObject newFileObj

WebUI.delay(2)

def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location=navLocation
println('##################################################################')
println (location)
println('##################################################################')


try {
	
	def filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
	20,extentTest,'Files Tab')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

    
	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
	WebUI.delay(2)

	TestObject viewIconTile = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title','equals', 'Tile View', true)

	TestObject viewIconList = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title','equals', 'List View', true)

	viewIconTilePresent = WebUI.waitForElementPresent(viewIconTile, 5, FailureHandling.CONTINUE_ON_FAILURE)

	viewIconListPresent = WebUI.waitForElementPresent(viewIconList, 5, FailureHandling.CONTINUE_ON_FAILURE)

	println('viewIconTilePresent - ' + viewIconTilePresent)

	println('viewIconListPresent - ' + viewIconListPresent)

	if (viewIconListPresent) {
		WebUI.click(viewIconList)
		extentTest.log(LogStatus.PASS, 'Changed View to ListView')
		WebUI.delay(2)
	}

	WebUI.delay(2)

	println(TestCaseName)

	if (TestCaseName.contains('Upload'))
	{
		println Operation

	}

	else
	{
		if (TestCaseName.contains('tile view')) {
			println("in tile wala if")
			WebUI.click(viewIconTile)
			extentTest.log(LogStatus.PASS, 'Changed View to Tile View - test case - ' +TestCaseName)

			WebUI.delay(2)

			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals',
					fileName, true //	WebUI.click(newFileObj)
					)
		} else {
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title',
					'equals', fileName, true)
		}
		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')

		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

		WebUI.verifyElementPresent(findTestObject('Object Repository/JobMonitoringPage/hiddenfile'), 3)
		
		
		WebUI.click(findTestObject('PageNavigation/Preferences/Profiletab'))
		extentTest.log(LogStatus.PASS, 'Click on profile tab')
		WebUI.delay(2)
		
		WebUI.click(findTestObject('Landing_Page/ListItem_Logout'))
		extentTest.log(LogStatus.PASS, 'Click on logout')
		
		WebUI.verifyElementVisible(findTestObject('LogOut-PopUp/Title_Logout'))
		
		WebUI.delay(2)
		
		WebUI.click(findTestObject('GenericObjects/btn_Yes'))
		extentTest.log(LogStatus.PASS, 'Click on yes button')
		WebUI.delay(2)
		
		WebUI.click(findTestObject('AppComposer/Login'))
		extentTest.log(LogStatus.PASS, 'Click on Login again')
		
		if (TestCaseName.contains('normal user'))
		{
		
		WebUI.setText(findTestObject('LoginPage/username_txtbx'),'rohini')
		extentTest.log(LogStatus.PASS, 'Enter username rohini ')
		
		WebUI.setText(findTestObject('LoginPage/password_txtbx'), 'rohini')
		extentTest.log(LogStatus.PASS, 'Enter  password  rohini')
		
		}
		
		else
		{
		
		WebUI.setText(findTestObject('LoginPage/username_txtbx'),'pbsworks')
		extentTest.log(LogStatus.PASS, 'Enter username pbsworks')
		
		WebUI.setText(findTestObject('LoginPage/password_txtbx'), 'pbsworks')
		extentTest.log(LogStatus.PASS, 'Enter  password  pbsworks')
		}
		
		WebUI.click(findTestObject('LoginPage/login_btn'))
		extentTest.log(LogStatus.PASS, 'Click on Login')
		
		 filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20,extentTest,'Files Tab')
		
			if (filesTab) {
				WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
			}
		
			
			extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')
			WebUI.delay(2)
			WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
			
					WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'),'/stage/pbsworks')
			
					WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
					extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')
			
					WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))
			
					WebUI.verifyElementPresent(findTestObject('Object Repository/JobMonitoringPage/hiddenfile'), 3)
					
				
		
	}
	
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()


}

