/* Siddharth Nadgaundi, hw9.c, CS24000, Spring 2023
 * Last updated March 29, 2023
 */

/* Add any includes here */

#include "hw9.h"

#include <assert.h>
#include <limits.h>
#include <malloc.h>
#include <stdio.h>
#include <string.h>


/*
 * This function allocates and populates a new collection_t node.
 * The function uses malloc() to allocate the necessary memory to
 * new_collection and its attributes. The pointer pointed to by the second
 * argument then is set to point to new_collection which is the new node.
 */

void create_collection(char *c_name, collection_t **p_to_p) {
  collection_t *new_collection = NULL;

  assert(c_name != NULL);
  assert(p_to_p != NULL);
  assert(*p_to_p == NULL);

  new_collection = (collection_t *) malloc(sizeof(collection_t));
  assert(new_collection != NULL);
  new_collection->collection_name = malloc(strlen(c_name) + 1);
  assert(new_collection->collection_name != NULL);
  strcpy(new_collection->collection_name, c_name);
  new_collection->book_count = 0;
  new_collection->books = NULL;
  new_collection->prev_collection = NULL;
  new_collection->next_collection = NULL;

  *p_to_p = new_collection;
} /* create_collection() */

/*
 * This function allocates and populates a new book_t node and places it at the
 * head of the list of books.
 * The function starts by using malloc() to allocate the necessary memory to
 * new_book. It then populates it with the parameters passed to the function.
 * Finally, it manipulates the pointers of book_collection to point to new_book
 * as the head of the list.
 */

void add_book(collection_t *book_collection, char *b_title, char *b_author,
              enum genre_t b_genre, enum condition_t b_condition) {
  book_t *new_book = NULL;
  book_t *current_head = NULL;

  assert(book_collection != NULL);
  assert(b_title != NULL);
  assert(b_author != NULL);
  assert(b_genre < 5);
  assert(b_condition < 4);

  new_book = (book_t *) malloc(sizeof(book_t));
  assert(new_book != NULL);
  new_book->title = malloc(strlen(b_title) + 1);
  assert(new_book->title != NULL);
  strcpy(new_book->title, b_title);
  new_book->author = malloc(strlen(b_author) + 1);
  assert(new_book->author != NULL);
  strcpy(new_book->author, b_author);
  new_book->genre = b_genre;
  new_book->condition = b_condition;
  new_book->check_out_count = 0;
  new_book->prev_book = NULL;
  new_book->next_book = NULL;

  current_head = book_collection->books;
  new_book->next_book = current_head;
  current_head->prev_book = new_book;
  book_collection->books = new_book;
  book_collection->book_count++;
} /* add_book() */

/*
 * This functions inserts the collection pointed to by the second argument in
 * the list of collections pointed to by the first argument.
 * The function uses a while loop to iterate through the list of collections in
 * prder to find the correct spot for the new_collection. This is done by using
 * strcmp within is statements to check the position of the new club relative
 * to the current club. In case the new collection is to be inserted at the
 * tail, the pointers are manipulated outside the loop using a temp_previous
 * variable.
 */

void add_collection(collection_t **collection_list,
                    collection_t *new_collection) {
  collection_t *coll_list = NULL;
  collection_t *temp_previous = NULL;

  assert(collection_list != NULL);
  assert(new_collection != NULL);

  coll_list = *collection_list;
  while (coll_list != NULL) {
    if (strcmp(new_collection->collection_name,
               coll_list->collection_name) < 0) {
      if (coll_list->prev_collection == NULL) {
        new_collection->prev_collection = NULL;
        new_collection->next_collection = coll_list;
        coll_list->prev_collection = new_collection;
        *collection_list = new_collection;
      }
      else {
        new_collection->prev_collection = coll_list->prev_collection;
        new_collection->next_collection = coll_list;
        coll_list->prev_collection = new_collection;
        temp_previous->next_collection = new_collection;
      }
      return;
    }
    temp_previous = coll_list;
    coll_list = coll_list->next_collection;
  }
  new_collection->prev_collection = temp_previous;
  temp_previous->next_collection = new_collection;
  new_collection->next_collection = NULL;
} /* add_collection() */

/*
 * This function is used to move a book from one collection to the collection
 * specified by the third parameter.
 * The function starts by using a while loop to traverse the collection list
 * and find the head. Next, a temp variable is used in a second while loop to
 * find if the destination collection exists. A third while loop is used to
 * find if the given book exists or not by using a while loop within the third
 * one to iterate through the books of each collection. If/else statement are
 * then used to identify the position of transfer_book within the list in order
 * to manipulate the pointers accordingly. Lastly, the pointers of the
 * destination collection are manipulated in order for the collection's books
 * pointer to point to transfer_book as the head.
 */

