package udacity.storm;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;


public class MongoDBNearest {

  public DBCollection collection;
  public Mongo mongo;
  public DB db;
  public MongoDBNearest(){
    mongo = new Mongo("localhost", 27017);
    db = mongo.getDB("stormDB");
    collection = db.getCollection("countries");
  }

  public String getNearestCountry(Double lon, Double lat){
    try{
        //db.countries.findOne({"loc" : {"$nearSphere" : [30, 28]}})
        BasicDBObject inQuery = new BasicDBObject();
        List<Double> list = new ArrayList<Double>();
        list.add(lon);
        list.add(lat);
        inQuery.put("loc", new BasicDBObject("$nearSphere", list));
        DBObject obj = collection.findOne(inQuery);
        return (String) obj.get("name");
    } catch (MongoException e) {
      return null;
    }
  }
}
