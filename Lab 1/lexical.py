def analyze_line(line, keyword_list, identifier_list, string_literal_list, number_list, other_list):
    current_token = ""
    inside_string_literal = False

    for char in line:
        if char == '\"':
            if inside_string_literal:
                current_token += char
                string_literal_list.append(current_token)
                print(f"String Literal: {current_token}")
                current_token = ""
            inside_string_literal = not inside_string_literal
        elif inside_string_literal:
            current_token += char
        elif char.isspace():
            process_token(current_token, keyword_list, identifier_list, string_literal_list, number_list, other_list)
            current_token = ""
        else:
            current_token += char

    process_token(current_token, keyword_list, identifier_list, string_literal_list, number_list, other_list)

def process_token(token, keyword_list, identifier_list, string_literal_list, number_list, other_list):
    token = token.strip()

    keywords = ["abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
                "const", "continue", "default", "do", "double", "else", "enum", "exports", "extends", "final",
                "finally", "float", "for", "if", "implements", "import", "instanceof", "int", "interface", "long",
                "module", "native", "new", "open", "opens", "package", "private", "protected", "provides", "public",
                "requires", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw",
                "throws", "transient", "transitive", "try", "var", "void", "volatile", "while", "with"]

    if not token:
        return

    if token in keywords:
        keyword_list.append(token)
        print(f"{token} : Keyword")
    elif is_valid_identifier(token):
        identifier_list.append(token)
        print(f"{token} : Identifiers")
    elif is_string_literal(token):
        string_literal_list.append(token)
        print(f"{token} : String Literals")
    elif is_number(token):
        number_list.append(token)
        print(f"{token} : Numbers")
    else:
        other_list.append(token)
        print(f"{token} : Others")

def is_valid_identifier(word):
    if not (word[0].isalpha() or word[0] == '_'):
        return False
    for char in word[1:]:
        if not (char.isalnum() or char == '_'):
            return False
    return True

def is_string_literal(token):
    return token.startswith("\"") and token.endswith("\"")

def is_number(word):
    try:
        float(word)
        return True
    except ValueError:
        return False

def lexical_analysis(file_name):
    try:
        with open(file_name, 'r') as file:
            keyword_list, identifier_list, string_literal_list, number_list, other_list = [], [], [], [], []
            for line in file:
                analyze_line(line, keyword_list, identifier_list, string_literal_list, number_list, other_list)

        print(f"Keywords: {keyword_list}")
        print(f"Identifiers: {identifier_list}")
        print(f"String Literals: {string_literal_list}")
        print(f"Numbers: {number_list}")
        print(f"Others: {other_list}")

    except Exception as e:
        print(f"Error reading the file: {e}")

if __name__ == "__main__":
    file_name = "Lab 1\input.java"
    lexical_analysis(file_name)
