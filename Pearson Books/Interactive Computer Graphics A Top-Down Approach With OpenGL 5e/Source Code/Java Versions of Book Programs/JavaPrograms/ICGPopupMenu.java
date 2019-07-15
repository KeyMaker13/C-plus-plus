// ICGPopupMenu.java
// Popup menu for ICG examples.

import java.awt.*;
import java.awt.event.*;

public class ICGPopupMenu extends PopupMenu
{
    public ICGPopupMenu(Component parent, final int button)
    {
	parent.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		if (e.getButton() == button)
		    show(e.getComponent(), e.getX(), e.getY());
	    }
	});
	/*
	addMenuEntry(KeyEvent.VK_ESCAPE, "Quit", new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
	*/
	parent.add(this);
    }

    public void addMenuEntry(int key, String description, ActionListener listener)
    {
	//MenuItem menuItem = new MenuItem(description, new MenuShortcut(key));
	MenuItem menuItem = new MenuItem(description);
	menuItem.addActionListener(listener);
	add(menuItem);
    }
    
    private static final long serialVersionUID = 1L;
}
