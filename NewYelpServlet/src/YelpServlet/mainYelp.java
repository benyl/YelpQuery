package YelpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servlet implementation class mainYelp
 */
@WebServlet("/mainYelp")
public class mainYelp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mainYelp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    Enumeration paramNames = request.getParameterNames();
	    String paramName = (String)paramNames.nextElement();
	    String[] paramValues = request.getParameterValues(paramName);
		String paramValue = paramValues[0];
	    String title = "Search result by: <font color=#b92828>"+ paramValue + "</font>";
	    
	    out.println("<script type='text/javascript' src='http://code.jquery.com/jquery-1.8.3.min.js'></script>" +
	    		"<script type='text/javascript'>" +
	    				"function scrollToBottom() {" +
		"$('html, body').animate({scrollTop:$(document).height()}, 'slow');" +
	"} "+
	"function scrollToTop() {" +
		" $('html, body').animate({scrollTop:0}, 'slow');"+
	"}" +
"</script>");
	    out.println("<BODY BGCOLOR=\"\"><center>\n" +
	    		"<div style='right:5%; position: fixed; z-index:1; bottom:10%; padding-bottom:0; background:; display:inline; padding:5 10 5 10; opacity:0.7; filter:alpha(opacity=70);' align='right'>" +
	    		"<a href='javascript:scrollToTop()' style='font-size:18px; font-family:Arial, Helvetica, sans-serif; font-weight:bold'><u>Go to Top</u></a>" +
	    "</div>" +
	                "<div align='center' width='100%' style='left:0%; right:0%; top:0%; background:#F5F5F5; font-family: Arial, Helvetica, sans-serif; height:30; font-weight:normal; padding-top: 7px; margin-bottom:35; font-size:16px'>" + title + "</div>\n");
	    String cafe_name_pattern = "\"cafe_name\"\\s:\\s\"[a-zA-Z0-9\\s]+\"";
	    String cafe_category_pattern = "\"cafe_category\"\\s:\\s\"[a-zA-Z0-9\\s]+\"";
		String cafe_url_pattern = "\"cafe_url\"\\s:\\s\"http://www.yelp.com/biz/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\"";
		String cafe_img_url_pattern = "\"cafe_imgURL\"\\s:\\s\"http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|.]\"";
		String cafe_address_pattern = "\"cafe_address\"\\s:\\s\"[a-zA-Z0-9\\s,]+\"";
		String cafe_phone_pattern = "\"cafe_bizphone\"\\s:\\s\"[a-zA-Z0-9\\s-()]+\"";
		Pattern p = Pattern.compile(cafe_name_pattern+"|"+cafe_category_pattern+"|"+cafe_url_pattern+"|"+cafe_img_url_pattern+"|"+cafe_address_pattern+"|"+cafe_phone_pattern);
		  
		int i = 0;
	    while(i==0) {
	      if (paramValues.length == 1) {
	    	 // String paramValue = paramValues[0];
	    	  if (paramValue.length() == 0)
	    		  out.print("<I>No Result</I>");
	    	  else{
	    		  Mongo mongo = new Mongo("localhost", 27017);
	    		  DB db = mongo.getDB("yelp");
	    		  DBCollection collection = db.getCollection("yelpCollection");
	    		  Result rst = YelpGate.getInstance().process(paramValue+"?");
	    		  
	    		  if(rst.type==-1) {
	    			  out.println("There is no result for your input...");
	    			  break;
	    		  }else if(rst.type==0) {
	    			  BasicDBObject query = rst.query;
	    			  DBCursor cursor = collection.find(query);
	    			  out.print("<table width='700' height=20 style='background:#FAF8CC; border-top:1px dashed #999999; border-bottom:0px solid #DDDDDD; margin-bottom:15'><tr><td width=250 style='Padding-top:3; padding-bottom:3; padding-left:10' align='left'>" +
	    		"<a href='index.html'>+ Start New Search</a></td><td width=450 align='right' style='padding-right:10'>Totally "+cursor.count()+" found.</td></tr></table>");
		    		  
		    		  try{
			    		  while(cursor.hasNext()) {
		    				  DBObject tobj = cursor.next();
			    			  String output_rating = (tobj.get("cafe_rating").equals("")) ? "" : 
		    					  ("<img src='" + tobj.get("cafe_ratingImgURL") +"'><br>");
			    			  
			    			  Matcher matcher = p.matcher(tobj.toString());
			    			  String output_cafe_name = "";
			    			  String output_cafe_category = "";
			    			  String output_cafe_url = "";
			    			  String output_cafe_img_url = "";
			    			  String output_cafe_address = "";
			    			  String output_cafe_phone = "";
			    			  
			    			  while(matcher.find()){
			    				  if(matcher.group().matches(cafe_name_pattern)){
			    					  String cafe_name = matcher.group().split(":\\s\"")[1];
			    					  output_cafe_name = "<strong><font color=#336633 style='font-size:18px'>" + cafe_name.substring(0, cafe_name.length()-1) + "</font></strong>&nbsp; ";
			    				  }else if(matcher.group().matches(cafe_category_pattern)){
			    					  String cafe_category = matcher.group().split(":\\s\"")[1];
			    					  output_cafe_category += cafe_category.substring(0, cafe_category.length()-1) + " food<br>";
			    				  }else if(matcher.group().matches(cafe_url_pattern)){
			    					  String cafe_url = matcher.group().split(":\\s\"")[1];
			    					  output_cafe_url += "<a href="+cafe_url.substring(0, cafe_url.length()-1)+" target='_blank'>"+cafe_url.substring(0, cafe_url.length()-1) + "</a><br>";
			    				  }else if(matcher.group().matches(cafe_img_url_pattern)){
			    					  String cafe_img_url = matcher.group().split(":\\s\"")[1];
			    					  output_cafe_img_url += "<img src="+cafe_img_url.substring(0, cafe_img_url.length()-1)+" />";
			    				  }else if(matcher.group().matches(cafe_address_pattern)){
			    					  String cafe_address = matcher.group().split(":\\s\"")[1];
			    					  output_cafe_address += cafe_address.substring(0, cafe_address.length()-1) + "<br>";
			    				  }else if(matcher.group().matches(cafe_phone_pattern)){
			    					  String cafe_phone = matcher.group().split(":\\s\"")[1];
			    					  output_cafe_phone += cafe_phone.substring(0, cafe_phone.length()-1) + "<br>";
			    				  }
			    			  }
			    			  out.print("<table width='700' align='center' style='margin-bottom:5px'><tr><td width='100' style='' valign='top'>"+output_cafe_img_url+"<td valign='top' align='left' style='font-family: Arial, Helvetica, sans-serif; padding-left:15px; font-size:13px; line-height:150%'>"+output_cafe_name+output_rating+output_cafe_url+output_cafe_category+output_cafe_address+output_cafe_phone+"</td></tr></table>");
			    			  i++;
			    		  }
		    		  }finally{
		    			  cursor.close();
		    		  }
		    		  
		    		  if(i==0) out.println("No result found");
	    			  break;
	    		  }else if(rst.type==1 || rst.type==2) {
	    			  BasicDBObject query = rst.query;
	    			  BasicDBObject query_cafeName = rst.name;
	    			  String[] highlight = rst.highlight.split(", ");
	    			  System.out.println(rst.query.toString()+"==="+rst.name.toString());
	    			  DBCursor cursor = collection.find(query);
	    			  if(rst.type==1) {
	    			  if(cursor.count()==0)
	    				  out.println("<table width='700' height=20 style='background:#FAF8CC; border-top:1px dashed #999999; border-bottom:1px solid #DDDDDD; margin-bottom:15'><tr><td style='Padding-top:3; padding-bottom:3; padding-left:10' align='left'>" +
	    						  	"The answer to your question is: &nbsp; <font style='font-size:18px; color:#B92828; font-weight:bold'><em>No</em></font>" +
		    			    	  	"</td></tr></table>");
	    			  else
	    				  out.println("<table width='700' height=20 style='background:#FAF8CC; border-top:1px dashed #999999; border-bottom:1px solid #DDDDDD; margin-bottom:15'><tr><td style='Padding-top:3; padding-bottom:3; padding-left:10' align='left'>" +
	    						  	"The answer to your question is: &nbsp; <font style='font-size:18px; color:#336633; font-weight:bold'><em>Yes</em></font>" +
		    			    	  	"</td></tr></table>");
	    			  }
	    			  
	    			  DBCursor cafeName_cursor = collection.find(query_cafeName);
	    			  
	    			  try{
	    				  DBObject tobj = cafeName_cursor.next();
		    			  Matcher matcher = p.matcher(tobj.toString());
		    			  String output_cafe_name = "";
		    			  String output_cafe_category = "";
		    			  String output_cafe_url = "";
		    			  String output_cafe_img_url = "";
		    			  String output_cafe_address = "";
		    			  String output_cafe_phone = "";
		    			  
		    			  String output_goodforKids = (tobj.get("cafe_goodforkids").equals("")) ? "" : 
		    				  ("Good for Kids: " + tobj.get("cafe_goodforkids") +"<br>");
		    			  String output_AcceptsCreditCards = (tobj.get("cafe_creditcards").equals("")) ? "" :
		    				  ("Accpet credit card? " + tobj.get("cafe_creditcards") +"<br>");
			  			  String output_Parking = (tobj.get("cafe_parking").equals("")) ? "" : 
	    					  ("Business parking? " + tobj.get("cafe_parking") +"<br>");
			  			  String output_restaurantsAttire = (tobj.get("cafe_restaurantsattire").equals("")) ? "" : 
	    					  ("Restaurant attires: " + tobj.get("cafe_restaurantsattire") +"<br>");
			  			  String output_businessGoodforGroups = (tobj.get("cafe_goodforgroups").equals("")) ? "" : 
	    					  ("Good for groups? " + tobj.get("cafe_goodforgroups") +"<br>");
			  			  String output_reservation = (tobj.get("cafe_reservation").equals("")) ? "" : 
	    					  ("Accept reservation? " + tobj.get("cafe_reservation") +"<br>");
			  			  String output_delivery = (tobj.get("cafe_delivery").equals("")) ? "" : 
	    					  ("Delivery? " + tobj.get("cafe_delivery") +"<br>");
			  			  String output_TakeOut = (tobj.get("cafe_takeout").equals("")) ? "" : 
	    					  ("Has takeout? " + tobj.get("cafe_takeout") +"<br>");
			  			  String output_tableService = (tobj.get("cafe_tableservice").equals("")) ? "" : 
	    					  ("Table service? " + tobj.get("cafe_tableservice") +"<br>");
			  			  String output_outdoorSeating = (tobj.get("cafe_outdoorseating").equals("")) ? "" : 
	    					  ("Has outdoor seating: " + tobj.get("cafe_outdoorseating") +"<br>");
			  			  String output_WiFi = (tobj.get("cafe_wifi").equals("")) ? "" : 
	    					  ("Has WiFi: " + tobj.get("cafe_wifi") +"<br>");
			  			  String output_goodforMeal = (tobj.get("cafe_goodformeal").equals("")) ? "" : 
	    					  ("Good for meal? " + tobj.get("cafe_goodformeal") +"<br>");
			  			  String output_alcohol = (tobj.get("cafe_alcohol").equals("")) ? "" : 
	    					  ("Alcohol? " + tobj.get("cafe_alcohol") +"<br>");
			  			  String output_noiseLevel = (tobj.get("cafe_noiselevel").equals("")) ? "" : 
	    					  ("Noise Level: " + tobj.get("cafe_noiselevel") +"<br>");
			  			  String output_ambience = (tobj.get("cafe_ambience").equals("")) ? "" : 
	    					  ("Ambience: " + tobj.get("cafe_ambience") +"<br>");
			  			  String output_hasTV = (tobj.get("cafe_hastv").equals("")) ? "" : 
	    					  ("Has TV? " + tobj.get("cafe_hastv") +"<br>");
			  			  String output_caters = (tobj.get("cafe_caters").equals("")) ? "" : 
	    					  ("Caters: " + tobj.get("cafe_caters") +"<br>");
			  			  String output_wheelchairAccessible = (tobj.get("cafe_wheelchairaccessible").equals("")) ? "" : 
	    					  ("Wheel chair accessible: " + tobj.get("cafe_wheelchairaccessible") +"<br>");
			  			  String output_neighborhood = (tobj.get("cafe_neighborhood").equals("")) ? "" : 
	    					  ("Neighorhood: " + tobj.get("cafe_neighborhood") +"<br>");
			  			  String output_rating = (tobj.get("cafe_rating").equals("")) ? "" : 
	    					  ("<img src='" + tobj.get("cafe_ratingImgURL") +"'><br>");
			  			  String output_price = (tobj.get("cafe_price").equals("")) ? "" : 
	    					  ("Price: " + tobj.get("cafe_price") +"<br>");
			  			  String output_hours = (tobj.get("cafe_hours").equals("")) ? "" : 
	    					  ("Open hours: " + tobj.get("cafe_hours") +"<br>");

		    			  if(Arrays.asList(highlight).contains("cafe_goodforkids")) 
		    				  output_goodforKids = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_goodforKids + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_creditcards")) 
		    				  output_AcceptsCreditCards = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_AcceptsCreditCards + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_Parking")) 
		    				  output_Parking = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_Parking + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_restaurantsattire")) 
		    				  output_restaurantsAttire = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_restaurantsAttire + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_goodforgroups")) 
		    				  output_businessGoodforGroups = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_businessGoodforGroups + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_reservation")) 
		    				  output_reservation = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_reservation + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_delivery")) 
		    				  output_delivery = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_delivery + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_tableservice")) 
		    				  output_tableService = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_tableService + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_takeout")) 
		    				  output_TakeOut = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_TakeOut + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_outdoorseating")) 
		    				  output_outdoorSeating = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_outdoorSeating + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_wifi")) 
		    				  output_WiFi = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_WiFi + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_goodformeal")) 
		    				  output_goodforMeal = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_goodforMeal + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_alcohol")) 
		    				  output_alcohol = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_alcohol + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_noiselevel")) 
		    				  output_noiseLevel = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_noiseLevel + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_ambience")) 
		    				  output_ambience = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_ambience + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_hastv")) 
		    				  output_hasTV = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_hasTV + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_caters")) 
		    				  output_caters = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_caters + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_wheelchairaccessible")) 
		    				  output_wheelchairAccessible = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_wheelchairAccessible + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_neighborhood")) 
		    				  output_neighborhood = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_neighborhood + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_rating")) 
		    				  output_rating = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_rating + "</span>";
		    			 // if(Arrays.asList(highlight).contains("cafe_ratingImgURL")) 
		    			//	  output_ratingImgURL = "<span style='background:#EEEEEE; font-weight:bold'>"
		    			//			  			    + output_goodforKids + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_price")) 
		    				  output_goodforKids = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_goodforKids + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_hours")) 
		    				  output_hours = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_hours + "</span>";
		    			  if(Arrays.asList(highlight).contains("cafe_address")) 
		    				  output_cafe_address = "<span style='background:#EEEEEE; font-weight:bold'>"
		    						  			    + output_cafe_address + "</span>";
			  			  
		    			  while(matcher.find()){
		    				  if(matcher.group().matches(cafe_name_pattern)){
		    					  String cafe_name = matcher.group().split(":\\s\"")[1];
		    					  output_cafe_name = "<div style='margin-bottom:7px'><strong><font color=#336633 style='font-size:18px'>" + cafe_name.substring(0, cafe_name.length()-1) + "</font></strong></div>";
		    				  }else if(matcher.group().matches(cafe_category_pattern)){
		    					  String cafe_category = matcher.group().split(":\\s\"")[1];
		    					  output_cafe_category += "Category: " + cafe_category.substring(0, cafe_category.length()-1) + " food<br>";
		    				  }else if(matcher.group().matches(cafe_url_pattern)){
		    					  String cafe_url = matcher.group().split(":\\s\"")[1];
		    					  output_cafe_url += "<a href="+cafe_url.substring(0, cafe_url.length()-1)+" target='_blank'>"+cafe_url.substring(0, cafe_url.length()-1) + "</a><br>";
		    				  }else if(matcher.group().matches(cafe_img_url_pattern)){
		    					  String cafe_img_url = matcher.group().split(":\\s\"")[1];
		    					  output_cafe_img_url += "<img src="+cafe_img_url.substring(0, cafe_img_url.length()-1)+" />";
		    				  }else if(matcher.group().matches(cafe_address_pattern)){
		    					  String cafe_address = matcher.group().split(":\\s\"")[1];
		    					  output_cafe_address += cafe_address.substring(0, cafe_address.length()-1) + "<br>";
		    				  }else if(matcher.group().matches(cafe_phone_pattern)){
		    					  String cafe_phone = matcher.group().split(":\\s\"")[1];
		    					  output_cafe_phone += cafe_phone.substring(0, cafe_phone.length()-1) + "<br>";
		    				  }
		    			  }
		    			  out.print("<table width='700' align='center' style='margin-bottom:5px'><tr><td width='100' style='' valign='top'>"+output_cafe_img_url+"<td valign='top' align='left' style='font-family: Arial, Helvetica, sans-serif; padding-left:15px; font-size:13px; line-height:160%'>"+
		    					  output_cafe_name+ 
		    					  output_rating + 
		    					  output_cafe_url+
		    					  output_cafe_category+
		    					  output_cafe_address+
		    					  output_cafe_phone+
		    					  output_goodforKids + 
		    					  output_AcceptsCreditCards + 
		    					  output_Parking + 
		    					  output_restaurantsAttire + 
		    					  output_businessGoodforGroups + 
		    					  output_reservation + 
		    					  output_delivery + 
		    					  output_TakeOut + 
		    					  output_tableService + 
		    					  output_outdoorSeating + 
		    					  output_WiFi + 
		    					  output_goodforMeal + 
		    					  output_alcohol + 
		    					  output_noiseLevel + 
		    					  output_ambience + 
		    					  output_hasTV + 
		    					  output_caters + 
		    					  output_wheelchairAccessible +  
		    					  output_neighborhood +
		    					  output_price + 
		    					  output_hours + 
		    					  "</td></tr></table>");
		    			  i++;
	    			  }finally{
	    				  cafeName_cursor.close();
	    			  }
	    		  }else if(rst.type==2) {
	    			  //query = rst.query;
	    			  break;
	    		  }
	    	   }
	       }else{
	    	   out.println("<UL>");
	    	   for(int j=0; j<paramValues.length; j++) {
	    		   out.println("<LI>" + paramValues[j]);
	    	   }
	    	   out.println("</UL>");
	       }
	    }
	    out.println("<table width='700' height=30 style='background:; border-top:1px dashed #999999; border-bottom:0px solid #DDDDDD; margin-top:25px'><tr><td width=250 style='Padding-top:10; padding-left:10' align='left'>" +
	    		"<a href='index.html'>+ Start New Search</a></td><td width=450 align='right' style='padding-right:10; padding-top:10'>Totally "+i+" found.</td></tr></table></center></BODY></HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
