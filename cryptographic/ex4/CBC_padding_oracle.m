
clear all;

TARGET = 'http://crypto-class.appspot.com/po?er=';
TARGET = 'https://www.baidu.com/';
secretdata = 'f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4';


sd = str2hex_byte(secretdata);

IV_char = secretdata(1:32);
IV_num = sd(1:16);

for cc = 1:(length(sd)/16 - 1)
    ct_index = (1:16) + cc * 16;
    ciphertext(cc,:) = sd(ct_index);
end


byte = 16;
correct_lastbyte = 207;
correct_msg(1,:) = [84, 104, 101, 32, 77, 97, 103, 105, 99, 32, 87, 111, 114, 100, 115, 32];
correct_msg(2,:)= [97, 114, 101, 32, 83, 113, 117, 101, 97, 109, 105, 115, 104, 32, 79, 115];
correct_msg(3,:)= [115, 105, 102, 114, 97, 103, 101, 9, 9, 9, 9, 9, 9, 9, 9, 9];

pad_2 = bitxor(bitxor(correct_lastmsg,2),correct_lastbyte);
for guess = 0:255
    
    ct_revise = ciphertext(1:2,:);
    ct_revise(1,byte) = bitxor(bitxor(ciphertext(1,byte),guess),1);
    ct_revise_str = (dec2hex(ct_revise(:)))';
    ct_revise_str = (ct_revise_str(:))';
    
    url = [TARGET,IV_char,ct_revise_str];
    

end