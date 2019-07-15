/* rotating cube with color interpolation */

/* demonstration of use of homogeneous-coordinate transformations
and simple data structure for representing cube from Chapter 4 */

/*colors are assigned to the vertices */
/*cube is centered at origin */

import java.awt.*;
import java.awt.event.*;
import java.nio.*;
import net.java.games.jogl.*;

class CubeV
{
    private static class Renderer implements GLEventListener
    {
	private static int axis = 2;
	private static final float theta[] = {0.0f, 0.0f, 0.0f};
	private byte[] cubeIndices = {
	    0, 3, 2, 1, 2, 3, 7, 6, 0, 4, 7, 3, 1, 2, 6, 5, 4, 5, 6, 7, 0, 1,
	    5, 4};

	// These buffers must be declared here.
	// Otherwise they will be garbage collected by the VM.
	ByteBuffer vertexByteBuf = ByteBuffer.allocateDirect(24 * 4);
	ByteBuffer colorByteBuf  = ByteBuffer.allocateDirect(24 * 4);

	public void init(GLDrawable drawable)
	{
	    float vertices[] = {
		-1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f,
		-1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f,
		1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f};
	    float colors[] = {
		0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
		1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f,
		1.0f, 0.0f, 1.0f, 1.0f};
	    GL gl = drawable.getGL();

	    getBuffer(vertexByteBuf).put(vertices);
	    getBuffer(colorByteBuf).put(vertices);

	    gl.glEnable(GL.GL_DEPTH_TEST);
	    gl.glEnableClientState(GL.GL_COLOR_ARRAY);
	    gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
	    gl.glVertexPointer(3, GL.GL_FLOAT, 0, getBuffer(vertexByteBuf));
	    gl.glColorPointer(3, GL.GL_FLOAT, 0, getBuffer(colorByteBuf));
	    gl.glColor3f(1.0f, 1.0f, 1.0f);

	    drawable.addMouseListener(new MouseResponder());
	}

	public void display(GLDrawable drawable)
	{
	    /* display callback, clear frame buffer and z buffer,
	       rotate cube and draw, swap buffers */
	    GL gl = drawable.getGL();
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	    gl.glRotatef(theta[0], 1.0f, 0.0f, 0.0f);
	    gl.glRotatef(theta[1], 0.0f, 1.0f, 0.0f);
	    gl.glRotatef(theta[2], 0.0f, 0.0f, 1.0f);

	    gl.glDrawElements(GL.GL_QUADS, 24, GL.GL_UNSIGNED_BYTE, cubeIndices);

	    gl.glBegin(GL.GL_LINES);
	    gl.glVertex3f(0.0f, 0.0f, 0.0f);
	    gl.glVertex3f(1.0f, 1.0f, 1.0f);
	    gl.glEnd();

	    theta[axis] += 2.0f;
	    if (theta[axis] > 360.0f)
		theta[axis] -= 360.0f;
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();
	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    if (w <= h)
		gl.glOrtho(-2.0, 2.0, -2.0 * (double) h / (double) w, 2.0 * (double) h / (double) w, -10.0, 10.0);
	    else
		gl.glOrtho(-2.0 * (double) w / (double) h, 2.0 * (double) w / (double) h, -2.0, 2.0, -10.0, 10.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

	private class MouseResponder extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {
		if (e.getButton() == MouseEvent.BUTTON1) axis = 0;
		if (e.getButton() == MouseEvent.BUTTON2) axis = 1;
		if (e.getButton() == MouseEvent.BUTTON3) axis = 2;
	    }
	}

	private FloatBuffer getBuffer(ByteBuffer buf)
	{
	    buf.order(ByteOrder.nativeOrder());
	    return buf.asFloatBuffer();
	}
    }

    public static void main(String[] args)
    {
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	final Animator animator = new Animator(canvas);

	ICGFrame frame = new ICGFrame("Color cube", canvas);
	frame.setVisible(true);
	animator.start();
    }
}
