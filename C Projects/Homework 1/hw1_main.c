/* CS240 Spring 2023
 * HW1 Main function
 * Last updated November 28, 2022
 */

#include "hw1.h"

#include <stdio.h>

int g_recaman_array[ARRAY_SIZE];

/*
 * cleans stdin
 */

int clean_stdin() {

  while (getchar() != '\n');
  return 1;

} /* clean_stdin() */

/*  This function is used to run the sequence_contains_number()
 *  and create_recaman_sequence() functions implemented in file
 *  hw1.c. User input is used to determine which function to run
 *  and what input is passed to it. Upon completion, the function
 *  returns zero.
 */

int main() {
  int choice = 0;
  int count = 0;
  int limit = 0;
  int first_number = 0;
  int value = 0;
  int student_return = 0;
  int read = 0;
  char c = '\0';

  while (choice != 3) {
    printf("-------------------------------------------------------\n");
    printf("Select a function to test:\n"
           "   1) create_recaman_sequence()\n"
           "   2) check_recaman_sequence()\n"
           "   3) Exit\n"
           "Your choice? ");

    read = scanf("%d%c", &choice, &c);
    while (((read != 2) || (c != '\n')) && clean_stdin()) {
      printf("Wrong input!\n");
      printf("Your choice? ");
      read = scanf("%d%c", &choice, &c);
    }
    switch (choice) {
      case 1:
        /* get user inputs for create_recaman_sequence() */
        printf("Enter the first number of the sequence: ");
        read = scanf("%d%c", &first_number, &c);
        while (((read != 2) || (c != '\n')) && clean_stdin()) {
          printf("Wrong input!\n");
          printf("Enter the first number of the sequence: ");
          read = scanf("%d%c", &first_number, &c);
        }
        printf("Enter the number of elements for the array: ");
        read = scanf("%d%c", &limit, &c);
        while (((read != 2) || (c != '\n')) && clean_stdin()) {
          printf("Wrong input!\n");
          printf("Enter the number of elements for the array: ");
          read = scanf("%d%c", &limit, &c);
        }

        /* run create_recaman_sequence() */
        student_return = create_recaman_sequence(first_number, limit);

        /* print the students results for create_recaman_sequence() */
        if (student_return == RECAMAN_ERROR) {
          printf("create_recaman_sequence returned RECAMAN_ERROR\n");
        }
        else {
          printf("The resulting sequence looks like:\n");
          for (count = 0; count < limit; count++) {
            printf("%d\t", g_recaman_array[count]);
            if (count % 10 == 9) {
              printf("\n");
            }
          }
          if (limit % 10 != 0) {
            printf("\n");
          }
        }
        break;

      case 2:
        /* get user inputs for check_recaman_sequence() */
        printf("Enter a limit: ");
        read = scanf("%d%c", &limit, &c);
        while (((read != 2) || (c != '\n')) && clean_stdin()) {
          printf("Wrong input!\n");
          printf("Enter a limit: ");
          read = scanf("%d%c", &limit, &c);
        }
        if (count < 1) {
          printf("Enter numbers to put into the array.\n");
        }

        /* load the array */
        for (count = 0; count < limit; count++) {
          printf("Element %2d: ", count);
          read = scanf("%d%c", &value, &c);
          while (((read != 2) || (c != '\n')) && clean_stdin()) {
            printf("Wrong input!\n");
            printf("Element %2d: ", count);
            read = scanf("%d%c", &value, &c);
          }
          g_recaman_array[count] = value;
        }

        /* run create_recaman_sequence() */
        student_return = check_recaman_sequence(limit);

        /* print the students results for check_recaman_sequence() */
        if (student_return == RECAMAN_ERROR) {
          printf("check_recaman_sequence returned RECAMAN_ERROR!\n");
        }
        else if (student_return == RECAMAN_CORRECT) {
          printf("check_recaman_sequence returned RECAMAN_CORRECT!\n");
        }
        else {
          printf("The recaman sequence contains an error at index %d!\n", student_return);
        }
        break;

      case 3:
        printf("Goodbye!\n");
        break;

      default:
        printf("Select one of the given options...!\n");
        break;
    }
  }

  return 0;
} /* main() */