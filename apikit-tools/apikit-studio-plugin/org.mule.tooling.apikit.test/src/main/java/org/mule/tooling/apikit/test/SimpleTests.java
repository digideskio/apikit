package org.mule.tooling.apikit.test;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.waitForWidget;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mule.tooling.apikit.test.api.GlobalElementWizardEditorBot;
import org.mule.tooling.apikit.test.api.MulePropertiesEditorBot;
import org.mule.tooling.apikit.test.api.MuleStudioBot;
import org.mule.tooling.apikit.test.api.StudioPreferencesEditor;
import org.mule.tooling.apikit.test.api.UILabels;
import org.mule.tooling.apikit.test.api.XmlComparer;

@RunWith(SWTBotJunit4ClassRunner.class)
public class SimpleTests {

    private static SWTWorkbenchBot bot;
    private static MuleStudioBot muleStudioBot;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // increase timeout to 10 seconds
        SWTBotPreferences.TIMEOUT = 8000;
        // increase timeout to 1 second
        SWTBotPreferences.PLAYBACK_DELAY = 10;
        bot = new SWTWorkbenchBot();
        muleStudioBot = new MuleStudioBot(bot);
    }
    
    @Ignore
  public void changeAPIkitComponentNameMule34() throws Exception {
    	changeAPIkitComponentName(UILabels.MULE_34,"Mule34");
    }
    
    @Ignore
  public void changeAPIkitComponentNameMule35() throws Exception {
    	changeAPIkitComponentName(UILabels.MULE_35,"Mule35");
    }    	

   public void changeAPIkitComponentName(String muleVersion,String nameAddition) throws Exception {
    	
    	String projectName = "dnd" + System.currentTimeMillis();
    	String flowName = "testflowcomponentname"+nameAddition;
    	String expectedXml = "/resources/changeAPIkitComponentName-expected.xml";
    	muleStudioBot.createProject(projectName, "changeAPIkitComponentName",muleVersion);
    	muleStudioBot.createFlow(flowName, "Description of the flow");

        SWTBotGefEditor editor = new SWTBotGefEditor(bot.editorByTitle(flowName).getReference(), bot);
        editor.bot().textWithLabel("Filter:").setText("HTTP");
        editor.activateTool("HTTP").click(601,500);
        editor.bot().textWithLabel("Filter:").setText("APIkit Router");
        editor.activateTool("APIkit Router").click(70,80);
        editor.click("APIkit Router");
        
        MulePropertiesEditorBot propertiesEditorBot = new MulePropertiesEditorBot(bot);
        propertiesEditorBot.setTextValue("Display Name:", "newname");
        propertiesEditorBot.apply();
        
        editor.click("newname");
        
        String nameInsideTheViewer = propertiesEditorBot.getTextValue("Display Name:");
        Assert.assertThat(nameInsideTheViewer, CoreMatchers.is("newname"));
        propertiesEditorBot.apply(); 
        
      //XML comparison
        XmlComparer comparer = new XmlComparer(bot);
        comparer.compareToTheXMLUsingUI("XML files are different.",flowName, expectedXml, true);
        
        muleStudioBot.saveAll();
    }

    @Ignore
    public void checkIfMuleStudioIsPairedWithASR(){
    	String token ="";
    	String host = "agent-registry.mulesoft.com";
    	String port = "443";
    	String path = "";
    	getPrincipalShell().activate();
    	SWTBotMenu file = bot.menu("Window").click();
    	file.menu("Preferences").click();
    	StudioPreferencesEditor preferences = new StudioPreferencesEditor(bot);
    	preferences.assertASRagentConfiguration(token, host, port, path);
    }
    
    private SWTBotShell getPrincipalShell(){
    	SWTBotShell shell = null;
    	for(int i = 0; i < bot.shells().length; i++){
    		if (bot.shells()[i].getText().endsWith("Mule Studio")){
    			shell = bot.shells()[i]; 
    		}
    	}
    	if (shell == null){
    		Assert.assertNotNull("Mule Studio shell could not be localized",null);
    	}
    	return shell;
    }
  
    @Ignore
    public void addAPIkitGlobalElementRouterMule34() throws Exception {
    	addAPIkitGlobalElementRouter(UILabels.MULE_34,"Mule34");
    }
    
    @Ignore
    public void addAPIkitGlobalElementRouterMule35() throws Exception {
    	addAPIkitGlobalElementRouter(UILabels.MULE_35,"Mule35");
    }
    
    public void addAPIkitGlobalElementRouter(String muleVersion, String nameAddition) throws Exception {
    	
    	String projectName = "aager" + System.currentTimeMillis();
    	String flowName = "testflowglobalelement" + nameAddition;
    	String expectedXml = "resources/addAPIkitGlobalElementRouter-expected.xml";
    	muleStudioBot.createProject(projectName, "changeAPIkitComponentName",muleVersion);
    	muleStudioBot.createFlow(flowName, "Description of the flow");

        SWTBotGefEditor editor = new SWTBotGefEditor(bot.editorByTitle(flowName).getReference(), bot);
        editor.bot().textWithLabel("Filter:").setText("HTTP");
        editor.activateTool("HTTP").click(601,500);
        editor.bot().textWithLabel("Filter:").setText("APIkit Router");
        editor.activateTool("APIkit Router").click(70,80);
        editor.click("APIkit Router");
        
        MulePropertiesEditorBot propertiesEditorBot = new MulePropertiesEditorBot(bot);
        propertiesEditorBot.setTextValue("Display Name:", "newname");
        propertiesEditorBot.clickTooltipButton("Add");
        GlobalElementWizardEditorBot wizardEditor = new GlobalElementWizardEditorBot(bot);
        
        wizardEditor.setName("MyRouter");
        wizardEditor.setYamlFileName("yamlFile.yaml");
        wizardEditor.setConsolePath("new console path");
        wizardEditor.clickOK();
        propertiesEditorBot.activate();
        propertiesEditorBot.apply();
        
        //XML comparison
        XmlComparer comparer = new XmlComparer(bot);
        comparer.compareToTheXMLUsingUI("XML files are different.",flowName, expectedXml, true);

        muleStudioBot.save();
    }

    @Test
    public void createNewAPIkitProjectMule34(){
    	createNewAPIkitProject(UILabels.MULE_34);
    }
    
    @Test
    public void createNewAPIkitProjectMule35(){
    	createNewAPIkitProject(UILabels.MULE_35);
    }
    
    public void createNewAPIkitProject(String muleVersion){
    	
    	String projectName = "cnap" + System.currentTimeMillis();
    	muleStudioBot.createProject(projectName, "newAPIkitProject",muleVersion);
    }
   
    @Ignore
    public void createNewAPIkitExample() throws Exception{
    	String projectName = "cnae" + System.currentTimeMillis();
    	String expectedXml = "resources/createNewAPIkitExample-expected.xml";
    	
    	muleStudioBot.createAPIkitExample(projectName, "newAPIkitExample","REST API with APIkit");
    	//waitForEditor(withText("leagues"));
    	String flowName = "leagues";
    	waitForWidget((withText(flowName)));

        String consoleText = "";
        waitForWidget((withText("Console")));
        //waitForView(withText("Console")));
        bot.viewByTitle("Console").show();
        do{
        	consoleText = bot.viewByTitle("Console").bot().styledText().getText().toString();
        }
        while(!consoleText.contains("BUILD SUCCESS") && !consoleText.contains("BUILD FAILED"));

    	//XML comparison
        
        XmlComparer comparer = new XmlComparer(bot);
        comparer.compareToTheXMLUsingUI("XML files are different.",flowName, expectedXml, true);
        
        muleStudioBot.saveAll();
        //bot.waitUntil((ICondition)bot.viewByTitle("Console"));
    	//XML comparison
        /*String expectedXml = "/resources/apikit-samples-example-expected.xml";
        XmlComparer comparer = new XmlComparer(bot);
        comparer.compareToTheXMLUsingUI(flowName, expectedXml, true);
        */
    //    bot.widget(instanceOf(org.eclipse.swt.custom.StyledText.class), bot.viewByTitle("Console").getWidget());
       /* Widget consoleViewComposite = view.getWidget();

		StyledText swtStyledText = (StyledText) bot.widget(instanceOf(StyledText.class), consoleViewComposite);
		SWTBotStyledText styledText = new SWTBotStyledText(swtStyledText);
*/

    }
}
    