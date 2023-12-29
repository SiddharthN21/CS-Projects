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

        # push string "9>8=%d\n" top=0
        movq $string0, %rbx

        # push 9
        movq $9,%r10

        # push 8
        movq $8,%r13

        # >
        movq $0, %rax
        cmpq %r13, %r10
        setg %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "8>9=%d\n" top=0
        movq $string1, %rbx

        # push 8
        movq $8,%r10

        # push 9
        movq $9,%r13

        # >
        movq $0, %rax
        cmpq %r13, %r10
        setg %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "9<8=%d\n" top=0
        movq $string2, %rbx

        # push 9
        movq $9,%r10

        # push 8
        movq $8,%r13

        # <
        movq $0, %rax
        cmpq %r13, %r10
        setl %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "8<9=%d\n" top=0
        movq $string3, %rbx

        # push 8
        movq $8,%r10

        # push 9
        movq $9,%r13

        # <
        movq $0, %rax
        cmpq %r13, %r10
        setl %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "9>=8=%d\n" top=0
        movq $string4, %rbx

        # push 9
        movq $9,%r10

        # push 8
        movq $8,%r13

        # >=
        movq $0, %rax
        cmpq %r13, %r10
        setge %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "8>=9=%d\n" top=0
        movq $string5, %rbx

        # push 8
        movq $8,%r10

        # push 9
        movq $9,%r13

        # >=
        movq $0, %rax
        cmpq %r13, %r10
        setge %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "9>=9=%d\n" top=0
        movq $string6, %rbx

        # push 9
        movq $9,%r10

        # push 9
        movq $9,%r13

        # >=
        movq $0, %rax
        cmpq %r13, %r10
        setge %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "9>=9=%d\n" top=0
        movq $string7, %rbx

        # push 9
        movq $9,%r10

        # push 9
        movq $9,%r13

        # >=
        movq $0, %rax
        cmpq %r13, %r10
        setge %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "9<=8=%d\n" top=0
        movq $string8, %rbx

        # push 9
        movq $9,%r10

        # push 8
        movq $8,%r13

        # <=
        movq $0, %rax
        cmpq %r13, %r10
        setle %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "8<=9=%d\n" top=0
        movq $string9, %rbx

        # push 8
        movq $8,%r10

        # push 9
        movq $9,%r13

        # <=
        movq $0, %rax
        cmpq %r13, %r10
        setle %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "9<=9=%d\n" top=0
        movq $string10, %rbx

        # push 9
        movq $9,%r10

        # push 9
        movq $9,%r13

        # <=
        movq $0, %rax
        cmpq %r13, %r10
        setle %al
        movq %rax, %r10
        movq %r10, %r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "9<=9=%d\n" top=0
        movq $string11, %rbx

        # push 9
        movq $9,%r10

        # push 9
        movq $9,%r13

        # <=
        movq $0, %rax
        cmpq %r13, %r10
        setle %al
        movq %rax, %r10
        movq %r10, %r13
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
        .string "9>8=%d\n"

string1:
        .string "8>9=%d\n"

string2:
        .string "9<8=%d\n"

string3:
        .string "8<9=%d\n"

string4:
        .string "9>=8=%d\n"

string5:
        .string "8>=9=%d\n"

string6:
        .string "9>=9=%d\n"

string7:
        .string "9>=9=%d\n"

string8:
        .string "9<=8=%d\n"

string9:
        .string "8<=9=%d\n"

string10:
        .string "9<=9=%d\n"

string11:
        .string "9<=9=%d\n"
