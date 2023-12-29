#include <stdlib.h>
#include "mystring.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>

void main() {
  
  char str1[5] = "test";
  printf("Mylen: %d\n", mystrlen(str1));
  printf("Len: %d\n", strlen(str1));
  char str2[5] = {'\0'};
  char str3[10] = "abc";
  char * result = '\0';
  mystrcpy(str2, str1);
  printf("Result: %s\n", str2);
  strcpy(str2, str1);
  printf("Result2: %s\n", str2);
  mystrcat(str3, "");
  printf("mystrcat: %s\n", str3);
  char str4[10] = "abc";
  strcat(str4, "");
  printf("strcat: %s\n", str4);
  printf("mystrcmp: %d\n", mystrcmp("ABC", "ABC"));
  printf("strcmp: %d\n", strcmp("ABC", "ABC"));
  printf("mystrstr: %s\n", mystrstr("Hello world. CS240","ld"));
  printf("strstr: %s\n", strstr("Hello world. CS240","ld"));
  printf("Duplicate result: %s\n", mystrdup(str1));
  printf("String dup: %s\n", strdup(str1));
  printf("-----------------------------------------------------------\n");
  
  char a[200];
  char c[200];
  char * b;
  char * d;
  mystrcpy(a, "Hello world");
  b = mystrcat(a, ", CS240 C Programming");
  b = mystrcat(a, ", This is a great course");
  printf("A and B\n");
  printf("\"%s\"\n", a);
  printf("\"%s\"\n", b);
  strcpy(c, "Hello world");
  d = strcat(c, ", CS240 C Programming");
  d = strcat(c, ", This is a great course");
  printf("C and D\n");
  printf("\"%s\"\n", c);
  printf("\"%s\"\n", d);

  /*mystrcpy(a, "");
  b = mystrcat(a, "");
  b = mystrcat(b, "Hello");
  printf("\"%s\"\n", a);
  printf("\"%s\"\n", b);
  */

  /*const char *str5 = NULL;
  printf("Duplicate result: %s\n", mystrdup(str1));
  printf("String dup: %s\n", strdup(str1));
  printf("mystrcat: %s\n", mystrcat(str3, str1));
  printf("strcat: %s\n", strcat(str4, str1));
  
  */

  /*
  char s1[64], *s2;

  printf("Purdue Test\n");

  mystrcpy(s1, "The quick brown fox jumps over the lazy dog");
  s2 = mystrstr(s1, "jumps");
  assert(!strcmp(s2, "jumps over the lazy dog"));
  printf("strcmp return %d\n", strcmp(s2, "jumps over the lazy dog"));
  s2 = strstr(s1, "jumps");
  printf("strcmp return 2 %d\n", strcmp(s2, "jumps over the lazy dog"));
  assert(mystrstr(s1, "Hello")==NULL);
  assert(mystrstr("jumps", s1)==NULL);
  s2 = mystrstr(s1, "");
  assert(s2 == s1);


  assert(mystrcmp("abcd","dabc")<0);
  assert(mystrcmp("dab", "abcd")>0);
  assert(mystrcmp("dab", "dab")==0);
  */
}





/*
 * This function has the same functionality as the strlen function in string.h
 * However, it is implemented using pointers to iterate over the length of the
 * string and then returns that length.
 */
int mystrlen(char * s) {
  assert(s != NULL);
  int count = 0;
  while (*s != '\0') {
    count++;
    s++;
  }
  return count;
}

/*
 * This function is used to copy the src string into the dest string like the
 * strcpy function in string.h. However, pointers are used to copy the contents
 * in addition to the mystrlen function to create a for loop over the length of
 * the src string.
 */
char * mystrcpy(char * dest, char * src) {
  assert(dest != NULL);
  assert(src != NULL);
  char *temp = dest;
  int str_len = 0;
  str_len = mystrlen(src);
  str_len++;
  for (int i = 0; i < str_len; i++) {
    *temp = *src;
    temp++;
    src++;
  }
  return dest;
}

/*
 * This function is used to conatenate the src string to the dest string by
 * first moving the dest pointer by its length so that the contents of src can
 * be added to the end of dest. The string is then returned by moving the dest
 * pointer back to its original location at the start.
 */
char * mystrcat(char * dest, char * src) {
  assert(dest != NULL);
  assert(src != NULL);
  int str_len = 0;
  int source_len = 0;
  str_len = mystrlen(dest);
  source_len = mystrlen(src);
  for (int j = 0; j < str_len; j++) {
    dest++;
  }
  for (int i = 0; i < source_len; i++) {
    *dest = *src;
    dest++;
    src++;
  }
  *dest = '\0';
  return (dest - str_len - source_len);
}

/*
 * This function compares two strings by using the * operator to get the
 * character at the current index. The corresponding return values are then
 * returned.
 */
int mystrcmp(char * s1, char * s2) {
  assert(s1 != NULL);
  assert(s2 != NULL);
  int ret = 0;
  for (int i = 0; ; i++) {
    if (*s1 < *s2) {
      ret = -1;
      break;
    }
    else if (*s1 > *s2) {
      ret = 1;
      break;
    }
    if ((*s1 == '\0') || (*s2 == '\0')) {
      break;
    }
    s1++;
    s2++;
  }
  return ret;
}

/*
 * This function is used to find the substring from the larger string. If
 * statements are used to compare the contents at each index using the *
 * operator. In case a match is not found, the pointers are moved accordingly.
 * The same is followed when returning the substring if it is found.
 */
char * mystrstr(char * hay, char * needle) {
  assert(hay != NULL);
  assert(needle != NULL);
  char *hay_str = hay;
  char *ref = needle;
  int str_len = 0;
  int count = 0;
  str_len = mystrlen(needle);
  if (!*needle) {
    return hay;
  }
  if (mystrlen(needle) > mystrlen(hay)) {
    return NULL;
  }
  while (*hay_str) {
    if (*hay_str == *ref) {
      count++;
      ref++;
    }
    else {
      count = 0;
      ref = needle;
    }
    hay_str++;
    if (count == str_len) {
      break;
    }
  }
  if (count == str_len) {
    return (hay_str - count);
  }
  else {
     return NULL;
  }
}

/*
 * This function creates a duplicate string by using malloc & mystrlen to
 * create the string and calling the mystrcpy function to populate the string.
 */
char * mystrdup(char * s) {
  assert(s != NULL);
  char *s2 = malloc(mystrlen(s));
  assert(s2 != NULL);
  return mystrcpy(s2, s);
}

/*
 * This functions is used to copy one string into another over the specified
 * length of n. The * operator is used to copy the elements.
 */
char * mymemcpy(char * dest, char * src, int n) {
  assert(dest != NULL);
  assert(src != NULL);
  char *destination = dest;
  char *source = src;
  for (int i = 0; i < n; i++) {
    *destination = *source;
    destination++;
    source++;
  }
  return dest;
}