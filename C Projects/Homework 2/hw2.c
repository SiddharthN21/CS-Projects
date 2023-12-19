/* Siddharth Nadgaundi, hw2.c, CS 24000, Spring 2023
 *
 */

/* Add any includes here */
#include "hw2.h"

#include <ctype.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * This function is used to calculate the average speed of the cars provided
 * by the manufacturer passed in as a parameter.
 *
 * The function first checks if the file can be opened and returns
 * FILE_READ_ERR is the file can't be read. Next, a while loop is used to read
 * in the data of the file in a character array. Subsequently, sscanf() is
 * used to read in the values to specific variables corresponding to various
 * parts of the file. In each instance, BAD_RECORD is returned if the return
 * value of sscanf() does not match the expected value.
 * The function then performs the necessary calculations and stores it in the
 * average_speed variable. Finally the average_speed variable is divided by
 * the manufacturer_found variable from the while loop in order to get the
 * average speed. In case no manufacturers are found, NO_DATA_POINTS is
 * returned.
 */

float average_speed_of_manufacturer(char *file_name, char *manufacturer) {
  FILE *fp = 0;
  char buff[121] = {0};
  char buff1[MAX_ID_LENGTH] = {0};
  char buff2[MAX_FIELD_LENGTH] = {0};
  char buff3[50] = {0};
  char buff4[50] = {0};
  char buff5[50] = {0};
  int  pitstops = 0;
  int distance = 0;
  float pitstop_time = 0.0;
  char buff6[50] = {0};
  char buff7[50] = {0};
  float test_distance = 0.0;
  float test_time = 0.0;
  float cost = 0.0;
  float average_speed = 0.0;
  int manufacturer_found = 0;
  int ret = 0;

  fp = fopen(file_name, "r");
  if (fp == NULL) {
    return FILE_READ_ERR;
  }

  while (fscanf(fp, "%[^\n] ", buff) != EOF) {
    ret = sscanf(buff, "%5[^,],%[^,],%[^,'km']km,%[^,'s']s,%[^,],$%[^,'$'],%" \
    "[^'%'\n]", buff1, buff2, buff3, buff4, buff5, buff6, buff7);
    if ((ret != 7) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff5, "%d/%d/%f", &pitstops, &distance, &pitstop_time);
    if ((ret != 3) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff3, "%f", &test_distance);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff4, "%f", &test_time);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff6, "%f", &cost);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }

    if (strcmp(manufacturer, buff2) == 0) {
      average_speed = average_speed + ((test_distance * 1000) / test_time);
      manufacturer_found = manufacturer_found + 1;
    }
  }
  if (manufacturer_found == 0) {
    fclose(fp);
    fp = NULL;
    return NO_DATA_POINTS;
  }
  else {
    average_speed = average_speed / manufacturer_found;
  }
  fclose(fp);
  fp = NULL;
  return average_speed;
} /* average_speed_of_manufacturer() */


/*
 * Assumption - Id passed should be unique to get correct results.
 *
 * This function is used to calaculate the number of expected pitstops by
 * passing in the id of the car as a parameter.
 *
 * The function first checks if the file can be opened and returns
 * FILE_READ_ERR is the file can't be read. Next, a while loop is used to read
 * in the data of the file in a character array. Subsequently, sscanf() is
 * used to read in the values to specific variables corresponding to various
 * parts of the file. In each instance, BAD_RECORD is returned if the return
 * value of sscanf() does not match the expected value.
 * The function also checks if the id is valid and returns BAD_ID if it does
 * not match the expected format. The function performs the required
 * calculations and stores it in a temp variable. Finally, if there is no data
 * for the given id, NO_DATA_POINTS is returned. If not, temp is assigned to
 * expected pitstops and returned.
 */

