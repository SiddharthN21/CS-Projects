/* Siddharth Nadgaundi, April 12, 2023 */

/* Includes */
#include "hw11.h"

#include <assert.h>
#include <malloc.h>
#include <stdio.h>
#include <string.h>

/* Function Prototypes */
int compare_events(event_info_t *, event_info_t *);
int compare_time(time_struct_t *, time_struct_t *);
void inorder_traversal(tree_node_t *, tree_node_t **, int *);
int get_node_count(tree_node_t *);
int time_diff_in_min(time_struct_t *, time_struct_t *);


/*
 * This function is used to allocate and populate a new event_info_t node with
 * the values provided as parameters in the function's argument list.
 */

void create_event(event_info_t **event_ptr, char *event_name,
                  time_struct_t s_time, time_struct_t e_time) {
  event_info_t *new_event = NULL;

  assert(event_ptr != NULL);
  assert(*event_ptr == NULL);
  assert(event_name != NULL);

  new_event = (event_info_t *) malloc(sizeof(event_info_t));
  assert(new_event != NULL);
  new_event->name = malloc(strlen(event_name) + 1);
  assert(new_event->name != NULL);
  strcpy(new_event->name, event_name);
  new_event->start_time = s_time;
  new_event->end_time = e_time;

  *event_ptr = new_event;
} /* create_event() */

/*
 * This function adds the event node to a specific spot in the tree based on
 * the start times.
 * The function makes use of the helper function compare_events to find the
 * location of the new node relative to the current node. If the new event
 * comes before the current event the function is called recurively with the
 * left child or with the right child in case the new event comes after the
 * current event.
 */

void add_event(tree_node_t **head_event_ptr, event_info_t *new_event) {
  tree_node_t *parent_event = NULL;
  tree_node_t **temp_event_ptr = NULL;
  tree_node_t *new_node = NULL;
  event_info_t *current_event = NULL;

  assert(head_event_ptr != NULL);
  assert(new_event != NULL);

  parent_event = *head_event_ptr;

  if (parent_event != NULL) {
    current_event = parent_event->event_info;
  }

  if (parent_event == NULL) {
    new_node = malloc(sizeof(struct tree_node));
    assert(new_node != NULL);
    new_node->event_info = new_event;
    new_node->left_child_ptr = NULL;
    new_node->right_child_ptr = NULL;
    *head_event_ptr = new_node;
  }
  else if ((compare_events(new_event, current_event) == 1) ||
  (compare_events(new_event, current_event) == 0)) {
    temp_event_ptr = &(parent_event->left_child_ptr);
    add_event(temp_event_ptr, new_event);
    if (parent_event->left_child_ptr == NULL) {
      parent_event->left_child_ptr = new_node;
    }
  }
  else if (compare_events(new_event, current_event) == 2) {
    temp_event_ptr = &(parent_event->right_child_ptr);
    add_event(temp_event_ptr, new_event);
    if (parent_event->right_child_ptr == NULL) {
      parent_event->right_child_ptr = new_node;
    }
  }
} /* add_event() */

/*
 * This function is used to count the number of events having an end time
 * before the time provided in the second argument.
 * The function makes use of the compare_time helper function to check if the
 * current event ends before the provided time. In case is does, the count is
 * incremented. This is followed by calling the function recursively with the
 * left and right child and adding the value to num_exp_events.
 */

int count_expired_events(tree_node_t *head_event, time_struct_t check_time) {
  int num_exp_events = 0;
  time_struct_t event_end_time = {0, 0, 0, 0, 0};
  if (head_event == NULL) {
    return 0;
  }
  event_end_time = head_event->event_info->end_time;
  if (compare_time(&(event_end_time), &(check_time)) == 1) {
    num_exp_events++;
  }
  num_exp_events += count_expired_events(head_event->left_child_ptr,
                                         check_time);
  num_exp_events += count_expired_events(head_event->right_child_ptr,
                                         check_time);

  return num_exp_events;
} /* count_expired_events() */

/*
 * This function is used to rempve all the events starting less than a certain
 * number of minutes after the previous event.
 * A while loop is used to go the the head event. Another while loop is then
 * used to iterate through the list. The while loop uses the time_diff_in_min
 * helper function to check if the current event has a time gap less than the
 * value provided. In case the time gap is less, if statements are used to
 * check whether the node to be removed is the tail node or not in order to
 * carry out the necessary pointer manipulations.
 */

