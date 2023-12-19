#ifndef HW12_H
#define HW12_H

#define APP    (1)
#define PHONE  (2)
#define CURRENT_YEAR  (2023)

/* Generic node structure */

typedef struct node {
  struct node *left_child;
  struct node *right_child;
  void *data;
  void (*print)(void *);
  void (*delete)(void **);
  int (*compare)(void *, void *);
} node_t;

typedef struct app_s {
  char *name;
  int size;
  float price;
  float rating;
} app_t;

typedef struct phone_s {
  char *model;
  int storage;
  int release_year;
  float price;
  float battery_life;
} phone_t;

/*
 * Functions of the Tree
 */

void print_tree(node_t *);
void delete_tree(node_t **);

/*
 * Functions of the Tree-nodes
 */

void print_node(node_t *);
void create_node(node_t **, void *, void (*)(void*),
                 void (*)(void **), int (*)(void *, void *));
void delete_node(node_t **);
void insert_node(node_t **, node_t *);
void remove_node(node_t **, node_t *);
node_t **find_nodes(node_t *, void *, int *);

/*
 * Functions for accessing/manipulating node-data
 */

/* data access/manipulation functions for app structure */

void print_app_data(void *);
void create_app_data(void **, const char *, int, float, float);
void delete_app_data(void **);
int  compare_app_data(void *, void *);

/* data access/manipulation functions for phone structure */

void print_phone_data(void *);
void create_phone_data(void **, const char *, int, int, float, float);
void delete_phone_data(void **);
int  compare_phone_data(void *, void *);

#endif // HW12_H