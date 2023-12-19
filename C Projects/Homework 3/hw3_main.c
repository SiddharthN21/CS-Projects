/* Homework 3 Main function
 * CS 240 - Spring 2023
 * Last updated Dec 9, 2022
 */

#include "hw3.h"

#include <stdio.h>

/*
 * This function is used to check the input
 */

int clean_stdin()
{
  while (getchar() != '\n') {
  }
  return (1);
} /* clean_stdin() */

/*
 * This function is used to print the return value for different functions
 */

void print_return_value(int val, char * function) {
  printf("The value returned by the %s() was: %d ", function, val);
  switch (val) {
    case FILE_READ_ERR:
      printf("(FILE_READ_ERR)\n");
      break;
    case FILE_WRITE_ERR:
      printf("(FILE_WRITE_ERR)\n");
      break;
    case RECORD_ERROR:
      printf("(RECORD_ERROR)\n");
      break;
    case OUT_OF_BOUNDS:
      printf("(OUT_OF_BOUNDS)\n");
      break;
    case NO_DATA_POINTS:
      printf("(NO_DATA_POINTS)\n");
      break;
    case NO_ATTEMPTS:
      printf("(NO_ATTEMPTS)\n");
      break;
    case NO_SUCH_DATA:
      printf("(NO_SUCH_DATA)\n");
      break;
    case BAD_RANGE:
      printf("(BAD_RANGE)\n");
      break;
    case BAD_DATA:
      printf("(BAD_DATA)\n");
      break;
    case BAD_PLAYER:
      printf("(BAD_PLAYER)\n");
      break;
    case BAD_ROW:
      printf("(BAD_ROW)\n");
      break;
    default:
      printf("\n");
      break;
  }
} /* print_return_value() */

/*  This function is used to run the functions implemented in hw3.c.
 *  User input is used to determine which function to run what input
 *  is passed to it. Upon completion, the function returns zero.
 */

int main() {

  int num = 0;
  int read = 0;
  char c = ' ';
  int loaded = 0;
  char file[1000] = "";
  int start = 0;
  int end = 0;
  char position[1000] = "";
  char player_name[1000] = "";
  int val = 0;
  float float_val = 0.0;

  while (1) {
    printf("-----------------------------------------\n");
    printf("0) Quit\n"
           "1) read_tables()\n"
           "2) find_top_scorer()\n"
           "3) find_most_accurate_player()\n"
           "4) count_players_in_weight_range()\n"
           "5) find_most_attempts_player()\n"
           "6) calculate_coach_grade()\n"
           "7) calculate_all_players_grade()\n"
           "8) write_tables()\n"
           "\nSelect a function: ");

    read = scanf("%d%c", &num, &c);
    while (((read != 2) || (c != '\n')) && (clean_stdin())) {
      printf("Wrong input!\n");
      printf("Select a function: ");
      read = scanf("%d%c", &num, &c);
    }
    if ((num > 8) || (num < 0)) {
      printf("Invalid selection.\n");
      continue;
    }

    if (num == 0) {
      printf("Goodbye!\n");
      break;
    }

    if ((num != 1) && (loaded == 0)) {
      printf("You tables are not loaded, please load your tables first.\n");
      continue;
    }

    /* get file name for option 1,7,8 functions */
    if ((num == 1) || (num == 7) || (num == 8)) {
      printf("Enter the file name: ");
      scanf("%s", file);
    }

    /* get bounds for option 4,8 functions */
    if ((num == 4) || (num == 8)) {
      if (num == 4) {
        printf("Enter the lowerbound: ");
      }
      if (num == 8) {
        printf("Enter the starting row index: ");
      }
      read = scanf("%d%c", &start, &c);
      while (((read != 2) || (c != '\n')) && (clean_stdin())) {
        printf("Wrong input!\n");
        printf("Please re-enter the value: ");
        read = scanf("%d%c", &start, &c);
      }

      if (num == 4) {
        printf("Enter the upperbound: ");
      }
      if (num == 8) {
        printf("Enter the ending row index: ");
      }
      read = scanf("%d%c", &end, &c);
      while (((read != 2) || (c != '\n')) && (clean_stdin())) {
        printf("Wrong input!\n");
        printf("Please re-enter the value: ");
        read = scanf("%d%c", &end, &c);
      }
    }

    /* get the position for option 5 function */
    if (num == 5) {
      printf("Enter the position: ");
      scanf("\n%[^\n]", position);
    }

    /* get the player name for option 6 function */
    if (num == 6) {
      printf("Enter the player_name: ");
      scanf("\n%[^\n]", player_name);
    }

    switch (num) {
      case 1:
        val = read_tables(file);
        print_return_value(val, "read_tables");
        if (val > 0) {
          loaded = 1;
        }
        else {
          loaded = 0;
        }
        break;
      case 2:
        val = find_top_scorer();
        print_return_value(val, "find_top_scorer");
        break;
      case 3:
        val = find_most_accurate_player();
        print_return_value(val, "find_most_accurate_player");
        break;
      case 4:
        val = count_players_in_weight_range(start, end);
        print_return_value(val, "count_players_in_weight_range");
        break;
      case 5:
        val = find_most_attempts_player(position);
        print_return_value(val, "find_most_attempts_player");
        break;
      case 6:
        float_val = calculate_coach_grade(player_name);
        printf("The value returned by calculate_coach_grade() was: %f ", float_val);
        break;
      case 7:
        val = calculate_all_players_grade(file);
        print_return_value(val, "calculate_all_players_grade");
        break;
      case 8:
        val = write_tables(file, start, end);
        print_return_value(val, "write_tables");
        break;
      default:
        break;
    }
  }

  return 0;
} /* main() */