int move_book(collection_t *book_collection, char *book_name,
              char *dest_collection_name) {
  collection_t *dest_collection = NULL;
  collection_t *head_collection = NULL;
  collection_t *source_collection = NULL;
  collection_t *temp_head = NULL;
  book_t *search_book = NULL;
  book_t *transfer_book = NULL;

  assert(book_collection != NULL);
  assert(book_name != NULL);
  assert(dest_collection_name != NULL);

  head_collection = book_collection;
  while (head_collection->prev_collection != NULL) {
    head_collection = head_collection->prev_collection;
  }
  temp_head = head_collection;

  while (temp_head != NULL) {
    if (strcmp(temp_head->collection_name, dest_collection_name) == 0) {
      dest_collection = temp_head;
    }
    temp_head = temp_head->next_collection;
  }
  if (dest_collection == NULL) {
    return NO_SUCH_COLLECTION;
  }

  temp_head = head_collection;
  while (temp_head != NULL) {
    search_book = temp_head->books;
    while (search_book != NULL) {
      if (strcmp(search_book->title, book_name) == 0) {
        source_collection = temp_head;
        transfer_book = search_book;
      }
      search_book = search_book->next_book;
    }
    temp_head = temp_head->next_collection;
  }
  if (transfer_book == NULL) {
    return NO_SUCH_BOOK;
  }

  if ((transfer_book->prev_book == NULL) &&
  (transfer_book->next_book == NULL)) {
    source_collection->books = NULL;
    source_collection->book_count = 0;
  }
  else if ((transfer_book->prev_book == NULL) &&
  (transfer_book->next_book != NULL)) {
    source_collection->books = transfer_book->next_book;
    transfer_book->next_book->prev_book = NULL;
    transfer_book->next_book = NULL;
    source_collection->book_count--;
  }
  else if ((transfer_book->prev_book != NULL) &&
  (transfer_book->next_book != NULL)) {
    transfer_book->next_book->prev_book = transfer_book->prev_book;
    transfer_book->prev_book->next_book = transfer_book->next_book;
    transfer_book->prev_book = NULL;
    transfer_book->next_book = NULL;
    source_collection->book_count--;
  }
  else if ((transfer_book->prev_book != NULL) &&
  (transfer_book->next_book == NULL)) {
    transfer_book->prev_book->next_book = NULL;
    transfer_book->prev_book = NULL;
    source_collection->book_count--;
  }

  transfer_book->next_book = dest_collection->books;
  if (dest_collection->books != NULL) {
    dest_collection->books->prev_book = transfer_book;
  }
  dest_collection->books = transfer_book;
  dest_collection->book_count++;

  return OK;
} /* move_book() */

/*
 * This function reorders the given list of books in lexicographic order.
 * The function starts by using a while loop to find the head book. A temp
 * variable is then used to find the number of books in the list. Next, the
 * case where only 1 book is present in the list is checked and the paramter is
 * set to that book if the statement is true. If not, a double for loop is used
 * to iterate through all the books and sort them one by one. This is based on
 * the bubble sort algorithm. An if statement is used to compare the name of the
 * book with the next book's name. Subsequent if/else statements are used to
 * identify the position of the current book in case a swap needs to occur. In
 * case a book needs to be swapped, the pointer is not updated to the next book
 * as swapping will move the book to the next position. The head_book is updated
 * when the head is swapped and assigned to the parameter at the end of the
 * function.
 */

void alphabetize_books(book_t **book_ptr) {
  book_t *head_book = NULL;
  book_t *temp_head = NULL;
  book_t *current_book = NULL;
  book_t *book_next = NULL;
  int book_count = 0;

  assert(book_ptr != NULL);

  head_book = *book_ptr;
  while (head_book->prev_book != NULL) {
    head_book = head_book->prev_book;
  }
  temp_head = head_book;
  while (temp_head != NULL) {
    book_count++;
    temp_head = temp_head->next_book;
  }

  current_book = head_book;
  if ((current_book->prev_book == NULL) && (current_book->next_book == NULL)) {
    *book_ptr = current_book;
    return;
  }

  for (int i = 0; i <= book_count - 1; i++) {
    if (i != 0) {
      current_book = head_book;
    }
    for (int j = 0; j <= book_count - i - 2; j++) {
      if (strcmp(current_book->title, current_book->next_book->title) > 0) {
        if ((current_book->prev_book == NULL) &&
        (current_book->next_book != NULL)) {
          book_next = current_book->next_book;
          if (book_next->next_book != NULL) {
            book_next->next_book->prev_book = current_book;
          }
          book_next->prev_book = NULL;
          current_book->prev_book = book_next;
          current_book->next_book = book_next->next_book;
          book_next->next_book = current_book;
          head_book = book_next;
        }
        else if ((current_book->prev_book != NULL) &&
        (current_book->next_book != NULL)) {
          book_next = current_book->next_book;
          if (book_next->next_book != NULL) {
            book_next->next_book->prev_book = current_book;
          }
          if (current_book->prev_book != NULL) {
            current_book->prev_book->next_book = book_next;
          }
          current_book->next_book = book_next->next_book;
          book_next->next_book = current_book;
          book_next->prev_book = current_book->prev_book;
          current_book->prev_book = book_next;
        }
      }
      else {
        current_book = current_book->next_book;
      }
    }
  }

  *book_ptr = head_book;
} /* alphabetize_books() */

