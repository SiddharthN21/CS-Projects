#ifndef HW6_H
#define HW6_H

/* Constant definitions */

#define MAX_FILE_LEN  (50)
#define MAX_COURSES   (50)
#define MAX_NAME_LEN  (80)
#define MAX_BUFF_LEN  (200)
#define MAX_PREREQ    (3)

enum course_type_t {
  LECTURE,
  PSO,
  LABORATORY,
  RECITATION,
  INDEPENDENT,
};

typedef struct course {
  char course_name[MAX_NAME_LEN];
  char prerequisites[MAX_PREREQ][MAX_NAME_LEN];
  enum course_type_t course_type;
  int credit_hours;
} course_t;

/* Error codes */

#define NON_READABLE_FILE  (-1)
#define BAD_RECORD         (-2)
#define TOO_MUCH_DATA      (-3)
#define NO_COURSES         (-4)
#define NOT_FOUND          (-5)

/* Function prototypes */

int read_courses(char *);
int find_prerequisites(char *, enum course_type_t);

/* Global variables */

extern int g_course_count;
extern course_t g_course_array[MAX_COURSES];

#endif // HW6_H