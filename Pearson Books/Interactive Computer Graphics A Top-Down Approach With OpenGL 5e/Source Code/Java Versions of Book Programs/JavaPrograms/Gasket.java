/* two-dimensional Sierpinski gasket          */
/* generated using randomly selected vertices */
/* and bisection                              */

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import net.java.games.jogl.*;

public class Gasket
{
    static class GasketRenderer implements GLEventListener
    {
	private Random random = new Random();

	public void init(GLDrawable drawable)
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    /* attributes */
	    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); /* white background */
	    gl.glColor3f(1.0f, 0.0f, 0.0f); /* draw in red */

	    /* set up viewing */
	    /* 50.0 x 50.0 camera coordinate window with origin lower left */
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(0.0f, 50.0f, 0.0f, 50.0f);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    /* A triangle */
	    float vertices[][] = {{0.0f,0.0f}, {25.0f,50.0f}, {50.0f,0.0f}};
	    float p[] = {7.5f, 5.0f};  /* arbitrary initial point inside triangle */

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);  /*clear the window */
	    gl.glBegin(GL.GL_POINTS);

	    /* compute and plot 5000 new points */
	    for(int k = 0; k < 5000; k++)
	    {
		 /* pick a vertex at random */
		 int j = random.nextInt(3);

		 /* compute point halfway between selected vertex and old point */
		 p[0] = (p[0] + vertices[j][0]) / 2.0f;
		 p[1] = (p[1] + vertices[j][1]) / 2.0f;

		 /* plot new point */
		 gl.glVertex2fv(p);
	    }

	    gl.glEnd();
	    gl.glFlush();
	}

	public void reshape(GLDrawable drawable, int x, int y, int width, int height) {}
	public void displayChanged(GLDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    }

    public static void main(String[] args)
    {
	final ICGFrame frame = new ICGFrame("Sierpinski's Gasket");
	GLCapabilities cap = new GLCapabilities();
	cap.setDoubleBuffered(false);
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(cap);
	canvas.addGLEventListener(new GasketRenderer());
	frame.add(canvas);
	frame.setVisible(true);
    }
}
