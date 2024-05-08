package appcomposer

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
import org.openqa.selenium.Keys

import internal.GlobalVariable

public class Create_NewApp {
	@Keyword

	def create_app(app,exec, extentTest) {

		WebUI.click(findTestObject('AppComposer/AppName'))

		extentTest.log(LogStatus.PASS, 'Click to Entered App name')

		WebUI.doubleClick(findTestObject('AppComposer/AppName'))

		WebUI.sendKeys(findTestObject('AppComposer/AppName'), Keys.chord(Keys.CONTROL,'a'))
		WebUI.delay(2)

		WebUI.setText(findTestObject('AppComposer/AppName'), '')

		WebUI.setText(findTestObject('AppComposer/AppName'), app)

		extentTest.log(LogStatus.PASS, 'Entered App name - ' + app)

		WebUI.click(findTestObject('AppComposer/Executableinput'))

		extentTest.log(LogStatus.PASS, 'Click to add exec commands')

		WebUI.setText(findTestObject('AppComposer/Executableinput'), '')

		WebUI.setText(findTestObject('AppComposer/Executableinput'), exec)

		extentTest.log(LogStatus.PASS, 'Add Exec commands - ' + exec)

		WebUI.click(findTestObject('AppComposer/Save'))

		extentTest.log(LogStatus.PASS, 'Click on Save button')

		WebUI.delay(3)
		WebUI.waitForElementPresent(findTestObject('Object Repository/AppComposer/AppSaved'), 5)

		def notification = WebUI.getText(findTestObject('Object Repository/AppComposer/AppSaved'))

		extentTest.log(LogStatus.PASS, 'Notification Generated ' + notification)
	}
}
