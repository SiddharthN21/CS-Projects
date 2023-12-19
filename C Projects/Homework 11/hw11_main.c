/* Includes */

#include "hw11.h"

#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

time_struct_t get_a_time(void);
void print_tree_node(tree_node_t *);
void print_tree(tree_node_t *);
void print_list_node(list_node_t *);
void print_list(list_node_t *);
void print_event_info(event_info_t *);
void print_time_struct(time_struct_t);

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
 * hw11.c. User input is used to determine which function to run and what input
 * is passed to it. Upon completion, the function returns zero.
 */

int main() {

  char c = ' ';
  int read = 0;
  int choice = 0;

  time_struct_t time = {0};
  time_struct_t end_time = {0};

  event_info_t *event_ptr = NULL;
  list_node_t *list_ptr = NULL;
  tree_node_t *tree_ptr = NULL;

  char name[100] = "";

  int result = 0;

  //Disable buffering to suppress memory leaks
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);
  setvbuf(stdin, NULL, _IONBF, 0);

  while (1) {
    printf("\nOPTIONS:\n"
           "0) Quit\n"
           "1) create_event()\n"
           "2) add_event()\n"
           "3) count_expired_events()\n"
           "4) add_to_calendar()\n"
           "5) free_up_schedule()\n"
           "6) delete_event_schedule()\n"
           "7) print event_ptr\n"
           "8) print list_ptr (user calendar)\n"
           "9) print tree_ptr (storage of events)\n"
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
        scanf("%s", name);
        printf("Enter the starting time:\n");
        time = get_a_time();
        printf("Enter the ending time:\n");
        end_time = get_a_time();

        create_event(&event_ptr, name, time, end_time);

        break;
      case 2:
        add_event(&tree_ptr, event_ptr);
        break;
      case 3:
        printf("Enter the time:\n");
        time = get_a_time();
        result = count_expired_events(tree_ptr, time);
        printf("%d was returned by count_expired_events()\n", result);
        break;
      case 4:
        add_to_calendar(tree_ptr, &list_ptr);
        break;
      case 5:
        printf("Enter the number of minutes: ");
        int num_minutes = 0;
        read = scanf("%d%c", &(num_minutes), &c);
        while (((read != 2) || (c != '\n')) && (clean_stdin())) {
          printf("Wrong input.\n");
          printf("Enter the number of minutes: ");
          read = scanf("%d%c", &(num_minutes), &c);
        }
        free_up_schedule(&list_ptr, num_minutes);
        break;
      case 6:
        delete_event_schedule(&tree_ptr);
        break;
      case 7:
        print_event_info(event_ptr);
        break;
      case 8:
        print_list(list_ptr);
        break;
      case 9:
        print_tree(tree_ptr);
        break;
      default:
        printf("\nInvalid input! Try again...\n");
        break;
    } /* switch (choice) */

  } /* while (1) */

  printf("Ending program\n");

  return 0;
} /* main() */



time_struct_t get_a_time(void) {

  time_struct_t the_time = {0};

  char c = ' ';
  int read = 0;

  printf("Enter the year: ");
  read = scanf("%d%c", &(the_time.year), &c);
  while (((read != 2) || (c != '\n')) && (clean_stdin())) {
    printf("Wrong input.\n");
    printf("Enter the year: ");
    read = scanf("%d%c", &(the_time.year), &c);
  }

  printf("Enter the month: ");
  read = scanf("%d%c", &(the_time.month), &c);
  while (((read != 2) || (c != '\n')) && (clean_stdin())) {
    printf("Wrong input.\n");
    printf("Enter the month: ");
     read = scanf("%d%c", &(the_time.month), &c);
   }

  printf("Enter the day: ");
  read = scanf("%d%c", &(the_time.day), &c);
  while (((read != 2) || (c != '\n')) && (clean_stdin())) {
    printf("Wrong input.\n");
    printf("Enter the day: ");
    read = scanf("%d%c", &(the_time.day), &c);
  }

  printf("Enter the hour: ");
  read = scanf("%d%c", &(the_time.hour), &c);
  while (((read != 2) || (c != '\n')) && (clean_stdin())) {
    printf("Wrong input.\n");
    printf("Enter the hour: ");
    read = scanf("%d%c", &(the_time.hour), &c);
  }

  printf("Enter the minute: ");
  read = scanf("%d%c", &(the_time.minute), &c);
  while (((read != 2) || (c != '\n')) && (clean_stdin())) {
    printf("Wrong input.\n");
    printf("Enter the minute: ");
    read = scanf("%d%c", &(the_time.minute), &c);
  }

  return the_time;
}

/*
 * prints an individual node
 */

void print_tree_node(tree_node_t *event) {
  if (event == NULL) {
    printf("\n\t[Null Node!]\n\n");
    return;
  }
  printf("\tNode: %p\n", event);
  print_event_info(event->event_info);
  printf("\tLeft Child:\n");
  if (event->left_child_ptr) {
    printf("\t\t%p\n", event->left_child_ptr);
    if (event->left_child_ptr->event_info) {
      printf("\t\t%s\n", event->left_child_ptr->event_info->name);
    }
  }
  else {
    printf("\t\tNULL\n\n");
  }
  printf("\tRight Child:\n");
  if (event->right_child_ptr) {
    printf("\t\t%p\n", event->right_child_ptr);
    if (event->right_child_ptr->event_info) {
      printf("\t\t%s\n", event->right_child_ptr->event_info->name);
    }
  }
  else {
    printf("\t\tNULL\n\n");
  }
  printf("\n");
  return;
} /* print_tree_node() */

/*
 * prints the entire tree
 */

void print_tree(tree_node_t *head_event) {
  print_tree_node(head_event);
  if (head_event != NULL) {
    if (head_event->left_child_ptr) {
      print_tree(head_event->left_child_ptr);
    }
    if (head_event->right_child_ptr) {
      print_tree(head_event->right_child_ptr);
    }
  }
  return;
} /* print_tree() */

/*
 * prints an individual node
 */

void print_list_node(list_node_t *event) {
  if (event == NULL) {
    printf("\n\n\n  [NULL Node!]\n\n");
    return;
  }
  printf("Current: %p\n", event);
  print_event_info(event->event_info);
  printf("Prev: %p\n", event->prev_event);
  printf("Next: %p\n", event->next_event);
  printf("\n");
  return;
} /* _my_print_tree_event() */

/*
 * prints an entire list
 */

void print_list(list_node_t *node) {
  if (node == NULL) {
    printf("\t[Empty list]\n\n");
  }
  while (node) {
    print_list_node(node);
    node = node->next_event;
  }
  return;
} /* print_list() */

/*
 * prints the event info
 */
void print_event_info(event_info_t *event) {
    if (event == NULL) {
        printf("\t[NULL event!]\n\n");
    }
    printf("\tEvent Address: %p\n", event);
    printf("\tEvent Name: %s\n", event->name);
    printf("\tStarts: ");
    print_time_struct(event->start_time);
    printf("\tEnds: ");
    print_time_struct(event->end_time);
} /* print_event_info() */

/*
 * prints a time struct
 */
void print_time_struct(time_struct_t time) {
  printf("%d/%d/%d at %d:%02d\n", time.month, time.day, time.year,
         time.hour, time.minute);
} /* print_time_struct() */