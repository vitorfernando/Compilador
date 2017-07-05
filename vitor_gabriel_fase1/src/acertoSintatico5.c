#include <stdio.h>
int babiesValue(int p , int babies){
	while(babies <= 9){
		printf("This generation has  %d  babies.\n",babies);
		p = babies;
		babies = p + babies;
		if(babies == 8){
			break;
		}
	}
	return babies ;
}
void main(){
	int babies;
	babies = babiesValue(1,1);
	printf("Final  %d .\n",babies);
	return ;
}
