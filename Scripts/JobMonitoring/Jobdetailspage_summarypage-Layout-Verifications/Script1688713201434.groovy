import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'
//=====================================================================================

def result
def jobName
def inputFolderPath
switch(jobState)
{
	case 'Completed': inputFolderPath=navLocation +"/ForJM/C1/OpsCompleted" 
	break;
	case 'Running': inputFolderPath=navLocation +"/ForJM/R1/Ops/"
	break;
	case 'Failed': inputFolderPath=navLocation +"/ForJM/F1/Ops/"
	break;
}
	
		
String[] InputFiles=['Running.sh','ToDelete.txt','ToEdit.txt','ToOpen.txt','ToOpenWith.txt','ToRename.txt']
WebUI.delay(2)
try
{
	WebUI.delay(2)
	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('LoginPage/NewJobPage/AppList_ShellScript'),
			20,extentTest,'App def')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}


	extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')


	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
	//WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
	//WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), 'Ops')
	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), 'RunJob')
	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	String myXpath="//div[@col-id='jobId']"
	List<WebElement> JobList = katalonWebDriver.findElements(By.xpath(myXpath))
	RemoteWebElement JobID = JobList.get(1)
	println(JobID.getText())
	JobID.click()

	WebUI.delay(2)
	if(WebUI.verifyElementPresent(findTestObject('Object Repository/JobDetailsPage/details_tab'),2))
	{
		extentTest.log(LogStatus.PASS, 'User navigated to Jobs Summary page by clicking on JobID link')
		//extentTest.log(LogStatus.PASS,'Verified Test cases - AD-1556 Job submission: Job details page.')
		result=false
		switch(userChoice)
		{

			case 'Fields':
				String myXpathText='//div[@class="job-summary-item"]//span[@class="job-summary-item-label show-text-ellipsis"]'
				String myXpathValue='//div[@class="job-summary-item"]//span[@class="job-summary-item-value show-text-ellipsis"]//span[position()=2]'
				List<WebElement> listElementKey = katalonWebDriver.findElements(By.xpath(myXpathText))
				println listElementKey.size()
				List<WebElement> listElementValue = katalonWebDriver.findElements(By.xpath(myXpathValue))
				println listElementValue.size()
				for(int i =1;i<listElementKey.size();i++)
				{
					RemoteWebElement ele = listElementKey.get(i)
					String myText=ele.getText()
					RemoteWebElement eleValue = listElementValue.get(i)
					String myVal=eleValue.getText()
					extentTest.log(LogStatus.PASS, 'Job Attribute  details  - '+myText +' ::: '+myVal)
				}


				break;
				
			case 'No data' :
			findTestObject('Object Repository/JobDetailsPage/NoDataTab')
			boolean noDataAvail=WebUI.verifyElementPresent(findTestObject('Object Repository/JobDetailsPage/NoDataTab'), 2)
			if(noDataAvail) {
				String nDA=WebUI.getText(findTestObject('Object Repository/JobDetailsPage/NoDataTab'))
				println(nDA)
				extentTest.log(LogStatus.PASS, 'No Data Available in JobDetailsPage when Jobs are in Queued Stage')
			}
			extentTest.log(LogStatus.PASS,'Verified Test cases - AD-1556 Job submission: Job details page.')
			break;

			case 'DetailsLink' :
				WebUI.click(findTestObject('Object Repository/JobDetailsPage/details_tab'))
				String myXpathText='//div[@col-id="name"]'
				String myXpathValue='//div[@col-id="value"]'
				List<WebElement> listElementKey = katalonWebDriver.findElements(By.xpath(myXpathText))
				println listElementKey.size()
				List<WebElement> listElementValue = katalonWebDriver.findElements(By.xpath(myXpathValue))
				if(listElementValue.size()!=0||listElementKey.size()!=0)
				{
					extentTest.log(LogStatus.PASS, 'User is naviagted to details page ')
					if(listElementValue.size()==listElementKey.size())
					{
						extentTest.log(LogStatus.PASS, 'Number of details listed -  '+listElementValue.size())
					}
				}

				for (int i=1 ;i<listElementKey.size();i++)
				{
					RemoteWebElement ele = listElementKey.get(i)
					String myText=ele.getText()
					if(myText.equals("Name"))
					{
						RemoteWebElement eleValue = listElementValue.get(i)
						String myVal=eleValue.getText()
						jobName=myVal
						break
					}
				}
				WebUI.click(findTestObject('Object Repository/GenericObjects/TitleLink_Jobs'))
				extentTest.log(LogStatus.PASS, 'Clicked on Jobs Tab ')
				result = WebUI.verifyElementPresent(findTestObject('Object Repository/JobMonitoringPage/Lable_SubmitJobUsing'),3)
				if(result)
				{
					extentTest.log(LogStatus.PASS, 'User is naviagted to jobs page ')
					extentTest.log(LogStatus.PASS, 'Verified "Submit Job Using: " lable on the Jobs Page ')
					extentTest.log(LogStatus.PASS, 'Verified  - AD-1557	Job submission:   Job details page. - go back with bread crumb ')

				}

				break;

			case 'OutPut':

				String myXpathText='//div[@col-id="name"]'

				jobName="Ops"
				List<WebElement> listElementKey = katalonWebDriver.findElements(By.xpath(myXpathText))
				println listElementKey.size()
				WebUI.click(findTestObject('Object Repository/JobDetailsPage/Output_tab'))
				extentTest.log(LogStatus.PASS, 'CLicked on Output tab to verify .e and .o files ')
				listElementKey = katalonWebDriver.findElements(By.xpath(myXpathText))

				for (int i=1 ;i<listElementKey.size();i++)
				{
					RemoteWebElement ele = listElementKey.get(i)
					String myText=ele.getText()
					if(myText.contains(jobName+".e")||myText.contains(jobName+".o"))
					{
						extentTest.log(LogStatus.PASS, 'Job Files - '+myText+' lsited')
					}
				}
				break;


			case 'Input':

				WebUI.click(findTestObject('JobMonitoringPage/InputFolder'))

				extentTest.log(LogStatus.PASS, 'Clicked on Input tab')
				result =WebUI.verifyElementPresent(findTestObject('Object Repository/JobDetailsPage/InputFolderPath'), 3)
				if(result)
				{
					extentTest.log(LogStatus.PASS, 'Job input folder listed  - '+WebUI.getText(findTestObject('Object Repository/JobDetailsPage/InputFolderPath')))
				}
				String myXpathText='//div[@col-id="name"]'

				jobName="Ops"
				List<WebElement> listElementKey = katalonWebDriver.findElements(By.xpath(myXpathText))
				println listElementKey.size()
				listElementKey = katalonWebDriver.findElements(By.xpath(myXpathText))

				for (int i=1 ;i<listElementKey.size();i++)
				{
					RemoteWebElement ele = listElementKey.get(i)
					String myText=ele.getText()
			
					if(Arrays.asList(InputFiles).contains(myText))
					{
						extentTest.log(LogStatus.PASS,"Job file - "+myText +" listed ")
						result=true

					}


				}
				break;



		}
	}
	else
	{
		extentTest.log(LogStatus.FAIL, 'user notnavigated to summary page on clicking jobid link')
	}

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

}
catch (StepErrorException  e)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)

	String p =TestCaseName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))
}
finally
{

	extent.endTest(extentTest);
	extent.flush();

}

