package es.pruebaGWT.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("servicioDecToBin")
public interface ConversionService extends RemoteService {
	public String convertirABinario(String numDec);
}
