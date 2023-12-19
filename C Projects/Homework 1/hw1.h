#ifndef HW1_H
#define HW1_H

#define ARRAY_SIZE (40)
extern int g_recaman_array[ARRAY_SIZE];

#define RECAMAN_CORRECT  (-1)
#define RECAMAN_ERROR    (-2)

int create_recaman_sequence(int, int);
int check_recaman_sequence(int);

#endif // HW1_H