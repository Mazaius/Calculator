package es.pruebaGWT.server;

import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.pruebaGWT.client.ConversionService;
import es.pruebaGWT.persistence.jdo.ConversionHistoric;
import es.pruebaGWT.persistence.jdo.ConversionHistoricDAO;

public class ConversionServiceImpl extends RemoteServiceServlet implements ConversionService {

	private static final long serialVersionUID = 7200382351777363686L;

	public String convertirABinario(String numDec) {
		
        String numBin = "";
        
        try {
        	// 1 - Se convierte el decimal de entrada en binario
            //
            // Debemos dividir n por 2 sucesivamente hasta que el resultado es 0.
            // La representación binaria resultante estará compuesto por los restos de
            // todas las divisiones, desde el último al primero.
        	int n = Integer.parseInt(numDec);

        	while (n != 0) {
        		int r = (int)(n % 2); // resto
        		numBin = r + numBin; // concatenar resto
        		n /= 2; // reduce n
        	}
        
        	// 2 - Almacenamos los valores de la conversion
        	ConversionHistoric conversionHistoric = new ConversionHistoric(numDec, numBin, new Date());
        	ConversionHistoricDAO conversionHistoricDAO = new ConversionHistoricDAO();
        	conversionHistoricDAO.insert(conversionHistoric);
        	
        } catch (Exception e) {
        	 e.printStackTrace();       	
        }
		return numBin;
	}
}