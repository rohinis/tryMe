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

import internal.GlobalVariable
import com.relevantcodes.extentreports.LogStatus
import  org.openqa.selenium.Keys

public class role {

	@Keyword
	def add_role(String roleid, extentTest) {
		WebUI.delay(2)

		WebUI.click(findTestObject('Access_Management/Add_rolebutton'))

		WebUI.click(findTestObject('Access_Management/Edit_role'))

		WebUI.delay(2)

		//WebUI.click(findTestObject('Access_Management/Edit_roleid'))
		WebUI.delay(3)
		WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))
		WebUI.sendKeys(findTestObject('Object Repository/AppComposer/Text'), Keys.chord(Keys.BACK_SPACE))
		WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')
		WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), roleid)
		extentTest.log(LogStatus.PASS, 'Add roleid name -' + roleid)

		WebUI.click(findTestObject('Access_Management/Confirm_button'))
		extentTest.log(LogStatus.PASS, 'Click on save')

	}
}
