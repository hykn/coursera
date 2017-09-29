% 
cd  = 'C:\\Users\\hyk_seu\\Documents\\MATLAB\\Graduation Project\\sakura\\sbox';

ptlen = 3000;
timelen = 1000;
key = zeros(1,16);
s = aesinit(key);
% data = zeros(50002,2,ptlen);
% PowerTrace_sbox = zeros(ptlen,timelen);
load('PowerTrace_sbox');
% 
% for i = 1:ptlen;
%     i
%     file_index = i - 1;
%     cd_power = sprintf([cd,'\\C2power traces%05d.dat'],file_index);   
%     data = load(cd_power);
%     if i == 1;
%         TimeTrace = data(1:timelen,1);
%     end
%     PowerTrace_sbox(i,:) = data(1:timelen,2);
% %     PowerTrace_cpr(i,:) = data(compress_index,2);
% end


dd = 1;
for ii = 0:255
    for jj = 0:255
        state_sbox(dd,:) = [jj,ii];
        dd = dd + 1;
    end
end

clear H_hypoPower_sbox;
for dd = 1:ptlen
    H_hypoPower_sbox(dd) = sum(bitget(bitxor(state_sbox(dd,1),state_sbox(dd,2)),1:8));
end

% for dd = 1:ptlen
%     H_hypoPower_sbox(dd) = sum(bitget(state_sbox(dd,1),1:8));
% end

% for dd = 1:ptlen
%     sbox_out = s.s_box(state_sbox(dd,1) + 1);
%     H_hypoPower_sbox(dd) = sum(bitget(sbox_out,1:8));
% end 

hold on;
plot(corr(H_hypoPower_sbox',PowerTrace_sbox),'r');
plot(PowerTrace_sbox(256,:),'b');
