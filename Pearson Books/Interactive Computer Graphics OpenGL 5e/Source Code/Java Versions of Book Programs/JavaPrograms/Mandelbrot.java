import java.awt.*;
import java.awt.event.*;
import java.util.*;
import net.java.games.jogl.*;

class Mandelbrot
{
    /* default data*/
    /* can enter other values via command line arguments */
    private static final float CENTERX  = -0.5f;
    private static final float CENTERY  = 0.5f;
    private static final float HEIGHT   = 0.5f;
    private static final float WIDTH    = 0.5f;
    private static final int   MAX_ITER = 100;

    /* N x M array to be generated */
    private static final int N = 500;
    private static final int M = 500;

    private static float height = HEIGHT; /* size of window in complex plane */
    private static float width = WIDTH;
    private static float cx = CENTERX; /* center of window in complex plane */
    private static float cy = CENTERY;
    private static int max = MAX_ITER; /* number of interations per point */

    private static int n = N;
    private static int m = M;

    private static byte[] image = new byte[N * M];

    /* complex data type and complex add, mult, and magnitude functions
       probably not worth overhead */

    private static class Complex
    {
	public Complex()
	{
	    real = imag = 0.0f;
	}

	public Complex(float a, float b)
	{
	    real = a;
	    imag = b;
	}

	public void add(Complex a, Complex b)
	{
	    real = a.real + b.real;
	    imag = a.imag + b.imag;
	}

	public void mult(Complex a, Complex b)
	{
	    real = a.real * b.real - a.imag * b.imag;
	    imag = a.real * b.imag + a.imag * b.real;
	}

	public float magnitude()
	{
	    return real * real + imag * imag;
	}

	private float real;
	private float imag;
    }

    private static class Renderer implements GLEventListener
    {
	public void init(GLDrawable drawable)
	{
	    float[] redmap   = new float[256];
	    float[] greenmap = new float[256];
	    float[] bluemap  = new float[256];

	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	    glu.gluOrtho2D(0.0, 0.0, (float) n, (float) m);

	    Random random = new Random();

	    /* define pseudocolor maps, ramps for red and blue,
	       random for green */
	    for(int i = 0; i < 256; i++)
	    {
		redmap[i]   = i / 255.0f;
		greenmap[i] = i / (float) random.nextInt(255);
		bluemap[i]  = 1.0f - i / 255.0f;
	    }

	    gl.glPixelMapfv(GL.GL_PIXEL_MAP_I_TO_R, 256, redmap);
	    gl.glPixelMapfv(GL.GL_PIXEL_MAP_I_TO_G, 256, greenmap);
	    gl.glPixelMapfv(GL.GL_PIXEL_MAP_I_TO_B, 256, bluemap);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    gl.glDrawPixels(n, m, GL.GL_COLOR_INDEX, GL.GL_UNSIGNED_BYTE, image);
	    gl.glFlush();
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		glu.gluOrtho2D(0.0, 0.0, (double) n, (double) m * (double) h /
			       (double) w);
	    else
		glu.gluOrtho2D(0.0, 0.0, (double) n * (double) w / (double) h,
			       (double) m);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}
	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}
    }

    public static void main(String[] args)
    {
	float x, y; 
	float v = 0.0f;
	Complex c0, c;
	Complex d = new Complex();

	if (args.length == 4)
	{
	    cx    = Integer.parseInt(args[0]);
	    cy    = Integer.parseInt(args[1]);
	    width = Integer.parseInt(args[2]);
	    max   = Integer.parseInt(args[3]);
	}
	if (args.length > 0)
	{
	    System.out.println("You must provide no arguments, or provide <center x>, <center Y>, <rectangle width>, <# iterations> as arguments.");
	}

	for (int i = 0; i < n; i++) 
	{
	    for(int j = 0; j < m; j++)
	    {
		/* starting point */
		x = i * (width / (n - 1)) + cx -width / 2.0f;
		y = j * (height / (m - 1)) + cy -height / 2.0f;

		c0 = new Complex(x, y);
		c  = new Complex(0, 0);

		/* complex iteration */
		for(int k = 0; k < max; k++)
		{
		    d.mult(c, c);
		    c.add(d, c0);
		    v = c.magnitude();
		    if (v > 4.0f) break; /* assume not in set if mag > 4 */
		}

		/* assign gray level to point based on its magnitude */
		if (v > 1.0f) v = 1.0f; /* clamp */
		image[i * n + j] = (byte) (255.0f * v);
	    }
	}

	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	ICGFrame frame = new ICGFrame("Mandelbrot", canvas);
	frame.setSize(N, M);
	frame.setVisible(true);
    }
}
