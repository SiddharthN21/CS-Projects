
/* Siddharth Nadgaundi, hw3.c, CS 24000, Spring 2023
 * Last updated Feb 6, 2023
 */

/* Add any includes here */
#include "hw3.h"

#include <ctype.h>
#include <math.h>
#include <stdio.h>
#include <string.h>
#include <strings.h>


/* Define your global variables here */

int g_player_count = 0;
char g_player_name[MAX_PLAYERS][NUM_ATTR][MAX_NAME_LEN] = {0};
int g_attributes[MAX_PLAYERS][NUM_ATTR] = {0};
int g_shooting[MAX_PLAYERS][NUM_COLS] = {0};
int g_statistics[MAX_PLAYERS][NUM_COLS] = {0};

/*
 * This function is used to read in the values provided by the input file
 * into the global variables provided.
 * A while loop is used to read in each line of the file into the variables.
 * During each iteration of the loop, the variables are compared with their
 * expected values. In case any particular data value does not meet the
 * expected requirements, the file pointer is closed and the corrresponding
 * error is provided. If all the conditions are passed, the values are
 * assigned to the corresponding array indices and the total number of players
 * is returned.
 */

int read_tables(char *in_file) {
  FILE *fp = NULL;
  char buff[2000] = {0};
  char player_name[MAX_NAME_LEN] = {0};
  char position[MAX_NAME_LEN] = {0};
  char height_unit[MAX_UNIT_LEN] = {0};
  char weight_unit[MAX_UNIT_LEN] = {0};
  int height = 0;
  int weight = 0;
  int att_2 = 0;
  int made_2 = 0;
  int att_3 = 0;
  int made_3 = 0;
  int att_ft = 0;
  int made_ft = 0;
  int rebound = 0;
  int assist = 0;
  int steal = 0;
  int block = 0;
  int turnover = 0;
  int foul = 0;
  int ret = 0;
  int total_players = 0;
  char my_len_unit_cm[MAX_UNIT_LEN] = "cm";
  char my_len_unit_in[MAX_UNIT_LEN] = "in";
  char my_wt_unit_lbs[MAX_UNIT_LEN] = "lbs";
  char my_wt_unit_kg[MAX_UNIT_LEN] = "kg";

  fp = fopen(in_file, "r");
  if (fp == NULL) {
    return FILE_READ_ERR;
  }

  while (fscanf(fp, "%[^\n] ", buff) != EOF) {
    ret = sscanf(buff, "%[^,],%[^|]|%d%[^,],%d%[^|]|%d,%d,%d,%d,%d,%d|%d,%d," \
    "%d,%d,%d,%d\n", player_name, position, &height, height_unit, &weight,
    weight_unit, &att_2, &made_2, &att_3, &made_3, &att_ft, &made_ft,
    &rebound, &assist, &steal, &block, &turnover, &foul);

    if ((ret != 18) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return RECORD_ERROR;
    }

    if (!((strcmp(height_unit, my_len_unit_cm) == 0) ||
    (strcmp(height_unit, my_len_unit_in ) == 0))) {
      fclose(fp);
      fp = NULL;
      g_player_count = total_players;
      return RECORD_ERROR;
    }

    if (strcmp(height_unit, my_len_unit_cm) == 0) {
      height = round(height * IN_PER_CM);
    }

    if (!((strcmp(weight_unit, my_wt_unit_kg) == 0) ||
    (strcmp(weight_unit, my_wt_unit_lbs) == 0))) {
      fclose(fp);
      fp = NULL;
      g_player_count = total_players;
      return RECORD_ERROR;
    }

    if (strcmp(weight_unit, my_wt_unit_kg) == 0) {
      weight = round(weight * LB_PER_KG);
    }

    if ((att_2 < 0) || (made_2 < 0) || (att_3 < 0) || (made_3 < 0) ||
    (att_ft < 0) || (made_ft < 0)) {
      fclose(fp);
      fp = NULL;
      return BAD_DATA;
    }

    if ((rebound < 0) || (assist < 0) || (steal < 0) || (block < 0) ||
    (turnover < 0) || (foul < 0)) {
      fclose(fp);
      fp = NULL;
      return BAD_DATA;
    }

    strcpy(g_player_name[total_players][0], player_name);
    strcpy(g_player_name[total_players][1], position);

    g_attributes[total_players][0] = height;
    g_attributes[total_players][1] = weight;

    g_shooting[total_players][0] = att_2;
    g_shooting[total_players][1] = made_2;
    g_shooting[total_players][2] = att_3;
    g_shooting[total_players][3] = made_3;
    g_shooting[total_players][4] = att_ft;
    g_shooting[total_players][5] = made_ft;

    g_statistics[total_players][0] = rebound;
    g_statistics[total_players][1] = assist;
    g_statistics[total_players][2] = steal;
    g_statistics[total_players][3] = block;
    g_statistics[total_players][4] = turnover;
    g_statistics[total_players][5] = foul;

    total_players++;
    if (total_players >= MAX_PLAYERS) {
      fclose(fp);
      fp = NULL;
      g_player_count = total_players;
      return OUT_OF_BOUNDS;
    }
  }

  if (total_players == 0) {
    fclose(fp);
    fp = NULL;
    g_player_count = total_players;
    return NO_DATA_POINTS;
  }
  fclose(fp);
  fp = NULL;
  g_player_count = total_players;
  return total_players;
} /* read_tables() */

