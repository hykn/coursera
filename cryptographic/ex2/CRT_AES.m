
clear all;


keyh = {'36' 'f1' '83' '57' 'be' '4d' 'bd' '77' 'f0' '50' '51' '5c' '73' 'fc' 'f9' 'f2'};
key = hex2dec(keyh);
s = aesinit(key);
Ciphertext = '69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329';
% Ciphertext = '770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451';

CT = (reshape(Ciphertext,2,length(Ciphertext)/2))';
CT = (hex2dec(CT))';

% CT = (reshape(CT,16,length(CT)/16))';
IV = CT(1:16);

for ii = 0:4
    IMD((1:16) + 16 * ii) = aesencrypt(s,[IV(1:15),IV(16) + ii]);
end

PT = bitxor(CT(17:end),IMD(1:length(CT(17:end))));
cc = (char(PT(:)))'

