/* rotating cube with color interpolation */

/* demonstration of use of homogeneous-coordinate transformations
and simple data structure for representing cube from Chapter 4 */

/*colors are assigned to the vertices */
/*cube is centered at origin */

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class Cube
{
    static class CubeRenderer implements GLEventListener
    {
	private static final float theta[] = {0.0f, 0.0f, 0.0f};
	private static int axis = 2;
	private float vertices[][] = {
	    {-1.0f, -1.0f, -1.0f}, { 1.0f, -1.0f, -1.0f},
	    { 1.0f,  1.0f, -1.0f}, {-1.0f,  1.0f, -1.0f}, {-1.0f, -1.0f, 1.0f},
	    { 1.0f, -1.0f,  1.0f}, { 1.0f,  1.0f,  1.0f}, {-1.0f,  1.0f, 1.0f}
	};
	private float colors[][] = {
	    {0.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f},
	    {1.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 1.0f},
	    {1.0f, 0.0f, 1.0f}, {1.0f, 1.0f, 1.0f}, {0.0f, 1.0f, 1.0f}
	};

	private class MouseResponder extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {
		/* mouse callback, selects an axis about which to rotate */
		if (e.getButton() == MouseEvent.BUTTON1) axis = 0;
		if (e.getButton() == MouseEvent.BUTTON2) axis = 1;
		if (e.getButton() == MouseEvent.BUTTON3) axis = 2;
	    }
	}

	private void polygon(GL gl, int a, int b, int c, int d)
	{
	    /* draw a polygon via list of vertices */
	    gl.glBegin(GL.GL_POLYGON);
	    gl.glColor3fv(colors[a]);
	    gl.glVertex3fv(vertices[a]);
	    gl.glColor3fv(colors[b]);
	    gl.glVertex3fv(vertices[b]);
	    gl.glColor3fv(colors[c]);
	    gl.glVertex3fv(vertices[c]);
	    gl.glColor3fv(colors[d]);
	    gl.glVertex3fv(vertices[d]);
	    gl.glEnd();
	}

	private void colorcube(GL gl)
	{
	    /* map vertices to faces */
	    polygon(gl, 0, 3, 2, 1);
	    polygon(gl, 2, 3, 7, 6);
	    polygon(gl, 0, 4, 7, 3);
	    polygon(gl, 1, 2, 6, 5);
	    polygon(gl, 4, 5, 6, 7);
	    polygon(gl, 0, 1, 5, 4);
	}

	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glEnable(GL.GL_DEPTH_TEST); /* Enable hidden-surface removal */
	    drawable.addMouseListener(new MouseResponder());
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    /* display callback, clear frame buffer and z buffer,
	       rotate cube and draw, swap buffers */
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	    gl.glRotatef(theta[0], 1.0f, 0.0f, 0.0f);
	    gl.glRotatef(theta[1], 0.0f, 1.0f, 0.0f);
	    gl.glRotatef(theta[2], 0.0f, 0.0f, 1.0f);

	    colorcube(gl);

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
		gl.glOrtho(-2.0, 2.0, -2.0 * (double) h / (double) w,
		    	   2.0 * (double) h / (double) w, -10.0, 10.0);
	    else
		gl.glOrtho(-2.0 * (double) w / (double) h, 2.0 * (double) w / (double) h, -2.0, 2.0, -10.0, 10.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    }

    public static void main(String[] args)
    {
	GLCapabilities caps = new GLCapabilities();
	caps.setDoubleBuffered(true);
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(caps);
	canvas.addGLEventListener(new CubeRenderer());
	Animator animator = new Animator(canvas);

	ICGFrame frame = new ICGFrame("Color cube", canvas);
	frame.setVisible(true);
	animator.start();
    }
}
