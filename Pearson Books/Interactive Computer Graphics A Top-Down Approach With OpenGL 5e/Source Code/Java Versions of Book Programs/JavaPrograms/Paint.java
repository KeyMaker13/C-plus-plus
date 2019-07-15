// simple painting program with text, lines, triangles, rectangles,
// and points

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import net.java.games.jogl.*;
import net.java.games.jogl.util.GLUT;

class Paint
{
    enum DrawMode
    {
	NONE,
	LINE,
	RECTANGLE,
	TRIANGLE,
	POINTS,
	TEXT
    };

    private static int wh = 500, ww = 500; /* initial window size */
    private static float r = 1.0f, g = 1.0f, b = 1.0f; /* drawing color */
    private static float size = 3.0f;    /* half side length of square */
    private static boolean fill = false; /* fill flag */

    private static class Renderer implements GLEventListener, MouseListener, KeyListener
    {
	private int[] xp = new int[2];
	private int[] yp = new int[2];
	private int x, y;
	private GLUT glut = new GLUT();
	private Random random = new Random();
	private boolean draw = false;
	private int count;
	private int rx, ry;           /* raster position */
	private DrawMode draw_mode = DrawMode.NONE; /* drawing mode */
	private char key;

	public void init(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glViewport(0, 0, ww, wh);

	    /* Pick 2D clipping window to match size of X window. This choice
	    avoids having to scale object coordinates each time window is
	    resized. */
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glOrtho(0.0, (double) ww, 0.0, (double) wh, -1.0, 1.0);

	    /* set clear color to black and clear window */
	    gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    gl.glFlush();

	    drawable.addMouseListener(this);
	    drawable.addKeyListener(this);
	}

	public void display(GLDrawable drawable)
	{
	    GL gl = drawable.getGL();

	    gl.glPushAttrib(GL.GL_ALL_ATTRIB_BITS);

	    if (draw)
	    {
		drawShape(gl, draw_mode);
	    }
	    else
	    {
		drawPad(gl);
	    }

	    gl.glPopAttrib();
	}

	public void reshape(GLDrawable drawable, int x, int y, int w, int h)
	{
	    GL gl = drawable.getGL();

	    /* adjust clipping box */
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glOrtho(0.0, (double)w, 0.0, (double)h, -1.0, 1.0);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glLoadIdentity();

	    /* adjust viewport and clear */
	    gl.glViewport(0, 0, w, h);

	    /* set global size for use by drawing routine */
	    ww = w;
	    wh = h;
	}

	public void displayChanged(GLDrawable drawable, boolean faceChanged, boolean deviceChanged) {}

	private void drawSquare(GL gl, int x, int y)
	{
	    y = wh - y;
	    gl.glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
	    gl.glBegin(GL.GL_POLYGON);
		gl.glVertex2f(x+size, y+size);
		gl.glVertex2f(x-size, y+size);
		gl.glVertex2f(x-size, y-size);
		gl.glVertex2f(x+size, y-size);
	    gl.glEnd();
	}

	private void drawShape(GL gl, DrawMode drawMode)
	{
	    gl.glColor3f(r, g, b);

	    switch (drawMode)
	    {
	    case LINE:
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2i(x, wh-y);
		gl.glVertex2i(xp[0], wh-yp[0]);
		gl.glEnd();
		break;
	    case RECTANGLE:
		if (fill)
		    gl.glBegin(GL.GL_POLYGON);
		else 
		    gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2i(x,wh-y);
		gl.glVertex2i(x,wh-yp[0]);
		gl.glVertex2i(xp[0],wh-yp[0]);
		gl.glVertex2i(xp[0],wh-y);
		gl.glEnd();
		break;
	    case TRIANGLE:
		if (fill)
		    gl.glBegin(GL.GL_POLYGON);
		else
		    gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2i(xp[0],wh-yp[0]);
		gl.glVertex2i(xp[1],wh-yp[1]);
		gl.glVertex2i(x,wh-y);
		gl.glEnd();
		break;
	    case POINTS:
		drawSquare(gl, x, y);
		break;
	    case TEXT:
		if (key == 0) break;
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glRasterPos2i(rx, ry);
		glut.glutBitmapCharacter(gl, GLUT.BITMAP_9_BY_15, key);
		// glut.glutStrokeCharacter(gl, GLUT.STROKE_ROMAN, i);
		rx += glut.glutBitmapWidth(GLUT.BITMAP_9_BY_15, key);
		break;
	    }

	    draw = false;
	}

