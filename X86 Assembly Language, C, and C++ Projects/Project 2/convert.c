
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>

#define MAXBASE 35
#define MAXDIGITS 100

int
main(int argc, char **argv)
{
        int baseFrom;
        int baseTo;
        char * number;
        char numberInBase[MAXDIGITS+1];
        char numberInBaseReversed[MAXDIGITS+1];

        if (argc < 4) {
                printf("Usage:  convert <basefrom> <baseto> <number>\n");
                exit(1);
        }

        int n = sscanf(argv[1], "%d", &baseFrom);
        if (n != 1) {
                printf("Bad <basefrom>: %s\n", argv[1]);
                exit(1);
        }

        n = sscanf(argv[2], "%d", &baseTo);
        if (n != 1) {
                printf("Bad <baseto>: %s\n", argv[2]);
                exit(1);
        }

        number = argv[3];

        printf("Number read in base %d: %s\n", baseFrom, number);

        exit(0);
}