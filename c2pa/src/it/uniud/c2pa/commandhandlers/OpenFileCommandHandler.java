
package it.uniud.c2pa.commandhandlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.bfo.box.C2PAStore;

import it.uniud.c2pa.C2PAVerify;
import it.uniud.c2pa.ModelManager;

public class OpenFileCommandHandler {
    @Execute
    public void execute() throws FileNotFoundException, IOException {
	FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell());
	fileDialog.setFilterExtensions(new String[] { "*.jpg", "*.jpeg" });
	String fileName = fileDialog.open();
	if (fileName != null) {
	    ModelManager modelManager = ModelManager.getInstance();

	    C2PAVerify c2paVerify = new C2PAVerify();
	    C2PAStore c2paStore = c2paVerify.getC2PAStore(fileName);
	    modelManager.setC2PAStore(c2paStore);

	    File file = new File(fileName);
	    modelManager.setImageBytes(FileUtils.readFileToByteArray(file));
	}
    }
}