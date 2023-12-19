#ifndef HW3_H
#define HW3_H

/* Constant definitions */

#define MAX_PLAYERS           (30)
#define MAX_NAME_LEN          (50)
#define MAX_UNIT_LEN          (4)
#define NUM_ATTR              (2)
#define NUM_COLS              (6)
#define IN_PER_CM             (0.39)
#define LB_PER_KG             (2.20)

/* Error codes */

#define OK                   (0)
#define FILE_READ_ERR        (-1)
#define FILE_WRITE_ERR       (-2)
#define RECORD_ERROR         (-3)
#define OUT_OF_BOUNDS        (-4)
#define NO_DATA_POINTS       (-5)
#define NO_ATTEMPTS          (-6)
#define NO_SUCH_DATA         (-7)
#define BAD_RANGE            (-8)
#define BAD_DATA             (-9)
#define BAD_PLAYER           (-10)
#define BAD_ROW              (-11)


/* Function prototypes */

int read_tables(char *);
int find_top_scorer();
int find_most_accurate_player();
int count_players_in_weight_range(int, int);
int find_most_attempts_player(char *);
float calculate_coach_grade(char *);
int calculate_all_players_grade(char *);
int write_tables(char *, int, int);

/* Global variables */

extern int g_player_count;
extern char g_player_name[MAX_PLAYERS][NUM_ATTR][MAX_NAME_LEN];
extern int g_attributes[MAX_PLAYERS][NUM_ATTR];
extern int g_shooting[MAX_PLAYERS][NUM_COLS];
extern int g_statistics[MAX_PLAYERS][NUM_COLS];

#pragma GCC poison access

#endif // HW3_H