
package it.uniud.c2pa.part;

import java.io.ByteArrayInputStream;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import it.uniud.c2pa.ModelManager;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

public class ImagePart {
    @Inject
    public ImagePart() {
    }

    @PostConstruct
    public void postConstruct(Composite parent) {
	Composite composite = new Composite(parent, SWT.BORDER);
	composite.setLayout(new FillLayout());
	Label label = new Label(composite, SWT.CENTER);
	label.addDisposeListener(new DisposeListener() {
	    @Override
	    public void widgetDisposed(DisposeEvent e) {
		if (label.getImage() != null) {
		    label.getImage().dispose();
		}
	    }
	});

	ModelManager modelManager = ModelManager.getInstance();
	modelManager.addImageBytesValueChangeListener(new IValueChangeListener<byte[]>() {
	    @Override
	    public void handleValueChange(ValueChangeEvent<? extends byte[]> event) {
		Image oldImage = label.getImage();
		if (oldImage != null) {
		    oldImage.dispose();
		}

		byte[] bytes = event.diff.getNewValue();
		if (bytes != null) {
		    ByteArrayInputStream bais = null;
		    try {
			bais = new ByteArrayInputStream(bytes);
			Image newImage = new Image(Display.getCurrent(), bais);
			label.setImage(newImage);
		    } finally {
			try {
			    bais.close();
			} catch (Exception e) {
			    // Pazienza
			}
		    }
		}
	    }
	});
    }
}