package map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Consumer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TiledParser {

	private static ArrayList<Object> allObjects = new ArrayList<Object>();
	private static ArrayList<Object> collidable = new ArrayList<Object>();

	@SuppressWarnings("unchecked")
	public static void loadObjects(String path){

		StringBuilder text = new StringBuilder();
		try {
			Files.lines(Paths.get(path)).forEach(s -> text.append(s));
		} catch (IOException e) {
			System.out.println("Map does not contain info file");
			return;
		}

		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject)parser.parse(text.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JSONArray layers = (JSONArray)obj.get("layers");

		layers.forEach((Consumer<JSONObject>)t -> {

			switch((String)t.get("type")){
			case "objectgroup":
				
				JSONArray objects = (JSONArray)t.get("objects");

				if(!t.get("name").equals("Collide")){
					addObject(objects);
					break;
				}else{
					addCollidable(objects);
				}

				break;
			}
		});

	}

	@SuppressWarnings("unchecked")
	private static void addObject(JSONArray objects){
		objects.forEach((Consumer<JSONObject>)object -> {
			String name = (String) object.get("name");
			System.out.println(object.get("x"));
			long x = (long) object.get("x");
			long y = (long) object.get("y");
			long width = (long) object.get("width");
			long height = (long) object.get("height");
			boolean visible = (boolean) object.get("visible");

			Object newObject = new EngineObject(name,x,y,width,height,visible);

			newObject.setProperty((JSONObject)object.get("properties"));

			newObject.loadObject();

			allObjects.add(newObject);
		});
	}

	@SuppressWarnings("unchecked")
	private static void addCollidable(JSONArray objects){
		objects.forEach((Consumer<JSONObject>)object -> {
			String name = (String) object.get("name");
			System.out.println(object.get("x"));
			long x = (long) object.get("x");
			long y = (long) object.get("y");
			long width = (long) object.get("width");
			long height = (long) object.get("height");
			boolean visible = (boolean) object.get("visible");
			
			Object newObject = null;
			if(object.containsKey("polyline")){

				JSONArray polyline = (JSONArray)(object.get("polyline"));
				
				int xPoints[] = new int[polyline.size()];
				int yPoints[] = new int[polyline.size()];
				for(int i=0;i<polyline.size();i++){
					xPoints[i] = Math.toIntExact((long)((JSONObject)polyline.get(i)).get("x"));
					yPoints[i] = Math.toIntExact((long)((JSONObject)polyline.get(i)).get("y"));
				}
				newObject = new CollidableObject(name,x,y,xPoints,yPoints,visible);
			}else{
				newObject = new CollidableObject(name,x,y,width,height,visible);
			}
			newObject.setProperty((JSONObject)object.get("properties"));

			newObject.loadObject();

			collidable.add(newObject);
		});
	}

	public static ArrayList<Object> getObjects(){
		return allObjects;
	}

	public static ArrayList<Object> getCollidable(){
		return collidable;
	}
}
