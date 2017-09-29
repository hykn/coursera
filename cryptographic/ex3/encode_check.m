


%% h_0 is known for video 6-2
% This script is used for check whether block encoding code is right

clear all;
h_0_h = '03c08f4ee0b576fe319338139c045c89c3e8e9409633bea29442e21425006ea8';

h_0 = hex2dec(h_0_h');
h_0 = h_0';

fid = fopen('video_bin.bin');

A = fread(fid);

block_care = A(1:1024 + 32)';

clear A;
fclose(fid);

% load('block_care');

block_care_uint8 = uint8(block_care);
block_care_double = block_care;

test_string_c = 'Rosetta code';
test_string = uint8(test_string_c);

test_SHA256_h = '764faf5c61ac315f1497f9dfa542713965b785e5cc2f707d6468d7d1124cdfcf';
test_SHA256 = hex2dec(test_SHA256_h');

test_hash = hash(test_string,'sha256')
block_uint8_hash = hash(block_care_uint8,'sha256')

