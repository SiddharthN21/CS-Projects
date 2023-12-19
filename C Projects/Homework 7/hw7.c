
/* Siddharth Nadgaundi, hw7.c, CS 24000, Spring 2023
 * Last updated March 8, 2023
 */

/* Add any includes here */
#include "hw7.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Define your global variables here */
int g_course_count = 0;
course_t g_course_array[MAX_COURSES] = {{{'\0'}, {{'\0'}, {'\0'}}, 0, 0,
{NULL}}};


/*
 * This function is used to read in the courses present in the input file to
 * g_course_array.
 * A while loop is used to read in the data to a character array. Then, if
 * statements are used along with sscanf to read the data from buff into the
 * corresponding arrays. The prerequisites are read using a seperate sscanf
 * statement and copied into the structure using strncpy and strncat. Finally,
 * the structure is assigned to g_course_array.
 */

int read_courses(char *file_name) {
  FILE *fp = NULL;
  char buff[MAX_BUFF_LEN] = {'\0'};
  char buff2[MAX_BUFF_LEN] = {'\0'};
  char buff3[MAX_BUFF_LEN] = {'\0'};
  int num_courses = 0;
  int course_type = 0;
  int credit_hours = 0;
  int prereq_y_n = 1;

  assert(file_name != NULL);

  fp = fopen(file_name, "r");
  if (fp == NULL) {
    return NON_READABLE_FILE;
  }

  while (fgets(buff, sizeof(buff), fp) != NULL) {
    if (num_courses >= MAX_COURSES) {
      fclose(fp);
      fp = NULL;
      return TOO_MUCH_DATA;
    }

    if (sscanf(buff, "%[^|]|%[^|]|%d|%d", buff2, buff3, &course_type,
    &credit_hours) != 4) {
      prereq_y_n = 0;
      if (sscanf(buff, "%[^||]||%d|%d", buff2, &course_type,
      &credit_hours) != 3) {
        fclose(fp);
        fp = NULL;
        return BAD_RECORD;
      }
    }

    course_t course = {{'\0'}, {{'\0'}, {'\0'}}, 0, 0, {NULL}};
    strncpy (course.course_name, buff2, MAX_NAME_LEN - 1);
    strncat (course.course_name, "\0", 1);
    course.course_type = course_type;
    course.credit_hours = credit_hours;

    if (prereq_y_n == 1) {
      char prereq_1[MAX_NAME_LEN] = {'\0'};
      char prereq_2[MAX_NAME_LEN] = {'\0'};
      char prereq_3[MAX_NAME_LEN] = {'\0'};
      char prereq_4[MAX_NAME_LEN] = {'\0'};
      int num_prereqs = 0;
      int ret = 0;

      ret = sscanf(buff3, "?%[^?]?%[^?]?%[^?]?%[^?]", prereq_1, prereq_2,
      prereq_3, prereq_4);
      if ((ret > 0) && (ret < 4)) {
        strncpy (course.prerequisites[num_prereqs], prereq_1, MAX_NAME_LEN - 1);
        strncat (course.prerequisites[num_prereqs], "\0", 1);
        num_prereqs++;
        strncpy (course.prerequisites[num_prereqs], prereq_2, MAX_NAME_LEN - 1);
        strncat (course.prerequisites[num_prereqs], "\0", 1);
        num_prereqs++;
        strncpy (course.prerequisites[num_prereqs], prereq_3, MAX_NAME_LEN - 1);
        strncat (course.prerequisites[num_prereqs], "\0", 1);
        num_prereqs++;
      }
      else {
        fclose(fp);
        fp = NULL;
        return BAD_RECORD;
      }
    }

    prereq_y_n = 1;
    g_course_array[num_courses] = course;
    num_courses++;
  }

  fclose(fp);
  fp = NULL;
  if (num_courses == 0) {
    return NO_COURSES;
  }

  g_course_count = num_courses;
  return num_courses;

} /* read_courses() */

/*
 * This function is used to link all the pointers of the course_t structure to
 * its corresponding prerequisite.
 * A for loop is used to iterate through a given courses prerequisites. Within
 * the for loop, an if statement is used to check if the given course has a
 * prerequisite in the list. If it does, it is copied in a temporary variable
 * and a while loop is used to iterate over the courses of the array to find
 * the course. If it is found, the pointer is set to the found prerequisite.
 */

