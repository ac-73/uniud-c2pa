
package it.uniud.c2pa.commandhandlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;

import it.uniud.c2pa.dialog.AboutDialog;
import jakarta.inject.Named;

public class AboutDialogCommandHandler {
    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
	AboutDialog aboutDialog = new AboutDialog(shell);
	aboutDialog.open();
    }
}