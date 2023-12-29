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

        # push 20
        movq $20,%r10

        # *
        imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call malloc
        movq %rax, %rbx
        movq %rbx, -8(%rbp)

        # push 8
        movq $8,%rbx

        # push 9
        movq $9,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        #top=0

        # push string "a[8]=%d\n" top=0
        movq $string0, %rbx

        # push 8
        movq $8,%r10

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
        .string "a[8]=%d\n"