%{
#include "yacc7b.tab.h"
%}
%%
[0-9]+ { yylval = atoi(yytext); return num; }
[ \t] ; /* Ignore whitespace */
\n return 0; /* Logical EOF */
. return yytext[0];
%%
int yywrap() {
    return 1;
}
void yyerror(char *s) {
    printf("error");
}
