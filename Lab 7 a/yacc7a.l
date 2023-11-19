%{
#include "yacc7a.tab.h"
%}

%%
[0-9]+  { yylval = atoi(yytext); return num; }
[a-zA-Z]+  { yylval = strdup(yytext); return NAME; }
[ \t]    ;  // Skip whitespace
\n       return '\n';
.        return yytext[0];
