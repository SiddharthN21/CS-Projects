/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    WORD = 258,                    /* WORD  */
    NOTOKEN = 259,                 /* NOTOKEN  */
    LPARENT = 260,                 /* LPARENT  */
    RPARENT = 261,                 /* RPARENT  */
    LBRACE = 262,                  /* LBRACE  */
    RBRACE = 263,                  /* RBRACE  */
    LCURLY = 264,                  /* LCURLY  */
    RCURLY = 265,                  /* RCURLY  */
    COMA = 266,                    /* COMA  */
    SEMICOLON = 267,               /* SEMICOLON  */
    EQUAL = 268,                   /* EQUAL  */
    STRING_CONST = 269,            /* STRING_CONST  */
    LONG = 270,                    /* LONG  */
    LONGSTAR = 271,                /* LONGSTAR  */
    VOID = 272,                    /* VOID  */
    CHARSTAR = 273,                /* CHARSTAR  */
    CHARSTARSTAR = 274,            /* CHARSTARSTAR  */
    INTEGER_CONST = 275,           /* INTEGER_CONST  */
    AMPERSAND = 276,               /* AMPERSAND  */
    OROR = 277,                    /* OROR  */
    ANDAND = 278,                  /* ANDAND  */
    EQUALEQUAL = 279,              /* EQUALEQUAL  */
    NOTEQUAL = 280,                /* NOTEQUAL  */
    LESS = 281,                    /* LESS  */
    GREAT = 282,                   /* GREAT  */
    LESSEQUAL = 283,               /* LESSEQUAL  */
    GREATEQUAL = 284,              /* GREATEQUAL  */
    PLUS = 285,                    /* PLUS  */
    MINUS = 286,                   /* MINUS  */
    TIMES = 287,                   /* TIMES  */
    DIVIDE = 288,                  /* DIVIDE  */
    PERCENT = 289,                 /* PERCENT  */
    IF = 290,                      /* IF  */
    ELSE = 291,                    /* ELSE  */
    WHILE = 292,                   /* WHILE  */
    DO = 293,                      /* DO  */
    FOR = 294,                     /* FOR  */
    CONTINUE = 295,                /* CONTINUE  */
    BREAK = 296,                   /* BREAK  */
    RETURN = 297,                  /* RETURN  */
    SWITCH = 298,                  /* SWITCH  */
    CASE = 299,                    /* CASE  */
    DEFAULT = 300,                 /* DEFAULT  */
    COLON = 301                    /* COLON  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif
/* Token kinds.  */
#define YYEMPTY -2
#define YYEOF 0
#define YYerror 256
#define YYUNDEF 257
#define WORD 258
#define NOTOKEN 259
#define LPARENT 260
#define RPARENT 261
#define LBRACE 262
#define RBRACE 263
#define LCURLY 264
#define RCURLY 265
#define COMA 266
#define SEMICOLON 267
#define EQUAL 268
#define STRING_CONST 269
#define LONG 270
#define LONGSTAR 271
#define VOID 272
#define CHARSTAR 273
#define CHARSTARSTAR 274
#define INTEGER_CONST 275
#define AMPERSAND 276
#define OROR 277
#define ANDAND 278
#define EQUALEQUAL 279
#define NOTEQUAL 280
#define LESS 281
#define GREAT 282
#define LESSEQUAL 283
#define GREATEQUAL 284
#define PLUS 285
#define MINUS 286
#define TIMES 287
#define DIVIDE 288
#define PERCENT 289
#define IF 290
#define ELSE 291
#define WHILE 292
#define DO 293
#define FOR 294
#define CONTINUE 295
#define BREAK 296
#define RETURN 297
#define SWITCH 298
#define CASE 299
#define DEFAULT 300
#define COLON 301

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
union YYSTYPE
{
#line 15 "simple.y"

                char   *string_val;
                int nargs;
                int my_nlabel;


#line 166 "y.tab.h"

};
typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;


int yyparse (void);


#endif /* !YY_YY_Y_TAB_H_INCLUDED  */