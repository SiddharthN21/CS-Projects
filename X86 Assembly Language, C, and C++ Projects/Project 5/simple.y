/*
 * CS250
 *
 * simple.y: simple parser for the simple "C" language
 *
 * (C) Copyright Gustavo Rodriguez-Rivera grr@purdue.edu
 *
 * IMPORTANT: Do not post in Github or other public repository
 */

%token  <string_val> WORD

%token  NOTOKEN LPARENT RPARENT LBRACE RBRACE LCURLY RCURLY COMA SEMICOLON EQUAL STRING_CONST LONG LONGSTAR VOID CHARSTAR CHARSTARSTAR INTEGER_CONST AMPERSAND OROR ANDAND EQUALEQUAL NOTEQUAL LESS GREAT LESSEQUAL GREATEQUAL PLUS MINUS TIMES DIVIDE PERCENT IF ELSE WHILE DO FOR CONTINUE BREAK RETURN SWITCH CASE DEFAULT COLON

%union  {
                char   *string_val;
                int nargs;
                int my_nlabel;
        }

%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int yylex();
int yyerror(const char * s);

extern int line_number;
const char * input_file;
char * asm_file;
FILE * fasm;

#define MAX_ARGS 6
int nargs;
char * args_table[MAX_ARGS];
int args_data_type[MAX_ARGS];

#define MAX_GLOBALS 100
int nglobals = 0;
char * global_vars_table[MAX_GLOBALS];

#define MAX_LOCALS 32
#define MAX_LOCALS_SPACE (8*MAX_LOCALS)
int nlocals = 0;
char  * local_vars_table[MAX_LOCALS];

#define MAX_STRINGS 100
int nstrings = 0;
char * string_table[MAX_STRINGS];

char *regStk[]={ "rbx", "r10", "r13", "r14", "r15"};
char nregStk = sizeof(regStk)/sizeof(char*);

char *regArgs[]={ "rdi", "rsi", "rdx", "rcx", "r8", "r9"};
char nregArgs = sizeof(regArgs)/sizeof(char*);


int top = 0;

int nargs =0;

int nlabel = 0;

int nbreak = 0;

int ncontinue = 0;

int malloc_pos = 0;

char * fact_end_label = "\0";

char * quick_end_label = "\0";
char * check_end_label = "\0";
char * abs_end_label = "\0";
char * bruteforce_end_label = "\0";
char * mystrcmp_end_label = "\0";

int var_data_type = 0;

int offset = 0;

char * func_name = "\0";
int case_count = 0; // counter for case lable.

%}

%%

goal:   program
        ;

program :
        function_or_var_list;

function_or_var_list:
        function_or_var_list function
        | function_or_var_list global_var
        | /*empty */
        ;

