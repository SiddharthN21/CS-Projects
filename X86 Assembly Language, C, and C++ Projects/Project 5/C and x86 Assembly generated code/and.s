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

        # push string "0 && 0 = %d\n" top=0
        movq $string0, %rbx

        # push 0
        movq $0,%r10

        # push 0
        movq $0,%r13

        # &&
        testq %r10, %r13
        je and_false_0
        movq $1, %r10
        jmp and_end_0
and_false_0:
        movq $0, %r10
and_end_0:
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "0 && 1 = %d\n" top=0
        movq $string1, %rbx

        # push 0
        movq $0,%r10

        # push 1
        movq $1,%r13

        # &&
        testq %r10, %r13
        je and_false_1
        movq $1, %r10
        jmp and_end_1
and_false_1:
        movq $0, %r10
and_end_1:
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "1 && 0 = %d\n" top=0
        movq $string2, %rbx

        # push 1
        movq $1,%r10

        # push 0
        movq $0,%r13

        # &&
        testq %r10, %r13
        je and_false_2
        movq $1, %r10
        jmp and_end_2
and_false_2:
        movq $0, %r10
and_end_2:
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "1 && 1 = %d\n" top=0
        movq $string3, %rbx

        # push 1
        movq $1,%r10

        # push 1
        movq $1,%r13

        # &&
        testq %r10, %r13
        je and_false_3
        movq $1, %r10
        jmp and_end_3
and_false_3:
        movq $0, %r10
and_end_3:
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
        .string "0 && 0 = %d\n"

string1:
        .string "0 && 1 = %d\n"

string2:
        .string "1 && 0 = %d\n"

string3:
        .string "1 && 1 = %d\n"