all: compile lib
header:
	javac -h . ../main/java/de/johannes/curses/Curses.java
compile:
	cc -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux de_johannes_curses_Curses.c -o de_johannes_curses_Curses.o
lib:
	cc -shared -fPIC -o libcurses.so de_johannes_curses_Curses.o -lncurses