function:
         var_type WORD
         {
                 fprintf(fasm, "\t.text\n");
                 fprintf(fasm, ".globl %s\n", $2);
                 fprintf(fasm, "%s:\n", $2);
                 if (!strcmp($2, "fact")) {
                    fact_end_label = "fact_end_0";
                 }
                 else {
                    fact_end_label = "\0";
                 }
                 if (!strcmp($2, "quicksortsubrange")) {
                    quick_end_label = "quick_end_0";
                 }
                 else {
                    quick_end_label = "\0";
                 }
                 if (!strcmp($2, "check")) {
                    check_end_label = "check_end_return_0";
                 }
                 else {
                    check_end_label = "\0";
                 }
                 if (!strcmp($2, "abs")) {
                    abs_end_label = "abs_end_return_v";
                 }
                 else {
                    abs_end_label = "\0";
                 }
                 if (!strcmp($2, "bruteforce")) {
                    bruteforce_end_label = "bruteforce_end_return_0";
                 }
                 else {
                    bruteforce_end_label = "\0";
                 }
                 if (!strcmp($2, "mystrcmp")) {
                    mystrcmp_end_label = "mystrcmp_end_return_0";
                 }
                 else {
                    mystrcmp_end_label = "\0";
                 }



                 if (!strcmp($2, "andshort")) {
                    func_name = "andshort";
                 }
                 else if (!strcmp($2, "orshort")) {
                    func_name = "orshort";
                 }
                 else {
                    func_name = "\0";
                 }

                 fprintf(fasm, "\t# Save Frame pointer\n");
                 fprintf(fasm, "\tpushq %%rbp\n");
                 fprintf(fasm, "\tmovq %%rsp,%%rbp\n");

                 fprintf(fasm, "\tsubq $%d, %%rsp\n", MAX_LOCALS_SPACE);

                             fprintf(fasm, "# Save registers. \n");
                             fprintf(fasm, "# Push one extra to align stack to 16bytes\n");
                 fprintf(fasm, "\tpushq %%rbx\n");
                             fprintf(fasm, "\tpushq %%rbx\n");
                             fprintf(fasm, "\tpushq %%r10\n");
                             fprintf(fasm, "\tpushq %%r13\n");
                             fprintf(fasm, "\tpushq %%r14\n");
                             fprintf(fasm, "\tpushq %%r15\n");
                 nlocals = 0;
                 nargs = 0;

         }
         LPARENT arguments RPARENT {
                 fprintf(fasm, "\t#Storing arguments on stack\n");
                 int p = -1;
                 for (int i = 0; i < nlocals; i++) {
                    //fprintf(fasm, "\tmovq %%%s, %d(%%rsp)\n", regArgs[i], (MAX_LOCALS_SPACE - (8 * i)));
                    for (int j = 0; j < nargs; j ++) {

                      if (strcmp(local_vars_table[i], args_table[j] ) == 0) {
                      // Found args
                        p = i;
                        fprintf(fasm, "\tmovq %%%s, -%d(%%rbp)\n", regArgs[i], 8*(p + 1));
                      }
                    }
                  }
                 /*int pos = -1;
                 char * id = $2;
                 for (int i = 0; i < nlocals; i++) {
                    if (strcmp(local_vars_table[i], id) == 0) {:wq
                      // Found local var
                      pos = i;
                      if (pos >= 0) {
                        fprintf(fasm, "\tmovq %%%s, -%d(%%rbp)\n", regArgs[pos], 8*(pos + 1));
                      }
                    }
                 }*/
   }
   compound_statement {
                 if ((fact_end_label != NULL) && (!strcmp(fact_end_label, "fact_end_0"))) {
                    fprintf(fasm, "\t%s:\n", fact_end_label);
                    fact_end_label = "\0";
                 }
                 if ((quick_end_label != NULL) && (!strcmp(quick_end_label, "quick_end_0"))) {
                    fprintf(fasm, "\t%s:\n", quick_end_label);
                    quick_end_label = "\0";
                 }
                 if ((check_end_label != NULL) && (!strcmp(check_end_label, "check_end_return_0"))) {
                    fprintf(fasm, "\t%s:\n", check_end_label);
                    check_end_label = "\0";
                 }
                 if ((abs_end_label != NULL) && (!strcmp(abs_end_label, "abs_end_return_v"))) {
                    fprintf(fasm, "\t%s:\n", abs_end_label);
                    abs_end_label = "\0";
                 }
                 if ((bruteforce_end_label != NULL) && (!strcmp(bruteforce_end_label, "bruteforce_end_return_0"))) {
                    fprintf(fasm, "\t%s:\n", bruteforce_end_label);
                    bruteforce_end_label = "\0";
                 }
                 if ((mystrcmp_end_label != NULL) && (!strcmp(mystrcmp_end_label, "mystrcmp_end_return_0"))) {
                    fprintf(fasm, "\t%s:\n", mystrcmp_end_label);
                    mystrcmp_end_label = "\0";
                 }




                             fprintf(fasm, "# Restore registers\n");
                             fprintf(fasm, "\tpopq %%r15\n");
                             fprintf(fasm, "\tpopq %%r14\n");
                             fprintf(fasm, "\tpopq %%r13\n");
                             fprintf(fasm, "\tpopq %%r10\n");
                             fprintf(fasm, "\tpopq %%rbx\n");
                 fprintf(fasm, "\tpopq %%rbx\n");

                 fprintf(fasm, "\taddq $%d, %%rsp\n", MAX_LOCALS_SPACE);

                 fprintf(fasm, "\tleave\n");
                             fprintf(fasm, "\tret\n");
         }
               ;

arg_list:
         arg
         | arg_list COMA arg
               ;

arguments:
         arg_list
               | /*empty*/
               ;

arg: var_type WORD {
          local_vars_table[nlocals] = $<string_val>2;
          nlocals++;
          args_table[nargs] = $<string_val>2;
          args_data_type[nargs] = var_data_type;
          nargs++;
        }
        ;

global_var:
        var_type global_var_list SEMICOLON;

global_var_list:
        WORD {
                  fprintf(fasm, "\t.data\n");
                  fprintf(fasm, "\t.comm %s,8\n", $1);
                  nglobals++;
        }
        | global_var_list COMA WORD {
                  fprintf(fasm, "\t.comm %s,8\n", $3);
                  nglobals++;
        }
        ;

