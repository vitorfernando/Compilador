#include <stdio.h>
#include <string.h>

int main(){
	int var1,guess,num;
bool achou;
	var1 = 0;
	guess = 0;
	num = 59;
	str1 = 'Henry';
	achou = False;
	printf("Well,  %s , I am thinking of a n between 1 and 100.",str1);
	for(var1 = 0;0 < 50; var1++){
		if(guess < num){
			printf("Your guess is too low.");
				guess = guess * 2;
			}
			else{
				if(guess > num){
					printf("Your guess is too high.");
						guess = guess / 2;
					}
					else{
						printf("Yes, I am thinking about  %d",guess);
							achou = True;
								break;
							}
						}
					}
					if(achou){
						printf("Nope. The number I was thinking of was  %d",num);
					}
	return 0;
}
