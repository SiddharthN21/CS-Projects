        .text
.globl printArray
printArray:
        # Save Frame pointer
        pushq %rbp
        movq %rsp,%rbp
        subq $256, %rsp
# Save registers.
# Push one extra to align stack to 16bytes
        pushq %rbx
        pushq %rbx
        pushq %r10
        pushq %r13
        pushq %r14
        pushq %r15
        #Storing arguments on stack
        movq %rdi, -8(%rbp)
        movq %rsi, -16(%rbp)
        movq %rdx, -24(%rbp)
        movq -16(%rbp), %rbx
        movq %rbx, -32(%rbp)
for_start_0:
        movq -32(%rbp), %rbx
        movq -24(%rbp), %r10

        # <=
        movq $0, %rax
        cmpq %r10, %rbx
        setle %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je for_end_0
        jmp for_details_0
for_increment_0:
        movq -32(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -32(%rbp)
        jmp for_start_0
for_details_0:
        #top=0

        # push string "%d: %s\n" top=0
        movq $string0, %rbx
        movq -32(%rbp), %r10
        movq -32(%rbp), %r13

        # array element re-assignment (a = b[4])
        imulq $8, %r13
        addq -8(%rbp), %r13
        movq (%r13), %r13

        # end of array element re-assignment (a = b[4])
     # func=printf nargs=3
     # Move values from reg stack to reg args
        movq %r13, %rdx
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        jmp for_increment_0
for_end_0:
# Restore registers
        popq %r15
        popq %r14
        popq %r13
        popq %r10
        popq %rbx
        popq %rbx
        addq $256, %rsp
        leave
        ret
        .text
.globl print
print:
        # Save Frame pointer
        pushq %rbp
        movq %rsp,%rbp
        subq $256, %rsp
# Save registers.
# Push one extra to align stack to 16bytes
        pushq %rbx
        pushq %rbx
        pushq %r10
        pushq %r13
        pushq %r14
        pushq %r15
        #Storing arguments on stack
        movq %rdi, -8(%rbp)
        #top=0

        # push string "==%s==\n" top=0
        movq $string1, %rbx
        movq -8(%rbp), %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
# Restore registers
        popq %r15
        popq %r14
        popq %r13
        popq %r10
        popq %rbx
        popq %rbx
        addq $256, %rsp
        leave
        ret
        .text
.globl mystrcmp
mystrcmp:
        # Save Frame pointer
        pushq %rbp
        movq %rsp,%rbp
        subq $256, %rsp
# Save registers.
# Push one extra to align stack to 16bytes
        pushq %rbx
        pushq %rbx
        pushq %r10
        pushq %r13
        pushq %r14
        pushq %r15
        #Storing arguments on stack
        movq %rdi, -8(%rbp)
        movq %rsi, -16(%rbp)
while_start_1:

        # push 0
        movq $0,%rbx

        # array element re-assignment (a = b[4])
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb (%rbx), %al
        movq %rax, %rbx

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -16(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])

        # &&
        testq %rbx, %r10
        je and_false_2
        movq $1, %rbx
        jmp and_end_2
and_false_2:
        movq $0, %rbx
