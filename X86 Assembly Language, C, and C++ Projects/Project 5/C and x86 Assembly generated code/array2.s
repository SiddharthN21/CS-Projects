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

        # push 0
        movq $0,%rbx
        movq %rbx, -16(%rbp)
for_start_0:
        movq -16(%rbp), %rbx

        # push 20
        movq $20,%r10

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
        movq -16(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -16(%rbp)
        jmp for_start_0
for_details_0:
        movq -16(%rbp), %rbx

        # push 3
        movq $3,%r10
        movq -16(%rbp), %r13

        # *
        imulq %r13,%r10

        # array element assignment (b[4] = a)
        imulq $8, %rbx
        addq -8(%rbp), %rbx
        movq %r10, (%rbx)

        # end of array element assignment (b[4] = a)
        jmp for_increment_0
for_end_0:
        #top=0

        # push string "Ok so far\n" top=0
        movq $string0, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx

        # push 0
        movq $0,%rbx
        movq %rbx, -16(%rbp)
for_start_1:
        movq -16(%rbp), %rbx

        # push 20
        movq $20,%r10

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
        #top=0

        # push string "%d: %d\n" top=0
        movq $string1, %rbx
        movq -16(%rbp), %r10
        movq -16(%rbp), %r13

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
        jmp for_increment_1
for_end_1:
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
        .string "Ok so far\n"

string1:
        .string "%d: %d\n"