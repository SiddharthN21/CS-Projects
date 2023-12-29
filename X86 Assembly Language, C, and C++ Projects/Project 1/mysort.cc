#include "mysort.h"
#include <alloca.h>
#include <assert.h>
#include <string.h>

/*
 * This function is used to sort an array of type void based on grade or name
 * which is given by the compFunc parameter. Two loops are used to iterate over
 * all the elements. The current and next elements are obtained by moving the
 * pointers by the current elementSize and casting it to char *. It is then
 * passed to compFunc to get the result. An if statement is used to check
 * whether the elements have to be swapped in ascending or descending order,
 * followed by using memcpy to swap the elements.
 */

//
// Sort an array of element of any type
// it uses "compFunc" to sort the elements.
// The elements are sorted such as:
//
// if ascending != 0
//   compFunc( array[ i ], array[ i+1 ] ) <= 0
// else
//   compFunc( array[ i ], array[ i+1 ] ) >= 0
//
// See test_sort to see how to use mysort.
//
void mysort( int n,                      // Number of elements
             int elementSize,            // Size of each element
             void * array,               // Pointer to an array
             int ascending,              // 0 -> descending; 1 -> ascending
             CompareFunction compFunc )  // Comparison function.
{
  // Add your code here. Use any sorting algorithm you want
  void *current = nullptr;
  void *next = nullptr;
  int comp_result = 0;
  char temp[elementSize];
  for (int  i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
      current = ((char *) array + j * elementSize);
      next = ((char *) array + (j + 1) * elementSize);
      comp_result = compFunc(current, next);
      if (((ascending) && (comp_result > 0)) || ((!ascending) && (comp_result < 0))) {
        memcpy(temp, current, elementSize);
        memcpy(current, next, elementSize);
        memcpy(next, temp, elementSize);
      }
    }
  }
}