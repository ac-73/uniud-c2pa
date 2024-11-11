
package it.uniud.c2pa.commandhandlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class ExitCommandHandler {
    @Execute
    public void execute(IWorkbench workbench) {
	workbench.close();
    }
}