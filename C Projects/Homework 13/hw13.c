/* Siddharth Nadgaundi, April 26, 2023*/

#include "graphics.h"
#include "object.h"

#include <assert.h>
#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * This function is used to read the object file provided and dynamically
 * allocate the data to an object structure.
 */

object * read_object(const char *file_name) {
  FILE *fp = NULL;
  object *new_object = NULL;
  point **point_array = NULL;
  char buff[500] = {0};
  char obj_name[200] = {0};
  char color[20] = {0};
  char rgb[20] = {0};
  int count = 0;
  int ret = 0;
  int object_points = 0;
  int polygon_points = 0;
  int number_of_polygons = 0;
  int temp_num = 0;
  double x_val = 0.0;
  double y_val = 0.0;
  double z_val = 0.0;
  int err_val = 0;
  Uint8 red = 0;
  Uint8 green = 0;
  Uint8 blue = 0;
  int pt_val = 0;
  int buff_offset = 0;
  char temp_str[10] = {0};
  int poly_pt_offset = 0;
  int temp_poly_alloc = 0;


  assert(file_name != NULL);

  fp = fopen(file_name, "r");
  if (fp == NULL) {
    return NULL;
  }

  fseek(fp, 0, SEEK_END);
  count = ftell(fp);
  if (count == 0) {
    fclose(fp);
    fp = NULL;
    return NULL;
  }
  else {
    fseek(fp, 0 , SEEK_SET);
  }

  ret = fscanf(fp, "%[^\n] ", buff);
  if (ret == EOF) {
    fclose(fp);
    fp = NULL;
    return NULL;
  }
  ret = sscanf(buff, "%[^\n]", obj_name);
  if ((ret != 1) || (ret == EOF)) {
    fclose(fp);
    fp = NULL;
    return NULL;
  }
  ret = fscanf(fp, "%[^\n] ", buff);
  if (ret == EOF) {
    fclose(fp);
    fp = NULL;
    return NULL;
  }
  ret = sscanf(buff, "%d %d\n", &object_points, &number_of_polygons);
  if ((ret != 2) || (ret == EOF)) {
    fclose(fp);
    fp = NULL;
    return NULL;
  }

  new_object = (object *) malloc(sizeof(object));
  assert(new_object != NULL);
  new_object->num_points = object_points;
  new_object->num_polygons = number_of_polygons;
  new_object->points = malloc(sizeof(point) * object_points);
  for (int i = 0; i < object_points; i++) {
    ret = fscanf(fp, "%[^\n] ", buff);
    if (ret == EOF) {
      err_val = 1;
      break;
    }
    ret = sscanf(buff, "%d %lf %lf %lf\n", &temp_num, &x_val, &y_val, &z_val);
    if ((ret != 4) || (ret == EOF)) {
      err_val = 1;
      break;
    }
    if (i == 0) {
      poly_pt_offset = temp_num;
    }
    new_object->points[i].arr[0] = x_val;
    new_object->points[i].arr[1] = y_val;
    new_object->points[i].arr[2] = z_val;
  }
  new_object->polygons = (polygon*)malloc(sizeof(polygon) * number_of_polygons);
  for (int i = 0; i < number_of_polygons; i++) {
    ret = fscanf(fp, "%[^\n] ", buff);
    if (ret == EOF) {
      err_val = 2;
      break;
    }
    ret = sscanf(buff, "%[^#]%[^ ] %d",color, rgb, &polygon_points);
    if ((ret != 3) || (ret == EOF)) {
      err_val = 2;
      break;
    }
    name_to_rgb(rgb, &red, &green, &blue);
    new_object->polygons[i].num_points = polygon_points;
    new_object->polygons[i].r = (unsigned char)red;
    new_object->polygons[i].g = (unsigned char)green;
    new_object->polygons[i].b = (unsigned char)blue;
    point_array = (point **)malloc(sizeof(point *) * polygon_points);
    sprintf(temp_str, "%d", polygon_points);
    buff_offset = (strlen(color) + strlen(rgb) + strlen(temp_str) + 2);
    for (int j = 0; j < polygon_points; j++) {
      ret = sscanf(buff + buff_offset, "%d", &pt_val);
      if ((ret != 1) || (ret == EOF)) {
        temp_poly_alloc = i;
        err_val = 2;
        break;
      }
      if ((pt_val - poly_pt_offset < 0) || (poly_pt_offset > object_points)) {
        temp_poly_alloc = i;
        err_val = 2;
        break;
      }
      point_array[j] = &(new_object->points[(pt_val - poly_pt_offset)]);
      sprintf(temp_str, "%d", pt_val);
      buff_offset += (strlen(temp_str) + 1);
    }
    new_object->polygons[i].point_arr = point_array;
  }

  if (err_val != 0) {
    for (int i = 0; i < temp_poly_alloc; i++) {
      free(new_object->polygons[i].point_arr);
      new_object->polygons[i].point_arr = NULL;
    }
    free(new_object->polygons);
    new_object->polygons = NULL;
    free(new_object->points);
    new_object->points = NULL;
    free(new_object);
    new_object = NULL;
  }

  fclose(fp);
  fp = NULL;
  return new_object;
} /* read_object() */

/*
 * This function is used to deallocate the object anf free everything it points
 * to.
 */

void free_object(object *my_obj) {
  int num_of_polygons = 0;
  if (my_obj == NULL) {
    return;
  }
  num_of_polygons = my_obj->num_polygons;
  for (int i = 0; i < num_of_polygons; i++) {
    free(my_obj->polygons[i].point_arr);
    my_obj->polygons[i].point_arr = NULL;
    my_obj->polygons[i].num_points = 0;
    my_obj->polygons[i].r = 0;
    my_obj->polygons[i].g = 0;
    my_obj->polygons[i].b = 0;
  }

  free(my_obj->polygons);
  my_obj->polygons = NULL;

  free(my_obj->points);
  my_obj->points = NULL;
  my_obj->num_points = 0;
  my_obj->num_polygons = 0;

  free(my_obj);
  my_obj = NULL;
} /* free_object() */