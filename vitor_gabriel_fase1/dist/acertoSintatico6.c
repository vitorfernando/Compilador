/*
P g:

N u, s, n;
S a;

u = 0;
s = 0;
a = 'Henry';
n = 9;

R 'Well, ' + a + ', I am thinking of a n between 1 and 9.';

W u < 9 {

    s = s + 2;

    u = u + 1;

    I s < n {
        R 'Your s is too low.';
    }

    I s > n {
        B;
    }
}

R 'Nope. The n I was thinking of was ' + n;

E

*/

#include <stdio.h>
#include <string.h>

int main(){
	
	int u, s, n;
	char a[20];

	u = 0;
	s = 0;
	strcpy(a, "Henry");
	n = 9;

	printf("Well, %s, I am thinking of a n between 1 and 9.", a);

	while( u < 9 ){

	    s = s + 2;
	    u = u + 1;

	    if( s < n ){
	        printf("Your s is too low.");
	    }

	    if( s > n ){
	        break;
	    }
	}

	printf("Nope. The n I was thinking of was %d", n);

	return 0;
}