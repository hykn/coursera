
Etext = '32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904';
% Etext = '315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef3e';
% Etext = data{6};
text_char = char(Etext);
s = size(text_char);
text_char = (reshape(text_char,[2,s(2)/2]))';
Etext = hex2dec(text_char);
len_Etext = length(Etext);
Ptext = zeros(1,len_Etext);
for ii = 1:10
    ii
    cxor = bitxor_dl(Etext,ciphertext{ii});
    index_select = find(index_space{ii} <= len_Etext);
    Ptext(index_space{ii}(index_select)) = cxor(index_space{ii}(index_select));
    Ptext_char = char(bitxor(Ptext,32 * ones(1,len_Etext)))
%     Ptext_char = char(bitxor(Ptext,32 * ones(1,len_Etext)))
end

% Ptext = 32 * 
% Ptext = bitxor(Ptext,32 * ones(1,83));
% Ptext_char = char(Ptext);
% The secuet meszage is: Wh n  s + e sstr    cipher, neve< use theokey more than once
% The secute message is: When using         stream cipher, never use the key more
% than once
% [116,72,69,0,83,69,67,85,69,84,0,77,69,83,90,65,71,69,0,73,83,26,0,119,72,0,78,0,0,83,0,11,0,69,0,83,83,84,82,0,0,0,0,67,73,80,72,69,82,12,0,78,69,86,69,28,0,85,83,69,0,84,72,69,79,75,69,89,0,77,79,82,69,0,84,72,65,78,0,79,78,67,69;]
%   T  h   e    s  e c  u   e  t    m  e  s  s  a  g  e _  i  s  : _  W  h  _    u _  s _e the        stream                          c  i  p  h  e  r, never use the key more than once
% The nicb thing)abo t Keey oq i e * swe    ptographers c/  drive . lot of  ancy cars    an Bo . 
% The nice thing                         cryp
% 'y' in Ptext_char_2 No.26   

Ptext_char_c{3}  = 'The nicb thing)abo t Keey oq i e * swe    ptographers c/  drive . lot of  ancy cars    an Bo . ';
Ptext_char_c{2}  = 'Eul r whuld prfbab y enjoy t a e * this   eorem becomesn  cornerostone of cryptography nymou k' ;
Ptext_char_c{4}  = 'The cipoertext produced b  a w $ e =cry   on algorithm " oks as (ood as c phertext p o uced  2h';
%%%%%%%%%%%%%%%%    The secuet meszage is: Wh n  s + e sstr    cipher, neve< use theokey more than once
Ptext_char_c{5}  = 'You don t want)to  uy a s t  f & 7 8eys   om a guy who = ecializ*s in ste ling cars    arc R ?-                           ';
Ptext_char_c{6}  = 'The e aue two }ype  of cryptographys- t    which will k+ p secre;s safe f om your li t e sis .:                                                                             ';
Ptext_char_c{7}  = '';
Ptext_char_c{8}  = '';
Ptext_char_c{9}  = '';
Ptext_char_c{10} = '';
Ptext_char_c{11} = 'The secure message is: When using a stream cipher, never use the key more than once';