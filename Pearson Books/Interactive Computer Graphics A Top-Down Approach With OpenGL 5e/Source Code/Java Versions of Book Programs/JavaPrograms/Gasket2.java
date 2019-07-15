/* recursive subdivision of triangle to form Sierpinski gasket */
/* number of recursive steps given on command line */

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class Gasket2
{
    /* initial triangle */
    private static float[][] v = {
	{-1.0f, -0.58f}, {1.0f, -0.58f}, {0.0f, 1.15f}
    };
    private static int n;

    private static void triangle(GL gl, float[] a, float[] b, float[] c)
    {
	gl.glVertex2fv(a);
	gl.glVertex2fv(b);
	gl.glVertex2fv(c);
    }

    private static void divide_triangle(GL gl, float[] a, float[] b, float[] c, int m)
    {
	/* triangle subdivision using vertex numbers */
	float[] v0 = new float[2];
	float[] v1 = new float[2];
	float[] v2 = new float[2];

	if (m > 0)
	{
	    for (int j = 0; j < 2; j++) v0[j] = (a[j] + b[j]) / 2;
	    for (int j = 0; j < 2; j++) v1[j] = (a[j] + c[j]) / 2;
	    for (int j = 0; j < 2; j++) v2[j] = (b[j] + c[j]) / 2;
	    divide_triangle(gl, a, v0, v1, m - 1);
	    divide_triangle(gl, c, v1, v2, m - 1);
	    divide_triangle(gl, b, v2, v0, m - 1);
	}
	else triangle(gl, a, b, c);
    }

    private static class Renderer implements GLEventListener
    {
	public void init(GLDrawable drawable)
	{
	    GL  gl = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(-2.0, 2.0, -2.0, 2.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	    gl.glColor3f(0.0f, 0.0f, 0.0f);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    gl.glBegin(GL.GL_TRIANGLES);
	    divide_triangle(gl, v[0], v[1], v[2], n);
	    gl.glEnd();
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h) {}
	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}
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
	ICGFrame frame = new ICGFrame("Sierpinski Gasket", canvas);
	frame.setVisible(true);
    }
}