int expected_pitstops(char *file_name, char *id) {
  FILE *fp = 0;
  char buff[121] = {0};
  char buff1[MAX_ID_LENGTH] = {0};
  char buff2[MAX_FIELD_LENGTH] = {0};
  char buff3[50] = {0};
  char buff4[50] = {0};
  char buff5[50] = {0};
  int  pitstops = 0;
  int pit_distance = 0;
  float pitstop_time = 0.0;
  char buff6[50] = {0};
  char buff7[50] = {0};
  float test_distance = 0.0;
  float test_time = 0.0;
  float cost = 0.0;
  int exp_pitstops = 0;
  int rec_found = 0;
  int ret = 0;
  double temp = 0.0;
  int len = 0;

  while (*(id + len) != '\0') {
    ++len;
  }
  if (len != (MAX_ID_LENGTH - 1)) {
    return BAD_ID;
  }

  fp = fopen(file_name, "r");
  if (fp == NULL) {
    return FILE_READ_ERR;
  }

  while (fscanf(fp, "%[^\n] ", buff) != EOF) {
    ret = sscanf(buff, "%5[^,],%[^,],%[^,'km']km,%[^,'s']s,%[^,],$%[^,'$'],%" \
    "[^'%'\n]", buff1, buff2, buff3, buff4, buff5, buff6, buff7);

    if ((ret != 7) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff5, "%d/%d/%f", &pitstops, &pit_distance, &pitstop_time);
    if ((ret != 3) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff3, "%f", &test_distance);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff4, "%f", &test_time);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff6, "%f", &cost);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }

    if (strcmp(id, buff1) == 0) {
      temp = ((RACE_LENGTH / (double)pit_distance) * (double) pitstops);
      temp = ceil(temp);
      rec_found = rec_found + 1;
    }
  }
  if (rec_found != 1) {
    fclose(fp);
    fp = NULL;
    return NO_DATA_POINTS;
  }
  fclose(fp);
  fp = NULL;
  exp_pitstops = temp;
  return exp_pitstops;
} /* expected_pitstops() */

/*
 * This function is used to find the maximum pitstops based on the
 * manufacturer passed in as a parameter.
 *
 * The function first checks if the file can be opened and returns
 * FILE_READ_ERR is the file can't be read. Next, a while loop is used to read
 * in the data of the file in a character array. Subsequently, sscanf() is
 * used to read in the values to specific variables corresponding to various
 * parts of the file. In each instance, BAD_RECORD is returned if the return
 * value of sscanf() does not match the expected value.
 * This function performs the same calculations as expected_pitstops() and
 * multiplies the result with the pitstop time. In every iteration of the
 * loop, the previous instance of the manufacturer's pitstop time is comapred
 * with the current one. If the current time is greater than the previous time
 * max_total_pitstop is assigned to the new value. If no manufacturer is found
 * NO_DATA_POINTS is returned. If not, max_total_pitstop is returned.
 */

float find_max_total_pitstop(char *file_name, char *manufacturer) {
  FILE *fp = 0;
  char buff[121] = {0};
  char buff1[MAX_ID_LENGTH] = {0};
  char buff2[MAX_FIELD_LENGTH] = {0};
  char buff3[50] = {0};
  char buff4[50] = {0};
  char buff5[50] = {0};
  int  pitstops = 0;
  int pit_distance = 0;
  float pitstop_time = 0.0;
  char buff6[50] = {0};
  char buff7[50] = {0};
  float test_distance = 0.0;
  float test_time = 0.0;
  float cost = 0.0;
  double exp_pitstops = 0.0;
  float temp = 0.0;
  float max_total_pitstop = 0.0;
  int manufacturer_found = 0;
  int ret = 0;

  fp = fopen(file_name, "r");
  if (fp == NULL) {
    return FILE_READ_ERR;
  }

  while (fscanf(fp, "%[^\n] ", buff) != EOF) {
    ret = sscanf(buff, "%5[^,],%[^,],%[^,'km']km,%[^,'s']s,%[^,],$%[^,'$'],%" \
    "[^'%'\n]", buff1, buff2, buff3, buff4, buff5, buff6, buff7);

    if ((ret != 7) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff5, "%d/%d/%f", &pitstops, &pit_distance, &pitstop_time);
    if ((ret != 3) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff3, "%f", &test_distance);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff4, "%f", &test_time);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff6, "%f", &cost);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      return BAD_RECORD;
    }

    if (strcmp(manufacturer, buff2) == 0) {
      exp_pitstops = ((RACE_LENGTH / (double)pit_distance) * (double) pitstops);
      exp_pitstops = ceil(exp_pitstops);
      temp = (exp_pitstops * pitstop_time);
      if (temp > max_total_pitstop) {
        max_total_pitstop = temp;
      }
      manufacturer_found = manufacturer_found + 1;
    }
  }

  if (manufacturer_found == 0) {
    fclose(fp);
    fp = NULL;
    return NO_DATA_POINTS;
  }

  fclose(fp);
  fp = NULL;
  return max_total_pitstop;
} /* find_max_total_pitstop() */

