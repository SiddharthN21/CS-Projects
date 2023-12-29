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
for_start_0:
        movq i,%rbx

        # push 15
        movq $15,%r10

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
        movq i,%rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
        jmp for_start_0
for_details_0:
        #top=0

        # push string "i=%d\n" top=0
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

        # push 6
        movq $6,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_1
        movq i,%rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
        jmp loop_continue_2
        jmp if_end_1
else_true_1:
if_end_1:
loop_continue_2:
        jmp for_increment_0
for_end_0:
        #top=0

        # push string "for i=%d\n" top=0
        movq $string1, %rbx
        movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx

        # push 0
        movq $0,%rbx
        movq %rbx, i
while_start_3:
        movq i,%rbx

        # push 15
        movq $15,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je while_end_3
        #top=0

        # push string "i=%d\n" top=0
        movq $string2, %rbx
        movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        movq i,%rbx

        # push 8
        movq $8,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_4
        movq i,%rbx

        # push 2
        movq $2,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
        jmp loop_continue_5
        jmp if_end_4
else_true_4:
if_end_4:
        movq i,%rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
loop_continue_5:
        jmp while_start_3
while_end_3:
        #top=0

        # push string "while i=%d\n" top=0
        movq $string3, %rbx
        movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx

        # push 0
        movq $0,%rbx
        movq %rbx, i
do_while_start_6:
        #top=0

        # push string "i=%d\n" top=0
        movq $string4, %rbx
        movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        movq i,%rbx

        # push 10
        movq $10,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10

        # if
        cmpq $0, %rbx
        je else_true_7
        movq i,%rbx

        # push 2
        movq $2,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
        jmp loop_continue_8
        jmp if_end_7
else_true_7:
if_end_7:
        movq i,%rbx

        # push 1
        movq $1,%r10

        # +
        addq %r10,%rbx
        movq %rbx, i
        movq i,%rbx

        # push 15
        movq $15,%r10

        # <
        movq $0, %rax
        cmpq %r10, %rbx
        setl %al
        movq %rax, %rbx
        movq %rbx, %r10
        cmpq $0, %rbx
        je do_while_end_6
loop_continue_8:
        jmp do_while_start_6
do_while_end_6:
        #top=0

        # push string "do/while i=%d\n" top=0
        movq $string5, %rbx
        movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
        movq %r10, %rsi
        movq %rbx, %rdi
        movl    $0, %eax
        call printf
        movq %rax, %rbx
        #top=0

        # push string "OK\n" top=0
        movq $string6, %rbx
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
        .string "i=%d\n"

string1:
        .string "for i=%d\n"

string2:
        .string "i=%d\n"

string3:
        .string "while i=%d\n"

string4:
        .string "i=%d\n"

string5:
        .string "do/while i=%d\n"

string6:
        .string "OK\n"