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

        # push string "88/11=%d\n" top=0
        movq $string0, %rbx

        # push 88
        movq $88,%r10

        # push 11
        movq $11,%r13

        # /
        movq %r10, %rax
        cqo
        idivq %r13
        movq %rax,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "83%11=%d\n" top=0
        movq $string1, %rbx

        # push 83
        movq $83,%r10

        # push 11
        movq $11,%r13

        # %
        movq %r10, %rax
        cqo
        idivq %r13
        movq %rdx,%r10
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
        .string "88/11=%d\n"

string1:
        .string "83%11=%d\n"