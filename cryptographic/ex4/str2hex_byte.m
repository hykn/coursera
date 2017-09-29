


function hex_byte = str2hex_byte(str)

str_len = length(str);
str_reform = (reshape(str,[2,str_len/2]))';
hex_byte = (hex2dec(str_reform))';

end