/*
 * This function is used to calculate the maximum points made by a player and
 * return the corresponding index.
 * A for loop is used to iterate over the global variable g_player_count.
 * During each iteration, the necessary calculations and comparisons are
 * performed in order to assign the index of the player who scored the most
 * points to top_index and return the value.
 */

int find_top_scorer(void) {
  int player_score = 0;
  int top_score = 0;
  int top_index = -1;

  if (g_player_count == 0) {
    return NO_DATA_POINTS;
  }

  for (int i = 0; i < g_player_count; i++) {
    player_score = ((g_shooting[i][1] * 2) + (g_shooting[i][3] * 3) +
    (g_shooting[i][5]) * 1);
    if (player_score > top_score) {
      top_score = player_score;
      top_index = i;
    }
  }
  return top_index;
} /* find_top_scorer() */

/*
 * This function is used to return the index of the player with thr highest
 * accuracy.
 * This is found out by calculating the percentage of attempts that are
 * successful through a for loop iterating over the number of players. During
 * each iteration, the calculations are assigned to the corresponding variables
 * and compared using if statements. The corresponding errors are returned when
 * g_player_count is 0 or no attempts are made.
 */

int find_most_accurate_player(void) {
  float player_accuracy = 0.0;
  float top_accuracy = 0.0;
  int top_index = 0;
  int player_attempts = 0;
  int max_attempts = 0;
  int player_height = 0;
  int lowest_height = 0;

  if (g_player_count == 0) {
    return NO_DATA_POINTS;
  }

  for (int i = 0; i < g_player_count; i++) {
    player_attempts = g_shooting[i][0] + g_shooting[i][2] + g_shooting[i][4];
    player_height = g_attributes[i][0];
    player_accuracy = (((float) g_shooting[i][1] + g_shooting[i][3] +
    g_shooting[i][5]) / player_attempts);

    if (player_accuracy > top_accuracy) {
      top_accuracy = player_accuracy;
      top_index = i;
      max_attempts = player_attempts;
      lowest_height = player_height;
    }
    else if (player_accuracy == top_accuracy ) {
      if ( player_attempts > max_attempts) {
        top_accuracy = player_accuracy;
        top_index = i;
        max_attempts = player_attempts;
        lowest_height = player_height;
      }
      else if (player_attempts == max_attempts) {
        if (player_height < lowest_height) {
          top_accuracy = player_accuracy;
          top_index = i;
          max_attempts = player_attempts;
          lowest_height = player_height;
        }
      }
    }
  }
  if (max_attempts == 0) {
    return NO_ATTEMPTS;
  }
  return top_index;
} /* find_most_accurate_player() */

