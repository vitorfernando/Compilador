#include <stdio.h>
void main(){
	int i,counter,alist[10];
	i = 10;
	counter = 0;
	alist = (20,30,40,90,50,60,70,80,100,110);
	while(i <= 110){
		if(alist == i){
			printf("i is in the list:  %d \n",i);
		}
		else{
			printf("i is not in the list:  %d \n",i);
		}
		i = i * 3 - 10;
		counter = counter + 1;
	}
	return ;
}
