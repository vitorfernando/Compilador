#include <stdio.h>
void for1(){
	int var1;
	for(var1 = 5;var1 < 15; var1++){
		printf("%d \t",var1);
	}
	printf("\n");
	return ;
}
void for2(){
	int var2;
	for(var2 = 10;var2 > 0; var2--){
		if(var2 == 2 or var2 > 5){
			printf("%d \t",var2);
		}
		if(var2 <> 6){
			printf("Diferente de 6\t");
		}
	}
	printf("\n");
	return ;
}
void main(){
	printf("Testing the for loop:\n");
	for1();
	for2();
	return ;
}
