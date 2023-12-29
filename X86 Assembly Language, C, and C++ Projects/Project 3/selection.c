
// selection.c:
#include <stdio.h>

long a[] = {6, 7, 2, 3, 1, 9, 4, 5, 0, -9, 8};
long n = (sizeof(a)/sizeof(long));

extern void selectionsort(long ascending, long n, long * a);
/*void selectionsort(long ascending, long n, long * a) {
  for (int i = 0; i < n- 1; i++) {
    long index = i;
    for (int j = i + 1; j < n; j++) {
      if (ascending) {
        if (a[j] < a[index]) {
          index = j;
        }
      }
      else {
        if (a[j] > a[index]) {
          index = j;
        }
      }
    }
    long temp = a[i];
    a[i] = a[index];
    a[index] = temp;
  }
}*/


void printArray(long n, long * a) {
   for (int i = 0; i < n; i++) {
           printf("%ld ", a[i]);
   }
   printf("\n");
}

int main(int argc, char ** argv)
{
      printf("Before Ascending:\n");printArray(n,a);
      selectionsort(1, n, a); // notice how we do not return anything here...
      printf("After Ascending:\n");printArray(n,a);

      printf("Before Descending:\n");printArray(n,a);
      selectionsort(0, n, a); // notice how we do not return anything here...
      printf("After Descending:\n");printArray(n,a);
      return 0;
}