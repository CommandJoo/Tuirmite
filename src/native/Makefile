all: header compile lib
header:
	javac -h . ../main/java/de/curses/NativeCurses.java
compile:
	cc -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux de_curses_NativeCurses.c -o de_curses_NativeCurses.o
lib:
	cc -shared -fPIC -o libcurses.so de_curses_NativeCurses.o -lncurses