	private void drawPad(GL gl)
	{
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	    gl.glColor3f(1.0f, 1.0f, 1.0f);
	    screen_box(gl, 0,wh-ww/10,ww/10);
	    gl.glColor3f(1.0f, 0.0f, 0.0f);
	    screen_box(gl, ww/10,wh-ww/10,ww/10);
	    gl.glColor3f(0.0f, 1.0f, 0.0f);
	    screen_box(gl, ww/5,wh-ww/10,ww/10);
	    gl.glColor3f(0.0f, 0.0f, 1.0f);
	    screen_box(gl, 3*ww/10,wh-ww/10,ww/10);
	    gl.glColor3f(1.0f, 1.0f, 0.0f);
	    screen_box(gl, 2*ww/5,wh-ww/10,ww/10);
	    gl.glColor3f(0.0f, 0.0f, 0.0f);
	    gl.glBegin(GL.GL_LINES);
	    gl.glVertex2i(wh/40,wh-ww/20);
	    gl.glVertex2i(wh/40+ww/20,wh-ww/20);
	    gl.glEnd();

	    gl.glBegin(GL.GL_TRIANGLES);
	    gl.glVertex2i(ww/5+ww/40,wh-ww/10+ww/40);
	    gl.glVertex2i(ww/5+ww/20,wh-ww/40);
	    gl.glVertex2i(ww/5+3*ww/40,wh-ww/10+ww/40);
	    gl.glEnd();
	    gl.glPointSize(3.0f);
	    gl.glBegin(GL.GL_POINTS);
	    gl.glVertex2i(3*ww/10+ww/20, wh-ww/20);
	    gl.glEnd();
	    gl.glRasterPos2i(2*ww/5,wh-ww/20);
	    glut.glutBitmapCharacter(gl, GLUT.BITMAP_9_BY_15, 'A');
	    int shift = glut.glutBitmapWidth(GLUT.BITMAP_9_BY_15, 'A');
	    gl.glRasterPos2i(2*ww/5+shift,wh-ww/20);
	    glut.glutBitmapCharacter(gl, GLUT.BITMAP_9_BY_15, 'B');
	    shift += glut.glutBitmapWidth(GLUT.BITMAP_9_BY_15, 'B');
	    gl.glRasterPos2i(2*ww/5+shift,wh-ww/20);
	    glut.glutBitmapCharacter(gl, GLUT.BITMAP_9_BY_15, 'C');
	    gl.glFlush();
	}

