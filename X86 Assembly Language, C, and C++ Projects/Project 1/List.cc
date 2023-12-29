//
// Implement the List class
//

#include <stdio.h>
#include <iostream>
#include "List.h"

/*
 * The functions of the List class are used to insert an element in a sorted
 * list, add it to the end, prepend it, remove a particular element, print the
 * list, and search for a particular element in the list.
 * The constructor declares the head node which is used in several of the given
 * functions.
 */


//
// Inserts a new element with value "val" in
// ascending order.
//
void
List::insertSorted( int val )
{
  // Complete procedure
  ListNode *prev = nullptr;
  ListNode *current = nullptr;
  ListNode *new_node = nullptr;
  new_node = new ListNode;
  if (!_head) {
    _head = new_node;
    _head->_value = val;
    _head->_next = nullptr;
  }
  else {
    current = _head;
    while ((current != nullptr) && (current->_value < val)) {
      prev = current;
      current = current->_next;
    }
    if (prev == nullptr) {
      new_node->_next = current;
      new_node->_value = val;
      _head = new_node;
    }
    else {
      prev->_next = new_node;
      new_node->_next = current;
      new_node->_value = val;
    }
  }
}

//
// Inserts a new element with value "val" at
// the end of the list.
//
void
List::append( int val )
{
  // Complete procedure
  ListNode *new_node = nullptr;
  ListNode *current = nullptr;
  new_node = new ListNode;
  new_node->_value = val;
  new_node->_next = nullptr;
  if (!_head) {
    _head = new_node;
  }
  else {
    current = _head;
    while (current->_next != nullptr) {
      current = current->_next;
    }
    current->_next = new_node;
  }
}

//
// Inserts a new element with value "val" at
// the beginning of the list.
//
void
List::prepend( int val )
{
  // Complete procedure
  ListNode *current = nullptr;
  ListNode *new_node = nullptr;
  new_node = new ListNode;
  new_node->_value = val;
  new_node->_next = nullptr;
  current = _head;
  new_node->_next = current;
  _head = new_node;
}

// Removes an element with value "val" from List
// Returns 0 if succeeds or -1 if it fails
int
List:: remove( int val )
{
  // Complete procedure
  ListNode *current = nullptr;
  ListNode *previous = nullptr;
  if (!_head) {
    return -1;
  }
  current = _head;
  while (current != nullptr) {
    if (current->_value == val) {
      if (previous == nullptr) {
        _head = current->_next;
        current->_next = nullptr;
      }
      else {
        previous->_next = current->_next;
      }
      delete current;
      return 0;
    }
    else {
      previous = current;
      current = current->_next;
    }
  }
  return -1;
}

// Prints The elements in the list.
void
List::print()
{
  // Complete procedure
  ListNode *current = nullptr;
  int value = 0;
  int i = 0;
  if (_head != nullptr) {
    current = _head;
    while (current != nullptr) {
      value = current->_value;
      //printf ("Element %d: %d\n", i, value);
      current = current->_next;
      i++;
    }
  }
}

//
// Returns 0 if "value" is in the list or -1 otherwise.
//
int
List::lookup(int val)
{
  // Complete procedure
  ListNode *current = nullptr;
  if (!_head) {
    return -1;
  }
  current = _head;
  while (current != nullptr) {
    if (current->_value == val) {
      return 0;
    }
    else {
      current = current->_next;
    }
  }

  return -1;
}

//
// List constructor
//
List::List()
{
  // Complete procedure
  ListNode *_head = nullptr;
}

//
// List destructor: delete all list elements, if any.
//
List::~List()
{
  // Complete procedure
  while (_head != nullptr) {
    remove(_head->_value);
  }
}