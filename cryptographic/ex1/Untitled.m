
clear all

n = 500;
for nn = 1:n
    nn
    for k = 1:nn
        T(k) = nchoosek(nn,k) * (1 + 1/k)^k * (k + 1);
    end 

    S(nn) = 1/(nn * 2^nn) * sum(T);
%     S(nn) = sum(T) - 2^n * n * exp(1);
end

plot(S - exp(1)/2);