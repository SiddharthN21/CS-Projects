/* Siddharth Nadgaundi, April 19, 2023*/

#include "hw12.h"

#include <assert.h>
#include <malloc.h>
#include <stdio.h>
#include <string.h>

/* Helper Function */
int get_node_count(node_t *, void *);
void prefix_traversal(node_t *, void *, node_t **, int *);


/*
 * Function to print the internal data of a app_t struct passed as a void
 * pointer to standard out
 */

void print_app_data(void *p) {
  app_t *ptr = (app_t *) p;
  assert(ptr && ptr->name);

  printf("%s: %dMB, $%.2f, %.2f/5\n", ptr->name, ptr->size,
         ptr->price, ptr->rating);
} /* print_app_data() */

/*
 * Function to print the internal data of a phone_t struct passed as a void
 * pointer to standard out
 */

void print_phone_data(void *p) {
  phone_t *ptr = (phone_t *) p;
  assert(ptr && ptr->model);

  printf("%s: %dGB, released %d, $%.2f, %.2f hours\n", ptr->model,
         ptr->storage, ptr->release_year, ptr->price, ptr->battery_life);
} /* print_phone_data() */

/*
 * Function to print a tree node by dynamically dispatching the print function
 * stored in the node on the data stored in the node
 */

void print_node(node_t *n_ptr) {
  assert(n_ptr && n_ptr->data && n_ptr->print);

  (n_ptr->print)(n_ptr->data);
} /* print_node() */

/*
 * Function to recursively print the tree using an in-order traversal
 */

void print_tree(node_t *root) {
  if (!root) {
    return;
  }

  print_tree(root->left_child);
  print_node(root);
  print_tree(root->right_child);

} /* print_tree() */

/* define the rest of the functions below (you can delete this comment) */

/*
 * This function dynamically allocates memory to a node_t and populates it with
 * the arguments passed into the function.
 */

void create_node(node_t **node_ptr, void *n_data, void (*print_fn_ptr)(void*),
                 void (*del_fn_ptr)(void **),
                 int (*comp_fn_ptr)(void *, void *)) {
  node_t *new_node = NULL;

  assert(node_ptr != NULL);
  assert(*node_ptr == NULL);
  assert(n_data != NULL);
  assert(print_fn_ptr != NULL);
  assert(del_fn_ptr != NULL);
  assert(comp_fn_ptr != NULL);

  new_node = (node_t *) malloc(sizeof(node_t));
  assert(new_node != NULL);
  new_node->data = n_data;
  new_node->print = print_fn_ptr;
  new_node->delete = del_fn_ptr;
  new_node->compare = comp_fn_ptr;
  new_node->left_child = NULL;
  new_node->right_child = NULL;

  *node_ptr = new_node;
} /* create_node() */

/*
 * This function deallocates the data of the pointer passed in as the parameter
 * by calling the delete function of the parameter with the data attribute
 * passed as the parameter. The node is then freed separately and set to NULL.
 */

void delete_node(node_t **del_node_ptr) {
  assert(del_node_ptr != NULL);
  assert(*del_node_ptr != NULL);
  assert((*del_node_ptr)->left_child == NULL);
  assert((*del_node_ptr)->right_child == NULL);

  ((*del_node_ptr)->delete)(&(*del_node_ptr)->data);

  free(*del_node_ptr);
  *del_node_ptr = NULL;
} /* delete_node() */

/*
 * This function is used to insert a node into the tree.
 * The function makes used of the root node's compare function to find the
 * position of the new node relative to the current node. If statements are
 * then used to call the function recursively based on the return value of
 * compare. In case one of the child pointers in NULL, the left or right
 * child pointer is set to the new node based on the if statement.
 */

void insert_node(node_t **root_node_ptr, node_t *tree_node_ptr) {
  int left_rt_node = 0;
  node_t **temp_root_node_ptr = NULL;

  assert(root_node_ptr != NULL);
  assert(tree_node_ptr != NULL);

  temp_root_node_ptr = root_node_ptr;

  if (*root_node_ptr == NULL) {
    *root_node_ptr = tree_node_ptr;
    return;
  }
  else {
    left_rt_node = ((*root_node_ptr)->compare)(((*root_node_ptr)->data),
                   ((tree_node_ptr)->data));
    if ((left_rt_node == 0) || (left_rt_node == 1)) {
      if ((*temp_root_node_ptr)->left_child != NULL) {
        insert_node(&((*temp_root_node_ptr)->left_child), tree_node_ptr);
      }
      else {
        (*temp_root_node_ptr)->left_child = tree_node_ptr;
        return;
      }

    }
    else if (left_rt_node == -1) {
      if ((*temp_root_node_ptr)->right_child != NULL) {
        insert_node(&((*temp_root_node_ptr)->right_child), tree_node_ptr);
      }
      else {
        (*temp_root_node_ptr)->right_child = tree_node_ptr;
        return;
      }
    }
  }
} /* insert_node() */

