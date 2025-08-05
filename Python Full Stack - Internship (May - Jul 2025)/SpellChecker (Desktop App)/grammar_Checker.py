import language_tool_python

def check_grammar(text):
    #.LanguageTool('en-US') -> uses java server. .LanguageToolPublicAPI('en-US') --> is API
    tool = language_tool_python.LanguageTool('en-US') 
    matches = tool.check(text)
    issues = [{
                    'message': match.message,
                    'suggestions': match.replacements,
                    'word': text[match.offset:match.offset + match.errorLength],
                    'sentence': text[match.offset:text.find('.', match.offset)].strip()
            } for match in matches]
    return issues