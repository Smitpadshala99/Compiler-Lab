%option noyywrap
%{
#include<stdio.h>
%}

%%
[0-9]+ {
    int num = atoi(yytext); // Convert matched text to an integer
    if (num % 7 == 0) {
        num += 3;
    }
    printf("%d ", num);
}

.|\n {
    printf("%s", yytext); // Print non-matching characters as they are
}
%%

int main()
{
    FILE* input = fopen("input.txt","r");
    if(!input)
    {
        fprintf(stderr, "Error opening the input file");
    }
    yyin = input;
    yylex();
    fclose(input);
}
