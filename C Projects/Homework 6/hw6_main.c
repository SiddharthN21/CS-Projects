/* HW6 main function
 * Spring 2023, CS2400
 */
#include "hw6.h"

#include <stdio.h>
#include <string.h>

/*
 * use to read input
 */

int clean_stdin(){
  while (getchar() != '\n') {
  }
  return 1;
} /* clean_stdin() */

/*
 * use to print the information of a course struct
 */

static void print_course(course_t course) {
  printf("\n\tcourse name: %s\n", course.course_name);

  printf("\tprerequisites:\n");

  for (int i = 0; i < MAX_PREREQ; i++) {
    if (*course.prerequisites[i] != 0) {
      printf("\t\t%s\n", course.prerequisites[i]);
    }
  }

  printf("\tcourse type: ");
  switch (course.course_type) {
    case LECTURE:
      printf("lecture\n");
      break;
    case PSO:
      printf("pso\n");
      break;
    case LABORATORY:
      printf("laboratory\n");
      break;
    case RECITATION:
      printf("recitation\n");
      break;
    case INDEPENDENT:
      printf("independent\n");
      break;
    default:
      printf("-1\n");
      break;
  }
  printf("\tcredit hours: %d\n", course.credit_hours);

} /* print_course() */

/*
 * This function is used to run the different functions implemented in file
 * hw6.c. User input is used to determine which function to run and what input
 * is passed to it. Upon completion, the function returns zero.
 */

int main() {
  char c = ' ';
  int read = 0;


  char database_file[100] = "";

  int value = 0;

  int num_courses = 0;

  char course_name[100] = "";
  int type = 0;

  int good_file = 0;

  while (1) {
    printf("\nOPTIONS:\n"
           "0) Quit\n"
           "1) read_courses()\n"
           "2) find_prerequisite()\n"
           "3) print_table()\n"
           "Select a function: ");
    int choice = 0;
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
        /* read filename and open file */
        printf("\nEnter the name of an input file to use: ");
        scanf("%s", database_file);

        /* run read_courses and print result */
        num_courses = read_courses(database_file);
        printf("\nThe value returned is %d\n", num_courses);

        if (num_courses > 1) {
          good_file = 1;
        }
        break;
      case 3:
        if (good_file != 1) {
          printf("\ng_course_array is not loaded, read courses into the array\n");
          break;
        }
        printf("The following array was loaded:\n");

        for (int i = 0; i < num_courses; i++) {

          printf("index %d:\n", i);
          print_course(g_course_array[i]);


        }
        break;
      case 2:

        if (good_file != 1) {
          printf("\ng_course_array is not loaded, read courses into the array\n");
          break;
        }

        /* read in the course */
        printf("\nEnter the course: ");
        scanf("%[^\n]", course_name);

        /* read in the enum */
        printf("\nEnter the course type (as in integer): ");
        scanf("%d", &type);

        /* run find_prerequisite() */
        value = find_prerequisites(course_name, type);
        printf("\nThe value returned is %d\n", value);
        break;
      default:
        printf("\nInvalid selection.\n");
        break;
    }
  }

  return 0;
} /* main() */