
.text
.globl maxarray      # // Finds the max value in an array
                                 #
                                 # long maxarray(long n, long *a)
                                 #    // n = %rdi     a = %rsi
maxarray:                        #    // i = %rdx     max = %rax
                                 #
         pushq  %rbp             # Save frame pointer
         movq    %rsp, %rbp      #
                                 #
         movq    $0,%rdx         #    i=0 ;
         movq    (%rsi),%rax     #    max = a[0]
                                 #
while:   cmpq    %rdx,%rdi       #    while (i<n) { // (n-i>0)
         jle     afterw          #
                                 #            //*(long*)((8*i+(char*)a)
         movq    %rdx,%rcx       #      long *tmp = &a[i];
         imulq   $8,%rcx         #
         addq    %rsi,%rcx       #
                                 #
         cmpq    (%rcx),%rax     #      if (max < *tmp) { // (max-*tmp<0)
         jge     afterif         #
         movq    (%rcx),%rax     #        max = *tmp
                                 #      }
afterif: addq    $1,%rdx         #      i++ ;
         jmp while               #
                                 #    }
afterw:  leave                   #
         ret                     # }