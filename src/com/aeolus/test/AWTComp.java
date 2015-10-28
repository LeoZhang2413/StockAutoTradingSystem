package com.aeolus.test;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AWTComp {

    public static void main(String[] args) throws InterruptedException,
                                                  InvocationTargetException
    {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout(SWT.VERTICAL));

        Composite awtComposite = new Composite(shell, SWT.NO_BACKGROUND
                                               | SWT.EMBEDDED);

        Text text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        text.setEditable(true);
        text.setText("SWT text area");

        final Frame frameRef = SWT_AWT.new_Frame(awtComposite);

        EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    Panel panel = new Panel(new BorderLayout());
                    frameRef.add(panel);

                    TextArea textArea = new TextArea("AWT text area");
                    panel.add(textArea);

                    textArea.addFocusListener(new FocusListener() {
                            public void focusGained(FocusEvent e) {
                                System.out.println("Focus gained");
                            }

                            public void focusLost(FocusEvent e) {
                                System.out.println("Focus lost");
                            }
                        });
                }
            });

        shell.setSize(500, 500);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}