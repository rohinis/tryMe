import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

if(TestCaseName.contains('tile view'))
{
	println('tile view not supported in this module')
}
else
{

String ReportFile = GlobalVariable.G_ReportName + '.html'


def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus

//Updaing test case name to represent multifile operations and icon operations

TestCaseName='JobSubmission RFB - MultiFolderOperation -'+TestCaseName
if (Operation.contains('icon')) {
	TestCaseName = (TestCaseName + ' thorugh top menu icons')
}
def extentTest = extent.startTest(TestCaseName)


//Login call
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

//updating location to navigate as per module

def pasteLocation
def result
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location

TestObject newFileObj

if (Operation.contains('icon'))
{
	location = navLocation + '/MultipleFolderIcons'
}
else
{
	location = navLocation + '/MultipleFolder'
}
if (TestCaseName.contains('tile view'))
{
	pasteLocation = (location + '/ToPasteTV')
	if(TestCaseName.contains('Cut')||TestCaseName.contains('Copy'))
	{
		location = (location + '/TileViewCut')
	}
	else
	{
		location = (location + '/TileView')
	}

	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/FolderRowItem_TileView'), 'title', 'equals','five', true )
}
else
{
	pasteLocation = (location + '/ToPasteLV')
	if(TestCaseName.contains('Cut')||TestCaseName.contains('Copy'))
	{
		location = (location + '/ListViewCut')
	}
	else
	{
		location = (location + '/ListView')
	}

	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/FolderRowItem_ListView'), 'title','equals', 'five', true)
}
println("================================================")
println("navlocation - "+ navLocation)
println("================================================")
println("location - "+location)
println("================================================")
println("pastelocation - "+pasteLocation)
println("================================================")

try {
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}

	extentTest.log(LogStatus.PASS, 'Navigated Job Tab')
	WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'), 'id', 'equals',
			AppName, true)

	WebUI.click(newAppObj)
	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+AppName)

	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/LoginPage/NewJobPage/GenericProfile'))
	WebUI.delay(2)

	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'('list view', extentTest)

	// checking the first row element - if two.txt sorting by name to get five.txt at top
	String myXpath="//div[@col-id='name']"
	List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
	println("================================================")
	println(listElement[1].getText())
	println("================================================")

	extentTest.log(LogStatus.INFO,listElement[1].getText())
	if(listElement[1].getText().equals('five'))
	{
		println("in order")
	}
	else{
		println("need to sort")
		WebUI.click(findTestObject('Object Repository/FilesPage/Label-FilesTable-Name'))
	}

	WebUI.click(findTestObject('FilesPage/SelectallFiles'))
	extentTest.log(LogStatus.PASS, 'Clicked on Select All header check box ')

	//Changing view as per test case
	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

	//Calling keyword as pre operation - icon or context menu
	if (Operation.contains('icon'))
	{
		result = CustomKeywords.'operations_FileModule.multifolderOps.multiFolderOperationsIcons'(Operation, TestCaseName, extentTest,
				katalonWebDriver)
	}
	else
	{
		def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, 'five')
		println(fileItem)
		if (fileItem) {
			WebUI.waitForElementPresent(newFileObj, 3)
			WebUI.scrollToElement(newFileObj, 3)
			WebUI.rightClick(newFileObj)
			extentTest.log(LogStatus.PASS, 'Right Clicked File to invoke context menu on  ')
		}
		result = CustomKeywords.'operations_FileModule.multifolderOpsCntxtMn.multiFolderOperations'(Operation, TestCaseName,
				extentTest, katalonWebDriver)
	}

	if (result)
	{
		extentTest.log(LogStatus.PASS, ('Verified - ' + TestCaseName) + '  Sucessfully')
	} else {
		extentTest.log(LogStatus.FAIL, ( TestCaseName) + ' - failed')
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
	extent.endTest(extentTest)
	extent.flush()
}
}