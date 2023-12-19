#ifndef HW8_H
#define HW8_H

#include <stdio.h>

/* Constant definitions */

/* Error codes */

#define OK                 (-1)
#define INVALID_TRANSFER   (-2)
#define NO_SUCH_CLUB       (-3)
#define NO_SUCH_PLAYER     (-4)

/* Structure Declarations */

typedef struct player {
  char *name;
  int salary;
  struct player * next_player;
} player_t;

typedef struct club {
  char *club_name;
  int goals_scored;
  int goals_lost;
  char *coach;
  int player_count;
  struct player *players;
  struct club *next_club;
} club_t;

/* Function prototypes */
int insert_player(club_t *, char *, char *, int);
int retire_player(club_t *, char *, char *);
int transfer_player(club_t *, char *, char *, char *);
club_t * find_dead_clubs(club_t *);
club_t * most_valuable_club(club_t *);
club_t * delete_club(club_t *, char *);


void print_player(player_t *);
void print_club(club_t *);
club_t *create_club(char*, int, int, char *);

#endif // HW8_H