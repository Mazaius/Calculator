package es.pruebaGWT.client;

import java.math.BigDecimal;

import com.google.appengine.tools.util.Option.Style;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;

public class Calculator implements EntryPoint {

	// Constantes para los botones
	private static final String SIMBOLO_MAS = "+";
	private static final String SIMBOLO_MENOS = "-";
	private static final String SIMBOLO_POR = "x";
	private static final String SIMBOLO_DIV = "/";
	private static final String SIMBOLO_MASMENOS = "+/-";
	private static final String SIMBOLO_PORCENTAJE = "%";
	private static final String SIMBOLO_IGUAL = "=";
	private static final String SIMBOLO_DECBIN = "DECBIN";
	private static final String SIMBOLO_PUNTO = ".";
	
	private static final String NUM_CERO = "0";
	private static final String NUM_UNO = "1";
	private static final String NUM_DOS = "2";
	private static final String NUM_TRES = "3";
	private static final String NUM_CUATRO = "4";
	private static final String NUM_CINCO = "5";
	private static final String NUM_SEIS = "6";
	private static final String NUM_SIETE = "7";
	private static final String NUM_OCHO = "8";
	private static final String NUM_NUEVE = "9";
	
	private static final String ALTO_BOTON_SIMPLE = "50";
	private static final String ANCHO_BOTON_SIMPLE = "50";
	
	// Atributos globales
	private final TextField pantalla = new TextField();
	private final TextField subPantalla = new TextField();
	private String ultimaPulsacion = "";
	private String operandoTmp = "";
	private String operando1 = "";
	private String operando2 = "";
	private String operacion = "";
	private BigDecimal subTotal;	

	ConversionServiceAsync servicioDecToBin = (ConversionServiceAsync) GWT.create(ConversionService.class);	
	
	@Override
	public void onModuleLoad() {
		
		RootPanel rtp = RootPanel.get();
		rtp.setStyleName("panelFondo");
		
	    crearPantallasCalculadora(rtp);
	    
	    crearBotonesFila1(rtp);
	    crearBotonesFila2(rtp);
	    crearBotonesFila3(rtp);
	    crearBotonesFila4(rtp);
	    crearBotonesFila5(rtp);
		
	}

	/**
	 * Metodo que genera las dos pantallas de la caluculadora
	 * @param rtp
	 */
	private void crearPantallasCalculadora(RootPanel rtp) {
	    pantalla.setSize("270", "25");
	    pantalla.setReadOnly(true);
	    subPantalla.setSize("270", "25");
	    subPantalla.setReadOnly(true);
	    rtp.add(pantalla, 50, 80);
	    rtp.add(subPantalla, 50, 105);
	}

	/**
	 * Metodo que genera los botones de la primera fila de la calculadora
	 * @param rtp
	 */
	public void crearBotonesFila1(RootPanel rtp) {

		TextButton textButton51 = new TextButton("DEC --> BIN");
		textButton51.setSize("160", ANCHO_BOTON_SIMPLE);
		rtp.add(textButton51, 50, 140);

		
		TextButton textButton52 = new TextButton("CE");
		textButton52.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton52, 215, 140);

