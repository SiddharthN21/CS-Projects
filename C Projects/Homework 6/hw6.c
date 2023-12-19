
/* Siddharth Nadgaundi, hw6.c, CS 24000, Spring 2023
 * Last updated March 2, 2023
 */

/* Add any includes here */
#include "hw6.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Define your global variables here */
int g_course_count = 0;
course_t g_course_array[MAX_COURSES] = {{{'\0'}, {{'\0'}, {'\0'}}, 0, 0}};


/*
 * This function is used to read in the courses present in the input file to
 * g_course_array.
 * A while loop is used to read in the data to a character array. Then, if
 * statements are used along with sscanf to read the data from buff into the
 * corresponding arrays. The prerequisites are read using a seperate sscanf
 * statement and copied into the structure using strncpy and strncat. Finally,
 * the structure is assigned to g_course_array;
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
  int ret = 0;

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

    course_t course = {{'\0'}, {{'\0'}, {'\0'}}, 0, 0};
    strncpy(course.course_name, buff2, MAX_NAME_LEN - 1);
    strncat(course.course_name, "\0", 1);
    course.course_type = course_type;
    course.credit_hours = credit_hours;

    if (prereq_y_n == 1) {
      char prereq_1[MAX_NAME_LEN] = {'\0'};
      char prereq_2[MAX_NAME_LEN] = {'\0'};
      char prereq_3[MAX_NAME_LEN] = {'\0'};
      char prereq_4[MAX_NAME_LEN] = {'\0'};
      int num_prereqs = 0;

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
 * This function is used to return the largest credit hours for a particular
 * type of course for a given input course.
 * A while loop is used to iterate over the courses provided. An if statement
 * is used to check if the course is found. A for loop is then used to find the
 * number of prerequisites of the course. Another for loop is then used to
 * iterate over the prerequisites. This is followed by if statements to check
 * if the prerequisites of the provided course exists and if they have the same
 * type. After the loops, if staements are used to return any errors or the
 * max_course_credit.
 */

int find_prerequisites(char * course_name, enum course_type_t type) {
  int num_courses = 0;
  course_t temp_course = {{'\0'}, {{'\0'}, {'\0'}}, 0, 0};
  char pre_req_course[MAX_NAME_LEN] = {'\0'};
  int max_course_credit = 0;
  int pre_req_found = 0;
  int pre_req_with_type_found = 0;
  int course_found = 0;
  int prereq_count = 0;

  assert(course_name != NULL);
  assert(type < 5);

  if (g_course_count == 0) {
    return NO_COURSES;
  }

  while (num_courses < g_course_count) {
    temp_course = g_course_array[num_courses];
    if (strstr(temp_course.course_name, course_name) != NULL) {
      course_found = 1;
      prereq_count = 0;
      for (int i = 0; i < MAX_PREREQ; i++) {
        if (strlen(temp_course.prerequisites[i]) > 0) {
          prereq_count++;
        }
      }
      if (prereq_count == 0) {
        return 0;
      }
      for (int i = 0; i < prereq_count; i++) {
        strncpy(pre_req_course, temp_course.prerequisites[i], MAX_NAME_LEN - 1);
        int num_course2 = 0;
        while (num_course2 < g_course_count) {
          if (strncmp(g_course_array[num_course2].course_name, pre_req_course,
          ((strlen(pre_req_course) - 1))) == 0) {
            pre_req_found++;
            if (g_course_array[num_course2].course_type == type) {
              pre_req_with_type_found++;
              if (max_course_credit <
              g_course_array[num_course2].credit_hours) {
                max_course_credit = g_course_array[num_course2].credit_hours;
              }
            }
          }
          num_course2++;
        }
      }
    }
    num_courses++;
  }

  if ((course_found == 0) || (pre_req_found != prereq_count)) {
    return NOT_FOUND;
  }
  if (pre_req_with_type_found == 0) {
    return 0;
  }
  if (max_course_credit <= 0) {
    return 0;
  }
  return max_course_credit;
} /* find_prerequisites() */

/* Remember, you don't need a main function!
 * It is provided by hw6_main.c or hw6_test.o
 */