        .data
        .comm queens,8
        .data
        .comm solid,8
        .text
.globl abs
abs:
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
        movq -8(%rbp), %rbx

        # push 0
        movq $0,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_0

        # push -1
        movq $-1,%rbx
        movq -8(%rbp), %r10

        # *
        imulq %r10,%rbx

        # return expression
        movq %rbx, %rax
        jmp abs_end_return_v
        jmp if_end_0
else_true_0:
if_end_0:
        movq -8(%rbp), %rbx

        # return expression
        movq %rbx, %rax
        jmp abs_end_return_v
        abs_end_return_v:
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
.globl check
check:
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

        # push 0
        movq $0,%rbx
        movq %rbx, -16(%rbp)
for_start_1:
        movq -16(%rbp), %rbx
        movq -8(%rbp), %r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je for_end_1
        jmp for_details_1
for_increment_1:
        movq -16(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -16(%rbp)
        jmp for_start_1
for_details_1:
        movq -16(%rbp), %rbx

        # global array element re-assignment (a = b[4])
        imulq $8, %rbx
        addq queens, %rbx
        movq (%rbx), %rbx

        # end of array element re-assignment (a = b[4])
        movq -8(%rbp), %r10

        # global array element re-assignment (a = b[4])
        imulq $8, %r10
        addq queens, %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])

        # ==
        cmpq %rbx, %r10
        je equal_true_2
        movq $0, %rbx
        jmp equal_end_2
equal_true_2:
        movq $1, %rbx
equal_end_2:
        movq -16(%rbp), %r10

        # global array element re-assignment (a = b[4])
        imulq $8, %r10
        addq queens, %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])
        movq -8(%rbp), %r13

        # global array element re-assignment (a = b[4])
        imulq $8, %r13
        addq queens, %r13
        movq (%r13), %r13

        # end of array element re-assignment (a = b[4])

        # -
        subq %r13,%r10
     # func=abs nargs=1
     # Move values from reg stack to reg args
        movq %r10, %rdi
        call abs
        movq %rax, %r10
        movq -8(%rbp), %r13
        movq -16(%rbp), %r14

        # -
        subq %r14,%r13

        # ==
        cmpq %r10, %r13
        je equal_true_3
        movq $0, %r10
        jmp equal_end_3
equal_true_3:
        movq $1, %r10
equal_end_3:

        # ||
        orq %rbx, %r10
        je or_false_4
        movq $1, %rbx
        jmp or_end_4
or_false_4:
        movq $0, %rbx
or_end_4:

        # if
        cmpq $0, %rbx
        je else_true_5

        # push 0
        movq $0,%rbx

        # return expression
        movq %rbx, %rax
        jmp check_end_return_0
        jmp if_end_5
else_true_5:
if_end_5:
        jmp for_increment_1
for_end_1:

        # push 1
        movq $1,%rbx

        # return expression
        movq %rbx, %rax
        jmp check_end_return_0
        check_end_return_0:
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
.globl bruteforce
bruteforce:
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
        movq -8(%rbp), %rbx

        # push 8
        movq $8,%r10

        # ==
        cmpq %rbx, %r10
        je equal_true_6
        movq $0, %rbx
        jmp equal_end_6
equal_true_6:
        movq $1, %rbx
equal_end_6:

        # if
        cmpq $0, %rbx
        je else_true_7
        #top=0

        # push string "Solution #%2ld = [ " top=0
        movq $string0, %rbx
        movq solid,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        movq solid,%rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, solid

        # push 0
        movq $0,%rbx
        movq %rbx, -16(%rbp)
for_start_8:
        movq -16(%rbp), %rbx

        # push 8
        movq $8,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je for_end_8
        jmp for_details_8
for_increment_8:
        movq -16(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -16(%rbp)
        jmp for_start_8
for_details_8:
        #top=0

        # push string "%ld " top=0
        movq $string1, %rbx
        movq -16(%rbp), %r10

        # global array element re-assignment (a = b[4])
        imulq $8, %r10
        addq queens, %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])

        # push 1
        movq $1,%r13

        # +
        addq %r13,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        jmp for_increment_8
for_end_8:
        #top=0

        # push string "]\n" top=0
        movq $string2, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx

        # push 0
        movq $0,%rbx

        # return expression
        movq %rbx, %rax
        jmp bruteforce_end_return_0
        jmp if_end_7
else_true_7:
if_end_7:

        # push 0
        movq $0,%rbx
        movq %rbx, -16(%rbp)
for_start_9:
        movq -16(%rbp), %rbx

        # push 8
        movq $8,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je for_end_9
        jmp for_details_9
for_increment_9:
        movq -16(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -16(%rbp)
        jmp for_start_9
for_details_9:
        movq -8(%rbp), %rbx
        movq -16(%rbp), %r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq queens, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        movq -8(%rbp), %rbx
     # func=check nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call check
        movq %rax, %rbx

        # push 0
        movq $0,%r10

        # ==
        cmpq %rbx, %r10
        jne nequal_true_10
        movq $0, %rbx
        jmp nequal_end_10
nequal_true_10:
        movq $1, %rbx
nequal_end_10:

        # if
        cmpq $0, %rbx
        je else_true_11
        movq -8(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
     # func=bruteforce nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call bruteforce
        movq %rax, %rbx
        jmp if_end_11
else_true_11:
if_end_11:
        jmp for_increment_9
for_end_9:

        # push 0
        movq $0,%rbx

        # return expression
        movq %rbx, %rax
        jmp bruteforce_end_return_0
        bruteforce_end_return_0:
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

        # push 8
        movq $8,%rbx

        # push 8
        movq $8,%r10

        # *
        imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call malloc
        movq %rax, %rbx
        movq %rbx, queens

        # push 1
        movq $1,%rbx
        movq %rbx, solid

        # push 0
        movq $0,%rbx
     # func=bruteforce nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call bruteforce
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
        .string "Solution #%2ld = [ "

string1:
        .string "%ld "

string2:
        .string "]\n"