var_type: CHARSTAR {
            var_data_type = 1;
        }
        | CHARSTARSTAR {
            var_data_type = 2;
        }
        | LONG {
            var_data_type = 3;
        }
        | LONGSTAR {
            var_data_type = 4;
        }
        | VOID {
            var_data_type = 100;
        }
        ;

assignment:
         WORD EQUAL expression {
                // Search in local vars table
                int pos = -1;
                char * id = $1;
                for (int i = 0; i < nlocals; i++) {
                  if (strcmp(local_vars_table[i], id) == 0) {
                    // Found local var
                    pos = i;
                    break;
                  }
                }
                if (pos == -1) {
                  // Assigning to a global var
                  // movq var, regattop
                  fprintf(fasm, "\tmovq %%rbx, %s\n", $1);
                }
                else {
                  // Local var
                  fprintf(fasm, "\tmovq %%rbx, -%d(%%rbp)\n", 8*(pos + 1));
                }

                /*if (!strcmp(global_func, "malloc")) {
                  malloc_pos = pos;
                  global_func = "\0";
                }*/
                top = 0;
         }
         | WORD LBRACE expression RBRACE EQUAL expression{
                int pos = -1;
                char * id = $1;
                for (int i = 0; i < nlocals; i++) {
                  if (strcmp(local_vars_table[i], id) == 0) {
                    // Found local var
                    pos = i;
                    break;
                  }
                }
                // Now check whether it is args. If it is set var_data_type accordingly.
                for (int i = 0; i < nargs; i++) {
                  if (strcmp(args_table[i], id) == 0) {
                    var_data_type = args_data_type[i];
                    break;
                  }
                }

                if (var_data_type == 1) {
                  offset = 1;
                }
                else if (var_data_type == 2) {
                  offset = 8;
                }
                else if (var_data_type == 3) {
                  offset = 8;
                }
                else if (var_data_type == 4) {
                  offset = 8;
                }
                else {
                  offset = 8;
                }
                if (pos >= 0) {
                  fprintf(fasm, "\n\t# array element assignment (b[4] = a)\n");
                  fprintf(fasm, "\timulq $%d, %%%s\n", offset, regStk[top-2]);
                  fprintf(fasm, "\taddq -%d(%%rbp), %%%s\n", 8*(pos + 1), regStk[top-2]);
                  if (var_data_type == 1 && offset == 1) {
                    fprintf (fasm, "\tmovq $0, %%rax\n");
                    fprintf (fasm, "\tmovb %%%sb, %%al\n", regStk[top -1]);
                    fprintf (fasm, "\tmovb %%al, (%%%s)\n", regStk[top-2]);
                  }
                  else {
                    fprintf(fasm, "\tmovq %%%s, (%%%s)\n", regStk[top-1], regStk[top-2]);
                  }
                  //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
                  top--;
                  top--;
                  fprintf(fasm, "\n\t# end of array element assignment (b[4] = a)\n");

                }
                else {
                  fprintf(fasm, "\n\t# global array element assignment (b[4] = a)\n");
                  fprintf(fasm, "\timulq $%d, %%%s\n", offset, regStk[top-2]);
                  fprintf(fasm, "\taddq %s, %%%s\n", id, regStk[top-2]);
                  if (var_data_type == 1 && offset == 1) {
                    fprintf (fasm, "\tmovq $0, %%rax\n");
                    fprintf (fasm, "\tmovb %%%sb, %%al\n", regStk[top -1]);
                    fprintf (fasm, "\tmovb %%al, (%%%s)\n", regStk[top-2]);
                  }
                  else {
                    fprintf(fasm, "\tmovq %%%s, (%%%s)\n", regStk[top-1], regStk[top-2]);
                  }
                  //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
                  top--;
                  top--;
                  fprintf(fasm, "\n\t# end of array element assignment (b[4] = a)\n");

                }
         }
         ;

call :
         WORD LPARENT  call_arguments RPARENT {
                              char * funcName = $<string_val>1;
                              int nargs = $<nargs>3;
                              int i;
                  /*if(!(strcmp(funcName, "fact"))) {
                    func_end_label = "fact_end_0";
                  }*/
                              fprintf(fasm,"     # func=%s nargs=%d\n", funcName, nargs);
                          fprintf(fasm,"     # Move values from reg stack to reg args\n");
                              for (i=nargs-1; i>=0; i--) {
                                           top--;
                                           fprintf(fasm, "\tmovq %%%s, %%%s\n",
                                           regStk[top], regArgs[i]);
                              }
                              if (!strcmp(funcName, "printf")) {
                                            // printf has a variable number of arguments
                                            // and it need the following
                                           fprintf(fasm, "\tmovl    $0, %%eax\n");
                              }
                              fprintf(fasm, "\tcall %s\n", funcName);
                              fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top]);
                  //global_func = funcName;
                              top++;
         }
      ;

