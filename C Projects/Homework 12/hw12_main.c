/* HW12 main function
 * Spring 2023, CS2400
 */

#include "hw12.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define QUIT (0)
#define APP_TREE_MANIPULATIONS (1)
#define PHONE_TREE_MANIPULATIONS (2)
#define CREATE_NODE (1)
#define INSERT_NODE (2)
#define FIND_NODES (3)
#define REMOVE_NODE (4)
#define DELETE_NODE (5)
#define DELETE_TREE (6)
#define PRINT_TREE (7)
#define MAX_STRING_LENGTH (100)

/*
 *  This function is used to run the functions in hw12.c. User input is used
 *  to determine which function to run and what is passed to it. main()
 *  prints information related to running the chosen function.
 */

int main(int argc, char *argv[]) {
  node_t **array_of_duplicates = NULL;
  node_t *root_node = NULL;
  node_t *node = NULL;
  void *data_node = NULL;

  while (1) {
    int data_type = 0;
    int user_choice = 0;
    while (!((data_type == PHONE_TREE_MANIPULATIONS) ||
           (data_type == APP_TREE_MANIPULATIONS))) {
      printf("\n\n"
          "0. Quit\n"
          "1. App Tree manipulations\n"
          "2. Phone Tree manipulations\n");

      user_choice = 0;
      printf("Enter user_choice: ");
      scanf("%d", &user_choice);
      getchar();

      if ((user_choice < QUIT) || (user_choice > PHONE_TREE_MANIPULATIONS)) {
        printf("Invalid response!\n");
        continue;
      }

      if (user_choice == QUIT) {
        printf("\nHave a good day!\n\n");
        return 0;
      }
      else if (user_choice == APP_TREE_MANIPULATIONS) {
        data_type = APP_TREE_MANIPULATIONS;
      }
      else {
        data_type = PHONE_TREE_MANIPULATIONS;
      }
    }

    int exit = 0;
    while (!exit) {
      char name[MAX_STRING_LENGTH] = "\0";
      char model[MAX_STRING_LENGTH] = "\0";
      int size = 0;
      float app_price = 0.0;
      float rating = 0.0;
      int storage = 0;
      int release_year = 0;
      int phone_price = 0;
      float battery_life = 0.0;

      user_choice = 0;
      printf("0. Quit\n"
          "1. create_node\n"
          "2. insert_node\n"
          "3. find_nodes\n"
          "4. remove_node\n"
          "5. delete_node\n"
          "6. delete_tree\n"
          "7. print_tree\n");

      printf("Enter user_choice: ");
      scanf("%d", &user_choice);
      getchar();

      if ((user_choice < QUIT) || (user_choice > PRINT_TREE)) {
        printf("Invalid response!\n");
        continue;
      }

      if (user_choice == QUIT) {
        printf("Cleaning up!\n");
        if (root_node) {
          delete_tree(&root_node);
        }
        if (node) {
          delete_node(&node);
        }
        exit = 1;
      }

      if ((user_choice == CREATE_NODE) && (!node)) {

        if (data_type == APP_TREE_MANIPULATIONS) {
          printf("Name (no spaces): ");
          scanf("%s", name);
          getchar();
          printf("Size : ");
          scanf("%d", &(size) );
          getchar();
          printf("Price : ");
          scanf("%f", &(app_price) );
          getchar();
          printf("Rating : ");
          scanf("%f", &(rating));
        }
        else {
          printf("Model (no spaces): ");
          scanf("%s", model);
          getchar();
          printf("storage : ");
          scanf("%d", &(storage));
          getchar();
          printf("Release year : ");
          scanf("%d", &(release_year));
          getchar();
          printf("Price : ");
          scanf("%d", &(phone_price));
          getchar();
          printf("Battery life : ");
          scanf("%f", &(battery_life));
          getchar();
        }
      }

      if (((user_choice == FIND_NODES) || (user_choice == REMOVE_NODE))
          && (!node)) {
        if (data_type == APP_TREE_MANIPULATIONS) {
          printf("Rating: ");
          scanf("%f", &rating );
          getchar();
          printf("Size: ");
          scanf("%d", &size );
          getchar();
          printf("Price: ");
          scanf("%f", &app_price );
          getchar();
        }
        else {
          printf("storage: ");
          scanf("%d", &storage);
          getchar();
          printf("Battery life: ");
          scanf("%f", &battery_life);
          getchar();
          printf("Price: ");
          scanf("%d", &phone_price);
          getchar();
          printf("Release year: ");
          scanf("%d", &release_year);
          getchar();
        }
      }

      switch (user_choice) {
        case CREATE_NODE:
          if (node) {
            printf("There's a node created already. Try "
                "inserting it in the tree or deleting it before "
                "allocating another node");
            break;
          }

          if (data_type == APP_TREE_MANIPULATIONS) {
            // Create the data node of the Generic node

            create_app_data(&data_node, name, size,
                app_price, rating);

            // Use the pointer returned by create_app_data to
            // create the generic node with a pointer to the data
            // node created above

            create_node(&node, data_node, print_app_data,
                delete_app_data, compare_app_data);
          }
          else {
            create_phone_data(&data_node, model, storage,
                release_year, phone_price, battery_life);
            create_node(&node, data_node, print_phone_data,
                delete_phone_data, compare_phone_data);
          }

          data_node = NULL;
          printf("Created Item is:\n");
          print_node(node);
          break;

        case INSERT_NODE:
          if (!node) {
            printf("Create a node first!\n");
            break;
          }
          printf("The tree before insertion :\n");
          print_tree(root_node);
          printf("After inserting node:\n");
          print_node(node);
          insert_node(&root_node, node);
          printf("\nThe new tree looks like :\n");
          print_tree(root_node);
          node = NULL;
          break;

        case FIND_NODES:
          if (!root_node) {
            printf("There is no tree!\n");
            break;
          }

          if (node) {
            printf("There's a node created already. Try "
                "inserting it in the tree or deleting it before "
                "allocating another node");
            break;
          }

          if (data_type == PHONE_TREE_MANIPULATIONS) {
            // Create a node with only the comparison element

            create_phone_data(&data_node, "", storage,
                              release_year, phone_price, battery_life);
          }
          else {
            // Create a node with only the comparison element

            create_app_data(&data_node, "", size, app_price, rating);
          }

          // Now use that node to find nodes of the tree that
          // match the comparison
          // array_of_duplicates holds pointers to the duplicate nodes

          int nodes = 0;
          array_of_duplicates = find_nodes(root_node, data_node, &nodes);
          if (data_type == APP_TREE_MANIPULATIONS) {
            printf("Found %d nodes with size, price, rating '%d, %f, %f'\n",
                nodes, size, app_price, rating);
          }
          else {
            printf("Found %d nodes with storage, release year, price,"
                   " battery life '%d, %d, %d, %f'\n",
                   nodes, storage, release_year, phone_price, battery_life);
          }
          for (int i = 0; i < nodes; i++) {
            printf("%d. ", i + 1);
            print_node(array_of_duplicates[i]);
          }

          // Free the 'dummy' node used to pass the comparison element
          // this function deallocates both the node and its data

          data_node = NULL;
          break;

        case REMOVE_NODE:
          if (!root_node) {
            printf("There is no tree!\n");
            break;
          }
          if (data_type == APP_TREE_MANIPULATIONS) {
            // Create a node with only the comparison element

            create_app_data(&data_node, "", size, app_price, rating);
            create_node(&node, data_node, print_app_data,
                delete_app_data, compare_app_data);
          }
          else {
            // Create a node with only the comparison element

            create_phone_data(&data_node, "", storage, release_year,
                              phone_price, battery_life);

            create_node(&node, data_node, print_phone_data,
                delete_phone_data, compare_phone_data);
          }

          // Now use that node to find nodes of the tree that
          // match the comparison
          // array_of_duplicates holds pointers to the duplicate nodes

          nodes = 0;
          array_of_duplicates = find_nodes(root_node, data_node, &nodes);
          if (data_type == APP_TREE_MANIPULATIONS) {
            printf("Found %d nodes with size, price, rating '%d, %f, %f'\n",
                   nodes, size, app_price, rating);
          }
          else {
            printf("Found %d nodes with storage, release year, price,"
                   " battery life '%d, %d, %d, %f'\n",
                   nodes, storage, release_year, phone_price, battery_life);
          }
          for (int i = 0; i < nodes; i++) {
            printf("%d. ", i + 1);
            print_node(array_of_duplicates[i]);
          }


          int index = 0;
          if (nodes > 1) {
            printf("Enter the index of part to remove: ");
            scanf("%d", &index);
            getchar();
            index--;
          }
          else if (nodes == 1) {
            index = 0;
          }
          else {
            printf("No part found!\n");
            break;
          }

          printf("Removing ");
          print_node(array_of_duplicates[index]);
          remove_node(&root_node, array_of_duplicates[index]);
          printf("New tree looks like: \n");
          print_tree(root_node);

          // Free the 'dummy' node used to pass the comparison element
          // this function deallocates both the node and its data

          delete_node(&node);
          data_node = NULL;
          if (array_of_duplicates) {
            free(array_of_duplicates);
            array_of_duplicates = NULL;
          }
          break;

        case DELETE_NODE:
          if (!node) {
            printf("You need to create a node first\n");
            break;
          }
          printf("Deleting node :\n");
          print_node(node);
          delete_node(&node);
          break;

        case DELETE_TREE:
          printf("Deleting entire Tree.\n");
          delete_tree(&root_node);
          break;

        case PRINT_TREE:
          printf("Current Tree: \n");
          print_tree(root_node);
          break;

      }
      /* end of switch */

    }
    /* end of  while (data_type ...  */

  }
  /* end of while(1) */

  /* never reached */

} /* main() */