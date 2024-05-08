package operations_JobsModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable


public class GetJobRowDetails {

	@Keyword
	def newGrid(WebDriver katalonWebDriver , String dataAttribute , String FilterValue, extentTest  ) {
		def fail=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		String x1="//div[@col-id='"
		String x2 ="']"
		String myXpath=x1+dataAttribute+x2

		println(myXpath)
		if(GlobalVariable.G_Browser.equals('FireFox')) {
			WebUI.delay(5)
			extentTest.log(LogStatus.PASS, 'Waiting for jobs table to load on FireFox')
		}
		WebUI.delay(5)
		List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		println listElement.size()

		for(int i =1;i<listElement.size();i++) {
			RemoteWebElement ele = listElement.get(i)
			String myText=ele.getText()
			if(myText.equals(FilterValue)) {
				println (ele.getText())
				extentTest.log(LogStatus.PASS, 'Filter value matched for row number - '+ i + ' in jobs table')
			}
			else {
				extentTest.log(LogStatus.PASS, myText)
				//	fail=true
				// no jobs  msg
			}
		}
		return fail
	}


	@Keyword
	def getJobByUser(WebDriver katalonWebDriver , String dataAttribute , String FilterValue, extentTest , String AllJobsUser ) {
		def fail=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		String myXpath="//div[@col-id='userName']"
		println(AllJobsUser)
		println(GlobalVariable.G_userName)
		if(GlobalVariable.G_userName.equals(AllJobsUser)) {
			extentTest.log(LogStatus.PASS, 'My Jobs Filter Applied')
		}
		else {
			extentTest.log(LogStatus.PASS, 'All Jobs Filter Applied')
			WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
			WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
			//WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.ENTER))
		}
		if(GlobalVariable.G_Browser.equals('FireFox')) {
			WebUI.delay(5)
			extentTest.log(LogStatus.PASS, 'Waiting for jobs table to load on FireFox')
		}
		List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		println listElement.size()

		for(int i =1;i<listElement.size();i++) {
			RemoteWebElement ele = listElement.get(i)
			def myText=ele.getText()
			if(myText.contains(AllJobsUser)) {
				extentTest.log(LogStatus.PASS, 'Job Owner value matched for row number - '+ i + ' in jobs table')
			}
			else {
				extentTest.log(LogStatus.PASS, myText)
				fail=true
				// no jobs  msg
			}
		}
		return fail
	}

