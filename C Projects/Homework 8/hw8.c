
/* Siddharth Nadgaundi, hw8.c, CS 24000, Spring 2023
 * Last updated March 22, 2023
 */

/* Add any includes here */
#include "hw8.h"

#include <assert.h>
#include <malloc.h>
#include <stdio.h>
#include <string.h>

/*
 * This function is used to populate a player_t structure and place it at the
 * correct location in the list.
 * First, a while loop is used to check if the club passed as a parameter
 * exists in the list. Next, the new_player variable is assigned values from
 * the parameters using malloc(). The club is then checked to see if it has any
 * players. A while loop is then used to check the location where the player is
 * to be inserted by checking if the current salary is greater than the player's
 * salary. Next, an if statement is used to check if the player has to be
 * inserted at the head or at some other position in the list. If there are no
 * errors, the function returns OK.
 */

int insert_player(club_t *head_ptr, char *club_name, char *p_name,
                  int p_salary) {
  player_t *new_player = NULL;
  player_t *previous_player = NULL;
  player_t *current_player = NULL;
  club_t *club_found = NULL;

  assert(head_ptr != NULL);
  assert(club_name != NULL);
  assert(p_name != NULL);
  assert(p_salary > 0);

  club_found = head_ptr;
  while ((club_found != NULL) &&
  (strcmp(club_found->club_name, club_name) != 0)) {
    club_found = club_found->next_club;
  }

  if (club_found == NULL) {
    return NO_SUCH_CLUB;
  }

  new_player = (player_t *) malloc(sizeof(player_t));
  assert(new_player != NULL);
  new_player->name = malloc(strlen(p_name) + 1);
  assert(new_player->name != NULL);
  strcpy(new_player->name, p_name);
  new_player->salary = p_salary;
  new_player->next_player = NULL;

  if (club_found->player_count != 0) {
    current_player = club_found->players;
    while ((current_player != NULL) && (current_player->salary > p_salary)) {
      previous_player = current_player;
      current_player = current_player->next_player;
    }
    if (previous_player == NULL) {
      new_player->next_player = current_player;
      club_found->players = new_player;
      club_found->player_count++;
    }
    else {
      previous_player->next_player = new_player;
      new_player->next_player = current_player;
      club_found->player_count++;
    }
  }
  else {
    club_found->players = new_player;
    club_found->player_count++;
  }

  return OK;
} /* insert_player() */

/*
 * This function is used to retire a player from the club by removing them
 * from the list.
 * First, a while loop is used to check if the club passed in as a parameter
 * exists in the list or not. If it does exist and has a player count greater
 * than 0, The pointer to the players of the club is assigned to current_player
 * in order to iterate through all the players to check if the player is
 * present in the club or not. If a player is found, another while loop is used
 * to remove the player from the list and set the previous pointer to the
 * next pointer that the retired player points to. An if statement is used to
 * check if the retiring player is the head of the list or not in order to
 * accordingly remove the retired player.
 */

int retire_player(club_t *head_ptr, char *club_name, char *p_name) {
  club_t *club_found = NULL;
  player_t *player_found = NULL;
  player_t *current_player = NULL;
  player_t *search_player = NULL;
  player_t *previous_player = NULL;
  int highest_salary = 0;
  int count = 0;

  assert(head_ptr != NULL);
  assert(club_name != NULL);
  assert(p_name != NULL);

  club_found = head_ptr;
  while ((club_found != NULL) &&
  (strcmp(club_found->club_name, club_name) != 0)) {
    club_found = club_found->next_club;
  }

  if (club_found == NULL) {
    return NO_SUCH_CLUB;
  }

  if (club_found->player_count != 0) {
    current_player = club_found->players;
    while (current_player != NULL) {
      if ((strcmp(current_player->name, p_name) == 0) &&
      (current_player->salary > highest_salary)) {
        player_found = current_player;
        player_found->name = current_player->name;
        highest_salary = current_player->salary;
      }
      current_player = current_player->next_player;
    }
  }
  else {
    return NO_SUCH_PLAYER;
  }

  if (highest_salary == 0) {
    return NO_SUCH_PLAYER;
  }

  search_player = club_found->players;
  while ((search_player != NULL) && (count == 0)) {
    if ((strcmp(search_player->name, player_found->name) == 0) &&
    (search_player->salary == highest_salary)) {
      if (previous_player == NULL) {
        club_found->players = player_found->next_player;
      }
      else {
        previous_player->next_player = player_found->next_player;
      }
      club_found->player_count--;

      free(player_found->name);
      player_found->name = NULL;
      free(player_found);
      player_found = NULL;
      count++;
    }
    if (count == 0) {
      previous_player = search_player;
      search_player = search_player->next_player;
    }
  }

  return OK;
} /* retire_player() */

