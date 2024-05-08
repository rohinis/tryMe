import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)
def username
def passwd 
def Browser = GlobalVariable.G_Browser
extentTest.log(LogStatus.PASS, 'Navigated to Acces Instance - '+GlobalVariable.G_BaseUrl)
if (user=='admin')
{
	username='pbsworks'
	passwd='pbsworks'
}
else
{
	username=GlobalVariable.G_userName
	passwd=GlobalVariable.G_Password
}
WebUI.setText(findTestObject('LoginPage/username_txtbx'), username)
WebUI.setText(findTestObject('LoginPage/password_txtbx'), passwd)
WebUI.click(findTestObject('LoginPage/login_btn'))
extentTest.log(LogStatus.PASS, 'Entered Creds - username - '+username +' password - '+passwd)

extentTest.log(LogStatus.PASS, 'Clicked on Login Button ')


def jobsTab = (new customWait.WaitForElement()).WaitForelementPresent(findTestObject('Object Repository/Landing_Page/LandigPage_AltairAccess_Link'),
		10,extentTest, 'AltairAccess Logo ')

if (jobsTab) {
	WebUI.click(findTestObject('Object Repository/Landing_Page/LandigPage_AltairAccess_Link'))
}

extentTest.log(LogStatus.PASS, 'Verified AltairAccess Logo post login ')

//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()

def location = navLocation + '/FilesModule/HiddenItems/'

//=====================================================================================
def result

try {
    extentTest.log(LogStatus.PASS, 'prefValue --- ' + prefValue)

    CustomKeywords.'customWait.setPrefrenceHidden.setprefrence'(prefValue, extentTest)

    extentTest.log(LogStatus.PASS, 'Setting the prefrence to view hidden files')

	extentTest.log(LogStatus.PASS, 'UserChoice - '+userChoice)
	CustomKeywords.'customWait.setPrefrenceHidden.navigateTo'(TestCaseName, userChoice, extentTest)
	
	result = CustomKeywords.'customWait.setPrefrenceHidden.checkHiddenItems'(prefValue, TestCaseName, extentTest)

    if (result) {
        extentTest.log(LogStatus.PASS, ('Hidden file - .hiddenFile is listed after log out and login '))
    } else {
        extentTest.log(LogStatus.FAIL, ('Hidden file - .hiddenFile is not listed '))
    }
    
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

    WebUI.setText(findTestObject('LoginPage/username_txtbx'), username)

    extentTest.log(LogStatus.PASS, 'Enter username -  ' + username)

    WebUI.setText(findTestObject('LoginPage/password_txtbx'), passwd)

    extentTest.log(LogStatus.PASS, 'Enter  password  - ' + passwd)

    WebUI.click(findTestObject('LoginPage/login_btn'))

    extentTest.log(LogStatus.PASS, 'Click on Login')

    WebUI.delay(2)

    CustomKeywords.'customWait.setPrefrenceHidden.navigateTo'(TestCaseName, userChoice, extentTest)
	
	result = CustomKeywords.'customWait.setPrefrenceHidden.checkHiddenItems'(prefValue, TestCaseName, extentTest)

    if (result) {
        extentTest.log(LogStatus.PASS, ('Hidden file - .hiddenFile is listed after log out and login '))
    } else {
        extentTest.log(LogStatus.FAIL, ('Hidden file - .hiddenFile is not listed '))
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
    extentTest.log(LogStatus.PASS, 'Closing the browser after executinge test case - ' + TestCaseName)

    extent.endTest(extentTest)

    extent.flush()
}

