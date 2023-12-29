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

        # push 0
        movq $0,%rbx
        movq %rbx, i
while_start_0:
        movq i,%rbx

        # push 9
        movq $9,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je while_end_0
        #top=0

        # push string "i=%ld\n" top=0
        movq $string0, %rbx
        movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        movq i,%rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
        jmp while_start_0
while_end_0:
        #top=0

        # push string "OK\n" top=0
        movq $string1, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
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
        .string "i=%ld\n"

string1:
        .string "OK\n"
