.PHONY: all clean doc compile

PEERSIM_JARS=""
LIB_JARS=`find -L lib/ -name "*.jar" | tr [:space:] :`

compile:
	mkdir -p classes
	javac -sourcepath src -classpath $(LIB_JARS):$(PEERSIM_JARS) -d classes `find -L -name "*.java"` -Xlint:unchecked

doc:
	mkdir -p doc
	javadoc -sourcepath src -classpath $(LIB_JARS):$(PEERSIM_JARS) -d doc peersim.chord

run:
	java -cp $(LIB_JARS):$(PEERSIM_JARS):classes peersim.Simulator test.cfg

all: compile doc run

clean: 
	rm -fr classes doc
