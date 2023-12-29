        .data
        .comm g,8
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

        # push string "Hello world" top=0
        movq $string0, %rbx
        movq %rbx, -8(%rbp)
        #top=0

        # push string "h[6]=%c\n" top=0
        movq $string1, %rbx

        # push 6
        movq $6,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -8(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "This is a great class!!" top=0
        movq $string2, %rbx
        movq %rbx, g
        #top=0

        # push string "g[11]=%c\n" top=0
        movq $string3, %rbx

        # push 11
        movq $11,%r10

        # global array element re-assignment (a = b[4])
        imulq $1, %r10
        addq g, %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

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
        .string "Hello world"

string1:
        .string "h[6]=%c\n"

string2:
        .string "This is a great class!!"

string3:
        .string "g[11]=%c\n"