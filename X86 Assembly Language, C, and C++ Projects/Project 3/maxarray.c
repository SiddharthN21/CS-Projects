#include <stdio.h>

// Finds the max value in an array
/*long maxarray(long n, long *a) {
long i=0;
long max = a[0];
while (i<n) {
if (max < a[i]) {
max = a[i];
}
i++ ;
}
return max;
}*/

long a[] = {4,6,3,7,9};
long maxarray(long n, long *a);
int main() {
  printf("maxarray(5,a)=%ld\n", maxarray(5,a));
  return 0;
}