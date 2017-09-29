
function [hash_0,hash_array] = block_hash(bitstream,block_len)


block_len = 1024;
hash_len = 32;

file_len = length(bitstream);
block_num = floor(file_len / block_len);

block_array = reshape(bitstream(1:(block_num * block_len)),[block_len,block_num]);
block_array = block_array';

block_last = bitstream((block_num * block_len + 1) : end);
block_last = block_last';

clear hash_array;
hash_char = (hash(block_last,'sha256'));
hash_byte = (reshape(hash_char,2,length(hash_char)/2))';

hash_array(block_num,:) = uint8((hex2dec(hash_byte))');

for bb = block_num:-1:2
    block_handle_index = bb
    hash_char = hash([block_array(bb,:),hash_array(bb,:)],'sha256');
    hash_byte = (reshape(hash_char,2,length(hash_char)/2))';

    hash_array(bb - 1,:) = uint8((hex2dec(hash_byte))');
end

hash_0 = hash([block_array(1,:),hash_array(1,:)],'sha256');

end