#include <sys/types.h>
#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include <assert.h>

char *mystrcpy(char *, const char *);
size_t mystrlen(const char *);
char *mystrdup(const char *);
char *mystrcat(char *, const char *);
char *mystrstr(char *s1, const char *s2);
int mystrcmp(const char *, const char *);

void main() {
  /*
  const char str1[5] = "test";
  char str2[5] = {'\0'};
  char str3[10] = "abc";
  strcat(str3, "d");
  char str4[10] = "abc";
  strcat(str4, "d");
  const char *str5 = NULL;
  mystrcpy(str2, str1);
  printf("Result: %s\n", str2);
  strcpy(str2, str1);
  printf("Result2: %s\n", str2);
  printf("Duplicate result: %s\n", mystrdup(str1));
  printf("String dup: %s\n", strdup(str1));
  printf("mystrcat: %s\n", mystrcat(str3, str1));
  printf("strcat: %s\n", strcat(str4, str1));
  printf("mystrstr: %s\n", mystrstr("this is a test for mystrstr", "test for"));
  printf("strstr: %s\n", strstr("this is a test for mystrstr", "test for"));
  printf("mystrcmp: %d\n", mystrcmp("AB", "ABC"));
  printf("strcmp: %d\n", strcmp("AB", "ABC"));
  */


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
  }



/*
 * Implement the following string procedures.
 *
 * Type "man strst" to find what each of the functions should do.
 *
 * For example, mystrcpy should do the same as strcpy.
 *
 * IMPORTANT: DO NOT use predefined string functions.
 *
 * These string functions have the same functionality as their regular string.h
 * function counterparts. Assertion checks are used for each of the functions'
 * parameters. malloc is implemented to allocate memory for strings, as seen in
 * the case of mystrdup.
 */

char *mystrcpy(char *, const char *);
size_t mystrlen(const char *);
char *mystrdup(const char *);


char *mystrcpy(char * s1, const char * s2)
{
  assert(s1 != NULL);
  assert(s2 != NULL);
  /* Complete procedure */
  int str_len = mystrlen(s2);
  str_len++;
  for (int i = 0; i < str_len; i++) {
    s1[i] = s2[i];
  }
  return s1;
}


size_t mystrlen(const char *s)
{
  assert(s != NULL);
  /* Complete procedure */
  int count = 0;
  for (int i = 0; s[i] != '\0'; i++) {
    count++;
  }
  return count;
}


char *mystrdup(const char *s1)
{
  assert(s1 != NULL);
  /* Complete procedure */
  char *s2 = malloc(mystrlen(s1));
  assert(s2 != NULL);
  return mystrcpy(s2, s1);
}


char *mystrcat(char * s1, const char * s2)
{
  assert(s1 != NULL);
  assert(s2 != NULL);
  /* Complete procedure */
  int str_len = 0;
  int source_len = 0;
  str_len = mystrlen(s1);
  source_len = mystrlen(s2);
  for (int i = 0; i < source_len; i++) {
    s1[str_len] = s2[i];
    str_len++;
  }
  s1[str_len] = '\0';
  return s1;
}


char *mystrstr(char * s1, const char * s2)
{
  assert(s1 != NULL);
  assert(s2 != NULL);
  /* Complete procedure */
  int j = 0;
  int str_len = 0;
  char *ret = mystrdup(s1);
  str_len = mystrlen(s2);
  const char *ref = s2;
  if (!*s2) {
    return s1;
  }
  if (mystrlen(s2) > mystrlen(s1)) {
    return NULL;
  }
  for (int i = 0; i < mystrlen(s1); i++) {
    *ret++;
    for (j = 0; j < str_len; j++) {
      if (s1[i] == s2[j]) {
        if (j == (str_len - 1)) {
          *ret++;
          return (ret - str_len - 1);
        }
        i++;
        *ret++;
      }
      else {
        j = 0;
        break;
      }
    }
  }
  return NULL;
}


int mystrcmp(const char *s1, const char *s2) {
  assert(s1 != NULL);
  assert(s2 != NULL);
  /* Complete procedure */
  int ret = 0;
  for (int i = 0; ; i++) {
    if (s1[i] < s2[i]) {
      ret = -1;
      break;
    }
    else if (s1[i] > s2[i]) {
      ret = 1;
      break;
    }
    if ((s1[i] == '\0') || (s2[i] == '\0')) {
      break;
    }
  }
  return ret;
}
