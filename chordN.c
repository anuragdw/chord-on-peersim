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



float fact(n) {
	if ((n==1) || (n==0)) return 1.0; 	
	return n*fact(n-1);
}

float comb(int n, int r) {
	return fact(n)/(fact(r)*fact(n-r)) ;
}

int prob(int l, int n_dash, int k, int N) {
	int j;
	int k_dash;
	float ret = 1.0;
	if (l==0) {
		k_dash = (int)comb(k,l);	
		return (comb(n_dash,k_dash)/comb(N,k_dash));
	}
	printf("%d reached ",l);
	for ( j=0; j<l; j++) {
		ret *= (1 - prob(j,n_dash,k,N));
	}
	k_dash = (int)comb(k,l);
	ret *= k_dash;
	printf("kk %d ",k_dash)); 
	//ret *= ( comb(n_dash,(int)k_dash) ) ; //  /comb(N,k_dash)) ;  
	return ret;
}

int main ( int argc, char* argv[]) {

	int k, N, i, l;
// i, k, N;
//	int l ;
	int n_dash;  
	if ( argc != 2 ) return 0;

	else {
		
		k = atoi(argv[1]);
		//printf("hello");
		N = powe(2,k);
		n_dash = 1;
		/*for ( i=1; i<(4*N); i++ ) {
			
			for (l=1; l<=k; l++) {
				n_dash += l*prob(l,n_dash,k,N);
			} 
			printf("%d %d\n",i,n_dash);	
		}*/

		for ( i=1; i<=k; i++ ) {
			
			//for (l=1; l<=k; l++) {
			//	n_dash += l*prob(l,n_dash,k,N);
			//} 
			printf("hello");
			printf("%d %f\n",i,prob(i,n_dash,k,N));	
		}	


	}
}
 

