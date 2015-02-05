

package ieci.tdw.ispac.designer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.DialogBox;




public abstract class BaseExamplesEntryPoint implements EntryPoint {


	public void onModuleLoad() {
		// set uncaught exception handler

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable throwable) {
				String text = "Uncaught exception: ";
				while (throwable != null) {
					StackTraceElement[] stackTraceElements = throwable
							.getStackTrace();
					text += new String(throwable.toString() + "\n");
					for (int i = 0; i < stackTraceElements.length; i++) {
						text += "    at " + stackTraceElements[i] + "\n";
					}
					throwable = throwable.getCause();
					if (throwable != null) {
						text += "Caused by: ";
					}
				}
				DialogBox dialogBox = new DialogBox(true);
				DOM.setStyleAttribute(dialogBox.getElement(),
						"backgroundColor", "#ABCDEF");
				System.err.print(text);
				text = text.replaceAll(" ", "&nbsp;");
				dialogBox.setHTML("<pre>" + text + "</pre>");
				dialogBox.center();
			}
		});

		// use a deferred command so that the handler catches onLoad()
		// exceptions
		DeferredCommand.addCommand(new Command() {
			public void execute() {
			   
				onLoad();
			}
		});
		
	}
	

	
	
	/**
	 * To be implemented by child classes.
	 */
	protected abstract void onLoad();

	

	

	
}
