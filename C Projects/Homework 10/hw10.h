#ifndef HW10_H
#define HW10_H

/* Constant definitions */

#define PREFIX        (1)
#define POSTFIX       (2)
#define INORDER       (3)
#define REVERSE       (4)

/* Structure Definitions */

typedef struct tree_employee {
  char *name;

  struct tree_employee *left_child_ptr;
  struct tree_employee *right_child_ptr;
} employee_t;

/* Function prototypes */

employee_t *create_employee(char *);
void insert_employee(employee_t **, employee_t *);
employee_t *find_employee(employee_t *, char *);
void delete_tree(employee_t **);
employee_t *traverse_employees(employee_t *, int, int);
employee_t *previous_employee(employee_t *, char *);

#endif // HW10_H