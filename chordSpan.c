#include <stdio.h>

int find ( int k, int i ) {
	int sum,j;
	if ( i > k ) return 0;
	else if ( i==k) return 1;
	else if ( i==0) return 1;
	else {
		 sum = 0;
		for (  j=0; j<k; j++) {
			sum += find (j,i-1);
		}
		return sum;
	}
}

int powe ( int a, int b) {

	int ret =1 ;
	int i;
	for ( i=1;i<=b; i++) ret*= a;
	return ret;
} 

int main ( int argc, char* argv[]) {

	int i, k; 
	if ( argc != 2 ) return 0;

	else {
		
		k = atoi(argv[1]);
		printf("N = %d\n", powe(2,k));
		int tmp[k+1];
	
		for(i = 0; i<=k; i++) {
			tmp[i] = find(k,i); 
			printf("Round %d : %d\n",i,tmp[i]);
		}
	}
}
 

