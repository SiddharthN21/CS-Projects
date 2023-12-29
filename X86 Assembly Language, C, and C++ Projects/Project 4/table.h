
#ifndef TABLE_H
#define TABLE_H

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

struct Table;

typedef struct Table * (*Func_table_init) (long maxWords);
typedef long (*Func_table_lookup)(void * table, char * word, long * value);
typedef long (*Func_table_update)(void * table, char * word, long value);
typedef long (*Func_table_print)(void * table, long count);

struct WordTableFuncs {
    Func_table_init table_init;
    Func_table_lookup table_lookup;
    Func_table_update table_update;
    Func_table_print table_print;
};

struct Table {
   struct WordTableFuncs funcs;
};

#endif