/*
 * This function is used to find duplicate nodes in the tree.
 * It make use of the get_node_count and prefix_traversal helper functions
 * to populate the node_ptr_array with the duplicate nodes and return it.
 */

struct node **find_nodes(node_t *root_node, void *ph_or_app_ptr,
                         int *node_dup_count) {
  node_t **node_ptr_array = NULL;
  int node_count = 0;
  int arr_index = 0;

  assert(root_node != NULL);
  assert(ph_or_app_ptr != NULL);
  assert(node_dup_count != NULL);

  node_count = get_node_count(root_node, ph_or_app_ptr);
  if (node_count == 0) {
    *node_dup_count = 0;
    return node_ptr_array;
  }
  node_ptr_array = (node_t **) malloc(sizeof(node_t *) * node_count);
  assert(node_ptr_array != NULL);

  prefix_traversal(root_node, ph_or_app_ptr, node_ptr_array, &arr_index);
  *node_dup_count = node_count;
  return node_ptr_array;
} /* find_nodes() */

/*
 * This function is used to deallocate all memory from the tree.
 * The function follows postfix traversal and calls the delete_tree function
 * recursively and the delete_node function to deallocate all memory.
 */

void delete_tree(node_t **tree_root_ptr) {
  assert(tree_root_ptr != NULL);

  if ((*tree_root_ptr) == NULL) {
    return;
  }
  delete_tree(&((*tree_root_ptr)->left_child));
  delete_tree(&((*tree_root_ptr)->right_child));
  delete_node(&(*tree_root_ptr));
} /* delete_tree() */


/*
 * This function is used to allocate memory for a new app_t node.
 * After allocating memory and populating the new node with the parameters, the
 * node is assigned to the first argument by casting it to void.
 */

void create_app_data(void **app_node_ptr, const char *app_name, int app_size,
                     float app_price, float app_rating) {
  app_t *new_app_node = NULL;

  assert(app_node_ptr != NULL);
  assert(*app_node_ptr == NULL);
  assert(app_name != NULL);

  new_app_node = (app_t *) malloc(sizeof(app_t));
  assert(new_app_node != NULL);
  new_app_node->name = malloc(strlen(app_name) + 1);
  assert(new_app_node->name != NULL);
  strcpy(new_app_node->name, app_name);
  new_app_node->size = app_size;
  new_app_node->price = app_price;
  new_app_node->rating = app_rating;

  *app_node_ptr = (void *) new_app_node;
} /* create_app_data() */

/*
 * This function is used to delete the node passed in as the parameter.
 * The function casts the void pointer to app_t and then deallocates the
 * corresponding data.
 */

void delete_app_data(void **app_node_ptr) {
  app_t *app_ptr = NULL;

  assert(app_node_ptr != NULL);
  assert(*app_node_ptr != NULL);

  app_ptr = (app_t *) (*app_node_ptr);

  free(app_ptr->name);
  app_ptr->name = NULL;
  free(app_ptr);
  app_ptr = NULL;
  *app_node_ptr = NULL;
} /* delete_app_data() */

/*
 * This function compares two app's data.
 * The function first casts the two arguments to app_t and then calculates
 * their data  based on the formula provided. The corresponding values are
 * returned through if statements.
 */

int  compare_app_data(void *a_one, void *a_two) {
  int app_rtn = 0;
  float data_app_one = 0.0;
  float data_app_two = 0.0;
  app_t *app_one = NULL;
  app_t *app_two = NULL;

  assert(a_one != NULL);
  assert(a_two != NULL);

  app_one = (app_t *) (a_one);
  app_two = (app_t *) (a_two);

  data_app_one = (app_one->rating) /
                 ((app_one->size) * ((app_one->price) + 1));
  data_app_two = (app_two->rating) /
                 ((app_two->size) * ((app_two->price) + 1));

  if (data_app_one == data_app_two) {
    app_rtn = 0;
  }
  else if (data_app_one > data_app_two) {
    app_rtn = 1;
  }
  else if (data_app_one < data_app_two) {
    app_rtn = -1;
  }
  return app_rtn;
} /* compare_app_data() */

