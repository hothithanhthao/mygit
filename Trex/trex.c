/*
File: trex.c
Author: Thao Ho
Description: T-rex Runner Game
*/


#include <stdio.h>
#include "trex.h"


String text[] = { "1. Type character 'p' to play",
    "2. Type character 'h' to get help",
    "3. Type character 'a' to get about",
    "4. Type character 'x' to exit"
};

String left_dinosaur[] = {
    "        млпллллм",
    "        лллллллл",
    "        лллллппп",
    "л      мллллппп ",
    "ллм  мллллллммм ",
    " пллллллллллл  п",
    "   плллллллп    ",
    "    пллп  ппп   ",
    "      лм        "
};

String right_dinosaur[] = {
    "        млпллллм",
    "        лллллллл",
    "        лллллппп",
    "л      мллллппп ",
    "ллм  мллллллммм ",
    " пллллллллллл  п",
    "   плллллллп    ",
    "     плм пл     ",
    "          лм    "
};

String jump_dinosaur[] = {
    "        млпллллм",
    "        лллллллл",
    "        лллллппп",
    "л      мллллппп ",
    "ллм  мллллллммм ",
    " пллллллллллл  п",
    "   плллллллп    ",
    "     плм пл     ",
    "      лм  лм    "
};

String cactus1[8] = {
    "       ",
    "       ",
    "       ",
    "л     л",
    "л     л",
    "лмммммл",
    "   л   ",
    "   л   "
};

String cactus2[] = {
    "       ",
    "   л   ",
    "   л   ",
    "л  л  л",
    "л  л  л",
    "лмммммл",
    "   л   ",
    "   л   "
};

String cactus3[] = {
    "   л   ",
    "л  л  л",
    "лмммммл",
    "   л   ",
    "л  л  л",
    "лмммммл",
    "   л   ",
    "   л   "
};
void texBox(String text[],int lenghth){
    int height = 10;
    int width = 50;
    int starty = (LINES - height) / 2;    /* Calculating for a center placement */
    int startx = (COLS - width) / 2;    /* of the window        */
    clear();
    WINDOW *win = newwin(height,width,starty,startx);
    box(win, 0, 0);
    
    for(int i = 0; i < lenghth; i++) {
        int j=2;
        mvwaddstr(win, j + i, 6, text[i]);
    }
    wrefresh(win);
}

String* getRandomCactus() {
    
    int zeroToTwo = rand() % 3;
    switch (zeroToTwo) {
        case 0:
            return cactus1;
        case 1:
            return cactus2;
        case 2:
            return cactus3;
        default:
            return cactus1;
    }
}

void update(int delay) {
    refresh();
    usleep(delay);
}
int randomPlace(){
    int maxy = 0;
    int maxx = 0;
    int a = 0;
    getmaxyx(stdscr, maxy, maxx);
    a = maxx+(rand()%100);
    return a;
}
void menu() {
    //int maxy, maxx;
    char ch, input;
    //getmaxyx(stdscr, maxy, maxx);
    texBox(text, 4);
    while ((input = getchar()) != EOF) {
        String *cactus_a[3];
        int cactus_place[3];
        FOR3 cactus_a[i]=getRandomCactus();
        FOR3 cactus_place[i]=randomPlace();
        bool down = TRUE;
        switch (input) {
            case 'p':
                for (;;) {
                    signal(SIGWINCH, resizeHandler);
                    if ((ch = getch()) != ' ') {
                        down = TRUE;
                        printDinosaur(left_dinosaur, 10);
                        FOR3 printCactus(cactus_a[i], cactus_place[i]+6);
                        printFloor();
                        update(DELAY);
                        clear();
                        printDinosaur(right_dinosaur, 10);
                        FOR3 printCactus(cactus_a[i], cactus_place[i]);
                        printFloor();
                        update(DELAY);
                        FOR3{
                            if(-2<cactus_place[i] && cactus_place[i]<18 && down){
                                clear();
                                mvprintw((LINES-3)/2, (COLS-10)/2, "You died!");
                                update(DELAY * 5);
                                
                                endwin();
                                goto here;
                            }
                            
                            else if(cactus_place[i]<=-3){
                                cactus_place[i] = randomPlace();
                                printCactus(cactus_a[i], cactus_place[i]);
                            }
                            
                        }
                        /*
                         if(a <= 18 && b<=18 && c<=18 && down) {
                         clear();
                         mvprintw((LINES-3)/2, (COLS-10)/2, "You died!");
                         update(DELAY * 5);
                         break;
                         }
                         */
                    }
                    else {
                        clear();
                        down = FALSE;
                        printDinosaur(jump_dinosaur,0);
                        FOR3 printCactus(cactus_a[i], cactus_place[i]);
                        printFloor();
                        update(DELAY);
                        clear();
                        
                    }
                    FOR3 cactus_place[i]-=6;
                }
                endwin();
                break;
            case 'h':
                mvprintw(2,5,"This is help: continue with 'c'");
                while ((ch = getch()) != 'c');
                clear();
                update(DELAY);
                break;
            case 'a':
                mvprintw(2,5,"Author: Thao Ho - 1601849 and Dung Ho - 1604068");
                mvprintw(3,5,"Title: The second challenging game");
                mvprintw(4,5,"Description: T-rex runner game");
                update(DELAY * 20);
                clear();
                update(DELAY);
                break;
            case 'x':
                clear();
                refresh();
                _exit(1);
        }
    here:
        texBox(text, 4);
    }
}

void printFloor(){
    int maxx = 0; 
    int maxy = 0;
    int x = 0, y = 0;
    getmaxyx(stdscr, maxy, maxx);
    char array1[maxx];
    for(int i = 0; i < maxx; i++) {
        array1[i] = '_';
    }
    mvprintw(y+19,x,array1);
    //update(DELAY);
}

void printCactus(String arr[], int pos_x) {
    int len = 8;
    for(int i = 0; i < len; i++) {
        int j = 11;
        mvprintw(j + i, pos_x, arr[i]);
        
    }
    //update(DELAY);
}

void printDinosaur(String arr[], int j){
    int len = 9, x = 0;
    //int max_y = 0,max_x = 0;
    for(int i = 0; i < len; i++) {
        mvprintw(j + i, x,arr[i]);
    }
    
}

void* resizeHandler(int sig)
{
    int nh = 0;
    int nw = 0;
    return getmaxyx(stdscr, nh, nw);  /* get the new screen size */
}




