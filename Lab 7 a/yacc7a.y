%{   
#include <stdio.h>
#include <math.h>
%}

%token NAME num
%%

S: E      { printf("Result: %d\n", $1); }
     | S E { printf("Result: %d\n", $2); }
     ;

E: E '+' E { $$ = $1 + $3; }
      | E '-' E { $$ = $1 - $3; }
      | E '*' E { $$ = $1 * $3; }
      | E '/' E { $$ = $1 / $3; }
      | E '^' E { $$ = pow($1, $3); }
      | '(' E ')' { $$ = $2; }
      | num { $$ = $1; }
      ;
%%
int main() {
    yyparse();
}