/*
 * This function is used to allocate memory for a new phone_t node.
 * After allocating memory and populating the new node with the parameters, the
 * node is assigned to the first argument by casting it to void.
 */

void create_phone_data(void **ph_node_ptr, const char *ph_name, int ph_storage,
                       int ph_year, float ph_price, float ph_bat_life) {
  phone_t *new_phone_node = NULL;

  assert(ph_node_ptr != NULL);
  assert(*ph_node_ptr == NULL);
  assert(ph_name != NULL);

  new_phone_node = (phone_t *) malloc(sizeof(phone_t));
  assert(new_phone_node != NULL);
  new_phone_node->model = malloc(strlen(ph_name) + 1);
  assert(new_phone_node->model != NULL);
  strcpy(new_phone_node->model, ph_name);
  new_phone_node->storage = ph_storage;
  new_phone_node->release_year = ph_year;
  new_phone_node->price = ph_price;
  new_phone_node->battery_life = ph_bat_life;

  *ph_node_ptr = (void *) new_phone_node;
} /* create_phone_data() */

/*
 * This function is used to delete the node passed in as the parameter.
 * The function casts the void pointer to phone_t and then deallocates the
 * corresponding data.
 */

void delete_phone_data(void **ph_node_ptr) {
  phone_t *phone_ptr = NULL;

  assert(ph_node_ptr != NULL);
  assert(*ph_node_ptr != NULL);

  phone_ptr = (phone_t *) (*ph_node_ptr);

  free(phone_ptr->model);
  phone_ptr->model = NULL;
  free(phone_ptr);
  phone_ptr = NULL;
  *ph_node_ptr = NULL;
} /* delete_phone_data() */

/*
 * This function compares two phones' data.
 * The function first casts the two arguments to phone_t and then calculates
 * their data  based on the formula provided. The corresponding values are
 * returned through if statements.
 */

int  compare_phone_data(void *ph_one, void *ph_two) {
  int ph_rtn = 0;
  float ph_one_data = 0.0;
  float ph_two_data = 0.0;
  phone_t *phone_one = NULL;
  phone_t *phone_two = NULL;

  assert(ph_one != NULL);
  assert(ph_two != NULL);

  phone_one = (phone_t *) ph_one;
  phone_two = (phone_t *) ph_two;

  ph_one_data = ((1.0 * (phone_one->storage)) * (phone_one->battery_life)) /
                  ((phone_one->price) *
                  (1.0 * (CURRENT_YEAR - phone_one->release_year)));
  ph_two_data = ((1.0 * (phone_two->storage)) * (phone_two->battery_life)) /
                  ((phone_two->price) *
                  (1.0 * (CURRENT_YEAR - phone_two->release_year)));

  if (ph_one_data > ph_two_data) {
    ph_rtn = 1;
  }
  else if (ph_one_data == ph_two_data) {
    ph_rtn = 0;
  }
  else if (ph_one_data < ph_two_data) {
    ph_rtn = -1;
  }
  return ph_rtn;
} /* compare_phone_data() */


/*
 * This function is used to remove the passed node from the tree.
 * The function uses a while loop in order to iterate over all the nodes of the
 * tree and find if the passed node matches any node within the tree. Within
 * the while loop, if statements are used to check the return value of the
 * compare function to manipulate the pointers accordingly. Next, if
 * statements are used to check if the node to be deleted is thr root or not.
 * Within the if statements, a series of if statements are used to find out
 * whether the node to be deleted has got children or not. Based on the outcome
 * of the if statements, the corresponding pointers are manipulated. Lastly, the
 * node is removed using the delete_node function.
 */

