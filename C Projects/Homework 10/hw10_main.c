/* Includes */

#include "hw10.h"

#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


void print_employee(employee_t *);
void print_employee_tree(employee_t *);

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
 * hw10.c. User input is used to determine which function to run and what input
 * is passed to it. Upon completion, the function returns zero.
 */

int main() {
  char c = ' ';
  int read = 0;
  int choice = 0;

  employee_t *created_employee = NULL;
  employee_t *employee_tree = NULL;
  employee_t *found_employee = NULL;

  char name[100] = "";
  int order = 0;
  int k = 0;

  //Disable buffering to suppress memory leaks
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);
  setvbuf(stdin, NULL, _IONBF, 0);

  while (1) {
    printf("\nOPTIONS:\n"
           "0) Quit\n"
           "1) create_employee()\n"
           "2) insert_employee()\n"
           "3) find_employee()\n"
           "4) delete_tree()\n"
           "5) traverse_employees()\n"
           "6) previous_employee()\n"
           "7) print created_employee\n"
           "8) print employee_tree\n"
           "9) print found_employee\n"
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
        printf("\nEnter the name: ");
        scanf("%[^\n]%*c", name);

        created_employee = create_employee(name);

        break;
      case 2:
        if (created_employee == NULL) {
          printf("Employee has not been created.\n");
          printf("Create an employee using create_employee()\n");
          break;
        }
        insert_employee(&employee_tree, created_employee);
        printf("Finished inserting employee into the employee tree\n");
        break;
      case 3:
        printf("\nEnter the name: ");
        scanf("%[^\n]%*c", name);

        found_employee = find_employee(employee_tree, name);
        break;
      case 4:
        delete_tree(&employee_tree);
        break;
      case 5:
        printf("\nEnter the traverse type (as in integer 1-4): ");
        scanf("%d", &order);
        printf("\nEnter k: ");
        scanf("%d", &k);
        if (order == 1) {
          found_employee = traverse_employees(employee_tree, PREFIX, k);
        }
        else if (order == 2) {
          found_employee = traverse_employees(employee_tree, POSTFIX, k);
        }
        else if (order == 3) {
          found_employee = traverse_employees(employee_tree, INORDER, k);
        }
        else if (order == 4) {
          found_employee = traverse_employees(employee_tree, REVERSE, k);
        }
        else {
          printf("Invalid integer\n");
        }
        break;
      case 6:
        printf("\nEnter the name: ");
        scanf("%[^\n]%*c", name);
        found_employee = previous_employee(employee_tree, name);
        break;
      case 7:
        print_employee(created_employee);
        break;
      case 8:
        print_employee_tree(employee_tree);
        break;
      case 9:
        print_employee(found_employee);
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
 * Function to print an employee.
*/

void print_employee(employee_t *employee) {
  if (employee == NULL) {
    printf("\n  [Empty Employee!]\n\n");
    return;
  }
  printf("\n  Name: %s\n", employee->name);
  if (employee->left_child_ptr) {
    printf("  Left child name: %s\n", employee->left_child_ptr->name);
  }
  else {
    printf("  Left child name: (nil)\n");
  }
  if (employee->right_child_ptr) {
    printf("  Right child name: %s\n", employee->right_child_ptr->name);
  }
  else {
    printf("  Right child name: (nil)\n");
  }
  return;
} /* print_employee() */

/*
 * Function to print binary tree in Prefix traversal order
 */

void print_employee_tree(employee_t *head_employee) {
  print_employee(head_employee);
  if (head_employee != NULL) {
    if (head_employee->left_child_ptr) {
      print_employee_tree(head_employee->left_child_ptr);
    }
    if (head_employee->right_child_ptr) {
      print_employee_tree(head_employee->right_child_ptr);
    }
  }
  return;
} /* print_employee_tree() */