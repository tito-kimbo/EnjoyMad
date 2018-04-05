package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import android.content.Context;
import android.location.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is responsible of deciding whether a Club is near enough to satisfy the client search filter or not.
 * 
 * @author Francisco Javier Blázquez Martínez
 */
public class LocationFilter implements Filter{
	
	private final static String API_KEY = "AIzaSyDCASxz1lerrq1zkYhhbO7FAKDrcmNx9xo";
	private final static double MILES_TO_KM = 1.60934;
	
	private float maxDistance;
	private double deviceLatitude;
	private double deviceLongitude;
	private Context myContext;
	
	public LocationFilter()	{
		//WARNING! Unimplemented, just to avoid error in FilterMapper
	}
	public LocationFilter(Context contexto)	{
		myContext = contexto;
	}
	public boolean filter(ClubPOJO c) 
	{	
		try {
			double distance = getNavigableDistance(c);	//Calcula la distancia al club
			return distance <= maxDistance;				//Realizamos la comprobación.
		}catch(NullPointerException nptr) {
			
		}catch(MalformedURLException url) {
			
		}catch(IOException io) {
			
		}catch(JSONException json) {
			
		}
		
		return false;
	}

	public Object clone(FilterPOJO fp) {
		
		return null;
	}
	
	
	/**
	 * Initializes {@link #deviceLatitude} and {@link #deviceLongitude} to the mobile device last known location.
	 * 
	 * @throws NullPointerException	In case the mobile phone has no location registers, so that it's not possible 
	 * to get the last and nearest location of the device.
	 */
	private void getCoordinatesDevice() //EN DESARROLLO!
	{
		//NEEDS A CONTEXT -> Create a main Singleton base for the App with its context
		
		//https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
		String context = Context.LOCATION_SERVICE;
		LocationManager lm = (LocationManager)myContext.getSystemService(context); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
	
		if(location == null)
			throw new NullPointerException("There is no last known location in this device.");
		
		deviceLongitude = location.getLongitude();
		deviceLatitude  = location.getLatitude();
		
		//Suponemos de momento que inicializa correctamente la latitud y longitud a la actual del dispositivo.
	}
	
	/**
	 * Method to get the distance from the device to a club.
	 * 
	 * @param club Club to get distance to.
	 * @return  Distance from the device to the club.
	 * @throws MalformedURLException Really strange case related to invalid GPS coordinates or abusive use 
	 * of the API_KEY.
	 * @throws IOException If the request to the URL fails during the connection process.
	 * @throws JSONException If it's not possible to parse the JSON section from the URL.
	 */
	private double getNavigableDistance(ClubPOJO club) throws IOException, JSONException
	{
		getCoordinatesDevice();
		
		/* -> Aquí se supone que se tienen ya las coordenadas GPS del dispositivo y del club.*/
		
		//https://stackoverflow.com/questions/11901831/how-to-get-json-object-from-http-request-in-java
		String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" +
							+ deviceLatitude     + "," + deviceLongitude     + "&destinations=" +
							+ club.getLatitude() + "," + club.getLongitude() + "&key=" + API_KEY;
		
		/*URL distanceRequest = new URL(requestURL);
		URLConnection connection = distanceRequest.openConnection();  
		connection.setDoOutput(true);  
		
		Scanner scanner = new Scanner(distanceRequest.openStream());
		String response = scanner.useDelimiter("\\Z").next();
 !! --> JSONObject json = Util.parseJson(response); 
		scanner.close();*/
		
		JSONObject json = JsonReader.readJsonFromUrl(requestURL);
		
		/* -> Auí se supone que se ha cargado correctamente el json generado al llamar a la página de arriba.*/
		
		String aux = (String) json.get("rows");
		String[] values = aux.split("[,:{}\\[\\]\\s\\t\n]");
		
		for(int i = 0; i < 10000; i++)
		{
			if(values[i].equals("distance"))
			{
				return MILES_TO_KM * Double.valueOf(values[i+2]).doubleValue();
			}
		}
		
		throw new IllegalStateException("Unable to get the distance from the JSON object.");
	}
	
	//https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	public static class JsonReader {
		//Copiado de un usuario de mucha reputación, NADA testeado.
		//Me preocupa lo marcado con /*!!*/
		  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  
		  
		  }

		  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream(); /*!! Usa URL no URLConnection !!*/
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
	}
	/*
	 * DUDAS PARA EDU Y OTROS:
	 * 
	 * -> Aclarar la forma de inicializar una variable de tipo LocationManager creando artificialmente un contexto.
	 * -> Antes de presentar versión definitiva repasar la corrección del inglés de los javadocs (no me fío de mí).
	 * -> Excepciones no controladas.
	 * -> Código chapuza por no saber manejar bien el formato JSON.
	 * 
	 * OTRAS OBSERVACIONES:
	 * 
	 * -> El Context myContext no se inicializa en ningún momento, es algo propio del móvil que queda pendiente.
	 *    (ver getCoordinatesDevice() y la inicialización de LocationManager lm).
	 * -> Suponemos en esta clase inicializadas correctamente la latitud y longitud del ClubPOJO (cosa que no se hace).
	 * -> Si quisieramos prescindir de la lat y long de los clubs habría que modificar String requestURL en el mth.
	 *    getNavigableDistance()
	 * 
	 */
	
}
