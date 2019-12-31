### This is an individual project I developed in the course Software Development Process.
This is a simple command-line utility called encode and provide some testing cases.

NAME:
encode - encodes words in a file.


SYNOPSIS
encode OPT <filename>

where OPT can be zero or more of
-a [integer]
(-r | -k) <string>
-c <string>
-l <string>


COMMAND-LINE ARGUMENTS AND OPTIONS

<filename>: the file on which the encode operation has to be performed.

-a [integer]: if specified, the utility will encode all alphabetic (a-z, A-Z) characters by applying an Atbash Cipher  (other characters remain unchanged and the capitalization is preserved), so ‘a’ and ‘A’ would be encoded as ‘z’ and ‘Z’, while ‘y’ and ‘Y’ would be encoded as ‘b’ and ‘B’.   If the optional integer is provided, the target alphabet will be shifted to the left that many characters.  So for arguments “-a 1”, ‘a’ and ‘A’ would be encoded as ‘y’ and ‘Y’, ‘b’ is encoded as ‘x’, and ‘c’ is encoded as ‘w’.  This option is always applied last.

k(-r|-k) <string>: if specified, the utility will remove(-r) or keep (-k) only the alphanumeric characters (capitalization insensitive) in the file which are included in the required <string>.   All non-alphanumeric characters are unaffected.  -r and -k are mutually exclusive.  A provided string with no alphanumeric characters will result in a usage error.

-c <string>: if specified, the encode utility will reverse the capitalization (i.e., lowercase to uppercase, and uppercase to lowercase) of all the occurrences in the file, of the letters specified in the required <string> argument.  A provided string with no alphabetic characters will result in a usage error.

-l <string>: if specified, the encode utility will output (to standard output) any lines in the file containing all of the characters in <string>. This option is always applied first.

If none of the OPT flags is specified, encode will default to applying -c to all letters (A-Z).
NOTES

While the last command-line parameter provided is always treated as the filename, OPT flags can be provided in any order; no matter the order of the parameters, though, option -a will always be applied last.