void link_courses() {
  course_t temp_course = {{'\0'}, {{'\0'}, {'\0'}}, 0, 0, {NULL}};
  int num_courses = 0;
  int num_course2 = 0;
  char pre_req_course[MAX_NAME_LEN] = {'\0'};

  while (num_courses < g_course_count) {
    temp_course = g_course_array[num_courses];

    for (int i = 0; i < MAX_PREREQ; i++) {
      if (strlen(temp_course.prerequisites[i]) > 0) {
        strncpy(pre_req_course, temp_course.prerequisites[i], MAX_NAME_LEN - 1);
        num_course2 = 0;
        while (num_course2 < g_course_count) {
          if (strncmp (g_course_array[num_course2].course_name, pre_req_course,
          ((strlen (pre_req_course) - 1))) == 0) {
            g_course_array[num_courses].prerequisite_course_ptrs[i] =
            &g_course_array[num_course2];
          }
          num_course2++;
        }
      }
    }
    num_courses++;
  }
} /* link_courses() */

/*
 * This function is used to return the largest chain of prerequsites for a
 * given course.
 * A while loop is used to iterate over all the courses provided. An if
 * statement is used to check if any course from the file matches the passed
 * parameter. If it does a while loop is used to iterate thorough all the
 * prerequisites of the passed course. Another while loop is used to iterate
 * over the chain where the prerequisite pointer is not NULL. Within that loop
 * the prerequisite pointer is set to the next pointer that the course points
 * to. Finally, a for loop is used to find the max chain by comparing the count
 * at each index of the chain_count array.
 */

int find_longest_chain(char *course_code) {
  course_t temp_course = {{'\0'}, {{'\0'}, {'\0'}}, 0, 0, {NULL}};
  int num_courses = 0;
  struct course *prerequisite_course_ptr = NULL;
  int chain_count[MAX_PREREQ] = {0};
  int pre_req_count = 0;
  int max_chain_count = 0;
  int course_found = 0;

  assert(course_code != NULL);

  while (num_courses < g_course_count) {
    temp_course = g_course_array[num_courses];
    if (strncmp (temp_course.course_name, course_code,
    ((strlen (course_code) - 1))) == 0) {
      course_found = 1;
      while (pre_req_count < MAX_PREREQ) {
        prerequisite_course_ptr =
        temp_course.prerequisite_course_ptrs[pre_req_count];
        while (prerequisite_course_ptr != NULL) {
          chain_count[pre_req_count]++;
          prerequisite_course_ptr =
          prerequisite_course_ptr->prerequisite_course_ptrs[pre_req_count];
        }
        pre_req_count++;
      }
    }
    num_courses++;
  }
  if (course_found == 0) {
    return NO_COURSES;
  }
  for (int i = 0; i < MAX_PREREQ; i++) {
    if (max_chain_count < chain_count[i]) {
      max_chain_count = chain_count[i];
    }
  }
  return max_chain_count;
} /* find_longest_chain() */

/*
 * This function is used to find the maximum credit hours among all the chains
 * of the provided course.
 * A while loop is used to iterate over all the prerequisites. Within the while
 * loop, the prerequisite pointer is set to the current prerequisite pointer of
 * the passed course. As second while loop is then used to iterate over the
 * chain while the pointer is not NULL similar to find_longest_chain(). Within
 * the loop, the credit hours at the current indec are updated with the value
 * the pointer is currently pointing to. Lastly, a for loop is used th iterate
 * over the prerequisite count to compare the sum of credit hours of each
 * prerequisite chain and find the largest one.
 */

int find_max_hours_chain(int index) {
  course_t temp_course = {{'\0'}, {{'\0'}, {'\0'}}, 0, 0, {NULL}};
  struct course *prerequisite_course_ptr = NULL;
  int credit_hours[MAX_PREREQ] = {0};
  int pre_req_count = 0;
  int max_credit_hours = 0;

  assert(index >= 0);
  assert(index < g_course_count);

  temp_course = g_course_array[index];
  while (pre_req_count < MAX_PREREQ) {
    prerequisite_course_ptr =
    temp_course.prerequisite_course_ptrs[pre_req_count];
    while (prerequisite_course_ptr != NULL) {
      credit_hours[pre_req_count] = credit_hours[pre_req_count] +
      (prerequisite_course_ptr->credit_hours);
      prerequisite_course_ptr =
      prerequisite_course_ptr->prerequisite_course_ptrs[pre_req_count];
    }
    pre_req_count++;
  }
  for (int i = 0; i < MAX_PREREQ; i++) {
    if (max_credit_hours < credit_hours[i]) {
      max_credit_hours = credit_hours[i];
    }
  }

  return max_credit_hours;
} /* find_max_hours_chain() */