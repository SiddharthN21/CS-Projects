        .text
.globl selectionsort                            # // Sorts the array by implementing selection sort
                                                #
                                                # void selection_sort(long ascending, long n, long * a) {
                                                # //ascending = %rdi, n = %rsi, a = %rdx
                                                # // temp = %14 (reused), i = %r8, long index = %r10, j = %r9
                                                # // (n - 1) = %rbx, a[j] = %r13, a[index] = %r14, later %r15
                                                # // array[i] = %rcx, temp1 = %r9 (reused)
                                                #
selectionsort:                                  #
               pushq    %rbp                    # Save frame pointer
               movq     %rsp, %rbp              #
               pushq    %rbx                    #
               pushq    %r10                    #
               pushq    %r13                    #
               pushq    %r14                    #
               pushq    %r15                    #
                                                #
               movq     %rsi, %rbx              #
               subq     $1, %rbx                # Set rbx as n - 1 instead of n
               movq     $0, %r8                 # i = 0
                                                #
outerloop:                                      #
               cmpq     %r8, %rbx               # i < (n - 1) comparison. Does (n - 1) - i > 0
               jle      endouterloop            #
                                                #
               movq     %r8, %r10               # long index = i; (i = %r8, index = %r10)
               movq     %r8, %r9                # j = i
               addq     $1, %r9                 # j = i + 1
                                                #
innerloop:                                      #
               cmpq     %r9, %rsi               # j < n. Does n - j > 0
               jle      endinnerloop            #
                                                #
ascending:                                      #
               cmpq     $0, %rdi                # if (ascending). Compares ascending - 0 > 0
               je       descending              #
               movq     %r9, %r13               # loading a[j] into %r13 for comparison
               imulq    $8, %r13                #
               addq     %rdx, %r13              #
               movq     %r10, %r14              # loading a[index] into %r14 for comparison
               imulq    $8, %r14                #
               addq     %rdx, %r14              #
               movq     (%r13), %r13            # Getting the value of a[j] into r13
               cmpq     %r13, (%r14)            # if (a[j] < a[index]). Compares a[index] - a[j] > 0
               jl       inrj                    #
               movq     %r9, %r10               # index = j;
                                                #
inrj:                                           #
               addq     $1, %r9                 # j++;
               jmp      innerloop               #
descending:                                     #
               movq     %r9, %r13               # loading a[j] into %r13 for comparison
               imulq    $8, %r13                #
               addq     %rdx, %r13              #
               movq     %r10, %r14              # loading a[index] into %r14 for comparison
               imulq    $8, %r14                #
               addq     %rdx, %r14              #
               movq     (%r13), %r13            #
               cmpq     %r13, (%r14)            # if (a[j] > a[index]). Compares a[index] - a[j] > 0
               jg       inrj                    #
               movq     %r9, %r10               # index = j;
               addq     $1, %r9                 # j++;
               jmp      innerloop               #
                                                #
                                                #
endinnerloop:                                   #
              movq      %r8, %rcx               # calculate the memory addres of a[i]
              imulq     $8, %rcx                #
              addq      %rdx, %rcx              #
              movq      %r10, %r15              # calculate memory address of a[index]
              imulq     $8, %r15                #
              addq      %rdx, %r15              #
              movq      (%r15), %r14            # temp = a[index]
              movq      (%rcx), %r9             # temp1 = a[i]
              movq      %r9, (%r15)             # a[index] = temp1
              movq      %r14, (%rcx)            # a[i] = temp
              addq      $1, %r8                 # i++:
              jmp       outerloop               #
                                                #
endouterloop:                                   #
              popq      %r15                    #
              popq      %r14                    #
              popq      %r13                    #
              popq      %r10                    #
              popq      %rbx                    #
              leave                             # pops the frame pointer
              ret                               # }