/*
 * This function calculates the total number of times a book with the specified
 * condition has been checked out within the passed list.
 * A while loop is used to iterate through the book_collection. An if statement
 * is used to check if the current book's condition matches with the one
 * specified by the parameter. In case there is a match, the count is updated.
 * The final count is returned after the while loop terminates.
 */

int count_total_checkouts(collection_t *book_collection,
                          enum condition_t book_condition) {
  book_t *book_list = NULL;
  int checkout_count = 0;

  assert(book_collection != NULL);
  assert(book_condition < 4);

  if (book_collection->book_count == 0) {
    return 0;
  }

  book_list = book_collection->books;
  while (book_list != NULL) {
    if (book_list->condition == book_condition) {
      checkout_count = checkout_count + book_list->check_out_count;
    }
    book_list = book_list->next_book;
  }

  return checkout_count;
} /* count_total_checkouts() */

/*
 * This function is used to find the most common genre in the given list and
 * return its corresponding enum value.
 * A while loop is used to iterate over book_collection. Within the loop, if
 * statements are used to increment the count of the current book's genre.
 * After the loop has terminated, if statement are used to compare each count
 * against the other and return the corresponding enum whenever a statement
 * evaluates to true.
 */

enum genre_t most_common_genre(collection_t *book_collection) {
  book_t *book_list = NULL;
  int novel_count = 0;
  int narrative_count = 0;
  int sci_fi_count = 0;
  int non_fiction_count = 0;
  int adventure_count = 0;

  assert(book_collection != NULL);

  if (book_collection->book_count == 0) {
    return NO_SUCH_BOOK;
  }

  book_list = book_collection->books;
  while (book_list != NULL) {
    if (book_list->genre == 0) {
      novel_count++;
    }
    else if (book_list->genre == 1) {
      narrative_count++;
    }
    else if (book_list->genre == 2) {
      sci_fi_count++;
    }
    else if (book_list->genre == 3) {
      non_fiction_count++;
    }
    else if (book_list->genre == 4) {
      adventure_count++;
    }
    book_list = book_list->next_book;
  }
  if ((novel_count >= narrative_count) && (novel_count >= sci_fi_count) &&
  (novel_count >= non_fiction_count) && (novel_count >= adventure_count)) {
    return 0;
  }
  else if ((narrative_count >= novel_count) && (narrative_count >= sci_fi_count)
  && (narrative_count >= non_fiction_count) &&
  (narrative_count >= adventure_count)) {
    return 1;
  }
  else if ((sci_fi_count >= narrative_count) && (sci_fi_count >= novel_count)
  && (sci_fi_count >= non_fiction_count) &&
  (sci_fi_count >= adventure_count)) {
    return 2;
  }
  else if ((non_fiction_count >= narrative_count) &&
  (non_fiction_count >= sci_fi_count) && (non_fiction_count >= novel_count) &&
  (non_fiction_count >= adventure_count)) {
    return 3;
  }
  else {
    return 4;
  }
} /* most_common_genre() */

/*
 * This function traverses the list and removes the least checked out book from
 * it.
 * The function starts by using a while loop to iterate through the loop to
 * find the least checked out book. When found the pointer is populated by the
 * book's corresponding values. If statements are then used to identify the
 * position of the found book in the list. This is followed by the manipulation
 * of pointers within each of the statements. Lastly, the book is deallocated
 * using free() and everything deallocated is set to NULL.
 */