		TextButton textButton53 = new TextButton("C");
		textButton53.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton53, 270, 140);
		
		
		textButton51.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				ultimaPulsacion = SIMBOLO_DECBIN;
				
				pasarABinario(operandoTmp);
			}
		});		
		
		textButton52.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				pantalla.setText("");
				subPantalla.setText("");
				operandoTmp = "";
				operando1 = "";
				operando2 = "";
				operacion = "";
				subTotal = new BigDecimal(NUM_CERO);
			}
		});
			
		textButton53.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				pantalla.setText("");
				subPantalla.setText("");
				operandoTmp = "";
				operando1 = "";
				operando2 = "";
				operacion = "";
				subTotal = new BigDecimal(NUM_CERO);
			}
		});		
	}

	/**
	 * Metodo que genera los botones de la segunda fila de la calculadora
	 * @param rtp
	 */
	public void crearBotonesFila2(RootPanel rtp) {

		TextButton textButton41 = new TextButton(NUM_SIETE);
		textButton41.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton41, 50, 195);
		
		TextButton textButton42 = new TextButton(NUM_OCHO);
		textButton42.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton42, 105, 195);

		TextButton textButton43 = new TextButton(NUM_NUEVE);
		textButton43.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton43, 160, 195);
		
		TextButton textButton44 = new TextButton(SIMBOLO_DIV);
		textButton44.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton44, 215, 195);
		
		TextButton textButton45 = new TextButton(SIMBOLO_PORCENTAJE);
		textButton45.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton45, 270, 195);		
		

		textButton41.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}	
				
				ultimaPulsacion = NUM_SIETE;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
					
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
					
			}
		});
		
		textButton42.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}				
				
				ultimaPulsacion = NUM_OCHO;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});		
		
		textButton43.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}				
				
				ultimaPulsacion = NUM_NUEVE;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});
		
		textButton44.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}	
				
				ultimaPulsacion = SIMBOLO_DIV;
				
				if (!operandoTmp.equals("")) {
					operacion = ultimaPulsacion;
					operando1 = operandoTmp;
					operandoTmp = "";
					subPantalla.setText(operando1 + operacion);
					pantalla.setText("");
				}
			}
		});
		
		textButton45.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
			
				if (ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = SIMBOLO_PORCENTAJE;
				
				if (!operandoTmp.equals("")) {
					operando2 = operandoTmp;
	
					realizaOperacion(true);
	
					pantalla.setText(subTotal.toString());
					operandoTmp = subTotal.toString();
					subTotal = new BigDecimal("0");
					
					operando1 = "";
					operando2 = "";
				}
			}
		});
		
	}
	
	/**
	 * Metodo que genera los botones de la tercera fila de la calculadora
	 * @param rtp
	 */
	private void crearBotonesFila3(RootPanel rtp)  {
		TextButton textButton31 = new TextButton(NUM_CUATRO);
		textButton31.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton31, 50, 250);
		
		TextButton textButton32 = new TextButton(NUM_CINCO);
		textButton32.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton32, 105, 250);

		TextButton textButton33 = new TextButton(NUM_SEIS);
		textButton33.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton33, 160, 250);
		
		TextButton textButton34 = new TextButton(SIMBOLO_POR);
		textButton34.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton34, 215, 250);
		
		TextButton textButton35 = new TextButton(SIMBOLO_MASMENOS);
		textButton35.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton35, 270, 250);		
		

		textButton31.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}				
				
				ultimaPulsacion = NUM_CUATRO;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});
		
		textButton32.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = NUM_CINCO;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});		
		
		textButton33.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = NUM_SEIS;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});
		
		textButton34.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = SIMBOLO_POR;
				
				if (!operandoTmp.equals("")) {
					operacion = ultimaPulsacion;
					operando1 = operandoTmp;
					operandoTmp = "";
					subPantalla.setText(operando1 + operacion);
					pantalla.setText("");
				}
			}
		});
		
		textButton35.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = SIMBOLO_MASMENOS;
				
				if (!operandoTmp.equals("")) {
					if (operandoTmp.contains(SIMBOLO_MENOS)) {
						operandoTmp = operandoTmp.substring(operandoTmp.indexOf(SIMBOLO_MENOS)+1);
					} else {
						operandoTmp = SIMBOLO_MENOS + operandoTmp;
					}
					
					subPantalla.setText(operandoTmp);
					pantalla.setText("");
				}
			}
		});
	}

	/**
	 * Metodo que genera los botones de la cuarta fila de la calculadora
	 * @param rtp
	 */
	private void crearBotonesFila4(RootPanel rtp)  {
		TextButton textButton21 = new TextButton(NUM_UNO);
		textButton21.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton21, 50, 305);
		
		TextButton textButton22 = new TextButton(NUM_DOS);
		textButton22.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton22, 105, 305);

		TextButton textButton23 = new TextButton(NUM_TRES);
		textButton23.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton23, 160, 305);
		
		TextButton textButton24 = new TextButton(SIMBOLO_MENOS);
		textButton24.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton24, 215, 305);
		

		textButton21.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = NUM_UNO;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});
		
		textButton22.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = NUM_DOS;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});		
		
		textButton23.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = NUM_TRES;
				operandoTmp = operandoTmp + ultimaPulsacion;
				pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
				if (!operacion.equals("")) {
					subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
				}
			}
		});
		
		textButton24.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = SIMBOLO_MENOS;
				
				if (!operandoTmp.equals("")) {
					operacion = ultimaPulsacion;
					operando1 = operandoTmp;
					operandoTmp = "";
					subPantalla.setText(operando1 + operacion);
					pantalla.setText("");
				}
			}
		});
		
	}

	/**
	 * Metodo que genera los botones de la quinta fila de la calculadora
	 * @param rtp
	 */
	private void crearBotonesFila5(RootPanel rtp) {
		TextButton textButton11 = new TextButton(NUM_CERO);
		textButton11.setSize("105", ANCHO_BOTON_SIMPLE);
		rtp.add(textButton11, 50, 360);
		
		TextButton textButton12 = new TextButton(SIMBOLO_PUNTO);
		textButton12.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton12, 160, 360);

		TextButton textButton13 = new TextButton(SIMBOLO_MAS);
		textButton13.setSize(ALTO_BOTON_SIMPLE, ANCHO_BOTON_SIMPLE);
		rtp.add(textButton13, 215, 360);
		
		TextButton textButton14 = new TextButton(SIMBOLO_IGUAL);
		textButton14.setSize(ALTO_BOTON_SIMPLE, "105");
		rtp.add(textButton14, 270, 305);

		textButton11.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = NUM_CERO;
				
				if (validaultimaPulsacionCero()) {
					operandoTmp = operandoTmp + ultimaPulsacion;
					pantalla.setText(pantalla.getText() + ultimaPulsacion);
				
					if (!operacion.equals("")) {
						subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
					}
				}	
			}
		});
		
		textButton12.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

				if (ultimaPulsacion.equals(SIMBOLO_IGUAL) ||
					ultimaPulsacion.equals(SIMBOLO_PORCENTAJE) ||
					ultimaPulsacion.equals(SIMBOLO_MASMENOS) ||
					ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = SIMBOLO_PUNTO;
				
				if (validaultimaPulsacionPunto()) {				
					if (operandoTmp.equals("")) {
						operandoTmp = operandoTmp + NUM_CERO+SIMBOLO_PUNTO;
						pantalla.setText(pantalla.getText() + NUM_CERO+SIMBOLO_PUNTO);
					} else {
						operandoTmp = operandoTmp + ultimaPulsacion;
						pantalla.setText(pantalla.getText() + ultimaPulsacion);
					}	
					
					if (!operacion.equals("")) {
						if (operandoTmp.equals("")) {
							subPantalla.setText(subPantalla.getText() + NUM_CERO+SIMBOLO_PUNTO);
						} else {
							subPantalla.setText(subPantalla.getText() + ultimaPulsacion);
						}
					}
				}	
			}
		});		
		
		textButton13.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if (ultimaPulsacion.equals(SIMBOLO_DECBIN)) {
					reset();
				}
				
				ultimaPulsacion = SIMBOLO_MAS;
				
				if (!operandoTmp.equals("")) {
					operacion = ultimaPulsacion;
					operando1 = operandoTmp;
					operandoTmp = "";
					subPantalla.setText(operando1 + operacion);
					pantalla.setText("");
				}
			}
		});
		
		textButton14.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				ultimaPulsacion = SIMBOLO_IGUAL;
				
				if (!operandoTmp.equals("")) {
					operando2 = operandoTmp;
	
					realizaOperacion(false);
	
					pantalla.setText(subTotal.toString());
					operandoTmp = subTotal.toString();
					subTotal = new BigDecimal("0");
					
					operando1 = "";
					operando2 = "";
				}
			}
		});
		
	}	

	private void realizaOperacion(Boolean esPorcentaje) {
	
		BigDecimal op1 = new BigDecimal(operando1);
		BigDecimal op2 = new BigDecimal(operando2);
		
		if (esPorcentaje) {
			op2 = (op1.multiply(op2)).divide(new BigDecimal(100), 10, BigDecimal.ROUND_UP);
		}
		
		if (operacion.equalsIgnoreCase(SIMBOLO_MAS)) {
			subTotal = op1.add(op2);
			
		} else if (operacion.equalsIgnoreCase(SIMBOLO_MENOS)) {
			subTotal = op1.subtract(op2);
			
		} else if (operacion.equalsIgnoreCase(SIMBOLO_POR)) {
			subTotal = op1.multiply(op2);
			
		} else if (operacion.equalsIgnoreCase(SIMBOLO_DIV)) {	
			if (esPorcentaje) {
				Window.alert("Not implemented");
				reset();
			} else {
				subTotal = op1.divide(op2, 10, BigDecimal.ROUND_UP);
			}
			
		}
	}
	
	/**
	 * Se llama al servicio que se encarga de convertir a binario el numero decimal
	 * pasado como argumento. También se realiza la inserción en el datastore una
	 * vez realizada la conversion
	 * @param numDec
	 */
	public void pasarABinario(String numDec) {

		subPantalla.setText(numDec);
		
		servicioDecToBin.convertirABinario(numDec, new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
				Window.alert("RPC to convertirABinario() failed.");
			}

			public void onSuccess(String result) {
				pantalla.setText(result);
			}
		});
    }	
	
	private boolean validaultimaPulsacionCero() {
		
		boolean retorno = true;
		
		if (ultimaPulsacion.equals(NUM_CERO)) {
			if (operandoTmp.equals(NUM_CERO)) {
				retorno = false;
			}
		}
		
		return retorno;
	}
	
	private boolean validaultimaPulsacionPunto() {
		
		boolean retorno = true;
		
		if (ultimaPulsacion.equals(SIMBOLO_PUNTO)) {
			if (operandoTmp.contains(SIMBOLO_PUNTO)) {
				retorno = false;
			}
		}
		
		return retorno;
	}
	
	private void reset() {
		
		pantalla.setText("");
		subPantalla.setText("");
		
		operacion = "";
		operandoTmp = "";
		operando1 = "";
		operando2 = "";
		
		subTotal = new BigDecimal("0");
				
	}
}
