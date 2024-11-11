package it.uniud.c2pa.dialog;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AboutDialog extends Dialog {
    private Image image;

    public AboutDialog(Shell parentShell) {
	super(parentShell);

	try (InputStream inputStream = getClass().getResourceAsStream("/icons/about_banner.png")) {
	    image = new Image(Display.getCurrent(), inputStream);

	    parentShell.addDisposeListener(new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
		    image.dispose();
		}
	    });

	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
    }

    @Override
    protected Control createDialogArea(Composite parent) {
	Composite composite = new Composite(parent, SWT.BORDER);
	composite.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
	composite.setLayout(new FormLayout());

	Label imageLabel = new Label(composite, SWT.NONE);
	imageLabel.setImage(image);
	{
	    FormData imageLabelFormData = new FormData();
	    imageLabelFormData.left = new FormAttachment(2);
	    imageLabelFormData.top = new FormAttachment(2);
	    imageLabelFormData.right = new FormAttachment(100 - 2);
	    imageLabel.setLayoutData(imageLabelFormData);
	}

	Text title = new Text(composite, SWT.WRAP);
	title.setEditable(false);
	title.setText("Master in Intelligence e Emerging Technologies");
	Font boldFont = createFontVariant(title, SWT.BOLD);
	title.setFont(boldFont);
	{
	    FormData titleFormData = new FormData();
	    titleFormData.left = new FormAttachment(2);
	    titleFormData.top = new FormAttachment(imageLabel, 30);
	    titleFormData.right = new FormAttachment(100 - 2);
	    title.setLayoutData(titleFormData);
	}

	Text text = new Text(composite, SWT.WRAP);
	text.setEditable(false);
	//@formatter:off
	text.setText("Progetto realizzato da Andrea Candolini come appendice all'elaborato conclusivo\n\n" +
		     "Icone tratte da http://www.icons8.com"); //@formatter:on
	{
	    FormData textFormData = new FormData();
	    textFormData.left = new FormAttachment(2);
	    textFormData.top = new FormAttachment(title, 30);
	    textFormData.right = new FormAttachment(100 - 2);
	    textFormData.bottom = new FormAttachment(100);
	    text.setLayoutData(textFormData);
	}

	return dialogArea;
    }

    private Font createFontVariant(Control control, int style) {
	Font font = control.getFont();
	FontData[] fontDataArray = font.getFontData();
	for (FontData fontData : fontDataArray) {
	    fontData.setStyle(style);
	}
	Font fontVariant = new Font(control.getDisplay(), fontDataArray);
	control.addDisposeListener(new DisposeListener() {
	    @Override
	    public void widgetDisposed(DisposeEvent e) {
		fontVariant.dispose();
	    }
	});
	return fontVariant;
    }

    @Override
    protected boolean isResizable() {
	return false;
    }

    @Override
    protected void configureShell(Shell shell) {
	super.configureShell(shell);
	shell.setSize(545, 480);
	shell.setText("Informazioni");

	centerDialog(shell);
    }

    private void centerDialog(Shell shell) {
	Shell parentShell = getParentShell();
	int parentShellWidth = parentShell.getClientArea().width;
	int parentShellHeight = parentShell.getClientArea().height;

	int width = shell.getSize().x;
	int height = shell.getSize().y;

	shell.setLocation(parentShell.getLocation().x + (parentShellWidth - width) / 2, parentShell.getLocation().y + (parentShellHeight - height) / 2);
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }
}
