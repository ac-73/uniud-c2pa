
package it.uniud.c2pa.part;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import it.uniud.c2pa.ModelManager;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

public class ManifestPart {
    @Inject
    public ManifestPart() {
    }

    @PostConstruct
    public void postConstruct(Composite parent) {
	Composite composite = new Composite(parent, SWT.BORDER);
	composite.setLayout(new FillLayout());
	Text text = new Text(composite, SWT.MULTI | SWT.V_SCROLL);
	text.setEditable(false);
	ModelManager modelManager = ModelManager.getInstance();
	modelManager.addImageBytesValueChangeListener(new IValueChangeListener<byte[]>() {
	    @Override
	    public void handleValueChange(ValueChangeEvent<? extends byte[]> event) {
		text.setText(modelManager.getManifestStore());
	    }
	});
    }
}