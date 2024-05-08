package create_user_role

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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.relevantcodes.extentreports.LogStatus


import internal.GlobalVariable
import  org.openqa.selenium.Keys


public class user {
	@Keyword
	def add_user(String username, String firstname, String lastname, extentTest) {

		WebUI.delay(2)


		WebUI.click(findTestObject('Access_Management/Add_user'))
		extentTest.log(LogStatus.PASS, 'Click on users')

		WebUI.click(findTestObject('Access_Management/Add_userbutton'))
		extentTest.log(LogStatus.PASS, 'Click on add user')



		WebUI.click(findTestObject('Access_Management/Username'))
		WebUI.doubleClick(findTestObject('Access_Management/Username'))
		WebUI.delay(3)
		WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
		WebUI.delay(2)
		WebUI.sendKeys(findTestObject('Object Repository/Access_Management/Username_text'), Keys.chord(Keys.BACK_SPACE))
		WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
		WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), username)
		extentTest.log(LogStatus.PASS, 'Add username name - ' + username )

		WebUI.click(findTestObject('Access_Management/Firstname'))
		WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
		extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)

		WebUI.click(findTestObject('Access_Management/Lastname'))
		WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
		extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)


		WebUI.click(findTestObject('Access_Management/Save'))
		extentTest.log(LogStatus.PASS, 'Click on save')
	}
}
