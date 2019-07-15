import java.awt.*;
import java.awt.event.*;

class ICGMenuItem extends MenuItem
{
    public ICGMenuItem()
    {
	super();
    }

    public ICGMenuItem(String label, ActionListener listener)
    {
	super(label);
	addActionListener(listener);
    }

    private static final long serialVersionUID = 1L;
}
