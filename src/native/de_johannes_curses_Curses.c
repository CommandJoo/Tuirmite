#define NCURSES_WIDECHAR 1
#define COLORS
#include "de_johannes_curses_Curses.h"
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <ncurses.h>
#include <locale.h>
#include <stdlib.h>
#include <string.h>

WINDOW* keywin;

// init()
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_init(JNIEnv *env,
                                                           jobject obj) {
  setlocale(LC_ALL, "");
  
  // initialize standard curses features
  initscr();
  // cbreak();
  keywin = newwin(0, 0, 0, 0);
  raw(); // accept ctrl-c as getch() args
  noecho();
  keypad(keywin, TRUE);
  mousemask(ALL_MOUSE_EVENTS | REPORT_MOUSE_POSITION, NULL);
  curs_set(0);
  set_escdelay(0);
  // set pre-definied colors
  start_color();
  use_default_colors();
  
  init_extended_pair(de_johannes_curses_Curses_BLACK, COLOR_BLACK, COLOR_BLACK);
  init_extended_pair(de_johannes_curses_Curses_LIGHT_GRAY, COLOR_WHITE, COLOR_BLACK);
  init_extended_pair(de_johannes_curses_Curses_DARK_RED, COLOR_RED, COLOR_BLACK);
  init_extended_pair(de_johannes_curses_Curses_DARK_GREEN, COLOR_GREEN, COLOR_BLACK);
  init_extended_pair(de_johannes_curses_Curses_DARK_BLUE, COLOR_BLUE, COLOR_BLACK);
  init_extended_pair(de_johannes_curses_Curses_DARK_CYAN, COLOR_CYAN, COLOR_BLACK);
  init_extended_pair(de_johannes_curses_Curses_DARK_MAGENTA, COLOR_MAGENTA, COLOR_BLACK);
  init_extended_pair(de_johannes_curses_Curses_DARK_YELLOW, COLOR_YELLOW, COLOR_BLACK);
}

JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_destroy(JNIEnv *,
                                                              jobject) {
  clear();
  curs_set(1);
  endwin();
}

// cls()
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_cls(JNIEnv *env,
                                                          jobject obj) {
  clear();
}

// setCursor()
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_setCursor(JNIEnv *env,
                                                                jobject obj,
                                                                jboolean flag) {
  if (flag) {
    curs_set(1);
  } else {
    curs_set(0);
  }
}

// moveCursor()
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_moveCursor(JNIEnv *env,
                                                                 jobject obj,
                                                                 jint x,
                                                                 jint y) {
  move(y, x);
  // refresh
}

// setColor()
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_setColor(JNIEnv *env,
                                                               jobject obj,
                                                               jint color) {
  wattroff(stdscr, A_BOLD);
  if (color == de_johannes_curses_Curses_LIGHT_RED) {
    wattron(stdscr, COLOR_PAIR(de_johannes_curses_Curses_DARK_RED) | A_BOLD);
  } else if (color == de_johannes_curses_Curses_LIGHT_GREEN) {
    wattron(stdscr, COLOR_PAIR(de_johannes_curses_Curses_DARK_GREEN) | A_BOLD);
  } else if (color == de_johannes_curses_Curses_LIGHT_BLUE) {
    wattron(stdscr, COLOR_PAIR(de_johannes_curses_Curses_DARK_BLUE) | A_BOLD);
  } else if (color == de_johannes_curses_Curses_LIGHT_CYAN) {
    wattron(stdscr, COLOR_PAIR(de_johannes_curses_Curses_DARK_CYAN) | A_BOLD);
  } else if (color == de_johannes_curses_Curses_LIGHT_MAGENTA) {
    wattron(stdscr,
            COLOR_PAIR(de_johannes_curses_Curses_DARK_MAGENTA) | A_BOLD);
  } else if (color == de_johannes_curses_Curses_LIGHT_YELLOW) {
    wattron(stdscr, COLOR_PAIR(de_johannes_curses_Curses_DARK_YELLOW) | A_BOLD);
  } else if (color == de_johannes_curses_Curses_DARK_GRAY) {
    wattron(stdscr, COLOR_PAIR(de_johannes_curses_Curses_BLACK) | A_BOLD);
  } else if (color == de_johannes_curses_Curses_WHITE) {
    wattron(stdscr, COLOR_PAIR(de_johannes_curses_Curses_LIGHT_GRAY) | A_BOLD);
  } else {
    wattron(stdscr, COLOR_PAIR(color) | A_NORMAL);
  }
}

JNIEXPORT jint JNICALL Java_de_johannes_curses_Curses_defineColor(
    JNIEnv *, jobject, jint color, jfloat r, jfloat g, jfloat b) {
  init_extended_color(color, r * 1000, g * 1000, b * 1000);
  return color;
}

JNIEXPORT jint JNICALL Java_de_johannes_curses_Curses_defineColorPair(
    JNIEnv *, jobject, jint pair, jint foreground, jint background) {
  init_extended_pair(pair, foreground, background);
  return pair;
}

// print()
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_print(JNIEnv *env,
                                                            jobject obj,
															jchar ch) {
  addch(ch);
}

JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_printstr(JNIEnv *env,
                                                               jobject obj,
                                                               jstring str) {
  const char *txt = (*env)->GetStringUTFChars(env, str, 0);
  printw(txt);
  (*env)->ReleaseStringUTFChars(env, str, txt);
}

JNIEXPORT jint JNICALL Java_de_johannes_curses_Curses_getch(JNIEnv *env,
                                                            jobject obj) {
  return wgetch(keywin);
}

