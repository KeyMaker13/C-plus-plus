/* generates contours using marching squares */

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class MarchingSquares
{
    /* region size */
    private static final double X_MAX = 1.0;
    private static final double Y_MAX = 1.0;
    private static final double X_MIN = -1.0;
    private static final double Y_MIN = -1.0;

    /* contour value */
    private static final double THRESHOLD = 0.0;

    /* number of cells */
    private static final int N_X = 50;
    private static final int N_Y = 50;

    private static class Renderer implements GLEventListener
    {
	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	    gl.glColor3f(1.0f, 1.0f, 1.0f);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();
	    double[][] data = new double[N_X + 1][N_Y + 1];

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

	    /* form data array from function */
	    for (int i = 0; i < N_X; i++)
		for (int j = 0; j < N_Y; j++)
		    data[i][j] = f(X_MIN + i * (X_MAX - X_MIN) / (N_X - 1.0), Y_MIN + j * (Y_MAX - Y_MIN) / (N_Y - 1.0));

	    /* process each cell */
	    for (int i = 0; i < N_X; i++)
		for (int j = 0; j < N_Y; j++)
		{
		   int c = cell(data[i][j], data[i+1][j], data[i+1][j+1], data[i][j+1]);
		   lines(gl, c, i, j, data[i][j], data[i+1][j], data[i+1][j+1], data[i][j+1]);
		}
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		glu.gluOrtho2D(X_MIN, X_MAX, Y_MIN * (double) h / (double) w, Y_MAX* (double) h / (double) w);
	    else
		glu.gluOrtho2D(X_MIN * (double) w / (double) h, X_MAX * (double) w / (double) h, Y_MIN, Y_MAX);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}

	/* define function f(x,y)      */
	private double f(double x, double y)
	{
	    double a = 0.49, b = 0.5;

	    /* Ovals of Cassini  */
	    return (x*x + y*y + a*a) * (x*x + y*y + a*a) -4*a*a*x*x - b*b*b*b;
	}

	/* define cell vertices */
	private int cell(double a, double b, double c , double d)
	{
	    int n = 0;
	    if (a > THRESHOLD) n += 1;
	    if (b > THRESHOLD) n += 8;
	    if (c > THRESHOLD) n += 4;
	    if (d > THRESHOLD) n += 2;
	    return n;
	}


	/* draw line segments for each case */
	private void lines(GL gl, int num, int i, int j, double a, double b, double c, double d)
	{
	     switch(num)
	     {
		 case 1: case 2: case 4: case 7: case 8: case 11: case 13: case 14:
		    draw_one(gl, num, i, j, a, b, c, d);
		    break;
		 case 3: case 6: case 9: case 12:
		    draw_adjacent(gl, num, i, j, a, b, c, d);
		    break;
		 case 5: case 10:
		    draw_opposite(gl, num, i,j, a, b, c, d);
		    break;
		 case 0: case 15:
		    break;
	     }
	}

	private void draw_one(GL gl, int num, int i, int j, double a, double b, double c, double d)
	{
	    double x1 = 0.0;
	    double y1 = 0.0;
	    double x2 = 0.0;
	    double y2 = 0.0;
	    double dx = (X_MAX - X_MIN) / (N_X - 1.0);
	    double dy = (Y_MAX - Y_MIN) / (N_Y - 1.0);
	    double ox = X_MIN + i * (X_MAX - X_MIN) / (N_X - 1.0);
	    double oy = Y_MIN + j * (Y_MAX - Y_MIN) / (N_Y - 1.0);

	    switch (num)
	    {
		case 1: case 14:
		    x1 = ox;
		    y1 = oy + dy * (THRESHOLD - a) / (d - a);
		    x2 = ox + dx * (THRESHOLD - a) / (b - a);
		    y2 = oy;
		    break;
		case 2: case 13:
		    x1 = ox;
		    y1 = oy + dy * (THRESHOLD - a) / (d - a);
		    x2 = ox + dx * (THRESHOLD - d) / (c - d);
		    y2 = oy + dy;
		    break;
		case 4: case 11:
		    x1 = ox + dx * (THRESHOLD - d) / (c - d);
		    y1 = oy + dy;
		    x2 = ox + dx;
		    y2 = oy + dy * (THRESHOLD - b) / (c - b);
		    break;
		case 7: case 8:
		    x1 = ox + dx * (THRESHOLD - a) / (b - a);
		    y1 = oy;
		    x2 = ox + dx;
		    y2 = oy + dy * (THRESHOLD - b) / (c - b);
		    break;
	    }

	    gl.glBegin(GL.GL_LINES);
	    gl.glVertex2d(x1, y1);
	    gl.glVertex2d(x2, y2);
	    gl.glEnd();
	}

	private void draw_adjacent(GL gl, int num, int i, int j, double a, double b, double c, double d)
	{
	    double x1 = 0.0;
	    double y1 = 0.0;
	    double x2 = 0.0;
	    double y2 = 0.0;
	    double dx = (X_MAX - X_MIN) / (N_X - 1.0);
	    double dy = (Y_MAX - Y_MIN) / (N_Y - 1.0);
	    double ox = X_MIN + i * (X_MAX - X_MIN) / (N_X - 1.0);
	    double oy = Y_MIN + j * (Y_MAX - Y_MIN) / (N_Y - 1.0);

	    switch(num)
	    {
		case 3: case 12:
		    x1 = ox + dx * (THRESHOLD - a) / (b - a);
		    y1 = oy;
		    x2 = ox + dx * (THRESHOLD - d) / (c - d);
		    y2 = oy + dy;
		    break;
		case 6: case 9:
		    x1 = ox;
		    y1 = oy + dy * (THRESHOLD - a) / (d - a);
		    x2 = ox + dx;
		    y2 = oy + dy * (THRESHOLD - b) / (c - b);
		    break;
		default:
		    break;
	    }

	    gl.glBegin(GL.GL_LINES);
	    gl.glVertex2d(x1, y1);
	    gl.glVertex2d(x2, y2);
	    gl.glEnd();
	}

	private void draw_opposite(GL gl, int num, int i, int j, double a, double b,
	double c, double d)
	{
	    double x1 = 0.0;
	    double y1 = 0.0;
	    double x2 = 0.0;
	    double y2 = 0.0;
	    double x3 = 0.0;
	    double y3 = 0.0;
	    double x4 = 0.0;
	    double y4 = 0.0;
	    double dx = (X_MAX - X_MIN) / (N_X - 1.0);
	    double dy = (Y_MAX - Y_MIN) / (N_Y - 1.0);
	    double ox = X_MIN + i * (X_MAX - X_MIN) / (N_X - 1.0);
	    double oy = Y_MIN + j * (Y_MAX - Y_MIN) / (N_Y - 1.0);

	    switch(num)
	    {
		case 5:
		    x1 = ox;
		    y1 = oy + dy * (THRESHOLD - a) / (d - a);
		    x2 = ox + dx * (THRESHOLD - a) / (b - a);
		    y2 = oy;
		    x3 = ox + dx * (THRESHOLD - d) / (c - d);
		    y3 = oy + dy ;
		    x4 = ox + dx;
		    y4 = oy + dy * (THRESHOLD - b) / (c - b);
		    break;
		case 10:
		    x1 = ox;
		    y1 = oy + dy * (THRESHOLD - a) / (d - a);
		    x2 = ox + dx * (THRESHOLD - d) / (c - d);
		    y2 = oy + dy;
		    x3 = ox + dy * (THRESHOLD - a) / (b - a);
		    y3 = oy;
		    x4 = ox + dx;
		    y4 = oy + dy * (THRESHOLD - b) / (c - b);
		    break;
		default:
		    break;
	    }

	    gl.glBegin(GL.GL_LINES);
	    gl.glVertex2d(x1, y1);
	    gl.glVertex2d(x2, y2);
	    gl.glVertex2d(x3, y3);
	    gl.glVertex2d(x4, y4);
	    gl.glEnd();
	}
    }

    public static void main(String[] args)
    {
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	ICGFrame frame = new ICGFrame("Contour Plot", canvas);
	frame.setVisible(true);
    }
}