and_end_2:

        # push 0
        movq $0,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -8(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r13

        # array element re-assignment (a = b[4])
        imulq $1, %r13
        addq -16(%rbp), %r13
        movq $0, %rax
        movb (%r13), %al
        movq %rax, %r13

        # end of array element re-assignment (a = b[4])

        # ==
        cmpq %r10, %r13
        je equal_true_3
        movq $0, %r10
        jmp equal_end_3
equal_true_3:
        movq $1, %r10
equal_end_3:

        # &&
        testq %rbx, %r10
        je and_false_4
        movq $1, %rbx
        jmp and_end_4
and_false_4:
        movq $0, %rbx
and_end_4:
        cmpq $0, %rbx
        je while_end_1
        movq -8(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -8(%rbp)
        movq -16(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -16(%rbp)
        jmp while_start_1
while_end_1:

        # push 0
        movq $0,%rbx

        # array element re-assignment (a = b[4])
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb (%rbx), %al
        movq %rax, %rbx

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r10

        # ==
        cmpq %rbx, %r10
        je equal_true_5
        movq $0, %rbx
        jmp equal_end_5
equal_true_5:
        movq $1, %rbx
equal_end_5:

        # push 0
        movq $0,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -16(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r13

        # ==
        cmpq %r10, %r13
        je equal_true_6
        movq $0, %r10
        jmp equal_end_6
equal_true_6:
        movq $1, %r10
equal_end_6:

        # &&
        testq %rbx, %r10
        je and_false_7
        movq $1, %rbx
        jmp and_end_7
and_false_7:
        movq $0, %rbx
and_end_7:

        # if
        cmpq $0, %rbx
        je else_true_8

        # push 0
        movq $0,%rbx

        # return expression
        movq %rbx, %rax
        jmp mystrcmp_end_return_0
        jmp if_end_8
else_true_8:
if_end_8:

        # push 0
        movq $0,%rbx

        # array element re-assignment (a = b[4])
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb (%rbx), %al
        movq %rax, %rbx

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r10

        # ==
        cmpq %rbx, %r10
        je equal_true_9
        movq $0, %rbx
        jmp equal_end_9
equal_true_9:
        movq $1, %rbx
equal_end_9:

        # push 0
        movq $0,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -16(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r13

        # ==
        cmpq %r10, %r13
        jne nequal_true_10
        movq $0, %r10
        jmp nequal_end_10
nequal_true_10:
        movq $1, %r10
nequal_end_10:

        # &&
        testq %rbx, %r10
        je and_false_11
        movq $1, %rbx
        jmp and_end_11
and_false_11:
        movq $0, %rbx
and_end_11:

        # if
        cmpq $0, %rbx
        je else_true_12

        # push -1
        movq $-1,%rbx

        # return expression
        movq %rbx, %rax
        jmp mystrcmp_end_return_0
        jmp if_end_12
else_true_12:
if_end_12:

        # push 0
        movq $0,%rbx

        # array element re-assignment (a = b[4])
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb (%rbx), %al
        movq %rax, %rbx

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r10

        # ==
        cmpq %rbx, %r10
        jne nequal_true_13
        movq $0, %rbx
        jmp nequal_end_13
nequal_true_13:
        movq $1, %rbx
nequal_end_13:

        # push 0
        movq $0,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -16(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r13

        # ==
        cmpq %r10, %r13
        je equal_true_14
        movq $0, %r10
        jmp equal_end_14
equal_true_14:
        movq $1, %r10
equal_end_14:

        # &&
        testq %rbx, %r10
        je and_false_15
        movq $1, %rbx
        jmp and_end_15
and_false_15:
        movq $0, %rbx
and_end_15:

        # if
        cmpq $0, %rbx
        je else_true_16

        # push 1
        movq $1,%rbx

        # return expression
        movq %rbx, %rax
        jmp mystrcmp_end_return_0
        jmp if_end_16
else_true_16:
if_end_16:

        # push 0
        movq $0,%rbx

        # array element re-assignment (a = b[4])
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb (%rbx), %al
        movq %rax, %rbx

        # end of array element re-assignment (a = b[4])

        # push 0
        movq $0,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -16(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])

        # >
        movq $0, %rax
        cmpq %r10, %rbx
        setg %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_17

        # push 1
        movq $1,%rbx

        # return expression
        movq %rbx, %rax
        jmp mystrcmp_end_return_0
        jmp if_end_17
else_true_17:
if_end_17:

        # push -1
        movq $-1,%rbx

        # return expression
        movq %rbx, %rax
        jmp mystrcmp_end_return_0
        mystrcmp_end_return_0:
# Restore registers
        popq %r15
        popq %r14
        popq %r13
        popq %r10
        popq %rbx
        popq %rbx
        addq $256, %rsp
        leave
        ret
        .text
.globl quicksortsubrange
quicksortsubrange:
        # Save Frame pointer
        pushq %rbp
        movq %rsp,%rbp
        subq $256, %rsp
# Save registers.
# Push one extra to align stack to 16bytes
        pushq %rbx
        pushq %rbx
        pushq %r10
        pushq %r13
        pushq %r14
        pushq %r15
        #Storing arguments on stack
        movq %rdi, -8(%rbp)
        movq %rsi, -16(%rbp)
        movq %rdx, -24(%rbp)
        movq -16(%rbp), %rbx
        movq -24(%rbp), %r10

        # >=
        movq $0, %rax
        cmpq %r10, %rbx
        setge %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_18

        # push 0
        movq $0,%rbx

        # return expression
        movq %rbx, %rax
        jmp quick_end_0
        jmp if_end_18
else_true_18:
if_end_18:
        movq -24(%rbp), %rbx

        # array element re-assignment (a = b[4])
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq (%rbx), %rbx

        # end of array element re-assignment (a = b[4])
        movq %rbx, -32(%rbp)
        movq -16(%rbp), %rbx
        movq %rbx, -40(%rbp)
        movq -24(%rbp), %rbx

        # push 1
        movq $1,%r10

        # -
        subq %r10,%rbx
        movq %rbx, -48(%rbp)
while_start_19:
        movq -40(%rbp), %rbx
        movq -48(%rbp), %r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je while_end_19
while_start_20:
        movq -40(%rbp), %rbx
        movq -48(%rbp), %r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        movq -40(%rbp), %r10

        # array element re-assignment (a = b[4])
        imulq $8, %r10
        addq -8(%rbp), %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])
        movq -32(%rbp), %r13
     # func=mystrcmp nargs=2
     # Move values from reg stack to reg args
        movq %r13, %rsi
        movq %r10, %rdi
        call mystrcmp
        movq %rax, %r10

        # push 0
        movq $0,%r13

        # <
        movq $0, %rax
        cmpq %r13, %r10
        setl %al
        movq %rax, %r10
        movq %r10, %r13

        # &&
        testq %rbx, %r10
        je and_false_21
        movq $1, %rbx
        jmp and_end_21
and_false_21:
        movq $0, %rbx
and_end_21:
        cmpq $0, %rbx
        je while_end_20
        movq -40(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -40(%rbp)
        jmp while_start_20
while_end_20:
while_start_22:
        movq -40(%rbp), %rbx
        movq -48(%rbp), %r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        movq -48(%rbp), %r10

        # array element re-assignment (a = b[4])
        imulq $8, %r10
        addq -8(%rbp), %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])
        movq -32(%rbp), %r13
     # func=mystrcmp nargs=2
     # Move values from reg stack to reg args
        movq %r13, %rsi
        movq %r10, %rdi
        call mystrcmp
        movq %rax, %r10

        # push 0
        movq $0,%r13

        # >=
        movq $0, %rax
        cmpq %r13, %r10
        setge %al
        movq %rax, %r10
        movq %r10, %r13

        # &&
        testq %rbx, %r10
        je and_false_23
        movq $1, %rbx
        jmp and_end_23
and_false_23:
        movq $0, %rbx
and_end_23:
        cmpq $0, %rbx
        je while_end_22
        movq -48(%rbp), %rbx

        # push 1
        movq $1,%r10

        # -
        subq %r10,%rbx
        movq %rbx, -48(%rbp)
        jmp while_start_22
while_end_22:
        movq -40(%rbp), %rbx
        movq -48(%rbp), %r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_24
        movq -40(%rbp), %rbx

        # array element re-assignment (a = b[4])
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq (%rbx), %rbx

        # end of array element re-assignment (a = b[4])
        movq %rbx, -56(%rbp)
        movq -40(%rbp), %rbx
        movq -48(%rbp), %r10

        # array element re-assignment (a = b[4])
        imulq $8, %r10
        addq -8(%rbp), %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        movq -48(%rbp), %rbx
        movq -56(%rbp), %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        jmp if_end_24
else_true_24:
if_end_24:
        jmp while_start_19
while_end_19:
        movq -24(%rbp), %rbx
        movq -40(%rbp), %r10

        # array element re-assignment (a = b[4])
        imulq $8, %r10
        addq -8(%rbp), %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        movq -40(%rbp), %rbx
        movq -32(%rbp), %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        movq -8(%rbp), %rbx
        movq -16(%rbp), %r10
        movq -40(%rbp), %r13

        # push 1
        movq $1,%r14

        # -
        subq %r14,%r13
     # func=quicksortsubrange nargs=3
     # Move values from reg stack to reg args
        movq %r13, %rdx
        movq %r10, %rsi
        movq %rbx, %rdi
        call quicksortsubrange
        movq %rax, %rbx
        movq -8(%rbp), %rbx
        movq -48(%rbp), %r10

        # push 1
        movq $1,%r13

        # +
        addq %r13,%r10
        movq -24(%rbp), %r13
     # func=quicksortsubrange nargs=3
     # Move values from reg stack to reg args
        movq %r13, %rdx
        movq %r10, %rsi
        movq %rbx, %rdi
        call quicksortsubrange
        movq %rax, %rbx
        quick_end_0:
# Restore registers
        popq %r15
        popq %r14
        popq %r13
        popq %r10
        popq %rbx
        popq %rbx
        addq $256, %rsp
        leave
        ret
        .text
.globl quicksort
quicksort:
        # Save Frame pointer
        pushq %rbp
        movq %rsp,%rbp
        subq $256, %rsp
# Save registers.
# Push one extra to align stack to 16bytes
        pushq %rbx
        pushq %rbx
        pushq %r10
        pushq %r13
        pushq %r14
        pushq %r15
        #Storing arguments on stack
        movq %rdi, -8(%rbp)
        movq %rsi, -16(%rbp)
        movq -8(%rbp), %rbx

        # push 0
        movq $0,%r10
        movq -16(%rbp), %r13

        # push 1
        movq $1,%r14

        # -
        subq %r14,%r13
     # func=quicksortsubrange nargs=3
     # Move values from reg stack to reg args
        movq %r13, %rdx
        movq %r10, %rsi
        movq %rbx, %rdi
        call quicksortsubrange
        movq %rax, %rbx
# Restore registers
        popq %r15
        popq %r14
        popq %r13
        popq %r10
        popq %rbx
        popq %rbx
        addq $256, %rsp
        leave
        ret
        .text
.globl main
main:
        # Save Frame pointer
        pushq %rbp
        movq %rsp,%rbp
        subq $256, %rsp
# Save registers.
# Push one extra to align stack to 16bytes
        pushq %rbx
        pushq %rbx
        pushq %r10
        pushq %r13
        pushq %r14
        pushq %r15
        #Storing arguments on stack

        # push 6
        movq $6,%rbx
        movq %rbx, -8(%rbp)
        movq -8(%rbp), %rbx

        # push 8
        movq $8,%r10

        # *
        imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call malloc
        movq %rax, %rbx
        movq %rbx, -16(%rbp)

        # push 0
        movq $0,%rbx
        #top=1

        # push string "Rachael" top=1
        movq $string2, %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 1
        movq $1,%rbx
        #top=1

        # push string "Monica" top=1
        movq $string3, %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 2
        movq $2,%rbx
        #top=1

        # push string "Phoebe" top=1
        movq $string4, %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 3
        movq $3,%rbx
        #top=1

        # push string "Joey" top=1
        movq $string5, %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 4
        movq $4,%rbx
        #top=1

        # push string "Ross" top=1
        movq $string6, %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 5
        movq $5,%rbx
        #top=1

        # push string "Chandler" top=1
        movq $string7, %r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        #top=0

        # push string "-------- Before -------\n" top=0
        movq $string8, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        movq -16(%rbp), %rbx

        # push 0
        movq $0,%r10
        movq -8(%rbp), %r13

        # push 1
        movq $1,%r14

        # -
        subq %r14,%r13
     # func=printArray nargs=3
     # Move values from reg stack to reg args
        movq %r13, %rdx
        movq %r10, %rsi
        movq %rbx, %rdi
        call printArray
        movq %rax, %rbx
        movq -16(%rbp), %rbx
        movq -8(%rbp), %r10
     # func=quicksort nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        call quicksort
        movq %rax, %rbx
        #top=0

        # push string "-------- After -------\n" top=0
        movq $string9, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        movq -16(%rbp), %rbx

        # push 0
        movq $0,%r10
        movq -8(%rbp), %r13

        # push 1
        movq $1,%r14

        # -
        subq %r14,%r13
     # func=printArray nargs=3
     # Move values from reg stack to reg args
        movq %r13, %rdx
        movq %r10, %rsi
        movq %rbx, %rdi
        call printArray
        movq %rax, %rbx
# Restore registers
        popq %r15
        popq %r14
        popq %r13
        popq %r10
        popq %rbx
        popq %rbx
        addq $256, %rsp
        leave
        ret
string0:
        .string "%d: %s\n"

string1:
        .string "==%s==\n"

string2:
        .string "Rachael"

string3:
        .string "Monica"

string4:
        .string "Phoebe"

string5:
        .string "Joey"

string6:
        .string "Ross"

string7:
        .string "Chandler"

string8:
        .string "-------- Before -------\n"

string9:
        .string "-------- After -------\n"