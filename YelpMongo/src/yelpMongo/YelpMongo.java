package yelpMongo;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.mongodb.*;

public class YelpMongo {

	public YelpMongo(){
		
	}
	
	public static void main(String[] args){
		try{
			Mongo mongo = new Mongo("localhost", 27017);
			
			DB db = mongo.getDB("yelp");
			DBCollection collection = db.getCollection("yelp");
			
			DBCursor cur = collection.find();

			try{
				FileWriter fstream = new FileWriter("Restaurant_info.txt");
				BufferedWriter out = new BufferedWriter(fstream);
			
				while(cur.hasNext()){
					//one = cur.next().get("businesses.name");
					//System.out.println("Loop Value: " + cur.curr().get("businesses.name") + "\n");
					out.write(cur.curr().get("businesses.name") + "\n");
				}
				out.close();
			}catch (Exception e){//Catch exception if any
				  System.err.println("Error: " + e.getMessage());
			}
			
//			DBObject obj = new BasicDBObject();
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
