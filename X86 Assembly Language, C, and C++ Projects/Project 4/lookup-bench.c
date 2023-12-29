
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

#include "table.h"

// Add here other type of tables
struct Table * ArrayTable_init(long maxWords);
struct Table * HashTable_C_init(long maxWords);
struct Table * HashTable_ASM_init(long maxWords);
struct Table * HashTable_ASM_OPT_init(long maxWords);
long HashTable_C_print(void * table, long count);
long HashTable_C_hash(void * table, char * word);
long HashTable_C_lookup(void * table, char * word, long * value);

void
createWordHistogram(struct Table * table, char * fileName) {

    // Read file in one memory buffer
    FILE * fin = fopen(fileName, "r");
    if ( fin == NULL ) {
        fprintf(stderr, "Cannot open file: \"%s\"\n", fileName);
        perror("fopen");
        exit(1);
    }

    // Compute size of file
    struct stat st;
    fstat(fileno(fin), &st);
    long fileSize = st.st_size;
    //printf("The file length is %ld\n", fileSize);

    // Malloc the length of the file and read it
    char * fileMem = (char*) malloc(fileSize);
    if ( fileMem == NULL ) {
        perror("malloc");
        exit(1);
    }

    int n = fread(fileMem, fileSize, 1, fin);

    // Extract words
    long MAXWORD = 100;
    char word[MAXWORD+1];
    char * q = fileMem;
    char * qmax = q + fileSize;
    long wordCount = 0;

    // Iterate over all words
    while ( q < qmax ) {
        //putchar(*q);

        // Find next word. A word is a sequence of letters
        while ( q < qmax && !(*q >= 'A' && *q <= 'Z' || *q >= 'a' &&  *q <= 'z' ) ) {
            q++;
        }

        if ( q>= qmax) {
            break;
        }

        // Copy word
        long wordLen = 0;
        while ( q < qmax &&
                (*q >= 'A' && *q <= 'Z' || *q >= 'a' &&  *q <= 'z' ) ) {
            if ( wordLen < MAXWORD) {
                // Limit word to MAXWORD
                // Convert to lower case
                //word[ wordLen ] = (0b00100000 | (*q));
                word[ wordLen ] = *q;
                wordLen++;
            }
            q++;
        }
        word[ wordLen ] = '\0';

        if ( wordLen > 0 ) {
            //printf("%ld: %s\n", wordCount, word);
            wordCount++;

            long ret1 = HashTable_C_print(table, wordCount);

            long c_hash = HashTable_C_hash(table, word);


            long value;
            bool c_found = HashTable_C_lookup(table, word, &value);
            bool found = table->funcs.table_lookup(table, word, &value);

            if (found) {
                // Word exists. Update value
                table->funcs.table_update(table, word, value+1);
            }
            else {
                // Word does not exist, add it and set to 1
                table->funcs.table_update(table, word, 1);
            }
        }

        if ( q>= qmax) {
            break;
        }

        q++;
    }

    fclose(fin);

}

void
printUsage() {
    printf("Usage: lookup-bench <array|hash-c|hash-asm|hash-asm-opt> <test1|test2|test3|test4|bench>\n");
}

struct Table *
allocateTable(char * tableType, long maxWords ) {

    struct Table * table = NULL;

    if (!strcmp(tableType,"array")) {
        table = ArrayTable_init(maxWords);
    }
    else if (!strcmp(tableType,"hash-c")) {
        table = HashTable_C_init(maxWords);
    }
    else if (!strcmp(tableType,"hash-asm")) {
        table = HashTable_ASM_init(maxWords);
    }
    else if (!strcmp(tableType,"hash-asm-opt")) {
        table = HashTable_ASM_OPT_init(maxWords);
    }
    else {
       printUsage();
       exit(1);
    }

    return table;
}

int
main( int argc, char ** argv)
{
    if ( argc < 3) {
       printUsage();
       exit(1);
    }

    char * tableType = argv[1];
    char * testNum = argv[2];

    struct Table * table = NULL;
    long maxWords = 1000*1000;

    table = allocateTable( tableType, maxWords );

    if ( !strcmp(testNum,"test1")) {
        createWordHistogram(table,"simple.txt");
        table->funcs.table_print(table, 0);
    }
    else if ( !strcmp(testNum,"test2")) {
        createWordHistogram(table,"computers.txt");
        table->funcs.table_print(table, 0);
    }
    else if ( !strcmp(testNum,"test3")) {
        char * hfile = "/homes/cs250/Fall2023/project4-hashtable-x86/HuckleberryFinn.txt";
        createWordHistogram(table,hfile);
        table->funcs.table_print(table, 0);
    }
    else if ( !strcmp(testNum,"test4")) {
        char * hfile = "/homes/cs250/Fall2023/project4-hashtable-x86/wikipedia.txt";
        createWordHistogram(table,hfile);
        table->funcs.table_print(table, 0);
    }
    else if ( !strcmp(testNum,"bench")) {
        for (int i = 0; i < 5; i++) {
                char * hfile = "/homes/cs250/Fall2023/project4-hashtable-x86/wikipedia.txt";
                createWordHistogram(table,hfile);
        }
        //table->funcs.table_print(table, 0);
    }
    else {
       printUsage();
       exit(1);
    }
}