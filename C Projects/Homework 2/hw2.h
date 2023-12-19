#ifndef HW2_H
#define HW2_H

/* Constant Definitions */

#define MAX_ID_LENGTH (5)
#define MAX_FIELD_LENGTH (50)
#define RACE_LENGTH (4000)
#define MAX_FILE_LENGTH (50)

/* Error Codes */

#define OK               (0)
#define FILE_READ_ERR   (-1)
#define FILE_WRITE_ERR  (-2)
#define BAD_RECORD      (-3)
#define NO_DATA_POINTS  (-4)
#define BAD_ID          (-5)

/* Function Prototypes */

float average_speed_of_manufacturer(char *, char *);
int expected_pitstops(char *, char *);
float find_max_total_pitstop(char *, char *);
int generate_expected_cost_report( char *, char *);

#pragma GCC poison access

#endif