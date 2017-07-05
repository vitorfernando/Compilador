#include <stdio.h>
int vetPlay(int a , int b0 , boolean x , string y , int b2){
	return a[4] + a[1] - a[2] / a[0] + b0 * b2 ;
}
void doNothing(){
	return ;
}
void main(){
	int a,b[3],x;
	a = (1,2,3,4,5);
	b[0] = 6;
	b[1] = 7;
	b[2] = 8;
	x = vetPlay(a,b[0],True,"string play",b[2]);
	printf("%d \n",x);
	doNothing();
	return ;
}