	@Keyword
	def getJobUsername(WebDriver katalonWebDriver , String dataAttribute , String FilterValue, extentTest , String AllJobsUser ){
		def fail=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		String myXpath="//div[@col-id='userName']"
		println(AllJobsUser)
		println(GlobalVariable.G_userName)
		if(GlobalVariable.G_userName.equals(AllJobsUser)) {
			extentTest.log(LogStatus.PASS, 'My Jobs Filter Applied')
		}
		else {
			extentTest.log(LogStatus.PASS, 'All Jobs Filter Applied')
			WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
			WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),AllJobsUser)
			//WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.ENTER))
		}
		if(GlobalVariable.G_Browser.equals('FireFox')) {
			WebUI.delay(5)
			extentTest.log(LogStatus.PASS, 'Waiting for jobs table to load on FireFox')
		}
		List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		println listElement.size()

		for(int i =1;i<listElement.size();i++) {
			RemoteWebElement ele = listElement.get(i)
			def myText=ele.getText()
			if(myText.contains(AllJobsUser)) {
				extentTest.log(LogStatus.PASS, 'Job Owner value matched for row number - '+ i + ' in jobs table')
			}
			else {
				extentTest.log(LogStatus.PASS, myText)
				fail=true
				// no jobs  msg
			}
		}
		return fail
	}


	@Keyword
	def getJobState(TestObject jobRow, extentTest  ) {

		def fail=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus


		String data=WebUI.getAttribute(jobRow, "data-node")
		String dataAttributeString='"jobState":"'
		String[] splitAddress = data.split(dataAttributeString)
		String  jobState = splitAddress[1]
		String[] s1 = jobState.split('",')
		String CheckString=s1[0]
		println("----------------------------------------------")
		println CheckString
		println("----------------------------------------------")
		return CheckString
	}

	@Keyword
	def getJobID(String jobText) {
		String[] splitAddress = jobText.split('J')
		String JobID = splitAddress[0]
		JobID=JobID.trim()
		return JobID
	}

	@Keyword
	def checkSubJobs(WebDriver katalonWebDriver,String origin, extentTest) {
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		def result
		if (origin.equals('JS')) {
			TestObject jobLink=(new buildTestObj.CreateJobSubmissionObjs()).myJobLinkRow(GlobalVariable.G_JobID)
			WebUI.click(jobLink)
		}
		WebUI.delay(2)
		String myXpath="//div[contains(@col-id, 'jobId')]"
		List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		println listElement.size()
		/*int x=0
		 if(x==0)
		 {
		 myXpath="//div[@col-id='jobId']"
		 listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		 listElement.size
		 println listElement.size()
		 }
		 while(x==0) {
		 listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		 listElement.size
		 println listElement.size()
		 }*/
		//extentTest.log(LogStatus.INFO, 'Sub Job keyword text passed - '+origin)
		if(listElement.size()>=2)
		{
			for(int i =1;i<listElement.size();i++) {
				RemoteWebElement ele = listElement.get(i)
				String myText=ele.getText()
				println (ele.getText())
				//extentTest.log(LogStatus.PASS, 'Sub Job ID - '+myText)
				String[] splitAddress = myText.split('\\[')
				def matchText=splitAddress[0]
				if(myText.contains(matchText))
				{
					extentTest.log(LogStatus.PASS, 'Sub Job ID - '+myText)
					result=true
				}
			}
		}
		return result
	}




	@Keyword
	def newCol(WebDriver katalonWebDriver , String dataAttribute , String ColName, extentTest  ) {
		def fail=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		String x1="//div[@class='ag-header-cell ag-header-cell-sortable'][@col-id='"
		String x2 ="']"
		String myXpath=x1+dataAttribute+x2

		String p1="//div[contains(@col-id,'"
		String p2="')]"
		String newXpath=p1+dataAttribute+p2
		//myXpath="//div[@col-id='creationTime']"
		//extentTest.log(LogStatus.PASS, 'myXpath - '+newXpath)
		//extentTest.log(LogStatus.PASS, 'dataAttribute - '+dataAttribute)
		//extentTest.log(LogStatus.PASS, 'ColName - '+ColName)

		WebUI.delay(3)

		//extentTest.log(LogStatus.INFO, 'List size - '+listElement.size())


		println(newXpath)
		if(GlobalVariable.G_Browser.equals('FireFox')) {
			WebUI.delay(5)
			extentTest.log(LogStatus.PASS, 'Waiting for table to load on FireFox')
		}

		List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(newXpath))
		println listElement.size()
		//extentTest.log(LogStatus.PASS, 'List size - '+listElement.size())
		if(listElement.size()>0)
		{
			fail=true
			//extentTest.log(LogStatus.PASS, 'Number of rows in table  -'+listElement.size())

		}
		else
		{
			fail=false
			//extentTest.log(LogStatus.FAIL, 'No rows displayed in table')

		}
		return fail
	}
	@Keyword
	def columnsorting(WebDriver katalonWebDriver , String dataAttribute , String ColName, extentTest  ) {



		String x1="//span[@class='ag-header-cell-text'][contains(text(),'"
		String x2 = "')]"
		String myXpath=x1+ColName+x2
		extentTest.log(LogStatus.PASS,"Click on the column to sort:: " + ColName)

		println(myXpath)
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		WebUI.delay(2)
		katalonWebDriver.findElement(By.xpath(myXpath)).click()

		x1="//div[@col-id='"
		x2 ="']"
		String myXpath2=x1+dataAttribute+x2

		extentTest.log(LogStatus.PASS,"iterate through the column::   " + ColName)

		println(myXpath2)

		WebUI.delay(2)
		List<WebElement> tableElements = katalonWebDriver.findElements(By.xpath(myXpath2))
		ArrayList<String> tableValues = new ArrayList<String>();
		for(int i=1; i < tableElements.size(); i++){
			String str = tableElements.get(i).getText();
			tableValues.add(str);
			println("Str** "+str)
		}
		println("*************************")
		ArrayList<String> referenceValues = new ArrayList<String>();
		for(int i=0; i < tableValues.size(); i++){
			referenceValues.add(tableValues.get(i))
			println(tableValues.get(i))
			extentTest.log(LogStatus.PASS,"The Values in the Column are ::  " + tableValues.get(i))
		}

		Collections.sort(referenceValues)

		return referenceValues.equals(tableValues)
	}
}