
/* Siddharth Nadgaundi, hw5.c, CS 24000, Spring 2023
 * Last updated February 22, 2023
 */

/* Add any includes here */
#include "hw5.h"

#include <assert.h>
#include <ctype.h>
#include <math.h>
#include <stdio.h>
#include <string.h>

//NULL terminate char array


/*
 * This function is used to read the records in the file from the given index
 * position by moving the file pointer to the required index. The function uses
 * if statements to evaluate if the provided offset exists within the file or
 * not.
 */

cupboard_t read_cupboard(FILE* input_file, int file_index) {
  cupboard_t temp_cupboard = {{'\0'}, 0.0, 0, 0, 0, {{0.0}, {0.0}}};

  assert(input_file != NULL);
  assert(file_index >= 0);

  int record_location = file_index * sizeof(temp_cupboard);
  if (fseek(input_file, record_location, SEEK_SET) == 0) {
    if (fread(&temp_cupboard, sizeof(temp_cupboard), 1, input_file) == 1) {
      return temp_cupboard;
    }
  }

  return BAD_CUPBOARD;
} /* read_cupboard() */

/*
 * This function is used to write the data in the structure to the file from
 * the corresponding offset, similar to the one seen in read_cupboard(). Assert
 * statements are used to check if the second argument is valid. A for loop is
 * used to make sure that each element of the structure is greater than or
 * equal to 0. If statements are used to check for the corresponding error
 * cases.
 */

int write_cupboard(FILE* input_file, cupboard_t temp_cupboard, int file_index) {
  assert(input_file != NULL);
  assert(file_index >= 0);
  assert(temp_cupboard.id != NULL);
  for (int i = 0; i < (MAX_ID_LEN - 1); i++) {
    assert(isalnum(temp_cupboard.id[i]));
  }
  assert(strlen(temp_cupboard.id) == (MAX_ID_LEN - 1));
  assert(temp_cupboard.max_weight > 0);
  assert(temp_cupboard.age >= 0);
  assert((temp_cupboard.material >= STEEL) &&
  (temp_cupboard.material <= CARDBOARD));
  assert((temp_cupboard.content >= FOOD) &&
  (temp_cupboard.content <= ELECTRONICS));
  for (int i = 0; i < N_HEIGHT; i++) {
    for (int j = 0; j < N_WIDTH; j++) {
      assert(temp_cupboard.storage[i][j] >= 0);
    }
  }

  long record_location = file_index * sizeof(temp_cupboard);
  fseek(input_file, 0, SEEK_END);
  if (ftell(input_file) < record_location) {
    return WRITE_ERROR;
  }
  if (fseek(input_file, record_location, SEEK_SET) != 0) {
    return WRITE_ERROR;
  }
  if (fwrite(&temp_cupboard, sizeof(temp_cupboard), 1, input_file) != 1) {
    return WRITE_ERROR;
  }
  return OK;
} /* write_cupboard() */

/*
 * This function is used to calculate the actual max weight of the cupboard
 * with the passed id. A while loop is used to check if an id is a bad id.
 * In case the id is correct, it is compared with the passed parameter to
 * check if it is the required cupboard. Switch statements are then used to
 * calculate the weight based on the material of the cupboard.
 */

float find_actual_max_weight(FILE* input_file, char* cubboard_id) {
  float actual_weight = 0.0;
  int age = 0;
  float age_div = 0.0;
  cupboard_t temp = {{'\0'}, 0.0, 0, 0, 0, {{0.0}, {0.0}}};
  int file_index = 0;

  assert(input_file != NULL);
  assert(cubboard_id != NULL);

  temp = read_cupboard(input_file, file_index);
  while (strcmp(temp.id, BAD_CUPBOARD.id) != 0) {
    if (strcmp(temp.id, cubboard_id) == 0) {
      age = temp.age;
      switch (temp.material) {
        case STEEL:
          age_div = age / 5.0;
          age = ceil(age_div);
          actual_weight = (temp.max_weight * pow(0.95, age));
          break;
        case WOOD:
          age_div = age / 7.0;
          age = ceil(age_div);
          actual_weight = (temp.max_weight * pow(0.90, age));
          break;
        case PLASTIC:
          age_div = age / 3.0;
          age = ceil(age_div);
          actual_weight = (temp.max_weight * pow(0.88, age));
          break;
        case CARDBOARD:
          age_div = age / 2.0;
          age = ceil(age_div);
          actual_weight = (temp.max_weight * pow(0.75, age));
          break;
      }
      return actual_weight;
    }
    file_index++;
    temp = read_cupboard(input_file, file_index);
  }
  return NO_SUCH_CUPBOARD;
} /* find_actual_max_weight() */

/*
 * This function is used to check if a cupboard is holding more weight than
 * its maximum value. Each record of the input while is read using a while loop
 * to check for a bad id. A double for loop is used to iterate over all the
 * elements of the current cupboard in order to get its currrent weight. The
 * current weight is then compared against the actual weight to find out if the
 * cupboard is overloaded.
 */

