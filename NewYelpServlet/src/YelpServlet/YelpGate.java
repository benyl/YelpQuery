package YelpServlet;

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
import java.util.regex.Pattern;
//import java.util.List;
//import java.util.Set;
//import java.util.HashSet;
//import java.util.Iterator;

import com.mongodb.*;

public class YelpGate{
	
	// used for corpus control
	private static gate.CorpusController ctrl;
	
	// singleton
	private static YelpGate instance = null;
	
	/*
	 * constructor of YelpGate, load Gate Plugins when initializaion
	 */
	private YelpGate()
	{
		//System.out.println("start initialization...");
		try {
			// remember create log4j.properties file under project dir!!!
			
			// use /path/to/your/webapp/WEB-INF as gate.home 
			String gateHome = 
					"C:/eclipse_home/workspace/NewYelpServlet/WebContent/WEB-INF/";
			Gate.setGateHome(new File(gateHome));
			Gate.setSiteConfigFile(new File(gateHome, "gate.xml"));
			Gate.setUserConfigFile(new File(gateHome, "user-gate.xml"));
			
			Gate.init();
	
			Gate.getCreoleRegister().registerDirectories(
					new File(gateHome + "plugins", "ANNIE").toURI().toURL());

			//For Only if you are using this plugin
			Gate.getCreoleRegister().registerDirectories(
					new File(gateHome + "plugins", "Tools").toURI().toURL());
		
			ctrl = ((gate.CorpusController)
					loadObjectFromFile(new java.io.File(gateHome + "gate/yelp.xgapp")));
			
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
	public String getKeyword(String question)
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
		    // REALLY important to release doc and coprpus after processing each request, 
		    // question, instruction, etc., or your program will quickly run out of heap space.
		    Factory.deleteResource(doc);
		    Factory.deleteResource(corpus);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return answer;
	} // end of: public String getKeyword()

	/*
	 * process questions and return the result
	 */	/*
	public BasicDBObject process(String question)
	{
		BasicDBObject query = new BasicDBObject();
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

			  // require fully match
		      String[] featureArr1 = {
		    		  "cafe_name",
		    		  "cafe_creditcards",
		    		  "cafe_caters",
		    		  "cafe_delivery",
		    		  "cafe_goodforgroups",
		    		  "cafe_goodforkids",
		    		  "cafe_hastv",
		    		  "cafe_noiselevel",
		    		  "cafe_outdoorseating",
		    		  "cafe_price",
		    		  "cafe_takeout",
		    		  "cafe_reservation",
		    		  "cafe_tableservice",
		    		  "cafe_wheelchairaccessible",
		    		  "cafe_wifi"};

			  // require partly match
		      String[] featureArr2 = {
		    		  "cafe_category",
		    		  "cafe_neighborhood",
		    		  "cafe_address",
		    		  "cafe_alcohol",
		    		  "cafe_ambience",
		    		  "cafe_goodformeal",
		    		  "cafe_parking",
		    		  "cafe_attire"};
		      
		      gate.Annotation ann = annList.get(0);
		      
		      for(String str:featureArr1){
		    	  Object obj = ann.getFeatures().get(str);
		    	  if(obj != null)
		    		  query.append(str, java.util.regex.Pattern.compile(
		    				  obj.toString(), Pattern.CASE_INSENSITIVE));
		      }
		      
		      for(String str:featureArr2){
		    	  Object obj = ann.getFeatures().get(str);
		    	  if(obj != null)
		    		  query.append(str, java.util.regex.Pattern.compile(
		    				  ".*"+obj.toString()+".*", Pattern.CASE_INSENSITIVE));
		      }

		    // REALLY important to release doc and coprpus after processing each request, 
		    // question, instruction, etc., or your program will quickly run out of heap space.
		    Factory.deleteResource(doc);
		    Factory.deleteResource(corpus);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		System.out.println(question);
		System.out.println(getKeyword(question));
		return query;
	} // end of: public String process()*/

	/*
	 * process questions and return the result
	 */
	public Result process(String question)
	{
		System.out.println(question);
		System.out.println(getKeyword(question));
		
		Result result = new Result();
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

			gate.Annotation ann = annList.get(0);
			String kind = ann.getFeatures().get("kind").toString();
			
			if(kind.equals("search")) result.type = 0; 
			else if(kind.equals("check")) result.type = 1;
			else if(kind.equals("ask")) result.type = 2;
			
			if(result.type > 0)
			{
				Object obj = ann.getFeatures().get("cafe_name");
				if(obj != null)
					result.name.append("cafe_name", 
							java.util.regex.Pattern.compile(
							obj.toString(), Pattern.CASE_INSENSITIVE));
			}

			// require fully match
			String[] featureArr1 = {
					  "cafe_name",
					  "cafe_creditcards",
					  "cafe_caters",
					  "cafe_delivery",
					  "cafe_goodforgroups",
					  "cafe_goodforkids",
					  "cafe_hastv",
					  "cafe_noiselevel",
					  "cafe_outdoorseating",
					  "cafe_price",
					  "cafe_takeout",
					  "cafe_reservation",
					  "cafe_tableservice",
					  "cafe_wheelchairaccessible",
					  "cafe_wifi"};

			// require partly match
			String[] featureArr2 = {
					  "cafe_category",
					  "cafe_neighborhood",
					  "cafe_address",
					  "cafe_alcohol",
					  "cafe_ambience",
					  "cafe_goodformeal",
					  "cafe_parking",
					  "cafe_attire"};
			
		      for(String str:featureArr1){
		    	  Object obj = ann.getFeatures().get(str);
		    	  if(obj != null)
		    	  {
		    		  result.query.append(str, java.util.regex.Pattern.compile(
		    				  obj.toString(), Pattern.CASE_INSENSITIVE));
		    		  result.highlight += str + ", ";
		    	  }
		      }
		      
		      for(String str:featureArr2){
		    	  Object obj = ann.getFeatures().get(str);
		    	  if(obj != null)
		    	  {
		    		  result.query.append(str, java.util.regex.Pattern.compile(
		    				  ".*"+obj.toString()+".*", Pattern.CASE_INSENSITIVE));
		    		  result.highlight += str + ", ";
		    	  }
		      }
		      
		    if(result.type > -1)
		    {
				Object obj = ann.getFeatures().get("cafe_rating");
				if(obj != null) 
				{
					if(obj.toString().equals("Good"))
						result.query.append("cafe_rating", new BasicDBObject("$gte", 4));
					else
						result.query.append("cafe_rating", new BasicDBObject("$lte", 3));
					result.highlight += "cafe_rating" + ", ";
				}
			}
		    	

			if(result.type == 2)
			{
				Object obj = ann.getFeatures().get("info");
				if(obj != null) result.highlight += obj.toString();
			}
				
		    // REALLY important to release doc and coprpus after processing each request, 
		    // question, instruction, etc., or your program will quickly run out of heap space.
		    Factory.deleteResource(doc);
		    Factory.deleteResource(corpus);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return result;
	} // end of: public String process()
	
	/*
	 * main function
	 */	
	public static void main(String[] args) {
		//String question = "Which restaurants that serve Halal are good?";
		//String question = "Which restaurant nearby that doesn't have free wifi and good for kids?";
		//String question = "Which restaurant that is good for kids?";
		String question = "Is Teresa's Restaurant good?";
		//String question = "Where is Teresa's Restaurant?";
		//String question = "What kind of food does Teresa's Restaurant serve?";
		
		Result answer = YelpGate.getInstance().process(question);
		System.out.println(answer.type);
		System.out.println(answer.name.toString());
		System.out.println(answer.query.toString());
		System.out.println(answer.highlight);
	}
	
} // end of: public class YelpGate {