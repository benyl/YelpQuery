package YelpServlet;

import com.mongodb.*;

public class Result {
	public int type;
	// -1 : invalid
	// 0 : search groups (i.e. Are there any good thai restaurant nearby that has free wifi?)
	// 1 : check a restaurant (i.e. Does Teresa's Restaurant take credit cards and have wifi?)
	// 2 : ask for information (i.e. What kind of food does Teresa's Restaurant serve?)
	public BasicDBObject name; // query to search the restaurant name  
	public BasicDBObject query; // query to search the features
	public String highlight; // features that needs to be highlighted
	
	public Result()
	{
		type = -1; // invalid result
		name = new BasicDBObject();
		query = new BasicDBObject();
		highlight = "";
	}
}