void free_up_schedule(list_node_t **user_cal_ptr, int time_gap) {
  list_node_t *cur_node = NULL;
  list_node_t *del_node = NULL;

  assert(user_cal_ptr != NULL);
  if (*user_cal_ptr == NULL) {
    return;
  }

  cur_node = *user_cal_ptr;
  while (cur_node->prev_event != NULL) {
    cur_node = cur_node->prev_event;
  }
  cur_node = cur_node->next_event;
  while (cur_node != NULL) {
    if (time_diff_in_min(&(cur_node->event_info->start_time),
    &(cur_node->prev_event->event_info->end_time)) < time_gap) {
      del_node = cur_node;
      if (del_node->next_event != NULL) {
        cur_node = cur_node->next_event;
        del_node->next_event->prev_event = del_node->prev_event;
        del_node->prev_event->next_event = del_node->next_event;
      }
      else if (del_node->next_event == NULL) {
        cur_node = cur_node->next_event;
        del_node->prev_event->next_event = del_node->next_event;
      }
      del_node->next_event = NULL;
      del_node->prev_event = NULL;
      free(del_node);
      del_node = NULL;
    }
    else {
      cur_node = cur_node->next_event;
    }
  }
} /* free_up_schedule() */

/*
 * This function is used to delete and free the entire event list.
 * The function makes use of if statements to check the position of the current
 * node. If the current node's left and right child pointers are NULL, the
 * corresponding memory is freed. If the right node is not null, the function
 * is called revursively with the right node followed by the current node. The
 * same procedure is followed when the left node is not NULL.
 */

void delete_event_schedule(tree_node_t **head_event_ptr) {
  tree_node_t *right_node = NULL;
  tree_node_t *left_node = NULL;
  tree_node_t *prev_node = NULL;
  event_info_t *delete_event = NULL;

  assert(head_event_ptr != NULL);

  if (*head_event_ptr == NULL) {
    head_event_ptr = NULL;
    return;
  }

  right_node = (*head_event_ptr)->right_child_ptr;
  left_node = (*head_event_ptr)->left_child_ptr;

  if ((right_node == NULL) && (left_node == NULL)) {
    delete_event = (*head_event_ptr)->event_info;
    free(delete_event->name);
    delete_event->name = NULL;
    free(delete_event);
    delete_event = NULL;
    (*head_event_ptr)->event_info = NULL;
    (*head_event_ptr)->left_child_ptr = NULL;
    (*head_event_ptr)->right_child_ptr = NULL;
    free((*head_event_ptr));
    *head_event_ptr = NULL;
    head_event_ptr = NULL;
  }
  else if (right_node != NULL) {
    prev_node = *head_event_ptr;
    delete_event_schedule(&right_node);
    prev_node->right_child_ptr = NULL;
    delete_event_schedule(&prev_node);
  }
  else if (left_node != NULL) {
    prev_node = *head_event_ptr;
    delete_event_schedule(&left_node);
    prev_node->left_child_ptr = NULL;
    delete_event_schedule(&prev_node);
  }
} /* delete_event_schedule() */


/*
 * This function is used to add the events from the tree to the user's calendar.
 * The function first gets the nodes of the tree in an ordered list using the
 * inorder_traversal helper function. A while loop is used to go to the head
 * event of the user's calendar. A for loop is then used to iterate through all
 * the events in order to find the correct location in the list. Within the for
 * loop, a while loop is used to compare the times of each event using the
 * compare_time helper function. Next, a series of if statements are used to
 * check whether the node would be at the head, middle, or tail of the list.
 * This is followed by if statements to check the return value of compare_time
 * and carry out the corresponding pointer operations.
 */

