#include <stdio.h>
void printValues(int t , int b , int d){
	printf("sum = %d \n",t);
	printf("%d \n",d);
	return ;
}
void main(){
	int a,b,c,d[3],t;
	a = 9;
	b = 5;
	c = 4;
	d = (1,2,3);
	t = a + b;
	t = t * c / 2;
	t = t + 2;
	printValues(t,b,d);
	return ;
}
