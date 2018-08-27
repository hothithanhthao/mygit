/*
File: main.c
Author: Thao Ho
Description: T-rex Runner Game
*/

#include "trex.h"
#include <stdlib.h>
#include <time.h>

int main() {
    srand(time(NULL));
    initscr();
    noecho();
    curs_set(FALSE);
    nodelay(stdscr, TRUE);
    menu();
}


