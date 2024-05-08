package generateFilePath

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable
import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.relevantcodes.extentreports.LogStatus as LogStatus

public class filePath {

	@Keyword
	def getFilePath(String filePath) {

		if(GlobalVariable.G_HostMachine.equals('Windows')) {
			String updatedPath = filePath.replaceAll("\\/", "\\\\");
			return updatedPath
		}
		else {
			return filePath
		}
	}
	@Keyword
	def navlocation( String location,extentTest) {

		WebUI.click(findTestObject('Object Repository/FilesPage/GotoFoldericon'))
		WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('Object Repository/FilesPage/gotofoldertext'), 10)
		WebUI.setText(findTestObject('Object Repository/FilesPage/gotofoldertext'), location)
		WebUI.delay(2)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/gotofoldertext'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Navigated to ' + location)
	}



	@Keyword
	def execLocation(){
		def navLocation
		println ("=====================================================")
		println(GlobalVariable.G_Platform +'keyword')
		println ("=====================================================")

		if(GlobalVariable.G_Platform.equals('Gird')) {
			navLocation='/gridusers/'+GlobalVariable.G_userName

			println(GlobalVariable.G_Platform +'   grid')
		}
		else {
			if(GlobalVariable.G_Platform.equals('Windows')) {
				navLocation=GlobalVariable.G_StageArea+'/'+GlobalVariable.G_userName
				println(GlobalVariable.G_Platform +'   win')
			}
			else {
				navLocation='/stage/'+GlobalVariable.G_userName
				println(GlobalVariable.G_Platform +'   liux')
			}
		}
		println ("=====================================================")
		println (navLocation + 'from keyword')
		println ("=====================================================")
		return navLocation
	}




	@Keyword
	def getErrorFileName(String jobID) {
		String x1 = '//div[contains(text(),\'.e'
		String x2 = '\')]'
		String xpath_eFile = (x1 + jobID) + x2
		println xpath_eFile

		TestObject eFileIdentifier = new TestObject('objectName')
		eFileIdentifier.addProperty('xpath', ConditionType.EQUALS, xpath_eFile)
		return eFileIdentifier
	}


	@Keyword
	def getOutFileName(String jobID) {
		String x1 = '//div[contains(text(),\'.o'
		String x2 = '\')]'
		String xpath_oFile = (x1 + jobID) + x2
		println xpath_oFile

		TestObject oFileIdentifier = new TestObject('objectName')
		oFileIdentifier.addProperty('xpath', ConditionType.EQUALS, xpath_oFile)
		return oFileIdentifier
	}

	@Keyword
	def downloadverification(String JobName) {

		//	File path = new File('/katalonDownloads');
		File path = new File(GlobalVariable.G_DownloadFolder);
		File [] files = path.listFiles();
		for (int i = 0; i < files.length; i++){
			if (files[i].isFile()){
				String fn=files[i]
				if(fn.contains(JobName+'_')) {
					//this line weeds out other directories/folders
					println(files[i]);
				}
			}
		}
	}
}
