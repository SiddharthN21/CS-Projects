#ifndef HW11_H
#define HW11_H


/* Structure Definitions */

typedef struct time_struct {
  int year;
  int month;
  int day;
  int hour;
  int minute;
} time_struct_t;

typedef struct event_info {
  char *name;
  time_struct_t start_time;
  time_struct_t end_time;
} event_info_t;

typedef struct list_node {
  struct event_info *event_info;
  struct list_node *next_event;
  struct list_node *prev_event;
} list_node_t;

typedef struct tree_node {
  struct event_info *event_info;
  struct tree_node *left_child_ptr;
  struct tree_node *right_child_ptr;
} tree_node_t;

/* Function prototypes */

void create_event(event_info_t **, char *, time_struct_t, time_struct_t);
void add_event(tree_node_t **, event_info_t *);
int count_expired_events(tree_node_t *, time_struct_t);
void add_to_calendar(tree_node_t *, list_node_t **);
void free_up_schedule(list_node_t **, int);
void delete_event_schedule(tree_node_t **);

#endif // HW11_H