%option noyywrap
%{
    #include <stdio.h>
%}

%%
auto|break|case|char|const|continue|default|do|double|else|enum|extern|float|for|goto|if|int|long|register|return|short|signed|sizeof|static|struct|switch|typedef|union|unsigned|void|volatile|while {printf("The length of keyword %s: %d \n", yytext, yyleng);}
[a-zA-Z]([a-zA-Z_]|[0-9])* {printf("The length of identifier %s is: %d \n", yytext, yyleng);}
[0-9]+ {printf("The length of digit %s is: %d\n", yytext, yyleng);}
[0-9]*\.[0-9]+ {printf("The length of float number %s is: %d\n",yytext, yyleng);}
[ \t\n] ;
. {printf("The length of %s is: %d\n", yytext, yyleng);}
%%

int main() {
    yylex();
    return 0;
}