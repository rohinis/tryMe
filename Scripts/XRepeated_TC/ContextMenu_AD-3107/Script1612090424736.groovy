import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

WebUI.callTestCase(findTestCase('XRepeated_TC/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)

/*WebDriver driver = DriverFactory.getWebDriver()
 Capabilities caps = ((driver) as RemoteWebDriver).getCapabilities()
 String browserName = caps.getBrowserName()
 String browserVersion = caps.getVersion()
 def Browser = GlobalVariable.G_Browser*/

String ReportFile = GlobalVariable.G_ReportName + '.html'

def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

fileName="CntxMenu"+fileName
WebUI.delay(2)


def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
def location='/FilesModule/NewFolder'
/*println('##################################################################')
 println (location)
 println('##################################################################')
 */
//TestObject lastItem
try
{
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20,extentTest,'Files Tab')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

	extentTest.log(LogStatus.PASS, "Navigated to Files Tab " )
	TestObject viewIconTile=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title', 'equals',"Tile View", true)
	TestObject viewIconList=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title', 'equals',"List View", true)

	viewIconTilePresent=WebUI.waitForElementPresent(viewIconTile, 3, FailureHandling.CONTINUE_ON_FAILURE)

	viewIconListPresent=WebUI.waitForElementPresent(viewIconList, 3, FailureHandling.CONTINUE_ON_FAILURE)

	println("viewIconTilePresent - "+viewIconTilePresent)
	println("viewIconListPresent - "+viewIconListPresent)

	WebUI.delay(3)
	//lastItem=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/FolderRowItem_ListView'), 'title', 'equals',"ListView.txt", true)

	if (viewIconListPresent) {
		WebUI.click(viewIconList)
		extentTest.log(LogStatus.PASS, 'Changed View to ListView')
		WebUI.delay(2)
		//	lastItem=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), 'text', 'equals',"New", true)
	}

	if (TestCaseName.contains('tile view')) {
		WebUI.click(viewIconTile)
		extentTest.log(LogStatus.PASS, 'Changed View to Tile View as per test case')
		location='stage/John/NewFolder'
		//	lastItem=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/GoButton'), 'class', 'equals',"jumpto-go-btn btn_plain_normal button_master theme-receiver secondary", true)

	}


	println('================================================================')
	println (location)
	println('================================================================')


	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - '+location)
	WebUI.delay(3)

	WebUI.mouseOverOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), 10, 450, )
	WebUI.delay(2)

	WebUI.rightClickOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFolder'), 10, 450, FailureHandling.STOP_ON_FAILURE)

	//	WebUI.rightClickOffset(findTestObject('Object Repository/FilesPage/btn_NewFileFoldern'), -50, -200, FailureHandling.STOP_ON_FAILURE)

	WebUI.delay(2)


	WebUI.verifyElementPresent(findTestObject('FilesPage/ContextMenu_CreateNewFile'))
	extentTest.log(LogStatus.PASS,"Clicked context menu - New file ")

	/*
	WebUI.click(findTestObject('FilesPage/TextBx_CreateFile'))

	WebUI.waitForElementVisible(findTestObject('FilesPage/TextBx_CreateFile'), 5)

	WebUI.click(findTestObject('FilesPage/TextBx_CreateFile'))


	WebUI.setText(findTestObject('FilesPage/TextBx_CreateFile'), fileName)

	WebUI.click(findTestObject('FilesPage/btn_Save'))
	extentTest.log(LogStatus.PASS, "Clicked on Save Button" )


	WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_FileCreation'), 3)

	println(WebUI.getText(findTestObject('Notificactions/Notification_FileCreation')))

	WebUI.delay(2)

	WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))


	WebUI.delay(2)

	//WebUI.mouseOver(findTestObject('Notificactions/xpath_notification'))
	TestObject createFileNotification = CustomKeywords.'buildTestObj.CreateTestObjFiles.myTestObjFileCreateNotification'(fileName)

	'Verify notification'
	def notification = WebUI.verifyElementPresent(createFileNotification, 5)

	println(notification)

	if(notification)
	{
		extentTest.log(LogStatus.PASS, fileName+" -  File To Create" )
		extentTest.log(LogStatus.PASS, fileName + "- Created file and verified notification")

	}
	else
	{
		extentTest.log(LogStatus.PASS, fileName+" - Not Created" )
		extentTest.log(LogStatus.FAIL)
	}
	*/

	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('XRepeated_TC/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}


}
catch (Exception  ex)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)

	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))
	KeywordUtil.markFailed('ERROR: '+ e)

}
catch (StepErrorException  e)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))
	KeywordUtil.markFailed('ERROR: '+ e)


}
finally
{

	extent.endTest(extentTest);
	extent.flush();

}



