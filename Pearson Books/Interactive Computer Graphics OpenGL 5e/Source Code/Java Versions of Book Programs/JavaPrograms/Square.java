/* The program opens a window, clears it to black, then draws a box at the
location of the mouse each time the left button is clicked. The program also
reacts correctly when the window is moved or resized by clearing the new
window to black.*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import net.java.games.jogl.*;

class Square
{
    private static class Renderer implements GLEventListener, MouseMotionListener
    {
	private static final float size = 3.0f; /* half side length of square */
	private static Random random = new Random();
	private static int drawX, drawY;

	public void init(GLDrawable drawable)
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();
	    Dimension size = drawable.getSize();

	    gl.glViewport(0, 0, size.width, size.height);

	    /* Pick 2D clipping window to match
	    size of screen window. This choice avoids having to scale object
	    coordinates each time window is resized. */
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(0.0, (double) size.width, 0.0, (double) size.height);
	    gl.glMatrixMode(GL.GL_MODELVIEW);

	    /* set clear color to black and clear window */
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

	    drawable.addMouseMotionListener(this);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    if (drawX >= 0 && drawY >= 0)
		drawSquare(gl, drawX, drawY);
	    else
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h) 
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    /* adjust clipping box */
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(0.0, (double) w, 0.0, (double) h);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glLoadIdentity();

	    /* adjust viewport and clear */
	    gl.glViewport(0, 0, w, h);
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	}

	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}

	private void drawSquare(GL gl, int x, int y)
	{
	    gl.glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
	    gl.glBegin(GL.GL_QUADS);
	    gl.glVertex2f((float) (x+size), (float) (y+size));
	    gl.glVertex2f((float) (x-size), (float) (y+size));
	    gl.glVertex2f((float) (x-size), (float) (y-size));
	    gl.glVertex2f((float) (x+size), (float) (y-size));
	    gl.glEnd();
	    gl.glFlush();
	}

	public void mouseDragged(MouseEvent e)
	{
	    GLCanvas canvas = (GLCanvas) e.getComponent();

	    drawX = e.getX();
	    drawY = canvas.getHeight() - e.getY() - 1;
	    canvas.display();
	    drawX = -1;
	    drawY = -1;
	}

	public void mouseMoved(MouseEvent e) {}
    }

    public static void main(String[] args)
    {
	GLCapabilities caps = new GLCapabilities();
	caps.setDoubleBuffered(false);
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(caps);
	canvas.setAutoSwapBufferMode(false);
	canvas.addGLEventListener(new Renderer());

	ICGFrame frame = new ICGFrame("Square", canvas);
	frame.setVisible(true);
    }
}
