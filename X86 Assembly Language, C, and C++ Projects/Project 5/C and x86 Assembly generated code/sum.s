        .text
.globl sum
sum:
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
        movq %rbx, -32(%rbp)

        # push 0
        movq $0,%rbx
        movq %rbx, -24(%rbp)
for_start_0:
        movq -24(%rbp), %rbx
        movq -8(%rbp), %r10

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
        addq -16(%rbp), %r10
        movq (%r10), %r10

        # end of array element re-assignment (a = b[4])

        # +
        addq %r10,%rbx
        movq %rbx, -32(%rbp)
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

        # push 5
        movq $5,%rbx

        # push 8
        movq $8,%r10

        # *
        imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call malloc
        movq %rax, %rbx
        movq %rbx, -8(%rbp)

        # push 0
        movq $0,%rbx

        # push 4
        movq $4,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 1
        movq $1,%rbx

        # push 3
        movq $3,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 2
        movq $2,%rbx

        # push 1
        movq $1,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 3
        movq $3,%rbx

        # push 7
        movq $7,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 4
        movq $4,%rbx

        # push 6
        movq $6,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)

        # push 5
        movq $5,%rbx
        movq -8(%rbp), %r10
     # func=sum nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        call sum
        movq %rax, %rbx
        movq %rbx, -16(%rbp)
        #top=0

        # push string "sum=%d\n" top=0
        movq $string0, %rbx
        movq -16(%rbp), %r10
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
        .string "sum=%d\n"