JNIEXPORT jobject JNICALL
Java_de_johannes_curses_Curses_getMouseEvent(JNIEnv* env, jobject obj) {
  jclass mouseClass = (*env)->FindClass(env, "de/johannes/curses/Mouse");
  jobject newMouse = (*env)->AllocObject(env, mouseClass); 

  jfieldID deviceField = (*env)->GetFieldID(env, mouseClass, "deviceId", "S");
  jfieldID xField = (*env)->GetFieldID(env, mouseClass, "x", "I");
  jfieldID yField = (*env)->GetFieldID(env, mouseClass, "y", "I");
  jfieldID stateField = (*env)->GetFieldID(env, mouseClass, "state", "I");

  MEVENT event;

  if (getmouse(&event) == OK) {
	  /* (*env)->SetShortField(env, newMouse, deviceField, event.id);*/
    (*env)->SetIntField(env, newMouse, xField, event.x);
    (*env)->SetIntField(env, newMouse, yField, event.y);
    (*env)->SetIntField(env, newMouse, stateField, event.bstate);

    return newMouse;
  }
  return NULL;
}

/f015i*
 * Class:     de_johannes_curses_Curses
 * Method:    inch
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_de_johannes_curses_Curses_inch(JNIEnv *, jobject) {
  return inch() & A_CHARTEXT;
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    moveinch
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_de_johannes_curses_Curses_moveinch(JNIEnv *,
                                                               jobject, jint x,
                                                               jint y) {
  return mvinch(y, x) & A_CHARTEXT;
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    attron
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_attron(JNIEnv *, jobject,
                                                             jint attr) {
  switch (attr) {
  case 0:
    attron(A_REVERSE);
    break;
  case 1:
    attron(A_DIM);
    break;
  case 2:
    attron(A_ITALIC);
    break;
  case 3:
    attron(A_INVIS);
    break;
  case 4:
    attron(A_BLINK);
    break;
  case 5:
    attron(A_UNDERLINE);
    break;
  default:
    break;
  }
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    attroff
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_attroff(JNIEnv *, jobject,
                                                              jint attr) {
  switch (attr) {
  case 0:
    attroff(A_REVERSE);
    break;
  case 1:
    attroff(A_DIM);
    break;
  case 2:
    attroff(A_ITALIC);
    break;
  case 3:
    attroff(A_INVIS);
    break;
  case 4:
    attroff(A_BLINK);
    break;
  case 5:
    attroff(A_UNDERLINE);
    break;
  default:
    break;
  }
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    getHeight
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_de_johannes_curses_Curses_getHeight(JNIEnv *,
                                                                jobject) {
  return getmaxy(stdscr);
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    getWidth
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_de_johannes_curses_Curses_getWidth(JNIEnv *,
                                                               jobject) {
  return getmaxx(stdscr);
}

JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_drawBox(
    JNIEnv *, jobject obj, jint x, jint y, jint width, jint height) {
  mvaddch(y, x, ACS_ULCORNER);
  mvvline(y + 1, x, 0, height - 1);
  mvaddch(y, x + width, ACS_URCORNER);
  mvvline(y + 1, x + width, 0, height - 1);
  mvaddch(y + height, x, ACS_LLCORNER);
  mvhline(y, x + 1, 0, width - 1);
  mvaddch(y + height, x + width, ACS_LRCORNER);
  mvhline(y + height, x + 1, 0, width - 1);
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    drawHorizontalLine
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_drawHorizontalLine(
    JNIEnv *, jobject, jint y, jint x1, jint x2) {
  mvhline(y, x1, 0, x2 - x1);
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    drawVerticalLine
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_drawVerticalLine(
    JNIEnv *, jobject, jint x, jint y1, jint y2) {
  mvvline(y1, x, 0, y2 - y1);
}

/*
 * Class:     de_johannes_curses_Curses
 * Method:    drawCorner
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_drawCorner(JNIEnv *,
                                                                 jobject,
                                                                 jint x, jint y,
                                                                 jint type) {
  switch (type) {
  case 0:
    mvaddch(y, x, ACS_LRCORNER);
    break;
  case 1:
    mvaddch(y, x, ACS_URCORNER);
    break;
  case 2:
    mvaddch(y, x, ACS_ULCORNER);
    break;
  case 3:
    mvaddch(y, x, ACS_LLCORNER);
    break;
  default:
    break;
  }
}

JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_drawTee(JNIEnv *, jobject,
                                                              jint x, jint y,
                                                              jint type) {
  switch (type) {
  case 0:
    mvaddch(y, x, ACS_LTEE);
    break;
  case 1:
    mvaddch(y, x, ACS_RTEE);
    break;
  case 2:
    mvaddch(y, x, ACS_BTEE);
    break;
  case 3:
    mvaddch(y, x, ACS_TTEE);
    break;
  case 4:
    mvaddch(y, x, ACS_PLUS);
    break;
  default:
    break;
  }
}

JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_drawArrow(JNIEnv *,
                                                                jobject, jint x,
                                                                jint y,
                                                                jint type) {
  switch (type) {
  case 0:
    mvaddch(y, x, ACS_LARROW);
    break;
  case 1:
    mvaddch(y, x, ACS_RARROW);
    break;
  case 2:
    mvaddch(y, x, ACS_UARROW);
    break;
  case 3:
    mvaddch(y, x, ACS_DARROW);
    break;
  default:
    break;
  }
}

JNIEXPORT void JNICALL Java_de_johannes_curses_Curses_refresh(JNIEnv *,
                                                              jobject) {
  refresh();
}
