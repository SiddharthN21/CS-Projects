# Define global variable a in data section
        .data
        .comm   a,8             # long a;

        .text
format1:
        .string "a="

format2:
        .string "%ld"

format3:
        .string "a^2 is %ld\n"

.globl main
main:                          # main()
                               #
        pushq  %rbp            # Save frame pointer
        movq    %rsp, %rbp     #
                               #
        movq    $format1, %rdi #   printf("a=");
        movq    $0, %rax       #
        call    printf         #   similar to `bl printf` in ARM
                               #
        movq    $format2, %rdi #   scanf("%ld",&a);
        movq    $a, %rsi       #
        movq    $0, %rax       #
        call    scanf          #
                               #
        movq    $format3, %rdi #   printf("a^2 is %ld",a*a);
        movq    $a, %rsi       #
        movq    (%rsi),%rsi    #
        imulq   %rsi,%rsi      #
        movq    $0, %rax       #
        call    printf         #

        leave                  # pops the frame pointer
        ret                    # }