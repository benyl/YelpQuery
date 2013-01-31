package yelpMongo;

import java.net.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.*;

public class YelpAPI {

	  OAuthService service;
	  Token accessToken;

	  /**
	   * Setup the Yelp API OAuth credentials.
	   *
	   * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
	   *
	   * @param consumerKey Consumer key
	   * @param consumerSecret Consumer secret
	   * @param token Token
	   * @param tokenSecret Token secret
	   */
	  public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
	    this.service = new ServiceBuilder().provider(YelpApi2.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
	    this.accessToken = new Token(token, tokenSecret);
	  }

	  /**
	   * Search with term and location.
	   *
	   * @param term Search term
	   * @param latitude Latitude
	   * @param longitude Longitude
	   * @return JSON string response
	   */
	  //public String search(String term, double latitude, double longitude) {
	  public String search(String term, String loc, String offSet) { 	 	
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
	    request.addQuerystringParameter("term", term);
	    //request.addQuerystringParameter("ll", latitude + "," + longitude);
	    request.addQuerystringParameter("location", loc);
	    request.addQuerystringParameter("sort", "1");
	    request.addQuerystringParameter("offset", offSet);
	    this.service.signRequest(this.accessToken, request);
	    Response response = request.send();
	    return response.getBody();
	  }

	  // CLI
	  public static void main(String[] args) {
		// Update tokens here from Yelp developers site, Manage API access.
		String consumerKey = "U24kxkAqhb7z4lPj0m3qlw";
		String consumerSecret = "tjqYVDWl7G-z8MdhFBlaSTaW8EM";
		String token = "TgFmB7BMtlsM-tWUuPx_9jU1HuPVsg-t";
		String tokenSecret = "ch_GBMzfc-jby1HgrATcZnKontI";
		String[] foodType = {
		//"Afghan",
		//"African",
		//"American",
		//"New American",
		//"Traditional American",
		//"Arabian",
		//"Argentine",
		//"Asian Fusion",
		//"Australian",
		//"Austrian",
		//"Bangladeshi",
		//"Barbeque",
		//"Basque",
		//"Belgian",
		//"Brasseries",
		//"Brazilian",
		//"Breakfast & Brunch",
		//"British",
		//"Buffets",
		//"Burgers",
		//"Burmese",
		//"Cafes",
		//"Cajun/Creole",
		//"Cambodian",
		//"Caribbean",
		//"Catalan",
		//"Cheesesteaks",
		//"Chicken Wings",
		//"Chinese",
		//"Comfort Food",
		//"Creperies",
		//"Cuban",
		//"Czech/Slovakian",
		//"Delis",
		//"Diners",
		//"Ethiopian",
		//"Fast Food",
		//"Filipino",
		//"Fish & Chips",
		//"Fondue",
		//"Food Court",
		//"Food Stands",
		//"French",
		//"Gastropubs",
		//"German",
		//"Gluten-Free",
		//"Greek",
		"Halal",
		"Hawaiian",
		"Himalayan/Nepalese",
		"Hot Dogs",
		"Hot Pot",
		"Hungarian",
		"Iberian",
		"Indian",
		"Indonesian",
		"Irish",
		"Italian",
		"Japanese",
		"Korean",
		"Kosher",
		"Laotian",
		"Latin American",
		"Live/Raw Food",
		"Malaysian",
		"Mediterranean",
		"Mexican",
		"Middle Eastern",
		"Modern European",
		"Mongolian",
		"Moroccan",
		"Pakistani",
		"Persian/Iranian",
		"Peruvian",
		"Pizza",
		"Polish",
		"Portuguese",
		"Russian",
		"Salad",
		"Sandwiches",
		"Scandinavian",
		"Scottish",
		"Seafood",
		"Singaporean",
		"Soul Food",
		"Soup",
		"Southern",
		"Spanish",
		"Steakhouses",
		"Sushi Bars",
		"Taiwanese",
		"Tapas Bars",
		"Tapas/Small Plates",
		"Tex-Mex",
		"Thai",
		"Turkish",
		"Ukrainian",
		"Vegan",
		"Vegetarian",
		"Vietnamese"};
		
	    YelpAPI yelp = new YelpAPI(consumerKey, consumerSecret, token, tokenSecret);
	      
	    for(int i=0; i<foodType.length; i++){
			int offSet = 0;
			
	    	while(offSet<1){
	    		final String foodCategory = foodType[i];
				String offSet_Val = "0";
		    	String response = yelp.search(foodCategory, "Brooklyn", offSet_Val);
			    //System.out.println(response);
			    
		    	offSet++;
			    offSet_Val = "" + offSet * 20;
			    
			    String cafe_name_pattern = "\"name\":\\s\"[[a-zA-Z0-9'&\\s]+\\s*]+\"";
			    String cafe_url_pattern = "\\s\"url\":\\s\"http://www.yelp.com/biz/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\"";
			    String cafe_imgURL_pattern = "\\s\"image_url\":\\s\"http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\"";
			    String cafe_rating_pattern = "\"rating\":\\s[0-9.,]+";
			    String cafe_ratingImgURL_pattern = "\\s\"rating_img_url\":\\s\"http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\"";
			    String cafe_price_pattern = "61\">[\\$$]+</span>";
			    String cafe_hours_pattern = "<p class=\"hours\">[-a-zA-Z0-9\\s:]+</p>";
			    
			    String cafe_goodforKids_pattern = "<dd class=\"attr-GoodForKids\">[a-zA-Z0-9]+<br>";
			    String cafe_businessAcceptsCreditCards_pattern = "<dd class=\"attr-BusinessAcceptsCreditCards\">[a-zA-Z0-9]+<br>";
			    String cafe_businessParking_pattern = "<dd class=\"attr-BusinessParking\">[a-zA-Z0-9]+<br>";
			    String cafe_restaurantsAttire_pattern = "<dd class=\"attr-RestaurantsAttire\">[a-zA-Z0-9]+<br>";
			    String cafe_businessGoodforGroups_pattern = "<dd class=\"attr-RestaurantsGoodForGroups\">[a-zA-Z0-9]+<br>";
			    String cafe_reservation_pattern = "<dd class=\"attr-RestaurantsReservations\">[a-zA-Z0-9]+<br>";
			    String cafe_delivery_pattern = "<dd class=\"attr-RestaurantsDelivery\">[a-zA-Z0-9]+<br>";
			    String cafe_TakeOut_pattern = "<dd class=\"attr-RestaurantsTakeOut\">[a-zA-Z0-9]+<br>";
			    String cafe_tableService_pattern = "<dd class=\"attr-RestaurantsTableService\">[a-zA-Z0-9]+<br>";
			    String cafe_outdoorSeating_pattern = "<dd class=\"attr-OutdoorSeating\">[a-zA-Z0-9]+<br>";
			    String cafe_WiFi_pattern = "<dd class=\"attr-WiFi\">[a-zA-Z0-9]+<br>";
			    String cafe_goodforMeal_pattern = "<dd class=\"attr-GoodForMeal\">[a-zA-Z0-9]+<br>";
			    String cafe_alcohol_pattern = "<dd class=\"attr-Alcohol\">[a-zA-Z0-9]+<br>";
			    String cafe_noiseLevel_pattern = "<dd class=\"attr-NoiseLevel\">[a-zA-Z0-9]+<br>";
			    String cafe_ambience_pattern = "<dd class=\"attr-Ambience\">[a-zA-Z0-9]+<br>";
			    String cafe_hasTV_pattern = "<dd class=\"attr-HasTV\">[a-zA-Z0-9]+<br>";
			    String cafe_caters_pattern = "<dd class=\"attr-Caters\">[a-zA-Z0-9]+<br>";
			    String cafe_wheelchairAccessible_pattern = "<dd class=\"attr-WheelchairAccessible\">[a-zA-Z0-9]+<br>";
			    String cafe_address_pattern = "<span itemprop=\"streetAddress\">[a-zA-Z0-9\\s]+</span>";
			    String cafe_addressLocality_pattern = "<span itemprop=\"addressLocality\">[a-zA-Z0-9\\s]+</span>";
			    String cafe_addressRegion_pattern = "<span itemprop=\"addressRegion\">[a-zA-Z0-9\\s]+</span>";
			    String cafe_addressPostalCode_pattern = "<span itemprop=\"postalCode\">[0-9]+</span>";
			    String cafe_bizphone_pattern = "<span id=\"bizPhone\" itemprop=\"telephone\">[a-zA-Z0-9\\s-()]+</span>";
			    String cafe_neighborhood_pattern = "Neighborhood:\\s[a-zA-Z]+<br>";
			    String method_cafe_pattern = cafe_goodforKids_pattern +"|" + cafe_businessAcceptsCreditCards_pattern 
			    		+ "|" + cafe_businessParking_pattern + "|" + cafe_restaurantsAttire_pattern
			    		+ "|" + cafe_businessGoodforGroups_pattern + "|" + cafe_reservation_pattern
			    		+ "|" + cafe_delivery_pattern + "|" + cafe_TakeOut_pattern
			    		+ "|" + cafe_tableService_pattern + "|" + cafe_outdoorSeating_pattern
			    		+ "|" + cafe_WiFi_pattern + "|" + cafe_goodforMeal_pattern
			    		+ "|" + cafe_alcohol_pattern + "|" + cafe_noiseLevel_pattern
			    		+ "|" + cafe_ambience_pattern + "|" + cafe_hasTV_pattern
			    		+ "|" + cafe_caters_pattern + "|" + cafe_wheelchairAccessible_pattern
			    		+ "|" + cafe_address_pattern + "|" + cafe_addressLocality_pattern
	    				+ "|" + cafe_addressRegion_pattern + "|" + cafe_addressPostalCode_pattern
	    				+ "|" + cafe_bizphone_pattern + "|" + cafe_neighborhood_pattern
	    				+ "|" + cafe_price_pattern + "|" + cafe_hours_pattern;
			    
			    Pattern p = Pattern.compile(cafe_name_pattern+"|"+cafe_url_pattern+"|"+cafe_imgURL_pattern+"|"+cafe_rating_pattern+"|"+cafe_ratingImgURL_pattern);
			    Matcher matcher = p.matcher(response);
		
			    String restaurant_name="";
			    String restaurant_url="";
			    String restaurant_img_URL = "";
			    String restaurant_rating = "";
			    String restaurant_rating_imgURL = "";
			    
			    String cafeDetails = "";
			    cafeInfo ci = new cafeInfo();
				
			    while(matcher.find()){
			    	if(matcher.group().matches(cafe_url_pattern)){
			    		restaurant_url = matcher.group().replace("\"", "").split("url: ")[1];
			    		ci.putCafeURL(restaurant_url);
			    		ci.putCategory(foodCategory);  
			    		System.out.printf("%s%n", restaurant_url);
	    	    		
			    		try{
			  			  URL yelp_url = new URL(restaurant_url);
			  			  URLConnection yc = yelp_url.openConnection();
			  			  BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			  			  String inputLine;
			  			  while((inputLine=in.readLine())!=null)
			  				  cafeDetails += inputLine;
			  			  in.close();
			    		}catch(Exception ex){
			    			ex.getMessage();
			  		  	}
			    		
			    		//BasicDBObject cafeDetailObj = new BasicDBObject();
			    		Pattern p_cafeDetail = Pattern.compile(method_cafe_pattern);
			    	    Matcher matcher_cafeDetail = p_cafeDetail.matcher(cafeDetails);
			    	    
			    	    String address_str = "";
			    	    
			    	    while(matcher_cafeDetail.find()){
			    	    	if(matcher_cafeDetail.group().toLowerCase().contains(cafe_goodforKids_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putGoodforKids(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_businessAcceptsCreditCards_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putCreditCards(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_businessParking_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putParking(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_restaurantsAttire_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putAttire(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_businessGoodforGroups_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putGoodForGroups(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_reservation_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putReservation(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_delivery_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putDelivery(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_TakeOut_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putTakeOut(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_tableService_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putAttire(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_outdoorSeating_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putOutdoorSeating(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_WiFi_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putWiFi(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_goodforMeal_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putGoodForMeal(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_alcohol_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putAlcohol(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_noiseLevel_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putNoiseLevel(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_ambience_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putAmbience(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_hasTV_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putHasTV(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_caters_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putCaters(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_wheelchairAccessible_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putWheelchairAccessible(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_bizphone_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putBizPhone(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_neighborhood_pattern.toLowerCase().split(":")[0]))
			    	    		ci.putNeighorhood(matcher_cafeDetail.group().split(":")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_hours_pattern.toLowerCase().split(">")[0]))
			    	    		ci.putHours(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_price_pattern.toLowerCase().split(">")[0])){
			    	    		ci.putPrice(matcher_cafeDetail.group().split(">")[1].split("<")[0]);
			    	    	}else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_address_pattern.toLowerCase().split(">")[0])){
			    	    		address_str = matcher_cafeDetail.group().split(">")[1].split("<")[0] + ", ";
			    	    	}else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_addressLocality_pattern.toLowerCase().split(">")[0])){
			    	    		address_str += matcher_cafeDetail.group().split(">")[1].split("<")[0] + ", ";
			    	    	}else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_addressRegion_pattern.toLowerCase().split(">")[0])){
			    	    		address_str += matcher_cafeDetail.group().split(">")[1].split("<")[0] + " ";
			    			}else if(matcher_cafeDetail.group().toLowerCase().contains(cafe_addressPostalCode_pattern.toLowerCase().split(">")[0])){
			    	    		address_str += matcher_cafeDetail.group().split(">")[1].split("<")[0];
			    	    		ci.putAddress(address_str);
			    	    	}
			    	    }
			    	}else if(matcher.group().matches(cafe_name_pattern)){
			    		restaurant_name = matcher.group().replace("\"", "").split("name: ")[1];
			    		ci.putCafeName(restaurant_name);
			    		System.out.printf("%s%n", restaurant_name);  
			    	}else if(matcher.group().matches(cafe_imgURL_pattern)){
			    		//System.out.printf("okkkkk==="+restaurant_name+"===="+matcher.group().replace("\"", ""));
			    		restaurant_img_URL = matcher.group().replace("\"", "").split("image_url: ")[1];
			    		ci.putImgURL(restaurant_img_URL);
			    		restaurant_img_URL = "";
			    		System.out.printf("%s%n", restaurant_img_URL);  

			    		// insert Cafe info into MongoDB
			    		insert_MongoDB(ci);
			    	}else if(matcher.group().matches(cafe_rating_pattern)){
			    		String rating = matcher.group().replace("\"", "").split("rating: ")[1];
			    		restaurant_rating = rating.substring(0, rating.length()-1);
			    		ci.putRating(restaurant_rating);
			    		System.out.printf("%s%n", restaurant_rating);  
			    	}else if(matcher.group().matches(cafe_ratingImgURL_pattern)){
			    		restaurant_rating_imgURL = matcher.group().replace("\"", "").split("rating_img_url: ")[1];
			    		ci.putRatingImgURL(restaurant_rating_imgURL);
			    		System.out.printf("%s%n", restaurant_rating_imgURL);  
			    	}
			     }
		      }
	       }
	    }
	  
	  public static boolean insert_MongoDB(cafeInfo cafeinfo){
		  try{
				Mongo mongo = new Mongo("localhost", 27017);
				
				DB db = mongo.getDB("yelp");
				DBCollection collection = db.getCollection("yelpCollection");
				
				//DBObject obj = new BasicDBObject().append("cafeDetails", cafeDetailObj);
				DBObject obj = new BasicDBObject();
				obj.put("cafe_name", cafeinfo.getCafeName());
				obj.put("cafe_url", cafeinfo.getCafeURL());
				obj.put("cafe_category", cafeinfo.getCategory());
				obj.put("cafe_price", cafeinfo.getPrice());
				obj.put("cafe_hours", cafeinfo.getHours());
				obj.put("cafe_rating", cafeinfo.getRating());
				obj.put("cafe_ratingImgURL", cafeinfo.getRatingImgURL());
				obj.put("cafe_imgURL", cafeinfo.getImgURL());
				obj.put("cafe_goodforkids", cafeinfo.getGoodforKids());
				obj.put("cafe_goodformeal", cafeinfo.getGoodForMeal());
				obj.put("cafe_alcohol", cafeinfo.getAlcohol());
				obj.put("cafe_ambience", cafeinfo.getAmbience());
				obj.put("cafe_creditcards", cafeinfo.getCreditCards());
				obj.put("cafe_goodforgroups", cafeinfo.getGoodForGroups());
				obj.put("cafe_parking", cafeinfo.getParking());
				obj.put("cafe_caters", cafeinfo.getCaters());
				obj.put("cafe_delivery", cafeinfo.getDelivery());
				obj.put("cafe_hastv", cafeinfo.getHasTV());
				obj.put("cafe_outdoorseating", cafeinfo.getOutdoorSeating());
				obj.put("cafe_reservation", cafeinfo.getReservation());
				obj.put("cafe_restaurantsattire", cafeinfo.getAttire());
				obj.put("cafe_tableservice", cafeinfo.getTableService());
				obj.put("cafe_takeout", cafeinfo.getTakeOut());
				obj.put("cafe_wifi", cafeinfo.getWiFi());
				obj.put("cafe_noiselevel", cafeinfo.getNoiseLevel());
				obj.put("cafe_wheelchairaccessible", cafeinfo.getWheelchairAccessible());
				obj.put("cafe_address", cafeinfo.getAddress());
				obj.put("cafe_bizphone", cafeinfo.getBizPhone());
				obj.put("cafe_neighborhood", cafeinfo.getNeighborhood());
				collection.insert(obj);
			} catch (Exception ex){
				ex.printStackTrace();
			}
			return true;
	  }
}