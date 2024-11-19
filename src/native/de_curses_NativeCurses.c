#include "stdio.h"
#include "de_curses_NativeCurses.h"
#include <curses.h>
#include <locale.h>
#include "string.h"

// init()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_init(JNIEnv * env, jobject obj) {
	setlocale(LC_CTYPE, "");
    // initialize standard curses features
    initscr();
    cbreak();
    noecho();
    keypad(stdscr, TRUE);
    curs_set(0);
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
	curs_set(1);
	clear();
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

// print()
JNIEXPORT void JNICALL Java_de_curses_NativeCurses_print(JNIEnv * env, jobject obj, jchar ch) {
    addch(ch);
    refresh();
}

JNIEXPORT void JNICALL Java_de_curses_NativeCurses_printstr(JNIEnv * env, jobject obj, jstring str) {
    const char *txt = (*env)->GetStringUTFChars(env, str, 0);
	for(int i = 0; i < strlen(txt); i++) {
		addch(txt[i]);
	}
	refresh();
    (*env)->ReleaseStringUTFChars(env, str, txt);
}


JNIEXPORT jint JNICALL Java_de_curses_NativeCurses_getch(JNIEnv * env, jobject obj) {
    flushinp();
    int ch = getch();
    return ch;
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
