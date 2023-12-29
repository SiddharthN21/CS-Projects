
#include <stdio.h>

int main() {
    long n = 0;
    long num = 0;
    long sum = 0;
    long average = 0;

    printf("n?");
    // Read the number of elements
    scanf("%ld", &n);

    // Read and sum the numbers
    for (int i = 0; i < n; i++) {
        scanf("%ld", &num);
        sum += num;
    }

    // Calculate and print the average
    average = sum / n;
    printf("SUM=%ld\n", sum);
    printf("AVG=%ld\n", average);

    return 0;
}
