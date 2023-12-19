// Siddharth Nadgaundi, April 5, 2023

/* Includes */
#include "hw10.h"

#include <assert.h>
#include <malloc.h>
#include <stdio.h>
#include <string.h>

/* function prototypes */

void prefix_traversal(employee_t *, employee_t **, int *);
void postfix_traversal(employee_t *, employee_t **, int *);
void inorder_traversal(employee_t *, employee_t **, int *);
void reverse_traversal(employee_t *, employee_t **, int *);
int get_node_count(employee_t *);

/*
 * This function is used to create a new employee node by dynamically
 * allocating memory to the new node. Assertion checks are used after
 * allocating memory to the strcuture itself and the name attribute.
 */

employee_t *create_employee(char *emp_name) {
  employee_t *new_employee = NULL;

  assert(emp_name != NULL);

  new_employee = (employee_t *) malloc(sizeof(employee_t));
  assert(new_employee != NULL);
  new_employee->name = (char *) malloc(strlen(emp_name) + 1);
  assert(new_employee->name != NULL);
  strcpy(new_employee->name, emp_name);
  new_employee->left_child_ptr = NULL;
  new_employee->right_child_ptr = NULL;

  return new_employee;
} /* create_employee() */

/*
 * This function is used to insert the new employee at the correct spot in the
 * tree. If statements are used to compare the new employee's name with the
 * parent employee (the current root). In case one of the else if statements
 * are true the function is called recursively with the current root's left or
 * right child pointer based on the outcome of strcmp.
 */

void insert_employee(employee_t **root_emp_pointer, employee_t *new_employee) {
  employee_t *parent_employee = NULL;
  employee_t **temp_root_ptr = NULL;

  assert(root_emp_pointer != NULL);
  assert(new_employee != NULL);
  assert(new_employee->left_child_ptr == NULL);
  assert(new_employee->right_child_ptr == NULL);

  parent_employee = *root_emp_pointer;

  if (parent_employee == NULL) {
    *root_emp_pointer = new_employee;
  }
  else if (strcmp(parent_employee->name, new_employee->name) < 0) {
    temp_root_ptr = &(parent_employee->right_child_ptr);
    insert_employee(temp_root_ptr, new_employee);
    if (parent_employee->right_child_ptr == NULL) {
      parent_employee->right_child_ptr = new_employee;
    }
  }
  else if (strcmp(parent_employee->name, new_employee->name) > 0) {
    temp_root_ptr = &(parent_employee->left_child_ptr);
    insert_employee(temp_root_ptr, new_employee);
    if (parent_employee->left_child_ptr == NULL) {
      parent_employee->left_child_ptr = new_employee;
    }
  }
} /* insert_employee() */

/*
 * This function is used to find a particular employee in the tree based on
 * their name. If statements are used along with strcmp to check if the current
 * employee's name is equal to, greater than, or less than the search_name. In
 * case one of the else if statements evaluates to true, the function is called
 * recursively with the current employee's left or right child based on the
 * value returned by strcmp.
 */

employee_t *find_employee(employee_t *root_employee, char *search_name) {
  employee_t  *search_emp = NULL;

  assert(search_name != NULL);

  if (root_employee == NULL) {
    return NULL;
  }
  search_emp = root_employee;

  if (strcmp(search_emp->name, search_name) == 0) {
    return search_emp;
  }
  else if (strcmp(search_emp->name, search_name) < 0) {
    search_emp = search_emp->right_child_ptr;
    search_emp = find_employee(search_emp, search_name);
  }
  else if (strcmp(search_emp->name, search_name) > 0) {
    search_emp = search_emp->left_child_ptr;
    search_emp = find_employee(search_emp, search_name);
  }
  return search_emp;
} /* find_employee() */

/*
 * This function is used to delete the entire binary tree recursively. The
 * function first checks if the root is null or not. In case it is null, the
 * function returns within the if statement itself. If not, the left and right
 * pointer's are checked if they are null. In case one of them is not null, the
 * function is called recursively with the current node's left or right child.
 * This is followed by calling the function recursively with the current node.
 */

