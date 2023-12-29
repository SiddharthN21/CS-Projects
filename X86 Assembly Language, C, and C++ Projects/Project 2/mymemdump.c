
#include <stdio.h>
#include <string.h>
#include <stdlib.h>



/*
 * This function is used to provide the memory dump of the given file. A for
 * loop is used to print the addresses over 16 byte intervals, while an inner
 * for loop is used to print the hexadecimal representations. A second inner
 * for loop is used to display the set of printable characters on the terminal.
 */
void mymemdump(FILE * fd, char * p , int len) {
    // Add your code here.
    // You may see p as an array.
    // p[0] will return the element 0
    // p[1] will return the element 1 and so on

    int i;
    //fprintf(fd, "0x%016lX: ", (unsigned long) p); // Print address of the beginning of p. You need to print it every 16 bytes

    for (i=0; i < len; i = i + 16) {
        fprintf(fd, "0x%016lX: ", (unsigned long) (p + i));

        for (int j = 0; j < 16; j++) {
          if ((i + j) < len) {
            int c = p[i + j]&0xFF; // Get value at [p]. The &0xFF is to make sure you truncate to 8bits or one byte.

            // Print first byte as hexadecimal
            fprintf(fd, "%02X ", c);
          }
          else {
            fprintf(fd, "   ");
          }
        }
        fprintf(fd, " ");

        // Print first byte as character. Only print characters >= 32 that are the printable characters.
        //fprintf(fd, "%c ", (c>=32&&c<127)?c:'.');

        for (int k = 0; k < 16; k++) {
          if ((i + k) < len) {
            int c = p[i + k]&0xFF;
            fprintf(fd, "%c", (c>=32 && c<127)?c:'.');
          }
          /*else {
            fprintf(fd, "");
          }*/
        }

        fprintf(fd,"\n");
    }
}
