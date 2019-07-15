import java.awt.*;
import java.awt.event.*;
import java.nio.*;
import net.java.games.jogl.*;

class Pick
{
    private static class Renderer implements GLEventListener, MouseListener
    {
	private final int SIZE = 512;
	private ByteBuffer selectByteBuf = ByteBuffer.allocateDirect(SIZE * 4);
	private boolean picked = false;
	private int pickX, pickY;

	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	    drawable.addMouseListener(this);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    if (picked)
	    {
		GLU glu = drawable.getGLU();
		int[] viewport  = new int[4];

		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport);
		gl.glSelectBuffer(SIZE, getBuffer(selectByteBuf));
		gl.glRenderMode(GL.GL_SELECT);
		gl.glInitNames();
		gl.glPushName(0);

		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();

		/* create 5x5 pixel picking region near cursor location */
		glu.gluPickMatrix((double) pickX, (double) (viewport[3] - pickY), 5.0, 5.0, viewport);
		glu.gluOrtho2D(-2.0, 2.0, -2.0, 2.0);
		drawObjects(gl, GL.GL_SELECT);

		gl.glPopMatrix();
		gl.glFlush();

		int hits = gl.glRenderMode(GL.GL_RENDER);
		int[] selectArray = new int[SIZE];
		getBuffer(selectByteBuf).get(selectArray);
		processHits(hits, selectArray);
		picked = false;
	    }
	    else
	    {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		drawObjects(gl, GL.GL_RENDER);
		gl.glFlush();
	    }
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL  gl  = drawable.getGL();
	    GLU glu = drawable.getGLU();

	    gl.glViewport(0, 0, w, h);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluOrtho2D(-2.0, 2.0, -2.0, 2.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glLoadIdentity();
	}

	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}

	public static void drawObjects(GL gl, int mode)
	{
	    if (mode == GL.GL_SELECT) gl.glLoadName(1);
	    gl.glColor3f(1.0f, 0.0f, 0.0f);
	    gl.glRectf(-0.5f, -0.5f, 1.0f, 1.0f);
	    if (mode == GL.GL_SELECT) gl.glLoadName(2);
	    gl.glColor3f(0.0f, 0.0f, 1.0f);
	    gl.glRectf(-1.0f, -1.0f, 0.5f, 0.5f);
	}

	private IntBuffer getBuffer(ByteBuffer buf)
	{
	    buf.order(ByteOrder.nativeOrder());
	    return buf.asIntBuffer();
	}

	public void mousePressed(MouseEvent e)
	{
	    GLCanvas canvas = (GLCanvas) e.getComponent();

	    pickX = e.getX();
	    pickY = e.getY();
	    picked = true;
	    // Actual pick processing is done in display() because
	    // OpenGL calls must be made in GLEventListener methods. 
	    canvas.display();
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

	/* processHits prints out the contents of the selection array */
	private void processHits(int hits, int[] buffer)
	{
	   int names;
	   int bufIndex = 0;

	   System.out.println("hits = " + hits);
	   for (int i = 0; i < hits; i++)
	   { /*  for each hit  */
	      names = buffer[bufIndex];
	      bufIndex += 3;
	      for (int j = 0; j < names; j++)
	      { /*  for each name */
		 if (buffer[bufIndex] == 1)
		     System.out.println("red rectangle");
		 else
		     System.out.println("blue rectangle");
		 bufIndex++;
	      }
	      System.out.println("");
	   }
	}
    }

    private static class KeyResponder extends KeyAdapter
    {
	public void keyPressed(KeyEvent e)
	{
	    if (e.getKeyChar() == 27)
		System.exit(0);
	}
    }

    public static void main(String[] args)
    {
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	canvas.addKeyListener(new KeyResponder());
	ICGFrame frame = new ICGFrame("Pick", canvas);
	frame.setVisible(true);
    }
}
