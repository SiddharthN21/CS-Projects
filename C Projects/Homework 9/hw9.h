#ifndef HW9_H
#define HW9_H

/* Error codes */

#define OK                   (0)
#define NO_SUCH_BOOK        (-1)
#define NO_SUCH_COLLECTION  (-2)


enum genre_t {
  NOVEL,
  NARRATIVE,
  SCI_FI,
  NON_FICTION,
  ADVENTURE
};

enum condition_t {
  NEW,
  GOOD,
  FAIR,
  POOR
};

/* Structure Definitions */

typedef struct book {
  char *title;
  char *author;
  enum genre_t genre;
  enum condition_t condition;
  int check_out_count;
  struct book *prev_book;
  struct book *next_book;
} book_t;

typedef struct collection {
  char *collection_name;
  int book_count;
  struct book *books;
  struct collection *prev_collection;
  struct collection *next_collection;
} collection_t;

/* Function prototypes */

void create_collection(char *, collection_t **);
void add_book(collection_t *, char *, char *, enum genre_t, enum condition_t);
void add_collection(collection_t **, collection_t *);
void delete_collection(collection_t **);
int move_book(collection_t *, char *, char *);
void alphabetize_books(book_t **);
int count_total_checkouts(collection_t *, enum condition_t);
int decommission_book(collection_t *);
enum genre_t most_common_genre(collection_t *);
void delete_collections(collection_t **);


#endif // HW9_H