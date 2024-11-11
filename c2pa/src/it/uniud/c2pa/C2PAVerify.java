package it.uniud.c2pa;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import com.bfo.box.BoxFactory;
import com.bfo.box.C2PAHelper;
import com.bfo.box.C2PAManifest;
import com.bfo.box.C2PASignature;
import com.bfo.box.C2PAStatus;
import com.bfo.box.C2PAStatus.Code;
import com.bfo.box.C2PAStore;
import com.bfo.box.C2PA_Assertion;
import com.bfo.json.Json;
import com.bfo.json.JsonWriteOptions;

public class C2PAVerify {
    public C2PAStore getC2PAStore(String filePath) throws FileNotFoundException, IOException {
	C2PAStore c2paStore = null;
	try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
	    Json jpegJson = C2PAHelper.readJPEG(fileInputStream);
	    if (jpegJson.has("c2pa")) {
		c2paStore = (C2PAStore) new BoxFactory().load(jpegJson.bufferValue("c2pa"));
	    }
	}
	return c2paStore;
    }
    
    public List<C2PAStatus> verifyAssertions(C2PAManifest c2paManifest, InputStream inputStream) {
	List<C2PAStatus> result = new ArrayList<>();
	List<C2PA_Assertion> assertionList = c2paManifest.getAssertions();
	if (assertionList != null) {
	    for (C2PA_Assertion assertion : assertionList) {
		try {
		    c2paManifest.setInputStream(inputStream);
		    List<C2PAStatus> statusList = assertion.verify();
		    result.addAll(statusList);
		} catch (UnsupportedOperationException | IOException e) {
		    C2PAStatus status = new C2PAStatus(Code.general_error, e.getLocalizedMessage(), null, e);
		    result.add(status);
		}
	    }
	}
	return result;
    }

    public List<C2PAStatus> verifySignature(C2PAManifest c2paManifest, InputStream inputStream) {
	List<C2PAStatus> result = new ArrayList<>();
	C2PASignature c2paSignature = c2paManifest.getSignature();
	if (c2paSignature != null) {
	    ModelManager modelManager = ModelManager.getInstance();
	    try {
		ByteArrayInputStream bais = null;
		try {
		    bais = new ByteArrayInputStream(modelManager.getImageBytes());
		    c2paManifest.setInputStream(bais);

		    KeyStore keyStore = KeyStore.getInstance("PKCS12"); // TODO
		    keyStore.load(null, null);

		    result.addAll(c2paSignature.verify(keyStore));
		} finally {
		    try {
			bais.close();
		    } catch (Exception e) {
			// Pazienza
		    }
		}
	    } catch (Exception e) {
		C2PAStatus status = new C2PAStatus(Code.general_error, e.getLocalizedMessage(), null, e);
		result.add(status);
	    }
	}
	return result;
    }

    public String getManifestStore(String filePath) throws FileNotFoundException, IOException {
	String manifestStore = null;
	C2PAStore c2paStore = getC2PAStore(filePath);
	Json json = c2paStore.toJson();
	manifestStore = jsonPrettyPrint(json);
	return manifestStore;
    }

    private String jsonPrettyPrint(Json json) throws IOException {
	JsonWriteOptions jsonWriteOptions = new JsonWriteOptions();
	jsonWriteOptions.setPretty(true);
	jsonWriteOptions.setSorted(true);
	jsonWriteOptions.setSpaceAfterColon(false);
	StringBuffer stringBuffer = new StringBuffer();
	json.write(stringBuffer, jsonWriteOptions);

	return stringBuffer.toString();
    }
}
