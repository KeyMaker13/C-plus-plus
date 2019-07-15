import java.awt.*;
import java.awt.event.*;

class ICGMenu extends Menu
{
    ICGMenu()
    {
	super();
    }

    ICGMenu(String label)
    {
	super(label);
    }

    public void addMenuEntry(String label, ActionListener listener)
    {
	MenuItem menuItem = new MenuItem(label);
	menuItem.addActionListener(listener);
	this.add(menuItem);
    }

    private static final long serialVersionUID = 1L;
}