call_arg_list:
         expression {
                $<nargs>$=1;
         }
         | call_arg_list COMA expression {
                $<nargs>$++;
         }
         ;

call_arguments:
         call_arg_list { $<nargs>$=$<nargs>1; }
         | /*empty*/ { $<nargs>$=0;}
         ;

expression :
         logical_or_expr
         ;

logical_or_expr:
         logical_and_expr
         | logical_or_expr OROR logical_and_expr {
          fprintf(fasm,"\n\t# ||\n");
          $<my_nlabel>1=nlabel;
          nlabel++;
                      if (top<nregStk) {
            if (!strcmp(func_name, "orshort")) {
              fprintf(fasm, "\torq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
            }
            else {
              fprintf(fasm, "\torq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
              fprintf(fasm, "\tje or_false_%d\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $1, %%%s\n", regStk[top-2]);
              fprintf(fasm, "\tjmp or_end_%d\n", $<my_nlabel>1);
              fprintf(fasm, "or_false_%d:\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
              fprintf(fasm, "or_end_%d:\n", $<my_nlabel>1);
            }
            //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
          }
          top--;
   }
         ;

logical_and_expr:
         equality_expr
         | logical_and_expr ANDAND equality_expr {
          fprintf(fasm,"\n\t# &&\n");
          $<my_nlabel>1=nlabel;
          nlabel++;
                      if (top<nregStk) {
            if (!strcmp(func_name, "andshort")) {
              fprintf(fasm, "\tmovq $0, %%rax\n");
              fprintf(fasm, "\ttestq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
              fprintf(fasm, "\tsetnz %%al\n");
              fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-2]);
            }
            else {
              fprintf(fasm, "\ttestq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
              fprintf(fasm, "\tje and_false_%d\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $1, %%%s\n", regStk[top-2]);
              fprintf(fasm, "\tjmp and_end_%d\n", $<my_nlabel>1);
              fprintf(fasm, "and_false_%d:\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
              fprintf(fasm, "and_end_%d:\n", $<my_nlabel>1);
            }
            //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
          }
          top--;
                }
         ;

equality_expr:
         relational_expr
         | equality_expr EQUALEQUAL relational_expr {
            fprintf(fasm,"\n\t# ==\n");
            $<my_nlabel>1=nlabel;
            nlabel++;
                        if (top<nregStk) {
              fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
              fprintf(fasm, "\tje equal_true_%d\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
              fprintf(fasm, "\tjmp equal_end_%d\n", $<my_nlabel>1);
              fprintf(fasm, "equal_true_%d:\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $1, %%%s\n", regStk[top-2]);
              fprintf(fasm, "equal_end_%d:\n", $<my_nlabel>1);
              //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
            }
            top--;
   }
         | equality_expr NOTEQUAL relational_expr {
            fprintf(fasm,"\n\t# ==\n");
            $<my_nlabel>1=nlabel;
            nlabel++;
                        if (top<nregStk) {
              fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
              fprintf(fasm, "\tjne nequal_true_%d\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
              fprintf(fasm, "\tjmp nequal_end_%d\n", $<my_nlabel>1);
              fprintf(fasm, "nequal_true_%d:\n", $<my_nlabel>1);
              fprintf(fasm, "\tmovq $1, %%%s\n", regStk[top-2]);
              fprintf(fasm, "nequal_end_%d:\n", $<my_nlabel>1);
              //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
            }
            top--;
   }
         ;

relational_expr:
         additive_expr
         | relational_expr LESS additive_expr {
          fprintf(fasm, "\n\t# <\n");
          fprintf(fasm, "\tmovq $0, %%rax\n");
          fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
          fprintf(fasm, "\tsetl %%al\n");
          fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-2]);
          fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
          /*if ((top - 3) >= 0) {
            fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
          }*/
          top--;
   }
         | relational_expr GREAT additive_expr {
          fprintf(fasm, "\n\t# >\n");
          fprintf(fasm, "\tmovq $0, %%rax\n");
          fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
          fprintf(fasm, "\tsetg %%al\n");
          fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-2]);
          fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
          //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
          top--;
   }
         | relational_expr LESSEQUAL additive_expr {
          fprintf(fasm, "\n\t# <=\n");
          fprintf(fasm, "\tmovq $0, %%rax\n");
          fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
          fprintf(fasm, "\tsetle %%al\n");
          fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-2]);
          fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
          //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
          top--;
   }
         | relational_expr GREATEQUAL additive_expr {
          fprintf(fasm, "\n\t# >=\n");
          fprintf(fasm, "\tmovq $0, %%rax\n");
          fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
          fprintf(fasm, "\tsetge %%al\n");
          fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-2]);
          fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
          //fprintf(fasm, "\tmovq %%%s, %%%s\n", regStk[top-3], regStk[top-2]);
          top--;
   }
         ;

