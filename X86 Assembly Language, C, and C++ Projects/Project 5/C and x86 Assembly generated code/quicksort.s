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

        # push string "%d\n" top=0
        movq $string0, %rbx
        movq -32(%rbp), %r10

        # array element re-assignment (a = b[4])
        imulq $8, %r10
        addq -8(%rbp), %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])
     # func=printf nargs=2
     # Move values from reg stack to reg args
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
        movq -24(%rbp), %rbx
        movq -16(%rbp), %r10

        # -
        subq %r10,%rbx

        # push 1
        movq $1,%r10

        # <=
        movq $0, %rax
        cmpq %r10, %rbx
        setle %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_1

        # push 0
        movq $0,%rbx

        # return expression
        movq %rbx, %rax
        jmp quick_end_0
        jmp if_end_1
else_true_1:
if_end_1:
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
while_start_2:
        movq -40(%rbp), %rbx
        movq -48(%rbp), %r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je while_end_2
while_start_3:
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

        # <
        movq $0, %rax
        cmpq %r13, %r10
        setl %al
        movq %rax, %r10
        movq %r10, %r13

        # &&
        testq %rbx, %r10
        je and_false_4
        movq $1, %rbx
        jmp and_end_4
and_false_4:
        movq $0, %rbx
and_end_4:
        cmpq $0, %rbx
        je while_end_3
        movq -40(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -40(%rbp)
        jmp while_start_3
while_end_3:
while_start_5:
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

        # >
        movq $0, %rax
        cmpq %r13, %r10
        setg %al
        movq %rax, %r10
        movq %r10, %r13

        # &&
        testq %rbx, %r10
        je and_false_6
        movq $1, %rbx
        jmp and_end_6
and_false_6:
        movq $0, %rbx
and_end_6:
        cmpq $0, %rbx
        je while_end_5
        movq -48(%rbp), %rbx

        # push 1
        movq $1,%r10

        # -
        subq %r10,%rbx
        movq %rbx, -48(%rbp)
        jmp while_start_5
while_end_5:
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
        je else_true_7
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
        jmp if_end_7
else_true_7:
if_end_7:
        jmp while_start_2
while_end_2:
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
        #top=0

        # push string "Hello" top=0
        movq $string2, %rbx
     # func=print nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call print
        movq %rax, %rbx

        # push 10
        movq $10,%rbx
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

        # push 8
        movq $8,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 1
        movq $1,%rbx

        # push 7
        movq $7,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 2
        movq $2,%rbx

        # push 1
        movq $1,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 3
        movq $3,%rbx

        # push 9
        movq $9,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 4
        movq $4,%rbx

        # push 11
        movq $11,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 5
        movq $5,%rbx

        # push 83
        movq $83,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 6
        movq $6,%rbx

        # push 7
        movq $7,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 7
        movq $7,%rbx

        # push 13
        movq $13,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 8
        movq $8,%rbx

        # push 94
        movq $94,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 9
        movq $9,%rbx

        # push 1
        movq $1,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -16(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        #top=0

        # push string "-------- Before -------\n" top=0
        movq $string3, %rbx
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
        movq $string4, %rbx
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
        .string "%d\n"

string1:
        .string "==%s==\n"

string2:
        .string "Hello"

string3:
        .string "-------- Before -------\n"

string4:
        .string "-------- After -------\n"
