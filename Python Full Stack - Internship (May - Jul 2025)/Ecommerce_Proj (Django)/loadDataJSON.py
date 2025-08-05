import codecs

def conv_UTF_16LE_To_8(inputfile, outputfile):
    # Try reading as UTF-16-LE (Little Endian, common for BOMs)
    try:
        with codecs.open(input_file, 'r', encoding='utf-16-le') as f_in:
            content = f_in.read()
        with codecs.open(output_file, 'w', encoding='utf-8') as f_out:
            f_out.write(content)
        print(f"Successfully converted '{input_file}' from UTF-16-LE to '{output_file}' (UTF-8).")
    except UnicodeDecodeError:
        print(f"Could not decode '{input_file}' as UTF-16-LE. It might be a different encoding.")
        # Fallback to trying other common encodings or open in a text editor to determine exact encoding.
    except Exception as e:
        print(f"An error occurred: {e}")
    
def removeUTF_8_BOM(inputfile):
    # The inputfile that has the UTF-8 BOM
    # We'll overwrite this file after reading its content

    try:
        # Read the content using 'utf-8-sig' encoding.
        # 'utf-8-sig' is specifically designed to handle and remove the UTF-8 BOM if present.
        with codecs.open(input_file, 'r', encoding='utf-8-sig') as f_in:
            content = f_in.read()

        # Write the content back to the same file, explicitly using 'utf-8' encoding.
        # This ensures no BOM is added when writing.
        with codecs.open(input_file, 'w', encoding='utf-8') as f_out:
            f_out.write(content)

        print(f"Successfully processed '{input_file}': BOM removed and saved as UTF-8.")

    except FileNotFoundError:
        print(f"Error: The file '{input_file}' was not found. Please ensure the path is correct.")
    except UnicodeDecodeError:
        # This might happen if the file is neither UTF-8 with BOM nor simple UTF-8
        print(f"Error: Could not decode '{input_file}' as UTF-8 with BOM. It might be a different encoding or corrupted.")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")

input_file = 'allData.json'
output_file = 'allData_utf8.json'

conv_UTF_16LE_To_8(input_file,output_file)   
removeUTF_8_BOM(output_file) 