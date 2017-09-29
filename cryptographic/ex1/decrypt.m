



clear all;
cd_ciphertexts = 'ciphertexts.txt';
data = importdata(cd_ciphertexts);
for ii = 1:10
    text_char = char(data(ii,:));
    s = size(text_char);
    text_char = (reshape(text_char,[2,s(2)/2]))';
    text_double = hex2dec(text_char);
    ciphertext{ii} = text_double;
end

% index_bit = 1:10;
% line_2 = combntns(index_bit,2);

for  cc = 1:10
    
    clear index_value xor len true_check;
    len = zeros(1,10);
    for jj = [1:cc - 1,cc + 1:10]
        xor = bitxor_dl(ciphertext{cc},ciphertext{jj});
        index_value{jj} = find((xor <= 90 & xor >= 65) | (xor <= 122 & xor >=97));
        len(jj) = length(index_value{jj});
    end
    
    [min_len,index_min] = sort(len);
    min_len = min_len(2);
    index_min = index_min(2);
    for tt = 1:min_len
        num_check = index_value{index_min}(tt);
        true_check(tt) = 1;
        for vv = [1:cc - 1,cc + 1:10]
            true_check(tt) = ismember(num_check,index_value{vv});
        end
    end

    index_space{cc} = index_value{index_min}(find(true_check == 1));
end