/*
 * This function is used to transfer a player from one club to another. Incase
 * duplicate names are present, the player with the highest salary is
 * transferred or the one to appear first.
 * The function starts by checking if the old club and new club are the same.
 * Next, 2 while loops are used to check if the old club and new club exist in
 * the list or not. Next, a while loop is used to check if the player exists
 * in the old club or not. Incase the player is found, the corresponding values
 * are assigned to the pointers. The insert_player() function is then called.
 * Finally, the player is removed from the old club with an if statement used
 * to check if the player is the head or not.
 */

int transfer_player(club_t *head_ptr, char *old_club, char *new_club,
                    char *p_name) {
  club_t *old_found = NULL;
  club_t *new_found = NULL;
  player_t *player_found = NULL;
  player_t *current_player = NULL;
  player_t *search_player = NULL;
  player_t *previous_player = NULL;
  int highest_salary = 0;
  int count = 0;
  int ret = 0;

  assert(head_ptr != NULL);
  assert(old_club != NULL);
  assert(new_club != NULL);
  assert(p_name != NULL);

  if (strcmp(old_club, new_club) == 0) {
    return INVALID_TRANSFER;
  }

  old_found = head_ptr;
  while ((old_found != NULL) && (strcmp(old_found->club_name, old_club) != 0)) {
    old_found = old_found->next_club;
  }
  if (old_found == NULL) {
    return NO_SUCH_CLUB;
  }

  new_found = head_ptr;
  while ((new_found != NULL) && (strcmp(new_found->club_name, new_club) != 0)) {
    new_found = new_found->next_club;
  }
  if (new_found == NULL) {
    return NO_SUCH_CLUB;
  }

  if (old_found->player_count != 0) {
    current_player = old_found->players;
    while (current_player != NULL) {
      if ((strcmp(current_player->name, p_name) == 0) &&
      (current_player->salary > highest_salary)) {
        player_found = current_player;
        player_found->name = current_player->name;
        highest_salary = current_player->salary;
      }
      current_player = current_player->next_player;
    }
  }
  else {
    return NO_SUCH_PLAYER;
  }

  if ((highest_salary == 0) || (player_found == NULL)) {
    return NO_SUCH_PLAYER;
  }

  ret = insert_player(head_ptr, new_club, player_found->name,
  player_found->salary);
  if (ret != -1) {
    return ret;
  }
  else {
    search_player = old_found->players;
    while ((search_player != NULL) && (count == 0)) {
      if ((strcmp(search_player->name, player_found->name) == 0) &&
      (search_player->salary == highest_salary)) {
        if (previous_player == NULL) {
          old_found->players = player_found->next_player;
        }
        else {
          previous_player->next_player = player_found->next_player;
        }
        old_found->player_count--;

        free(player_found->name);
        player_found->name = '\0';
        free(player_found);
        player_found = NULL;
        count++;
      }

      if (count == 0) {
        previous_player = search_player;
        search_player = search_player->next_player;
      }
    }
  }

  return OK;
} /* transfer_player() */

/*
 * This function is used to deallocate all memory used by clubs that don't have
 * any players.
 * A while loop is used to iterate through all the clubs in the list in order
 * to check if any club has 0 players. In case a club with 0 players is found,
 * an if statement is used to determine if the club is the head or not in order
 * to move the pointers accordingly. This is followed by deallocation of all
 * memory present in that club.
 */

