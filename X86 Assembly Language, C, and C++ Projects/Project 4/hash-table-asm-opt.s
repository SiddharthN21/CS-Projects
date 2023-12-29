        .text
fmt1:
        .string "malloc"

#long HashTable_ASM_OPT_hash(void * table, char * word);
.globl HashTable_ASM_OPT_hash
HashTable_ASM_OPT_hash:
        pushq %rbp            # saves the frame pointer
        movq %rsp, %rbp       #

        # Add your implementation here
        # struct HashTable * hashTable = table;
        # table = %rdi
        # word = %rsi
        # hashNum = %rax
        # temporal %rcx, %r8

        movq $1, %rax         # long hasNum = 1;
                              #
whileloop:                    #
        #movq $0, %rcx         # Getting each character of word (%rsi)
        movb (%rsi), %cl      #
        cmpq $0, %rcx         # while (*word != 0)
        je   afterwhile       #
                              #
        movq  %rax, %rdx      #
        imulq $32, %rax       # hashNum = 31 * hashNum + *word;
        addq  %rcx, %rax      #
        subq  $1, %rax

                              #
        addq  $1, %rsi        # word++;
        jmp   whileloop       #
                              #
afterwhile:                   #
        cmpq  $0, %rax        # if (hashNum < 0) hashNum = -hashNum;
        jge   nonnegative     #
        #imulq $-1, %rax       # Setting hashNum to a non-negative value
        notq  %rax
                              #
nonnegative:                  #
        movq  $0, %rdx        # return hashNum % hashTable->nBuckets;
        movq  48(%rdi), %rcx  # %rcx = hash->nBuckets
        divq  %rcx            #
        movq  %rdx, %rax      # %rax = hashNum
                              #
        leave                 # pops the frame pointer
        ret                   #

#long HashTable_ASM_OPT_lookup(void * table, char * word, long * value);
.globl HashTable_ASM_OPT_lookup
HashTable_ASM_OPT_lookup:
        pushq %rbp                    # Saves the frame pointer
        movq  %rsp, %rbp              #
        pushq %rbx                    #
        pushq %r12                    #
        pushq %r13                    #
        pushq %r14                    #
        pushq %r15                    #
        pushq %rcx                    #

        # Add your implementation here
        movq  %rdi, %r13              # %r13 = HashTable
        movq  %rsi, %r15              # %r15 = word
        movq  %rdx, %r12              # %r12 = value
        call HashTable_ASM_hash       # long hashNum = HashTable_C_hash(hashTable, word);
        movq  %rax, %r14              # return of hash function is now in %r14 (hashNum)
        movq  %r15, %rsi              # rsi gets back its original value
                                      #
        imulq  $24, %r14              # hashNum is in %r14, struct HashTableElement * elem = hashTable->array[hashNum].next;
        addq   56(%rdi), %r14         #
        movq   16(%r14), %r14         # %r14 now has elem
                                      #
        movq  $0, %rax                # initialize return to 0 (false)
                                      #
loopStart:                            #
        #movq  16(%r14), %r14          #
        cmpq  $0, %r14                # Check if elem != NULL;
        #addq  $16, (%r14)
        #cmpq  $0, %r14
        je    wordnotFound            #
        #movq  (%r14), %rdi            # Check if strcmp(elem->word, word) != 0
        movq  (%r14), %rdi
        #movq  $0, %rax                #
        call  my_strcmp                  #
        cmpq  $0, %rax                # Checks if the strings are equal
        je    wordFound               #
        #movq  %r13, %rdi              #
        #movq  %r15, %rsi              #
        movq  16(%r14), %r14          # elem = elem->next;
        jmp   loopStart               #
                                      #
wordFound:                            #
        #movq  %r13, %rdi              # Restoring %rdi
        #movq  %r15, %rsi              # Restoring %rsi
        #movq  16(%r14), %r14
        movq  8(%r14), %r8            # %r8 = elem->value
        movq  %r12, %rdx              # Restoring %rdx
        movq  %r8, (%rdx)             # *value = elem->value;
        movq  $1, %rax                # Set return to 1 (true)
        jmp   return                  #
                                      #
wordnotFound:                         #
        movq  $0, %rax                # Set return to 0 (false)
                                      #
return:                               #
        popq  %rcx                    #
        popq  %r15                    #
        popq  %r14                    #
        popq  %r13                    #
        popq  %r12                    #
        popq  %rbx                    #
        leave                         # Pops the frame pointer
        ret                           #


#long HashTable_ASM_OPT_update(void * table, char * word, long value);
.globl HashTable_ASM_OPT_update
HashTable_ASM_OPT_update:
        pushq %rbp                    # Saves the frame pointer
        movq  %rsp, %rbp              #
        pushq %rbx                    #
        pushq %r12                    #
        pushq %r13                    #
        pushq %r14                    #
        pushq %r15                    #
        pushq %rcx                    #

        # Add your implementation here
        movq  %rdi, %r13              # %r13 = HashTable
        movq  %rsi, %r15              # %r15 = word
        movq  %rdx, %r12              # %r12 = value
        call HashTable_ASM_hash       # long hashNum = HashTable_C_hash(hashTable, word);
        movq  %rax, %r14              # return of hash function is now in %r14 (hashNum)
        movq  %r15, %rsi              # rsi gets back its original value

        imulq  $24, %r14              # hashNum is in %r14, struct HashTableElement * elem = hashTable->array[hashNum].next;
        addq   56(%rdi), %r14         #
        movq   %r14, %r9              # %r9 now has elem
        movq   16(%r14), %r14         # %r14 now has elem->next
                                      #
        #movq  $0, %rax                # initialize return to 0 (false)
                                      #
        cmpq  $1, %r12                # if value = 1 then it is pure insert.
        je    loopStart2

