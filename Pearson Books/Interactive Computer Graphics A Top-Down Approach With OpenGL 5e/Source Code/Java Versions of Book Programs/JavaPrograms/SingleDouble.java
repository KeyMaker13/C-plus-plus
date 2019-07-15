import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class SingleDouble
{
    private static Animator singleAnimator;
    private static Animator doubleAnimator;
    private static float x, y;

    // Base class for SingleBufferRenderer and DoubleBufferRenderer.
    private static class Renderer implements GLEventListener
    {
	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	    gl.glColor3f(1.0f, 1.0f, 1.0f);
	    gl.glShadeModel(GL.GL_FLAT);
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();

	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		gl.glOrtho(-50.0, 50.0, -50.0*(double)h/(double)w, 50.0*(double)h/(double)w, -1.0, 1.0);
	    else
		gl.glOrtho(-50.0*(double)w/(double)h, 50.0*(double)w/(double)h, -50.0, 50.0, -1.0, 1.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glLoadIdentity();
	}

	public void display(GLDrawable drawable)
	{
	    spin();
	}

	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}

	protected void square(GL gl)
	{
	    gl.glBegin(GL.GL_QUADS);
	       gl.glVertex2f( x,  y);
	       gl.glVertex2f(-y,  x);
	       gl.glVertex2f(-x, -y);
	       gl.glVertex2f( y, -x);
	    gl.glEnd();
	}

	private static final float DEG2RAD = (float) Math.PI / 180.0f;
	private static float spin = 0.0f;

	private static void spin()
	{
	    spin += 2.0f;
	    if (spin > 360.0f)
		spin -= 360.0f;
	    x = 25.0f * (float) Math.cos(DEG2RAD * spin);
	    y = 25.0f * (float) Math.sin(DEG2RAD * spin);
	}
    }

    private static class SingleBufferRenderer extends Renderer
    {
	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    super.display(drawable);
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    square(gl);
	    gl.glFlush();
	}
    }

    private static class DoubleBufferRenderer extends Renderer
    {
	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    super.display(drawable);
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    square(gl);
	}
    }

    private static class MouseResponder extends MouseAdapter
    {
	public void mousePressed(MouseEvent e)
	{
	    try {
		switch (e.getButton())
		{
		case MouseEvent.BUTTON1:
		    singleAnimator.start();
		    doubleAnimator.start();
		    break;
		case MouseEvent.BUTTON2:
		    singleAnimator.stop();
		    doubleAnimator.stop();
		    break;
		}
	    } catch (GLException exception) {
		// Ignore exception.
	    }
	}
    }

    public static void main(String[] args)
    {
	// Create an window with single buffering.
	GLCapabilities singleCaps = new GLCapabilities();
	singleCaps.setDoubleBuffered(false);
	GLCanvas singleCanvas = GLDrawableFactory.getFactory().createGLCanvas(singleCaps);
	singleCanvas.addGLEventListener(new SingleBufferRenderer());
	singleCanvas.addMouseListener(new MouseResponder());
	ICGFrame singleFrame = new ICGFrame("Single buffered", singleCanvas);
	singleFrame.setVisible(true);
	singleAnimator = new Animator(singleCanvas);
	singleAnimator.start();

	// Create another window with double buffering.
	GLCapabilities doubleCaps = new GLCapabilities();
	doubleCaps.setDoubleBuffered(true);
	GLCanvas doubleCanvas = GLDrawableFactory.getFactory().createGLCanvas(doubleCaps);
	doubleCanvas.addGLEventListener(new DoubleBufferRenderer());
	doubleCanvas.addMouseListener(new MouseResponder());
	ICGFrame doubleFrame = new ICGFrame("Double buffered", doubleCanvas);
	doubleFrame.setVisible(true);
	doubleAnimator = new Animator(doubleCanvas);
	doubleAnimator.start();
    }
}
