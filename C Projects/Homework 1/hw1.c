/* Siddharth Nadgaundi, hw1.c, CS 24000, Spring 2023
 * Last updated Jan 20, 2023
 */

/* Add any includes here */
#include "hw1.h"

#include <stdio.h>

/* Define create_recaman_sequence here.
 *
 * This function is used to create a recaman sequence array by taking the
 * starting number and number of elements in the array as the parameters.
 *
 * The function starts by checking if the number of elements is greater
 * than the maximum array size, not a positive number, or if the start
 * number is less than 0. If any of these conditions evaluate to true,
 * the function returns RECAMAN_ERROR;
 * The function then assigns the starting number to the index 0 of the
 * array. It then starts iterating from 1 till num_elements - 1. Through
 * the loop, it performs the calculations provided and assigns the value
 * to the variable current_num. In order to verify that a number is not
 * repeated, a nested for loop is used along with an if statement which
 * uses the second equation provided when evaluated to true.
 * Once the loop has iterated through all elements, the function returns
 * RECAMAN_CORRECT if the array has been successfully created.
 */

int create_recaman_sequence(int start_num, int num_elements) {
  if ((num_elements > ARRAY_SIZE) || (num_elements <= 0) || (start_num < 0)) {
    return RECAMAN_ERROR;
  }
  else {
    g_recaman_array[0] = start_num;
    for (int i = 1; i < num_elements; i++) {
      int current_num = g_recaman_array[i - 1] - i;
      for (int j = 0; j < i; j++) {
        if ((current_num <= 0) || (g_recaman_array[j] == current_num)) {
          current_num = g_recaman_array[i - 1] + i;
          break;
        }
      }
      g_recaman_array[i] = current_num;
    }
    return RECAMAN_CORRECT;
  }
} /* create_recaman_sequence() */

/* Define check_recaman_sequence here.
 * The first number in the array is always valid.
 *
 * This function is used to check if the array provided is a recaman array or
 * or not by taking the number of elements in the array as its parameter.
 *
 * The function declares the variables - check_num, incorrect_num_index,
 * arr_len, eq1, eq2, and correct_num.
 * The function first checks if num_elements is less than or equal to 0 or if
 * num_elements is greater than arr_len. If the statement evaluates to true in
 * either of the cases, the function returns RECAMAN_ERROR.
 * If not, the function uses a for loop to iterate through num_elements. It
 * assigns the current element in the array to check_num. It then checks if
 * the elements is the first one or not. If it's the first element, it uses
 * an if statement to check if the number is negative. If the number is
 * negative, it assigns the index value to incorrect_num_index and returns it.
 * If i > 0, the function function assigns the two equations to eq1 and eq2.
 * If eq1 > 0, correct_num is assigned eq1 and a for loop is used to check if
 * the value in eq1 is equal to any value in the array. If not, correct_num
 * is assigned the value of eq2. Finally, if the number to check is not equal
 * to correct_num, the incorrect index is returned.
 * If all the cases are passed, the function returns RECAMAN_CORRECT.
 */

int check_recaman_sequence(int num_elements) {
  int check_num = 0;
  int incorrect_num_index = 0;
  int eq1 = 0;
  int eq2 = 0;
  int correct_num = 0;
  int arr_len = sizeof(g_recaman_array) / sizeof(g_recaman_array[0]);
  if ((num_elements <= 0) || (num_elements > arr_len)) {
    return RECAMAN_ERROR;
  }
  else {
    for (int i = 0; i < num_elements; i++) {
      check_num = g_recaman_array[i];
      if (i > 0) {
        eq1 = g_recaman_array[i - 1] - i;
        eq2 = g_recaman_array[i - 1] + i;
        if (eq1 > 0) {
          correct_num = eq1;
          for (int j = 0; j < i; j++) {
            if (eq1 == g_recaman_array[j]) {
              correct_num = eq2;
            }
          }
        }
        else {
          correct_num = eq2;
        }
        if (check_num != correct_num) {
          incorrect_num_index = i;
          return incorrect_num_index;
        }
      }
      else if (i == 0) {
        if (check_num < 0) {
          incorrect_num_index = i;
          return incorrect_num_index;
        }
      }
    }
    return RECAMAN_CORRECT;
  }
} /* check_recaman_sequence() */

/* Remember, you don't need a main function!
 * It is provided by hw1_main.c or hw1_test.o
 */