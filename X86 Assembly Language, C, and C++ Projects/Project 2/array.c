
#include <stdio.h>

#include "array.h"

// Return sum of the array
double sumArray(int n, double * array) {
  double sum = 0;

  double * p = array;
  double * pend = p+n;

  while (p < pend) {
    sum += *p;
    p++;
  }

  return sum;
}

/*
 * Return maximum element of array
 * This is done by checking the current element of the array using * against
 * the current max element over the length of the array.
 */
double maxArray(int n, double * array) {
  double max_element = 0;
  double *cur_element = array;
  for (int i = 0; i < n; i++) {
    if (*cur_element > max_element) {
      max_element = *cur_element;
    }
    cur_element++;
  }
  return max_element;
}

/*
 * Return minimum element of array
 * This is done by checking the current element of the array using * against
 * the current min element over the length of the array.
 */
double minArray(int n, double * array) {
  double min_element = *array;
  double *cur_element = array;
  for (int i = 0; i < n; i++) {
    if (*cur_element < min_element) {
      min_element = *cur_element;
    }
    cur_element++;
  }
  return min_element;
}

/*
 * Find the position int he array of the first element x
 * such that min<=x<=max or -1 if no element was found
 * This is fone by checking each element using the * operator against the max
 * and min specified over the length of the array. If found the loop is broken.
 */
int findArray(int n, double * array, double min, double max) {
  int i = 0;
  int element_found = 0;
  double *cur_element = array;
  while (i < n) {
    if((*cur_element >= min) && (*cur_element <= max)) {
      element_found = 1;
      break;
    }
    else {
      cur_element++;
      i++;
    }
  }

  if (element_found == 1) {
    return i;
  }
  else {
    return -1;
  }
}

/*
 * Sort array without using [] operator. Use pointers
 * Hint: Use a pointer to the current and another to the next element
 * This is done using the insertion sort algorithm to compare each element
 * against all of its previous elements (in the worst case) to maintain the
 * sorted part of the array over each iteration.
 */
int sortArray(int n, double * array) {
  int j = 0;
  double swap_element = 0;
  double *array_element = array;
  for (int i = 1; i < n; i++) {
    swap_element = *(array_element + i);
    j = i - 1;
    while ((j >= 0) && (*(array_element + j) > swap_element)) {
      *(array_element + j + 1) = *(array_element + j);
      *(array_element + j) = swap_element;
      j = j - 1;
    }
  }
}

/*
 * Print array
 * This is done by using a for loop to iterate over all the elements of the
 * array, which are then printed using the * operator.
 */
void printArray(int n, double * array) {
  int i = 0;
  double *cur_element = array;
  for (int j = 0; j < n; j++) {
    printf("%d:%lf\n", i, *cur_element);
    cur_element++;
    i++;
  }
}