int decommission_book(collection_t *book_collection) {
  book_t *book_list = NULL;
  book_t *book_found = NULL;
  book_t *previous_book = NULL;
  int least_checkout_num = INT_MAX;
  int checkout_num = 0;

  assert(book_collection != NULL);

  if (book_collection->book_count == 0) {
    return NO_SUCH_BOOK;
  }

  book_list = book_collection->books;
  while (book_list != NULL) {
    if (book_list->check_out_count < least_checkout_num) {
      least_checkout_num = book_list->check_out_count;
      book_found = book_list;
      book_found->title = book_list->title;
      book_found->author = book_list->author;
    }
    book_list = book_list->next_book;
  }
  previous_book = book_found->prev_book;
  if ((book_found->next_book == NULL) && (book_found->prev_book == NULL)) {
    book_collection->books = NULL;
    checkout_num = book_found->check_out_count;
  }
  else if ((book_found->prev_book == NULL) && (book_found->next_book != NULL)) {
    book_collection->books = book_found->next_book;
    book_found->next_book->prev_book = NULL;
    checkout_num = book_found->check_out_count;
  }
  else if ((book_found->next_book == NULL) && (book_found->prev_book != NULL)) {
    previous_book->next_book = NULL;
    checkout_num = book_found->check_out_count;
  }
  else {
    previous_book->next_book = book_found->next_book;
    book_found->next_book->prev_book = previous_book;
    checkout_num = book_found->check_out_count;
  }
  book_collection->book_count--;
  free(book_found->title);
  book_found->title = NULL;
  free(book_found->author);
  book_found->author = NULL;
  free(book_found);
  book_found = NULL;

  return checkout_num;
} /* decommission_book() */

/*
 * This function deallocates the entire list of clubs and sets the pointer
 * pointed to by the first argument to NULL.
 * The function starts by setting cur_collection to collections and its previous
 * collection to previous_collection. Two while loops are used then iterate over
 * the collections coming after the current collection and before the current
 * collection. Within each loop, the current collection is completely
 * deallocated using the delete_collection function. Lastly, the pointer pointed
 * to by the first argument is set to NULL.
 */

void delete_collections(collection_t **collections) {
  collection_t *cur_collection = NULL;
  collection_t *previous_collection = NULL;
  collection_t *del_collection = NULL;

  assert(collections != NULL);
  assert(*collections != NULL);

  cur_collection = *collections;
  previous_collection = cur_collection->prev_collection;

  while (cur_collection != NULL) {
    del_collection = cur_collection;
    cur_collection = cur_collection->next_collection;
    delete_collection(&del_collection);
  }

  while (previous_collection != NULL) {
    del_collection = previous_collection;
    previous_collection = previous_collection->prev_collection;
    delete_collection(&del_collection);
  }

  del_collection = NULL;
  cur_collection = NULL;
  previous_collection = NULL;
  *collections = NULL;
} /* delete_collections() */

/*
 * This function will deallocate a single-doubly linked node and remove it from
 * the list if it exists.
 * The function starts by assigning the head book of the collection to book_head
 * and then iterating through all the books of the collection. Within each
 * iteration all of the book's memory/data is deallocated. Next, if statements
 * are used to identify the position of the collection within the list. Based
 * on the collection's position, the corresponding pointers are manipulated to
 * represent the new list without the collection. Next, a while loop is used to
 * get the head of the new list. This is followed by deallocating all the memory
 * of the collection to be deleted. Lastly, the head of the list is set to the
 * parameter.
 */

void delete_collection(collection_t** coll_pointer) {
  collection_t *del_collection = NULL;
  collection_t *coll_head= NULL;
  book_t *book_head = NULL;
  book_t *delete_book = NULL;

  assert(coll_pointer != NULL);
  assert(*coll_pointer != NULL);

  del_collection = *coll_pointer;

  book_head = del_collection->books;
  while (book_head != NULL) {
    delete_book = book_head;
    book_head = book_head->next_book;
    if (book_head != NULL) {
      book_head->prev_book = NULL;
    }
    free(delete_book->title);
    delete_book->title = NULL;
    free(delete_book->author);
    delete_book->author = NULL;
    free(delete_book);
    delete_book = NULL;
  }
  if ((del_collection->prev_collection == NULL) &&
  (del_collection->next_collection != NULL)) {
    del_collection->next_collection->prev_collection = NULL;
    coll_head = del_collection->next_collection;
  }
  else if ((del_collection->next_collection == NULL) &&
  (del_collection->prev_collection != NULL)) {
    del_collection->prev_collection->next_collection = NULL;
    coll_head = del_collection->prev_collection;
  }
  else if ((del_collection->next_collection != NULL) &&
  (del_collection->prev_collection != NULL)) {
    del_collection->prev_collection->next_collection =
    del_collection->next_collection;
    del_collection->next_collection->prev_collection =
    del_collection->prev_collection;
    coll_head = del_collection->prev_collection;
  }
  else if ((del_collection->prev_collection == NULL) &&
  (del_collection->next_collection == NULL)) {
    coll_head = NULL;
  }
  if (coll_head != NULL) {
    while (coll_head->prev_collection != NULL) {
      coll_head = coll_head->prev_collection;
    }
  }
  free(del_collection->collection_name);
  del_collection->collection_name = NULL;
  del_collection->books = NULL;
  del_collection->book_count = 0;
  free(del_collection);
  del_collection = NULL;

  *coll_pointer = NULL;
  *coll_pointer = coll_head;
} /* delete_collection() */