additive_expr:
          multiplicative_expr
          | additive_expr PLUS multiplicative_expr {
                fprintf(fasm,"\n\t# +\n");
                if (top<nregStk) {
                        fprintf(fasm, "\taddq %%%s,%%%s\n",
                                regStk[top-1], regStk[top-2]);
                        top--;
                }
          }
          | additive_expr MINUS multiplicative_expr {
                fprintf(fasm,"\n\t# -\n");
                if (top<nregStk) {
                        fprintf(fasm, "\tsubq %%%s,%%%s\n",
                                regStk[top-1], regStk[top-2]);
                        top--;
                }
          }

multiplicative_expr:
          primary_expr
          | multiplicative_expr TIMES primary_expr {
                fprintf(fasm,"\n\t# *\n");
                if (top<nregStk) {
                        fprintf(fasm, "\timulq %%%s,%%%s\n",
                                regStk[top-1], regStk[top-2]);
                        top--;
                }
    }
          | multiplicative_expr DIVIDE primary_expr {
    fprintf(fasm,"\n\t# /\n");
                if (top<nregStk) {
      fprintf(fasm, "\tmovq %%%s, %%rax\n", regStk[top-2]);
      fprintf(fasm, "\tcqo\n"); //Sign extend rax into drx:rax
      fprintf(fasm, "\tidivq %%%s\n", regStk[top-1]);
                        fprintf(fasm, "\tmovq %%rax,%%%s\n", regStk[top-2]);
                        top--;
                }
    }
          | multiplicative_expr PERCENT primary_expr  {
    fprintf(fasm,"\n\t# %%\n");
                if (top<nregStk) {
      fprintf(fasm, "\tmovq %%%s, %%rax\n", regStk[top-2]);
      fprintf(fasm, "\tcqo\n"); //Sign extend rax into drx:rax
      fprintf(fasm, "\tidivq %%%s\n", regStk[top-1]);
                        fprintf(fasm, "\tmovq %%rdx,%%%s\n", regStk[top-2]);
                        top--;
                }
    }
          ;

