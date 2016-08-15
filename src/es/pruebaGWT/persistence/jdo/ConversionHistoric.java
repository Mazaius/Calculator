package es.pruebaGWT.persistence.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ConversionHistoric {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String numDec;
	
	@Persistent
	private String numBin;
	
	@Persistent
	private Date fecha;
	
	public ConversionHistoric(String numdec, String numbin, Date fecha) {
		this.numDec = numdec;
		this.numBin = numbin;
		this.fecha = fecha;
	}
	
	public String getNumDec() {
		return numDec;
	}
	public void setNumDec(String numDec) {
		this.numDec = numDec;
	}
	public String getNumBin() {
		return numBin;
	}
	public void setNumBin(String numBin) {
		this.numBin = numBin;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
