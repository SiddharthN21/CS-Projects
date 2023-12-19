/* HW9 main function */

#include <stdio.h>
#include <stdbool.h>
#include <unistd.h>

#include "hw9.h"

void print_book(book_t *);
void print_book_list(book_t *);
void print_collection(collection_t *);
void print_collection_list(collection_t *);

/*
 * This function is used for reading input
 */

int clean_stdin() {
  while (getchar() != '\n') {
  }
  return 1;
} /* clean_stdin() */


/*
 *  This function is used to run the functions in hw9.c. User input is used
 *  to determine which function to run and what input is passed to it. main()
 *  prints information related to running the chosen function.
 */

int main() {
  char c = ' ';
  int read = 0;
  int response = 0;

  // collections to play around with
  struct collection *collection_1 = NULL;
  struct collection *collection_2 = NULL;
  int collection_num = 0;

  char coll_name[100] = "";

  char book_title[100] = "";
  char book_author[100] = "";
  int book_genre = 0;
  int book_condition = 0;

  int result = 0;



  // Disable buffering to suppress memory leaks
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);
  setvbuf(stdin, NULL, _IONBF, 0);

  /* begin the main program */
  while (true) {
  printf("\n\nTest Menu\n");
  printf("1. create_collection()\n");
  printf("2. add_book()\n");
  printf("3. add_collection()\n");
  printf("4. delete_collection()\n");
  printf("5. move_book()\n");
  printf("6. alphabetize_books()\n");
  printf("7. count_total_checkouts()\n");
  printf("8. most_common_genre()\n");
  printf("9. decommission_book()\n");
  printf("10. delete_collections()\n");
  printf("11. print collection\n");
  printf("0. Quit\n");
  printf("Enter your choice: ");

  read = scanf("%d%c", &response, &c);
  while (((read != 2) || (c != '\n')) && (clean_stdin())) {
    printf("Invalid selection.\n");
    printf("Enter your choice: ");
    read = scanf("%d%c", &response, &c);
  }
  printf("\n");

  switch (response) {
    case 1:
    /* create_collection() */

    /* get the collection name, pointer to a pointer to a list of collections */
    printf("Enter the collection name: ");
    scanf("%s", coll_name);

    printf("Enter collection list to create (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    /* run create_collection()*/
    if (collection_num == 1) {
      if (collection_1 != NULL) {
      printf("You've already allocated collelction_1.\n");
      printf("Deallocate it to allocate a new collection_1\n");
      break;
      }
      create_collection(coll_name, &collection_1);
    }
    else {
      if (collection_2 != NULL) {
      printf("WARNING: You've already allocated collelction_2.\n");
      printf("Deallocate it to allocate a new collection_2\n");
      break;
      }
      create_collection(coll_name, &collection_2);
    }

    printf("\nFinished running create_collection()\n");

    break;
    case 2:
    /* add_book() */

    /* get the pointer to a collection, title, author, genre, and condition */

    printf("Enter collection list to add to (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    printf("Enter the title: ");
    scanf("%s", book_title);
    printf("Enter the author: ");
    scanf("%s", book_author);
    printf("\nEnter the genre (as in integer): ");
    scanf("%d", &book_genre);
    printf("\nEnter the condition (as in integer): ");
    scanf("%d", &book_condition);


    /* run add_book()*/
     if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      add_book(collection_1, book_title, book_author, book_genre, book_condition);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      add_book(collection_2, book_title, book_author, book_genre, book_condition);
    }

    printf("\nFinished running add_book()\n");
    break;
    case 3:
    /* add_collection() */

    if ((collection_1 == NULL) || (collection_2 == NULL)) {
      printf("Make sure both collection_1 and collection_2 are allocated using option 1\n");
      break;
    }

    /* get the pointer to a collection */
    printf("Enter 1 to add collection_2 to collection_1 (and 2 for vice versa): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }


    /* run add_collection()*/
    if (collection_num == 1) {
      add_collection(&collection_1, collection_2);
    }
    else {
      add_collection(&collection_2, collection_1);
    }

    printf("\nFinished running add_collection()\n");
    break;

    case 4:
    /* delete_collection() */

    /* get the pointer to a collection */
    printf("Enter the collection to delete (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    /* run delete_collection()*/
    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      delete_collection(&collection_1);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      delete_collection(&collection_2);
    }
    printf("\nFinished running delete_collection()\n");
    break;

    case 5:
    /* move_book() */

    /* get pointer to a collection, book name, and collection name*/
    printf("Enter the collection (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    printf("Enter the title: ");
    scanf("%s", book_title);
    printf("Enter the collection name: ");
    scanf("%s", coll_name);

    /* run move_book() */
    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = move_book(collection_1, book_title, coll_name);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = move_book(collection_2, book_title, coll_name);
    }
    printf("\nmove_book() returned %d\n", result);
    break;

    case 6:
    /* alphabetize_books() */

    printf("Enter the collection to alphabetize (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      alphabetize_books(&(collection_1->books));
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      alphabetize_books(&(collection_2->books));
    }
    printf("\nFinished running alphabetize_books()\n");
    break;

    case 7:
    /* count_total_checkouts() */

    printf("Enter collection (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    printf("\nEnter the condition (as in integer): ");
    scanf("%d", &book_condition);

    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = count_total_checkouts(collection_1, book_condition);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = count_total_checkouts(collection_2, book_condition);
    }

    printf("\ncount_total_checkouts() returned %d\n", result);
    break;

    case 8:
    /* most_common_genre() */

    printf("Enter collection (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = most_common_genre(collection_1);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = most_common_genre(collection_2);
    }

    printf("\nmost_common_genre() returned %d\n", result);
    break;


    case 9:
    /* decommission_book() */

    printf("Enter collection (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }
    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = decommission_book(collection_1);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      result = decommission_book(collection_2);
    }

    printf("\ndecommission_book() returned %d\n", result);
    break;


    case 10:
    /* delete_collections() */

    printf("Enter collection (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      delete_collections(&collection_1);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      delete_collections(&collection_2);
    }

    printf("Finished running delete_collections()\n");
    break;

    case 11:

    printf("Enter collection (1 or 2): ");
    read = scanf("%d%c", &collection_num, &c);
    while ((((read != 2) || (c != '\n')) && (clean_stdin()))
         || ((collection_num != 1) && (collection_num != 2))) {
      printf("\nWrong input!\n");
      printf("\nSelect deck to add (1 or 2): ");
      read = scanf("%d%c", &collection_num, &c);
    }

    if (collection_num == 1) {
      if (collection_1 == NULL) {
      printf("collection_1 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      print_collection(collection_1);
    }
    else {
      if (collection_2 == NULL) {
      printf("collection_2 has not been allocated (use option 1 to allocate)\n");
      break;
      }
      print_collection(collection_2);
    }
    break;


    case 0:
    printf("\n\nGoodbye!!\n");
    printf("Deleting collection_1 and collection_2\n");
    if (collection_1 != NULL) {
      delete_collections(&collection_1);
    }
    if (collection_2 != NULL) {
      delete_collections(&collection_2);
    }
    return 0;

    default:
    printf("Invalid selection.\n");
    break;
  }
  }
  return 0;

} /* main() */

/*
 * prints an individual book
 */
void print_book(book_t *book) {
  if (book == NULL) {
    printf("\t\tNULL\n");
    return;
  }
  printf("\tBook @ %p:\n", book);
  printf("\t\tTitle: %s\n", book->title);
  printf("\t\tAuthor: %s\n", book->author);
  printf("\t\tCheckout count: %d\n", book->check_out_count);

  printf("\t\tGenre: ");
  switch (book->genre) {
  case NOVEL:
    printf("NOVEL\n");
    break;
  case NARRATIVE:
    printf("NARRATIVE\n");
    break;
  case SCI_FI:
    printf("SCI_FI\n");
    break;
  case NON_FICTION:
    printf("NON_FICTION\n");
    break;
  case ADVENTURE:
    printf("ADVENTURE\n");
    break;
  default:
    printf("-1\n");
    break;
  }

  printf("\t\tCondition: ");
  switch (book->condition) {
  case NEW:
    printf("NEW\n");
    break;
  case GOOD:
    printf("GOOD\n");
    break;
  case FAIR:
    printf("FAIR\n");
    break;
  case POOR:
    printf("POOR\n");
    break;
  default:
    printf("-1\n");
    break;
  }

  printf("\t\tPrevious book: %p\n", book->prev_book);
  printf("\t\tNext book: %p\n", book->next_book);
} /* print_book() */

/*
 * prints a list of books
 */

void print_book_list(book_t *books) {

  if (books == NULL) {
    print_book(books);
  }

  /* go to the head */

  while (books && books->prev_book) {
    books = books->prev_book;
  }
  while (books) {
    print_book(books);
    books = books->next_book;
  }
} /* print_book_list() */

/*
 * prints an individual collection
 */

void print_collection(collection_t *collection) {
  if (collection == NULL) {
    printf("\tNULL\n");
    return;
  }
  printf("Collection @ %p:\n", collection);
  printf("\tTitle: %s\n", collection->collection_name);
  printf("\tBooks count: %d\n", collection->book_count);
  printf("\tPrevious collection: %p\n", collection->prev_collection);
  printf("\tNext collection: %p\n", collection->next_collection);
  book_t *book = collection->books;
  print_book_list(book);
} /* print_collection() */

/*
 * prints a list of collections
 */
void print_collection_list(collection_t *collections) {

  if (collections == NULL) {
    printf("\tNULL\n");
    return;
  }

  collection_t *head = collections;

  /* go to the head */

  while (collections->prev_collection) {
    collections = collections->prev_collection;
  }
  while (collections) {
    if (head == collections) {
      printf("==== NODE POINTED TO ->   ");
    }
    print_collection(collections);
    collections = collections->next_collection;
  }
} /* print_collection_list() */