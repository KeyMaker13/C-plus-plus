/* rotating cube with color interpolation */

/* demonstration of use of homogeneous-coordinate transformations
and simple data structure for representing cube from Chapter 4 */

/*colors are assigned to the vertices */
/*cube is centered at origin */

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class CubeT
{
    private static float   angle = 0.0f;
    private static float[] axis  = new float[3];
    private static float[] trans = new float[3];
    private static Animator animator;

    static class Renderer implements GLEventListener
    {
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
	    polygon(gl, 1, 0, 3, 2);
	    polygon(gl, 3, 7, 6, 2);
	    polygon(gl, 7, 3, 0, 4);
	    polygon(gl, 2, 6, 5, 1);
	    polygon(gl, 4, 5, 6, 7);
	    polygon(gl, 5, 4, 0, 1);
	}

	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();
	    gl.glEnable(GL.GL_DEPTH_TEST); /* Enable hidden-surface removal */
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

	    if (trackballMove)
	    {
		gl.glRotatef(angle, axis[0], axis[1], axis[2]);

	    }
	    colorcube(gl);
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();
	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		gl.glOrtho(-2.0, 2.0, -2.0 * (float) h / (float) w,
		    	   2.0 * (float) h / (float) w, -10.0, 10.0);
	    else
		gl.glOrtho(-2.0 * (float) w / (float) h,
		    	   2.0 * (float) w / (float) h, -2.0, 2.0, -10.0, 10.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    }

    private static void trackball_ptov(int x, int y, int width, int height, float[] v)
    {
	    /* project x,y onto a hemisphere centered within width, height */
	    v[0] = (2.0f * x - width) / width;
	    v[1] = (height - 2.0f * y) / height;
	    float d = (float) Math.sqrt(v[0]*v[0] + v[1]*v[1]);
	    v[2] = (float) Math.cos((Math.PI / 2.0f) * ((d < 1.0f) ? d : 1.0f));
	    float a = 1.0f / (float) Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
	    v[0] *= a;
	    v[1] *= a;
	    v[2] *= a;
    }

    private static float[] lastPos = {0.0f, 0.0f, 0.0f};
    private static boolean trackingMouse  = false;
    private static boolean trackballMove  = false;

    private static class MouseResponder extends MouseAdapter
    {
	private int startX, startY;
	private int curx, cury;

	public void mousePressed(MouseEvent e)
	{
	    trackingMouse = true;
	    animator.stop();
	    startX = e.getX();
	    startY = e.getY();
	    curx = startX;
	    cury = startX;
	    Dimension size = e.getComponent().getSize();
	    trackball_ptov(startX, startY, size.width, size.height, lastPos);
	    trackballMove = true;
	}

	public void mouseReleased(MouseEvent e)
	{
	    trackingMouse = false;

	    if (startX != e.getX() || startY != e.getY())
	    {
		animator.start();
	    } else
	    {
		angle = 0.0f;
		animator.stop();
		trackballMove = false;
	    }
	}
    }

    private static class MouseMotionResponder extends MouseMotionAdapter
    {
	public void mouseDragged(MouseEvent e)
	{
	    float[] curPos = new float[3];
	    Dimension size = e.getComponent().getSize();

	    trackball_ptov(e.getX(), e.getY(), size.width, size.height, curPos);

	    if (trackingMouse)
	    {
		float dx = curPos[0] - lastPos[0];
		float dy = curPos[1] - lastPos[1];
		float dz = curPos[2] - lastPos[2];

		if (Math.abs(dx) > 0.0 || Math.abs(dy) > 0.0 || Math.abs(dz) > 0.0)
		{
		    angle = 90.0f * (float) Math.sqrt(dx * dx + dy * dy + dz * dz);

		    axis[0] = lastPos[1] * curPos[2] - lastPos[2] * curPos[1];
		    axis[1] = lastPos[2] * curPos[0] - lastPos[0] * curPos[2];
		    axis[2] = lastPos[0] * curPos[1] - lastPos[1] * curPos[0];

		    lastPos[0] = curPos[0];
		    lastPos[1] = curPos[1];
		    lastPos[2] = curPos[2];
		}
	    }
	    ((GLCanvas) e.getComponent()).repaint();
	}
    }

    public static void main(String[] args)
    {
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	canvas.addMouseListener(new MouseResponder());
	canvas.addMouseMotionListener(new MouseMotionResponder());
	animator = new Animator(canvas);

	ICGFrame frame = new ICGFrame("Color cube", canvas);
	frame.setVisible(true);
	animator.start();
    }
}