void delete_tree(employee_t **root_emp_pointer) {
  employee_t *right_node = NULL;
  employee_t *left_node = NULL;
  employee_t *delete_emp = NULL;
  employee_t *prev_node = NULL;

  assert(root_emp_pointer != NULL);

  delete_emp = *root_emp_pointer;

  if (delete_emp == NULL) {
    root_emp_pointer = NULL;
    return;
  }

  right_node = delete_emp->right_child_ptr;
  left_node = delete_emp->left_child_ptr;

  if ((right_node == NULL) && (left_node == NULL)) {
    free(delete_emp->name);
    delete_emp->name = NULL;
    free(delete_emp);
    delete_emp = NULL;
    *root_emp_pointer = NULL;
    root_emp_pointer = NULL;
  }
  else if (right_node != NULL) {
    prev_node = delete_emp;
    delete_tree(&right_node);
    prev_node->right_child_ptr = NULL;
    delete_tree(&prev_node);
  }
  else if (left_node != NULL) {
    prev_node = delete_emp;
    delete_tree(&left_node);
    prev_node->left_child_ptr = NULL;
    delete_tree(&prev_node);
  }
} /* delete_tree() */

/*
 * This function is used to traverse through the tree based on the order
 * provided and return the employee at the provided index.
 * The function first checks if the root is null. If not, it calls the
 * get_node_count function with the root to get the number of nodes in the
 * tree. Next, the function mallocs the nodes pointer with the structure's size
 * and the tree_size. Then, switch statements are used based on the travel
 * order. Within each case, the corresponding traversal helper function is
 * called. This assigns the entire tree to the nodes variable. Lastly, it sets
 * the employee at the index passed as the third parameter to a variable
 * returned by the function.
 */

employee_t *traverse_employees(employee_t *root_employee, int travel_order,
                               int emp_index) {
  int tree_size = 0;
  int index = 0;
  employee_t* index_emp = NULL;
  employee_t** nodes = NULL;

  assert(travel_order > 0);
  assert(travel_order < 5);
  assert(emp_index >= 0);

  if (root_employee == NULL) {
    return NULL;
  }

  tree_size = get_node_count(root_employee);
  nodes = (employee_t**)malloc(sizeof(employee_t*) * tree_size);

  switch (travel_order) {
    case 1:
      prefix_traversal(root_employee, nodes, &index);
      break;
    case 2:
      postfix_traversal(root_employee, nodes, &index);
      break;
    case 3:
      inorder_traversal(root_employee, nodes, &index);
      break;
    case 4:
      reverse_traversal(root_employee, nodes, &index);
      break;
    default:
      break;
  }

  index_emp = nodes[emp_index];
  free(nodes);
  nodes = NULL;
  return index_emp;
} /* traverse_employees() */

/*
 * This function is used to return the previous employee in the tree based on
 * the employee name given as the second parameter.
 * The function first finds the number of nodes in the tree using the
 * get_node_count helper function. It then allocates memory of the size of the
 * structure and the number of nodes to the nodes variable. It then calls the
 * inorder_traversal helper function to get the tree in sorted order in nodes.
 * A for loop is then used to iterate through all the nodes of tree. Within the
 * for loop, an if statement with strcmp is used to check if the current node
 * matches with the provided name. If it does, another if statement is used to
 * verify that it is not the root node and then assigns the previous node to
 * emp_prev.
 */

employee_t* previous_employee(employee_t* root, char* emp_name) {
  int tree_size = 0;
  int index = 0;
  employee_t** nodes = NULL;
  employee_t* emp_prev = NULL;

  assert(emp_name != NULL);

  tree_size = get_node_count(root);
  nodes = (employee_t**) malloc(sizeof(employee_t*) * tree_size);

  inorder_traversal(root, nodes, &index);

  for (int i = 0; i < index; i++) {
    if (strcmp(emp_name, nodes[i]->name) == 0) {
      if (i > 0) {
        emp_prev = nodes[i - 1];
      }
    }
  }
  free(nodes);
  nodes = NULL;
  return emp_prev;
} /* previous_employee() */


