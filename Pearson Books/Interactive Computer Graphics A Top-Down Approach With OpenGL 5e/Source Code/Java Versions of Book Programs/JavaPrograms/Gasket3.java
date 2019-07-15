/* recursive subdivision of triangle to form Sierpinski gasket */
/* number of recursive steps given on command line */

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class Gasket3
{
    private static int n;

    private static class Renderer implements GLEventListener
    {
	// initial tetrahedron
	private static float[][] v = {
	    {0.0f, 0.0f, 1.0f}, {0.0f, 0.942809f, -0.33333f},
	    {-0.816497f, -0.471405f, -0.333333f},
	    {0.816497f, -0.471405f, -0.333333f}
	};

	private static float[][] colors = {
	    {1.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f},
	    {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 0.0f}
	};

	public void init(GLDrawable drawable)
	{
	    GL  gl = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(-2.0, 2.0, -2.0, 2.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	    gl.glEnable(GL.GL_DEPTH_TEST);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	    gl.glBegin(GL.GL_TRIANGLES);
	    divide_tetra(gl, v[0], v[1], v[2], v[3], n);
	    gl.glEnd();
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();

	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		gl.glOrtho(-1.0, 1.0, -1.0 * (double) h / (double) w,
		    1.0 * (double) h / (double) w, -10.0, 10.0);
	    else
		gl.glOrtho(-1.0 * (double) w / (double) h,
		    1.0 * (double) w / (double) h, -1.0, 1.0, -10.0, 10.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}

	private static void triangle(GL gl, float[] a, float[] b, float[] c)
	{
	    gl.glVertex3fv(a);
	    gl.glVertex3fv(b);
	    gl.glVertex3fv(c);
	}

	private static void tetra(GL gl, float[] a, float[] b, float[] c, float[] d)
	{
	    gl.glColor3fv(colors[0]);
	    triangle(gl, a, b, c);
	    gl.glColor3fv(colors[1]);
	    triangle(gl, a, c, d);
	    gl.glColor3fv(colors[2]);
	    triangle(gl, a, d, b);
	    gl.glColor3fv(colors[3]);
	    triangle(gl, b, d, c);
	}

	private static void divide_tetra(GL gl, float[] a, float[] b, float[] c, float[] d, int m)
	{
	    float[][] mid = new float[6][3];

	    if (m > 0)
	    {
		/* compute six midpoints */
		for(int j = 0; j < 3; j++) mid[0][j] = (a[j] + b[j]) / 2.0f;
		for(int j = 0; j < 3; j++) mid[1][j] = (a[j] + c[j]) / 2.0f;
		for(int j = 0; j < 3; j++) mid[2][j] = (a[j] + d[j]) / 2.0f;
		for(int j = 0; j < 3; j++) mid[3][j] = (b[j] + c[j]) / 2.0f;
		for(int j = 0; j < 3; j++) mid[4][j] = (c[j] + d[j]) / 2.0f;
		for(int j = 0; j < 3; j++) mid[5][j] = (b[j] + d[j]) / 2.0f;

		/* create 4 tetrahedrons by subdivision */
		divide_tetra(gl, a, mid[0], mid[1], mid[2], m - 1);
		divide_tetra(gl, mid[0], b, mid[3], mid[5], m - 1);
		divide_tetra(gl, mid[1], mid[3], c, mid[4], m - 1);
		divide_tetra(gl, mid[2], mid[4], d, mid[5], m - 1);
	    }
	    else tetra(gl, a, b, c, d); /* draw tetrahedron at end of recursion */
	}
    }

    public static void main(String[] args)
    {
	if (args.length != 1)
	{
	    System.out.println("You must provide the depth of recursion as argument.");
	    System.exit(0);
	}
	n = Integer.parseInt(args[0]);
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	ICGFrame frame = new ICGFrame("3D Sierpinski Gasket", canvas);
	frame.setVisible(true);
    }
}
