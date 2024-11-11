package it.uniud.c2pa;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import jakarta.inject.Inject;

/**
 * This is a stub implementation containing e4 LifeCycle annotated methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the <em>org.eclipse.core.runtime.products' extension point</em>) that references this class.
 **/
public class E4LifeCycle {
    public static final String MODEL_MANAGER = "ModelManager";

    @Inject
    private ECommandService commandService;

    @PostContextCreate
    void postContextCreate(IEclipseContext context, IEventBroker eventBroker) {
	eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, new EventHandler() {
	    @Override
	    public void handleEvent(Event event) {
		eventBroker.unsubscribe(this);

		try {
		    Command openFileCommand = commandService.getCommand("it.uniud.c2pa.command.openFile");
		    openFileCommand.executeWithChecks(new ExecutionEvent());
		} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
		    // Pazienza
		}
	    }
	});
    }

    @PreSave
    void preSave(IEclipseContext workbenchContext) {
    }

    @ProcessAdditions
    void processAdditions(IEclipseContext workbenchContext, MApplication application, EModelService modelService) {
	MTrimmedWindow main = (MTrimmedWindow) modelService.find("org.eclipse.e4.window.main", application);
	centerMWindow(main);
    }

    private void centerMWindow(MWindow window) {
	Monitor[] monitorArray = Display.getCurrent().getMonitors();
	if (monitorArray != null) {
	    Monitor monitor = monitorArray[0];
	    int width = monitor.getClientArea().width;
	    int height = monitor.getClientArea().height;

	    window.setX((width - window.getWidth()) / 2);
	    window.setY((height - window.getHeight()) / 2);
	}
    }

    @ProcessRemovals
    void processRemovals(IEclipseContext workbenchContext) {
    }
}
