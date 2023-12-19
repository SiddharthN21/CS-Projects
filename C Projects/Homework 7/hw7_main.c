/* HW6 main function
 * Spring 2023, CS2400
 */

#include "hw7.h"

#include <stdio.h>
#include <string.h>

course_t g_course_array[MAX_COURSES];
int g_course_count;

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
 * hw7.c. User input is used to determine which function to run and what input
 * is passed to it. Upon completion, the function returns zero.
 */

int main() {
  char c = ' ';
  int read = 0;


  char database_file[100] = "";

  int value = 0;

  int num_courses = 0;

  char course_name[100] = "";
  int index = 0;

  int good_file = 0;

  while (1) {
    printf("\nOPTIONS:\n"
           "0) Quit\n"
           "1) read_courses()\n"
           "2) link_courses()\n"
           "3) find_longest_chain()\n"
           "4) find_max_hours_chain()\n"
           "5) print_course()\n"
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
        /* read filename */
        printf("\nEnter the name of an input file to use: ");
        scanf("%s", database_file);

        /* run read_courses and print result */
        num_courses = read_courses(database_file);
        printf("\nThe value returned is %d\n", num_courses);

        if (num_courses > 1) {
          good_file = 1;
        }
        break;
      case 2:

        if (good_file != 1) {
          printf("\ng_course_array is not loaded, read courses into the array\n");
          break;
        }

        /* run link_courses() */
        link_courses();
        break;
      case 3:
        /* check if the array is good */
        if (good_file != 1) {
          printf("\ng_course_array is not loaded, read courses into the array\n");
          break;
        }
        /* read course_title */
        printf("\nEnter the course code: ");
        scanf("%s", course_name);

        /* run find_longest_chain and print the returned value */
        value = find_longest_chain(course_name);
        printf("\nThe value returned is %d\n", value);
        break;
      case 4:

        /* check if the array is good */
        if (good_file != 1) {
          printf("\ng_course_array is not loaded, read courses into the array\n");
          break;
        }

        /* get the index from the user */
        printf("Enter the course index: ");
        read = scanf("%d%c", &index, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("Enter the index: ");
          read = scanf("%d%c", &index, &c);
        }

        /* run find_max_hours_chain() and print the return value */
        value = find_max_hours_chain(index);
        printf("\nThe value returned is %d\n", value);

        break;
      case 5:

        /* check if the array is good */
        if (good_file != 1) {
          printf("\ng_course_array is not loaded, read courses into the array\n");
          break;
        }

        /* get the index from the user */
        printf("Enter the course index: ");
        read = scanf("%d%c", &index, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("Enter the index: ");
          read = scanf("%d%c", &index, &c);
        }

        print_course(g_course_array[index]);
        break;
      default:
        printf("\nInvalid selection.\n");
        break;
    }
  }

  return 0;
} /* main() */