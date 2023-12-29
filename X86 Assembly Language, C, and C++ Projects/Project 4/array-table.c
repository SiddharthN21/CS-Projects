
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

#include "table.h"

struct Table * ArrayTable_init(long maxWords);
long ArrayTable_lookup(void * table, char * word, long * value);
long ArrayTable_update(void * table, char * word, long value);
long ArrayTable_print(void * table, long count);

struct ArrayTableElement {
    char * word;
    long value;
};

struct ArrayTable {
    struct WordTableFuncs funcs;

    long maxWords;
    long nWords;
    struct ArrayTableElement * array;
};

struct Table * ArrayTable_init(long maxWords) {
    struct ArrayTable * arrayTable = (struct ArrayTable *) malloc(sizeof(struct ArrayTable));
    arrayTable->funcs.table_init = ArrayTable_init;
    arrayTable->funcs.table_lookup = ArrayTable_lookup;
    arrayTable->funcs.table_update = ArrayTable_update;
    arrayTable->funcs.table_print = ArrayTable_print;

    arrayTable->maxWords = maxWords;
    arrayTable->nWords = 0;
    arrayTable->array = (struct ArrayTableElement *)
        malloc(maxWords * sizeof(struct ArrayTableElement));

    if (arrayTable->array == NULL) {
       perror("malloc");
       exit(1);
    }
    return (void*) arrayTable;
}

long ArrayTable_lookup(void * table, char * word, long * value) {
    struct ArrayTable * arrayTable  = table;
    for ( long i = 0; i < arrayTable->nWords; i++) {
        struct ArrayTableElement * elem = &arrayTable->array[i];
        if ( !strcmp(elem->word,word)) {
            // Found entry
            *value = elem->value;
            return true;
        }
    }
    // Did not find entry
    return false;
}

long ArrayTable_update(void * table, char * word, long value) {
    struct ArrayTable * arrayTable  = table;

    for ( long i = 0; i < arrayTable->nWords; i++) {
        struct ArrayTableElement * elem = &arrayTable->array[i];
        if ( !strcmp(elem->word,word)) {
            // Found entry. Update value
            elem->value = value;
            return true;
        }
    }

    struct ArrayTableElement * elem = &arrayTable->array[arrayTable->nWords];
    arrayTable->nWords++;
    elem->word = strdup(word);
    elem->value = value;

    // Did not find entry
    return 0;
}

long ArrayTable_print(void * table, long count) {
    struct ArrayTable * arrayTable  = table;
    if (count == 0) {
        count = arrayTable->nWords;
    }
    for ( long i = 0; i < count; i++) {
        struct ArrayTableElement * elem = &arrayTable->array[i];
        printf("%-40s %ld\n", elem->word, elem->value);
    }
}