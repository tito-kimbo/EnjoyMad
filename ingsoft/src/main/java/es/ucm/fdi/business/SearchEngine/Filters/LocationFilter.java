package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import android.content.Context;
import android.location.*;
import org.json.simple.parser.*;

/**
 * This class is responsible of deciding whether a Club is near enough to satisfy the client search filter or not.
 * 
 * @author Francisco Javier Blázquez Martínez
 */
public class LocationFilter implements Filter{

	private double deviceLatitude;
	private double deviceLongitude;
	private Context myContext;
	
	public LocationFilter(Context contexto)	{
		myContext = contexto;
	}
	public boolean filter(ClubPOJO c) {	
		
		
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
	
	
	
	
	
	
	/*
	 * DUDAS PARA EDU Y OTROS:
	 * 
	 * -> Aclarar la forma de inicializar una variable de tipo LocationManager creando artificialmente un contexto.
	 * -> Antes de presentar versión definitiva repasar la corrección del inglés de los javadocs (no me fío de mí).
	 * 
	 * 
	 * 
	 * OTRAS OBSERVACIONES:
	 * 
	 * -> El Context myContext no se inicializa en ningún momento, es algo propio del móvil que queda pendiente.
	 *    (ver getCoordinatesDevice() y la inicialización de LocationManager lm).
	 * -> Suponemos en esta clase inicializadas correctamente la latitud y longitud del ClubPOJO (cosa que no se hace).
	 * 
	 * 
	 */
	
}
