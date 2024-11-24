#include "stdio.h"
#include "string.h"
#include "de_curses_NativeCurses.h"
#include <curses.h>
#include <locale.h>
#include <stdlib.h>
#include "time.h"

// init()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_init(JNIEnv * env, jobject obj) {
	setlocale(LC_CTYPE, "");
	
	srand(time(NULL));
    // initialize standard curses features
    initscr();
    //cbreak();
	raw();//accept ctrl-c as getch() args
    noecho();
    keypad(stdscr, TRUE);
    curs_set(0);
	set_escdelay(0);
    // set pre-definied colors
    start_color();
    init_pair(de_curses_NativeCurses_BLACK, COLOR_BLACK, COLOR_BLACK);
    init_pair(de_curses_NativeCurses_LIGHT_GRAY, COLOR_WHITE, COLOR_BLACK);
    init_pair(de_curses_NativeCurses_DARK_RED, COLOR_RED, COLOR_BLACK);
    init_pair(de_curses_NativeCurses_DARK_GREEN, COLOR_GREEN, COLOR_BLACK);
    init_pair(de_curses_NativeCurses_DARK_BLUE, COLOR_BLUE, COLOR_BLACK);
    init_pair(de_curses_NativeCurses_DARK_CYAN, COLOR_CYAN, COLOR_BLACK);
    init_pair(de_curses_NativeCurses_DARK_MAGENTA, COLOR_MAGENTA, COLOR_BLACK);
    init_pair(de_curses_NativeCurses_DARK_YELLOW, COLOR_YELLOW, COLOR_BLACK);
}

JNIEXPORT void JNICALL Java_de_curses_NativeCurses_destroy(JNIEnv *, jobject) {
	clear();
	curs_set(1);
	endwin();
}

// cls()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_cls(JNIEnv * env, jobject obj) {
	clear();
}

// setCursor()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_setCursor(JNIEnv * env, jobject obj, jboolean flag) {
    if (flag) { curs_set(1); } else { curs_set(0); }
}

// moveCursor()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_moveCursor(JNIEnv * env, jobject obj, jint x, jint y) {
    move(y, x);
    refresh();
}


// setColor()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_setColor(JNIEnv * env, jobject obj, jint color) {
    wattroff(stdscr, A_BOLD);
    if (color == de_curses_NativeCurses_LIGHT_RED) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_DARK_RED) | A_BOLD);
    } else if (color == de_curses_NativeCurses_LIGHT_GREEN) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_DARK_GREEN) | A_BOLD);
    } else if (color == de_curses_NativeCurses_LIGHT_BLUE) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_DARK_BLUE) | A_BOLD);
    } else if (color == de_curses_NativeCurses_LIGHT_CYAN) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_DARK_CYAN) | A_BOLD);
    } else if (color == de_curses_NativeCurses_LIGHT_MAGENTA) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_DARK_MAGENTA) | A_BOLD);
    } else if (color == de_curses_NativeCurses_LIGHT_YELLOW) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_DARK_YELLOW) | A_BOLD);
    } else if (color == de_curses_NativeCurses_DARK_GRAY) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_BLACK) | A_BOLD);
    } else if (color == de_curses_NativeCurses_WHITE) {
        wattron(stdscr, COLOR_PAIR(de_curses_NativeCurses_LIGHT_GRAY) | A_BOLD);
    } else {
        wattron(stdscr, COLOR_PAIR(color) | A_NORMAL);
    }
}

JNIEXPORT jint JNICALL Java_de_curses_NativeCurses_defineColor(JNIEnv *, jobject, jint color, jfloat r, jfloat g, jfloat b) {
	init_color(color, r*1000, g*1000, b*1000);
	return color;
}

JNIEXPORT jint JNICALL Java_de_curses_NativeCurses_defineColorPair(JNIEnv *, jobject, jint pair, jint foreground, jint background) {
    init_pair(pair, foreground, background);
	return pair;
}

// print()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_print(JNIEnv * env, jobject obj, jchar ch) {
    addch(ch);
    refresh();
}

JNIEXPORT void JNICALL Java_de_curses_NativeCurses_printstr(JNIEnv * env, jobject obj, jstring str) {
    const char *txt = (*env)->GetStringUTFChars(env, str, 0);
	printw(txt);
	refresh();
    (*env)->ReleaseStringUTFChars(env, str, txt);
}

bool kbhit() {
int ch = getch();
	if(ch != ERR) {
		ungetch(ch);
		refresh();
		return true;
	}else {
		refresh();
		return false;
	}
}

JNIEXPORT jint JNICALL Java_de_curses_NativeCurses_getch(JNIEnv * env, jobject obj) {
	return getch();
}

/*
 * Class:     de_curses_NativeCurses
 * Method:    getHeight
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_de_curses_NativeCurses_getHeight(JNIEnv *, jobject) {
	return getmaxy(stdscr);
}

/*
 * Class:     de_curses_NativeCurses
 * Method:    getWidth
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_de_curses_NativeCurses_getWidth(JNIEnv *, jobject) {
    return getmaxx(stdscr);
}

JNIEXPORT void JNICALL Java_de_curses_NativeCurses_drawBox(JNIEnv *, jobject obj, jint x, jint y, jint width, jint height) {
	mvaddch(y, x, ACS_ULCORNER);
	mvvline(y+1, x, 0, height-1);
	mvaddch(y, x+width, ACS_URCORNER);
	mvvline(y+1, x+width, 0, height-1);
	mvaddch(y+height, x, ACS_LLCORNER);
	mvhline(y, x+1, 0, width-1);
	mvaddch(y+height, x+width, ACS_LRCORNER);
	mvhline(y+height, x+1, 0, width-1);
}

/*
 * Class:     de_curses_NativeCurses
 * Method:    drawHorizontalLine
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_drawHorizontalLine(JNIEnv *, jobject, jint y, jint x1, jint x2) {
	mvhline(y, x1, 0, x2-x1);
}

/*
 * Class:     de_curses_NativeCurses
 * Method:    drawVerticalLine
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_drawVerticalLine(JNIEnv *, jobject, jint x, jint y1, jint y2) {
	mvvline(y1, x, 0, y2-y1);
}

/*
 * Class:     de_curses_NativeCurses
 * Method:    drawCorner
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_drawCorner(JNIEnv *, jobject, jint x, jint y, jint type) {
	switch(type) {
		case 0:
			mvaddch(y,x, ACS_LRCORNER);
			break;
		case 1:
			mvaddch(y,x, ACS_URCORNER);
			break;
		case 2:
			mvaddch(y,x, ACS_ULCORNER);
			break;
		case 3:
			mvaddch(y,x, ACS_LLCORNER);
			break;
		default:
			break;
	}
}

JNIEXPORT void JNICALL Java_de_curses_NativeCurses_drawTee(JNIEnv *, jobject, jint x, jint y, jint type) {
	switch(type) {
		case 0:
			mvaddch(y,x, ACS_LTEE);
			break;
		case 1:
			mvaddch(y,x, ACS_RTEE);
			break;
		case 2:
			mvaddch(y,x, ACS_BTEE);
			break;
		case 3:
			mvaddch(y,x, ACS_TTEE);
			break;
		case 4:
			mvaddch(y,x, ACS_PLUS);
			break;
		default:
			break;
	}
}
