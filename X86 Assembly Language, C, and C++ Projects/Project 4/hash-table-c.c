
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

#include "table.h"

struct Table * HashTable_C_init(long maxWords);
long HashTable_C_lookup(void * table, char * word, long * value);
long HashTable_C_update(void * table, char * word, long value);
long HashTable_C_print(void * table, long count);

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

struct Table * HashTable_C_init(long maxWords) {
    struct HashTable * hashTable = (struct HashTable *) malloc(sizeof(struct HashTable));
    hashTable->funcs.table_init = HashTable_C_init;
    hashTable->funcs.table_lookup = HashTable_C_lookup;
    hashTable->funcs.table_update = HashTable_C_update;
    hashTable->funcs.table_print = HashTable_C_print;

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

long HashTable_C_hash(void * table, char * word) {
        struct HashTable * hashTable = table;
        long hashNum = 1;
        long len = strlen(word);
        for (long i = 0; i < len; i++) {
                hashNum = 31 * hashNum + word[i];
        }
        if (hashNum < 0) hashNum = - hashNum;
        return hashNum % hashTable->nBuckets;
}

long HashTable_C_lookup(void * table, char * word, long * value) {
    struct HashTable * hashTable  = table;

    long hashNum = HashTable_C_hash(hashTable, word);
    struct HashTableElement * elem = hashTable->array[hashNum].next;

    while (elem != NULL && strcmp(elem->word,word) != 0) {
      elem = elem->next;
    }

    if ( elem == NULL) {
      //Not found
      return false;
    }

    *value = elem->value;
    return true;
}

long HashTable_C_update(void * table, char * word, long value) {
    struct HashTable * hashTable  = table;

    long hashNum = HashTable_C_hash(hashTable, word);
    struct HashTableElement * elem = &hashTable->array[hashNum];

    while (elem->next != NULL && strcmp(elem->next->word,word) != 0) {
      elem = elem->next;
    }

    if ( elem->next != NULL) {
        // found
        elem->next->value = value;
        return true;
    }

    // Not found
    struct HashTableElement * e = (struct HashTableElement *)
                        malloc(sizeof(struct HashTableElement));
    if (e == NULL) {
        perror("malloc");
        exit(1);
    }

    elem->next = e;
    e->word = strdup(word);
    e->value = value;
    e->next = NULL;

    return false;
}

long HashTable_C_print(void * table, long count) {
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