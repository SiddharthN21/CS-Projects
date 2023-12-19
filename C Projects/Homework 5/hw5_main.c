/* HW5 main function
 * Spring 2023, CS2400
 */
#include "hw5.h"

#include <stdio.h>
#include <string.h>

/* function definition*/

cupboard_t read_cupboard_from_file(char *);

/*
 * use to read input
 */

int clean_stdin(){
  while (getchar() != '\n') {
  }
  return 1;
} /* clean_stdin() */

/*
 * use to check whether cupboard is bad or not
 */

static int compare_bad(cupboard_t cupboard) {
  return (!strcmp(cupboard.id, "BAD")) &&
    (cupboard.max_weight == -1.0) &&
    (cupboard.age == -1) &&
    (cupboard.material == -1) &&
    (cupboard.content == -1) &&
    (cupboard.storage[N_HEIGHT - 1][N_WIDTH - 1] == -1.0);
} /* compare_bad() */

/*
 * use to print the information of a cupboard struct
 */

static void print_cupboard(cupboard_t cupboard) {
  printf("\nID: %s\n", cupboard.id);
  printf("Max weight: %.2f\n", cupboard.max_weight);
  printf("Age: %d\n", cupboard.age);
  printf("Material: ");
  switch (cupboard.material) {
    case STEEL:
      printf("Steel\n");
      break;
    case WOOD:
      printf("Wood\n");
      break;
    case PLASTIC:
      printf("Plastic\n");
      break;
    case CARDBOARD:
      printf("Cardboard");
      break;
    default:
      printf("-1\n");
      break;
  }
  printf("Content: ");
  switch (cupboard.content) {
    case FOOD:
      printf("Food\n");
      break;
    case CLOTH:
      printf("Cloth\n");
      break;
    case ELECTRONICS:
      printf("Electronics\n");
      break;
    default:
      printf("-1\n");
      break;
  }
  if (!compare_bad(cupboard)) {
    printf("Storage:");
    for (int width = 0; width < N_WIDTH; width++) {
      printf(" ");
      for (int height = 0; height < N_HEIGHT; height++) {
        printf("%.2f", cupboard.storage[width][height]);
      }
    }
    printf("\n");
  }
} /* print_cupboard() */

/*
 * This function is used to run the different functions implemented in file
 * hw5.c. User input is used to determine which function to run and what input
 * is passed to it. Upon completion, the function returns zero.
 */

