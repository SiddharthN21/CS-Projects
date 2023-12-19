
/* Siddharth Nadgaundi, hw3.c, CS 24000, Spring 2023
 * Last updated February 15, 2023
 */

/* Add any includes here */
#include "hw4.h"

#include <math.h>

/*
 * This function adds the corresponding fields of the two complex numbers
 * passed as arguments and returns a complex number representing their
 * sum.
 */

complex_t add_complex(complex_t comp_a, complex_t comp_b) {
  complex_t ret_complex = {0.0, 0.0};

  ret_complex.x = (comp_a.x + comp_b.x);
  ret_complex.y = (comp_a.y + comp_b.y);

  return ret_complex;
} /* add_complex() */

/*
 * This function returns the negative of the corresponding components of the
 * passed complex number.
 */

complex_t neg_complex(complex_t comp_a) {
  complex_t ret_complex = {0.0, 0.0};

  ret_complex.x = (comp_a.x * -1.0);
  ret_complex.y = (comp_a.y * -1.0);

  return ret_complex;
} /* neg_complex() */

/*
 * This function is used to compute the difference of two complex numbers by
 * calling the add_complex() function and passing comp_a as the first
 * parameter and and negative of comp_b as the second parameter.
 */

complex_t sub_complex(complex_t comp_a, complex_t comp_b) {
  complex_t ret_complex = {0.0, 0.0};
  complex_t temp = {0.0, 0.0};

  temp = neg_complex(comp_b);

  ret_complex = add_complex(comp_a, temp);

  return ret_complex;
} /* sub_complex() */

/*
 * This function is used to return the dot product of two complex numbers
 * passed in as parameters. The dot product is computed by performing the
 * calculations provided in the documentation.
 */

double dot_complex(complex_t comp_a, complex_t comp_b) {
  double ret_dotproduct = 0.0;

  ret_dotproduct = ((comp_a.x * comp_b.x) + (comp_a.y * comp_b.y));

  return ret_dotproduct;
} /* dot_complex() */

/*
 * This function returns the reciprocal of the argument passed by performing
 * the necessary calculations on the x and y components respectively.
 */

complex_t inv_complex(complex_t comp_a) {
  complex_t ret_complex = {0.0, 0.0};
  double comp_magnitude = 0.0;

  comp_magnitude = ((comp_a.x * comp_a.x) + (comp_a.y * comp_a.y));
  ret_complex.x = comp_a.x / comp_magnitude;
  ret_complex.y = (-1.0 * (comp_a.y / comp_magnitude));

  return ret_complex;
} /* inv_complex() */

/*
 * This function is used to return the product of two complex numbers by
 * performing the necessary calculations through the formula provided in the
 * documentation.
 */

complex_t mul_complex(complex_t comp_a, complex_t comp_b) {
  complex_t ret_complex = {0.0, 0.0};

  ret_complex.x = ((comp_a.x * comp_b.x) - (comp_a.y * comp_b.y));
  ret_complex.y = ((comp_a.x * comp_b.y) + (comp_a.y * comp_b.x));

  return ret_complex;
} /* mul_complex() */

/*
 * This function returns the division of the first argument by the second by
 * calling mul_complex() with comp_a as argument one and the inverse of comp_b
 * as argument two.
 */

complex_t div_complex(complex_t comp_a, complex_t comp_b) {
  complex_t ret_complex = {0.0, 0.0};
  complex_t temp = {0.0, 0.0};

  temp = inv_complex(comp_b);
  ret_complex = mul_complex(comp_a, temp);

  return ret_complex;
} /* div_complex() */

/*
 * This function returns the exponential function of the provided complex
 * number by performing the calculations provided in the documentation and
 * utilising functions from the math library of C.
 */

complex_t exp_complex(complex_t comp_a) {
  complex_t ret_complex = {0.0, 0.0};
  double e_xvalue = 0.0;
  double e_yvalue = 0.0;

  e_xvalue = (exp(comp_a.x) * cos(comp_a.y));
  e_yvalue = (exp(comp_a.x) * sin(comp_a.y));

  ret_complex.x = e_xvalue;
  ret_complex.y = e_yvalue;

  return ret_complex;
} /* exp_complex() */

/*
 * This function returns the number of calculations it took for Z to reach a
 * magnitude greater or equal to 2. A while loop is used to ensure that the
 * calculations take place while Z is less than 4 and while the number of
 * iterations are less than the maximum provided value.
 */

int mandelbrot(complex_t comp_a) {
  double z_mandelbrot = 0;
  double temp_real = 0.0;
  double temp_img = 0.0;
  int iterations = 0;

  temp_real = comp_a.x;
  temp_img = comp_a.y;

  while ((z_mandelbrot < 4.0) && (iterations < MAX_MANDELBROT)) {
    z_mandelbrot = dot_complex(comp_a, comp_a);
    comp_a = mul_complex(comp_a, comp_a);
    comp_a.x = comp_a.x + temp_real;
    comp_a.y = comp_a.y + temp_img;
    iterations++;
  }

  return iterations;
} /* mandelbrot() */