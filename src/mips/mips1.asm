.data
str: 	.asciiz "\n"
.text
main:   
	
# initialiser $s7 avec $sp
move $s7, $sp

# réserver la place pour 3 variables
add $sp, $sp, -16

# affectation i
li $v0, 81
sw $v0, 0($s7)

# affectation j
li $v0, 2
sw $v0, -4($s7)

# Ecrire i
	li $v0 , 1 
	lw $a0, 0($s7)     
	syscall 

#Retour à la ligne
	li $v0, 4 	# $v0 <- code du print
	la $a0, str 	# $a0 <- adresse de la cha?ne ? ?crire
	syscall 	# afficher
	
# Ecrire j
	li $v0 , 1 	
	lw $a0, -4($s7)     
	syscall 	
	
#Retour à la ligne
	li $v0, 4 	# $v0 <- code du print
	la $a0, str 	# $a0 <- adresse de la cha?ne ? ?crire
	syscall 	# afficher

# affectation k
lw $v0, 0($s7)
sw $v0, -8($s7)

# affectaion l
lw $v0, 0($s7)
sw $v0, -12($s7)

# Ecrire k
	li $v0 , 1 
	lw $a0, -8($s7)     
	syscall 
	
#Retour à la ligne
	li $v0, 4 	# $v0 <- code du print
	la $a0, str 	# $a0 <- adresse de la cha?ne ? ?crire
	syscall 	# afficher	
	
# Ecrire l	
	li $v0 , 1 
	lw $a0, -12($s7)     
	syscall 
	
#Retour à la ligne
	li $v0, 4 	# $v0 <- code du print
	la $a0, str 	# $a0 <- adresse de la cha?ne ? ?crire
	syscall 	# afficher


#si
si :
#stockage variable temporaire
lw $t1, 0($s7)
lw $t2, -8($s7)
#condition de validation
bne $t1, $t2, Sinon
#l vaut 18
li $v0, 18
sw $v0, -12($s7)
#fin de branche
b FinSi
#branche alternative
Sinon :
#l vaut 180
li $v0, 180
sw $v0, -12($s7)
FinSi :

#tantque
tantque :
#pas de variable temporaire ici car le syscall va la detruire
lw $s0, -12($s7)
#condition du tant que ( l>1)
blez $s0, Fintantque
# Ecrire l	
	li $v0 , 1 
	lw $a0, -12($s7)     
	syscall 
	
#Retour à la ligne
	li $v0, 4 	# $v0 <- code du print
	la $a0, str 	# $a0 <- adresse de la cha?ne ? ?crire
	syscall 	# afficher
#soustraction de j
sub $s0, $s0, 10
sw $s0, -12($s7)
#retour tant que condition vrai
j tantque
Fintantque:

#Pour
li $v0, 1
sw $v0, -4($s7)
pour:
lw $s1, -4($s7)
#condition (1..10)
bgt $s1, 10, Finpour
# Ecrire k
	li $v0 , 1 
	lw $a0, -8($s7)     
	syscall 
	
#Retour à la ligne
	li $v0, 4 	# $v0 <- code du print
	la $a0, str 	# $a0 <- adresse de la cha?ne ? ?crire
	syscall 	# afficher	
#addition de 1 a j
addi $s1,$s1,1	
sw $s1, -4($s7)
j pour
Finpour:

#fin
end :
li $v0, 10
syscall 