void remove_node(node_t **root_node_ptr, node_t *rmv_node_ptr) {
  node_t *current_node = NULL;
  node_t *prev_node = NULL;
  node_t *right_most_child = NULL;
  int node_found = 0;
  int left_or_rt = 0;

  assert(root_node_ptr != NULL);
  assert(rmv_node_ptr != NULL);

  if (*root_node_ptr == NULL) {
    return;
  }

  current_node = *root_node_ptr;
  while ((node_found == 0) && (current_node != NULL)) {
    int comp_nodes = 0;
    comp_nodes = ((current_node)->compare)(((current_node)->data),
                  ((rmv_node_ptr)->data));
    if (comp_nodes == -1) {
      prev_node = current_node;
      current_node = current_node->right_child;
      left_or_rt = 2;
    }
    else if (comp_nodes == 1) {
      prev_node = current_node;
      current_node = current_node->left_child;
      left_or_rt = 1;
    }
    else if (comp_nodes == 0) {
      if (current_node == rmv_node_ptr) {
        node_found = 1;
      }
      else {
        prev_node = current_node;
        current_node = current_node->left_child;
        left_or_rt = 1;
      }
    }
  }

  if ((node_found == 1) && (prev_node != NULL)) {
    if ((current_node->left_child != NULL) &&
        (current_node->right_child != NULL)) {
      right_most_child = current_node->left_child;
      while (right_most_child->right_child != NULL) {
        right_most_child = right_most_child->right_child;
      }
      if (left_or_rt == 1) {
        prev_node->left_child = current_node->left_child;
      }
      else if (left_or_rt == 2) {
        prev_node->right_child = current_node->left_child;
      }
      right_most_child->right_child = current_node->right_child;
      current_node->left_child = NULL;
      current_node->right_child = NULL;
    }
    else if ((current_node->left_child != NULL) &&
             (current_node->right_child == NULL)) {
      if (left_or_rt == 1) {
        prev_node->left_child = current_node->left_child;
      }
      else if (left_or_rt == 2) {
        prev_node->right_child = current_node->left_child;
      }
      current_node->left_child = NULL;
      current_node->right_child = NULL;
    }
    else if ((current_node->left_child == NULL) &&
             (current_node->right_child != NULL)) {
      if (left_or_rt == 1) {
        prev_node->left_child = current_node->right_child;
      }
      else if (left_or_rt == 2) {
        prev_node->right_child = current_node->right_child;
      }
      current_node->left_child = NULL;
      current_node->right_child = NULL;
    }
    else {
      if (left_or_rt == 1) {
        prev_node->left_child = NULL;
      }
      else if (left_or_rt == 2) {
        prev_node->right_child = NULL;
      }
      current_node->left_child = NULL;
      current_node->right_child = NULL;
    }
  }
  else if ((node_found == 1) && (prev_node == NULL)) {
    if (current_node->left_child != NULL) {
      right_most_child = current_node->left_child;
      while (right_most_child->right_child != NULL) {
        right_most_child = right_most_child->right_child;
      }
      right_most_child->right_child = current_node->right_child;
      *root_node_ptr = current_node->left_child;
    }
    if ((current_node->left_child == NULL) &&
        (current_node->right_child != NULL)) {
      *root_node_ptr = current_node->right_child;
    }
    if ((current_node->left_child == NULL) &&
        (current_node->right_child == NULL)) {
      *root_node_ptr = NULL;
    }
    current_node->left_child = NULL;
    current_node->right_child = NULL;
  }
  if (node_found == 1) {
    delete_node(&rmv_node_ptr);
    rmv_node_ptr = NULL;
    current_node = NULL;
  }
} /* remove_node() */


/*
 * This helper function is used to count the number of duplicate nodes in the
 * tree using the root node's compare function. The function recursively calls
 * itself using prefix traversal.
 */

int get_node_count(node_t *cur_root_node, void *phone_or_app_ptr) {
  int t_node_count = 0;
  if (cur_root_node == NULL) {
    return 0;
  }
  if (((cur_root_node->compare)((cur_root_node->data), (phone_or_app_ptr))) ==
      0) {
    t_node_count++;
  }
  t_node_count += get_node_count(cur_root_node->left_child, phone_or_app_ptr);
  t_node_count += get_node_count(cur_root_node->right_child, phone_or_app_ptr);

  return t_node_count;
} /* get_node_count() */

/*
 * This helper function is used to populate the duplicate nodes array with the
 * corresponding duplicate nodes. The function calls itself recursively using
 * prefix traversal and uses the root node's compare function to identify
 * duplicates.
 */

void prefix_traversal(node_t *current_node, void *phone_or_app,
                      node_t **nodes_arr, int *array_index) {
  if (current_node != NULL) {
    if (((current_node->compare)((current_node->data), (phone_or_app))) == 0) {
      nodes_arr[*array_index] = &(*current_node);
      (*array_index)++;
    }
    prefix_traversal(current_node->left_child,
                     phone_or_app, nodes_arr, array_index);
    prefix_traversal(current_node->right_child,
                     phone_or_app, nodes_arr, array_index);
  }
} /* prefix_traversal() */