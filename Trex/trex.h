/*
File: trex.h
Author: Thao Ho - 1601849 and Dung Ho -1604068
Description: T-rex Runner Game
*/


#ifndef TREX_H
#define TREX_H

#include <stdio.h>
#include <ncurses.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>


#define DELAY 300000
#define FOR3 for (int i=0; i<3; i++)

typedef const char* String;

void* resizeHandler();
void printDinosaur(String [], int);
void printCactus(String [], int);
void printFloor(void);
void texBox(String [], int);
void menu(void);
void update(int);
String* getRandomCactus(void);
int randomPlace(void);


#endif /* trex_h */