club_t * find_dead_clubs(club_t *head_ptr) {
  club_t *head_club = NULL;
  club_t *search_club = NULL;
  club_t *club_found = NULL;
  club_t *previous_club = NULL;

  assert(head_ptr != NULL);

  head_club = head_ptr;
  search_club = head_ptr;
  while (search_club != NULL) {
    if ((search_club->player_count == 0) && (search_club->players == NULL)) {
      club_found = search_club;
      if (previous_club == NULL) {
        head_club = club_found->next_club;
        search_club = search_club->next_club;
      }
      else {
        previous_club->next_club = club_found->next_club;
        search_club = search_club->next_club;
      }

      free(club_found->club_name);
      club_found->club_name = '\0';
      free(club_found->coach);
      club_found->coach = '\0';
      free(club_found);
      club_found = NULL;
    }
    else {
      previous_club = search_club;
      search_club = search_club->next_club;
    }
  }

  return head_club;
} /* find_dead_clubs() */

/*
 * This function is used to determine the most valuable club based on the
 * formula provided.
 * A while loop is used to iterate through all the clubs. A second while loop
 * is used to iterate through all the players present in each club. Within the
 * while loops, the value of the club is computed and compared against the
 * current highest value to determine if the current club has the highest
 * value. The pointers are updated within this if statement and returned at
 * the end of the function.
 */

club_t * most_valuable_club(club_t *head_ptr) {
  club_t *current_club = NULL;
  club_t *most_valuable = NULL;
  player_t *current_player = NULL;
  int g_scored = 0;
  int g_lost = 0;
  int player_sal = 0;
  long highest_value = 0;
  long temp_val = 0;
  float temp = 0.0;

  assert(head_ptr != NULL);

  current_club = head_ptr;
  while (current_club != NULL) {
    if (current_club->player_count != 0) {
      current_player = current_club->players;
      while (current_player != NULL) {
        player_sal = player_sal + current_player->salary;
        current_player = current_player->next_player;
      }
      g_scored = current_club->goals_scored;
      g_lost = current_club->goals_lost + 1;
      temp = ((g_scored * 1.0) / (g_lost * 1.0));
      temp_val = temp * player_sal;
      if (temp_val > highest_value) {
        highest_value = temp_val;
        most_valuable = current_club;
      }
    }
    player_sal = 0;
    g_scored = 0;
    g_lost = 0;
    temp_val = 0;
    temp = 0.0;
    current_club = current_club->next_club;
  }

  return most_valuable;
} /* most_valuable_club() */

/*
 * This function is used to traverse through the list and deallocate everything
 * in the club with the passed name.
 * First, a while loop is used to check if the passed club is present in the
 * list or not. A second while loop is used to iterate through all the players
 * of the club and deallocate all memory of the players in the club. The third
 * while loop is used to iterate through all the clubs again to deallocate
 * memory related to club_name, coach, and the club itself.
 */

club_t * delete_club(club_t *head_ptr, char *club_name) {
  club_t *club_found = NULL;
  club_t *head_club = NULL;
  club_t *search_club = NULL;
  club_t *previous_club = NULL;
  player_t *current_player = NULL;
  char *name = {'\0'};

  assert(head_ptr != NULL);
  assert(club_name != NULL);

  head_club = head_ptr;
  club_found = head_ptr;
  while ((club_found != NULL) &&
  (strcmp(club_found->club_name, club_name) != 0)) {
    club_found = club_found->next_club;
  }

  if (club_found == NULL) {
    return head_ptr;
  }

  current_player = club_found->players;
  while (current_player != NULL) {
    int ret = 0;
    name = current_player->name;
    current_player = current_player->next_player;
    ret = retire_player(head_ptr, club_found->club_name, name);
    if (ret != -1) {
      return head_ptr;
    }
  }

  search_club = head_ptr;
  while (search_club != NULL) {
    if (strcmp(search_club->club_name, club_name) == 0) {
      if (previous_club == NULL) {
        head_club = club_found->next_club;
        search_club = search_club->next_club;
      }
      else {
        previous_club->next_club = club_found->next_club;
        search_club = search_club->next_club;
      }

      free(club_found->club_name);
      club_found->club_name = '\0';
      free(club_found->coach);
      club_found->coach = '\0';
      free(club_found);
      club_found = NULL;
    }
    else {
      previous_club = search_club;
      search_club = search_club->next_club;
    }
  }

  return head_club;
} /* delete_club() */