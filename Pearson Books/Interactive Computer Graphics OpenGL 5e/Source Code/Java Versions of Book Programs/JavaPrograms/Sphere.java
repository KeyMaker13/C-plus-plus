/* Recursive subdivision of tetrahedron (Chapter 6). Three display
modes: wire frame, constant, and interpolative shading. */

/* program also illustrates defining materials and light sources
in init() */

/* mode 0 = wire frame, mode 1 = constant shading, mode 3 =
interpolative shading */

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class Sphere
{
    private static int n;

    private static class Renderer implements GLEventListener
    {
	/* initial tetrahedron */
	private static float[][] v = {
	    {0.0f, 0.0f, 1.0f}, {0.0f, 0.942809f, -0.33333f},
	    {-0.816497f, -0.471405f, -0.333333f},
	    {0.816497f, -0.471405f, -0.333333f}
	};
	private static float[] theta = {0.0f, 0.0f, 0.0f};

	public void init(GLDrawable drawable)
	{
	    float[] mat_specular   = {1.0f, 1.0f, 1.0f, 1.0f};
	    float[] mat_diffuse    = {1.0f, 1.0f, 1.0f, 1.0f};
	    float[] mat_ambient    = {1.0f, 1.0f, 1.0f, 1.0f};
	    float   mat_shininess  = 100.0f;
	    float[] light_ambient  = {0.0f, 0.0f, 0.0f, 1.0f};
	    float[] light_diffuse  = {1.0f, 1.0f, 1.0f, 1.0f};
	    float[] light_specular = {1.0f, 1.0f, 1.0f, 1.0f};
	    GL gl = drawable.getGL();

	    /* set up ambient, diffuse, and specular components for light 0 */
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, light_ambient);
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, light_diffuse);
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, light_specular);

	    /* define material properties for front face of all polygons */
	    //gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular);
	    //gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient);
	    //gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse);
	    //gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess);

	    gl.glShadeModel(GL.GL_SMOOTH); /* enable smooth shading */
	    gl.glEnable(GL.GL_LIGHTING); /* enable lighting */
	    gl.glEnable(GL.GL_LIGHT0);  /* enable light 0 */
	    gl.glEnable(GL.GL_DEPTH_TEST); /* enable z buffer */
	    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	    gl.glColor3f(0.0f, 0.0f, 0.0f);
	}

	/* displays all three modes, side by side */
	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();

	    // mode 0
	    tetrahedron(gl, n, 0);
	    // mode 1
	    gl.glTranslatef(-2.0f, 0.0f, 0.0f);
	    tetrahedron(gl, n, 1);
	    // mode 2
	    gl.glTranslatef(4.0f, 0.0f, 0.0f);
	    tetrahedron(gl, n, 2);
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();

	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		gl.glOrtho(-4.0, 4.0, -4.0 * (double) h / (double) w, 4.0 * (double) h / (double) w, -10.0, 10.0);
	    else
		gl.glOrtho(-4.0 * (double) w / (double) h, 4.0 * (double) w / (double) h, -4.0, 4.0, -10.0, 10.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}

	/* display one triangle using a line loop for wire frame, a single
	normal for constant shading, or three normals for interpolative
	shading */
	private void triangle(GL gl, float[] a, float[] b, float[] c, int mode)
	{
	    if (mode == 0)
		gl.glBegin(GL.GL_LINE_LOOP);
	    else
		gl.glBegin(GL.GL_POLYGON);
	    if (mode == 1) gl.glNormal3fv(a);
	    if (mode == 2) gl.glNormal3fv(a);
	    gl.glVertex3fv(a);
	    if (mode == 2) gl.glNormal3fv(b);
	    gl.glVertex3fv(b);
	    if (mode == 2) gl.glNormal3fv(c);
	    gl.glVertex3fv(c);
	    gl.glEnd();
	}

	/* normalize a vector */
	private void normal(float[] p)
	{
	    float d = 0.0f;

	    for (int i = 0; i < 3; i++) d += p[i] * p[i];
	    d = (float) Math.sqrt(d);
	    if(d > 0.0f) for (int i = 0; i < 3; i++) p[i] /= d;
	}

	/* Triangle subdivision using vertex numbers. Right-hand rule
	applied to create outward-pointing faces. */
	private void divide_triangle(GL gl, float[] a, float[] b, float[] c, int m, int mode)
	{
	    float[] v1 = new float[3];
	    float[] v2 = new float[3];
	    float[] v3 = new float[3];

	    if (m > 0)
	    {
		for(int j = 0; j < 3; j++) v1[j] = a[j] + b[j];
		normal(v1);
		for(int j = 0; j < 3; j++) v2[j] = a[j] + c[j];
		normal(v2);
		for(int j = 0; j < 3; j++) v3[j] = b[j] + c[j];
		normal(v3);
		divide_triangle(gl, a, v1, v2, m-1, mode);
		divide_triangle(gl, c, v2, v3, m-1, mode);
		divide_triangle(gl, b, v3, v1, m-1, mode);
		divide_triangle(gl, v1, v3, v2, m-1, mode);
	    }
	    else triangle(gl, a, b, c, mode); /* draw triangle at end of recursion */
	}

	/* apply triangle subdivision to faces of tetrahedron */
	private void tetrahedron(GL gl, int n, int mode)
	{
	    divide_triangle(gl, v[0], v[1], v[2], n, mode);
	    divide_triangle(gl, v[3], v[2], v[1], n, mode);
	    divide_triangle(gl, v[0], v[3], v[1], n, mode);
	    divide_triangle(gl, v[0], v[2], v[3], n, mode);
	}
    }

    public static void main(String[] args)
    {
	if (args.length != 1)
	{
	    System.out.println("You must provide the depth of recursion.");
	    System.exit(0);
	}
	n = Integer.parseInt(args[0]);

	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	ICGFrame frame = new ICGFrame("Sphere", canvas);
	frame.setVisible(true);
    }
}