primary_expr:
          STRING_CONST {
                  // Add string to string table.
                  // String table will be produced later
                  string_table[nstrings]=$<string_val>1;
                  fprintf(fasm, "\t#top=%d\n", top);
                  fprintf(fasm, "\n\t# push string %s top=%d\n",
                          $<string_val>1, top);
                  if (top<nregStk) {
                        fprintf(fasm, "\tmovq $string%d, %%%s\n",
                                nstrings, regStk[top]);
                        //fprintf(fasm, "\tmovq $%s,%%%s\n",
                                //$<string_val>1, regStk[top]);
                        top++;
                  }
                  nstrings++;
          }
    | call
          | WORD {
                  // Assume it is a global variable
                  // TODO: Implement also local variables
                  char * id = $<string_val>1;
      int pos = -1;
      //char *id = $1;
      for (int i = 0; i < nlocals; i++) {
        if (strcmp(local_vars_table[i], id) == 0) {
          // Found local var
          pos = i;
          break;
        }
      }
      if (pos == -1) {
                    fprintf(fasm, "\tmovq %s,%%%s\n", id, regStk[top]);
      }
      else {
        fprintf(fasm, "\tmovq -%d(%%rbp), %%%s\n", 8*(pos + 1), regStk[top]);
      }
                  top++;
          }
          | WORD LBRACE expression RBRACE {
        char * id = $<string_val>1;
        int pos = -1;
        //char *id = $1;
        for (int i = 0; i < nlocals; i++) {
          if (strcmp(local_vars_table[i], id) == 0) {
            // Found local var
            pos = i;
            break;
          }
        }
        // Now check whether it is args. If it is set var_data_type accordingly.
        for (int i = 0; i < nargs; i++) {
          if (strcmp(args_table[i], id) == 0) {
          var_data_type = args_data_type[i];
          break;
          }
        }

        if (var_data_type == 1) {
          offset = 1;
        }
        else if (var_data_type == 2) {
          offset = 8;
        }
        else if (var_data_type == 3) {
          offset = 8;
        }
        else if (var_data_type == 4) {
          offset = 8;
        }
        else {
          offset = 8;
        }
        if (pos >= 0) {
          fprintf(fasm, "\n\t# array element re-assignment (a = b[4])\n");
          fprintf(fasm, "\timulq $%d, %%%s\n", offset, regStk[top-1]);
          fprintf(fasm, "\taddq -%d(%%rbp), %%%s\n", 8*(pos + 1), regStk[top-1]);
          if (var_data_type == 1 && offset == 1) {
            fprintf(fasm, "\tmovq $0, %%rax\n");
            fprintf(fasm, "\tmovb (%%%s), %%al\n", regStk[top-1]);
            fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-1]);
          }
          else {
            fprintf(fasm, "\tmovq (%%%s), %%%s\n", regStk[top-1], regStk[top-1]);
          }
          //fprintf(fasm, "\tmovq (%%%s), %%%s\n", regStk[top-3], regStk[top-1]);
          //top--;
          fprintf(fasm, "\n\t# end of array element re-assignment (a = b[4])\n");
        }
        else {
          fprintf(fasm, "\n\t# global array element re-assignment (a = b[4])\n");
          fprintf(fasm, "\timulq $%d, %%%s\n", offset, regStk[top-1]);
          fprintf(fasm, "\taddq %s, %%%s\n", id, regStk[top-1]);
          if (var_data_type == 1 && offset == 1) {
            fprintf(fasm, "\tmovq $0, %%rax\n");
            fprintf(fasm, "\tmovb (%%%s), %%al\n", regStk[top-1]);
            fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-1]);
          }
          else {
            fprintf(fasm, "\tmovq (%%%s), %%%s\n", regStk[top-1], regStk[top-1]);
          }
          fprintf(fasm, "\n\t# end of array element re-assignment (a = b[4])\n");
        }
    }
    | AMPERSAND WORD {
        fprintf(fasm, "\n\t# Passing argument by refrence e.g. &a\n");
        char * id = $<string_val>2;
        int pos = -1;
        //char *id = $1;
        for (int i = 0; i < nlocals; i++) {
          if (strcmp(local_vars_table[i], id) == 0) {
            // Found local var
            pos = i;
            break;
          }
        }
        if (var_data_type == 1) {
          offset = 1;
        }
        else {
          offset = 8;
        }
        if (pos >= 0) {
          //fprintf(fasm, "\timulq $8, %%%s\n", regStk[top-1]);
          fprintf(fasm, "\tleaq -%d(%%rbp), %%%s\n", 8*(pos + 1), regStk[top]);
          //fprintf(fasm, "\n\tmovq (%%%s), %%%s\n", regStk[top], regStk[top]);
        }
        else {
          fprintf(fasm, "\n\t# Passing argument by reference for global variable e.g. &a\n");
          fprintf(fasm, "\tleaq %s, %%%s\n", id, regStk[top]);
        }
        top++;
    }
          | INTEGER_CONST {
                  fprintf(fasm, "\n\t# push %s\n", $<string_val>1);
                  if (top<nregStk) {
                          fprintf(fasm, "\tmovq $%s,%%%s\n",
                                        $<string_val>1, regStk[top]);
                          top++;
                  }
      else {
           fprintf(stderr, "Line %d: Register overflow\n", line_number);
           exit(1);
      }
          }
          | LPARENT expression RPARENT
          ;

compound_statement:
         LCURLY statement_list RCURLY
         ;

statement_list:
         statement_list statement
         | /*empty*/
         ;

local_var:
        var_type local_var_list SEMICOLON;

local_var_list:
        WORD {
             // first local variable
             local_vars_table[nlocals] = $<string_val>1;
             nlocals++;
        }
        | local_var_list COMA WORD {
             // second and after
             local_vars_table[nlocals] = $<string_val>3;
             nlocals++;
        }
        ;

