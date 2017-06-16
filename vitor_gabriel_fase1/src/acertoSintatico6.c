#include <stdio.h>

int main(){
	int var1,var2;
	printf("Testing the for loop");
	for(var1 = 5;5 < 15; var1++){
		printf("%d",var1);
	}
	for(var2 = 10;10 > 0; var2--){
		if(var2 == 2 or var2 > 5){
			printf("%d",var2);
		}
		if(var2 <> 6){
			printf("Diferente de 6");
		}
	}
	return 0;
}
