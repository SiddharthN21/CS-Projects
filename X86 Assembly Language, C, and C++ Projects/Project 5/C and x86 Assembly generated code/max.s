        .text
.globl max
max:
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

        # push 0
        movq $0,%rbx

        # array element re-assignment (a = b[4])
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq (%rbx), %rbx

        # end of array element re-assignment (a = b[4])
        movq %rbx, -32(%rbp)

        # push 0
        movq $0,%rbx
        movq %rbx, -24(%rbp)
for_start_0:
        movq -24(%rbp), %rbx
        movq -16(%rbp), %r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je for_end_0
        jmp for_details_0
for_increment_0:
        movq -24(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -24(%rbp)
        jmp for_start_0
for_details_0:
        movq -32(%rbp), %rbx
        movq -24(%rbp), %r10

        # array element re-assignment (a = b[4])
        imulq $8, %r10
        addq -8(%rbp), %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_1
        movq -24(%rbp), %rbx

        # array element re-assignment (a = b[4])
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq (%rbx), %rbx

        # end of array element re-assignment (a = b[4])
        movq %rbx, -32(%rbp)
        jmp if_end_1
else_true_1:
if_end_1:
        jmp for_increment_0
for_end_0:
        movq -32(%rbp), %rbx

        # return expression
        movq %rbx, %rax
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
        .data
        .comm a,8
        .data
        .comm n,8
        .data
        .comm i,8
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

        # push 10
        movq $10,%rbx
        movq %rbx, n

        # push 10
        movq $10,%rbx

        # push 8
        movq $8,%r10

        # *
        imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call malloc
        movq %rax, %rbx
        movq %rbx, a

        # push 0
        movq $0,%rbx

        # push 8
        movq $8,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 1
        movq $1,%rbx

        # push 7
        movq $7,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 2
        movq $2,%rbx

        # push 1
        movq $1,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 3
        movq $3,%rbx

        # push 9
        movq $9,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 4
        movq $4,%rbx

        # push 11
        movq $11,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 5
        movq $5,%rbx

        # push 83
        movq $83,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 6
        movq $6,%rbx

        # push 7
        movq $7,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 7
        movq $7,%rbx

        # push 13
        movq $13,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 8
        movq $8,%rbx

        # push 94
        movq $94,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 9
        movq $9,%rbx

        # push 1
        movq $1,%r10

        # global array element assignment (b[4] = a)
        imulq $8, %rbx
        addq a, %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 0
        movq $0,%rbx
        movq %rbx, i
for_start_2:
        movq i,%rbx
        movq n,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je for_end_2
        jmp for_details_2
for_increment_2:
        movq i,%rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
        jmp for_start_2
for_details_2:
        #top=0

        # push string "%d: %d\n" top=0
        movq $string0, %rbx
        movq i,%r10
        movq i,%r13

        # global array element re-assignment (a = b[4])
        imulq $8, %r13
        addq a, %r13
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
        jmp for_increment_2
for_end_2:
        #top=0

        # push string "n=%d\n" top=0
        movq $string1, %rbx
        movq n,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "max=%d\n" top=0
        movq $string2, %rbx
        movq a,%r10
        movq n,%r13
     # func=max nargs=2
     # Move values from reg stack to reg args
        movq %r13, %rsi
        movq %r10, %rdi
        call max
        movq %rax, %r10
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
string0:
        .string "%d: %d\n"

string1:
        .string "n=%d\n"

string2:
        .string "max=%d\n"