/*
 * This function is used to generate the expected cost report from the in_file
 * and write that data to the out_file.
 *
 * The function first checks if the file can be opened and returns
 * FILE_READ_ERR is the file can't be read. Next, a while loop is used to read
 * in the data of the file in a character array. Subsequently, sscanf() is
 * used to read in the values to specific variables corresponding to various
 * parts of the file. In each instance, BAD_RECORD is returned if the return
 * value of sscanf() does not match the expected value.
 * In addition th this, the function checks if the out_file can be written to
 * and returns FILE_WRITE_ERR if it can't. The function also checks if the
 * is blank and returns NO_DATA_POINTS. If not, the pointer is set to the
 * beginning of the file and read through. During each iteration of the loop,
 * the expected breakdown cost is calculated and written to the out_file along
 * with the id and manufacturer. If the file was successfully written to, the
 * function returns OK.
 */

int generate_expected_cost_report(char *in_file, char *out_file) {
  FILE *fp = 0;
  char buff[121] = {0};
  char buff1[MAX_ID_LENGTH] = {0};
  char buff2[MAX_FIELD_LENGTH] = {0};
  char buff3[50] = {0};
  char buff4[50] = {0};
  char buff5[50] = {0};
  int  pitstops = 0;
  int distance = 0;
  float pitstop_time = 0.0;
  char buff6[50] = {0};
  char buff7[50] = {0};
  float test_distance = 0.0;
  float test_time = 0.0;
  float cost = 0.0;
  double breakdown_rate = 0.0;
  float exp_brk_cost = 0.0;
  int ret = 0;
  FILE *file_wptr = 0;
  int count = 0;
  int rec_counter = 0;

  fp = fopen(in_file, "r");
  if (fp == NULL) {
    return FILE_READ_ERR;
  }

  fseek(fp, 0, SEEK_END);
  count = ftell(fp);
  if (count == 0) {
    fclose(fp);
    fp = NULL;
    return NO_DATA_POINTS;
  }
  else {
    fseek(fp, 0 , SEEK_SET);
  }

  file_wptr = fopen(out_file, "w");
  if (file_wptr == NULL) {
    fclose(fp);
    fp = NULL;
    return FILE_WRITE_ERR;
  }

  while (fscanf(fp, "%[^\n] ", buff) != EOF) {
    ret = sscanf(buff, "%5[^,],%[^,],%[^,'km']km,%[^,'s']s,%[^,],$%[^,'$'],%" \
    "[^'%'\n]", buff1, buff2, buff3, buff4, buff5, buff6, buff7);

    if ((ret != 7) || (ret == EOF)) {
      fclose(fp);
      fp = NULL;
      fclose(file_wptr);
      file_wptr = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff5, "%d/%d/%f", &pitstops, &distance, &pitstop_time);
    if ((ret != 3) || (ret == EOF)) {
      fclose(fp);
      fclose(file_wptr);
      fp = NULL;
      file_wptr = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff3, "%f", &test_distance);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fclose(file_wptr);
      fp = NULL;
      file_wptr = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff4, "%f", &test_time);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fclose(file_wptr);
      fp = NULL;
      file_wptr = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff6, "%f", &cost);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fclose(file_wptr);
      fp = NULL;
      file_wptr = NULL;
      return BAD_RECORD;
    }
    ret = sscanf(buff7, "%lf", &breakdown_rate);
    if ((ret != 1) || (ret == EOF)) {
      fclose(fp);
      fclose(file_wptr);
      fp = NULL;
      file_wptr = NULL;
      return BAD_RECORD;
    }

    exp_brk_cost = ((float) cost * (float) breakdown_rate) / 100;

    fprintf(file_wptr, "Car number %s made by %s has a breakdown cost " \
    "expectancy of $%.2f.\n", buff1, buff2, exp_brk_cost);

    rec_counter++;
  }
  fclose(fp);
  fclose(file_wptr);
  fp = NULL;
  file_wptr = NULL;
  if (rec_counter == 0) {
    return BAD_RECORD;
  }
  return OK;
} /* generate_expected_cost_report() */