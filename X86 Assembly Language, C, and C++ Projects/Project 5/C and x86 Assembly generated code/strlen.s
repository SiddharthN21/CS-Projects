        .text
.globl mystrlen
mystrlen:
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
while_start_0:
        movq -16(%rbp), %rbx

        # array element re-assignment (a = b[4])
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb (%rbx), %al
        movq %rax, %rbx

        # end of array element re-assignment (a = b[4])
        cmpq $0, %rbx
        je while_end_0
        movq -16(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -16(%rbp)
        jmp while_start_0
while_end_0:
        movq -16(%rbp), %rbx

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
.globl mystrcpy
mystrcpy:
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
while_start_1:

        # push 0
        movq $0,%rbx

        # array element re-assignment (a = b[4])
        imulq $1, %rbx
        addq -16(%rbp), %rbx
        movq $0, %rax
        movb (%rbx), %al
        movq %rax, %rbx

        # end of array element re-assignment (a = b[4])
        cmpq $0, %rbx
        je while_end_1

        # push 0
        movq $0,%rbx

        # push 0
        movq $0,%r10

        # array element re-assignment (a = b[4])
        imulq $1, %r10
        addq -16(%rbp), %r10
        movq $0, %rax
        movb (%r10), %al
        movq %rax, %r10

        # end of array element re-assignment (a = b[4])

        # array element assignment (b[4] = a)
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb %r10b, %al
        movb %al, (%rbx)

        # end of array element assignment (b[4] = a)
        movq -8(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -8(%rbp)
        movq -16(%rbp), %rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, -16(%rbp)
        jmp while_start_1
while_end_1:

        # push 0
        movq $0,%rbx

        # push 0
        movq $0,%r10

        # array element assignment (b[4] = a)
        imulq $1, %rbx
        addq -8(%rbp), %rbx
        movq $0, %rax
        movb %r10b, %al
        movb %al, (%rbx)

        # end of array element assignment (b[4] = a)
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

        # push 30
        movq $30,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call malloc
        movq %rax, %rbx
        movq %rbx, -8(%rbp)
        movq -8(%rbp), %rbx
        #top=1

        # push string "Hello world" top=1
        movq $string0, %r10
     # func=mystrcpy nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        call mystrcpy
        movq %rax, %rbx
        #top=0

        # push string "h=%s\n" top=0
        movq $string1, %rbx
        movq -8(%rbp), %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "l=%d\n" top=0
        movq $string2, %rbx
        movq -8(%rbp), %r10
     # func=mystrlen nargs=1
     # Move values from reg stack to reg args
        movq %r10, %rdi
        call mystrlen
        movq %rax, %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx

        # push 30
        movq $30,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
        movq %rbx, %rdi
        call malloc
        movq %rax, %rbx
        movq %rbx, g
        movq g,%rbx
        #top=1

        # push string "This is a great course" top=1
        movq $string3, %r10
     # func=strcpy nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        call strcpy
        movq %rax, %rbx
        #top=0

        # push string "g=%s\n" top=0
        movq $string4, %rbx
        movq g,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "l=%d\n" top=0
        movq $string5, %rbx
        movq g,%r10
     # func=mystrlen nargs=1
     # Move values from reg stack to reg args
        movq %r10, %rdi
        call mystrlen
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
        .string "Hello world"

string1:
        .string "h=%s\n"

string2:
        .string "l=%d\n"

string3:
        .string "This is a great course"

string4:
        .string "g=%s\n"

string5:
        .string "l=%d\n"