
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


/*
 * This function is used to provide the address, hexadecimal representation, and
 * character representation (printable characters only) for the given file,
 * which is passed as p. The hexadciaml representation is provided using a for
 * loop over 16 bytes each. Lastly, the printable set of characters are
 * displayed using a for loop as well to iterate over the 16 bytes. The
 * addresses are provided by iterating over 16 byte intervals.
 */
void filedump(char * p , int len) {
  // Add your code here.
  // You may see p as an array.
  // p[0] will return the element 0
  // p[1] will return the element 1 and so on

  int i;

  for (i=0; i < len; i = i + 16) {
    fprintf(stdout, "0x%016lX: ", (unsigned long) (i));

    for (int j = 0; j < 16; j++) {
      if ((i + j) < len) {
        int c = p[i + j]&0xFF; // Get value at [p]. The &0xFF is to make sure you truncate to 8bits or one byte.

        // Print first byte as hexadecimal
        fprintf(stdout, "%02X ", c);
      }
      else {
        fprintf(stdout, "   ");
      }
    }
    fprintf(stdout, " ");

    // Print first byte as character. Only print characters >= 32 that are the printable characters.

    for (int k = 0; k < 16; k++) {
      if ((i + k) < len) {
        int c = p[i + k]&0xFF;
        fprintf(stdout, "%c", (c>=32 && c<127)?c:'.');
      }
    }

    fprintf(stdout, "\n");
  }
}


/*
 * The main method is used to open the file and acquire the parameters needed
 * for the filedump function.
 */
int main(int argc, char **argv) {
  char *file_name = NULL;
  char *buffer_length = NULL;
  char *buffer = NULL;
  int file_size = 0;
  FILE *fp = 0;
  int bites_read = 0;
  int temp_length = 0;
  if (argc < 2) {
    printf("File name is not passed. Please pass the filename.\n");
    return -1;
  }
  file_name = argv[1];
  if (argc == 3) {
    file_name = argv[1];
    buffer_length = argv[2];
    temp_length = atoi(buffer_length);
  }
  fp = fopen(file_name, "r");
  if (fp == NULL) {
    printf("Error opening file \"invalidfile\"\n");
    return -1;
  }
  fseek(fp, 0 , SEEK_END);
  file_size = ftell(fp);
  fseek(fp, 0, SEEK_SET);
  if ((temp_length < file_size) && (temp_length >= 0) && (argc == 3)) {
    file_size = temp_length;
  }
  buffer = malloc(file_size);
  bites_read = fread(buffer, 1, file_size, fp);
  filedump(buffer, file_size);
  return 0;
}