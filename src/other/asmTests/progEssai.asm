.data
str: .asciiz "Des caracteres ....\n"
lus: .space 256
newLine: .asciiz "\n"
.text
main:
li $v0, 1
li $t0, 0
li $t1, 0
li $t2, 0
li $t3, 0
li $t2, 57
move $a0, $t2
syscall
move $t0, $t2
move $t1, $t2
move $a0, $t0
syscall
move $a0, $t1
syscall
blt $t2, $t0, Else
li $t1, 180
b EndIf
Else : li $t1, 18
EndIf :
While : 
    ble $t1, $zero, EndWhile     
    move $a0, $t1
    syscall
    subi $t1, $t1, 11
EndWhile :
li $t3, 12
For : 
    bgt $t3, 22, EndFor
    addi $t0, $t0, 1
    move $a0, $t0
    syscall
    addi $t3, $t3, 1
    j For
EndFor :
exit :
    li $v0, 10
    syscall