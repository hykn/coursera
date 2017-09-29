
function xor_value = bitxor_dl(text1,text2)

len_1 = length(text1);
len_2 = length(text2);

len_xor = min(len_1,len_2);

xor_value = bitxor(text1(1:len_xor),text2(1:len_xor));