# Global Variables
        .data
        .comm n,8           # long n
        .comm num,8         # long num

        .text
fmt1:
        .string "n?"
fmt2:
        .string "%ld"
fmt3:
        .string "SUM=%ld\n"
fmt4:
        .string "AVG=%ld\n"

.globl main
main:                            # main() {
                                 #
        pushq    %rbp            # Save frame pointer
        movq     %rsp, %rbp      #
        pushq    %r13            #
        pushq    %r14            #
                                 #
        movq     $fmt1, %rdi     # printf("n?");
        movq     $0, %rax        #
        call     printf          #
                                 #
        movq     $fmt2, %rdi     # scanf("%ld", &n);
        movq     $n, %rsi        #
        movq     $0, %rax        #
        call     scanf           #
                                 #
        movq     %rsi, %rbx      # Number of elements are in rbx.
        movq     $1, %r13        # Initialize loop counter, i = 1;
        movq     $0, %r14        # Initialize sum to 0, sum = 0;
        jmp      loop_check      #
                                 #
loop_start:                      # for loop
        movq     $fmt2, %rdi     # scanf("%ld", &n);
        movq     $num, %rsi      #
        movq     $0, %rax        #
        call     scanf           #
                                 #
        addq     %rsi, %r14      # sum += num;
        addq     $1, %r13        # i++;
                                 #
loop_check:                      #
        cmpq     %r13, %rbx      # Check in for loop for i < n
        jge      loop_start      # Continue the for loop
                                 #
after_loop:                      # Printing the sum and average
        movq     %r14, %rax      # Storing sum into %rax for division.
        idivq    %rbx            # average = sum / n;
        movq     %rax, %r13      #
                                 #
        movq     $fmt3, %rdi     # printf("SUM=%ld\n", sum);
        movq     %r14, %rsi      #
        movq     $0, %rax        #
        call     printf          #
                                 #
        movq     $fmt4, %rdi     # printf("AVG=%ld\n", average);
        movq     %r13, %rsi      #
        movq     $0, %rax        #
        call     printf          #
                                 #
        popq     %r14            #
        popq     %r13            #
        leave                    # pops the frame pointer
        ret                      # return 0; }