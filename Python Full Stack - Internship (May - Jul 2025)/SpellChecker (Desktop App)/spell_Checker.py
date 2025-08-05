from spellchecker import SpellChecker

def check_spelling(text):
    spell = SpellChecker()
    spelling_issues = []
    index = 0

    while index < len(text):
        word = ""
        while index < len(text) and text[index].isalpha():
            word += text[index]
            index += 1
        if word:
            if word.lower() not in spell:
                suggestions = spell.candidates(word)
                spelling_issues.append({
                    'message': f"Spelling issue with '{word}'",
                    'suggestions': list(suggestions) if suggestions else [],
                    'word': word
                })
        index += 1  # Skip non-alphabetic characters

    return spelling_issues