/*
 * This function is used th determine the number of players in a particular
 * weight range through the provided parameters.
 * The variables and parameters are checked for errors before entering the
 * for loop. Within the for loop iterating over all the players, if the current
 * weight is withing the lower and upper bound, wt_range_Count is incremented.
 * If the count is greater than 0, it is returned at the end of the function.
 */

int count_players_in_weight_range(int lower_bound, int upper_bound) {
  int wt_range_count = 0;

  if (g_player_count == 0) {
    return NO_DATA_POINTS;
  }

  if (upper_bound < lower_bound) {
    return BAD_RANGE;
  }
  for (int i = 0; i < g_player_count; i++) {
    if ((g_attributes[i][1] >= lower_bound) &&
    (g_attributes[i][1] <= upper_bound)) {
      wt_range_count++;
    }
  }

  if (wt_range_count == 0) {
    return NO_SUCH_DATA;
  }
  else {
    return wt_range_count;
  }
} /* count_players_in_weight_range() */

/*
 * This function is used to return the maximum total attempts of a player in a
 * particular position.
 * A for loop is used to iterate over all players and the string case compare
 * function is used th check if the passed value is equivalent to any of the
 * positions in the file. If a match is found, the necessary calculations are
 * performed and assigned to max_attempts. If max_attempts is greater than 0,
 * it is returned at the end of the function.
 */

int find_most_attempts_player(char* given_position) {
  int player_attemps = 0;
  int max_attempts = 0;
  char player_position[MAX_NAME_LEN] = {0};

  if (g_player_count == 0) {
    return NO_DATA_POINTS;
  }

  for (int i = 0; i < g_player_count; i++) {
    strcpy(player_position, g_player_name[i][1]);
    if ((strcasecmp(player_position, given_position)) == 0) {
      player_attemps = (g_shooting[i][0] + g_shooting[i][2] + g_shooting[i][4]);
      if (player_attemps > max_attempts) {
        max_attempts = player_attemps;
      }
    }
  }
  if (max_attempts == 0) {
    return NO_SUCH_DATA;
  }
  return max_attempts;
} /* find_most_attempts_player() */

/*
 * This function is used to calculate the grade of a player passed as a
 * parameter in the function.
 * A while loop is used to iterate over all the players. If a player matches
 * the one passsed as a parameter, the necessary calculations are performed
 * and stored in temporary variables in order to assign it into player_grade.
 * In case the grade is leass than 0, BAD_PLAYER is returned.
 */

float calculate_coach_grade(char *player_name) {
  int total_players = 0;
  float player_grade = 0.0;
  float temp3 = 0.0;
  int temp1 = 0;
  int temp2 = 0;
  int rebound = 0;
  int assist = 0;
  int steal = 0;
  int block = 0;
  int turnover = 0;
  int foul = 0;

  if (g_player_count == 0) {
    return NO_DATA_POINTS;
  }

  while (total_players < g_player_count) {
    if (strcmp(g_player_name[total_players][0], player_name) == 0) {
      rebound = g_statistics[total_players][0];
      assist = g_statistics[total_players][1];
      steal = g_statistics[total_players][2];
      block = g_statistics[total_players][3];
      turnover = g_statistics[total_players][4];
      foul = g_statistics[total_players][5];

      temp1 = ((rebound * 25) + (assist * 20) + (steal * 35) + (block * 25));
      temp2 = ((turnover * 15) + (foul * 20));
      temp3 = (float) (rebound + assist + steal + block + turnover + foul + 1);

      player_grade = (temp1 - temp2) / temp3;

      if (player_grade < 0.0) {
        return BAD_PLAYER;
      }
      else {
        return player_grade;
      }
    }
    total_players++;
  }
  return NO_SUCH_DATA;
} /* calculate_coach_grade() */

/*
 * This function is used to write the player grades to out_file.
 * To do this, a for loop is used to iterate over all the players and during
 * each iteration, calculate_coach_grade() is called with the current player
 * name in order to get the player's grade. If the player has a negative grade
 * it is increased to 0.00. All the grades are then truncated to 2 decimal
 * places and written to the out file.
 */

