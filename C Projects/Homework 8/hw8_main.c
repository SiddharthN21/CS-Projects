/* Includes */

#include "hw8.h"

#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void print_player(player_t *);
void print_club(club_t *);
club_t *create_club(char*, int, int, char *);

/*
 * This function is used to handle the input reading
 */

int clean_stdin() {
  while (getchar() != '\n') {
  }
  return 1;
} /* clean_stdin() */

/*
 * This function is used to run the different functions implemented in file
 * hw8.c. User input is used to determine which function to run and what input
 * is passed to it. Upon completion, the function returns zero.
 */

int main() {
  char c = ' ';
  int read = 0;
  int result = 0;
  int choice = 0;

  club_t *club1 = NULL;

  char former_club_name[1024] = "";
  char club_name[1024] = "";
  char coach_name[1024] = "";
  char player_name[1024] = "";
  int salary = 0;
  int goals_scored = 0;
  int goals_lost = 0;

  //Disable buffering to suppress memory leaks
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);
  setvbuf(stdin, NULL, _IONBF, 0);

  while (1) {
    printf("\nOPTIONS:\n"
           "0) Quit\n"
           "1) insert_player()\n"
           "2) retire_player()\n"
           "3) transfer_player()\n"
           "4) find_dead_clubs()\n"
           "5) most_valuable_club()\n"
           "6) delete_club()\n"
           "7) add a club\n"
           "Select a function: ");

    read = scanf("%d%c", &choice, &c);
    while (((read != 2) || (c != '\n')) && (clean_stdin())) {
      printf("Wrong input!\n");
      printf("Select a function: ");
      read = scanf("%d%c", &choice, &c);
    }

    if (choice == 0) {
      printf("\nGoodbye!\n\n");
      break;
    }

    switch (choice) {
      case 0:
        printf("\nGoodbye!\n\n");
        break;
      case 1:
        printf("\nEnter club name: ");
        scanf("%s", club_name);

        printf("\nEnter player name: ");
        scanf("%s", player_name);

        printf("\nEnter the player salary: ");
        read = scanf("%d%c", &salary, &c);
        while ((((read != 2) || (c != '\n')) && (clean_stdin()))) {
          printf("\nWrong input!\n");
          printf("\nEnter the player salary: ");
          read = scanf("%d%c", &salary, &c);
        }

        /* run insert_player and print result */
        result = insert_player(club1, club_name, player_name, salary);
        printf("\nElements in the club 1:\n");
        print_club(club1);


        printf("The value returned by insert_player() was %d\n", result);
        break;
      case 2:
        printf("\nEnter club name: ");
        scanf("%s", club_name);

        printf("\nEnter player name: ");
        scanf("%s", player_name);

        /* run retire_player and print result */

        result = retire_player(club1, club_name, player_name);
        printf("\nElements in the club 1:\n");
        print_club(club1);

        printf("The value returned by retire_player() was %d\n", result);
        break;
      case 3:
        printf("\nEnter former club name (the club the player is leaving): ");
        scanf("%s", former_club_name);

        printf("\nEnter new club name (the club the player is moving to): ");
        scanf("%s", club_name);

        printf("\nEnter player name: ");
        scanf("%s", player_name);

        /* run transfer_player and print result */
        result = transfer_player(club1, former_club_name, club_name, player_name);
        printf("\nElements in the club 1:\n");
        print_club(club1);

        printf("The value returned by transfer_player() was %d\n", result);

        break;
      case 4:
        /* run find_dead_clubs and print result */
        club1 = find_dead_clubs(club1);
        printf("\nElements in the club 1:\n");
        print_club(club1);

        break;
      case 5:
        /* run most_valuable_club and print result */
        club1 = most_valuable_club(club1);
        printf("\nElements in the club 1:\n");
        print_club(club1);

        break;
      case 6:


        printf("\nEnter club name: ");
        scanf("%s", club_name);

        /* run delete_club and print result */
        club1 = delete_club(club1, club_name);
        printf("\nElements in the club 1:\n");
        print_club(club1);

        break;
      case 7:
        printf("\nEnter club name: ");
        scanf("%s", club_name);

        printf("\nEnter coach name: ");
        scanf("%s", coach_name);

        printf("\nEnter goals scored: ");
        scanf("%d%c", &goals_scored, &c);

        printf("\nEnter goals lost: ");
        scanf("%d%c", &goals_lost, &c);

        club_t *temp = create_club(club_name, goals_scored, goals_lost, coach_name);
        temp->next_club = club1;
        club1 = temp;

        break;
      default:
        printf("\nInvalid input! Try again...\n");
        break;
    } /* switch (choice) */

  } /* while (1) */

  printf("Ending program\n");

  return 0;
} /* main() */

/*
 * Function to print a player
 */

void print_player(player_t * list) {
  player_t * temp = list;
  while (temp != NULL) {
    printf("    %s: $%d\n", temp->name, temp->salary);
    temp = temp->next_player;
  }
  printf("    (NULL)\n\n");
} /* print_player() */

/*
 * Function to print a list of players
 */

void print_club(club_t * list) {
  club_t * temp = list;
  while (temp != NULL) {
    printf("%s:\n  goals_scored: %d\n  goals_lost: %d\n", temp->club_name, temp->goals_scored, temp->goals_lost);
    printf("  coach: %s\n  player_count: %d\n  players:\n", temp->coach, temp->player_count);
    print_player(temp->players);
    temp = temp->next_club;
  }
  printf("(NULL)\n");
} /* print_club() */

/*
 * Function to create a club
 */

club_t *create_club(char *name, int goals_scored, int goals_lost, char *coach) {
  assert(name != NULL);
  assert(coach != NULL);

  club_t *club = malloc(sizeof(club_t));
  assert(club != NULL);

  club->club_name = malloc(strlen(name) + 1);
  assert(club->club_name != NULL);

  club->coach = malloc(strlen(coach) + 1);
  assert(club->coach != NULL);

  strcpy(club->club_name, name);
  strcpy(club->coach, coach);

  club->goals_scored = goals_scored;
  club->goals_lost = goals_lost;

  club->next_club = NULL;
  club->players = NULL;
  club->player_count = 0;

  return club;
} /* create_club() */