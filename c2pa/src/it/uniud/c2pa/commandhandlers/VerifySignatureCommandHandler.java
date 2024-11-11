package it.uniud.c2pa.commandhandlers;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.bfo.box.C2PAManifest;
import com.bfo.box.C2PAStatus;
import com.bfo.box.C2PAStore;

import it.uniud.c2pa.C2PAVerify;
import it.uniud.c2pa.ModelManager;
import jakarta.inject.Named;

public class VerifySignatureCommandHandler {
    private C2PAVerify c2paVerify = new C2PAVerify();

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) throws FileNotFoundException {
	List<C2PAStatus> result = new ArrayList<>();
	ModelManager modelManager = ModelManager.getInstance();
	C2PAStore c2paStore = modelManager.getC2PAStore();
	if (c2paStore != null) {
	    if (c2paStore.getManifests() != null) {
		C2PAManifest c2paManifest = c2paStore.getActiveManifest();
		ByteArrayInputStream bais = null;
		try {
		    bais = new ByteArrayInputStream(modelManager.getImageBytes());
		    result.addAll(c2paVerify.verifySignature(c2paManifest, bais));
		} finally {
		    try {
			bais.close();
		    } catch (IOException e) {
			// Pazienza
		    }
		}
	    }
	    if (result.isEmpty()) {
		MessageDialog.openInformation(shell, "Verifica firma", "La firma risulta valida!");
	    } else {
		StringBuffer stringBuffer = new StringBuffer();
		for (C2PAStatus c2paStatus : result) {
		    stringBuffer.append(c2paStatus.getMessage());
		    stringBuffer.append("\n");
		}
		MessageDialog.openError(shell, "Verifica firma", stringBuffer.toString());
	    }
	}
    }
}