int calculate_all_players_grade(char *out_file) {
  FILE *file_wptr = NULL;
  float player_grade = 0.0;
  char player_name[MAX_NAME_LEN] = {0};
  int ret = 0;

  if (g_player_count == 0) {
    return NO_DATA_POINTS;
  }

  file_wptr = fopen(out_file, "w");
  if (file_wptr == NULL) {
    return FILE_WRITE_ERR;
  }

  for (int i = 0; i < g_player_count; i++) {
    strcpy(player_name, g_player_name[i][0]);
    player_grade = calculate_coach_grade(player_name);
    if (player_grade < 0.0) {
      player_grade = 0.00;
    }
    ret = fprintf(file_wptr, "%s: %.2f\n", player_name, player_grade);
    if (ret == 0) {
      fclose(file_wptr);
      file_wptr = NULL;
      return FILE_WRITE_ERR;
    }
  }
  fclose(file_wptr);
  file_wptr = NULL;
  return OK;
} /* calculate_all_players_grade() */

/*
 * This function is used to write the data in the input file to the output file
 * using the parameters of start_row and end_row.
 * First, the parameters are checked to satisfy the necessary conditions. In
 * case they do not, the corresponding error is returned. If the conditions
 * are satisfied, a while loop is used to iterate from the start_row to the
 * end_row. During each iteration, the values in the input file are assigned
 * to the variables through the global variables. The values are then written
 * to out_file using fprintf(). In case the value returned by fprintf() is 0,
 * FILE_WRITE_ERR is returned.
 */

int write_tables(char *out_file, int start_row, int end_row) {
  FILE *file_wptr = NULL;
  char player_name[MAX_NAME_LEN] = {0};
  char position[MAX_NAME_LEN] = {0};
  char height_unit[MAX_UNIT_LEN] = "in";
  char weight_unit[MAX_UNIT_LEN] = "lbs";
  int height = 0;
  int weight = 0;
  int att_2 = 0;
  int made_2 = 0;
  int att_3 = 0;
  int made_3 = 0;
  int att_ft = 0;
  int made_ft = 0;
  int rebound = 0;
  int assist = 0;
  int steal = 0;
  int block = 0;
  int turnover = 0;
  int foul = 0;
  int ret = 0;

  file_wptr = fopen(out_file, "w");
  if (file_wptr == NULL) {
    return FILE_WRITE_ERR;
  }

  if (g_player_count == 0) {
    fclose(file_wptr);
    file_wptr = NULL;
    return NO_DATA_POINTS;
  }

  if ((start_row < 0) || (end_row >= g_player_count) ||
  (end_row < start_row)) {
    fclose(file_wptr);
    file_wptr = NULL;
    return BAD_ROW;
  }

  while (start_row <= end_row) {
    strcpy(player_name, g_player_name[start_row][0]);
    strcpy(position, g_player_name[start_row][1]);

    height = g_attributes[start_row][0];
    weight = g_attributes[start_row][1];

    att_2 = g_shooting[start_row][0];
    made_2 = g_shooting[start_row][1];
    att_3 = g_shooting[start_row][2];
    made_3 = g_shooting[start_row][3];
    att_ft = g_shooting[start_row][4];
    made_ft = g_shooting[start_row][5];

    rebound = g_statistics[start_row][0];
    assist = g_statistics[start_row][1];
    steal = g_statistics[start_row][2];
    block = g_statistics[start_row][3];
    turnover = g_statistics[start_row][4];
    foul = g_statistics[start_row][5];

    ret = fprintf(file_wptr, "%s,%s|%d%s,%d%s|%d,%d,%d,%d,%d,%d|%d,%d,%d,%d," \
    "%d,%d\n", player_name, position, height, height_unit, weight, weight_unit,
    att_2, made_2, att_3, made_3, att_ft, made_ft, rebound, assist, steal,
    block, turnover, foul);

    if (ret == 0) {
      fclose(file_wptr);
      file_wptr = NULL;
      return FILE_WRITE_ERR;
    }
    start_row++;
  }

  fclose(file_wptr);
  file_wptr = NULL;
  return OK;
} /* write_tables() */

/* Remember, you don't need a main function!
 * It is provided by hw3_main.c or hw3_test.o
 */