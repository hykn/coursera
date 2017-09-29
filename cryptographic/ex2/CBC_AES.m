
clear all;


keyh = {'14' '0b' '41' 'b2' '2a' '29' 'be' 'b4' '06' '1b' 'da' '66' 'b6' '74' '7e' '14'};
key = hex2dec(keyh);
s = aesinit(key);
% Ciphertext = '4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81';
Ciphertext = '5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253';

CT = (reshape(Ciphertext,2,length(Ciphertext)/2))';
CT = hex2dec(CT);
CT = (reshape(CT,16,length(CT)/16))';

IV = CT(1,:);

PT(1,:) = bitxor(IV,aesdecrypt(s,CT(2,:)));
PT(2,:) = bitxor(CT(2,:),aesdecrypt(s,CT(3,:)));
PT(3,:) = bitxor(CT(3,:),aesdecrypt(s,CT(4,:)));
PT = PT';
cc = (char(PT(:)))'

%1. Basic CBC mode encryption needs padding.
%2. 
%3. CTR mode lets you build a stream cipher from a block cipher.
%4. Always avoid the two time pad!