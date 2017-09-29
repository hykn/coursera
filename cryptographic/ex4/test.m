

% 
clear all;
clc;

ciphertext = '20814804c1767293b99f1d9cab3bc3e7ac1e37bfb15599e5f40eef805488281d';
ct = str2hex_byte(ciphertext);

plaintext = 'Pay Bob 100$'
plaintext_rv = 'Pay Bob 500$';
pt = uint8(plaintext);

ciphertext_rv = '20814804c1767293bd9f1d9cab3bc3e7ac1e37bfb15599e5f40eef805488281d';