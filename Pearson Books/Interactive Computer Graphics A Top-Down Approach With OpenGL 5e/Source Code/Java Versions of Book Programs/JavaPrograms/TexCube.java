/* rotating cube with color interpolation */

/* demonstration of use of homogeneous-coordinate transformations
and simple data structure for representing cube from Chapter 4 */

/*colors are assigned to the vertices */
/*cube is centered at origin */

import java.awt.*;
import java.awt.event.*;
import net.java.games.jogl.*;

class TexCube
{
    private static Animator animator;

    static class Renderer implements GLEventListener
    {
	private static final float theta[] = {0.0f, 0.0f, 0.0f};
	private static int axis = 2;
	private float vertices[][] = {
	    {-1.0f, -1.0f, -1.0f}, { 1.0f, -1.0f, -1.0f},
	    { 1.0f,  1.0f, -1.0f}, {-1.0f,  1.0f, -1.0f}, {-1.0f, -1.0f, 1.0f},
	    { 1.0f, -1.0f,  1.0f}, { 1.0f,  1.0f,  1.0f}, {-1.0f,  1.0f, 1.0f}
	};
	private float colors[][] = {
	    {0.0f, 0.0f, 0.0f, 0.5f}, {1.0f, 0.0f, 0.0f, 0.5f},
	    {1.0f, 1.0f, 0.0f, 0.5f}, {0.0f, 1.0f, 0.0f, 0.5f},
	    {0.0f, 0.0f, 1.0f, 0.5f}, {1.0f, 0.0f, 1.0f, 0.5f},
	    {1.0f, 1.0f, 1.0f, 0.5f}, {0.0f, 1.0f, 1.0f, 0.5f}
	};

	private class MouseResponder extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {
		if (e.getButton() == MouseEvent.BUTTON1) axis = 0;
		if (e.getButton() == MouseEvent.BUTTON2) axis = 1;
		if (e.getButton() == MouseEvent.BUTTON3) axis = 2;
	    }
	}

	private class KeyResponder extends KeyAdapter
	{
	    public void keyTyped(KeyEvent e)
	    {
		switch (e.getKeyChar())
		{
		case '1':
		    animator.start();
		    break;
		case '2':
		    animator.stop();
		    break;
		case 'q':
		    System.exit(0);
		}
	    }
	}

	private void polygon(GL gl, int a, int b, int c, int d)
	{
	    gl.glBegin(GL.GL_POLYGON);
	    gl.glColor4fv(colors[a]);
	    gl.glTexCoord2f(0.0f, 0.0f);
	    gl.glVertex3fv(vertices[a]);
	    gl.glColor4fv(colors[b]);
	    gl.glTexCoord2f(0.0f, 1.0f);
	    gl.glVertex3fv(vertices[b]);
	    gl.glColor4fv(colors[c]);
	    gl.glTexCoord2f(1.0f, 1.0f);
	    gl.glVertex3fv(vertices[c]);
	    gl.glColor4fv(colors[d]);
	    gl.glTexCoord2f(1.0f, 0.0f);
	    gl.glVertex3fv(vertices[d]);
	    gl.glEnd();
	}

	private void colorcube(GL gl)
	{
	    polygon(gl, 0, 3, 2, 1);
	    polygon(gl, 2, 3, 7, 6);
	    polygon(gl, 0, 4, 7, 3);
	    polygon(gl, 1, 2, 6, 5);
	    polygon(gl, 4, 5, 6, 7);
	    polygon(gl, 0, 1, 5, 4);
	}

	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();
	    byte[] image = new byte[64 * 64];
	    byte r, c;

	    for(int i = 0; i < 64; i++)
	    {
		for(int j = 0; j < 64; j++)
		{
		    r = ((i & 0x8) == 0) ? (byte) 1 : (byte) 0;
		    c = ((j & 0x8) == 0) ? (byte) 1 : (byte) 0;
		    image[i*64+j] = (byte) ((r ^ c) * (byte) 255);
		}
	    }

	    gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_LUMINANCE, 64, 64, 0, GL.GL_LUMINANCE, GL.GL_UNSIGNED_BYTE, image);
	    gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
	    gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
	    gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
	    gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
	    gl.glEnable(GL.GL_TEXTURE_2D);
	    gl.glEnable(GL.GL_DEPTH_TEST); /* Enable hidden-surface removal */

	    drawable.addMouseListener(new MouseResponder());
	    drawable.addKeyListener(new KeyResponder());
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

	    colorcube(gl);

	    drawable.swapBuffers();

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
    }

    public static void main(String[] args)
    {
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	canvas.addGLEventListener(new Renderer());
	animator = new Animator(canvas);

	ICGFrame frame = new ICGFrame("Color cube", canvas);
	frame.setVisible(true);
	animator.start();
    }
}
