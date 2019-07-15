/*
  Bezier.java
  Bezier curve drawing program
    q - Quit the program
    c - Clear the screen
    b - Toggle drawing of Bezier curves
*/

import java.awt.*;
import java.awt.event.*;
import java.nio.FloatBuffer;
import net.java.games.jogl.*;

class Bezier
{
    /* fixed maximum number of control points */
    private static final int MAX_CPTS = 25;
    private static FloatBuffer cpts = FloatBuffer.allocate(MAX_CPTS * 3);
    private static int ncpts = 0;
    private static boolean drawCurvesFlag = false;

    private static class BezierRenderer implements GLEventListener
    {
	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	    gl.glColor3f(0.0f, 0.0f, 0.0f);
	    gl.glPointSize(5.0f);
	    gl.glEnable(GL.GL_MAP1_VERTEX_3);

	    drawable.addMouseListener(new MouseResponder());
	    drawable.addKeyListener(new KeyResponder());
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();
	    float[] vertexBuf = new float[3];

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    gl.glBegin(GL.GL_POINTS);
	    for (int i = 0; i < ncpts; i++)
	    {
		cpts.position(i * 3);
		cpts.get(vertexBuf, 0, 3);
		gl.glVertex3fv(vertexBuf);
	    }
	    gl.glEnd();
    
	    if (drawCurvesFlag)
	    {
		drawCurves(gl);
	    }
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();

	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glViewport(0, 0, w, h);
	}

	public void displayChanged(GLDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    }

    private static class MouseResponder extends MouseAdapter
    {
	public void mousePressed(MouseEvent e)
	{
	    GLCanvas canvas = getCanvas(e.getComponent());
	    GL gl = canvas.getGL();

	    if (e.getButton() == MouseEvent.BUTTON1)
	    {
	        /* see if we have room for any more control points */
	        if (ncpts == MAX_CPTS) return;

	        /* translate back to our coordinate system */
		Dimension size = e.getComponent().getSize();
	        float wx = (2.0f * e.getX()) / (float)(size.width - 1) - 1.0f;
	        float wy = (2.0f * (size.height - 1 - e.getY())) / (float)(size.height - 1) - 1.0f;

	        /* save the point */
		/*
	        cpts[ncpts][0] = wx;
	        cpts[ncpts][1] = wy;
	        cpts[ncpts][2] = 0.0f;
		*/
		cpts.put(ncpts * 3,     wx);
		cpts.put(ncpts * 3 + 1, wy);
		cpts.put(ncpts * 3 + 2, 0.0f);
	        ncpts++;

		canvas.repaint();
	    }
	}
    }

    private static class KeyResponder extends KeyAdapter
    {
	public void keyPressed(KeyEvent e)
	{
	    GLCanvas canvas = getCanvas(e.getComponent());

	    switch (e.getKeyChar())
	    {
		case 'q':
		case 'Q':
		    System.exit(0);
		    break;
		case 'c':
		case 'C':
		    ncpts = 0;
		    cpts.clear();
		    canvas.repaint();
		    break;
		case 'b':
		case 'B':
		    drawCurvesFlag = drawCurvesFlag ? false : true;
		    canvas.repaint();
		    break;
	    }
	}
    }

    private static void drawCurves(GL gl)
    {
	// vertexBuf contains four control points.
	int bufLen = 3 * 4;
	float[] vertexBuf = new float[bufLen];

	for (int i = 0; i < (ncpts - 3); i += 3)
	{
	    cpts.position(i * 3);
	    cpts.get(vertexBuf, 0, bufLen);
	    /* draw the curve using OpenGL evaluators */
	    gl.glMap1f(GL.GL_MAP1_VERTEX_3, 0.0f, 1.0f, 3, 4, vertexBuf);
	    gl.glMapGrid1f(30, 0.0f, 1.0f);
	    gl.glEvalMesh1(GL.GL_LINE, 0, 30);
	}
    }

    private static GLCanvas getCanvas(Component c)
    {
	GLCanvas canvas = null;

	if (c instanceof GLCanvas)
	{
	    canvas = (GLCanvas) c;
	}
	return canvas;
    }

    public static void main(String[] args)
    {
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new BezierRenderer());
	ICGFrame frame = new ICGFrame("Curves", canvas);
	frame.setVisible(true);
    }
}
