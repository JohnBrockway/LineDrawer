JFLAGS = -g
JC = javac -encoding UTF-8
JAVA = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	CanvasView.java ColorButton.java ColorView.java IconButton.java Main.java Model.java Point.java SliderView.java Stroke.java ThicknessButton.java ThicknessView.java View.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run: default
	$(JAVA) -cp . Main

