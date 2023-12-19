/* Homework 2
 * CS 240 - Spring 2023
 * Last updated Nov 30, 2022
 */

#include "hw2.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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
 * This function is used to run the functions implemented in hw2.c.
 * User input is used to determine which function to run what input
 * is passed to it. Upon completion, the function returns zero.
 */

int main(int argc, char **argv) {
  while (1) {
    /* list choices */
    printf("====================================================\n");
    printf("Test Menu\n");
    printf("1. average_speed_of_manufacturer()\n");
    printf("2. expected_pitstops()\n");
    printf("3. find_max_total_pitstop()\n");
    printf("4. generate_expected_cost_report()\n");
    printf("5. Exit\n");
    printf("Enter your choice: ");

    int response = 0;
    int read = 0;
    char c = ' ';

    /* obtain user response */
    read = scanf("%d%c", &response, &c);
    while (((read != 2) || (c != '\n')) && clean_stdin()) {
      printf("Wrong input!\n");
      printf("Enter your choice: ");
      read = scanf("%d%c", &response, &c);
    }

    char input_filename[MAX_FILE_LENGTH] = "";
    char output_filename[MAX_FILE_LENGTH] = "";
    char manufacturer[MAX_FIELD_LENGTH] = "";
    char id[MAX_ID_LENGTH] = "";


    switch (response) {
      case 1:
        /* get user inputs for average_speed_of_manufacturer() */
        printf("Enter the input file name: ");
        scanf("%s", input_filename);
        printf("Enter the manufacturer: ");
        scanf("%s", manufacturer);

        /* run average_speed_of_manufacturer() */
        float avg_result = average_speed_of_manufacturer(input_filename, manufacturer);

        /* print students return value of average_speed_of_manufacturer() */
        if (avg_result >= 0.0) {
          printf("average_speed_of_manufacturer(%s, %s) returned %f\n",
              input_filename, manufacturer, avg_result);
        } else {
          printf("Error! (code %d)\n", (int) avg_result);
        }
        break;

      case 2:
        /* get user inputs for expected_pitstops() */
        printf("Enter the input file name: ");
        scanf("%s", input_filename);
        printf("Enter the id: ");
        scanf("%s", id);

        /* run expected_pitstops() */
        int pitstops = expected_pitstops(input_filename, id);
        if (pitstops >= 0) {
          printf("get_max_sold(%s, %s) returned %d\n", input_filename, id, pitstops);
        } else {
          printf("Error! (code %d)\n", pitstops);
        }
        break;

      case 3:
        /* get user inputs for find_max_total_pitstops() */
        printf("Enter the input file name: "); // missing from handout??????
        scanf("%s", input_filename);
        printf("Enter the manufacturer: ");
        scanf("%s", manufacturer);

        /* run find_max_total_pitstops() */
        float max_pitstops = find_max_total_pitstop(input_filename, manufacturer);
        if (max_pitstops >= 0) {
          printf("find_max_total_pitstops(%s, %s) returned %f\n",
              input_filename, manufacturer, max_pitstops);
        } else {
          printf("Error! (code %d)\n", (int)max_pitstops);
        }
        break;

      case 4:
        /* get user inputs for generate_expected_cost_report() */
        printf("Enter the input file name: ");
        scanf("%s", input_filename);
        printf("Enter the output file name: ");
        scanf("%s", output_filename);

        /* run generate_expected_cost_report() */
        int err_code = generate_expected_cost_report(input_filename, output_filename);

        /* print results of generate_cost_report() */
        if (err_code ==  OK) {
          printf("generate_expected_cost_report(%s, %s) returned OK.\n"
                 , input_filename, output_filename);
          printf("Consider opening the output file to see its contents.\n");
        } else {
          printf("Error! (code %d)\n", err_code);
        }
        break;
      case 5:
        printf("Goodbye!\n\n");
        return (0);

      default:
        printf("Invalid choice!\n");
        break;
    }
  }
  return (0);
} /* main() */