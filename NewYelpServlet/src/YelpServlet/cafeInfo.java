package YelpServlet;

public class cafeInfo {
	private String cafe_name="";
	private String cafe_url="";
	private String cafe_category="";
	private String cafe_price="";
	//private String cafe_rate="";
	private String cafe_img_url="";
	private double cafe_rating = 0.0;
	private String cafe_rating_img_url="";
	private String cafe_hours="";
	private String cafe_goodforKids = "";
	private String cafe_businessAcceptsCreditCards = "";
	private String cafe_businessParking = "";
	private String cafe_restaurantsAttire = "";
	private String cafe_businessGoodforGroups = "";
	private String cafe_reservation = "";
	private String cafe_delivery = "";
	private String cafe_TakeOut = "";
	private String cafe_tableService = "";
	private String cafe_outdoorSeating = "";
	private String cafe_WiFi = "";
	private String cafe_goodforMeal = "";
	private String cafe_alcohol = "";
	private String cafe_noiseLevel = "";
	private String cafe_ambience = "";
	private String cafe_hasTV = "";
	private String cafe_caters = "";
	private String cafe_wheelchairAccessible = "";
	private String cafe_address = "";
	private String cafe_neighborhood = "";
	private String cafe_bizphone = "";
	
	public String getCafeName(){
		return this.cafe_name;
	}
	
	public String getCafeURL(){
		return this.cafe_url;
	}
	
	public String getCategory(){
		return this.cafe_category;
	}
	
	public String getPrice(){
		return this.cafe_price;
	}

	public String getImgURL(){
		return this.cafe_img_url;
	}

	public double getRating(){
		return this.cafe_rating;
	}
	
	public String getRatingImgURL(){
		return this.cafe_rating_img_url;
	}
	
	public String getHours(){
		return this.cafe_hours;
	}
	
	public String getGoodforKids(){
		return this.cafe_goodforKids;
	}
	
	public String getCreditCards(){
		return this.cafe_businessAcceptsCreditCards;
	}	
	
	public String getParking(){
		return this.cafe_businessParking;
	}
	
	public String getAttire(){
		return this.cafe_restaurantsAttire;
	}	
	
	public String getGoodForGroups(){
		return this.cafe_businessGoodforGroups;
	}
	
	public String getReservation(){
		return this.cafe_reservation;
	}	
	
	public String getDelivery(){
		return this.cafe_delivery;
	}
	
	public String getTakeOut(){
		return this.cafe_TakeOut;
	}	
	
	public String getTableService(){
		return this.cafe_tableService;
	}
	
	public String getOutdoorSeating(){
		return this.cafe_outdoorSeating;
	}	
	
	public String getWiFi(){
		return this.cafe_WiFi;
	}
	
	public String getGoodForMeal(){
		return this.cafe_goodforMeal;
	}	
	
	public String getAlcohol(){
		return this.cafe_alcohol;
	}
	
	public String getNoiseLevel(){
		return this.cafe_noiseLevel;
	}	
	
	public String getAmbience(){
		return this.cafe_ambience;
	}
	
	public String getHasTV(){
		return this.cafe_hasTV;
	}
	
	public String getCaters(){
		return this.cafe_caters;
	}
	
	public String getWheelchairAccessible(){
		return this.cafe_wheelchairAccessible;
	}
	
	public String getAddress(){
		return this.cafe_address;
	}
	
	public String getNeighborhood(){
		return this.cafe_neighborhood;
	}
	
	public String getBizPhone(){
		return this.cafe_bizphone;
	}
	
	public boolean putCafeName(String cafe_name){
		this.cafe_name = cafe_name;
		return true;
	}
	
	public boolean putCafeURL(String cafe_url){
		this.cafe_url = cafe_url;
		return true;
	}

	public boolean putCategory(String cafe_category){
		this.cafe_category = cafe_category;
		return true;
	}
	
	public boolean putPrice(String cafe_price){
		this.cafe_price = cafe_price;
		return true;
	}

	public boolean putImgURL(String cafe_img_url){
		this.cafe_img_url = cafe_img_url;
		return true;
	}
	
	public boolean putRating(double cafe_rating){
		this.cafe_rating = cafe_rating;
		return true;
	}
	
	public boolean putRatingImgURL(String cafe_rating_img_url){
		this.cafe_rating_img_url = cafe_rating_img_url;
		return true;
	}
	
	public boolean putHours(String cafe_hours){
		this.cafe_hours = cafe_hours;
		return true;
	}
	
	public boolean putGoodforKids(String cafe_goodforKids){
		this.cafe_goodforKids = cafe_goodforKids;
		return true;
	}
	
	public boolean putCreditCards(String cafe_businessAcceptsCreditCards){
		this.cafe_businessAcceptsCreditCards = cafe_businessAcceptsCreditCards;
		return true;
	}	
	
	public boolean putParking(String cafe_businessParking){
		this.cafe_businessParking = cafe_businessParking;
		return true;
	}
	
	public boolean putAttire(String cafe_restaurantsAttire){
		this.cafe_restaurantsAttire = cafe_restaurantsAttire;
		return true;
	}	
	
	public boolean putGoodForGroups(String cafe_businessGoodforGroups){
		this.cafe_businessGoodforGroups = cafe_businessGoodforGroups;
		return true;
	}
	
	public boolean putReservation(String cafe_reservation){
		this.cafe_reservation = cafe_reservation;
		return true;
	}	
	
	public boolean putDelivery(String cafe_delivery){
		this.cafe_delivery = cafe_delivery;
		return true;
	}
	
	public boolean putTakeOut(String cafe_TakeOut){
		this.cafe_TakeOut = cafe_TakeOut;
		return true;
	}	
	
	public boolean putTableService(String cafe_tableService){
		this.cafe_tableService = cafe_tableService;
		return true;
	}
	
	public boolean putOutdoorSeating(String cafe_outdoorSeating){
		this.cafe_outdoorSeating = cafe_outdoorSeating;
		return true;
	}	
	
	public boolean putWiFi(String cafe_WiFi){
		this.cafe_WiFi= cafe_WiFi;
		return true;
	}
	
	public boolean putGoodForMeal(String cafe_goodforMeal){
		this.cafe_goodforMeal = cafe_goodforMeal;
		return true;
	}	
	
	public boolean putAlcohol(String cafe_alcohol){
		this.cafe_alcohol = cafe_alcohol;
		return true;
	}
	
	public boolean putNoiseLevel(String cafe_noiseLevel){
		cafe_noiseLevel = this.cafe_noiseLevel;
		return true;
	}	
	
	public boolean putAmbience(String cafe_ambience){
		cafe_ambience = this.cafe_ambience;
		return true;
	}
	
	public boolean putHasTV(String cafe_hasTV){
		this.cafe_hasTV = cafe_hasTV;
		return true;
	}
	
	public boolean putCaters(String cafe_caters){
		this.cafe_caters = cafe_caters;
		return true;
	}
	
	public boolean putWheelchairAccessible(String cafe_wheelchairAccessible){
		this.cafe_wheelchairAccessible = cafe_wheelchairAccessible;
		return true;
	}
	
	public boolean putAddress(String cafe_address){
		this.cafe_address = cafe_address;
		return true;
	}
	
	public boolean putNeighorhood(String cafe_neighborhood){
		this.cafe_neighborhood = cafe_neighborhood;
		return true;
	}
	
	public boolean putBizPhone(String cafe_bizphone){
		this.cafe_bizphone = cafe_bizphone;
		return true;
	}
 }