void add_to_calendar(tree_node_t *head_event, list_node_t **user_cal_ptr) {
  int tree_size = 0;
  int index = 0;
  tree_node_t** nodes = NULL;
  list_node_t* new_list_node_to_add = NULL;
  list_node_t* curr_list_node = NULL;
  list_node_t* head_list_node = NULL;

  assert(user_cal_ptr != NULL);

  if (head_event == NULL) {
    return;
  }

  tree_size = get_node_count(head_event);
  nodes = (tree_node_t **)malloc(sizeof(tree_node_t *) * tree_size);
  assert(nodes != NULL);

  inorder_traversal(head_event, nodes, &index);

  head_list_node = *user_cal_ptr;
  if (head_list_node != NULL) {
    while (head_list_node->prev_event != NULL) {
      head_list_node = head_list_node->prev_event;
    }
  }

  for (int i = 0; i < index; i++) {
    new_list_node_to_add = (list_node_t *) malloc(sizeof(list_node_t));
    assert(new_list_node_to_add != NULL);
    new_list_node_to_add->event_info = nodes[i]->event_info;
    new_list_node_to_add->next_event = NULL;
    new_list_node_to_add->prev_event = NULL;
    int node_added = 0;
    int add_before = 0;
    int add_after = 0;
    if (head_list_node == NULL) {
      head_list_node = new_list_node_to_add;
      node_added = 1;
    }
    else {
      curr_list_node = head_list_node;
      while ((curr_list_node != NULL) && (node_added == 0)) {
        add_after =
        compare_time(&(new_list_node_to_add->event_info->start_time),
        &(curr_list_node->event_info->end_time));
        add_before = compare_time(&(curr_list_node->event_info->start_time),
        &(new_list_node_to_add->event_info->end_time));
        if (curr_list_node->prev_event == NULL) {
          if (add_before == 2) {
            head_list_node = new_list_node_to_add;
            curr_list_node->prev_event = new_list_node_to_add;
            new_list_node_to_add->next_event = curr_list_node;
            new_list_node_to_add->prev_event = NULL;
            node_added = 1;
          }
          if ((add_after == 2) && (curr_list_node->next_event == NULL)) {
            curr_list_node->next_event = new_list_node_to_add;
            new_list_node_to_add->prev_event = curr_list_node;
            new_list_node_to_add->next_event = NULL;
            node_added = 1;
          }
        }
        else if (curr_list_node->next_event == NULL) {
          if (add_after == 2) {
            curr_list_node->next_event = new_list_node_to_add;
            new_list_node_to_add->prev_event = curr_list_node;
            new_list_node_to_add->next_event = NULL;
            node_added = 1;
          }
          if ((add_before == 2) && (curr_list_node->prev_event != NULL)) {
            if (compare_time(&(new_list_node_to_add->event_info->start_time),
                &(curr_list_node->prev_event->event_info->end_time)) == 2) {
              new_list_node_to_add->next_event = curr_list_node;
              new_list_node_to_add->prev_event = curr_list_node->prev_event;
              curr_list_node->prev_event->next_event = new_list_node_to_add;
              curr_list_node->prev_event = new_list_node_to_add;
              node_added = 1;
            }
          }
        }
        else if ((curr_list_node->prev_event != NULL) &&
        (curr_list_node->next_event != NULL)) {
          if (add_before == 2) {
            if (compare_time(&(new_list_node_to_add->event_info->start_time),
                &(curr_list_node->prev_event->event_info->end_time)) == 2) {
              new_list_node_to_add->next_event = curr_list_node;
              new_list_node_to_add->prev_event = curr_list_node->prev_event;
              curr_list_node->prev_event->next_event = new_list_node_to_add;
              curr_list_node->prev_event = new_list_node_to_add;
              node_added = 1;
            }
          }
        }
        curr_list_node = curr_list_node->next_event;
      }
    }
    if (node_added == 0) {
      new_list_node_to_add->event_info = NULL;
      new_list_node_to_add->next_event = NULL;
      new_list_node_to_add->prev_event = NULL;
      free(new_list_node_to_add);
      new_list_node_to_add = NULL;
    }
  }
  free(nodes);
  nodes = NULL;

  *user_cal_ptr = head_list_node;
} /* add_to_calendar() */


/*
 * This helper function is used to find which event comes before the other one
 * based on their start times. The function compares all the attributes of
 * time_struct.
 * If event_one starts earlier than event two it returns 1.
 * If event_one starts at same time as event two it returns 0.
 * If event_one starts later than event two it returns 2.
 */

