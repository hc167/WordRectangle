JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	WordGroup.java \
	WordProcessHelper.java \
	WordRectangleHelper.java \
	MainApp.java 

default: classes

classes: $(CLASSES:.java=.class)

run:
	java MainApp

clean:
	$(RM) *.class
	$(RM) *~

