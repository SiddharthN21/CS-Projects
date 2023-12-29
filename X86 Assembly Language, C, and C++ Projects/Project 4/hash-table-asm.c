
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

#include "table.h"

struct Table * HashTable_ASM_init(long maxWords);
long HashTable_ASM_lookup(void * table, char * word, long * value);
long HashTable_ASM_update(void * table, char * word, long value);
long HashTable_ASM_print(void * table, long count);


struct HashTableElement {
    char * word;
    long value;
    struct HashTableElement * next;
};

struct HashTable {
    struct WordTableFuncs funcs;

    long maxWords;
    long nWords;
    long nBuckets;
    struct HashTableElement * array;
};

struct Table * HashTable_ASM_init(long maxWords) {
    struct HashTable * hashTable = (struct HashTable *) malloc(sizeof(struct HashTable));
    hashTable->funcs.table_init = HashTable_ASM_init;
    hashTable->funcs.table_lookup = HashTable_ASM_lookup;
    hashTable->funcs.table_update = HashTable_ASM_update;
    hashTable->funcs.table_print = HashTable_ASM_print;

    long primeNumbers[] = { 67, 131, 257, 521, 1031, 2053, 4099, 8209, 16411, 32771, 65537, 131073 };
    long nPrimeNumbers = sizeof(primeNumbers)/sizeof(long);

    hashTable->nBuckets = primeNumbers[nPrimeNumbers-1];
    for (int i = 0; i < nPrimeNumbers; i++) {
       if ( maxWords < primeNumbers[i]) {
            hashTable->nBuckets =  primeNumbers[i];
       }
    }

    hashTable->maxWords = maxWords;
    hashTable->nWords = 0;
    hashTable->array = (struct HashTableElement *)
        malloc(hashTable->nBuckets * sizeof(struct HashTableElement));
    if (hashTable->array == NULL) {
       perror("malloc");
       exit(1);
    }

    for (int i = 0; i < hashTable->nBuckets; i++) {
        hashTable->array[i].next = NULL;
    }

    return (void*) hashTable;
}


// Defined in hash-table-asm.s
long HashTable_ASM_hash(void * table, char * word);

// Defined in hash-table-asm.s
long HashTable_ASM_lookup(void * table, char * word, long * value);

// Defined in hash-table-asm.s
long HashTable_ASM_update(void * table, char * word, long value);

long HashTable_ASM_print(void * table, long count) {
    struct HashTable * hashTable  = table;
    if (count == 0) {
        count = hashTable->nWords;
    }
    long j = 0;
    for ( long i = 0; i < hashTable->nBuckets; i++) {
        struct HashTableElement * elem = hashTable->array[i].next;

        while (elem != NULL) {
                printf("%-40s %ld\n", elem->word, elem->value);
                elem = elem->next;
                j++;
        }
    }
}

/*
long HashTable_ASM_myprint(void * table, long hashValue) {
    struct HashTable * hashTable  = table;

    struct HashTableElement * elem = hashTable->array[hashValue].next;

                printf("%-40s %ld\n", elem->word, elem->value);
}
*/