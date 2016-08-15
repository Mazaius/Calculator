package es.pruebaGWT.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConversionServiceAsync {
	public void convertirABinario(String s, AsyncCallback<String> callback);
}