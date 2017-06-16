#include <stdio.h>

int main(){
	int p,babies;
p = 1;
babies = 1;
while(babies <= 9){
printf("This generation has  %d  babies",babies);
p = babies;
babies = p + babies;
	if(babies == 8){
		break;
	}}
	return 0;
}
