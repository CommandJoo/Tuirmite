all: compile lib# compilew libw
header:
	javac -h . ../main/java/de/johannes/curses/Curses.java
compile:
	cc -c -fPIC  -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux de_johannes_curses_Curses.c -o de_johannes_curses_Curses.o
lib:
	cc -shared -fPIC de_johannes_curses_Curses.o -L/usr/i686-w64-mingw32/include/ncursesw/ncurses.h --enable-ext-colors --enable-widec -lncursesw -o libcurses.so
#compilew:
#	x86_64-w64-mingw32-gcc -c -fPIC -I/usr/x86_64-w64-mingw32/include/ncursesw/ -lncurses -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux de_johannes_curses_Curses.c -lncursesw -o de_johannes_curses_Curses_W.o
#libw:
#	x86_64-w64-mingw32-gcc -shared -fPIC -I/usr/x86_64-w64-mingw32/include/ncursesw/ -o libcurses.dll de_johannes_curses_Curses_W.o -lncursesw