int compare_events(event_info_t *event_one, event_info_t *event_two) {
  time_struct_t *event_one_time = NULL;
  time_struct_t *event_two_time = NULL;

  event_one_time = &(event_one->start_time);
  event_two_time = &(event_two->start_time);

  if (event_one_time->year != event_two_time->year) {
    return (event_one_time->year > event_two_time->year) ? 2 : 1;
  }

  if (event_one_time->month != event_two_time->month) {
    return (event_one_time->month > event_two_time->month) ? 2 : 1;
  }

  if (event_one_time->day != event_two_time->day) {
    return (event_one_time->day > event_two_time->day) ? 2 : 1;
  }

  if (event_one_time->hour != event_two_time->hour) {
    return (event_one_time->hour > event_two_time->hour) ? 2 : 1;
  }

  if (event_one_time->minute != event_two_time->minute) {
    return (event_one_time->minute > event_two_time->minute) ? 2 : 1;
  }

  return 0;
} /* compare_events() */

/*
 * This helper function is used to find which event comes before the other one
 * based on their times. The function compares all the attributes of
 * time_struct. The only difference between this function and the
 * comapre_events function is that this function takes time_struct as the
 * parameters rather than pointers.
 * If event_one starts earlier than event two it returns 1.
 * If event_one starts at same time as event two it returns 0.
 * If event_one starts later than event two it returns 2.
 */

int compare_time(time_struct_t *event_one_time, time_struct_t *event_two_time) {
  if (event_one_time->year != event_two_time->year) {
    return (event_one_time->year > event_two_time->year) ? 2 : 1;
  }

  if (event_one_time->month != event_two_time->month) {
    return (event_one_time->month > event_two_time->month) ? 2 : 1;
  }

  if (event_one_time->day != event_two_time->day) {
    return (event_one_time->day > event_two_time->day) ? 2 : 1;
  }

  if (event_one_time->hour != event_two_time->hour) {
    return (event_one_time->hour > event_two_time->hour) ? 2 : 1;
  }

  if (event_one_time->minute != event_two_time->minute) {
    return (event_one_time->minute > event_two_time->minute) ? 2 : 1;
  }

  return 0;
} /* compare_time() */


/*
 * The function accepts a pointer to the root node, a pointer/array to hold all
 * the element in the tree and the index to traverse through all the nodes.
 * The function uses an if statement to check if the root is not NULL. In case
 * it is not null it calls itself recursively with the root's left child. It
 * then assigns the current root to the current index of nodes. This is
 * followed by calling the function recursively with the root's right child in
 * inorder order.
 */

void inorder_traversal(tree_node_t* root, tree_node_t** nodes, int* index) {
  if (root != NULL) {
    inorder_traversal(root->left_child_ptr, nodes, index);

    nodes[*index] = root;
    (*index)++;

    inorder_traversal(root->right_child_ptr, nodes, index);
  }
} /* inorder_traversal() */


/*
 * The fucntion finds the total number of nodes in the tree. The function first
 * checks if the root is null. If it is null the function returns 0 for that
 * call. If not, count is initialized to 1 and then the function is called
 * recursively and added to the current value of count starting with the root's
 * left child followed by the right child. Count is returned at the end of the
 * function's implementation.
 */

int get_node_count(tree_node_t* root) {
  if (root == NULL) {
    return 0;
  }
  int count = 1;
  count += get_node_count(root->left_child_ptr);
  count += get_node_count(root->right_child_ptr);

  return count;
} /* get_node_count() */

/*
 * This function is used to find the time difference in the two times passed.
 * The function subtracts the time of event two from that of event one. It then
 * converts each attribute (year, month etc.) into minutes and assigns the sum
 * to the variable returned by the function. This represents the time gap
 * between the two times.
 */

int time_diff_in_min(time_struct_t *event_one_time,
                     time_struct_t *event_two_time) {
  int yr_diff = 0;
  int mon_diff = 0;
  int day_diff = 0;
  int hr_diff = 0;
  int min_diff = 0;
  int total_diff_in_min = 0;

  yr_diff = (event_one_time->year - event_two_time->year);
  mon_diff = (event_one_time->month - event_two_time->month);
  day_diff = (event_one_time->day - event_two_time->day);
  hr_diff = (event_one_time->hour - event_two_time->hour);
  min_diff = (event_one_time->minute - event_two_time->minute);

  total_diff_in_min = ((yr_diff * 12 * 30 * 24 * 60) +
                      (mon_diff * 30 * 24 * 60) +
                      (day_diff * 24 * 60) +
                      (hr_diff * 60) +
                      (min_diff));

  return total_diff_in_min;
} /* time_diff_in_min() */