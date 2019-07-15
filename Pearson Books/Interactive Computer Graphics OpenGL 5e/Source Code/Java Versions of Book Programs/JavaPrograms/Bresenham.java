// Bresenham.java

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class Bresenham
{
    private static final int BLACK = 0;

    private static class BresenhamRenderer implements GLEventListener
    {
	public void init(GLDrawable drawable)
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	    gl.glColor3f(1.0f, 0.0f, 0.0f);
	    gl.glPointSize(1.0f);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(0.0, 499.0, 0.0, 499.0);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    bres(gl, 200, 200, 100, 50);
	    gl.glFlush();
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h) {}
	public void displayChanged(GLDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

	private void draw_pixel(GL gl, int ix, int iy, int value)
	{
	    gl.glBegin(GL.GL_POINTS);
	    gl.glVertex2i(ix, iy);
	    gl.glEnd();
	}

	private void bres(GL gl, int x1, int y1, int x2, int y2)
        {
	    int e;
	    int inc1, inc2;

	    int dx = x2 - x1;
	    int dy = y2 - y1;

	    if (dx < 0) dx = -dx;
	    if (dy < 0) dy = -dy;
	    int incx = 1;
	    if (x2 < x1) incx = -1;
	    int incy = 1;
	    if (y2 < y1) incy = -1;
	    int x = x1;
	    int y = y1;

	    if (dx > dy)
	    {
		draw_pixel(gl, x, y, BLACK);
		e = 2 * dy - dx;
		inc1 = 2 * (dy - dx);
		inc2 = 2 * dy;
		for (int i = 0; i < dx; i++)
		{
		    if (e >= 0)
		    {
			y += incy;
			e += inc1;
		    }
		    else e += inc2;
		    x += incx;
		    draw_pixel(gl, x, y, BLACK);
		}
	    }
	    else
	    {
		draw_pixel(gl, x, y, BLACK);
		e = 2 * dx - dy;
		inc1 = 2 * (dx - dy);
		inc2 = 2 * dx;
		for (int i = 0; i < dy; i++)
		{
		    if (e >= 0)
		    {
			x += incx;
			e += inc1;
		    }
		    else e += inc2;
		    y += incy;
		    draw_pixel(gl, x, y, BLACK);
		}
	    }
	}
    }

    public static void main(String[] args)
    {
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new BresenhamRenderer());
	ICGFrame frame = new ICGFrame("Bresenham's Algorithm", canvas);
	frame.setVisible(true);
    }
}
