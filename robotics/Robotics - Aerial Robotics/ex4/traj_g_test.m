
clear all;

waypoints = [0    0   0;
             1    1   1;
             2    0   2;
             3    -1  1;
             -4    0   0]';
         
% waypoints = [0,0,0;1,1,1;2,2,2]';
% trajhandle = @traj_generator;
% trajhandle([],[],waypoints);


d = waypoints(:,2:end) - waypoints(:,1:end-1);
d0 = 2 * sqrt(d(1,:).^2 + d(2,:).^2 + d(3,:).^2);
traj_time = [0, cumsum(d0)];

S = traj_time;
T = traj_time(2:end) - traj_time(1:end - 1);


n = size(waypoints,2) - 1;
power_max = 7;
k = power_max + 1;

syms t S_is1 T_i P;
for kk = 1:k
    P(1,kk) = ((t - S_is1)/T_i) ^ (kk - 1);
end

for kk = 2:k
    P(kk,:) = diff(P(kk - 1,:),t);
end

A = zeros(k * n , k * n);
B_X = zeros(k * n , 1);
B_Y = zeros(k * n , 1);
B_Z = zeros(k * n , 1);

w_x = waypoints(1,:);
w_y = waypoints(2,:);
w_z = waypoints(3,:);


% alpha matrix:
% aph10 ; aph11 ; aph12 ; aph13 ; ... aph17 ; aph20 ; aph21 ; ... ; aphn0 ;
% aphn1 ; ... aphn6; aphn7;


% 2n points 
% p_i(S_i-1) = w_i-1
% p_i(S_i) = w_i

for nn = 1:2:(2 * n - 1)
    nn
    
    n_index = (nn + 1)/2;
    alpha_index = (1:k) + k * (n_index - 1);
    
    % X
    A(nn,alpha_index) = subs(P(1,:),[t,S_is1,T_i],[S(n_index - 1 + 1),S(n_index - 1 + 1),T(n_index)]);
    A(nn + 1,alpha_index) = subs(P(1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index - 1 + 1),T(n_index)]);
    
    B_X(nn) = w_x(n_index - 1 + 1);
    B_X(nn + 1) = w_x(n_index + 1);

    B_Y(nn) = w_y(n_index - 1 + 1);
    B_Y(nn + 1) = w_y(n_index + 1);

    B_Z(nn) = w_z(n_index - 1 + 1);
    B_Z(nn + 1) = w_z(n_index + 1);

end

% 6 points
% p_1(k)(S_0) = p_n(k)(S_0) = 0; k = 1,2,3

nn = 2 * n + 1;
for kk = 1:3
    n_index = 1;
    A(nn, 1:k) = subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index - 1 + 1),S(n_index - 1 + 1),T(n_index)]);
    B(nn) = 0;
    nn = nn + 1;
    
    n_index = n;
    A(nn,(1:k) + (n - 1) * k) = subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index - 1 + 1),T(n_index)]);
    B(nn) = 0;
    
    nn = nn + 1;
end


% 6n - 6 points
% p_i(k)(S_i) = p_i+1(k)(S_i) k = 1,2,..6

nn = 2 * n + 7;
for n_index = 1:n - 1
    alpha_index = (1:k) + k * (n_index - 1);
    alpha_index_nia1 = (1:k) + k * n_index;
    for kk = 1:6
        nn
%         nn = n_start_index + 6 * (n_index - 1) + kk - 1;
        A(nn,alpha_index) = subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index - 1 + 1),T(n_index)]);
        A(nn,alpha_index_nia1) = - subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index + 1),T(n_index + 1)]);
        
        B(nn) = 0;
        
        nn = nn + 1;
    end
end


Alpha_X = inv(A) * B_X;
Alpha_Y = inv(A) * B_Y;
Alpha_Z = inv(A) * B_Z;

%% plot

t_plot = 0:0.1:S(end);
t_rel = 0;
clear des_state;
for tt = 1:length(t_plot)
    tt
    if t_plot(tt) == 0
        des_state.posX(tt) = 0;
        
    else
        t_index = find(S >= t_plot(tt),1);
        t_index = t_index - 1;
        
        alpha_index = (1:k) + k * (t_index - 1);
        alpha_select = Alpha_X(alpha_index);
        des_state.posX(tt) = subs(P(1,:),[t,S_is1,T_i],[t_plot(tt),S(t_index - 1 + 1),T(t_index)]) * alpha_select;
    end
end


hold on;
plot(t_plot,des_state.posX,'r');
plot(S,waypoints(1,:),'b');