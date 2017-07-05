#include <stdio.h>
int tValue(int a , int b , int c){
	int t;
	t = a + b;
	t = t * c / 2;
	t = t + 2;
	return t ;
}
void main(){
	int a,b,c,d[3],t;
	a = 9;
	b = 5;
	c = 4;
	d = (1,2,3);
	t = tValue()(a,b,c);
	printf("sum =  %d \n",t);
	return ;
}
