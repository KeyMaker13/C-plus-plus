// rotating cube with viewer movement from Chapter 5
// cube definition and display similar to rotating-cube program

// we use the Lookat function in the display callback to point the
// viewer, whose position can be altered by the x, X, y, Y, z, and Z keys
// the perspective view is set in the reshape callback

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class CubeView
{
    static class CubeRenderer implements GLEventListener
    {
	private static final float theta[] = {0.0f, 0.0f, 0.0f};
	private static int axis = 2;
	private static float viewer[] = {0.0f, 0.0f, 5.0f};
	private class MouseResponder extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {
		/* mouse callback, selects an axis about which to rotate */
		if (e.getButton() == MouseEvent.BUTTON1) axis = 0;
		if (e.getButton() == MouseEvent.BUTTON2) axis = 1;
		if (e.getButton() == MouseEvent.BUTTON3) axis = 2;

		Component c = e.getComponent();
		if (c instanceof GLCanvas)
		{
		    GLCanvas canvas = (GLCanvas) c;
		    canvas.display();
		}
	    }
	}

	private class KeyResponder extends KeyAdapter
	{
	    private void redraw(Component c)
	    {
		if (c instanceof GLCanvas)
		{
		    GLCanvas canvas = (GLCanvas) c;
		    canvas.display();
		}
	    }

	    public void keyPressed(KeyEvent e)
	    {
		Component c = e.getComponent();

		switch (e.getKeyChar())
		{
		    case 'x':
			viewer[0] -= 1.0;
			redraw(c);
			break;
		    case 'X':
			viewer[0] += 1.0;
			redraw(c);
			break;
		    case 'y':
			viewer[0] -= 1.0;
			redraw(c);
			break;
		    case 'Y':
			viewer[0] += 1.0;
			redraw(c);
			break;
		    case 'z':
			viewer[0] -= 1.0;
			redraw(c);
			break;
		    case 'Z':
			viewer[0] += 1.0;
			redraw(c);
			break;
		}
	    }
	}

	private void polygon(GL gl, int a, int b, int c, int d)
	{
	    float vertices[][] = {
		{-1.0f, -1.0f, -1.0f}, { 1.0f, -1.0f, -1.0f},
		{ 1.0f,  1.0f, -1.0f}, {-1.0f,  1.0f, -1.0f},
		{-1.0f, -1.0f,  1.0f}, { 1.0f, -1.0f,  1.0f},
		{ 1.0f,  1.0f,  1.0f}, {-1.0f,  1.0f,  1.0f}
	    };
	    float colors[][] = {
		{0.0f, 1.0f, 1.0f}, {1.0f, 0.0f, 0.0f},
		{1.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f},
		{0.0f, 0.0f, 1.0f}, {1.0f, 0.0f, 1.0f},
		{1.0f, 1.0f, 1.0f}, {0.0f, 0.0f, 0.0f}
	    };

	    /* draw a polygon via list of vertices */
	    gl.glBegin(GL.GL_POLYGON);
	    gl.glColor3fv(colors[a]);
	    gl.glVertex3fv(vertices[a]);
	    gl.glVertex3fv(vertices[b]);
	    gl.glVertex3fv(vertices[c]);
	    gl.glVertex3fv(vertices[d]);
	    gl.glEnd();
	}

	private void colorcube(GL gl)
	{
	    polygon(gl, 0, 3, 2, 1);
	    polygon(gl, 1, 2, 6, 5);
	    polygon(gl, 2, 3, 7, 6);
	    polygon(gl, 3, 0, 4, 7);
	    polygon(gl, 4, 5, 6, 7);
	    polygon(gl, 5, 4, 0, 1);
	}

	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();
	    gl.glEnable(GL.GL_DEPTH_TEST);
	    drawable.addMouseListener(new MouseResponder());
	    drawable.addKeyListener(new KeyResponder());
	}

	public void display(GLDrawable drawable)
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	    glu.gluLookAt(viewer[0], viewer[1], viewer[2], 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	    gl.glRotatef(theta[0], 1.0f, 0.0f, 0.0f);
	    gl.glRotatef(theta[1], 0.0f, 1.0f, 0.0f);
	    gl.glRotatef(theta[2], 0.0f, 0.0f, 1.0f);

	    colorcube(gl);

	    drawable.swapBuffers();

	    theta[axis] += 2.0f;
	    if (theta[axis] > 360.0f)
		theta[axis] -= 360.0f;
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();
	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		gl.glFrustum(-2.0, 2.0, -2.0 * (double) h / (double) w,
		    	     2.0 * (double) h / (double) w, 2.0, 20.0);
	    else
		gl.glFrustum(-2.0 * (double) w / (double) h,
		    	     2.0 * (double) w / (double) h, -2.0, 2.0, 2.0, 20.0);
	    // or we can use gluPerspective()
	    // gluPerspective(45.0, w / h, 1.0, 10.0);
 
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    }

    public static void main(String[] args)
    {
	GLCapabilities caps = new GLCapabilities();
	caps.setDoubleBuffered(true);
	//caps.setDepthBits(32);
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(caps);
	canvas.addGLEventListener(new CubeRenderer());

	ICGFrame frame = new ICGFrame("Color cube");
	frame.add(canvas);
	frame.setVisible(true);
    }
}
