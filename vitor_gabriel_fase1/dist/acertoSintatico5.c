#include <stdio.h>

int main(){
	int p,b;

	p = 1;
	
	b = 1;
	
	while(b < 9){
		printf("This generation has  %d babies",b);
		p = b;
		b = p + b;
	}
	
	return 0;
}
