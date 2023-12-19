#ifndef HW5_H
#define HW5_H

#include <stdio.h>

/* Constant definitions */

#define MAX_ID_LEN         (5)
#define N_WIDTH            (10)
#define N_HEIGHT           (15)

/* Error codes */

#define OK                 (-1)
#define NO_SUCH_CUPBOARD   (-2)
#define NO_SUCH_CONTENT    (-3)
#define BAD_BOARD          (-4)
#define SPACE_ISSUE        (-5)
#define WEIGHT_ISSUE       (-6)
#define WRITE_ERROR        (-7)

#define BAD_CUPBOARD ((cupboard_t){\
  .id = "BAD",\
  .max_weight = -1.0,\
  .age = -1,\
  .material = -1,\
  .content = -1,\
  .storage[N_HEIGHT - 1][N_WIDTH - 1] = -1.0})

enum material_t {
  STEEL,
  WOOD,
  PLASTIC,
  CARDBOARD,
};

enum content_t {
  FOOD,
  CLOTH,
  ELECTRONICS,
};

/* Structure Declarations */

typedef struct {
  char             id[MAX_ID_LEN];
  float            max_weight;
  int              age;
  enum material_t  material;
  enum content_t   content;
  float            storage[N_HEIGHT][N_WIDTH];
} cupboard_t;

/* Function prototypes */
cupboard_t read_cupboard(FILE *, int);
int write_cupboard(FILE *, cupboard_t, int);
float find_actual_max_weight(FILE *, char *);
int find_overloaded_cupboard(FILE *);
float potential_storage_space(FILE *, enum content_t);
int place_object(FILE *, int, int, int, char *, int, int);

#endif // HW5_H