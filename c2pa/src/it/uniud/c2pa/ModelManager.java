package it.uniud.c2pa;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.WritableValue;

import com.bfo.box.C2PAStore;
import com.bfo.json.Json;
import com.bfo.json.JsonWriteOptions;

public class ModelManager {
    private IObservableValue<byte[]> imageBytesObservable = WritableValue.withValueType(byte[].class);
    private IObservableValue<C2PAStore> c2paStoreObservable = WritableValue.withValueType(C2PAStore.class);

    private static ModelManager instance;

    private ModelManager() {
    }

    public static ModelManager getInstance() {
	if (instance == null) {
	    instance = new ModelManager();
	}
	return instance;
    }

    public void addImageBytesValueChangeListener(IValueChangeListener<byte[]> valueChangeListener) {
	imageBytesObservable.addValueChangeListener(valueChangeListener);
    }

    public byte[] getImageBytes() {
	return imageBytesObservable.getValue();
    }

    public void setImageBytes(byte[] bytes) {
	imageBytesObservable.setValue(bytes);
    }

    public String getManifestStore() {
	String result = "Dati C2PA non presenti";
	C2PAStore c2paStore = getC2PAStore();
	if (c2paStore != null) {
	    Json json = c2paStore.toJson();
	    result = jsonPrettyPrint(json);
	}
	return result;
    }

    public C2PAStore getC2PAStore() {
	return c2paStoreObservable.getValue();
    }

    public void setC2PAStore(C2PAStore c2paStore) {
	c2paStoreObservable.setValue(c2paStore);
    }

    private String jsonPrettyPrint(Json json) {
	String result = null;
	try {
	    JsonWriteOptions jsonWriteOptions = new JsonWriteOptions();
	    jsonWriteOptions.setPretty(true);
	    jsonWriteOptions.setSorted(true);
	    jsonWriteOptions.setSpaceAfterColon(false);
	    StringBuffer stringBuffer = new StringBuffer();
	    json.write(stringBuffer, jsonWriteOptions);
	    result = stringBuffer.toString();
	} catch (Exception e) {
	    result = e.getLocalizedMessage();
	}

	return result;
    }
}
