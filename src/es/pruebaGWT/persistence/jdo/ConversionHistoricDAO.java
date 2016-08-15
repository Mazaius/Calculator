package es.pruebaGWT.persistence.jdo;

import javax.jdo.PersistenceManager;

public class ConversionHistoricDAO {

	public void insert(ConversionHistoric conversionHistoric) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();

	    try {
	        pm.makePersistent(conversionHistoric);
	    } finally {
	        pm.close();
	    }
	}	

}
