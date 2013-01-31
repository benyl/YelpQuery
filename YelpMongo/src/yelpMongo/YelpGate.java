package yelpMongo;

//import gate.*;
import gate.Gate;
//import gate.gui.MainFrame;
//import gate.creole.SerialAnalyserController;
//import gate.AnnotationSet;
import gate.Corpus;
import gate.Factory;
import static gate.util.persistence.PersistenceManager.loadObjectFromFile;

import java.io.File;
//import java.net.URL;
import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.HashSet;
//import java.util.Iterator;

public class YelpGate {

	// remember create log4j.properties file under project dir!!!
	String homeDir = "C:/Program Files/GATE_Developer_7.0/";
	String ruleDir = "C:/eclipse_home/workspace/YelpMongo/Yelp/yelp.xgapp";
	
	// used for corpus control
	private static gate.CorpusController ctrl;
	
	// singleton
	private static YelpGate instance = null;
	
	/*
	 * constructor of YelpGate, load Gate Plugins when initializaion
	 */
	private YelpGate()
	{
		System.out.println("start initialization...");
		try {
			if (Gate.getGateHome() == null)
			{
				Gate.setGateHome(new File(homeDir));
			}
			
			Gate.init();
			Gate.getCreoleRegister().registerDirectories(
					new File(homeDir + "plugins", "ANNIE").toURI().toURL());
		
			//For Only if you are using this plugin
			Gate.getCreoleRegister().registerDirectories(
					new File(homeDir + "plugins", "Tools").toURI().toURL());
		
			ctrl = ((gate.CorpusController)
					loadObjectFromFile(new java.io.File(ruleDir)));
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	} // end of: private YelpGate()
	
	/*
	 * get the instance of YelpGate, create one when it was first called
	 */	
	public static YelpGate getInstance()
	{
		if (instance == null)
		{
			synchronized (YelpGate.class) 
			{
				if (instance == null) 
				{
					instance = new YelpGate();
				}
			}
		}
		return instance;
	} // end of: public static YelpGate getInstance()

	/*
	 * process questions and return a string  
	 */	
	public String process(String question)
	{
		String answer = "";
		try 
		{
			Corpus corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
			gate.Document doc = gate.Factory.newDocument(question);
			corpus.add(doc);
			ctrl.setCorpus(corpus);
			ctrl.execute();
			
			corpus.clear();
			
			ArrayList<gate.Annotation> annList = new ArrayList<gate.Annotation>();
			//Add whatever annotations you are interested in processing further
			//to annList--questions, requests, instructions, etc.
			annList.addAll(doc.getAnnotations().get("question"));
			
		    for(gate.Annotation ann:annList)
		    {
		    	answer += ann.getFeatures().toString();
		    }
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return answer;
	} // end of: public String process()
	
	/*
	 * main function
	 */	
	public static void main(String[] args) {
		String question = "Which thai restaurant nearby that doesn't have free wifi and good for lunch and accept credit cards?";
		String answer = YelpGate.getInstance().process(question);
		
		System.out.println(answer);
	} 
	
} // end of: public class YelpGate { 