int main() {
  char c = ' ';
  int read = 0;


  char database_file[100] = "";
  FILE * database_fp = 0;
  int n = 0;
  cupboard_t cupboard = {0};

  char cupboard_file[100] = "";

  int type = 0;

  int width = 0;
  int height = 0;
  int mass = 0;
  char id[20] = "";
  int x_pos = 0;
  int y_pos = 0;

  int value = 0;
  float f_value = 0;

  while (1) {
    printf("\nOPTIONS:\n"
           "0) Quit\n"
           "1) read_cupboard()\n"
           "2) write_cupboard()\n"
           "3) find_actual_max_weight()\n"
           "4) find_overloaded_cupboard()\n"
           "5) potential_storage_space()\n"
           "6) place_object()\n"
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
        database_fp = fopen(database_file, "rb+");
        while (database_fp == NULL) {
          printf("Unable to open %s for input.\n", database_file);
          printf("\nEnter the name of an input file to use: ");
          scanf("%s", database_file);
          database_fp = fopen(database_file, "rb+");
        }

        /* read in position index */
        printf("\nEnter position index to read: ");
        read = scanf("%d%c", &n, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("\nEnter offset to read: ");
          read = scanf("%d%c", &n, &c);
        }

        /* run read_cupboard and print result */
        cupboard = read_cupboard(database_fp, n);
        printf("\nThe cupboard returned is \n");
        print_cupboard(cupboard);
        break;
      case 2:
        /* read filename and open file */
        printf("\nEnter the name of an input file to use: ");
        scanf("%s", database_file);
        database_fp = fopen(database_file, "rb+");
        while (database_fp == NULL) {
          printf("Unable to open %s for input.\n", database_file);
          printf("\nEnter the name of an input file to use: ");
          scanf("%s", database_file);
          database_fp = fopen(database_file, "rb+");
        }

        /* read in index to write to */
        printf("\nEnter offset to write: ");
        read = scanf("%d%c", &n, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("\nEnter offset to write: ");
          read = scanf("%d%c", &n, &c);
        }

        /* read ta information */
        printf("\nEnter a file containing a cupboard representation: ");
        fscanf(stdin, "\n%s", cupboard_file);
        cupboard = read_cupboard_from_file(cupboard_file);
        printf("\nRead the following information:\n");
        print_cupboard(cupboard);


        /*value = write_teaching_assistant(database_fp, teaching_assistant, n);
        printf("\nThe value returned is %d.\n", value);*/
        break;
      case 3:
        /* read filename and open file */
        printf("\nEnter the name of an input file to use: ");
        scanf("%s", database_file);
        database_fp = fopen(database_file, "rb+");
        while (database_fp == NULL) {
          printf("Unable to open %s for input.\n", database_file);
          printf("\nEnter the name of an input file to use: ");
          scanf("%s", database_file);
          database_fp = fopen(database_file, "rb+");
        }

        /* read in the id */
        printf("\nEnter the id: ");
        scanf("%s", id);

        /* run find_actual_max_weight and print the return value */
        f_value = find_actual_max_weight(database_fp, id);
        printf("\nThe value returned is %.2f%%.\n", f_value);
        break;
      case 4:
        /* read filename and open file */
        printf("\nEnter the name of an input file to use: ");
        scanf("%s", database_file);
        database_fp = fopen(database_file, "rb+");
        while (database_fp == NULL) {
          printf("Unable to open %s for input.\n", database_file);
          printf("\nEnter the name of an input file to use: ");
          scanf("%s", database_file);
          database_fp = fopen(database_file, "rb+");
        }

        /* run find_overloaded_cupboard and print return value */
        value = find_overloaded_cupboard(database_fp);
        printf("\nThe value returned is %d.\n", value);
        break;
      case 5:
        /* read filename and open file */
        printf("\nEnter the name of an input file to use: ");
        scanf("%s", database_file);
        database_fp = fopen(database_file, "rb+");
        while (database_fp == NULL) {
          printf("Unable to open %s for input.\n", database_file);
          printf("\nEnter the name of an input file to use: ");
          scanf("%s", database_file);
          database_fp = fopen(database_file, "rb+");
        }

        /* read in content type */
        printf("\nEnter content type (as an integer): ");
        fscanf(stdin, "%d", &type);

        /* run potential_storage_space and print return value */
        f_value = potential_storage_space(database_fp, type);
        printf("\nThe value returned is %.2f%%.\n", f_value);
        break;
      case 6:
        /* read filename and open file */
        printf("\nEnter the name of an input file to use: ");
        scanf("%s", database_file);
        database_fp = fopen(database_file, "rb+");
        while (database_fp == NULL) {
          printf("Unable to open %s for input.\n", database_file);
          printf("\nEnter the name of an input file to use: ");
          scanf("%s", database_file);
          database_fp = fopen(database_file, "rb+");
        }

        /* read in the width */
        printf("\nEnter the width: ");
        read = scanf("%d%c", &width, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("\nEnter the width: ");
          read = scanf("%d%c", &width, &c);
        }

        /* read in the height */
        printf("\nEnter the height: ");
        read = scanf("%d%c", &height, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("\nEnter the height: ");
          read = scanf("%d%c", &height, &c);
        }

        /* read in the mass */
        printf("\nEnter the mass: ");
        read = scanf("%d%c", &mass, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("\nEnter the mass: ");
          read = scanf("%d%c", &mass, &c);
        }

        /* read in the id */
        printf("\nEnter the id: ");
        scanf("%s", id);

        /* read in the x position */
        printf("\nEnter the x position: ");
        read = scanf("%d%c", &x_pos, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("\nEnter the x position: ");
          read = scanf("%d%c", &x_pos, &c);
        }

        /* read in the y position */
        printf("\nEnter the y position: ");
        read = scanf("%d%c", &y_pos, &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input!\n");
          printf("\nEnter the y position: ");
          read = scanf("%d%c", &y_pos, &c);
        }

        /* run place_object and print the result */
        value = place_object(database_fp, width, height, mass, id, x_pos, y_pos);
        printf("\nThe value returned is %d.\n", value);
        break;
      default:
        printf("\nInvalid selection.\n");
        break;
    }
  }

  return 0;
} /* main() */

/*
 * Function to read in information about a cupboard
 */

cupboard_t read_cupboard_from_file(char *file_name) {
  FILE *fp = fopen(file_name, "r");
  cupboard_t cupboard = BAD_CUPBOARD;
  if (fp == NULL) {
    return cupboard;
  }
  if (fscanf(fp, "%19[^\n]\n", cupboard.id) != 1) {
    return cupboard;
  }
  if (fscanf(fp, "%f\n", &cupboard.max_weight) != 1) {
    return cupboard;
  }
  if (fscanf(fp, "%d\n", &cupboard.age) != 1) {
    return cupboard;
  }
  if (fscanf(fp, "%d\n", ((int *)&cupboard.material)) != 1) {
    return cupboard;
  }
  if (fscanf(fp, "%d\n", ((int *)&cupboard.content)) != 1) {
    return cupboard;
  }

  for (int i = 0; i < N_WIDTH; i++) {
    for (int j = 0; j < N_HEIGHT; j++) {
      if (fscanf(fp, "%f", &cupboard.storage[i][j]) != 1) {
        return cupboard;
      }
    }
    if (fscanf(fp, "\n") != 0) {
      return cupboard;
    }
  }
  return cupboard;
} /* read_cupboard_from_file() */