	public void mousePressed(MouseEvent e)
	{
	    x = e.getX();
	    y = e.getY();

	    DrawMode where = pick(x, y);

	    if (where != DrawMode.NONE)
	    {
	       count = 0;
	       draw_mode = where;
	    }
	    else
	    {
		GLCanvas canvas = (GLCanvas) e.getComponent();

		switch (draw_mode)
		{
		case LINE:
		    if (count == 0)
		    {
			count++;
			xp[0] = x;
			yp[0] = y;
		    }
		    else
		    {
			draw = true;
			canvas.display();
			draw_mode = DrawMode.NONE;
			count = 0;
		    }
		    break;
		case RECTANGLE:
		    if (count == 0)
		    {
			count++;
			xp[0] = x;
			yp[0] = y;
		    }
		    else
		    {
			draw = true;
			canvas.display();
			draw_mode = DrawMode.NONE;
			count = 0;
		    }
		    break;
		case TRIANGLE:
		    if (count < 2)
		    {
			xp[count] = x;
			yp[count] = y;
			count++;
		    }
		    else
		    {
			draw = true;
			canvas.display();
			draw_mode = DrawMode.NONE;
			count = 0;
		    }
		    break;
		case POINTS:
			draw = true;
			canvas.display();
			count++;
		    break;
	        case TEXT:
		    rx=x;
		    ry=wh-y;
		    draw = true;
		    canvas.display();
		    count = 0;
		    break;
		}
	    }
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

	private DrawMode pick(int x, int y)
	{
	    y = wh - y;

	    if      (y < wh - ww / 10) return DrawMode.NONE;
	    else if (x < ww / 10)      return DrawMode.LINE;
	    else if (x < ww / 5)       return DrawMode.RECTANGLE;
	    else if (x < 3 * ww / 10)  return DrawMode.TRIANGLE;
	    else if (x < 2 * ww / 5)   return DrawMode.POINTS;
	    else if (x < ww / 2)       return DrawMode.TEXT;
	    else                       return DrawMode.NONE;
	}

	public void keyPressed(KeyEvent e) 
	{
	    if (draw_mode != DrawMode.TEXT) return;

	    GLCanvas canvas = (GLCanvas) e.getComponent();
	    key = e.getKeyChar();
	    draw = true;
	    canvas.display();
	    key = 0;
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	private void screen_box(GL gl, int x, int y, int s)
	{
	    gl.glBegin(GL.GL_QUADS);
	    gl.glVertex2i(x, y);
	    gl.glVertex2i(x+s, y);
	    gl.glVertex2i(x+s, y+s);
	    gl.glVertex2i(x, y+s);
	    gl.glEnd();
	}
    }

    private static void createPopupMenus(final Component parent)
    {
	ICGMenu colorMenu = new ICGMenu("Colors");

	colorMenu.addMenuEntry("Red", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 1.0f; g = 0.0f; b = 0.0f;
		}
	    });
	colorMenu.addMenuEntry("Green", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 0.0f; g = 1.0f; b = 0.0f;
		}
	    });
	colorMenu.addMenuEntry("Blue", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 0.0f; g = 0.0f; b = 1.0f;
		}
	    });
	colorMenu.addMenuEntry("Cyan", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 0.0f; g = 1.0f; b = 1.0f;
		}
	    });
	colorMenu.addMenuEntry("Magenta", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 1.0f; g = 0.0f; b = 1.0f;
		}
	    });
	colorMenu.addMenuEntry("Yellow", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 1.0f; g = 1.0f; b = 0.0f;
		}
	    });
	colorMenu.addMenuEntry("White", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 1.0f; g = 1.0f; b = 1.0f;
		}
	    });
	colorMenu.addMenuEntry("Black", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    r = 0.0f; g = 0.0f; b = 0.0f;
		}
	    });

	ICGMenu pixelMenu = new ICGMenu("Pixel Size");

	pixelMenu.addMenuEntry("increase pixel size", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    size *= 2;
		}
	    });
	pixelMenu.addMenuEntry("decrease pixel size", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    size /= 2;
		}
	    });

	ICGMenu fillMenu = new ICGMenu("Fill");

	fillMenu.addMenuEntry("fill on", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    fill = true;
		}
	    });
	fillMenu.addMenuEntry("fill off", new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    fill = false;
		}
	    });

	ICGPopupMenu middleButtonPopup = new ICGPopupMenu(parent, MouseEvent.BUTTON2);
	middleButtonPopup.add(colorMenu);
	middleButtonPopup.add(pixelMenu);
	middleButtonPopup.add(fillMenu);

	ICGMenuItem quitMenuItem = new ICGMenuItem("Quit", new ActionListener () {
		public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		}
	    });

	ICGMenuItem clearMenuItem = new ICGMenuItem("Clear", new ActionListener () {
		public void actionPerformed(ActionEvent e) {
		    parent.repaint();
		}
	    });

	ICGPopupMenu rightButtonPopup = new ICGPopupMenu(parent, MouseEvent.BUTTON3);
	rightButtonPopup.add(quitMenuItem);
	rightButtonPopup.add(clearMenuItem);
    }

    public static void main(String[] args)
    {
	GLCapabilities caps = new GLCapabilities();
	caps.setDoubleBuffered(false);
	GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(caps);
	canvas.addGLEventListener(new Renderer());
	createPopupMenus(canvas);
	ICGFrame frame = new ICGFrame("Paint", canvas);
	frame.setSize(ww, wh);
	frame.setVisible(true);
    }
}