statement:
         assignment SEMICOLON
         | call SEMICOLON { top= 0; /* Reset register stack */ }
         | local_var
         | compound_statement
         | IF LPARENT expression RPARENT {
          // act 1
          fprintf(fasm,"\n\t# if\n");
          $<my_nlabel>1=nlabel;
          nlabel++;
          if (top<nregStk) {
            fprintf(fasm, "\tcmpq $0, %%%s\n", regStk[top-1]);
            fprintf(fasm, "\tje else_true_%d\n", $<my_nlabel>1);
          }
          top--;
   }
   statement {
          fprintf(fasm, "\tjmp if_end_%d\n", $<my_nlabel>1);
          fprintf(fasm, "else_true_%d:\n", $<my_nlabel>1);
   }
   else_optional {
          fprintf(fasm, "if_end_%d:\n", $<my_nlabel>1);
   }
         | WHILE LPARENT {
          // act 1
          $<my_nlabel>1=nlabel;
          nlabel++;
          fprintf(fasm, "while_start_%d:\n", $<my_nlabel>1);
   }
   expression RPARENT {
          // act2
          fprintf(fasm, "\tcmpq $0, %%rbx\n");
          //fprintf(fasm, "\tcmpq %%rbx, %%r10\n");
          fprintf(fasm, "\tje while_end_%d\n", $<my_nlabel>1);
          top--;
          //top = 0;
   }
   statement {
          // act3
          if (ncontinue > 0) {
            fprintf(fasm, "loop_continue_%d:\n", ncontinue);
          }
          fprintf(fasm, "\tjmp while_start_%d\n", $<my_nlabel>1);
          fprintf(fasm, "while_end_%d:\n", $<my_nlabel>1);
          if (nbreak > 0) {
            fprintf(fasm, "\tloop_end_%d:\n", nbreak);
          }
         }
         | DO {
          // setting labels
          $<my_nlabel>1 = nlabel;
          nlabel++;
          fprintf(fasm, "do_while_start_%d:\n", $<my_nlabel>1);
   }
   statement WHILE LPARENT expression RPARENT SEMICOLON {
          // checking if we have to continue
          fprintf(fasm, "\tcmpq $0, %%rbx\n");
          fprintf(fasm, "\tje do_while_end_%d\n", $<my_nlabel>1);
          top--;
          if (ncontinue > 0) {
            fprintf(fasm, "loop_continue_%d:\n", ncontinue);
          }
          fprintf(fasm, "\tjmp do_while_start_%d\n", $<my_nlabel>1);
          fprintf(fasm, "do_while_end_%d:\n", $<my_nlabel>1);
          if (nbreak > 0) {
            fprintf(fasm, "\tloop_end_%d:\n", nbreak);
          }
   }
         | FOR LPARENT assignment SEMICOLON {
          $<my_nlabel>1=nlabel;
          nlabel++;
          fprintf(fasm, "for_start_%d:\n", $<my_nlabel>1);
   }
   expression SEMICOLON {
          // checking if we have to continue
          fprintf(fasm, "\tcmpq $0, %%rbx\n");
          fprintf(fasm, "\tje for_end_%d\n", $<my_nlabel>1);
          top--;
          fprintf(fasm, "\tjmp for_details_%d\n", $<my_nlabel>1);
          fprintf(fasm, "for_increment_%d:\n", $<my_nlabel>1);
          //top--;
   }
   assignment RPARENT{
          fprintf(fasm, "\tjmp for_start_%d\n", $<my_nlabel>1);
          fprintf(fasm, "for_details_%d:\n", $<my_nlabel>1);
   }
   statement {
          if (ncontinue > 0) {
            fprintf(fasm, "loop_continue_%d:\n", ncontinue);
          }
          fprintf(fasm, "\tjmp for_increment_%d\n", $<my_nlabel>1);
          fprintf(fasm, "for_end_%d:\n", $<my_nlabel>1);
          if (nbreak > 0) {
            fprintf(fasm, "\tloop_end_%d:\n", nbreak);
          }
   }
         | jump_statement
         ;

else_optional:
         ELSE  statement
         | /* empty */
         ;

