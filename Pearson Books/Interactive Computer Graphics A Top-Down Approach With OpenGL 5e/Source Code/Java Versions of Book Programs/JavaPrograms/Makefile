JC		= javac
JCFLAGS		= -d classes
CLASSPATH	= classes:jogl.jar
SRCS		= ICGMenu.java ICGMenuItem.java ICGPopupMenu.java \
ICGFrame.java Bezier.java Bresenham.java Cube.java CubeT.java CubeV.java \
CubeView.java Gasket.java Gasket2.java Gasket3.java Mandelbrot.java \
MarchingSquares.java Paint.java Pick.java SingleDouble.java Sphere.java \
Square.java TexCube.java
TARGETS		= $(SRCS:.java=.class)

all: classes $(TARGETS)

classes:
	mkdir $@

%.class: %.java
	$(JC) $(JCFLAGS) -classpath $(CLASSPATH) $<

clean:
	-rm -f classes/*.class
	rmdir classes