/*
 * This is one of the helper functions used in the traverse_employee function.
 * The function accepts a pointer to the root node, a pointer/array to hold all
 * the element in the tree and the index to traverse through all the employees.
 * The function uses an if statement to check if the root is not NULL. In case
 * it is not null it assigns the current root to the current index of nodes and
 * then updates the index. It then calls the function recursively with the
 * root's left child followed by the root's right child in prefix order.
 */

void prefix_traversal(employee_t* root, employee_t** nodes, int* index) {
  if (root != NULL) {
    nodes[*index] = root;
    (*index)++;

    prefix_traversal(root->left_child_ptr, nodes, index);

    prefix_traversal(root->right_child_ptr, nodes, index);
  }
} /* prefix_traversal() */

/*
 * This is one of the helper functions used in the traverse_employee function.
 * The function accepts a pointer to the root node, a pointer/array to hold all
 * the element in the tree and the index to traverse through all the employees.
 * The function uses an if statement to check if the root is not NULL. In case
 * it is not null it calls itself recursively with the root's left child
 * followed by the right child. Lastly, it assigns the current root to the
 * current index of nodes in postfix order.
 */

void postfix_traversal(employee_t* root, employee_t** nodes, int* index) {
  if (root != NULL) {
    postfix_traversal(root->left_child_ptr, nodes, index);

    postfix_traversal(root->right_child_ptr, nodes, index);

    nodes[*index] = root;
    (*index)++;
  }
} /* postfix_traversal() */

/*
 * This is one of the helper functions used in the traverse_employee function.
 * The function accepts a pointer to the root node, a pointer/array to hold all
 * the element in the tree and the index to traverse through all the employees.
 * The function uses an if statement to check if the root is not NULL. In case
 * it is not null it calls itself recursively with the root's left child. It
 * then assigns the current root to the current index of nodes. This is
 * followed by calling the function recursively with the root's right child in
 * inorder order.
 */

void inorder_traversal(employee_t* root, employee_t** nodes, int* index) {
  if (root != NULL) {
    inorder_traversal(root->left_child_ptr, nodes, index);

    nodes[*index] = root;
    (*index)++;

    inorder_traversal(root->right_child_ptr, nodes, index);
  }
} /* inorder_traversal() */

/*
 * This is one of the helper functions used in the traverse_employee function.
 * The function accepts a pointer to the root node, a pointer/array to hold all
 * the element in the tree and the index to traverse through all the employees.
 * The fucntion uses an if statement to check if the root is not NULL. In case
 * it is not null it calls itself recursively with the root's right child. It
 * then assigns the current root to the current index of nodes  This is
 * followed by calling the function recursively with the root's left child in
 * reverse order.
 */

void reverse_traversal(employee_t* root, employee_t** nodes, int* index) {
  if (root != NULL) {
    reverse_traversal(root->right_child_ptr, nodes, index);

    nodes[*index] = root;
    (*index)++;

    reverse_traversal(root->left_child_ptr, nodes, index);
  }
} /* reverse_traversal() */

/*
 * This is one of the functions used in the traverse_employees and
 * previous_employee functions. The fucntion finds the total number of nodes in
 * the tree. The function first checks if the root is null. If it is null the
 * function returns 0 for that call. If not, count is initialized to 1 and then
 * the function is called recursively and added to the current value of count
 * starting with the root's left child followed by the right child. Count is
 * returned at the end of the function's implementation.
 */

int get_node_count(employee_t* root) {
  if (root == NULL) {
    return 0;
  }
  int count = 1;
  count += get_node_count(root->left_child_ptr);
  count += get_node_count(root->right_child_ptr);

  return count;
} /* get_node_count() */