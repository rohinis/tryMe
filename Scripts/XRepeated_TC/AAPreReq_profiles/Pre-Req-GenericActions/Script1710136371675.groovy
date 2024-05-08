import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
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
TestObject genericAction

WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
WebUI.click(findTestObject('Generic_Actions/Generic_Actions'))

switch (userChoice)
{
	case 'Files':
		genericAction=WebUI.modifyObjectProperty(findTestObject('Generic_Actions/Qstat_of_Job'), 'text', 'equals', 'Files (hpccluster)', true)
		WebUI.click(genericAction)
		WebUI.setText(findTestObject('JobMonitoringPage/txtBx_GenericActionInput'), GlobalVariable.G_userName)
		WebUI.click(findTestObject('GenericObjects/Confirm_button'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Generic_Actions/Close_button'))
		break

	case 'Jobs':
		genericAction=WebUI.modifyObjectProperty(findTestObject('Generic_Actions/Qstat_of_Job'), 'text', 'equals', 'ShortCut (hpccluster)', true)
		WebUI.click(genericAction)
		WebUI.setText(findTestObject('JobMonitoringPage/txtBx_GenericActionInput'), GlobalVariable.G_userName)
		WebUI.click(findTestObject('GenericObjects/Confirm_button'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Generic_Actions/Close_button'))
		break

}