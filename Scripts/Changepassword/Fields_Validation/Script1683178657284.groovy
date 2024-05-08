import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import com.relevantcodes.extentreports.LogStatus as LogStatus
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.exception.StepErrorException as StepErrorException

def Browser = GlobalVariable.G_Browser
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================

try {

	WebUI.delay(2)

	extentTest.log(LogStatus.PASS, 'Navigated to url - ' + GlobalVariable.G_BaseUrl)


	switch (UserChoice) {

		case 'EmptyFields':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)
			if (((oldpassword == 'blank') || (newpassword == 'blank')) || (confirmpassword == 'blank')) {
				WebUI.delay(1)

				WebUI.clearText(findTestObject('Object Repository/change_password/oldpasswd'))

				WebUI.delay(1)

				WebUI.clearText(findTestObject('Object Repository/change_password/newpasswd'))

				WebUI.delay(1)

				WebUI.clearText(findTestObject('Object Repository/change_password/confrmpasswd'))

				WebUI.delay(1)
			}

			WebUI.click(findTestObject('Object Repository/change_password/button'))

			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')
			CustomKeywords.'change_password_feature.entering_passwords.validatemessages'(ErrorMessage1, ErrorMessage2, ErrorMessage3, extentTest)
			break

		case 'WeakPassword':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(GlobalVariable.G_Password, newpassword, confirmpassword,extentTest)

			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(8)

		//click on the notification

			WebUI.click(findTestObject('Object Repository/Notificactions/Notification'))
			extentTest.log(LogStatus.PASS, 'Clicked on the Notification tab')
		//verify the error message
			TestObject error_msg = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notificationlistitem'), 'data-tip', 'contains', ErrorMessage1, true)
			def err_msg = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(error_msg,10, extentTest, 'Error Msg Pop up ')
			def errormesage
			if (err_msg) {
				errormesage= WebUI.verifyElementPresent(error_msg, 4)
			}
			if(errormesage)
				extentTest.log(LogStatus.PASS, 'Error message:: '+ ErrorMessage2)
			else
				extentTest.log(LogStatus.FAIL, 'Failed to verify the message ')
			break
		case 'PasswordMismatch':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)


			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')

			TestObject error_msg1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notification_Item'),'text', 'equals', ErrorMessage1, true)
			extentTest.log(LogStatus.PASS, 'Error message ::  ' + ErrorMessage1)
			break
		case 'WrongOldPassword':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)

			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/Notificactions/Notification'))
			TestObject error_msg1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notificationlistitem'),'data-tip', 'contains', ErrorMessage1, true)

			extentTest.log(LogStatus.PASS, 'Error message ::  ' + ErrorMessage1)

			break
		case 'EmptyOldWrongConfirmPassword':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)

			if (oldpassword == "blank" ) {
				WebUI.delay(1)
				WebUI.clearText(findTestObject('Object Repository/change_password/oldpasswd'))
			}

			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')

		//verify the error message
			TestObject error_msg1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notification_Item'),
					'text', 'equals', ErrorMessage1, true)

			TestObject error_msg2 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notification_Item'),
					'text', 'equals', ErrorMessage2, true)

			String errormesage = WebUI.getText(error_msg1)

			extentTest.log(LogStatus.PASS, 'Error message 1:: ' + ErrorMessage1)

			extentTest.log(LogStatus.PASS, 'Error messages 2::   ' + ErrorMessage2)
			break
		case 'EmptyOldEmptyNewPassword':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)

			if (oldpassword == "blank" || newpassword == "blank" ) {
				WebUI.delay(1)
				WebUI.clearText(findTestObject('Object Repository/change_password/oldpasswd'))
				WebUI.delay(1)
				WebUI.clearText(findTestObject('Object Repository/change_password/newpasswd'))

			}
			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')

			CustomKeywords.'change_password_feature.entering_passwords.validatemessages'(ErrorMessage1, ErrorMessage2, ErrorMessage3, extentTest)

			break
		case 'EmptyConfirmPassword':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)
			if (confirmpassword == "blank"){
				WebUI.delay(1)
				WebUI.clearText(findTestObject('Object Repository/change_password/confrmpasswd'))
			}

			WebUI.delay(2)
			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')

		//verify the error message
			TestObject error_msg1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notification_Item'),
					'text', 'equals', ErrorMessage1, true)

			extentTest.log(LogStatus.PASS, 'Error message : ' + ErrorMessage1)
			break

		case 'SameOldNewPassword':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)
			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')
			WebUI.delay(3)
			WebUI.click(findTestObject('Object Repository/Notificactions/Notification'))
			TestObject error_msg1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notificationlistitem'),
					'data-tip', 'contains', ErrorMessage1, true)
			extentTest.log(LogStatus.PASS, 'Error message ::  ' + ErrorMessage1)
			break

		case 'EmptyNewEmptyConfirmPassword':
			CustomKeywords.'change_password_feature.entering_passwords.clickonchangepassword'(oldpassword, newpassword, confirmpassword,extentTest)

			if (oldpassword == "blank" || newpassword == "blank" ) {
				WebUI.delay(1)
				WebUI.clearText(findTestObject('Object Repository/change_password/newpasswd'))
				WebUI.delay(1)
				WebUI.clearText(findTestObject('Object Repository/change_password/confrmpasswd'))

			}
			WebUI.delay(2)


			WebUI.click(findTestObject('Object Repository/change_password/button'))
			WebUI.delay(1)

			extentTest.log(LogStatus.PASS, 'Clicked on the change button')



		//verify the error message
			TestObject error_msg1 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notification_Item'),
					'text', 'equals', ErrorMessage1, true)

			TestObject error_msg2 = WebUI.modifyObjectProperty(findTestObject('Object Repository/Notificactions/Notification_Item'),
					'text', 'equals', ErrorMessage2, true)


			String errormesage = WebUI.getText(error_msg1)

			extentTest.log(LogStatus.PASS, 'Error message 1:: ' + ErrorMessage1)

			extentTest.log(LogStatus.PASS, 'Error messages 2::   ' + ErrorMessage2)

			break
		case 'DefaultFields':
		//Verify Default Fields
		//click on the user menu
			WebUI.click(findTestObject('Object Repository/change_password/unityuser'))

		//click on the change password option
			WebUI.click(findTestObject('Object Repository/change_password/changepass_menu'))

			extentTest.log(LogStatus.PASS, 'Clicked on the change password icon  ')

		//verify the title
			println(WebUI.verifyElementPresent(findTestObject('Object Repository/change_password/title'), 20))

			String title = WebUI.getText(findTestObject('Object Repository/change_password/title'))
			def returnvalue= WebUI.verifyMatch(title, "Change Password", true)
			if(returnvalue)
				extentTest.log(LogStatus.PASS, ('Verify the Title ' + title) + 'is present')
			else
				extentTest.log(LogStatus.FAIL, ('Verify the Title::  ' + title) + 'is not present')


			def oldpwd= WebUI.modifyObjectProperty(findTestObject('Object Repository/change_password/placeholders') ,'placeholder', 'equals', ErrorMessage1, true)
			def newpwd= WebUI.modifyObjectProperty(findTestObject('Object Repository/change_password/placeholders') ,'placeholder', 'equals', ErrorMessage2, true)
			def confmpwd= WebUI.modifyObjectProperty(findTestObject('Object Repository/change_password/placeholders') ,'placeholder', 'equals', ErrorMessage3, true)

			println(WebUI.verifyElementPresent(oldpwd, 20))
			println(WebUI.verifyElementPresent(newpwd, 20))

			println(WebUI.verifyElementPresent(confmpwd, 20))
			extentTest.log(LogStatus.PASS, 'Verifying the Field::  '+ ErrorMessage1 + ' is present ')
			extentTest.log(LogStatus.PASS, 'Verifying the Field::  '+ ErrorMessage2+ ' is present ')
			extentTest.log(LogStatus.PASS, 'Verifying the Field::  '+ ErrorMessage3 + ' is present ')
			break
	}


}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'

	extentTest.log(LogStatus.FAIL, ex)

	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))

	KeywordUtil.markFailed('ERROR: ' + ex)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'

	extentTest.log(LogStatus.FAIL, ex)

	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()
}


