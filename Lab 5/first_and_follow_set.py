# 21BCP187
# Smit Padshala

FIRST = {}
FOLLOW = {}
non_terminals = set()
terminals = set()

# Function to compute FIRST set for a non-terminal
def first_set(non_terminal):
    if FIRST.get(non_terminal) is not None:
        return FIRST[non_terminal]

    FIRST[non_terminal] = set()

    for production in grammar[non_terminal]:
        for symbol in production:
            if symbol in terminals:
                FIRST[non_terminal].add(symbol)
                break
            elif symbol == '@':
                FIRST[non_terminal].add('@')
                break
            else:
                FIRST[non_terminal].update(first_set(symbol))
                if '@' not in FIRST[symbol]:
                    break

    return FIRST[non_terminal]

# Function to compute FOLLOW set for a non-terminal
def follow_set(non_terminal):
    if FOLLOW.get(non_terminal) is not None:
        return FOLLOW[non_terminal]

    FOLLOW[non_terminal] = set()

    if non_terminal == starting_symbol:
        FOLLOW[non_terminal].add('$')

    for nt, productions in grammar.items():
        for production in productions:
            for idx, symbol in enumerate(production):
                if symbol == non_terminal:
                    if idx < len(production) - 1:
                        next_symbol = production[idx + 1]
                        if next_symbol in terminals:
                            FOLLOW[non_terminal].add(next_symbol)
                        else:
                            FIRST_next = first_set(next_symbol)
                            FOLLOW[non_terminal].update(
                                FIRST_next.difference({'@'}))
                            if '@' in FIRST_next:
                                FOLLOW[non_terminal].update(
                                    follow_set(nt))
                    else:
                        FOLLOW[non_terminal].update(follow_set(nt))

    return FOLLOW[non_terminal]

no_of_terminals = int(input("Enter the number of terminals: "))
print("Enter the terminals:")
terminals = set(input() for _ in range(no_of_terminals))

no_of_non_terminals = int(input("Enter the number of non-terminals: "))
print("Enter the non-terminals:")
non_terminals = set(input() for _ in range(no_of_non_terminals))

starting_symbol = input("Enter the starting symbol: ")

no_of_productions = int(input("Enter the number of productions: "))

print("Enter the productions in the format NonTerminal -> Production1 | Production2 | ...")
grammar = {}
for non_terminal in non_terminals:
    grammar[non_terminal] = set()

for _ in range(no_of_productions):
    production_input = input()
    non_terminal, productions = production_input.split(' -> ')
    productions = productions.split(' | ')
    grammar[non_terminal] = grammar[non_terminal].union(productions)

# Compute FIRST and FOLLOW sets
for non_terminal in non_terminals:
    first_set(non_terminal)
    follow_set(non_terminal)

print("\nFIRST sets:")
for non_terminal in non_terminals:
    print(f'FIRST({non_terminal}) = {sorted(list(FIRST[non_terminal]))}')

print("\nFOLLOW sets:")
for non_terminal in non_terminals:
    print(f'FOLLOW({non_terminal}) = {sorted(list(FOLLOW[non_terminal]))}')