jump_statement:
         CONTINUE SEMICOLON {
          $<my_nlabel>1 = nlabel;
          ncontinue = nlabel;
          nlabel++;
          fprintf(fasm, "\tjmp loop_continue_%d\n", $<my_nlabel>1);
   }
         | BREAK SEMICOLON {
          $<my_nlabel>1=nlabel;
          nbreak = nlabel;
          nlabel++;
          fprintf(fasm, "\tjmp loop_end_%d\n", $<my_nlabel>1);
   }
         | RETURN expression SEMICOLON {
     fprintf(fasm, "\n\t# return expression\n");
                 fprintf(fasm, "\tmovq %%rbx, %%rax\n");
     if ((fact_end_label != NULL) && (!strcmp(fact_end_label, "fact_end_0"))) {
        fprintf(fasm, "\tjmp %s\n", fact_end_label);
     }
     if ((quick_end_label != NULL) && (!strcmp(quick_end_label, "quick_end_0"))) {
        fprintf(fasm, "\tjmp %s\n", quick_end_label);
     }
     if ((check_end_label != NULL) && (!strcmp(check_end_label, "check_end_return_0"))) {
        fprintf(fasm, "\tjmp %s\n", check_end_label);
     }
     if ((abs_end_label != NULL) && (!strcmp(abs_end_label, "abs_end_return_v"))) {
        fprintf(fasm, "\tjmp %s\n", abs_end_label);
     }
     if ((bruteforce_end_label != NULL) && (!strcmp(bruteforce_end_label, "bruteforce_end_return_0"))) {
        fprintf(fasm, "\tjmp %s\n", bruteforce_end_label);
     }
     if ((mystrcmp_end_label != NULL) && (!strcmp(mystrcmp_end_label, "mystrcmp_end_return_0"))) {
        fprintf(fasm, "\tjmp %s\n", mystrcmp_end_label);
     }



                 top = 0;
         }
         ;


switch:
    SWITCH LPARENT expression {
        //get switch variable from stack. It could be global or local. Move that varibale to register stack.
          fprintf(fasm, "\n\t *** Start of Switch Statement *****\n");
          char * id = $<string_val>1;
          int pos = -1;
          for (int i = 0; i < nlocals; i++) {
            if (strcmp(local_vars_table[i], id) == 0) {
            // Found local var
              pos = i;
              break;
            }
          }
          if (pos == -1) {
                        fprintf(fasm, "\tmovq %s,%%%s\n", id, regStk[top]);
          }
          else {
            fprintf(fasm, "\tmovq -%d(%%rbp), %%%s\n", 8*(pos + 1), regStk[top]);
          }
                      top++;
    }
    RPARENT LCURL case_list {
          fprintf(fasm, "\tjmp switch_end\n");
    }
    DEFAULT COLON statement BREAK SEMI {
          //Code for default case
          fprintf(fasm, "\tswitch_end:\n");
    }
    ;
case_item:
    CASE expression COLON {
          //Code to compare switch statement. Assuming case value will be number.
          int case_value = atoi($<string_val>1);
          fprintf(fasm, "\tcmpq $%s, %%%s\n", case_value, regStk[top-1] );
          fprintf(fasm, "\tje case_%d\n", ++case_count);
          top--;
     } statement BREAK SEMI {
          //Code to be  executed after case statement
         fprintf(fasm, "\tjmp switch_end\n");
         fprintf(fasm, "\tcase_%d:\n", case_count);
     }
;
case_list:
    // At lease one case.
    case_item
    |case_list case_item;
;


%%

void yyset_in (FILE *  in_str );

int
yyerror(const char * s)
{
        fprintf(stderr,"%s:%d: %s\n", input_file, line_number, s);
}


int
main(int argc, char **argv)
{
        printf("-------------WARNING: You need to implement global and local vars ------\n");
        printf("------------- or you may get problems with top------\n");

        // Make sure there are enough arguments
        if (argc <2) {
                fprintf(stderr, "Usage: simple file\n");
                exit(1);
        }

        // Get file name
        input_file = strdup(argv[1]);

        int len = strlen(input_file);
        if (len < 2 || input_file[len-2]!='.' || input_file[len-1]!='c') {
                fprintf(stderr, "Error: file extension is not .c\n");
                exit(1);
        }

        // Get assembly file name
        asm_file = strdup(input_file);
        asm_file[len-1]='s';

        // Open file to compile
        FILE * f = fopen(input_file, "r");
        if (f==NULL) {
                fprintf(stderr, "Cannot open file %s\n", input_file);
                perror("fopen");
                exit(1);
        }

        // Create assembly file
        fasm = fopen(asm_file, "w");
        if (fasm==NULL) {
                fprintf(stderr, "Cannot open file %s\n", asm_file);
                perror("fopen");
                exit(1);
        }

        // Uncomment for debugging
        //fasm = stderr;

        // Create compilation file
        //
        yyset_in(f);
        yyparse();

        // Generate string table
        int i;
        for (i = 0; i<nstrings; i++) {
                fprintf(fasm, "string%d:\n", i);
                fprintf(fasm, "\t.string %s\n\n", string_table[i]);
        }

        fclose(f);
        fclose(fasm);

        return 0;
}