int find_overloaded_cupboard(FILE* input_file) {
  float actual_max_weight = 0.0;
  float current_item_wt = 0.0;
  cupboard_t temp = {{'\0'}, 0.0, 0, 0, 0, {{0.0}, {0.0}}};
  int file_index = 0;
  char cup_id[MAX_ID_LEN] = {'\0'};

  assert(input_file != NULL);
  temp = read_cupboard(input_file, file_index);
  while (strcmp(temp.id, BAD_CUPBOARD.id) != 0) {
    strcpy(cup_id, temp.id);
    actual_max_weight = find_actual_max_weight(input_file, cup_id);
    for (int i = 0; i < N_HEIGHT; i++) {
      for (int j = 0; j < N_WIDTH; j++) {
        current_item_wt = current_item_wt + temp.storage[i][j];
      }
    }
    if (actual_max_weight < current_item_wt) {
      return file_index;
    }
    current_item_wt = 0.0;
    file_index++;
    temp = read_cupboard(input_file, file_index);
  }
  return OK;
} /* find_overloaded_cupboard() */

/*
 * This function is used to check the total storage space available for the
 * given content type. A while loop is used to check if the current id is a
 * bad id or not. In case it's not, an if statement is used to check if the
 * current cupboard is of the required content type. In case it is, a double
 * for loop is used to get the current weight of the cupboard. If the current
 * weight is less than the max weight, their difference is added to the
 * potential storage.
 */

float potential_storage_space(FILE* input_file, enum content_t content_type) {
  float pot_storage_space = 0.0;
  float actual_max_weight = 0.0;
  float current_item_wt = 0.0;
  cupboard_t temp = {{'\0'}, 0.0, 0, 0, 0, {{0.0}, {0.0}}};
  int file_index = 0;
  char cup_id[MAX_ID_LEN] = {'\0'};
  int record_found = 0;

  assert(input_file != NULL);
  assert((content_type <= 2) && (content_type >= 0));
  temp = read_cupboard(input_file, file_index);
  while (strcmp(temp.id, BAD_CUPBOARD.id) != 0) {
    if (temp.content == content_type) {
      strcpy(cup_id, temp.id);
      actual_max_weight = find_actual_max_weight(input_file, cup_id);
      for (int i = 0; i < N_HEIGHT; i++) {
        for (int j = 0; j < N_WIDTH; j++) {
          current_item_wt = current_item_wt + temp.storage[i][j];
        }
      }
      if ((actual_max_weight - current_item_wt) > 0.0) {
        pot_storage_space = (pot_storage_space +
        (actual_max_weight - current_item_wt));
      }
      record_found = 1;
    }
    current_item_wt = 0.0;
    file_index++;
    temp = read_cupboard(input_file, file_index);
  }
  if (record_found == 1) {
    return pot_storage_space;
  }
  else {
    return NO_SUCH_CONTENT;
  }
} /* potential_storage_space() */

/*
 * This function will place the given object into the required cupboard.
 * A for loop is used to check if the given id is alpha-numeric or not. A while
 * loop is used to iterate through the input file and check if the current id
 * is a bad id or not. An if statement is then used to check if the current id
 * is the required id. In case it is the correct id, an if statement is used to
 * check if the width and height after the addition of the object are falling
 * within N_WIDTH and N_HEIGHT. Then a  double for loop is used to check if the
 * weight added is less than the max weight. The if statement compares the
 * current cupboard weight against the max weight to distribute the weight
 * evenly or to return WEIGHT_ISSUE.
 */

int place_object(FILE* input_file, int width, int height, int mass,
                 char * cubboard_id, int x_pos, int y_pos) {

  cupboard_t temp = {{'\0'}, 0.0, 0, 0, 0, {{0.0}, {0.0}}};
  int file_index = 0;
  float current_item_wt = 0.0;
  float temp_weight = 0.0;
  float actual_weight = 0.0;

  assert(input_file != NULL);
  assert(width > 0);
  assert(height > 0);
  assert(mass > 0);
  assert(cubboard_id != NULL);
  for (int i = 0; i < (MAX_ID_LEN - 1); i++) {
    assert(isalnum(cubboard_id[i]));
  }
  assert(strlen(cubboard_id) == (MAX_ID_LEN - 1));
  assert((x_pos >= 0) && (x_pos < N_WIDTH));
  assert((y_pos >= 0) && (y_pos < N_HEIGHT));

  temp = read_cupboard(input_file, file_index);
  while (strcmp(temp.id, BAD_CUPBOARD.id) != 0) {
    if (strcmp(temp.id, cubboard_id) == 0) {
      if (((x_pos + width) < N_WIDTH) && ((y_pos + height) < N_HEIGHT)) {
        for (int i = y_pos; i < (y_pos + height); i++) {
          for (int j = x_pos; j < (x_pos + width); j++) {
            temp_weight = temp_weight + temp.storage[i][j];
          }
        }
      }
      else {
        return SPACE_ISSUE;
      }
      if (temp_weight > 0) {
        return SPACE_ISSUE;
      }
      for (int i = 0; i < N_HEIGHT; i++) {
        for (int j = 0; j < N_WIDTH; j++) {
          current_item_wt = current_item_wt + temp.storage[i][j];
        }
      }
      actual_weight = find_actual_max_weight(input_file, cubboard_id);
      if ((current_item_wt + mass) < actual_weight) {
        for (int i = y_pos; i < (y_pos + height); i++) {
          for (int j = x_pos; j < (x_pos + width); j++) {
            temp.storage[i][j] = (mass / (width * height * 1.0));
          }
        }
      }
      else {
        return WEIGHT_ISSUE;
      }
      write_cupboard(input_file, temp, file_index);
      return OK;
    }
    file_index++;
    temp = read_cupboard(input_file, file_index);
  }
  return NO_SUCH_CUPBOARD;
} /* place_object() */