loopStart1:                           #
        cmpq  $0, %r14                # Check if elem->next != NULL;
        je    wordnotFound1           #
        movq  (%r14), %rdi            # Check if strcmp(elem->next->word, word) != 0
        #movq  $0, %rax                #
        call  my_strcmp                  #
        cmpq  $0, %rax                # Checks if the strings are equal
        je    wordFound1              #
        #movq  %r13, %rdi              # Restore original value of %rdi
        #movq  %r15, %rsi              # Restore original value of %rsi
        movq  %r14, %r9               # Moving %r9 to the next element
        movq  16(%r14), %r14          # elem = elem->next;
        jmp   loopStart1              #

loopStart2:
        cmpq  $0, %r14
        je wordnotFound1
        movq  %r14, %r9
        movq  16(%r14), %r14
        jmp  loopStart2
                                      #
wordFound1:                           #
        #movq  %r13, %rdi              # Restore original value of %rdi
        #movq  %r15, %rsi              # Restore original value of %rsi
        #movq  %r12, %rdx              # Restore original value of %rdx
        #movq  %rdx, 8(%r14)           # elem->next->value = value;;
        movq   %r12, 8(%r14)
        movq  $1, %rax                # Set return to 1 (true)
        jmp   return1                 #
                                      #
wordnotFound1:                        #
        movq  %r9, %r14               # %r14 = %r9 = elem
        movq  $24, %rdi               # malloc 24 bytes for struct HashtableElement * e
        call  malloc                  #
        #testq %rax, %rax              # check if malloc was successful (not null)
        #jz    mallocError             #

        movq %rax, %rbx
        movq %rbx, 16(%r14)
        movq %r15, %rdi
        call my_strdup
        movq %rax, 0(%rbx)
        movq %r12, 8(%rbx)
        movq $0, 16(%rbx)

        #movq  %rax, %r8               # %r8 = e;
        #movq  %r8, 16(%r14)           # elem->next = e
        #movq  %r8, %rbx               # saving %r8
        #movq  %r15, %rdi              # Calling strdup(word), %r15 = word
        #call  strdup                  #
                                      #
        #movq  %rbx, %r8               # Restoring value of %r8 (e)
        #movq  %rax, 0(%r8)            # e->word = strdup(word);
        #movq  %r12, 8(%r8)            # e->value = value;
        #movq  $0, 16(%r8)             # e->next = NULL;
                                      #
        #movq  %r13, %rdi              # Restoring argument register values
        #movq  %r15, %rsi              #
        #movq  %r12, %rdx              #
        movq  $0, %rax                # Set return to 0 (false)
        jmp   return1                 #
                                      #                                      #
return1:                              #
        popq  %rcx                    #
        popq  %r15                    #
        popq  %r14                    #
        popq  %r13                    #
        popq  %r12                    #
        popq  %rbx                    #
        leave                         # Pops the frame pointer
        ret                           #

mallocError:                          #
        #movq  %r13, %rdi              # Restoring argument register values
        #movq  %r15, %rsi              #
        #movq  %r12, %rdx              #
        movq  $fmt1, %rdi             # perror(malloc);
        movq  $0, %rax                #
        call  perror                  #
        movq  $1, %rdi                # exit(1);
        call  exit                    #
                                      #


.globl my_strcmp
my_strcmp:
    pushq %rbp
    movq  %rsp, %rbp
    pushq %rsi

    movb (%rdi), %al  # Load a byte from str1 into %al

    movb (%rsi), %dl  # Load a byte from str2 into %dl


compare_loop:

    cmpb %al, %dl    # Compare the two bytes

    je continue     # If equal, continue comparing
    movq $1, %rax
    jmp mystrcmp_return


continue:

    incq %rdi         # Move to the next byte in str1
    incq %rsi         # Move to the next byte in str2

    movb (%rdi), %al  # Load a byte from str1 into %al
    movb (%rsi), %dl  # Load a byte from str2 into %dl

    cmpb $0, %al   # Check if we have reached the end of the string
    jnz compare_loop # If not, continue comparing
    cmpb $0, %dl
    jnz compare_loop

    movq  $0, %rax

mystrcmp_return:
    popq %rsi
    leave
    ret


.globl my_strdup               # strlen(char * s) {
                              # // s = %rdi
                              #
my_strdup:                     #
        pushq    %rbp         # Saves frame pointer
        movq     %rsp, %rbp   #

        movq     %rdi, %r13
                              #
        movq     $1, %rax     # Intiaze length counter.
                              #
loopstart:                    #
        movb     (%rdi), %dl  # Move first charter for comparision
        cmpb     $0,%dl
        je       loopend      #
        incq     %rdi         # Move to next character
        addq     $1, %rax
        jmp      loopstart    #
loopend:                      #
        #movq     %rcx, %rax   #

        movq    %rax, %rdi
        call    malloc
        movq    %rax, %r15

strcpy_loop:
        #movb    (%r13), %dl
        #movb    %dl, (%r15)
        #incq    %r13
        #incq    %r15
        #cmpb    $0, %dl
        #jnz     strcpy_loop

        movq    %rax, %rdi
        movq    %r13, %rsi
        call    strcpy

        leave                 # pops the frame pointer
        ret                   # }