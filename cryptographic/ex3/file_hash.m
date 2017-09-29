


%% Hash file in blocks


%% Hash test video file
% This script is used for check whether block encoding code is right

clear all;

block_len = 1024;

file_index = '6 - 2 - Generic birthday attack (16 min).mp4';
fid = fopen(file_index);

bitstream = fread(fid,'*uint8');

fclose(fid);

[H_0,H] = block_hash(bitstream,block_len);

pause;
%% Hash given video file 

file_index = '6 - 1 - Introduction (11 min).mp4';
fid = fopen(file_index);

bitstream = fread(fid,'*uint8');

fclose(fid);

[H_0,H] = block_hash(bitstream,block_len);
